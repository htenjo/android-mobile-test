package co.zero.android.armyofones.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
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

    public static String formatDouble(double number){
        return formatDouble(number, Constants.DEFAULT_MIN_DECIMALS, Constants.DEFAULT_MAX_DECIMALS);
    }

    public static String formatCurrency(double value, String currencyCode){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        Currency currency = Currency.getInstance(currencyCode);
        format.setMinimumFractionDigits(Constants.DEFAULT_MIN_DECIMALS);
        format.setMaximumFractionDigits(Constants.DEFAULT_MAX_DECIMALS);
        format.setCurrency(currency);
        return format.format(value);
    }

    public static Date parseDate(String dateFormated, String format) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(dateFormated);
    }
}
