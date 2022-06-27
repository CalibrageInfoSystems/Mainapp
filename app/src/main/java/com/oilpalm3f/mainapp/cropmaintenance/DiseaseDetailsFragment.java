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
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUiUtils;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.conversion.PalmDetailsEditListener;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DataSavingHelper;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.Disease;
import com.oilpalm3f.mainapp.dbmodels.Nutrient;
import com.oilpalm3f.mainapp.ui.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;

import static com.oilpalm3f.mainapp.common.CommonUtils.spinnerSelect;
import static com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation.adapterSetFromHashmap;
import static com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation.getKey;


/**
 * A simple {@link Fragment} subclass.
 */

//Used to diseases found while crop maintenance visit
public class DiseaseDetailsFragment extends Fragment implements View.OnClickListener, PalmDetailsEditListener, UpdateUiListener {
    private static int DISEASE_DATA = 4;
    ArrayList<Disease> mDiseaseModelArray = new ArrayList<>();
    private View rootView;
    private Spinner diseaseNameSpin, nameOfChemicalUsedSpin,percOfTreeSpin,controlMeasureSpin;
    private EditText observationsEdt;
    private LinearLayout parentLayout;
    private LinearLayout headerLL;
    private RecyclerView diaseaseList;
    private ArrayList<Character> ratingList;
    private char prc_tree = ' ' ;

    private ArrayList<Disease> diseaselastvisitdatamap;


    private Button saveBtn, historyBtn;
    private Button complaintsBtn;
    private Spinner nameOfChemicalUsedSpinRecmnd, rcmnduomperSpin;
    private LinkedHashMap uomDataMap, rcmnduomperDatamap;
    private EditText rcmndosageEdt;
    AdapterView.OnItemSelectedListener spinListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {

                case R.id.diseaseNameSpin:
                    if (position == 0) {
                        nameOfChemicalUsedSpin.setSelection(0);
                        nameOfChemicalUsedSpin.setEnabled(false);
                        percOfTreeSpin.setSelection(0);
                        percOfTreeSpin.setEnabled(false);
                        controlMeasureSpin.setSelection(0);
                        controlMeasureSpin.setEnabled(false);
                        nameOfChemicalUsedSpinRecmnd.setSelection(0);
                        nameOfChemicalUsedSpinRecmnd.setEnabled(false);
                        rcmndosageEdt.setEnabled(false);
                        rcmnduomSpin.setSelection(0);
                        rcmnduomSpin.setEnabled(false);
                        rcmnduomperSpin.setSelection(0);
                        rcmnduomperSpin.setEnabled(false);
                        observationsEdt.setEnabled(false);
                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha(0.5f);
                    }else if(position == 14){
                        nameOfChemicalUsedSpin.setSelection(0);
                        nameOfChemicalUsedSpin.setEnabled(false);
                        percOfTreeSpin.setSelection(0);
                        percOfTreeSpin.setEnabled(false);
                        controlMeasureSpin.setSelection(0);
                        controlMeasureSpin.setEnabled(false);
                        nameOfChemicalUsedSpinRecmnd.setSelection(0);
                        nameOfChemicalUsedSpinRecmnd.setEnabled(false);
                        rcmndosageEdt.setEnabled(false);
                        rcmnduomSpin.setSelection(0);
                        rcmnduomSpin.setEnabled(false);
                        rcmnduomperSpin.setSelection(0);
                        rcmnduomperSpin.setEnabled(false);
                        observationsEdt.setEnabled(true);
                        saveBtn.setEnabled(true);
                        saveBtn.setAlpha(1.0f);

                    }
                    else {
                        nameOfChemicalUsedSpin.setEnabled(true);
                        nameOfChemicalUsedSpinRecmnd.setEnabled(true);
                        controlMeasureSpin.setEnabled(true);
                        rcmndosageEdt.setEnabled(true);
                        percOfTreeSpin.setEnabled(true);
                        rcmnduomSpin.setEnabled(true);
                        rcmnduomperSpin.setEnabled(true);
                        observationsEdt.setEnabled(true);
                        saveBtn.setEnabled(true);
                        saveBtn.setAlpha(1.0f);
                    }

                    break;
                case R.id.percOfTreeSpin:


                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    private LinkedHashMap<String, String> diseaseNameDataMap, chemicalNameDataMap,percentageMap;
    private DataAccessHandler dataAccessHandler;
    private GenericTypeAdapter diseaseDataAdapter;
    private Context mContext;
    private UpdateUiListener updateUiListener;
    private Calendar myCalendar = Calendar.getInstance();
    private Spinner rcmnduomSpin;
    Toolbar toolbar;
    private ActionBar actionBar;
    


    public DiseaseDetailsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.fragment_disease_details, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ratingList = new ArrayList<>();


        actionBar.setTitle(getActivity().getResources().getString(R.string.disease_details_screen));

        mContext = getActivity();

        setHasOptionsMenu(true);
        initViews();
        setViews();
        dataAccessHandler = new DataAccessHandler(mContext);
        bindData();

        return rootView;
    }



