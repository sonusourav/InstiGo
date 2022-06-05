package com.iitdh.sonusourav.instigo.Resources.CS;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iitdh.sonusourav.instigo.Account.LoginActivity;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Resources.DocsAdapter;
import com.iitdh.sonusourav.instigo.Resources.DocsClass;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CSResourceDocs extends AppCompatActivity {

    private ProgressDialog csDocProgressDialog;

    private static final String TAG =CSResourceDocs.class.getSimpleName() ;
    private ArrayList<DocsClass> csDocList;
    private DocsAdapter csDocAdapter;

    private DatabaseReference csDocRef;
    private FirebaseUser csDocUser;
    FirebaseStorage storage;
    StorageReference storageReference;
    private StorageReference docStorageRef;
    private StorageReference docStorageRootRef;
    private String userReference;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 70;
    private TextView emptyDoc;
    private FloatingActionButton csAddDoc;

    private String courseName;
    private String courseNo;

    private String topic;
    private String subTopic;
    private String type;

    private  Dialog dialog;
    private Button addButton;
    private DocsClass newDoc;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_main);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = findViewById(R.id.main_content);

        Bundle bundle=getIntent().getExtras();
        assert bundle != null;
        courseName=bundle.getString("csCourseName");
        courseNo=bundle.getString("csCourseNo");

        csInit();

        initCollapsingToolbar();

        updateDocs();


        try {
            Glide.with(this).load(R.drawable.image_resource).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        csAddDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDoc();
            }
        });

    }

    private void csInit(){

        emptyDoc=findViewById(R.id.doc_empty);
        csAddDoc=findViewById(R.id.doc_add);
        List<DocsClass> courseList = new ArrayList<>();
        DocsAdapter adapter = new DocsAdapter(this, courseList);

        RecyclerView recyclerView = findViewById(R.id.doc_list);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        csAddDoc =findViewById(R.id.doc_add);
        csDocList = new ArrayList<>();

        FirebaseAuth csDocAuth = FirebaseAuth.getInstance();
        FirebaseDatabase csDocInstance = FirebaseDatabase.getInstance();
        DatabaseReference csDocRootRef = csDocInstance.getReference("Resources");
        csDocRef= csDocRootRef.child("CourseDocs").child("CSE").getRef().child(courseName);
        csDocUser = csDocAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child(encodeUserEmail(Objects.requireNonNull(csDocUser.getEmail())));
        docStorageRootRef=storageReference.child("Resources").child("CSE");

        if(csDocUser ==null){
            startActivity(new Intent(CSResourceDocs.this, LoginActivity.class));
            finish();
        }

        csDocAdapter = new DocsAdapter(this, csDocList);
        recyclerView.setAdapter(csDocAdapter);
    }


    private void updateDocs(){


        showProgressDialog();
        csDocRef.limitToLast(20).orderByChild("dateCreated").addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists()){
                    csDocList.clear();

                    emptyDoc.setVisibility(View.GONE);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: reached");
                        DocsClass document = snapshot.getValue(DocsClass.class);

                        if(document!=null){
                            csDocList.add(document);

                            if(emptyDoc.getVisibility()==View.VISIBLE){
                                emptyDoc.setVisibility(View.GONE);
                            }
                        }

                    }
                    Collections.reverse(csDocList);
                    csDocAdapter.notifyDataSetChanged();

                }
                hideProgressDialog();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e(TAG, "Failed to read value.", databaseError.toException());
                hideProgressDialog();
            }
        });


    }




    private void addDoc(){


        dialog = new Dialog(CSResourceDocs.this);
        dialog.setContentView(R.layout.add_doc);
        dialog.setTitle(" Add Document ");
        dialog.setCancelable(true);


        final EditText topicName =dialog.findViewById(R.id.doc_topic);
        final EditText subTopicName=dialog.findViewById(R.id.doc_sub_topic);
        final Spinner docType=dialog.findViewById(R.id.doc_type);
        final Button uploadDoc=dialog.findViewById(R.id.doc_upload);
        addButton=dialog.findViewById(R.id.doc_add_btn);


        uploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type=docType.getSelectedItem().toString();
                Log.d("docType ",type);
                chooseImage(type);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setCancelable(false);


                topic=topicName.getText().toString().trim();
                subTopic=subTopicName.getText().toString().trim();
                type=docType.getSelectedItem().toString();

                if(topic.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill topic name ", Toast.LENGTH_SHORT).show();
                    topicName.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }
                if(subTopic.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill sub-topic name ", Toast.LENGTH_SHORT).show();
                    subTopicName.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }

                showProgressDialog();

                Calendar calendar=Calendar.getInstance();
                final String date = new SimpleDateFormat("dd MMM yy h:mm:ss a", Locale.US).format(calendar.getTime());


                final String username=csDocUser.getDisplayName();

                 newDoc=new DocsClass(courseName,courseNo,date,username,"CSE",topic,subTopic,type);


                userReference= csDocRef.child(topic).push().getKey();
                csDocRef.child(topic).push();

                assert userReference != null;
                csDocRef.child(userReference).setValue(newDoc).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed to add document",Toast.LENGTH_SHORT).show();
                        hideProgressDialog();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        newDoc=new DocsClass(courseName,courseNo,date,username,"CSE",topic,subTopic,type);
                        uploadImage();

                        hideProgressDialog();
                    }
                });
                dialog.dismiss();

            }
        });

        dialog.show();

    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }


    private void chooseImage(String type) {
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setAction(Intent.ACTION_GET_CONTENT);


        if(type.equalsIgnoreCase("Pdf")){
            intent.setType("application/pdf");

        }

        else if(type.equalsIgnoreCase("Image")) {
            intent.setType("image/*");
        }
        else if(type.equalsIgnoreCase("ppt")) {
            intent.setType("application/vnd.ms-powerpoint");
        }
        else if(type.equalsIgnoreCase("Excel")) {
            intent.setType("application/vnd.ms-excel");
        }
        else if(type.equalsIgnoreCase("Zip")){
            intent.setType("application/zip");

        }else{
            intent.setType("*/*");
        }

        startActivityForResult(Intent.createChooser(intent, "Select file"),PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == PICK_IMAGE_REQUEST)  && resultCode == RESULT_OK  && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                if(dialog.isShowing()){
                    addButton.setEnabled(true);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }



    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


                docStorageRef = docStorageRootRef.child(courseName).child(topic);



            docStorageRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(CSResourceDocs.this, "Uploaded", Toast.LENGTH_SHORT).show();

                            docStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d("Pic Url Fetching","success");


                                        csDocRef.child(userReference).child("imageUrl").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Pic Url Uploading","success");

                                                updateDocs();

                                                if(emptyDoc.getVisibility()==View.VISIBLE){
                                                    emptyDoc.setVisibility(View.GONE);
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Pic Url Uploading","failed");
                                                Toast.makeText(getApplicationContext(),"Network error!\nFailed to upload pic.",Toast.LENGTH_SHORT).show();


                                            }
                                        });
                                        }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {

                                    Log.d("Pic Url Fetching","failure");
                                    Toast.makeText(getApplicationContext(),"Network error!\n Failed to upload pic.",Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CSResourceDocs.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                 findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        AppBarLayout appBarLayout =  findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);




        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    collapsingToolbar.setTitle(" ");
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(Html.fromHtml("<font color='#ffffff'>Documents</font>"));
                    Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
                    isShow = true;
                } else if (isShow) {
                    Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#005cae80")));
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }



    protected void onResume() {
        super.onResume();

    }



    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return true;

    }


    public void showProgressDialog() {
        if (isNetworkAvailable()){
            if (csDocProgressDialog == null) {
                csDocProgressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
                csDocProgressDialog.setMessage("Updating courses....");
                csDocProgressDialog.setIndeterminate(true);
                csDocProgressDialog.setCanceledOnTouchOutside(false);
            }

            csDocProgressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isNetworkAvailable()){
                        hideProgressDialog();
                        Snackbar.make(coordinatorLayout,"No Internet Connection",Snackbar.LENGTH_LONG).show();
                    }
                }
            },15000);
        }else {
            Snackbar.make(coordinatorLayout,"No Internet Connection",Snackbar.LENGTH_LONG).show();
        }

    }

    public void hideProgressDialog() {
        if (csDocProgressDialog != null && csDocProgressDialog.isShowing()) {
            csDocProgressDialog.dismiss();
        }
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null && networkInfo.isConnected();
    }



}
