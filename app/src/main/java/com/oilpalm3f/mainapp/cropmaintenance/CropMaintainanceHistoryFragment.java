package com.oilpalm3f.mainapp.cropmaintenance;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.SingleItemAdapter;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.dbmodels.Disease;
import com.oilpalm3f.mainapp.dbmodels.Fertilizer;
import com.oilpalm3f.mainapp.dbmodels.InterCropPlantationXref;
import com.oilpalm3f.mainapp.dbmodels.MainPestModel;
import com.oilpalm3f.mainapp.dbmodels.Nutrient;
import com.oilpalm3f.mainapp.dbmodels.Pest;
import com.oilpalm3f.mainapp.dbmodels.PestChemicalXref;
import com.oilpalm3f.mainapp.dbmodels.RecommndFertilizer;
import com.oilpalm3f.mainapp.dbmodels.Weed;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Calibrage11 on 7/22/2017.
 */

//Used to display Crop maintenance visited dates
public class CropMaintainanceHistoryFragment extends DialogFragment {
    private View rootView;
    private RecyclerView dataListView;
    private Context context;
    private TextView titleName, lastDate, uom;
    private int screen;
    private ArrayList<Fertilizer> mFertilizerModelArray;
    private ArrayList<RecommndFertilizer> mRecoFertilizerModelArray;
    private ArrayList<Nutrient> mNutrientModelArray;
    private ArrayList<Disease> mDiseaseModelArray;
    private ArrayList<Pest> mPestModelArray;
    private ArrayList<Weed> weedsModelArrayList;
    private DataAccessHandler dataAccessHandler;
    private Toolbar titleToolbar;
    private LinkedHashMap<String, String> recommfertilizerDataMap,fertilizerDataMap, recomfertilizerTypeDataMap,fertilizerTypeDataMap,diseaseNameDataMap,recomuomDataMap, uomDataMap, nutritionDataMap, chemicalNameDataMap, pestNameDataMap, frequencyOfApplicationDataMap, weedingMethodMap, cropDataMap;
    private GenericTypeAdapter genericTypeAdapter;
    private RecmndGenericTypeAdapter RecmgenericTypeAdapter;
    public static final int NUTRIENT_DATA = 2;
    public static final int FERTILIZER_DATA = 1;
    public static final int INTERCROP_DATA = 0;
    public static final int PEST_DATA = 3;
    public static final int DISEASE_DATA = 4;
    public  static final  int RECOM_FERTILIZER_DATA = 5;

