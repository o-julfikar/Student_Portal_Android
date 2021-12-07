package com.zulfikar.studentportal.notification;

import java.util.ArrayList;

public class NotificationCardGroup {

    private String txtNotiGroupTitle;
    private ArrayList<NotificationCard> notificationCards;

    public NotificationCardGroup(String txtNotiGroupTitle, ArrayList<NotificationCard> notificationCards) {
        this.txtNotiGroupTitle = txtNotiGroupTitle;
        this.notificationCards = notificationCards;
    }

    public String getTxtNotiGroupTitle() {
        return txtNotiGroupTitle;
    }

    public ArrayList<NotificationCard> getNotificationCards() {
        return notificationCards;
    }
}
