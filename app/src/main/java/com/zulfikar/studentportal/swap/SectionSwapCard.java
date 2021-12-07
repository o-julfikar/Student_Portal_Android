package com.zulfikar.studentportal.swap;

public class SectionSwapCard {
    private int providerId, receiverId, courseId, sectionId, sectionNumber;
    private String providerName, providerPhoto, receiverName, receiverPhoto, courseCode;

    public SectionSwapCard(int providerId, int receiverId, int courseId, int sectionId, String providerName, String providerPhoto, String receiverName, String receiverPhoto, String courseCode, int sectionNumber) {
        this.providerId = providerId;
        this.receiverId = receiverId;
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.sectionNumber = sectionNumber;
        this.providerName = providerName;
        this.providerPhoto = providerPhoto;
        this.receiverName = receiverName;
        this.receiverPhoto = receiverPhoto;
        this.courseCode = courseCode;
    }

    public int getProviderId() {
        return providerId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public String getSectionNumberStr() {
        return "Section " + sectionNumber;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderPhoto() {
        return providerPhoto;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverPhoto() {
        return receiverPhoto;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
