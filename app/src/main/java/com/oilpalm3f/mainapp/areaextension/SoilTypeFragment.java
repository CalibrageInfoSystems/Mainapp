package com.oilpalm3f.mainapp.areaextension;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.PlotIrrigationTypeXref;
import com.oilpalm3f.mainapp.dbmodels.SoilResource;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.oilpalm3f.mainapp.common.CommonUtils.isFromConversion;
import static com.oilpalm3f.mainapp.common.CommonUtils.isFromCropMaintenance;
import static com.oilpalm3f.mainapp.common.CommonUtils.isFromFollowUp;


//Soil/Power Type Entry Fragment
public class SoilTypeFragment extends Fragment implements SoilTypeAdapter.OnCartChangedListener, EditEntryDialogFragment.OnDataEditChangeListener {

    private View rootView;
    private ActionBar actionBar;
    private Spinner soiltype, plotprioritizationSpin, typeofirrigationSpin,soilNatureType,irrigationRecSpn;
    private EditText noofhourspowerEdit, water_commentsEdit,irrigatedArea;
    private Button  irigationSaveBtn, saveBtn;
    private RecyclerView mRecyclerView;
    private SoilTypeAdapter mSoilTypeAdapter;
    private int selectedPosition = 0;
    private Spinner powerAvailSpin;
    private LinkedHashMap<String, String> soilTypeMap, typeofirrigationMap, plotPrioritizationMap,soilNatureTypeMap;
    private DataAccessHandler dataAccessHandler;
    private SoilResource msoilTypeModel;
    private ArrayList<PlotIrrigationTypeXref> msoilTypeIrrigationModelList = new ArrayList<>();
    private boolean updateFromDb;
    private UpdateUiListener updateUiListener;

    public SoilTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_soil_type, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(activity.getResources().getString(R.string.water_soil_power_details));

        initViews();
        setViews();

