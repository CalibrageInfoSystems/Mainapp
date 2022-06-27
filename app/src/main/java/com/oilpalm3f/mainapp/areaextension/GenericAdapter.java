package com.oilpalm3f.mainapp.areaextension;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;

import java.util.List;

/**
 * Created by siva on 05/05/17.
 */

//Common Adapter used to delete the Id Proofs Data
public class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.MyHolder> {
    private static final int FROM_CURRENT_CROP = 1;
    private Context mContext;
    private List<Pair<String, String>> dataPair;
    private GenericListItemClickListener genericListItemClickListener;

    public GenericAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void updateAdapter(List<Pair<String, String>> dataPair) {
        this.dataPair = dataPair;
        notifyDataSetChanged();
    }

    @Override
    public GenericAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bookingView = inflater.inflate(R.layout.adapter_idproof, null);
        MyHolder myHolder = new MyHolder(bookingView);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(GenericAdapter.MyHolder holder, final int position) {
        holder.itemName.setText(dataPair.get(position).first);
        holder.itemValue.setText(dataPair.get(position).second);

        holder.editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genericListItemClickListener.onEditClicked(position, FROM_CURRENT_CROP);
            }
        });

        holder.deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genericListItemClickListener.onDeleteClicked(position, FROM_CURRENT_CROP);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataPair.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemValue;
        ImageView editView, deleteView;

        public MyHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.idproofName);
            itemValue = (TextView) itemView.findViewById(R.id.idproofNo);
            editView = (ImageView) itemView.findViewById(R.id.editIcon);
            deleteView = (ImageView) itemView.findViewById(R.id.trashIcon);
        }
    }

    public void setGenericListItemClickListener(GenericListItemClickListener genericListItemClickListener) {
        this.genericListItemClickListener = genericListItemClickListener;
    }
}
