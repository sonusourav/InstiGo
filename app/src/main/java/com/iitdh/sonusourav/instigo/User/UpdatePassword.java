package com.iitdh.sonusourav.instigo.User;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.CommonFunctions;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;

import java.util.Objects;

public class UpdatePassword extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private EditText newPassword;
    private FirebaseAuth updatePassAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference rootRef;
    private EditText oldPassword;
    private PreferenceManager updatePref;

    private ProgressBar updateProgressBar;
    private Button updateButton;
    private FrameLayout updateFrameLayout;
    DatabaseReference userRef;
    private String email;
    private String TAG=UpdatePassword.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        CommonFunctions.setUser(this);

        findViewById(R.id.include_update_pass).setVisibility(View.VISIBLE);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        updatePassInit();
        if(updatePref.isPassUpdated()){
            updateFrameLayout.setVisibility(View.VISIBLE);
            Log.d(TAG+"  IsPasswordUpdated","Updated");
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProgressBar.setVisibility(View.VISIBLE);
                updateButton.setEnabled(false);
                final String newPass =newPassword.getText().toString().trim();


                    if(newPass.isEmpty() ){

                        updateProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"New Password is Empty .",Toast.LENGTH_SHORT).show();
                        updateButton.setEnabled(true);
                    }
                    else if(newPass.length()<7){
                        updateProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Password should not contain less than 6 characters .",Toast.LENGTH_SHORT).show();
                        updateButton.setEnabled(true);

                    }else
                        if(!updatePref.isPassUpdated()){

                            Log.d(TAG+"  FirstPassChange","Reaching");

                            AuthCredential credential = EmailAuthProvider.getCredential(email, newPass);
                            assert updatePassAuth.getCurrentUser()!=null;
                            updatePassAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "linkWithCredential:success");

                                        userRef=rootRef.child(encodeUserEmail(Objects.requireNonNull(firebaseUser.getEmail()))).child("pass");
                                        userRef.setValue(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                updatePref.setIsPassUpdated(true);
                                                updateProgressBar.setVisibility(View.GONE);
                                                Log.d(TAG + "UpdatePassword", "First Time Password updated");
                                                startActivity(new Intent(UpdatePassword.this,HomeActivity.class));


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Log.d(TAG+"OnFailure", e.getLocalizedMessage());
                                                Toast.makeText(getApplicationContext(),"Failed to update password.\nPlease try again.",Toast.LENGTH_SHORT).show();
                                                updateProgressBar.setVisibility(View.GONE);
                                                updateButton.setEnabled(true);

                                            }
                                        });

                                    } else {
                                        Log.w(TAG, "linkWithCredential:failure", task.getException());

                                        Toast.makeText(getApplicationContext(),"Failed to update password.\nPlease try again.",Toast.LENGTH_SHORT).show();
                                        updateProgressBar.setVisibility(View.GONE);
                                        updateButton.setEnabled(true);

                                    }

                                }
                            });

                    }
                else {

                    String oldPass =oldPassword.getText().toString().trim();

                    if(oldPass.isEmpty() ){

                        updateProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Old Password is Empty .",Toast.LENGTH_SHORT).show();
                        updateButton.setEnabled(true);
                    }
                    assert email != null;
                    AuthCredential credential = EmailAuthProvider.getCredential(email,oldPass);

                    firebaseUser.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseUser.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    updatePref.setLoginCredentials(firebaseUser.getEmail(),newPass);


                                                    userRef=rootRef.child(encodeUserEmail(email)).getRef();
                                                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()){
                                                                dataSnapshot.getRef().child("pass").setValue(newPass);
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            Toast.makeText(getApplicationContext(),"Database update failed",Toast.LENGTH_SHORT).show();
                                                            updateButton.setEnabled(true);
                                                            finish();
                                                        }
                                                    });


                                                    Toast.makeText(getApplicationContext(),"Password successfully updated",Toast.LENGTH_SHORT).show();
                                                    Log.d("Update Password", "Password updated");
                                                    startActivity(new Intent(UpdatePassword.this,HomeActivity.class));
                                                } else {
                                                    Toast.makeText(getApplicationContext(),"Password update failed. Try Again!",Toast.LENGTH_SHORT).show();
                                                    updateButton.setEnabled(true);
                                                    Log.d("Update Password", "Error password not updated");
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Error : Wrong old password.",Toast.LENGTH_SHORT).show();
                                        updateButton.setEnabled(true);
                                        Log.d("Update Password", "Error auth failed");
                                    }

                                    updateProgressBar.setVisibility(View.GONE);}

                            });


                }


            }
        });

    }



    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }



    @Override
    protected void onResume(){
        super.onResume();
        updateButton.setEnabled(true);
    }

    private void updatePassInit(){
        updateButton = findViewById(R.id.btnUpdatePassword);
        newPassword = findViewById(R.id.etNewPassword);
        oldPassword = findViewById(R.id.etOldPassword);
        updatePassAuth=FirebaseAuth.getInstance();
        firebaseUser =updatePassAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference().child("Users");
        assert firebaseUser != null;
        email = firebaseUser.getEmail();
        updatePref = new PreferenceManager(this);
        updateProgressBar=findViewById(R.id.update_progress_bar);
        updateFrameLayout=findViewById(R.id.update_frame1);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return CommonFunctions.navigationItemSelect(item, this);
    }
}
