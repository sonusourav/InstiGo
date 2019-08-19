package com.iitdh.sonusourav.instigo.Utils;

import com.iitdh.sonusourav.instigo.Resources.CourseClass;
import com.iitdh.sonusourav.instigo.Resources.DocsClass;
import com.iitdh.sonusourav.instigo.User.UserClass;
import java.util.ArrayList;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RetrofitInterface {

  @GET("users/picnameemail")
  Call<UserClass> getPicNameEmail(@Header("Authorization") String header);

}