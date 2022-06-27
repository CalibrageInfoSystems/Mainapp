package com.oilpalm3f.mainapp.areaextension;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.common.FiscalDate;
import com.oilpalm3f.mainapp.conversion.IdProofsListAdapter;
import com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.Address;
import com.oilpalm3f.mainapp.dbmodels.LandlordBank;
import com.oilpalm3f.mainapp.dbmodels.LandlordIdProof;
import com.oilpalm3f.mainapp.dbmodels.NeighbourPlot;
import com.oilpalm3f.mainapp.dbmodels.Plot;
import com.oilpalm3f.mainapp.dbmodels.PlotCurrentCrop;
import com.oilpalm3f.mainapp.dbmodels.PlotLandlord;
import com.oilpalm3f.mainapp.farmersearch.DisplayPlotsFragment;
import com.oilpalm3f.mainapp.dbmodels.CropModel;
import com.oilpalm3f.mainapp.uihelper.InteractiveScrollView;
import com.oilpalm3f.mainapp.utils.DateFormats;
import com.oilpalm3f.mainapp.utils.UiUtils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


//Fill Plot Details Fragment
public class PlotDetailsFragment extends Fragment implements MultiEntryDialogFragment.onDataSelectedListener, GenericListItemClickListener, EditEntryDialogFragment.OnDataEditChangeListener, IdProofsListAdapter.idProofsClickListener, LandLordIdProofListAdapter.idProofsClickListener {

    public static final int NEIGHBOUR_PLOT_TYPE = 1;
    public static final int CURRENT_CROP_TYPE = 0;
    private static final String LOG_TAG = PlotDetailsFragment.class.getName();
    public static double currentCropArea = 0.0;
    public List<CropModel> currentCropList, neighbourPlotsList;
    public List<Pair<String, String>> dataPair = new ArrayList<>();
    public int statePos, districtPos, mandalPos, villagePos;
    protected boolean userStateSelect = true, userDisSelect = true, userManSelect = true, userVillageSelect = true;
    private Plot plot = new Plot();
    private Spinner regionSpinner, stateSpinner, districtSpinner, mandalSpinner,
            villageSpinner, properBoundryFencingSpinner, ownerShipSpin, incomeSpin,
            careTakerSpinner, bankDetailsSpin, branchNameSpin, idproof, landTypeSpn;
    private EditText plotareaedt, surveyNoedt, adangalNoedt,areaUnderSuitablePalmEdit;
    private EditText landmarkedt,plotaddress, careTakerNameEdit, careTakerNumberEdit,pincode;
    private android.widget.TextView stateTxt, ClusterNameTv;
    private String plotcodeStr, totalplotareaStr, surveyNoedtStr, adangalNoedtStr,
            landmarkStr, plotaddressStr, properboundaryfencingStr, ownershipStr;
    private RecyclerView currentCropRecyclerView;
    private ImageView addRowImg;
    private RecyclerView neighbourPlotRecyclerView;
    private ImageView addRowImgNbPlot;
    private Button farmerSaveBtn;
    private LinearLayout parent, area_suitable_palm, currentCropsLayout, incomeLayout;
    private com.oilpalm3f.mainapp.uihelper.InteractiveScrollView scrollView;
    private ImageView bottomScroll;
    private View rootView;
    private DataAccessHandler dataAccessHandler;
    private LinkedHashMap<String, Pair> stateDataMap = null, districtDataMap, mandalDataMap, villagesDataMap;
    private String villageCodeStr = "";
    private GenericAdapter genericAdapter;
    private SingleItemAdapter neighbourPlotAdapter;
    private int selectedPosition;
    private int selectedType;
    private TextView plotCodeTxt;
    private LinkedHashMap ownerShipMap, incomeDataMap,landTypeMap;
    private List<Pair> neighbourPlotPair = new ArrayList<>();
    private Address savedAddressData = new Address();
    private boolean isUpdateData;
    private UpdateUiListener updateUiListener;
    private String globalpincode = "";
    private LinkedHashMap<String, String> bankCodeDataMap;
    private LinkedHashMap<String, String> branchNameDataMap;
    private LinkedHashMap<String, String> idProofsData, mainData;
    private List<LandlordIdProof> identityProofsList;
    private LinkedHashMap<String, String> cropDataMap;
    private Calendar myCalendar = Calendar.getInstance();
    private LinearLayout landLordLayout, converstionSurveyLayout, careTakerlayout, careTakerEntrylayout, idprooflayout;
    private EditText LLHolderName, LLMobileNo, LLAccountName, LLAccountNumber, LLIFSCcode, LLleaseDate, LLleaseEndDate, totalAreaHorticulture;
    private String LLHolderNameStr, LLMobileNoStr, LLAccountNameStr, LLIFSCcodeStr, LLAccountNumberStr, LLleaseDateStr, LLleaseEndDateStr, bankdetailnameStr, branchnameStr, aadharStr;
    private ImageView addIDProof;

    private LandLordIdProofListAdapter idProofsListAdapter;
    private RecyclerView idProofsRecyclerView;
    private LandlordBank landlordBank = null;
    private PlotLandlord plotLandLord = null;
    private AlertDialog alert;
    private int leaseActiveValue;
    private boolean leased;
    private boolean picker;
    private List<String> mandalIds, districtIds, stateIds, userVillages;
    private String plotAreaStr, totalAreaStr;
    private double plotArea = 0.0, totalArea = 0.0;
    private int checkUpdateValueBank = 0, checkUpdateValueBranch = 0;
    private boolean fromDataBase = false;
    public int financialYear;
    private String days = "";
    private String financalSubStringYear = "";
    public static String financalYrDays = "";


