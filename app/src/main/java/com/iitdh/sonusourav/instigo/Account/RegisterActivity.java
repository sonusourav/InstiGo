package com.iitdh.sonusourav.instigo.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.AppSingleton;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

  private EditText registerEmail;
  private EditText registerUsername;
  private EditText registerPass;
  private Button registerButton;
  private TextView loginHere;
  private ProgressDialog registerProgressDialog;
  private PreferenceManager regisPref;
  private String signUpEmail;
  private String signUpUsername;
  private String signUpPass;
  private String registerUrl;
  private String TAG = RegisterActivity.class.getCanonicalName();

  protected void onCreate(Bundle bundle) {

    super.onCreate(bundle);
    setContentView(R.layout.activity_register);

    if (bundle != null) {
      onRestoreInstanceState(bundle);
    }

    initRegister();
    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        registerButton.setFocusable(false);
        registerButton.setClickable(false);
        loginHere.setFocusable(false);
        loginHere.setClickable(false);

        signUpEmail = registerEmail.getText().toString().trim();
        signUpPass = registerPass.getText().toString().trim();
        signUpUsername = registerUsername.getText().toString().trim();

        if (!validateUserInput(signUpEmail, signUpUsername, signUpPass)) {

          registerButton.setFocusable(true);
          registerButton.setClickable(true);
          return;
        }

        showProgressDialog();

        registerUser(signUpEmail, signUpUsername, signUpPass);
      }
    });

    loginHere.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
      }
    });
  }

  public boolean validateUserInput(String signUpEmail, String signUpUsername, String signUpPass) {

    if (TextUtils.isEmpty(signUpEmail) || signUpEmail.equals("") || signUpEmail.length() == 0) {
      Toast.makeText(getApplicationContext(), "Enter your  email address!", Toast.LENGTH_SHORT)
          .show();
      registerEmail.requestFocus();

      return false;
    }

    if (TextUtils.isEmpty(signUpUsername)
        || signUpUsername.equals("")
        || signUpUsername.length() == 0) {
      Toast.makeText(getApplicationContext(), "Enter your Username!", Toast.LENGTH_SHORT).show();
      registerUsername.requestFocus();

      return false;
    }

    if (signUpPass.equals("") || TextUtils.isEmpty(signUpPass) || signUpPass.length() == 0) {
      Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT).show();
      registerPass.requestFocus();

      return false;
    }

    if (!validateEmail(signUpEmail)) {
      Toast.makeText(getApplicationContext(), "Invalid Email address", Toast.LENGTH_SHORT).show();
      registerEmail.requestFocus();

      return false;
    }

    if (signUpPass.length() < 6) {
      Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!",
          Toast.LENGTH_SHORT).show();
      registerPass.requestFocus();
      loginHere.setFocusable(true);
      loginHere.setClickable(true);
      return false;
    }
    /*

    String[] split = signUpEmail.split("@");
    String domain = split[1]; //This Will Give You The Domain After '@'
    if (!domain.equalsIgnoreCase("iitdh.ac.in")) {

      Toast.makeText(RegisterActivity.this, "Register only with IITDh account", Toast.LENGTH_SHORT)
          .show();
      loginHere.setFocusable(true);
      loginHere.setClickable(true);
      return false;
    }
    */

    return true;
  }

  @Override
  protected void onResume() {
    super.onResume();
    registerButton.setFocusable(true);
    registerButton.setClickable(true);
    loginHere.setFocusable(true);
    loginHere.setClickable(true);
  }

  //validateEmailMethod
  public boolean validateEmail(String email) {

    Pattern pattern;
    Matcher matcher;
    String EMAIL_PATTERN =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    pattern = Pattern.compile(EMAIL_PATTERN);
    matcher = pattern.matcher(email);
    return matcher.matches();
  }

  public void initRegister() {

    registerEmail = findViewById(R.id.register_email);
    registerUsername = findViewById(R.id.register_username);
    registerPass = findViewById(R.id.register_pass);
    registerButton = findViewById(R.id.button_register);
    loginHere = findViewById(R.id.register_bottom);
    registerEmail.setText("");
    registerUsername.setText("");
    registerPass.setText("");
    regisPref = new PreferenceManager(this);
    registerUrl = Constants.baseUrl + "users/signup";
  }

  public void showProgressDialog() {

    if (registerProgressDialog == null) {
      registerProgressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
      registerProgressDialog.setMessage("Creating Account ....");
      registerProgressDialog.setIndeterminate(true);
      registerProgressDialog.setCanceledOnTouchOutside(false);
    }

    registerProgressDialog.show();
  }

  public void hideProgressDialog() {
    if (registerProgressDialog != null && registerProgressDialog.isShowing()) {
      registerProgressDialog.dismiss();
    }
  }

  private void registerUser(final String email, final String name, String password) {

    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("email", email);
      jsonObject.put("password", password);
      jsonObject.put("name", name);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    JsonObjectRequest jsonObjReq =
        new JsonObjectRequest(Request.Method.POST, registerUrl, jsonObject,
            new Response.Listener<JSONObject>() {

              @Override
              public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                  Log.d(TAG, response.toString());
                  if (response.getString("message").equals("success")) {
                    hideProgressDialog();
                    regisPref.setIsPassUpdated(true);
                    regisPref.setPrefName(name);
                    regisPref.setPrefEmail(email);

                    Toast emailToast = Toast.makeText(RegisterActivity.this,
                        "Registered successfully!\nCheck your email", Toast.LENGTH_SHORT);
                    TextView v = emailToast.getView().findViewById(android.R.id.message);
                    if (v != null) v.setGravity(Gravity.CENTER);
                    emailToast.show();

                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                  } else if (response.getString("message").substring(0, 7).equals("failure")) {
                    String errorMsg = response.getString("message").substring(8);
                    hideProgressDialog();
                    registerButton.setFocusable(true);
                    registerButton.setClickable(true);
                    loginHere.setFocusable(true);
                    loginHere.setClickable(true);
                    Toast.makeText(RegisterActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, errorMsg);
                  } else {
                    Log.d(TAG, "Bad UploadObject");
                  }
                } catch (JSONException e) {
                  hideProgressDialog();
                  registerButton.setFocusable(true);
                  registerButton.setClickable(true);
                  loginHere.setFocusable(true);
                  loginHere.setClickable(true);
                  Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT)
                      .show();
                  Log.d("TAG", e.toString());
                  e.printStackTrace();
                }
              }
            },
            new Response.ErrorListener() {

              @Override
              public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                registerButton.setFocusable(true);
                registerButton.setClickable(true);
                loginHere.setFocusable(true);
                loginHere.setClickable(true);
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Error:" + error.toString(),
                    Toast.LENGTH_SHORT).show();
              }
            }) {
          @Override
          public Map<String, String> getHeaders() {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            return headers;
          }
        };
    jsonObjReq.setRetryPolicy(
        new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    AppSingleton.getInstance().addToRequestQueue(jsonObjReq);
  }
}
