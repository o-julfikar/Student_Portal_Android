package com.zulfikar.studentportal.review.models;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("course_code")
    private final String course_code;
    @SerializedName("review_text")
    private final String reviewText;
    @SerializedName("review_points")
    private final Double reviewPoints;
    @SerializedName("instructor_initial")
    private final String initial;

    public Review(String course_code, String reviewText, Double reviewPoints, String initial) {
        this.course_code = course_code;
        this.reviewText = reviewText;
        this.reviewPoints = reviewPoints;
        this.initial = initial;
    }
}
