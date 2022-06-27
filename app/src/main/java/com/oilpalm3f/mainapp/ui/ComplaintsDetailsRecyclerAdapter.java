package com.oilpalm3f.mainapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.dbmodels.ComplaintsDetails;
import com.oilpalm3f.mainapp.uihelper.CircleImageView;
import com.oilpalm3f.mainapp.farmersearch.FarmerDetailsRecyclerAdapter;

import java.util.List;

/**
 * Created by RAMESH BABU on 02-07-2017.
 */

//Binding complaints data & displaying them
public class ComplaintsDetailsRecyclerAdapter extends RecyclerView.Adapter<ComplaintsDetailsRecyclerAdapter.ComplaintDetailsViewHolder> {

    private static final String LOG_TAG = FarmerDetailsRecyclerAdapter.class.getName();
    private List<ComplaintsDetails> mList;
    private Context context;
    private ComplaintsDetails item;
    private RecyclerItemClickListener recyclerItemClickListener;
    private DataAccessHandler dataAccessHandler;
    private ClickListener clickListener;

    public ComplaintsDetailsRecyclerAdapter(Context context, List<ComplaintsDetails> mList, DataAccessHandler dataAccessHandler) {
        this.context = context;
        this.mList = mList;
        this.dataAccessHandler = dataAccessHandler;
    }

    @Override
    public ComplaintsDetailsRecyclerAdapter.ComplaintDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.complaint_item, null);
        ComplaintsDetailsRecyclerAdapter.ComplaintDetailsViewHolder myHolder = new ComplaintsDetailsRecyclerAdapter.ComplaintDetailsViewHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final ComplaintDetailsViewHolder holder, final int position) {
        item = mList.get(position);
        holder.complaint_Id.setText(item.getComplaintId());
        holder.complaint_name.setText(item.getComplaintTypeName());
        String date = item.getComplaintRaisedon();
        if (date.contains("T") && date.contains(".")) {
            holder.complaint_raised_on.setText(CommonUtils.getProperDate(item.getComplaintRaisedon()));
        } else {
            if (date.contains("T")) {
                date = date.replace("T", " ");
            }
            holder.complaint_raised_on.setText(CommonUtils.getProperComplaintsDate(date));
        }
        holder.farmer_Name.setText(item.getfarmerFirstName() + " " + item.getFarmerLastName());
        holder.status.setText(item.getComplaintStatusTypeName());
        holder.plot_Id.setText(item.getPlotId());
        holder.village.setText(item.getVillage());
        holder.createdBy.setText(item.getComplaintCreatedBy());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClicked(mList.get(position).getComplaintId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateAdapter(List<ComplaintsDetails> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    private void loadImageFromStorage(String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClicked(String complaintCode);
    }

    public class ComplaintDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView complaint_Id;
        private TextView farmer_Name;
        private TextView village;
        private TextView plot_Id;
        private TextView complaint_name;
        private TextView complaint_raised_on;
        private TextView status;
        private TextView createdBy;
        private CircleImageView circularImageView;
        private View convertView;

        public ComplaintDetailsViewHolder(View view) {
            super(view);
            convertView = view;
            complaint_Id = (TextView) convertView.findViewById(R.id.complaint_Id);
            circularImageView = (CircleImageView) convertView.findViewById(R.id.circularImageView);
            farmer_Name = (TextView) convertView.findViewById(R.id.farmer_Name);
            village = (TextView) convertView.findViewById(R.id.village);
            plot_Id = (TextView) convertView.findViewById(R.id.plot_Id);
            complaint_name = (TextView) convertView.findViewById(R.id.complaint_name);
            complaint_raised_on = (TextView) convertView.findViewById(R.id.complaint_raised_on);
            status = (TextView) convertView.findViewById(R.id.status);
            createdBy = (TextView) convertView.findViewById(R.id.createdBy);
        }
    }
}
