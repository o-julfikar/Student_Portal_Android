package com.zulfikar.studentportal.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.models.EnrollCourse;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollCourseFragment extends Fragment {

    View rootView;
    RecyclerView rvEnrolledCourses;
    Spinner cboSemester, cboYear;
    EditText txtCourse;
    Button btnAdd;

    String TAG = "EnrolledCourseFragment";

    public EnrollCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_enroll_course, container, false);

        SessionManager.auth(getContext());
        rvEnrolledCourses = rootView.findViewById(R.id.rvEnrolledCourses);
        cboSemester = rootView.findViewById(R.id.cboSemester);
        cboYear = rootView.findViewById(R.id.cboYear);
        txtCourse = rootView.findViewById(R.id.txtCourse);
        btnAdd = rootView.findViewById(R.id.btnAdd);

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        refreshEnrolledCourses();
        cboYear.setSelection(currentYear - Integer.parseInt((String) cboYear.getAdapter().getItem(0)), true);

        btnAdd.setOnClickListener(v -> {
            EnrollCourse enrollCourse = new EnrollCourse(
                    txtCourse.getText().toString().toUpperCase(),
                    cboSemester.getSelectedItem().toString() + " " + cboYear.getSelectedItem().toString());
            JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
            Call<Boolean> addEnrollCourseCall = jsonPlaceHolderApi.addEnrollCourse(enrollCourse);
            addEnrollCourseCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Boolean result = response.body();
                        assert result != null;
                        if (result) {
                            refreshEnrolledCourses();
                            txtCourse.setText("");
                        }
                    } else {
                        Log.e(TAG, "Code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });
        });


        return rootView;
    }

    public void refreshEnrolledCourses() {
        SessionManager.auth(getContext());
        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
        Call<List<Map<String, String>>> rvEnrolledCoursesCall = jsonPlaceHolderApi.getEnrolledCourses();
        rvEnrolledCoursesCall.enqueue(new Callback<List<Map<String, String>>>() {
            @Override
            public void onResponse(Call<List<Map<String, String>>> call, Response<List<Map<String, String>>> response) {
                if (response.isSuccessful()) {
                    List<Map<String, String>> rvEnrolledCoursesResponse = response.body();
                    assert rvEnrolledCoursesResponse != null;
                    ArrayList<RVEnrolledCourseCard> rvEnrolledCoursesList = new ArrayList<>();
                    for (Map<String, String> enrolledCourse : rvEnrolledCoursesResponse) {
                        rvEnrolledCoursesList.add(new RVEnrolledCourseCard(enrolledCourse.get("course") + " - " + enrolledCourse.get("semester")));
                    }

                    RVEnrolledCourseCardAdapter rvEnrolledCourseCardAdapter =
                            new RVEnrolledCourseCardAdapter(
                                    getContext(),
                                    rvEnrolledCoursesList,
                                    EnrollCourseFragment.this
                            );
                    rvEnrolledCourses.setAdapter(rvEnrolledCourseCardAdapter);
                    rvEnrolledCourses.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, String>>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}