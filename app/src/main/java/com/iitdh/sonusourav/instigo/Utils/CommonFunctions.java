package com.iitdh.sonusourav.instigo.Utils;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iitdh.sonusourav.instigo.Council.CouncilActivity;
import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.Login.LoginActivity;
import com.iitdh.sonusourav.instigo.Maintenance.MaintenanceActivity;
import com.iitdh.sonusourav.instigo.Mess.MessActivity;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.TestActivity;


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
            case R.id.nav_council: {

                activity.startActivity(new Intent().setClass(activity, com.iitdh.sonusourav.instigo.CouncilActivity.class));
                break;

            }
            case R.id.nav_notification: {
                Toast.makeText(activity.getApplicationContext(), "Welcome to Notification", Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent().setClass(activity, TestActivity.class));
                break;

            }
            case R.id.nav_complaint: {
                activity.startActivity(new Intent().setClass(activity, MaintenanceActivity.class));
                break;

            }
            case R.id.nav_logout: {
                PreferenceManager preferenceManager=new PreferenceManager(activity.getApplicationContext());
                preferenceManager.setIsLoggedIn(false);
                preferenceManager.setLoginCredentials("email","password");
                FirebaseAuth loginAuth=FirebaseAuth.getInstance();
                loginAuth.signOut();

                Toast.makeText(activity.getApplicationContext(), "User successfully logged out.", Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent().setClass(activity, LoginActivity.class));
                break;

            }
        }

            DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }

    public static void setUser(Activity activity){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if (firebaseUser!=null){
            NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            LinearLayout drawerHeader = (LinearLayout) navigationView.getHeaderView(0);
            TextView emailTextView = (TextView) drawerHeader.findViewById(R.id.nav_header_email);
            TextView usernameTextView = (TextView) drawerHeader.findViewById(R.id.nav_header_username);
            ImageView userImage=drawerHeader.findViewById(R.id.nav_header_imageView);

            emailTextView.setText(firebaseUser.getEmail());
            usernameTextView.setText(firebaseUser.getDisplayName());
            Glide.with(activity.getApplicationContext())
                    .load(firebaseUser.getPhotoUrl())
                    .into(userImage);

        }
    }


    }


