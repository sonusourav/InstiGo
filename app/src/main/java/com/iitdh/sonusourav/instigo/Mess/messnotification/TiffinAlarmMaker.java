package com.iitdh.sonusourav.instigo.Mess.messnotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;


public class TiffinAlarmMaker {

    public static void makeAlarm(Context context) {

        Calendar tiffinTime = Calendar.getInstance();
        tiffinTime.set(Calendar.HOUR_OF_DAY,16);
        tiffinTime.set(Calendar.MINUTE,30);
        tiffinTime.set(Calendar.SECOND,0);
        if (Calendar.getInstance().after(tiffinTime))
            tiffinTime.add(Calendar.DATE, 1);

        Intent alarmIntent = new Intent(context, TiffinAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 103, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, tiffinTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}
