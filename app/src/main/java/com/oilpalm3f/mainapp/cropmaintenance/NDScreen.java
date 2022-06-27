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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import com.oilpalm3f.mainapp.datasync.refreshsyncmodel.FarmerComplaintsData;
import com.oilpalm3f.mainapp.dbmodels.Disease;
import com.oilpalm3f.mainapp.dbmodels.Healthplantation;
import com.oilpalm3f.mainapp.dbmodels.Nutrient;
import com.oilpalm3f.mainapp.ui.BaseFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.oilpalm3f.mainapp.R.id.diaseaseList;
import static com.oilpalm3f.mainapp.R.id.saveBtn;
import static com.oilpalm3f.mainapp.common.CommonUtils.spinnerSelect;
import static com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation.getKey;
import static com.oilpalm3f.mainapp.cropmaintenance.CropMaintainanceHistoryFragment.NUTRIENT_DATA;

//Used to enter nutrient deficiency details during crop maintenance
public class NDScreen extends Fragment implements View.OnClickListener, PalmDetailsEditListener, UpdateUiListener {

    private Context mContext;
    private View rootView;
    private Spinner nutritionSpin, nameOfChemicalUsedSpin, percentageOfTreesSpn;
    private EditText commentsEdt;
    private Button saveBtn, historyBtn;
    private RecyclerView nutrientList;
    private LinkedHashMap<String, String> nutritionDataMap, chemicalNameDataMap, percentageMap;
    private ArrayList<Nutrient> mNutrientModelArray;
    private GenericTypeAdapter nutrientDataAdapter;
    private DataAccessHandler dataAccessHandler;
    private UpdateUiListener updateUiListener;
    private Button complaintsBtn;
    private Spinner rcmndfertilizerProductNameSpin, rcmnduomSpin,rcmnduomperSpin;
    private EditText rcmndosageEdt;
    private LinkedHashMap fertilizerTypeDataMap, uomDataMap, rcmnduomperDatamap;
    private char prc_tree = ' ';
    private ArrayList<Character> ratingList;
    private ActionBar actionBar;
    private Toolbar toolbar;

