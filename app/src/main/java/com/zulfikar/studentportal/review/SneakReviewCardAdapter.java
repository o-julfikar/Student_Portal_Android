package com.zulfikar.studentportal.review;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.R;

public class SneakReviewCardAdapter extends RecyclerView.Adapter<SneakReviewCardAdapter.SneakReviewCardViewHolder> {

    Context context;
    SneakReviewCard[] sneakReviewCards;
    Handler sneakRCHandler;

    public SneakReviewCardAdapter(Context context, SneakReviewCard[] sneakReviewCards) {
        this.context = context;
        this.sneakReviewCards = sneakReviewCards;
        sneakRCHandler = new Handler();
    }

    @NonNull
    @Override
    public SneakReviewCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sneakReviewCard = LayoutInflater.from(context).inflate(R.layout.card_sneak, parent, false);
        return new SneakReviewCardViewHolder(sneakReviewCard);
    }

    @Override
    public int getItemCount() {
        return sneakReviewCards.length;
    }

    @Override
    public void onBindViewHolder(@NonNull SneakReviewCardViewHolder holder, int position) {
        new FetchImage(holder.imgSneakRCInstructorPhoto, sneakReviewCards[position].instructorPhoto, sneakRCHandler);
        holder.txtSneakRCInstructorName.setText(sneakReviewCards[position].instructorName);
        holder.txtSneakRCInstructorInitial.setText(sneakReviewCards[position].instructorInitial);

        for (int i = 0; i < 3 && i < sneakReviewCards[position].sneakCards.length; i++) {
            holder.linLayoutSneakRCSneakCards.addView(sneakReviewCards[position].sneakCards[i].getView(context, holder.linLayoutSneakRCSneakCards));
        }
    }

    protected static class SneakReviewCardViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView imgSneakRCInstructorPhoto;
        ImageView sneakRCStar1, sneakRCStar2, sneakRCStar3, sneakRCStar4, sneakRCStar5;
        Button btnSneakRCReview, btnSneakRCMore;
        LinearLayout linLayoutSneakRCSneakCards;
        TextView txtSneakRCInstructorName, txtSneakRCInstructorInitial;

        public SneakReviewCardViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSneakRCInstructorPhoto = itemView.findViewById(R.id.imgSneakRCInstructorPhoto);
            sneakRCStar1 = itemView.findViewById(R.id.sneakRCStar1);
            sneakRCStar2 = itemView.findViewById(R.id.sneakRCStar2);
            sneakRCStar3 = itemView.findViewById(R.id.sneakRCStar3);
            sneakRCStar4 = itemView.findViewById(R.id.sneakRCStar4);
            sneakRCStar5 = itemView.findViewById(R.id.sneakRCStar5);
            btnSneakRCReview = itemView.findViewById(R.id.btnSneakRCReview);
            btnSneakRCMore = itemView.findViewById(R.id.btnSneakRCMore);
            linLayoutSneakRCSneakCards = itemView.findViewById(R.id.linLayoutSneakRCSneakCards);
            txtSneakRCInstructorName = itemView.findViewById(R.id.txtSneakRCInstructorName);
            txtSneakRCInstructorInitial = itemView.findViewById(R.id.txtSneakRCInstructorInitial);
        }
    }
}
