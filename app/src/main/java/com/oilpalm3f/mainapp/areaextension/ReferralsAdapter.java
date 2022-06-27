package com.oilpalm3f.mainapp.areaextension;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.dbmodels.Referrals;

import java.util.List;

//to display the Referral person details
public class ReferralsAdapter extends RecyclerView.Adapter<ReferralsAdapter.MyHolder> {
    private Context mContext;
    private List<Referrals> referralDataModelArrayList;
    private referralsEditClickListener referralsEditClickListener;
    private OnCartChangedListener onCartChangedListener;
    public static final String SELECTED_TYPE_DELETE = "selected_type_delete";
    public static final String SELECTED_TYPE_EDIT = "selected_type_edit";

    public ReferralsAdapter(Context mContext, List<Referrals> referralDataModelArrayList) {
        this.mContext = mContext;
        this.referralDataModelArrayList = referralDataModelArrayList;
    }

    public void updateData(List<Referrals> referralDataModelArrayList) {
        this.referralDataModelArrayList = referralDataModelArrayList;
    }
    @Override
    public ReferralsAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adapter_referral, null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(ReferralsAdapter.MyHolder holder, final int position) {
        holder.villageCodeText.setText(referralDataModelArrayList.get(position).getVillageName());
        holder.referralNameText.setText(referralDataModelArrayList.get(position).getFarmername());
        holder.mobileNoText.setText(referralDataModelArrayList.get(position).getContactnumber());

        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    if (referralsEditClickListener != null) {
//                        referralsEditClickListener.onEditClicked(referralDataModelArrayList.get(getAdapterPosition()));
//                    }
                onCartChangedListener.setCartClickListener("edit",position);
            }
        });

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    if (referralsEditClickListener != null) {
//                        referralsEditClickListener.onDeleteClicked(getAdapterPosition());
//                    }
                onCartChangedListener.setCartClickListener("delete",position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return referralDataModelArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView villageCodeText, referralNameText, mobileNoText;
        ImageView editIcon, deleteIcon;

        public MyHolder(View itemView) {
            super(itemView);

            villageCodeText = (TextView) itemView.findViewById(R.id.villageCodeText);
            referralNameText = (TextView) itemView.findViewById(R.id.referralNameText);
            mobileNoText = (TextView) itemView.findViewById(R.id.mobileNoText);

            editIcon = (ImageView) itemView.findViewById(R.id.editIcon);
            deleteIcon = (ImageView) itemView.findViewById(R.id.trashIcon);

        }
    }

    public void setReferralsEditClickListener(referralsEditClickListener referralsEditClickListener) {
        this.referralsEditClickListener = referralsEditClickListener;
    }
    public interface referralsEditClickListener {
        void onEditClicked(Referrals dataModel);
        void onDeleteClicked(int position);
    }

    public void setOnCartChangedListener(OnCartChangedListener onCartChangedListener) {
        this.onCartChangedListener = onCartChangedListener;
    }

    public interface OnCartChangedListener {
        void setCartClickListener(String status,int position);
    }
}
