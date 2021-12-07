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

public class SectionSwapOptionsFragment extends Fragment {

    RecyclerView rvSectionOffer, rvSectionPrefer;
    Spinner cboOfferCourse, cboOfferSection, cboPreferCourse, cboPreferSection, cboSection;
    Button btnSectionOfferAdd, btnSectionPreferAdd, btnSectionFind;

    View rootView;
    public SectionSwapOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_section_swap_options, container, false);

        rvSectionOffer = rootView.findViewById(R.id.rvSectionOffer);
        rvSectionPrefer = rootView.findViewById(R.id.rvSectionPrefer);
        cboOfferCourse = rootView.findViewById(R.id.cboOfferCourse);
        cboOfferSection = rootView.findViewById(R.id.cboOfferSection);
        cboPreferCourse = rootView.findViewById(R.id.cboPreferCourse);
        cboPreferSection = rootView.findViewById(R.id.cboPreferSection);
        cboSection = rootView.findViewById(R.id.cboSection);
        btnSectionOfferAdd = rootView.findViewById(R.id.btnSectionOfferAdd);
        btnSectionPreferAdd = rootView.findViewById(R.id.btnSectionPreferAdd);
        btnSectionFind = rootView.findViewById(R.id.btnSectionFind);

        btnSectionFindOnClick();

        return rootView;
    }

    public void btnSectionFindOnClick() {
        ArrayList<SectionSwapCard> swapCards = new ArrayList<>();
        swapCards.add(new SectionSwapCard(0, 1, 0, 0, "Mohammad Zulfikar Ali Mahbub", Assets.defaultImg, "G M Sohanur Rahman", Assets.defaultImg, "CSE110", 2));
        swapCards.add(new SectionSwapCard(1, 2, 1, 1, "G M Sohanur Rahman", Assets.defaultImg, "Prioty Saha Tonny", Assets.defaultImg, "CSE421", 7));
        swapCards.add(new SectionSwapCard(2, 3, 2, 2, "Prioty Saha Tonny", Assets.defaultImg, "Md. Imtiyaz Bhuiyan", Assets.defaultImg, "CSE341", 9));
        swapCards.add(new SectionSwapCard(3, 0, 3, 3, "Md. Imtiyaz Bhuiyan", Assets.defaultImg, "Mohammad Zulfikar Ali Mahbub", Assets.defaultImg, "CSE425", 1));

        SectionSwapCardAdapter sectionSwapCardAdapter = new SectionSwapCardAdapter(getContext(), swapCards);

        Fragment swapResultFragment = SwapResultFragment.newInstance(sectionSwapCardAdapter);

        btnSectionFind.setOnClickListener(v -> SectionSwapOptionsFragment.this.requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.swapFragmentContainer, swapResultFragment).commit());
    }
}