package com.wjj.top.utils;

import java.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FileUtils
{
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static String writeToFile(String content, String title,HttpServletRequest request){
        String fileName = title;
        String host = TopUtils.getProjectPath(request);
        String saveUrl = "src/main/resources/doc/" +fileName + ".txt";
        try {
            File file =new File(saveUrl);
            if(!file.exists()){
                file.createNewFile();
            }

            byte bt[] = content.getBytes();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bt,0,bt.length);
            outputStream.close();

            return saveUrl;
        }catch (Exception e){
            logger.error("文件写入错误",e.getMessage());
            return "";
        }
    }

    public static String readFile(String fileName){

        BufferedReader reader = null;
        String tempString = null;
        String content = "";

        try{
            reader = new BufferedReader(new FileReader(fileName));
            while ((tempString = reader.readLine()) != null){
                content += tempString;
            }
            return content;
        }catch (Exception e){
            logger.error("读取文件失败"+e.getMessage());
            return "";
        }

    }
}
