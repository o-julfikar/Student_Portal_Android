package com.zulfikar.studentportal.swap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.Assets;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.swap.adapters.UserOffersAdapter;
import com.zulfikar.studentportal.swap.adapters.UserPrefersAdapter;
import com.zulfikar.studentportal.swap.models.Course;
import com.zulfikar.studentportal.swap.models.CourseSection;
import com.zulfikar.studentportal.swap.models.UserOffers;
import com.zulfikar.studentportal.swap.models.UserPrefers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionSwapOptionsFragment extends Fragment {

    RecyclerView rvSectionOffer, rvSectionPrefer;
    Spinner cboOfferCourse, cboOfferSection, cboPreferCourse, cboPreferSection, cboSection;
    Button btnSectionOfferAdd, btnSectionPreferAdd, btnSectionFind, btnShowHistory;

    View rootView;

    String TAG = "SectionSwapOptionsFragment";

    public SectionSwapOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());
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
        btnShowHistory = rootView.findViewById(R.id.btnShowHistory);

        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
        Call<List<String>> userOffersCall = jsonPlaceHolderApi.getOffers();
        Call<List<String>> userPrefersCall = jsonPlaceHolderApi.getPrefers();
        Call<List<String>> courseCall = jsonPlaceHolderApi.getCourse();

        userOffersCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> userOffers = response.body();
                    if (userOffers != null) {
                        ArrayList<String> offersArrayList = new ArrayList<>(userOffers);
                        UserOffersAdapter offersAdapter = new UserOffersAdapter(getContext(), offersArrayList);
                        rvSectionOffer.setAdapter(offersAdapter);
                        rvSectionOffer.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e(TAG, t.getMessage() );
            }
        });

        userPrefersCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> userPrefers = response.body();
                    if (userPrefers != null) {
                        ArrayList<String> prefersArrayList = new ArrayList<>(userPrefers);
                        UserPrefersAdapter userPrefersAdapter = new UserPrefersAdapter(getContext(), prefersArrayList);
                        rvSectionPrefer.setAdapter(userPrefersAdapter);
                        rvSectionPrefer.setLayoutManager(new LinearLayoutManager(getContext()));
                        ArrayList<String> prefersList = new ArrayList<>(Collections.singletonList("Select a preferred course"));
                        prefersList.addAll(prefersArrayList);
                        cboSection.setAdapter(
                                new ArrayAdapter<String>(
                                        getContext(),
                                        R.layout.support_simple_spinner_dropdown_item,
                                        prefersList
                                ));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

        courseCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
//                        Log.e(TAG, "onResponse: " + course.getCourseCode().toString());
                        List<String> course = new ArrayList<>(Collections.singletonList("Select a course"));
                        course.addAll(response.body());
                        ArrayAdapter<String> courseOfferAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, course);
                        ArrayAdapter<String> coursePreferAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, course);
                        cboOfferCourse.setAdapter(courseOfferAdapter);
                        cboPreferCourse.setAdapter(coursePreferAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

        cboOfferCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null && position > 0) {
                    onCourseSelected(cboOfferSection, ((TextView) view).getText().toString());
                } else {
                    onCourseDefaultSelected(cboOfferSection);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cboPreferCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null && position > 0) {
                    onCourseSelected(cboPreferSection, ((TextView) view).getText().toString());
                } else {
                    onCourseDefaultSelected(cboPreferSection);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSectionFindOnClick();
        btnShowHistory.setOnClickListener(v -> {
            SectionSwapOptionsFragment.this.requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.swapFragmentContainer, new SecSwapHistoryFragment())
                    .commit();
        });

        return rootView;
    }

    private void onCourseSelected(Spinner cboSection, String courseCode) {
        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
        Call<CourseSection> courseSectionCall = jsonPlaceHolderApi.getCourseSection(courseCode);
        courseSectionCall.enqueue(new Callback<CourseSection>() {
            @Override
            public void onResponse(Call<CourseSection> call, Response<CourseSection> response) {
                if (response.isSuccessful()) {
                    CourseSection courseSection = response.body();
                    if (courseSection != null) {
                        ArrayList<String> sections = new ArrayList<>(new ArrayList<>(Collections.singletonList("Select a section")));
                        for (int section : courseSection.getSections()) {
                            sections.add(String.valueOf(section));
                        }
                        ArrayAdapter<String> sectionAdapter;
                        sectionAdapter = new ArrayAdapter<>(
                                getContext(),
                                R.layout.support_simple_spinner_dropdown_item,
                                sections
                        );
                        cboSection.setAdapter(sectionAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<CourseSection> call, Throwable t) {

            }
        });
    }

    private void onCourseDefaultSelected(Spinner cboSection) {
        cboSection.setAdapter(
                new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                Collections.singletonList("Select a section")));
    }

    public void btnSectionFindOnClick() {
        SessionManager.auth(getContext());
        ArrayList<SectionSwapCard> swapCards = new ArrayList<>();
        swapCards.add(new SectionSwapCard(0, 1, 0, 0, "Mohammad Zulfikar Ali Mahbub", Assets.defaultUserphoto, "G M Sohanur Rahman", Assets.defaultUserphoto, "CSE110", 2));
        swapCards.add(new SectionSwapCard(1, 2, 1, 1, "G M Sohanur Rahman", Assets.defaultUserphoto, "Prioty Saha Tonny", Assets.defaultUserphoto, "CSE421", 7));
        swapCards.add(new SectionSwapCard(2, 3, 2, 2, "Prioty Saha Tonny", Assets.defaultUserphoto, "Md. Imtiyaz Bhuiyan", Assets.defaultUserphoto, "CSE341", 9));
        swapCards.add(new SectionSwapCard(3, 0, 3, 3, "Md. Imtiyaz Bhuiyan", Assets.defaultUserphoto, "Mohammad Zulfikar Ali Mahbub", Assets.defaultUserphoto, "CSE425", 1));

        SectionSwapCardAdapter sectionSwapCardAdapter = new SectionSwapCardAdapter(getContext(), swapCards);

        Fragment swapResultFragment = SwapResultFragment.newInstance(sectionSwapCardAdapter);

        btnSectionFind.setOnClickListener(v -> {
//            SectionSwapOptionsFragment.this.requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.swapFragmentContainer, swapResultFragment).commit()
        });
    }
}