package com.example.allem.revised_capstone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class PreferenceUtils {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    static final String PREF_NAME = "user_pref";
    static final String IS_LOGIN = "isLoggedIn";
    static final String KEY_NAME = "user_id";


    public PreferenceUtils(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }
    public void createLoginSession(String id){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, id);
        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(context, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public HashMap<String, String > getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        return user;

    }

    public void logout(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
    }

    private boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
