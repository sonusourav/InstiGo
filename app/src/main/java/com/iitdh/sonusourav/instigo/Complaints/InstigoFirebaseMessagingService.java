package com.iitdh.sonusourav.instigo.Complaints;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InstigoFirebaseMessagingService extends FirebaseMessagingService {

  private static final String TAG = "FirebaseMsgService";


  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {

    Log.d(TAG, "From: " + remoteMessage.getFrom());

    // Check if message contains a data payload.
    if (remoteMessage.getData().size() > 0) {
      Log.d(TAG, "Message data payload: " + remoteMessage.getData());

    }

    // Check if message contains a notification payload.
    if (remoteMessage.getNotification() != null) {
      Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

      String title = remoteMessage.getNotification().getTitle();
      String body = remoteMessage.getNotification().getBody();

      sendNotification(title,body);
    }

  }


  @Override
  public void onNewToken(String token) {
    Log.d(TAG, "Refreshed token: " + token);
    new PreferenceManager(getApplicationContext()).setFcmToken(token);
  }

  private void sendRegistrationToServer(final String token) {

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build();
   ComplaintsInterface complaintsInterface = new Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ComplaintsInterface.class);

    Call<ResponseBody> postFCMToken = complaintsInterface.sendFCMToken(token);
    postFCMToken.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(@NonNull Call<ResponseBody> call, @NonNull
          Response<ResponseBody> response) {

        Log.d(TAG,new PreferenceManager(getApplicationContext()).getUserId());
        if(response.code()==200){
          Toast.makeText(getApplicationContext(),"FCM Token sent",Toast.LENGTH_SHORT).show();
        }else {
          Toast.makeText(getApplicationContext(),"Failed to send fcm token",Toast.LENGTH_SHORT).show();
          Log.d(TAG,response.toString());
        }
      }

      @Override
      public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
        Log.d(TAG,t.toString());
      }
    });


  }


  private void sendNotification(String title,String messageBody) {
    Intent intent = new Intent(this, HomeActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
        PendingIntent.FLAG_ONE_SHOT);

    String channelId = Constants.CHANNEL_ID;
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder =
        new NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo_instigo)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent);

    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    // Since android Oreo notification channel is needed.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel(channelId,
          "Channel human readable title",
          NotificationManager.IMPORTANCE_DEFAULT);
      notificationManager.createNotificationChannel(channel);
    }

    notificationManager.notify(0 , notificationBuilder.build());
  }
}