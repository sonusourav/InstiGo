package com.iitdh.sonusourav.instigo.Mess;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.iitdh.sonusourav.instigo.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;


public class FoldingCellListAdapter extends ArrayAdapter<Item> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultSubmitBtnClickListener;
    private RatingBar.OnRatingBarChangeListener defaultRatingBarListener;


    public FoldingCellListAdapter(Context context, List<Item> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        Item item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {


            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);


            // binding view parts to view holder
            viewHolder.part = cell.findViewById(R.id.mess_part);
            viewHolder.time = cell.findViewById(R.id.title_day_label);
            viewHolder.items = cell.findViewById(R.id.mess_items);
            viewHolder.contentItems=cell.findViewById(R.id.content_items_names);
            viewHolder.textRatings = cell.findViewById(R.id.textView_ratings);
            viewHolder.contentPart=cell.findViewById(R.id.content_mess_part);
            viewHolder.contentTime=cell.findViewById(R.id.content_mess_time);
            viewHolder.submitButton = cell.findViewById(R.id.submit_btn);
            viewHolder.titleRatingBar =cell.findViewById(R.id.title_rating);
            viewHolder.contentRatingBar =cell.findViewById(R.id.content_ratings);
            cell.setTag(viewHolder);

        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
            return cell;

        // bind data from selected element to view through view holder

        String contentMessPart=item.getMessPart();
        contentMessPart = contentMessPart.replaceAll("\n","");
        viewHolder.part.setText(item.getMessPart());
        viewHolder.time.setText(item.getMessTime());
        viewHolder.items.setText(item.getMessItems());
        viewHolder.contentItems.setText(item.getMessItems());

        if (item.getMessRatings() == null || Float.parseFloat(item.getMessRatings())==0) {
            viewHolder.textRatings.setVisibility(View.VISIBLE);
            viewHolder.titleRatingBar.setVisibility(View.GONE);
            viewHolder.textRatings.setText("Ratings Unavailable");
        } else {
            viewHolder.textRatings.setVisibility(View.GONE);
            viewHolder.titleRatingBar.setVisibility(View.VISIBLE);
            viewHolder.titleRatingBar.setRating((Float.parseFloat(item.getMessRatings())));
        }
        viewHolder.contentPart.setText(contentMessPart);
        viewHolder.contentTime.setText(item.getMessTime());

        if(item.getRatingBarClickListener() !=null){
            viewHolder.contentRatingBar.setOnRatingBarChangeListener(item.getRatingBarClickListener());

        }else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.contentRatingBar.setOnRatingBarChangeListener(defaultRatingBarListener);
        }

        // set custom btn handler for list item from that item
        if (item.getSubmitBtnClickListener() != null) {
            viewHolder.submitButton.setOnClickListener(item.getSubmitBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.submitButton.setOnClickListener(defaultSubmitBtnClickListener);
        }


        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                Log.d("Position ",Integer.toString(position));
                registerToggle(position);
            }


        });




        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    private void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }


    public void removeAll(){
        if(unfoldedIndexes.contains(0)){
            registerFold(0);
        }
        if(unfoldedIndexes.contains(1)){
            registerFold(1);

        } if(unfoldedIndexes.contains(2)){
            registerFold(2);

        } if(unfoldedIndexes.contains(3)){
            registerFold(3);
        }
    }

    private void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultSubmitBtnClickListener() {
        return defaultSubmitBtnClickListener;
    }

    public void setDefaultSubmitBtnClickListener(View.OnClickListener defaultSubmitBtnClickListener) {
        this.defaultSubmitBtnClickListener = defaultSubmitBtnClickListener;
    }

    public RatingBar.OnRatingBarChangeListener getDefaultRatingBarListener() {
        return defaultRatingBarListener;
    }

    public void setDefaultRatingBarListener(RatingBar.OnRatingBarChangeListener defaultRatingBarListener) {
        this.defaultRatingBarListener = defaultRatingBarListener;
    }

    // View lookup cache
    private static class ViewHolder {

        TextView part;
        TextView time;
        TextView items;
        TextView textRatings;
        RatingBar titleRatingBar;
        TextView contentItems;
        RatingBar contentRatingBar;
        TextView contentPart;
        TextView contentTime;
        Button submitButton;
    }
}
