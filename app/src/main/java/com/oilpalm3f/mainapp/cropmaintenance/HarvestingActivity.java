package com.oilpalm3f.mainapp.cropmaintenance;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.common.CustomAutoAdapter;
import com.oilpalm3f.mainapp.common.InputFilterMinMax;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Palm3FoilDatabase;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.dbmodels.BasicFarmerDetails;
import com.oilpalm3f.mainapp.dbmodels.BasicHarvestorDetails;
import com.oilpalm3f.mainapp.dbmodels.HarvestorDataModel;
import com.oilpalm3f.mainapp.dbmodels.PlotLandlord;
import com.oilpalm3f.mainapp.farmersearch.SearchFarmerScreen;
import com.oilpalm3f.mainapp.ui.OilPalmBaseActivity;
import com.oilpalm3f.mainapp.utils.UiUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

//Used to enter harvesting details with ffb quality details
public class HarvestingActivity extends OilPalmBaseActivity {
    private static final String[] COUNTRIES = new String[]{"Belgium",
            "France", "France_", "Italy", "Germany", "Spain", "abcf", "FFa", "FFb", "bFF", "aFF"};

    private static final String LOG_TAG = HarvestingActivity.class.getSimpleName();
    String searchKey = "";
    int offset;
    public static final int LIMIT = 30;
    private boolean isSearch = false;
    private boolean isLoading = false;
    private boolean hasMoreItems = true;
    private List<BasicHarvestorDetails> mHarvestorsList = new ArrayList<>();

    RadioButton existingharvestor, newharvestor;

    Spinner harvesterType, ownpoleavailable, ffbsupplyto, farmeravailableinfield, detailsinformedtofarmer,  isloosefruitavailable_spinner;
    EditText harvestercode, harvestername, mobilenumber, village, mandal, harvestingprice, harvestinginterval, totalexpectedquntity, unripen, underripe, ripen, overripe, diseased,
            emptybunches, longstalk, mediumstalk, shortstalk, optimum,loosefruitweight;
    TextView lastcollectiondate, harvestingdate, nextharvestingdate;
    Button submit;
    private EditText mEtSearch;
    private ImageView mIVClear;
    private ProgressBar progress;
    private LinkedHashMap<String, String> harvestingTypesDataMap;
    private DataAccessHandler dataAccessHandler;
    LinearLayout detailsinformedtoFarmerLL,harvestingcodell,autoselectharvestorLL,loosefruitweightLL;
    LinkedHashMap collecectionCenterList;
    private Palm3FoilDatabase palm3FoilDatabase;
    String SelectedccCode;
    RadioGroup radioGroup;

    private List<HarvestorDataModel> harvestorDataModelList = new ArrayList<>();
    private AutoCompleteTextView autoCompleteHarvestor;
    boolean isExisitng = true;
    String lastharvestdate, datetimevaluereq;
    String harvestingtypeCode, harvestertypename;

    @Override
    public void Initialize() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View parentView = inflater.inflate(R.layout.activity_harvesting, null);
        baseLayout.addView(parentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        android.util.Log.v("@@@KK", CommonConstants.PLOT_CODE);
        dataAccessHandler = new DataAccessHandler(HarvestingActivity.this);
        palm3FoilDatabase = new Palm3FoilDatabase(this);
        initView();
        setViews();
        setTile("Harvesting Screen");

    }

