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


public class LunchAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent repeatingIntent = new Intent(context, SplashActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 102, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        String menu = null;

            Calendar now = Calendar.getInstance();
            int day = now.get(Calendar.DAY_OF_WEEK);
            switch (day) {
                case Calendar.MONDAY:
                    menu = "Steamed Rice,\n" + "Onion Rice,\n" + "Dal Masala(Red Masoor,\n" + "Tur, Green Gram),\n" + "Plain roti, Butter Roti,\n" + "Mutter Paneer,\n" + "Aalo Gobhi Dry,\n" + "Lemonade";
                    break;
                case Calendar.TUESDAY:
                    menu = "Steamed Rice,\n" + "Schezwan Rice,\n" + "Amti Dal(Dry Fruits, Tuar),\n" + "Plain Roti, Butter Roti,\n" + "Gobhi Mutter Gravy,\n" + "Veg Keema,\n" + "ButterMilk";
                    break;
                case Calendar.WEDNESDAY:
                    menu = "Steamed Rice,\n" + "Tomato Rice,\n" + "Chana Dal,\n" + "Plain Roti, Butter Roti,\n" + "Aalo Mutter Curry,\n" + "Lauki Ki Sabzi,\n" + "Custard,\n" + "Rasna";
                    break;
                case Calendar.THURSDAY:
                    menu = "Steamed Rice,\n" + "Vangi Bhaat,\n" + "Dal Tadka,\n" + "Plain Roti, Butter Roti,\n" + "Paneer Do Pyaza,\n" + "Aalo Jeera Lasooni,\n" + "Lass";
                    break;
                case Calendar.FRIDAY:
                    menu = "Steamed Rice,\n" + "Jeera Rice,\n" + "Masoor Dal ,\n" + "Plain Roti, Butter Roti,\n" + "Rajma Curry,\n" + "Methi Aalo Dry,\n" + "Custard";
                    break;
                case Calendar.SATURDAY:
                    menu = "Steamed Rice,\n" + "Ajwain Rice,\n" + "Moong Dal,\n" + "Plain Roti, Butter Roti,\n" + "Chana Masala Curry,\n" + "Lauki Dahiwala,\n" + "Curd";
                    break;
                case Calendar.SUNDAY:
                    menu = "Steamed Rice,\n" + "Ghee Rice,\n" + "Moong Kolhapuri,\n" + "Plain Roti, Butter Roti,\n" + "Sev Tomato,\n" + "Gobhi Achari+Peas";
                    break;
            }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.icon_instigo);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_instigo));
        builder.setContentTitle("Today's Lunch");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(menu));
        builder.setContentText(menu);
        builder.setAutoCancel(true);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setChannelId(context.getString(R.string.alarm_notification_channel_id));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("mess", true)) {
            assert notificationManager != null;
            notificationManager.notify(102, builder.build());
        }


    }
}
