package com.oilpalm3f.mainapp.cropmaintenance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.dbmodels.Fertilizer;
import com.oilpalm3f.mainapp.dbmodels.WhiteFlyAssessment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FertilizerNonBioVisitedDataAdapter extends RecyclerView.Adapter<FertilizerNonBioVisitedDataAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<Fertilizer> itemsList;
    private DataAccessHandler dataAccessHandler;

    public FertilizerNonBioVisitedDataAdapter(Context context,ArrayList<Fertilizer> itemsList,DataAccessHandler dataAccessHandler) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.dataAccessHandler = dataAccessHandler;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adapter_fertilizernonbio, parent, false);
       MyHolder viewHolder = new MyHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder myHolder, int i) {

        String fertilizername = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(itemsList.get(i).getFertilizerid()));
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

        LinearLayout fertilizerappliedll,fertilizermonthyearll,nameoftheshopll,sourceoffertilizerll,fertcommentsll,fertapptypell,fertnamell,fertapplieddatell,fertdosagell;

        TextView fertilizerapplied, fertilizermonthyear,nameoftheshop,sourceoffertilizer,fertcomments,fertapptype,fertname,fertapplieddate,fertdosage;

        public MyHolder(View itemView) {
            super(itemView);

//            fertilizerappliedll = (LinearLayout) itemView.findViewById(R.id.fertilizerappliedll);
//            fertilizermonthyearll = (LinearLayout) itemView.findViewById(R.id.fertilizermonthyearll);
//            nameoftheshopll = (LinearLayout) itemView.findViewById(R.id.nameoftheshopll);
//            sourceoffertilizerll = (LinearLayout) itemView.findViewById(R.id.sourceoffertilizerll);
//            fertcommentsll = (LinearLayout) itemView.findViewById(R.id.fertcommentsll);
//            fertapptypell = (LinearLayout) itemView.findViewById(R.id.fertapptypell);
            fertnamell = (LinearLayout) itemView.findViewById(R.id.fertnamell);
            fertapplieddatell = (LinearLayout) itemView.findViewById(R.id.fertapplieddatell);
            fertdosagell = (LinearLayout) itemView.findViewById(R.id.fertdosagell);

//             fertilizerapplied = (TextView) itemView.findViewById(R.id.fertilizerapplied);
//             fertilizermonthyear = (TextView) itemView.findViewById(R.id.fertilizermonthyear);
//             nameoftheshop = (TextView) itemView.findViewById(R.id.nameoftheshop);
//             sourceoffertilizer = (TextView) itemView.findViewById(R.id.sourceoffertilizer);
//             fertcomments = (TextView) itemView.findViewById(R.id.fertcomments);
//             fertapptype = (TextView) itemView.findViewById(R.id.fertapptype);
            fertname = (TextView) itemView.findViewById(R.id.fertname);
            fertapplieddate = (TextView) itemView.findViewById(R.id.fertapplieddate);
            fertdosage = (TextView) itemView.findViewById(R.id.fertdosage);
        }
    }
}
