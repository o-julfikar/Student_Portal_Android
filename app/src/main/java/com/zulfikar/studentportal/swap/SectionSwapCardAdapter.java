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

public class SectionSwapCardAdapter extends RecyclerView.Adapter<SectionSwapCardAdapter.SectionSwapCardHolder> {

    ArrayList<SectionSwapCard> sectionSwapCards;
    Context context;
    Handler handler;

    public SectionSwapCardAdapter(Context context, ArrayList<SectionSwapCard> sectionSwapCards) {
        this.context = context;
        this.sectionSwapCards = sectionSwapCards;
        handler = new Handler();
    }

    @NonNull
    @Override
    public SectionSwapCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SectionSwapCardHolder(LayoutInflater.from(context).inflate(R.layout.card_swap_section, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SectionSwapCardHolder holder, int position) {
        new FetchImage(holder.imgProvider, sectionSwapCards.get(position).getProviderPhoto(), handler);
        new FetchImage(holder.imgReceiver, sectionSwapCards.get(position).getReceiverPhoto(), handler);
        holder.txtProvider.setText(sectionSwapCards.get(position).getProviderName());
        holder.txtReceiver.setText(sectionSwapCards.get(position).getReceiverName());
        holder.txtCourse.setText(sectionSwapCards.get(position).getCourseCode());
        holder.txtSection.setText(sectionSwapCards.get(position).getSectionNumberStr());
    }

    @Override
    public int getItemCount() {
        return sectionSwapCards.size();
    }

    public static class SectionSwapCardHolder extends RecyclerView.ViewHolder {

        ShapeableImageView imgProvider, imgReceiver;
        TextView txtProvider, txtReceiver, txtCourse, txtSection;

        public SectionSwapCardHolder(@NonNull View itemView) {
            super(itemView);
            imgProvider = itemView.findViewById(R.id.imgProvider);
            imgReceiver = itemView.findViewById(R.id.imgReceiver);
            txtProvider = itemView.findViewById(R.id.txtProvider);
            txtReceiver = itemView.findViewById(R.id.txtReceiver);
            txtCourse = itemView.findViewById(R.id.txtCourse);
            txtSection = itemView.findViewById(R.id.txtSection);
        }
    }
}
