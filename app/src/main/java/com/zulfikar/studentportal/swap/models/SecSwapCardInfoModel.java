package com.zulfikar.studentportal.swap.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class SecSwapCardInfoModel {
    @SerializedName("sec_swap_request_id")
    private int requestId;
    @SerializedName("sec_swap_request_creator_bracu_id")
    private int creatorBracuId;
    @SerializedName("sec_swap_request_creator_name")
    private String creatorName;
    @SerializedName("sec_swap_request_creator_photo")
    private String creatorPhoto;
    @SerializedName("sec_swap_request_date_created")
    private Date dateCreated;
    @SerializedName("total_swaps")
    private int totalSwaps;
    @SerializedName("user_accepted")
    private int userAccepted;
    @SerializedName("request_status")
    private int requestStatus;
    private List<SecSwapCardModel> cards;

    public int getRequestId() {
        return requestId;
    }

    public int getCreatorBracuId() {
        return creatorBracuId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getCreatorPhoto() {
        return creatorPhoto;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public int getTotalSwaps() {
        return totalSwaps;
    }

    public int getUserAccepted() {
        return userAccepted;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public List<SecSwapCardModel> getCards() {
        return cards;
    }

    private static class SecSwapCardModel {
        private int id;
        @SerializedName("teacher_bracu_id")
        private int teacherBracuId;
        @SerializedName("teacher_name")
        private String teacherName;
        @SerializedName("teacher_photo")
        private String teacherPhoto;
        @SerializedName("learner_bracu_id")
        private int learnerBracuId;
        @SerializedName("learner_name")
        private String learnerName;
        @SerializedName("learner_photo")
        private String learnerPhoto;
        @SerializedName("course_code")
        private String courseCode;
        @SerializedName("sec_slot")
        private String secSlot;

        public int getId() {
            return id;
        }

        public int getTeacherBracuId() {
            return teacherBracuId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public String getTeacherPhoto() {
            return teacherPhoto;
        }

        public int getLearnerBracuId() {
            return learnerBracuId;
        }

        public String getLearnerName() {
            return learnerName;
        }

        public String getLearnerPhoto() {
            return learnerPhoto;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public String getSecSlot() {
            return secSlot;
        }
    }
}
