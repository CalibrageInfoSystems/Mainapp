package com.oilpalm3f.mainapp.cropmaintenance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oilpalm3f.mainapp.R;

/**
 * Created by Calibrage11 on 7/22/2017.
 */

public class CropMaintainanceHistoryAdapter extends RecyclerView.Adapter<CropMaintainanceHistoryAdapter.Myholder> {
    private Context context;


    public CropMaintainanceHistoryAdapter(Context context){
        this.context = context;

    }
    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history_crop_maintainance, null);
        Myholder mh = new Myholder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Myholder extends RecyclerView.ViewHolder{
        public Myholder(View itemView) {
            super(itemView);
        }
    }
}
