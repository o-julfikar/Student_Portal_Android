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
import android.widget.Toast;

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
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionSwapOptionsFragment extends Fragment {

    RecyclerView rvSectionOffer, rvSectionPrefer;
    Spinner cboOfferCourse, cboOfferSection, cboPreferCourse, cboPreferSection, cboSection;
    Button btnSectionOfferAdd, btnSectionPreferAdd, btnSectionFind, btnShowHistory;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Call<List<String>>
            getUserOffersCall,
            getUserPrefersCall,
            getCourseCall;
    Call<Boolean>
            postUserOffersCall,
            postUserPrefersCall,
            deleteUserOffersCall,
            deleteUserPrefersCall;

    Call<CourseSection> getCourseSectionCall;

    View rootView;

    String TAG = "SectionSwapOptionsFragment";

    public SectionSwapOptionsFragment() {
        jsonPlaceHolderApi = Client.getApi(getContext());
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

        getCourseCall = jsonPlaceHolderApi.getCourse();
        getCourseCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
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

        btnSectionOfferAdd.setOnClickListener(v -> {
            if (!verifySelection("Please select an offer course and section first.",
                    cboOfferCourse, cboOfferSection)) {
                return;
            }
            UserOffers userOffer = new UserOffers(
                    getCboText(cboOfferCourse),
                    getCboText(cboOfferSection));

            postUserOffersCall = jsonPlaceHolderApi.postUserOffer(userOffer);
            postUserOffersCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body()) {
                            loadOffers();
                        } else {
                            showToast("Failed to add the offer. Please try again later");
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        });

        btnSectionPreferAdd.setOnClickListener(v -> {
            if (!verifySelection("Please select a prefer course and section first.",
                    cboPreferCourse, cboPreferSection)) {
                return;
            }
            UserPrefers userPrefer = new UserPrefers(
                    getCboText(cboPreferCourse),
                    getCboText(cboPreferSection)
            );
            postUserPrefersCall = jsonPlaceHolderApi.postUserPrefer(userPrefer);
            postUserPrefersCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body()) {
                            loadPrefers();
                        } else {
                            showToast("Failed to add the selected preferred course section. " +
                                    "Please try again later.");
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        });

        btnSectionFindOnClick();
        btnShowHistory.setOnClickListener(v -> {
            SectionSwapOptionsFragment.this.requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.swapFragmentContainer, new SecSwapHistoryFragment())
                    .commit();
        });

        loadOffers();
        loadPrefers();

        return rootView;
    }

    private String getCboText(Spinner spinner) {
        Object selectedItem = spinner.getSelectedItem();
        if (selectedItem instanceof String) return (String) selectedItem;
        else if (selectedItem instanceof TextView) return ((TextView) spinner.getSelectedItem()).getText().toString();
        return "";
    }

    private boolean verifySelection(String msg, Spinner ...spinners) {
        for (Spinner spinner : spinners) {
            if (spinner.getSelectedItemPosition() == 0) {
                showToast(msg);
                return false;
            }
        }

        return true;
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void loadOffers() {
        getUserOffersCall = jsonPlaceHolderApi.getOffers();
        getUserOffersCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> userOffers = response.body();
                    if (userOffers != null) {
                        ArrayList<String> offersArrayList = new ArrayList<>(userOffers);
                        UserOffersAdapter offersAdapter = new UserOffersAdapter(getContext(),
                                offersArrayList,
                                SectionSwapOptionsFragment.this);
                        rvSectionOffer.setAdapter(offersAdapter);
                        rvSectionOffer.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void loadPrefers() {
        getUserPrefersCall = jsonPlaceHolderApi.getPrefers();
        getUserPrefersCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> userPrefers = response.body();
                    if (userPrefers != null) {
                        ArrayList<String> prefersArrayList = new ArrayList<>(userPrefers);
                        UserPrefersAdapter userPrefersAdapter = new UserPrefersAdapter(
                                getContext(),
                                prefersArrayList,
                                SectionSwapOptionsFragment.this);
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
    }

    public void deleteOffer(String courseCode, String sectionNumber) {
        UserOffers userOffer = new UserOffers(courseCode, sectionNumber);
        deleteUserOffersCall = jsonPlaceHolderApi.deleteUserOffer(userOffer);
        deleteUserOffersCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body()) {
                        loadOffers();
                    } else {
                        showToast("Failed to delete the offer at the moment. " +
                                "Please try again later.");
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void deletePrefer(String courseCode, String sectionNumber) {
        UserPrefers userPrefer = new UserPrefers(courseCode, sectionNumber);
        deleteUserPrefersCall = jsonPlaceHolderApi.deleteUserPrefer(userPrefer);
        deleteUserPrefersCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body()) {
                        loadPrefers();
                    } else {
                        showToast("Failed to delete the prefer at the moment. " +
                                "Please try again later.");
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    private void onCourseSelected(Spinner cboSection, String courseCode) {
        getCourseSectionCall = jsonPlaceHolderApi.getCourseSection(courseCode);
        getCourseSectionCall.enqueue(new Callback<CourseSection>() {
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

//        Fragment swapResultFragment = SwapResultFragment.newInstance(sectionSwapCardAdapter);

        btnSectionFind.setOnClickListener(v -> {
//            SectionSwapOptionsFragment.this.requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.swapFragmentContainer, swapResultFragment).commit()
        });
    }
}