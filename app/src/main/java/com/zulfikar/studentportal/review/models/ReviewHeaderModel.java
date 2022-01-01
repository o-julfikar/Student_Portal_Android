package com.zulfikar.studentportal.review.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewHeaderModel {
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

    public ReviewHeaderModel(Object data) {
        instructorInitials = new ArrayList<>();
        instructorEmails = new ArrayList<>();
        instructorPhones = new ArrayList<>();
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
}
