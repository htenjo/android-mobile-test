package co.zero.android.armyofones.presenter;

import android.util.Log;

import java.util.Arrays;

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

    static {
        currencies = new String[]{
                Constants.CURRENCY_BRAZIL,
                Constants.CURRENCY_ENGLAND,
                Constants.CURRENCY_EURO,
                Constants.CURRENCY_JAPAN
        };
    }

    public MainPresenter(ConverterView view){
        this.view = view;
    }

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
                view.updateRateValues(rates.getEUR(), rates.getGBP(), rates.getJPY(), rates.getBRL());
            }

            @Override
            public void onFailure(Call<CurrencyBase> call, Throwable t) {
                view.showError(t.getMessage());
            }
        });
    }
}
