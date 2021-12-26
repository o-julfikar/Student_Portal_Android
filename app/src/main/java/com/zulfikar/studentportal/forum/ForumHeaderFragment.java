package com.zulfikar.studentportal.forum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;

public class ForumHeaderFragment extends Fragment {

    TextView txtNewPost;
    View rootView;

    public ForumHeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());
        rootView = inflater.inflate(R.layout.fragment_forum_header, container, false);

        txtNewPost = rootView.findViewById(R.id.txtNewPost);
        txtNewPost.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.headerFragmentContainer, new NewPostHeaderFragment()).commit();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new NewPostFragment()).commit();
        });

        return rootView;
    }
}