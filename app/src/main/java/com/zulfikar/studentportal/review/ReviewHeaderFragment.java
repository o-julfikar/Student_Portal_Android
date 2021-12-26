package com.zulfikar.studentportal.review;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;

public class ReviewHeaderFragment extends Fragment {

    public ReviewHeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SessionManager.auth(getContext());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review_header, container, false);
    }
}