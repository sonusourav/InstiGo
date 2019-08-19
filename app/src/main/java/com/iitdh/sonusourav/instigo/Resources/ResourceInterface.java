package com.iitdh.sonusourav.instigo.Resources;

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

public interface ResourceInterface {

  @GET("courses")
  Call<ArrayList<CourseClass>> getCourses();

  @GET("courses/getdocuments/{courseCode}")
  Call<ArrayList<DocsClass>> getDocs(@Path("courseCode") String courseCode);

  @GET
  Call<ResponseBody> downloadFile(@Url String fileUrl);

  @POST("courses/postcourses")
  Call<ResponseBody> postCourse(@Body CourseClass courseClass);


  @Multipart
  @POST("documents/{courseCode}")
  Call<ResponseBody> postDocuments(@Path("courseCode") String courseCode,
      @Part("docTitle") RequestBody docTitle,
      @Part("desc") RequestBody desc,
      @Part("prof") RequestBody prof,
      @Part MultipartBody.Part file,
      @Header("Authorization") String auth
  );

}