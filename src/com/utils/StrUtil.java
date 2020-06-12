package com.utils;

public class StrUtil {
    public static boolean isNullOrEmpty(String str){

        if(str==null || "".equals(str.trim())){
            return true;
        }
        else{
            return false;
        }
    }
}
