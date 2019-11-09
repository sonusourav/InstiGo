package com.iitdh.sonusourav.instigo.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.AppSingleton;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.VolleyErrorInstances;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {

    private EditText passwordEmail;
  private String forgotPassUrl;
  private static String TAG = ForgotPassword.class.getSimpleName();
  private ProgressDialog mProgressDialog;

  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.activity_forgot_password);

    if (bundle != null) {
      onRestoreInstanceState(bundle);
    }

    final Button resetPassword = findViewById(R.id.button_reset);
    passwordEmail = findViewById(R.id.forgot_email);
    TextView backToLogin = findViewById(R.id.back_to_login);
    forgotPassUrl = Constants.baseUrl + "users/forgotp";

    resetPassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String userEmail = passwordEmail.getText().toString().trim();
        showProgressDialog();
        if (userEmail.isEmpty()) {
          hideProgressDialog();
          Toast.makeText(getApplicationContext(), "Please Enter an email id", Toast.LENGTH_SHORT)
              .show();
        } else {

          JSONObject jsonObject = new JSONObject();
          try {
            jsonObject.put("email", userEmail);
          } catch (JSONException e) {
            e.printStackTrace();
          }
          JsonObjectRequest tokenReq =
              new JsonObjectRequest(Request.Method.POST, forgotPassUrl, jsonObject,
                  new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                      try {
                        if (response.getString("message").equals("success")) {
                          Log.d(TAG, response.toString());
                          Toast.makeText(ForgotPassword.this,
                              "Check your email to reset password.", Toast.LENGTH_SHORT)
                              .show();
                          hideProgressDialog();
                          startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                        } else if (response.get("message")
                            .toString()
                            .substring(0, 7)
                            .equals("failure")) {
                          String errorMsg = response.getString("message").substring(8);
                          Toast.makeText(ForgotPassword.this, errorMsg, Toast.LENGTH_SHORT).show();
                          Log.d(TAG, "Else_If_Error: " + response.toString());
                          hideProgressDialog();
                        } else {
                          Log.d(TAG, "Bad UploadObject");
                          hideProgressDialog();
                        }
                      } catch (JSONException e) {
                        hideProgressDialog();
                        e.printStackTrace();
                      }
                    }
                  },
                  new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                      new VolleyErrorInstances().getErrorType(getApplicationContext(), volleyError);
                      Log.d(TAG, "VolleyError: " + volleyError.toString());
                      hideProgressDialog();
                    }
                  });

          tokenReq.setRetryPolicy(new DefaultRetryPolicy(
              6000,
              DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
              DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
          );

          AppSingleton.getInstance().addToRequestQueue(tokenReq);
        }
      }
    });

    backToLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
      }
    });
  }

  public void showProgressDialog() {

    if (mProgressDialog == null) {
      mProgressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
      mProgressDialog.setMessage("Please wait....");
      mProgressDialog.setIndeterminate(true);
      mProgressDialog.setCanceledOnTouchOutside(false);
    }

    mProgressDialog.show();
  }

  public void hideProgressDialog() {
    if (mProgressDialog != null && mProgressDialog.isShowing()) {
      mProgressDialog.dismiss();
    }
  }
}
