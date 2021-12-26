package com.zulfikar.studentportal.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;

public class SearchHeaderFragment extends Fragment {

    public SearchHeaderFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());

        return inflater.inflate(R.layout.fragment_search_header, container, false);
    }
}