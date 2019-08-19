package com.iitdh.sonusourav.instigo.Complaints;

import com.iitdh.sonusourav.instigo.Resources.CourseClass;
import com.iitdh.sonusourav.instigo.Resources.DocsClass;
import com.iitdh.sonusourav.instigo.User.UserClass;
import com.iitdh.sonusourav.instigo.Utils.ResponseClass;
import java.util.ArrayList;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ComplaintsInterface {

  @POST("complaints/create")
  Call<ResponseBody> postComplaints(@Body ComplainItemClass complainItemClass, @Header("Authorization") String auth);


  @GET("complaints/{path}")
  Call<ArrayList<ComplainItemClass>> getComplaints(@Path(value = "path", encoded = true) String path);


  @GET("complaints/my")
  Call<ArrayList<ComplainItemClass>> getMyComplaints(@Header("Authorization") String auth);

  @FormUrlEncoded
  @POST("users/fcmtoken")
  Call<ResponseBody> sendFCMToken(@Field("fcmToken") String fcmToken);

  @FormUrlEncoded
  @POST("complaints/{to}/{status}/{complainId}")
  Call<ResponseClass> notifyUsers(@Path("to") String to,@Path("status") String status,@Path("complainId") String complainId,@Field("comment") String comment,@Header("Authorization") String auth);

}