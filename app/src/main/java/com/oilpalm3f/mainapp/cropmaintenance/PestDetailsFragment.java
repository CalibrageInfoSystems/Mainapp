package com.oilpalm3f.mainapp.cropmaintenance;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.UpdateUiListener;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUiUtils;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.conversion.PalmDetailsEditListener;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.Disease;
import com.oilpalm3f.mainapp.dbmodels.MainPestModel;
import com.oilpalm3f.mainapp.dbmodels.Pest;
import com.oilpalm3f.mainapp.dbmodels.PestChemicalXref;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.oilpalm3f.mainapp.common.CommonUtils.spinnerSelect;
import static com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation.adapterSetFromHashmap;
import static com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation.getKey;


/**
 * A simple {@link Fragment} subclass.
 */

//Used to enter Pest releated details during Crop maintenance
public class PestDetailsFragment extends Fragment implements View.OnClickListener, PalmDetailsEditListener, UpdateUiListener {


    private View rootView;
    private Spinner pestNameSpin, nameOfChemicalUsedSpin, rcmnduomSpin, percentageOfTreeSpin, controlMeasureSpin;
    private EditText ObservationsEdt;
    private LinearLayout parentLayout;
    private Button saveBtn, historyBtn;
    private RecyclerView pestDetailsList;
    private LinkedHashMap<String, String> pestNameDataMap, chemicalNameDataMap, percentageMap, recmChemicalMap, rcmnduomperDatamap;
    private DataAccessHandler dataAccessHandler;
    private ArrayList<Pest> mPestModelArray = new ArrayList<>();
    private ArrayList<PestChemicalXref> mPestChemicalXrefModelArray = new ArrayList<>();
    private ArrayList<MainPestModel> mainPestModelList;
    private GenericTypeAdapter pestDataAdapter;
    private ArrayList<Character> ratingList;
    private char prc_tree = ' ';
    private Context mContext;
    //    public static final String MAIN_PEST_DETAIL = "main_pest_detail";
    private UpdateUiListener updateUiListener;
    private static int PEST_DATA = 3;
    private Button complaintsBtn;
    private Spinner nameOfChemicalUsedSpinRecmnd, rcmnduomperSpin;
    private LinkedHashMap uomDataMap;
    private EditText rcmndosageEdt;
    Toolbar toolbar;
    private ActionBar actionBar;

    private ArrayList<Pest> pestlastvisitdatamap;


    public PestDetailsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_pest_details, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });
      //  actionBar.setTitle(getString(R.string.nutrient_details));

