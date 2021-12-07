package com.zulfikar.studentportal.swap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.zulfikar.studentportal.Assets;
import com.zulfikar.studentportal.R;

import java.util.ArrayList;

public class StudySwapOptionsFragment extends Fragment {

    RecyclerView rvTeach, rvLearn, rvSlot;
    Spinner cboTeachCourse, cboLearnCourse, cboSlotDay, cboSlotTime, cboCourse;
    Button btnTeachAdd, btnLearnAdd, btnSlotAdd, btnStudyFind;
    View rootView;

    public StudySwapOptionsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        btnStudyFindOnClick();

        return  rootView;
    }

    private void btnStudyFindOnClick() {
        ArrayList<StudySwapCard> swapCards = new ArrayList<>();
        swapCards.add(new StudySwapCard(0, 1, 0, 0, "Mohammad Zulfikar Ali Mahbub", Assets.defaultImg, "G M Sohanur Rahman", Assets.defaultImg, "CSE110", "8:00 AM | MON"));
        swapCards.add(new StudySwapCard(1, 2, 1, 1, "G M Sohanur Rahman", Assets.defaultImg, "Prioty Saha Tonny", Assets.defaultImg, "CSE421", "9:30 AM | WED"));
        swapCards.add(new StudySwapCard(2, 3, 2, 2, "Prioty Saha Tonny", Assets.defaultImg, "Md. Imtiyaz Bhuiyan", Assets.defaultImg, "CSE341", "2:00 PM | THU"));
        swapCards.add(new StudySwapCard(3, 0, 3, 3, "Md. Imtiyaz Bhuiyan", Assets.defaultImg, "Mohammad Zulfikar Ali Mahbub", Assets.defaultImg, "CSE425", "11:00 AM | SAT"));

        StudySwapCardAdapter studySwapCardAdapter = new StudySwapCardAdapter(getContext(), swapCards);

        Fragment swapResultFragment = SwapResultFragment.newInstance(studySwapCardAdapter);

        btnStudyFind.setOnClickListener(v -> StudySwapOptionsFragment.this.requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.swapFragmentContainer, swapResultFragment).commit());

    }

}