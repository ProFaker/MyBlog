package com.wjj.top.service.lucene;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CSearchConfig implements InitializingBean{

    /**
     * 索引存储目录
     */
    @Value("${csearch.indexStorePath}")
    private String indexStorePath;

    /**
     * 索引存储目录
     */
    @Value("${csearch.documentPath}")
    private String docStorePath;

    /**
     * 高亮标签
     */
    @Value("${csearch.highlighter.opening}")
    private String highlighterOpening;

    /**
     * 高亮标签
     */
    @Value("${csearch.highlighter.closing}")
    private String highlighterClosing;

    /**
     * 搜索结果片段长度
     */
    @Value("${csearch.fragmentSize}")
    private Integer fragmentSize;

    private Formatter highLighterFormatter;


    public String getIndexStorePath() {
        return indexStorePath;
    }

    public void setIndexStorePath(String indexStorePath) {
        this.indexStorePath = indexStorePath;
    }

    public String getHighlighterOpening() {
        return highlighterOpening;
    }

    public void setHighlighterOpening(String highlighterOpening) {
        this.highlighterOpening = highlighterOpening;
    }

    public String getHighlighterClosing() {
        return highlighterClosing;
    }

    public void setHighlighterClosing(String highlighterClosing) {
        this.highlighterClosing = highlighterClosing;
    }

    public Integer getFragmentSize() {
        return fragmentSize;
    }

    public void setFragmentSize(Integer fragmentSize) {
        this.fragmentSize = fragmentSize;
    }

    public Formatter getHighLighterFormatter() {
        return highLighterFormatter;
    }

    public void setHighLighterFormatter(Formatter highLighterFormatter) {
        this.highLighterFormatter = highLighterFormatter;
    }

    public String getDocStorePath() {
        return docStorePath;
    }

    public void setDocStorePath(String docStorePath) {
        this.docStorePath = docStorePath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isBlank(indexStorePath)) throw  new IllegalArgumentException("indexStorePath can't be empty");
        if (StringUtils.isBlank(this.highlighterOpening)||StringUtils.isBlank(this.highlighterClosing)){
            this.highLighterFormatter=new SimpleHTMLFormatter();
        }else {
            this.highLighterFormatter=new SimpleHTMLFormatter(this.highlighterOpening,this.highlighterClosing);
        }
        if (fragmentSize==null){
            fragmentSize=150;
        }
    }
}
