package com.iitdh.sonusourav.instigo.Resources;

import android.Manifest;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DocsAdapter extends RecyclerView.Adapter<DocsAdapter.MyViewHolder>
    implements EasyPermissions.PermissionCallbacks {

  private static final int WRITE_REQUEST_CODE = 300;
  private static final String TAG = DocumentsActivity.class.getSimpleName();
  private DocumentsActivity mContext;
  private ArrayList<DocsClass> docList;
  private String docFile, url;

  private OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
      .connectTimeout(60, TimeUnit.SECONDS)
      .readTimeout(60, TimeUnit.SECONDS)
      .writeTimeout(120, TimeUnit.SECONDS)
      .build();
  ResourceInterface resourceInterface = new Retrofit.Builder()
      .baseUrl(Constants.baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()
      .create(ResourceInterface.class);

  DocsAdapter(DocumentsActivity mContext, ArrayList<DocsClass> docList) {
    this.mContext = mContext;
    this.docList = docList;
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
    final DocsClass course = docList.get(position);
    final String docName = course.getDocTitle();
    String dateCreated = course.getDate();
    String docProf = course.getProf();
    final String docBy = course.getBy();
    final String docDesc = course.getDesc();
    final String docUrl = course.getUrl();
    String type = course.getType();
    final String docPath = course.getPath();
    final String docFileName = course.getFile();
    docFile = docFileName;

    TextView date = holder.date;
    TextView docTitle = holder.docTitle;
    final TextView desc = holder.desc;
    TextView prof = holder.prof;
    final TextView by = holder.by;
    final ImageView userImage = holder.url;
    ImageView docDownload = holder.download;
    ImageView docArrow = holder.docMore;
    ImageView docType = holder.docType;
    final View docLineView = holder.lineView;
    final RelativeLayout relativeLayout = holder.relativeLayout;

    date.setText(dateCreated);
    prof.setText(docProf);
    docTitle.setText(docName);

    docArrow.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (relativeLayout.getVisibility() != View.VISIBLE) {
          relativeLayout.setVisibility(View.VISIBLE);
          docLineView.setVisibility(View.GONE);
          by.setText(docBy);
          if (docDesc != null) {
            desc.setText(docDesc);
          } else {
            desc.setText("");
          }

          userImage.setImageResource(R.drawable.image_profile_pic);

          if (docUrl != null) {
            Glide.with(mContext)
                .load(docUrl)
                .into(userImage);
          }
        } else {
          relativeLayout.setVisibility(View.GONE);
          docLineView.setVisibility(View.VISIBLE);
        }
      }
    });

    docType.setImageDrawable(mContext.getDrawable(R.drawable.icon_pdf));

      /*  if (type.equalsIgnoreCase("pdf")) {
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
        }*/

    final String finalType = type;
    docDownload.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mContext.showProgressDialog1();
        String downloadPath =
            Constants.baseUrl + "download/" + mContext.getCourseCode() + "/" + docFileName;
        Log.d(TAG, downloadPath);
        CheckSDCard(downloadPath, mContext.getCourseCode(), docName, docFileName, finalType);
      }
    });
  }

  @Override
  public int getItemCount() {
    return docList.size();
  }

  private void CheckSDCard(String path, String courseName, String docTitle, String fileName,
      String type) {
    if (CheckForSDCard.isSDCardPresent()) {

      Log.d(TAG, "CheckSDCard");
      //check if app has permission to write to the external storage.
      if (EasyPermissions.hasPermissions(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        //Get the URL entered
        Log.d(TAG, path);

        getFile(path, fileName);
      } else {
        Log.d(TAG, "PermissionNotPresent");

        //If permission is not present request for the same.
        EasyPermissions.requestPermissions(mContext, mContext.getString(R.string.write_file),
            WRITE_REQUEST_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        url = path;
      }
    } else {
      Toast.makeText(mContext,
          "SD Card not found", Toast.LENGTH_LONG).show();
    }
  }

  private boolean writeResponseBodyToDisk(ResponseBody body, String fileName) {
    try {
      // todo change the file location/name according to your needs
      File futureStudioIconFile =
          new File(mContext.getExternalCacheDir() + File.separator + fileName);

      InputStream inputStream = null;
      OutputStream outputStream = null;

      try {
        byte[] fileReader = new byte[4096];

        long fileSize = body.contentLength();
        long fileSizeDownloaded = 0;

        inputStream = body.byteStream();
        try {
          outputStream = new FileOutputStream(futureStudioIconFile);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }

        while (true) {
          int read = 0;
          try {
            read = inputStream.read(fileReader);
          } catch (IOException e) {
            e.printStackTrace();
          }

          if (read == -1) {
            break;
          }

          outputStream.write(fileReader, 0, read);

          fileSizeDownloaded += read;

          Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
        }

        if (outputStream != null) {
          outputStream.flush();
        }

        return true;
      } catch (IOException e) {
        return false;
      } finally {
        if (inputStream != null) {
          inputStream.close();
        }

        if (outputStream != null) {
          outputStream.close();
        }
      }
    } catch (IOException e) {
      return false;
    }
  }

  private void getFile(String docPath, final String fileName) {

    Call<ResponseBody> call = resourceInterface.downloadFile(docPath);

    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(@NonNull Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.code() == 200) {
          Log.d(TAG, "server contacted and has file");
          boolean writtenToDisk = false;
          if (response.body() != null) {
            writtenToDisk = writeResponseBodyToDisk(response.body(), fileName);
          }
          Toast.makeText(mContext,
              "Downloaded successfully", Toast.LENGTH_LONG).show();
          Log.d(TAG, "file download was a success? " + writtenToDisk);
        } else {
          Log.d(TAG, "server contact failed");
        }
        mContext.hideProgressDialog();
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e(TAG, call.toString());
        Log.d(TAG, t.toString());
        mContext.hideProgressDialog();
      }
    });
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    Log.d(TAG, "PermissionRequest");
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, mContext);
  }

  @Override
  public void onPermissionsGranted(int requestCode, List<String> perms) {
    //Download the file once permission is granted

    Log.d(TAG, url);
    getFile(url, docFile);
  }

  @Override
  public void onPermissionsDenied(int requestCode, List<String> perms) {
    Log.d(TAG, "Permission has been denied");
  }

  static class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView docTitle, prof, date, by, desc;
    private ImageView download, docType, docMore, url;
    private View lineView;
    private RelativeLayout relativeLayout;

    MyViewHolder(View view) {
      super(view);
      this.docTitle = view.findViewById(R.id.doc_title);
      this.prof = view.findViewById(R.id.doc_prof);
      this.date = view.findViewById(R.id.doc_date);
      this.by = view.findViewById(R.id.doc_by);
      this.desc = view.findViewById(R.id.doc_desc);
      this.download = view.findViewById(R.id.doc_download);
      this.docType = view.findViewById(R.id.doc_type);
      this.docMore = view.findViewById(R.id.doc_more);
      this.url = view.findViewById(R.id.doc_url);
      this.lineView = view.findViewById(R.id.doc_view1);
      this.relativeLayout = view.findViewById(R.id.doc_more_details);
    }
  }
}
