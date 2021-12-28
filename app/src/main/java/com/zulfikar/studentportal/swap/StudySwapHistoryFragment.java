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
import com.zulfikar.studentportal.swap.adapters.StudySwapHistoryRowAdapter;
import com.zulfikar.studentportal.swap.models.SecSwapRequest;
import com.zulfikar.studentportal.swap.models.StudySwapRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudySwapHistoryFragment extends Fragment {

    Button btnRefresh;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Call<List<StudySwapRequest>> studySwapRequestsCall;
    RecyclerView rvStudySwapHistory;
    View rootView;

    public StudySwapHistoryFragment() {
        jsonPlaceHolderApi = Client.getApi(getContext());
        studySwapRequestsCall = jsonPlaceHolderApi.getStudySwapRequests();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_study_swap_history, container, false);

        btnRefresh = rootView.findViewById(R.id.btnRefresh);
        rvStudySwapHistory = rootView.findViewById(R.id.rvStudySwapHistory);

        btnRefresh.setOnClickListener(v -> {
            refreshHistory();
        });

        refreshHistory();

        return rootView;
    }

    public void refreshHistory() {
        studySwapRequestsCall.enqueue(new Callback<List<StudySwapRequest>>() {
            @Override
            public void onResponse(Call<List<StudySwapRequest>> call, Response<List<StudySwapRequest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<StudySwapRequest> studySwapRequests = new ArrayList<>(response.body());
                    StudySwapHistoryRowAdapter studySwapHistoryRowAdapter = new StudySwapHistoryRowAdapter(
                            getContext(),
                            studySwapRequests,
                            StudySwapHistoryFragment.this
                    );
                    rvStudySwapHistory.setAdapter(studySwapHistoryRowAdapter);
                    rvStudySwapHistory.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }

            @Override
            public void onFailure(Call<List<StudySwapRequest>> call, Throwable t) {

            }
        });
    }
}