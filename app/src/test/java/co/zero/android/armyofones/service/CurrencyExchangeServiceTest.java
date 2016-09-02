package co.zero.android.armyofones.service;

import org.junit.Before;
import org.junit.Test;

import co.zero.android.armyofones.util.Constants;

/**
 * Created by htenjo on 8/19/16.
 */
public class CurrencyExchangeServiceTest {
    private CurrencyExchangeService service;
    private String[] requiredCurrencies;

    @Before
    public void setUp() throws Exception {
        service = NetworkFactory.buildService(CurrencyExchangeService.class, Constants.SERVICE_BASE_URL);
        requiredCurrencies = new String[]{
                Constants.CURRENCY_EURO,
                Constants.CURRENCY_BRL,
                Constants.CURRENCY_GBP,
                Constants.CURRENCY_JPY};
    }

    @Test
    public void testGetLatestExchangeRates() throws Exception {
        //TODO: Implement this test
    }
}