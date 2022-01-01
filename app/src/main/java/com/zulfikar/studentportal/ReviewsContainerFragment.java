package com.zulfikar.studentportal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.review.adapters.ReviewCardAdapter;
import com.zulfikar.studentportal.review.models.SneakCardModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsContainerFragment extends Fragment {

    JsonPlaceHolderApi jsonPlaceHolderApi;
    RecyclerView rvReviews;
    String instructorInitial;
    View rootView;

    public ReviewsContainerFragment() {
        jsonPlaceHolderApi = Client.getApi(getContext());
    }

    public static ReviewsContainerFragment newInstance(String instructorInitial) {
        ReviewsContainerFragment fragment = new ReviewsContainerFragment();
        fragment.instructorInitial = instructorInitial;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_reviews_container, container, false);

        rvReviews = rootView.findViewById(R.id.rvReviews);

        Call<Object> getReviewCardsCall = jsonPlaceHolderApi.getReview(instructorInitial);
        getReviewCardsCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body() instanceof Double) {
                        Toast.makeText(getContext(),
                                "Failed to fetch the reviews.",
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        List<?> reviewObjects = (List<?>) response.body();
                        ArrayList<SneakCardModel.SneakReviewModel> sneakReviewModels;
                        sneakReviewModels = new ArrayList<>();
                        for (Object reviewObject : reviewObjects) {
                            sneakReviewModels.add(new SneakCardModel.SneakReviewModel(
                                    reviewObject
                            ));
                        }
                        ReviewCardAdapter reviewCardAdapter = new ReviewCardAdapter(
                                getContext(),
                                sneakReviewModels
                        );
                        rvReviews.setAdapter(reviewCardAdapter);
                        rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

        return rootView;
    }
}