    private void bindData() {
        mDiseaseModelArray = (ArrayList<Disease>) DataManager.getInstance().getDataFromManager(DataManager.DISEASE_DETAILS);
//        if (mDiseaseModelArray == null && (isFromFollowUp() || isFromCropMaintenance() || isFromConversion())) {
//            mDiseaseModelArray = (ArrayList<Disease>) dataAccessHandler.getDiseaseData(Queries.getInstance().getDiseaseData(CommonConstants.PLOT_CODE), 1);
//        }

        if (null == mDiseaseModelArray)
            mDiseaseModelArray = new ArrayList<Disease>();


        diseaseDataAdapter = new GenericTypeAdapter(getActivity(), mDiseaseModelArray, diseaseNameDataMap, chemicalNameDataMap,percentageMap,uomDataMap, GenericTypeAdapter.TYPE_DISEASE);
        diaseaseList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        diaseaseList.setAdapter(diseaseDataAdapter);
        diseaseDataAdapter.setEditClickListener(this);
    }

    private void initViews() {
        dataAccessHandler = new DataAccessHandler(getActivity());
        diseaseNameSpin = (Spinner) rootView.findViewById(R.id.diseaseNameSpin);
        nameOfChemicalUsedSpin = (Spinner) rootView.findViewById(R.id.nameOfChemicalUsedSpin);
        nameOfChemicalUsedSpinRecmnd = (Spinner) rootView.findViewById(R.id.nameOfChemicalUsedSpinRecmnd);
        percOfTreeSpin = (Spinner) rootView.findViewById(R.id.percOfTreeSpin);
        controlMeasureSpin= (Spinner) rootView.findViewById(R.id.controlMeasureSpin);

        rcmnduomSpin = (Spinner) rootView.findViewById(R.id.rcmnduomSpin);
        rcmnduomperSpin = (Spinner) rootView.findViewById(R.id.rcmnduomperSpin);
        rcmndosageEdt=(EditText)rootView.findViewById(R.id.rcmndosageEdt);
        observationsEdt = (EditText) rootView.findViewById(R.id.ObservationsEdt);
        diaseaseList = (RecyclerView) rootView.findViewById(R.id.diaseaseList);
        saveBtn = (Button) rootView.findViewById(R.id.saveBtn);
        historyBtn = (Button) rootView.findViewById(R.id.diseasehistoryBtn);

        parentLayout = (LinearLayout) rootView.findViewById(R.id.diseaseParentLayout);
        headerLL = (LinearLayout) rootView.findViewById(R.id.headerLL);
        complaintsBtn = (Button) rootView.findViewById(R.id.complaintsBtn);
        complaintsBtn.setEnabled(false);
        complaintsBtn.setVisibility(View.GONE);
       // complaintsBtn.setVisibility((CommonUiUtils.isComplaintsDataEntered()) ? View.GONE : View.VISIBLE);
        complaintsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();
                dataBundle.putBoolean(CommonConstants.KEY_NEW_COMPLAINT, true);
                ComplaintDetailsFragment complaintDetailsFragment = new ComplaintDetailsFragment();
				complaintDetailsFragment.setArguments(dataBundle);
				complaintDetailsFragment.setUpdateUiListener(DiseaseDetailsFragment.this);
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
        percOfTreeSpin.setOnItemSelectedListener(spinListener);

        diseaseNameSpin.setOnItemSelectedListener(spinListener);
        diseaseNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("5"));
        chemicalNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("7"));
        chemicalNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("7"));
        percentageMap=dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("571"));
       // controlMeasureMap=dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData(""));


        diseaseNameSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Disease Name", diseaseNameDataMap));
        nameOfChemicalUsedSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Name of chemical", chemicalNameDataMap));
        nameOfChemicalUsedSpinRecmnd.setAdapter(adapterSetFromHashmap(getActivity(), "Name of Chemical", chemicalNameDataMap));
        percOfTreeSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Percentage of Tree", percentageMap));
      //  controlMeasureSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Control Measure", controlMeasureMap));

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
    public void addingValues(){
        if(CommonConstants.perc_tree_disease == ' '){


            if ((percOfTreeSpin.getSelectedItem().toString().equals("No Deficiency"))&&(controlMeasureSpin.getSelectedItemPosition() == 1)) {
                CommonConstants.perc_tree_disease = 'A';
                ratingList.add('A');


            }
            else if((percOfTreeSpin.getSelectedItem().toString() .equals("Less than 5%"))&&(controlMeasureSpin.getSelectedItemPosition() == 1))
            {
                CommonConstants.perc_tree_disease='A';
                ratingList.add('A');




            }
            else if((percOfTreeSpin.getSelectedItem().toString().equals("5 to 25%"))&&(controlMeasureSpin.getSelectedItemPosition() == 1)){
                CommonConstants.perc_tree_disease='B';
                ratingList.add('B');

            }else{
                CommonConstants.perc_tree_disease='C';
                ratingList.add('C');

            }
            Log.v("@@@rating",""+ratingList.size());


        }
        else {
            if ((percOfTreeSpin.getSelectedItem().toString().equals("No Deficiency"))&&(controlMeasureSpin.getSelectedItemPosition() == 1)) {
                ratingList.add('A');
            }else if (((percOfTreeSpin.getSelectedItem().toString()).equals("Less than 5%"))&&(controlMeasureSpin.getSelectedItemPosition() == 1)) {
                ratingList.add('A');

            }else if ((percOfTreeSpin.getSelectedItem().toString().equals("5 to 25%"))&&(controlMeasureSpin.getSelectedItemPosition() == 1)) {
                prc_tree = 'B';

                if (prc_tree > CommonConstants.perc_tree_disease) {
                    ratingList.add('B');

                    CommonConstants.perc_tree_disease = prc_tree;
                }else {
                    CommonConstants.perc_tree_disease = 'C';
                    ratingList.add('B');
                }
            }else {
                CommonConstants.perc_tree_disease = 'C';
                ratingList.add('C');
            }
        }


        Log.v("@@@diseaseRating",""+CommonConstants.perc_tree_disease);


    }

    public void showDialog(Context activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.diseaselastvisteddata);

        Toolbar titleToolbar;
        titleToolbar = (Toolbar) dialog.findViewById(R.id.titleToolbar);
        titleToolbar.setTitle("Disease History");
        titleToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        LinearLayout mainLL = (LinearLayout) dialog.findViewById(R.id.diseasemainlyt);
        LinearLayout diseasechemicalusedll = (LinearLayout) dialog.findViewById(R.id.diseasechemicalusedll);
        LinearLayout diseasepercentageoftreesll = (LinearLayout) dialog.findViewById(R.id.diseasepercentageoftreesll);
        LinearLayout diseasecontrolmeasuresll = (LinearLayout) dialog.findViewById(R.id.diseasecontrolmeasuresll);
        LinearLayout diseaserecommendedchemicall = (LinearLayout) dialog.findViewById(R.id.diseaserecommendedchemicall);
        LinearLayout diseaserecommendeddosagell = (LinearLayout) dialog.findViewById(R.id.diseaserecommendeddosagell);
        LinearLayout diseaserecommendeduomll = (LinearLayout) dialog.findViewById(R.id.diseaserecommendeduomll);
        LinearLayout diseaserecommenduomperll = (LinearLayout) dialog.findViewById(R.id.diseaserecommenduomperll);
        LinearLayout diseasecommentsll = (LinearLayout) dialog.findViewById(R.id.diseasecommentsll);

        TextView diseasesseen = (TextView) dialog.findViewById(R.id.diseasesseen);
        TextView diseasechemicalused = (TextView) dialog.findViewById(R.id.diseasechemicalused);
        TextView diseasepercentageoftrees = (TextView) dialog.findViewById(R.id.diseasepercentageoftrees);
        TextView diseasecontrolmeasures = (TextView) dialog.findViewById(R.id.diseasecontrolmeasures);
        TextView diseaserecommendedchemical = (TextView) dialog.findViewById(R.id.diseaserecommendedchemical);
        TextView diseaserecommendeddosage = (TextView) dialog.findViewById(R.id.diseaserecommendeddosage);
        TextView diseaserecommendeduom = (TextView) dialog.findViewById(R.id.diseaserecommendeduom);
        TextView diseaserecommenduomper = (TextView) dialog.findViewById(R.id.diseaserecommenduomper);
        TextView diseasecomments = (TextView) dialog.findViewById(R.id.diseasecomments);
        TextView norecords = (TextView) dialog.findViewById(R.id.diseasenorecord_tv);

        String lastVisitCode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getLatestCropMaintanaceHistoryCode(CommonConstants.PLOT_CODE));
        diseaselastvisitdatamap = (ArrayList<Disease>) dataAccessHandler.getDiseaseData(Queries.getInstance().getRecommndCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_DISEASE), 1);

        if (diseaselastvisitdatamap.size() > 0){
            norecords.setVisibility(View.GONE);
            mainLL.setVisibility(View.VISIBLE);

            String disease = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(diseaselastvisitdatamap.get(0).getDiseaseid()));

            diseasesseen.setText(disease);

            if (diseaselastvisitdatamap.get(0).getChemicalid() != null){
                diseasechemicalusedll.setVisibility(View.VISIBLE);
                String chemicalused = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(diseaselastvisitdatamap.get(0).getChemicalid()));
                diseasechemicalused.setText(chemicalused);
            }else{
                diseasechemicalusedll.setVisibility(View.GONE);
            }
            if (diseaselastvisitdatamap.get(0).getPercTreesId() != 0){
                diseasepercentageoftreesll.setVisibility(View.VISIBLE);
                String percentageoftress = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(diseaselastvisitdatamap.get(0).getPercTreesId()));
                diseasepercentageoftrees.setText(percentageoftress);
            }else{
                diseasepercentageoftreesll.setVisibility(View.GONE);
            }


            if (diseaselastvisitdatamap.get(0).getRecommendFertilizerProviderId() != null){
                diseaserecommendedchemicall.setVisibility(View.VISIBLE);
                String fertilizerrec = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(diseaselastvisitdatamap.get(0).getRecommendFertilizerProviderId()));
                diseaserecommendedchemical.setText(fertilizerrec);
            }else{
                diseaserecommendedchemicall.setVisibility(View.GONE);
            }

            if (diseaselastvisitdatamap.get(0).getRecommendDosage() != 0.0){
                diseaserecommendeddosagell.setVisibility(View.VISIBLE);
                diseaserecommendeddosage.setText(diseaselastvisitdatamap.get(0).getRecommendDosage() + "");
            }else{
                diseaserecommendeddosagell.setVisibility(View.GONE);
            }

            if (diseaselastvisitdatamap.get(0).getRecommendUOMId() != null){
                diseaserecommendeduomll.setVisibility(View.VISIBLE);
                String recommendedUOM = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getUOMdata(diseaselastvisitdatamap.get(0).getRecommendUOMId()));
                diseaserecommendeduom.setText(recommendedUOM);
            }else{
                diseaserecommendeduomll.setVisibility(View.GONE);
            }

            if (diseaselastvisitdatamap.get(0).getRecommendedUOMId() != null){
                diseaserecommenduomperll.setVisibility(View.VISIBLE);
                String recommendedUOMper = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getUOMdata(diseaselastvisitdatamap.get(0).getRecommendedUOMId()));
                diseaserecommenduomper.setText(recommendedUOMper);
            }else{
                diseaserecommenduomperll.setVisibility(View.GONE);
            }

