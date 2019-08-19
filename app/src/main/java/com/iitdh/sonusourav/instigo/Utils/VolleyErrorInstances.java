package com.iitdh.sonusourav.instigo.Utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.iitdh.sonusourav.instigo.Account.LoginActivity;

public class VolleyErrorInstances  {

  private static VolleyErrorInstances errorInstancesInstance;

 public VolleyErrorInstances (){
   errorInstancesInstance=this;
 }

  public static synchronized VolleyErrorInstances getErrorInstancesInstance() {
    return errorInstancesInstance;
  }

  public void getErrorType(Context context,VolleyError volleyError){

    if (volleyError instanceof NetworkError) {
      Toast.makeText(context,"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_SHORT).show();
    } else if (volleyError instanceof ServerError) {
      Toast.makeText(context,"The server could not be found. Please try again after some time!!",Toast.LENGTH_SHORT).show();
    } else if (volleyError instanceof AuthFailureError) {
      Toast.makeText(context,"AuthFailureError",Toast.LENGTH_SHORT).show();
      context.startActivity(new Intent(context, LoginActivity.class));
    } else if (volleyError instanceof ParseError) {
      Toast.makeText(context,"Parsing error! Please try again after some time!!",Toast.LENGTH_SHORT).show();

    } else if (volleyError instanceof TimeoutError) {
      Toast.makeText(context,"Connection TimeOut! Please check your internet connection.",Toast.LENGTH_SHORT).show();

    }
  }
}
