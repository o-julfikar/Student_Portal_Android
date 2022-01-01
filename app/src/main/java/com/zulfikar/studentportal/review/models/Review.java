package com.zulfikar.studentportal.review.models;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("course_code")
    private String course_code;
    @SerializedName("review_text")
    private String reviewText;
    @SerializedName("review_points")
    private Double reviewPoints;
    @SerializedName("instructor_initial")
    private String initial;
}
