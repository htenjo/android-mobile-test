package co.zero.android.armyofones.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.zero.android.armyofones.model.Rates;
import co.zero.android.armyofones.util.FormatUtils;

/**
 * Created by htenjo on 8/26/16.
 */
public class ExchangeRateManager {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final int COLUMN_EURO_INDEX = 0;
    private static final int COLUMN_GBR_INDEX = 1;
    private static final int COLUMN_JPY_INDEX = 2;
    private static final int COLUMN_BRL_INDEX = 3;
    private static final int COLUMN_DATE_INDEX = 4;
    private ExchangeRateDBHelper dbHelper;

    public ExchangeRateManager(Context context) {
        this.dbHelper = new ExchangeRateDBHelper(context);
    }

    /**
     *
     * @param rates
     * @param date
     * @return
     */
    public long insertRates(Rates rates, Date date){
        ContentValues values = new ContentValues();
        values.put(ExchangeRateContract.RateEntry.COLUMN_EURO, rates.getEUR());
        values.put(ExchangeRateContract.RateEntry.COLUMN_GBP, rates.getGBP());
        values.put(ExchangeRateContract.RateEntry.COLUMN_JPY, rates.getJPY());
        values.put(ExchangeRateContract.RateEntry.COLUMN_BRL, rates.getBRL());
        values.put(ExchangeRateContract.RateEntry.COLUMN_DATE, FormatUtils.formatDate(date, DEFAULT_DATE_FORMAT));
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        try{
            return sqLiteDatabase.insert(ExchangeRateContract.RateEntry.TABLE_NAME, null, values);
        }finally {
            sqLiteDatabase.close();
        }
    }

    /**
     * This method calculate the avg exchanged values for a given day
     * @param date The date of the required rates
     * @return Object with the average rates of the day
     */
    public Rates getAvgRatesByDay(Date date){
        String query = buildQueryRatesValuesByDay();
        String dateFormatted = FormatUtils.formatDate(date, DEFAULT_DATE_FORMAT);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(query.toString(), new String[]{dateFormatted});
        Rates rates = null;

        if(cursor.moveToFirst()){
            rates = new Rates();
            cursor.moveToFirst();
            rates.setEUR(cursor.getDouble(COLUMN_EURO_INDEX));
            rates.setGBP(cursor.getDouble(COLUMN_GBR_INDEX));
            rates.setJPY(cursor.getDouble(COLUMN_JPY_INDEX));
            rates.setBRL(cursor.getDouble(COLUMN_BRL_INDEX));
        }

        return rates;
    }

    /**
     * This method calculate the average exchanges rates for a Month given a day
     * @param date The day of the month of the required rates
     * @return Collection on Rates (one per day) of the month
     */
    public List<Rates> getRateValuesByMonth(Date date) throws ParseException {
        String[] params = buildParamsQueryRatesValuesByMonth(date);
        String query = buildQueryRatesValuesByMonth();
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query.toString(), params);
        List<Rates> ratesList = new ArrayList<>();
        Rates rates;

        if(cursor.moveToFirst()){
            rates = new Rates();
            rates.setEUR(cursor.getDouble(COLUMN_EURO_INDEX));
            rates.setGBP(cursor.getDouble(COLUMN_GBR_INDEX));
            rates.setJPY(cursor.getDouble(COLUMN_JPY_INDEX));
            rates.setBRL(cursor.getDouble(COLUMN_BRL_INDEX));

            try{
                String dateFormatted = cursor.getString(COLUMN_DATE_INDEX);
                rates.setDate(FormatUtils.parseDate(dateFormatted, DEFAULT_DATE_FORMAT));
            }catch(ParseException e){
                Log.e(getClass().getSimpleName(), e.getMessage());
            }
        }

        return ratesList;
    }

    /**
     *
     * @return
     */
    private String buildQueryRatesValuesByDay(){
        StringBuilder query = new StringBuilder();
        query.append("SELECT AVG(").append(ExchangeRateContract.RateEntry.COLUMN_EURO).append(")")
                .append(", AVG(").append(ExchangeRateContract.RateEntry.COLUMN_GBP).append(")")
                .append(", AVG(").append(ExchangeRateContract.RateEntry.COLUMN_JPY).append(")")
                .append(", AVG(").append(ExchangeRateContract.RateEntry.COLUMN_BRL).append(")")
                .append(" FROM ").append(ExchangeRateContract.RateEntry.TABLE_NAME)
                .append(" WHERE ").append(ExchangeRateContract.RateEntry.COLUMN_DATE).append(" = Date(?)")
                .append(" GROUP BY ").append(ExchangeRateContract.RateEntry.COLUMN_DATE)
                .append(" ORDER BY ").append(ExchangeRateContract.RateEntry._ID);
        return query.toString();
    }

    /**
     *
     * @return
     */
    private String buildQueryRatesValuesByMonth(){
        StringBuilder query = new StringBuilder();
        query.append("SELECT AVG(").append(ExchangeRateContract.RateEntry.COLUMN_EURO).append(")")
                .append(", AVG(").append(ExchangeRateContract.RateEntry.COLUMN_GBP).append(")")
                .append(", AVG(").append(ExchangeRateContract.RateEntry.COLUMN_JPY).append(")")
                .append(", AVG(").append(ExchangeRateContract.RateEntry.COLUMN_BRL).append("), ")
                .append(ExchangeRateContract.RateEntry.COLUMN_DATE)
                .append(" FROM ").append(ExchangeRateContract.RateEntry.TABLE_NAME)
                .append(" WHERE ").append(ExchangeRateContract.RateEntry.COLUMN_DATE)
                .append("   BETWEEN Date(?) AND Date(?)")
                .append(" GROUP BY ").append(ExchangeRateContract.RateEntry.COLUMN_DATE)
                .append(" ORDER BY ").append(ExchangeRateContract.RateEntry._ID);
        return query.toString();
    }

    /**
     *
     * @param date
     * @return
     */
    private String[] buildParamsQueryRatesValuesByMonth(Date date){
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);
        startDate.set(Calendar.DAY_OF_MONTH, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(date);
        endDate.set(Calendar.DAY_OF_MONTH, endDate.getMaximum(Calendar.DAY_OF_MONTH));
        String startDateFormatted = FormatUtils.formatDate(startDate.getTime(), DEFAULT_DATE_FORMAT);
        String endDateFormatted = FormatUtils.formatDate(endDate.getTime(), DEFAULT_DATE_FORMAT);
        return new String[]{startDateFormatted, endDateFormatted};
    }
}
