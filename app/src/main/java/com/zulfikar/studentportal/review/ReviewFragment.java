package com.zulfikar.studentportal.review;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends Fragment {

    RecyclerView recyclerViewSneakReviewCards;
    View rootView;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());
        rootView = inflater.inflate(R.layout.fragment_review, container, false);
        recyclerViewSneakReviewCards = rootView.findViewById(R.id.rvSneakReviewCards);

        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
        Call<Object> getInitialsCall = jsonPlaceHolderApi.getInstructorInitials();
        getInitialsCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body() instanceof Integer) {

                    } else if (response.body() instanceof List) {
                        List<?> initials = (List<?>) response.body();
                        ArrayList<SneakReviewCard> sneakReviewCards = new ArrayList<>();
                        for (Object initial : initials) {
                            if (initial instanceof String) {
                                sneakReviewCards.add(new SneakReviewCard(
                                        (String) initial
                                ));
                            }
                        }
                        SneakReviewCardAdapter sneakReviewCardAdapter;
                        sneakReviewCardAdapter = new SneakReviewCardAdapter(
                                getContext(),
                                sneakReviewCards
                        );
                        recyclerViewSneakReviewCards.setAdapter(sneakReviewCardAdapter);
                        recyclerViewSneakReviewCards.setLayoutManager(new LinearLayoutManager(container.getContext()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });



//        SneakReviewCard sneakReviewCard1 = new SneakReviewCard(
//                0, 4.3, "Abu Hasnayen Zillanee", "AHN", "",
//                new SneakCard[]{
//                        new SneakCard(0, "CSE110", "It was wonderful experience to learn the basics of Python from him..."),
//                        new SneakCard(0, "CSE321", "If it was not him, I would never be able to understand the basics of Op..."),
//                        new SneakCard(0, "CSE421", "Networking won’t be the same without his guidance...")
//                });
//        SneakReviewCard sneakReviewCard2 = new SneakReviewCard(
//                0, 4.3, "Md. Shahriyar Hossain Shihab", "HAB", "",
//                new SneakCard[]{
//                        new SneakCard(0, "ENG102", "I couldn't even write my name properly before attending ..."),
//                        new SneakCard(0, "CSE321", "Operation System is an important part of Computer, so is...")
//                });
//        SneakReviewCard sneakReviewCard3 = new SneakReviewCard(
//                0, 4.3, "Syed Rahmatullah Afsan", "SRA", "",
//                new SneakCard[]{
//                        new SneakCard(0, "ARC609", "Was so afraid to take this 600 level course. But now I am..."),
//                        new SneakCard(0, "BUS201", "I have started my own startup from midterm and it’s bloom..."),
//                        new SneakCard(0, "PHY112", "Never understand electromagnetism lik...")
//                });
//        SneakReviewCard sneakReviewCard4 = new SneakReviewCard(
//                0, 4.3, "Md. Imtiyaz Bhuiyan", "MIB", "",
//                new SneakCard[]{
//                        new SneakCard(0, "BIO101", "I can literally feel all cells of my body after doing the cour..."),
//                        new SneakCard(0, "CSE341", "I designed the new Apple M1 Max chip after doing this c..."),
//                        new SneakCard(0, "POL101", "I am now a Finance Minister in Kingdom...")
//                });

//        SneakReviewCardAdapter sneakReviewCardAdapter = new SneakReviewCardAdapter(getContext(), new SneakReviewCard[]{
//                sneakReviewCard1,
//                sneakReviewCard2,
//                sneakReviewCard3,
//                sneakReviewCard4
//        });

//        recyclerViewSneakReviewCards.setAdapter(sneakReviewCardAdapter);
//        recyclerViewSneakReviewCards.setLayoutManager(new LinearLayoutManager(container.getContext()));

        return rootView;
    }
}