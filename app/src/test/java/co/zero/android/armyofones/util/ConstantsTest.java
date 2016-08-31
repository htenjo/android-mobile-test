package co.zero.android.armyofones.util;

import junit.framework.Assert;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * Created by htenjo on 8/31/16.
 */
public class ConstantsTest {
    /**
     * This test is only for coverage purpose because the class doesn't have a truly behavior
     * @throws Exception
     */
    @Test
    public void testHiddenConstructor() throws Exception {
        Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();
        Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}