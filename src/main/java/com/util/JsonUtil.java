package com.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vo.EngineerDefriendJson;

import java.util.List;

/**
 * Created by upupgogogo on 2018/11/9.下午2:54
 */
public class JsonUtil {
    private static Gson gson = new Gson();

    public static String toJonSting(List<EngineerDefriendJson> list){

        String str = JSONObject.toJSONString(list);
        return str;

    }

    public static List<EngineerDefriendJson> toJsonList(String json){
        List<EngineerDefriendJson> list = gson.fromJson(json, new TypeToken<List<EngineerDefriendJson>>(){}.
                getType());

        return list;
    }
}
