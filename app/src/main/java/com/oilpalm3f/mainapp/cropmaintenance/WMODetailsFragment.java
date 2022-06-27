package com.oilpalm3f.mainapp.cropmaintenance;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.oilpalm3f.mainapp.dbmodels.Fertilizer;
import com.oilpalm3f.mainapp.dbmodels.Uprootment;
import com.oilpalm3f.mainapp.dbmodels.Weed;
import com.oilpalm3f.mainapp.ui.BaseFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.oilpalm3f.mainapp.common.CommonUtils.spinnerSelect;
import static com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation.getKey;


//Used to enter Weed Management Details during crop maintenance
public class WMODetailsFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private View rootView;
    private Spinner weedProparlySpin,weedingMethodSpin,
            frequencyOfApplicationSpin,pruningSpin,
            frequencyOfPrunningSpin,mulchingSpin,
            nameOfChemicalUsedSpin,weedSpin,weevilsSpin,inflorescenceSpin,basinHealthSpin;
    private Button saveBtn;
    private LinkedHashMap<String, String> chemicalNameDataMap,frequencyOfApplicationDataMap,
            weedingMethodMap,weedMap,pruningMap,weevilsMap,inflorescenceMap,basinHealthMap;
    private DataAccessHandler dataAccessHandler;
    private Weed mWeed;
    private UpdateUiListener updateUiListener;
    Toolbar toolbar;
    ActionBar actionBar;
    LinearLayout nameofthechemicalusedLL;

    private Button historyBtn;

    private ArrayList<Weed> weedlastvisitdatamap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.wmo_details_screen, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(getActivity().getResources().getString(R.string.wmo_title));

        mContext = getActivity();

        initViews();
        setViews();
        bindData();

        return rootView;
    }

    private void bindData() {
        mWeed = (Weed) DataManager.getInstance().getDataFromManager(DataManager.WEEDING_DETAILS);
        if (mWeed != null){

            basinHealthSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(basinHealthMap,mWeed.getBasinHealthId()));
            weedSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(weedMap,mWeed.getWeedId()));
            pruningSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(pruningMap,mWeed.getPruningId()));
            weevilsSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(weevilsMap,mWeed.getWeevilsId()));
            inflorescenceSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(inflorescenceMap,mWeed.getInflorescenceId()));

            weedProparlySpin.setSelection(mWeed.getIsweedproperlydone() == null ? 0 : mWeed.getIsweedproperlydone() == 1 ? 1 : 2);
            weedingMethodSpin.setSelection(mWeed.getMethodtypeid() == null ? 0 :CommonUtilsNavigation.getvalueFromHashMap(weedingMethodMap,mWeed.getMethodtypeid()));
            nameOfChemicalUsedSpin.setSelection(mWeed.getChemicalid() == null ? 0 :CommonUtilsNavigation.getvalueFromHashMap(chemicalNameDataMap,mWeed.getChemicalid()));
            frequencyOfApplicationSpin.setSelection(mWeed.getApplicationfrequency() == null ? 0 :CommonUtilsNavigation.getvalueFromHashMap(frequencyOfApplicationDataMap,mWeed.getApplicationfrequency()));
            frequencyOfPrunningSpin.setSelection(mWeed.getPrunningfrequency() == null ? 0 :CommonUtilsNavigation.getvalueFromHashMap(frequencyOfApplicationDataMap,mWeed.getPrunningfrequency()));
            mulchingSpin.setSelection(mWeed.getIsmulchingseen() == null ? 0 : mWeed.getIsmulchingseen() == 1 ? 1 : 2);
        }
    }

    public void initViews() {
        dataAccessHandler = new DataAccessHandler(mContext);
        weedProparlySpin = (Spinner) rootView.findViewById(R.id.weedProparlySpin);
        weedingMethodSpin = (Spinner) rootView.findViewById(R.id.weedingMethodSpin);
        frequencyOfApplicationSpin= (Spinner) rootView.findViewById(R.id.frequencyOfApplicationSpin);
        pruningSpin = (Spinner) rootView.findViewById(R.id.pruningSpin);
        frequencyOfPrunningSpin= (Spinner) rootView.findViewById(R.id.frequencyOfPrunningSpin);
        mulchingSpin= (Spinner) rootView.findViewById(R.id.mulchingSpin);
        nameOfChemicalUsedSpin= (Spinner) rootView.findViewById(R.id.nameOfChemicalUsedSpin);
        weedSpin = rootView.findViewById(R.id.weedSpin);
        weevilsSpin = rootView.findViewById(R.id.weevilsSpin);
        inflorescenceSpin = rootView.findViewById(R.id.inflorescenceSpin);
        basinHealthSpin = rootView.findViewById(R.id.basinHealthSpin);
        nameofthechemicalusedLL = rootView.findViewById(R.id.nameofthechemicalusedLL);

        saveBtn= (Button) rootView.findViewById(R.id.saveBtn);

        historyBtn = (Button) rootView.findViewById(R.id.weedlastvisitdataBtn);

        saveBtn.setOnClickListener(this);

    }

    public void setViews() {
        chemicalNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("7"));
        frequencyOfApplicationDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("30"));
        weedingMethodMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("29"));
        weedMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("573"));
        pruningMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("574"));
        weevilsMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("575"));
        inflorescenceMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("576"));
        basinHealthMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("572"));

        basinHealthSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "basinHealth", basinHealthMap));
        weedSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "weed", weedMap));
        pruningSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "pruning", pruningMap));
        weevilsSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "weevils", weevilsMap));
        inflorescenceSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "inflorescence", inflorescenceMap));


        nameOfChemicalUsedSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Name of chemical", chemicalNameDataMap));
        frequencyOfApplicationSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Select Frequency of Application / yr", frequencyOfApplicationDataMap));
        frequencyOfPrunningSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Select Frequency of prunning / yr", frequencyOfApplicationDataMap));
        weedingMethodSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Select weed method", weedingMethodMap));

        weedingMethodSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (weedingMethodSpin.getSelectedItemPosition() == 2 || weedingMethodSpin.getSelectedItemPosition() == 0){

                    nameofthechemicalusedLL.setVisibility(View.GONE);
                }

                if (weedingMethodSpin.getSelectedItemPosition() == 1){

                    nameofthechemicalusedLL.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        dialog.setContentView(R.layout.weedlastvisteddata);

        Toolbar titleToolbar;
        titleToolbar = (Toolbar) dialog.findViewById(R.id.titleToolbar);
        titleToolbar.setTitle("Weed Management And Other History");
        titleToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        LinearLayout weednameofthechemicalusedLL = (LinearLayout) dialog.findViewById(R.id.weednameofthechemicalusedLL);
        LinearLayout weedingproperlydonell = (LinearLayout) dialog.findViewById(R.id.weedingproperlydonell);
        LinearLayout weedingmethodll = (LinearLayout) dialog.findViewById(R.id.weedingmethodll);
        LinearLayout frequencyofappll = (LinearLayout) dialog.findViewById(R.id.frequencyofappll);
        LinearLayout pruningfrequencyll = (LinearLayout) dialog.findViewById(R.id.pruningfrequencyll);
        LinearLayout mainLL = (LinearLayout) dialog.findViewById(R.id.mainlyt);

        TextView weedbasinhealth = (TextView) dialog.findViewById(R.id.weedbasinhealth);
        TextView weedtype = (TextView) dialog.findViewById(R.id.weedtype);
        TextView weedpruning = (TextView) dialog.findViewById(R.id.weedpruning);
        TextView weedweevils = (TextView) dialog.findViewById(R.id.weedweevils);
        TextView weedInflorescence = (TextView) dialog.findViewById(R.id.weedInflorescence);
        TextView weedmulchingseen = (TextView) dialog.findViewById(R.id.weedmulchingseen);
        TextView weedproperlydone = (TextView) dialog.findViewById(R.id.weedproperlydone);
        TextView weedWeedingMethod = (TextView) dialog.findViewById(R.id.weedWeedingMethod);

        TextView weedChemicalUsed = (TextView) dialog.findViewById(R.id.weedChemicalUsed);
        TextView weedapplicationyr = (TextView) dialog.findViewById(R.id.weedapplicationyr);
        TextView weedfrequencyofpruning = (TextView) dialog.findViewById(R.id.weedfrequencyofpruning);


        TextView norecords = (TextView) dialog.findViewById(R.id.norecord_tv);

        String lastVisitCode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getLatestCropMaintanaceHistoryCode(CommonConstants.PLOT_CODE));
        weedlastvisitdatamap = (ArrayList<Weed>) dataAccessHandler.getWeedData(Queries.getInstance().getRecommndCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_WEED), 1);

        if (weedlastvisitdatamap.size() > 0){
            norecords.setVisibility(View.GONE);
            mainLL.setVisibility(View.VISIBLE);

            String basintypeStr = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(weedlastvisitdatamap.get(0).getBasinHealthId()));
            String weedtypeStr = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(weedlastvisitdatamap.get(0).getWeedId()));
            String pruningtypeStr = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(weedlastvisitdatamap.get(0).getPruningId()));
            String weevilsStr = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(weedlastvisitdatamap.get(0).getWeevilsId()));
            String infloresenceStr = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(weedlastvisitdatamap.get(0).getInflorescenceId()));

            String Ismulchingseen;
            String Isweedproperlydone = null;

            if (weedlastvisitdatamap.get(0).getIsmulchingseen() != null){
                Ismulchingseen = "Yes";
            }else{
                Ismulchingseen = "No";
            }

            if (weedlastvisitdatamap.get(0).getIsweedproperlydone() == null){
                weedingproperlydonell.setVisibility(View.GONE);
            }else{
                weedingproperlydonell.setVisibility(View.VISIBLE);
                if (weedlastvisitdatamap.get(0).getIsweedproperlydone() == 1){
                    Isweedproperlydone = "Yes";
                    weedproperlydone.setText(Isweedproperlydone + "");
                }else{
                    Isweedproperlydone = "No";
                    weedproperlydone.setText(Isweedproperlydone + "");
                }
            }

            if (weedlastvisitdatamap.get(0).getMethodtypeid() == null){
                weedingmethodll.setVisibility(View.GONE);
            }else{
                String methodtypeStr = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(weedlastvisitdatamap.get(0).getMethodtypeid()));
                weedingmethodll.setVisibility(View.VISIBLE);
                weedWeedingMethod.setText(methodtypeStr + "");

                if (methodtypeStr.equalsIgnoreCase("Chemical")){
                    String chemicaltypeStr = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(weedlastvisitdatamap.get(0).getChemicalid()));
                    weednameofthechemicalusedLL.setVisibility(View.VISIBLE);
                    weedChemicalUsed.setText(chemicaltypeStr + "");
                }else{
                    weednameofthechemicalusedLL.setVisibility(View.GONE);
                }
            }

            if (weedlastvisitdatamap.get(0).getApplicationfrequency() == null){
                frequencyofappll.setVisibility(View.GONE);
            }else{
                String appfrequencyStr = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(weedlastvisitdatamap.get(0).getApplicationfrequency()));
                frequencyofappll.setVisibility(View.VISIBLE);
                weedapplicationyr.setText(appfrequencyStr + "");
            }

            if (weedlastvisitdatamap.get(0).getPrunningfrequency() == null){
                pruningfrequencyll.setVisibility(View.GONE);
            }else{
                String pruningfrequencyStr = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(weedlastvisitdatamap.get(0).getPrunningfrequency()));
                pruningfrequencyll.setVisibility(View.VISIBLE);
                weedfrequencyofpruning.setText(pruningfrequencyStr + "");

            }

            weedbasinhealth.setText(basintypeStr + "");
            weedtype.setText(weedtypeStr + "");
            weedpruning.setText(pruningtypeStr + "");
            weedweevils.setText(weevilsStr + "");
            weedInflorescence.setText(infloresenceStr + "");
            weedmulchingseen.setText(Ismulchingseen + "");

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
        switch (v.getId()){
            case R.id.saveBtn:
                if (validateFields()){
                    mWeed = new Weed();

                    mWeed.setWeedId(Integer.parseInt(getKey(weedMap, weedSpin.getSelectedItem().toString())));
                    mWeed.setPruningId(Integer.parseInt(getKey(pruningMap, pruningSpin.getSelectedItem().toString())));
                    mWeed.setBasinHealthId(Integer.parseInt(getKey(basinHealthMap, basinHealthSpin.getSelectedItem().toString())));
                    mWeed.setWeevilsId(Integer.parseInt(getKey(weevilsMap, weevilsSpin.getSelectedItem().toString())));
                    mWeed.setInflorescenceId(Integer.parseInt(getKey(inflorescenceMap, inflorescenceSpin.getSelectedItem().toString())));
                    mWeed.setIsweedproperlydone(weedProparlySpin.getSelectedItemPosition() == 0 ? null : weedProparlySpin.getSelectedItemPosition() == 1 ? 1 : 0 );
                    mWeed.setMethodtypeid(weedingMethodSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(weedingMethodMap, weedingMethodSpin.getSelectedItem().toString())));
                    mWeed.setChemicalid(nameOfChemicalUsedSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(chemicalNameDataMap, nameOfChemicalUsedSpin.getSelectedItem().toString())));
                    mWeed.setPrunningfrequency(frequencyOfPrunningSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(frequencyOfApplicationDataMap, frequencyOfPrunningSpin.getSelectedItem().toString())));
                    mWeed.setIsprunning(pruningSpin.getSelectedItemPosition() == 0 ? null : pruningSpin.getSelectedItemPosition() == 1 ? 1 : 0 );mWeed.setApplicationfrequency(frequencyOfApplicationSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(frequencyOfApplicationDataMap, frequencyOfApplicationSpin.getSelectedItem().toString())));
                    mWeed.setApplicationfrequency(frequencyOfApplicationSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(getKey(frequencyOfApplicationDataMap, frequencyOfApplicationSpin.getSelectedItem().toString())));
                    mWeed.setIsmulchingseen(mulchingSpin.getSelectedItemPosition() == 0 ? null : mulchingSpin.getSelectedItemPosition() == 1 ? 1 : 0 );

                    calculateRating(mWeed);
                    DataManager.getInstance().addData(DataManager.WEEDING_DETAILS, mWeed);
                    getFragmentManager().popBackStack();
                    clearFields();
                    updateUiListener.updateUserInterface(0);
                }
                break;


        }
    }

    public boolean validateFields() {
//        return spinnerSelect(basinHealthSpin, "basinHealth", mContext) && spinnerSelect(weedSpin, "weed", mContext)
//                && spinnerSelect(pruningSpin, "pruning", mContext)
//                &&spinnerSelect(weevilsSpin,"weevils",mContext)
//                &&spinnerSelect(inflorescenceSpin,"inflorescence",mContext)
//                &&spinnerSelect(mulchingSpin,"mulching",mContext);

        if (basinHealthSpin.getSelectedItemPosition() == 0){
            Toast.makeText(mContext, "Please Select BasinHealth", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (weedSpin.getSelectedItemPosition() == 0){
            Toast.makeText(mContext, "Please Select Weed", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pruningSpin.getSelectedItemPosition() == 0){
            Toast.makeText(mContext, "Please Select Pruning", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (weevilsSpin.getSelectedItemPosition() == 0){
            Toast.makeText(mContext, "Please Select Weevils", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (inflorescenceSpin.getSelectedItemPosition() == 0){
            Toast.makeText(mContext, "Please Select Inflorescence", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mulchingSpin.getSelectedItemPosition() == 0){
            Toast.makeText(mContext, "Please Select Mulching", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (weedingMethodSpin.getSelectedItemPosition() == 1){
            if (nameOfChemicalUsedSpin.getSelectedItemPosition() == 0) {
                Toast.makeText(mContext, "Please Select Name of the Chemical Used", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    private void clearFields(){
        weedProparlySpin.setSelection(0);
        weedingMethodSpin.setSelection(0);
        frequencyOfApplicationSpin.setSelection(0);
        pruningSpin.setSelection(0);
        frequencyOfPrunningSpin.setSelection(0);
        mulchingSpin.setSelection(0);
        nameOfChemicalUsedSpin.setSelection(0);
    }

    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }

    private void calculateRating(Weed weed){


        if(((weedSpin.getSelectedItem().toString()).equals("Weed Free"))&&
                (((basinHealthSpin.getSelectedItem().toString()).equals("Properly Formed Basins")) || ((basinHealthSpin.getSelectedItem().toString()).equals("Intact Basins")))&&
                (weed.getIsmulchingseen() ==1))
        {
            CommonConstants.Basin_Health_rating = "A";

        }
        else if(((weedSpin.getSelectedItem().toString()).equals("Moderate Level Of Weeds"))&&((basinHealthSpin.getSelectedItem().toString()).equals("Intact Basins")))
        {
            CommonConstants.Basin_Health_rating = "B";
        }
        else {
            CommonConstants.Basin_Health_rating = "C";
        }



        CommonConstants.Inflorescence_rating = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getRating(576,weed.getInflorescenceId()));
        CommonConstants.Weevils_rating = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getRating(575,weed.getWeevilsId()));

        Log.v("@@@ids",""+CommonConstants.Inflorescence_rating+"  "+CommonConstants.Weevils_rating);

    }

}