//            if (!(TextUtils.isEmpty(diseaselastvisitdatamap.get(0).getComments()))){
//                diseasecommentsll.setVisibility(View.VISIBLE);
//                diseasecomments.setText(diseaselastvisitdatamap.get(0).getComments() + "");
//            }else{
//                diseasecomments.setVisibility(View.GONE);
//            }

            if (TextUtils.isEmpty(diseaselastvisitdatamap.get(0).getComments()) || diseaselastvisitdatamap.get(0).getComments().equalsIgnoreCase("")  || diseaselastvisitdatamap.get(0).getComments().equalsIgnoreCase(null) || diseaselastvisitdatamap.get(0).getComments().equalsIgnoreCase("null")) {
                diseasecomments.setVisibility(View.GONE);
            }else{
                diseasecommentsll.setVisibility(View.VISIBLE);
                diseasecomments.setText(diseaselastvisitdatamap.get(0).getComments() + "");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                Disease mDiseaseModel = new Disease();

                if(diseaseNameSpin.getSelectedItem().toString().equals("No Diseases")){
                    if(validateUI1()) {
                        mDiseaseModel.setDiseaseid(diseaseNameSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(diseaseNameDataMap, diseaseNameSpin.getSelectedItem().toString())));
                      //  mDiseaseModel.setIsControlMeasure(controlMeasureSpin.getSelectedItemPosition() == 1 ? 1 : 0);
                        mDiseaseModel.setComments(observationsEdt.getText().toString());
                        mDiseaseModel.setCreateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        mDiseaseModelArray.add(mDiseaseModel);
                        ratingList.add('A');
                        diseaseNameSpin.setSelection(0);
                        controlMeasureSpin.setSelection(0);
                        DataManager.getInstance().addData(DataManager.DISEASE_DETAILS, mDiseaseModelArray);
                        diseaseDataAdapter.notifyDataSetChanged();
                        updateUiListener.updateUserInterface(0);
                    }
                }else {

                    if (validateUI()) {
                        mDiseaseModel.setDiseaseid(diseaseNameSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(diseaseNameDataMap, diseaseNameSpin.getSelectedItem().toString())));
                        mDiseaseModel.setChemicalid(nameOfChemicalUsedSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(chemicalNameDataMap, nameOfChemicalUsedSpin.getSelectedItem().toString())));
                        //mDiseaseModel.setPercTreesId(percOfTreeSpin.getSelectedItemPosition() == 0 ? null :Integer.parseInt(getKey(percentageMap, percOfTreeSpin.getSelectedItem().toString())));

                        mDiseaseModel.setPercTreesId(Integer.parseInt(getKey(percentageMap, percOfTreeSpin.getSelectedItem().toString())));
                        mDiseaseModel.setComments(observationsEdt.getText().toString());
                        mDiseaseModel.setCreateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        mDiseaseModel.setRecommendFertilizerProviderId(nameOfChemicalUsedSpinRecmnd.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(chemicalNameDataMap, nameOfChemicalUsedSpinRecmnd.getSelectedItem().toString())));
                        mDiseaseModel.setRecommendUOMId(rcmnduomSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(uomDataMap, rcmnduomSpin.getSelectedItem().toString())));
                        mDiseaseModel.setRecommendedUOMId(rcmnduomperSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(rcmnduomperDatamap, rcmnduomperSpin.getSelectedItem().toString())));

                        mDiseaseModel.setRecommendDosage(TextUtils.isEmpty(rcmndosageEdt.getText().toString()) == true ? 0.0 : Double.parseDouble(rcmndosageEdt.getText().toString()));
                        mDiseaseModel.setIsControlMeasure(controlMeasureSpin.getSelectedItemPosition() == 1 ? 1 : 0);
                        mDiseaseModelArray.add(mDiseaseModel);
                        addingValues();
                        diseaseNameSpin.setSelection(0);
                        nameOfChemicalUsedSpinRecmnd.setSelection(0);
                        percOfTreeSpin.setSelection(0);
                        controlMeasureSpin.setSelection(0);
                        rcmnduomSpin.setSelection(0);
                        rcmnduomperSpin.setSelection(0);
                        rcmndosageEdt.setText("");
                        DataManager.getInstance().addData(DataManager.DISEASE_DETAILS, mDiseaseModelArray);

                        diseaseDataAdapter.notifyDataSetChanged();
                        updateUiListener.updateUserInterface(0);

                    }
                }
                CommonUtilsNavigation.hideKeyBoard(getActivity());
                break;
