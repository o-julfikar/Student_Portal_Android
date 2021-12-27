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

public class UserStudySlotsAdapter extends RecyclerView.Adapter<UserStudySlotsAdapter.UserStudySlotsViewHolder> {

    ArrayList<String> studySlots;
    Context context;
    StudySwapOptionsFragment studySwapOptionsFragment;

    public UserStudySlotsAdapter(Context context, ArrayList<String> studySlots,
                                 StudySwapOptionsFragment studySwapOptionsFragment) {
        this.context = context;
        this.studySlots = studySlots;
        this.studySwapOptionsFragment = studySwapOptionsFragment;
    }

    @NonNull
    @Override
    public UserStudySlotsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userStudySlotView = LayoutInflater.from(context).inflate(R.layout.layout_table_row, parent, false);
        return new UserStudySlotsViewHolder(userStudySlotView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserStudySlotsViewHolder holder, int position) {
        holder.txtRow.setText(studySlots.get(position));
        holder.imgDelete.setOnClickListener(v -> {
            String[] dayTime = studySlots.get(position).split(" \\| ");
            String day = dayTime[0];
            String time = dayTime[1];
            studySwapOptionsFragment.deleteStudySlot(day, time);
        });
    }

    @Override
    public int getItemCount() {
        return studySlots.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class UserStudySlotsViewHolder extends RecyclerView.ViewHolder {

        TextView txtRow;
        ImageView imgDelete;

        public UserStudySlotsViewHolder(@NonNull View itemView) {
            super(itemView);

            txtRow = itemView.findViewById(R.id.txtRow);
            imgDelete = itemView.findViewById(R.id.imgDelete);

        }
    }
}
