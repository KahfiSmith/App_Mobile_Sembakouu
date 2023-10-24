package com.example.sembakomobile.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.sembakomobile.Model.Retrofit.DataItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class SaveAccount {
    private static final String LIST_PEMBELI = "list_pembeli";

    public static void writeDataPembeli(Context context, DataItem dataItem){
        Gson gson = new Gson();
        String jsonString = gson.toJson(dataItem);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_PEMBELI, jsonString);
        editor.apply();
    }

    public static DataItem readDataPembeli(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_PEMBELI, "");
        Gson gson = new Gson();
        Type type = new TypeToken<DataItem>() {}.getType();
        DataItem dataPembeli = gson.fromJson(jsonString, type);
        return dataPembeli;
}
}