//            case R.id.historyBtn:
//                CropMaintainanceHistoryFragment newFragment = new CropMaintainanceHistoryFragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt("screen", DISEASE_DATA);
//                newFragment.setArguments(bundle);
//                newFragment.show(getActivity().getFragmentManager(), "history");
//
//                break;

        }
    }

    private boolean validateUI() {
        return spinnerSelect(diseaseNameSpin, "Select Source of Disease", mContext) && spinnerSelect(nameOfChemicalUsedSpin, "Select Name of Chemical", mContext)
                &&spinnerSelect(percOfTreeSpin,"Percentage Of Tree",mContext)&&spinnerSelect(controlMeasureSpin,"Control Measurs",mContext);

    }

    private boolean validateUI1() {
        return spinnerSelect(diseaseNameSpin, "Select Source of Disease", mContext);

    }


    @Override
    public void onEditClicked(int position) {

         mDiseaseModelArray.remove(position);
        Log.v("@@@Tree",""+position);

        ratingList.remove(position);

        if (mDiseaseModelArray.isEmpty()){
            CommonConstants.perc_tree_disease =' ';
        }

        if(mDiseaseModelArray.size() == 1)
        {
            CommonConstants.perc_tree_disease = ratingList.get(0);
        }
        else if(  mDiseaseModelArray.size() > 1)
        {
            for(int i=0;i<  ratingList.size()-1;i++){
                for(int j=i+1;j<  ratingList.size();j++){

                    if(ratingList.get(i) > ratingList.get(j))
                    {
                        CommonConstants.perc_tree_disease = ratingList.get(i);
                    }


                    else{
                        CommonConstants.perc_tree_disease=ratingList.get(j);
                    }
                }
            }
        }

        diseaseDataAdapter.notifyDataSetChanged();
    }

    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }

    @Override
    public void updateUserInterface(int refreshPosition) {
        complaintsBtn.setVisibility(View.GONE);
    }
}