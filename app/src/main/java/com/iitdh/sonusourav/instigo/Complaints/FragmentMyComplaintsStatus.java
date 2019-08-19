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
import com.yalantis.taurus.PullToRefreshView;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentMyComplaintsStatus extends Fragment {

    private static final String TAG = FragmentMyComplaintsStatus.class.getSimpleName() ;
    private ArrayList<ComplainItemClass> complainStatusList;
    private static final int REFRESH_DELAY = 4000;
    private PullToRefreshView mPullToRefreshView;
    private ProgressDialog statusProgressDialog;
    private ComplaintsInterface complaintsInterface;
    private ComplaintsAdapter statusAdapter;
    private PreferenceManager myComplaintPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.complaint_my_status, null);
        ListView listView = view.findViewById(R.id.status_listview);
        mPullToRefreshView=view.findViewById(R.id.pull_to_refresh);
        complainStatusList = new ArrayList<>();
        myComplaintPref =
            new PreferenceManager(Objects.requireNonNull(getActivity()));

        showProgressDialog();

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });

        statusAdapter = new ComplaintsAdapter(getActivity(), complainStatusList);
        listView.setAdapter(statusAdapter);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build();
        complaintsInterface = new Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ComplaintsInterface.class);

        fetchStatus();

        return view;
    }

    private void fetchStatus(){

        Call<ArrayList<ComplainItemClass>>  fetchComplaints = complaintsInterface.getMyComplaints("Bearer "+myComplaintPref.getUserId());
        Log.d(TAG,myComplaintPref.getUserId());
        fetchComplaints.enqueue(new Callback<ArrayList<ComplainItemClass>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<ComplainItemClass>> call, @NonNull
                Response<ArrayList<ComplainItemClass>> response) {

                hideProgressDialog();

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
