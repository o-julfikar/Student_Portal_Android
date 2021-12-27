package com.zulfikar.studentportal.swap.models;

import com.google.gson.annotations.SerializedName;

public class UserStudySlots {
    @SerializedName("slot_day")
    private final String slotDay;
    @SerializedName("slot_time")
    private final String slotTime;

    public UserStudySlots(String slotDay, String slotTime) {
        this.slotDay = slotDay;
        this.slotTime = slotTime;
    }

    public String getSlotDay() {
        return slotDay;
    }

    public String getSlotTime() {
        return slotTime;
    }
}
