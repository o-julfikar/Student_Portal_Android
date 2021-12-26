package com.zulfikar.studentportal.account;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zulfikar.studentportal.HomeActivity;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.api.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(SessionActivity.this);
        Call<Boolean> auth = jsonPlaceHolderApi.auth();
        auth.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Boolean result = response.body();
                    if (result != null && result) {
                        startActivity(new Intent(SessionActivity.this, HomeActivity.class));
                    } else {
                        startActivity(new Intent(SessionActivity.this, IdentifierActivity.class));
                    }
                } else {
                    Toast.makeText(SessionActivity.this, "CODE: " +
                            response.code() + "\n" +
                            response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(SessionActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
}