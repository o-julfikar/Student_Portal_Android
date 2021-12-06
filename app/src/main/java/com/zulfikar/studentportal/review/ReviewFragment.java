package com.zulfikar.studentportal.review;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zulfikar.studentportal.R;

public class ReviewFragment extends Fragment {

    RecyclerView recyclerViewSneakReviewCards;
    View rootView;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_review, container, false);
        recyclerViewSneakReviewCards = rootView.findViewById(R.id.rvSneakReviewCards);

        SneakReviewCard sneakReviewCard1 = new SneakReviewCard(
                0, 4.3, "Abu Hasnayen Zillanee", "AHN", "",
                new SneakCard[]{
                        new SneakCard(0, "CSE110", "It was wonderful experience to learn the basics of Python from him..."),
                        new SneakCard(0, "CSE321", "If it was not him, I would never be able to understand the basics of Oper..."),
                        new SneakCard(0, "CSE421", "Networking won’t be the same without his guidan...")
                });
        SneakReviewCard sneakReviewCard2 = new SneakReviewCard(
                0, 4.3, "Md. Shahriyar Hossain Shihab", "HAB", "",
                new SneakCard[]{
                        new SneakCard(0, "ENG102", "I couldn’t even write my name properly before attending ..."),
                        new SneakCard(0, "CSE321", "Operation System is an important part of Computer, so is...")
                });
        SneakReviewCard sneakReviewCard3 = new SneakReviewCard(
                0, 4.3, "Syed Ramatullah Afsan", "SRA", "",
                new SneakCard[]{
                        new SneakCard(0, "ARC609", "Was so afraid to take this 600 level course. But now I am..."),
                        new SneakCard(0, "BUS201", "I have started my own startup from midterm and it’s bloo..."),
                        new SneakCard(0, "PHY112", "Never understand electromagnetism lik...")
                });
        SneakReviewCard sneakReviewCard4 = new SneakReviewCard(
                0, 4.3, "Md. Imtiyaz Bhiyan", "MIB", "",
                new SneakCard[]{
                        new SneakCard(0, "BIO101", "I can literally feel all cells of my body after doing the cour..."),
                        new SneakCard(0, "CSE341", "I designed the new Apple M1 Max chip after doing this c..."),
                        new SneakCard(0, "POL101", "I am now a Finance Minister in Kingdom...")
                });

        SneakReviewCardAdapter sneakReviewCardAdapter = new SneakReviewCardAdapter(getContext(), new SneakReviewCard[]{
                sneakReviewCard1,
                sneakReviewCard2,
                sneakReviewCard3,
                sneakReviewCard4
        });

        recyclerViewSneakReviewCards.setAdapter(sneakReviewCardAdapter);
        recyclerViewSneakReviewCards.setLayoutManager(new LinearLayoutManager(container.getContext()));

        return rootView;
    }
}