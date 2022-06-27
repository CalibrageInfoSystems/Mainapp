package com.oilpalm3f.mainapp.activitylogdetails;

import android.content.Context;
import android.location.LocationManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areacalculator.LatLongListener;
import com.oilpalm3f.mainapp.areacalculator.LocationProvider;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Palm3FoilDatabase;
import com.oilpalm3f.mainapp.datasync.helpers.DataSyncHelper;
import com.oilpalm3f.mainapp.dbmodels.FarmerBank;
import com.oilpalm3f.mainapp.dbmodels.VisitLog;
import com.oilpalm3f.mainapp.ui.OilPalmBaseActivity;
import com.oilpalm3f.mainapp.utils.UiUtils;

public class LogBookScreenActivity extends OilPalmBaseActivity {
    EditText clientname, mobileNumber, location, details;
    public Context context;
    Button save;
    DataAccessHandler dataAccessHandler;
    String clientname_str, location_str, details_str, mobileNumber_str, createdDate, serverStatus;
    private Palm3FoilDatabase palm3FoilDatabase;
    private static final String LOG_TAG = "MyService";
    float latitude, longitude;
    int createdByUser;
    private static LocationProvider mLocationProvider;
    private double currentLatitude, currentLongitude;
    private static String latLong = "";
    LocationManager lm;

    //GetLocation
    public static LocationProvider getLocationProvider(Context context, boolean showDialog) {
        if (mLocationProvider == null) {
            mLocationProvider = new LocationProvider(context, new LatLongListener() {

                public void getLatLong(String mLatLong) {
                    latLong = mLatLong;
                }
            });

        }
        if (mLocationProvider.getLocation(showDialog)) {
            return mLocationProvider;
        } else {
            return null;
        }

    }



    public String getLatLong(Context context, boolean showDialog) {

        mLocationProvider = getLocationProvider(context, showDialog);

        if (mLocationProvider != null) {
            latLong = mLocationProvider.getLatitudeLongitude();


        }
        return latLong;
    }

    //Initializing the UI
    @Override
    public void Initialize() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View parentView = inflater.inflate(R.layout.activity_client_details, null);
        baseLayout.addView(parentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setTile("Log Book");
        dataAccessHandler = new DataAccessHandler(this);
        palm3FoilDatabase = new Palm3FoilDatabase(this);
        clientname = findViewById(R.id.clientname);
        mobileNumber = findViewById(R.id.clientmobilenum);
        location = findViewById(R.id.clientlocation);
        details = findViewById(R.id.clientdetails);
        save = findViewById(R.id.save);


        if (android.os.Build.VERSION.SDK_INT >= 29) {
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                getLatitude_Longitude();

            }else {
                UiUtils.showCustomToastMessage("Please Trun On GPS", this, 1);
            }


        } else {
            if (CommonUtils.isLocationPermissionGranted(this)) {
                getLatitude_Longitude();
            } else {
                UiUtils.showCustomToastMessage("Please Trun On GPS", this, 1);
            }
        }

//Save Button On Click Listener
        save.setOnClickListener(v -> {
            clientname_str = clientname.getText().toString();
            location_str = location.getText().toString();
            mobileNumber_str = mobileNumber.getText().toString();
            details_str = details.getText().toString();
            if (Validations()) {

                latitude = Float.parseFloat(String.valueOf(currentLatitude));
                longitude = Float.parseFloat(String.valueOf(currentLongitude));
                boolean isInserted = palm3FoilDatabase.insertLogDetails(clientname_str, mobileNumber_str, location_str, details_str, latitude, longitude, serverStatus);
                if (isInserted) {
                    UiUtils.showCustomToastMessage("Data  Inserted successfully", this, 0);
                    finish();
                    Clear();
                } else {
                    UiUtils.showCustomToastMessage("Data  Insertion Failed", this, 1);

                }
            }
        });
    }

    //Getting Latitude and Longitude
    private void getLatitude_Longitude() {
        String latlong[] = getLatLong(this, false).split("@");
        currentLatitude = Double.parseDouble(latlong[0]);
        currentLongitude = Double.parseDouble(latlong[1]);
    }

    //Validations
    public boolean Validations() {

        if (TextUtils.isEmpty(clientname_str)) {
            UiUtils.showCustomToastMessage("Please Enter Client Name", this, 1);
            clientname.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(location_str)) {
            UiUtils.showCustomToastMessage("Please Enter you Meeting Location", this, 1);
            location.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(details_str)) {
            UiUtils.showCustomToastMessage("Please Enter Details", this, 1);
            details.requestFocus();
            return false;
        }
        return true;
    }

    //Clearing the Fields
    protected void Clear() {
        clientname.setText("");
        location.setText("");
        mobileNumber.setText("");
        details.setText("");
        clientname.requestFocus();
    }
}
