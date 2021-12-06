package com.zulfikar.studentportal.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zulfikar.studentportal.R;

public class SneakCard {

    int reviewId;
    String reviewCourse, reviewContent;

    public SneakCard(int reviewId, String reviewCourse, String reviewContent) {
        this.reviewId = reviewId;
        this.reviewCourse = reviewCourse;
        this.reviewContent = reviewContent;
    }

    public View getView(Context context, ViewGroup parent) {
        View sneakCard = LayoutInflater.from(context).inflate(R.layout.card_sneak_review, parent, false);

        TextView txtSneakCardCourse, txtSneakCardContent;
        txtSneakCardCourse = sneakCard.findViewById(R.id.txtSneakCardCourse);
        txtSneakCardContent = sneakCard.findViewById(R.id.txtSneakCardContent);

        txtSneakCardCourse.setText(reviewCourse);
        txtSneakCardContent.setText(reviewContent);

        return sneakCard;
    }
}
