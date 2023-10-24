package com.example.sembakomobile.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.example.sembakomobile.Model.Retrofit.DataItem;

import java.util.HashMap;

public class SessionManager {
    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String IS_LOGGER_IN = "isLoggedIn";
    private static final String ID_PEMBELI = "idPembeli";
    private static final String NAMA_PEMBELI = "NamaPembeli";
    private static final String ALAMAT = "Alamat";
    private static final String NO_HP = "no_hp";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";

    public SessionManager(Context context) {
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }
    public void createLoginSession(DataItem user) {
        editor.putBoolean(IS_LOGGER_IN, true);
        editor.putString(ID_PEMBELI, user.getIdPembeli());
        editor.putString(NAMA_PEMBELI, user.getNamaPembeli());
        editor.putString(ALAMAT, user.getAlamat());
        editor.putString(NO_HP, user.getNo_hp());
        editor.putString(USERNAME, user.getUsername());
        editor.putString(PASSWORD, user.getPassword());
        editor.commit();
    }
    public HashMap<String,String> getUserDetail() {
        HashMap<String,String> user = new HashMap<>();
        user.put(ID_PEMBELI, sharedPreferences.getString(ID_PEMBELI, null));
        user.put(NAMA_PEMBELI, sharedPreferences.getString(NAMA_PEMBELI, null));
        user.put(ALAMAT, sharedPreferences.getString(ALAMAT, null));
        user.put(NO_HP, sharedPreferences.getString(NO_HP, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        return user;
    }
    public void loginSession() {
        editor.clear();
        editor.commit();
    }
    public boolean logoutSession() {
        return  sharedPreferences.getBoolean(IS_LOGGER_IN, false);
    }
}

