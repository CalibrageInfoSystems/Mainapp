package com.oilpalm3f.mainapp.areaextension;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.conversion.IdProofsListAdapter;
import com.oilpalm3f.mainapp.dbmodels.IdentityProof;
import com.oilpalm3f.mainapp.dbmodels.LandlordIdProof;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Stampit-PC1 on 6/5/2017.
 */

//To enter the Lordlord Id Proofs
public class LandLordIdProofListAdapter  extends RecyclerView.Adapter<LandLordIdProofListAdapter.MyHolder> {
    private Context mContext;
    private List<LandlordIdProof> landlordIdProofList;
    private idProofsClickListener idProofsClickListener;
    private LinkedHashMap<String, String> idProofsData;


    public LandLordIdProofListAdapter(Context mContext, List<LandlordIdProof> idProofsPair, LinkedHashMap<String, String> idProofsData) {
        this.mContext = mContext;
        this.landlordIdProofList = idProofsPair;
        this.idProofsData = idProofsData;
    }
    @Override
    public LandLordIdProofListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bookingView = inflater.inflate(R.layout.adapter_idproof, null);
        MyHolder myHolder = new MyHolder(bookingView);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(LandLordIdProofListAdapter.MyHolder holder, final int position) {
        holder.idProofName.setText(idProofsData.get(String.valueOf(landlordIdProofList.get(position).getIDProofTypeId())));
        holder.idProofNo.setText(landlordIdProofList.get(position).getIdProofNumber());

        holder.editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idProofsClickListener.onEditClicked(position);
            }
        });

        holder.deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idProofsClickListener.onDeleteClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return landlordIdProofList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView idProofName, idProofNo;
        ImageView editView, deleteView;

        public MyHolder(View itemView) {
            super(itemView);
            idProofName = (TextView) itemView.findViewById(R.id.idproofName);
            idProofNo = (TextView) itemView.findViewById(R.id.idproofNo);
            editView = (ImageView) itemView.findViewById(R.id.editIcon);
            deleteView = (ImageView) itemView.findViewById(R.id.trashIcon);
        }
    }

    public void setIdProofsClickListener(idProofsClickListener idProofsClickListener) {
        this.idProofsClickListener = idProofsClickListener;
    }
    public interface idProofsClickListener {
        void onEditClicked(int position);
        void onDeleteClicked(int position);
    }

    public void updateData(List<LandlordIdProof> idProofsPair, LinkedHashMap<String, String> idProofsData) {
        this.landlordIdProofList = idProofsPair;
        this.idProofsData = idProofsData;
        notifyDataSetChanged();
    }
}
