package com.zulfikar.studentportal.account;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zulfikar.studentportal.HomeActivity;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.models.UserLogin;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    TextView txtBracuId, txtPassword;
    Button btnSignIn;
    String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtBracuId = findViewById(R.id.txtBracuId);
        txtPassword = findViewById(R.id.txtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);

        Intent intent = getIntent();
        String userBracuId = intent.getStringExtra("userBracuId");
        String userPassword = intent.getStringExtra("userPassword");

        if (userBracuId != null) {
            txtBracuId.setText(userBracuId);
        }

        if (userPassword != null) {
            txtPassword.setText(userPassword);
        }

        Retrofit retrofit = Client.getRetrofit(LoginActivity.this);

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        btnSignIn.setOnClickListener(v -> {
            btnSignIn.setEnabled(false);
            UserLogin userLogin =
                    new UserLogin(Integer.parseInt(txtBracuId.getText().toString()),
                            txtPassword.getText().toString());
            Call<Object> call = jsonPlaceHolderApi.loginUser(userLogin);
            call.enqueue(new Callback<Object>() {

                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful()) {
                        Object sessionKey = response.body();
                        if (sessionKey instanceof String) {
                            SessionManager.saveSession(LoginActivity.this, (String) sessionKey);
                            SessionManager.saveSessionUser(LoginActivity.this, Integer.parseInt(txtBracuId.getText().toString()));
                            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                        } else if (sessionKey instanceof Boolean && !(boolean) sessionKey) {
                            Toast.makeText(LoginActivity.this, "Invalid BRACU ID or Password.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, response.code(), Toast.LENGTH_LONG).show();
                    }
                    btnSignIn.setEnabled(true);
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    btnSignIn.setEnabled(true);
                }
            });
        });
    }
}