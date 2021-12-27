package com.zulfikar.studentportal.swap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.swap.StudySwapOptionsFragment;

import java.util.ArrayList;

public class UserLearnsAdapter extends RecyclerView.Adapter<UserLearnsAdapter.UserLearnsViewHolder> {

    ArrayList<String> userLearns;
    Context context;
    StudySwapOptionsFragment studySwapOptionsFragment;

    public UserLearnsAdapter(Context context, ArrayList<String> userLearns,
                             StudySwapOptionsFragment studySwapOptionsFragment) {
        this.context = context;
        this.userLearns = userLearns;
        this.studySwapOptionsFragment = studySwapOptionsFragment;
    }

    @NonNull
    @Override
    public UserLearnsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userLearnView = LayoutInflater.from(context).inflate(R.layout.layout_table_row, parent, false);
        return new UserLearnsViewHolder(userLearnView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserLearnsViewHolder holder, int position) {
        holder.txtRow.setText(userLearns.get(position));
        holder.imgDelete.setOnClickListener(v -> {
            studySwapOptionsFragment.deleteLearnCourse(userLearns.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return userLearns.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class UserLearnsViewHolder extends RecyclerView.ViewHolder {

        TextView txtRow;
        ImageView imgDelete;

        public UserLearnsViewHolder(@NonNull View itemView) {
            super(itemView);

            txtRow = itemView.findViewById(R.id.txtRow);
            imgDelete = itemView.findViewById(R.id.imgDelete);

        }
    }
}
