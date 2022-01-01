package com.zulfikar.studentportal.review.models;

import com.google.gson.annotations.SerializedName;

public class Instructor {
    @SerializedName("instructor_initial")
    private String initial;
    @SerializedName("instructor_fullname")
    private String fullname;
    @SerializedName("instructor_photo")
    private String photo;
    @SerializedName("instructor_email")
    private String email;
    @SerializedName("instructor_phone")
    private String phone;
    @SerializedName("course_code")
    private String courseCode;
    @SerializedName("review_text")
    private String reviewText;
    @SerializedName("review_points")
    private Double reviewPoints;

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
