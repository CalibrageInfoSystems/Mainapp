package com.oilpalm3f.mainapp.areaextension;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUiUtils;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.conversion.ConversionBankDetailsFragment;
import com.oilpalm3f.mainapp.conversion.ConversionIDProofFragment;
import com.oilpalm3f.mainapp.cropmaintenance.BankDetailsFragment;
import com.oilpalm3f.mainapp.cropmaintenance.CropMaintanenceIdProofsDetails;
import com.oilpalm3f.mainapp.cropmaintenance.CropMaintenanceHomeScreen;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DataSavingHelper;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.Farmer;
import com.oilpalm3f.mainapp.dbmodels.IdentityProof;
import com.oilpalm3f.mainapp.dbmodels.Plot;
import com.oilpalm3f.mainapp.dbmodels.PlotIrrigationTypeXref;
import com.oilpalm3f.mainapp.dbmodels.WaterResource;
import com.oilpalm3f.mainapp.ui.OilPalmBaseActivity;
import com.oilpalm3f.mainapp.uihelper.ProgressBar;
import com.oilpalm3f.mainapp.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;


//Registration Modules Screen
public class RegistrationFlowScreen extends OilPalmBaseActivity implements UpdateUiListener, WaterSoilTypeDialogFragment.onTypeSelected {
    private static final String LOG_TAG = RegistrationFlowScreen.class.getName();
    private Button personalDetailsBtn;
    private Button plotDetailsBtn;
    private Button wspBtn;
    private Button plotGeoTagBtn;
    private Button cpBtn;
    private Button referalsBtn;
    private Button marketSurveyBtn;
    private Button idProofBtn;
    private Button bankDetailsBtn;
    private Button finishBtn;
    private LinearLayout areaExtensionRel;
    private ActionBar actionBar;
    public FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    List<IdentityProof> identityProofs = new ArrayList<>();
    private PersonalDetailsFragment personalDetailsFragment;
    private ConversionPotentialFragment conversionPotentialFragment;
    private DataAccessHandler dataAccessHandler;


    //Initializing the Class & UI
    @Override
    public void Initialize() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View parentView = inflater.inflate(R.layout.content_registration_flow_screen, null);
        baseLayout.addView(parentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.areaExtensionRel = (LinearLayout) findViewById(R.id.areaExtensionRel);
        this.finishBtn = (Button) findViewById(R.id.finishBtn);
        this.marketSurveyBtn = (Button) findViewById(R.id.marketSurveyBtn);
        this.referalsBtn = (Button) findViewById(R.id.referalsBtn);
        this.cpBtn = (Button) findViewById(R.id.cpBtn);
        this.plotGeoTagBtn = (Button) findViewById(R.id.plotGeoTagBtn);
        this.wspBtn = (Button) findViewById(R.id.wspBtn);
        this.plotDetailsBtn = (Button) findViewById(R.id.plotDetailsBtn);
        this.personalDetailsBtn = (Button) findViewById(R.id.personalDetailsBtn);
        bankDetailsBtn = findViewById(R.id.bankDetailsBtn);
        idProofBtn = findViewById(R.id.idProofBtn);


        if (CommonUtils.isNewPlotRegistration()) {
            setTile(getResources().getString(R.string.existing_farmer_registration));
        } else if (CommonUtils.isFromFollowUp()) {
            setTile(getResources().getString(R.string.followup));
        } else {
            setTile(getResources().getString(R.string.new_farmer_registration));
        }

        dataAccessHandler = new DataAccessHandler(this);


        setviews();

    }

