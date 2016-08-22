package co.zero.android.armyofones.view;

/**
 * Created by htenjo on 8/19/16.
 */
public interface ConverterView {
    void updateExchangeRateValues(double euroRate, double gbrRate, double jpyRate, double brzRate);
    void updateValues(double euroRate, double gbpRate, double jpyRate, double brlRate);
    void updateChart(double euroRate, double gbpRate, double jpyRate, double brlRate);
    void showError(String error);
    double getCurrentUSD();
}
