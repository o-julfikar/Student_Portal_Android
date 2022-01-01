package com.zulfikar.studentportal.review;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.R;

public class DetailReviewFragment extends Fragment {

    int reviewId;
    String facultyName, facultyInitial, totalReviewPoints, totalReviews;

    Button btnReview;
    ImageView star1, star2, star3, star4, star5;
    RecyclerView rvReviews;
    ShapeableImageView imgFaculty;
    TextView txtFacultyName, txtFacultyInitial, txtTotalReviewPoints, txtTotalReviews;
    View rootView;

    public DetailReviewFragment() {

    }

    public static DetailReviewFragment createInstance(int reviewId,
                                                      String facultyName,
                                                      String facultyInitial,
                                                      String totalReviewPoints,
                                                      String totalReviews) {
        DetailReviewFragment detailReviewFragment = new DetailReviewFragment();
        detailReviewFragment.reviewId = reviewId;
        detailReviewFragment.facultyName = facultyName;
        detailReviewFragment.facultyInitial = facultyInitial;
        detailReviewFragment.totalReviewPoints = totalReviewPoints;
        detailReviewFragment.totalReviews = totalReviews;

        return detailReviewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_review, container, false);

        btnReview = rootView.findViewById(R.id.btnReview);
        star1 = rootView.findViewById(R.id.star1);
        star2 = rootView.findViewById(R.id.star2);
        star3 = rootView.findViewById(R.id.star3);
        star4 = rootView.findViewById(R.id.star4);
        star5 = rootView.findViewById(R.id.star5);
        rvReviews = rootView.findViewById(R.id.rvReviews);
        imgFaculty = rootView.findViewById(R.id.imgFaculty);
        txtFacultyName = rootView.findViewById(R.id.txtFacultyName);
        txtFacultyInitial = rootView.findViewById(R.id.txtFacultyInitial);
        txtTotalReviewPoints = rootView.findViewById(R.id.txtTotalReviewPoints);
        txtTotalReviews = rootView.findViewById(R.id.txtTotalReviews);

        return rootView;
    }
}