package com.example.sembakomobile.Util;

import com.example.sembakomobile.Model.Retrofit.DataItem;
import com.google.gson.Gson;

public class SerializeData {
    public static String serializeData(DataItem dataItem){
        Gson gson = new Gson();
        return gson.toJson(dataItem);
    }
}
