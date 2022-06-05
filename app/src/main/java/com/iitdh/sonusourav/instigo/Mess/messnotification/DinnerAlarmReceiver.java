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
import android.util.Log;

import com.iitdh.sonusourav.instigo.Account.SplashActivity;
import com.iitdh.sonusourav.instigo.R;

import java.util.Calendar;


public class DinnerAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Intent repeatingIntent = new Intent(context, SplashActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 101, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        String menu = null;


            Calendar now = Calendar.getInstance();
            int day = now.get(Calendar.DAY_OF_WEEK);
            switch (day) {
                case Calendar.MONDAY:
                    menu ="Steamed Rice,\n" + "Kashmiri Pulav,\n" + "Plain Roti, Butter Roti,\n" + "Dal Panchratna,\n" + "Cabbage Mutter,\n" + "Paneer Makhni,\n" + "Kulfi Candy";
                    break;
                case Calendar.TUESDAY:
                    menu = "Steamed Rice,\n" + "Schezwan Rice,\n" + "Plain Roti, Butter Roti,\n" + "Dal Lasooni,\n" + "SoyaChilli,\n" + "Ghiya Chana,\n" + "Rasmalai-2 Pcs";
                    break;
                case Calendar.WEDNESDAY:
                    menu = "Steamed Rice,\n" + "Onion Jeera Rice,\n" + "Plain Roti, Butter Roti,\n" + "Dal Fry,\n" + "Aalo Baingan,\n" + "Lauki Kofta,\n" + "Boondi Ladoo-2 Pc";
                    break;
                case Calendar.THURSDAY:
                    menu ="Steamed Rice,\n" + "Dal Khichdi,\n" + "Plain Roti, Butter Roti,\n" + "Kali Maasoor Dal,\n" + "Beetroot Curry,\n" + "Dry Punjabi Kadhi,\n" + "Lauki Halwa";
                    break;
                case Calendar.FRIDAY:
                    menu = "Steamed Rice,\n" + "Corn rice,\n" + "Plain Roti, Butter Roti,\n" + "Dal Kolhapuri,\n" + "Chilli Potato,\n" + "Paneer Lababdar,\n" + "Rava Laddu with Dry fruits";
                    break;
                case Calendar.SATURDAY:
                    menu = "Steamed Rice,\n" + "Peanut Rice,\n" + "Plain Roti, Butter Roti,\n" + "Mix Dal Tadka,\n" + "Cluster Beans Curry,\n" + "Moong Gassi,\n" + "Jalebi";
                    break;
                case Calendar.SUNDAY:
                    menu = "Steamed Rice,\n" + "Veg Pulao,\n" + "Plain Roti, Butter Roti,\n" + "Dal Fry,\n" + "Carrot Beans Dry,\n" + "Shahi Paneer,\n" + "Lavang Latika-2 Pcs";
                    break;
            }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.icon_instigo);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_instigo));
        builder.setContentTitle("Today's Dinner");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(menu));
        builder.setContentText(menu);
        builder.setAutoCancel(true);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setChannelId(context.getString(R.string.alarm_notification_channel_id));
        Log.d("DinnerAlarmReceiver","Reaching");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("mess", true)) {
            Log.d("MessSharedPref","Reaching");
            assert notificationManager != null;
            notificationManager.notify(101, builder.build());
        }

    }
}
