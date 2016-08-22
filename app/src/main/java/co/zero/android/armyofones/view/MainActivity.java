package co.zero.android.armyofones.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.List;

import co.zero.android.armyofones.R;
import co.zero.android.armyofones.presenter.MainPresenter;
import co.zero.android.armyofones.util.Constants;
import co.zero.android.armyofones.util.FormatUtils;

public class MainActivity extends BaseActivity implements ConverterView{
    private static final int EURO_DATASET_POSITION = 0;
    private static final int GBP_DATASET_POSITION = 1;
    private static final int JPY_DATASET_POSITION = 2;
    private static final int BRL_DATASET_POSITION = 3;
    private MainPresenter presenter;
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        Button convert = (Button)findViewById(R.id.button_convert);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.updateExchangeRates();
            }
        });

        paintChart();
        initChartValues();
     }

    @Override
    public void updateExchangeRateValues(double euroRate, double gbrRate, double jpyRate, double brzRate) {
        TextView rateEuField = (TextView)findViewById(R.id.text_rate_eu);
        TextView rateJpField = (TextView)findViewById(R.id.text_rate_jp);
        TextView rateGrbField = (TextView)findViewById(R.id.text_rate_uk);
        TextView rateBzField = (TextView)findViewById(R.id.text_rate_bz);

        rateEuField.setText(String.format("Rate: %s", FormatUtils.formatDouble(euroRate)));
        rateJpField.setText(String.format("Rate: %s", FormatUtils.formatDouble(jpyRate)));
        rateGrbField.setText(String.format("Rate: %s", FormatUtils.formatDouble(gbrRate)));
        rateBzField.setText(String.format("Rate: %s", FormatUtils.formatDouble(brzRate)));

        mChart.animateXY(1000, 1000);
        Toast.makeText(this, "Exchange Rates Updated", Toast.LENGTH_SHORT);
        Log.i(getLogTag(), "::::::::::: Values updated");
    }

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

    @Override
    public void updateChart(double euroRate, double gbpRate, double jpyRate, double brlRate) {
        if (mChart.getData() == null || mChart.getData().getDataSetCount() == 0) {
            initChartValues();
        }

        int entryCounter = mChart.getData().getDataSetByIndex(EURO_DATASET_POSITION).getEntryCount();
        jpyRate /= 100;
        entryCounter++;
        mChart.getData().addEntry(new Entry(entryCounter, ((Double) euroRate).floatValue()), EURO_DATASET_POSITION);
        mChart.getData().addEntry(new Entry(entryCounter, ((Double) gbpRate).floatValue()), GBP_DATASET_POSITION);
        mChart.getData().addEntry(new Entry(entryCounter, ((Double) jpyRate).floatValue()), JPY_DATASET_POSITION);
        mChart.getData().addEntry(new Entry(entryCounter, ((Double) brlRate).floatValue()), BRL_DATASET_POSITION);
        mChart.getData().notifyDataChanged();
        mChart.notifyDataSetChanged();
    }

    @Override
    public double getCurrentUSD() {
        TextView valueUsField = (TextView)findViewById(R.id.text_value_us);
        String usStringValue = valueUsField.getText().toString();

        try{
            return Double.parseDouble(usStringValue);
        }catch (NumberFormatException | NullPointerException e){
            return 0;
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT);
    }

    private void initChartValues(){
        List<Entry> euroEntries = new ArrayList<>();
        List<Entry> gbpEntries = new ArrayList<>();
        List<Entry> jpyEntries = new ArrayList<>();
        List<Entry> brlEntries = new ArrayList<>();

        if(mChart.getData() == null){
            LineDataSet euroSet = buildDataSet(Constants.CURRENCY_EURO, euroEntries, Color.rgb(239, 3, 137));
            LineDataSet gbpSet = buildDataSet(Constants.CURRENCY_GBP, gbpEntries, Color.rgb(43, 102, 196));
            LineDataSet jpySet = buildDataSet(Constants.CURRENCY_JPY + "/100", jpyEntries, Color.rgb(235, 221, 73));
            LineDataSet brlSet = buildDataSet(Constants.CURRENCY_BRL, brlEntries, Color.rgb(91, 191, 52));

            euroSet.setValueTextSize(9f);
            euroSet.setDrawValues(true);
            gbpSet.setValueTextSize(9f);
            gbpSet.setDrawValues(true);
            jpySet.setValueTextSize(9f);
            jpySet.setDrawValues(true);

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
    private void paintChart(){
        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setDescription(StringUtils.EMPTY);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        XAxis x = mChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis y = mChart.getAxisLeft();
        y.setLabelCount(5, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(true);

        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(true);
        mChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        mChart.animateXY(2000, 2000);
        // dont forget to refresh the drawing
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
