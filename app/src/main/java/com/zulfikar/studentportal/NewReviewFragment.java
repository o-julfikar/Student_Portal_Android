package com.zulfikar.studentportal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zulfikar.studentportal.account.SessionManager;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.review.DetailReviewFragment;
import com.zulfikar.studentportal.review.models.Review;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewReviewFragment extends Fragment {

    int reviewPoint;
    String facultyInitial;

    Button btnSubmit;
    DetailReviewFragment detailReviewFragment;
    EditText txtNewReview, txtCourseCode;
    ImageView[] stars;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    private View rootView;

    public NewReviewFragment() {
        jsonPlaceHolderApi = Client.getApi(getContext());
        stars = new ImageView[6];
    }

    public static NewReviewFragment newInstance(
            String facultyInitial,
            DetailReviewFragment detailReviewFragment
    ) {
        NewReviewFragment fragment = new NewReviewFragment();
        fragment.facultyInitial = facultyInitial;
        fragment.detailReviewFragment = detailReviewFragment;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SessionManager.auth(getContext());

        rootView = inflater.inflate(R.layout.fragment_new_review, container, false);

        txtNewReview = rootView.findViewById(R.id.txtNewReview);
        txtCourseCode = rootView.findViewById(R.id.txtCourseCode);
        btnSubmit = rootView.findViewById(R.id.btnSubmit);
        stars[1] = rootView.findViewById(R.id.star1);
        stars[2] = rootView.findViewById(R.id.star2);
        stars[3] = rootView.findViewById(R.id.star3);
        stars[4] = rootView.findViewById(R.id.star4);
        stars[5] = rootView.findViewById(R.id.star5);

        for (int i = 1; i < stars.length; i++) {
            int finalI = i;
            stars[i].setOnClickListener(v -> {
                starOnClick(finalI);
            });
        }

        btnSubmit.setOnClickListener(v -> {
            if (txtNewReview.getText().toString().trim().length() == 0) {
                showToast("Review text must not be empty");
            } else if (txtCourseCode.getText().toString().trim().length() == 0) {
                showToast("Course code must not be empty");
            } else if (reviewPoint == 0) {
                showToast("Must provide a rating of minimum 1 star to continue");
            } else if (txtCourseCode.getText().length() > 10) {
                showToast("Course code length must not exceed more than 10 characters");
            } else {
                Review review = new Review(
                        txtCourseCode.getText().toString(),
                        txtNewReview.getText().toString(),
                        (double) reviewPoint,
                        facultyInitial
                );

                Call<Integer> postReview = jsonPlaceHolderApi.postReview(review);
                postReview.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            int responseCode = response.body();
                            if (responseCode > 0) {
                                detailReviewFragment.refresh();
                            } else if (responseCode == -3) {
                                Toast.makeText(
                                        getContext(),
                                        "You cannot provide feedback more than once for an " +
                                                "instructor on the same course",
                                        Toast.LENGTH_SHORT
                                ).show();
                            } else {
                                Toast.makeText(
                                        getContext(),
                                        "Failed to submit the review. Please try again later.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
            }

        });

        return rootView;
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void starOnClick(int starIndex) {
        if (starIndex == reviewPoint) reviewPoint = 0;
        else reviewPoint = starIndex;

        for (int i = 1; i <= reviewPoint; i++) {
            if (getContext() != null) {
                stars[i].setColorFilter(getContext().getColor(R.color.yellow_orange));
            }
        }

        for (int i = reviewPoint + 1; i < stars.length; i++) {
            stars[i].setColorFilter(null);
        }
    }
}