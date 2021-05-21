package com.example.smarthome;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.smarthome.Model.User;
import com.google.gson.Gson;


public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    final String USER_KEY = "userKey";


    private static SessionManagement mInstance = null;

    private SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // khởi tạo mInstance
    public static SessionManagement getInstance(Context context){
        if(mInstance == null){
            mInstance = new SessionManagement(context);
        }
        return mInstance;
    }

    public void saveSession(User user){
        //save session of user whenever user is logged in
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        editor.putString(USER_KEY,userJson).commit();
    }

    public String getSession(){
        //return user token whose session is saved
        return sharedPreferences.getString(USER_KEY, null);
    }

    public void removeSession(){
        editor.putString(USER_KEY,null).commit();
    }
}
