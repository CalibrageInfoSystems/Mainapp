package com.oilpalm3f.mainapp.areaextension;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.common.FiscalDate;
import com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.CookingOil;
import com.oilpalm3f.mainapp.dbmodels.MarketSurvey;
import com.oilpalm3f.mainapp.uihelper.InteractiveScrollView;
import com.oilpalm3f.mainapp.utils.UiUtils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


//Market Survey Screen
public class MarketSurveyScreen extends AppCompatActivity {

    private static final String LOG_TAG = MarketSurveyScreen.class.getName();
    private static Double totalQuantity, totalAmount;
    public InteractiveScrollView interactiveScrollView;
    public ImageView scrollBottomIndicator;
    private Double finalQuantity = 0.0, finalAmount = 0.0;
    private RecyclerView cookingOilRecyclerView;
    private ImageView addRowImg;
    private EditText marketSurveyDateEdt, personFamilyEdt, particularOilEdt, willingToPayEdt, quantityCattleEdt;
    private Button marketSurveySaveBtn;
    private TextView framerCodeText, quantityMonth, amountMonth;
    private String personFamilyStr, particularOilStr,
            payStr, brandStr, quantityCattleStr;
    private AlertDialog alert;
    private CookingOilAdapter cookingOilAdapter;
    private DataAccessHandler dataAccessHandler;
    private Calendar myCalendar = Calendar.getInstance();
    private LinearLayout cattleLayout, vehicleLayout, parentLayout;
    private Double quantityStr;
    private Double amountStr;
    private Spinner swapSpin, smartPhoneSpin, cattleSpin, ownVehicleSpin, vehicleTypeSpin, cattleTypeSpin;
    private List<CookingOil> cookingOilList = new ArrayList<>();
    private MarketSurvey marketSurvey;
    private List<String> oilTypeArr;
    private boolean isFromUpdate = false;
    private LinkedHashMap<String, String> vehicleTypeMap, oilTypeMap, mainMap, cattleTypeMap;
    private String marketsurveycode,generatedMarketSuyCode;
    public int financialYear;
    private String days = "";
    private String financalSubStringYear = "";
    public static String financalYrDays = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_survey);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.market_survey);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        CommonUtils.currentActivity = this;

        init();

        marketSurvey = (MarketSurvey) DataManager.getInstance().getDataFromManager(DataManager.MARKET_SURVEY_DATA);

        if (marketSurvey != null) {
            isFromUpdate = true;
            cookingOilList = (List<CookingOil>) DataManager.getInstance().getDataFromManager(DataManager.OIL_TYPE_MARKET_SURVEY_DATA);
            bindData();
        }

        mainMap = oilTypeMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("5"));


        cookingOilRecyclerView.setHasFixedSize(true);
        cookingOilAdapter = new CookingOilAdapter(MarketSurveyScreen.this, cookingOilList, mainMap);
        cookingOilRecyclerView.setLayoutManager(new LinearLayoutManager(MarketSurveyScreen.this, LinearLayoutManager.VERTICAL, false));
        cookingOilRecyclerView.setAdapter(cookingOilAdapter);
        cookingOilAdapter.notifyDataSetChanged();

    }

    public void init() {
        oilTypeArr = Arrays.asList(getResources().getStringArray(R.array.CookingOilType));

        scrollBottomIndicator = (ImageView) findViewById(R.id.bottomScroll);
        interactiveScrollView = (InteractiveScrollView) findViewById(R.id.scrollView);
        scrollBottomIndicator.setVisibility(View.VISIBLE);

        scrollBottomIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interactiveScrollView.post(new Runnable() {

                    @Override
                    public void run() {
                        interactiveScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });

        interactiveScrollView.setOnBottomReachedListener(new InteractiveScrollView.OnBottomReachedListener() {
            @Override
            public void onBottomReached() {
                scrollBottomIndicator.setVisibility(View.GONE);
            }
        });

        interactiveScrollView.setOnTopReachedListener(new InteractiveScrollView.OnTopReachedListener() {
            @Override
            public void onTopReached() {
            }
        });

        interactiveScrollView.setOnScrollingListener(new InteractiveScrollView.OnScrollingListener() {
            @Override
            public void onScrolling() {
                scrollBottomIndicator.setVisibility(View.VISIBLE);
            }
        });

        dataAccessHandler = new DataAccessHandler(this);

        parentLayout = (LinearLayout) findViewById(R.id.marketparent);

        cattleLayout = (LinearLayout) findViewById(R.id.cattleLayout);
        vehicleLayout = (LinearLayout) findViewById(R.id.vehicleLayout);
        cookingOilRecyclerView = (RecyclerView) findViewById(R.id.cookingOilRecyclerView);
        marketSurveyDateEdt = (EditText) findViewById(R.id.marketSurveyDateEdt);
        personFamilyEdt = (EditText) findViewById(R.id.personFamilyEdt);
        particularOilEdt = (EditText) findViewById(R.id.particularOilEdt);
        willingToPayEdt = (EditText) findViewById(R.id.payEdt);
        cattleTypeSpin = (Spinner) findViewById(R.id.cattleTypeSpin);
        quantityCattleEdt = (EditText) findViewById(R.id.QuantityEdt);
        addRowImg = (ImageView) findViewById(R.id.addRowImg);
        framerCodeText = (TextView) findViewById(R.id.framerCodeText);
        swapSpin = (Spinner) findViewById(R.id.swapSpin);
        smartPhoneSpin = (Spinner) findViewById(R.id.smartPhoneSpin);
        cattleSpin = (Spinner) findViewById(R.id.cattleSpin);
        ownVehicleSpin = (Spinner) findViewById(R.id.vehicleSpin);
        vehicleTypeSpin = (Spinner) findViewById(R.id.vehicleTypeSpin);
        // Finical Year
        final Calendar calendar = Calendar.getInstance();
        final FiscalDate fiscalDate = new FiscalDate(calendar);
        financialYear = fiscalDate.getFiscalYear();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                String currentdate = CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_3);
                String financalDate = "01/04/"+String.valueOf(financialYear);
                Date date1 = dateFormat.parse(currentdate);
                Date date2 = dateFormat.parse(financalDate);
                long diff = date1.getTime() - date2.getTime();
              String  noOfDays = String.valueOf(TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS));
                days = StringUtils.leftPad(noOfDays,3,"0");
                financalYrDays = String.valueOf(financialYear).substring(2,3) + days;
