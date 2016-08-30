package co.zero.android.armyofones.view;

import java.util.List;

import co.zero.android.armyofones.model.Rates;

/**
 * Created by htenjo on 8/19/16.
 */
public interface ConverterView {
    void updateExchangeRateValues(double euroRate, double gbrRate, double jpyRate, double brzRate);
    void updateValues(double euroRate, double gbpRate, double jpyRate, double brlRate);
    void updateChartValues(List<Rates> ratesList, boolean isShowingDailyValues);
    void showMessage(String message, int length);
    double getCurrentUSD();
    boolean isConfiguredDailyChart();
}
