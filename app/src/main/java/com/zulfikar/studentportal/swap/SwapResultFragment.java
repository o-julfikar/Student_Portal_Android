package com.zulfikar.studentportal.swap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zulfikar.studentportal.R;

public class SwapResultFragment extends Fragment {

    Button btnSendRequest;
    RecyclerView rvSwapResult;
    RecyclerView.Adapter<?> rvAdapter;
    View rootView;

    public SwapResultFragment() {

    }

    public static SwapResultFragment newInstance(RecyclerView.Adapter<?> rvAdapter) {
        SwapResultFragment swapResultFragment = new SwapResultFragment();
        swapResultFragment.rvAdapter = rvAdapter;

        return swapResultFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_swap_result, container, false);

        btnSendRequest = rootView.findViewById(R.id.btnSendRequest);
        rvSwapResult = rootView.findViewById(R.id.rvSwapResult);

        rvSwapResult.setAdapter(rvAdapter);
        rvSwapResult.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }
}