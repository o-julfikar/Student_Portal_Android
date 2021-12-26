package com.zulfikar.studentportal.account;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.models.User;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.forum.CommentCard;
import com.zulfikar.studentportal.forum.EnrolledCourseCard;
import com.zulfikar.studentportal.forum.EnrolledCourseCardAdapter;
import com.zulfikar.studentportal.forum.PostCard;
import com.zulfikar.studentportal.forum.PostCardAdapter;
import com.zulfikar.studentportal.forum.models.Post;
import com.zulfikar.studentportal.forum.models.PostComments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private int userBracuId;

    Button btnEditProfile, btnAdminPanel, btnLogout, btnEnrollCourse;
    Handler profileHandler;
    ProfileHeaderFragment profileHeaderFragment;
    RecyclerView rvEnrolledCourses, rvPostCards;
    ShapeableImageView imgUserPhoto;
    TextView txtPosts, txtReviews, txtSecSwap, txtStudSwap, txtRole, txtEmail, txtDepartment,
            txtEnrolledSemester;
    View rootView;

    public ProfileFragment(int userBracuId, ProfileHeaderFragment profileHeaderFragment) {
        this();
        this.profileHeaderFragment = profileHeaderFragment;
        this.userBracuId = userBracuId;
    }

    public ProfileFragment() {
        // Required empty public constructor
        profileHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());
        rootView = inflater.inflate( R.layout.fragment_profile, container, false);

        btnEditProfile = rootView.findViewById(R.id.btnEditProfile);
        btnAdminPanel = rootView.findViewById(R.id.btnAdmin);
        btnLogout = rootView.findViewById(R.id.btnLogout);
        btnEnrollCourse = rootView.findViewById(R.id.btnEnrollCourse);
        txtPosts = rootView.findViewById(R.id.txtPosts);
        txtReviews = rootView.findViewById(R.id.txtReviews);
        txtSecSwap = rootView.findViewById(R.id.txtSecSwap);
        txtStudSwap = rootView.findViewById(R.id.txtStudSwap);
        txtRole = rootView.findViewById(R.id.txtRole);
        txtEmail = rootView.findViewById(R.id.txtEmail);
        txtDepartment = rootView.findViewById(R.id.txtProgram);
        txtEnrolledSemester = rootView.findViewById(R.id.txtEnrolledSemester);
        rvEnrolledCourses = rootView.findViewById(R.id.rvEnrolledCourses);
        rvPostCards = rootView.findViewById(R.id.rvPostCards);
        imgUserPhoto = rootView.findViewById(R.id.imgUserPhoto);

        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
        Call<User> userCall;
        if (userBracuId == -1) userCall = jsonPlaceHolderApi.getUser();
        else userCall = jsonPlaceHolderApi.getUserById(userBracuId);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        userBracuId = user.getBracu_id();
                        profileHeaderFragment.setTxtUserName(user.getFullname());
                        txtRole.setText(user.getRole());
                        txtEmail.setText(user.getEmail());
                        txtDepartment.setText(user.getProgram());
                        txtEnrolledSemester.setText(user.getSemester());
                        new FetchImage(imgUserPhoto, user.getPhoto(), profileHandler).start();
                        if (user.getBracu_id() == SessionManager.getLoggedUserBracuId(getContext())) {
                            btnEnrollCourse.setVisibility(View.VISIBLE);
                            btnEditProfile.setText(R.string.edit_profile_button_text);
                            btnAdminPanel.setVisibility(user.getRole().equalsIgnoreCase("admin")?
                                    View.VISIBLE : View.GONE);
                        } else {
                            btnEditProfile.setText(R.string.open_in_search_button_text);
                        }
                        ArrayList<EnrolledCourseCard> enrolledCourseCards = new ArrayList<>();
                        for (Map<String, String> enrolledCourse : user.getEnrolled_courses()) {
                            enrolledCourseCards.add(
                                    new EnrolledCourseCard(
                                            enrolledCourse.get("course") +
                                            " | " +
                                            enrolledCourse.get("semester")
                                    )
                            );
                        }
                        rvEnrolledCourses.setAdapter(new EnrolledCourseCardAdapter(getContext(), enrolledCourseCards));
                        rvEnrolledCourses.setLayoutManager(new LinearLayoutManager(getContext()));

                        Call<List<Post>> postCall = jsonPlaceHolderApi.getPostByUser(userBracuId);
                        postCall.enqueue(new Callback<List<Post>>() {
                            @Override
                            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                                if (response.isSuccessful()) {
                                    List<Post> posts = response.body();
                                    if (posts != null) {
                                        PostCard[] postCards = new PostCard[posts.size()];
                                        int i = 0;
                                        for (Post post : posts) {

                                            PostComments[] postComments = new PostComments[4];
                                            CommentCard[] commentCards = new CommentCard[0];
                                            PostCard postCard = new PostCard(post.getId(), post.getAuthorBracuId(), 0, 0,
                                                    post.getAuthorName(), post.getAuthorPhoto(),
                                                    post.getPostCourse(), post.getPostSemester(),
                                                    post.getPostContent(),
                                                    post.getDateCreated().toString(),
                                                    String.valueOf((int) Double.parseDouble(Objects.requireNonNull(post.getPostReactions().get("count")).toString())),
                                                    String.valueOf((int) Double.parseDouble(Objects.requireNonNull(post.getPostComments().get("count")).toString()))
                                            );
                                            postCards[i++] = postCard;
                                        }

                                        PostCardAdapter postCardAdapter = new PostCardAdapter(getContext(), postCards);
                                        rvPostCards.setAdapter(postCardAdapter);
                                        rvPostCards.setLayoutManager(new LinearLayoutManager(getContext()));
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
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnEnrollCourse.setOnClickListener(v -> {
            SessionManager.auth(getContext());
            requireActivity().getSupportFragmentManager().beginTransaction().replace(
                    R.id.mainFragmentContainer,
                    new EnrollCourseFragment()).commit();
        });

        btnLogout.setOnClickListener(v -> {
            SessionManager.logout(getContext());
        });

        return rootView;
    }
}