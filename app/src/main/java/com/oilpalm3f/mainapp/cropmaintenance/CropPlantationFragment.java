package com.oilpalm3f.mainapp.cropmaintenance;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.alerts.AlertPlotInfoAdapter;
import com.oilpalm3f.mainapp.alerts.AlertsDisplayScreen;
import com.oilpalm3f.mainapp.areaextension.UpdateUiListener;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.Plantation;
import com.oilpalm3f.mainapp.dbmodels.Plot;
import com.oilpalm3f.mainapp.ui.BaseFragment;
import com.oilpalm3f.mainapp.ui.RecyclerItemClickListener;
import com.oilpalm3f.mainapp.utils.UiUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

//Crop Plantation Screen
public class CropPlantationFragment extends Fragment implements RecyclerItemClickListener {
    TextView TotalArea;
    TextView totalarea_text;
    private View rootView;
    private Context mContext;
    private DataAccessHandler dataAccessHandler;
    private UpdateUiListener updateUiListener;
    private RecyclerView current_plation_recylerview;
    private LinearLayoutManager layoutManager;
    private PlantDetailsAdapter mPlantDetailsAdapter;
    private ArrayList<Plantation> msPlantationList,msPlantationListCheck;
    private ActionBar actionBar;
    private EditText areaAllocatedEdt,treeCountEdt;
    private Spinner sourceofsaplingsSpin, cropSpin, varietySpin, reasonSpin, saplingNurserySpin, saplingVendorSpin;
    private LinkedHashMap<String, String> varietyDataMap, sourceofsaplingsMap, cropDataMap, saplingVendorMap, saplingNurseryMap;
    private Button plantationSave;
    private  LinearLayout addPlantationLL;

    public CropPlantationFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.crop_plantaion_recyclerview, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Plantation Details");



        mContext = getActivity();
        setHasOptionsMenu(true);
        initViews();


