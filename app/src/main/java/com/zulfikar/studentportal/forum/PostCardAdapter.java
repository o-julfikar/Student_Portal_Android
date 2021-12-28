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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.Utility;
import com.zulfikar.studentportal.account.ProfileFragment;
import com.zulfikar.studentportal.account.ProfileHeaderFragment;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.forum.models.Post;
import com.zulfikar.studentportal.forum.models.PostComments;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        holder.txtPCDate.setText(Utility.dateTimeFormat(postCards[position].postDate));
        holder.txtPCContent.setText(postCards[position].postContent);
        holder.txtPCReactionCount.setText(postCards[position].reactionsCount);
        holder.txtPCCommentCount.setText(postCards[position].commentsCount);

        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(context);
        final int postId = postCards[position].postId;

        if ((postCards[position].courseCode.trim() + postCards[position].semesterNameYear.trim()).length() == 0) {
            String general = "General";
            holder.txtPCPostSemSeparator.setText(general);
            holder.txtPCPostSemSeparator.setPadding(
                    0,
                    holder.txtPCPostSemSeparator.getPaddingTop(),
                    holder.txtPCPostSemSeparator.getPaddingRight(),
                    holder.txtPCPostSemSeparator.getPaddingBottom());
        } else if (
                (postCards[position].courseCode.trim().length() == 0) ||
                (postCards[position].semesterNameYear.trim().length() == 0)) {
            holder.txtPCPostSemSeparator.setText("");
        }

        holder.txtPCUserName.setOnClickListener(v -> {
            if (context instanceof AppCompatActivity) {
                ProfileHeaderFragment profileHeaderFragment = new ProfileHeaderFragment();
                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(
                        R.id.headerFragmentContainer,
                        profileHeaderFragment).commit();
                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(
                        R.id.mainFragmentContainer,
                        new ProfileFragment(postCards[position].authorId, profileHeaderFragment)).commit();
            }
        });

        holder.imgPCUserPhoto.setOnClickListener(v -> {
            if (context instanceof AppCompatActivity) {
                ProfileHeaderFragment profileHeaderFragment = new ProfileHeaderFragment();
                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(
                        R.id.headerFragmentContainer,
                        profileHeaderFragment).commit();
                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(
                        R.id.mainFragmentContainer,
                        new ProfileFragment(postCards[position].authorId, profileHeaderFragment)).commit();
            }
        });

        holder.btnPCSubmitComment.setOnClickListener(v -> {
            PostComments.PostComment postComment = new PostComments.PostComment(postId, holder.txtPCNewComment.getText().toString());
            Call<Boolean> postCommentCall = jsonPlaceHolderApi.createComment(postComment);
            postCommentCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body()) {
                            refreshComments(postId, holder, jsonPlaceHolderApi);
                            holder.txtPCNewComment.setText("");
                        }
                    } else {
                        Toast.makeText(context, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        holder.itemView.setOnClickListener(v -> {
            PostFragment postFragment = PostFragment.getInstance(postId);
            ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(
                    R.id.mainFragmentContainer,
                    postFragment).commit();
        });

        refreshComments(postId, holder, jsonPlaceHolderApi);
    }

    public void refreshComments(int postId, PostCardViewHolder holder, JsonPlaceHolderApi jsonPlaceHolderApi) {
        Call<PostComments> commentsCall = jsonPlaceHolderApi.getComments(postId);
        commentsCall.enqueue(new Callback<PostComments>() {
            @Override
            public void onResponse(Call<PostComments> call, Response<PostComments> response) {
                if (response.isSuccessful()) {
                    PostComments postComments = response.body();
                    holder.linearLayoutCommentCards.removeAllViews();
                    assert postComments != null;
                    for (PostComments.PostComment postComment : postComments.getComments()) {
                        CommentCard commentCard = new CommentCard(
                                postComment.getComment_id(),
                                postComment.getAuthor_bracu_id(),
                                postId,
                                postComment.getAuthor_name(),
                                postComment.getAuthor_photo(),
                                postComment.getContent(),
                                postComment.getDate_created()
                        );
                        holder.linearLayoutCommentCards.addView(
                                commentCard.getView(
                                        context,
                                        holder.linearLayoutCommentCards,
                                        postCardHandler
                                )
                        );
                    }
                    holder.txtPCCommentCount.setText(String.valueOf(postComments.getCount()));
                } else {
                    Toast.makeText(context, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostComments> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return postCards.length;
    }

    protected static class PostCardViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView imgPCUserPhoto;
        ImageView imgPCMore, imgPCReactionIcon, imgPCCommentIcon;
        TextView txtPCUserName, txtPCPostCourse, txtPCPostSemester, txtPCDate, txtPCContent,
                txtPCReactionCount, txtPCCommentCount, txtPCPostSemSeparator;
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
            txtPCPostSemSeparator = itemView.findViewById(R.id.txtPCPostSemSeparator);
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
