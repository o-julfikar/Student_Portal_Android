package com.zulfikar.studentportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(MainActivity.this, IdentifierActivity.class));

        findViewById(R.id.btnGetStarted).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        });
    }
}