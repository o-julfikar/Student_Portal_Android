package com.zulfikar.studentportal.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zulfikar.studentportal.R;

public class ProfileHeaderFragment extends Fragment {

    private TextView txtUserName;
    private View rootView;

    public ProfileHeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        SessionManager.auth(getContext());

        rootView = inflater.inflate(R.layout.fragment_profile_header,container,false);
        txtUserName = rootView.findViewById(R.id.txtUserName);

        return rootView;
    }

    public void setTxtUserName(String userName) {
        if (txtUserName != null) txtUserName.setText(userName);
    }
}