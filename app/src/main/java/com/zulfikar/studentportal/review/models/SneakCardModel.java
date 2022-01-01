package com.zulfikar.studentportal.review.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SneakCardModel {
    @SerializedName("instructor_initials")
    private final List<String> instructorInitials;
    @SerializedName("instructor_fullname")
    private final String instructorFullname;
    @SerializedName("instructor_photo")
    private final String instructorPhoto;
    @SerializedName("instructor_emails")
    private final List<String> instructorEmails;
    @SerializedName("instructor_phones")
    private final List<String> instructorPhones;
    @SerializedName("instructor_review_points")
    private final Double instructorReviewPoints;
    @SerializedName("instructor_total_reviews")
    private final Integer instructorTotalReviews;
    @SerializedName("instructor_reviews")
    private final List<SneakReviewModel> instructorReviews;

    public SneakCardModel(Object data) {
        instructorInitials = new ArrayList<>();
        instructorEmails = new ArrayList<>();
        instructorPhones = new ArrayList<>();
        instructorReviews = new ArrayList<>();
        Map<?, ?> dataMap = (Map<?, ?>) data;
        List<?> mapList = (List<?>) dataMap.get("instructor_initials");
        if (mapList != null) {
            for (Object obj : mapList) {
                instructorInitials.add((String) obj);
            }
        }
        instructorFullname = (String) dataMap.get("instructor_fullname");
        instructorPhoto = (String) dataMap.get("instructor_photo");
        mapList = (List<?>) dataMap.get("instructor_emails");
        if (mapList != null) {
            for (Object obj : mapList) {
                instructorEmails.add((String) obj);
            }
        }
        mapList = (List<?>) dataMap.get("instructor_phones");
        if (mapList != null) {
            for (Object obj : mapList) {
                instructorPhones.add((String) obj);
            }
        }
        instructorReviewPoints = (Double) dataMap.get("instructor_review_points");
        Double total_reviews = ((Double) dataMap.get("instructor_total_reviews"));
        if (total_reviews == null) {
            instructorTotalReviews = 0;
        } else {
            instructorTotalReviews = total_reviews.intValue();
        }
        mapList = (List<?>) dataMap.get("instructor_reviews");
        if (mapList != null) {
            for (Object obj : mapList) {
                instructorReviews.add(new SneakReviewModel(
                        obj
                ));
            }
        }
    }

    public List<String> getInstructorInitials() {
        return instructorInitials;
    }

    public String getInstructorFullname() {
        return instructorFullname;
    }

    public String getInstructorPhoto() {
        return instructorPhoto;
    }

    public List<String> getInstructorEmails() {
        return instructorEmails;
    }

    public List<String> getInstructorPhones() {
        return instructorPhones;
    }

    public Double getInstructorReviewPoints() {
        return instructorReviewPoints;
    }

    public Integer getInstructorTotalReviews() {
        return instructorTotalReviews;
    }

    public List<SneakReviewModel> getInstructorReviews() {
        return instructorReviews;
    }

    public static class SneakReviewModel {
        @SerializedName("review_id")
        private final Integer reviewId;
        @SerializedName("review_course_code")
        private final String courseCode;
        @SerializedName("review_points")
        private final Double reviewPoints;
        @SerializedName("review_text")
        private final String reviewText;

        public SneakReviewModel(Object data) {
            Map<?, ?> dataMap = (Map<?, ?>) data;
            Double reviewIdDouble = (Double) dataMap.get("review_id");
            if (reviewIdDouble != null) {
                reviewId = reviewIdDouble.intValue();
            } else {
                reviewId = -1;
            }
            courseCode = (String) dataMap.get("review_course_code");
            reviewPoints = (Double) dataMap.get("review_points");
            reviewText = (String) dataMap.get("review_text");
        }

        public Integer getReviewId() {
            return reviewId;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public Double getReviewPoints() {
            return reviewPoints;
        }

        public String getReviewText() {
            return reviewText;
        }
    }
}
