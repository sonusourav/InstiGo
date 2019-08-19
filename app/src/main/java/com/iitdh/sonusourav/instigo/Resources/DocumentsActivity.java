package com.iitdh.sonusourav.instigo.Resources;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.w3c.dom.Document;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Multipart;

public class DocumentsActivity extends AppCompatActivity {

    private ProgressDialog docsProgDialog;
    private static final String TAG = DocumentsActivity.class.getSimpleName() ;
    private ArrayList<DocsClass> docList;
    private DocsAdapter docsAdapter;
    private String courseCode;
    private TextView emptyCourses;
    private FloatingActionButton addDocuments;
    private ResourceInterface resourceInterface;
    private  Uri selectedImage;
    private PreferenceManager docPref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_listview);

        courseCode=getIntent().getStringExtra("CourseCode");
        showProgressDialog();

        csInit();
        if (courseCode != null) {
            Log.d(TAG,courseCode);
            emptyCourses.setVisibility(View.GONE);
            getDocuments(courseCode);

        }

        addDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking the permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(DocumentsActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                    finish();
                    startActivity(intent);
                    return;
                }

                addCourse();
            }
        });

    }


    private void addCourse(){

        final Dialog dialog = new Dialog(DocumentsActivity.this);
        dialog.setContentView(R.layout.add_doc);
        dialog.setTitle(" Add new Document ");
        dialog.setCancelable(true);


        final EditText docTitle =dialog.findViewById(R.id.doc_title);
        final EditText docDesc=dialog.findViewById(R.id.doc_desc);
        final EditText docProf=dialog.findViewById(R.id.doc_prof);
        final Button upload=dialog.findViewById(R.id.doc_upload);
        Button addButton=dialog.findViewById(R.id.doc_add_btn);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setCancelable(false);

                final String title=docTitle.getText().toString().trim();
                final String desc=docDesc.getText().toString().trim();;
                String prof=docProf.getText().toString().trim();;


                if(title.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill Title ", Toast.LENGTH_SHORT).show();
                    docTitle.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }
                if(desc.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill description ", Toast.LENGTH_SHORT).show();
                    docDesc.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }

                if(prof.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill Professor name", Toast.LENGTH_SHORT).show();
                    docDesc.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }


                if(selectedImage!=null){
                    showProgressDialog();

                    dialog.dismiss();

                    DocsClass newDoc=new DocsClass(title,desc,prof);
                    uploadFile(selectedImage,newDoc);
                }




            }
        });

        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void uploadFile(Uri fileUri,DocsClass newDoc) {

        //creating a file
        File file = new File(getRealPathFromURI(fileUri));

        //creating request body for file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), newDoc.getDocTitle());
        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), newDoc.getDesc());
        RequestBody prof = RequestBody.create(MediaType.parse("text/plain"), newDoc.getProf());

        MultipartBody.Part docFile= MultipartBody.Part.createFormData("resource", file.getName(), requestFile);
        //creating a call and calling the upload image method
        Call<ResponseBody> call = resourceInterface.postDocuments(courseCode, title,desc,prof,docFile, "Bearer "+docPref.getUserId());

        //finally performing the call
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if(response.code()==200){
                    Toast.makeText(DocumentsActivity.this, "Document successfully uploaded",
                        Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"docUploaded");
                    hideProgressDialog();
                }else{
                    Log.d(TAG,"failed to upload");
                    hideProgressDialog();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG,t.toString());
                hideProgressDialog();
            }
        });
    }


    private void getDocuments(String code){
        Call<ArrayList<DocsClass>> call = resourceInterface.getDocs(code);
        call.enqueue(new Callback<ArrayList<DocsClass>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<DocsClass>> call, @NonNull
                Response<ArrayList<DocsClass>> response) {

                hideProgressDialog();
                if(response.body()!=null){

                    ArrayList<DocsClass> documents=response.body();
                    for (DocsClass docs : documents) {
                        Log.v(TAG, docs.get_id());
                        docList.add(docs);
                        docsAdapter.notifyDataSetChanged();

                    }
                    Log.d(TAG,response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<DocsClass>> call, @NonNull Throwable t) {
                hideProgressDialog();
                Toast.makeText(DocumentsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.toString());
                Log.d(TAG,call.toString());
            }
        });
    }


    public String getCourseCode(){
        return courseCode;
    }

    private void csInit(){

        docPref = new PreferenceManager(getApplicationContext());
        emptyCourses=findViewById(R.id.branch_course_empty);
        RecyclerView recyclerView = findViewById(R.id.branch_list_view);
        addDocuments =findViewById(R.id.docs_add_fab);
        docList = new ArrayList<>();
        docsAdapter = new DocsAdapter(this, docList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(docsAdapter);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build();
        resourceInterface = new Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ResourceInterface.class);
    }

    protected void onResume() {
        super.onResume();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        androidx.appcompat.app.ActionBar FSportsActionBar = getSupportActionBar();
        assert FSportsActionBar != null;
        FSportsActionBar.setHomeButtonEnabled(true);
        FSportsActionBar.setDisplayHomeAsUpEnabled(true);
        FSportsActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
        FSportsActionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Documents</font>"));
        return super.onCreateOptionsMenu(menu);

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

        if (docsProgDialog == null) {
            docsProgDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
            docsProgDialog.setMessage("Updating courses....");
            docsProgDialog.setIndeterminate(true);
            docsProgDialog.setCanceledOnTouchOutside(false);
        }

        docsProgDialog.show();
    }

    public void hideProgressDialog() {
        if (docsProgDialog != null && docsProgDialog.isShowing()) {
            docsProgDialog.dismiss();
        }
    }

}
