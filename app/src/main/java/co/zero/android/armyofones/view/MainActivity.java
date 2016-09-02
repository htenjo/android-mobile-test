package co.zero.android.armyofones.view;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import co.zero.android.armyofones.R;
import co.zero.android.armyofones.model.Rates;
import co.zero.android.armyofones.presenter.MainPresenter;
import co.zero.android.armyofones.util.AndroidUtils;
import co.zero.android.armyofones.util.Constants;
import co.zero.android.armyofones.util.FormatUtils;

public class MainActivity extends BaseActivity implements ConverterView{
    private static final int EURO_DATA_SET_POSITION = 0;
    private static final int GBP_DATA_SET_POSITION = 1;
    private static final int JPY_DATA_SET_POSITION = 2;
    private static final int BRL_DATA_SET_POSITION = 3;
    private static final int DEFAULT_CHART_ANIMATION_TIME = 1500;
    private static final int JAPAN_SCALE_FACTOR = 100;
    private static final int BRAZIL_SCALE_FACTOR = 3;
    private static final String SAVED_RATES_NAME = "currentRates";
    public static final String SAVED_VALUE_NAME = "currentValue";
    private static final String PREFERENCES_NAME = "ArmyOfOnesPref";
    public static final String PREFERENCE_CHART_DAILY = "dailyChartType";
    private HashMap<String, Double> currentRates;
    private Double currentValue;
    private MainPresenter presenter;
    private LineChart mChart;
    private Menu menu;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        currentRates = new HashMap<>();

