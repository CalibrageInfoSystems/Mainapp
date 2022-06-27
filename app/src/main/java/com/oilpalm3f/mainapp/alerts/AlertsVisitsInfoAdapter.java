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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Siva on 20-11-2017.
 */

//Display Alerts for Visits

public class AlertsVisitsInfoAdapter extends RecyclerView.Adapter<AlertsVisitsInfoAdapter.AlertVisitsDetailsViewHolder>{

    private static final String LOG_TAG = AlertPlotInfoAdapter.class.getName();
    private List<AlertsVisitsInfo> mList;
    private Context context;
    private AlertsVisitsInfo item;
    private RecyclerItemClickListener recyclerItemClickListener;

    private DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private  DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public AlertsVisitsInfoAdapter(Context context, List<AlertsVisitsInfo> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void addItems(List<AlertsVisitsInfo> list) {
        this.mList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @Override
    public AlertsVisitsInfoAdapter.AlertVisitsDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alerts_visits_item, null);
        AlertsVisitsInfoAdapter.AlertVisitsDetailsViewHolder myHolder = new AlertsVisitsInfoAdapter.AlertVisitsDetailsViewHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final AlertsVisitsInfoAdapter.AlertVisitsDetailsViewHolder holder, final int position) {
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
        if(item.getDateofplanting() != null && !(item.getDateofplanting().trim().isEmpty())){
            String dop = item.getDateofplanting().replace("T"," ");

            Date date = null;
            try {
                date = inputFormat.parse(dop);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            String DateOP = outputFormat.format(date);
            holder.tvdateofplanting.setText(DateOP);
        }else{
            holder.tvdateofplanting.setText("");
        }
        if(item.getPlotvisiteddate() != null && !(item.getPlotvisiteddate().trim().isEmpty())){
            String pvd = item.getPlotvisiteddate().replace("T"," ");

            Date date = null;
            try {
                date = inputFormat.parse(pvd);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            String PlotVD = outputFormat.format(date);
            holder.tvplotvisiteddate.setText(PlotVD);
        }else{
            holder.tvplotvisiteddate.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateAdapter(List<AlertsVisitsInfo> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

//    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
//        this.recyclerItemClickListener = recyclerItemClickListener;
//    }

    public class AlertVisitsDetailsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvplotCode;
        private TextView tvfarmerCode;
        private TextView tvfirstName;
        private TextView tvcontactNumber;
        private TextView tvmandalName;
        private TextView tvvillageName;
        private TextView tvtotalPlotArea;
        private TextView tvdateofplanting;
        private TextView tvplotvisiteddate;

        private View convertView;

        public AlertVisitsDetailsViewHolder(View view) {
            super(view);
            convertView = view;

            tvplotCode = (TextView) view.findViewById(R.id.tvPlotID);
            tvfarmerCode = (TextView) view.findViewById(R.id.tv_farmercode);
            tvfirstName = (TextView) view.findViewById(R.id.tv_farmername);
            tvcontactNumber = (TextView) view.findViewById(R.id.tvContactNumber);
            tvmandalName = (TextView) view.findViewById(R.id.tvPlotMandal);
            tvvillageName = (TextView) view.findViewById(R.id.tvPlotVillage);
            tvtotalPlotArea = (TextView) view.findViewById(R.id.tvSize);
            tvdateofplanting = (TextView) view.findViewById(R.id.tvdop);
            tvplotvisiteddate = (TextView) view.findViewById(R.id.tvplotvisiteddate);
        }
    }
}
