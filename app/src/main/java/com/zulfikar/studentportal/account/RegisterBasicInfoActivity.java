package com.zulfikar.studentportal.account;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.models.IdInfo;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterBasicInfoActivity extends AppCompatActivity {

    TextView txtBracuId, txtName, txtEmail, txtBirthDate, txtPhone, txtProgram, txtSemester;
    DatePickerDialog.OnDateSetListener setListener;
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
        txtProgram = findViewById(R.id.txtProgram);
        txtSemester = findViewById(R.id.txtSemester);
        btnNext = findViewById(R.id.btnNext);


        Intent intent = getIntent();
        String userBracuId = intent.getStringExtra("userBracuId");

        if (userBracuId != null) {
            txtBracuId.setText(userBracuId);
        }

        autoFillProgramSemester();

        txtBirthDate.setOnClickListener(v -> {
            showDatePicker();
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                 String date = year + "-" + month + "-" + dayOfMonth;
                 txtBirthDate.setText(date);
            }
        };

        btnNext.setOnClickListener(v -> {
            btnNext.setEnabled(false);
            TextView[] textViews = new TextView[]{txtBracuId, txtName, txtEmail, txtBirthDate, txtPhone, txtProgram, txtSemester};

            for (TextView view : textViews) {
                if (view.getText().toString().length() <= 0) {
                    if (view == txtBirthDate) showDatePicker();
                    else view.requestFocus();

                    btnNext.setEnabled(true);
                    return;
                }
            }

            String birthdate = txtBirthDate.getText().toString();

            Intent finalRegistrationIntent = new Intent(RegisterBasicInfoActivity.this, RegisterFinalActivity.class);
            finalRegistrationIntent.putExtra("userBracuId", txtBracuId.getText().toString());
            finalRegistrationIntent.putExtra("userName", txtName.getText().toString());
            finalRegistrationIntent.putExtra("userEmail", txtEmail.getText().toString());
            finalRegistrationIntent.putExtra("userBirthdate", birthdate);
            finalRegistrationIntent.putExtra("userPhone", txtPhone.getText().toString());
            finalRegistrationIntent.putExtra("userProgram", txtProgram.getText().toString());
            finalRegistrationIntent.putExtra("userEnrolledSemester", txtSemester.getText().toString());

            startActivity(finalRegistrationIntent);

            btnNext.setEnabled(true);
        });

        txtBracuId.setOnFocusChangeListener((v, hasFocus) -> {
            autoFillProgramSemester();
        });
    }

    private void autoFillProgramSemester() {
        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(RegisterBasicInfoActivity.this);
        Call<IdInfo> idInfoCall = jsonPlaceHolderApi.getInfoById(Integer.parseInt(txtBracuId.getText().toString()));
        idInfoCall.enqueue(new Callback<IdInfo>() {
            @Override
            public void onResponse(Call<IdInfo> call, Response<IdInfo> response) {
                if (response.isSuccessful()) {
                    IdInfo info = response.body();
                    if (info != null) {
                        txtSemester.setText(info.getEnrolledSemester());
                        txtProgram.setText(info.getProgram());
                    }
                } else {
                    Log.e("TAG", "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<IdInfo> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RegisterBasicInfoActivity.this, setListener, year, month, day);
        datePickerDialog.show();
    }
}