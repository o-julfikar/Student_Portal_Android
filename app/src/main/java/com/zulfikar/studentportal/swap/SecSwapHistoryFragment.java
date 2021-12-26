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
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.swap.adapters.SecSwapHistoryRowAdapter;
import com.zulfikar.studentportal.swap.adapters.StudySwapHistoryRowAdapter;
import com.zulfikar.studentportal.swap.models.SecSwapRequest;
import com.zulfikar.studentportal.swap.models.StudySwapRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecSwapHistoryFragment extends Fragment {

    Button btnRefresh;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Call<List<SecSwapRequest>> secSwapRequestsCall;
    RecyclerView rvSectionSwapHistory;
    View rootView;

    public SecSwapHistoryFragment() {
        jsonPlaceHolderApi = Client.getApi(getContext());
        secSwapRequestsCall = jsonPlaceHolderApi.getSecSwapRequests();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sec_swap_history, container, false);

        btnRefresh = rootView.findViewById(R.id.btnRefresh);
        rvSectionSwapHistory = rootView.findViewById(R.id.rvSectionSwapHistory);

        btnRefresh.setOnClickListener(v -> refreshHistory());
        refreshHistory();

        return rootView;
    }

    public void refreshHistory() {
        secSwapRequestsCall.enqueue(new Callback<List<SecSwapRequest>>() {
            @Override
            public void onResponse(Call<List<SecSwapRequest>> call, Response<List<SecSwapRequest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<SecSwapRequest> secSwapRequests = new ArrayList<>(response.body());
                    SecSwapHistoryRowAdapter secSwapHistoryRowAdapter = new SecSwapHistoryRowAdapter(
                            getContext(),
                            secSwapRequests
                    );
                    rvSectionSwapHistory.setAdapter(secSwapHistoryRowAdapter);
                    rvSectionSwapHistory.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }

            @Override
            public void onFailure(Call<List<SecSwapRequest>> call, Throwable t) {

            }

        });
    }
}