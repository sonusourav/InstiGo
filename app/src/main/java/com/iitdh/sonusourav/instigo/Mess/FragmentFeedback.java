package com.iitdh.sonusourav.instigo.Mess;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.AppSingleton;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;
import com.iitdh.sonusourav.instigo.Utils.VolleyErrorInstances;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentFeedback extends Fragment {

    public FragmentFeedback() {

    }
    ArrayList<FeedbackUserClass> feedbackList;
    MessFeedbackAdapter messFeedbackAdapter;
    private ProgressDialog feedbackProgressDialog;

  private String about, day, title, desc, fetchFeedbackUrl, part, postFeedbackUrl;
  private float rating;
  private static String TAG;
  private PreferenceManager feedbackPref;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      feedbackInit();
    }

  private void feedbackInit() {
    fetchFeedbackUrl = Constants.baseUrl + "mess/getfeedbacks";
    TAG = "Feedback";
    feedbackList = new ArrayList<>();
    messFeedbackAdapter = new MessFeedbackAdapter(getActivity(), feedbackList);
    feedbackPref = new PreferenceManager(Objects.requireNonNull(getActivity()));
    postFeedbackUrl = Constants.baseUrl + "mess/feedback";
    Log.d(TAG, postFeedbackUrl);
  }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.fragment_feedback, null);

        ListView listView = view.findViewById(R.id.mess_feedback_listview);
        FloatingActionButton fabButton = view.findViewById(R.id.mess_feedback_fab);
        listView.setAdapter(messFeedbackAdapter);

      showProgressDialog();
      fetchFeedbacks();


        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
                dialog.setContentView(R.layout.add_feedback);
                dialog.setTitle(" Feedback ");

              final Spinner partSpinner = dialog.findViewById(R.id.add_feedback_spinner1);
                final Spinner daySpinner =dialog.findViewById(R.id.add_feedback_spinner2);
                final EditText titleEditText =dialog.findViewById(R.id.feedback_title);
                final RatingBar ratingBar=dialog.findViewById(R.id.add_feedback_ratings);
                final EditText descEditText=dialog.findViewById(R.id.add_feedback_desc);

                Button submit=dialog.findViewById(R.id.add_feedback_submit);

                ArrayAdapter<String> aboutAdapter = new ArrayAdapter<String>(
                    getActivity().getApplicationContext(), R.layout.spinner_item,
                    getResources().getStringArray(R.array.feedbackAbout)
                );
                aboutAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
              partSpinner.setAdapter(aboutAdapter);

                ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(
                    getActivity().getApplicationContext(), R.layout.spinner_item,
                    getResources().getStringArray(R.array.dayOfWeek)
                );
                dayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
                daySpinner.setAdapter(dayAdapter);


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                      part = partSpinner.getSelectedItem().toString();
                        day=daySpinner.getSelectedItem().toString();
                        title=titleEditText.getText().toString();
                        Log.d("Title",title);
                      rating = ratingBar.getRating();
                        desc=descEditText.getText().toString();
                        Log.d("Desc",desc);


                        if(title.isEmpty()){
                            Toast.makeText(getActivity(),"Please fill Feedback title ", Toast.LENGTH_SHORT).show();
                            titleEditText.requestFocus();
                            return;
                        }

                      FeedbackUserClass newFeedback =
                          new FeedbackUserClass(rating, title, part, desc, day);

                      JSONObject jsonFeedback = null;
                      try {
                        jsonFeedback = new JSONObject(new Gson().toJson(newFeedback));
                      } catch (JSONException e) {
                        e.printStackTrace();
                        }
                      if (jsonFeedback != null) {
                        showProgressDialog();
                        postFeedback(postFeedbackUrl, jsonFeedback);
                      }

                        dialog.dismiss();


                    }
                });

                dialog.show();

            }

        });

        return view;
    }

  private void postFeedback(String feedbackUrl, JSONObject feedback) {

    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, feedbackUrl, feedback,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            try {
              if (response.getString("message").equals("success")) {
                Log.d(TAG, "feedback posted");
                JsonParser parser = new JsonParser();
                JsonElement mJson = parser.parse(response.getString("feedback"));
                Gson gson = new Gson();
                FeedbackUserClass feedback = gson.fromJson(mJson, FeedbackUserClass.class);
                feedbackList.add(feedback);
                messFeedbackAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Feedback posted successfully",
                    Toast.LENGTH_SHORT).show();
              } else if (response.getString("message").equals("success")) {
                Log.d(TAG, "feedback posting failed");
                Toast.makeText(getActivity(), "Failed to post",
                    Toast.LENGTH_SHORT).show();
              } else {
                Log.d(TAG, "else");
              }
              hideProgressDialog();
            } catch (JSONException e) {
              e.printStackTrace();
              hideProgressDialog();
              Log.d(TAG, "catchError: " + e.getMessage());
            }
          }
        },
        new Response.ErrorListener() {

          @Override
          public void onErrorResponse(VolleyError volleyError) {
            new VolleyErrorInstances().getErrorType(getActivity(), volleyError);
            Log.d(TAG, "VolleyError: " + volleyError.toString());
          }
        }) {
      @Override
      public Map<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + feedbackPref.getUserId());
        return headers;
      }
    };

    AppSingleton.getInstance().addToRequestQueue(jsonObjReq);
  }

  private void fetchFeedbacks() {

    JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, fetchFeedbackUrl, null,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {

            Log.d(TAG, response.toString());
            try {
              JSONArray mFeedbacks = response.getJSONArray("feedbacks");
              Gson gson = new Gson();
              for (int i = 0; i < mFeedbacks.length(); i++) {

                FeedbackUserClass feedbacks =
                    gson.fromJson(mFeedbacks.getString(i), FeedbackUserClass.class);
                feedbackList.add(feedbacks);
                Log.d(TAG, feedbackList.get(0).getDate());
              }
              messFeedbackAdapter.notifyDataSetChanged();
              hideProgressDialog();
            } catch (JSONException e) {
              e.printStackTrace();
              hideProgressDialog();
              Log.d(TAG, e.getMessage());
            }
          }
        }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        VolleyLog.d(TAG, "Error: " + error.getMessage());
        hideProgressDialog();
        Toast.makeText(getActivity(),
            error.getMessage(), Toast.LENGTH_LONG).show();
      }
    });

    AppSingleton.getInstance().addToRequestQueue(req);
  }

    public void showProgressDialog() {

        if (feedbackProgressDialog == null) {
            feedbackProgressDialog = new ProgressDialog(getActivity(),R.style.MyAlertDialogStyle);
            feedbackProgressDialog.setMessage("Fetching feedbacks....");
            feedbackProgressDialog.setIndeterminate(true);
            feedbackProgressDialog.setCanceledOnTouchOutside(true);
        }

        feedbackProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (feedbackProgressDialog != null && feedbackProgressDialog.isShowing()) {
            feedbackProgressDialog.dismiss();
        }
    }

  public void onResume() {
    super.onResume();
  }



}
