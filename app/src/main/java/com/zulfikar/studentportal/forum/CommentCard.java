package com.zulfikar.studentportal.forum;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.R;

public class CommentCard {
    public final int authorId, postId, commentId;
    public final String authorName, authorPhoto, commentContent, commentDate;

    public CommentCard(int commentId, int authorId, int postId, String authorName,
                       String authorPhoto,
                       String commentContent, String commentDate) {
        this.postId = postId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorPhoto = authorPhoto;
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
    }

    public View getView(Context context, ViewGroup parent, Handler handler) {
        View commentCard = LayoutInflater.from(context).inflate(R.layout.card_comment, parent, false);
        if (handler == null) handler = new Handler();

        ShapeableImageView imgCCUserPhoto;
        TextView txtCCUserName, txtCCCommentDate, txtCCContent;
        ImageView btnCCReactionIcon, btnCCCommentIcon;

        imgCCUserPhoto = commentCard.findViewById(R.id.imgCCUserPhoto);
        txtCCUserName = commentCard.findViewById(R.id.txtCCUserName);
        txtCCCommentDate = commentCard.findViewById(R.id.txtCCCommentDate);
        txtCCContent = commentCard.findViewById(R.id.txtCCContent);
        btnCCReactionIcon = commentCard.findViewById(R.id.btnCCReactionIcon);
        btnCCCommentIcon = commentCard.findViewById(R.id.btnCCCommentIcon);

        new FetchImage(imgCCUserPhoto, authorPhoto, handler);
        txtCCUserName.setText(authorName);
        txtCCCommentDate.setText(commentDate);
        txtCCContent.setText(commentContent);

        return commentCard;
    }
}
