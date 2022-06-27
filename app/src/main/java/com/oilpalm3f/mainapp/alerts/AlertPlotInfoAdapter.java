package com.oilpalm3f.mainapp.alerts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.Log;

import com.oilpalm3f.mainapp.ui.RecyclerItemClickListener;


import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Siva on 15-11-2017.
 */


//Display Alerts for Plots Followup
public class AlertPlotInfoAdapter extends RecyclerView.Adapter<AlertPlotInfoAdapter.AlertPlotDetailsViewHolder> {

    private static final String LOG_TAG = AlertPlotInfoAdapter.class.getName();
    private List<AlertsPlotInfo> mList;
    private Context context;
    private AlertsPlotInfo item;
    private RecyclerItemClickListener recyclerItemClickListener;

    private DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private  DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public AlertPlotInfoAdapter(Context context, List<AlertsPlotInfo> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void addItems(List<AlertsPlotInfo> list) {
        this.mList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @Override
    public AlertPlotInfoAdapter.AlertPlotDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alerts_list_item, null);
        AlertPlotInfoAdapter.AlertPlotDetailsViewHolder myHolder = new AlertPlotInfoAdapter.AlertPlotDetailsViewHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final AlertPlotInfoAdapter.AlertPlotDetailsViewHolder holder, final int position) {
        item = mList.get(position);
        String lastName = "", middleName = "";
        if (!TextUtils.isEmpty(item.getLastName())) {
            lastName = item.getLastName();
        }
        if (!TextUtils.isEmpty(item.getMiddleName()) && !
                item.getMiddleName().equalsIgnoreCase("null")) {
            middleName = item.getMiddleName();
        }
        holder.tvfirstName.setText(item.getFirstName().trim() + " " + middleName + " " + lastName.trim() + " " + "(" + item.getFarmerCode().trim() + ")");
        if (!TextUtils.isEmpty(item.getContactNumber()) && !  item.getContactNumber().equalsIgnoreCase("null")) {
            holder.tvcontactNumber.setText(item.getContactNumber());
        } else {
            holder.tvcontactNumber.setText("");
        }
        holder.tvvillageName.setText(item.getVillageName() != null ? item.getVillageName().trim() : "");
        holder.tvmandalName.setText(item.getMandalName() != null ? item.getMandalName().trim() : "");
        holder.tvplotCode.setText(item.getPlotCode() != null ? item.getPlotCode().trim() : "");
        holder.tvfarmerCode.setText(item.getFarmerCode() != null ? item.getFarmerCode().trim() : "");
        holder.tvtotalPlotArea.setText(item.getTotalPlotArea() != null ? item.getTotalPlotArea().trim() : "");
        holder.tvpotentialScore.setText(item.getPotentialScore() != null ? item.getPotentialScore().trim() : "");
        holder.tvcropName.setText(item.getCropName() != null ? item.getCropName().trim() : "");
        if(item.getLastVistDate() != null && !(item.getLastVistDate().trim().isEmpty())){
            String lvd = item.getLastVistDate().replace("T"," ");

            Date date = null;
            try {
                date = inputFormat.parse(lvd);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            String lastDate = outputFormat.format(date);

            holder.tvlastVistDate.setText(lastDate);
        }else{
            holder.tvlastVistDate.setText("");
        }
        if(item.getHarvestDate() != null && !(item.getHarvestDate().trim().isEmpty())){

/*
            String hd = item.getHarvestDate().replace("T"," ");

            Date date = null;
            try {
                date = inputFormat.parse(hd);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            String outputDateHD = outputFormat.format(date);*/

            holder.tvharvestDate.setText(item.getHarvestDate());
        }else{
            holder.tvharvestDate.setText("");
        }
        holder.tvPrioritization.setText(item.getPrioritization() != null ? item.getPrioritization().trim() : "");
        holder.tvUserName.setText(item.getUserName() != null ? item.getUserName()  : "");


//        holder.convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.v(LOG_TAG, "@@@ on item clicked");
//                recyclerItemClickListener.onItemSelected(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateAdapter(List<AlertsPlotInfo> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

//    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
//        this.recyclerItemClickListener = recyclerItemClickListener;
//    }

    public class AlertPlotDetailsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvplotCode;
        private TextView tvfarmerCode;
        private TextView tvfirstName;
        private TextView tvcontactNumber;
        private TextView tvmandalName;
        private TextView tvvillageName;
        private TextView tvtotalPlotArea;
        private TextView tvpotentialScore;
        private TextView tvcropName;
        private TextView tvlastVistDate;
        private TextView tvharvestDate;
        private TextView tvPrioritization,tvUserName;
        private View convertView;

        public AlertPlotDetailsViewHolder(View view) {
            super(view);
            convertView = view;

            tvplotCode = (TextView) view.findViewById(R.id.tvPlotID);
            tvfarmerCode = (TextView) view.findViewById(R.id.tv_farmercode);
            tvfirstName = (TextView) view.findViewById(R.id.tv_farmername);
            tvcontactNumber = (TextView) view.findViewById(R.id.tvContactNumber);
            tvmandalName = (TextView) view.findViewById(R.id.tvPlotMandal);
            tvvillageName = (TextView) view.findViewById(R.id.tvPlotVillage);
            tvtotalPlotArea = (TextView) view.findViewById(R.id.tvSize);
            tvpotentialScore = (TextView) view.findViewById(R.id.tvPlotScore);
            tvcropName = (TextView) view.findViewById(R.id.tvCurrentCrop);
            tvlastVistDate = (TextView) view.findViewById(R.id.tvLastVisistedDate);
            tvharvestDate = (TextView) view.findViewById(R.id.tvHarvestingDate);
            tvPrioritization = (TextView) view.findViewById(R.id.tvPlotPrioritization);
            tvUserName= (TextView) view.findViewById(R.id.tvUserName);

        }
    }
}
