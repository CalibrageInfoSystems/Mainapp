package com.oilpalm3f.mainapp.alerts;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Siva on 13-11-2017.
 */

//Assign values to Alert Type
public enum AlertType {
    ALERT_PLOT_FOLLOWUP(100),
    ALERT_PLOT_VISITS(101),
    ALERT_PLOT_MISSING_TREES(102);
    private final int value;
    public int getValue() {
        return value;
    }

    @SuppressLint("UseSparseArrays")
    private static Map<Integer, AlertType> alertTypeHashMap = new HashMap<>();

    AlertType (final int value) { this.value = value; }

    static {
        for (AlertType alertType : AlertType.values()) {
            alertTypeHashMap.put(alertType.value, alertType);
        }
    }

    public static AlertType valueOf(int value) {
        return alertTypeHashMap.get(value);
    }
}
