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

import java.util.ArrayList;

public class UserPrefersAdapter extends RecyclerView.Adapter<UserPrefersAdapter.UsersPrefersViewHolder> {

    Context context;
    ArrayList<String> prefers;

    public UserPrefersAdapter(Context context, ArrayList<String> prefers) {
        this.context = context;
        this.prefers = prefers;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public UsersPrefersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View prefersRow = LayoutInflater.from(context).inflate(R.layout.layout_table_row, parent, false);
        return new UsersPrefersViewHolder(prefersRow);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersPrefersViewHolder holder, int position) {
        holder.txtRow.setText(prefers.get(position));
    }

    @Override
    public int getItemCount() {
        return prefers.size();
    }

    public static class UsersPrefersViewHolder extends RecyclerView.ViewHolder {
        TextView txtRow;
        ImageView imgDelete;

        public UsersPrefersViewHolder(@NonNull View itemView) {
            super(itemView);

            txtRow = itemView.findViewById(R.id.txtRow);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
