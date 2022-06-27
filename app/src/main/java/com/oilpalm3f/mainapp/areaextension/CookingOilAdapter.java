package com.oilpalm3f.mainapp.areaextension;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation;
import com.oilpalm3f.mainapp.dbmodels.CookingOil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Calibrage10 on 9/21/2016.
 */

//Cooking Oil Type Adapter
public class CookingOilAdapter extends RecyclerView.Adapter<CookingOilAdapter.MyHolder> {
    private Context mContext;
    private List<CookingOil> coockingOilTypelList;
    private LinkedHashMap oilTypeMap;

    public CookingOilAdapter(Context mContext, List<CookingOil> coockingOilTypelList, LinkedHashMap oilTypeMap) {
        this.mContext = mContext;
        this.coockingOilTypelList = coockingOilTypelList;
        this.oilTypeMap = oilTypeMap;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bookingView = inflater.inflate(R.layout.adapter_cooking_oil, null);
        MyHolder myHolder = new MyHolder(bookingView);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.cookingOilSpin.setText(""+oilTypeMap.get(String.valueOf(coockingOilTypelList.get(position).getCookingoiltypeid())));
        holder.BrandText.setText(coockingOilTypelList.get(position).getBrandname());
        holder.quantityText.setText(""+coockingOilTypelList.get(position).getMonthlyquantity());
        holder.amountPaidText.setText(""+coockingOilTypelList.get(position).getTotalpaidamount());
    }


    @Override
    public int getItemCount() {
        return coockingOilTypelList.size();
    }

    public void updateList(List<CookingOil> coockingOilTypelList, LinkedHashMap oilTypeMap) {
        this.coockingOilTypelList = coockingOilTypelList;
        this.oilTypeMap = oilTypeMap;
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView cookingOilSpin, BrandText, quantityText, amountPaidText;

        public MyHolder(View view) {
            super(view);
            cookingOilSpin = (TextView) itemView.findViewById(R.id.cookingOilSpin);
            BrandText = (TextView) itemView.findViewById(R.id.BrandText);
            quantityText = (TextView) itemView.findViewById(R.id.quantityText);
            amountPaidText = (TextView) itemView.findViewById(R.id.amountPaidText);
        }
    }
}