//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
//
//        //toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbar);
//        actionBar = activity.getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        ratingList = new ArrayList<>();

        actionBar.setTitle(getActivity().getResources().getString(R.string.pest_details));
        mContext = getActivity();
        initViews();
        setViews();
        //setHasOptionsMenu(true);
        dataAccessHandler = new DataAccessHandler(mContext);
        bindData();

        return rootView;
    }


    private void bindData() {
        mainPestModelList = (ArrayList<MainPestModel>) DataManager.getInstance().getDataFromManager(DataManager.MAIN_PEST_DETAIL);
        if (null == mainPestModelList)
            mainPestModelList = new ArrayList<MainPestModel>();

        pestDataAdapter = new GenericTypeAdapter(getActivity(), mainPestModelList, pestNameDataMap, chemicalNameDataMap, percentageMap, uomDataMap, GenericTypeAdapter.TYPE_PEST);
        pestDetailsList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        pestDetailsList.setAdapter(pestDataAdapter);
        pestDataAdapter.setEditClickListener(this);


    }

    private void initViews() {
        dataAccessHandler = new DataAccessHandler(getActivity());
        pestNameSpin = (Spinner) rootView.findViewById(R.id.pestNameSpin);
        nameOfChemicalUsedSpin = (Spinner) rootView.findViewById(R.id.nameOfChemicalUsedSpin);
        nameOfChemicalUsedSpinRecmnd = (Spinner) rootView.findViewById(R.id.nameOfChemicalUsedSpinRecmnd);

        rcmnduomSpin = (Spinner) rootView.findViewById(R.id.rcmnduomSpin);
        percentageOfTreeSpin = (Spinner) rootView.findViewById(R.id.percentageOfTreeSpin);
        controlMeasureSpin = (Spinner) rootView.findViewById(R.id.controlMeasureSpin);
        rcmndosageEdt = (EditText) rootView.findViewById(R.id.rcmndosageEdt);
        ObservationsEdt = (EditText) rootView.findViewById(R.id.ObservationsEdt);
        saveBtn = (Button) rootView.findViewById(R.id.saveBtn);
        historyBtn = (Button) rootView.findViewById(R.id.historyBtn);
        pestDetailsList = (RecyclerView) rootView.findViewById(R.id.pestDetailsList);
        parentLayout = (LinearLayout) rootView.findViewById(R.id.pestParentLayout);
        complaintsBtn = (Button) rootView.findViewById(R.id.complaintsBtn);
        complaintsBtn.setEnabled(false);
        complaintsBtn.setVisibility(View.GONE);

      //  complaintsBtn.setVisibility((CommonUiUtils.isComplaintsDataEntered()) ? View.GONE : View.VISIBLE);
        rcmnduomperSpin = (Spinner) rootView.findViewById(R.id.rcmnduomperSpin);

        complaintsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();
                dataBundle.putBoolean(CommonConstants.KEY_NEW_COMPLAINT, true);
                ComplaintDetailsFragment complaintDetailsFragment = new ComplaintDetailsFragment();
                complaintDetailsFragment.setArguments(dataBundle);
                complaintDetailsFragment.setUpdateUiListener(PestDetailsFragment.this);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, complaintDetailsFragment).addToBackStack(null)
                        .commit();
            }
        });
    }

    private void setViews() {

        parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                CommonUtilsNavigation.hideKeyBoard(getActivity());
                return false;
            }
        });
        saveBtn.setOnClickListener(this);
        //historyBtn.setOnClickListener(this);
        nameOfChemicalUsedSpin.setOnItemSelectedListener(spinListener);
        nameOfChemicalUsedSpinRecmnd.setOnItemSelectedListener(spinListener);
        pestNameSpin.setOnItemSelectedListener(spinListener);
        pestNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("6"));
        chemicalNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("7"));
        percentageMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("570"));


        pestNameSpin.setAdapter(adapterSetFromHashmap(getActivity(), "Pest Name", pestNameDataMap));
        nameOfChemicalUsedSpin.setAdapter(adapterSetFromHashmap(getActivity(), "Name of Chemical", chemicalNameDataMap));
        percentageOfTreeSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Percentage of tree", percentageMap));

        nameOfChemicalUsedSpinRecmnd.setAdapter(adapterSetFromHashmap(getActivity(), "Name of Chemical", chemicalNameDataMap));
        uomDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getUOM());
        rcmnduomSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "UOM", uomDataMap));

        rcmnduomperDatamap = dataAccessHandler.getGenericData(Queries.getInstance().getUOMper());
        rcmnduomperSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "UOM Per", rcmnduomperDatamap));

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
        dialog.setContentView(R.layout.pestlastvisteddata);

        Toolbar titleToolbar;
        titleToolbar = (Toolbar) dialog.findViewById(R.id.titleToolbar);
        titleToolbar.setTitle("Pest History");
        titleToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        LinearLayout mainLL = (LinearLayout) dialog.findViewById(R.id.pestmainlyt);
        LinearLayout pestchemicalusedll = (LinearLayout) dialog.findViewById(R.id.pestchemicalusedll);
        LinearLayout pestpercentageoftreesll = (LinearLayout) dialog.findViewById(R.id.pestpercentageoftreesll);
        LinearLayout pestcontrolmeasuresll = (LinearLayout) dialog.findViewById(R.id.pestcontrolmeasuresll);
        LinearLayout pestrecommendedchemicall = (LinearLayout) dialog.findViewById(R.id.pestrecommendedchemicall);
        LinearLayout pestrecommendeddosagell = (LinearLayout) dialog.findViewById(R.id.pestrecommendeddosagell);
        LinearLayout pestrecommendeduomll = (LinearLayout) dialog.findViewById(R.id.pestrecommendeduomll);
        LinearLayout pestrecommenduomperll = (LinearLayout) dialog.findViewById(R.id.pestrecommenduomperll);
        LinearLayout pestcommentsll = (LinearLayout) dialog.findViewById(R.id.pestcommentsll);

        TextView pestsseen = (TextView) dialog.findViewById(R.id.pestseen);
        TextView pestchemicalused = (TextView) dialog.findViewById(R.id.pestchemicalused);
        TextView pestpercentageoftrees = (TextView) dialog.findViewById(R.id.pestpercentageoftrees);
        TextView pestcontrolmeasures = (TextView) dialog.findViewById(R.id.pestcontrolmeasures);
        TextView pestrecommendedchemical = (TextView) dialog.findViewById(R.id.pestrecommendedchemical);
        TextView pestrecommendeddosage = (TextView) dialog.findViewById(R.id.pestrecommendeddosage);
        TextView pestrecommendeduom = (TextView) dialog.findViewById(R.id.pestrecommendeduom);
        TextView pestrecommenduomper = (TextView) dialog.findViewById(R.id.pestrecommenduomper);
        TextView pestcomments = (TextView) dialog.findViewById(R.id.pestcomments);
        TextView norecords = (TextView) dialog.findViewById(R.id.pestnorecord_tv);

        String lastVisitCode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getLatestCropMaintanaceHistoryCode(CommonConstants.PLOT_CODE));
        pestlastvisitdatamap = (ArrayList<Pest>) dataAccessHandler.getPestData(Queries.getInstance().getRecommndCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_PEST), 1);

        if (pestlastvisitdatamap.size() > 0){
            norecords.setVisibility(View.GONE);
            mainLL.setVisibility(View.VISIBLE);

            String pestname = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(pestlastvisitdatamap.get(0).getPestid()));

            pestsseen.setText(pestname);

            if (pestlastvisitdatamap.get(0).getPercTreesId() != 0){
                pestpercentageoftreesll.setVisibility(View.VISIBLE);
                String percentageoftress = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(pestlastvisitdatamap.get(0).getPercTreesId()));
                pestpercentageoftrees.setText(percentageoftress);
            }else{
                pestpercentageoftreesll.setVisibility(View.GONE);
            }


            if (pestlastvisitdatamap.get(0).getRecommendFertilizerProviderId() != null){
                pestrecommendedchemicall.setVisibility(View.VISIBLE);
                String fertilizerrec = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(pestlastvisitdatamap.get(0).getRecommendFertilizerProviderId()));
                pestrecommendedchemical.setText(fertilizerrec);
            }else{
                pestrecommendedchemicall.setVisibility(View.GONE);
            }

            if (pestlastvisitdatamap.get(0).getRecommendDosage() != 0.0){
                pestrecommendeddosagell.setVisibility(View.VISIBLE);
                pestrecommendeddosage.setText(pestlastvisitdatamap.get(0).getRecommendDosage() + "");
            }else{
                pestrecommendeddosagell.setVisibility(View.GONE);
            }

            if (pestlastvisitdatamap.get(0).getRecommendUOMId() != null){
                pestrecommendeduomll.setVisibility(View.VISIBLE);
                String recommendedUOM = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getUOMdata(pestlastvisitdatamap.get(0).getRecommendUOMId()));
                pestrecommendeduom.setText(recommendedUOM);
            }else{
                pestrecommendeduomll.setVisibility(View.GONE);
            }

            if (pestlastvisitdatamap.get(0).getRecommendedUOMId() != null){
                pestrecommenduomperll.setVisibility(View.VISIBLE);
                String recommendedUOMper = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getUOMdata(pestlastvisitdatamap.get(0).getRecommendedUOMId()));
                pestrecommenduomper.setText(recommendedUOMper);
            }else{
                pestrecommenduomperll.setVisibility(View.GONE);
            }