        Button convert = (Button)findViewById(R.id.button_convert);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.updateExchangeRates(false);
                AndroidUtils.hideSoftKeyboard(MainActivity.this);
            }
        });

        EditText valueUsField = (EditText)findViewById(R.id.text_value_us);
        valueUsField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    presenter.updateExchangeRates(false);
                    AndroidUtils.hideSoftKeyboard(MainActivity.this);
                }

                return true;
            }
        });

        buildChart();
        initChartDataSets();
     }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        this.menu = menu;

        if(!settings.contains(PREFERENCE_CHART_DAILY)){
            editor.putBoolean(PREFERENCE_CHART_DAILY, true).apply();
        }

        updateMenuChartOptions();
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        switch (item.getItemId()){
            case R.id.menu_update_data:
                presenter.updateExchangeRates(true);
                break;
            case R.id.menu_chart_daily:
                editor.putBoolean(PREFERENCE_CHART_DAILY, true).apply();
                updateMenuChartOptions();
                presenter.updateExchangeRates(false);
                break;
            case R.id.menu_chart_monthly:
                editor.putBoolean(PREFERENCE_CHART_DAILY, false).apply();
                updateMenuChartOptions();
                presenter.updateExchangeRates(false);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    /**
     *
     */
    private void updateMenuChartOptions(){
        SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
        boolean dailyChart = settings.getBoolean(PREFERENCE_CHART_DAILY, true);
        MenuItem dailyItem = menu.findItem(R.id.menu_chart_daily);
        MenuItem monthlyItem = menu.findItem(R.id.menu_chart_monthly);

        if(dailyChart){
            dailyItem.setChecked(true);
        }else{
            monthlyItem.setChecked(true);
        }
    }

    /**
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_RATES_NAME, currentRates);

        if(currentValue != null) {
            outState.putDouble(SAVED_VALUE_NAME, currentValue);
        }
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentRates = (HashMap<String, Double>) savedInstanceState.getSerializable(SAVED_RATES_NAME);
        currentValue = savedInstanceState.getDouble(SAVED_VALUE_NAME);

        if(currentRates.size() > 0) {
            updateExchangeRateValues(
                    currentRates.get(Constants.CURRENCY_EURO),
                    currentRates.get(Constants.CURRENCY_GBP),
                    currentRates.get(Constants.CURRENCY_JPY),
                    currentRates.get(Constants.CURRENCY_BRL));
            updateValues(
                    currentRates.get(Constants.CURRENCY_EURO),
                    currentRates.get(Constants.CURRENCY_GBP),
                    currentRates.get(Constants.CURRENCY_JPY),
                    currentRates.get(Constants.CURRENCY_BRL));
        }
    }

    /**
     *
     * @param euroRate
     * @param gbrRate
     * @param jpyRate
     * @param brzRate
     */
    @Override
    public void updateExchangeRateValues(double euroRate, double gbrRate, double jpyRate, double brzRate) {
        currentRates.put(Constants.CURRENCY_EURO, euroRate);
        currentRates.put(Constants.CURRENCY_GBP, gbrRate);
        currentRates.put(Constants.CURRENCY_JPY, jpyRate);
        currentRates.put(Constants.CURRENCY_BRL, brzRate);
        TextView rateEuField = (TextView)findViewById(R.id.text_rate_eu);
        TextView rateJpField = (TextView)findViewById(R.id.text_rate_jp);
        TextView rateGrbField = (TextView)findViewById(R.id.text_rate_uk);
        TextView rateBzField = (TextView)findViewById(R.id.text_rate_bz);
        String template = Constants.TEXT_RATE_TEMPLATE;

        rateEuField.setText(String.format(template, FormatUtils.formatDouble(euroRate)));
        rateJpField.setText(String.format(template, FormatUtils.formatDouble(jpyRate)));
        rateGrbField.setText(String.format(template, FormatUtils.formatDouble(gbrRate)));
        rateBzField.setText(String.format(template, FormatUtils.formatDouble(brzRate)));

        mChart.animateXY(DEFAULT_CHART_ANIMATION_TIME, DEFAULT_CHART_ANIMATION_TIME);
        Log.i(getLogTag(), "Exchange rates updated in MainView");
    }

    /**
     *
     * @param euroRate
     * @param gbpRate
     * @param jpyRate
     * @param brlRate
     */
    @Override
    public void updateValues(double euroRate, double gbpRate, double jpyRate, double brlRate) {
        TextView valueEuField = (TextView)findViewById(R.id.text_value_eu);
        TextView valueJpField = (TextView)findViewById(R.id.text_value_jp);
        TextView valueUkField = (TextView)findViewById(R.id.text_value_uk);
        TextView valueBzField = (TextView)findViewById(R.id.text_value_bz);

        double usValue = getCurrentUSD();
        double euroValue = usValue * euroRate;
        double gbrValue = usValue * gbpRate;
        double jpValue = usValue * jpyRate;
        double bzValue = usValue * brlRate;

        valueEuField.setText(FormatUtils.formatCurrency(euroValue, getStringFromId(R.string.currency_euro_code)));
        valueJpField.setText(FormatUtils.formatCurrency(jpValue, getStringFromId(R.string.currency_jpn_code)));
        valueUkField.setText(FormatUtils.formatCurrency(gbrValue, getStringFromId(R.string.currency_grb_code)));
        valueBzField.setText(FormatUtils.formatCurrency(bzValue, getStringFromId(R.string.currency_brl_code)));
    }

    /**
     *
     * @param ratesList
     * @param isShowingDailyValues
     */
    @Override
    public void updateChartValues(List<Rates> ratesList, boolean isShowingDailyValues){
        mChart.clear();
        Calendar calendar = Calendar.getInstance();

        if (mChart.getData() == null || mChart.getData().getDataSetCount() == 0) {
            initChartDataSets();
        }

        for (int i = 0; i < ratesList.size(); i++) {
            Rates rates = ratesList.get(i);
            int xIndex = i + 1;

            if(!isShowingDailyValues){
                calendar.setTime(rates.getDate());
                xIndex = calendar.get(Calendar.DAY_OF_MONTH);
            }

            mChart.getData().addEntry(new Entry(xIndex, rates.getEUR().floatValue()), EURO_DATA_SET_POSITION);
            mChart.getData().addEntry(new Entry(xIndex, rates.getGBP().floatValue()), GBP_DATA_SET_POSITION);
            mChart.getData().addEntry(new Entry(xIndex, rates.getJPY().floatValue() / JAPAN_SCALE_FACTOR), JPY_DATA_SET_POSITION);
            mChart.getData().addEntry(new Entry(xIndex, rates.getBRL().floatValue() / BRAZIL_SCALE_FACTOR), BRL_DATA_SET_POSITION);
        }

        mChart.notifyDataSetChanged();
        mChart.getData().notifyDataChanged();
        mChart.invalidate();
        mChart.setVisibility(View.VISIBLE);
    }

    /**
     *
     * @return
     */
    @Override
    public double getCurrentUSD() {
        TextView valueUsField = (TextView)findViewById(R.id.text_value_us);
        String usStringValue = valueUsField.getText().toString();

        try{
            currentValue = Double.parseDouble(usStringValue);
            return currentValue;
        }catch (NumberFormatException | NullPointerException e){
            return 0;
        }
    }

    /**
     *
     * @param message
     */
    @Override
    public void showMessage(String message, int length) {
        Toast.makeText(this, message, length).show();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isConfiguredDailyChart() {
        SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
        return settings.getBoolean(PREFERENCE_CHART_DAILY, true);
    }

    /**
     *
     */
    private void initChartDataSets(){
        List<Entry> euroEntries = new ArrayList<>();
        List<Entry> gbpEntries = new ArrayList<>();
        List<Entry> jpyEntries = new ArrayList<>();
        List<Entry> brlEntries = new ArrayList<>();

        if(mChart.getData() == null){
            LineDataSet euroSet = buildDataSet(Constants.CURRENCY_EURO, euroEntries, Color.rgb(239, 3, 137));
            LineDataSet gbpSet = buildDataSet(Constants.CURRENCY_GBP, gbpEntries, Color.rgb(43, 102, 196));
            LineDataSet jpySet = buildDataSet(Constants.CURRENCY_JPY + "/" + JAPAN_SCALE_FACTOR, jpyEntries, Color.rgb(235, 221, 73));
            LineDataSet brlSet = buildDataSet(Constants.CURRENCY_BRL + "/" + BRAZIL_SCALE_FACTOR, brlEntries, Color.rgb(91, 191, 52));

            euroSet.setValueTextSize(9f);
            gbpSet.setValueTextSize(9f);
            jpySet.setValueTextSize(9f);
            brlSet.setValueTextSize(9f);

            LineData data = new LineData();
            data.addDataSet(euroSet);
            data.addDataSet(gbpSet);
            data.addDataSet(jpySet);
            data.addDataSet(brlSet);

            mChart.setData(data);
        }
    }

    /**
     *
     */
    private void buildChart(){
        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setDescription(StringUtils.EMPTY);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setNoDataText("Ups! No rates found...");

        XAxis x = mChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        //Max days of month
        x.setAxisMinValue(0);
        x.setAxisMaxValue(35);

        YAxis y = mChart.getAxisLeft();
        y.setLabelCount(4, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(true);

        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(true);
        mChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        mChart.animateXY(DEFAULT_CHART_ANIMATION_TIME - 1000, DEFAULT_CHART_ANIMATION_TIME - 1000);
        // don't forget to refresh the drawing
        mChart.invalidate();
    }

    /**
     *
     * @param label
     * @param values
     * @param color
     * @return
     */
    private LineDataSet buildDataSet(String label, List<Entry> values, int color){
        LineDataSet set = new LineDataSet(values, label);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.2f);
        set.setDrawCircles(false);
        set.setLineWidth(3f);
        set.setCircleRadius(5f);
        set.setCircleColor(color);
        set.setDrawCircles(true);
        set.setHighLightColor(Color.BLACK);
        set.setColor(color);
        set.setDrawHorizontalHighlightIndicator(true);
        return set;
    }
}
