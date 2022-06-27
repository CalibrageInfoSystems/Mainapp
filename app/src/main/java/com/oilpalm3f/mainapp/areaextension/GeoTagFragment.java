package com.oilpalm3f.mainapp.areaextension;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areacalculator.LatLongListener;
import com.oilpalm3f.mainapp.areacalculator.LocationProvider;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.GeoBoundaries;
import com.oilpalm3f.mainapp.ui.BaseFragment;
import com.oilpalm3f.mainapp.utils.UiUtils;


//To get plot Geo Tag
@SuppressLint("SetTextI18n")
public class GeoTagFragment extends Fragment {

    private static final String LOG_TAG = GeoTagFragment.class.getName();
    private Toolbar toolbar;
    private View rootView;
    private ActionBar actionBar;
    private TextView lattitudeTxt, titleHeader;
    private TextView longitudeTxt;
    private ImageView geotag;
    private Button farmerSaveBtn, retakeGeoTagBtn;
    private TextView addressTxt;
    private double latitude, longitude;
    public UpdateUiListener updateUiListener;
    public GeoBoundaries geoBoundaries = null;
    @SuppressLint("StaticFieldLeak")
    private static LocationProvider mLocationProvider;
    private double currentLatitude, currentLongitude;
    private ProgressBar progressBar;
    LocationManager lm;


    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }


    public GeoTagFragment() {

    }

    private static String latLong = "";

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        rootView = inflater.inflate(R.layout.fragment_geo_tag, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(getActivity().getResources().getString(R.string.create_geo_tag));
        lattitudeTxt = (TextView) rootView.findViewById(R.id.lattitudeTxt);
        longitudeTxt = (TextView) rootView.findViewById(R.id.longitudeTxt);
        geotag = (ImageView) rootView.findViewById(R.id.geotag);
        farmerSaveBtn = (Button) rootView.findViewById(R.id.createGeoTagBtn);
        retakeGeoTagBtn = (Button) rootView.findViewById(R.id.retakeGeoTagBtn);
        addressTxt = (TextView) rootView.findViewById(R.id.addressTxt);
        titleHeader = (TextView) rootView.findViewById(R.id.titleHeader);
        titleHeader.setVisibility(View.VISIBLE);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);

        farmerSaveBtn.setOnClickListener(v -> {
            if (farmerSaveBtn.getText().toString().equalsIgnoreCase("Save") && currentLatitude != 0 && currentLongitude != 0) {
                setGeoTagData();
            } else {

                lattitudeTxt.setText("" + CommonConstants.Current_Latitude);
                longitudeTxt.setText("" + CommonConstants.Current_Longitude);
                if (android.os.Build.VERSION.SDK_INT >= 29) {
                    lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        progressBar.setVisibility(View.VISIBLE);
                        getLocationDetails();


                    }
                } else {
                    if (CommonUtils.isLocationPermissionGranted(getActivity())) {
                        progressBar.setVisibility(View.VISIBLE);
                        getLocationDetails();

                    }
                }
            }
        });

        retakeGeoTagBtn.setOnClickListener(v -> {

            if (android.os.Build.VERSION.SDK_INT >= 29) {
                lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getLocationDetails();


                }
            } else {
                if (CommonUtils.isLocationPermissionGranted(getActivity())) {
                    getLocationDetails();

                }
            }
        });

        geoBoundaries = (GeoBoundaries) DataManager.getInstance().getDataFromManager(DataManager.PLOT_GEO_TAG);
        if (geoBoundaries != null) {

            currentLatitude = geoBoundaries.getLatitude();
            currentLongitude = geoBoundaries.getLongitude();
            lattitudeTxt.setText("" + currentLatitude);
            longitudeTxt.setText("" + currentLongitude);
            addressTxt.setText(CommonConstants.GEO_TAG_ADDRESS);
            farmerSaveBtn.setText("Save");
            retakeGeoTagBtn.setVisibility(View.VISIBLE);

        }

        return rootView;
    }


    private void getLocationDetails() {
        String latlong[] = getLatLong(getActivity(), false).split("@");

        if (!TextUtils.isEmpty(latlong[0]) && !TextUtils.isEmpty(latlong[1])) {
            currentLatitude = Double.parseDouble(latlong[0]);
            currentLongitude = Double.parseDouble(latlong[1]);
            lattitudeTxt.setText("" + currentLatitude);
            longitudeTxt.setText("" + currentLongitude);

            farmerSaveBtn.setText("Save");
            progressBar.setVisibility(View.GONE);
            titleHeader.setVisibility(View.GONE);
            retakeGeoTagBtn.setVisibility(View.VISIBLE);
            CommonUtils.getAddressByLocation(getActivity(),
                    currentLatitude, currentLongitude, false, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, final String zipCode, final String geoCountry) {
                            Log.d(LOG_TAG, "### in getAddressByLocation from :" + LOG_TAG);

                            if (success) {
                                ApplicationThread.uiPost(LOG_TAG, "location details", () -> {
                                    progressBar.setVisibility(View.GONE);
                                    addressTxt.setText(geoCountry);
                                    CommonConstants.GEO_TAG_ADDRESS = geoCountry;

                                });
                            }
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Turn On GPS", Toast.LENGTH_SHORT).show();
        }


    }


    public void setGeoTagData() {
        if (currentLatitude != 0 && currentLongitude != 0) {
            geoBoundaries = new GeoBoundaries();
            geoBoundaries.setLatitude(currentLatitude);
            geoBoundaries.setLongitude(currentLongitude);
            if (CommonUtils.isFromCropMaintenance()) {
                geoBoundaries.setGeocategorytypeid(256);
            } else {
                geoBoundaries.setGeocategorytypeid(207);
            }

            DataManager.getInstance().addData(DataManager.PLOT_GEO_TAG, geoBoundaries);
            updateUiListener.updateUserInterface(0);
        }
        getFragmentManager().popBackStack();
    }

}
