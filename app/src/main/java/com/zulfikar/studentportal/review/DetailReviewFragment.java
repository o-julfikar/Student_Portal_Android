package com.zulfikar.studentportal.review;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.NewReviewFragment;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.ReviewCoursesFragment;
import com.zulfikar.studentportal.ReviewsContainerFragment;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.review.adapters.ReviewCardAdapter;
import com.zulfikar.studentportal.review.adapters.ReviewCourseAdapter;
import com.zulfikar.studentportal.review.models.ReviewHeaderModel;
import com.zulfikar.studentportal.review.models.SneakCardModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailReviewFragment extends Fragment {
    boolean startWithReview;
    int totalReviews;
    ColorStateList btnReviewTint;
    String facultyName, facultyPhoto;
    List<String> facultyInitials;

    double totalReviewPoints;
    Button btnReview;
    FragmentContainerView layoutReviewDetailFragment;
    ImageView star1, star2, star3, star4, star5;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    LinearLayout layoutStars;
    ShapeableImageView imgFaculty;
    TextView txtFacultyName, txtFacultyInitial, txtTotalReviewPoints, txtTotalReviews;
    View rootView;
    private int currentMode;

    public DetailReviewFragment() {
        jsonPlaceHolderApi = Client.getApi(getContext());
        currentMode = -1;
    }

    public static DetailReviewFragment createInstance(String facultyName,
                                                      List<String> facultyInitials,
                                                      String facultyPhoto,
                                                      Double totalReviewPoints,
                                                      int totalReviews,
                                                      boolean startWithReview
                                                      ) {
        DetailReviewFragment detailReviewFragment = new DetailReviewFragment();
        detailReviewFragment.facultyName = facultyName;
        detailReviewFragment.facultyInitials = facultyInitials;
        detailReviewFragment.facultyPhoto = facultyPhoto;
        detailReviewFragment.totalReviewPoints = totalReviewPoints;
        detailReviewFragment.totalReviews = totalReviews;
        detailReviewFragment.startWithReview = startWithReview;

        return detailReviewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_review, container, false);

        layoutReviewDetailFragment = rootView.findViewById(R.id.layoutReviewDetailFragment);
        btnReview = rootView.findViewById(R.id.btnReview);
        star1 = rootView.findViewById(R.id.star1);
        star2 = rootView.findViewById(R.id.star2);
        star3 = rootView.findViewById(R.id.star3);
        star4 = rootView.findViewById(R.id.star4);
        star5 = rootView.findViewById(R.id.star5);
        imgFaculty = rootView.findViewById(R.id.imgFaculty);
        layoutStars = rootView.findViewById(R.id.layoutStars);
        txtFacultyName = rootView.findViewById(R.id.txtFacultyName);
        txtTotalReviews = rootView.findViewById(R.id.txtTotalReviews);
        txtFacultyInitial = rootView.findViewById(R.id.txtFacultyInitial);
        txtTotalReviewPoints = rootView.findViewById(R.id.txtTotalReviewPoints);

        fillHeaderInfo();

        btnReview.setOnClickListener(v -> {
            if (btnReview.getText().equals(getResources().getString(R.string.cancel))) {
                showReviews();
            } else {
                openNewReview();
            }
        });

        if (startWithReview) {
            openNewReview();
        } else {
            showReviews();
        }

        return rootView;
    }

    public void refresh() {
        Call<Object> getInstructorSneakCardCall = jsonPlaceHolderApi
                .getInstructorSneakCard(facultyInitials.get(0));
        getInstructorSneakCardCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body() instanceof Integer) {

                    } else {
                        ReviewHeaderModel headerInfo = new ReviewHeaderModel(response.body());
                        facultyName = headerInfo.getInstructorFullname();
                        facultyPhoto = headerInfo.getInstructorPhoto();
                        facultyInitials = headerInfo.getInstructorInitials();
                        totalReviewPoints = headerInfo.getInstructorReviewPoints();
                        totalReviews = headerInfo.getInstructorTotalReviews();

                        fillHeaderInfo();
                        showReviews();
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    public void openNewReview() {
        NewReviewFragment newReviewFragment = NewReviewFragment.newInstance(
                facultyInitials.get(0),
                DetailReviewFragment.this
        );
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(layoutReviewDetailFragment.getId(), newReviewFragment)
                .commit();
        btnReview.setText(R.string.cancel);
        if (getContext() != null) {
            btnReviewTint = btnReview.getBackgroundTintList();
            btnReview.setBackgroundTintList(ContextCompat.getColorStateList(
                    getContext(),
                    R.color.red_bd
            ));
        }
        hideExtraInfo();
    }

    public void showCourses() {
        resetReviewButton();
        ReviewCoursesFragment reviewCoursesFragment = ReviewCoursesFragment.newInstance(
                facultyInitials.get(0),
                DetailReviewFragment.this
        );
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.layoutReviewDetailFragment, reviewCoursesFragment)
                .commit();
    }

    public void showReviews() {
        resetReviewButton();
        ReviewsContainerFragment reviewsContainerFragment = ReviewsContainerFragment
                .newInstance(
                        facultyInitials.get(0)
                );
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.layoutReviewDetailFragment, reviewsContainerFragment)
                .commit();
    }

    private void resetReviewButton() {
        if (
                btnReview.getText().equals(getResources().getString(R.string.cancel))
                    &&
                getContext() != null

        ) {
            btnReview.setText(R.string.review);
            btnReview.setBackgroundTintList(ContextCompat.getColorStateList(
                    getContext(),
                    R.color.student_portal_blue
            ));
            layoutStars.setVisibility(View.VISIBLE);
            txtTotalReviewPoints.setVisibility(View.VISIBLE);
            txtTotalReviews.setVisibility(View.VISIBLE);
            txtFacultyInitial.setVisibility(View.VISIBLE);
            imgFaculty.setVisibility(View.VISIBLE);
        }
    }

    private void hideExtraInfo() {
        layoutStars.setVisibility(View.GONE);
        txtTotalReviewPoints.setVisibility(View.GONE);
        txtTotalReviews.setVisibility(View.GONE);
        txtFacultyInitial.setVisibility(View.GONE);
        imgFaculty.setVisibility(View.GONE);
    }

    private void fillHeaderInfo() {
        new FetchImage(imgFaculty, facultyPhoto, new Handler()).start();
        txtFacultyName.setText(facultyName);
        txtFacultyInitial.setText(String.join(", ", facultyInitials));
        String reviewPointsText = totalReviewPoints + " of 5 stars";
        txtTotalReviewPoints.setText(reviewPointsText);
        String reviewCountText = "From " + totalReviews + " reviews";
        txtTotalReviews.setText(reviewCountText);

        if (getContext() != null) {
            if (totalReviewPoints > 0) {
                star1.setColorFilter(getContext().getColor(R.color.yellow_orange));
            }
            if (totalReviewPoints > 1) {
                star2.setColorFilter(getContext().getColor(R.color.yellow_orange));
            }
            if (totalReviewPoints > 2) {
                star3.setColorFilter(getContext().getColor(R.color.yellow_orange));
            }
            if (totalReviewPoints > 3) {
                star4.setColorFilter(getContext().getColor(R.color.yellow_orange));
            }
            if (totalReviewPoints > 4) {
                star5.setColorFilter(getContext().getColor(R.color.yellow_orange));
            }
        }
    }
}