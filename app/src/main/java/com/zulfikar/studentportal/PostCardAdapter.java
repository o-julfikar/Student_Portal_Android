package com.zulfikar.studentportal;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.InputStream;
import java.net.URL;

public class PostCardAdapter extends RecyclerView.Adapter<PostCardAdapter.PostCardViewHolder> {

    Context context;
    private final int authorId, postId, courseId, semesterId;
    private final String authorName, authorPhoto, courseCode, semesterNameYear, postContent, postDate,
            commentsCount, reactionsCount;
    private final CommentCard[] commentCards;
    protected Handler postCardHandler;
    protected Notification notification;

    PostCardAdapter(Context context, int postId, int authorId, int courseId, int semesterId,
                    String authorName, String authorPhoto, String courseCode,
                    String semesterNameYear, String postContent, String postDate,
                    String reactionsCount, String commentsCount, CommentCard[] commentCards) {
        this.context = context;
        this.postId = postId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorPhoto = authorPhoto;
        this.courseCode = courseCode;
        this.courseId = courseId;
        this.semesterId = semesterId;
        this.semesterNameYear = semesterNameYear;
        this.postContent = postContent;
        this.postDate = postDate;
        this.commentsCount = commentsCount;
        this.reactionsCount = reactionsCount;
        this.commentCards = commentCards;
        postCardHandler = new Handler();
    }

    @NonNull
    @Override
    public PostCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater postCardInflater = LayoutInflater.from(context);
        View postCard = postCardInflater.inflate(R.layout.post_card_layout, parent, false);
        return new PostCardViewHolder(postCard);
    }

    @Override
    public void onBindViewHolder(@NonNull PostCardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    protected class PostCardViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView imgPCUserPhoto;
        ImageView imgPCMore, imgPCReactionIcon, imgPCCommentIcon;
        TextView txtPCUserName, txtPCPostCourse, txtPCPostSemester, txtPCDate, txtPCContent,
                txtPCReactionCount, txtPCCommentCount;
        EditText txtPCNewComment;
        Button btnPCSubmitComment;
        RecyclerView recyclerViewCommentCards;

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
            recyclerViewCommentCards = itemView.findViewById(R.id.recyclerViewCommentCards);

            new FetchImage(imgPCUserPhoto, authorPhoto).start();
            txtPCUserName.setText(authorName);
            txtPCPostCourse.setText(courseCode);
            txtPCPostSemester.setText(semesterNameYear);
            txtPCDate.setText(postDate);
            txtPCContent.setText(postContent);
            txtPCReactionCount.setText(reactionsCount);
            txtPCCommentCount.setText(commentsCount);

        }

    }

    private class FetchImage extends Thread {

        String imageURL;
        Bitmap bitmap;
        ShapeableImageView shapeableImageView;

        FetchImage (ShapeableImageView shapeableImageView, String imageURL) {
            this.shapeableImageView = shapeableImageView;
            this.imageURL = imageURL;
        }

        @Override
        public void run() {
            postCardHandler.post(new Runnable() {
                @Override
                public void run() {

                }
            });

            try {
                InputStream inputStream = new URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            postCardHandler.post(new Runnable() {
                @Override
                public void run() {
                    shapeableImageView.setImageBitmap(bitmap);
                }
            });
        }
    }
}
