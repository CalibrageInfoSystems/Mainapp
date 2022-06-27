package com.oilpalm3f.mainapp.cropmaintenance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.conversion.PalmDetailsEditListener;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.refreshsyncmodel.FarmerComplaintsData;
import com.oilpalm3f.mainapp.dbmodels.ComplaintStatusHistory;
import com.oilpalm3f.mainapp.dbmodels.Disease;
import com.oilpalm3f.mainapp.dbmodels.Fertilizer;
import com.oilpalm3f.mainapp.dbmodels.InterCropPlantationXref;
import com.oilpalm3f.mainapp.dbmodels.MainPestModel;
import com.oilpalm3f.mainapp.dbmodels.Nutrient;
import com.oilpalm3f.mainapp.dbmodels.Pest;
import com.oilpalm3f.mainapp.dbmodels.PestChemicalXref;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Calibrage11 on 9/30/2016.
 */

//Common Recyclerview Adapter
public class GenericTypeAdapter extends RecyclerView.Adapter<GenericTypeAdapter.SingleItemRowHolder> {

    public static final int TYPE_FERTILIZER = 100;
    public static final int TYPE_PEST = 200;
    public static final int TYPE_DISEASE = 300;
    public static final int TYPE_COMPLAINT = 400;
    public static final int TYPE_NUTRIENT = 500;
    public static final int TYPE_INTERCROPDETAILS = 600;
    public static final int TYPE_COMPLAINTSSTATUSCOMMENTS = 700;
    public static final int TYPE_RECOM_FERTILIZER = 800;
    private  LinkedHashMap<String, String> muomDataMap;
    private  LinkedHashMap<String, String> mfertilizerTypeDataMap;
    private List itemsList;
    private Context mContext;
    public PalmDetailsEditListener palmDetailsEditListener;
    private LinkedHashMap<String, String> itemTypeDataMap, extraDataMap;
    private int adapterType = 0;
    private boolean fromHistory;
    private DataAccessHandler dataAccessHandler;

    private DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
    private DateFormat inputFormatYYMMDD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private  DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy ");

    public GenericTypeAdapter(Context context, ArrayList itemsList, LinkedHashMap<String, String> fertilizerTypeDataMap,
                              LinkedHashMap<String, String> uomDataMap, int adapterType) {
        this.itemsList = itemsList;
        this.itemTypeDataMap = fertilizerTypeDataMap;
       // this.mfertilizerTypeDataMap
        this.extraDataMap = uomDataMap;
        this.mContext = context;
        this.adapterType = adapterType;
    }

    public GenericTypeAdapter(Context context, ArrayList itemsList, LinkedHashMap<String, String> fertilizerTypeDataMap,
                              LinkedHashMap<String, String> uomDataMap, int adapterType, boolean fromHistory) {
        this.itemsList = itemsList;
        this.itemTypeDataMap = fertilizerTypeDataMap;
        this.extraDataMap = uomDataMap;
        this.mContext = context;
        this.adapterType = adapterType;
        this.fromHistory = fromHistory;
    }


    public GenericTypeAdapter(Context context, ArrayList itemsList, LinkedHashMap<String, String> nutritionDataMap, LinkedHashMap<String, String> uomDataMap1, LinkedHashMap<String, String> fertilizerTypeDataMap,
                              LinkedHashMap<String, String> uomDataMap, int adapterType) {

        this.itemsList = itemsList;
        this.itemTypeDataMap = nutritionDataMap;
        this.extraDataMap = uomDataMap1;
        this.mContext = context;
        this.mfertilizerTypeDataMap=fertilizerTypeDataMap;
        this.muomDataMap=uomDataMap;
        this.adapterType = adapterType;
        this.fromHistory = fromHistory;
    }

