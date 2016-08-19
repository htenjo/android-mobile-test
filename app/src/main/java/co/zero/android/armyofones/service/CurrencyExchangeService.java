package co.zero.android.armyofones.service;

import co.zero.android.armyofones.model.CurrencyBase;
import co.zero.android.armyofones.util.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by htenjo on 8/19/16.
 */
public interface CurrencyExchangeService {
    /**
     * Retrieve the exchange rates for the given currencies
     * @param baseCurrency Tha currency used to calculate the required exchange rates
     * @param requiredCurrencies Required rate currency symbols
     * @return All the earthQuakes generated between the dates
     */
    @GET(Constants.SERVICE_LASTEST_RESOURCE)
    Call<CurrencyBase> getLastestExchangeRates(
            @Query("base") String baseCurrency,
            @Query("symbols") String... requiredCurrencies);

    @GET(Constants.SERVICE_HISTORICAL_RESOURCE)
    Call<CurrencyBase> getHistoricalExchangeRates(
            @Path("date") String exchangeRateDate,
            @Query("symbols") String... requiredCurrencies);
}