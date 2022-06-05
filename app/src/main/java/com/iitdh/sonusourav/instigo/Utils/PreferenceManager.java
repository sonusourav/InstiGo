package com.iitdh.sonusourav.instigo.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferenceManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    // shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "iitdh";
    private static final String IS_FIRST_TIME_LAUNCH = "isFirstTimeLaunch";
    private static final String IS_LOGGED_IN = "idLoggedIn";
    private static final String IS_FIRST_GOOGLE_LOGIN = "isFirstGoogleLogin";
    private static final String IS_PASSWORD_UPDATED = "isPasswordUpdated";
    private static final String PREF_EMAIL = "email";
    private static final String PREF_PASSWORD = "password";

    public PreferenceManager(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();

    }

    public void setLoginCredentials(String email,String password){
        editor.putString(PREF_EMAIL, email);
        editor.putString(PREF_PASSWORD, password);
        editor.commit();
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public void setIsFirstGoogleLogin(boolean googleLogin) {
        editor.putBoolean(IS_FIRST_GOOGLE_LOGIN, googleLogin);
        editor.commit();
    }

    public void setIsPassUpdated(boolean passUpdated) {
        Log.d("ClassName1",context.getClass().getSimpleName());
        editor.putBoolean(IS_PASSWORD_UPDATED, passUpdated);
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    public String getPrefEmail(){
       return pref.getString(PREF_EMAIL,"email");
    }

    public String getPrefPassword(){
        return pref.getString(PREF_PASSWORD,"password");
    }

    public boolean isLoggedIn() { return pref.getBoolean(IS_LOGGED_IN, false); }

    public boolean isFirstGoogleLogin() {
        return pref.getBoolean(IS_FIRST_GOOGLE_LOGIN, true);
    }

    public boolean isPassUpdated() {
        Log.d("ClassName",context.getClass().getSimpleName());
        return pref.getBoolean(IS_PASSWORD_UPDATED, false); }

    public boolean isFirstTimeLaunch() { return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true); }
}