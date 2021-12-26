package com.zulfikar.studentportal.swap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;

public class SwapHeaderFragment extends Fragment {

    private final int SECTION = 0, STUDY = 1;

    Spinner cboSwapOptions;
    View rootView;
    public SwapHeaderFragment() {

    }

    public SwapHeaderFragment newInstance() {
        SwapHeaderFragment fragment = new SwapHeaderFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());
        rootView = inflater.inflate(R.layout.fragment_swap_header, container, false);

        cboSwapOptions = rootView.findViewById(R.id.cboSwapOptions);
        cboSwapOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Fragment swapFragment = null;
                if (position == SECTION) {
                    swapFragment = new SectionSwapOptionsFragment();
                } else  if (position == STUDY) {
                    swapFragment = new StudySwapOptionsFragment();
                }
                if (swapFragment == null) return;
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.swapFragmentContainer, swapFragment).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }


}