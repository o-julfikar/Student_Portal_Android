package com.zulfikar.studentportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

public class RegisterFinalActivity extends AppCompatActivity {

    RelativeLayout layoutBack;
    ShapeableImageView imgDisplayPhoto;
    TextView txtChange, txtRemove, txtPassword, txtPasswordConfirm;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_final);

        layoutBack = findViewById(R.id.layoutBack);
        imgDisplayPhoto = findViewById(R.id.imgDisplayPhoto);
        txtChange = findViewById(R.id.txtChange);
        txtRemove = findViewById(R.id.txtRemove);
        txtPassword = findViewById(R.id.txtPassword);
        txtPasswordConfirm = findViewById(R.id.txtPasswordConfirm);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(RegisterFinalActivity.this, HomeActivity.class));
        });
    }
}