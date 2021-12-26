package com.zulfikar.studentportal.account.models;

public class EnrollCourse {
    private String course;
    private String semester;

    public EnrollCourse(String course, String semester) {
        this.course = course;
        this.semester = semester;
    }

    public String getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }
}
