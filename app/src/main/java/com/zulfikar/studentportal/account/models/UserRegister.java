package com.zulfikar.studentportal.account.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashMap;

public class UserRegister {
    @SerializedName("user")
    private final UserRegisterHeader user;
    @SerializedName("user_detail")
    private final UserRegisterDetail userDetail;

    public UserRegister(String bracuId, String email, String password, String fullname, String birthdate,
                        String phone, String photo, String program, String semester) {
        user = new UserRegisterHeader(bracuId, email, password);
        userDetail = new UserRegisterDetail(fullname, birthdate, phone, photo, program, semester);
    }

    private static class UserRegisterHeader {
        @SerializedName("bracu_id")
        private final String bracuId;
        private final String email;
        private final String password;

        public UserRegisterHeader(String bracuId, String email, String password) {
            this.bracuId = bracuId;
            this.email = email;
            this.password = password;
        }

        public String getBracuId() {
            return bracuId;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    private static class UserRegisterDetail {
        private final String fullname;
        private final String birthdate;
        private final String phone;
        private final String photo;
        private final String program;
        private final String semester;

        public UserRegisterDetail(String fullname, String birthdate, String phone, String photo,
                                  String program, String semester) {
            this.fullname = fullname;
            this.birthdate = birthdate;
            this.phone = phone;
            this.photo = photo;
            this.program = program;
            this.semester = semester;
        }

        public String getFullname() {
            return fullname;
        }

        public String getBirthdate() {
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
    }

}
