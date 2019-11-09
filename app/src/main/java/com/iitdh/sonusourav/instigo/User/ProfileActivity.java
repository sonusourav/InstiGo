package com.iitdh.sonusourav.instigo.User;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.exifinterface.media.ExifInterface;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.AppSingleton;
import com.iitdh.sonusourav.instigo.Utils.CommonFunctions;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;
import com.iitdh.sonusourav.instigo.Utils.VolleyErrorInstances;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private final int PICK_IMAGE_REQUEST1 = 70;
  private final int PICK_IMAGE_REQUEST2 = 41;
  private String TAG = ProfileActivity.class.getSimpleName();

  private ImageButton profileEditBtn;
  private TextView profileName, profileBranch, profileYear, profileMobile, profileDob,
      profileGender, profileHostel;
  private ProgressDialog profileDialog;
  private ImageView profilePic, profileCoverPic;
  private PreferenceManager profilePref;
  private UserClass user;
  private String profilePicUrl, coverPicUrl;
  private RetrofitInterface retrofitInterface;
  private static final int PERMISSION_REQUEST_CODE = 200;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);

    findViewById(R.id.include_profile).setVisibility(View.VISIBLE);
    CommonFunctions.setUser(this);

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
    getSupportActionBar().setHomeButtonEnabled(true);

    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();
    profileInit();

    if (Build.VERSION.SDK_INT >= 23) {
      if (checkPermission()) {
        showProgressDialog();
        getProfile();
      } else {
        requestPermission(); // Code for permission
      }
    } else {

      showProgressDialog();
      getProfile();
    }

    profileEditBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        showAlertDialogButtonClicked(profileEditBtn);
      }
    });
  }

  private boolean checkPermission() {
    int result = ContextCompat.checkSelfPermission(ProfileActivity.this,
        Manifest.permission.READ_EXTERNAL_STORAGE);
    int result1 =
        ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA);
    Log.d(TAG, result + "");
    Log.d(TAG, result1 + "");

    return ((result == PackageManager.PERMISSION_GRANTED) && (result1
        == PackageManager.PERMISSION_GRANTED));
  }

  private void requestPermission() {

    Log.d(TAG, "request permission");
    ActivityCompat.requestPermissions(ProfileActivity.this,
        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA },
        PERMISSION_REQUEST_CODE);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == PERMISSION_REQUEST_CODE) {
      if (grantResults.length > 0) {

        boolean storagePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

        if (storagePermission && cameraAccepted) {
          Log.d(TAG, "permission granted");
        } else {
          Log.d(TAG, "permission Denied");

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                && shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
              showMessageOKCancel("You need to allow access to both the permissions",
                  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      requestPermissions(new String[] {
                              Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                          },
                          PERMISSION_REQUEST_CODE);
                    }
                  });
            }
          }
        }
      }
    }
  }

  private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
    new AlertDialog.Builder(ProfileActivity.this)
        .setMessage(message)
        .setPositiveButton("OK", okListener)
        .setNegativeButton("Cancel", null)
        .create()
        .show();
  }

  public void showAlertDialogButtonClicked(View view) {

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Profile Update");

    builder.setMessage("What do you want to update?")
        .setCancelable(true)
        .setPositiveButton("Profile pic", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
            if (checkPermission()) {
              chooseImage(PICK_IMAGE_REQUEST1);
            } else {
              requestPermission();
            }
          }
        }).setNeutralButton("Cover pic", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {

        dialog.cancel();
        if (checkPermission()) {
          chooseImage(PICK_IMAGE_REQUEST2);
        } else {
          requestPermission();
        }
      }
    })
        .setNegativeButton("Profile Info", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {

            Intent editInfoIntent = new Intent(ProfileActivity.this, EditInfoActivity.class);
            editInfoIntent.putExtra("userInfo", user);
            startActivity(editInfoIntent);
            dialog.cancel();
          }
        });

    AlertDialog dialog = builder.create();
    dialog.show();
  }

  private void profileInit() {

    profilePref = new PreferenceManager(getApplicationContext());
    profileEditBtn = findViewById(R.id.profile_edit);
    profileName = findViewById(R.id.profile_name);
    profileBranch = findViewById(R.id.profile_branch);
    profileYear = findViewById(R.id.profile_year);
    profileDob = findViewById(R.id.profile_dob);
    profileMobile = findViewById(R.id.profile_mobile);
    profileGender = findViewById(R.id.profile_gender);
    profileHostel = findViewById(R.id.profile_hostel);
    profilePic = findViewById(R.id.profile_pic);
    profileCoverPic = findViewById(R.id.profile_cover_pic);
    profilePicUrl = Constants.baseUrl + "profilepic";
    coverPicUrl = Constants.baseUrl + "coverpic";
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build();

    retrofitInterface = new Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .client(okHttpClient)
        .build()
        .create(RetrofitInterface.class);
  }

  private void multipartImageUpload(String url, Bitmap bitmap, Uri fileName, int type) {
    try {
      File filesDir = getApplicationContext().getFilesDir();
      File file = new File(filesDir, getFileName(fileName));

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
      byte[] bitmapdata = bos.toByteArray();

      FileOutputStream fos = new FileOutputStream(file);
      fos.write(bitmapdata);
      fos.flush();
      fos.close();

      RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
      MultipartBody.Part body;
      if (type == 0) {
        body = MultipartBody.Part.createFormData("profilepic", file.getName(), reqFile);
      } else {
        body = MultipartBody.Part.createFormData("coverpic", file.getName(), reqFile);
      }

      Call<ResponseBody> req =
          retrofitInterface.uploadImage(url, body, "Bearer " + profilePref.getUserId());
      req.enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(@NonNull Call<ResponseBody> call,
            @NonNull retrofit2.Response<ResponseBody> response) {

          hideProgressDialog();
          if (response.code() == 200) {
            Log.d(TAG, "Upload success");
          }
          Log.d(TAG, response.message());
          if (response.body() != null) {
            Log.d(TAG, response.body().toString());
          }
          Log.d(TAG, response.toString());
        }

        @Override
        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

          hideProgressDialog();
          Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
          t.printStackTrace();
          Log.d(TAG, call.toString());
          Log.d(TAG, t.toString());
        }
      });
    } catch (FileNotFoundException e) {
      hideProgressDialog();
      e.printStackTrace();
    } catch (IOException e) {
      hideProgressDialog();
      e.printStackTrace();
    }
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      NavUtils.navigateUpFromSameTask(this);
    }
  }

  private void getProfile() {

    String profileUrl = Constants.baseUrl + "users/profile";

    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, profileUrl, null,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {

            if (response != null) {

              Log.d(TAG, response.toString());
              Iterator<String> keys = response.keys();
              while (keys.hasNext()) {
                String key = keys.next();
                try {
                  if (response.getString(key).equals("null")) {
                    response.put(key, "");
                  }
                } catch (JSONException e) {
                  e.printStackTrace();
                }
              }
              user = new Gson().fromJson(response.toString(), UserClass.class);
              profileName.setText(user.getName());
              profileBranch.setText(user.getBranch());
              profileYear.setText(user.getYear());
              profileMobile.setText(user.getPhone());
              profileDob.setText(user.getDob());
              profileGender.setText(user.getGender());
              if (user.getHostel() != null && !user.getHostel().equals("")) {
                profileHostel.setText(
                    getResources().getTextArray(R.array.hostel)[Integer.parseInt(
                        user.getHostel())]);
              }

              if (!user.getProfilePic().trim().equals("")) {

                Log.d(TAG, user.getProfilePic());
                Glide.with(ProfileActivity.this)
                    .load(user.getProfilePic())
                    .into(profilePic);
              } else {
                profilePic.setImageResource(R.drawable.image_profile_pic);
              }

              if (!user.getCoverPic().trim().equals("")) {

                Log.d(TAG, user.getCoverPic());
                Glide.with(ProfileActivity.this)
                    .load(user.getCoverPic())
                    .into(profileCoverPic);
              }

              if (user.getBranch().equals("") || user.getBranch().isEmpty()) {
                profileBranch.setText("Branch");
              }

              if (user.getDob().equals("") || user.getDob().isEmpty()) {
                profileDob.setText("Date of Birth");
              }

              if (user.getGender().equals("") || user.getGender().isEmpty()) {
                profileGender.setText("Gender");
              }

              if (user.getYear().equals("") || user.getYear().isEmpty()) {
                profileYear.setText("Year of Study");
              }

              if (user.getPhone().equals("") || user.getPhone().isEmpty()) {
                profileMobile.setText("Mobile no");
              }

              hideProgressDialog();
            } else {
              Toast.makeText(ProfileActivity.this, "User Profile does not exist",
                  Toast.LENGTH_SHORT).show();
              hideProgressDialog();
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
        Map<String, String> params = new HashMap<>();
        params.put("Authorization", "Bearer " + profilePref.getUserId());

        return params;
      }
    };
    AppSingleton.getInstance().addToRequestQueue(jsonObjReq);
  }

  private void chooseImage(int imageReq) {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Select Picture"), imageReq);
  }

  public String getFileName(Uri uri) {
    String result = null;
    if (Objects.equals(uri.getScheme(), "content")) {
      try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
        if (cursor != null && cursor.moveToFirst()) {
          result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
        }
      }
    }
    if (result == null) {
      result = uri.getPath();
      int cut = 0;
      if (result != null) {
        cut = result.lastIndexOf('/');
      }
      if (cut != -1) {
        if (result != null) {
          result = result.substring(cut + 1);
        }
      }
    }
    return result;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (((requestCode == PICK_IMAGE_REQUEST1) || (requestCode == PICK_IMAGE_REQUEST2))
        && resultCode == RESULT_OK
        && data != null
        && data.getData() != null) {
      showProgressDialog();
      Uri filePath = data.getData();

      try {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
        //Bitmap bitmap=handleSamplingAndRotationBitmap(ProfileActivity.this,filePath);

        if (requestCode == PICK_IMAGE_REQUEST1) {
          Glide.with(this)
              .load(bitmap)
              .into(profilePic);
          Log.d(TAG, "req1");
          Log.d(TAG, getStringImage(bitmap));

          multipartImageUpload(profilePicUrl, bitmap, filePath, 0);
        } else {
          Glide.with(this)
              .load(bitmap)
              .into(profileCoverPic);
          Log.d(TAG, "req2");
          multipartImageUpload(coverPicUrl, bitmap, filePath, 1);
        }
      } catch (IOException e) {
        e.printStackTrace();
        hideProgressDialog();
      }
    }
  }

  public String getStringImage(Bitmap bmp) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    byte[] imageBytes = baos.toByteArray();
    return Base64.encodeToString(imageBytes, Base64.DEFAULT);
  }

  public void showProgressDialog() {

    if (profileDialog == null) {
      profileDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
      profileDialog.setMessage("Updating your profile ....");
      profileDialog.setIndeterminate(true);
      profileDialog.setCanceledOnTouchOutside(false);
    }

    profileDialog.show();
  }

  public void hideProgressDialog() {
    if (profileDialog != null && profileDialog.isShowing()) {
      profileDialog.dismiss();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    return CommonFunctions.navigationItemSelect(item, this);
  }

  private static Bitmap rotateImage(Bitmap img, int degree) {
    Matrix matrix = new Matrix();
    matrix.postRotate(degree);
    Bitmap rotatedImg =
        Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
    img.recycle();
    return rotatedImg;
  }

  private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage)
      throws IOException {

    InputStream input = context.getContentResolver().openInputStream(selectedImage);
    ExifInterface ei;
    if (Build.VERSION.SDK_INT > 23) {
      ei = new ExifInterface(input);
    } else {
      ei = new ExifInterface(selectedImage.getPath());
    }

    int orientation =
        ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

    switch (orientation) {
      case ExifInterface.ORIENTATION_ROTATE_90:
        return rotateImage(img, 90);
      case ExifInterface.ORIENTATION_ROTATE_180:
        return rotateImage(img, 180);
      case ExifInterface.ORIENTATION_ROTATE_270:
        return rotateImage(img, 270);
      default:
        return img;
    }
  }

  public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
      throws IOException {
    int MAX_HEIGHT = 1024;
    int MAX_WIDTH = 1024;

    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
    BitmapFactory.decodeStream(imageStream, null, options);
    if (imageStream != null) {
      imageStream.close();
    }

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    imageStream = context.getContentResolver().openInputStream(selectedImage);
    Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

    img = rotateImageIfRequired(context, img, selectedImage);
    return img;
  }

  private static int calculateInSampleSize(BitmapFactory.Options options,
      int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

      // Calculate ratios of height and width to requested height and width
      final int heightRatio = Math.round((float) height / (float) reqHeight);
      final int widthRatio = Math.round((float) width / (float) reqWidth);

      // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
      // with both dimensions larger than or equal to the requested height and width.
      inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

      // This offers some additional logic in case the image has a strange
      // aspect ratio. For example, a panorama may have a much larger
      // width than height. In these cases the total pixels might still
      // end up being too large to fit comfortably in memory, so we should
      // be more aggressive with sample down the image (=larger inSampleSize).

      final float totalPixels = width * height;

      // Anything more than 2x the requested pixels we'll sample down further
      final float totalReqPixelsCap = reqWidth * reqHeight * 2;

      while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
        inSampleSize++;
      }
    }
    return inSampleSize;
  }
}
