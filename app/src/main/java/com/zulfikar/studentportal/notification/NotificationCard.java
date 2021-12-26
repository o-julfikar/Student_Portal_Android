package com.zulfikar.studentportal.notification;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.R;

import java.util.Date;
import java.util.Locale;

public class NotificationCard {

    private final int notificationId;
    private final String notiDate, readDate;
    private final String notiMessage, userPhoto;

    public NotificationCard(int notificationId, String notiDate, String readDate, String notiMessage, String userPhoto) {
        this.notificationId = notificationId;
        this.notiDate = notiDate;
        this.readDate = readDate;
        this.notiMessage = notiMessage;
        this.userPhoto = userPhoto;
    }

    public View getView(Context context, ViewGroup parent, Handler handler) {
        View notiCard = LayoutInflater.from(context).inflate(R.layout.card_notification, parent, false);

        ImageView imgNotiRead;
        ShapeableImageView imgUserPhoto;
        TextView txtNotiMessage, txtNotiDate;

        imgNotiRead = notiCard.findViewById(R.id.imgNotiRead);
        imgUserPhoto = notiCard.findViewById(R.id.imgUserPhoto);
        txtNotiMessage = notiCard.findViewById(R.id.txtNotiMsg);
        txtNotiDate = notiCard.findViewById(R.id.txtNotiDate);

        if (readDate != null) imgNotiRead.setColorFilter(context.getResources().getColor(R.color.transparent, context.getTheme()), PorterDuff.Mode.MULTIPLY);
        new FetchImage(imgUserPhoto, userPhoto, handler).start();
        txtNotiMessage.setText(notiMessage);
        txtNotiDate.setText(String.format(Locale.ENGLISH, "%s", notiDate.toString()));

        return notiCard;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public String getNotiDate() {
        return notiDate;
    }

    public String getReadDate() {
        return readDate;
    }

    public String getNotiMessage() {
        return notiMessage;
    }

    public String getUserPhoto() {
        return userPhoto;
    }
}
