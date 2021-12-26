package com.zulfikar.studentportal.account;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.models.EnrollCourse;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RVEnrolledCourseCardAdapter extends RecyclerView.Adapter<RVEnrolledCourseCardAdapter.RVEnrolledCourseCardViewHolder> {

    Context context;
    EnrollCourseFragment enrollCourseFragment;
    ArrayList<RVEnrolledCourseCard> rvEnrolledCourseCards;


    String TAG = "RVEnrolledCourseCardAdapter";

    public RVEnrolledCourseCardAdapter(Context context,
                                       ArrayList<RVEnrolledCourseCard> rvEnrolledCourseCards,
                                       EnrollCourseFragment enrollCourseFragment) {
        this.context = context;
        this.rvEnrolledCourseCards = rvEnrolledCourseCards;
        this.enrollCourseFragment = enrollCourseFragment;
    }

    @NonNull
    @Override
    public RVEnrolledCourseCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rvEnrolledCourseCard = LayoutInflater.from(context).inflate(R.layout.card_enrolled_courses_rv_row, parent, false);

        return new RVEnrolledCourseCardViewHolder(rvEnrolledCourseCard);
    }

    @Override
    public void onBindViewHolder(@NonNull RVEnrolledCourseCardViewHolder holder, int position) {
        holder.txtEnrolledCourse.setText(rvEnrolledCourseCards.get(position).getCourse());
        final int fixedPosition = position;
        holder.imgDelete.setOnClickListener(v -> {
            String courseSemester = holder.txtEnrolledCourse.getText().toString();
            if (courseSemester.contains(" - ")) {
                String[] courseSemesterSplit = courseSemester.split(" - ");
                if (courseSemesterSplit.length == 2) {
                    String course = courseSemesterSplit[0];
                    String semester = courseSemesterSplit[1];
                    EnrollCourse enrollCourse = new EnrollCourse(course, semester);
                    JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(context);
                    Call<Boolean> deleteEnrollCourseCall = jsonPlaceHolderApi.deleteEnrolledCourse(enrollCourse);
                    deleteEnrollCourseCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.isSuccessful()) {
                                enrollCourseFragment.refreshEnrolledCourses();
                            } else {
                                Log.e(TAG, "onResponse: Code: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage());
                        }
                    });
                }
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return rvEnrolledCourseCards.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class RVEnrolledCourseCardViewHolder extends RecyclerView.ViewHolder {

        TextView txtEnrolledCourse;
        ImageView imgDelete;
        View rootView;

        public RVEnrolledCourseCardViewHolder(@NonNull View itemView) {
            super(itemView);

            rootView = itemView;
            txtEnrolledCourse = itemView.findViewById(R.id.txtEnrolledCourse);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
