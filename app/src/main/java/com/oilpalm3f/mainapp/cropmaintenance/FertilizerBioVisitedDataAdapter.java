package com.oilpalm3f.mainapp.cropmaintenance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.dbmodels.Fertilizer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FertilizerBioVisitedDataAdapter extends RecyclerView.Adapter<FertilizerBioVisitedDataAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<Fertilizer> itemsList;
    private DataAccessHandler dataAccessHandler;

    public FertilizerBioVisitedDataAdapter(Context context,ArrayList<Fertilizer> itemsList,DataAccessHandler dataAccessHandler) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.dataAccessHandler = dataAccessHandler;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adapter_fertilizerbio, parent, false);
        MyHolder viewHolder = new MyHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FertilizerBioVisitedDataAdapter.MyHolder myHolder, int i) {

        String fertilizername = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(itemsList.get(i).getBioFertilizerId()));
        DateFormat inputFormatYYMMDD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy ");

        String inputDataFirstItem = null;
        inputDataFirstItem = itemsList.get(i).getLastapplieddate();
        String hd = inputDataFirstItem.replace("T", " ");
        Date date = null;
        try {
            date = inputFormatYYMMDD.parse(hd);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDate = outputFormat.format(date);

        myHolder.fertname.setText(fertilizername + "");
        myHolder.fertapplieddate.setText(outputDate + "");
        myHolder.fertdosage.setText(itemsList.get(i).getDosage() + "gm");

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        LinearLayout fertnamell,fertapplieddatell,fertdosagell;

        TextView fertname,fertapplieddate,fertdosage;

        public MyHolder(View itemView) {
            super(itemView);

            fertnamell = (LinearLayout) itemView.findViewById(R.id.fertnamell);
            fertapplieddatell = (LinearLayout) itemView.findViewById(R.id.fertapplieddatell);
            fertdosagell = (LinearLayout) itemView.findViewById(R.id.fertdosagell);

            fertname = (TextView) itemView.findViewById(R.id.fertname);
            fertapplieddate = (TextView) itemView.findViewById(R.id.fertapplieddate);
            fertdosage = (TextView) itemView.findViewById(R.id.fertdosage);

        }
    }
}
