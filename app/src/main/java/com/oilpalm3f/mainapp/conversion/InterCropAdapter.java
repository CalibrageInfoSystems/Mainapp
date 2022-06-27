package com.oilpalm3f.mainapp.conversion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.oilpalm3f.mainapp.R;

import java.util.ArrayList;

/**
 * Created by pc on 29-09-2016.
 */

//To display the intercrops entered
public class InterCropAdapter extends RecyclerView.Adapter<InterCropAdapter.SingleItemRowHolder> {

    ArrayList<InterCropModel> itemsList ;
    private PalmDetailsEditListener palmDetailsEditListener;

    public InterCropAdapter(ArrayList<InterCropModel> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singleitem_layout, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, final int i) {

        holder.cropCount.setText(itemsList.get(i).getInterCropCount());
        holder.cropYearTxt.setText(itemsList.get(i).getInterCropInYear());

        holder.editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                palmDetailsEditListener.onEditClicked(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView cropCount,cropYearTxt;
        ImageView editView;

        public SingleItemRowHolder(View view) {
            super(view);

            this.cropCount = (TextView) view.findViewById(R.id.itemNameTxt);
            this.cropYearTxt = (TextView) view.findViewById(R.id.itemVale);
            editView = (ImageView) itemView.findViewById(R.id.editIcon);

        }

    }

    public void setEditClickListener(PalmDetailsEditListener palmDetailsEditListener) {
        this.palmDetailsEditListener = palmDetailsEditListener;
    }

}
