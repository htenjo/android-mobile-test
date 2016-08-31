package co.zero.android.armyofones.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by htenjo on 8/26/16.
 */
public class AndroidUtils {
    private AndroidUtils(){
    }

    /**
     * This service checks the state of the network in the device. You need to grand the required
     * permissions in the AndroidManifest.xml: ACCESS_WIFI_STATE, ACCESS_NETWORK_STATE
     * @param context Object that can access the required information
     * @return True if the device has an active network, false otherwise
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
