package com.zulfikar.studentportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RegisterBasicInfoActivity extends AppCompatActivity {

    TextView txtBracuId, txtName, txtEmail, txtBirthDate, txtPhone, txtSemester;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_basic_info);

        txtBracuId = findViewById(R.id.txtBracuId);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtBirthDate = findViewById(R.id.txtBirthDate);
        txtPhone = findViewById(R.id.txtPhone);
        txtSemester = findViewById(R.id.txtSemester);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            startActivity(new Intent(RegisterBasicInfoActivity.this, RegisterFinalActivity.class));
        });

    }
}