package com.iitdh.sonusourav.instigo.Mess.messnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.preference.PreferenceManager;
import androidx.core.app.NotificationCompat;

import com.iitdh.sonusourav.instigo.Account.SplashActivity;
import com.iitdh.sonusourav.instigo.R;

import java.util.Calendar;


public class BreakfastAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent repeatingIntent = new Intent(context, SplashActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        String menu = null;


            Calendar now = Calendar.getInstance();
            int day = now.get(Calendar.DAY_OF_WEEK);
            switch (day) {
                case Calendar.MONDAY:
                    menu = "Poha,\n" + "Usal Matki,\n" + "Sev/Namkeen,\n" + "Moong Sprouts,\n" + "Boiled egg,\n" + "Banana,\n" + "Paneer Bhurji";
                    break;
                case Calendar.TUESDAY:
                    menu = "Tomato-onion Utappam,\n" + "Coconut Chutney\n" + "Sambar\n" + "Chickpea Sprouts,\n" + "Egg Bhurji,\n" + "Seasonal Fruit,\n" + "Sweet Corn";
                    break;
                case Calendar.WEDNESDAY:
                    menu = "Mix-Veg Paratha,\n" + "Curd,\n" + "Adzuki Sprouts,\n" + "Omelette,\n" + "Banana,\n" + "Paneer Bhurji";
                    break;
                case Calendar.THURSDAY:
                    menu = "Daliya Lapsi,\n" + "Coconut Chutney,\n" + "Moong Sprouts,\n" + "Boiled Egg,\n" + "Banana,\n" + "Sweet Corn";
                    break;
                case Calendar.FRIDAY:
                    menu = "Mooli Paratha,\n" + "Curd,\n" + "Chickpea Sprouts,\n" + "Egg Bhurji,\n" + "Seasonal Fruit,\n" + "Paneer Bhurji";
                    break;
                case Calendar.SATURDAY:
                    menu = "Sevai Upma,\n" + "Coconut Chutney,\n" + "Adzuki Sprouts,\n" + "Omelette Egg,\n" + "Banana,\n" + "Sweet Corn";
                    break;
                case Calendar.SUNDAY:
                    menu = "Rava Masala Dosa,\n" + "Coconut Chutney,\n" + "Sambar,\n" + "Moong Sprouts,\n" + "Boiled Egg,\n" + "Seasonal Fruit,\n" + "Paneer Bhurji";
                    break;
            }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.icon_instigo);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_instigo));
        builder.setContentTitle("Today's Breakfast");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(menu));
        builder.setContentText(menu);
        builder.setAutoCancel(true);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setChannelId(context.getString(R.string.alarm_notification_channel_id));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("mess", true)) {
            assert notificationManager != null;
            notificationManager.notify(100, builder.build());
        }


    }
}