    private ArrayList<Nutrient> nutrientlastvisitdatamap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.nutrient_details_layout, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.nutrient_details));

        ratingList = new ArrayList<>();
        mContext = getActivity();
        initView();
        setViews();
        bindData();

        return rootView;
    }


    private void initView() {
        dataAccessHandler = new DataAccessHandler(getActivity());
        nutritionSpin = (Spinner) rootView.findViewById(R.id.nutritionSpin);
        nameOfChemicalUsedSpin = (Spinner) rootView.findViewById(R.id.nameOfChemicalUsedSpin);
        commentsEdt = (EditText) rootView.findViewById(R.id.commentsEdt);
        nutrientList = (RecyclerView) rootView.findViewById(R.id.nutrientList);
        saveBtn = (Button) rootView.findViewById(R.id.saveBtn);
        historyBtn = (Button) rootView.findViewById(R.id.historyBtn);
        rcmndfertilizerProductNameSpin = (Spinner) rootView.findViewById(R.id.rcmndfertilizerProductNameSpin);
        rcmnduomSpin = (Spinner) rootView.findViewById(R.id.rcmnduomSpin);
        rcmndosageEdt = (EditText) rootView.findViewById(R.id.rcmndosageEdt);
        percentageOfTreesSpn = rootView.findViewById(R.id.percentageSpn);
        rcmnduomperSpin = (Spinner) rootView.findViewById(R.id.rcmnduomperSpin);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        complaintsBtn = (Button) rootView.findViewById(R.id.complaintsBtn);

        complaintsBtn.setEnabled(false);
        complaintsBtn.setVisibility(View.GONE);
        //complaintsBtn.setVisibility((CommonUiUtils.isComplaintsDataEntered()) ? View.GONE : View.VISIBLE);
        complaintsBtn.setOnClickListener(v -> {
            Bundle dataBundle = new Bundle();
            dataBundle.putBoolean(CommonConstants.KEY_NEW_COMPLAINT, true);
            ComplaintDetailsFragment complaintDetailsFragment = new ComplaintDetailsFragment();
            complaintDetailsFragment.setArguments(dataBundle);
            complaintDetailsFragment.setUpdateUiListener(NDScreen.this);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, complaintDetailsFragment).addToBackStack(null)
                    .commit();
        });
    }

    private void bindData() {
        mNutrientModelArray = (ArrayList<Nutrient>) DataManager.getInstance().getDataFromManager(DataManager.NUTRIENT_DETAILS);
        if (null == mNutrientModelArray)
            mNutrientModelArray = new ArrayList<Nutrient>();

        nutrientDataAdapter = new GenericTypeAdapter(getActivity(), mNutrientModelArray, nutritionDataMap, chemicalNameDataMap, percentageMap, uomDataMap, GenericTypeAdapter.TYPE_NUTRIENT);
        nutrientList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        nutrientList.setAdapter(nutrientDataAdapter);
        nutrientDataAdapter.setEditClickListener(this);
    }

    private void setViews() {

        saveBtn.setOnClickListener(this);
        //historyBtn.setOnClickListener(this);
        nutritionDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("21"));
        chemicalNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("23"));
        percentageMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("569"));

       nutritionSpin.setOnItemSelectedListener(spinListener);
        nutritionSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Nutrient Name", nutritionDataMap));
        nameOfChemicalUsedSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Name of chemical", chemicalNameDataMap));
        percentageOfTreesSpn.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Percentage of tree", percentageMap));
        fertilizerTypeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("23"));
        uomDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getUOM());
        rcmnduomperDatamap = dataAccessHandler.getGenericData(Queries.getInstance().getUOMper());


        rcmndfertilizerProductNameSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "fertilizer Product Name", fertilizerTypeDataMap));
        rcmnduomSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "UOM", uomDataMap));
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
        dialog.setContentView(R.layout.nutrientlastvisteddata);

        Toolbar titleToolbar;
        titleToolbar = (Toolbar) dialog.findViewById(R.id.titleToolbar);
        titleToolbar.setTitle("Nutrient History");
        titleToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        LinearLayout mainLL = (LinearLayout) dialog.findViewById(R.id.nutrientmainlyt);
        LinearLayout nutrientfertilizerusedll = (LinearLayout) dialog.findViewById(R.id.nutrientfertilizerusedll);
        LinearLayout nutrientpercentageoftressll = (LinearLayout) dialog.findViewById(R.id.nutrientpercentageoftressll);
        LinearLayout nutrientfertilizerrecommendedll = (LinearLayout) dialog.findViewById(R.id.nutrientfertilizerrecommendedll);
        LinearLayout nutrientrecommendeddosagell = (LinearLayout) dialog.findViewById(R.id.nutrientrecommendeddosagell);
        LinearLayout nutrientrecommendeduomll = (LinearLayout) dialog.findViewById(R.id.nutrientrecommendeduomll);
        LinearLayout nutrientrecommendeduomperll = (LinearLayout) dialog.findViewById(R.id.nutrientrecommendeduomperll);
        LinearLayout nutrientcommentsll = (LinearLayout) dialog.findViewById(R.id.nutrientcommentsll);

        TextView nutrientdeficencyname = (TextView) dialog.findViewById(R.id.nutrientdeficencyname);
        TextView nutrientfertilizerused = (TextView) dialog.findViewById(R.id.nutrientfertilizerused);
        TextView nutrientpercentageoftress = (TextView) dialog.findViewById(R.id.nutrientpercentageoftress);
        TextView nutrientfertilizerrecommended = (TextView) dialog.findViewById(R.id.nutrientfertilizerrecommended);
        TextView nutrientrecommendeddosage = (TextView) dialog.findViewById(R.id.nutrientrecommendeddosage);
        TextView nutrientrecommendeduom = (TextView) dialog.findViewById(R.id.nutrientrecommendeduom);
        TextView nutrientrecommendeduomper = (TextView) dialog.findViewById(R.id.nutrientrecommendeduomper);
        TextView nutrientcomments = (TextView) dialog.findViewById(R.id.nutrientcomments);
        TextView norecords = (TextView) dialog.findViewById(R.id.nutrientnorecord_tv);

        String lastVisitCode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getLatestCropMaintanaceHistoryCode(CommonConstants.PLOT_CODE));
        nutrientlastvisitdatamap = (ArrayList<Nutrient>) dataAccessHandler.getNutrientData(Queries.getInstance().getRecommndCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_NUTRIENT), 1);

        if (nutrientlastvisitdatamap.size() > 0){
            norecords.setVisibility(View.GONE);
            mainLL.setVisibility(View.VISIBLE);

            String nutrient = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(nutrientlastvisitdatamap.get(0).getNutrientid()));

            nutrientdeficencyname.setText(nutrient);

            if (nutrientlastvisitdatamap.get(0).getChemicalid() != null){
                nutrientfertilizerusedll.setVisibility(View.VISIBLE);
                String fertilizer = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(nutrientlastvisitdatamap.get(0).getChemicalid()));
                nutrientfertilizerused.setText(fertilizer);
            }else{
                nutrientfertilizerusedll.setVisibility(View.GONE);
            }
            if (nutrientlastvisitdatamap.get(0).getPercTreesId() != 0){
                nutrientpercentageoftressll.setVisibility(View.VISIBLE);
                String percentageoftress = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(nutrientlastvisitdatamap.get(0).getPercTreesId()));

                nutrientpercentageoftress.setText(percentageoftress);
            }else{
                nutrientpercentageoftressll.setVisibility(View.GONE);
            }

            if (nutrientlastvisitdatamap.get(0).getRecommendFertilizerProviderId() != null){
                nutrientfertilizerrecommendedll.setVisibility(View.VISIBLE);
                String fertilizerrec = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(nutrientlastvisitdatamap.get(0).getRecommendFertilizerProviderId()));
                nutrientfertilizerrecommended.setText(fertilizerrec);
            }else{
                nutrientfertilizerrecommendedll.setVisibility(View.GONE);
            }

            if (nutrientlastvisitdatamap.get(0).getRecommendDosage() != 0.0){
                nutrientrecommendeddosagell.setVisibility(View.VISIBLE);
                nutrientrecommendeddosage.setText(nutrientlastvisitdatamap.get(0).getRecommendDosage() + "");
            }else{
                nutrientrecommendeddosagell.setVisibility(View.GONE);
            }

            if (nutrientlastvisitdatamap.get(0).getRecommendUOMId() != null){
                nutrientrecommendeduomll.setVisibility(View.VISIBLE);
                String recommendedUOM = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getUOMdata(nutrientlastvisitdatamap.get(0).getRecommendUOMId()));
                nutrientrecommendeduom.setText(recommendedUOM);
            }else{
                nutrientrecommendeduomll.setVisibility(View.GONE);
            }

            if (nutrientlastvisitdatamap.get(0).getRecommendedUOMId() != null){
                nutrientrecommendeduomperll.setVisibility(View.VISIBLE);
                String recommendedUOMper = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getUOMdata(nutrientlastvisitdatamap.get(0).getRecommendedUOMId()));
                nutrientrecommendeduomper.setText(recommendedUOMper);
            }else{
                nutrientrecommendeduomperll.setVisibility(View.GONE);
            }

            if (TextUtils.isEmpty(nutrientlastvisitdatamap.get(0).getComments()) || nutrientlastvisitdatamap.get(0).getComments().equalsIgnoreCase("")  || nutrientlastvisitdatamap.get(0).getComments().equalsIgnoreCase(null) || nutrientlastvisitdatamap.get(0).getComments().equalsIgnoreCase("null")){
                nutrientcommentsll.setVisibility(View.GONE);
            }else{
                nutrientcommentsll.setVisibility(View.VISIBLE);
                nutrientcomments.setText(nutrientlastvisitdatamap.get(0).getComments() + "");
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
        if (CommonConstants.perc_tree == ' ') {

            if (percentageOfTreesSpn.getSelectedItem().toString().equals("No Deficiency")) {
                CommonConstants.perc_tree = 'A';
                ratingList.add('A');


            } else if (percentageOfTreesSpn.getSelectedItem().toString().equals("Less than 10%")) {
                CommonConstants.perc_tree = 'B';
                ratingList.add('B');

            } else {
                CommonConstants.perc_tree = 'C';
                ratingList.add('C');

            }
        } else {
            if ((percentageOfTreesSpn.getSelectedItem().toString()).equals("No Deficiency")) {
                ratingList.add('A');

            } else if (percentageOfTreesSpn.getSelectedItem().toString().equals("Less than 10%")) {
                prc_tree = 'B';
                if (prc_tree > CommonConstants.perc_tree) {
                    ratingList.add('B');

                    CommonConstants.perc_tree = prc_tree;
                } else {

                    CommonConstants.perc_tree = 'C';
                    ratingList.add('B');

                }
            } else {
                CommonConstants.perc_tree = 'C';
                ratingList.add('C');
            }
        }


        Log.v("@@@Tree", "" + CommonConstants.perc_tree);

    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                Nutrient mNutrientModel = new Nutrient();

                if(nutritionSpin.getSelectedItem().toString().equals("No Nutrient Deficiency")){
                    if(validateFields1()) {
                        mNutrientModel.setNutrientid(nutritionSpin.getSelectedItemPosition() == 0 ? 0 : Integer.parseInt(getKey(nutritionDataMap, nutritionSpin.getSelectedItem().toString())));
                        mNutrientModel.setComments(commentsEdt.getText().toString());
                        mNutrientModel.setCreateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        mNutrientModelArray.add(mNutrientModel);
                        ratingList.add('C');
                        nutritionSpin.setSelection(0);
                        DataManager.getInstance().addData(DataManager.NUTRIENT_DETAILS, mNutrientModelArray);
                        CommonUtilsNavigation.hideKeyBoard(getActivity());
                        nutrientDataAdapter.notifyDataSetChanged();
                        commentsEdt.setText("");
                        rcmndosageEdt.setText("");
                        updateUiListener.updateUserInterface(0);
                    }

                }else {
                    if (validateFields()) {
                        mNutrientModel.setNutrientid(nutritionSpin.getSelectedItemPosition() == 0 ? 0 : Integer.parseInt(getKey(nutritionDataMap, nutritionSpin.getSelectedItem().toString())));
                        mNutrientModel.setChemicalid(nameOfChemicalUsedSpin.getSelectedItemPosition() == 0 ? 0 : Integer.parseInt(getKey(chemicalNameDataMap, nameOfChemicalUsedSpin.getSelectedItem().toString())));
                        mNutrientModel.setPercTreesId(Integer.parseInt(getKey(percentageMap, percentageOfTreesSpn.getSelectedItem().toString())));
                        mNutrientModel.setComments(commentsEdt.getText().toString());
                        mNutrientModel.setCreateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        mNutrientModel.setRecommendFertilizerProviderId(rcmndfertilizerProductNameSpin.getSelectedItemPosition() == 0 ? 0 : (Integer.parseInt(getKey(fertilizerTypeDataMap, rcmndfertilizerProductNameSpin.getSelectedItem().toString()))));
                        mNutrientModel.setRecommendUOMId(rcmnduomSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(uomDataMap, rcmnduomSpin.getSelectedItem().toString())));
                        mNutrientModel.setRecommendedUOMId(rcmnduomperSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(rcmnduomperDatamap, rcmnduomperSpin.getSelectedItem().toString())));

                        mNutrientModel.setRecommendDosage(TextUtils.isEmpty(rcmndosageEdt.getText().toString()) == true ? 0.0 : Double.parseDouble(rcmndosageEdt.getText().toString()));

                        mNutrientModelArray.add(mNutrientModel);

                        addingValues();

                        nutritionSpin.setSelection(0);
                        nameOfChemicalUsedSpin.setSelection(0);
                        percentageOfTreesSpn.setSelection(0);
                        rcmnduomSpin.setSelection(0);
                        rcmnduomperSpin.setSelection(0);
                        rcmndfertilizerProductNameSpin.setSelection(0);
                        DataManager.getInstance().addData(DataManager.NUTRIENT_DETAILS, mNutrientModelArray);
                        CommonUtilsNavigation.hideKeyBoard(getActivity());
                        nutrientDataAdapter.notifyDataSetChanged();
                        commentsEdt.setText("");
                        rcmndosageEdt.setText("");
                        updateUiListener.updateUserInterface(0);


                    }
                }

                break;
