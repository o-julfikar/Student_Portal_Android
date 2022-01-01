package com.zulfikar.studentportal.review.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.review.DetailReviewFragment;

import java.util.List;

public class ReviewCourseAdapter extends RecyclerView.Adapter<ReviewCourseAdapter.ReviewCourseViewHolder> {

    Context context;
    List<String> courses;
    DetailReviewFragment detailReviewFragment;

    public ReviewCourseAdapter(Context context, List<String> courses, DetailReviewFragment detailReviewFragment) {
        this.context = context;
        this.courses = courses;
        this.detailReviewFragment = detailReviewFragment;
    }

    @NonNull
    @Override
    public ReviewCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View courseRow = LayoutInflater.from(context)
                .inflate(R.layout.row_review_course, parent, false);

        return new ReviewCourseViewHolder(courseRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewCourseViewHolder holder, int position) {
        holder.txtCourseCode.setText(courses.get(position));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ReviewCourseViewHolder extends RecyclerView.ViewHolder {

        TextView txtCourseCode;

        public ReviewCourseViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCourseCode = itemView.findViewById(R.id.txtCourseCode);

        }
    }

}
