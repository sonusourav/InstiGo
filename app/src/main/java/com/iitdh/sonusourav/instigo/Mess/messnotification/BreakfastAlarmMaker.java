package com.iitdh.sonusourav.instigo.Mess.messnotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;




public class BreakfastAlarmMaker {

    public static void makeAlarm(Context context) {

        Calendar breakfastTime = Calendar.getInstance();
        breakfastTime.set(Calendar.HOUR_OF_DAY,7);
        breakfastTime.set(Calendar.MINUTE,30);
        breakfastTime.set(Calendar.SECOND,0);
        if (Calendar.getInstance().after(breakfastTime))
            breakfastTime.add(Calendar.DATE, 1);

        Intent alarmIntent = new Intent(context, BreakfastAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, breakfastTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}
