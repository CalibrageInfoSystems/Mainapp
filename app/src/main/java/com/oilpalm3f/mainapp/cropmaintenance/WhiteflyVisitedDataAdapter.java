package com.oilpalm3f.mainapp.cropmaintenance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.dbmodels.WhiteFlyAssessment;

import java.util.ArrayList;

public class WhiteflyVisitedDataAdapter extends RecyclerView.Adapter<WhiteflyVisitedDataAdapter.MyHolder> {


    private Context mContext;
    private ArrayList<WhiteFlyAssessment> itemsList;
    private DataAccessHandler dataAccessHandler;

    public WhiteflyVisitedDataAdapter(Context context, ArrayList itemsList,DataAccessHandler dataAccessHandler) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.dataAccessHandler = dataAccessHandler;
    }

//    @NonNull
//    @Override
//    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_whiteflyassesment, null);
//        MyHolder mh = new MyHolder(v);
//        return mh;
//    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adapter_whiteflyassesment, parent, false);
        MyHolder viewHolder = new MyHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WhiteflyVisitedDataAdapter.MyHolder myHolder, int i) {

        myHolder.whiteflyquestion.setText(itemsList.get(i).getQuestion());
        myHolder.whiteflyvalue.setText(itemsList.get(i).getValue() + "");
        myHolder.whiteflyyear.setText(itemsList.get(i).getYear() + "");

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView whiteflyquestion, whiteflyvalue,whiteflyyear;

        public MyHolder(View itemView) {
            super(itemView);

            whiteflyquestion = itemView.findViewById(R.id.whiteflyquestion);
            whiteflyvalue = itemView.findViewById(R.id.whiteflyvalue);
            whiteflyyear = itemView.findViewById(R.id.whiteflyyear);
        }
    }
}
