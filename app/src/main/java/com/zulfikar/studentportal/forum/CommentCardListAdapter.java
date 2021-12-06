package com.zulfikar.studentportal.forum;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.R;


public class CommentCardListAdapter extends ArrayAdapter<CommentCard> {

    Handler commentCardListHandler;

    public CommentCardListAdapter(@NonNull Context context, CommentCard[] commentCards) {
        super(context, R.layout.card_comment, commentCards);

        commentCardListHandler = new Handler();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CommentCard commentCard = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_comment, parent, false);
        }

        ShapeableImageView imgCCUserPhoto;
        TextView txtCCUserName, txtCCCommentDate, txtCCContent;
        ImageView btnCCReactionIcon, btnCCCommentIcon;

        imgCCUserPhoto = convertView.findViewById(R.id.imgCCUserPhoto);
        txtCCUserName = convertView.findViewById(R.id.txtCCUserName);
        txtCCCommentDate = convertView.findViewById(R.id.txtCCCommentDate);
        txtCCContent = convertView.findViewById(R.id.txtCCContent);
        btnCCReactionIcon = convertView.findViewById(R.id.btnCCReactionIcon);
        btnCCCommentIcon = convertView.findViewById(R.id.btnCCCommentIcon);

        new FetchImage(imgCCUserPhoto, commentCard.authorPhoto, commentCardListHandler);
        txtCCUserName.setText(commentCard.authorName);
        txtCCCommentDate.setText(commentCard.commentDate);
        txtCCContent.setText(commentCard.commentContent);

        return convertView;
    }

}
