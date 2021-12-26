package com.zulfikar.studentportal.account.models;

public class UserLogin {
    private Integer id;
    private final Integer bracu_id;
    private String password;

    public UserLogin(int bracu_id) {
        this.bracu_id = bracu_id;
    }

    public UserLogin(int bracu_id, String password) {
        this.bracu_id = bracu_id;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
