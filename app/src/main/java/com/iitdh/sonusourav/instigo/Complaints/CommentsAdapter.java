package com.iitdh.sonusourav.instigo.Complaints;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.iitdh.sonusourav.instigo.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import org.w3c.dom.Comment;

public class CommentsAdapter extends BaseAdapter {


    private Context mcontext;
    private ArrayList<CommentClass> commentList;

     CommentsAdapter(Context context, ArrayList<CommentClass> arrayList) {
        this.mcontext = context;
        this.commentList = arrayList;
    }

    public int getCount() {
        return commentList.size();
    }

    public Object getItem(int pos) {
        return commentList.get(pos);
    }


    public long getItemId(int i) {
        return i;
    }


    public View getView(int i, View view, ViewGroup viewGroup) {


        if (view == null) {
            view = LayoutInflater.from(mcontext).inflate(R.layout.item_comment, viewGroup, false);
        }

        CommentClass comment = commentList.get(i);

        TextView commentUsername=view.findViewById(R.id.comment_username);
        TextView commentEmail=view.findViewById(R.id.comment_email);
        TextView commentDate=view.findViewById(R.id.comment_date);
        TextView comments=view.findViewById(R.id.comment_info);
        CircleImageView commentPic=view.findViewById(R.id.comment_image);

        if(comment.getPicUrl()!=null){
            Glide.with(mcontext)
                .load(comment.getPicUrl())
                .into(commentPic);
        }

        commentUsername.setText(comment.getCommentBy());
        commentEmail.setText(comment.getCommentEmail());
        commentDate.setText(comment.getCommentDate());
        comments.setText(comment.getComment());
        return view;
    }
}
