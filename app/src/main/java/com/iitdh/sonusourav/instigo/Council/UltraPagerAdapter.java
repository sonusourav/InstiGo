/*
 *
 *  MIT License
 *
 *  Copyright (c) 2017 Alibaba Group
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package com.iitdh.sonusourav.instigo.Council;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iitdh.sonusourav.instigo.R;

import java.util.ArrayList;


public class UltraPagerAdapter extends PagerAdapter {
    private boolean isMultiScr;
    private Context context;
    private ArrayList<CouncilUserClass> councilList;
    private static final int RESULT_PICK_CONTACT = 1;

    public UltraPagerAdapter(boolean isMultiScr) {
        this.isMultiScr = isMultiScr;
    }


    UltraPagerAdapter(boolean isMultiScr, Context context) {
        this.isMultiScr = isMultiScr;
        this.context=context;

    }
    UltraPagerAdapter(boolean isMultiScr, Context context,ArrayList<CouncilUserClass> councilList) {
        this.isMultiScr = isMultiScr;
        this.context=context;
        this.councilList=councilList;

    }

    @Override
    public int getCount() {
        return councilList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }



    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.ultra_view_pager, null);

        final ArrayList<CouncilUserClass> wardenList;

        wardenList=this.councilList;



        //new Relative Layout(container.getContext());
        TextView wardenDesgn=relativeLayout.findViewById(R.id.council_warden_desgn);
        TextView wardenName=relativeLayout.findViewById(R.id.council_warden_name);
        ImageButton wardenPhone=relativeLayout.findViewById(R.id.council_warden_phone);
        ImageButton wardenEmail=relativeLayout.findViewById(R.id.council_warden_email);
        ImageView wardenPic=relativeLayout.findViewById(R.id.council_warden_pic);

        wardenDesgn.setText(wardenList.get(position).getDesgn());
        wardenName.setText(wardenList.get(position).getName());

        wardenPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(wardenList.get(position).getPhoneNo().isEmpty()){
                    Toast.makeText(context,"Phone no. is not available .",Toast.LENGTH_SHORT).show();

                }else{
                    sendToPhoneDial(position);

                    Log.d("onClick:sendToPhoneDial", "Reached");
                }

            }
        });
        wardenEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wardenList.get(position).getEmail().isEmpty()){
                    Toast.makeText(context,"Email  is not available",Toast.LENGTH_SHORT).show();

                }else{
                    sendEmail(position);
                }
            }
        });

        relativeLayout.setId(R.id.item_id);
        wardenPic.setBackgroundResource(wardenList.get(position).getPicId());

        container.addView(relativeLayout);

        return relativeLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }

    public void sendEmail(int pos){

        String email=councilList.get(pos).getEmail().trim();
        Log.i("send email", "send email to "+ email);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void sendToPhoneDial(int pos) {
        String phone = councilList.get(pos).getPhoneNo().trim();
        Log.i("Make call", "Make call >>>>>>" + phone);

        Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
        phoneIntent.setData(Uri.parse("tel:" + phone));
        try {
            context.startActivity(phoneIntent);
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }


            Log.i("Finished making a call", "");
        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(context, "Call failed, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }



}
