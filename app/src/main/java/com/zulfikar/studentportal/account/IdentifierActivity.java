package com.zulfikar.studentportal.account;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.account.models.UserLogin;
import com.zulfikar.studentportal.api.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class IdentifierActivity extends AppCompatActivity {

    TextView txtBracuId;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identifier);

        txtBracuId = findViewById(R.id.txtBracuId);
        btnContinue = findViewById(R.id.btnContinue);

        Retrofit retrofit = Client.getRetrofit(IdentifierActivity.this);
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        btnContinue.setOnClickListener(v -> {
            btnContinue.setEnabled(false);
            UserLogin userLogin = new UserLogin(Integer.parseInt(txtBracuId.getText().toString()));
            Call<Boolean> call = jsonPlaceHolderApi.identifyUser(userLogin);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Boolean identified = response.body();
                        Intent intent = null;
                        if (identified != null && identified) intent = new Intent(IdentifierActivity.this, LoginActivity.class);
                        else intent = new Intent(IdentifierActivity.this, RegisterBasicInfoActivity.class);
                        intent.putExtra("userBracuId", txtBracuId.getText().toString());
                        startActivity(intent);
                    } else {
                        Toast.makeText(IdentifierActivity.this, response.code(), Toast.LENGTH_LONG).show();
                    }
                    btnContinue.setEnabled(true);
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(IdentifierActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    btnContinue.setEnabled(true);
                }
            });
        });
    }
}