package com.example.sembakomobile.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.sembakomobile.Model.Retrofit.DataKeranjang;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartSaveProduct {
    private static final String LIST_KEY = "list_key";
    public static void writeListKeranjang(Context context, List<DataKeranjang> list){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    public static List<DataKeranjang> readListKeranjang(Context context){
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = shared.getString(LIST_KEY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<DataKeranjang>>() {}.getType();
        List<DataKeranjang> list = gson.fromJson(jsonString, type);
        return list;
    }
}
