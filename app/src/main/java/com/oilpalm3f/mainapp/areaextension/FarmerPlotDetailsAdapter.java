package com.oilpalm3f.mainapp.areaextension;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.farmersearch.DisplayPlotsFragment;
import com.oilpalm3f.mainapp.dbmodels.PlotDetailsObj;
import com.oilpalm3f.mainapp.uihelper.SelectableAdapter;
import com.oilpalm3f.mainapp.utils.UiUtils;

import java.util.List;

/**
 * Created by skasam on 9/28/2016.
 */

//To Bind the farmer plot details
public class FarmerPlotDetailsAdapter extends SelectableAdapter<FarmerPlotDetailsAdapter.PlotDetailsViewHolder> {


    private Context context;
    private List<PlotDetailsObj> plotlist;
    private PlotDetailsObj plotdetailsObj;
    private ClickListener clickListener;
    private int layoutResourceId;
    private boolean showArrow;
    DataAccessHandler dataAccessHandler;
    private double currentLatitude, currentLongitude;


    public FarmerPlotDetailsAdapter(Context context, List<PlotDetailsObj> plotlist, int layoutResourceId, boolean showArrow) {
        this.context = context;
        this.plotlist = plotlist;
        this.layoutResourceId = layoutResourceId;
        this.showArrow = showArrow;
    }

