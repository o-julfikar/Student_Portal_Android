package com.zulfikar.studentportal.review.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.review.models.SneakCardModel;

import java.util.List;

public class ReviewCardAdapter extends RecyclerView.Adapter<ReviewCardAdapter.ReviewCardViewHolder> {

    List<SneakCardModel.SneakReviewModel> sneakReviewModels;
    Context context;

    public ReviewCardAdapter(List<SneakCardModel.SneakReviewModel> sneakReviewModels, Context context) {
        this.sneakReviewModels = sneakReviewModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View reviewCard = LayoutInflater.from(context).inflate(R.layout.card_review, parent, false);
        return new ReviewCardViewHolder(reviewCard);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewCardViewHolder holder, int position) {
        holder.txtCourseCode.setText(sneakReviewModels.get(position).getCourseCode());
        holder.txtReview.setText(sneakReviewModels.get(position).getReviewText());
        double reviewPoints = sneakReviewModels.get(position).getReviewPoints();
        if (reviewPoints > 0) {
//            holder.star1.setColorFilter(ReSou);
        }
    }

    @Override
    public int getItemCount() {
        return sneakReviewModels.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ReviewCardViewHolder extends RecyclerView.ViewHolder {

        TextView txtCourseCode, txtReview;
        ImageView star1, star2, star3, star4, star5;

        public ReviewCardViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCourseCode = itemView.findViewById(R.id.txtCourseCode);
            txtReview = itemView.findViewById(R.id.txtReview);

            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);

        }
    }
}