    private TextView norecord_tv;
    private RelativeLayout mainrelavtivell;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            screen = bundle.getInt("screen");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_cropmain_history, null);
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        rootView.setMinimumWidth((int) (displayRectangle.width() * 0.7f));
        context = getActivity();
        dataAccessHandler = new DataAccessHandler(context);
        dataListView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        titleName = (TextView) rootView.findViewById(R.id.titleName);
        lastDate = (TextView) rootView.findViewById(R.id.lastDate);
        uom = (TextView) rootView.findViewById(R.id.uom);
        titleToolbar = (Toolbar) rootView.findViewById(R.id.titleToolbar);
        norecord_tv = (TextView) rootView.findViewById(R.id.norecord_tv);
        mainrelavtivell = (RelativeLayout) rootView.findViewById(R.id.mainrelavtivell);

       // String lastVisitCode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getLatestCropmaintenanceHistoryRecord());
        String lastVisitCode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getLatestCropMaintanaceHistoryCode(CommonConstants.PLOT_CODE));
        chemicalNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("7"));
        if (screen == INTERCROP_DATA) {
            titleToolbar.setTitle("InterCrop Details History");
            titleName.setText("Inter Crop");
            lastDate.setText("Crop");
            uom.setVisibility(View.GONE);
            cropDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getCropsMasterInfo());
            List<InterCropPlantationXref> selectedInterCropData = (List<InterCropPlantationXref>) dataAccessHandler.getInterCropPlantationXrefData(Queries.getInstance().getCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_INTERCROPPLANTATIONXREF), 1);
            List<Pair> interCropList = convertToCurrentCropModel(selectedInterCropData);
            SingleItemAdapter interCropAdapter;
            interCropAdapter = new SingleItemAdapter(true);
            dataListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            dataListView.setAdapter(interCropAdapter);
            if (interCropList != null && interCropList.size() > 0) {
                interCropAdapter.updateAdapter(interCropList);
            }

        } else if (screen == FERTILIZER_DATA) {


            uom.setVisibility(View.VISIBLE);
            titleToolbar.setTitle("Fertilizer Details History");
            fertilizerDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("33"));
            fertilizerTypeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("23"));
            uomDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getUOM());
            mFertilizerModelArray = (ArrayList<Fertilizer>) dataAccessHandler.getFertilizerData(Queries.getInstance().getCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_FERTLIZER), 1);
            if (mFertilizerModelArray.size() > 0) {
                norecord_tv.setVisibility(View.GONE);
                mainrelavtivell.setVisibility(View.VISIBLE);
            genericTypeAdapter = new GenericTypeAdapter(getActivity(), mFertilizerModelArray, fertilizerTypeDataMap, uomDataMap, GenericTypeAdapter.TYPE_FERTILIZER, true);
            dataListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            dataListView.setAdapter(genericTypeAdapter);

            }else{
                norecord_tv.setVisibility(View.VISIBLE);
                mainrelavtivell.setVisibility(View.GONE);
            }

        } else if (screen == NUTRIENT_DATA) {
            uom.setVisibility(View.VISIBLE);
            titleToolbar.setTitle("Nutrient Details History");
           /* titleName.setText(R.string.nutrient_defiency);
            lastDate.setText(R.string.chemical_applied);
            uom.setText(R.string.register_date);*/
            nutritionDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("21"));
            chemicalNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("23"));

          //  nutritionDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("5"));
            fertilizerTypeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("23"));
            uomDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getUOM());

            mNutrientModelArray = (ArrayList<Nutrient>) dataAccessHandler.getNutrientData(Queries.getInstance().getRecommndCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_NUTRIENT), 1);
            genericTypeAdapter = new GenericTypeAdapter(getActivity(), mNutrientModelArray, nutritionDataMap, chemicalNameDataMap,fertilizerTypeDataMap,uomDataMap, GenericTypeAdapter.TYPE_NUTRIENT, true);
            dataListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            dataListView.setAdapter(genericTypeAdapter);

        } else if (screen == PEST_DATA) {
            uom.setVisibility(View.VISIBLE);
            titleToolbar.setTitle("Pest Details History");

         /*   titleName.setText("Pest Name");
            titleToolbar.setTitle("Pest Details History");
            lastDate.setText(R.string.chemical_applied);*/
            uom.setText(R.string.register_date);
            pestNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("6"));
            List<Pest> mPestDataList = (List<Pest>) dataAccessHandler.getPestData(Queries.getInstance().getRecommndCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_PEST), 1);
            ArrayList<MainPestModel> mainPestModelList = buildPestData(mPestDataList, dataAccessHandler);
            Log.e("@@mainPestModelList", "" + mainPestModelList.size());
            recomuomDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getUOM());

            genericTypeAdapter = new GenericTypeAdapter(getActivity(), mainPestModelList, pestNameDataMap, chemicalNameDataMap,chemicalNameDataMap,recomuomDataMap, GenericTypeAdapter.TYPE_PEST, true);
            dataListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            dataListView.setAdapter(genericTypeAdapter);

        } else if (screen == DISEASE_DATA) {
            uom.setVisibility(View.VISIBLE);

            titleToolbar.setTitle("Disease Details History");
            titleName.setText("Disease ");
           /* titleToolbar.setTitle("Disease Details History");
            lastDate.setText(R.string.chemical_applied);
            uom.setText(R.string.register_date);*/

            diseaseNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("5"));
            mDiseaseModelArray = (ArrayList<Disease>) dataAccessHandler.getDiseaseData(Queries.getInstance().getRecommndCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_DISEASE), 1);
            chemicalNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("7"));
            recomuomDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getUOM());

            genericTypeAdapter = new GenericTypeAdapter(getActivity(), mDiseaseModelArray, diseaseNameDataMap,chemicalNameDataMap,chemicalNameDataMap,recomuomDataMap, GenericTypeAdapter.TYPE_DISEASE,true);
            dataListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            dataListView.setAdapter(genericTypeAdapter);


        }else if (screen == RECOM_FERTILIZER_DATA) {
            uom.setVisibility(View.VISIBLE);
            titleToolbar.setTitle("Recommendation Fertilizer Details History");

            recommfertilizerDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("33"));
            recomfertilizerTypeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("23"));
            mRecoFertilizerModelArray = (ArrayList<RecommndFertilizer>) dataAccessHandler.getRecomFertlizerData(Queries.getInstance().getRecommndCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_RECOMMND_FERTLIZER), 1);
            recomuomDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getUOM());
            RecmgenericTypeAdapter = new RecmndGenericTypeAdapter(getActivity(), mRecoFertilizerModelArray, recomfertilizerTypeDataMap, recomuomDataMap, 1,true);
            dataListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            dataListView.setAdapter(RecmgenericTypeAdapter);

        }
        /*else if (screen == RECOM_FERTILIZER_DATA) {
            uom.setVisibility(View.VISIBLE);
            titleToolbar.setTitle("Recommendation Fertilizer Details History");
            titleName.setText(R.string.recomm_fertilizer);
            lastDate.setText(R.string.Dosage);
            uom.setText(R.string.Uom);
            recommfertilizerDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("33"));
            recomfertilizerTypeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("23"));
            mRecoFertilizerModelArray = (ArrayList<RecommndFertilizer>) dataAccessHandler.getRecomFertlizerData(Queries.getInstance().getRecommndCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_RECOMMND_FERTLIZER), 1);
            recomuomDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getUOM());
            RecmgenericTypeAdapter = new RecmndGenericTypeAdapter(getActivity(), mRecoFertilizerModelArray, recomfertilizerTypeDataMap, recomuomDataMap, 1);
            dataListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            dataListView.setAdapter(RecmgenericTypeAdapter);

        }*/
        return rootView;
    }

    public ArrayList<MainPestModel> buildPestData(List<Pest> pestData, DataAccessHandler dataAccessHandler) {
        ArrayList<MainPestModel> mainPestModelList = new ArrayList<>();
        if (pestData != null && !pestData.isEmpty()) {
            for (Pest pest : pestData) {
                MainPestModel mainPestModel = new MainPestModel();
                mainPestModel.setPest(pest);
                List<PestChemicalXref> pestChemicalXrefs = (List<PestChemicalXref>) dataAccessHandler.getPestChemicalXrefData(Queries.getInstance().getPestXrefData(pest.getCode()), 1);
                if (pestChemicalXrefs != null && !pestChemicalXrefs.isEmpty()) {
                    for (PestChemicalXref pestChemical :
                            pestChemicalXrefs) {
                        mainPestModel.setmPestChemicalXref(pestChemical);
                        mainPestModelList.add(mainPestModel);
                    }
                }
            }
        }
        return mainPestModelList;
    }

    public List<Pair> convertToCurrentCropModel(List<InterCropPlantationXref> data) {
        List<Pair> interCropPair = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            interCropPair.add(Pair.create("InterCrop " + i, cropDataMap.get(String.valueOf(data.get(i).getCropId()))));
        }
        return interCropPair;
    }
}