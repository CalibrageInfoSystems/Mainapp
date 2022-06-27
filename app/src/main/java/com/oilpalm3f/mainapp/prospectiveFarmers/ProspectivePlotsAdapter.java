package com.oilpalm3f.mainapp.prospectiveFarmers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.conversion.IdProofsListAdapter;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.farmersearch.DisplayPlotsFragment;
import com.oilpalm3f.mainapp.utils.UiUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by siva on 11/06/17.
 */

//to display prospective plots
public class ProspectivePlotsAdapter extends RecyclerView.Adapter<ProspectivePlotsAdapter.ProspectiveViewHolder> {
    private Context mContext;
    private List<ProspectivePlotsModel> prospectivePlotsModels;
    private double currentLatitude,currentLongitude;

    private  DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private  DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public ProspectivePlotsAdapter(Context mContext, List<ProspectivePlotsModel> prospectivePlotsModels) {
        this.mContext = mContext;
        this.prospectivePlotsModels = prospectivePlotsModels;
    }

    @Override
    public ProspectiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bookingView = inflater.inflate(R.layout.big_plots_view, null);
        ProspectiveViewHolder myHolder = new ProspectiveViewHolder(bookingView);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(ProspectiveViewHolder holder, final int position) {
        holder.mandalNameTxt.setText(prospectivePlotsModels.get(position).getMandalName());
        holder.villageNameTxt.setText(prospectivePlotsModels.get(position).getPlotVillageName());
        holder.plotCodeTxt.setText(prospectivePlotsModels.get(position).getPlotID());
       // CommonConstants.PLOT_CODE = prospectivePlotsModels.get(position).getPlotID();
        final String selectedLatLong = new DataAccessHandler(mContext).getLatLongs(Queries.getInstance().queryVerifyGeoTag());
        final String[] yieldDataArr = selectedLatLong.split("-");
       final String latlong[]=  new DisplayPlotsFragment().getLatLong(mContext,false).split("@");
        // Comment by Sushil to change LatLong Logic
        // actualDistance = CommonUtils.distance(currentLatitude, currentLongitude, Double.parseDouble(yieldDataArr[0]), Double.parseDouble(yieldDataArr[1]), 'm');
        try {
            currentLatitude = Double.parseDouble(latlong[0]);
            currentLongitude = Double.parseDouble(latlong[1]);
        } catch (Exception e) {
            currentLatitude = 0.0;
            currentLongitude = 0.0;
        }
        holder.totalPlotAreaTxt.setText(""+prospectivePlotsModels.get(position).getPlotArea());
        holder.plotIncomeTxt.setText(""+prospectivePlotsModels.get(position).getPlotIncome());
        holder.potentialScoreTxt.setText(""+prospectivePlotsModels.get(position).getPotentialScore());
        holder.cropTxt.setText(""+prospectivePlotsModels.get(position).getPlotCrops());

        String inputDate = ""+prospectivePlotsModels.get(position).getLastVisitedDate();
        inputDate = inputDate.replace("T"," ");
        Date date = null;
        try {
            date = inputFormat.parse(inputDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String outputDate = outputFormat.format(date);


        holder.latestVisitedDateTxt.setText(outputDate);

        holder.ivb_plot_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectedLatLong.isEmpty()) {
                    String uri = "http://maps.google.com/maps?saddr=" + currentLatitude + "," + currentLongitude + "(" + "Village Name = " + prospectivePlotsModels.get(position).getPlotVillageName() + "/" + "MandalName = " + prospectivePlotsModels.get(position).getMandalName() + ")&daddr=" + yieldDataArr[0] + "," + yieldDataArr[1] + " (" + "Where the party is at" + ")";
                    /*String uri = String.format(Locale.ROOT, "geo:%f,%f?z=%d&q=%f,%f (%s)",
                            ""+latlong[0], ""+latlong[1], 100, ""+yieldDataArr[0], ""+yieldDataArr[1], "Village Name = " + prospectivePlotsModels.get(position).getPlotVillageName() +  "MandalName = " + prospectivePlotsModels.get(position).getMandalName());
                    */Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    mContext.startActivity(intent);
                }
                else{
                    UiUtils.showCustomToastMessage("This Plot has not Lat_Long to show plot on Google Map",mContext,1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return prospectivePlotsModels.size();
    }

    public static class ProspectiveViewHolder extends RecyclerView.ViewHolder {
        TextView plotCodeTxt, totalPlotAreaTxt, villageNameTxt, mandalNameTxt, plotIncomeTxt, potentialScoreTxt, cropTxt, latestVisitedDateTxt;
        ImageButton ivb_plot_location;
        public ProspectiveViewHolder(View itemView) {
            super(itemView);
            plotCodeTxt = (TextView) itemView.findViewById(R.id.tvplotidvalue);
            totalPlotAreaTxt = (TextView) itemView.findViewById(R.id.tvplotareavalue);
            villageNameTxt = (TextView) itemView.findViewById(R.id.tvplotvillagevalue);
            mandalNameTxt = (TextView) itemView.findViewById(R.id.tvplotmandal);
            plotIncomeTxt = (TextView) itemView.findViewById(R.id.tvplotincome);
            potentialScoreTxt = (TextView) itemView.findViewById(R.id.score);
            cropTxt = (TextView) itemView.findViewById(R.id.tvplotcrop);
            latestVisitedDateTxt = (TextView) itemView.findViewById(R.id.date);
            ivb_plot_location = (ImageButton) itemView.findViewById(R.id.ivb_plot_location);


        }
    }
}
