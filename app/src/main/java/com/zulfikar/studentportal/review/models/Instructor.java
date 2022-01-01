package com.zulfikar.studentportal.review.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Instructor {
    @SerializedName("instructor_initial")
    private final String initial;
    @SerializedName("instructor_fullname")
    private final String fullname;
    @SerializedName("instructor_photo")
    private final String photo;
    @SerializedName("instructor_email")
    private final String email;
    @SerializedName("instructor_phone")
    private final String phone;
    @SerializedName("course_code")
    private final String courseCode;
    @SerializedName("review_text")
    private final String reviewText;
    @SerializedName("review_points")
    private final Double reviewPoints;

    public Instructor(String initial, String fullname, String photo, String email, String phone, String courseCode, String reviewText, Double reviewPoints) {
        this.initial = initial;
        this.fullname = fullname;
        this.photo = photo;
        this.email = email;
        this.phone = phone;
        this.courseCode = courseCode;
        this.reviewText = reviewText;
        this.reviewPoints = reviewPoints;
    }

    public String getInitial() {
        return initial;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Double getReviewPoints() {
        return reviewPoints;
    }
}
