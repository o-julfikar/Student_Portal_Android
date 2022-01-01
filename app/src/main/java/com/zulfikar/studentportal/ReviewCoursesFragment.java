package com.zulfikar.studentportal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.review.DetailReviewFragment;
import com.zulfikar.studentportal.review.adapters.ReviewCourseAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewCoursesFragment extends Fragment {

    String facultyInitial;

    Button btnSubmit;
    DetailReviewFragment detailReviewFragment;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    RecyclerView rvReviewCourses;
    View rootView;

    public ReviewCoursesFragment() {
        jsonPlaceHolderApi = Client.getApi(getContext());
    }

    public static ReviewCoursesFragment newInstance(
            String facultyInitial,
            DetailReviewFragment detailReviewFragment
    ) {
        ReviewCoursesFragment fragment = new ReviewCoursesFragment();
        fragment.facultyInitial = facultyInitial;
        fragment.detailReviewFragment = detailReviewFragment;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_review_courses, container, false);

        btnSubmit = rootView.findViewById(R.id.btnSubmit);
        rvReviewCourses = rootView.findViewById(R.id.rvReviewCourses);

        Call<Object> getReviewCoursesCall = jsonPlaceHolderApi.getInstructorCourses(facultyInitial);
        getReviewCoursesCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body() instanceof Double) {
                        Toast.makeText(getContext(),
                                "Failed to fetch the courses.",
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        List<?> courseObjects = (List<?>) response.body();
                        ArrayList<String> courses = new ArrayList<>();
                        for (Object courseObject : courseObjects) {
                            courses.add((String) courseObject);
                        }
                        ReviewCourseAdapter reviewCourseAdapter = new ReviewCourseAdapter(
                                getContext(),
                                courses,
                                detailReviewFragment
                        );
                        rvReviewCourses.setAdapter(reviewCourseAdapter);
                        rvReviewCourses.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

        btnSubmit.setOnClickListener(v -> {
            detailReviewFragment.showReviews();
        });

        return rootView;
    }
}