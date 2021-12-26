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
import com.zulfikar.studentportal.swap.models.SecSwapRequest;
import com.zulfikar.studentportal.swap.models.StudySwapRequest;

import java.util.ArrayList;

public class SecSwapHistoryRowAdapter extends RecyclerView.Adapter<SecSwapHistoryRowAdapter.SecSwapHistoryRowViewHolder> {

    Context context;

    public SecSwapHistoryRowAdapter(Context context, ArrayList<SecSwapRequest> secSwapRequests) {
        this.context = context;
        this.secSwapRequests = secSwapRequests;
    }

    ArrayList<SecSwapRequest> secSwapRequests;

    @NonNull
    @Override
    public SecSwapHistoryRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View secSwapHistoryRow = LayoutInflater.from(context).inflate(R.layout.row_section_swap_history, parent, false);
        return new SecSwapHistoryRowViewHolder(secSwapHistoryRow);
    }

    @Override
    public void onBindViewHolder(@NonNull SecSwapHistoryRowViewHolder holder, int position) {
        holder.txtSwapDate.setText(Utility.simpleDateFormat(secSwapRequests.get(position).getDateCreated()));
        switch (secSwapRequests.get(position).getIsApproved()) {
            case SecSwapRequest.DECLINED:
                holder.imgStatus.setImageResource(R.drawable.ic_cancel);
                break;
            case SecSwapRequest.APPROVED:
                holder.imgStatus.setImageResource(R.drawable.ic_round_check_circle);
                break;
            default:
                holder.imgStatus.setImageResource(R.drawable.ic_round_pending);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return secSwapRequests.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class SecSwapHistoryRowViewHolder extends RecyclerView.ViewHolder {

        TextView txtSwapDate;
        ImageView imgStatus;

        public SecSwapHistoryRowViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSwapDate = itemView.findViewById(R.id.txtSwapDate);
            imgStatus = itemView.findViewById(R.id.imgStatus);
        }
    }

}