        return rootView;
    }

    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }

    private void initViews() {
        current_plation_recylerview=(RecyclerView)rootView.findViewById(R.id.current_plation_recylerview);
        TotalArea = (TextView) rootView.findViewById(R.id.totalArea);
        totalarea_text = (TextView) rootView.findViewById(R.id.totalarea_text);

        varietySpin = rootView.findViewById(R.id.varietySpin);
        sourceofsaplingsSpin = rootView.findViewById(R.id.saplingSourceSpin);
        saplingVendorSpin = rootView.findViewById(R.id.saplingVendorSpin);
        saplingNurserySpin = rootView.findViewById(R.id.saplingNurserySpin);
        areaAllocatedEdt = rootView.findViewById(R.id.areaAllocatedEdt);
        treeCountEdt = rootView.findViewById(R.id.treeCountEdt);
        plantationSave = rootView.findViewById(R.id.plantationSave);
        addPlantationLL = rootView.findViewById(R.id.addPlantationLL);


        msPlantationList = new ArrayList<>();
        dataAccessHandler = new DataAccessHandler(getActivity());

        msPlantationList = (ArrayList<Plantation>) dataAccessHandler.getPlantationDataset(Queries.getInstance().getPlantatiobData(CommonConstants.PLOT_CODE), 1);
        mPlantDetailsAdapter = new PlantDetailsAdapter(getActivity(), msPlantationList,dataAccessHandler,1);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        current_plation_recylerview.setLayoutManager(layoutManager);
        current_plation_recylerview.setAdapter(mPlantDetailsAdapter);

        if(msPlantationList.isEmpty())
        {
            addPlantation();
        }else {
            addPlantationLL.setVisibility(View.GONE);
           setViews();
        }
    }

    private void addPlantation(){

        msPlantationListCheck = (ArrayList<Plantation>) DataManager.getInstance().getDataFromManager(DataManager.PLANTATION_CON_DATA);

        if(msPlantationListCheck !=null && !msPlantationListCheck.isEmpty())
        {
            msPlantationList.addAll(msPlantationListCheck);
            mPlantDetailsAdapter.updateAdapter(msPlantationList,0);
        }

        saplingNurseryMap = dataAccessHandler.getGenericData(Queries.getInstance().getSaplingsNursery());
        saplingNurserySpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(mContext, "Saplings nursery", saplingNurseryMap));

        saplingVendorMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("19"));
        saplingVendorSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(mContext, "Saplings vendor", saplingVendorMap));

        sourceofsaplingsMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("17"));
        sourceofsaplingsSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(mContext, "" + getString(R.string.source_of_saplings), sourceofsaplingsMap));

        varietyDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("18"));
        ArrayAdapter<String> adapterSet = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, CommonUtils.removeDuplicates(varietyDataMap, "varietyType"));
        adapterSet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        varietySpin.setAdapter(adapterSet);



        plantationSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateFields())
                {
                    double area = Double.parseDouble(areaAllocatedEdt.getText().toString());
                    Plantation plantation = new Plantation();
                    plantation.setAllotedarea(area);
                    plantation.setNurserycode(Integer.parseInt(CommonUtils.getKeyFromValue(saplingNurseryMap, saplingNurserySpin.getSelectedItem().toString())));
                    plantation.setCropVarietyId(Integer.parseInt(CommonUtils.getKeyFromValue(varietyDataMap, varietySpin.getSelectedItem().toString())));
                    plantation.setSaplingsourceid(Integer.parseInt(CommonUtils.getKeyFromValue(sourceofsaplingsMap, sourceofsaplingsSpin.getSelectedItem().toString())));
                    plantation.setTreescount(Integer.parseInt(treeCountEdt.getText().toString()));
                    plantation.setSaplingvendorid(Integer.parseInt(CommonUtils.getKeyFromValue(saplingVendorMap, saplingVendorSpin.getSelectedItem().toString())));

                    msPlantationList.add(plantation);
                    mPlantDetailsAdapter.updateAdapter(msPlantationList,0);

                    saplingNurserySpin.setSelection(0);
                    varietySpin.setSelection(0);
                    sourceofsaplingsSpin.setSelection(0);
                    treeCountEdt.setText("");
                    areaAllocatedEdt.setText("");
                    saplingVendorSpin.setSelection(0);

                    DataManager.getInstance().addData(DataManager.PLANTATION_CON_DATA, msPlantationList);

                    updateUiListener.updateUserInterface(0);
                }
            }
        });



    }

    private void setViews() {

         Plot selectedPlot = (Plot) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getSelectedPlot(CommonConstants.PLOT_CODE), 0);
        if (null != selectedPlot) {
            totalarea_text.setText("" + selectedPlot.getTotalplotarea());
            totalarea_text.setVisibility(View.VISIBLE);
        }
    }

    public boolean validateFields() {
        if (CommonUtils.isEmptySpinner(saplingNurserySpin)) {
            UiUtils.showCustomToastMessage("Please select sapling nursery", getActivity(), 1);
            return false;
        }

        if (CommonUtils.isEmptySpinner(sourceofsaplingsSpin)) {
            UiUtils.showCustomToastMessage("Please select sapling source", getActivity(), 1);
            return false;
        }

        if (CommonUtils.isEmptySpinner(saplingVendorSpin)) {
            UiUtils.showCustomToastMessage("Please select " + getResources().getString(R.string.sapling_vendor), getActivity(), 1);
            return false;
        }

        if (CommonUtils.isEmptySpinner(varietySpin)) {
            UiUtils.showCustomToastMessage("Please select " + getResources().getString(R.string.Variety_cross), getActivity(), 1);
            return false;
        }



        if (TextUtils.isEmpty(areaAllocatedEdt.getText().toString())) {
            UiUtils.showCustomToastMessage("Please enter area allocated", getActivity(), 1);
            return false;
        }

        int treeCount ;
        if (TextUtils.isEmpty(treeCountEdt.getText().toString())){
            treeCount = 0;
        }else {
            treeCount = Integer.parseInt((treeCountEdt.getText().toString()));
        }

        if (TextUtils.isEmpty(treeCountEdt.getText().toString()) || (treeCount == 0)) {
            UiUtils.showCustomToastMessage("Please enter count of trees & must not be Zero", getActivity(), 1);
            return false;
        }
        return true;
    }


    @Override
    public void onItemSelected(int position) {

        msPlantationList.remove(position);
        mPlantDetailsAdapter.updateAdapter(msPlantationList,0);

    }
}
