package com.zulfikar.studentportal.swap;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.Utility;
import com.zulfikar.studentportal.account.SessionManager;
import com.zulfikar.studentportal.swap.models.SecSwapCardInfoModel;

import java.util.Date;

public class SwapResultFragment extends Fragment {

    int requestId, creatorBracuId, totalSwaps, userAccepted, requestStatus;
    String creatorName, creatorPhoto;
    Date dateCreated;

    Button btnAccept, btnDecline;
    ConstraintLayout layoutInfo;
    TextView txtCreator, txtDateCreated, txtTotalSwaps, txtStatus;
    RecyclerView rvSwapResult;
    RecyclerView.Adapter<?> rvAdapter;
    View rootView;

    public SwapResultFragment() {

    }

    public static SwapResultFragment newInstance(RecyclerView.Adapter<?> rvAdapter,
                                                 int requestId, int creatorBracuId,
                                                 String creatorName,
                                                 String creatorPhoto,
                                                 Date dateCreated,
                                                 int totalSwaps,
                                                 int userAccepted,
                                                 int requestStatus) {

        SwapResultFragment swapResultFragment = new SwapResultFragment();
        swapResultFragment.rvAdapter = rvAdapter;

        swapResultFragment.requestId = requestId;
        swapResultFragment.creatorBracuId = creatorBracuId;
        swapResultFragment.creatorName = creatorName;
        swapResultFragment.creatorPhoto = creatorPhoto;
        swapResultFragment.dateCreated = dateCreated;
        swapResultFragment.totalSwaps = totalSwaps;
        swapResultFragment.userAccepted = userAccepted;
        swapResultFragment.requestStatus = requestStatus;

        return swapResultFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());
        rootView = inflater.inflate(R.layout.fragment_swap_result, container, false);

        btnAccept = rootView.findViewById(R.id.btnAccept);
        btnDecline = rootView.findViewById(R.id.btnDecline);
        txtCreator = rootView.findViewById(R.id.txtCreator);
        txtDateCreated = rootView.findViewById(R.id.txtDateCreated);
        txtTotalSwaps = rootView.findViewById(R.id.txtTotalSwaps);
        txtStatus = rootView.findViewById(R.id.txtStatus);
        layoutInfo = rootView.findViewById(R.id.layoutInfo);
        rvSwapResult = rootView.findViewById(R.id.rvSwapResult);

        txtCreator.setText(creatorName);
        txtDateCreated.setText(Utility.simpleDateFormat(dateCreated));
        txtTotalSwaps.setText(String.valueOf(totalSwaps));

        if (requestStatus == SecSwapCardInfoModel.APPROVED) { // #C6FFCF  #89FF9C
            txtStatus.setText(R.string.approved);
            layoutInfo.setBackgroundColor(Color.parseColor("#89FF9C"));
        } else if (requestStatus == SecSwapCardInfoModel.DECLINED) {
            txtStatus.setText(R.string.declined);
            layoutInfo.setBackgroundColor(Color.parseColor("#FAA099"));
        } else if (userAccepted == SecSwapCardInfoModel.APPROVED) {
            txtStatus.setText(R.string.accepted);
            layoutInfo.setBackgroundColor(Color.parseColor("#C6FFCF"));
        } else if (userAccepted == SecSwapCardInfoModel.DECLINED) {
            txtStatus.setText(R.string.declined);
            layoutInfo.setBackgroundColor(Color.parseColor("#FFB7B2"));
        } else {
            btnAccept.setVisibility(View.VISIBLE);
            btnDecline.setVisibility(View.VISIBLE);
            txtStatus.setVisibility(View.GONE);
        }

        rvSwapResult.setAdapter(rvAdapter);
        rvSwapResult.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }
}