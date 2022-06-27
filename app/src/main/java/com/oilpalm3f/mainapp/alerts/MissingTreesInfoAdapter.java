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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skasam on 11/17/2017.
 */

//Display Alerts for Missing Trees

public class MissingTreesInfoAdapter extends RecyclerView.Adapter<MissingTreesInfoAdapter.AlertMissingTreesViewHolder>{

    private static final String LOG_TAG = AlertPlotInfoAdapter.class.getName();
    private List<MissingTressInfo> mList;
    private Context context;
    private MissingTressInfo item;
    private RecyclerItemClickListener recyclerItemClickListener;

    public MissingTreesInfoAdapter(Context context, List<MissingTressInfo> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void addItems(List<MissingTressInfo> list) {
        this.mList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @Override
    public MissingTreesInfoAdapter.AlertMissingTreesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alerts_missingtrees_item, null);
        MissingTreesInfoAdapter.AlertMissingTreesViewHolder myHolder = new MissingTreesInfoAdapter.AlertMissingTreesViewHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MissingTreesInfoAdapter.AlertMissingTreesViewHolder holder, final int position) {
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
        holder.tvvillageName.setText(item.getVillageName() != null ? item.getVillageName().trim() : "");
        holder.tvmandalName.setText(item.getMandalName() != null ? item.getMandalName().trim() : "");
        holder.tvplotCode.setText(item.getPlotCode() != null ? item.getPlotCode().trim() : "");
        holder.tvfarmerCode.setText(item.getFarmerCode() != null ? item.getFarmerCode().trim() : "");
        holder.tvsaplingsplanted.setText(item.getSaplingsplanted() != null ? item.getSaplingsplanted().trim() : "");
        holder.tvcurrenttrees.setText(item.getCurrentTrees() != null ? item.getCurrentTrees().trim() : "");
        holder.tvmissingstrees.setText(item.getMissingTrees() != null ? item.getMissingTrees().trim() : "");
        holder.tvpercentage.setText(item.getPercent() != null ? item.getPercent().trim() : "");

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

    public void updateAdapter(List<MissingTressInfo> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

//    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
//        this.recyclerItemClickListener = recyclerItemClickListener;
//    }

    public class AlertMissingTreesViewHolder extends RecyclerView.ViewHolder {

        private TextView tvplotCode;
        private TextView tvfarmerCode;
        private TextView tvfirstName;
        private TextView tvmandalName;
        private TextView tvvillageName;
        private TextView tvsaplingsplanted;
        private TextView tvcurrenttrees;
        private TextView tvmissingstrees;
        private TextView tvpercentage;
        private View convertView;

        public AlertMissingTreesViewHolder(View view) {
            super(view);
            convertView = view;
            tvplotCode = (TextView) view.findViewById(R.id.tvPlotID);
            tvfarmerCode = (TextView) view.findViewById(R.id.tv_farmercode);
            tvfirstName = (TextView) view.findViewById(R.id.tv_farmername);
            tvmandalName = (TextView) view.findViewById(R.id.tvPlotMandal);
            tvvillageName = (TextView) view.findViewById(R.id.tvPlotVillage);
            tvsaplingsplanted = (TextView) view.findViewById(R.id.tvsaplingsplanted);
            tvcurrenttrees = (TextView) view.findViewById(R.id.tvcurrenttrees);
            tvmissingstrees = (TextView) view.findViewById(R.id.tvmissingtrees);
            tvpercentage = (TextView) view.findViewById(R.id.tvpercent);
        }
    }
}