        return rootView;
    }

    private void initViews() {
        dataAccessHandler = new DataAccessHandler(getActivity());

        soiltype = (Spinner) rootView.findViewById(R.id.soiltype);
        plotprioritizationSpin = (Spinner) rootView.findViewById(R.id.plotprioritizationSpin);
        typeofirrigationSpin = (Spinner) rootView.findViewById(R.id.typeofirrigationSpin);
        powerAvailSpin = (Spinner) rootView.findViewById(R.id.poweravailSpin);
        noofhourspowerEdit = (EditText) rootView.findViewById(R.id.noofhourspowerEdit);
        water_commentsEdit = (EditText) rootView.findViewById(R.id.water_commentsEdit);
        irigationSaveBtn = (Button) rootView.findViewById(R.id.irigationSaveBtn);
        saveBtn = (Button) rootView.findViewById(R.id.saveBtn);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        irrigatedArea = rootView.findViewById(R.id.irrigatedArea);
        soilNatureType = rootView.findViewById(R.id.soilNatureType);
        irrigationRecSpn = rootView.findViewById(R.id.irrigationRecSpn);

    }

    private void setViews() {
        soilTypeMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("35"));
        soilNatureTypeMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("54"));
        typeofirrigationMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("36"));
        plotPrioritizationMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("37"));

        soiltype.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "SoilType", soilTypeMap));
        soilNatureType.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "SoilNatureType", soilNatureTypeMap));
        plotprioritizationSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Select Prioritization", plotPrioritizationMap));
        selectedPosition = 0;

        msoilTypeIrrigationModelList = (ArrayList<PlotIrrigationTypeXref>) DataManager.getInstance().getDataFromManager(DataManager.TypeOfIrrigation);
        if (msoilTypeIrrigationModelList == null && (isFromFollowUp() || isFromCropMaintenance() || isFromConversion())) {
            msoilTypeIrrigationModelList = (ArrayList<PlotIrrigationTypeXref>) dataAccessHandler.getPlotIrrigationXRefData(Queries.getInstance().getPlotIrrigationTypeXrefBinding(CommonConstants.PLOT_CODE), 1);
        }

        if (msoilTypeIrrigationModelList == null || msoilTypeIrrigationModelList.isEmpty())
            msoilTypeIrrigationModelList = new ArrayList<>();
        else{
            for (PlotIrrigationTypeXref mPlotIrrigationTypeXref : msoilTypeIrrigationModelList) {
                typeofirrigationMap.remove(String.valueOf(mPlotIrrigationTypeXref.getIrrigationtypeid()));
            }
            saveBtn.setVisibility(View.VISIBLE);
        }
        typeofirrigationSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Select Type", typeofirrigationMap));
        irrigationRecSpn.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Select Type", typeofirrigationMap));



        mRecyclerView.setHasFixedSize(true);
        mSoilTypeAdapter = new SoilTypeAdapter(getActivity(), msoilTypeIrrigationModelList,typeofirrigationMap);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mSoilTypeAdapter);
        mSoilTypeAdapter.setOnCartChangedListener(this);

        msoilTypeModel = (SoilResource) DataManager.getInstance().getDataFromManager(DataManager.SoilType);
        if (msoilTypeModel == null && (isFromFollowUp() || isFromCropMaintenance() || isFromConversion())) {
            updateFromDb = true;
            msoilTypeModel = (SoilResource) dataAccessHandler.getSoilResourceData(Queries.getInstance().getSoilResourceBinding(CommonConstants.PLOT_CODE), 0);
        }

        if (msoilTypeModel != null) {
            soiltype.setSelection(CommonUtilsNavigation.getvalueFromHashMap(soilTypeMap, msoilTypeModel.getSoiltypeid()));
            powerAvailSpin.setSelection((null != msoilTypeModel.getIspoweravailable() && msoilTypeModel.getIspoweravailable() == 1) ? 1 : 2);
            noofhourspowerEdit.setText("" + msoilTypeModel.getAvailablepowerhours());
            plotprioritizationSpin.setSelection(msoilTypeModel.getPrioritizationtypeid() == null ? 0 : CommonUtilsNavigation.getvalueFromHashMap(plotPrioritizationMap, msoilTypeModel.getPrioritizationtypeid()));
            water_commentsEdit.setText(msoilTypeModel.getComments());
        }

        irigationSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeofirrigationSpin.getSelectedItemPosition() != 0 && irrigationRecSpn.getSelectedItemPosition()!=0) {
                    PlotIrrigationTypeXref msoilTypeIrrigationModel = new PlotIrrigationTypeXref();
                    msoilTypeIrrigationModel.setIrrigationtypeid(Integer.parseInt(CommonUtilsNavigation.getKey(typeofirrigationMap, typeofirrigationSpin.getSelectedItem().toString())));
                    msoilTypeIrrigationModel.setName(typeofirrigationSpin.getSelectedItem().toString());
                    msoilTypeIrrigationModel.setRecmIrrgId(Integer.parseInt(CommonUtilsNavigation.getKey(typeofirrigationMap, irrigationRecSpn.getSelectedItem().toString())));
                    Log.v("@@@id",""+msoilTypeIrrigationModel.getRecmIrrgId());

                    msoilTypeIrrigationModelList.add(msoilTypeIrrigationModel);
                    DataManager.getInstance().addData(DataManager.TypeOfIrrigation, msoilTypeIrrigationModelList);
                    mSoilTypeAdapter.notifyDataSetChanged();
                    typeofirrigationSpin.setSelection(0);
                    irrigationRecSpn.setSelection(0);

                } else
                    CommonUtils.showToast("Please select Irrigation type and Recommended Irrigation", getActivity());

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                try {
                    if (!TextUtils.isEmpty(noofhourspowerEdit.getText().toString()) && Double.parseDouble(noofhourspowerEdit.getText().toString()) > 24){
                        CommonUtils.showToast(getString(R.string.error_exceed24), getActivity());
                        return;
                    }
                }catch (Exception e){

                }


                if (CommonUtilsNavigation.spinnerSelect("Soil Type", soiltype.getSelectedItemPosition(), getActivity())
                        &&CommonUtilsNavigation.spinnerSelect("Soil Nature Type", soilNatureType.getSelectedItemPosition(), getActivity())
                        && CommonUtilsNavigation.spinnerSelect("Power Availability", powerAvailSpin.getSelectedItemPosition(), getActivity())
                        && CommonUtilsNavigation.edittextSelect(getActivity(),irrigatedArea,"Irrigated area")
                        && CommonUtilsNavigation.listEmpty(msoilTypeIrrigationModelList, "Irrigation Details", getActivity())) {

                        msoilTypeModel = new SoilResource();
                        msoilTypeModel.setSoiltypeid(Integer.parseInt(CommonUtilsNavigation.getKey(soilTypeMap, soiltype.getSelectedItem().toString())));
                        msoilTypeModel.setIspoweravailable(powerAvailSpin.getSelectedItemPosition() == 1 ? 1 : 0);
                        msoilTypeModel.setAvailablepowerhours(noofhourspowerEdit.getText().toString().length() > 0 ? Double.parseDouble(noofhourspowerEdit.getText().toString()) : 0.0);
                        msoilTypeModel.setPrioritizationtypeid(plotprioritizationSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(CommonUtilsNavigation.getKey(plotPrioritizationMap, plotprioritizationSpin.getSelectedItem().toString())));
                        msoilTypeModel.setComments(water_commentsEdit.getText().toString());
                        msoilTypeModel.setSoilNatureId(Integer.parseInt(CommonUtilsNavigation.getKey(soilNatureTypeMap, soilNatureType.getSelectedItem().toString())));
                        msoilTypeModel.setIrrigatedArea(Float.parseFloat(irrigatedArea.getText().toString()));
                        DataManager.getInstance().addData(DataManager.SoilType, msoilTypeModel);
                        soiltype.setEnabled(false);
                        powerAvailSpin.setEnabled(false);
                        noofhourspowerEdit.setEnabled(false);
                        plotprioritizationSpin.setEnabled(false);
                        water_commentsEdit.setEnabled(false);

                        if (updateFromDb) {
                            DataManager.getInstance().addData(DataManager.IS_WOP_DATA_UPDATED, true);
                        }
                        CommonConstants.Flags.isWOPDataUpdated = true;
                        updateUiListener.updateUserInterface(0);
                        getFragmentManager().popBackStack();

                }


            }
        });
    }

    @Override
    public void setCartClickListener(String clickItem, final int selectPos) {
        if (clickItem.equalsIgnoreCase("edit")) {
            EditEntryDialogFragment editEntryDialogFragment = new EditEntryDialogFragment();
            editEntryDialogFragment.setOnDataEditChangeListener(this);
            Bundle inputBundle = new Bundle();
            selectedPosition = selectPos;
            inputBundle.putString("title", "Irrigation Type");

            inputBundle.putInt("typeDialog", EditEntryDialogFragment.TYPE_SPINNER_IRIGATION_TYPE);
            inputBundle.putString("prevData", msoilTypeIrrigationModelList.get(selectedPosition).getName() + "-" + getString(R.string.typeofirrigation) + (selectedPosition + 1));

            editEntryDialogFragment.setArguments(inputBundle);
            FragmentManager mFragmentManager = getChildFragmentManager();
            editEntryDialogFragment.show(mFragmentManager, "fragment_edit_name");
        } else if (clickItem.equalsIgnoreCase("delete")) {
            msoilTypeIrrigationModelList.remove(selectPos);
            mSoilTypeAdapter.notifyDataSetChanged();
            DataManager.getInstance().addData(DataManager.TypeOfIrrigation, msoilTypeIrrigationModelList);
        }
    }

    @Override
    public void onDataEdited(Bundle dataBundle) {
        PlotIrrigationTypeXref msoilTypeIrrigationModel = new PlotIrrigationTypeXref();
        msoilTypeIrrigationModel.setName("" + dataBundle.getString("inputValue"));
        msoilTypeIrrigationModelList.set(selectedPosition, msoilTypeIrrigationModel);

        mSoilTypeAdapter.notifyDataSetChanged();

    }



    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }
}
