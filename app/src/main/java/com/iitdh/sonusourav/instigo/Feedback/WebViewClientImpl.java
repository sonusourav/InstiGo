package com.iitdh.sonusourav.instigo.Feedback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iitdh.sonusourav.instigo.R;


public class WebViewClientImpl extends WebViewClient {

    private Activity activity;
    private ProgressDialog feedbackProgressDialog;


    WebViewClientImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if(url.contains("docs.google.com")) return false;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        //do what you want to do

        hideProgressDialog();
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap facIcon) {
        super.onPageStarted(view,url,facIcon);
        //SHOW LOADING IF IT ISNT ALREADY VISIBLE

        showProgressDialog();
    }


    public void showProgressDialog() {

        if (feedbackProgressDialog == null) {
            feedbackProgressDialog = new ProgressDialog(activity,R.style.MyAlertDialogStyle);
            feedbackProgressDialog.setMessage("Taking you the feedback form...");
            feedbackProgressDialog.setIndeterminate(true);
        }

        feedbackProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (feedbackProgressDialog != null && feedbackProgressDialog.isShowing()) {
            feedbackProgressDialog.dismiss();
        }
    }


}