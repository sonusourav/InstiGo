package com.iitdh.sonusourav.instigo.Utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

@SuppressLint("Registered") public class AppSingleton extends Application {

  public static final String TAG = AppSingleton.class.getSimpleName();

  private RequestQueue mRequestQueue;
  private ImageLoader mImageLoader;

  private static AppSingleton mInstance;

  @Override
  public void onCreate() {
    super.onCreate();
    mInstance = this;
  }

  public static synchronized AppSingleton getInstance() {
    return mInstance;
  }

  public RequestQueue getRequestQueue() {
    if (mRequestQueue == null) {
      mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    return mRequestQueue;
  }

  public ImageLoader getImageLoader() {
    getRequestQueue();
    if (mImageLoader == null) {
      mImageLoader = new ImageLoader(this.mRequestQueue,
          new LruBitmapCache());
    }
    return this.mImageLoader;
  }

  public  void addToRequestQueue(Request req, String tag) {
    // set the default tag if tag is empty
    req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
    getRequestQueue().add(req);
  }

  public  void addToRequestQueue(Request req) {
    req.setTag(TAG);
    getRequestQueue().add(req);
  }

  public void cancelPendingRequests(Object tag) {
    if (mRequestQueue != null) {
      mRequestQueue.cancelAll(tag);
    }
  }
}