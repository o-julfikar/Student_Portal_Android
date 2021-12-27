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

public class UserTeachesAdapter extends RecyclerView.Adapter<UserTeachesAdapter.UserTeachesViewHolder> {

    ArrayList<String> userTeaches;
    Context context;
    StudySwapOptionsFragment studySwapOptionsFragment;

    public UserTeachesAdapter(Context context, ArrayList<String> userTeaches,
                              StudySwapOptionsFragment studySwapOptionsFragment) {
        this.context = context;
        this.userTeaches = userTeaches;
        this.studySwapOptionsFragment = studySwapOptionsFragment;
    }

    @NonNull
    @Override
    public UserTeachesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userTeachView = LayoutInflater.from(context).inflate(R.layout.layout_table_row, parent, false);
        return new UserTeachesViewHolder(userTeachView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserTeachesViewHolder holder, int position) {
        holder.txtRow.setText(userTeaches.get(position));
        holder.imgDelete.setOnClickListener(v -> {
            studySwapOptionsFragment.deleteTeachCourse(userTeaches.get(position));
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return userTeaches.size();
    }

    public static class UserTeachesViewHolder extends RecyclerView.ViewHolder {

        TextView txtRow;
        ImageView imgDelete;

        public UserTeachesViewHolder(@NonNull View itemView) {
            super(itemView);

            txtRow = itemView.findViewById(R.id.txtRow);
            imgDelete = itemView.findViewById(R.id.imgDelete);

        }
    }
}
