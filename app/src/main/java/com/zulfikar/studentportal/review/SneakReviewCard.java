package com.zulfikar.studentportal.review;

public class SneakReviewCard {
    int instructorId;
    double reviewPoints;
    String instructorName, instructorInitial, instructorPhoto;
    SneakCard[] sneakCards;

    public SneakReviewCard(int instructorId, double reviewPoints, String instructorName, String instructorInitial, String instructorPhoto, SneakCard[] sneakCards) {
        this.instructorId = instructorId;
        this.reviewPoints = reviewPoints;
        this.instructorName = instructorName;
        this.instructorInitial = instructorInitial;
        this.instructorPhoto = instructorPhoto;
        this.sneakCards = sneakCards;
    }

    public SneakReviewCard(String instructorInitial) {
        this.instructorInitial = instructorInitial;
    }
}
