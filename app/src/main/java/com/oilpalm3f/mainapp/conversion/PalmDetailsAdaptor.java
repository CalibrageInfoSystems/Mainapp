package com.oilpalm3f.mainapp.conversion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.dbmodels.Plantation;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by siva on 31/05/17.
 */

//Adapter for showing dispatched sapling data
public class PalmDetailsAdaptor extends RecyclerView.Adapter<PalmDetailsAdaptor.SingleItemRowHolder>  {

    public PalmDetailsEditListener palmDetailsEditListener;
    public SaplingsPlantedListner saplingsPlantedListner;

    public List<Plantation> itemsList;
    public List<Plantation> sendlist;
    private Context mContext;
    private LinkedHashMap<String, String> saplingVendorMap, crossVarietyDataMap,saplingNurseryDataMap,originSaplingDataMap;

    public PalmDetailsAdaptor(Context context, List<Plantation> itemsList, LinkedHashMap<String, String> saplingVendorMaps,
                              LinkedHashMap<String, String> originSaplingDataMap, LinkedHashMap<String, String> saplingNurseryDataMap,
                              LinkedHashMap<String, String> varietyDataMap,SaplingsPlantedListner SaplingsPlantedListnervalue) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.saplingVendorMap = saplingVendorMaps;
        this.originSaplingDataMap = originSaplingDataMap;
        this.saplingNurseryDataMap = saplingNurseryDataMap;
        this.crossVarietyDataMap = varietyDataMap;
        this.saplingsPlantedListner = SaplingsPlantedListnervalue;
    }

    public void updateData(List<Plantation> itemsList){
        this.itemsList = itemsList;
        notifyDataSetChanged();
    }
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_palm_details, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, final int i)
    {


        holder.sapling_Nursery.setText(saplingNurseryDataMap.get(String.valueOf(itemsList.get(i).getNurserycode())));
        holder.vendorTv.setText(saplingVendorMap.get(String.valueOf(itemsList.get(i).getSaplingvendorid())));
        holder.origin_Sapling.setText(originSaplingDataMap.get(String.valueOf(itemsList.get(i).getSaplingsourceid())));
        holder.cross.setText(crossVarietyDataMap.get(String.valueOf(itemsList.get(i).getCropVarietyId())));
        holder.varietyTypeTxt.setText(""+itemsList.get(i).getTreescount());
        holder.varietyNameTxt.setText(""+itemsList.get(i).getAllotedarea()+"Ha");

        if (itemsList.get(i).getSaplingsplanted() != null) {
            holder.plantedsaplings.setText("" + itemsList.get(i).getSaplingsplanted());
            int missingtreecount = 0;
            missingtreecount = itemsList.get(i).getTreescount() - itemsList.get(i).getSaplingsplanted();
            holder.missingsaplings.setText(missingtreecount + "");
        }

//        itemsList.get(i).setSaplingsplanted(0);


        holder.plantedsaplings.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {




            }

            @Override
            public void afterTextChanged(Editable editable) {
                Integer intvalue = 0,outvalue = 0;
                itemsList.get(i).setSaplingsplanted(intvalue);
                holder.missingsaplings.setText(outvalue+"");
                Log.d(PalmDetailsAdaptor.class.getSimpleName(),"onTextChanged ***** :"+editable);
                String currentvalue = holder.plantedsaplings.getText().toString();
                if(currentvalue != null & !TextUtils.isEmpty(currentvalue))
                {
                    intvalue = Integer.parseInt(currentvalue);


                    if(intvalue <= itemsList.get(i).getTreescount())
                    {
                        if(intvalue > 0)
                            outvalue =  itemsList.get(i).getTreescount()- intvalue;

                        if(outvalue < 0)
                            outvalue = 0;

                        Log.d("#########","Outvalue :"+outvalue);

                        holder.missingsaplings.setText(outvalue+"");
                        itemsList.get(i).setSaplingsplanted(intvalue);
                    }else{
                        Log.d("#########   <","Outvalue :"+outvalue);
                        holder.plantedsaplings.setText("0");
                        Toast.makeText(mContext, "Planted Saplings can't be greater than total tree count", Toast.LENGTH_SHORT).show();
                        //itemsList.get(i).setSaplingsplanted(intvalue);
                        itemsList.get(i).setSaplingsplanted(Integer.parseInt(holder.plantedsaplings.getText().toString()));
                    }


                }

                Integer totalvalue = 0;
                Integer avaialbelSapingsCount = 0;
                for (Plantation itemsList : itemsList) {
                    Log.d("MAHESH", "each item count :"+itemsList.getSaplingsplanted());
                    if(itemsList.getSaplingsplanted() !=null && itemsList.getSaplingsplanted() > 0){
                        totalvalue = totalvalue+itemsList.getSaplingsplanted();
                        avaialbelSapingsCount = avaialbelSapingsCount+itemsList.getTreescount();
                    }else
                    {
                        totalvalue = totalvalue+0;
                        avaialbelSapingsCount = avaialbelSapingsCount+0;
                    }

                }
                Log.d(PalmDetailsAdaptor.class.getSimpleName(),"Saplings Total Count :"+totalvalue);

                saplingsPlantedListner.getPlantedSaplingsListner(totalvalue,avaialbelSapingsCount);




//                if (!editable.toString().equalsIgnoreCase("")) {
//
//                    Log.d(PalmDetailsAdaptor.class.getSimpleName(),"onTextChanged  ignore***** :"+editable);
//                    if (holder.plantedsaplings.getText().toString()!= null & (Integer.parseInt(holder.plantedsaplings.getText().toString().trim()) > itemsList.get(i).getTreescount())){
//
//                        Toast.makeText(mContext, "Planted Saplings can't be greater than total tree count", Toast.LENGTH_SHORT).show();
//                        holder.plantedsaplings.setText("0");
//
//                    }else if(Integer.parseInt(holder.plantedsaplings.getText().toString())  < itemsList.get(i).getTreescount()){
//
//                        holder.missingsaplings.setText(itemsList.get(i).getTreescount() - Integer.parseInt(holder.plantedsaplings.getText().toString())  + "");
//
//                    }else{
//
//                    }
//                }

            }
        });


   /*     holder.editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                palmDetailsEditListener.onEditClicked(i);
            }
        });*/
    }
    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public void setEditClickListener(PalmDetailsEditListener palmDetailsEditListener) {
        this.palmDetailsEditListener = palmDetailsEditListener;
    }

    public  boolean getTreesCount(){
        boolean isDataentered = true;

        for(int i = 0; i < itemsList.size(); i ++)
        {
            Log.d("PlantedTreesCount",itemsList.get(i).getSaplingsplanted().toString());
            if(itemsList.get(i).getSaplingsplanted() == 0){
                isDataentered = false;
            }
        }

        return isDataentered;

    }

    public  List<Plantation> getSendlist(){

        return itemsList;

    }



    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView vendorTv, varietyTypeTxt, varietyNameTxt,sapling_Nursery,origin_Sapling,cross, plantedsaplings, missingsaplings;
//        ImageView editView;

        public SingleItemRowHolder(View view) {
            super(view);

            this.vendorTv = (TextView) view.findViewById(R.id.vendorTv);
            this.varietyTypeTxt = (TextView) view.findViewById(R.id.varietyTypeTxt);
            this.varietyNameTxt = (TextView) view.findViewById(R.id.varietyNameTxt);
            this.sapling_Nursery = (TextView) view.findViewById(R.id.sapling_Nursery);
            this.origin_Sapling = (TextView) view.findViewById(R.id.origin_Sapling);
            this.cross = (TextView) view.findViewById(R.id.crossTv1);
            this.plantedsaplings = (TextView) view.findViewById(R.id.plantedSaplingsTxt);
            this.missingsaplings = (TextView) view.findViewById(R.id.missingsTressTxt);

//            editView = (ImageView) itemView.findViewById(R.id.deleteIcon);

        }

    }



}

