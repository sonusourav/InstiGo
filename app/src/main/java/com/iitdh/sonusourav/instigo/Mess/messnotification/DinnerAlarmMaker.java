package com.iitdh.sonusourav.instigo.Mess.messnotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;



public class DinnerAlarmMaker {

    public static void makeAlarm(Context context) {

        Log.d("DinnerAlarmMaker","Reaching");

        Calendar dinnerTime = Calendar.getInstance();
        dinnerTime.set(Calendar.HOUR_OF_DAY,19);
        dinnerTime.set(Calendar.MINUTE,20);
        dinnerTime.set(Calendar.SECOND,0);
        if (Calendar.getInstance().after(dinnerTime))
            dinnerTime.add(Calendar.DATE, 1);

        Intent alarmIntent = new Intent(context, DinnerAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dinnerTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}
