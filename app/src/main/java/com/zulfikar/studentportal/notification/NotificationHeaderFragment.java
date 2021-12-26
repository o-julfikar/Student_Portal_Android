package com.zulfikar.studentportal.notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;

public class NotificationHeaderFragment extends Fragment {

    View rootView;

    public NotificationHeaderFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());
        rootView = inflater.inflate(R.layout.fragment_notification_header, container, false);

        return rootView;
    }
}