    @Override
    public PlotDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutResourceId, null);
        PlotDetailsViewHolder myHolder = new PlotDetailsViewHolder(view);
        return myHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final PlotDetailsViewHolder holder, final int position) {

        plotdetailsObj = plotlist.get(position);

        holder.tvplotId.setText("Plot Code : " + plotdetailsObj.getPlotID());

        if (!TextUtils.isEmpty(plotdetailsObj.getPlotLandMark())) {
            holder.tvlandmark.setText("Plot LandMark : " + plotdetailsObj.getPlotLandMark());
            holder.tvlandmark.setVisibility(View.VISIBLE);
        } else {
            holder.tvlandmark.setVisibility(View.GONE);
        }
        if (CommonUtils.isFromCropMaintenance() || CommonUtils.isComplaint() || CommonUtils.isFromHarvesting()) {
            holder.tvPlotDop.setVisibility(View.VISIBLE);
            holder.tvPlotDop.setText("Date Of Planting : " + plotdetailsObj.getDateofPlanting());
        } else {
            holder.tvPlotDop.setVisibility(View.GONE);
        }

        if (CommonUtils.isFromCropMaintenance() || CommonUtils.isComplaint() || CommonUtils.isFromHarvesting()) {

            holder.tvplotarea.setText("Total Palm Area : " + plotdetailsObj.getTotalPalm() + " Ha");
        } else {
            holder.tvplotarea.setText("Total Plot Area : " + plotdetailsObj.getPlotArea() + " Ha");
        }

        holder.tvplotvillage.setText("Plot Village : " + plotdetailsObj.getVillageName());
        if (!TextUtils.isEmpty(plotdetailsObj.getSurveyNumber()) && !plotdetailsObj.getSurveyNumber().equalsIgnoreCase("null")) {
            holder.tvplotsurveynumber.setText("Plot SurveyNumber : " + plotdetailsObj.getSurveyNumber());
        } else {
            holder.tvplotsurveynumber.setVisibility(View.GONE);

        }

        dataAccessHandler = new DataAccessHandler(context);

        String visitCount = dataAccessHandler.getOnlyTwoValueFromDb(Queries.getInstance().getVisitCount(plotdetailsObj.getPlotID()));

        String count = visitCount.split("@")[0];

        String date = visitCount.split("@")[1];


        if (CommonUtils.isFromCropMaintenance() || CommonUtils.isFromHarvesting()) {

            if (!count.equals("0")) {
                holder.vist_count.setText("Plot Visit Count : " + count);
            } else {
                holder.vist_count.setVisibility(View.GONE);
                // holder.lastest_vistDate.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(date))
                holder.lastest_vistDate.setText("Last Visit Date : " + date.split("T")[0]);

        } else {
            holder.vist_count.setVisibility(View.GONE);
            holder.lastest_vistDate.setVisibility(View.GONE);
        }


        holder.convertView.setOnClickListener(v -> {
            Log.v(FarmerPlotDetailsAdapter.class.getSimpleName(), "#### clicked position " + position);
            clickListener.onItemClicked(position, holder.convertView);
        });

        holder.arrowImage.setVisibility((showArrow) ? View.VISIBLE : View.INVISIBLE);
        holder.ivb_plot_location_cropcollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonConstants.PLOT_CODE = plotdetailsObj.getPlotID();
                final String selectedLatLong = new DataAccessHandler(context).getLatLongs(Queries.getInstance().queryVerifyGeoTag());

                if (!selectedLatLong.isEmpty()) {
                    final String[] yieldDataArr = selectedLatLong.split("-");
                    final String latlong[] = new DisplayPlotsFragment().getLatLong(context, false).split("@");
                    String land_lattitude = yieldDataArr[0].replaceAll("[\\s\\-()]", "");
                    String land_longitude = yieldDataArr[1].replaceAll("[\\s\\-()]", "");
                    if (land_longitude.isEmpty()) {
                        try {
                            land_longitude = yieldDataArr[2].replaceAll("[\\s\\-()]", "");
                        } catch (Exception e) {
                            land_longitude = "00.000";
                        }
                    }

                    currentLatitude = Double.parseDouble(latlong[0]);
                    currentLongitude = Double.parseDouble(latlong[1]);
                    String uri = "http://maps.google.com/maps?saddr=" + currentLatitude + "," + currentLongitude + "(" + "Village Name = " + plotdetailsObj.getVillageName() + "/" + "LandMark = " + plotdetailsObj.getPlotLandMark() + ")&daddr=" + land_lattitude + "," + land_longitude;
//                    String uri = String.format(Locale.ROOT, "geo:%f,%f?z=%d&q=%f,%f (%s)",
//                            ""+latlong[0], ""+latlong[1], 100, ""+yieldDataArr[0], ""+yieldDataArr[1], "Village Name = " + prospectivePlotsModels.get(position).getPlotVillageName() +  "MandalName = " + prospectivePlotsModels.get(position).getMandalName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    context.startActivity(intent);

                } else {

                    UiUtils.showCustomToastMessage("This Plot has not Lat_Long to show plot on Google Map", context, 1);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return plotlist.size();
    }

    public static class PlotDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvplotId;
        private TextView tvlandmark;
        private TextView tvplotarea;
        private TextView tvplotvillage;
        private TextView tvplotsurveynumber, tvPlotDop;
        private TextView vist_count, lastest_vistDate;
        private ImageView arrowImage;
        private View convertView;
        private ImageButton ivb_plot_location_cropcollection;

        public PlotDetailsViewHolder(View view) {
            super(view);
            this.convertView = view;
            tvplotId = view.findViewById(R.id.tvplotidvalue);
            tvlandmark = view.findViewById(R.id.tvplotlandmarkvalue);
            tvplotarea = view.findViewById(R.id.tvplotareavalue);
            tvplotvillage = view.findViewById(R.id.tvplotvillagevalue);
            tvPlotDop = view.findViewById(R.id.tvplotDOP);
            tvplotsurveynumber = view.findViewById(R.id.tvplotsurveyvalue);
            arrowImage = view.findViewById(R.id.arrow_right);
            vist_count = view.findViewById(R.id.vistCount);
            lastest_vistDate = view.findViewById(R.id.lastest_vistDate);
            ivb_plot_location_cropcollection = view.findViewById(R.id.ivb_plot_location_cropcollection);
        }
    }

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClicked(int position, View view);
    }
}
