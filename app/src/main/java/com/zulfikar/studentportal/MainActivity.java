package com.zulfikar.studentportal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zulfikar.studentportal.account.SessionActivity;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.forum.models.PostReactions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView txtResult;
    Button btnGetStarted;
    Button btnSendRequest;

    String TAG = "MainActivityLOGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        startActivity(new Intent(MainActivity.this, IdentifierActivity.class));
        startActivity(new Intent(MainActivity.this, SessionActivity.class));

        Retrofit retrofit = Client.getRetrofit(MainActivity.this);

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        txtResult = findViewById(R.id.txtResult);
        (btnGetStarted = findViewById(R.id.btnGetStarted)).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SessionActivity.class));
        });
        (btnSendRequest = findViewById(R.id.btnSendRequest)).setOnClickListener(v -> {
            Call<PostReactions> call = jsonPlaceHolderApi.getReactions(22);
            call.enqueue(new Callback<PostReactions>() {
                @Override
                public void onResponse(Call<PostReactions> call, Response<PostReactions> response) {
                    if (response.isSuccessful()) {
                        PostReactions postReactions = response.body();
                        txtResult.setText(postReactions.toString());
                    } else {
                        Log.e(TAG, "Response Code: " + response.code() + "\n" + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<PostReactions> call, Throwable t) {

                }
            });
        });
    }
}