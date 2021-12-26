package com.zulfikar.studentportal.notification.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationGroup {
    @SerializedName("noti_group_key")
    private String groupKey;
    @SerializedName("noti_group_data")
    private List<Notification> notifications;

    public String getGroupKey() {
        return groupKey;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public static class Notification {
        @SerializedName("notification_id")
        private int id;
        @SerializedName("sender_bracu_id")
        private int senderBracuId;
        @SerializedName("sender_photo")
        private String senderPhoto;
        @SerializedName("notification_content")
        private String notificationContent;
        @SerializedName("date_created")
        private String dateCreated;
        @SerializedName("date_read")
        private String dateRead;
        @SerializedName("noti_type")
        private String notiType;
        @SerializedName("reaction_type")
        private String reactionType;
        @SerializedName("post")
        private int postId;
        @SerializedName("comment")
        private int commentId;
        @SerializedName("course")
        private int courseId;
        @SerializedName("instructor")
        private int instructorId;
        @SerializedName("sec_swap_request")
        private int secSwapRequestId;
        @SerializedName("study_swap_request")
        private int studySwapRequestId;


        public int getId() {
            return id;
        }

        public int getSenderBracuId() {
            return senderBracuId;
        }

        public String getSenderPhoto() {
            return senderPhoto;
        }

        public String getNotificationContent() {
            return notificationContent;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public String getDateRead() {
            return dateRead;
        }

        public String getNotiType() {
            return notiType;
        }

        public String getReactionType() {
            return reactionType;
        }

        public int getPostId() {
            return postId;
        }

        public int getCommentId() {
            return commentId;
        }

        public int getCourseId() {
            return courseId;
        }

        public int getInstructorId() {
            return instructorId;
        }

        public int getSecSwapRequestId() {
            return secSwapRequestId;
        }

        public int getStudySwapRequestId() {
            return studySwapRequestId;
        }
    }
}
