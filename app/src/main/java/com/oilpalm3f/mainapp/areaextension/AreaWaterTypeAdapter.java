package com.oilpalm3f.mainapp.areaextension;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.dbmodels.WaterResource;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Narendra on 4/25/2017.
 */

//Displaying Water Details Adapter
public class AreaWaterTypeAdapter extends RecyclerView.Adapter<AreaWaterTypeAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<WaterResource> mWaterTypeModelList;
    private LinkedHashMap sourceOfWaterMap;
    private OnCartChangedListener onCartChangedListener;


    public AreaWaterTypeAdapter(Context mContext, ArrayList<WaterResource> mWaterTypeModelList,LinkedHashMap sourceOfWaterMap) {
        this.mContext = mContext;
        this.mWaterTypeModelList = mWaterTypeModelList;
        this.sourceOfWaterMap = sourceOfWaterMap;
    }


    @Override
    public AreaWaterTypeAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bookingView = inflater.inflate(R.layout.area_water_adapter, null);
        AreaWaterTypeAdapter.MyHolder myHolder = new AreaWaterTypeAdapter.MyHolder(bookingView);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(AreaWaterTypeAdapter.MyHolder holder, final int position) {
        holder.typeTxt.setText(""+sourceOfWaterMap.get(String.valueOf(mWaterTypeModelList.get(position).getSourceofwaterid())));
        holder.numberTxt.setText(""+mWaterTypeModelList.get(position).getBorewellnumber());
        holder.dischargeTxt.setText(""+mWaterTypeModelList.get(position).getWaterdischargecapacity());
        holder.canalTxt.setText(""+mWaterTypeModelList.get(position).getCanalwater());

        if (null != mWaterTypeModelList.get(position)) {
            holder.numberTxtLay.setVisibility((null != mWaterTypeModelList.get(position).getBorewellnumber() && mWaterTypeModelList.get(position).getBorewellnumber() != 0) ? View.VISIBLE : View.GONE);
            holder.dischargeTxtLay.setVisibility(mWaterTypeModelList.get(position).getWaterdischargecapacity() != 0.0 ? View.VISIBLE : View.GONE);
            holder.canalTxtLay.setVisibility(mWaterTypeModelList.get(position).getCanalwater() != 0.0 ? View.VISIBLE : View.GONE);
        }

        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCartChangedListener.setCartClickListener("edit",position);
            }
        });

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCartChangedListener.setCartClickListener("delete",position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mWaterTypeModelList != null ? mWaterTypeModelList.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView numberTxt, dischargeTxt, canalTxt, typeTxt;
        private LinearLayout numberTxtLay, dischargeTxtLay,canalTxtLay;
        private ImageView editIcon,deleteIcon;

        public MyHolder(View view) {
            super(view);
            numberTxt = (TextView) itemView.findViewById(R.id.numberTxt);
            dischargeTxt = (TextView) itemView.findViewById(R.id.dischargeTxt);
            canalTxt = (TextView) itemView.findViewById(R.id.canalTxt);
            typeTxt = (TextView) itemView.findViewById(R.id.typeTxt);
            numberTxtLay = (LinearLayout) itemView.findViewById(R.id.numberTxtLay);
            dischargeTxtLay = (LinearLayout) itemView.findViewById(R.id.dischargeTxtLay);
            canalTxtLay = (LinearLayout) itemView.findViewById(R.id.canalTxtLay);
            editIcon = (ImageView) itemView.findViewById(R.id.editIcon);
            deleteIcon = (ImageView) itemView.findViewById(R.id.trashIcon);

        }
    }
    public void setOnCartChangedListener(OnCartChangedListener onCartChangedListener) {
        this.onCartChangedListener = onCartChangedListener;
    }

    public interface OnCartChangedListener {
        void setCartClickListener(String status,int position);
    }
}

