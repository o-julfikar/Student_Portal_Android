package com.zulfikar.studentportal.swap.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserPrefers {
    @SerializedName("course_code")
    private final String courseCode;
    @SerializedName("section_number")
    private final String sectionNumber;

    public UserPrefers(String courseCode, String sectionNumber) {
        this.courseCode = courseCode;
        this.sectionNumber = sectionNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getSectionNumber() {
        return sectionNumber;
    }
}
