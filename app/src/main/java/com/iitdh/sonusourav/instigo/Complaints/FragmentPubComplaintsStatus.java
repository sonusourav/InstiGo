package com.iitdh.sonusourav.instigo.Complaints;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentPubComplaintsStatus extends Fragment {

    private static final String TAG = FragmentPubComplaintsStatus.class.getSimpleName() ;
    private ArrayList<ComplainItemClass> complainStatusList;
    private static final int REFRESH_DELAY = 4000;
    private ProgressDialog statusProgressDialog;
    private ComplaintsInterface complaintsInterface;
    private ComplaintsAdapter statusAdapter;
    private PreferenceManager pubComplaintPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.complaint_pub_status, null);
        ListView listView = view.findViewById(R.id.status_listview);
        complainStatusList = new ArrayList<>();

        showProgressDialog();

        statusAdapter = new ComplaintsAdapter(getActivity(), complainStatusList);
        listView.setAdapter(statusAdapter);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
        complaintsInterface = new Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ComplaintsInterface.class);
        pubComplaintPref=new PreferenceManager(Objects.requireNonNull(getActivity()));

        int level=pubComplaintPref.getUserLevel();
        Log.d(TAG,"Level is "+level);
        fetchStatus(level);

        return view;
    }

    private void fetchStatus(int level){
        Log.d(TAG,level+"");
        String path;
        switch (level){
            case 0:path= "";
            break;
            case 1:path="level1";
            break;
            case 2:path="level2/"+(level);
            break;
            default:path="level2/"+(level);
            break;
        }
        Log.d(TAG,path);


        Call<ArrayList<ComplainItemClass>>  fetchComplaints = complaintsInterface.getComplaints(path);
        fetchComplaints.enqueue(new Callback<ArrayList<ComplainItemClass>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<ComplainItemClass>> call, @NonNull
                Response<ArrayList<ComplainItemClass>> response) {

               hideProgressDialog();

               Log.d(TAG,response.toString());
               if(response.body()!=null){
                   Log.d(TAG,"Fetch");
                   complainStatusList.addAll(response.body());
                   statusAdapter.notifyDataSetChanged();
                   Log.d(TAG,response.toString());
               }

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<ComplainItemClass>> call, @NonNull Throwable t) {
                hideProgressDialog();
                Log.d(TAG,"failure");
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.toString());
            }
        });


    }

    public void onResume() {
        super.onResume();

    }

    public void showProgressDialog() {

        if (statusProgressDialog == null) {
            statusProgressDialog = new ProgressDialog(getActivity(),R.style.MyAlertDialogStyle);
            statusProgressDialog.setMessage("Fetching complaints....");
            statusProgressDialog.setIndeterminate(true);
            statusProgressDialog.setCanceledOnTouchOutside(false);
        }

        statusProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (statusProgressDialog != null && statusProgressDialog.isShowing()) {
            statusProgressDialog.dismiss();
        }
    }

}
