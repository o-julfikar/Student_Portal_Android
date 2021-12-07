package com.zulfikar.studentportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class IdentifierActivity extends AppCompatActivity {

    TextView txtBracuId;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identifier);

        txtBracuId = findViewById(R.id.txtBracuId);
        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> {
            if (txtBracuId.getText().toString().length() == 0) {
                startActivity(new Intent(IdentifierActivity.this, LoginActivity.class));
            } else {
                startActivity(new Intent(IdentifierActivity.this, RegisterBasicInfoActivity.class));
            }
        });
    }
}