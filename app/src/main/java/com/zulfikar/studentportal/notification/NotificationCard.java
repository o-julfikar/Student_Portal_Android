package com.zulfikar.studentportal.notification;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.forum.PostFragment;
import com.zulfikar.studentportal.swap.SwapResultFragment;

import java.util.Locale;

public class NotificationCard {

    private final FragmentActivity fragmentActivity;
    private final Integer notificationId, senderBracuId, postId, commentId,
            courseId, instructorId, secSwapRequestId, studySwapRequestId;
    private final String notiDate, readDate, notificationContent, senderPhoto,
            notiType, reactionType;
    private View notiCard;

    public NotificationCard(Integer notificationId, Integer senderBracuId, Integer postId,
                            Integer commentId, Integer courseId, Integer instructorId,
                            Integer secSwapRequestId, Integer studySwapRequestId,
                            String notiDate, String readDate, String notificationContent,
                            String senderPhoto, String notiType, String reactionType,
                            FragmentActivity fragmentActivity
    ) {
        this.notificationId = notificationId;
        this.senderBracuId = senderBracuId;
        this.postId = postId;
        this.commentId = commentId;
        this.courseId = courseId;
        this.instructorId = instructorId;
        this.secSwapRequestId = secSwapRequestId;
        this.studySwapRequestId = studySwapRequestId;
        this.notiDate = notiDate;
        this.readDate = readDate;
        this.notificationContent = notificationContent;
        this.senderPhoto = senderPhoto;
        this.notiType = notiType;
        this.reactionType = reactionType;
        this.fragmentActivity = fragmentActivity;
    }

    public View getView(Context context, ViewGroup parent, Handler handler) {
        notiCard = LayoutInflater.from(context).inflate(R.layout.card_notification, parent, false);

        ImageView imgNotiRead;
        ShapeableImageView imgUserPhoto;
        TextView txtNotiMessage, txtNotiDate;

        imgNotiRead = notiCard.findViewById(R.id.imgNotiRead);
        imgUserPhoto = notiCard.findViewById(R.id.imgUserPhoto);
        txtNotiMessage = notiCard.findViewById(R.id.txtNotiMsg);
        txtNotiDate = notiCard.findViewById(R.id.txtNotiDate);

        if (readDate != null) imgNotiRead.setColorFilter(context.getResources().getColor(R.color.transparent, context.getTheme()), PorterDuff.Mode.MULTIPLY);
        new FetchImage(imgUserPhoto, senderPhoto, handler).start();
        txtNotiMessage.setText(notificationContent);
        txtNotiDate.setText(String.format(Locale.ENGLISH, "%s", notiDate.toString()));

        notiCard.setOnClickListener(v -> {
            String[] modelAction = notiType.split("( )+");
            String model = modelAction[0];
            String action = modelAction[1];

            if (model.equalsIgnoreCase(NotificationTypeModel.POST)) {
                PostFragment postFragment = PostFragment.getInstance(postId);
                displayFragment(postFragment);
            } else if (model.equalsIgnoreCase(NotificationTypeModel.COMMENT)) {

            } else if (model.equalsIgnoreCase(NotificationTypeModel.REPLY)) {

            } else if (model.equalsIgnoreCase(NotificationTypeModel.REVIEW)) {

            } else if (model.equalsIgnoreCase(NotificationTypeModel.SECTION_SWAP)) {
                SwapResultFragment swapResultFragment = SwapResultFragment.newInstance(
                        secSwapRequestId, SwapResultFragment.SECTION_SWAP
                );
                displayFragment(swapResultFragment);
            } else if (model.equalsIgnoreCase(NotificationTypeModel.STUDY_SWAP)) {
                SwapResultFragment swapResultFragment = SwapResultFragment.newInstance(
                        studySwapRequestId, SwapResultFragment.STUDY_SWAP
                );
                displayFragment(swapResultFragment);
            }
        });

        return notiCard;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public Integer getSenderBracuId() {
        return senderBracuId;
    }

    public Integer getPostId() {
        return postId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public Integer getInstructorId() {
        return instructorId;
    }

    public Integer getSecSwapRequestId() {
        return secSwapRequestId;
    }

    public Integer getStudySwapRequestId() {
        return studySwapRequestId;
    }

    public String getNotiDate() {
        return notiDate;
    }

    public String getReadDate() {
        return readDate;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public String getNotiType() {
        return notiType;
    }

    public String getReactionType() {
        return reactionType;
    }

    public View getNotiCard() {
        return notiCard;
    }

    private void displayFragment(Fragment fragment) {
        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFragmentContainer, fragment)
                .commit();
    }
}
