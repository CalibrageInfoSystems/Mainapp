package com.oilpalm3f.mainapp.conversion;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areacalculator.PreViewAreaCalScreen;
import com.oilpalm3f.mainapp.areaextension.AreaWaterTypeFragment;
import com.oilpalm3f.mainapp.areaextension.PersonalDetailsFragment;
import com.oilpalm3f.mainapp.areaextension.PlotDetailsFragment;
import com.oilpalm3f.mainapp.areaextension.ReferralsFragment;
import com.oilpalm3f.mainapp.areaextension.SoilTypeFragment;
import com.oilpalm3f.mainapp.areaextension.UpdateUiListener;
import com.oilpalm3f.mainapp.areaextension.WaterSoilTypeDialogFragment;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUiUtils;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.common.OilPalmException;
import com.oilpalm3f.mainapp.cropmaintenance.BankDetailsFragment;
import com.oilpalm3f.mainapp.cropmaintenance.CropMaintanenceIdProofsDetails;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DataSavingHelper;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.IdentityProof;
import com.oilpalm3f.mainapp.farmersearch.DisplayPlotsFragment;
import com.oilpalm3f.mainapp.dbmodels.CropModel;
import com.oilpalm3f.mainapp.ui.OilPalmBaseActivity;
import com.oilpalm3f.mainapp.uihelper.ProgressBar;
import com.oilpalm3f.mainapp.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;


//Conversion flow modules
public class ConversionMainFlowActivity extends OilPalmBaseActivity implements UpdateUiListener, WaterSoilTypeDialogFragment.onTypeSelected {

    private Button personalDetailsBtn, conversionbankdetailsBtn, conversionidproofdetailsBtn, plotDetailsBtn,
            plantationdetailsBtn, plotGeoTagBtn, interCropBtn,
            wspBtn, digitalcontractBtn, referalsBtn;
    private RelativeLayout contentregistrationflowscreen;
    private ActionBar actionBar;
    private Button finishBtn;
    List<IdentityProof> identityProofs = new ArrayList<>();
    List<CropModel> cropModelList = new ArrayList<>();
    private boolean doubleback = false;


    //Initializing the Class & UI Initialization
    @Override
    public void Initialize() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View parentView = inflater.inflate(R.layout.conversion_modules_flow_screen, null);
        contentregistrationflowscreen = parentView.findViewById(R.id.content_conversion_flow_screen);
        baseLayout.addView(contentregistrationflowscreen, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        referalsBtn = findViewById(R.id.referalsBtn);
        digitalcontractBtn = findViewById(R.id.digitalcontractBtn);
        wspBtn = findViewById(R.id.wspBtn);
        conversionidproofdetailsBtn = findViewById(R.id.conversionidproofdetailsBtn);
        plotGeoTagBtn = findViewById(R.id.plotGeoTagBtn);
        interCropBtn = findViewById(R.id.interCropDetailsBtn);
        plantationdetailsBtn = findViewById(R.id.plantationdetailsBtn);
        plotDetailsBtn = findViewById(R.id.plotDetailsBtn);
        conversionbankdetailsBtn = findViewById(R.id.conversionbankdetailsBtn);
        personalDetailsBtn = findViewById(R.id.personalDetailsBtn);
        finishBtn = findViewById(R.id.finishBtn);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        updateActionBarTitle(getString(R.string.conversiontitle));
        setviews();
    }

