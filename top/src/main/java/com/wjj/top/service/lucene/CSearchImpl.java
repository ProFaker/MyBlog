package com.wjj.top.service.lucene;

import com.hankcs.lucene.HanLPAnalyzer;
import com.hankcs.lucene.HanLPIndexAnalyzer;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.*;

import static com.wjj.top.service.lucene.CSearchDocument.*;

/**
 * 创建索引及查询操作
 * Created by wjj on 2016/11/7.
 */

@Service
public class CSearchImpl implements CSearch,InitializingBean{

    /**
     * 文件存储位置
     */
    @Value("${csearch.indexStorePath}")
    public String docPath;
    /**
     * 索引存储位置
     */
    @Value("${csearch.documentPath}")
    public String indexPath;

    private static final int DEFAULT_ROW = 10;
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private Directory fsDirectory;
    @Autowired
    private CSearchConfig cSearchConfig;

    /**  
     * 使用HanLP中文分词，该分词区分大小写，因此索引及查询均对入参做了toLowerCase操作
     */
    private Analyzer analyzer = new HanLPAnalyzer();
    private DirectoryReader reader;
    private IndexWriter indexWriter;


    /**
     * 增量索引
     * @param doc
     * @return
     */
    @Override
    public boolean createIndex(CSearchConfig cSearchConfig,CSearchDocument doc) {
        this.cSearchConfig = cSearchConfig;
        List<CSearchDocument> docs = new ArrayList<>();
        docs.add(doc);
        try{
            //1、创建Dictionary
            fsDirectory = FSDirectory.open(Paths.get(this.cSearchConfig.getIndexStorePath()));
            Analyzer analyzer = new HanLPIndexAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(fsDirectory, indexWriterConfig);
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return this.createIndex(docs);
    }

    @Override
    public boolean createIndex() {
        return false;
    }

    /**
     * 批量创建索引
     * @return
     */
    public boolean createIndex(CSearchConfig cSearchConfig){
        this.cSearchConfig = cSearchConfig;
        List<CSearchDocument> docs = new ArrayList<>();
        try{
            System.out.println(this.cSearchConfig.getIndexStorePath());
            //1、创建Dictionary
            fsDirectory = FSDirectory.open(Paths.get(this.cSearchConfig.getIndexStorePath()));
            Analyzer analyzer = new HanLPIndexAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(fsDirectory, indexWriterConfig);
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            //3、清除已有的index
            indexWriter.deleteAll();
            //4、要搜索的File路径
            File dfile = new File(cSearchConfig.getDocStorePath());
            File[] files = dfile.listFiles();
            for(File file : files) {
                String ID = String.valueOf(file.getName().hashCode());
                String title = file.getName();
                String path = file.getAbsolutePath();
                CSearchDocument document = new CSearchDocument(ID,title,path);
                docs.add(document);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return this.createIndex(docs);
    }

    /**
     * 索引实现
     * @param docs
     * @return
     */
    public boolean createIndex(List<CSearchDocument> docs) {
        IndexWriter writer = null;
        try {
            writer = this.getIndexWriter();
            for (CSearchDocument doc:docs){
                //创建前尝试先删除已有的
                logger.info("tryDelete id={}",doc.getId());
                writer.deleteDocuments(new Term(ID,doc.getId()));
                Document document = new Document();
                document.add(new StringField(ID, doc.getId(), Field.Store.YES));
                //HanLp区分大小写，所以全转小写
                document.add(new TextField(TITLE, doc.getTitle().toLowerCase(), Field.Store.YES));
                document.add(new Field(CONTENT, new FileReader(new File(doc.getPath())), TextField.TYPE_NOT_STORED));
                document.add(new Field(PATHNAME, doc.getPath(), TextField.TYPE_STORED));
                writer.addDocument(document);
                logger.info("createIndex id={}",doc.getId());
            }
        } catch (Exception e) {
            try {
                if (writer!=null)
                writer.rollback();
            } catch (Exception ignored) {
            }
            return false;
        } finally {
            if (writer != null)
                try {
                    writer.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
        return true;
    }

    public boolean createIndex(CSearchDocument doc) {
        return false;
    }

    public void clearAll() {
        try {

            IndexWriter writer = this.getIndexWriter();
            writer.deleteAll();
            writer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CSearchDocument> search(String queryStr, int num) throws Exception {
        return null;
    }

    public List<CSearchDocument> search(CSearchConfig cSearchConfig,String queryStr, int num) throws Exception {

        try{
            this.cSearchConfig = cSearchConfig;
            fsDirectory = FSDirectory.open(Paths.get(this.cSearchConfig.getIndexStorePath()));
            Analyzer analyzer = new HanLPIndexAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(fsDirectory, indexWriterConfig);
        }catch (Exception e){
//            e.printStackTrace();
        }

        if (StringUtils.isBlank(queryStr)) {
            return Collections.emptyList();
        }
        //搜索标题和内容两个字段
        String[] fields = {TITLE, CONTENT};
        QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer);
        //HanLp区分大小写，所以全转小写
        Query query = queryParser.parse(queryStr.toLowerCase());
        IndexSearcher searcher = new IndexSearcher(getReader());
        TopDocs topDocs = searcher.search(query, num);
        //设置高亮格式
        Highlighter highlighter = new Highlighter(this.cSearchConfig.getHighLighterFormatter(), new QueryScorer(query));
        //设置返回字符串长度
//        highlighter.setTextFragmenter(new SimpleFragmenter(150));
        List<CSearchDocument> result = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            //这里的.replaceAll("\\s*", "")是必须的，\r\n这样的空白字符会导致高亮标签错位
            System.out.println(doc.get(PATHNAME));
            String content = TestUtil.readToString(doc.get(PATHNAME));

            //没有高亮字符会返回null
            String highContext = highlighter.getBestFragment(analyzer, CONTENT, content);
            String title = doc.get(TITLE).replaceAll("\\s*", "");
            if(highContext == null){
                highContext = subContext(content);
                System.out.println("highContext:"+highContext);
            }
            CSearchDocument document = new CSearchDocument(doc.get(ID), title, highContext,doc.get(PATHNAME));
//            String highTitle = highlighter.getBestFragment(analyzer, TITLE, title);
//            CSearchDocument document = new CSearchDocument(doc.get(ID), highTitle==null?title:highTitle, highContext==null?subContext(content):highContext,doc.get(PATHNAME));
            document.setCompleteContent(content);
            result.add(document);
        }
        return result;
    }

    /**
     * 根据 {@link CSearchConfig#fragmentSize}截取片段长度
     * @param content
     * @return
     */
    private String subContext(String content) {
        return  content.length()>cSearchConfig.getFragmentSize()?content.substring(0,cSearchConfig.getFragmentSize()).replaceAll("</?[^<]+>", ""):content;
    }

    /**
     * reader 返回当前reader 如果文档有更新则新打开一个
     * @return
     * @throws Exception
     */
    private DirectoryReader getReader() throws Exception {
        if (reader==null){
            this.reader=DirectoryReader.open(fsDirectory);
        }
        //有更新则重新打开,读入新增加的增量索引内容，满足实时查询需求
        DirectoryReader newReader = DirectoryReader.openIfChanged(reader,  getIndexWriter(), false);
        if (newReader != null) {
            reader.close();
            reader = newReader;
        }
        return reader;
    }


    private IndexWriter getIndexWriter() throws Exception {
        return indexWriter;
    }

    @Override
    public List<CSearchDocument> search(String key) throws Exception {
        return  this.search(key, DEFAULT_ROW);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
