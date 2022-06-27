package com.oilpalm3f.mainapp.cropmaintenance;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.UpdateUiListener;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.Harvest;
import com.oilpalm3f.mainapp.dbmodels.PlotIrrigationTypeXref;
import com.oilpalm3f.mainapp.dbmodels.Uprootment;
import com.oilpalm3f.mainapp.ui.BaseFragment;
import com.oilpalm3f.mainapp.utils.UiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

import static com.oilpalm3f.mainapp.common.CommonUtils.isFromConversion;
import static com.oilpalm3f.mainapp.common.CommonUtils.isFromCropMaintenance;
import static com.oilpalm3f.mainapp.common.CommonUtils.isFromFollowUp;
import static com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation.getKey;
import static com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation.getvalueFromHashMap;


//Used to enter Harvest Details during crop maintenance
public class FFB_HarvestDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = FFB_HarvestDetailsFragment.class.getName();
    private View rootView;
    private Spinner preferredSpin, modeOfTransportSpin, harvestingMethodSpin,consractperMethodSpin, typeOfHarvestSpin, vehicleTypeSpin;
    private EditText ammountPaidforTrans, wagesperDayEdt, consractperMTEdt, consractperANUMEdt, commentsEdt;
    private LinearLayout wagesperDayLay, contractperMTLay, consractperANUMLay, parentLayout;
    private TextView yieldPerHactor, yieldPerPlot,ExpectedYield;
    private Button saveBtn;
    private Context mContext;
    private LinkedHashMap<String, String> modeOfTransportDataMap,ContractUnitperwagesMethodDataMap, harvestingMethodDataMap, typeOfHarvestDataMap, preferredDataMap, vehicleTypeMap;
    private DataAccessHandler dataAccessHandler;
    private int pitchyes, consentyes;
    private boolean isEditMode = false;
    private int ffbHarvestingId;
    private Harvest mHarvest;
    private String yieldPerHactorStr, yieldPerPlotStr,ExpectedYieldStr;
    private UpdateUiListener updateUiListener;
    private  String Fincmonth;
    private static int Fincyear;
    private  Toolbar toolbar;
    private ActionBar actionBar;

    private Button historyBtn;
    private ArrayList<Harvest> harvestlastvisitdatamap;


    public FFB_HarvestDetailsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ffb__harvest_details, container,false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.ffb_harvesting));

        mContext = getActivity();
        initViews();
        setViews();
        //bindData();

        return rootView;
    }

    private void bindData() {

        DataManager.getInstance().deleteData(DataManager.FFB_HARVEST_DETAILS);

        mHarvest = (Harvest) DataManager.getInstance().getDataFromManager(DataManager.FFB_HARVEST_DETAILS);
        if (mHarvest == null) {
            mHarvest = (Harvest) dataAccessHandler.getHarvestData(Queries.getInstance().getHarvestBinding(CommonConstants.PLOT_CODE), 0);
        }

        if (mHarvest != null) {
            preferredSpin.setSelection(mHarvest.getCollectioncenterid() == null ? 0 : getvalueFromHashMap(preferredDataMap, mHarvest.getCollectioncenterid()));
            modeOfTransportSpin.setSelection(mHarvest.getTransportmodetypeid() == null ? 0 : getvalueFromHashMap(modeOfTransportDataMap, mHarvest.getTransportmodetypeid()));
            vehicleTypeSpin.setSelection(mHarvest.getVehicletypeid() == null ? 0 : CommonUtilsNavigation.getvalueFromHashMap(vehicleTypeMap, mHarvest.getVehicletypeid()));
            ammountPaidforTrans.setText("" + mHarvest.getTransportpaidamount());
            harvestingMethodSpin.setSelection(mHarvest.getHarvestingmethodtypeid() == null ? 0 : getvalueFromHashMap(harvestingMethodDataMap, mHarvest.getHarvestingmethodtypeid()));
            consractperMethodSpin.setSelection(mHarvest.getWagesUnitTypeId() == null ? 0 : getvalueFromHashMap(ContractUnitperwagesMethodDataMap, mHarvest.getWagesUnitTypeId()));
            wagesperDayEdt.setText("" + mHarvest.getWagesperday());
            if (mHarvest.getWagesperday() !=0 ){
                wagesperDayLay.setVisibility(View.VISIBLE);
                contractperMTLay.setVisibility(View.GONE);
                consractperANUMLay.setVisibility(View.GONE);
            }else {
                wagesperDayLay.setVisibility(View.GONE);
                contractperMTLay.setVisibility(View.VISIBLE);
                consractperANUMLay.setVisibility(View.GONE);
            }
           // consractperMTEdt.setText("" + mHarvest.getContractrupeespermonth());
            consractperANUMEdt.setText("" + mHarvest.getContractAmount());
            typeOfHarvestSpin.setSelection(mHarvest.getHarvestingtypeid() == null ? 0 : getvalueFromHashMap(typeOfHarvestDataMap, mHarvest.getHarvestingtypeid()));
            commentsEdt.setText("" + mHarvest.getComments());

        }
    }

    private void initViews() {
        dataAccessHandler = new DataAccessHandler(getActivity());
        preferredSpin = (Spinner) rootView.findViewById(R.id.preferredSpin);
        modeOfTransportSpin = (Spinner) rootView.findViewById(R.id.modeOfTransportSpin);
        harvestingMethodSpin = (Spinner) rootView.findViewById(R.id.harvestingMethodSpin);
        consractperMethodSpin = (Spinner) rootView.findViewById(R.id.consractperspiner);
        typeOfHarvestSpin = (Spinner) rootView.findViewById(R.id.typeOfHarvestSpin);
        wagesperDayEdt = (EditText) rootView.findViewById(R.id.wagesperDayEdt);
        vehicleTypeSpin = (Spinner) rootView.findViewById(R.id.vehicleTypeSpin);
        ammountPaidforTrans = (EditText) rootView.findViewById(R.id.ammountPaidforTrans);
//        consractperMTEdt = (EditText) rootView.findViewById(R.id.consractperMTEdt);
        consractperANUMEdt = (EditText) rootView.findViewById(R.id.consractperANUMEdt);
        wagesperDayLay = (LinearLayout) rootView.findViewById(R.id.wagesperDayLay);
        contractperMTLay = (LinearLayout) rootView.findViewById(R.id.contractperMTLay);
        consractperANUMLay = (LinearLayout) rootView.findViewById(R.id.consractperANUMLay);
        yieldPerHactor = (TextView) rootView.findViewById(R.id.yieldPerHactor);
        ExpectedYield = (TextView) rootView.findViewById(R.id.ExpectedYield);
        yieldPerPlot = (TextView) rootView.findViewById(R.id.yfp);
        saveBtn = (Button) rootView.findViewById(R.id.saveBtn);
        commentsEdt = (EditText) rootView.findViewById(R.id.FFBCommentsEdt);
        parentLayout = (LinearLayout) rootView.findViewById(R.id.FFBparentLayout);

        historyBtn = (Button) rootView.findViewById(R.id.harvestlastvisitdataBtn);

        vehicleTypeMap = dataAccessHandler.getGenericData(Queries.getInstance().getVehicleTypeQuery());
        vehicleTypeSpin.setAdapter(UiUtils.createAdapter(getActivity(), vehicleTypeMap, "Vehicle Type"));

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setViews() {

        String[]monthName={"January","February","March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        Date referenceDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(referenceDate);
        c.add(Calendar.MONTH, -12);

        Fincmonth=monthName[c.get(Calendar.MONTH)];
        displayFinancialDate(Calendar.getInstance());

        parentLayout.setOnTouchListener((view, motionEvent) -> {
            CommonUtilsNavigation.hideKeyBoard(getActivity());
            return false;
        });
        saveBtn.setOnClickListener(this);
        String dateFormatter = "yyyy-MM-dd";
        SimpleDateFormat sdf2 = new SimpleDateFormat(dateFormatter, Locale.US);
        String fromDate = sdf2.format(c.getTime());

        String currentDate = CommonUtils.getcurrentDateTime(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY));
        Log.v(LOG_TAG, " Current date"+currentDate);

        String data = dataAccessHandler.getTwoValues(Queries.getInstance().getYieldQuery(fromDate, currentDate));

        // calculate plotage and plotLocation
        String plotAgeandStateId = dataAccessHandler.getplotAgeandSateId(Queries.getInstance().plotAgeAndPlotLocation(CommonConstants.PLOT_CODE,currentDate));

        String[] PlotAgeStateIdArr = plotAgeandStateId.split("-");
        Log.v(LOG_TAG, "plotAgeAndStateId "+PlotAgeStateIdArr[0]+"  StateId"+PlotAgeStateIdArr[1]);
        String YPHvalue  = dataAccessHandler.getYPHvaluefromBenchMark(Queries.getInstance().CalculateExpectedYield(Fincyear, PlotAgeStateIdArr[0],PlotAgeStateIdArr[1],Fincmonth));
        Log.v(LOG_TAG, "@@@@ month "+ YPHvalue);
        if (!TextUtils.isEmpty(String.valueOf(YPHvalue)) && !(YPHvalue == null)) {
            ExpectedYieldStr = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().ExpectedYield(CommonConstants.PLOT_CODE, YPHvalue));

        } else {
           ExpectedYieldStr = null;
            Toast.makeText(getActivity(),"the current plot age is less than 3",Toast.LENGTH_LONG).show();

         }

        if (!TextUtils.isEmpty(String.valueOf(ExpectedYieldStr)) && !String.valueOf(ExpectedYieldStr).equalsIgnoreCase("null")) {
//            ExpectedYieldStr = String.valueOf(Expectedyield);
            ExpectedYield.setText(" "+ExpectedYieldStr +" Kgs");
        }
          else {
            ExpectedYieldStr = "0";
            ExpectedYield.setText("0 "+" Kgs");
        }

      Log.v(LOG_TAG, "@@@@ data "+data);

        String[] yieldDataArr = data.split("-");

        if (yieldDataArr.length > 0) {
            if (!TextUtils.isEmpty(yieldDataArr[0]) && !yieldDataArr[0].equalsIgnoreCase("null")) {
                yieldPerPlot.setText(""+yieldDataArr[0] +" Kgs");
            } else {
                yieldPerPlot.setText("0");
            }
            if (!TextUtils.isEmpty(yieldDataArr[1]) && !yieldDataArr[1].equalsIgnoreCase("null")) {
                yieldPerHactorStr = yieldDataArr[1];
                yieldPerHactor.setText(""+yieldDataArr[1] +" Kgs");
            } else {
                yieldPerHactorStr = "0";
                yieldPerHactor.setText("0");
            }
        }

        preferredDataMap = dataAccessHandler.getGenericData("select Id,Name from CollectionCenter");
        harvestingMethodDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("17"));
        ContractUnitperwagesMethodDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("49"));
        modeOfTransportDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("18"));
        typeOfHarvestDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("20"));

        preferredSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Mode of transport", preferredDataMap));
        modeOfTransportSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Mode of transport", modeOfTransportDataMap));
        harvestingMethodSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Harvest Method", harvestingMethodDataMap));
        consractperMethodSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Unit Per Wages", ContractUnitperwagesMethodDataMap));
        typeOfHarvestSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Type of harvesting", typeOfHarvestDataMap));

        harvestingMethodSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    wagesperDayLay.setVisibility(View.GONE);
                    contractperMTLay.setVisibility(View.GONE);
                    consractperANUMLay.setVisibility(View.GONE);
                } else if (position == 1) {
                    wagesperDayLay.setVisibility(View.VISIBLE);
                    contractperMTLay.setVisibility(View.GONE);
                    consractperANUMLay.setVisibility(View.GONE);
                } else if (position == 2) {
                    wagesperDayLay.setVisibility(View.GONE);
                    contractperMTLay.setVisibility(View.VISIBLE);
                    consractperANUMLay.setVisibility(View.GONE);
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        consractperMethodSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    wagesperDayLay.setVisibility(View.GONE);
                    contractperMTLay.setVisibility(View.VISIBLE);
                    consractperANUMLay.setVisibility(View.GONE);
                } else if (position == 1) {
                    wagesperDayLay.setVisibility(View.GONE);
                    contractperMTLay.setVisibility(View.VISIBLE);
                    consractperANUMLay.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    wagesperDayLay.setVisibility(View.GONE);
                    contractperMTLay.setVisibility(View.VISIBLE);
                    consractperANUMLay.setVisibility(View.VISIBLE);
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(getContext());
            }
        });

    }

    public void showDialog(Context activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.harvestlastvisteddata);

        Toolbar titleToolbar;
        titleToolbar = (Toolbar) dialog.findViewById(R.id.titleToolbar);
        titleToolbar.setTitle("FFB Harvest History");
        titleToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        LinearLayout harvestcommentsll = (LinearLayout) dialog.findViewById(R.id.harvestcommentsll);
        LinearLayout typeofvehiclell = (LinearLayout) dialog.findViewById(R.id.typeofvehiclell);
        LinearLayout amountpaidll = (LinearLayout) dialog.findViewById(R.id.amountpaidll);
        LinearLayout wagesperDayLay = (LinearLayout) dialog.findViewById(R.id.wagesperDayLay);
        LinearLayout untiforwagesll = (LinearLayout) dialog.findViewById(R.id.untiforwagesll);
        LinearLayout consractperANUMLay = (LinearLayout) dialog.findViewById(R.id.consractperANUMLay);
        LinearLayout mainLL = (LinearLayout) dialog.findViewById(R.id.harvestmainlyt);


        TextView harvestpreferredccmill = (TextView) dialog.findViewById(R.id.harvestpreferredccmill);
        TextView harvestmodeoftransport = (TextView) dialog.findViewById(R.id.harvestmodeoftransport);
        TextView harvestvehicletype = (TextView) dialog.findViewById(R.id.harvestvehicletype);
        TextView harvestamountpaidforTrans = (TextView) dialog.findViewById(R.id.harvestamountpaidforTrans);

        TextView harvestharvestingmethod = (TextView) dialog.findViewById(R.id.harvestharvestingmethod);
        TextView harvestwagesperday = (TextView) dialog.findViewById(R.id.harvestwagesperday);
        TextView harvestunitforwages = (TextView) dialog.findViewById(R.id.harvestunitforwages);
        TextView harvestcontractamount = (TextView) dialog.findViewById(R.id.harvestcontractamount);
        TextView harvesttypeofharvesting = (TextView) dialog.findViewById(R.id.harvesttypeofharvesting);


        TextView comments = (TextView) dialog.findViewById(R.id.harvestcomments_tv);
        TextView norecords = (TextView) dialog.findViewById(R.id.norecord_tv);

        String lastVisitCode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getLatestCropMaintanaceHistoryCode(CommonConstants.PLOT_CODE));
        harvestlastvisitdatamap = (ArrayList<Harvest>) dataAccessHandler.getHarvestData(Queries.getInstance().getRecommndCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_HARVEST), 1);

        if (harvestlastvisitdatamap.size() > 0){
            norecords.setVisibility(View.GONE);
            mainLL.setVisibility(View.VISIBLE);


            String preferredcc = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getCCdata(harvestlastvisitdatamap.get(0).getCollectioncenterid()));
            String modeoftransportstr = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(harvestlastvisitdatamap.get(0).getTransportmodetypeid()));
            String harvestingmethod = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(harvestlastvisitdatamap.get(0).getHarvestingmethodtypeid()));
            String harvestingtype = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(harvestlastvisitdatamap.get(0).getHarvestingtypeid()));



            harvestpreferredccmill.setText(preferredcc);
            harvestmodeoftransport.setText(modeoftransportstr);
            harvestharvestingmethod.setText(harvestingmethod);
            harvesttypeofharvesting.setText(harvestingtype);

            if (harvestlastvisitdatamap.get(0).getHarvestingmethodtypeid() == 99){

                String wagesunit = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(harvestlastvisitdatamap.get(0).getWagesUnitTypeId()));

                untiforwagesll.setVisibility(View.VISIBLE);
                consractperANUMLay.setVisibility(View.VISIBLE);
                wagesperDayLay.setVisibility(View.GONE);
                harvestunitforwages.setText(wagesunit);
                harvestcontractamount.setText(harvestlastvisitdatamap.get(0).getContractAmount() + "");

            }else{

                untiforwagesll.setVisibility(View.GONE);
                consractperANUMLay.setVisibility(View.GONE);
                wagesperDayLay.setVisibility(View.VISIBLE);
                harvestwagesperday.setText(harvestlastvisitdatamap.get(0).getWagesperday() + "");
            }

            if (harvestlastvisitdatamap.get(0).getVehicletypeid() == null){
                typeofvehiclell.setVisibility(View.GONE);
            }else{
                typeofvehiclell.setVisibility(View.VISIBLE);
                String vehicleType = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(harvestlastvisitdatamap.get(0).getVehicletypeid()));
                harvestvehicletype.setText(vehicleType);
            }

            if (String.valueOf(harvestlastvisitdatamap.get(0).getTransportpaidamount()) == null){
                amountpaidll.setVisibility(View.GONE);
            }else{
                amountpaidll.setVisibility(View.VISIBLE);
                harvestamountpaidforTrans.setText(harvestlastvisitdatamap.get(0).getTransportpaidamount() + "");
            }


