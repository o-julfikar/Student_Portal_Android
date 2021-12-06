package com.zulfikar.studentportal.forum;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.R;

public class CommentCardAdapter extends RecyclerView.Adapter<CommentCardAdapter.CommentCardViewHolder> {

    Context context;
    CommentCard[] commentCards;;
    protected Handler commentCardHandler;
    protected Notification notification;
    String TAG = "CommentCardAdapter";

    public CommentCardAdapter(Context context, CommentCard[] commentCards) {
        this.context = context;
        this.commentCards = commentCards;
        this.commentCardHandler = new Handler();
    }

    @NonNull
    @Override
    public CommentCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater commentCardInflater = LayoutInflater.from(context);
        View commentCard = commentCardInflater.inflate(R.layout.card_comment, parent, false);
        return new CommentCardViewHolder(commentCard);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentCardViewHolder holder, int position) {
        new FetchImage(holder.imgCCUserPhoto, commentCards[position].authorPhoto, commentCardHandler);
        holder.txtCCUserName.setText(commentCards[position].authorName);
        holder.txtCCCommentDate.setText(commentCards[position].commentDate);
        holder.txtCCContent.setText(commentCards[position].commentContent);
    }

    @Override
    public int getItemCount() {
        return commentCards.length;
    }

    public static class CommentCardViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView imgCCUserPhoto;
        TextView txtCCUserName, txtCCCommentDate, txtCCContent;
        ImageView btnCCReactionIcon, btnCCCommentIcon;

        public CommentCardViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCCUserPhoto = itemView.findViewById(R.id.imgCCUserPhoto);
            txtCCUserName = itemView.findViewById(R.id.txtCCUserName);
            txtCCCommentDate = itemView.findViewById(R.id.txtCCCommentDate);
            txtCCContent = itemView.findViewById(R.id.txtCCContent);
            btnCCReactionIcon = itemView.findViewById(R.id.btnCCReactionIcon);
            btnCCCommentIcon = itemView.findViewById(R.id.btnCCCommentIcon);
        }
    }
}
