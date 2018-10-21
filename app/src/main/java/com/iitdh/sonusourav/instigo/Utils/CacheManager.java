package com.iitdh.sonusourav.instigo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bhavesh on 07/10/17.
 */

public class CacheManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREF_NAME = "MyCache";

    private static final String MENU = "menu";
    private static final String NEWS = "news";
    private static final String TIME = "time";
    private static final String NOTIFICATIONS = "notifications";

    public CacheManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void addMenu(String menu){
        editor.putString(MENU, menu);
        editor.commit();
    }

    public void addNews(String news){
        editor.putString(NEWS, news);
        editor.commit();
    }

    public void addTime(String time){
        editor.putString(TIME, time);
        editor.commit();
    }

    public void addNotification(String notification) {
        StringBuffer temp = new StringBuffer();
        temp.append(""+pref.getString(NOTIFICATIONS, null));
        temp.append("~~~~"+notification);
        editor.putString(NOTIFICATIONS, temp.toString());
        editor.commit();
    }

    public String getMenu() {
        return pref.getString(MENU, null);
    }

    public String getNews() {
        return pref.getString(NEWS, null);
    }

    public String getTime() {
        return pref.getString(TIME, null);
    }

    public String getNotifications() {
        return pref.getString(NOTIFICATIONS, "");
    }

}
