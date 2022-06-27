package com.oilpalm3f.mainapp.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.dbmodels.HarvestorDataModel;

import java.util.ArrayList;
import java.util.List;

//Custom Auto Search Adapter
public class CustomAutoAdapter extends ArrayAdapter {

    private List<HarvestorDataModel> dataList;
    private Context mContext;
    private int itemLayout;

    private ListFilter listFilter = new ListFilter();
    private List<HarvestorDataModel> dataListAllItems;



    public CustomAutoAdapter(Context context, int resource, List<HarvestorDataModel> storeDataLst) {
        super(context, resource, storeDataLst);
        dataList = storeDataLst;
        mContext = context;
        itemLayout = resource;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public HarvestorDataModel getItem(int position) {
        Log.d("CustomListAdapter",
                dataList.get(position).getCode());
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }
        TextView strCode = (TextView) view.findViewById(R.id.Code);
        TextView strName = (TextView) view.findViewById(R.id.name);
        TextView strMobile = (TextView) view.findViewById(R.id.mobile);

        strCode.setText(getItem(position).getCode());
        strName.setText(getItem(position).getName());
        strMobile.setText(getItem(position).getMobileNumber());
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<HarvestorDataModel>(dataList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<HarvestorDataModel> matchValues = new ArrayList<HarvestorDataModel>();

                for (HarvestorDataModel dataItem : dataListAllItems) {
                    if (dataItem.getCode().toLowerCase().contains(searchStrLowerCase)   ) {
                        matchValues.add(dataItem);
                    }else if(dataItem.getMobileNumber().toLowerCase().contains(searchStrLowerCase)){
                        matchValues.add(dataItem);
                    }
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                dataList = (ArrayList<HarvestorDataModel>)results.values;
            } else {
                dataList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}