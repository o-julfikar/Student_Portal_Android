package com.zulfikar.studentportal.swap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.swap.adapters.UserLearnsAdapter;
import com.zulfikar.studentportal.swap.adapters.UserStudySlotsAdapter;
import com.zulfikar.studentportal.swap.adapters.UserTeachesAdapter;
import com.zulfikar.studentportal.swap.models.StudySwapCardInfoModel;
import com.zulfikar.studentportal.swap.models.UserLearns;
import com.zulfikar.studentportal.swap.models.UserStudySlots;
import com.zulfikar.studentportal.swap.models.UserTeaches;

import java.util.ArrayList;
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
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Call<List<String>>
            studySlotsCall,
            teachesCall,
            learnsCall,
            courseCall,
            slotDaysCall,
            slotTimesCall;
    Call<Boolean>
            postTeachCourseCall,
            postLearnCourseCall,
            postStudySlotCall,
            deleteTeachCourseCall,
            deleteLearnCourseCall,
            deleteStudySlotCall;


    public StudySwapOptionsFragment() {
        jsonPlaceHolderApi = Client.getApi(getContext());
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

        loadSlots();
        loadTeaches();
        loadLearns();
        loadCourses();
        loadSlotDaysTimes();

        btnTeachAdd.setOnClickListener(v -> {
            if (!verifySelection("Please select a course to teach first", cboTeachCourse)) return;
            UserTeaches userTeach = new UserTeaches(getCboText(cboTeachCourse));
            postTeachCourseCall = jsonPlaceHolderApi.postUserTeach(userTeach);
            postTeachCourseCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body()) {
                            loadTeaches();
                        } else {
                            showToast("Failed to add the selected course to teach list. " +
                                    "Please try again later.");
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        });

        btnLearnAdd.setOnClickListener(v -> {
            if (!verifySelection("Please select a course to learn first.", cboLearnCourse)) return;
            UserLearns userLearn = new UserLearns(getCboText(cboLearnCourse));
            postLearnCourseCall = jsonPlaceHolderApi.postUserLearn(userLearn);
            postLearnCourseCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() &&
                            response.body() != null &&
                            response.body()) {
                        loadLearns();
                    } else {
                        showToast("Failed to add the selected course to learning list. " +
                                "Please try again later.");
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        });

        btnSlotAdd.setOnClickListener(v -> {
            if (!verifySelection("Select slot day and time first.", cboSlotDay, cboSlotTime))
                return;
            UserStudySlots userStudySlot = new UserStudySlots(
                    getCboText(cboSlotDay), getCboText(cboSlotTime)
            );
            postStudySlotCall = jsonPlaceHolderApi.postUserStudySlot(userStudySlot);
            postStudySlotCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() &&
                            response.body() != null && response.body()) {
                        loadSlots();
                    } else {
                        showToast("Failed to add the select day and time for available slots. " +
                                "Please try again later.");
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        });


        btnStudyFindOnClick();
        btnShowHistory.setOnClickListener(v -> {
            StudySwapOptionsFragment.this.requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.swapFragmentContainer, new StudySwapHistoryFragment()).commit();

        });

        return rootView;
    }

    private String getCboText(Spinner spinner) {
        Object selectedItem = spinner.getSelectedItem();
        if (selectedItem instanceof String) return (String) selectedItem;
        else if (selectedItem instanceof TextView)
            return ((TextView) spinner.getSelectedItem()).getText().toString();
        return "";
    }

    private boolean verifySelection(String msg, Spinner... spinners) {
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

    private void loadSlots() {
        studySlotsCall = jsonPlaceHolderApi.getStudySlots();
        studySlotsCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ArrayList<String> studySlots = new ArrayList<>(response.body());
                        rvSlot.setAdapter(new UserStudySlotsAdapter(
                                getContext(),
                                studySlots,
                                StudySwapOptionsFragment.this
                        ));
                        rvSlot.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    private void loadTeaches() {
        teachesCall = jsonPlaceHolderApi.getTeaches();
        teachesCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<String> teaches = new ArrayList<>(response.body());
                    rvTeach.setAdapter(new UserTeachesAdapter(
                            getContext(),
                            teaches,
                            StudySwapOptionsFragment.this
                    ));
                    rvTeach.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    private void loadLearns() {
        learnsCall = jsonPlaceHolderApi.getLearns();
        learnsCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<String> learns = new ArrayList<>(response.body());
                    rvLearn.setAdapter(new UserLearnsAdapter(
                            getContext(),
                            learns,
                            StudySwapOptionsFragment.this
                    ));
                    rvLearn.setLayoutManager(new LinearLayoutManager(getContext()));
                    ArrayList<String> learnsForCbo = new ArrayList<>(Collections
                            .singletonList("Select a preferred course to learn"));
                    learnsForCbo.addAll(learns);
                    cboCourse.setAdapter(new ArrayAdapter<String>(
                            getContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            learnsForCbo
                    ));
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    private void loadCourses() {
        courseCall = jsonPlaceHolderApi.getCourse();
        courseCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<String> courses = new ArrayList<>(Collections.singletonList("Select a course"));
                    courses.addAll(response.body());
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
    }

    private void loadSlotDaysTimes() {
        slotDaysCall = jsonPlaceHolderApi.getSlotDays();
        slotDaysCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> slotDays = new ArrayList<>(Collections.singletonList("Select slot day"));
                    slotDays.addAll(response.body());
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
        slotTimesCall = jsonPlaceHolderApi.getSlotTimes();
        slotTimesCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> slotTimes = new ArrayList<>(Collections
                            .singletonList("Select slot time"));
                    slotTimes.addAll(response.body());
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
    }

    public void deleteTeachCourse(String courseCode) {
        UserTeaches userTeach = new UserTeaches(courseCode);
        deleteTeachCourseCall = jsonPlaceHolderApi.deleteUserTeach(userTeach);
        deleteTeachCourseCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    loadTeaches();
                } else {
                    showToast("Failed to delete the course from teaching list. " +
                            "Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void deleteLearnCourse(String courseCode) {
        UserLearns userLearn = new UserLearns(courseCode);
        deleteLearnCourseCall = jsonPlaceHolderApi.deleteUserLearn(userLearn);
        deleteLearnCourseCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    loadLearns();
                } else {
                    showToast("Failed to delete the course from learning list. " +
                            "Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void deleteStudySlot(String slotDay, String slotTime) {
        UserStudySlots userStudySlot = new UserStudySlots(slotDay, slotTime);
        deleteStudySlotCall = jsonPlaceHolderApi.deleteUserStudySlot(userStudySlot);
        deleteStudySlotCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    loadSlots();
                } else {
                    showToast("Failed to delete the slot from available slots. " +
                            "Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    private void btnStudyFindOnClick() {
        SessionManager.auth(getContext());
        btnStudyFind.setOnClickListener(v -> {
            if (!verifySelection("Please select a preferred course to learn first.",
                    cboCourse)) return;
            UserLearns userLearn = new UserLearns(getCboText(cboCourse));
            Call<Object> postStudySwapRequestCall = jsonPlaceHolderApi
                    .postStudySwapRequest(userLearn);
            postStudySwapRequestCall.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body() instanceof Integer ||
                                response.body() instanceof Double) {
                            int studySwapReqId = -1;
                            if (response.body() instanceof Double) {
                                studySwapReqId = ((Double) response.body()).intValue();
                            } else {
                                studySwapReqId = (int) response.body();
                            }
                            loadCourses();
                            loadLearns();
                            loadTeaches();
                            loadLearns();
                            loadSlots();
                            loadSlotDaysTimes();
                            JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
                            Call<StudySwapCardInfoModel> studySwapCardInfoModelCall = jsonPlaceHolderApi
                                    .getStudySwapCardInfo(studySwapReqId);
                            studySwapCardInfoModelCall.enqueue(new Callback<StudySwapCardInfoModel>() {
                                @Override
                                public void onResponse(Call<StudySwapCardInfoModel> call, Response<StudySwapCardInfoModel> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        StudySwapCardInfoModel studySwapCardInfoModel = response.body();
                                        ArrayList<StudySwapCard> studySwapCards = new ArrayList<>();
                                        for (StudySwapCardInfoModel.StudySwapCardModel studySwapCardModel : studySwapCardInfoModel.getCards()) {
                                            studySwapCards.add(new StudySwapCard(
                                                    studySwapCardModel.getTeacherBracuId(),
                                                    studySwapCardModel.getLearnerBracuId(),
                                                    -1,
                                                    -1,
                                                    studySwapCardModel.getTeacherName(),
                                                    studySwapCardModel.getTeacherPhoto(),
                                                    studySwapCardModel.getLearnerName(),
                                                    studySwapCardModel.getLearnerPhoto(),
                                                    studySwapCardModel.getCourseCode(),
                                                    studySwapCardModel.getStudySlot()
                                            ));
                                        }
                                        StudySwapCardAdapter studySwapCardAdapter = new StudySwapCardAdapter(
                                                getContext(),
                                                studySwapCards
                                        );
                                        Fragment swapResultFragment = SwapResultFragment.newInstance(
                                                studySwapCardAdapter,
                                                studySwapCardInfoModel.getRequestId(),
                                                studySwapCardInfoModel.getCreatorBracuId(),
                                                studySwapCardInfoModel.getCreatorName(),
                                                studySwapCardInfoModel.getCreatorPhoto(),
                                                studySwapCardInfoModel.getDateCreated(),
                                                studySwapCardInfoModel.getTotalSwaps(),
                                                studySwapCardInfoModel.getUserAccepted(),
                                                studySwapCardInfoModel.getRequestStatus(),
                                                SwapResultFragment.STUDY_SWAP
                                        );
                                        requireActivity()
                                                .getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.mainFragmentContainer, swapResultFragment)
                                                .commit();
                                    }
                                }

                                @Override
                                public void onFailure(Call<StudySwapCardInfoModel> call, Throwable t) {

                                }
                            });
                        } else if (response.body() instanceof Boolean) {
                            if (!((Boolean) response.body())) {
                                showToast("Unfortunately, we could not find a " +
                                        "swap matching your requirement at the moment." +
                                        " Please try again later.");
                            }
                        }
                    } else {
                        showToast("Failed to submit the study swap request at the " +
                                "moment. Please try again later.");
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                }
            });
        });
    }

}