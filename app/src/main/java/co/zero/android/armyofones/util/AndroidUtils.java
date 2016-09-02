package co.zero.android.armyofones.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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

    /**
     * This method only works if is called from an activity
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();

        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
