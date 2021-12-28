package com.zulfikar.studentportal.forum;

import android.os.Bundle;
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

import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.Utility;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.forum.models.Post;
import com.zulfikar.studentportal.forum.models.PostComments;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment {

    int postId;
    View rootView;
    ShapeableImageView imgPCUserPhoto;
    ImageView imgPCMore, imgPCReactionIcon, imgPCCommentIcon;
    TextView txtPCUserName, txtPCPostCourse, txtPCPostSemester, txtPCDate, txtPCContent,
            txtPCReactionCount, txtPCCommentCount, txtPCPostSemSeparator;
    EditText txtPCNewComment;
    Button btnPCSubmitComment;
    LinearLayout linearLayoutCommentCards;
    Handler postHandler;

    public PostFragment() {
        postHandler = new Handler();
    }

    public static PostFragment getInstance(int postId) {
        PostFragment postFragment = new PostFragment();
        postFragment.postId = postId;


        return postFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_post, container, false);

        imgPCUserPhoto = rootView.findViewById(R.id.imgPCUserPhoto);
        imgPCMore = rootView.findViewById(R.id.imgPCMore);
        imgPCReactionIcon = rootView.findViewById(R.id.imgPCReactionIcon);
        imgPCCommentIcon = rootView.findViewById(R.id.imgPCCommentIcon);
        txtPCUserName = rootView.findViewById(R.id.txtPCUserName);
        txtPCPostCourse = rootView.findViewById(R.id.txtPCPostCourse);
        txtPCPostSemester = rootView.findViewById(R.id.txtPCPostSemester);
        txtPCDate = rootView.findViewById(R.id.txtPCDate);
        txtPCContent = rootView.findViewById(R.id.txtPCContent);
        txtPCReactionCount = rootView.findViewById(R.id.txtPCReactionCount);
        txtPCCommentCount = rootView.findViewById(R.id.txtPCCommentCount);
        txtPCPostSemSeparator = rootView.findViewById(R.id.txtPCPostSemSeparator);
        txtPCNewComment = rootView.findViewById(R.id.txtPCNewComment);
        btnPCSubmitComment = rootView.findViewById(R.id.btnPCSubmitComment);
        linearLayoutCommentCards = rootView.findViewById(R.id.linearLayoutCommentCards);

        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
        Call<Post> getPostCall = jsonPlaceHolderApi.getPostById(postId);
        getPostCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Post post = response.body();
                    new FetchImage(imgPCUserPhoto, post.getAuthorPhoto(), postHandler).start();
                    txtPCUserName.setText(post.getAuthorName());
                    txtPCPostCourse.setText(post.getPostCourse());
                    txtPCPostSemester.setText(post.getPostSemester());
                    txtPCDate.setText(Utility.simpleDateFormat(post.getDateCreated()));
                    txtPCContent.setText(post.getPostContent());
                    txtPCReactionCount.setText(post.getPostReactionsCount());
                    txtPCCommentCount.setText(post.getPostCommentsCount());
                    refreshComments(jsonPlaceHolderApi);
                    if ((post.getPostCourse().trim() + post.getPostSemester().trim()).length() == 0) {
                        String general = "General";
                        txtPCPostSemSeparator.setText(general);
                        txtPCPostSemSeparator.setPadding(
                                0,
                                txtPCPostSemSeparator.getPaddingTop(),
                                txtPCPostSemSeparator.getPaddingRight(),
                                txtPCPostSemSeparator.getPaddingBottom());
                    } else if (
                            (post.getPostCourse().trim().length() == 0) ||
                                    (post.getPostSemester().trim().length() == 0)) {
                        txtPCPostSemSeparator.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

        btnPCSubmitComment.setOnClickListener(v -> {
            PostComments.PostComment postComment = new PostComments.PostComment(
                    postId,
                    txtPCNewComment.getText().toString());
            Call<Boolean> postCommentCall = jsonPlaceHolderApi.createComment(postComment);
            postCommentCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body()) {
                            refreshComments(jsonPlaceHolderApi);
                            txtPCNewComment.setText("");
                        }
                    } else {
                        Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        return rootView;
    }

    public void refreshComments(JsonPlaceHolderApi jsonPlaceHolderApi) {
        Call<PostComments> commentsCall = jsonPlaceHolderApi.getComments(postId);
        commentsCall.enqueue(new Callback<PostComments>() {
            @Override
            public void onResponse(Call<PostComments> call, Response<PostComments> response) {
                if (response.isSuccessful()) {
                    PostComments postComments = response.body();
                    linearLayoutCommentCards.removeAllViews();
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
                        linearLayoutCommentCards.addView(
                                commentCard.getView(
                                        getContext(),
                                        linearLayoutCommentCards,
                                        postHandler,
                                        "#F6F6F6"
                                )
                        );
                    }
                    txtPCCommentCount.setText(String.valueOf(postComments.getCount()));
                } else {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostComments> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}