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
import com.zulfikar.studentportal.swap.SectionSwapOptionsFragment;

import java.util.ArrayList;

public class UserOffersAdapter extends RecyclerView.Adapter<UserOffersAdapter.UserOfferViewHolder> {


    ArrayList<String> offers;
    Context context;
    SectionSwapOptionsFragment sectionSwapOptionsFragment;

    public UserOffersAdapter(Context context, ArrayList<String> offers,
                             SectionSwapOptionsFragment sectionSwapOptionsFragment) {
        this.context = context;
        this.offers = offers;
        this.sectionSwapOptionsFragment = sectionSwapOptionsFragment;

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public UserOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View offerRow = LayoutInflater.from(context).inflate(R.layout.layout_table_row, parent, false);
        return new UserOfferViewHolder(offerRow);
    }

    @Override
    public void onBindViewHolder(@NonNull UserOfferViewHolder holder, int position) {
        holder.txtRow.setText(offers.get(position));
        holder.imgDelete.setOnClickListener(v -> {
            String[] courseCodeSection = offers.get(position).split(" ");
            String courseCode = courseCodeSection[0];
            String section = courseCodeSection[1];
            section = section.substring(1, section.length() - 1);
            sectionSwapOptionsFragment.deleteOffer(courseCode, section);
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public static class UserOfferViewHolder extends RecyclerView.ViewHolder {

        TextView txtRow;
        ImageView imgDelete;

        public UserOfferViewHolder(@NonNull View itemView) {
            super(itemView);

            txtRow = itemView.findViewById(R.id.txtRow);
            imgDelete = itemView.findViewById(R.id.imgDelete);

        }
    }
}
