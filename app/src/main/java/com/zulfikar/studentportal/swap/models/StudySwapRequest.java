package com.zulfikar.studentportal.swap.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class StudySwapRequest {
    public static final int DECLINED = -1, PENDING = 0, APPROVED = 1;

    private int id;
    @SerializedName("date_created")
    private Date dateCreated;
    @SerializedName("is_approved")
    private int isApproved;
    @SerializedName("creator")
    private int creatorId;

    public int getId() {
        return id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public int getCreatorId() {
        return creatorId;
    }
}
