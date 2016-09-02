package co.zero.android.armyofones.service;

import co.zero.android.armyofones.model.CurrencyBase;
import co.zero.android.armyofones.util.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
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
    @GET(Constants.SERVICE_LATEST_RESOURCE)
    Call<CurrencyBase> getLatestExchangeRates(
            @Query("base") String baseCurrency,
            @Query("symbols") String requiredCurrencies);
}