    public GenericTypeAdapter(Context context, ArrayList itemsList, LinkedHashMap<String, String> nutritionDataMap, LinkedHashMap<String, String> chemicalNameDataMap, LinkedHashMap<String, String> fertilizerTypeDataMap, LinkedHashMap<String, String> uomDataMap, int adapterType, boolean fromHistory) {

        this.itemsList = itemsList;
        this.itemTypeDataMap = nutritionDataMap;
        this.extraDataMap = chemicalNameDataMap;
        this.mContext = context;
        this.mfertilizerTypeDataMap=fertilizerTypeDataMap;
        this.muomDataMap=uomDataMap;
        this.adapterType = adapterType;
        this.fromHistory = fromHistory;
    }


    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_fertilization_application, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, final int position) {
        dataAccessHandler = new DataAccessHandler(mContext);
        String idForData = null;
        String ifForPercentage = null;
        String inputDataFirstItem = null, inputDataThirdItem = null, recommendeduomperItem;
        Double inputDataSecondItem = null;
        holder.FProductNameText1.setVisibility(View.GONE);
        holder.typeText1.setVisibility(View.GONE);
        holder.sourceText1.setVisibility(View.GONE);
        holder.deleteIcon.setVisibility((fromHistory) ? View.GONE : View.VISIBLE);
        if (adapterType == TYPE_FERTILIZER) {

            holder.recommendations_Tv.setVisibility(View.GONE);
            Fertilizer givenFertilizer = (Fertilizer) itemsList.get(position);
            idForData = String.valueOf(givenFertilizer.getFertilizerid());


            inputDataFirstItem = givenFertilizer.getLastapplieddate();
            String hd = inputDataFirstItem.replace("T", " ");
            Date date = null;
            try {
                date = inputFormatYYMMDD.parse(hd);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDate = outputFormat.format(date);

            inputDataSecondItem = givenFertilizer.getDosage();
            inputDataThirdItem = String.valueOf(givenFertilizer.getUomid());
            holder.tvFirst.setText("Fertilizer Name");
            holder.tvSecond.setText("LastApplied Date");
            holder.tvThird.setText("Dosage,UOM");

            holder.sourceText.setText("" + ((null != itemTypeDataMap) ? itemTypeDataMap.get(idForData) : ""));
            holder.typeText.setText("" + outputDate);
            holder.FProductNameText.setText("" + inputDataSecondItem + "(" + ((null != extraDataMap) ? extraDataMap.get(inputDataThirdItem) + ")" : ""));

            holder.recom_chemicals_LL.setVisibility(View.GONE);
            holder.recom_chemicals_data_LL.setVisibility(View.GONE);
//            holder.sourceText1.setText("" + ((null != itemTypeDataMap) ? itemTypeDataMap.get(idForData) : ""));
//            holder.typeText1.setText("" + inputDataFirstItem);
//            holder.FProductNameText1.setText("" + inputDataSecondItem + "(" + ((null != extraDataMap) ? extraDataMap.get(inputDataThirdItem) +")" : ""));

        }
        else if (adapterType == TYPE_PEST) {


            MainPestModel givenPest = (MainPestModel) itemsList.get(position);

            String percentagefortree= dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(givenPest.getPest().getPercTreesId()));

            idForData = String.valueOf(givenPest.getPest().getPestid());
            PestChemicalXref pestChemicalXref = givenPest.getmPestChemicalXref();
            if (null != pestChemicalXref) {
                inputDataThirdItem = String.valueOf(givenPest.getmPestChemicalXref().getChemicalId());

            }
            recommendeduomperItem = String.valueOf(givenPest.getPest().getRecommendedUOMId());
            holder.tvFirst.setText("Pest Name");
            holder.tvSecond.setText("chemical Applied");
            holder.tvThird.setText("Percentage Of Trees");
            Date date = null;
            try {
                date = inputFormatYYMMDD.parse(""+givenPest.getPest().getCreateddate());

            } catch (ParseException e) {
                e.printStackTrace();
            }
           // String pestDate = outputFormat.format(date);
            holder.sourceText.setText("" + ((null != itemTypeDataMap) ? itemTypeDataMap.get(idForData) : ""));
            holder.typeText.setText("" + ((null != extraDataMap) ? extraDataMap.get(inputDataThirdItem) : ""));
            //holder.FProductNameText.setText("" + ((null != mfertilizerTypeDataMap) ? mfertilizerTypeDataMap.get(""+givenPest.getPest().getPercTreesId()) : ""));
            holder.FProductNameText.setText("" + percentagefortree);
            idForData = String.valueOf(givenPest.getPest().getRecommendFertilizerProviderId());
            inputDataSecondItem = givenPest.getPest().getRecommendDosage();
            inputDataThirdItem = String.valueOf(givenPest.getPest().getRecommendUOMId());
            holder.recom_chemicals_LL.setVisibility(View.VISIBLE);
            holder.recom_chemicals_data_LL.setVisibility(View.VISIBLE);
            holder.FProductNameText1.setVisibility(View.VISIBLE);
            holder.typeText1.setVisibility(View.VISIBLE);
            holder.sourceText12.setText("Chemical");
            holder.typeText12.setText("UOM");
            holder.FProductNameText12.setText("Dosage");
            holder.uomper.setText("UOM Per");
            holder.sourceText1.setVisibility(View.VISIBLE);
            holder.sourceText1.setText("" + ((null != extraDataMap) ? extraDataMap.get(idForData) : ""));
            holder.typeText1.setText("" + ((null != muomDataMap) ? muomDataMap.get(inputDataThirdItem) : ""));
            holder.uompercount.setText("" + ((null != muomDataMap) ? muomDataMap.get(recommendeduomperItem) : ""));
            holder.FProductNameText1.setText("" + inputDataSecondItem  /*"(" + ((null != extraDataMap) ? extraDataMap.get(inputDataThirdItem) +")" : ""*/);

        } else if (adapterType == TYPE_DISEASE) {

            Disease givenDisease = (Disease) itemsList.get(position);

            String percentagefortree= dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(givenDisease.getPercTreesId()));
            Log.d("percentagefortree", percentagefortree + "");

            idForData = String.valueOf(givenDisease.getDiseaseid());
            inputDataThirdItem = String.valueOf(givenDisease.getChemicalid());
            holder.tvFirst.setText("Disease Name");
            holder.tvSecond.setText("chemical Applied");
            holder.tvThird.setText("Percentage Of Trees");
            Date date = null;
            try {
                date = inputFormatYYMMDD.parse(""+givenDisease.getCreateddate());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            //String diseaseDate = outputFormat.format(date);
            holder.sourceText.setText("" + ((null != itemTypeDataMap) ? itemTypeDataMap.get(idForData) : ""));
            holder.typeText.setText("" + ((null != extraDataMap) ? extraDataMap.get(inputDataThirdItem) : ""));
           //holder.FProductNameText.setText("" + ((null != mfertilizerTypeDataMap) ? mfertilizerTypeDataMap.get(""+givenDisease.getPercTreesId()) : ""));
           holder.FProductNameText.setText(percentagefortree + "");
           //holder.FProductNameText.setText(givenDisease.getPercTreesId());
            Log.d("PercentageofTree",((Disease) itemsList.get(position)).getPercTreesId() + "");
            recommendeduomperItem = String.valueOf(givenDisease.getRecommendedUOMId());
            idForData = String.valueOf(givenDisease.getRecommendFertilizerProviderId());
            inputDataSecondItem = givenDisease.getRecommendDosage();
            inputDataThirdItem = String.valueOf(givenDisease.getRecommendUOMId());
            String s=mfertilizerTypeDataMap.get(idForData);
            holder.recom_chemicals_LL.setVisibility(View.VISIBLE);
            holder.recom_chemicals_data_LL.setVisibility(View.VISIBLE);
            holder.FProductNameText1.setVisibility(View.VISIBLE);
            holder.typeText1.setVisibility(View.VISIBLE);
            holder.sourceText1.setVisibility(View.VISIBLE);
            holder.sourceText12.setText("Chemical");
            holder.typeText12.setText("UOM");
            holder.FProductNameText12.setText("Dosage");
            holder.uomper.setText("UOM Per");
            holder.sourceText1.setText("" + ((null != extraDataMap) ? extraDataMap.get(idForData) : ""));
            holder.typeText1.setText("" + ((null != muomDataMap) ? muomDataMap.get(inputDataThirdItem)  : ""));
            holder.uompercount.setText("" + ((null != muomDataMap) ? muomDataMap.get(recommendeduomperItem) : ""));
            holder.FProductNameText1.setText("" + inputDataSecondItem  /*"(" + ((null != extraDataMap) ? extraDataMap.get(inputDataThirdItem) +")" : ""*/);

        }else if (adapterType == TYPE_COMPLAINT) {
            FarmerComplaintsData givenDisease = (FarmerComplaintsData) itemsList.get(position);
            idForData = String.valueOf(givenDisease.getComplaintId());
            inputDataThirdItem = String.valueOf(givenDisease.getComplaintId());
            holder.sourceText.setText("" + ((null != itemTypeDataMap) ? itemTypeDataMap.get(idForData) : ""));
            holder.typeText.setText("" + ((null != extraDataMap) ? extraDataMap.get(inputDataThirdItem) : ""));
            holder.FProductNameText.setText("" + givenDisease.getComments());
        }else if (adapterType == TYPE_NUTRIENT) {
            Nutrient givenNutrient = (Nutrient) itemsList.get(position);

            String percentagefortree= dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(givenNutrient.getPercTreesId()));

            idForData = String.valueOf(givenNutrient.getNutrientid());
            inputDataThirdItem = String.valueOf(givenNutrient.getChemicalid());
            holder.tvFirst.setText("Nutrient Deficiency Name");
            holder.tvSecond.setText("Chemical Applied");
            holder.tvThird.setText("Percentage Of Trees");
            Date date = null;
            try {
                date = inputFormatYYMMDD.parse(""+givenNutrient.getCreateddate());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            //String nutrientDate = outputFormat.format(date);
            recommendeduomperItem = String.valueOf(givenNutrient.getRecommendedUOMId());
            holder.sourceText.setText("" + ((null != itemTypeDataMap) ? itemTypeDataMap.get(idForData) : ""+"--" + ((null != mfertilizerTypeDataMap) ? mfertilizerTypeDataMap.get(idForData) : "")));
            holder.typeText.setText("" + ((null != extraDataMap) ? extraDataMap.get(inputDataThirdItem) : ""));
            //holder.FProductNameText.setText("" + ((null != mfertilizerTypeDataMap) ? mfertilizerTypeDataMap.get(""+givenNutrient.getPercTreesId()) : ""));
            holder.FProductNameText.setText("" + percentagefortree);
            idForData = String.valueOf(givenNutrient.getRecommendFertilizerProviderId());
            inputDataSecondItem = givenNutrient.getRecommendDosage();
            inputDataThirdItem = String.valueOf(givenNutrient.getRecommendUOMId());
            holder.recom_chemicals_LL.setVisibility(View.VISIBLE);
            holder.recom_chemicals_data_LL.setVisibility(View.VISIBLE);
            holder.FProductNameText1.setVisibility(View.VISIBLE);
            holder.typeText1.setVisibility(View.VISIBLE);
            holder.sourceText1.setVisibility(View.VISIBLE);
            holder.sourceText12.setText("Fertilizer");
            holder.typeText12.setText("UOM");
            holder.FProductNameText12.setText("Dosage");
            holder.uomper.setText("UOM Per");
            holder.sourceText1.setText(""+ ((null != extraDataMap) ? extraDataMap.get(idForData) : ""));
            holder.typeText1.setText(""+ ((null != muomDataMap) ? muomDataMap.get(inputDataThirdItem)  : ""));
            holder.uompercount.setText("" + ((null != muomDataMap) ? muomDataMap.get(recommendeduomperItem) : ""));
            holder.FProductNameText1.setText("" + inputDataSecondItem  /*"(" + ((null != extraDataMap) ? extraDataMap.get(inputDataThirdItem) +")" : ""*/);

        }else if (adapterType == TYPE_COMPLAINTSSTATUSCOMMENTS) {
            holder.recom_chemicals_LL.setVisibility(View.GONE);
            holder.recom_chemicals_data_LL.setVisibility(View.GONE);
            holder.fertizier_LL.setVisibility(View.GONE);
            holder.layout_view.setVisibility(View.GONE);
            ComplaintStatusHistory givenStatus = (ComplaintStatusHistory) itemsList.get(position);
            idForData = String.valueOf(givenStatus.getStatusTypeId());
            inputDataThirdItem = String.valueOf(givenStatus.getComments());
            holder.sourceText.setText("" + ((null != itemTypeDataMap) ? itemTypeDataMap.get(idForData) : ""));
            holder.typeText.setText(inputDataThirdItem);

                String createddate = givenStatus.getCreatedDate().replace("T"," ");
                Date date = null;
                try {
                    date = inputFormatYYMMDD.parse(createddate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String complaintCreatedDate = outputFormat.format(date);
                holder.FProductNameText.setText(""+complaintCreatedDate);

            holder.deleteIcon.setVisibility(View.GONE);
        }
        else if (adapterType == TYPE_INTERCROPDETAILS) {
            InterCropPlantationXref givenDisease = (InterCropPlantationXref) itemsList.get(position);
            idForData = String.valueOf(""+(position+1));
            inputDataThirdItem = String.valueOf(givenDisease.getCropId());
            holder.sourceText.setText("" + ((null != itemTypeDataMap) ? itemTypeDataMap.get(idForData) : ""));
            holder.typeText.setText("" + ((null != extraDataMap) ? extraDataMap.get(inputDataThirdItem) : ""));
            holder.FProductNameText.setText(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_2));
        }


        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != palmDetailsEditListener) {
                    palmDetailsEditListener.onEditClicked(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView tvFirst,tvSecond,tvThird,sourceText, typeText, FProductNameText,sourceText1, typeText1, FProductNameText1,sourceText12, typeText12, FProductNameText12, uomper, uompercount;
        private ImageView deleteIcon;
        private LinearLayout recom_chemicals_LL,recom_chemicals_data_LL,fertizier_LL,mainll;
        private View layout_view;
        private TextView recommendations_Tv, norecord_tv;

        public SingleItemRowHolder(View view) {
            super(view);
            sourceText = (TextView) view.findViewById(R.id.sourceText);
            typeText = (TextView) view.findViewById(R.id.typeText);
            FProductNameText = (TextView) view.findViewById(R.id.FProductNameText);
            sourceText1 = (TextView) view.findViewById(R.id.sourceText1);
            typeText1 = (TextView) view.findViewById(R.id.typeText1);
            FProductNameText1 = (TextView) view.findViewById(R.id.FProductNameText1);
            sourceText12 = (TextView) view.findViewById(R.id.sourceText12);
            typeText12 = (TextView) view.findViewById(R.id.typeText12);
            FProductNameText12 = (TextView) view.findViewById(R.id.FProductNameText12);
            tvFirst = (TextView) view.findViewById(R.id.tvFirst);
            tvSecond = (TextView) view.findViewById(R.id.tvSecond);
            tvThird = (TextView) view.findViewById(R.id.tvThird);
            deleteIcon = (ImageView) itemView.findViewById(R.id.deleteIcon);
            recom_chemicals_LL = (LinearLayout) view.findViewById(R.id.recom_chemicals_LL);
            recom_chemicals_data_LL = (LinearLayout) view.findViewById(R.id.recom_chemicals_data_LL);
            fertizier_LL = (LinearLayout) view.findViewById(R.id.fertizier_LL);
            layout_view = (View) view.findViewById(R.id.layout_view);
            uomper =  (TextView) view.findViewById(R.id.uomper);
            uompercount =  (TextView) view.findViewById(R.id.uompercount);

            recommendations_Tv = (TextView) view.findViewById(R.id.recommendations_Tv);

            mainll = (LinearLayout) view.findViewById(R.id.mainll);
            norecord_tv =  (TextView) view.findViewById(R.id.norecord_tv);

        }
    }

    public void setEditClickListener(PalmDetailsEditListener palmDetailsEditListener) {
        this.palmDetailsEditListener = palmDetailsEditListener;
    }
}
