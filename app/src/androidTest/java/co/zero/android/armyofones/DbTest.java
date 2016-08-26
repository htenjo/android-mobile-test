package co.zero.android.armyofones;

import junit.framework.TestCase;

import co.zero.android.armyofones.persistence.ExchangeRateDBHelper;

/**
 * Created by htenjo on 8/26/16.
 */
//@RunWith(AndroidJUnit4.class)
public class DbTest extends TestCase{
    private ExchangeRateDBHelper dbHelper;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        //InstrumentationR context = new RenamingDelegatingContext(getContext(), "test_");
        //dbHelper = new ExchangeRateDBHelper();
    }

    @Override
    public void tearDown() throws Exception {
        dbHelper.close();
        super.tearDown();
    }

    public void testDBCreation(){

    }
}
