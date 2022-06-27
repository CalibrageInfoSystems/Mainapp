package com.oilpalm3f.mainapp.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.datasync.helpers.DataSyncHelper;

//Receives the status of network connection
public class NetworkReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = NetworkReceiver.class.getName();

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION) || intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            boolean status = NetworkUtils.isNetworkConnected(context);
            Log.d(LOG_TAG, "# onReceive, status: "+status);
            if (status) {
                ApplicationThread.bgndPost(LOG_TAG, "", new Runnable() {
                    @Override
                    public void run() {
                        DataSyncHelper.sendTrackingData(context, new ApplicationThread.OnComplete() {
                            @Override
                            public void execute(boolean success, Object result, String msg) {
                                if (success) {
                                    Log.v(LOG_TAG, "sent success");
                                } else {
                                    Log.e(LOG_TAG, "sent failed");
                                }
                            }
                        });
                    }
                });
            }
        }
    }
}
