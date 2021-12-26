package com.zulfikar.studentportal.account.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private int id;
    private int bracu_id;
    private String email;
    private String first_name;
    private String last_name;
    @SerializedName("name")
    private String fullname;
    private boolean is_superuser;
    @SerializedName("role")
    private String role;
    private boolean is_active;
    private Date last_login;
    private Date date_joined;
    private Date birthdate;
    private String phone;
    private String photo;
    @SerializedName("department")
    private String program;
    @SerializedName("enrolled_semester")
    private String semester;
    private ArrayList<LinkedTreeMap<String, String>> enrolled_courses;

    public User() {

    }

    public User(int bracu_id, String email, String fullname, Date birthdate, String phone, String photo, String program, String semester) {
        this.bracu_id = bracu_id;
        this.email = email;
        this.fullname = fullname;
        this.birthdate = birthdate;
        this.phone = phone;
        this.photo = photo;
        this.program = program;
        this.semester = semester;
    }

    public int getId() {
        return id;
    }

    public int getBracu_id() {
        return bracu_id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFullname() {
        return fullname;
    }

    public String getRole() {
        return role;
    }

    public boolean isIs_superuser() {
        return is_superuser;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public Date getLast_login() {
        return last_login;
    }

    public Date getDate_joined() {
        return date_joined;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhoto() {
        return photo;
    }

    public String getProgram() {
        return program;
    }

    public String getSemester() {
        return semester;
    }

    public ArrayList<LinkedTreeMap<String, String>> getEnrolled_courses() {
        return enrolled_courses;
    }
}
