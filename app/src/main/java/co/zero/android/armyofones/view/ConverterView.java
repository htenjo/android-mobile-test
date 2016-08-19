package co.zero.android.armyofones.view;

/**
 * Created by htenjo on 8/19/16.
 */
public interface ConverterView {
    void updateRateValues(double euro, double gbr, double jpy, double brz);
    void showError(String error);
}
