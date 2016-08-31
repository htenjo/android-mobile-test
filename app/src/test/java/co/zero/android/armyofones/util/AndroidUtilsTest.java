package co.zero.android.armyofones.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.mockito.Mockito.when;

/**
 * Created by htenjo on 8/31/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class AndroidUtilsTest {
    @Mock private Context context;
    @Mock private ConnectivityManager manager;
    @Mock private NetworkInfo networkInfo;


    /**
     * This test is only for coverage purpose because the class doesn't have a truly behavior
     * @throws Exception
     */
    @Test
    public void testHiddenConstructor() throws Exception {
        Constructor<AndroidUtils> constructor = AndroidUtils.class.getDeclaredConstructor();
        Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test(expected = NullPointerException.class)
    public void testIsNetworkConnected_ContextNull() throws Exception {
        AndroidUtils.isNetworkConnected(null);
        Assert.fail("The context is null, should throw an exception");
    }

    @Test(expected = NullPointerException.class)
    public void testIsNetworkConnected_WithoutNetwork_NullManager() throws Exception {
        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(null);
        boolean networkState = AndroidUtils.isNetworkConnected(context);
        Assert.assertFalse(networkState);
    }

    @Test
    public void testIsNetworkConnected_WithoutNetwork_NullInfo() throws Exception {
        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(manager);
        boolean networkState = AndroidUtils.isNetworkConnected(context);
        Assert.assertFalse(networkState);
    }

    @Test
    public void testIsNetworkConnected_WithoutNetwork_NotIsConnected() throws Exception {
        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(manager);
        when(manager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.isConnected()).thenReturn(false);
        boolean networkState = AndroidUtils.isNetworkConnected(context);
        Assert.assertFalse(networkState);
    }

    @Test
    public void testIsNetworkConnected_OK() throws Exception {
        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(manager);
        when(manager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.isConnected()).thenReturn(true);
        boolean networkState = AndroidUtils.isNetworkConnected(context);
        Assert.assertTrue(networkState);
    }
}