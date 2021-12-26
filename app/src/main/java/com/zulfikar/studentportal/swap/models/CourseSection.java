package com.zulfikar.studentportal.swap.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseSection {
    @SerializedName("course_code")
    private String courseCode;
    @SerializedName("course_sections")
    private List<Integer> sections;

    public String getCourseCode() {
        return courseCode;
    }

    public List<Integer> getSections() {
        return sections;
    }
}