    public PlotDetailsFragment() {

    }

    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Creating the View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_plot_details, container, false);

        dataAccessHandler = new DataAccessHandler(getActivity());
        CommonConstants.PLOT_CODE = DisplayPlotsFragment.plotCode;

        initUI();
        bindGeoGraphicalLocation();
        handleAddressData();

        cropDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getCropsMasterInfo());
        genericAdapter = new GenericAdapter(getActivity());
        genericAdapter.setGenericListItemClickListener(PlotDetailsFragment.this);
        currentCropList = new ArrayList<>();
        neighbourPlotsList = new ArrayList<>();

        neighbourPlotAdapter = new SingleItemAdapter();
        neighbourPlotRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        neighbourPlotAdapter.setEditClickListener(this);
        neighbourPlotRecyclerView.setAdapter(neighbourPlotAdapter);
        plot = (Plot) DataManager.getInstance().getDataFromManager(DataManager.PLOT_DETAILS);


        if ((CommonUtils.isFromFollowUp() || CommonUtils.isFromConversion() || CommonUtils.isFromCropMaintenance())) {
            Log.v(LOG_TAG, "#### here plot code already generated and saved in database");
            fromDataBase = true;
        }

        if (fromDataBase && plot == null) {
            Log.v(LOG_TAG, "@@@@@ getting data from database");
            plot = (Plot) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getSelectedPlot(CommonConstants.PLOT_CODE), 0);
            Address selectedPlotAddress = (Address) dataAccessHandler.getSelectedFarmerAddress(Queries.getInstance().getSelectedPlotAddress(plot.getAddesscode()), 0);
            List<PlotCurrentCrop> selectedPlotCurrentCrop = (List<PlotCurrentCrop>) dataAccessHandler.getSelectedPlotCurrentCropData(Queries.getInstance().getSelectedPlotCurrentCrop(plot.getCode()), 1);
            List<NeighbourPlot> selectedNeighbourPlotData = (List<NeighbourPlot>) dataAccessHandler.getSelectedNeighbourPlotData(Queries.getInstance().getSelectedNeighbourPlot(plot.getCode()), 1);
            if (null != plot) {
                isUpdateData = true;
                DataManager.getInstance().addData(DataManager.PLOT_DETAILS, plot);
                DataManager.getInstance().addData(DataManager.PLOT_ADDRESS_DETAILS, selectedPlotAddress);
                DataManager.getInstance().addData(DataManager.PLOT_CURRENT_CROPS_DATA, convertToCurrentCropModel(selectedPlotCurrentCrop));
                DataManager.getInstance().addData(DataManager.PLOT_NEIGHBOURING_PLOTS_DATA, convertToCropModel(selectedNeighbourPlotData));
                DataManager.getInstance().addData(DataManager.PLOT_CURRENT_CROPS_DATA_PAIR, dataPair);
                DataManager.getInstance().addData(DataManager.PLOT_NEIGHBOURING_PLOTS_DATA_PAIR, neighbourPlotPair);
                handleViewEnableAndDisable();
                bindData();
            } else {
                UiUtils.showCustomToastMessage("Error while getting local plot data", getActivity(), 1);
            }
        } else if (plot != null) {
            isUpdateData = true;
            bindData();
            landlordBank = (LandlordBank) DataManager.getInstance().getDataFromManager(DataManager.LANDLORD_BANK_DATA);
            if (null != landlordBank) {
                bindLandLordBankData();
            }

            plotLandLord = (PlotLandlord) DataManager.getInstance().getDataFromManager(DataManager.LANDLORD_LEASED_DATA);
            if (null != plotLandLord) {
                bindLandLordPersonalData();
            }
        }

        if (fromDataBase) {
            handleViewEnableAndDisable();
        }

        if (CommonUtils.isFromCropMaintenance() && landlordBank == null) {
            landlordBank = (LandlordBank) dataAccessHandler.getLandLordBankData(Queries.getInstance().queryLandLordBankData(CommonConstants.PLOT_CODE), 0);
            plotLandLord = (PlotLandlord) dataAccessHandler.getPlotLandLordData(Queries.getInstance().queryPlotLandlordData(CommonConstants.PLOT_CODE), 0);
        }

        return rootView;
    }


    //Enable/Disable the fields
    private void handleViewEnableAndDisable() {

        plotareaedt.setEnabled(false);
        plotareaedt.setCursorVisible(false);
        plotareaedt.setClickable(false);
        plotareaedt.setFocusable(false);
//        landmarkedt.setEnabled(false);
//        landmarkedt.setFocusable(false);
//        plotaddress.setEnabled(false);
//        plotaddress.setFocusable(false);

        LLIFSCcode.setEnabled(false);
        LLIFSCcode.setFocusable(false);

    }

    //Binding Data to Spinners & fields
    @SuppressLint("SetTextI18n")
    public void bindData() {
        if (fromDataBase) {
            stateSpinner.setEnabled(false);
            stateSpinner.setFocusable(false);
            districtSpinner.setEnabled(false);
            districtSpinner.setFocusable(false);
            mandalSpinner.setEnabled(false);
            mandalSpinner.setFocusable(false);
            villageSpinner.setEnabled(false);
            villageSpinner.setFocusable(false);
        }

        savedAddressData = (Address) DataManager.getInstance().getDataFromManager(DataManager.PLOT_ADDRESS_DETAILS);
        if (isUpdateData && savedAddressData == null) {
            savedAddressData = (Address) dataAccessHandler.getSelectedFarmerAddress(Queries.getInstance().getSelectedPlotAddress(plot.getAddesscode()), 0);
            DataManager.getInstance().addData(DataManager.PLOT_ADDRESS_DETAILS, savedAddressData);
            List<PlotCurrentCrop> selectedPlotCurrentCrop = (List<PlotCurrentCrop>) dataAccessHandler.getSelectedPlotCurrentCropData(Queries.getInstance().getSelectedPlotCurrentCrop(plot.getCode()), 1);
            List<NeighbourPlot> selectedNeighbourPlotData = (List<NeighbourPlot>) dataAccessHandler.getSelectedNeighbourPlotData(Queries.getInstance().getSelectedNeighbourPlot(plot.getCode()), 1);
            DataManager.getInstance().addData(DataManager.PLOT_CURRENT_CROPS_DATA, convertToCurrentCropModel(selectedPlotCurrentCrop));
            DataManager.getInstance().addData(DataManager.PLOT_NEIGHBOURING_PLOTS_DATA, convertToCropModel(selectedNeighbourPlotData));
            DataManager.getInstance().addData(DataManager.PLOT_CURRENT_CROPS_DATA_PAIR, dataPair);
            DataManager.getInstance().addData(DataManager.PLOT_NEIGHBOURING_PLOTS_DATA_PAIR, neighbourPlotPair);
        }

        if (null != savedAddressData) {
            CommonConstants.statePlotId = String.valueOf(savedAddressData.getStateid());
            CommonConstants.districtIdPlot = String.valueOf(savedAddressData.getDistictid());
            CommonConstants.mandalIdPlot = String.valueOf(savedAddressData.getMandalid());
            CommonConstants.villageIdPlot = String.valueOf(savedAddressData.getVillageid());

            districtDataMap = dataAccessHandler.getPairData(Queries.getInstance().getUserDistricts(TextUtils.join(",", districtIds), CommonConstants.statePlotId));
            mandalDataMap = dataAccessHandler.getPairData(Queries.getInstance().getUserMandals(TextUtils.join(",", mandalIds), (TextUtils.join(",", districtIds))));
            villagesDataMap = dataAccessHandler.getPairData(Queries.getInstance().getUserVillages(TextUtils.join(",", userVillages), (TextUtils.join(",", mandalIds))));

            statePos = CommonUtils.getIndex(stateDataMap.keySet(), String.valueOf(savedAddressData.getStateid())) + 1;
            districtPos = CommonUtils.getIndex(districtDataMap.keySet(), String.valueOf(savedAddressData.getDistictid())) + 1;
            mandalPos = CommonUtils.getIndex(mandalDataMap.keySet(), String.valueOf(savedAddressData.getMandalid())) + 1;
            villagePos = CommonUtils.getIndex(villagesDataMap.keySet(), String.valueOf(savedAddressData.getVillageid())) + 1;
            stateSpinner.setSelection(statePos);

            ArrayAdapter<String> spinnerDistrictArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(districtDataMap, "District"));
            spinnerDistrictArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            districtSpinner.setAdapter(spinnerDistrictArrayAdapter);
            CommonConstants.stateName = stateSpinner.getSelectedItem().toString();
            Log.v("@@@state",""+CommonConstants.statePlotId+" "+stateDataMap.size());
            Pair statePair = stateDataMap.get(CommonConstants.statePlotId);

            CommonConstants.stateCodePlot = statePair.first.toString();
            districtSpinner.setSelection(districtPos);

            Log.v(LOG_TAG, "@@@ Selected State " + CommonConstants.districtIdPlot);
            mandalDataMap = dataAccessHandler.getPairData(Queries.getInstance().getUserMandals(TextUtils.join(",", mandalIds), TextUtils.join(",", districtIds)));
            ArrayAdapter<String> spinnerMandalArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(mandalDataMap, "Mandal"));
            spinnerMandalArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mandalSpinner.setAdapter(spinnerMandalArrayAdapter);
            CommonConstants.districtName = districtSpinner.getSelectedItem().toString();
            Pair districtPair = districtDataMap.get(CommonConstants.districtIdPlot);
            CommonConstants.districtCodePlot = districtPair.first.toString();
            mandalSpinner.setSelection(mandalPos);

            Log.v(LOG_TAG, "@@@ Selected State " + CommonConstants.mandalIdPlot);
            CommonConstants.mandalName = mandalSpinner.getSelectedItem().toString();
            Pair mandalPair = mandalDataMap.get(CommonConstants.mandalIdPlot);
            CommonConstants.mandalCodePlot = mandalPair.first.toString();
            villagesDataMap = dataAccessHandler.getPairData(Queries.getInstance().getUserVillages(TextUtils.join(",", userVillages), (TextUtils.join(",", mandalIds))));

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(villagesDataMap, "Village"));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            villageSpinner.setAdapter(spinnerArrayAdapter);
            villageSpinner.setSelection(villagePos);

            villageCodeStr = villageSpinner.getSelectedItem().toString();
            Pair villagePair = villagesDataMap.get(CommonConstants.villageIdPlot);
            CommonConstants.villageCodePlot = villagePair.first.toString();

            CommonConstants.villageName = villageSpinner.getSelectedItem().toString();
            if (!CommonUtils.isFromFollowUp() && !CommonUtils.isFromConversion() && !CommonUtils.isFromCropMaintenance()) {
                CommonConstants.PLOT_CODE = dataAccessHandler.getGeneratedPlotId(Queries.getInstance().getMaxNumberForPlotQuery(financalYrDays), financalYrDays);
            }
            setPinCode();
            String ClusterName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getClusterName(CommonConstants.villageIdPlot));
            ClusterNameTv.setText(" " + ClusterName);
            plotCodeTxt.setText(plot.getCode());
            plotareaedt.setText(String.valueOf(plot.getTotalplotarea()));
            CommonConstants.TotalPlot_Area = String.valueOf(plot.getTotalplotarea());
            areaUnderSuitablePalmEdit.setText(String.valueOf(plot.getSuitablePalmOilArea()));
            pincode.setText("" + savedAddressData.getPincode());
            landmarkedt.setText("" + savedAddressData.getLandmark());
            plotaddress.setText("" + savedAddressData.getAddressline1());
            if (CommonUtils.isFromCropMaintenance() && TextUtils.isEmpty(plotaddress.getText().toString())) {
                plotaddress.setEnabled(true);
                plotaddress.setFocusable(true);
                plotaddress.setClickable(true);
                plotaddress.setCursorVisible(true);
            }
            if (CommonUtils.isFromCropMaintenance() && TextUtils.isEmpty(savedAddressData.getLandmark())) {
                landmarkedt.setEnabled(true);
                landmarkedt.setFocusable(true);
                landmarkedt.setClickable(true);
                landmarkedt.setCursorVisible(true);
            }
            if (null != plot.getIsBoundryFencing()) {
                properBoundryFencingSpinner.setSelection(plot.getIsBoundryFencing() == 1 ? 1 : 2);
            }
            incomeSpin.setSelection(CommonUtils.getIndex(incomeDataMap.keySet(), String.valueOf(plot.getCropincometypeid())) + 1);
            ownerShipSpin.setSelection(CommonUtils.getIndex(ownerShipMap.keySet(), String.valueOf(plot.getPlotownershiptypeid())) + 1);
            landTypeSpn.setSelection(CommonUtils.getIndex(landTypeMap.keySet(), String.valueOf(plot.getLandTypeId())) + 1);
            totalAreaHorticulture.setText(""+plot.getTotalAreaUnderHorticulture());
            dataPair = (List<Pair<String, String>>) DataManager.getInstance().getDataFromManager(DataManager.PLOT_CURRENT_CROPS_DATA_PAIR);
            neighbourPlotPair = (List<Pair>) DataManager.getInstance().getDataFromManager(DataManager.PLOT_NEIGHBOURING_PLOTS_DATA_PAIR);

            if (dataPair != null && dataPair.size() > 0) {
                genericAdapter.updateAdapter(dataPair);
                currentCropRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                currentCropRecyclerView.setAdapter(genericAdapter);
                currentCropList = (List<CropModel>) DataManager.getInstance().getDataFromManager(DataManager.PLOT_CURRENT_CROPS_DATA);
            }

            if (neighbourPlotPair != null && neighbourPlotPair.size() > 0) {
                neighbourPlotAdapter.updateAdapter(neighbourPlotPair);
                neighbourPlotsList = (List<CropModel>) DataManager.getInstance().getDataFromManager(DataManager.PLOT_NEIGHBOURING_PLOTS_DATA);
            }

            if (CommonUtils.isFromCropMaintenance() || CommonUtils.isFromConversion()) {
                if (TextUtils.isEmpty(plot.getSurveynumber()) || plot.getSurveynumber().equalsIgnoreCase("null")) {
                    surveyNoedt.setText("");
                } else {
                    surveyNoedt.setText("" + plot.getSurveynumber());
                }

                if (TextUtils.isEmpty(plot.getAdangalnumber()) || plot.getAdangalnumber().equalsIgnoreCase("null")) {
                    adangalNoedt.setText("");
                } else {
                    adangalNoedt.setText("" + plot.getAdangalnumber());
                }

            }

            if (CommonUtils.isFromConversion() || CommonUtils.isFromCropMaintenance()) {
                if (null != plot.getIsplothandledbycaretaker()) {
                    careTakerSpinner.setSelection(plot.getIsplothandledbycaretaker() == 1 ? 1 : 2);
                    if (plot.getIsplothandledbycaretaker() == 1) {
                        careTakerNumberEdit.setText(plot.getCaretakercontactnumber());
                        careTakerNameEdit.setText(plot.getCaretakername());
                    }
                } else {
                    String plotcaretakercode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gePlotCareTakerfromDB(CommonConstants.PLOT_CODE));
                    if (plotcaretakercode == null || plotcaretakercode.equalsIgnoreCase("null") || plotcaretakercode.isEmpty()) {

                    } else {
                        int caretakercode = Integer.parseInt(plotcaretakercode);
                        careTakerSpinner.setSelection(caretakercode == 1 ? 1 : 2);
                        if (caretakercode == 1) {
                            careTakerNumberEdit.setText(plot.getCaretakercontactnumber());
                            careTakerNameEdit.setText(plot.getCaretakername());
                        }
                    }
                }
            }
        } else {
            UiUtils.showCustomToastMessage("Error while getting address details", getActivity(), 1);
        }
    }


    //Initializing the UI
    public void initUI() {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(activity.getResources().getString(R.string.plot_details));
        this.bottomScroll = rootView.findViewById(R.id.bottomScroll);
        this.scrollView = rootView.findViewById(R.id.scrollView);
        this.parent = rootView.findViewById(R.id.parent);
        this.farmerSaveBtn = rootView.findViewById(R.id.farmerSaveBtn);
        this.addRowImgNbPlot = rootView.findViewById(R.id.addRowImgNbPlot);
        this.neighbourPlotRecyclerView = rootView.findViewById(R.id.neighbourPlotRecyclerView);
        this.addRowImg = rootView.findViewById(R.id.addRowImg);
        this.currentCropRecyclerView = rootView.findViewById(R.id.currentCropRecyclerView);
        this.stateTxt = rootView.findViewById(R.id.stateTxt);
        this.pincode = rootView.findViewById(R.id.pincode);
        this.plotaddress = rootView.findViewById(R.id.plot_address);
        this.landmarkedt = rootView.findViewById(R.id.land_mark_edt);
        this.areaUnderSuitablePalmEdit = rootView.findViewById(R.id.area_suitable_palm_edit);
        this.plotareaedt = rootView.findViewById(R.id.plot_area_edt);
        this.villageSpinner = rootView.findViewById(R.id.villageName);
        this.mandalSpinner = rootView.findViewById(R.id.mandalName);
        this.stateSpinner = rootView.findViewById(R.id.statespin);
        this.regionSpinner = rootView.findViewById(R.id.regionSpin);
        this.districtSpinner = rootView.findViewById(R.id.districtSpin);
        plotCodeTxt = rootView.findViewById(R.id.plotCodeTxt);
        properBoundryFencingSpinner = rootView.findViewById(R.id.ipbfSpinner);
        ownerShipSpin = rootView.findViewById(R.id.ownerShipSpinner);
        incomeSpin = rootView.findViewById(R.id.incomefromCurrentCropsSpinner);
        careTakerSpinner = rootView.findViewById(R.id.careTakerSpinner);
        surveyNoedt = rootView.findViewById(R.id.Survey_no_edt);
        adangalNoedt = rootView.findViewById(R.id.adangal_edt);
        ClusterNameTv = rootView.findViewById(R.id.Cluster_Name);
        //LandLord Intilize
        LLHolderName = rootView.findViewById(R.id.landNameEdt);
        LLMobileNo = rootView.findViewById(R.id.landContactEdt);
        LLAccountName = rootView.findViewById(R.id.landlord_account_holder_name);
        LLAccountNumber = rootView.findViewById(R.id.landlord_account_number);
        LLIFSCcode = rootView.findViewById(R.id.landlord_ifsc_code);
        LLleaseDate = rootView.findViewById(R.id.lease_datefrom_edt);
        LLleaseEndDate = rootView.findViewById(R.id.lease_dateto_edt);
        bankDetailsSpin = rootView.findViewById(R.id.bankCodeSpin);
        branchNameSpin = rootView.findViewById(R.id.branchSpin);
        addIDProof = rootView.findViewById(R.id.addRowlandLordImg);
        idProofsRecyclerView = rootView.findViewById(R.id.idProofsRecyclerView);
        careTakerNameEdit = rootView.findViewById(R.id.caretakernameEdt);
        careTakerNumberEdit = rootView.findViewById(R.id.caretakercontactnumlEdt);

        landLordLayout = rootView.findViewById(R.id.landlord_details_screen);
        careTakerlayout = rootView.findViewById(R.id.careTakerlayout);
        careTakerEntrylayout = rootView.findViewById(R.id.careTakerEntrylayout);
        converstionSurveyLayout = rootView.findViewById(R.id.converstionSurveyLayout);
        area_suitable_palm = rootView.findViewById(R.id.area_suitable_palm);
        incomeLayout = rootView.findViewById(R.id.incomeLayout);
        currentCropsLayout = rootView.findViewById(R.id.currentCropsLayout);
        totalAreaHorticulture = rootView.findViewById(R.id.totalAreaHorticulture);
        landTypeSpn = rootView.findViewById(R.id.landTypeSpn);

        // Finical Year
        final Calendar calendar = Calendar.getInstance();
        final FiscalDate fiscalDate = new FiscalDate(calendar);
        financialYear = fiscalDate.getFiscalYear();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String currentdate = CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_3);
            String financalDate = "01/04/" + String.valueOf(financialYear);
            Date date1 = dateFormat.parse(currentdate);
            Date date2 = dateFormat.parse(financalDate);
            long diff = date1.getTime() - date2.getTime();
            String noOfDays = String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1);
            days = StringUtils.leftPad(noOfDays, 3, "0");
            financalYrDays = String.valueOf(financialYear).substring(2, 4) + days;


        } catch (Exception e) {
            e.printStackTrace();
        }


        if (CommonUtils.isFromConversion()) {
            area_suitable_palm.setVisibility(View.GONE);
        } else {
            area_suitable_palm.setVisibility(View.GONE);
        }

        if (CommonUtils.isFromConversion() || CommonUtils.isFromCropMaintenance()) {
            incomeLayout.setVisibility(View.GONE);
            currentCropsLayout.setVisibility(View.GONE);
        } else {
            incomeLayout.setVisibility(View.VISIBLE);
            currentCropsLayout.setVisibility(View.VISIBLE);
            areaUnderSuitablePalmEdit.setVisibility(View.GONE);
        }

        if (CommonUtils.isFromFollowUp()) {
            currentCropsLayout.setVisibility(View.GONE);
        }

        //IDProof Details
        idProofsData = mainData = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("12"));

        identityProofsList = (List<LandlordIdProof>) DataManager.getInstance().getDataFromManager(DataManager.LANDLORD_IDPROOFS_DATA);
        if (null == identityProofsList) {
            identityProofsList = new ArrayList<>();
        } else {
            idProofsRecyclerView.setVisibility(View.VISIBLE);
        }

        idProofsRecyclerView.setHasFixedSize(true);
        idProofsListAdapter = new LandLordIdProofListAdapter(getActivity(), identityProofsList, mainData);
        idProofsListAdapter.setIdProofsClickListener(PlotDetailsFragment.this);
        idProofsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        idProofsRecyclerView.setAdapter(idProofsListAdapter);

        if (CommonUtils.isFromConversion() || CommonUtils.isFromCropMaintenance()) {
            converstionSurveyLayout.setVisibility(View.VISIBLE);
            careTakerEntrylayout.setVisibility(View.VISIBLE);
        } else {
            converstionSurveyLayout.setVisibility(View.GONE);
        }

        //Binding Data to Spinner & On Click Listeners
        String[] boundaryFencingArray = getActivity().getResources().getStringArray(R.array.yesOrNo_values);
        List<String> boundaryList = Arrays.asList(boundaryFencingArray);
        ArrayAdapter<String> spinnerBoundryArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, boundaryList);
        spinnerBoundryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        properBoundryFencingSpinner.setAdapter(spinnerBoundryArrayAdapter);

        String[] careTakerArray = getActivity().getResources().getStringArray(R.array.yesOrNo_values);
        List<String> careTakerList = Arrays.asList(careTakerArray);
        ArrayAdapter<String> spinnerCareTakerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, careTakerList);
        spinnerCareTakerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        careTakerSpinner.setAdapter(spinnerCareTakerArrayAdapter);

        scrollView.setOnBottomReachedListener(new InteractiveScrollView.OnBottomReachedListener() {
            @Override
            public void onBottomReached() {


            }
        });

        ownerShipSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (ownerShipSpin.getSelectedItem().toString().equalsIgnoreCase("Leased")) {
                   // if (CommonUtils.isFromConversion() || CommonUtils.isFromCropMaintenance()) {
                        leaseActiveValue = 1;
                        leased = true;
                        landLordLayout.setVisibility(View.VISIBLE);
                   // }

                } else {
                    leaseActiveValue = 0;
                    landLordLayout.setVisibility(View.GONE);
                    leased = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        careTakerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (careTakerSpinner.getSelectedItem().toString().equalsIgnoreCase("Yes")) {
                    careTakerlayout.setVisibility(View.VISIBLE);
                } else {
                    careTakerlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addRowImg.setOnClickListener(v -> {
            MultiEntryDialogFragment multiEntryDialogFragment = new MultiEntryDialogFragment();
            multiEntryDialogFragment.setOnDataSelectedListener(PlotDetailsFragment.this);
            Bundle inpuptBundle = new Bundle();
            inpuptBundle.putInt("type", CURRENT_CROP_TYPE);
            multiEntryDialogFragment.setArguments(inpuptBundle);
            String backStateName = multiEntryDialogFragment.getClass().getName();
            FragmentManager mFragmentManager = getChildFragmentManager();
            multiEntryDialogFragment.show(mFragmentManager, "fragment_edit_name");
        });

        addRowImgNbPlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiEntryDialogFragment multiEntryDialogFragment = new MultiEntryDialogFragment();
                multiEntryDialogFragment.setOnDataSelectedListener(PlotDetailsFragment.this);
                Bundle inpuptBundle = new Bundle();
                inpuptBundle.putInt("type", NEIGHBOUR_PLOT_TYPE);
                inpuptBundle.putInt("neighbourPlotCount", neighbourPlotPair.size());
                multiEntryDialogFragment.setArguments(inpuptBundle);
                FragmentManager mFragmentManager = getChildFragmentManager();
                multiEntryDialogFragment.show(mFragmentManager, "fragment_edit_name");
            }
        });

        farmerSaveBtn.setOnClickListener(v -> {

            if (CommonUtils.isFromCropMaintenance()){
                CommonConstants.isplotupdated = true;
            }else{
                CommonConstants.isplotupdated = false;
            }

            if (isValid()) {
                CommonUtils.hideSoftKeyboard(getActivity());
                try {
                    saveState();
//                        Toast.makeText(getActivity(),"TotalPlotArea"+CommonConstants.TotalPlot_Area,Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        savedAddressData = (Address) DataManager.getInstance().getDataFromManager(DataManager.PLOT_ADDRESS_DETAILS);
        if (savedAddressData == null) {
            savedAddressData = new Address();
        }

        //BankDetails
        bankCodeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("3"));
        ArrayAdapter<String> bankCodeSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.fromMap(bankCodeDataMap, "BankName"));
        bankCodeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankDetailsSpin.setAdapter(bankCodeSpinnerArrayAdapter);

        bankDetailsSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (bankCodeDataMap != null && bankCodeDataMap.size() > 0 && bankDetailsSpin.getSelectedItemPosition() != 0 && checkUpdateValueBank == 0) {
                    CommonConstants.bankTypeId = bankCodeDataMap.keySet().toArray(new String[bankCodeDataMap.size()])[i - 1];
                    branchNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getBranchDetails(CommonConstants.bankTypeId));
                    ArrayAdapter<String> branchNameSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.fromMap(branchNameDataMap, "BranchName"));
                    branchNameSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    branchNameSpin.setAdapter(branchNameSpinnerArrayAdapter);
                    bankdetailnameStr = bankDetailsSpin.getSelectedItem().toString();
                } else {
                    checkUpdateValueBank = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        branchNameSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (branchNameDataMap != null && branchNameDataMap.size() > 0 && branchNameSpin.getSelectedItemPosition() != 0 && checkUpdateValueBranch == 0) {
                    CommonConstants.branchTypeId = branchNameDataMap.keySet().toArray(new String[branchNameDataMap.size()])[i - 1];
                    LLIFSCcode.setText(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getIfscCode(CommonConstants.branchTypeId)));
                    branchnameStr = branchNameSpin.getSelectedItem().toString();
                } else {
                    checkUpdateValueBranch = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        addIDProof.setOnClickListener(v -> displayIdProofsDialog());

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

        LLleaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));  //date is dateSetListener as per your code in question
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                picker = true;
            }
        });

        LLleaseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker = false;
            }
        });

        plotareaedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                plotAreaStr = plotareaedt.getText().toString();
                if (TextUtils.isEmpty(plotAreaStr) || !validateDoubles() && (CommonUtils.isFromConversion() || CommonUtils.isFromCropMaintenance())) {
                    areaUnderSuitablePalmEdit.setText("");
                }
            }
        });

        areaUnderSuitablePalmEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                totalAreaStr = areaUnderSuitablePalmEdit.getText().toString();
                if (!validateDoubles() && (CommonUtils.isFromConversion() || CommonUtils.isFromCropMaintenance())) {
                    if (!TextUtils.isEmpty(plotAreaStr)) {
                        areaUnderSuitablePalmEdit.setText(totalAreaStr.substring(0, totalAreaStr.length() - 1));
                        areaUnderSuitablePalmEdit.setSelection(totalAreaStr.length());
                    }
                }
            }
        });


        landTypeMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtComplaintsTypeData("53"));
        ArrayAdapter<String> landTypeSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.fromMap(landTypeMap, "LandType"));
        landTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        landTypeSpn.setAdapter(landTypeSpinnerAdapter);

    }

    //Binding the Landlord fields.
    private void bindLandLordPersonalData() {
        LLHolderName.setText(plotLandLord.getLandlordname());
        LLMobileNo.setText(plotLandLord.getLandlordcontactnumber());
        LLleaseDate.setText(plotLandLord.getLeasestartdate());
        LLleaseEndDate.setText(plotLandLord.getLeaseenddate());
    }

    //Binding Landlord bank details
    private void bindLandLordBankData() {
        checkUpdateValueBank = 1;
        checkUpdateValueBranch = 1;
        CommonConstants.bankTypeId = String.valueOf(landlordBank.getBankid());
        String bankTypeId = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getBankTypeId(CommonConstants.bankTypeId));
        bankDetailsSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(bankCodeDataMap, Integer.parseInt(bankTypeId)));
        branchNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getBranchDetails(String.valueOf(landlordBank.getBankid())));

        LLAccountName.setText(landlordBank.getAccountholdername());
        LLAccountNumber.setText(landlordBank.getAccountnumber());

        branchNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getBranchDetails(bankTypeId));
        ArrayAdapter<String> branchNameSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.fromMap(branchNameDataMap, "BranchName"));
        branchNameSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchNameSpin.setAdapter(branchNameSpinnerArrayAdapter);
        int branchPos = CommonUtilsNavigation.getvalueFromHashMap(branchNameDataMap, landlordBank.getBankid());
        com.oilpalm3f.mainapp.cloudhelper.Log.v(LOG_TAG, "##### selected branch pos " + branchPos);
        branchNameSpin.setSelection(branchPos);
        LLIFSCcode.setText(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getIfscCode(String.valueOf(landlordBank.getBankid()))));
        LLIFSCcode.setEnabled(false);
        LLIFSCcode.setFocusable(false);
        LLIFSCcode.setClickable(false);
    }

