package com.iitdh.sonusourav.instigo.Mess.messnotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;
import java.util.Calendar;


public class LunchAlarmMaker {

    @RequiresApi(api = VERSION_CODES.M)
    public static void makeAlarm(Context context) {

        Calendar lunchTime = Calendar.getInstance();
        lunchTime.set(Calendar.HOUR_OF_DAY,12);
        lunchTime.set(Calendar.MINUTE,30);
        lunchTime.set(Calendar.SECOND,0);
        if (Calendar.getInstance().after(lunchTime))
            lunchTime.add(Calendar.DATE, 1);

        Intent alarmIntent = new Intent(context, LunchAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, lunchTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}
