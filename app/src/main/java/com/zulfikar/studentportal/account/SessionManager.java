package com.zulfikar.studentportal.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.zulfikar.studentportal.Constants;
import com.zulfikar.studentportal.HomeActivity;
import com.zulfikar.studentportal.account.models.User;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionManager {
    private static SharedPreferences sharedPreferences;

    private static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null) sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    public static void saveSession(Context context, String sessionKey) {
        getEditor(context).putString(Constants.SESSION_KEY, sessionKey).commit();
    }

    public static void saveSessionUser(Context context, int userBracuId) {
        getEditor(context).putInt(Constants.LOGGED_IN_USER_ID, userBracuId).commit();
    }

    public static void deleteSession(Context context) {
        getEditor(context).remove(Constants.SESSION_KEY).commit();
        getEditor(context).remove(Constants.LOGGED_IN_USER_ID).commit();
    }

    public static String getSessionKey(Context context) {
        return getSharedPreferences(context).getString(Constants.SESSION_KEY, "");
    }

    public static int getLoggedUserBracuId(Context context) {
        return getSharedPreferences(context).getInt(Constants.LOGGED_IN_USER_ID, -1);
    }

    public static void logout(Context context) {
        deleteSession(context);
        auth(context);
    }

    public static void auth(Context context) {
        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(context);
        Call<Boolean> auth = jsonPlaceHolderApi.auth();
        auth.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Boolean result = response.body();
                    if (result == null || !result) {
                        deleteSession(context);
                        context.startActivity(new Intent(context, IdentifierActivity.class));
                        Toast.makeText(context, "You session has expired.", Toast.LENGTH_LONG).show();
                    } else if (getLoggedUserBracuId(context) == -1) {
                        Call<User> userCall = jsonPlaceHolderApi.getUser();
                        userCall.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    User user = response.body();
                                    if (user != null) {
                                        SessionManager.saveSessionUser(context, user.getBracu_id());
                                    }
                                } else {
                                    Toast.makeText(context, "CODE: " +
                                            response.code() + "\n" +
                                            response.errorBody().toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(context, "CODE: " +
                            response.code() + "\n" +
                            response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
