package com.iitdh.sonusourav.instigo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

  private static final String PREF_NAME = "instiGo";
  private static final String IS_FIRST_TIME_LAUNCH = "isFirstTimeLaunch";
  private static final String IS_LOGGED_IN = "isLoggedIn";
  private static final String IS_FIRST_GOOGLE_LOGIN = "isFirstGoogleLogin";
  private static final String IS_PASSWORD_UPDATED = "isPasswordUpdated";
  private static final String PREF_EMAIL = "email";
  private static final String PREF_USERNAME = "username";
  private static final String PREF_PASSWORD = "password";
  private static final String USER_ID = "userId";
  private static final String AUTH_TYPE = "authType";
  private static final String PASS_LAST_UPDATED = "lastUpdated";
  private static String FCM_TOKEN = "fcmToken";
  private static String USER_LEVEL = "userLevel";

  private SharedPreferences pref;
  private SharedPreferences.Editor editor;

  public PreferenceManager(Context context) {
    int PRIVATE_MODE = 0;
    pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    editor = pref.edit();
    editor.apply();
  }

  public int getUserLevel() {
    return pref.getInt(USER_LEVEL, 0);
  }

  public void setUserLevel(int userLevel) {
    editor.putInt(USER_LEVEL, userLevel);
    editor.commit();
  }

  public String getFcmToken() {
    return pref.getString(FCM_TOKEN, null);
  }

  public void setFcmToken(String fcmToken) {
    editor.putString(FCM_TOKEN, fcmToken);
    editor.commit();
  }

  public void setAuthType(String authType) {
    editor.putString(AUTH_TYPE, authType);
    editor.commit();
  }

  public void setLoginCredentials(String email, String password, String authType) {
    editor.putString(PREF_EMAIL, email);
    editor.putString(PREF_PASSWORD, password);
    editor.putString(AUTH_TYPE, authType);
    editor.commit();
  }

  public void setIsLoggedIn(boolean isLoggedIn, String userId) {
    editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
    editor.putString(USER_ID, userId);
    editor.commit();
  }

  public void setUserId(String userId) {
    editor.putString(USER_ID, userId);
    editor.commit();
  }

  public void setIsFirstGoogleLogin(boolean googleLogin) {
    editor.putBoolean(IS_FIRST_GOOGLE_LOGIN, googleLogin);
    editor.commit();
  }

  public void setIsPassUpdated(boolean passUpdated) {
    editor.putBoolean(IS_PASSWORD_UPDATED, passUpdated);
    editor.commit();
  }

  public void setPassLastUpdated(long passLastUpdated) {
    editor.putLong(PASS_LAST_UPDATED, passLastUpdated);
    editor.commit();
  }

  public void setFirstTimeLaunch(boolean isFirstTime) {
    editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
    editor.commit();
  }

  public void setPrefName(String name) {
    editor.putString(PREF_USERNAME, name);
    editor.commit();
  }

  public void setPrefEmail(String email) {
    editor.putString(PREF_EMAIL, email);
    editor.commit();
  }

  public long passLastUpdated() {
    return pref.getLong(PASS_LAST_UPDATED, 0);
  }

  public String getPrefEmail() {
    return pref.getString(PREF_EMAIL, "email");
  }

  public String getPrefPassword() {
    return pref.getString(PREF_PASSWORD, "password");
  }

  public String getUserId() {
    return pref.getString(USER_ID, "userId");
  }

  public String getUserName() {
    return pref.getString(PREF_USERNAME, "username");
  }

  public String getAuthType() {
    return pref.getString(AUTH_TYPE, "authType");
  }

  public boolean isLoggedIn() {
    return pref.getBoolean(IS_LOGGED_IN, false);
  }

  public boolean isFirstGoogleLogin() {
    return pref.getBoolean(IS_FIRST_GOOGLE_LOGIN, true);
  }

  public boolean isPassUpdated() {
    return pref.getBoolean(IS_PASSWORD_UPDATED, false);
  }

  public boolean isFirstTimeLaunch() {
    return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
  }
}