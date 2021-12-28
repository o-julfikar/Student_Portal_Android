package com.zulfikar.studentportal.forum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zulfikar.studentportal.Assets;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.forum.models.PostComments;
import com.zulfikar.studentportal.forum.models.Post;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForumFragment extends Fragment {

    RecyclerView recyclerViewPostCards;
    View rootView;

    String TAG = "ForumFragment";

    public ForumFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());

        rootView = inflater.inflate(R.layout.fragment_forum, container, false);
        recyclerViewPostCards = rootView.findViewById(R.id.rvPostCards);

        Retrofit retrofit = Client.getRetrofit(getContext());

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> postCall = jsonPlaceHolderApi.getPost();
        postCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> posts = response.body();
                    if (posts != null) {
                        PostCard[] postCards = new PostCard[posts.size()];
                        int i = 0;
                        for (Post post : posts) {

                            PostCard postCard = new PostCard(post.getId(), post.getAuthorBracuId(), 0, 0,
                                    post.getAuthorName(), post.getAuthorPhoto(),
                                    post.getPostCourse(), post.getPostSemester(),
                                    post.getPostContent(),
                                    post.getDateCreated(),
                                    String.valueOf((int) Double.parseDouble(Objects.requireNonNull(post.getPostReactions().get("count")).toString())),
                                    String.valueOf((int) Double.parseDouble(Objects.requireNonNull(post.getPostComments().get("count")).toString()))
                                    );
                            postCards[i++] = postCard;
                        }
                        Log.d(TAG, "onCreateView: " + recyclerViewPostCards);

                        PostCardAdapter postCardAdapter = new PostCardAdapter(container.getContext(), postCards);
                        recyclerViewPostCards.setAdapter(postCardAdapter);
                        recyclerViewPostCards.setLayoutManager(new LinearLayoutManager(container.getContext()));
                    }
                } else {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}