//                    Toast.makeText(getActivity(),"Nof Days"+days,Toast.LENGTH_LONG).show();
//                financalSubStringYear = String.valueOf(financialYear).substring(2,3);

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        framerCodeText.setText(CommonConstants.farmerFirstName + " " + CommonConstants.farmerMiddleName + " " + CommonConstants.farmerLastName);

        quantityMonth = (TextView) findViewById(R.id.quantity_mnth);
        amountMonth = (TextView) findViewById(R.id.amount_mnth);
        CommonConstants.MarketSurveyCode= CommonConstants.MARKET_SURVEY_CODE_PREFIX + CommonConstants.FARMER_CODE;
//        CommonConstants.MarketSurveyCode = dataAccessHandler.getGeneratedMarketSurveyCode(Queries.getInstance().getMaxNumberQueryMarketSurvey(CommonConstants.villageId, CommonConstants.villageCode));
//        generatedMarketSuyCode = CommonConstants.MARKET_SURVEY_CODE_PREFIX + CommonConstants.FARMER_CODE;
//        marketsurveycode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getMarketSurveyId(CommonConstants.FARMER_CODE));
//        Toast.makeText(MarketSurveyScreen.this,"marketsurveycode"+marketsurveycode,Toast.LENGTH_LONG).show();


        updateLabel();

        parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftKeyboard();
                return false;
            }
        });

        cattleSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    cattleLayout.setVisibility(View.VISIBLE);
                    cattleTypeMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("20"));
                    ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(MarketSurveyScreen.this, android.R.layout.simple_spinner_item,
                            CommonUtils.fromMap(cattleTypeMap, "Cattle Type"));
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cattleTypeSpin.setAdapter(spinnerArrayAdapter);
                    if (isFromUpdate) {
                        cattleTypeSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(cattleTypeMap, marketSurvey.getCattleTypeId()));
                    }
                } else {
                    cattleLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ownVehicleSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    vehicleLayout.setVisibility(View.VISIBLE);
                } else {
                    vehicleLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        marketSurveyDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MarketSurveyScreen.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addRowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputCookingOilDialog();
            }
        });

        marketSurveySaveBtn = (Button) findViewById(R.id.marketSurveySaveBtn);

        vehicleTypeMap = dataAccessHandler.getGenericData(Queries.getInstance().getVehicleTypeQuery());
        vehicleTypeSpin.setAdapter(UiUtils.createAdapter(this, vehicleTypeMap, "Vehicle Type"));

        marketSurveySaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (marketsurveycode != null){
//                    UiUtils.showCustomToastMessage("  MarketSurvey for this farmer already done",MarketSurveyScreen.this,1);
//                }else {
//                    UiUtils.showCustomToastMessage("Generate new market survey",MarketSurveyScreen.this,0);
                    personFamilyStr = personFamilyEdt.getText().toString();
                    particularOilStr = particularOilEdt.getText().toString();
                    payStr = willingToPayEdt.getText().toString();
                    quantityCattleStr = quantityCattleEdt.getText().toString();

//                    String Villagecode = CommonConstants.FARMER_CODE.substring(6,7);
//                    CommonConstants.MarketSurveyCode = dataAccessHandler.getGeneratedMarketSurveyCode(Queries.getInstance().getMaxNumberQueryMarketSurvey(CommonConstants.villageId, CommonConstants.villageCode));
                CommonConstants.MarketSurveyCode = CommonConstants.MARKET_SURVEY_CODE_PREFIX + CommonConstants.FARMER_CODE;
                    setMarketSurveyData();
//                }

            }
        });
    }

    private void bindData() {
        quantityMonth.setText("" + totalQuantity);
        amountMonth.setText("" + totalAmount);
        marketSurveyDateEdt.setText(marketSurvey.getSurveyDate());
        framerCodeText.setText(marketSurvey.getFarmerCode());
        personFamilyEdt.setText("" + marketSurvey.getFamilyMembersCount());
        particularOilEdt.setText(marketSurvey.getReason());
        if (marketSurvey.getIsFarmerWillingtoUse() == 1) {
            swapSpin.setSelection(1);
            quantityCattleEdt.setText("" + marketSurvey.getCattlesCount());
        } else if (marketSurvey.getIsFarmerWillingtoUse() == 3) {
            swapSpin.setSelection(2);
        } else {
            swapSpin.setSelection(0);
        }

        if (marketSurvey.getIsCattleExist() == 1) {
            cattleSpin.setSelection(1);
        } else if (marketSurvey.getIsCattleExist() == 3) {
            cattleSpin.setSelection(2);
        } else {
            cattleSpin.setSelection(0);
        }

        if (marketSurvey.getIsFarmerHavingOwnVehicle() == 1) {
            ownVehicleSpin.setSelection(1);
        } else if (marketSurvey.getIsFarmerHavingOwnVehicle() == 3) {
            ownVehicleSpin.setSelection(2);
        } else {
            ownVehicleSpin.setSelection(0);
        }

        if (marketSurvey.getIsFarmerUseSmartPhone() == 1) {
            smartPhoneSpin.setSelection(1);
        } else if (marketSurvey.getIsFarmerUseSmartPhone() == 3) {
            smartPhoneSpin.setSelection(2);
        } else {
            smartPhoneSpin.setSelection(0);
        }

        willingToPayEdt.setText("" + marketSurvey.getEstimatedAmounttoPay());
        if (null != marketSurvey.getVehicleTypeId()) {
            vehicleTypeSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(vehicleTypeMap, marketSurvey.getVehicleTypeId()));
        }

    }

    public void setMarketSurveyData() {
        try {
            marketSurvey = new MarketSurvey();
            marketSurvey.setVillageId(Integer.parseInt(CommonConstants.villageId));
            marketSurvey.setSurveyDate(marketSurveyDateEdt.getText().toString());
            marketSurvey.setFarmerCode(CommonConstants.FARMER_CODE);
            marketSurvey.setFarmerName(framerCodeText.getText().toString());
            marketSurvey.setFamilyMembersCount(!TextUtils.isEmpty(personFamilyStr) ? Integer.parseInt(personFamilyStr) : 0);
            marketSurvey.setReason(particularOilStr);
            marketSurvey.setIsFarmerWillingtoUse(swapSpin.getSelectedItem().toString().equalsIgnoreCase("Yes") ? 1 : 0);
            marketSurvey.setEstimatedAmounttoPay(!TextUtils.isEmpty(payStr) ? Double.parseDouble(payStr) : 0);
            marketSurvey.setIsFarmerUseSmartPhone(smartPhoneSpin.getSelectedItem().toString().equalsIgnoreCase("Yes") ? 1 : 0);
            marketSurvey.setIsCattleExist(cattleSpin.getSelectedItem().toString().equalsIgnoreCase("Yes") ? 1 : 0);
            marketSurvey.setIsFarmerHavingOwnVehicle(ownVehicleSpin.getSelectedItem().toString().equalsIgnoreCase("Yes") ? 1 : 0);
            if (!cattleTypeSpin.getSelectedItem().toString().isEmpty() && cattleTypeSpin.getSelectedItem().toString().trim().length() > 0 && cattleTypeMap != null) {
                marketSurvey.setCattleTypeId(Integer.parseInt(CommonUtilsNavigation.getKey(cattleTypeMap, cattleTypeSpin.getSelectedItem().toString())));
                marketSurvey.setCattlesCount(!TextUtils.isEmpty(quantityCattleStr) ? Integer.parseInt(quantityCattleStr) : 0);
            } else {
                marketSurvey.setCattleTypeId(null);
                marketSurvey.setCattlesCount(0);
            }

            if (!ownVehicleSpin.getSelectedItem().toString().isEmpty() && ownVehicleSpin.getSelectedItem().toString().trim().length() > 0 && ownVehicleSpin.getSelectedItem().toString().equalsIgnoreCase("Yes")) {
                int vehid = Integer.parseInt(CommonUtils.getKeyFromValue(vehicleTypeMap, vehicleTypeSpin.getSelectedItem().toString()));
                marketSurvey.setVehicleTypeId(vehid);
            } else {
                marketSurvey.setVehicleTypeId(null);
            }
            marketSurvey.setFarmerMobileNumber(""+CommonConstants.farmerMobileNumber);
            DataManager.getInstance().addData(DataManager.MARKET_SURVEY_DATA, marketSurvey);
            DataManager.getInstance().addData(DataManager.OIL_TYPE_MARKET_SURVEY_DATA, cookingOilList);
            CommonConstants.Flags.isMarketSurveyUpdated = true;
            finish();
        } catch (Exception e) {
            Log.e(LOG_TAG, "### error while setting data");
        }


    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        marketSurveyDateEdt.setText(sdf.format(myCalendar.getTime()));
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    protected void showInputCookingOilDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MarketSurveyScreen.this);
        View promptView = layoutInflater.inflate(R.layout.cookingoil_layout, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MarketSurveyScreen.this);
        alertDialogBuilder.setView(promptView);
        final Spinner cookingOil_spin = (Spinner) promptView.findViewById(R.id.cookingOil_spin);
        final EditText Brand_edt = (EditText) promptView.findViewById(R.id.Brand_edt);
        final EditText consumedPerMnth_edt = (EditText) promptView.findViewById(R.id.consumedPerMnth_edt);
        final EditText oilPerMonth_edt = (EditText) promptView.findViewById(R.id.oilPerMonth_edt);
        final Button marketSurveySaveBtn = (Button) promptView.findViewById(R.id.marketSurveySaveBtn);
        final Button marketSurveyCancelBtn = (Button) promptView.findViewById(R.id.marketSurveyCancelBtn);

        if (null != cookingOilList && !cookingOilList.isEmpty()) {
            for (CookingOil cookingOil : cookingOilList) {
                oilTypeMap.remove(String.valueOf(cookingOil.getCookingoiltypeid()));
            }
        }

        ArrayAdapter<String> oilTypeSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CommonUtils.fromMap(oilTypeMap, "Oil Type"));
        oilTypeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cookingOil_spin.setAdapter(oilTypeSpinnerArrayAdapter);

        marketSurveySaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!CommonUtils.isEmptySpinner(cookingOil_spin)) {

                    if (!TextUtils.isEmpty(Brand_edt.getText().toString()) &&
                            !TextUtils.isEmpty(consumedPerMnth_edt.getText().toString()) &&
                            !TextUtils.isEmpty(oilPerMonth_edt.getText().toString())) {

                        brandStr = Brand_edt.getText().toString();

                        if (consumedPerMnth_edt.getText().toString() != null && consumedPerMnth_edt.getText().toString().length() > 0)
                            quantityStr = Double.parseDouble(consumedPerMnth_edt.getText().toString());
                        else
                            quantityStr = 0.0;

                        if (oilPerMonth_edt.getText().toString() != null && oilPerMonth_edt.getText().toString().length() > 0)
                            amountStr = Double.parseDouble(oilPerMonth_edt.getText().toString());
                        else
                            amountStr = 0.0;


                        if (finalQuantity == 0) {
                            finalQuantity = quantityStr;
                        } else {
                            finalQuantity = finalQuantity + quantityStr;
                        }
                        Log.e("finalquantity", "" + finalQuantity);


                        if (finalAmount == 0) {
                            finalAmount = amountStr;
                        } else {
                            finalAmount = finalAmount + amountStr;
                        }
                        Log.e("finalamount", "" + finalAmount);

                        quantityMonth.setText("" + finalQuantity);
                        amountMonth.setText("" + finalAmount);

                        totalQuantity = finalQuantity;
                        totalAmount = finalAmount;
                        CookingOil cookingOil = new CookingOil();
                        String key = CommonUtils.getKeyFromValue(oilTypeMap, cookingOil_spin.getSelectedItem().toString());
                        cookingOil.setCookingoiltypeid(Integer.parseInt(key));
                        cookingOil.setBrandname(brandStr);
                        cookingOil.setMonthlyquantity(quantityStr);
                        cookingOil.setTotalpaidamount(amountStr);
                        cookingOilList.add(cookingOil);
                        oilTypeMap.remove(key);
                        mainMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("5"));
                        cookingOilAdapter.updateList(cookingOilList, mainMap);
                        alert.cancel();
                    } else {
                        Toast.makeText(MarketSurveyScreen.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MarketSurveyScreen.this, "Please Select Cooking Oil Type", Toast.LENGTH_SHORT).show();
                }
            }
        });
        marketSurveyCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();

            }
        });
        alert = alertDialogBuilder.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

