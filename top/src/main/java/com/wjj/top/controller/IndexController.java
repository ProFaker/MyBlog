package com.wjj.top.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class IndexController {


    @RequestMapping(value = {"/profile/{groupID}/{userID}"})
    public String profile(@PathVariable("groupID") Integer groupID,
                          @PathVariable("userID") Integer userID ,
                          @RequestParam(value="type" ) Integer type,
                          @RequestParam(value="key",defaultValue = "default") String key)
    {

        return  String.format("GID{%d},UID{%d},TYPE{%d},KEY{%s}",groupID,userID,type,key);
    }

    //@RequestMapping(value = "/login")
    public String login(@RequestParam("key") String  key){
        if(key.equals(("admin"))){
            return "{res:hello}";
        }
        throw  new IllegalArgumentException("Key 错误");
    }

    @ExceptionHandler
    public String error(Exception  e){

        return  "error:"+ e.getMessage();
    }
}