    //Setting the OnClick Listeners
    private void setviews() {
        if (CommonUtils.isFromFollowUp()){
            if (null != DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS)) {
                Farmer savedFarmerData = (Farmer) DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS);
                Plot savedPlot = (Plot) DataManager.getInstance().getDataFromManager(DataManager.PLOT_DETAILS);
                if (CommonConstants.FARMER_CODE.equalsIgnoreCase(savedPlot.getFarmercode())){
                    CommonConstants.FARMER_CODE = savedFarmerData.getCode();
                }else {
                    UiUtils.showCustomToastMessage( "Farmer Code IS Not Matches", RegistrationFlowScreen.this, 1);
                    finish();
                }
            }
        }
        personalDetailsBtn.setOnClickListener(this);
        plotDetailsBtn.setOnClickListener(this);
        wspBtn.setOnClickListener(this);
        plotGeoTagBtn.setOnClickListener(this);
        cpBtn.setOnClickListener(this);
        referalsBtn.setOnClickListener(this);
        marketSurveyBtn.setOnClickListener(this);
        idProofBtn.setOnClickListener(this);
        bankDetailsBtn.setOnClickListener(this);
        finishBtn.setOnClickListener(this);
        finishBtn.setEnabled(CommonUiUtils.isMandatoryDataEnteredForNRF());
    }

    //OnClick Listeners
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.personalDetailsBtn:
                personalDetailsFragment = new PersonalDetailsFragment();
                personalDetailsFragment.setUpdateUiListener(this);
                replaceFragment(personalDetailsFragment);

                break;
            case R.id.idProofBtn:

                Farmer f1 = (Farmer) DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS);

                if(f1 !=null)
                {
                    final DataAccessHandler dataAccessHandlerObj = new DataAccessHandler(this);
                    boolean recordExistedid = dataAccessHandlerObj.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(DatabaseKeys.TABLE_IDENTITYPROOF, "FarmerCode", CommonConstants.FARMER_CODE));
                    if (recordExistedid) {
                        replaceFragment(new CropMaintanenceIdProofsDetails());
                    } else {
                        Bundle idproofsBundle = new Bundle();
                        idproofsBundle.putString("whichScreen", "conversionidproofshomepage");
                        ConversionIDProofFragment conversionIDProofFragment = new ConversionIDProofFragment();
                        conversionIDProofFragment.setUpdateUiListener(this);
                        replaceFragment(conversionIDProofFragment, idproofsBundle);
                    }
                }
                else {
                    UiUtils.showCustomToastMessage("Please Take Personal Details Data", RegistrationFlowScreen.this, 1);
                }

                break;
            case R.id.bankDetailsBtn:


                Farmer f2 = (Farmer) DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS);

                if(f2 !=null) {
                    final DataAccessHandler dataAccessHandler = new DataAccessHandler(this);
                    boolean recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(DatabaseKeys.TABLE_FARMERBANK, "FarmerCode", CommonConstants.FARMER_CODE));
                    if (recordExisted) {
                        replaceFragment(new BankDetailsFragment());
                    } else {
                        Bundle bankBundle = new Bundle();
                        bankBundle.putString("whichScreen", "conversionBankhomepage");
                        ConversionBankDetailsFragment conversionBankDetailsFragment = new ConversionBankDetailsFragment();
                        conversionBankDetailsFragment.setUpdateUiListener(this);
                        replaceFragment(conversionBankDetailsFragment, bankBundle);
                    }
                }
                else {
                    UiUtils.showCustomToastMessage("Please Take Personal Details Data", RegistrationFlowScreen.this, 1);

                }
                break;
            case R.id.plotDetailsBtn:

                Farmer f = (Farmer) DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS);

               if(f !=null)
                {
                    PlotDetailsFragment plotDetailsFragment = new PlotDetailsFragment();
                    plotDetailsFragment.setUpdateUiListener(this);
                    replaceFragment(plotDetailsFragment);
                }
                else {
                    UiUtils.showCustomToastMessage("Please Take Personal Details Data", RegistrationFlowScreen.this, 1);

                }
                break;

            case R.id.wspBtn:
