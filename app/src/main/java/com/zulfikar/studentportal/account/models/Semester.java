package com.zulfikar.studentportal.account.models;

public class Semester {
    public static int SPRING = 1, SUMMER = 3, FALL = 2;

    private int id;
    private int code;
    private int year;

    public int getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public int getYear() {
        return year;
    }
}
