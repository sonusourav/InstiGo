package com.iitdh.sonusourav.instigo.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonIOException;
import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.AppSingleton;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;
import com.iitdh.sonusourav.instigo.Utils.VolleyErrorInstances;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

  private static final int RC_SIGN_IN = 234;
  private static final String TAG = LoginActivity.class.getSimpleName();
  private Button loginButton;
  private EditText loginEmail, loginPass;
  private SignInButton googleSignInButton;
  private CheckBox rememberMe;
  private TextView forgotPass, signUp;
  private ImageView loginLogo;
  private PreferenceManager loginPref;
  private String signInEmail, signInPass, loginUrl;
  private ProgressDialog mProgressDialog;
  private GoogleSignInClient mGoogleSignInClient;
  private long back_pressed;
  private String fcmToken;
  private ConstraintLayout constraintLayout;

  protected void onCreate(Bundle bundle) {

    super.onCreate(bundle);
    setContentView(R.layout.activity_login);

    if (bundle != null) {
      onRestoreInstanceState(bundle);
    }

    initLogin();

    final Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        Animation fadeIn = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fadeout);
        loginLogo.setVisibility(View.VISIBLE);
        loginLogo.startAnimation(fadeIn);
      }
    }, 1000);

    //Login button onClick
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        signInEmail = loginEmail.getText().toString().trim();
        signInPass = loginPass.getText().toString().trim();

        if ((signInEmail == null) || signInEmail.equals("") || TextUtils.isEmpty(signInEmail) || (
            signInEmail.length() == 0)) {
          Toast.makeText(getApplicationContext(), "Enter your username", Toast.LENGTH_SHORT).show();
          return;
        }

        if (signInPass.equals("")
            || TextUtils.isEmpty(signInPass)
            || signInPass.length() == 0) {
          Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT).show();
          return;
        }
        if (!validateEmail(signInEmail)) {
          Toast.makeText(getApplicationContext(), "Invalid Email address", Toast.LENGTH_SHORT)
              .show();
          return;
        }

        showProgressDialog();
        loginUser(signInEmail, signInPass);
      }
    });

    //Google SignIn starts
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.web_client_id))
        .requestEmail()
        .build();

    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    googleSignInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        signIn();
      }
    });

    //SignUp button onClick
    signUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
      }
    });

    //ForgotPass button onClick
    forgotPass.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
      }
    });
  }

  private void signIn() {
    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
    Log.d(TAG, "signIn");
    showProgressDialog();
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {
      if (resultCode == RESULT_OK) {
        Log.d(TAG, "reaching onActivityResult");
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        handleSignInResult(task);
      } else {
        Snackbar snackbar = Snackbar
            .make(constraintLayout, "Login failed", Snackbar.LENGTH_SHORT);

        snackbar.show();
        //Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
        hideProgressDialog();
      }
    }
  }

  private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
    try {
      GoogleSignInAccount account = completedTask.getResult(ApiException.class);
      if (account != null) {
        Log.d(TAG, "account");
        if (account.getIdToken() != null) {
          Log.d(TAG, account.getIdToken());
          verifyToken(account.getIdToken(), account);
        } else {
          Toast.makeText(getApplicationContext(), "Login failure", Toast.LENGTH_SHORT).show();
        }
      }
    } catch (ApiException e) {
      Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
      e.printStackTrace();
    }
  }

  private void verifyToken(String idToken, final GoogleSignInAccount account) {

    Log.d(TAG, "verifyToken");
    String googleAuthUrl = Constants.baseUrl + "tokensignin/" + idToken;
    Log.d(TAG, googleAuthUrl);

    JsonObjectRequest tokenReq = new JsonObjectRequest(Request.Method.GET, googleAuthUrl, null,
        new Response.Listener<JSONObject>() {

          @Override
          public void onResponse(JSONObject response) {
            try {
              if (response.getString("message").equals("success")) {
                Log.d(TAG, response.toString());
                String userId = response.getString("userId");
                loginPref.setIsLoggedIn(true, userId);
                loginPref.setUserLevel(response.getInt("level"));
                Log.d(TAG, response.getInt("level") + "");
                loginPref.setIsFirstGoogleLogin(false);
                loginPref.setPrefEmail(account.getEmail());
                loginPref.setAuthType("googleAuth");
                loginPref.setPrefName(account.getDisplayName());
                Log.d(TAG, loginPref.getAuthType());
                Log.d(TAG, loginPref.isFirstGoogleLogin() + "");
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
              }
            } catch (JSONException | JsonIOException e) {
              e.printStackTrace();
            }
          }
        },
        new Response.ErrorListener() {

          @Override
          public void onErrorResponse(VolleyError volleyError) {
            new VolleyErrorInstances().getErrorType(getApplicationContext(), volleyError);
            Log.d(TAG, "VolleyError: " + volleyError.toString());
          }
        });

    tokenReq.setRetryPolicy(new DefaultRetryPolicy(
        6000,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
    );

    AppSingleton.getInstance().addToRequestQueue(tokenReq);
  }

  private void loginUser(final String email, final String password) {

    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("email", email);
      jsonObject.put("password", password);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, loginUrl, jsonObject,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            try {
              if (response.getString("message").equals("success")) {
                String userId = response.getString("userId");
                long passLastUpdated = response.getLong("passLastUpdated");
                int level = response.getInt("level");
                loginPref.setUserLevel(level);
                if (rememberMe.isChecked()) {
                  loginPref.setIsLoggedIn(true, userId);
                  loginPref.setPassLastUpdated(passLastUpdated);
                  loginPref.setLoginCredentials(signInEmail, signInPass, "emailAuth");
                } else {
                  loginPref.setUserId(userId);
                  loginPref.setPassLastUpdated(passLastUpdated);
                }
                loginPref.setIsPassUpdated(true);
                Log.d(TAG, response.toString());
                subscribeToTopic();
                hideProgressDialog();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
              } else if (response.get("message").toString().substring(0, 7).equals("failure")) {
                String errorMsg = response.getString("message").substring(8);
                Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                hideProgressDialog();
                Log.d(TAG, "Else_If_Error: " + response.toString());
              } else {
                hideProgressDialog();
                Log.d(TAG, "Bad UploadObject");
              }
            } catch (JSONException e) {
              hideProgressDialog();
              e.printStackTrace();
              Log.d(TAG, "catchError: " + e.getMessage());
            }
          }
        },
        new Response.ErrorListener() {

          @Override
          public void onErrorResponse(VolleyError volleyError) {
            new VolleyErrorInstances().getErrorType(getApplicationContext(), volleyError);
            hideProgressDialog();
            Log.d(TAG, "VolleyError: " + volleyError.toString());
          }
        }) {
      @Override
      public Map<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
      }
    };

    AppSingleton.getInstance().addToRequestQueue(jsonObjReq);
  }

  private void subscribeToTopic() {
    FirebaseMessaging.getInstance().subscribeToTopic("newUsers")
        .addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
            String msg = "Successfully subscribed";
            if (!task.isSuccessful()) {
              msg = "Failed to subscribe";
            }
            Log.d(TAG, msg);
            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
          }
        });
  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  public void initLogin() {

    loginLogo = findViewById(R.id.login_logo);
    loginEmail = findViewById(R.id.ed_user_name);
    loginPass = findViewById(R.id.ed_password);
    rememberMe = findViewById(R.id.remember_me_chk_box);
    loginButton = findViewById(R.id.button_login);
    googleSignInButton = findViewById(R.id.button_google_login);
    forgotPass = findViewById(R.id.forgot_pass);
    signUp = findViewById(R.id.login_tv3);
    loginEmail.setText("");
    loginPass.setText("");
    rememberMe.setChecked(false);
    constraintLayout = findViewById(R.id.constraint_layout);
    loginPref = new PreferenceManager(this);
    loginUrl = Constants.baseUrl + "users/signin";
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

  public void onBackPressed() {
    if (back_pressed + 2000 > System.currentTimeMillis()) {
      moveTaskToBack(true);
    } else {
      Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
    }
    back_pressed = System.currentTimeMillis();
  }

  public void showProgressDialog() {

    if (mProgressDialog == null) {
      mProgressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
      mProgressDialog.setMessage("Logging In ....");
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
