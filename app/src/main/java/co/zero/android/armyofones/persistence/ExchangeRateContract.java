package co.zero.android.armyofones.persistence;

import android.provider.BaseColumns;

/**
 * Created by htenjo on 8/26/16.
 */
public class ExchangeRateContract {
    public static final class RateEntry implements BaseColumns{
        public static final String TABLE_NAME = "exchange_rates";
        public static final String COLUMN_DATE = "ex_date";
        public static final String COLUMN_EURO = "euro";
        public static final String COLUMN_GBP = "gbp";
        public static final String COLUMN_JPY = "jpy";
        public static final String COLUMN_BRL = "brl";

    }
}
