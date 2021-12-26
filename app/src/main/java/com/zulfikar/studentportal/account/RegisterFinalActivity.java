package com.zulfikar.studentportal.account;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.models.UserRegister;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Intent intent = getIntent();
        String txtBracuId = intent.getStringExtra("userBracuId");
        String txtName = intent.getStringExtra("userName");
        String txtEmail = intent.getStringExtra("userEmail");
        String txtBirthDate = intent.getStringExtra("userBirthdate");
        String txtPhone = intent.getStringExtra("userPhone");
        String txtProgram = intent.getStringExtra("userProgram");
        String txtSemester = intent.getStringExtra("userEnrolledSemester");

        layoutBack.setOnClickListener(v -> {
            finish();
        });

        btnSignUp.setOnClickListener(v -> {
            btnSignUp.setEnabled(false);
            TextView[] textViews = new TextView[]{txtPassword, txtPasswordConfirm};

            for (TextView view : textViews) {
                if (view.getText().toString().length() <= 0) {
                    view.requestFocus();
                    btnSignUp.setEnabled(true);
                    return;
                }
            }

            if (!txtPassword.getText().toString().equals(txtPasswordConfirm.getText().toString())) {
                Toast.makeText(RegisterFinalActivity.this, "Password did not match", Toast.LENGTH_LONG).show();

                btnSignUp.setEnabled(true);
            } else {
                UserRegister userRegister = new UserRegister(
                        txtBracuId,
                        txtEmail,
                        txtPassword.getText().toString(),
                        txtName,
                        txtBirthDate,
                        txtPhone,
                        "",
                        txtProgram,
                        txtSemester);

                JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(RegisterFinalActivity.this);
                Call<Integer> registerCall = jsonPlaceHolderApi.registerUser(userRegister);
                registerCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body() > 0) {
                                Intent loginIntent = new Intent(RegisterFinalActivity.this, LoginActivity.class);
                                loginIntent.putExtra("userBracuId", txtBracuId);
                                loginIntent.putExtra("userPassword", txtPassword.getText().toString());
                                startActivity(loginIntent);
                            } else {
                                Toast.makeText(RegisterFinalActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(RegisterFinalActivity.this, "Error code: " + response.code(), Toast.LENGTH_LONG).show();
                        }
                        btnSignUp.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(RegisterFinalActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        btnSignUp.setEnabled(true);
                    }
                });

            }

        });
    }
}