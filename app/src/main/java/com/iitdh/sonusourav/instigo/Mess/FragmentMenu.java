package com.iitdh.sonusourav.instigo.Mess;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.AppSingleton;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.ramotion.foldingcell.FoldingCell;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;

public class FragmentMenu extends Fragment {

  private static String TAG;
  int dayNo;
  private String[] weekdays;
  private FoldingCellListAdapter adapter;
  private ArrayList<MealClass> items = new ArrayList<>();
  private String menuUrl;
  private int day;
  private Button dayButton;
  private List<MenuClass> messMenu;

  public FragmentMenu() {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    menuInit();
  }

  private void fetchMenu() {

    JsonArrayRequest req = new JsonArrayRequest(menuUrl,
        new Response.Listener<JSONArray>() {
          @Override
          public void onResponse(JSONArray response) {

            Log.d(TAG, response.toString());
            try {
              Gson gson = new Gson();
              for (int i = 0; i < response.length(); i++) {

                MenuClass menuClass = gson.fromJson(response.getString(i), MenuClass.class);
                messMenu.add(menuClass);
              }
            } catch (JSONException e) {
              e.printStackTrace();
              Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            if ((messMenu != null) && messMenu.size() > 0) {
              setMenu(messMenu, dayNo);
            }
          }
        }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        VolleyLog.d(TAG, "Error: " + error.getMessage());
        Toast.makeText(getActivity(),
            error.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });

    AppSingleton.getInstance().addToRequestQueue(req);
  }

  private void setMenu(List<MenuClass> messMenu, int day) {
    MenuClass menu;
    menu = messMenu.get(day);
    MealClass breakfast, lunch, snacks, dinner;
    breakfast = menu.getBreakfast();
    lunch = menu.getLunch();
    snacks = menu.getSnacks();
    dinner = menu.getDinner();

    items.clear();
    items.add(breakfast);
    items.add(lunch);
    items.add(snacks);
    items.add(dinner);

    adapter.notifyDataSetChanged();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.fragment_menu, null);
    final ListView theListView = view.findViewById(R.id.mainListView);
    dayButton = view.findViewById(R.id.mess_day_button);

    BoomMenuButton bmb = view.findViewById(R.id.bmb);
    assert bmb != null;

    adapter = new FoldingCellListAdapter(this, items);
    theListView.setAdapter(adapter);

    dayButton.setText(weekdays[day - 1]);
    fetchMenu();

    theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

        ((FoldingCell) view).toggle(false);
        // register in adapter that state for selected cell is toggled
        adapter.registerToggle(pos);
      }
    });

    bmb.setButtonEnum(ButtonEnum.TextInsideCircle);
    bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_4);
    bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_3);
    bmb.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.Bottom);

    for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
      bmb.addBuilder(new TextInsideCircleButton.Builder().normalText(" " + weekdays[i])
          .textGravity(Gravity.CENTER).typeface(Typeface.DEFAULT_BOLD)
          .textSize(12).rippleEffect(true).rotateImage(true).normalImageRes(R.drawable.dolphin)
      );

      bmb.setOnBoomListener(new OnBoomListener() {
        @Override
        public void onClicked(int index, BoomButton boomButton) {

          dayNo = index;
          dayButton.setText(weekdays[index]);

          if ((messMenu != null) && !messMenu.isEmpty()) {
            adapter.removeAll();
            setMenu(messMenu, index);
          }
        }

        @Override
        public void onBackgroundClick() {

        }

        @Override
        public void onBoomWillHide() {

        }

        @Override
        public void onBoomDidHide() {

        }

        @Override
        public void onBoomWillShow() {

        }

        @Override
        public void onBoomDidShow() {

        }
      });
    }

    return view;
  }

  private void menuInit() {

    menuUrl = Constants.baseUrl + "mess";
    weekdays = new String[] {
        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };
    TAG = Objects.requireNonNull(getActivity()).getClass().getSimpleName();
    messMenu = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    day = calendar.get(Calendar.DAY_OF_WEEK);
    dayNo = day - 1;
  }

  @Override
  public void onResume() {
    super.onResume();
  }
}