    //UI setup
    private void setviews() {
        personalDetailsBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));

        // plotDetailsBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        DataAccessHandler dataAccessHandler = new DataAccessHandler(this);
        boolean recordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(DatabaseKeys.TABLE_IDENTITYPROOF, "FarmerCode", CommonConstants.FARMER_CODE));
        if (recordExisted) {
            conversionidproofdetailsBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
            conversionbankdetailsBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        }
        boolean sourceResourcerecordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(DatabaseKeys.TABLE_SOILRESOURCE, "PlotCode", CommonConstants.PLOT_CODE));
        boolean waterResourcerecordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(DatabaseKeys.TABLE_WATERESOURCE, "PlotCode", CommonConstants.PLOT_CODE));
        if (waterResourcerecordExisted || sourceResourcerecordExisted) {
            wspBtn.setBackgroundColor(getResources().getColor(R.color.gray));
        }

        personalDetailsBtn.setOnClickListener(this);
        conversionbankdetailsBtn.setOnClickListener(this);
        plotDetailsBtn.setOnClickListener(this);
        plantationdetailsBtn.setOnClickListener(this);
        plotGeoTagBtn.setOnClickListener(this);
        wspBtn.setOnClickListener(this);
        digitalcontractBtn.setOnClickListener(this);
        referalsBtn.setOnClickListener(this);
        conversionidproofdetailsBtn.setOnClickListener(this);
        interCropBtn.setOnClickListener(this);
        finishBtn.setOnClickListener(this);
    }

    public void updateActionBarTitle(final String title) {
        actionBar.setTitle(title);
    }

    //On Click Listeners
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personalDetailsBtn:
                PersonalDetailsFragment personalDetailsFragment = new PersonalDetailsFragment();
                personalDetailsFragment.setUpdateUiListener(this);
                replaceFragment(personalDetailsFragment);
                break;
            case R.id.conversionidproofdetailsBtn:
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
                break;
            case R.id.conversionbankdetailsBtn:
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
                break;
            case R.id.plotDetailsBtn:
                PlotDetailsFragment plotDetailsFragment = new PlotDetailsFragment();
                plotDetailsFragment.setUpdateUiListener(this);
                replaceFragment(plotDetailsFragment);
                break;
            case R.id.plantationdetailsBtn:
                if (null == DataManager.getInstance().getDataFromManager(DataManager.PLOT_DETAILS)){
                    UiUtils.showCustomToastMessage("First fill the plot details", ConversionMainFlowActivity.this, 1);
                }else {
                    ConversionPlantationFragment conversionPlantationFragment = new ConversionPlantationFragment();
                    conversionPlantationFragment.setUpdateUiListener(this);
                    Bundle plantationBundle = new Bundle();
                    plantationBundle.putString("whichScreen", "conversionPlothomepage");
                    replaceFragment(conversionPlantationFragment, plantationBundle);
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
                if (null == DataManager.getInstance().getDataFromManager(DataManager.PLANTATION_CON_DATA)){
                    UiUtils.showCustomToastMessage("First fill the plot details and Plantation details", ConversionMainFlowActivity.this, 1);
                }else {
                    startActivity(new Intent(ConversionMainFlowActivity.this, PreViewAreaCalScreen.class));
                }
                break;
            case R.id.interCropDetailsBtn:
                InterCropDetailsFragment interCropDetailsFragment = new InterCropDetailsFragment();
                interCropDetailsFragment.setUpdateUiListener(this);
                replaceFragment(interCropDetailsFragment);
                break;
            case R.id.digitalcontractBtn:
//                if (null == DataManager.getInstance().getDataFromManager(DataManager.PLOT_GEO_TAG)){
//                    UiUtils.showCustomToastMessage("Please Take the Plot Boundaries", ConversionMainFlowActivity.this, 1);
//                }else {
                ConversionDigitalContractFragment conversionDigitalContractFragment = new ConversionDigitalContractFragment();
                conversionDigitalContractFragment.setUpdateUiListener(this);
                replaceFragment(conversionDigitalContractFragment);
//            }
                break;
            case R.id.referalsBtn:
                ReferralsFragment referralsFragment = new ReferralsFragment();
                referralsFragment.setUpdateUiListener(this);
                replaceFragment(referralsFragment);
                break;
            case R.id.finishBtn:

                if (!CommonUiUtils.isFarmerPhotoTaken(ConversionMainFlowActivity.this)) {
                    UiUtils.showCustomToastMessage("Please take farmer picture", ConversionMainFlowActivity.this, 1);
                } else if (!CommonUiUtils.isConversionPlotDataEntered()) {
                    UiUtils.showCustomToastMessage("Please enter required plot details", ConversionMainFlowActivity.this, 1);
                } else if (!CommonUiUtils.isWSPowerDataEntered(ConversionMainFlowActivity.this)) {
                    UiUtils.showCustomToastMessage("Please enter water soil details", ConversionMainFlowActivity.this, 1);
                } else {
                    ProgressBar.showProgressBar(ConversionMainFlowActivity.this,"Please wait data is Updating in DataBase.....");
                    DataSavingHelper.saveFarmerAddressData(this, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            if (success) {
                                ProgressBar.hideProgressBar();
                                DisplayPlotsFragment.plotCode = "";
                                UiUtils.showCustomToastMessage("Conversion Details Data updated successfully", ConversionMainFlowActivity.this, 0);
                                finish();
                            } else {
                                DisplayPlotsFragment.plotCode = "";
                                ProgressBar.hideProgressBar();
                                Log.pushExceptionToCrashlytics(new OilPalmException("Data saving failed in conversion "+msg+"-"+result));
                                UiUtils.showCustomToastMessage("Data saving failed "+msg+"-"+result, ConversionMainFlowActivity.this, 1);
                            }
                        }
                    });
                }

                break;
        }
    }

    @Override
    public void updateUserInterface(int refreshPosition) {
        uiRefresh();
    }

    //Update UI based on conditions

    public void uiRefresh() {
        finishBtn.setEnabled(CommonUiUtils.isMandatoryDataEnteredForConversion(this));
        finishBtn.setFocusable(CommonUiUtils.isMandatoryDataEnteredForConversion(this));
        finishBtn.setClickable(CommonUiUtils.isMandatoryDataEnteredForConversion(this));
        if (CommonUiUtils.isMandatoryDataEnteredForConversion(this)) {
            finishBtn.setAlpha(1.0f);
        } else {
            finishBtn.setAlpha(0.5f);
        }

        if (null != DataManager.getInstance().getDataFromManager(DataManager.PLOT_DETAILS) || CommonUtils.isFromFollowUp()) {
            plotDetailsBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        }
        if (null != DataManager.getInstance().getDataFromManager(DataManager.ID_PROOFS_DATA)) {
            identityProofs = (List<IdentityProof>) DataManager.getInstance().getDataFromManager(DataManager.ID_PROOFS_DATA);
        }
        if (null != DataManager.getInstance().getDataFromManager(DataManager.ID_PROOFS_DATA) && !identityProofs.isEmpty()) {
            conversionidproofdetailsBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        } else {
            //conversionidproofdetailsBtn.setBackground(getResources().getDrawable(R.drawable.rounded_btn));
        }
        if (null != DataManager.getInstance().getDataFromManager(DataManager.FARMER_BANK_DETAILS)) {
            conversionbankdetailsBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        }

        if (null != DataManager.getInstance().getDataFromManager(DataManager.PLOT_INTER_CROP_DATA)) {
            cropModelList = (List<CropModel>) DataManager.getInstance().getDataFromManager(DataManager.PLOT_INTER_CROP_DATA);
        }
        if (null != DataManager.getInstance().getDataFromManager(DataManager.PLOT_INTER_CROP_DATA) && !cropModelList.isEmpty()) {
            interCropBtn.setBackgroundColor(getResources().getColor(R.color.gray));
        } else {
            interCropBtn.setBackground(getResources().getDrawable(R.drawable.rounded_btn));
        }
        if (null != DataManager.getInstance().getDataFromManager(DataManager.PLANTATION_CON_DATA)) {
            plantationdetailsBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        }
        if (null != DataManager.getInstance().getDataFromManager(DataManager.PLOT_GEO_TAG)) {
            plotGeoTagBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        }

        if (ConversionDigitalContractFragment.isContractAgreed) {
            digitalcontractBtn.setBackgroundColor(getResources().getColor(R.color.green_dark));
        }

        if (null != DataManager.getInstance().getDataFromManager(DataManager.SOURCE_OF_WATER)
                || null != DataManager.getInstance().getDataFromManager(DataManager.SoilType)) {
            wspBtn.setBackgroundColor(getResources().getColor(R.color.gray));
        }

        if (null != DataManager.getInstance().getDataFromManager(DataManager.REFERRALS_DATA)) {
            referalsBtn.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    @Override
    public void onTypeSelected(int type) {
        AreaWaterTypeFragment areaWaterTypeFragment = new AreaWaterTypeFragment();
        areaWaterTypeFragment.setUpdateUiListener(this);
        SoilTypeFragment soilTypeFragment = new SoilTypeFragment();
        soilTypeFragment.setUpdateUiListener(this);
        replaceFragment(type == 1 ? areaWaterTypeFragment : soilTypeFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        uiRefresh();
    }

 /*   @Override
    public void onBackPressed() {
        super.onBackPressed();
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            uiRefresh();
        }
    }*/


    //What should happen on back click
    @Override
    public void onBackPressed() {
      List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            uiRefresh();
        }
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count > 0) {
            fm.popBackStack();
        } else {
            if (doubleback) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finish();
//                    finishAffinity();
                }
            } else {
                doubleback = true;
                // custom dialog
                final Dialog dialog = new Dialog(ConversionMainFlowActivity.this);
                dialog.setContentView(R.layout.custom_alert_dailog);

                Button yesDialogButton = dialog.findViewById(R.id.Yes);
                Button noDialogButton = dialog.findViewById(R.id.No);
                TextView msg = dialog.findViewById(R.id.test);
                msg.setText("If You Want to Exit Click Yes,but The Data Will Clear.....");
                // if button is clicked, close the custom dialog
                yesDialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
//                        Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                noDialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
//                        Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleback = false;
                    }
                }, 2000);
            }
        }
    }
}
