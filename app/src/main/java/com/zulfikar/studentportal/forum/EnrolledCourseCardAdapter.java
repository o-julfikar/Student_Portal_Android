package com.zulfikar.studentportal.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.R;

import java.util.ArrayList;

public class EnrolledCourseCardAdapter extends RecyclerView.Adapter<EnrolledCourseCardAdapter.EnrolledCourseCardViewHolder> {

    Context context;
    ArrayList<EnrolledCourseCard> enrolledCourseCards;

    public EnrolledCourseCardAdapter(Context context, ArrayList<EnrolledCourseCard> enrolledCourseCards) {
        this.context = context;
        this.enrolledCourseCards = enrolledCourseCards;
    }

    @NonNull
    @Override
    public EnrolledCourseCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View enrolledCourseCard = LayoutInflater.from(context).inflate(R.layout.card_enrolled_course_row, parent, false);

        return new EnrolledCourseCardViewHolder(enrolledCourseCard);
    }

    @Override
    public void onBindViewHolder(@NonNull EnrolledCourseCardViewHolder holder, int position) {
        holder.txtEnrolledCourse.setText(enrolledCourseCards.get(position).getCourse());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return enrolledCourseCards.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class EnrolledCourseCardViewHolder extends RecyclerView.ViewHolder {

        TextView txtEnrolledCourse;

        public EnrolledCourseCardViewHolder(@NonNull View itemView) {
            super(itemView);

            txtEnrolledCourse = itemView.findViewById(R.id.txtEnrolledCourse);
        }
    }
}
