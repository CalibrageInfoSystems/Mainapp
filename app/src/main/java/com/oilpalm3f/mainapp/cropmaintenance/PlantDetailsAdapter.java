package com.oilpalm3f.mainapp.cropmaintenance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.alerts.AlertsPlotInfo;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.dbmodels.Plantation;
import com.oilpalm3f.mainapp.dbmodels.Plot;
import com.oilpalm3f.mainapp.ui.RecyclerItemClickListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Siva on 15-11-2017.
 */

//Used to display entered crop plantation data
public class PlantDetailsAdapter extends RecyclerView.Adapter<PlantDetailsAdapter.PlotDetailsViewHolder> {

    private static final String LOG_TAG = PlantDetailsAdapter.class.getName();
    private ArrayList<Plantation> mList;
    private Context context;
    private Plantation item;
    private RecyclerItemClickListener recyclerItemClickListener;
    DataAccessHandler dataAccessHandler;
    int type;


    private DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private  DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public PlantDetailsAdapter(Context context, ArrayList<Plantation> mPlantationList, DataAccessHandler mdataAccessHandler,int Type) {
        this.context = context;
        this.mList = mPlantationList;
        this.dataAccessHandler=mdataAccessHandler;
        type = Type;
    }

    public void addItems(List<Plantation> list) {
        this.mList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @Override
    public PlantDetailsAdapter.PlotDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.crop_plantation, null);
        PlantDetailsAdapter.PlotDetailsViewHolder myHolder = new PlantDetailsAdapter.PlotDetailsViewHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final PlantDetailsAdapter.PlotDetailsViewHolder holder, final int position) {
        item = mList.get(position);

        if(type == 1) {
            Plot selectedPlot = (Plot) dataAccessHandler.getSelectedPlotData(Queries.getInstance().getSelectedPlot(CommonConstants.PLOT_CODE), 0);
            if (null != selectedPlot) {
                String dateofPlanting = selectedPlot.getDateofPlanting().replace("T", " ");

                Date date = null;
                try {
                    date = inputFormat.parse(dateofPlanting);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String outputDate = outputFormat.format(date);

                holder.dateplanting_text.setText(outputDate);


            }
            holder.delete.setVisibility(View.GONE);
        }else {
            holder.datePlantationLL.setVisibility(View.GONE);
        }


        holder.areaallocated_text.setText("" + item.getAllotedarea() + " ha");
        holder.saplingnursery_text.setText("" + dataAccessHandler.getGenericData(Queries.getInstance().getSaplingsNursery()).get(String.valueOf(item.getNurserycode())));
        holder.saplingsourse_text.setText("" + dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("17")).get(String.valueOf(item.getSaplingsourceid())));
        holder.saplingvendor_text.setText("" + dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("19")).get(String.valueOf(item.getSaplingvendorid())));
        holder. variety_text.setText("" + dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("18")).get(String.valueOf(item.getCropVarietyId())));
        holder.counttrees_text.setText("" + item.getTreescount());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateAdapter(ArrayList<Plantation> list,int Type) {
        this.mList = list;
        type = Type;
        notifyDataSetChanged();
    }

    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public class PlotDetailsViewHolder extends RecyclerView.ViewHolder {

        private  View rootView;
        private LinearLayout datePlantationLL;
        private ImageView delete;
        private TextView  dateplanting_text, areaallocated_text, saplingnursery_text, saplingsourse_text, saplingvendor_text, variety_text, counttrees_text;


        public PlotDetailsViewHolder(View view) {
            super(view);
            rootView = view;

            datePlantationLL =  rootView.findViewById(R.id.datePlantationLL);
            dateplanting_text = (TextView) rootView.findViewById(R.id.dateplanting_text);
            areaallocated_text = (TextView) rootView.findViewById(R.id.areaallocated_text);
            saplingnursery_text = (TextView) rootView.findViewById(R.id.saplingnursery_text);
            saplingsourse_text = (TextView) rootView.findViewById(R.id.saplingsource_text);
            saplingvendor_text = (TextView) rootView.findViewById(R.id.saplingvendor_text);
            variety_text = (TextView) rootView.findViewById(R.id.variety_text);
            counttrees_text = (TextView) rootView.findViewById(R.id.counttrees_text);
            delete = rootView.findViewById(R.id.delete);

        }
    }
}
