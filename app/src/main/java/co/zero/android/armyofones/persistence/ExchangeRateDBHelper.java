package co.zero.android.armyofones.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by htenjo on 8/26/16.
 */
public class ExchangeRateDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "exchange_rate.db";

    /**
     *
     * @param context
     */
    public ExchangeRateDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE ")
                .append(ExchangeRateContract.RateEntry.TABLE_NAME).append(" (")
                .append(ExchangeRateContract.RateEntry._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ExchangeRateContract.RateEntry.COLUMN_DATE).append(" TEXT NOT NULL, ")
                .append(ExchangeRateContract.RateEntry.COLUMN_EURO).append(" REAL NOT NULL, ")
                .append(ExchangeRateContract.RateEntry.COLUMN_GBP).append(" REAL NOT NULL, ")
                .append(ExchangeRateContract.RateEntry.COLUMN_JPY).append(" REAL NOT NULL, ")
                .append(ExchangeRateContract.RateEntry.COLUMN_BRL).append(" REAL NOT NULL);");

        sqLiteDatabase.execSQL(query.toString());
    }

    /**
     *
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ExchangeRateContract.RateEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