//filtering Id Proofs
    public void filterIdProofs() {
        for (LandlordIdProof identityProof : identityProofsList) {
            idProofsData.remove(String.valueOf(identityProof.getIDProofTypeId()));
        }
    }

    //Adding Id Proof Dialog
    private void displayIdProofsDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View promptView = layoutInflater.inflate(R.layout.dialog_idproof, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);
        final EditText aadhar_edt = promptView.findViewById(R.id.idproofsEdt);
        filterIdProofs();
        idproof = promptView.findViewById(R.id.idProofsSpin);
        idprooflayout = promptView.findViewById(R.id.idprooflayout);
        idprooflayout.setVisibility(View.GONE);
        idproof.setAdapter(UiUtils.createAdapter(getActivity(), idProofsData, "Id proof"));

        idproof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aadhar_edt.setText("");
                String selectedProof = "" + idproof.getSelectedItem().toString();
                aadhar_edt.setInputType(selectedProof.contains("Aadhar") ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT);

                if (selectedProof.contains("Driving")) {
                    aadhar_edt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                } else if (selectedProof.contains("PAN")) {
                    aadhar_edt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                } else if (selectedProof.contains("Aadhar")) {
                    aadhar_edt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Button SaveBtn = promptView.findViewById(R.id.SaveBtn);
        final Button CancelBtn = promptView.findViewById(R.id.CancelBtn);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aadharStr = aadhar_edt.getText().toString();
                if (!CommonUtils.isEmptySpinner(idproof)) {
                    if (!TextUtils.isEmpty(aadharStr)) {
                        String selectedProof = idproof.getSelectedItem().toString();

                        if (TextUtils.isEmpty(selectedProof)) {
                            UiUtils.showCustomToastMessage("Please select id proof", getActivity(), 1);
                            return;
                        }

                        if (selectedProof.equalsIgnoreCase(getResources().getString(R.string.adhar_number))) {
                            if (!TextUtils.isDigitsOnly(aadharStr)) {
                                UiUtils.showCustomToastMessage("Enter only numbers", getActivity(), 1);
                                return;
                            }
                            if (aadharStr.length() < 12) {
                                UiUtils.showCustomToastMessage("Enter proper Aadhar card number", getActivity(), 1);
                                return;
                            } else {
                                updateIdProofsAdapter(idproof.getSelectedItem().toString(), aadharStr);
                                alert.cancel();
                            }
                        } else if (selectedProof.equalsIgnoreCase(getResources().getString(R.string.pancard_number))) {
                            if (aadharStr.length() < 10) {
                                UiUtils.showCustomToastMessage("Enter proper PAN card number", getActivity(), 1);
                                return;
                            } else {
                                updateIdProofsAdapter(idproof.getSelectedItem().toString(), aadharStr);
                                alert.cancel();
                            }
                        } else if (selectedProof.equalsIgnoreCase(getResources().getString(R.string.drive_number))) {
                            if (aadharStr.length() < 16) {
                                UiUtils.showCustomToastMessage("Enter proper Driving License number", getActivity(), 1);
                                return;
                            } else {
                                updateIdProofsAdapter(idproof.getSelectedItem().toString(), aadharStr);
                                alert.cancel();
                            }
                        } else {
                            updateIdProofsAdapter(idproof.getSelectedItem().toString(), aadharStr);
                            alert.cancel();
                        }

                    } else {
                        UiUtils.showCustomToastMessage("Please enter the Idproof detail", getActivity(), 1);
                    }
                } else {
                    UiUtils.showCustomToastMessage("Please select IdProof", getActivity(), 1);
                }
            }
        });
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });
        alert = alertDialogBuilder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    //Update IdProof Data
    private void updateIdProofsAdapter(String idProofName, String enteredNumber) {
        String idProofId = CommonUtils.getKeyFromValue(mainData, idProofName);
        LandlordIdProof identityProof = new LandlordIdProof();
        identityProof.setIDProofTypeId(Integer.parseInt(idProofId));
        identityProof.setIdProofNumber(enteredNumber);
        identityProofsList.add(identityProof);
        idProofsRecyclerView.setVisibility(View.VISIBLE);
        mainData = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("12"));
        idProofsListAdapter.updateData(identityProofsList, mainData);
    }

    //Validations
    private boolean isValid() {

        plotcodeStr = plotCodeTxt.getText().toString();
        totalplotareaStr = plotareaedt.getText().toString();
        landmarkStr = landmarkedt.getText().toString();
        plotaddressStr = plotaddress.getText().toString();
        surveyNoedtStr = surveyNoedt.getText().toString();
        adangalNoedtStr = adangalNoedt.getText().toString();

        LLHolderNameStr = LLHolderName.getText().toString();
        LLMobileNoStr = LLMobileNo.getText().toString();
        LLAccountNameStr = LLAccountName.getText().toString();
        LLAccountNumberStr = LLAccountNumber.getText().toString();
        LLIFSCcodeStr = LLIFSCcode.getText().toString();
        LLleaseDateStr = LLleaseDate.getText().toString();
        LLleaseEndDateStr = LLleaseEndDate.getText().toString();

        if (CommonUtils.isEmptySpinner(stateSpinner)) {
            UiUtils.showCustomToastMessage("Please select state", getActivity(), 0);
        }

        if (CommonUtils.isEmptySpinner(districtSpinner)) {
            UiUtils.showCustomToastMessage("Please select district", getActivity(), 0);
        }

        if (CommonUtils.isEmptySpinner(mandalSpinner)) {
            UiUtils.showCustomToastMessage("Please select mandal", getActivity(), 0);
        }

        if (CommonUtils.isEmptySpinner(villageSpinner)) {
            UiUtils.showCustomToastMessage("Please select village", getActivity(), 0);

        }
        if (TextUtils.isEmpty(CommonConstants.PLOT_CODE)) {
            com.oilpalm3f.mainapp.cloudhelper.Log.pushLogToCrashlytics(PlotDetailsFragment.class.getSimpleName() + "\n" + CommonConstants.PLOT_CODE);

            CommonConstants.PLOT_CODE = dataAccessHandler.getGeneratedPlotId(Queries.getInstance().getMaxNumberForPlotQuery(financalYrDays), financalYrDays);
            UiUtils.showCustomToastMessage("plot code is generated", getActivity(), 0);

        }


        if (TextUtils.isEmpty(totalplotareaStr)) {
            UiUtils.showCustomToastMessage("Please enter total plot area", getActivity(), 0);
            plotareaedt.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(landmarkStr)) {
            UiUtils.showCustomToastMessage("Please enter land mark", getActivity(), 0);
            landmarkedt.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(plotaddressStr)) {
            UiUtils.showCustomToastMessage("Please enter plot address", getActivity(), 0);
            plotaddress.requestFocus();
            return false;
        }
//        if (CommonUtils.isEmptySpinner(properBoundryFencingSpinner)) {
//            UiUtils.showCustomToastMessage("Please Select Proper Boundary Fencing", getActivity(), 0);
//            return false;
//        }

        if (CommonUtils.isEmptySpinner(ownerShipSpin)) {
            UiUtils.showCustomToastMessage("Please Select ownership", getActivity(), 0);
            return false;
        }

        if (!CommonUtils.isFromCropMaintenance() && !CommonUtils.isFromConversion() && !CommonUtils.isFromFollowUp()) {
            if (dataPair == null || dataPair.isEmpty()) {
                UiUtils.showCustomToastMessage("Please enter Current Crop Grown", getActivity(), 0);
                return false;
            }
        }

        if (CommonUtils.isFromConversion()) {
            if (TextUtils.isEmpty(surveyNoedtStr)) {
                UiUtils.showCustomToastMessage("Please enter Survey Number", getActivity(), 0);
                surveyNoedt.requestFocus();
                return false;
            }
        }

        if (leased) {
            if (CommonUtils.isFromConversion() || CommonUtils.isFromCropMaintenance()) {
                if (TextUtils.isEmpty(surveyNoedtStr)) {
                    UiUtils.showCustomToastMessage("Please enter Survey Number", getActivity(), 0);
                    surveyNoedt.requestFocus();
                    return false;
                }
            }
            if (CommonUtils.isFromConversion() || CommonUtils.isFromCropMaintenance()) {

                if (TextUtils.isEmpty(LLHolderNameStr)) {
                    UiUtils.showCustomToastMessage("Please Enter LandLord Holder Name", getActivity(), 0);
                    LLHolderName.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(LLMobileNoStr) || LLMobileNoStr.length() < 10) {
                    UiUtils.showCustomToastMessage("Please Enter Mobile Number", getActivity(), 0);
                    LLMobileNo.requestFocus();
                    return false;
                }

                if (TextUtils.isEmpty(LLleaseDateStr)) {
                    UiUtils.showCustomToastMessage("Please Enter Lease Start Date", getActivity(), 0);
                    LLleaseDate.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(LLleaseEndDateStr)) {
                    UiUtils.showCustomToastMessage("Please Enter Lease End Date", getActivity(), 0);
                    LLleaseEndDate.requestFocus();
                    return false;
                }
            }
        }

        if (CommonUtils.isFromCropMaintenance() || CommonUtils.isFromConversion()) {
            if (CommonUtils.isEmptySpinner(careTakerSpinner)) {
                UiUtils.showCustomToastMessage("Please select care taker type", getActivity(), 0);
                careTakerSpinner.requestFocus();
                return false;
            }

            if (careTakerSpinner.getSelectedItemPosition() == 1) {
                if (TextUtils.isEmpty(careTakerNameEdit.getText().toString())) {
                    UiUtils.showCustomToastMessage("Please enter care taker name", getActivity(), 0);
                    careTakerNameEdit.requestFocus();
                    return false;
                }

                if (TextUtils.isEmpty(careTakerNumberEdit.getText().toString()) || careTakerNumberEdit.getText().toString().length() < 10) {
                    //   primary_contactno.setError(getResources().getString(R.string.error_contact_number));
                    UiUtils.showCustomToastMessage("Please enter proper care taker number", getActivity(), 0);
                    careTakerNumberEdit.requestFocus();
                    return false;
                }

            }

        }

        double currentCropArea = 0.0;
        if (currentCropList != null && !currentCropList.isEmpty()) {
            for (CropModel cropModel : currentCropList) {
                try {
                    if (cropModel.isActive == 1)
                        currentCropArea = currentCropArea + Double.parseDouble(cropModel.recName);
                } catch (NumberFormatException nfe) {
                    Log.e(LOG_TAG, "@@@@ error while parsing number");
                }
            }
        }

        if (plotArea < currentCropArea) {
            UiUtils.showCustomToastMessage("Current crops area should not exceed the total plot area", getActivity(), 1);
            return false;
        }

        return true;
    }

    //Set Pincode based on villageId
    public void setPinCode() {
        globalpincode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getPincode(CommonConstants.villageIdPlot));
        if (!TextUtils.isEmpty(globalpincode)) {
            pincode.setText(globalpincode);
            pincode.setEnabled(false);
            pincode.setCursorVisible(false);
            pincode.setClickable(false);
            pincode.setTextColor(Color.BLACK);
        }
    }

    //saving the details plot and address
    public void saveState() {
        ///
        savePlotDetails();
        saveAddressData();

        if (CommonUtils.isFromCropMaintenance() && CommonConstants.isplotupdated == true){
            DataManager.getInstance().addData(DataManager.PLOT_DETAILS, plot);
        }else if(CommonUtils.isFromConversion() || CommonUtils.isFromFollowUp() ){
            DataManager.getInstance().addData(DataManager.PLOT_DETAILS, plot);
        }else if(CommonUtils.isNewRegistration() || CommonUtils.isNewPlotRegistration()){
            DataManager.getInstance().addData(DataManager.PLOT_DETAILS, plot);
        }

        DataManager.getInstance().addData(DataManager.VALIDATE_PLOT_ADDRESS_DETAILS, savedAddressData);
        DataManager.getInstance().addData(DataManager.PLOT_ADDRESS_DETAILS, savedAddressData);
        DataManager.getInstance().addData(DataManager.PLOT_NEIGHBOURING_PLOTS_DATA_PAIR, neighbourPlotPair);
        DataManager.getInstance().addData(DataManager.PLOT_CURRENT_CROPS_DATA_PAIR, dataPair);

        if (null != neighbourPlotsList) {
            DataManager.getInstance().addData(DataManager.PLOT_NEIGHBOURING_PLOTS_DATA, neighbourPlotsList);
        }

        DataManager.getInstance().addData(DataManager.PLOT_CURRENT_CROPS_DATA, currentCropList);

        if (leased) {
            saveLandLordDetails();
        } else {
            if (fromDataBase) {
                DataManager.getInstance().addData(DataManager.IS_PLOTS_DATA_UPDATED, true);
            } else {
                DataManager.getInstance().addData(DataManager.IS_PLOTS_DATA_UPDATED, false);
            }
            updateUiListener.updateUserInterface(0);
            CommonConstants.Flags.isPlotsDataUpdated = true;
            getFragmentManager().popBackStack();
        }


    }

    //saving landlord details
    private void saveLandLordDetails() {
        PlotLandlord landLordLeaseData = new PlotLandlord();
        landLordLeaseData.setPlotcode(CommonConstants.PLOT_CODE);
        landLordLeaseData.setLandlordname(LLHolderNameStr);
        landLordLeaseData.setLandlordcontactnumber(LLMobileNoStr);
        landLordLeaseData.setLeasestartdate(LLleaseDateStr);
        landLordLeaseData.setLeaseenddate(LLleaseEndDateStr);
        landLordLeaseData.setIsactive(leaseActiveValue);
        landLordLeaseData.setCreatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
        landLordLeaseData.setCreateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        landLordLeaseData.setUpdatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
        landLordLeaseData.setUpdateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));

        DataManager.getInstance().addData(DataManager.LANDLORD_LEASED_DATA, landLordLeaseData);

        saveLandLordBankDetailsData();
        saveLandLordIDproofs();

    }

    //Saving Landlord Idproofs
    private void saveLandLordIDproofs() {
        DataManager.getInstance().addData(DataManager.LANDLORD_IDPROOFS_DATA, identityProofsList);
    }

    //Saving Landlord Bankdetails
    private void saveLandLordBankDetailsData() {
        LandlordBank landlordBank = new LandlordBank();
        if(bankDetailsSpin.getSelectedItemPosition()>0) {
            String bankId = CommonUtils.getKeyFromValue(bankCodeDataMap, bankDetailsSpin.getSelectedItem().toString());
            landlordBank.setBankid(Integer.parseInt(bankId));
            landlordBank.setAccountholdername(LLAccountNameStr);
            landlordBank.setAccountnumber(LLAccountNumberStr);
            DataManager.getInstance().addData(DataManager.LANDLORD_BANK_DATA, landlordBank);
        }


        updateUiListener.updateUserInterface(0);
        CommonConstants.Flags.isPlotsDataUpdated = true;
        if (fromDataBase) {
            DataManager.getInstance().addData(DataManager.IS_PLOTS_DATA_UPDATED, true);
        } else {
            DataManager.getInstance().addData(DataManager.IS_PLOTS_DATA_UPDATED, false);
        }
        getFragmentManager().popBackStack();
    }

    //Saving Plot Details
    public void savePlotDetails() {
        if (plot == null) {
            plot = new Plot();
        }
        plot.setTotalplotarea(Double.parseDouble(plotareaedt.getText().toString().trim()));
        if (!TextUtils.isEmpty(areaUnderSuitablePalmEdit.getText().toString())) {
            plot.setSuitablePalmOilArea(Double.parseDouble(areaUnderSuitablePalmEdit.getText().toString().trim()));
        } else {
            plot.setSuitablePalmOilArea(0.0);
        }

        if (!TextUtils.isEmpty(totalAreaHorticulture.getText().toString())) {
            plot.setTotalAreaUnderHorticulture(Float.parseFloat(totalAreaHorticulture.getText().toString().trim()));
        } else {
            plot.setSuitablePalmOilArea(0.0);
        }
        plot.setAddesscode(CommonConstants.ADDRESS_CODE_PREFIX + CommonConstants.PLOT_CODE);
        if (TextUtils.isEmpty(CommonConstants.PLOT_CODE)) {

            CommonConstants.PLOT_CODE = dataAccessHandler.getGeneratedPlotId(Queries.getInstance().getMaxNumberForPlotQuery(financalYrDays), financalYrDays);

        }
        plot.setCode(CommonConstants.PLOT_CODE);
        plot.setFarmercode(CommonConstants.FARMER_CODE);

        String incomeTypeId = CommonUtils.getKeyFromValue(incomeDataMap, incomeSpin.getSelectedItem().toString());
        String ownerShipTypeId = CommonUtils.getKeyFromValue(ownerShipMap, ownerShipSpin.getSelectedItem().toString());
        String landTypeid = CommonUtils.getKeyFromValue(landTypeMap, landTypeSpn.getSelectedItem().toString());

        plot.setPlotownershiptypeid((!TextUtils.isEmpty(ownerShipTypeId)) ? Integer.parseInt(CommonUtils.getKeyFromValue(ownerShipMap, ownerShipSpin.getSelectedItem().toString())) : null);
        plot.setCropincometypeid((!TextUtils.isEmpty(incomeTypeId)) ? Integer.parseInt(CommonUtils.getKeyFromValue(incomeDataMap, incomeSpin.getSelectedItem().toString())) : null);
        plot.setLandTypeId((!TextUtils.isEmpty(landTypeid)) ? Integer.parseInt(CommonUtils.getKeyFromValue(landTypeMap, landTypeSpn.getSelectedItem().toString())) : null);
        if (!CommonUtils.isEmptySpinner(properBoundryFencingSpinner)) {
            plot.setIsBoundryFencing(properBoundryFencingSpinner.getSelectedItemPosition() == 1 ? 1 : 2);
        } else {
            plot.setIsBoundryFencing(null);
        }
        if (CommonUtils.isFromConversion() || CommonUtils.isFromCropMaintenance()) {
            plot.setSurveynumber(surveyNoedtStr);
            plot.setAdangalnumber(adangalNoedtStr);
            plot.setIsplothandledbycaretaker(careTakerSpinner.getSelectedItemPosition() == 1 ? 1 : 2);
            if (careTakerSpinner.getSelectedItemPosition() == 1) {
                plot.setCaretakercontactnumber(careTakerNumberEdit.getText().toString());
                plot.setCaretakername(careTakerNameEdit.getText().toString());
            }
        }
    }

    //binding data to address spinners
    public void saveAddressData() {
        savedAddressData.setAddressline1(plotaddress.getText().toString().trim());
        savedAddressData.setAddressline2("");
        savedAddressData.setAddressline3("");
        savedAddressData.setLandmark(landmarkedt.getText().toString().trim());
        savedAddressData.setVillageid(Integer.parseInt(CommonConstants.villageIdPlot));
        savedAddressData.setMandalid(Integer.parseInt(CommonConstants.mandalIdPlot));
        savedAddressData.setDistictid(Integer.parseInt(CommonConstants.districtIdPlot));
        savedAddressData.setStateid(Integer.parseInt(CommonConstants.statePlotId));
        savedAddressData.setCountryid(Integer.parseInt(CommonConstants.countryID));
        savedAddressData.setPincode(pincode.getText().toString().trim().length() == 0 ? 0 : new BigInteger(pincode.getText().toString().trim()).intValue());
        DataManager.getInstance().addData(DataManager.PLOT_ADDRESS_DETAILS, savedAddressData);
    }

    //Handling
    public void handleAddressData() {

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (userStateSelect) {
                    userStateSelect = false;
                } else {
                    if (stateDataMap != null && stateDataMap.size() > 0 && stateSpinner.getSelectedItemPosition() != 0) {
                        CommonConstants.statePlotId = stateDataMap.keySet().toArray(new String[stateDataMap.size()])[i - 1];
                        Log.v(LOG_TAG, "@@@ Selected State " + CommonConstants.statePlotId);
                        districtDataMap = dataAccessHandler.getPairData(Queries.getInstance().getUserDistricts(TextUtils.join(",", districtIds), CommonConstants.statePlotId));
                        ArrayAdapter<String> spinnerDistrictArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(districtDataMap, "District"));
                        spinnerDistrictArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSpinner.setAdapter(spinnerDistrictArrayAdapter);
                        CommonConstants.stateName = stateSpinner.getSelectedItem().toString();
                        Pair statePair = stateDataMap.get(CommonConstants.statePlotId);
                        CommonConstants.stateCodePlot = statePair.first.toString();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (userDisSelect) {
                    userDisSelect = false;
                } else if (districtDataMap != null && districtDataMap.size() > 0 && districtSpinner.getSelectedItemPosition() != 0) {
                    CommonConstants.districtIdPlot = districtDataMap.keySet().toArray(new String[districtDataMap.size()])[i - 1];
                    Log.v(LOG_TAG, "@@@ Selected State " + CommonConstants.districtIdPlot);
                    mandalDataMap = dataAccessHandler.getPairData(Queries.getInstance().setUserMandals(TextUtils.join(",", mandalIds), CommonConstants.districtIdPlot));
                    ArrayAdapter<String> spinnerMandalArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(mandalDataMap, "Mandal"));
                    spinnerMandalArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mandalSpinner.setAdapter(spinnerMandalArrayAdapter);
                    CommonConstants.districtName = districtSpinner.getSelectedItem().toString();
                    Pair districtPair = districtDataMap.get(CommonConstants.districtIdPlot);
                    CommonConstants.districtCodePlot = districtPair.first.toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mandalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (userManSelect) {
                    userManSelect = false;
                } else if (mandalDataMap != null && mandalDataMap.size() > 0 && mandalSpinner.getSelectedItemPosition() != 0) {
                    CommonConstants.mandalIdPlot = mandalDataMap.keySet().toArray(new String[mandalDataMap.size()])[i - 1];
                    Log.v(LOG_TAG, "@@@ Selected State " + CommonConstants.mandalIdPlot);
                    CommonConstants.mandalName = mandalSpinner.getSelectedItem().toString();
                    Pair mandalPair = mandalDataMap.get(CommonConstants.mandalIdPlot);
                    CommonConstants.mandalCodePlot = mandalPair.first.toString();
                    CommonConstants.prevMandalPos = i;
                    villagesDataMap = dataAccessHandler.getPairData(Queries.getInstance().setUserVillages(TextUtils.join(",", userVillages), CommonConstants.mandalIdPlot));

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(villagesDataMap, "Village"));
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
                if (userVillageSelect) {
                    userVillageSelect = false;
                } else if (villagesDataMap != null && villagesDataMap.size() > 0 && villageSpinner.getSelectedItemPosition() != 0) {
                    CommonConstants.villageIdPlot = villagesDataMap.keySet().toArray(new String[villagesDataMap.size()])[i - 1];
                    villageCodeStr = villageSpinner.getSelectedItem().toString();
                    Pair villagePair = villagesDataMap.get(CommonConstants.villageIdPlot);
                    CommonConstants.villageCodePlot = villagePair.first.toString();
                    CommonConstants.villageName = villageSpinner.getSelectedItem().toString();
                    CommonConstants.prevVillagePos = i;
                    if (!CommonUtils.isFromFollowUp() && !CommonUtils.isFromConversion() && !CommonUtils.isFromCropMaintenance()) {
                        financalSubStringYear = String.valueOf(financialYear).substring(2, 4);
                        CommonConstants.PLOT_CODE = dataAccessHandler.getGeneratedPlotId(Queries.getInstance().getMaxNumberForPlotQuery(financalSubStringYear + days), financalYrDays);
                    }
                    plotCodeTxt.setText(CommonConstants.PLOT_CODE);
                    String ClusterName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getClusterName(CommonConstants.villageIdPlot));
                    ClusterNameTv.setText("" + ClusterName);
                    setPinCode();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ownerShipMap = dataAccessHandler.getGenericData(Queries.getInstance().getPlotOwnerShip());
        String[] ownership = CommonUtils.fromMap(ownerShipMap, "Owner ship");
        ArrayAdapter<String> ownerShipAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ownership);
        ownerShipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownerShipSpin.setAdapter(ownerShipAdapter);

        incomeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getAnualIncome());
        String[] income = CommonUtils.fromMap(incomeDataMap, "Income");
        ArrayAdapter<String> incomeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, income);
        incomeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeSpin.setAdapter(incomeAdapter);

    }


    @Override
    public void onDataSelected(int type, Bundle bundle) {
        if (type == CURRENT_CROP_TYPE) { //cropId
            dataPair.add(Pair.create(bundle.getString("cropName"), bundle.getString("areaAllocated")));
            currentCropList.add(new CropModel(bundle.getString("cropName"), Integer.parseInt(bundle.getString("cropId")), bundle.getString("areaAllocated"), 1));
            genericAdapter.updateAdapter(dataPair);
            currentCropRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            currentCropRecyclerView.setAdapter(genericAdapter);
        } else if (type == NEIGHBOUR_PLOT_TYPE) {
            neighbourPlotsList.add(new CropModel(bundle.getString("cropName"), Integer.parseInt(bundle.getString("cropId")), bundle.getString("neightbourPlot")));
            neighbourPlotPair.add(Pair.create(bundle.getString("neightbourPlot"), bundle.getString("cropName")));
            neighbourPlotAdapter.updateAdapter(neighbourPlotPair);
        }
    }

    @Override
    public void onEditClicked(int position, int tag) {
        EditEntryDialogFragment editEntryDialogFragment = new EditEntryDialogFragment();
        editEntryDialogFragment.setOnDataEditChangeListener(this);
        Bundle inputBundle = new Bundle();
        selectedPosition = position;
        selectedType = tag;
        if (tag == 1) {
            inputBundle.putInt("typeDialog", EditEntryDialogFragment.TYPE_EDIT_BOX);
            inputBundle.putString("title", dataPair.get(position).first);
            inputBundle.putString("prevData", dataPair.get(position).second + "-" + "Area of Current Crop");
            editEntryDialogFragment.setArguments(inputBundle);
        } else {
            inputBundle.putInt("typeDialog", EditEntryDialogFragment.TYPE_SPINNER);
            inputBundle.putString("title", neighbourPlotPair.get(position).first.toString());
            inputBundle.putString("prevData", neighbourPlotPair.get(position).second.toString());
            editEntryDialogFragment.setArguments(inputBundle);
        }
        FragmentManager mFragmentManager = getChildFragmentManager();
        editEntryDialogFragment.show(mFragmentManager, "fragment_edit_name");
    }

    @Override
    public void onDeleteClicked(int position, int tag) {
        Log.v(LOG_TAG, "@@@ delete clicked " + position);
        if (tag == 0) {
            CropModel cropModel = neighbourPlotsList.get(position);
            if (fromDataBase) {
                cropModel.isActive = 0;
                neighbourPlotsList.set(position, cropModel);
            } else {
                neighbourPlotsList.remove(position);
            }
           /* if(dataPair.size()>0){
            dataPair.remove(position);
            }*/
            neighbourPlotPair.remove(position);
            neighbourPlotAdapter.notifyDataSetChanged();
        } else {
            CropModel cropModel = currentCropList.get(position);
            if (fromDataBase) {
                cropModel.isActive = 0;
                currentCropList.set(position, cropModel);
            } else {
                currentCropList.remove(position);
            }
            dataPair.remove(position);
            genericAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDataEdited(Bundle dataBundle) {
        if (selectedType == 1) {
            currentCropList.set(selectedPosition, new CropModel(currentCropList.get(selectedPosition).cropName, currentCropList.get(selectedPosition).cropId, dataBundle.getString("inputValue"), 1));
            dataPair.set(selectedPosition, Pair.create(dataPair.get(selectedPosition).first, dataBundle.getString("inputValue")));
            genericAdapter.notifyDataSetChanged();
        } else {
            neighbourPlotsList.set(selectedPosition, new CropModel(neighbourPlotsList.get(selectedPosition).cropName, neighbourPlotsList.get(selectedPosition).cropId, dataBundle.getString("inputValue")));
            neighbourPlotPair.set(selectedPosition, Pair.create(neighbourPlotPair.get(selectedPosition).first, dataBundle.getString("inputValue")));
            neighbourPlotAdapter.notifyDataSetChanged();
        }
    }

    public List<CropModel> convertToCropModel(List<NeighbourPlot> data) {
        List<CropModel> npDataList = new ArrayList<>();
        for (NeighbourPlot neighbourPlot : data) {
            npDataList.add(new CropModel(neighbourPlot.getName(), neighbourPlot.getCropid(), cropDataMap.get(String.valueOf(neighbourPlot.getCropid())), neighbourPlot.getIsactive()));
            neighbourPlotPair.add(Pair.create(neighbourPlot.getName(), cropDataMap.get(String.valueOf(neighbourPlot.getCropid()))));
        }
        return npDataList;
    }

    public List<CropModel> convertToCurrentCropModel(List<PlotCurrentCrop> data) {
        List<CropModel> currentCropList = new ArrayList<>();
        for (PlotCurrentCrop currentCrop : data) {
            currentCropList.add(new CropModel(cropDataMap.get(String.valueOf(currentCrop.getCropid())), currentCrop.getCropid(), String.valueOf(currentCrop.getCurrentcroparea()), currentCrop.getIsactive()));
            dataPair.add(Pair.create(cropDataMap.get(String.valueOf(currentCrop.getCropid())), String.valueOf(currentCrop.getCurrentcroparea())));
        }
        return currentCropList;
    }

    @Override
    public void onEditClicked(int position) {
        Log.v(LOG_TAG, "@@@ edit clicked " + position);
        showEditDialog(position);
    }


    @Override
    public void onDeleteClicked(int position) {
        Log.v(LOG_TAG, "@@@ delete clicked " + position);
        identityProofsList.remove(position);
        idProofsListAdapter.notifyDataSetChanged();

    }

    private void showEditDialog(final int position) {
        mainData = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("12"));
        final EditText idEdit = new EditText(getActivity());
        final String title = mainData.get(String.valueOf(identityProofsList.get(position).getIDProofTypeId()));
        idEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        idEdit.setText(identityProofsList.get(position).getIdProofNumber());
        @SuppressLint("RestrictedApi") final AlertDialog.Builder idProofsBuilder = new AlertDialog.Builder(getActivity())
                .setTitle("Edit")
                .setMessage(title)
                .setView(idEdit, 20, 0, 20, 0)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        final AlertDialog idProofsDialog = idProofsBuilder.create();
        idProofsDialog.setCancelable(false);
        idProofsDialog.setCanceledOnTouchOutside(false);
        idProofsDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button muteBtn = idProofsDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                muteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(idEdit.getText().toString())) {
                            if (!TextUtils.isEmpty(title) && title.equalsIgnoreCase(getResources().getString(R.string.adhar_number))
                                    && !TextUtils.isDigitsOnly(idEdit.getText().toString())) {
                                Toast.makeText(getActivity(), "Adhar card accepts only numbers", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            LandlordIdProof identityProof = new LandlordIdProof();
                            identityProof.setIDProofTypeId(identityProofsList.get(position).getIDProofTypeId());
                            identityProof.setIdProofNumber(idEdit.getText().toString());
                            identityProofsList.set(position, identityProof);
                            idProofsListAdapter.updateData(identityProofsList, mainData);
                            idProofsDialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Please enter id proof value", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        idProofsDialog.show();

    }


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (picker) {

            //start date
            LLleaseDate.setText(sdf.format(myCalendar.getTime()));
            LLleaseDateStr = LLleaseDate.getText().toString();
            LLleaseEndDate.setText("");
            LLleaseEndDateStr = "";
            //  lease = leaseDateFromStr;
        } else {
            LLleaseEndDateStr = sdf.format(myCalendar.getTime());

            if (TextUtils.isEmpty(LLleaseDateStr)) {
                Toast.makeText(getActivity(), "Please select lease start date", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DateFormats.compareMMddyyyyFormatDateStrings(LLleaseDateStr, LLleaseEndDateStr)) {

                LLleaseEndDate.setText(LLleaseEndDateStr);
            } else {
                LLleaseEndDateStr = "";
                LLleaseEndDate.setText("");

                Toast.makeText(getActivity(), "Lease end date should be greater than start date", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void bindGeoGraphicalLocation() {
        userVillages = dataAccessHandler.getSingleListData(Queries.getInstance().getUserVillageIds(CommonConstants.USER_ID));
        mandalIds = dataAccessHandler.getSingleListData(Queries.getInstance().getUserMandalIds(TextUtils.join(",", userVillages)));
        districtIds = dataAccessHandler.getSingleListData(Queries.getInstance().getUserDistrictIds(TextUtils.join(",", mandalIds)));
        stateIds = dataAccessHandler.getSingleListData(Queries.getInstance().getUserStateIds(TextUtils.join(",", districtIds)));
        stateDataMap = dataAccessHandler.getPairData(Queries.getInstance().getUserStates(TextUtils.join(",", stateIds)));
        ArrayAdapter<String> spinnerStateArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(stateDataMap, "State"));
        spinnerStateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(spinnerStateArrayAdapter);
    }

    public boolean validateDoubles() {
        if (!TextUtils.isEmpty(plotAreaStr) && !plotAreaStr.equalsIgnoreCase(".") && !TextUtils.isEmpty(totalAreaStr) && !totalAreaStr.equalsIgnoreCase(".")) {
            try {
                plotArea = Double.parseDouble(plotAreaStr);
                totalArea = Double.parseDouble(totalAreaStr);
                if (plotArea >= totalArea) {
                    getAreaLeft(plotArea, totalArea);
                } else {
                    Toast.makeText(getActivity(), "Total Area Under Palm Should Not Exceed Plot Area", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "" + e.getMessage());
            }
        } else if (!TextUtils.isEmpty(plotAreaStr) && !plotAreaStr.equalsIgnoreCase(".")) {
            plotArea = Double.parseDouble(plotAreaStr);
            getAreaLeft(plotArea, 0.0);
        }

        return true;
    }

    public double getAreaLeft(final Double plotAreaDouble, final Double totalAreaDouble) {
        double diff = plotAreaDouble - totalAreaDouble;
        return diff;
    }
}
