package com.zulfikar.studentportal.swap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zulfikar.studentportal.R;

public class SwapFragment extends Fragment {

    View rootView;
    FragmentContainerView swapFragmentContainer;

    public SwapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_swap, container, false);

        swapFragmentContainer = rootView.findViewById(R.id.swapFragmentContainer);

        return rootView;
    }
}