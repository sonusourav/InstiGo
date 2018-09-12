package com.iitdh.sonusourav.instigo.Mess;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.iitdh.sonusourav.instigo.R;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }
    private BoomMenuButton bmb;
    private String weekdays[]={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    private ListView theListView;
    private  FoldingCellListAdapter adapter;
    private ArrayList<Item> items;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_menu, null);

        // get our list view
         theListView = view.findViewById(R.id.mainListView);

        bmb = (BoomMenuButton) view.findViewById(R.id.bmb);
        assert bmb != null;

        // prepare elements to display
          items = Item.getTestingList();

        // add custom btn handler to first list item

        for(int i=0;i<4;i++){

            items.get(i).setSubmitBtnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getActivity(), "Your feedback has been recorded", Toast.LENGTH_SHORT).show();
                }
            });
        }

        for(int i=0;i<4;i++){

            items.get(i).setRatingBarClickListener(new RatingBar.OnRatingBarChangeListener() {


                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                     ratingBar= view.findViewById(R.id.content_ratings);
                    float ratings =ratingBar.getRating();
                    Log.d("Ratings"," "+ ratings);
                    Toast.makeText(getActivity(), "Your Ratings "+ " "+ ratings, Toast.LENGTH_SHORT).show();

                }
            });
        }


        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
         adapter = new FoldingCellListAdapter(getContext(), items);

        // add default btn handler for each request btn on each item if custom handler not found
        adapter.setDefaultSubmitBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Your feedback has been recorded", Toast.LENGTH_SHORT).show();
            }
        });



        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state

                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                Log.d("Position ",Integer.toString(pos));
                adapter.registerToggle(pos);
            }
        });



        bmb.setButtonEnum(ButtonEnum.TextInsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_4);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_3);
        bmb.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.Bottom);

        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
            Log.d("Value of i"," " + i);
            bmb.addBuilder(new TextInsideCircleButton.Builder().normalText(" "+weekdays[i])
                    .textGravity(Gravity.CENTER).typeface(Typeface.DEFAULT_BOLD)
                    .textSize(12).rippleEffect(true).rotateImage(true).normalImageRes(R.drawable.dolphin)
            );

            bmb.setOnBoomListener(new OnBoomListener() {
                @Override
                public void onClicked(int index, BoomButton boomButton) {
                    Toast.makeText(getActivity(), "Menu : Day " + index, Toast.LENGTH_SHORT).show();
                    items.clear();
                   if(items.addAll(Item.makeMenu(index)))
                    adapter.notifyDataSetChanged();

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



    @Override
    public void onResume() {
        super.onResume();


    }
}
