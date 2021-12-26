package com.zulfikar.studentportal;

import java.util.Date;
import java.util.Locale;

public class Utility {
    public static String simpleDateFormat(Date date) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        return String.format(Locale.ENGLISH, "%tb %<te, %<tY", date);
    }
}
