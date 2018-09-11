package com.iitdh.sonusourav.instigo.Mess;


import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iitdh.sonusourav.instigo.R;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }
    private BoomMenuButton bmb;
    private String weekdays[]={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, null);



        bmb = (BoomMenuButton) view.findViewById(R.id.bmb);
        assert bmb != null;
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

        }
        return view;

    }





}
