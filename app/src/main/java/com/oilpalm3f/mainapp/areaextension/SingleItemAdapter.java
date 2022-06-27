package com.oilpalm3f.mainapp.areaextension;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;

import java.util.List;


//Single Item entry Adapter
public class SingleItemAdapter extends RecyclerView.Adapter<SingleItemAdapter.SingleItemRowHolder> {

    private static final int FROM_SINGLE_ITEM = 0;
    public List<Pair> itemsList;
    private GenericListItemClickListener genericListItemClickListener;
    private boolean fromHistory;
    

    public SingleItemAdapter() {

    }

    public SingleItemAdapter(boolean fromHistory ) {
        this.fromHistory = fromHistory;

    }

    public void updateAdapter(List<Pair> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singleitem_layout, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, final int i) {
        Pair selectedItemPair = itemsList.get(i);
        holder.cropCount.setText(selectedItemPair.first.toString());
        holder.cropYearTxt.setText(""+selectedItemPair.second.toString());
        holder.deleteView.setVisibility((fromHistory) ? View.GONE : View.VISIBLE);
      //  holder.editView.setVisibility((fromHistory) ? View.GONE : View.VISIBLE);
//        holder.editView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (null != genericListItemClickListener) {
//                    genericListItemClickListener.onEditClicked(i, FROM_SINGLE_ITEM);
//                }
//            }
//        });

        holder.deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != genericListItemClickListener) {
                    genericListItemClickListener.onDeleteClicked(i, FROM_SINGLE_ITEM);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView cropCount, cropYearTxt;
        ImageView editView, deleteView;

        public SingleItemRowHolder(View view) {
            super(view);

            this.cropCount = (TextView) view.findViewById(R.id.itemNameTxt);
            this.cropYearTxt = (TextView) view.findViewById(R.id.itemVale);
            editView = (ImageView) itemView.findViewById(R.id.editIcon);
            deleteView = (ImageView) itemView.findViewById(R.id.trashIcon);

        }

    }

    public void setEditClickListener(GenericListItemClickListener palmDetailsEditListener) {
        this.genericListItemClickListener = palmDetailsEditListener;
    }

}

