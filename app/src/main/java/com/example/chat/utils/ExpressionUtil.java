package com.example.chat.utils;

import com.example.chat.App;

public class ExpressionUtil {
    public static int getExpressionByName(String name){
        System.out.println("vvvname"+App.expressionMap.get(name));
        Integer imgRes=App.expressionMap.get(name);
        if (imgRes!=null){
            return imgRes;
        }else{
            return -1;
        }
    }
}
