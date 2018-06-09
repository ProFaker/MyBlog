package com.wjj.top.utils;


import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TopUtils {

    public  static  String IMAGE_DIR = "D:/PS/";

    public static  String[] IMAGE_FILE_EXT = new String[]{"png","bmp","jpg","jpeg"};

    public  static boolean isFileAllowed(String fileExt){

        for(String ext : IMAGE_FILE_EXT){
            if(ext.equals(fileExt))
                return  true;
        }

        return  false;
    }

    public  static String getProjectPath(HttpServletRequest request){

        return  request.getSession().getServletContext().getRealPath("/");
    }

    public static String getJsonString(int code){
        JSONObject json=new JSONObject();
        json.put("code",code);
        return json.toJSONString();
    }

    public static String getJsonString(int code,String msg){
        JSONObject json=new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        return json.toJSONString();
    }

    public static String getJsonString(int code, Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",code);
        for(Map.Entry<String ,Object> entry : map.entrySet()){
            json.put(entry.getKey(),entry.getValue());
        }
        return json.toJSONString();
    }
}
