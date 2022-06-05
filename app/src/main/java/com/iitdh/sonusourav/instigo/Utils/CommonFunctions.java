package com.iitdh.sonusourav.instigo.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iitdh.sonusourav.instigo.Account.LoginActivity;
import com.iitdh.sonusourav.instigo.Complaints.ComplaintsActivity;
import com.iitdh.sonusourav.instigo.Council.CouncilActivity;
import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.Mess.MessActivity;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Resources.ResourceActivity;
import com.iitdh.sonusourav.instigo.Settings.SettingsActivity;
import com.iitdh.sonusourav.instigo.User.ProfileActivity;
import com.iitdh.sonusourav.instigo.User.UpdatePassword;


public class CommonFunctions {

    public static boolean navigationItemSelect(MenuItem item, final Activity activity) {
        int id = item.getItemId();


        switch (id) {

            case R.id.nav_home: {
                activity.startActivity(new Intent().setClass(activity, HomeActivity.class));
                break;

            }

            case R.id.nav_mess: {
                activity.startActivity(new Intent().setClass(activity, MessActivity.class));
                break;

            }

            case R.id.nav_complaint: {
                activity.startActivity(new Intent().setClass(activity, ComplaintsActivity.class));
                break;

            }

            case R.id.nav_council: {

                activity.startActivity(new Intent().setClass(activity, CouncilActivity.class));
                break;

            }

            case R.id.nav_resource: {

                activity.startActivity(new Intent().setClass(activity, ResourceActivity.class));
                break;

            }


            case R.id.nav_profile: {
                activity.startActivity(new Intent().setClass(activity, ProfileActivity.class));
                break;

            }
            case R.id.nav_pass: {
                activity.startActivity(new Intent().setClass(activity, UpdatePassword.class));
                break;

            }
            case R.id.nav_logout: {
                PreferenceManager preferenceManager=new PreferenceManager(activity.getApplicationContext());
                preferenceManager.setIsLoggedIn(false);
                preferenceManager.setLoginCredentials("email","password");

                FirebaseAuth loginAuth=FirebaseAuth.getInstance();
                loginAuth.signOut();

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(activity.getResources().getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(activity, gso);

                googleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity.getApplicationContext(), "User successfully logged out.", Toast.LENGTH_SHORT).show();
                        activity.startActivity(new Intent().setClass(activity, LoginActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity.getApplicationContext(), "Cannot Log Out, Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }

            case R.id.nav_feedback: {
                String url = "https://goo.gl/forms/2FMpLtR0hskOGj5C2";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(activity, Uri.parse(url));
                break;

            }

            case R.id.nav_settings: {
                activity.startActivity(new Intent().setClass(activity, SettingsActivity.class));
                break;

            }
        }

            DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }

    public static void setUser(Activity activity){
        FirebaseApp.initializeApp(activity);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if (firebaseUser!=null){
            NavigationView navigationView = activity.findViewById(R.id.nav_view);
            LinearLayout drawerHeader = (LinearLayout) navigationView.getHeaderView(0);

            TextView emailTextView = drawerHeader.findViewById(R.id.nav_header_email);
            TextView usernameTextView = drawerHeader.findViewById(R.id.nav_header_username);
            ImageView userImage=drawerHeader.findViewById(R.id.nav_header_imageView);

            emailTextView.setText(firebaseUser.getEmail());
            usernameTextView.setText(firebaseUser.getDisplayName());

            Uri photoUri = firebaseUser.getPhotoUrl();
            if(photoUri == null){
                String userName = firebaseUser.getDisplayName();
                char ch;
                if(userName != null) {
                    ch = userName.charAt(0);
                    TextDrawable drawable = TextDrawable.builder()
                            .buildRound(String.valueOf(ch), Color.BLUE);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    Glide.with(activity.getApplicationContext())
                            .load(bitmap)
                            .into(userImage);
                }
            }
            else{
                Glide.with(activity.getApplicationContext())
                        .load(photoUri)
                        .into(userImage);
            }
        }
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 96;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 96;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}


