package co.zero.android.armyofones.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by htenjo on 8/26/16.
 */
public class AndroidUtils {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
