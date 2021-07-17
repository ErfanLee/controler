package com.lx.controler;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pc01 on 2021/6/27.
 */

public class Json2Hashmap {
    static public Map getMap(String json){
        Map<String, Double> map = new HashMap<>();//保存读取数据keyValueMap
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                map.put(jsonObject.getString("tag"), jsonObject.getDouble("text"));
            }
            Log.i("DebugTag","getMap()hash map:"+map.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}

