package com.oilpalm3f.mainapp.cropmaintenance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.conversion.PalmDetailsEditListener;
import com.oilpalm3f.mainapp.dbmodels.RecommndFertilizer;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Calibrage11 on 9/30/2016.
 */

//Common Adapter to display entered fertilizer/pest/disease data
public class RecmndGenericTypeAdapter extends RecyclerView.Adapter<RecmndGenericTypeAdapter.SingleItemRowHolder> {

    public static final int TYPE_FERTILIZER = 100;
    public static final int TYPE_PEST = 200;
    public static final int TYPE_DISEASE = 300;
    public static final int TYPE_COMPLAINT = 400;
    public static final int TYPE_NUTRIENT = 500;
    public static final int TYPE_INTERCROPDETAILS = 600;
    public static final int TYPE_COMPLAINTSSTATUSCOMMENTS = 700;
    private ArrayList itemsList;
    private Context mContext;
    public PalmDetailsEditListener palmDetailsEditListener;
    private LinkedHashMap<String, String> itemTypeDataMap, extraDataMap;
    private int adapterType = 0;
    private boolean fromHistory;

    public RecmndGenericTypeAdapter(Context context, ArrayList<RecommndFertilizer> itemsList, LinkedHashMap<String, String> fertilizerTypeDataMap,
                                    LinkedHashMap<String, String> uomDataMap, int adapterType) {
        this.itemsList = itemsList;
        this.itemTypeDataMap = fertilizerTypeDataMap;
        this.extraDataMap = uomDataMap;
        this.mContext = context;
        this.adapterType = adapterType;
    }

    public RecmndGenericTypeAdapter(Context context, ArrayList itemsList, LinkedHashMap<String, String> fertilizerTypeDataMap,
                                    LinkedHashMap<String, String> uomDataMap, int adapterType, boolean fromHistory) {
        this.itemsList = itemsList;
        this.itemTypeDataMap = fertilizerTypeDataMap;
        this.extraDataMap = uomDataMap;
        this.mContext = context;
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
        String idForData = null;
        String inputDataFirstItem = null, inputDataThirdItem = null;
        Double inputDataSecondItem = null;
        holder.deleteIcon.setVisibility((fromHistory) ? View.GONE : View.VISIBLE);
        RecommndFertilizer givenFertilizer =  (RecommndFertilizer) itemsList.get(position);

            inputDataFirstItem = "" + givenFertilizer.getRecommendFertilizerProviderId();
            idForData = String.valueOf(givenFertilizer.getRecommendFertilizerProviderId());
            inputDataSecondItem = givenFertilizer.getRecommendDosage();
            inputDataThirdItem = String.valueOf(givenFertilizer.getRecommendUOMId());
            holder.sourceText.setText("Recommended Fertilizer\n\n" + ((null != itemTypeDataMap) ? itemTypeDataMap.get(idForData) : ""));
            holder.typeText.setText("Recommended UOM\n\n" + ((null != extraDataMap) ? extraDataMap.get(inputDataThirdItem) : ""));
            holder.FProductNameText.setText("Recommended Dosage\n\n" + inputDataSecondItem  /*"(" + ((null != extraDataMap) ? extraDataMap.get(inputDataThirdItem) +")" : ""*/);
            holder.recom_chemicals_LL.setVisibility(View.GONE);
            holder.recom_chemicals_data_LL.setVisibility(View.GONE);
            holder.FProductNameText1.setVisibility(View.GONE);
            holder.typeText1.setVisibility(View.GONE);
            holder.sourceText1.setVisibility(View.GONE);





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
        protected TextView sourceText, typeText, FProductNameText,sourceText1, typeText1, FProductNameText1;
        private ImageView deleteIcon;
        private LinearLayout recom_chemicals_LL,recom_chemicals_data_LL;

        public SingleItemRowHolder(View view) {
            super(view);
            sourceText = (TextView) view.findViewById(R.id.sourceText);
            typeText = (TextView) view.findViewById(R.id.typeText);
            FProductNameText = (TextView) view.findViewById(R.id.FProductNameText);
            deleteIcon = (ImageView) itemView.findViewById(R.id.deleteIcon);
            sourceText1 = (TextView) view.findViewById(R.id.sourceText1);
            typeText1 = (TextView) view.findViewById(R.id.typeText1);
            FProductNameText1 = (TextView) view.findViewById(R.id.FProductNameText1);
            recom_chemicals_LL = (LinearLayout) view.findViewById(R.id.recom_chemicals_LL);
            recom_chemicals_data_LL = (LinearLayout) view.findViewById(R.id.recom_chemicals_data_LL);

        }
    }

    public void setEditClickListener(PalmDetailsEditListener palmDetailsEditListener) {
        this.palmDetailsEditListener = palmDetailsEditListener;
    }
}
