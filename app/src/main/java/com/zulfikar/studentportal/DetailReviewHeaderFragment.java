package com.zulfikar.studentportal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.zulfikar.studentportal.review.DetailReviewFragment;

import java.util.List;

public class DetailReviewHeaderFragment extends Fragment {

    int totalReviews;
    double totalReviewPoints;
    String facultyName, facultyPhoto;
    List<String> facultyInitials;

    DetailReviewFragment detailReviewFragment;
    TextView txtCourseFilter;
    View rootView;

    public DetailReviewHeaderFragment() {
        // Required empty public constructor
    }

    public static DetailReviewHeaderFragment createInstance(
            DetailReviewFragment detailReviewFragment,
            String facultyName,
            List<String> facultyInitials,
            String facultyPhoto,
            Double totalReviewPoints,
            int totalReviews) {
        DetailReviewHeaderFragment detailReviewHeaderFragment = new DetailReviewHeaderFragment();
        detailReviewHeaderFragment.detailReviewFragment = detailReviewFragment;
        detailReviewHeaderFragment.facultyName = facultyName;
        detailReviewHeaderFragment.facultyInitials = facultyInitials;
        detailReviewHeaderFragment.facultyPhoto = facultyPhoto;
        detailReviewHeaderFragment.totalReviewPoints = totalReviewPoints;
        detailReviewHeaderFragment.totalReviews = totalReviews;

        return detailReviewHeaderFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_detail_review_header, container, false);

        txtCourseFilter = rootView.findViewById(R.id.txtCourseFilter);

        txtCourseFilter.setOnClickListener(v -> {
            detailReviewFragment.showCourses();
        });

        return rootView;
    }
}