//            Log.d("Comments", harvestlastvisitdatamap.get(0).getComments() + "");
//
//            if (harvestlastvisitdatamap.get(0).getComments() != null){
//                 harvestcommentsll.setVisibility(View.VISIBLE);
//                comments.setText(harvestlastvisitdatamap.get(0).getComments() + "");
//            }else{
//                harvestcommentsll.setVisibility(View.GONE);
//            }

            if (TextUtils.isEmpty(harvestlastvisitdatamap.get(0).getComments()) || harvestlastvisitdatamap.get(0).getComments().equalsIgnoreCase("")  || harvestlastvisitdatamap.get(0).getComments().equalsIgnoreCase(null) || harvestlastvisitdatamap.get(0).getComments().equalsIgnoreCase("null")){
                harvestcommentsll.setVisibility(View.GONE);
            }
            else{
                harvestcommentsll.setVisibility(View.VISIBLE);
                comments.setText(harvestlastvisitdatamap.get(0).getComments() + "");
            }

        }else{
            mainLL.setVisibility(View.GONE);
            norecords.setVisibility(View.VISIBLE);
        }



        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 500);
    }


    private static void displayFinancialDate(Calendar calendar) {
        FiscalDate fiscalDate = new FiscalDate(calendar);
        Fincyear = fiscalDate.getFiscalYear();
        System.out.println("Current Date : " + calendar.getTime().toString());
        System.out.println("Fiscal Years : " + Fincyear + "-" + (Fincyear + 1));
        System.out.println("Fiscal Month : " + fiscalDate.getFiscalMonth());
        Log.v(LOG_TAG, "Fiscal Years : " + Fincyear );

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                if (validateFields()) {
                    mHarvest = new Harvest();
                    mHarvest.setYieldperhactor(TextUtils.isEmpty(yieldPerHactorStr) ? 0 : Double.parseDouble(yieldPerHactorStr));
                    mHarvest.setCollectioncenterid(preferredSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(preferredDataMap, preferredSpin.getSelectedItem().toString())));
                    mHarvest.setTransportmodetypeid(modeOfTransportSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(modeOfTransportDataMap, modeOfTransportSpin.getSelectedItem().toString())));
                    Integer vehid = vehicleTypeSpin.getSelectedItemPosition() == 0 ? null :  Integer.parseInt(CommonUtils.getKeyFromValue(vehicleTypeMap, vehicleTypeSpin.getSelectedItem().toString()));
                    mHarvest.setVehicletypeid(vehid);
                    mHarvest.setTransportpaidamount(ammountPaidforTrans.getText().toString().equalsIgnoreCase("") ? 0 : CommonUtils.convertToBigNumber(ammountPaidforTrans.getText().toString()));
                    mHarvest.setHarvestingmethodtypeid(harvestingMethodSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(harvestingMethodDataMap, harvestingMethodSpin.getSelectedItem().toString())));
                    mHarvest.setWagesUnitTypeId(consractperMethodSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(ContractUnitperwagesMethodDataMap, consractperMethodSpin.getSelectedItem().toString())));
                    mHarvest.setWagesperday(!wagesperDayEdt.isShown() ? 0.0 : Double.parseDouble(wagesperDayEdt.getText().toString()));
                    mHarvest.setContractAmount(!consractperANUMEdt.isShown() ? 0.0 :Double.parseDouble(consractperANUMEdt.getText().toString()));
                    mHarvest.setHarvestingtypeid(typeOfHarvestSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(typeOfHarvestDataMap, typeOfHarvestSpin.getSelectedItem().toString())));
                    mHarvest.setComments(commentsEdt.getText().toString());
                    DataManager.getInstance().addData(DataManager.FFB_HARVEST_DETAILS, mHarvest);
                    getFragmentManager().popBackStack();
                    updateUiListener.updateUserInterface(0);
                }
                CommonUtilsNavigation.hideKeyBoard(getActivity());
                break;
        }
    }

    public boolean validateFields() {
        return CommonUtilsNavigation.spinnerSelect("Prefered Collection", preferredSpin.getSelectedItemPosition(), getActivity()) &&
                CommonUtilsNavigation.spinnerSelect("Mode of Transport", modeOfTransportSpin.getSelectedItemPosition(), getActivity()) &&
                CommonUtilsNavigation.spinnerSelect("Harvest method", harvestingMethodSpin.getSelectedItemPosition(), getActivity()) &&
                (!consractperMethodSpin.isShown() || CommonUtilsNavigation.spinnerSelect("Unit per Wages method", consractperMethodSpin.getSelectedItemPosition(), getActivity())) &&
                CommonUtilsNavigation.spinnerSelect("Harvest type", typeOfHarvestSpin.getSelectedItemPosition(), getActivity()) &&
                (!wagesperDayEdt.isShown() || CommonUtilsNavigation.edittextSelect(mContext, wagesperDayEdt, "Wages/Day")) &&
                (!consractperANUMEdt.isShown() || CommonUtilsNavigation.edittextSelect(mContext, consractperANUMEdt, "Contract/Year"));

    }

    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }
}
