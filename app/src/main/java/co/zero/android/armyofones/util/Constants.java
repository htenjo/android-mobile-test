package co.zero.android.armyofones.util;

/**
 * Created by htenjo on 8/19/16.
 */
public class Constants {
    public static final String CURRENCY_US = "USD";
    public static final String CURRENCY_EURO = "EUR";
    public static final String CURRENCY_GBP = "GBP";
    public static final String CURRENCY_BRL = "BRL";
    public static final String CURRENCY_JPY = "JPY";

    public static final String SERVICE_BASE_URL = "http://api.fixer.io";
    public static final String SERVICE_LASTEST_RESOURCE = "/latest";
    public static final String SERVICE_HISTORICAL_RESOURCE = "/{date}";
    public static final String SERVICE_DATE_FORMAT = "yyyy-MM-dd";
    public static final int DEFAULT_MIN_DECIMALS  = 2;
    public static final int DEFAULT_MAX_DECIMALS  = 2;

    public static final String FONT_HUGE_BOLD_PATH = "fonts/huge_bold.ttf";
    public static final String FONT_HUGE_NORMAL_PATH = "fonts/huge_bold.ttf";

    public static final String POJO_FIELD_BASE = "base";
    public static final String POJO_FIELD_DATE = "date";
    public static final String POJO_FIELD_RATES = "rates";
    public static final String POJO_FIELD_BRL = CURRENCY_BRL;
    public static final String POJO_FIELD_GBP = CURRENCY_GBP;
    public static final String POJO_FIELD_JPY = CURRENCY_JPY;
    public static final String POJO_FIELD_EUR = CURRENCY_EURO;

    public static final String TEXT_RATE_TEMPLATE = "Rate: %s";

    private Constants(){
    }
}
