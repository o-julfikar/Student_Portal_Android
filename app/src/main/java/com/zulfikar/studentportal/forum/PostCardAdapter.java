package com.zulfikar.studentportal.forum;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.R;

public class PostCardAdapter extends RecyclerView.Adapter<PostCardAdapter.PostCardViewHolder> {

    Context context;
    PostCard[] postCards;
    protected Handler postCardHandler;
    protected Notification notification;
    String TAG = "PostCardAdapter";
    public PostCardAdapter(Context context, PostCard[] postCards) {
        this.context = context;
        this.postCards = postCards;
        postCardHandler = new Handler();
    }

    @NonNull
    @Override
    public PostCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater postCardInflater = LayoutInflater.from(context);
        View postCard = postCardInflater.inflate(R.layout.card_post, parent, false);
        return new PostCardViewHolder(postCard);
    }

    @Override
    public void onBindViewHolder(@NonNull PostCardViewHolder holder, int position) {
        new FetchImage(holder.imgPCUserPhoto, postCards[position].authorPhoto, postCardHandler).start();
        holder.txtPCUserName.setText(postCards[position].authorName);
        holder.txtPCPostCourse.setText(postCards[position].courseCode);
        holder.txtPCPostSemester.setText(postCards[position].semesterNameYear);
        holder.txtPCDate.setText(postCards[position].postDate);
        holder.txtPCContent.setText(postCards[position].postContent);
        holder.txtPCReactionCount.setText(postCards[position].reactionsCount);
        holder.txtPCCommentCount.setText(postCards[position].commentsCount);

        for (int i = 0; i < 2 && i < postCards[position].commentCards.length; i++) {
            holder.linearLayoutCommentCards.addView(postCards[position].commentCards[i].getView(context, holder.linearLayoutCommentCards, postCardHandler));
        }
    }

    @Override
    public int getItemCount() {
        return postCards.length;
    }

    protected static class PostCardViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView imgPCUserPhoto;
        ImageView imgPCMore, imgPCReactionIcon, imgPCCommentIcon;
        TextView txtPCUserName, txtPCPostCourse, txtPCPostSemester, txtPCDate, txtPCContent,
                txtPCReactionCount, txtPCCommentCount;
        EditText txtPCNewComment;
        Button btnPCSubmitComment;
        LinearLayout linearLayoutCommentCards;
//        ListView listViewCommentCards;
//        RecyclerView recyclerViewCommentCards;

        public PostCardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPCUserPhoto = itemView.findViewById(R.id.imgPCUserPhoto);
            imgPCMore = itemView.findViewById(R.id.imgPCMore);
            imgPCReactionIcon = itemView.findViewById(R.id.imgPCReactionIcon);
            imgPCCommentIcon = itemView.findViewById(R.id.imgPCCommentIcon);
            txtPCUserName = itemView.findViewById(R.id.txtPCUserName);
            txtPCPostCourse = itemView.findViewById(R.id.txtPCPostCourse);
            txtPCPostSemester = itemView.findViewById(R.id.txtPCPostSemester);
            txtPCDate = itemView.findViewById(R.id.txtPCDate);
            txtPCContent = itemView.findViewById(R.id.txtPCContent);
            txtPCReactionCount = itemView.findViewById(R.id.txtPCReactionCount);
            txtPCCommentCount = itemView.findViewById(R.id.txtPCCommentCount);
            txtPCNewComment = itemView.findViewById(R.id.txtPCNewComment);
            btnPCSubmitComment = itemView.findViewById(R.id.btnPCSubmitComment);
            linearLayoutCommentCards = itemView.findViewById(R.id.linearLayoutCommentCards);
//            listViewCommentCards = itemView.findViewById(R.id.listViewCommentCards);
//            recyclerViewCommentCards = itemView.findViewById(R.id.recyclerViewCommentCards);
        }

    }
}
