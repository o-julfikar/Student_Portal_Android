package com.zulfikar.studentportal.swap;

public class StudySwapCard {
    private int teacherId, learnerId, courseId, slotId;
    private String teacherName, teacherPhoto, learnerName, learnerPhoto, courseCode, slot;

    public StudySwapCard(int teacherId, int learnerId, int courseId, int slotId, String teacherName, String teacherPhoto, String learnerName, String learnerPhoto, String courseCode, String slot) {
        this.teacherId = teacherId;
        this.learnerId = learnerId;
        this.courseId = courseId;
        this.slotId = slotId;
        this.teacherName = teacherName;
        this.teacherPhoto = teacherPhoto;
        this.learnerName = learnerName;
        this.learnerPhoto = learnerPhoto;
        this.courseCode = courseCode;
        this.slot = slot;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getLearnerId() {
        return learnerId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getSlotId() {
        return slotId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherPhoto() {
        return teacherPhoto;
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

    public String getSlot() {
        return slot;
    }
}
