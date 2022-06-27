package com.oilpalm3f.mainapp.areaextension;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.dbmodels.ExistingFarmerData;
import com.oilpalm3f.mainapp.dbmodels.Farmer;
import com.oilpalm3f.mainapp.ui.RecyclerItemClickListener;

import java.util.ArrayList;

//To display duplicate farmers information when contact number/bank account number/id proof numbers are matched
public class FarmerViewDetailsAdapter extends RecyclerView.Adapter<FarmerViewDetailsAdapter.MyHolder> {

    private RecyclerItemClickListener recyclerViewListener;
    private Context mContext;
    ArrayList<ExistingFarmerData> list;

    public FarmerViewDetailsAdapter(Context mContext, ArrayList<ExistingFarmerData> list) {
        this.list= list;
        this.mContext =mContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bookingView = inflater.inflate(R.layout.alertdilog_farmer_adapter, null);
        MyHolder myHolder = new MyHolder(bookingView);
        return myHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {

            String  middleName =" ";

        if (list.get(i).getMiddleName().equalsIgnoreCase("null"))
        {
            holder.farmerName.setText(""+list.get(i).getFirstName() +"\t"+ middleName+"\t"+ list.get(i).getLastName());

        }
        else {
            holder.farmerName.setText("" + list.get(i).getFirstName() + "\t" + list.get(i).getMiddleName() + "\t" + list.get(i).getLastName());

        }
        holder.farmercode.setText(list.get(i).getCode());
        holder.villagename.setText(list.get(i).getVillageName());
        holder.clustername.setText(list.get(i).getClusterName());



        holder.mainlyt.setOnClickListener(v -> {

                    recyclerViewListener.onItemSelected(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setDuplicateFarmers(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerViewListener = recyclerItemClickListener;
    }
    public class MyHolder extends RecyclerView.ViewHolder{
        TextView farmerName, farmercode,villagename, clustername;
        LinearLayout mainlyt;

        public MyHolder(@NonNull View itemView) {
            super(itemView);


            mainlyt =itemView.findViewById(R.id.mainlyt);


            farmerName =itemView.findViewById(R.id.farmerName);
            farmercode =itemView.findViewById(R.id.farmercode);
            villagename =itemView.findViewById(R.id.villageName);
            clustername =itemView.findViewById(R.id.clusterName);
        }
    }

}
