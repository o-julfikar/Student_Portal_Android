package com.zulfikar.studentportal.account.models;

import com.google.gson.annotations.SerializedName;

public class IdInfo {
    @SerializedName("bracu_id")
    private String bracuId;
    @SerializedName("enrolled_year")
    private int enrolledYear;
    @SerializedName("enrolled_semester")
    private String enrolledSemester;
    private String program;

    public String getBracuId() {
        return bracuId;
    }

    public int getEnrolledYear() {
        return enrolledYear;
    }

    public String getEnrolledSemester() {
        return enrolledSemester;
    }

    public String getProgram() {
        return program;
    }
}
