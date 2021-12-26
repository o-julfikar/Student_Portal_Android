package com.zulfikar.studentportal.forum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.forum.models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPostFragment extends Fragment {

    View rootView;
    EditText txtNewPost;
    Spinner cboEnrolledCourses;
    Button btnPost;


    public NewPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());
        rootView = inflater.inflate(R.layout.fragment_new_post, container, false);

        txtNewPost = rootView.findViewById(R.id.txtNewPost);
        cboEnrolledCourses = rootView.findViewById(R.id.cboEnrolledCourses);
        btnPost = rootView.findViewById(R.id.btnPost);

        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
        Call<List<Map<String, String>>> enrolledCoursesCall = jsonPlaceHolderApi.getEnrolledCourses();
        enrolledCoursesCall.enqueue(new Callback<List<Map<String, String>>>() {
            @Override
            public void onResponse(Call<List<Map<String, String>>> call, Response<List<Map<String, String>>> response) {
                if (response.isSuccessful()) {
                    List<Map<String, String>> enrolledCoursesResponse = response.body();
                    assert enrolledCoursesResponse != null;
                    ArrayList<String> enrolledCourses = new ArrayList<>();
                    for (Map<String, String> enrolledCourse : enrolledCoursesResponse) {
                        enrolledCourses.add(enrolledCourse.get("course") + " - " + enrolledCourse.get("semester"));
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, enrolledCourses);
                    cboEnrolledCourses.setAdapter(arrayAdapter);
                } else {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, String>>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnPost.setOnClickListener(v -> {
            SessionManager.auth(getContext());
            String courseSemester = cboEnrolledCourses.getSelectedItem() == null? "" : cboEnrolledCourses.getSelectedItem().toString();
            Post post = null;
            if (courseSemester.contains(" - ")) {
                String[] courseSemesterSplit = courseSemester.split(" - ");
                if (courseSemesterSplit.length == 2) {
                    String course = courseSemesterSplit[0];
                    String semester = courseSemesterSplit[1];
                    post = new Post(
                            txtNewPost.getText().toString(),
                            course, semester);
                }
            }
            if (post == null) post = new Post(txtNewPost.getText().toString());
            Call<Boolean> createPostCall = jsonPlaceHolderApi.createPost(post);
            createPostCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body()) {
                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new ForumFragment()).commit();
                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.headerFragmentContainer, new ForumHeaderFragment()).commit();
                        }
                    } else {
                        Toast.makeText(getContext(), "Error code: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        return rootView;
    }
}