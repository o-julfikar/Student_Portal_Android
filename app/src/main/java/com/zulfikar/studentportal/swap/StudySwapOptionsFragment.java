package com.zulfikar.studentportal.swap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.zulfikar.studentportal.Assets;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.swap.adapters.UserLearnsAdapter;
import com.zulfikar.studentportal.swap.adapters.UserStudySlotsAdapter;
import com.zulfikar.studentportal.swap.adapters.UserTeachesAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudySwapOptionsFragment extends Fragment {

    RecyclerView rvTeach, rvLearn, rvSlot;
    Spinner cboTeachCourse, cboLearnCourse, cboSlotDay, cboSlotTime, cboCourse;
    Button btnTeachAdd, btnLearnAdd, btnSlotAdd, btnStudyFind, btnShowHistory;
    View rootView;

    public StudySwapOptionsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());
        rootView = inflater.inflate(R.layout.fragment_study_swap_options, container, false);

        rvTeach = rootView.findViewById(R.id.rvTeach);
        rvLearn = rootView.findViewById(R.id.rvLearn);
        rvSlot = rootView.findViewById(R.id.rvSlot);
        cboTeachCourse = rootView.findViewById(R.id.cboTeachCourse);
        cboLearnCourse = rootView.findViewById(R.id.cboLearnCourse);
        cboSlotDay = rootView.findViewById(R.id.cboSlotDay);
        cboSlotTime = rootView.findViewById(R.id.cboSlotTime);
        cboCourse = rootView.findViewById(R.id.cboCourse);
        btnTeachAdd = rootView.findViewById(R.id.btnTeachAdd);
        btnLearnAdd = rootView.findViewById(R.id.btnLearnAdd);
        btnSlotAdd = rootView.findViewById(R.id.btnSlotAdd);
        btnStudyFind = rootView.findViewById(R.id.btnStudyFind);
        btnShowHistory = rootView.findViewById(R.id.btnShowHistory);

        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
        Call<List<String>> studySlotsCall = jsonPlaceHolderApi.getStudySlots();
        Call<List<String>> teachesCall = jsonPlaceHolderApi.getTeaches();
        Call<List<String>> learnsCall = jsonPlaceHolderApi.getLearns();
        Call<List<String>> courseCall = jsonPlaceHolderApi.getCourse();
        Call<List<String>> slotDaysCall = jsonPlaceHolderApi.getSlotDays();
        Call<List<String>> slotTimesCall = jsonPlaceHolderApi.getSlotTimes();

        studySlotsCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ArrayList<String> studySlots = new ArrayList<>(response.body());
                        rvSlot.setAdapter(new UserStudySlotsAdapter(
                                getContext(),
                                studySlots
                        ));
                        rvSlot.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

        teachesCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<String> teaches = new ArrayList<>(response.body());
                    rvTeach.setAdapter(new UserTeachesAdapter(
                            getContext(),
                            teaches
                    ));
                    rvTeach.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

        learnsCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<String> learns = new ArrayList<>(response.body());
                    rvLearn.setAdapter(new UserLearnsAdapter(
                            getContext(),
                            learns
                    ));
                    rvLearn.setLayoutManager(new LinearLayoutManager(getContext()));
                    cboCourse.setAdapter(new ArrayAdapter<String>(
                            getContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            learns
                    ));
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

        courseCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> courses = response.body();
                    cboTeachCourse.setAdapter(new ArrayAdapter<String>(
                            getContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            courses
                    ));
                    cboLearnCourse.setAdapter(new ArrayAdapter<>(
                            getContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            courses
                    ));
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

        slotDaysCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> slotDays = response.body();
                    cboSlotDay.setAdapter(new ArrayAdapter<>(
                            getContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            slotDays
                    ));
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

        slotTimesCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> slotTimes = response.body();
                    cboSlotTime.setAdapter(new ArrayAdapter<>(
                            getContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            slotTimes
                    ));
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

        btnStudyFindOnClick();
        btnShowHistory.setOnClickListener(v -> {
            StudySwapOptionsFragment.this.requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.swapFragmentContainer, new StudySwapHistoryFragment()).commit();

        });

        return  rootView;
    }

    private void btnStudyFindOnClick() {
        SessionManager.auth(getContext());
        ArrayList<StudySwapCard> swapCards = new ArrayList<>();
        swapCards.add(new StudySwapCard(0, 1, 0, 0, "Mohammad Zulfikar Ali Mahbub", Assets.defaultUserphoto, "G M Sohanur Rahman", Assets.defaultUserphoto, "CSE110", "8:00 AM | MON"));
        swapCards.add(new StudySwapCard(1, 2, 1, 1, "G M Sohanur Rahman", Assets.defaultUserphoto, "Prioty Saha Tonny", Assets.defaultUserphoto, "CSE421", "9:30 AM | WED"));
        swapCards.add(new StudySwapCard(2, 3, 2, 2, "Prioty Saha Tonny", Assets.defaultUserphoto, "Md. Imtiyaz Bhuiyan", Assets.defaultUserphoto, "CSE341", "2:00 PM | THU"));
        swapCards.add(new StudySwapCard(3, 0, 3, 3, "Md. Imtiyaz Bhuiyan", Assets.defaultUserphoto, "Mohammad Zulfikar Ali Mahbub", Assets.defaultUserphoto, "CSE425", "11:00 AM | SAT"));

        StudySwapCardAdapter studySwapCardAdapter = new StudySwapCardAdapter(getContext(), swapCards);

        Fragment swapResultFragment = SwapResultFragment.newInstance(studySwapCardAdapter);

        btnStudyFind.setOnClickListener(v -> {
        });
//                StudySwapOptionsFragment.this.requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.swapFragmentContainer, swapResultFragment).commit()

    }

}