//            if (!(TextUtils.isEmpty(pestlastvisitdatamap.get(0).getComments()))){
//                pestcommentsll.setVisibility(View.VISIBLE);
//                pestcomments.setText(pestlastvisitdatamap.get(0).getComments() + "");
//            }else{
//                pestcomments.setVisibility(View.GONE);
//            }

            if (TextUtils.isEmpty(pestlastvisitdatamap.get(0).getComments()) || pestlastvisitdatamap.get(0).getComments().equalsIgnoreCase("")  || pestlastvisitdatamap.get(0).getComments().equalsIgnoreCase(null) || pestlastvisitdatamap.get(0).getComments().equalsIgnoreCase("null")){

                pestcomments.setVisibility(View.GONE);
            }else{
                pestcommentsll.setVisibility(View.VISIBLE);
               pestcomments.setText(pestlastvisitdatamap.get(0).getComments() + "");
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


    public void addingValues() {

        if (CommonConstants.perc_tree_pest == ' ') {


            if ((percentageOfTreeSpin.getSelectedItem().toString().equals("No Deficiency")) && (controlMeasureSpin.getSelectedItemPosition() == 1)) {
                CommonConstants.perc_tree_pest = 'A';
                ratingList.add('A');


            } else if ((percentageOfTreeSpin.getSelectedItem().toString().equals("Less than 5%")) && (controlMeasureSpin.getSelectedItemPosition() == 1)) {
                CommonConstants.perc_tree_pest = 'A';
                ratingList.add('A');


            } else if (percentageOfTreeSpin.getSelectedItem().toString().equals("5 to 25%") && (controlMeasureSpin.getSelectedItemPosition() == 1)) {
                CommonConstants.perc_tree_pest = 'B';
                ratingList.add('B');

            } else {
                CommonConstants.perc_tree_pest = 'C';
                ratingList.add('C');

            }
            Log.v("@@@pestRatingEmpty", "" + CommonConstants.perc_tree_pest);
        } else {
            if ((percentageOfTreeSpin.getSelectedItem().toString().equals("No Deficiency")) && (controlMeasureSpin.getSelectedItemPosition() == 1)) {
                ratingList.add('A');
            } else if (((percentageOfTreeSpin.getSelectedItem().toString()).equals("Less than 5%")) && (controlMeasureSpin.getSelectedItemPosition() == 1)) {
                ratingList.add('A');


            } else if ((percentageOfTreeSpin.getSelectedItem().toString().equals("5 to 25%")) && (controlMeasureSpin.getSelectedItemPosition() == 1)) {
                prc_tree = 'B';
                if (prc_tree > CommonConstants.perc_tree_pest) {
                    ratingList.add('B');

                    CommonConstants.perc_tree_pest = prc_tree;
                } else {
                    ratingList.add('B');
                    CommonConstants.perc_tree_pest = 'C';
                }
            } else {
                CommonConstants.perc_tree_pest = 'C';
                ratingList.add('C');
            }
        }


        Log.v("@@@pestRating", "" + CommonConstants.perc_tree_pest);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                MainPestModel mainPestModel = new MainPestModel();
                Pest mPestModel = new Pest();
                PestChemicalXref mPestChemicalXref = new PestChemicalXref();

                if (pestNameSpin.getSelectedItem().toString().equals("No Pest Deficiency")) {

                    if (validateUI1()) {
                        mPestModel.setPestid(pestNameSpin.getSelectedItemPosition() == 0 ? 0 : Integer.parseInt(getKey(pestNameDataMap, pestNameSpin.getSelectedItem().toString())));
                        mPestModel.setComments(ObservationsEdt.getText().toString());
                        mPestModel.setCreateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                      //  mPestModel.setIsControlMeasure(controlMeasureSpin.getSelectedItemPosition() == 1 ? 1 : 0);

                        mainPestModel.setPest(mPestModel);

                        mPestModel.setCreateddate(CommonUtils.getcurrentDateTime(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS)));
                        mPestModelArray.add(mPestModel);
                        mainPestModelList.add(mainPestModel);
                        DataManager.getInstance().addData(DataManager.PEST_DETAILS, mPestModelArray);
                        ratingList.add('A');

                        pestNameSpin.setSelection(0);
                        controlMeasureSpin.setSelection(0);
                        pestDataAdapter.notifyDataSetChanged();
                        updateUiListener.updateUserInterface(0);
                    }
                } else {
                    if (validateUI()) {

                        mPestModel.setPestid(pestNameSpin.getSelectedItemPosition() == 0 ? 0 : Integer.parseInt(getKey(pestNameDataMap, pestNameSpin.getSelectedItem().toString())));
                        mPestModel.setComments(ObservationsEdt.getText().toString());
                        mPestModel.setCreateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        mPestModel.setRecommendFertilizerProviderId(nameOfChemicalUsedSpinRecmnd.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(chemicalNameDataMap, nameOfChemicalUsedSpinRecmnd.getSelectedItem().toString())));
                        mPestModel.setRecommendUOMId(rcmnduomSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(uomDataMap, rcmnduomSpin.getSelectedItem().toString())));
                        mPestModel.setRecommendedUOMId(rcmnduomperSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(rcmnduomperDatamap, rcmnduomperSpin.getSelectedItem().toString())));

                        // mPestModel.setRecommendDosage(Double.parseDouble(rcmndosageEdt.getText().toString()));
                        mPestModel.setRecommendDosage(TextUtils.isEmpty(rcmndosageEdt.getText().toString()) == true ? 0.0 : Double.parseDouble(rcmndosageEdt.getText().toString()));
                        mPestModel.setPercTreesId(Integer.parseInt(getKey(percentageMap, percentageOfTreeSpin.getSelectedItem().toString())));
                        mPestModel.setIsControlMeasure(controlMeasureSpin.getSelectedItemPosition() == 1 ? 1 : 0);

                        mainPestModel.setPest(mPestModel);

                        mPestModel.setCreateddate(CommonUtils.getcurrentDateTime(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS)));
                        mPestModelArray.add(mPestModel);
                        mPestChemicalXref.setChemicalId(nameOfChemicalUsedSpin.getSelectedItemPosition() == 0 ? 0 : Integer.parseInt(getKey(chemicalNameDataMap, nameOfChemicalUsedSpin.getSelectedItem().toString())));

                        mainPestModel.setmPestChemicalXref(mPestChemicalXref);
                        mainPestModelList.add(mainPestModel);
                        mPestChemicalXrefModelArray.add(mPestChemicalXref);
                        DataManager.getInstance().addData(DataManager.PEST_DETAILS, mPestModelArray);
                        DataManager.getInstance().addData(DataManager.CHEMICAL_DETAILS, mPestChemicalXrefModelArray);
                        DataManager.getInstance().addData(DataManager.MAIN_PEST_DETAIL, mainPestModelList);
                        addingValues();

                        pestNameSpin.setSelection(0);
                        nameOfChemicalUsedSpinRecmnd.setSelection(0);
                        percentageOfTreeSpin.setSelection(0);
                        controlMeasureSpin.setSelection(0);
                        rcmnduomSpin.setSelection(0);
                        rcmnduomperSpin.setSelection(0);
                        rcmndosageEdt.setText("");
                        pestDataAdapter.notifyDataSetChanged();
                        updateUiListener.updateUserInterface(0);

                    }
                }
                CommonUtilsNavigation.hideKeyBoard(getActivity());
                break;
