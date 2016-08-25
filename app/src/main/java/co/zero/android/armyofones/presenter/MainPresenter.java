package co.zero.android.armyofones.presenter;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import co.zero.android.armyofones.model.CurrencyBase;
import co.zero.android.armyofones.model.Rates;
import co.zero.android.armyofones.service.CurrencyExchangeService;
import co.zero.android.armyofones.service.NetworkFactory;
import co.zero.android.armyofones.util.Constants;
import co.zero.android.armyofones.view.ConverterView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htenjo on 8/19/16.
 */
public class MainPresenter {
    private ConverterView view;
    private CurrencyExchangeService service;
    private static String[] currencies;
    private HashMap<String, List<Double>> cache;

    static {
        currencies = new String[]{
                Constants.CURRENCY_EURO,
                Constants.CURRENCY_GBP,
                Constants.CURRENCY_JPY,
                Constants.CURRENCY_BRL
        };
    }

    /**
     *
     * @param view
     */
    public MainPresenter(ConverterView view){
        this.view = view;
        this.cache = new HashMap<>();
    }

    /**
     * 
     */
    public void updateExchangeRates(){
        if(service == null){
            service = NetworkFactory.buildService(CurrencyExchangeService.class, Constants.SERVICE_BASE_URL);
        }

        String currenciesParams = Arrays.toString(currencies);
        currenciesParams = currenciesParams.substring(1, currenciesParams.length() - 1);

        Call<CurrencyBase> call = service.getLastestExchangeRates(Constants.CURRENCY_US, currenciesParams);
        call.enqueue(new Callback<CurrencyBase>() {
            @Override
            public void onResponse(Call<CurrencyBase> call, Response<CurrencyBase> response) {
                CurrencyBase currencyBase = response.body();
                Rates rates = currencyBase.getRates();
                Log.i(this.getClass().getSimpleName(), rates.toString());
                view.updateExchangeRateValues(rates.getEUR(), rates.getGBP(), rates.getJPY(), rates.getBRL());
                view.updateValues(rates.getEUR(), rates.getGBP(), rates.getJPY(), rates.getBRL());
                view.updateChart(rates.getEUR(), rates.getGBP(), rates.getJPY(), rates.getBRL());
            }

            @Override
            public void onFailure(Call<CurrencyBase> call, Throwable t) {
                view.showError(t.getMessage());
            }
        });
    }
}
