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
import com.zulfikar.studentportal.Utility;
import com.zulfikar.studentportal.swap.models.StudySwapRequest;

import java.util.ArrayList;

public class StudySwapHistoryRowAdapter extends RecyclerView.Adapter<StudySwapHistoryRowAdapter.StudySwapHistoryRowViewHolder> {

    Context context;
    ArrayList<StudySwapRequest> studySwapRequests;

    public StudySwapHistoryRowAdapter(Context context, ArrayList<StudySwapRequest> studySwapRequests) {
        this.context = context;
        this.studySwapRequests = studySwapRequests;
    }

    @NonNull
    @Override
    public StudySwapHistoryRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View studySwapHistoryRow = LayoutInflater.from(context).inflate(R.layout.row_study_swap_history, parent, false);
        return new StudySwapHistoryRowViewHolder(studySwapHistoryRow);
    }

    @Override
    public void onBindViewHolder(@NonNull StudySwapHistoryRowViewHolder holder, int position) {
        holder.txtSwapDate.setText(Utility.simpleDateFormat(studySwapRequests.get(position).getDateCreated()));
        switch (studySwapRequests.get(position).getIsApproved()) {
            case StudySwapRequest.DECLINED:
                holder.imgStatus.setImageResource(R.drawable.ic_cancel);
                break;
            case StudySwapRequest.APPROVED:
                holder.imgStatus.setImageResource(R.drawable.ic_round_check_circle);
                break;
            default:
                holder.imgStatus.setImageResource(R.drawable.ic_round_pending);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return studySwapRequests.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class StudySwapHistoryRowViewHolder extends RecyclerView.ViewHolder {

        TextView txtSwapDate;
        ImageView imgStatus;

        public StudySwapHistoryRowViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSwapDate = itemView.findViewById(R.id.txtSwapDate);
            imgStatus = itemView.findViewById(R.id.imgStatus);

        }
    }
}