//            case R.id.historyBtn:
//                CropMaintainanceHistoryFragment newFragment = new CropMaintainanceHistoryFragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt("screen", PEST_DATA);
//                newFragment.setArguments(bundle);
//                newFragment.show(getActivity().getFragmentManager(), "history");
//                break;
        }
    }

    AdapterView.OnItemSelectedListener spinListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {

                case R.id.pestNameSpin:
                    if (position == 0) {
                        nameOfChemicalUsedSpin.setSelection(0);
                        nameOfChemicalUsedSpin.setEnabled(false);
                        percentageOfTreeSpin.setSelection(0);
                        percentageOfTreeSpin.setEnabled(false);
                        nameOfChemicalUsedSpinRecmnd.setSelection(0);
                        nameOfChemicalUsedSpinRecmnd.setEnabled(false);
                        controlMeasureSpin.setSelection(0);
                        controlMeasureSpin.setEnabled(false);
                        rcmndosageEdt.setEnabled(false);
                        rcmnduomSpin.setSelection(0);
                        rcmnduomSpin.setEnabled(false);
                        rcmnduomperSpin.setSelection(0);
                        rcmnduomperSpin.setEnabled(false);
                        ObservationsEdt.setEnabled(false);
                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha(0.5f);
                    } else if (position == 13) {

                        nameOfChemicalUsedSpin.setSelection(0);
                        nameOfChemicalUsedSpin.setEnabled(false);
                        percentageOfTreeSpin.setSelection(0);
                        percentageOfTreeSpin.setEnabled(false);
                        nameOfChemicalUsedSpinRecmnd.setSelection(0);
                        nameOfChemicalUsedSpinRecmnd.setEnabled(false);
                        controlMeasureSpin.setSelection(0);
                        controlMeasureSpin.setEnabled(false);
                        rcmndosageEdt.setEnabled(false);
                        rcmnduomSpin.setSelection(0);
                        rcmnduomSpin.setEnabled(false);
                        rcmnduomperSpin.setSelection(0);
                        rcmnduomperSpin.setEnabled(false);
                        ObservationsEdt.setEnabled(true);
                        saveBtn.setEnabled(true);
                        saveBtn.setAlpha(1.0f);


                    } else {
                        nameOfChemicalUsedSpin.setEnabled(true);
                        nameOfChemicalUsedSpinRecmnd.setEnabled(true);
                        controlMeasureSpin.setEnabled(true);
                        percentageOfTreeSpin.setEnabled(true);
                        rcmnduomSpin.setEnabled(true);
                        rcmnduomperSpin.setEnabled(true);
                        ObservationsEdt.setEnabled(true);
                        rcmndosageEdt.setEnabled(true);
                        saveBtn.setEnabled(true);
                        saveBtn.setAlpha(1.0f);
                    }
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private boolean validateUI() {
        return spinnerSelect(pestNameSpin, "Source of Pest", mContext)
                && spinnerSelect(nameOfChemicalUsedSpin, "Select Name of Chemical", mContext)
                && spinnerSelect(percentageOfTreeSpin, "Percentage of tree", mContext) && spinnerSelect(controlMeasureSpin, "Control Measure", mContext);

    }

    private boolean validateUI1() {
        return spinnerSelect(pestNameSpin, "Source of Pest", mContext);

    }


    @Override
    public void onEditClicked(int position) {

        mainPestModelList.remove(position);


        ratingList.remove(position);


        if (null != mPestModelArray && mPestModelArray.size() > 0) {
            mPestModelArray.remove(position);
        }

        if (null != mPestChemicalXrefModelArray && mPestChemicalXrefModelArray.size() > 0) {
            mPestChemicalXrefModelArray.remove(position);
        } else {
        }

        Log.v("mainpestModel", "pestMain   " + mainPestModelList.size() + " pest " + mPestModelArray.size() + "chemical  " + mPestChemicalXrefModelArray.size() + "rating  " + ratingList.size());


        if (mPestModelArray.isEmpty()) {
            CommonConstants.perc_tree_pest = ' ';
        }

        if (mPestModelArray.size() == 1) {
            CommonConstants.perc_tree_pest = ratingList.get(0);
        } else if (mPestModelArray.size() > 1) {
            for (int i = 0; i < ratingList.size() - 1; i++) {
                for (int j = i + 1; j < ratingList.size(); j++) {

                    if (ratingList.get(i) > ratingList.get(j)) {
                        CommonConstants.perc_tree_pest = ratingList.get(i);
                    } else {
                        CommonConstants.perc_tree_pest = ratingList.get(j);
                    }
                }
            }
        }


        pestDataAdapter.notifyDataSetChanged();


    }

    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }

    @Override
    public void updateUserInterface(int refreshPosition) {
        complaintsBtn.setVisibility(View.GONE);
    }

    /**
     * Created by Calibrage11 on 7/22/2017.
     */


}
