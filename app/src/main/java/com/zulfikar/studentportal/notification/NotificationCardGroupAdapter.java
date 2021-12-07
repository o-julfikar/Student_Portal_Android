package com.zulfikar.studentportal.notification;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.R;

import java.util.ArrayList;

public class NotificationCardGroupAdapter extends RecyclerView.Adapter<NotificationCardGroupAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<NotificationCardGroup> notificationCardGroups;
    private final Handler handler;

    public NotificationCardGroupAdapter(Context context, ArrayList<NotificationCardGroup> notificationCardGroups) {
        this.context = context;
        this.notificationCardGroups = notificationCardGroups;
        handler = new Handler();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_notification_group, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNotiGroupTitle.setText(notificationCardGroups.get(position).getTxtNotiGroupTitle());
        for (NotificationCard notificationCard : notificationCardGroups.get(position).getNotificationCards()) {
            holder.layoutNotiGroup.addView(notificationCard.getView(context, holder.layoutNotiGroup, handler));
        }
    }

    @Override
    public int getItemCount() {
        return notificationCardGroups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNotiGroupTitle;
        LinearLayout layoutNotiGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNotiGroupTitle = itemView.findViewById(R.id.txtNotiGroupTitle);
            layoutNotiGroup = itemView.findViewById(R.id.layoutNotiGroup);
        }
    }
}
