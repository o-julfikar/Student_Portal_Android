package com.zulfikar.studentportal.forum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;

public class NewPostHeaderFragment extends Fragment {

    public NewPostHeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SessionManager.auth(getContext());
        return inflater.inflate(R.layout.fragment_new_post_header, container, false);
    }
}