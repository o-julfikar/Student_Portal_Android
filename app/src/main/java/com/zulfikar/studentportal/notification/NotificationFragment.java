package com.zulfikar.studentportal.notification;

import static java.text.DateFormat.getDateInstance;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.Assets;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.account.SessionManager;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.notification.models.NotificationGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    RecyclerView rvNotifications;
    View rootView;
    String TAG = "NotificationFragment";

    public NotificationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        rvNotifications = rootView.findViewById(R.id.rvNotifications);

        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
        Call<List<NotificationGroup>> notificationGroupCall = jsonPlaceHolderApi.getNotifications();
        notificationGroupCall.enqueue(new Callback<List<NotificationGroup>>() {
            @Override
            public void onResponse(Call<List<NotificationGroup>> call, Response<List<NotificationGroup>> response) {
                if (response.isSuccessful()) {
                    List<NotificationGroup> notificationGroups = response.body();
                    if (notificationGroups != null) {
                        ArrayList<NotificationCardGroup> notificationCardGroups = new ArrayList<>();
                        for (NotificationGroup notificationGroup : notificationGroups) {
                            ArrayList<NotificationCard> notificationCards = new ArrayList<>();
                            for (NotificationGroup.Notification notification : notificationGroup.getNotifications()) {
                                notificationCards.add(new NotificationCard(
                                        notification.getId(),
                                        notification.getDateCreated(),
                                        notification.getDateRead(),
                                        notification.getNotificationContent(),
                                        notification.getSenderPhoto()
                                ));
                            }
                            notificationCardGroups.add(new NotificationCardGroup(
                                    notificationGroup.getGroupKey(), notificationCards
                            ));
                        }
                        NotificationCardGroupAdapter notificationCardGroupAdapter = new NotificationCardGroupAdapter(getContext(), notificationCardGroups);
                        rvNotifications.setAdapter(notificationCardGroupAdapter);
                        rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NotificationGroup>> call, Throwable t) {

            }
        });

        return rootView;
    }
}