//                replaceFragment(new WSPDetailsFragment());
                FragmentManager fm = getSupportFragmentManager();
                WaterSoilTypeDialogFragment mWaterSoilTypeDialogFragment = new WaterSoilTypeDialogFragment();
                mWaterSoilTypeDialogFragment.setOnTypeSelected(this);
                mWaterSoilTypeDialogFragment.show(fm, "fragment_edit_name");
                break;

            case R.id.plotGeoTagBtn:
                GeoTagFragment geoTagFragment = new GeoTagFragment();
                geoTagFragment.setUpdateUiListener(this);
                replaceFragment(geoTagFragment);
                break;

            case R.id.cpBtn:
                conversionPotentialFragment = new ConversionPotentialFragment();
                conversionPotentialFragment.setUpdateUiListener(this);
                replaceFragment(conversionPotentialFragment);
                break;

            case R.id.referalsBtn:
                ReferralsFragment referralsFragment = new ReferralsFragment();
                referralsFragment.setUpdateUiListener(this);
                replaceFragment(referralsFragment);
                break;

            case R.id.marketSurveyBtn:
                startActivity(new Intent(RegistrationFlowScreen.this, MarketSurveyScreen.class));
                break;

            case R.id.finishBtn:
                if(CommonUiUtils.checkforIdentityDetails(this)){
                    UiUtils.showCustomToastMessage("Farmer is Ready To Convert Yes So Please Take IdentityDetails", RegistrationFlowScreen.this, 1);
                }else if(CommonUiUtils.checkBankDetails(this)){
                    UiUtils.showCustomToastMessage("Farmer is Ready To Convert Yes So Please Take BankDetails", RegistrationFlowScreen.this, 1);
                } else if (CommonUiUtils.checkForGeoTag(this)) {
                    UiUtils.showCustomToastMessage("Farmer is Ready To Convert Yes So Please Take GeoTag", RegistrationFlowScreen.this, 1);
                } else if (CommonUiUtils.checkForWaterSoilPowerDetails(this)){
                    UiUtils.showCustomToastMessage("Farmer is Ready To Convert Yes So Please Take Water Soil Power Details", RegistrationFlowScreen.this, 1);
                }
                else  if(CommonUiUtils.checkHorticultureAndLandType(this))
                {
                    UiUtils.showCustomToastMessage("Farmer is Ready To Convert , So Please select  land Type and total area of horticulture in plot screen", RegistrationFlowScreen.this, 1);

                }
                else {
                    ProgressBar.showProgressBar(RegistrationFlowScreen.this,"Please wait data is Inserting in DataBase.....");
                    DataSavingHelper.saveFarmerAddressData(this, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            if (success) {
                                ProgressBar.hideProgressBar();
                                UiUtils.showCustomToastMessage((CommonUtils.isNewRegistration()) ? "Data saved successfully" : "Data updated successfully", RegistrationFlowScreen.this, 0);
                                finish();
                            } else {
                                UiUtils.showCustomToastMessage("Data saving failed", RegistrationFlowScreen.this, 0);
                            }
                        }
                    });
                }
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        uiRefresh();
    }

    //Refresh UI based on Fields filled
    public void uiRefresh() {
        if (CommonUtils.isFromFollowUp()) {
            finishBtn.setEnabled(CommonUiUtils.isMandatoryDataEnteredForFollowUp());
            finishBtn.setFocusable(CommonUiUtils.isMandatoryDataEnteredForFollowUp());
            finishBtn.setClickable(CommonUiUtils.isMandatoryDataEnteredForFollowUp());
            if (CommonUiUtils.isMandatoryDataEnteredForFollowUp()) {
                finishBtn.setAlpha(1.0f);
            } else {
                finishBtn.setAlpha(0.5f);
            }
        } else if (!CommonUtils.isFromFollowUp()) {
            finishBtn.setEnabled(CommonUiUtils.isMandatoryDataEnteredForNRF());
            finishBtn.setFocusable(CommonUiUtils.isMandatoryDataEnteredForNRF());
            finishBtn.setClickable(CommonUiUtils.isMandatoryDataEnteredForNRF());
            if (CommonUiUtils.isMandatoryDataEnteredForNRF()) {
                finishBtn.setAlpha(1.0f);
            }else {
                finishBtn.setAlpha(0.5f);
            }
        } else {
            finishBtn.setEnabled(true);
            finishBtn.setFocusable(true);
            finishBtn.setClickable(true);
        }

        if (null != DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS)) {
            Farmer savedFarmerData = (Farmer) DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS);
            CommonConstants.FARMER_CODE = savedFarmerData.getCode();
            personalDetailsBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        }

        if (null != DataManager.getInstance().getDataFromManager(DataManager.ID_PROOFS_DATA)) {
            identityProofs = (List<IdentityProof>) DataManager.getInstance().getDataFromManager(DataManager.ID_PROOFS_DATA);
        }
        if (null != DataManager.getInstance().getDataFromManager(DataManager.ID_PROOFS_DATA) && !identityProofs.isEmpty()) {
            idProofBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        } else {
            idProofBtn.setBackground(getResources().getDrawable(R.drawable.rounded_btn));
        }
        if (null != DataManager.getInstance().getDataFromManager(DataManager.FARMER_BANK_DETAILS)) {
            bankDetailsBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        }
        if (null != DataManager.getInstance().getDataFromManager(DataManager.PLOT_DETAILS) || CommonUtils.isFromFollowUp()) {
            plotDetailsBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        }

        boolean existed = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().queryGeoTagCheck(CommonConstants.PLOT_CODE));
        if (existed) {
            plotGeoTagBtn.setEnabled(false);
            plotGeoTagBtn.setFocusable(false);
            plotGeoTagBtn.setClickable(false);
            plotGeoTagBtn.setAlpha(0.5f);
            plotGeoTagBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        } else {
            plotGeoTagBtn.setEnabled(true);
            plotGeoTagBtn.setFocusable(true);
            plotGeoTagBtn.setClickable(true);
            plotGeoTagBtn.setAlpha(1.0f);

            if (null != DataManager.getInstance().getDataFromManager(DataManager.PLOT_GEO_TAG)) {
                plotGeoTagBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
            }
        }

