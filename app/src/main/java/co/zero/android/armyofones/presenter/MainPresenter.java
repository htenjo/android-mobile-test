package co.zero.android.armyofones.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import co.zero.android.armyofones.model.CurrencyBase;
import co.zero.android.armyofones.model.Rates;
import co.zero.android.armyofones.persistence.ExchangeRateManager;
import co.zero.android.armyofones.service.CurrencyExchangeService;
import co.zero.android.armyofones.service.NetworkFactory;
import co.zero.android.armyofones.util.AndroidUtils;
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
    private ExchangeRateManager rateManager;

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
        this.rateManager = new ExchangeRateManager(((Activity)view).getApplicationContext());
    }

    /**
     * 
     */
    public void updateExchangeRates(boolean forceRemoteUpdate){
        initService();

        if(forceRemoteUpdate){
            updateDataFromRemote();
        }else{
            Rates rates = rateManager.getAvgRatesByDay(new Date());

            if(rates == null){
                updateDataFromRemote();
            }else{
                updateViews(rates);
                Log.i(this.getClass().getSimpleName(), "Exchange Rates Updated from LOCAL");
            }
        }
    }

    /**
     *
     */
    private void updateDataFromRemote(){
        if(AndroidUtils.isNetworkConnected(getContext())){
            String currenciesParams = Arrays.toString(currencies);
            currenciesParams = currenciesParams.substring(1, currenciesParams.length() - 1);

            Call<CurrencyBase> call = service.getLastestExchangeRates(Constants.CURRENCY_US, currenciesParams);
            call.enqueue(new Callback<CurrencyBase>() {
                @Override
                public void onResponse(Call<CurrencyBase> call, Response<CurrencyBase> response) {
                    CurrencyBase currencyBase = response.body();
                    Rates rates = currencyBase.getRates();
                    rateManager.insertRates(rates, new Date());
                    Log.i(MainPresenter.this.getClass().getSimpleName(), "Exchange Rates Updated from REMOTE");
                    Toast.makeText(getContext(), "Exchange Rates Updated", Toast.LENGTH_SHORT).show();
                    updateViews(rates);
                }

                @Override
                public void onFailure(Call<CurrencyBase> call, Throwable t) {
                    view.showMessage("Service Error: " + t.getMessage(), Toast.LENGTH_LONG);
                }
            });
        }else{
            Toast.makeText(getContext(), "Please connect to internet!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     *
     */
    private void initService(){
        if(service == null){
            service = NetworkFactory.buildService(CurrencyExchangeService.class, Constants.SERVICE_BASE_URL);
        }
    }

    /**
     *
     * @param rates
     */
    private void updateViews(Rates rates){
        view.updateExchangeRateValues(rates.getEUR(), rates.getGBP(), rates.getJPY(), rates.getBRL());
        view.updateValues(rates.getEUR(), rates.getGBP(), rates.getJPY(), rates.getBRL());
        List<Rates> ratesList;
        Date currentDate = new Date();

        if(view.isConfiguredDailyChart()){
            ratesList = rateManager.getRateValuesByDay(currentDate);
            view.updateChartValues(ratesList, true);
        }else{
            ratesList = rateManager.getRateValuesByMonth(currentDate);
            view.updateChartValues(ratesList, false);
        }
    }

    /**
     *
     * @return
     */
    private Context getContext(){
        return ((Activity)view).getApplicationContext();
    }
}
