package com.oilpalm3f.mainapp.areaextension.location;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.RegistrationFlowScreen;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

//For Selecting State/District/Mandal/Village
public class LocationSelectionScreen extends AppCompatActivity {

    private static final String LOG_TAG = LocationSelectionScreen.class.getName();

    private Button submitBtn;
    private RelativeLayout parentPanel;

    private Spinner statespin, districtSpin, mandalSpin, villageSpinner;
    private ActionBar actionBar;

    private LinkedHashMap<String, Pair> stateDataMap = null, districtDataMap, mandalDataMap, villagesDataMap;
    private DataAccessHandler dataAccessHandler;
    private String villageCodeStr;
    public TextView ClusterNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Farmer Residential location");

        CommonUtils.currentActivity = LocationSelectionScreen.this;
        dataAccessHandler = new DataAccessHandler(LocationSelectionScreen.this);


        statespin = (Spinner) findViewById(R.id.statespin);
        villageSpinner = (Spinner) findViewById(R.id.villageSpin);
        ClusterNameTv = (TextView) findViewById(R.id.Cluster_Name);
        stateDataMap = dataAccessHandler.getPairData(Queries.getInstance().getStatesMasterQuery());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(LocationSelectionScreen.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(stateDataMap, "State"));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statespin.setAdapter(spinnerArrayAdapter);

        districtSpin = (Spinner) findViewById(R.id.districtSpin);
        mandalSpin = (Spinner) findViewById(R.id.mandalSpin);

        parentPanel = (RelativeLayout) findViewById(R.id.parentPanel);
        parentPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtils.isEmptySpinner(statespin)) {
                    Toast.makeText(LocationSelectionScreen.this, "Please select state", Toast.LENGTH_SHORT).show();
                } else if (CommonUtils.isEmptySpinner(districtSpin)) {
                    Toast.makeText(LocationSelectionScreen.this, "Please select district", Toast.LENGTH_SHORT).show();
                } else if (CommonUtils.isEmptySpinner(mandalSpin)) {
                    Toast.makeText(LocationSelectionScreen.this, "Please select mandal", Toast.LENGTH_SHORT).show();
                } else if (CommonUtils.isEmptySpinner(villageSpinner)) {
                    Toast.makeText(LocationSelectionScreen.this, "Please select village", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(LocationSelectionScreen.this, RegistrationFlowScreen.class));
                    finish();
                }

            }
        });
        statespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (stateDataMap != null && stateDataMap.size() > 0 && statespin.getSelectedItemPosition() != 0) {
                    CommonConstants.stateId = stateDataMap.keySet().toArray(new String[stateDataMap.size()])[i - 1];
                    Log.v(LOG_TAG, "@@@ Selected State " + CommonConstants.stateId);
                    districtDataMap = dataAccessHandler.getPairData(Queries.getInstance().getDistrictQuery(CommonConstants.stateId));
                    ArrayAdapter<String> spinnerDistrictArrayAdapter = new ArrayAdapter<>(LocationSelectionScreen.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(districtDataMap, "District"));
                    spinnerDistrictArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    districtSpin.setAdapter(spinnerDistrictArrayAdapter);
                    CommonConstants.stateName = statespin.getSelectedItem().toString();
                    Pair statePair = stateDataMap.get(CommonConstants.stateId);
                    CommonConstants.stateCode = statePair.first.toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        districtSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (districtDataMap != null && districtDataMap.size() > 0 && districtSpin.getSelectedItemPosition() != 0) {
                    CommonConstants.districtId = districtDataMap.keySet().toArray(new String[districtDataMap.size()])[i - 1];
                    Log.v(LOG_TAG, "@@@ Selected State " + CommonConstants.districtId);
                    mandalDataMap = dataAccessHandler.getPairData(Queries.getInstance().getMandalsQuery(CommonConstants.districtId));
                    ArrayAdapter<String> spinnerMandalArrayAdapter = new ArrayAdapter<>(LocationSelectionScreen.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(mandalDataMap, "Mandal"));
                    spinnerMandalArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mandalSpin.setAdapter(spinnerMandalArrayAdapter);
                    CommonConstants.districtName = districtSpin.getSelectedItem().toString();
                    Pair districtPair = districtDataMap.get(CommonConstants.districtId);
                    CommonConstants.districtCode = districtPair.first.toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mandalSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mandalDataMap != null && mandalDataMap.size() > 0 && mandalSpin.getSelectedItemPosition() != 0) {
                    CommonConstants.mandalId = mandalDataMap.keySet().toArray(new String[mandalDataMap.size()])[i - 1];
                    Log.v(LOG_TAG, "@@@ Selected State " + CommonConstants.mandalId);
                    CommonConstants.mandalName = mandalSpin.getSelectedItem().toString();

                    Pair mandalPair = mandalDataMap.get(CommonConstants.mandalId);
                    CommonConstants.mandalCode = mandalPair.first.toString();
                    CommonConstants.prevMandalPos = i;

                    villagesDataMap = dataAccessHandler.getPairData(Queries.getInstance().getVillagesQuery(CommonConstants.mandalId));
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(LocationSelectionScreen.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(villagesDataMap, "Village"));
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    villageSpinner.setAdapter(spinnerArrayAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        villageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (villagesDataMap != null && villagesDataMap.size() > 0 && villageSpinner.getSelectedItemPosition() != 0) {
                    CommonConstants.villageId = villagesDataMap.keySet().toArray(new String[villagesDataMap.size()])[i - 1];
                    villageCodeStr = villageSpinner.getSelectedItem().toString();
                    Pair villagePair = villagesDataMap.get(CommonConstants.villageId);
                    CommonConstants.villageCode = villagePair.first.toString();
//                  CommonConstants.FARMER_CODE = dataAccessHandler.getGeneratedFarmerCode(Queries.getInstance().getMaxNumberQuery(CommonConstants.villageId, CommonConstants.villageCode));
                    CommonConstants.villageName = villageSpinner.getSelectedItem().toString();
                    CommonConstants.prevVillagePos = i;
                    String ClusterName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getClusterName(CommonConstants.villageId));
                    ClusterNameTv.setText(" "+ClusterName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
