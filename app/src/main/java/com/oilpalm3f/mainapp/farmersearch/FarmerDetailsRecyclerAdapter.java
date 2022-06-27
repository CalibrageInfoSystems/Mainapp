package com.oilpalm3f.mainapp.farmersearch;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.dbmodels.BasicFarmerDetails;
import com.oilpalm3f.mainapp.ui.RecyclerItemClickListener;
import com.oilpalm3f.mainapp.uihelper.CircleImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


//Used to Bind the farmer details
public class FarmerDetailsRecyclerAdapter extends RecyclerView.Adapter<FarmerDetailsRecyclerAdapter.FarmerDetailsViewHolder> {

    private static final String LOG_TAG = FarmerDetailsRecyclerAdapter.class.getName();
    private List<BasicFarmerDetails> mList;
    private Context context;
    private BasicFarmerDetails item;
    private RecyclerItemClickListener recyclerItemClickListener;


    public FarmerDetailsRecyclerAdapter(Context context, List<BasicFarmerDetails> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void addItems(List<BasicFarmerDetails> list) {
        this.mList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @Override
    public FarmerDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.member_item, null);FarmerDetailsViewHolder myHolder = new FarmerDetailsViewHolder(view);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(final FarmerDetailsViewHolder holder, final int position) {
        item = mList.get(position);
        String lastName = "", middleName = "";
        if (!TextUtils.isEmpty(item.getFarmerLastName())) {
            lastName = item.getFarmerLastName();
        }

        if (!TextUtils.isEmpty(item.getFarmerMiddleName()) && !
                item.getFarmerMiddleName().equalsIgnoreCase("null")) {
            middleName = item.getFarmerMiddleName();
        }

        holder.tvuserName.setText(item.getFarmerFirstName().trim() + " " + middleName + " " + lastName.trim() + " " + "(" + item.getFarmerCode().trim() + ")");

        if (!TextUtils.isEmpty(item.getFarmerFatherName()) && !
                item.getFarmerFatherName().equalsIgnoreCase("null")) {
            holder.fatherName.setText(item.getFarmerFatherName().trim());
        } else {
            holder.fatherName.setText("");
        }

        if (!TextUtils.isEmpty(item.getPrimaryContactNum()) && !
                item.getPrimaryContactNum().equalsIgnoreCase("null")) {
            holder.mobileNumber.setVisibility(View.VISIBLE);
            holder.mobileNumber.setText(item.getPrimaryContactNum());
        } else {
            holder.mobileNumber.setText("");
            holder.mobileNumber.setVisibility(View.GONE);
        }

        holder.villageName.setText(item.getFarmerVillageName() != null ? item.getFarmerVillageName().trim() : "");
        holder.tvuserStateName.setText(item.getFarmerStateName() != null ? item.getFarmerStateName().trim() : "");

        holder.userImage.setBorderColor(context.getResources().getColor(R.color.colorPrimary));

        final String imageUrl = CommonUtils.getImageUrl(item);
        Uri uri = null;
        if (null != item.getPhotoLocation()) {
            File photoFiles = new File(item.getPhotoLocation());
            if (photoFiles != null) {
                uri = Uri.fromFile(new File(item.getPhotoLocation()));
                if (uri != null) {
                    Picasso.with(context).load(uri).into(holder.userImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
//                            holder.userImage.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));

                            renderImage(imageUrl, holder.userImage);
                        }
                    });
                } else {
                    renderImage(imageUrl, holder.userImage);
                }
            } else {
                renderImage(imageUrl, holder.userImage);
            }
        } else {
            renderImage(imageUrl, holder.userImage);
        }
//        Picasso.with(context)
//                .load(imageUrl)
//                .into(holder.userImage, new Callback() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onError() {
//                        if (!TextUtils.isEmpty(mList.get(position).getPhotoLocation())) {
//                            loadImageFromStorage(mList.get(position).getPhotoLocation(), holder.userImage);
//                        }
//                    }
//                });

        holder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "@@@ on item clicked");
                recyclerItemClickListener.onItemSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateAdapter(List<BasicFarmerDetails> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public void renderImage(String imageUrl, ImageView imageView) {
        if (imageUrl != null) {
            Picasso.with(context)
                    .load(imageUrl)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {


                        }

                        @Override
                        public void onError() {
                            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));
                        }
                    });

        }
        else {
            Picasso.with(context)
                    .load(R.mipmap.ic_launcher)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));
                        }
                    });

        }
    }
    private void loadImageFromStorage(String path, ImageView imageView) {
//        Glide.with(context)
//                .load(path)
//                .placeholder(R.mipmap.ic_launcher)
//                .into(imageView);
    }

    public class FarmerDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvuserName;
        private TextView mobileNumber;
        private TextView fatherName;
        private TextView villageName;
        private CircleImageView userImage;
        private TextView tvuserStateName;
        private View convertView;

        public FarmerDetailsViewHolder(View view) {
            super(view);
            convertView = view;
            tvuserName = (TextView) view.findViewById(R.id.notif_title);
            userImage = (CircleImageView) view.findViewById(R.id.circularImageView);
            mobileNumber = (TextView) view.findViewById(R.id.mobile_number);
            fatherName = (TextView) view.findViewById(R.id.fatherName);
            villageName = (TextView) view.findViewById(R.id.villageName);
            tvuserStateName = (TextView) view.findViewById(R.id.stateName);
        }
    }
}