//            case R.id.historyBtn:
//                CropMaintainanceHistoryFragment newFragment = new CropMaintainanceHistoryFragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt("screen", NUTRIENT_DATA);
//                newFragment.setArguments(bundle);
//                newFragment.show(getActivity().getFragmentManager(), "history");
//
//                break;

        }
    }

    public boolean validateFields() {
        return spinnerSelect(nutritionSpin, "Name of Nutrient", mContext)
                && spinnerSelect(nameOfChemicalUsedSpin, "Name of Chemical", mContext)
                && spinnerSelect(percentageOfTreesSpn, "Percentage of tree", mContext);

    }

    public boolean validateFields1() {
        return spinnerSelect(nutritionSpin, "Name of Nutrient", mContext);

    }

    AdapterView.OnItemSelectedListener spinListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {

                case R.id.nutritionSpin:
                    if (position == 0) {
                        nameOfChemicalUsedSpin.setSelection(0);
                        nameOfChemicalUsedSpin.setEnabled(false);
                        percentageOfTreesSpn.setSelection(0);
                        percentageOfTreesSpn.setEnabled(false);
                        rcmndfertilizerProductNameSpin.setSelection(0);
                        rcmndfertilizerProductNameSpin.setEnabled(false);
                        rcmndosageEdt.setEnabled(false);
                        rcmnduomSpin.setSelection(0);
                        rcmnduomSpin.setEnabled(false);
                        rcmnduomperSpin.setSelection(0);
                        rcmnduomperSpin.setEnabled(false);
                        commentsEdt.setEnabled(false);
                        saveBtn.setEnabled(false);
                        saveBtn.setAlpha(1.0f);
                    }else if (position == 9) {
                        nameOfChemicalUsedSpin.setSelection(0);
                        nameOfChemicalUsedSpin.setEnabled(false);
                        percentageOfTreesSpn.setSelection(0);
                        percentageOfTreesSpn.setEnabled(false);
                        rcmndfertilizerProductNameSpin.setSelection(0);
                        rcmndfertilizerProductNameSpin.setEnabled(false);
                        rcmndosageEdt.setEnabled(false);
                        rcmnduomSpin.setSelection(0);
                        rcmnduomSpin.setEnabled(false);
                        rcmnduomperSpin.setSelection(0);
                        rcmnduomperSpin.setEnabled(false);
                        commentsEdt.setEnabled(true);
                        saveBtn.setEnabled(true);
                        saveBtn.setAlpha(1.0f);

                    } else {
                        nameOfChemicalUsedSpin.setEnabled(true);
                        percentageOfTreesSpn.setEnabled(true);
                        rcmndfertilizerProductNameSpin.setEnabled(true);
                        rcmndosageEdt.setEnabled(true);
                        rcmnduomSpin.setEnabled(true);
                        rcmnduomperSpin.setEnabled(true);
                        commentsEdt.setEnabled(true);
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


    @Override
    public void onEditClicked(int position) {
        mNutrientModelArray.remove(position);
        ratingList.remove(position);

        if (mNutrientModelArray.isEmpty()) {
            CommonConstants.perc_tree = ' ';
        }

        if (mNutrientModelArray.size() == 1) {
            CommonConstants.perc_tree = ratingList.get(0);
        } else if (mNutrientModelArray.size() > 1) {

            Log.v("@@@ adda value", "" + mNutrientModelArray.size());
            for (int i = 0; i < ratingList.size() - 1; i++) {
                for (int j = i + 1; j < ratingList.size(); j++) {

                    Log.v("@@@ adda value", "" + ratingList.get(i));

                    if (ratingList.get(i) > ratingList.get(j)) {
                        CommonConstants.perc_tree = ratingList.get(i);
                        Log.v("@@@ adda value", "" + CommonConstants.perc_tree);
                    } else {
                        CommonConstants.perc_tree = ratingList.get(j);
                        Log.v("@@@ add value", "" + CommonConstants.perc_tree);
                    }
                }
            }
        }

        Log.v("@@@cheecking the Values", "" + CommonConstants.perc_tree + " size " + mNutrientModelArray.size());

        nutrientDataAdapter.notifyDataSetChanged();


    }

    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }

    @Override
    public void updateUserInterface(int refreshPosition) {
        complaintsBtn.setVisibility(View.GONE);
    }

}

