package com.wjj.top.service;

import org.apache.commons.lang.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

@Service
public class SensitveService implements InitializingBean{

    private static Logger logger = LoggerFactory.getLogger(SensitveService.class);
    @Override
    public void afterPropertiesSet() throws Exception {

        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("sensitiveWords.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineText ="";
            while ((lineText=bufferedReader.readLine())!=null){
                System.out.println(lineText);
                addWord(lineText.trim());
            }
            inputStreamReader.close();
        }catch (Exception e){
            logger.error("读取敏感词失败！");
        }
    }

    //增加关键词
    private void addWord(String lineText){
        TrieNode tempNode = root;
        for(int i = 0;i<lineText.length();i++){
            Character c = lineText.charAt(i);
            TrieNode node = tempNode.getSubNode(c);

            if(node == null){
                node = new TrieNode();
                tempNode.addSubNode(c,node);
            }

            tempNode = node;

            if(i == lineText.length()-1)
                node.setIsKeywordEnd(true);

        }
    }


    private class TrieNode{
        private boolean end = false;

        private Map<Character,TrieNode> subNodes = new HashMap<Character, TrieNode>();

        public void addSubNode(Character key,TrieNode node){
            subNodes.put(key,node);
        }

        public TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }

        public boolean isKeywordEnd(){
            return  end;
        }

        public void setIsKeywordEnd(boolean end){
            this.end = end;
        }
    }

    private TrieNode root = new TrieNode();

    private boolean isSymbol(Character c){
        int ic = (int) c;
        return  !CharUtils.isAsciiAlphanumeric(c) && (ic<0x2E80 || ic>0x9FFF);
    }

    public String filter(String text){
        if(StringUtils.isBlank(text))
            return  text;
        String replacement = "***";
        TrieNode tempNode = root;
        int begin = 0;
        int position = 0;
        StringBuilder result =  new StringBuilder();


        while(position < text.length()){
            Character c =   text.charAt(position);

            if(isSymbol(c)){
                //開始時
                if(tempNode == root){
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }

            tempNode = tempNode.getSubNode(c);
            if(tempNode == null){
                result.append(text.charAt(begin));
                position = begin +1;
                begin = position;
                tempNode = root;
            }else if(tempNode.isKeywordEnd()){
                result.append(replacement);
                position = position+1;
                begin = position;
                tempNode = root;
            }else{
                ++position;
            }
        }
        result.append(text.substring(begin));
        return  result.toString();
    }

    public static void main(String[] args){
        SensitveService service = new SensitveService();
        service.addWord("色情");
        service.addWord("賭博");
        System.out.println(service.filter("  你好，賭 博"));
    }
}
