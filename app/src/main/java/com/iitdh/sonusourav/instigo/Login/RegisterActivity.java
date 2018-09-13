package com.iitdh.sonusourav.instigo.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iitdh.sonusourav.instigo.R;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    private EditText registerEmail;
    private EditText registerUsername;
    private EditText registerPass;
    private Button registerButton;
    private TextView loginHere;
    private ProgressDialog registerProgressDialog;

    private String signUpEmail;
    private String signUpUsername;
    private String signUpPass;
    private String errorCode;

    private FirebaseAuth registerAuth;
    private FirebaseDatabase registerFirebaseInstance;
    private DatabaseReference registerRootReference;
    private DatabaseReference registerUserRef;

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

                if(!validateUserInput(signUpEmail, signUpUsername, signUpPass)){
                    return;
                }


                showProgressDialog();
                registerAuth.createUserWithEmailAndPassword(signUpEmail, signUpPass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            sendEmailVerification();

                        }
                        if (!task.isSuccessful()) {

                            hideProgressDialog();
                            registerButton.setFocusable(true);
                            registerButton.setClickable(true);
                            loginHere.setFocusable(true);
                            loginHere.setClickable(true);
                            errorCode = ((FirebaseAuthException) Objects.requireNonNull(task.getException())).getErrorCode();
                             Log.d("error code",errorCode);

                            switch (errorCode) {

                                case "ERROR_INVALID_EMAIL":
                                    Toast.makeText(RegisterActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                    registerEmail.setError("The email address is badly formatted.");
                                    registerEmail.requestFocus();
                                    break;

                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    Toast.makeText(RegisterActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                    registerEmail.setError("The email address is already in use by another account.");
                                    registerEmail.requestFocus();
                                    break;

                                case "ERROR_USER_DISABLED":
                                    Toast.makeText(RegisterActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                    break;


                                case "ERROR_OPERATION_NOT_ALLOWED":
                                    Toast.makeText(RegisterActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_WEAK_PASSWORD":
                                    Toast.makeText(RegisterActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                    registerPass.setError("The password is invalid it must 6 characters at least");
                                    registerPass.requestFocus();
                                    break;

                            }
                        }
                    }



                });

            }

        });

        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

            }
        });

    }


    public boolean validateUserInput(String signUpEmail,String signUpUsername,String signUpPass){

        if (TextUtils.isEmpty(signUpEmail) || signUpEmail.equals("") || signUpEmail.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter your  email address!", Toast.LENGTH_SHORT).show();
            registerEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(signUpUsername) || signUpUsername.equals("") || signUpUsername.length() == 0) {
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
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            registerPass.requestFocus();
            return false;
        }


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
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public void initRegister(){

        registerEmail =findViewById(R.id.register_email);
        registerUsername =findViewById(R.id.register_username);
        registerPass =findViewById(R.id.register_pass);
        registerButton=findViewById(R.id.button_register);
        loginHere=findViewById(R.id.register_bottom);
        registerEmail.setText("");
        registerUsername.setText("");
        registerPass.setText("");
        registerAuth = FirebaseAuth.getInstance();
        registerFirebaseInstance = FirebaseDatabase.getInstance();
        registerRootReference = registerFirebaseInstance.getReference("UserId");
        registerUserRef = registerFirebaseInstance.getReference("Users");


            }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    //send email Verification
    private  void  sendEmailVerification(){
        FirebaseUser firebaseUser = registerAuth.getCurrentUser();
        if (firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        hideProgressDialog();
                        Toast.makeText(RegisterActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        sendUserData();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        hideProgressDialog();
                        Toast.makeText(RegisterActivity.this, "Verification mail failed to sent.\nCheck your network connection and try again!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    //send Userdata to database
    private void sendUserData(){


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rootReference=firebaseDatabase.getReference();
        DatabaseReference myRef = rootReference.child("Users").child(encodeUserEmail(signUpEmail));

        UserClass userProfile =new UserClass(signUpEmail,signUpUsername,signUpPass);
        myRef.setValue(userProfile);
        Toast.makeText(getApplicationContext(),"sending userdata",Toast.LENGTH_SHORT).show();

    }

    public void showProgressDialog() {

        if (registerProgressDialog == null) {
            registerProgressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
            registerProgressDialog.setMessage("Creating Account ....");
            registerProgressDialog.setIndeterminate(true);
        }

        registerProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (registerProgressDialog != null && registerProgressDialog.isShowing()) {
            registerProgressDialog.dismiss();
        }
    }

}
