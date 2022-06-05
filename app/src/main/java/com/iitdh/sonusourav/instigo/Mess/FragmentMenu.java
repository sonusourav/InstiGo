package com.iitdh.sonusourav.instigo.Mess;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iitdh.sonusourav.instigo.R;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMenu extends Fragment {


    public FragmentMenu() {
        // Required empty public constructor
    }

    private String weekdays[]={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    private  FoldingCellListAdapter adapter;
    private ArrayList<Item> items;
    private float ratings;
    private  View view;
    private Button dayButton;

    private DatabaseReference ratingRef;
    private DatabaseReference dayRef;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, null);
        ListView theListView = view.findViewById(R.id.mainListView);
        dayButton=view.findViewById(R.id.mess_day_button);

        FirebaseDatabase menuInstance = FirebaseDatabase.getInstance();
        DatabaseReference menuRootRef = menuInstance.getReference("Mess");
        DatabaseReference menuRef = menuRootRef.child("Menu").getRef();
        ratingRef= menuRef.child("Ratings").getRef();
        dayRef=ratingRef.child("Day").getRef();


        BoomMenuButton bmb =  view.findViewById(R.id.bmb);
        assert bmb != null;

        // prepare elements to display
        items = Item.getTestingList();
        adapter = new FoldingCellListAdapter(getContext(), items);
        theListView.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                dayRef=ratingRef.child("Sunday").getRef();
                dayButton.setText("Sunday");
                break;

            case Calendar.MONDAY:
                dayRef=ratingRef.child("Monday").getRef();
                dayButton.setText("Monday");
                break;

            case Calendar.TUESDAY:
                dayRef=ratingRef.child("Tuesday").getRef();
                dayButton.setText("Tuesday");
                break;

            case Calendar.WEDNESDAY:
                dayRef=ratingRef.child("Wednesday").getRef();
                dayButton.setText("Wednesday");
                break;

            case Calendar.THURSDAY:
                dayRef=ratingRef.child("Thursday").getRef();
                dayButton.setText("Thursday");
                break;


            case Calendar.FRIDAY:
                dayRef=ratingRef.child("Friday").getRef();
                dayButton.setText("Friday");
                break;


            case Calendar.SATURDAY: {
                dayRef=ratingRef.child("Saturday").getRef();
                dayButton.setText("Saturday");
                break;

            }

            default:break;
        }


        dayRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String BreakfastRatings ;
                String LunchRatings ;
                String SnacksRatings ;
                String DinnerRatings;

                if(dataSnapshot.child("BreakfastRatings").exists()){
                    BreakfastRatings = (dataSnapshot.child("BreakfastRatings").getValue()).toString();
                }else
                    BreakfastRatings="0";

                if(dataSnapshot.child("LunchRatings").exists()){
                    LunchRatings = (dataSnapshot.child("LunchRatings").getValue()).toString();
                }else
                    LunchRatings="0";

                if(dataSnapshot.child("SnacksRatings").exists()){
                    SnacksRatings = (dataSnapshot.child("SnacksRatings").getValue()).toString();
                }else
                    SnacksRatings="0";

                if(dataSnapshot.child("DinnerRatings").exists()){
                    DinnerRatings = (dataSnapshot.child("DinnerRatings").getValue()).toString();
                }else
                    DinnerRatings="0";

                for (int i=0;i<4;i++){
                    Item currentItem =items.get(i);

                    switch (i) {
                        case 0:
                        {
                            currentItem.setMessRatings(BreakfastRatings);

                            break;
                        }
                        case 1:
                        {
                            currentItem.setMessRatings(LunchRatings);
                            break;
                        }  case 2:
                        {
                            currentItem.setMessRatings(SnacksRatings);
                            break;
                        }  case 3:
                        {
                            currentItem.setMessRatings(DinnerRatings);
                            break;
                        }

                        default:break;
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(),"Ratings fetching failed",Toast.LENGTH_SHORT).show();
            }
        });




        // add custom btn handler to first list item

        for(int i=0;i<4;i++){

            final int finalI = i;
            items.get(i).setSubmitBtnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Log.d("Submit Btn OnClick","Reached");

                    switch (items.get(finalI).getDay()){
                        case 0:dayRef=ratingRef.child("Sunday").getRef();
                            break;
                        case 1:dayRef=ratingRef.child("Monday").getRef();
                            break;
                        case 2:dayRef=ratingRef.child("Tuesday").getRef();
                            break;
                        case 3:dayRef=ratingRef.child("Wednesday").getRef();
                            break;
                        case 4:dayRef=ratingRef.child("Thursday").getRef();
                            break;
                        case 5:dayRef=ratingRef.child("Friday").getRef();
                            break;
                        case 6:dayRef=ratingRef.child("Saturday").getRef();
                            break;

                    }

                    dayRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String BreakfastRatings ;
                            String LunchRatings ;
                            String SnacksRatings ;
                            String DinnerRatings;
                            String BreakfastRaters ;
                            String LunchRaters;
                            String SnacksRaters;
                            String DinnerRaters;

                            Log.d("Datasnapshot ratings", "Reached");

                            if(dataSnapshot.child("BreakfastRatings").exists()){
                                BreakfastRatings = (dataSnapshot.child("BreakfastRatings").getValue()).toString();
                            }else
                                BreakfastRatings="0";

                            if(dataSnapshot.child("LunchRatings").exists()){
                                LunchRatings = (dataSnapshot.child("LunchRatings").getValue()).toString();
                            }else
                                LunchRatings="0";

                            if(dataSnapshot.child("SnacksRatings").exists()){
                                SnacksRatings = (dataSnapshot.child("SnacksRatings").getValue()).toString();
                            }else
                                SnacksRatings="0";

                            if(dataSnapshot.child("DinnerRatings").exists()){
                                DinnerRatings = (dataSnapshot.child("DinnerRatings").getValue()).toString();
                            }else
                                DinnerRatings="0";

                            if(dataSnapshot.child("BreakfastRaters").exists()){
                                BreakfastRaters = (dataSnapshot.child("BreakfastRaters").getValue()).toString();
                            }else
                                BreakfastRaters="0";

                            if(dataSnapshot.child("LunchRaters").exists()){
                                LunchRaters = (dataSnapshot.child("LunchRaters").getValue()).toString();
                            }else
                                LunchRaters="0";

                            if(dataSnapshot.child("SnacksRaters").exists()){
                                SnacksRaters = (dataSnapshot.child("SnacksRaters").getValue()).toString();
                            }else
                                SnacksRaters="0";

                            if(dataSnapshot.child("DinnerRaters").exists()){
                                DinnerRaters = (dataSnapshot.child("DinnerRaters").getValue()).toString();
                            }else
                                DinnerRaters="0";


                            Float newRaters;
                            Float product;
                            Float newRatings;
                            int newrater;
                            switch (finalI){
                                case 0:
                                    Log.d("update ratings", "Reached");

                                    newRaters = Float.parseFloat(BreakfastRaters)+1;
                                    product=Float.parseFloat(BreakfastRaters)*Float.parseFloat(BreakfastRatings);
                                    newRatings = (product+ratings)/newRaters;
                                    newrater=Math.round(newRaters);
                                    dayRef.child("BreakfastRaters").setValue(Integer.toString(newrater));
                                    dayRef.child("BreakfastRatings").setValue(Float.toString(newRatings));
                                    items.get(finalI).setMessRatings(Float.toString(newRatings));
                                    adapter.registerToggle(finalI);
                                    break;
                                case 1:
                                    Log.d("update ratings", "Reached");

                                    newRaters = Float.parseFloat(LunchRaters)+1;
                                    product=Float.parseFloat(LunchRaters)*Float.parseFloat(LunchRatings);
                                    newRatings = (product+ratings)/newRaters;
                                    newrater=Math.round(newRaters);
                                    dayRef.child("LunchRaters").setValue(Integer.toString(newrater));
                                    dayRef.child("LunchRatings").setValue(Float.toString(newRatings));
                                    items.get(finalI).setMessRatings(Float.toString(newRatings));
                                    adapter.registerToggle(finalI);
                                    break;
                                case 2:
                                    Log.d("update ratings", "Reached");

                                    newRaters = Float.parseFloat(SnacksRaters)+1;
                                    product=Float.parseFloat(SnacksRaters)*Float.parseFloat(SnacksRatings);
                                    newRatings = (product+ratings)/newRaters;
                                    newrater=Math.round(newRaters);
                                    dayRef.child("SnacksRaters").setValue(Integer.toString(newrater));
                                    dayRef.child("SnacksRatings").setValue(Float.toString(newRatings));
                                    items.get(finalI).setMessRatings(Float.toString(newRatings));
                                    adapter.registerToggle(finalI);
                                    break;
                                case 3:
                                    Log.d("update ratings", "Reached");

                                    newRaters = Float.parseFloat(DinnerRaters)+1;
                                    product=Float.parseFloat(DinnerRaters)*Float.parseFloat(DinnerRatings);
                                    newRatings = (product+ratings)/newRaters;
                                    newrater=Math.round(newRaters);
                                    dayRef.child("DinnerRaters").setValue(Integer.toString(newrater));
                                    dayRef.child("DinnerRatings").setValue(Float.toString(newRatings));
                                    items.get(finalI).setMessRatings(Float.toString(newRatings));
                                    adapter.registerToggle(finalI);
                                    break;

                            }
                            Toast.makeText(getActivity(), "Your feedback has been recorded", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(), "Failed to record your feedback.\n Please try again.", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            });
        }

        for(int i=0;i<4;i++){

            items.get(i).setRatingBarClickListener(new RatingBar.OnRatingBarChangeListener() {


                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                    ratingBar= view.findViewById(R.id.content_ratings);
                    ratings =ratingBar.getRating();
                    Log.d("Ratings"," "+ ratings);

                }
            });
        }


        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)


        // add default btn handler for each request btn on each item if custom handler not found
        adapter.setDefaultSubmitBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Your feedback has been recorded", Toast.LENGTH_SHORT).show();
            }
        });



        // set elements to adapter

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

                    adapter.removeAll();


                    Toast.makeText(getActivity(), "Menu : Day " + index, Toast.LENGTH_SHORT).show();

                    items.clear();
                    if(items.addAll(Item.makeMenu(index)))
                        adapter.notifyDataSetChanged();

                    switch (index){
                        case 0:dayRef=ratingRef.child("Sunday").getRef();
                            dayButton.setText("Sunday");
                            break;
                        case 1:dayRef=ratingRef.child("Monday").getRef();
                            dayButton.setText("Monday");
                            break;
                        case 2:dayRef=ratingRef.child("Tuesday").getRef();
                            dayButton.setText("Tuesday");
                            break;
                        case 3:dayRef=ratingRef.child("Wednesday").getRef();
                            dayButton.setText("Wednesday");
                            break;
                        case 4:dayRef=ratingRef.child("Thursday").getRef();
                            dayButton.setText("Thursday");
                            break;
                        case 5:dayRef=ratingRef.child("Friday").getRef();
                            dayButton.setText("Friday");
                            break;
                        case 6:dayRef=ratingRef.child("Saturday").getRef();
                            dayButton.setText("Saturday");
                            break;
                        default:dayRef=ratingRef.child("Sunday").getRef();

                    }
                    dayRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange( DataSnapshot dataSnapshot) {



                            String BreakfastRatings ;
                            String LunchRatings ;
                            String SnacksRatings ;
                            String DinnerRatings;


                            Log.d("Datasnapshot ratings", "Reached");

                            if(dataSnapshot.child("BreakfastRatings").exists()){
                                BreakfastRatings = (dataSnapshot.child("BreakfastRatings").getValue()).toString();
                            }else
                                BreakfastRatings="0";

                            if(dataSnapshot.child("LunchRatings").exists()){
                                LunchRatings = (dataSnapshot.child("LunchRatings").getValue()).toString();
                            }else
                                LunchRatings="0";

                            if(dataSnapshot.child("SnacksRatings").exists()){
                                SnacksRatings = (dataSnapshot.child("SnacksRatings").getValue()).toString();
                            }else
                                SnacksRatings="0";

                            if(dataSnapshot.child("DinnerRatings").exists()){
                                DinnerRatings = (dataSnapshot.child("DinnerRatings").getValue()).toString();
                            }else
                                DinnerRatings="0";




                            for (int i=0;i<4;i++){
                                Item item =items.get(i);

                                switch (i) {
                                    case 0:
                                    {
                                        Log.d("Sunday Breakfast Rating",BreakfastRatings);
                                        item.setMessRatings(BreakfastRatings);
                                        adapter.notifyDataSetChanged();
                                        Log.d("Sunday Breakfast Rating",item.getMessRatings());


                                        break;
                                    }
                                    case 1:
                                    {
                                        Log.d("Sunday Lunch Rating",LunchRatings);
                                        item.setMessRatings(LunchRatings);
                                        adapter.notifyDataSetChanged();

                                        break;
                                    }  case 2:
                                    {
                                        Log.d("Sunday Snacks Rating",SnacksRatings);
                                        item.setMessRatings(SnacksRatings);
                                        adapter.notifyDataSetChanged();

                                        break;
                                    }  case 3:
                                    {
                                        Log.d("Sunday Dinner Rating",DinnerRatings);
                                        item.setMessRatings(DinnerRatings);
                                        adapter.notifyDataSetChanged();

                                        break;
                                    }

                                    default:break;
                                }
                            }




                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(getActivity(),"Ratings fetching failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                    adapter.notifyDataSetChanged();



                    for(int i=0;i<4;i++){

                        items.get(i).setRatingBarClickListener(new RatingBar.OnRatingBarChangeListener() {


                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                                ratingBar= view.findViewById(R.id.content_ratings);
                                ratings =ratingBar.getRating();
                                Log.d("Ratings"," "+ ratings);


                            }
                        });
                    }




                    for(int i=0;i<4;i++){

                        final int finalI = i;
                        items.get(i).setSubmitBtnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Log.d("Submit Btn OnClick","Reached");

                                switch (items.get(finalI).getDay()){
                                    case 0:dayRef=ratingRef.child("Sunday").getRef();
                                        break;
                                    case 1:dayRef=ratingRef.child("Monday").getRef();
                                        break;
                                    case 2:dayRef=ratingRef.child("Tuesday").getRef();
                                        break;
                                    case 3:dayRef=ratingRef.child("Wednesday").getRef();
                                        break;
                                    case 4:dayRef=ratingRef.child("Thursday").getRef();
                                        break;
                                    case 5:dayRef=ratingRef.child("Friday").getRef();
                                        break;
                                    case 6:dayRef=ratingRef.child("Saturday").getRef();
                                        break;

                                }

                                dayRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        String BreakfastRatings ;
                                        String LunchRatings ;
                                        String SnacksRatings ;
                                        String DinnerRatings;
                                        String BreakfastRaters ;
                                        String LunchRaters;
                                        String SnacksRaters;
                                        String DinnerRaters;

                                        Log.d("Datasnapshot ratings", "Reached");

                                        if(dataSnapshot.child("BreakfastRatings").exists()){
                                            BreakfastRatings = (dataSnapshot.child("BreakfastRatings").getValue()).toString();
                                        }else
                                            BreakfastRatings="0";

                                        if(dataSnapshot.child("LunchRatings").exists()){
                                            LunchRatings = (dataSnapshot.child("LunchRatings").getValue()).toString();
                                        }else
                                            LunchRatings="0";

                                        if(dataSnapshot.child("SnacksRatings").exists()){
                                            SnacksRatings = (dataSnapshot.child("SnacksRatings").getValue()).toString();
                                        }else
                                            SnacksRatings="0";

                                        if(dataSnapshot.child("DinnerRatings").exists()){
                                            DinnerRatings = (dataSnapshot.child("DinnerRatings").getValue()).toString();
                                        }else
                                            DinnerRatings="0";

                                        if(dataSnapshot.child("BreakfastRaters").exists()){
                                            BreakfastRaters = (dataSnapshot.child("BreakfastRaters").getValue()).toString();
                                        }else
                                            BreakfastRaters="0";

                                        if(dataSnapshot.child("LunchRaters").exists()){
                                            LunchRaters = (dataSnapshot.child("LunchRaters").getValue()).toString();
                                        }else
                                            LunchRaters="0";

                                        if(dataSnapshot.child("SnacksRaters").exists()){
                                            SnacksRaters = (dataSnapshot.child("SnacksRaters").getValue()).toString();
                                        }else
                                            SnacksRaters="0";

                                        if(dataSnapshot.child("DinnerRaters").exists()){
                                            DinnerRaters = (dataSnapshot.child("DinnerRaters").getValue()).toString();
                                        }else
                                            DinnerRaters="0";


                                        Float newRaters;
                                        Float product;
                                        Float newRatings;
                                        int newrater;

                                        switch (finalI){
                                            case 0:
                                                Log.d("update ratings", "Reached");

                                                newRaters = Float.parseFloat(BreakfastRaters)+1;
                                                product=Float.parseFloat(BreakfastRaters)*Float.parseFloat(BreakfastRatings);
                                                newRatings = (product+ratings)/newRaters;
                                                newrater=Math.round(newRaters);
                                                dayRef.child("BreakfastRaters").setValue(Integer.toString(newrater));
                                                dayRef.child("BreakfastRatings").setValue(Float.toString(newRatings));
                                                items.get(finalI).setMessRatings(Float.toString(newRatings));
                                                adapter.registerToggle(finalI);
                                                break;
                                            case 1:
                                                Log.d("update ratings", "Reached");

                                                newRaters = Float.parseFloat(LunchRaters)+1;
                                                product=Float.parseFloat(LunchRaters)*Float.parseFloat(LunchRatings);
                                                newRatings = (product+ratings)/newRaters;
                                                newrater=Math.round(newRaters);
                                                dayRef.child("LunchRaters").setValue(Integer.toString(newrater));
                                                dayRef.child("LunchRatings").setValue(Float.toString(newRatings));
                                                items.get(finalI).setMessRatings(Float.toString(newRatings));
                                                adapter.registerToggle(finalI);
                                                break;
                                            case 2:
                                                Log.d("update ratings", "Reached");

                                                newRaters = Float.parseFloat(SnacksRaters)+1;
                                                product=Float.parseFloat(SnacksRaters)*Float.parseFloat(SnacksRatings);
                                                newRatings = (product+ratings)/newRaters;
                                                newrater=Math.round(newRaters);
                                                dayRef.child("SnacksRaters").setValue(Integer.toString(newrater));
                                                dayRef.child("SnacksRatings").setValue(Float.toString(newRatings));
                                                items.get(finalI).setMessRatings(Float.toString(newRatings));
                                                adapter.registerToggle(finalI);
                                                break;
                                            case 3:
                                                Log.d("update ratings", "Reached");

                                                newRaters = Float.parseFloat(DinnerRaters)+1;
                                                product=Float.parseFloat(DinnerRaters)*Float.parseFloat(DinnerRatings);
                                                newRatings = (product+ratings)/newRaters;
                                                newrater=Math.round(newRaters);
                                                dayRef.child("DinnerRaters").setValue(Integer.toString(newrater));
                                                dayRef.child("DinnerRatings").setValue(Float.toString(newRatings));
                                                items.get(finalI).setMessRatings(Float.toString(newRatings));
                                                adapter.registerToggle(finalI);
                                                break;

                                        }
                                        Toast.makeText(getActivity(), "Your feedback has been recorded", Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(getActivity(), "Failed to record your feedback.\n Please try again.", Toast.LENGTH_SHORT).show();

                                    }
                                });


                            }
                        });
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


        adapter.notifyDataSetChanged();





        return view;

    }



    @Override
    public void onResume() {
        super.onResume();
    }



}

