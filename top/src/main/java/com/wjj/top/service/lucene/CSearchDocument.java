package com.wjj.top.service.lucene;

/**
 * Created by chulung on 2016/11/10.
 */
public class CSearchDocument {
    public static final String ID = "id";
    public static  final  String TITLE="title";
    public  static  final  String CONTENT="content";
    public static final String PATHNAME="path";

    /**
     * 唯一ID，创建前会根据id删除已有的
     */
    private String id;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档路径
     */
    private String path;
    /**
     * 文档内容
     */
    private String content;
    /**
     * 完整内容
     */
    private String completeContent;

    /**
     *
     * @param id
     * @param title
     * @param path
     */
    public CSearchDocument(String id,String title,String path){
        if (id==null) throw new IllegalArgumentException("id can't be null!");
        if (title==null) throw new IllegalArgumentException("title can't be null!");
        if (path ==null) throw new IllegalArgumentException("path can't be null!");
        this.id=id;
        this.title=title;
        this.path = path;
    }



    public CSearchDocument(String id,String title,String content,String path){
        if (id==null) throw new IllegalArgumentException("id can't be null!");
        if (title==null) throw new IllegalArgumentException("title can't be null!");
        if (content ==null) throw new IllegalArgumentException("content can't be null!");
        this.id=id;
        this.title=title;
        this.content = content;
        this.path = path;
    }

    public String getCompleteContent() {
        return completeContent;
    }

    public void setCompleteContent(String completeContent) {
        this.completeContent = completeContent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CSearchDocument{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