//        if (CommonUtils.isFromFollowUp()) {
//            ArrayList<WaterResource> mWaterTypeModelList = (ArrayList<WaterResource>) dataAccessHandler.getWaterResourceData(Queries.getInstance().getWaterResourceBinding(CommonConstants.PLOT_CODE), 1);
//            ArrayList<PlotIrrigationTypeXref> msoilTypeIrrigationModelList = (ArrayList<PlotIrrigationTypeXref>) dataAccessHandler.getPlotIrrigationXRefData(Queries.getInstance().getPlotIrrigationTypeXrefBinding(CommonConstants.PLOT_CODE), 1);
//            if ((null != mWaterTypeModelList && !mWaterTypeModelList.isEmpty()) || (null != msoilTypeIrrigationModelList && !msoilTypeIrrigationModelList.isEmpty())) {
//                wspBtn.setBackgroundColor(getResources().getColor(R.color.gray));
//            }
//        } else {
//            if (null != DataManager.getInstance().getDataFromManager(DataManager.SOURCE_OF_WATER)
//                    || null != DataManager.getInstance().getDataFromManager(DataManager.SoilType)) {
//                wspBtn.setBackgroundColor(getResources().getColor(R.color.gray));
//            }
//        }

            if (null != DataManager.getInstance().getDataFromManager(DataManager.SOURCE_OF_WATER)
                    || null != DataManager.getInstance().getDataFromManager(DataManager.SoilType)) {
                wspBtn.setBackgroundColor(getResources().getColor(R.color.gray));
            }



        if (null != DataManager.getInstance().getDataFromManager(DataManager.PLOT_FOLLOWUP)) {
            cpBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        }

        if (null != DataManager.getInstance().getDataFromManager(DataManager.REFERRALS_DATA)) {
            referalsBtn.setBackgroundColor(getResources().getColor(R.color.gray));
        }
        if (null != DataManager.getInstance().getDataFromManager(DataManager.MARKET_SURVEY_DATA)) {
            marketSurveyBtn.setBackgroundColor(getResources().getColor(R.color.gray));
        }

        if (CommonConstants.REGISTRATION_SCREEN_FROM.equalsIgnoreCase(CommonConstants.REGISTRATION_SCREEN_FROM_NEW_PLOT)
                || CommonConstants.REGISTRATION_SCREEN_FROM.equalsIgnoreCase(CommonConstants.REGISTRATION_SCREEN_FROM_FOLLOWUP)) {
            Farmer selectedFarmer = (Farmer) DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS);
            if (null != selectedFarmer && CommonUiUtils.isMarketSurveyAddedForFarmer(this, Queries.getInstance().getMarketSurveyFromFarmerCode(selectedFarmer.getCode()))) {
                Log.v(LOG_TAG, "#### checking for market survey already market survey data entered");
                marketSurveyBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
                marketSurveyBtn.setAlpha(0.5f);
                marketSurveyBtn.setEnabled(false);
                marketSurveyBtn.setFocusable(false);
                marketSurveyBtn.setClickable(false);
            }
        }
    }

    @Override
    public void updateUserInterface(int position) {
        Log.v(LOG_TAG, "@@@ ui update called");
        uiRefresh();
    }

    //on Water Type Selected
    @Override
    public void onTypeSelected(int type) {
        AreaWaterTypeFragment areaWaterTypeFragment = new AreaWaterTypeFragment();
        areaWaterTypeFragment.setUpdateUiListener(this);
        SoilTypeFragment soilTypeFragment = new SoilTypeFragment();
        soilTypeFragment.setUpdateUiListener(this);
        replaceFragment(type == 1 ? areaWaterTypeFragment : soilTypeFragment);
    }
}
