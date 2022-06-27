package com.oilpalm3f.mainapp.areacalculator;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.uihelper.ProgressBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import letsrock.areaviewlib.AreaView;
import letsrock.areaviewlib.GPSCoordinate;


//To calculate the Geo Boundaries using Location
public class FieldCalculatorActivity extends AppCompatActivity {

    private static final String LOG_TAG = FieldCalculatorActivity.class.getName();
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static List<GPSCoordinate> firstFourCoordinates = new ArrayList<>();
    public static List<GPSCoordinate> recordedBoundries = new ArrayList<>();
    public static List<GPSCoordinate> totalBoundries = new ArrayList<>();
    private AreaView measureView;
    private Button startStopButton, saveBtn, resetBtn;
    private Context c;
    private LocationManager locationManager;
    private LinkedHashMap<String, String> latLongMap = new LinkedHashMap<>();
    private Button recordBtn;
    private RecyclerView recordsList;

    private BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null && intent.getExtras() != null) {
                latLongMap.put(String.valueOf(intent.getExtras().getDouble("latitude")), String.valueOf(intent.getExtras().getDouble("longitude")));

                if (null != firstFourCoordinates && firstFourCoordinates.size() <= 4) {
                    firstFourCoordinates.add(new GPSCoordinate(intent.getExtras().getDouble("latitude"), intent.getExtras().getDouble("longitude"), 0));
                }
                totalBoundries.add(new GPSCoordinate(intent.getExtras().getDouble("latitude"), intent.getExtras().getDouble("longitude"), 0));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isLocationPermissionGranted()) {
            Log.v(LOG_TAG, "Location Permissions Not Granted");
            requestLocationPermissions();
        } else {
            initViews();
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(locationReceiver, new IntentFilter("location_receiver"));

    }

    public void initViews() {

        setContentView(R.layout.activity_field_caluculator);
        recordedBoundries.clear();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        recordBtn = (Button) findViewById(R.id.recordBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        resetBtn = (Button) findViewById(R.id.reset);
        recordsList = (RecyclerView) findViewById(R.id.gpsRecords);
        recordsList.setLayoutManager(new LinearLayoutManager(FieldCalculatorActivity.this, LinearLayoutManager.VERTICAL, false));

        recordBtn.setOnClickListener(view -> {

            if (measureView.isRunning()) {
                GPSCoordinate pointsToRecord;
                if (null != recordedBoundries && recordedBoundries.size() > 0) {
                    double distance = CommonUtils.distance(recordedBoundries.get(recordedBoundries.size() - 1).latitude,
                            recordedBoundries.get(recordedBoundries.size() - 1).longitude, AreaView.latitude, AreaView.longitude, 'M');
                    pointsToRecord = new GPSCoordinate(AreaView.latitude, AreaView.longitude, distance);
                } else {
                    pointsToRecord = new GPSCoordinate(AreaView.latitude, AreaView.longitude, 0.0);
                }

                recordedBoundries.add(pointsToRecord);
                recordsList.setAdapter(new RecordedCoordinatesAdapter(FieldCalculatorActivity.this, recordedBoundries));
            }

        });
        resetBtn.setOnClickListener(view -> {
            measureView.reset();
            measureView.invalidate();
            recordedBoundries.clear();
            recordsList.setAdapter(new RecordedCoordinatesAdapter(FieldCalculatorActivity.this, recordedBoundries));

        });

        saveBtn.setOnClickListener(view -> {
           double measuredArea = Math.round(100 * measureView.getArea()) / (double) 100;
            if (measureView.isRunning()) {
                Toast.makeText(FieldCalculatorActivity.this, "Please stop area measuring and save", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!measureView.isReadyToStart()) {
                Toast.makeText(FieldCalculatorActivity.this, "Gps signal not received", Toast.LENGTH_SHORT).show();
                return;
            }
            if (measuredArea > 0) {
                displayAreaAreaDialog();
            } else {
                Toast.makeText(FieldCalculatorActivity.this, "Area is not measured", Toast.LENGTH_SHORT).show();
            }




       //    saveLatLongData();
        });

        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            displayGpsDialog();
        }

        measureView = (AreaView) findViewById(R.id.measureView);
        measureView.setLengthUnits(AreaView.LENGTH_UNITS_KILOMETER);
        measureView.setAreaUnits(AreaView.AREA_UNITS_HECTARE);
        startStopButton = (Button) findViewById(R.id.startBtn);
        startStopButton.setOnClickListener(view -> {

            if (measureView.isReadyToStart()) {
                measureView.start();
                startStopButton.setText(c.getString(R.string.stop));
                startStopButton.postInvalidate();
            } else if (measureView.isRunning()) {
                measureView.stop();
                startStopButton.setText(c.getString(R.string.start));
                startStopButton.postInvalidate();
            } else {
                Toast.makeText(FieldCalculatorActivity.this, "Waiting for gps signal", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean isLocationPermissionGranted() {
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED
                && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationPermissions() {
        if (!isLocationPermissionGranted()) {
            String[] perms = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, perms, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    initViews();

                    if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
                        displayGpsDialog();
                    }

                } else {

                }
                break;
        }
    }

    private void displayGpsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("GPS is turned off ,Please Enable GPS").setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(settingsIntent);
                    }
                });
        builder.show();

    }

    private void displayAreaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        double measuredArea = Math.round(100 * measureView.getArea()) / (double) 100;
        builder.setMessage("Total field area is : " + measuredArea + " " + measureView.getAreaUnit()).setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                saveLatLongData();
            }
        });
        builder.show();

    }


    private void displayAreaAreaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        double measuredArea = Math.round(100 * measureView.getArea()) / (double) 100;
