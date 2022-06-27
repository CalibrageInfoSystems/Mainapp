package com.oilpalm3f.mainapp.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


//Network releated methods
public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getName();

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info != null && info.isConnected();
    }

    public static boolean isRoamingNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null && isNetworkTypeMobile(networkInfo.getType()) && networkInfo
                    .isConnected()) {
                return networkInfo.isRoaming();
            }
        }

        return false;
    }

    public static boolean isMobileNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            return networkInfo != null && isNetworkTypeMobile(networkInfo.getType()) && networkInfo
                    .isConnected();
        }

        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context
                        .CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnected();

    }

    /**
     * This one is copied from ConnectivityManager because of missing weird type
     * Checks if a given type uses the cellular data connection.
     * This should be replaced in the future by a network property.
     *
     * @param networkType the type to check
     * @return a boolean - {@code true} if uses cellular network, else {@code false}
     */

    private static final int TYPE_MOBILE_FOTA = 10;
    private static final int TYPE_MOBILE_IMS = 11;
    private static final int TYPE_MOBILE_CBS = 12;
    private static final int TYPE_MOBILE_IA = 14;

    public static boolean isNetworkTypeMobile(int networkType) {
        switch (networkType) {
            case ConnectivityManager.TYPE_MOBILE:
            case ConnectivityManager.TYPE_MOBILE_MMS:
            case ConnectivityManager.TYPE_MOBILE_SUPL:
            case ConnectivityManager.TYPE_MOBILE_DUN:
            case ConnectivityManager.TYPE_MOBILE_HIPRI:
            case TYPE_MOBILE_FOTA:
            case TYPE_MOBILE_IMS:
            case TYPE_MOBILE_CBS:
            case TYPE_MOBILE_IA:
                return true;
            default:
                return false;
        }
    }
}