    private void initView() {

        harvesterType = findViewById(R.id.harvestingtype_spinner);
        ownpoleavailable = findViewById(R.id.ownpoleavailable_spinner);
        ffbsupplyto = findViewById(R.id.ffbsupply_spinner);
        farmeravailableinfield = findViewById(R.id.farmeravailableinField_spinner);
        detailsinformedtofarmer = findViewById(R.id.detailsinformedtoFarmer_spinner);
        isloosefruitavailable_spinner = findViewById(R.id.isloosefruitavailable_spinner);

        harvestercode = findViewById(R.id.harvester_code);
        harvestername = findViewById(R.id.harvester_name);
        mobilenumber = findViewById(R.id.harvestermobileNumber);
        village = findViewById(R.id.harvestervillage);
        mandal = findViewById(R.id.harvestermandal);
        harvestingprice = findViewById(R.id.harvestingPrice);
        harvestinginterval = findViewById(R.id.harvestingInterval);
        totalexpectedquntity = findViewById(R.id.totalexpectedquantity);
        unripen = findViewById(R.id.unripen);
        underripe = findViewById(R.id.underripe);
        ripen = findViewById(R.id.ripen);
        overripe = findViewById(R.id.overipe);
        diseased = findViewById(R.id.diseased);
        emptybunches = findViewById(R.id.emptybunch);
        longstalk = findViewById(R.id.longstake);
        mediumstalk = findViewById(R.id.mediumstake);
        shortstalk = findViewById(R.id.shortstake);
        optimum = findViewById(R.id.optimum);
        loosefruitweight = findViewById(R.id.loosefruitweight);

        lastcollectiondate = findViewById(R.id.lastcollectiondate);
        harvestingdate = findViewById(R.id.harvestingDate);
        nextharvestingdate = findViewById(R.id.newharvestingDate);

        submit = findViewById(R.id.harvestingsubmit);

        detailsinformedtoFarmerLL = findViewById(R.id.detailsinformedtoFarmerLL);
        autoselectharvestorLL = findViewById(R.id.autoselectharvestorLL);
        harvestingcodell = findViewById(R.id.harvestingcodell);
        loosefruitweightLL = findViewById(R.id.loosefruitweightLL);

        mEtSearch = (EditText) findViewById(R.id.et_search);
        mIVClear = (ImageView) findViewById(R.id.iv_clear);
        progress = (ProgressBar) findViewById(R.id.progress);

        autoCompleteHarvestor = findViewById(R.id.autoCompleteHarvestor);

        existingharvestor = findViewById(R.id.existingharvestor);
        newharvestor = findViewById(R.id.newharvestor);
        radioGroup = findViewById(R.id.radioGroup);


    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!TextUtils.isEmpty(harvestinginterval.getText().toString())){
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Calendar c = Calendar.getInstance();
                c.setTime(new Date()); // Using today's date
                c.add(Calendar.DATE, Integer.parseInt(harvestinginterval.getText().toString())); // Adding 5 days
                String output = sdf.format(c.getTime());
                Log.d("Output", output + "");
                nextharvestingdate.setText(output + "");
            }

        }

        @Override
        public void afterTextChanged(final Editable s) {



        }
    };


    private void setViews() {

        harvestercode.setFocusable(false);
        harvestername.setFocusable(false);
        mobilenumber.setFocusable(false);
        village.setFocusable(false);
        mandal.setFocusable(false);

        lastharvestdate = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().lastHarvestedDate(CommonConstants.PLOT_CODE));

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        try {
            if (lastharvestdate != null) {
                Date oneWayTripDate = input.parse(lastharvestdate);
                datetimevaluereq = output.format(oneWayTripDate);
            }

            //datetimevalute.setText(output.format(oneWayTripDate));

         //   android.util.Log.e("===============", "======currentData======" + output.format(oneWayTripDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "Last Harvested Date is "+ datetimevaluereq);

        if (lastharvestdate != null){

            lastcollectiondate.setText(datetimevaluereq);
        }else{

            lastcollectiondate.setEnabled(false);
        }

//        existingharvestor.setChecked(true);
//        newharvestor.setChecked(false);
//
//        if (newharvestor.isChecked() == true){
//            existingharvestor.setChecked(false);
//        }else{
//            existingharvestor.setChecked(true);
//        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);

//                Toast.makeText(HarvestingActivity.this,
//                        radioButton.getText(), Toast.LENGTH_SHORT).show();

                if (radioButton.getText().toString().equalsIgnoreCase("New")) {
                    isExisitng = false;
                    autoselectharvestorLL.setVisibility(View.GONE);
                    harvestercode.setFocusableInTouchMode(true);
                    harvestername.setFocusableInTouchMode(true);
                    mobilenumber.setFocusableInTouchMode(true);
                    village.setFocusableInTouchMode(true);
                    mandal.setFocusableInTouchMode(true);
                    harvestingcodell.setVisibility(View.GONE);

                    // Clear Data
                    autoCompleteHarvestor.setText("");
                    harvestercode.setText("");
                    harvestername.setText("");
                    mobilenumber.setText("");
                    village.setText("");
                    mandal.setText("");
                } else {
                    isExisitng = true;
                    autoselectharvestorLL.setVisibility(View.VISIBLE);
                    harvestercode.setFocusable(false);
                    harvestingcodell.setVisibility(View.VISIBLE);
                    harvestername.setFocusable(false);
                    mobilenumber.setFocusable(false);
                    village.setFocusable(false);
                    mandal.setFocusable(false);

                    harvestercode.setText("");
                    harvestername.setText("");
                    mobilenumber.setText("");
                    village.setText("");
                    mandal.setText("");

                }

            }
        });
        harvestorDataModelList = dataAccessHandler.getHarvestorData(Queries.getInstance().getActiveHarvestors());
        for (HarvestorDataModel har : harvestorDataModelList
        ) {
            Log.d("Mahesh", "=====> Harvestor Code :" + har.getCode());
        }

        //mEtSearch.addTextChangedListener(mTextWatcher);
        collecectionCenterList = dataAccessHandler.getLookUpData11(Queries.getInstance().getCollectionCenterbyuser(CommonConstants.USER_ID));
        ffbsupplyto.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(this, "CC Name", collecectionCenterList));
        ffbsupplyto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (ffbsupplyto.getSelectedItemPosition() != 0) {

                    SelectedccCode = collecectionCenterList.keySet().toArray()[position - 1].toString();
//                   Log.d(HarvestingActivity.class.getSimpleName(), "====> Analysis ==> Selected CC :"+ccCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        String[] isloosefruitavailableArray = getResources().getStringArray(R.array.yesOrNo_values);
        List<String> isloosefruitavailableList = Arrays.asList(isloosefruitavailableArray);
        ArrayAdapter<String> isloosefruitavailableAdapter = new ArrayAdapter<>(HarvestingActivity.this, android.R.layout.simple_spinner_item, isloosefruitavailableList);
        isloosefruitavailableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isloosefruitavailable_spinner.setAdapter(isloosefruitavailableAdapter);

        String[] ownpoleavailableArray = getResources().getStringArray(R.array.yesOrNo_values);
        List<String> ownpoleavailableList = Arrays.asList(ownpoleavailableArray);
        ArrayAdapter<String> spinnerownpoleavailablAdapter = new ArrayAdapter<>(HarvestingActivity.this, android.R.layout.simple_spinner_item, ownpoleavailableList);
        spinnerownpoleavailablAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownpoleavailable.setAdapter(spinnerownpoleavailablAdapter);

        String[] farmeravailableinfieldArray = getResources().getStringArray(R.array.yesOrNo_values);
        List<String> farmeravailableinfieldList = Arrays.asList(farmeravailableinfieldArray);
        ArrayAdapter<String> spinnerfarmeravailableinfieldAdapter = new ArrayAdapter<>(HarvestingActivity.this, android.R.layout.simple_spinner_item, farmeravailableinfieldList);
        spinnerfarmeravailableinfieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        farmeravailableinfield.setAdapter(spinnerfarmeravailableinfieldAdapter);

        String[] detailsinformedtofarmerArray = getResources().getStringArray(R.array.yesOrNo_values);
        List<String> detailsinformedtofarmerArrayList = Arrays.asList(detailsinformedtofarmerArray);
        ArrayAdapter<String> spinnerdetailsinformedtofarmerArrayListAdapter = new ArrayAdapter<>(HarvestingActivity.this, android.R.layout.simple_spinner_item, detailsinformedtofarmerArrayList);
        spinnerdetailsinformedtofarmerArrayListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detailsinformedtofarmer.setAdapter(spinnerdetailsinformedtofarmerArrayListAdapter);

        harvestingTypesDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getharvestingtypesQuery());
        harvesterType.setAdapter(UiUtils.createAdapter(HarvestingActivity.this, harvestingTypesDataMap, "Harvester Type"));
        harvesterType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (harvestingTypesDataMap != null && harvestingTypesDataMap.size() > 0 && harvesterType.getSelectedItemPosition() != 0) {

                    harvestingtypeCode = harvestingTypesDataMap.keySet().toArray(new String[harvestingTypesDataMap.size()])[i - 1];
                    harvestertypename = harvesterType.getSelectedItem().toString();

                    android.util.Log.v(LOG_TAG, "@@@ harvestor code " + harvestingtypeCode + " harvestor name " + harvestertypename);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        farmeravailableinfield.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (farmeravailableinfield.getSelectedItemPosition() == 2) {

                    detailsinformedtoFarmerLL.setVisibility(View.VISIBLE);
                } else {
                    detailsinformedtoFarmerLL.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        isloosefruitavailable_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (isloosefruitavailable_spinner.getSelectedItemPosition() == 1) {

                    loosefruitweightLL.setVisibility(View.VISIBLE);
                } else {
                    loosefruitweightLL.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        harvestingdate.setText(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_2));
        harvestinginterval.addTextChangedListener(mTextWatcher);
        unripen.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        underripe.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        ripen.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        overripe.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        diseased.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        emptybunches.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        longstalk.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        mediumstalk.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        shortstalk.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        optimum.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});


        CustomAutoAdapter adapter = new CustomAutoAdapter(this,
                R.layout.autocompleteitem, harvestorDataModelList);
        autoCompleteHarvestor.setAdapter(adapter);
        autoCompleteHarvestor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                autoCompleteHarvestor.setText( adapter.getItem(i).getCode());
                harvestercode.setText( adapter.getItem(i).getCode());
                harvestername.setText( adapter.getItem(i).getName());
                mobilenumber.setText( adapter.getItem(i).getMobileNumber());
                village.setText( adapter.getItem(i).getVillage());
                mandal.setText( adapter.getItem(i).getMandal());

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(HarvestingActivity.class.getSimpleName(), "===> analysis   ==> Harvestertype :" + harvesterType.getSelectedItem());
                Log.d(HarvestingActivity.class.getSimpleName(), "===> analysis   ==> Harvestertype Id :" + harvesterType.getSelectedItemId());

                if (validate()) {
                    String HarvestorVisitCode = dataAccessHandler.getGenerateHarvestingVisitiCode(Queries.getInstance().getMaxNumberQueryForHarvesting());
                    Log.d(HarvestingActivity.class.getSimpleName(), "==> analysis ==> HarvestorVisitCode  :" + HarvestorVisitCode);

                    Log.d(LOG_TAG, "lastdigit is"+ HarvestorVisitCode.substring(HarvestorVisitCode.lastIndexOf("-") + 1));

                    List<LinkedHashMap> histoty = new ArrayList<>();
                    LinkedHashMap map = new LinkedHashMap();
                    map.put("Code", HarvestorVisitCode);
                    map.put("Name", HarvestorVisitCode.substring(HarvestorVisitCode.lastIndexOf("-") + 1));
                    map.put("PlotCode", CommonConstants.PLOT_CODE);
                    map.put("CreatedByUserId", CommonConstants.USER_ID);
                    map.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    map.put("ServerUpdatedStatus", 0);

                    histoty.add(map);


                    dataAccessHandler.saveData(DatabaseKeys.TABLE_HarvestorVisitHistory, histoty, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {


                            if (success) {

                                List<LinkedHashMap> details = new ArrayList<>();
                                LinkedHashMap map = new LinkedHashMap();

                                map.put("HarvestorVisitCode", HarvestorVisitCode);
                                map.put("HarvestorTypeId", Integer.parseInt(harvestingtypeCode));

                                if (isExisitng == true){

                                    map.put("HarvestorCode", harvestercode.getText().toString());

                                }

                                int haspole = 0;

                                if (ownpoleavailable.getSelectedItemPosition() == 1){

                                     haspole = 1;
                                }else if (ownpoleavailable.getSelectedItemPosition() == 2){
                                     haspole = 0;
                                }

//                      map.put("HarvestorId",HarvestorId);
                                map.put("HasPole", haspole);
                                map.put("TonnageCost", Double.parseDouble(harvestingprice.getText().toString()));
                                map.put("Interval", Integer.parseInt(harvestinginterval.getText().toString()));
                                map.put("Quantity", Double.parseDouble(totalexpectedquntity.getText().toString()));
                                map.put("CCCode", SelectedccCode);
                                map.put("UnRipen", Double.parseDouble(unripen.getText().toString()));
                                map.put("UnderRipe", Double.parseDouble(underripe.getText().toString()));
                                map.put("Ripen", Double.parseDouble(ripen.getText().toString()));
                                map.put("OverRipe", Double.parseDouble(overripe.getText().toString()));
                                map.put("Diseased", Double.parseDouble(diseased.getText().toString()));
                                map.put("EmptyBunches", Double.parseDouble(emptybunches.getText().toString()));
                                map.put("FFBQualityLong", Double.parseDouble(longstalk.getText().toString()));
                                map.put("FFBQualityMedium", Double.parseDouble(mediumstalk.getText().toString()));
                                map.put("FFBQualityShort", Double.parseDouble(shortstalk.getText().toString()));
                                map.put("FFBQualityOptimum", Double.parseDouble(optimum.getText().toString()));

                                int farmeravailable = 0;

                                if (farmeravailableinfield.getSelectedItemPosition() == 1){

                                    farmeravailable = 1;
                                }else if (farmeravailableinfield.getSelectedItemPosition() == 2){
                                    farmeravailable = 0;
                                }

                                map.put("FarmerAvailable", farmeravailable);

                                if (isExisitng == false){

                                    map.put("Name", harvestername.getText().toString());
                                    map.put("MobileNumber", mobilenumber.getText().toString());
                                    map.put("Village", village.getText().toString());
                                    map.put("Mandal", mandal.getText().toString());
                                }


                                map.put("CreatedByUserId", CommonConstants.USER_ID);
                                map.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                                map.put("ServerUpdatedStatus", 0);

                                int isfruitavailable = 0;

                                if (isloosefruitavailable_spinner.getSelectedItemPosition() == 1){

                                    isfruitavailable = 1;
                                }else if (isloosefruitavailable_spinner.getSelectedItemPosition() == 2){
                                    isfruitavailable = 0;
                                }

                                map.put("LooseFruit", isfruitavailable);

                                if (isloosefruitavailable_spinner.getSelectedItemPosition() == 1) {

                                    map.put("LooseFruitWeight", Integer.parseInt(loosefruitweight.getText().toString()));
                                }
                                //map.put("DetailesInformed",0);

                                if (farmeravailableinfield.getSelectedItemPosition() == 2) {

                                    if (detailsinformedtofarmer.getSelectedItemPosition() == 1){

                                        map.put("DetailesInformed",1);
                                    }else if (detailsinformedtofarmer.getSelectedItemPosition() == 2){
                                        map.put("DetailesInformed",0);
                                    }

                                }

                                details.add(map);

                                dataAccessHandler.saveData(DatabaseKeys.TABLE_HarvestorVisitDetails, details, new ApplicationThread.OnComplete<String>() {
                                    @Override
                                    public void execute(boolean success, String result, String msg) {

                                        if (success) {
                                            Log.d(HarvestingActivity.class.getSimpleName(), "==>  Analysis ==> TABLE_HarvestorVisitDetails INSERT COMPLETED");
                                            Toast.makeText(HarvestingActivity.this, "Submit Successfully", Toast.LENGTH_SHORT).show();
                                            finish();

                                        } else {
                                            Toast.makeText(HarvestingActivity.this, "Submit Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            } else {
                                Log.d(HarvestingActivity.class.getSimpleName(), "==>  Analysis ==> TABLE_HarvestorVisitHistory INSERT Failed");
                            }


                        }
                    });


//                    Toast.makeText(HarvestingActivity.this, "Submit Success", Toast.LENGTH_SHORT).show();
//                    Log.d("TotalCount", Integer.parseInt(unripen.getText().toString()) + Integer.parseInt(underripe.getText().toString()) + Integer.parseInt(ripen.getText().toString()) + Integer.parseInt(overripe.getText().toString()) + Integer.parseInt(diseased.getText().toString()) +Integer.parseInt(emptybunches.getText().toString()) + "");
                }
            }
        });

    }

    public void doSearch(String searchQuery) {
        Log.d("DoSearchQuery", "is :" + searchQuery);
        offset = 0;
        hasMoreItems = true;
        if (searchQuery != null & !TextUtils.isEmpty(searchQuery) & searchQuery.length() > 0) {

            offset = 0;
            isSearch = true;
            searchKey = searchQuery.trim();
            getAllUsers();
        } else {
            searchKey = "";
            isSearch = false;
            getAllUsers();
        }
    }

    public void getAllUsers() {

        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
//        ProgressBar.showProgressBar(this, "Please wait...");
        ApplicationThread.bgndPost(LOG_TAG, "getting transactions data", () ->
                dataAccessHandler.getharvestorForSearch(searchKey, offset, LIMIT,
                        new ApplicationThread.OnComplete<List<BasicHarvestorDetails>>() {
                            @Override
                            public void execute(boolean success, final List<BasicHarvestorDetails> harvestorDetails, String msg) {
//                        ProgressBar.hideProgressBar();
                                isLoading = false;
                                if (harvestorDetails.isEmpty()) {
                                    hasMoreItems = false;
                                }

                                if (offset == 0 && isSearch) {
                                    mHarvestorsList.clear();
                                    mHarvestorsList.addAll(harvestorDetails);

                                } else {

                                    if (harvestorDetails != null & harvestorDetails.size() > 0)
                                        mHarvestorsList.clear();

                                    mHarvestorsList.addAll(harvestorDetails);
                                }
                                ApplicationThread.uiPost(LOG_TAG, "update ui", new Runnable() {
                                    @Override
                                    public void run() {
                                        progress.setVisibility(View.GONE);
                                        int harvestorSize = harvestorDetails.size();
                                        Log.v(LOG_TAG, "data size " + harvestorSize);

                                    }
                                });
                            }

                        }));

    }

    public boolean validate() {

        if (harvesterType.getSelectedItemPosition() == 0) {
            UiUtils.showCustomToastMessage("Please Select Harvester Type", HarvestingActivity.this, 0);
            return false;
        }

        if (isExisitng == true){

            if (TextUtils.isEmpty(autoCompleteHarvestor.getText().toString())){

                UiUtils.showCustomToastMessage("Please Select Harvestor", HarvestingActivity.this, 0);
                return false;
            }
        }

        if (isExisitng == false) {
            if (TextUtils.isEmpty(harvestername.getText().toString())) {
                UiUtils.showCustomToastMessage("Please Enter Harvester Name", HarvestingActivity.this, 0);
                return false;
            }
            if (TextUtils.isEmpty(mobilenumber.getText().toString())) {
                UiUtils.showCustomToastMessage("Please Enter Mobile Number", HarvestingActivity.this, 0);
                return false;
            }
            if (mobilenumber.getText().toString().length() < 10) {
                UiUtils.showCustomToastMessage("Please Enter Proper Mobile Number", HarvestingActivity.this, 0);
                return false;
            }

            if (TextUtils.isEmpty(village.getText().toString())) {
                UiUtils.showCustomToastMessage("Please Enter Village", HarvestingActivity.this, 0);
                return false;
            }
            if (TextUtils.isEmpty(mandal.getText().toString())) {
                UiUtils.showCustomToastMessage("Please Enter Mandal", HarvestingActivity.this, 0);
                return false;
            }

        }

        if (ownpoleavailable.getSelectedItemPosition() == 0) {
            UiUtils.showCustomToastMessage("Please Select Own Pole Available", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(harvestingprice.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Harvesting Price", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(harvestinginterval.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Harvesting Interval", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(totalexpectedquntity.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Total Expected Quantity", HarvestingActivity.this, 0);
            return false;
        }
        if (ffbsupplyto.getSelectedItemPosition() == 0) {
            UiUtils.showCustomToastMessage("Please Enter FFB Supply to", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(unripen.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Unripen", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(underripe.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Underripe", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(ripen.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Ripen", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(overripe.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Overripe", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(diseased.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Diseased", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(emptybunches.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Empty Bunches", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(longstalk.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Long Stock Quality", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(mediumstalk.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Medium Stock Quality", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(shortstalk.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Short Stock Quality", HarvestingActivity.this, 0);
            return false;
        }
        if (TextUtils.isEmpty(optimum.getText().toString())) {
            UiUtils.showCustomToastMessage("Please Enter Optimum Stock Quality", HarvestingActivity.this, 0);
            return false;
        }

        if (isloosefruitavailable_spinner.getSelectedItemPosition() == 0) {
            UiUtils.showCustomToastMessage("Please Select Is Loose Fruit Available", HarvestingActivity.this, 0);
            return false;
        }

        if (isloosefruitavailable_spinner.getSelectedItemPosition() == 1){
            if (TextUtils.isEmpty(loosefruitweight.getText().toString())) {
                UiUtils.showCustomToastMessage("Please Enter Loose Fruit Weight", HarvestingActivity.this, 0);
                return false;
            }
        }

        if (farmeravailableinfield.getSelectedItemPosition() == 0) {
            UiUtils.showCustomToastMessage("Please Select Farmer Available in Field", HarvestingActivity.this, 0);
            return false;
        }
        if (farmeravailableinfield.getSelectedItemPosition() == 2) {
            if (detailsinformedtofarmer.getSelectedItemPosition() == 0) {
                UiUtils.showCustomToastMessage("Please Select Details Informed to Farmer", HarvestingActivity.this, 0);
                return false;
            }
        }

        if ((Double.parseDouble(unripen.getText().toString()) + Double.parseDouble(underripe.getText().toString()) + Double.parseDouble(ripen.getText().toString()) + Double.parseDouble(overripe.getText().toString()) + Double.parseDouble(diseased.getText().toString()) + Double.parseDouble(emptybunches.getText().toString())) != 100) {
            UiUtils.showCustomToastMessage("FFB Bunch Quality should be equal to 100%", HarvestingActivity.this, 0);
            return false;
        }

        if ((Double.parseDouble(longstalk.getText().toString()) + Double.parseDouble(mediumstalk.getText().toString()) + Double.parseDouble(shortstalk.getText().toString()) + Double.parseDouble(optimum.getText().toString())) != 100) {
            UiUtils.showCustomToastMessage("FFB Stalk Quality should be equal to 100%", HarvestingActivity.this, 0);
            return false;
        }

        return true;
    }

    private List<String> getData() {
        List<String> dataList = new ArrayList<String>();
        dataList.add("Fashion Men");
        dataList.add("Fashion Women");
        dataList.add("Baby");
        dataList.add("Kids");
        dataList.add("Electronics");
        dataList.add("Appliance");
        dataList.add("Travel");
        dataList.add("Bags");
        dataList.add("FootWear");
        dataList.add("Jewellery");
        dataList.add("Sports");
        dataList.add("Electrical");
        dataList.add("Sports Kids");
        return dataList;
    }
}