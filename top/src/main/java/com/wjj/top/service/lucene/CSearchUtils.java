package com.wjj.top.service.lucene;


import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.css.CSSCharsetRule;

import java.util.List;

public class CSearchUtils {

    @Autowired
    private static CSearchImpl cSearch = new CSearchImpl();

    /**
     * 创建批量索引
     * @return
     */
    public static boolean createIndex(CSearchConfig cSearchConfig){
        return cSearch.createIndex(cSearchConfig);
    }

    /**
     * 创建增量索引
     * @return
     */
    public static boolean createIndex(CSearchConfig cSearchConfig,CSearchDocument doc){
        return cSearch.createIndex(cSearchConfig,doc);
    }

    /**
     * 搜索
     * @param cSearchConfig
     * @param key
     * @param num
     * @return
     * @throws Exception
     */
    public static List<CSearchDocument> search(CSearchConfig cSearchConfig,String key,int num) throws Exception {
        return cSearch.search(cSearchConfig,key,num);
    }
}
