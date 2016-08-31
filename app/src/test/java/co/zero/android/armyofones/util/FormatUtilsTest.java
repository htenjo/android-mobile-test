package co.zero.android.armyofones.util;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by htenjo on 8/31/16.
 */
public class FormatUtilsTest {
    /**
     * This test is only for coverage purpose because the class doesn't have a truly behavior
     * @throws Exception
     */
    @Test
    public void testHiddenConstructor() throws Exception {
        Constructor<FormatUtils> constructor = FormatUtils.class.getDeclaredConstructor();
        Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testFormatDate() throws Exception {
        String pattern = "yyyy-MM-dd";
        String expectedDateFormatted = "2016-01-01";
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.YEAR, 2016);
        currentDate.set(Calendar.MONTH, Calendar.JANUARY);
        currentDate.set(Calendar.DAY_OF_MONTH, 1);
        String dateFormatted = FormatUtils.formatDate(currentDate.getTime(), pattern);
        Assert.assertEquals(expectedDateFormatted, dateFormatted);
    }

    @Test
    public void testFormatDouble() throws Exception {
        double number = 10.5443;
        String numberFormatted = FormatUtils.formatDouble(number, 2, 2);
        String expectedNumberFormatted = "10.54";
        Assert.assertEquals(expectedNumberFormatted, numberFormatted);
    }

    @Test
    public void testFormatDoubleDefault() throws Exception {
        double number = 10.5443;
        String numberFormatted = FormatUtils.formatDouble(number);
        String expectedNumberFormatted = "10.54";
        Assert.assertEquals(expectedNumberFormatted, numberFormatted);
    }

    @Test @Ignore
    public void testFormatCurrency() throws Exception {
        double number = 5.5434;
        String currencyFormatted = FormatUtils.formatCurrency(number, Constants.CURRENCY_US);
        String expectedCurrency = "USD5.54";
        Assert.assertEquals(expectedCurrency, currencyFormatted);
    }

    @Test
    public void testParseDate() throws Exception {
        Date date = FormatUtils.parseDate("2016-01-01", "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Assert.assertEquals(2016, calendar.get(Calendar.YEAR));
        Assert.assertEquals(Calendar.JANUARY, calendar.get(Calendar.MONTH));
        Assert.assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test(expected = ParseException.class)
    public void testParseDateException() throws Exception {
        FormatUtils.parseDate("asdasd", "yyyy-MM-dd");
    }
}