//        double diffPercentage = CommonUtils.getPercentage(measuredArea, ConversionLandDetailsFragment.plotEnteredArea);
        double diffPercentage = 0;
        double roundedValue = 0.0;

        try {
            DecimalFormat f = new DecimalFormat("##.000000");
            String formattedValue = f.format(diffPercentage);
            if (!TextUtils.isEmpty(formattedValue)) {
                roundedValue = Double.parseDouble(formattedValue);
            }
        } catch (Exception e) {
            roundedValue = 0;
        }


        String message = "Total field area is : " + measuredArea + " " + measureView.getAreaUnit();

        if (diffPercentage >= 60 && roundedValue != Double.POSITIVE_INFINITY && diffPercentage != Double.NEGATIVE_INFINITY) {
            message = message + "\n Variation between Plot area and Gps area is " + roundedValue + " %";
        }
        builder.setMessage(message).setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                saveLatLongData();
            }
        });
        builder.show();

    }

    public void saveLatLongData() {
        ProgressBar.showProgressBar(FieldCalculatorActivity.this, "Saving Gps data");
        try {

            if (recordedBoundries.isEmpty()) {
                recordedBoundries.addAll(totalBoundries);
            }

            double measuredArea = Math.round(100 * measureView.getArea()) / (double) 100;
            Intent intent = new Intent();
            intent.putExtra("area", measuredArea);
            setResult(RESULT_OK, intent);
            finish();

        } catch (Exception e) {
            Log.v(LOG_TAG, "@@@@ Error while saving lat longs");
        }

    }

    @Override
    public void onBackPressed() {
        totalBoundries.clear();
        recordedBoundries.clear();
        firstFourCoordinates.clear();
        Intent intent = new Intent();
        intent.putExtra("area", 0.0);
        setResult(RESULT_OK, intent);
        finish();
    }

    public class RecordedCoordinatesAdapter extends RecyclerView.Adapter<RecordedCoordinatesAdapter.MyHolder> {
        private Context mContext;
        private List<GPSCoordinate> gpsCoordinates;


        public RecordedCoordinatesAdapter(Context mContext, List<GPSCoordinate> gpsCoordinates) {
            this.mContext = mContext;
            this.gpsCoordinates = gpsCoordinates;
        }

        @Override
        public RecordedCoordinatesAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View bookingView = inflater.inflate(R.layout.records_list_item, null);
            MyHolder myHolder = new MyHolder(bookingView);
            return myHolder;
        }


        @Override
        public void onBindViewHolder(RecordedCoordinatesAdapter.MyHolder holder, int position) {
            holder.latitude.setText("" + gpsCoordinates.get(position).latitude);
            holder.longitude.setText("" + gpsCoordinates.get(position).longitude);
            if (gpsCoordinates != null && gpsCoordinates.size() > 1) {
                holder.distance.setText("" + gpsCoordinates.get(position).altitude + " Meters");

            } else {
                holder.distance.setText("0 " + "Meters");
            }
        }

        @Override
        public int getItemCount() {
            return gpsCoordinates.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder { 
            TextView latitude, longitude, distance;
            public MyHolder(View itemView) {
                super(itemView);
                latitude = (TextView) itemView.findViewById(R.id.latitude);
                longitude = (TextView) itemView.findViewById(R.id.longitude);
                distance = (TextView) itemView.findViewById(R.id.distance);
            }
        }
    }

}
