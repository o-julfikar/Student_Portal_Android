package com.zulfikar.studentportal.swap;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.R;

import java.util.ArrayList;

public class StudySwapCardAdapter extends RecyclerView.Adapter<StudySwapCardAdapter.StudySwapCardHolder> {


    ArrayList<StudySwapCard> studySwapCards;
    Context context;
    Handler handler;

    public StudySwapCardAdapter(Context context, ArrayList<StudySwapCard> studySwapCards) {
        this.context = context;
        this.studySwapCards = studySwapCards;
        handler = new Handler();
    }

    @NonNull
    @Override
    public StudySwapCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudySwapCardAdapter.StudySwapCardHolder(LayoutInflater.from(context).inflate(R.layout.card_swap_study, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudySwapCardHolder holder, int position) {
        new FetchImage(holder.imgTeacher, studySwapCards.get(position).getTeacherPhoto(), handler);
        new FetchImage(holder.imgLearner, studySwapCards.get(position).getLearnerPhoto(), handler);
        holder.txtTeacherName.setText(studySwapCards.get(position).getTeacherName());
        holder.txtLearnerName.setText(studySwapCards.get(position).getLearnerName());
        holder.txtCourseCode.setText(studySwapCards.get(position).getCourseCode());
        holder.txtSlot.setText(studySwapCards.get(position).getSlot());
    }

    @Override
    public int getItemCount() {
        return studySwapCards.size();
    }

    public static class StudySwapCardHolder extends RecyclerView.ViewHolder {

        ShapeableImageView imgTeacher, imgLearner;
        TextView txtTeacherName, txtLearnerName, txtCourseCode, txtSlot;

        public StudySwapCardHolder(@NonNull View itemView) {
            super(itemView);

            imgTeacher = itemView.findViewById(R.id.imgTeacher);
            imgLearner = itemView.findViewById(R.id.imgLearner);
            txtTeacherName = itemView.findViewById(R.id.txtTeacher);
            txtLearnerName = itemView.findViewById(R.id.txtLearner);
            txtCourseCode = itemView.findViewById(R.id.txtCourse);
            txtSlot = itemView.findViewById(R.id.txtSlot);
        }
    }
}
