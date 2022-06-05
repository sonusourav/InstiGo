package com.iitdh.sonusourav.instigo.Resources;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Resources.CS.CSResourceDocs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;


public class DocsAdapter extends RecyclerView.Adapter<DocsAdapter.MyViewHolder> implements EasyPermissions.PermissionCallbacks{

    private Context mContext;
    private List<DocsClass> courseList;
    private String url;
    private static final int WRITE_REQUEST_CODE = 300;
    private static final String TAG = CSResourceDocs.class.getSimpleName();
    private String tName,cName,sName,docType;



    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView docTopic, docSubTopic;
        private ImageView download, docType;

        MyViewHolder(View view) {
            super(view);
            this.docTopic = view.findViewById(R.id.doc_topic);
            this.docSubTopic = view.findViewById(R.id.doc_sub_topic);
            this.download = view.findViewById(R.id.doc_download);
            this.docType = view.findViewById(R.id.doc_type);

        }
    }


    public DocsAdapter(Context mContext, List<DocsClass> courseList) {
        this.mContext = mContext;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doc_cell, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final DocsClass course = courseList.get(position);
        final String courseName,topicName,sub_topicName,type;

        TextView topic = holder.docTopic;
        TextView subTopic = holder.docSubTopic;
        ImageView docType = holder.docType;
        ImageView docDownload = holder.download;

        courseName=course.getCourseName();
        topicName=course.getTopic();
        sub_topicName=course.getSubTopic();

        topic.setText(course.getTopic());
        subTopic.setText(course.getSubTopic());

        if (course.getType().equalsIgnoreCase("PDF")) {
            docType.setImageDrawable(mContext.getDrawable(R.drawable.icon_pdf));
            type=".pdf";

        } else if (course.getType().equalsIgnoreCase("Image")) {
            docType.setImageDrawable(mContext.getDrawable(R.drawable.icon_image));
            type=".png";

        } else if (course.getType().equalsIgnoreCase("Zip")) {
            docType.setImageDrawable(mContext.getDrawable(R.drawable.icon_zip));
            type=".zip";

        }else if (course.getType().equalsIgnoreCase("PPT")) {
            docType.setImageDrawable(mContext.getDrawable(R.drawable.icon_ppt));
            type=".ppt";

        }else if (course.getType().equalsIgnoreCase("Excel")) {
            docType.setImageDrawable(mContext.getDrawable(R.drawable.icon_excel));
            type=".xls";


        } else if (course.getType().equalsIgnoreCase("Other")) {
            docType.setImageDrawable(mContext.getDrawable(R.drawable.icon_folder));
            type="";


        } else {
            docType.setImageDrawable(mContext.getDrawable(R.drawable.icon_text_file));
            type=".txt";
        }

        docDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckSDCard(course.getImageUrl(),courseName,topicName,sub_topicName,type);

            }
        });

    }


    @Override
    public int getItemCount() {
        return courseList.size();
    }


    private void CheckSDCard(String path,String courseName,String topicName,String sub_topicName,String type) {
        if (CheckForSDCard.isSDCardPresent()) {

            Log.d("CheckSDCard","Reaching");
            //check if app has permission to write to the external storage.
            if (EasyPermissions.hasPermissions(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Get the URL entered
                url = path;
                Log.d("URL1",path + "");
                new DownloadFile(mContext).execute(path,courseName,topicName,sub_topicName,type);

            } else {
                Log.d("PermissionNotPresent","Reaching");


                //If permission is not present request for the same.
                EasyPermissions.requestPermissions(mContext, mContext.getString(R.string.write_file), WRITE_REQUEST_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                tName=courseName;
                cName=courseName;
                sName=sub_topicName;
                docType=type;
            }


        } else {
            Toast.makeText(mContext,
                    "SD Card not found", Toast.LENGTH_LONG).show();

        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("PermissionRequest","Reaching");
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, mContext);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //Download the file once permission is granted

        Log.d("URL2",url);
        new DownloadFile(mContext).execute(url,cName,tName,sName,docType);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }


}





  class DownloadFile extends AsyncTask<String, String, String> {

    private ProgressDialog progressDialog;
      private static final String TAG = CSResourceDocs.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private Context dContext;


    /**
     * Before starting background thread
     * Show Progress Bar Dialog
     */

      DownloadFile(Context context){

          this.dContext=context;
      }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog = new ProgressDialog(dContext);
        this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    /**
     * Downloading file in background thread
     */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            Log.d("courseName",f_url[1]);
            Log.d("topicName",f_url[2]);
            Log.d("subTopicName",f_url[3]);
            Log.d("type",f_url[4]);


            URLConnection connection = url.openConnection();
            connection.connect();
            // getting file length
            int lengthOfFile = connection.getContentLength();


            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss",Locale.ENGLISH).format(new Date());

            //Extract file name from URL
/*
                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());
*/


            String fileName;
            if(!f_url[1].isEmpty() && !f_url[2].isEmpty() && !f_url[3].isEmpty() && !f_url[4].isEmpty()){
                fileName =f_url[1]+ "_"+ f_url[2]+"_"+f_url[3]+"_"+f_url[4];

            }else{

                fileName ="downloaded_file";
                //Append timestamp to file name
                if(!f_url[4].isEmpty()){
                    fileName = timestamp + "_" + fileName +f_url[4];

                }else{
                    fileName = timestamp + "_" + fileName;

                }
            }


            //External directory path to save file
            String folder = Environment.getExternalStorageDirectory() + File.separator + "InstiGoResources/";

            //Create androiddeft folder if it does not exist
            File directory = new File(folder);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Output stream to write file
            OutputStream output = new FileOutputStream(folder + fileName);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lengthOfFile));
                Log.d(TAG, "Progress: " + (int) ((total * 100) / lengthOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();
            return "Downloaded at: " + folder + fileName;

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return "Something went wrong";
    }

    /**
     * Updating progress bar
     */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        progressDialog.setProgress(Integer.parseInt(progress[0]));
    }


    @Override
    protected void onPostExecute(String message) {
        // dismiss the dialog after the file was downloaded
        this.progressDialog.dismiss();

        // Display File path after downloading
        Toast.makeText(dContext,
                message, Toast.LENGTH_LONG).show();
    }
}
