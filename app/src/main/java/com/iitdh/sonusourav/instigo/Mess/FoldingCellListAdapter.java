package com.iitdh.sonusourav.instigo.Mess;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.AppSingleton;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.VolleyErrorInstances;
import com.ramotion.foldingcell.FoldingCell;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

public class FoldingCellListAdapter extends ArrayAdapter<MealClass> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
  private FragmentMenu fragmentMenu;

  FoldingCellListAdapter(FragmentMenu fragmentMenu, ArrayList<MealClass> objects) {
    super(Objects.requireNonNull(fragmentMenu.getActivity()), 0, objects);
    this.fragmentMenu = fragmentMenu;
    }


    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {

      final MealClass item = getItem(position);
        FoldingCell cell = (FoldingCell) convertView;
      final ViewHolder viewHolder;
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
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
            return cell;

      viewHolder.part.setText(getMealType(position, 0));
      viewHolder.time.setText(item.getTime());
      viewHolder.items.setText(item.getItem());
      viewHolder.contentItems.setText(item.getItem());

       /* if (item.getRatings() == 0 ) {
            viewHolder.textRatings.setVisibility(View.VISIBLE);
            viewHolder.titleRatingBar.setVisibility(View.GONE);
            viewHolder.textRatings.setText(
                Objects.requireNonNull(fragmentMenu.getActivity()).getResources().getString(R.string.rating_unavailable));
        } else {
            viewHolder.textRatings.setVisibility(View.GONE);
            viewHolder.titleRatingBar.setVisibility(View.VISIBLE);
            viewHolder.titleRatingBar.setRating(item.getRatings());
        }*/

      viewHolder.textRatings.setVisibility(View.GONE);
      viewHolder.titleRatingBar.setVisibility(View.VISIBLE);
      viewHolder.titleRatingBar.setRating(item.getRatings());
      viewHolder.contentPart.setText(getMealType(position, 1));
      viewHolder.contentTime.setText(item.getTime());

      viewHolder.contentRatingBar.setOnRatingBarChangeListener(
          new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
              rating = viewHolder.contentRatingBar.getRating();
              Log.d("MessActivity", rating + "");
            }
          });

      final FoldingCell finalCell = cell;
      viewHolder.submitButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          finalCell.callOnClick();
          postRatings(getRatingUrl(fragmentMenu.dayNo, position),
              viewHolder.contentRatingBar.getRating(), viewHolder.titleRatingBar);
        }
      });


        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FoldingCell) view).toggle(false);
                registerToggle(position);
            }
        });
        return cell;
    }

  private String getRatingUrl(int day, int position) {
    String ratingUrl;
    switch (position) {
      case 0:
        ratingUrl = Constants.baseUrl + "mess/ratings/" + day + "/breakfast";
        break;
      case 1:
        ratingUrl = Constants.baseUrl + "mess/ratings/" + day + "/lunch";
        break;
      case 2:
        ratingUrl = Constants.baseUrl + "mess/ratings/" + day + "/snacks";
        break;
      case 3:
        ratingUrl = Constants.baseUrl + "mess/ratings/" + day + "/dinner";
        break;
      default:
        ratingUrl = Constants.baseUrl + "mess/ratings/" + day + "/dinner";
        break;
    }
    return ratingUrl;
  }

  private String getMealType(int pos, int type) {
    String mealType;

    if (type == 0) {
      switch (pos) {
        case 0:
          mealType = "B\nR\nE\nA\nK\nF\nA\nS\nT";
          break;
        case 1:
          mealType = "L\nU\nN\nC\nH";
          break;
        case 2:
          mealType = "S\nN\nA\nC\nK\nS";
          break;
        case 3:
          mealType = "D\nI\nN\nN\nE\nR";
          break;
        default:
          mealType = "M\nE\nS\nS";
          break;
      }
    } else {
      switch (pos) {
        case 0:
          mealType = "BREAKFAST";
          break;
        case 1:
          mealType = "LUNCH";
          break;
        case 2:
          mealType = "SNACKS";
          break;
        case 3:
          mealType = "DINNER";
          break;
        default:
          mealType = "MESS";
          break;
      }
    }
    return mealType;
  }

    // simple methods for register cell state changes
    void registerToggle(int position) {

        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    private void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

  void removeAll() {
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

  private void postRatings(String ratingUrl, float ratings, final RatingBar ratingBar) {

    Log.d("MessActivity", ratingUrl);
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("ratings", ratings);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, ratingUrl, jsonObject,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {

            try {
              if (response.getString("message").equals("success")) {
                Log.d("MessActivity", "postRatings");
                float newRating = (float) response.getDouble("ratings");
                Log.d("ratings", newRating + "");
                ratingBar.setRating(newRating);
                Toast.makeText(fragmentMenu.getActivity(), "Your feedback has been recorded",
                    Toast.LENGTH_SHORT).show();
              } else if (response.getString("message").equals("failure")) {
                Log.d("MessActivity", "failedPostRatings");
              } else {
                Log.d("MessActivity", "else");
              }
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        VolleyLog.d("MessActivity", "Error: " + error.getMessage());
        new VolleyErrorInstances().getErrorType(fragmentMenu.getActivity(), error);
      }
    });

    AppSingleton.getInstance().addToRequestQueue(req);
  }

}
