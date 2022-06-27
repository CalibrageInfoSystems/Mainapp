package com.oilpalm3f.mainapp.areaextension;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.uihelper.InteractiveScrollView;

import java.util.LinkedHashMap;

//Type of Water Details
public class WSPDetailsFragment extends Fragment {


    private Spinner sourceOfWaterSpin;
    private static final String LOG_TAG = WSPDetailsFragment.class.getName();
    private EditText sourceEdit,numberEdit,depthEdit,capacityofmotorEdit,distancefromfieldEdit,wateravailabilityofcanalEdit,waterdischargecapacityEdit,
            totalwaterdischargeEdit,totalamountofwaterdischargeEdit,noofhourspowerEdit,commentsEdit;
    private LinearLayout waternumberLL,waterdepthLL,capacityofmotorLL,distancefromfieldLL,wateravailabilityofcanalLL,waterdischargecapacityLL;
    private Spinner typeofirrigationSpin,plotprioritizationSpin,soiltypeSpin,typeofpowerspnr;
    private LinkedHashMap<String, String> sourceOfWaterMap;
    private LinkedHashMap<String, String> typeOfIrrigationMap;
    private LinkedHashMap<String, String> plotPrioritizationMap;
    private LinkedHashMap<String, String> soilTypeMap;
    private LinkedHashMap<String, String> powerAvailabiltyMap;
    private LinkedHashMap<String, String> typeOfPowerMap;
    private InteractiveScrollView scrollView;
    public ImageView scrollBottomIndicator;
    private View rootView;
    private String sourceofwater,waterNumber,depth,capacityOfMotor,distanceFromField,waterAvailabilityOfCanal,waterDischargeCapacity,
            totalWaterDischarge,totalAmountOfWaterDischarge,noOgHoursPower,Comments,irrigationTypeCode,plotPrioitizationCode,soiltypeCode,
            powerAvailabilityCode,typeOfPowerCode,sourceOfWaterCode;
    private ActionBar actionBar;
    private DataAccessHandler dataAccessHandler;
    private Button btn_WPSSave;
    private LinearLayout parentLayout;
    public WSPDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_wspdetails, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(activity.getResources().getString(R.string.water_soil_power_details));
        initializeUI();
        return rootView;
    }

    private void initializeUI()
    {
        dataAccessHandler = new DataAccessHandler(getActivity());

        btn_WPSSave = (Button) rootView.findViewById(R.id.WPSSaveBtn);

        scrollView = (InteractiveScrollView)  rootView.findViewById(R.id.scrollView);
        scrollBottomIndicator = (ImageView) rootView.findViewById(R.id.bottomScroll);
        parentLayout = (LinearLayout)rootView.findViewById(R.id.parent_layout);
        numberEdit = (EditText)  rootView.findViewById(R.id.numberEdit);
        sourceEdit = (EditText)  rootView.findViewById(R.id.sourceEdit);
        depthEdit = (EditText)  rootView.findViewById(R.id.depthEdit);
        capacityofmotorEdit = (EditText)  rootView.findViewById(R.id.capacityofmotorEdit);
        distancefromfieldEdit = (EditText)  rootView.findViewById(R.id.distancefromfieldEdit);
        wateravailabilityofcanalEdit = (EditText)  rootView.findViewById(R.id.wateravailabilityofcanalEdit);
        waterdischargecapacityEdit = (EditText)  rootView.findViewById(R.id.waterdischargecapacityEdit);
        totalamountofwaterdischargeEdit = (EditText)  rootView.findViewById(R.id.totalamountofwaterdischargeEdit);
        totalwaterdischargeEdit = (EditText)  rootView.findViewById(R.id.totalwaterdischargeEdit);
        commentsEdit = (EditText)  rootView.findViewById(R.id.commentsEdit);
        noofhourspowerEdit = (EditText)  rootView.findViewById(R.id.noofhourspowerEdit);

        sourceOfWaterSpin = (Spinner)  rootView.findViewById(R.id.sourceOfWaterSpin);
        typeofpowerspnr = (Spinner)  rootView.findViewById(R.id.typeofpowerspnr);
        soiltypeSpin = (Spinner)  rootView.findViewById(R.id.soiltype);
        plotprioritizationSpin = (Spinner)  rootView.findViewById(R.id.plotprioritizationSpin);
        typeofirrigationSpin = (Spinner)  rootView.findViewById(R.id.typeofirrigationSpin);

        waterdischargecapacityLL = (LinearLayout)  rootView.findViewById(R.id.waterdischargecapacityLL);
        wateravailabilityofcanalLL = (LinearLayout)  rootView.findViewById(R.id.wateravailabilityofcanalLL);
        distancefromfieldLL = (LinearLayout)  rootView.findViewById(R.id.distancefromfieldLL);
        capacityofmotorLL = (LinearLayout)  rootView.findViewById(R.id.capacityofmotorLL);
        waterdepthLL = (LinearLayout)  rootView.findViewById(R.id.waterdepthLL);
        waternumberLL = (LinearLayout)  rootView.findViewById(R.id.waternumberLL);

        scrollBottomIndicator.setVisibility(View.GONE);
        scrollBottomIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.post(new Runnable() {

                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
        scrollView.setOnBottomReachedListener(new InteractiveScrollView.OnBottomReachedListener() {
            @Override
            public void onBottomReached() {
                scrollBottomIndicator.setVisibility(View.GONE);
            }
        });

        scrollView.setOnTopReachedListener(new InteractiveScrollView.OnTopReachedListener() {
            @Override
            public void onTopReached() {
            }
        });

        scrollView.setOnScrollingListener(new InteractiveScrollView.OnScrollingListener() {
            @Override
            public void onScrolling() {
                android.util.Log.d(LOG_TAG, "onScrolling: ");
                scrollBottomIndicator.setVisibility(View.VISIBLE);
            }
        });

        sourceOfWaterMap = dataAccessHandler.getGenericData(Queries.getInstance().getCastesQuery());
        ArrayAdapter<String> sourceOfWaterSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.fromMap(sourceOfWaterMap, "Category"));
        sourceOfWaterSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceOfWaterSpin.setAdapter(sourceOfWaterSpinnerArrayAdapter);

        plotPrioritizationMap = dataAccessHandler.getGenericData(Queries.getInstance().getplotPrioritizationQuery());
        ArrayAdapter<String> plotPrioSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.fromMap(plotPrioritizationMap, "Category"));
        plotPrioSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plotprioritizationSpin.setAdapter(plotPrioSpinnerArrayAdapter);

        typeOfIrrigationMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeofIrrigationQuery());
        ArrayAdapter<String> typeOfIrrigationSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.fromMap(typeOfIrrigationMap, "Category"));
        typeOfIrrigationSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeofirrigationSpin.setAdapter(typeOfIrrigationSpinnerArrayAdapter);

        soilTypeMap = dataAccessHandler.getGenericData(Queries.getInstance().getSoilTypeQuery());
        ArrayAdapter<String> soilTypeSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.fromMap(soilTypeMap, "Category"));
        soilTypeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soiltypeSpin.setAdapter(soilTypeSpinnerArrayAdapter);

        typeOfPowerMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeOfPowerQuery());
        ArrayAdapter<String> typrOfPowerSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.fromMap(typeOfPowerMap, "Category"));
        typrOfPowerSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeofpowerspnr.setAdapter(typrOfPowerSpinnerArrayAdapter);



        sourceOfWaterSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sourceOfWaterMap != null && sourceOfWaterMap.size() > 0 && sourceOfWaterSpin.getSelectedItemPosition() != 0)
                    sourceOfWaterCode = sourceOfWaterMap.keySet().toArray(new String[sourceOfWaterMap.size()])[i - 1];
                  Log.v(LOG_TAG, "@@@ Selected State " + sourceOfWaterCode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(sourceOfWaterSpin.getSelectedItem().toString().equalsIgnoreCase("Borewell")||sourceOfWaterSpin.getSelectedItem().toString().equalsIgnoreCase("Openwell"))
        {
            waternumberLL.setVisibility(View.VISIBLE);
            waterdepthLL.setVisibility(View.VISIBLE);
            capacityofmotorLL.setVisibility(View.VISIBLE);
        }
        else if(sourceOfWaterSpin.getSelectedItem().toString().equalsIgnoreCase("canal"))
        {
            distancefromfieldLL.setVisibility(View.VISIBLE);
            wateravailabilityofcanalLL.setVisibility(View.VISIBLE);
            waterdischargecapacityLL.setVisibility(View.VISIBLE);
        }

        plotprioritizationSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (plotPrioritizationMap != null && plotPrioritizationMap.size() > 0 && plotprioritizationSpin.getSelectedItemPosition() != 0)
                    plotPrioitizationCode = plotPrioritizationMap.keySet().toArray(new String[plotPrioritizationMap.size()])[i - 1];
                Log.v(LOG_TAG, "@@@ Selected State " + plotPrioitizationCode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        typeofirrigationSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (typeOfIrrigationMap != null && typeOfIrrigationMap.size() > 0 && typeofirrigationSpin.getSelectedItemPosition() != 0)
                    irrigationTypeCode = typeOfIrrigationMap.keySet().toArray(new String[typeOfIrrigationMap.size()])[i - 1];
                Log.v(LOG_TAG, "@@@ Selected State " + irrigationTypeCode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        soiltypeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (soilTypeMap != null && soilTypeMap.size() > 0 && soiltypeSpin.getSelectedItemPosition() != 0)
                    soiltypeCode = soilTypeMap.keySet().toArray(new String[sourceOfWaterMap.size()])[i - 1];
                Log.v(LOG_TAG, "@@@ Selected State " + soiltypeCode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        typeofpowerspnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (typeOfPowerMap != null && typeOfPowerMap.size() > 0 && typeofpowerspnr.getSelectedItemPosition() != 0)
                    typeOfPowerCode = typeOfPowerMap.keySet().toArray(new String[sourceOfWaterMap.size()])[i - 1];
                Log.v(LOG_TAG, "@@@ Selected State " + typeOfPowerCode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_WPSSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate())
                {

                }
            }
        });


    }
    public boolean validate() {

        waterNumber = numberEdit.getText().toString();
        depth = depthEdit.getText().toString();
        capacityOfMotor = capacityofmotorEdit.getText().toString();
        distanceFromField = distancefromfieldEdit.getText().toString();
        waterAvailabilityOfCanal = wateravailabilityofcanalEdit.getText().toString();
        waterDischargeCapacity = waterdischargecapacityEdit.getText().toString();
        totalWaterDischarge = totalamountofwaterdischargeEdit.getText().toString();
        totalAmountOfWaterDischarge = totalamountofwaterdischargeEdit.getText().toString();
        noOgHoursPower = noofhourspowerEdit.getText().toString();
        Comments = commentsEdit.getText().toString();




        if(sourceOfWaterSpin.getSelectedItem().toString().equalsIgnoreCase("Borewell")||sourceOfWaterSpin.getSelectedItem().toString().equalsIgnoreCase("Openwell"))
        {
            if (TextUtils.isEmpty(waterNumber)) {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_waternumber), Toast.LENGTH_SHORT).show();
                numberEdit.requestFocus();
                return false;
            }

            if (TextUtils.isEmpty(depth)) {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_waterdepth), Toast.LENGTH_SHORT).show();
                depthEdit.requestFocus();
                return false;
            }

            if (TextUtils.isEmpty(capacityOfMotor)) {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_motorcapacity), Toast.LENGTH_SHORT).show();
                capacityofmotorEdit.requestFocus();
                return false;
            }

        }

        if(sourceOfWaterSpin.getSelectedItem().toString().equalsIgnoreCase("canal"))
        {
            if (TextUtils.isEmpty(distanceFromField)) {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_fileddistance), Toast.LENGTH_SHORT).show();
                distancefromfieldEdit.requestFocus();
                return false;
            }
            if (TextUtils.isEmpty(waterDischargeCapacity)) {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_waterdischargecapacity), Toast.LENGTH_SHORT).show();
                waterdischargecapacityEdit.requestFocus();
                return false;
            }
        }
        if (TextUtils.isEmpty(totalWaterDischarge)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_totalwaterdischarge), Toast.LENGTH_SHORT).show();
            totalwaterdischargeEdit.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(totalAmountOfWaterDischarge)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_totalamountofwaterdischarge), Toast.LENGTH_SHORT).show();
            totalamountofwaterdischargeEdit.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(soiltypeCode)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_soiltype), Toast.LENGTH_SHORT).show();
            soiltypeSpin.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(typeOfPowerCode)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_typeofpower), Toast.LENGTH_SHORT).show();
            typeofpowerspnr.requestFocus();
            return false;
        }
        return true;
    }
}
