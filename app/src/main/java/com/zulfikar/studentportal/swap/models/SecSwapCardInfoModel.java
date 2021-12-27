package com.zulfikar.studentportal.swap.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class SecSwapCardInfoModel {
    public static final int DECLINED = -1, PENDING = 0, APPROVED = 1;

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

    public static class SecSwapCardModel {
        @SerializedName("id")
        private int id;
        @SerializedName("provider_bracu_id")
        private int providerBracuId;
        @SerializedName("provider_name")
        private String providerName;
        @SerializedName("provider_photo")
        private String providerPhoto;
        @SerializedName("recipient_bracu_id")
        private int recipientBracuId;
        @SerializedName("recipient_name")
        private String recipientName;
        @SerializedName("recipient_photo")
        private String recipientPhoto;
        @SerializedName("course_code")
        private String courseCode;
        @SerializedName("section_number")
        private int sectionNumber;

        public int getId() {
            return id;
        }

        public int getProviderBracuId() {
            return providerBracuId;
        }

        public String getProviderName() {
            return providerName;
        }

        public String getProviderPhoto() {
            return providerPhoto;
        }

        public int getRecipientBracuId() {
            return recipientBracuId;
        }

        public String getRecipientName() {
            return recipientName;
        }

        public String getRecipientPhoto() {
            return recipientPhoto;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public int getSectionNumber() {
            return sectionNumber;
        }
    }
}
