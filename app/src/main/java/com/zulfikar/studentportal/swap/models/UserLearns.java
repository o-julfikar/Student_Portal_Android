package com.zulfikar.studentportal.swap.models;

import com.google.gson.annotations.SerializedName;

public class UserLearns {
    @SerializedName("course_code")
    private final String courseCode;

    public UserLearns(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
