package com.zulfikar.studentportal.notification;

import static java.text.DateFormat.getDateInstance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zulfikar.studentportal.Assets;
import com.zulfikar.studentportal.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class NotificationFragment extends Fragment {

    RecyclerView rvNotifications;
    View rootView;
    String TAG = "NotificationFragment";

    public NotificationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        Log.d(TAG, "onCreateView: " +  LocalDate.now().toString());
        try {
            Log.d(TAG, "textxam" + getDateInstance(DateFormat.SHORT).parse("10/19/18"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        rvNotifications = rootView.findViewById(R.id.rvNotifications);
        ArrayList<NotificationCard> notificationCards = new ArrayList<>();
        notificationCards.add(
                new NotificationCard(0, parse("8/12/2021 0:16:0", "dd/MM/yyyy hh:mm:ss"), null, "Steve Jobs and other 609 people commented on your post.", Assets.defaultImg)
        );
        notificationCards.add(
                new NotificationCard(0, parse("8/12/2021 0:10:0", "dd/MM/yyyy hh:mm:ss"), parse("8/12/2021 0:11:35", "dd/MM/yyyy hh:mm:ss"), "Congratulations! Your section swap request for CSE110 section 2 is accepted by all the members.", Assets.defaultImg)
        );
        notificationCards.add(
                new NotificationCard(0, parse("8/12/2021 0:3:0", "dd/MM/yyyy hh:mm:ss"), null, "Sourojit Roy has posted a new post for CSE110 for Fall 2021. With tags #flowcharts #assignment2 ...", Assets.defaultImg)
        );
        notificationCards.add(
                new NotificationCard(0, parse("18/7/2021", "dd/MM/yyyy"), parse("19/7/2021 16:13:40", "dd/MM/yyyy hh:mm:ss"), "Afsan, Hasnayen and Sohan accepted your section swap request for CSE110 section 2.", Assets.defaultImg)
        );
        notificationCards.add(
                new NotificationCard(0, parse("6/7/2021", "dd/MM/yyyy"), null, "You recieved a study planner request. Click here to view the planner in detail.", Assets.defaultImg)
        );
        notificationCards.add(
                new NotificationCard(0, parse("14/6/2020", "dd/MM/yyyy"), null, "Imtiyaz Bhiyan just liked your post.", Assets.defaultImg)
        );
        notificationCards.add(
                new NotificationCard(0, parse("17/6/2020", "dd/MM/yyyy"), null, "Sohan and Shahriyar are waiting for your response on a study plan. Click to view the study planner.", Assets.defaultImg)
        );
        notificationCards.add(
                new NotificationCard(0, parse("18/7/2015", "dd/MM/yyyy"), null, "Abu Hasnayen Zillanee got a new review from a student for CSE421. Click to view.", Assets.defaultImg)
        );

        NotificationCardGroupAdapter notificationCardGroupAdapter = new NotificationCardGroupAdapter(getContext(), arrangeNotifications(notificationCards));
        rvNotifications.setAdapter(notificationCardGroupAdapter);
        rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    private ArrayList<NotificationCardGroup> arrangeNotifications(ArrayList<NotificationCard> notificationCards) {
        ArrayList<NotificationCardGroup> notificationCardGroups = new ArrayList<>();

        notificationCards.sort(Comparator.comparing(NotificationCard::getNotiDate));
        HashMap<String, ArrayList<NotificationCard>> groupMap = new LinkedHashMap<>();

        notificationCards.forEach(notificationCard -> {
            String groupTitle = getGroupTitle(notificationCard.getNotiDate());
            if (!groupMap.containsKey(groupTitle)) {
                groupMap.put(groupTitle, new ArrayList<>());
            }
            groupMap.get(groupTitle).add(notificationCard);
        });

        List groupTitles = Arrays.asList(groupMap.keySet().toArray());
        Collections.reverse(groupTitles);
        for (Object title : groupTitles) {
            notificationCardGroups.add(new NotificationCardGroup((String) title, groupMap.get(title)));
        };

        return notificationCardGroups;
    }

    private String getGroupTitle(Date date) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(new Date());
        calendar2.setTime(date);

        int date1Days = calendar1.get(Calendar.DAY_OF_MONTH) +
                calendar1.get(Calendar.MONTH) * 30 +
                calendar1.get(Calendar.YEAR) * 365;

        int date2Days = calendar2.get(Calendar.DAY_OF_MONTH) +
                calendar2.get(Calendar.MONTH) * 30 +
                calendar2.get(Calendar.YEAR) * 365;

        int difference = date1Days - date2Days;

        if (difference < 0) return "Future";
        else if (difference == 0) return "Today";
        else if (difference == 1) return "Yesterday";
        else if (difference == 2) return "Before yesterday";
        else if (difference < 7) return "Within this week";
        else if (difference < 30) return difference / 7 + " week" + (difference >= 14? "s" : "");
        else if (difference < 365) return difference / 30 + " month" + (difference >= 60? "s" : "");

        return difference / 365 + " year" + (difference >= 730? "s" : "");
    }

    private Date parse(String dateStr, String datePattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.ENGLISH);

        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    private Date getDate(String dateStr, int dateFormat) {
        try {
            return getDateInstance(dateFormat).parse(getDateShort(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    private String getDateShort(String dateStr) {
        String[] dateStrSplit = dateStr.split("-");
        if (dateStrSplit.length < 3) return "6/16/1990";
        return dateStrSplit[1] + "/" + dateStrSplit[2] + "/" + dateStrSplit[0];
    }
}