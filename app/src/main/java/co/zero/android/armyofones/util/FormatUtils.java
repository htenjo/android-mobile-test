package co.zero.android.armyofones.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by htenjo on 8/19/16.
 */
public class FormatUtils {
    public static String formatDate(Date date, String pattern){
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String formatDouble(double number, int minDecimals, int maxDecimals){
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumFractionDigits(minDecimals);
        format.setMaximumFractionDigits(maxDecimals);
        return format.format(number);
    }
}
