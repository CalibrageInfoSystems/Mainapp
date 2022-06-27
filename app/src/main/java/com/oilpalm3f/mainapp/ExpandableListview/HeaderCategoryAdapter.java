package com.oilpalm3f.mainapp.ExpandableListview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.dbmodels.KrasDataToDisplay;

import java.util.List;

/**
 * Created by Lenovo on 11/16/2017.
 */

public class HeaderCategoryAdapter extends ExpandableRecyclerAdapter<HeaderViewHolder, SubViewHolder> {

    private LayoutInflater mInflator;

    public HeaderCategoryAdapter(Context context, List<? extends ParentListItem> displayList) {
        super(displayList);
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public HeaderViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View kraAnnualCategoryView = mInflator.inflate(R.layout.kra_header, parentViewGroup, false);
        return new HeaderViewHolder(kraAnnualCategoryView);
    }

    @Override
    public SubViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View kraMonthlyCategoryView = mInflator.inflate(R.layout.single_item_view, childViewGroup, false);
        return new SubViewHolder(kraMonthlyCategoryView);
    }

    @Override
    public void onBindParentViewHolder(HeaderViewHolder kraAnnualCategoryViewHolder, int position, ParentListItem parentListItem) {
        HeaderCategory kraAnnualCategory = (HeaderCategory) parentListItem;
        kraAnnualCategoryViewHolder.bind(kraAnnualCategory.getName());
    }

    @Override
    public void onBindChildViewHolder(SubViewHolder kraMonthlyViewHolder, int position, Object childListItem) {
        KrasDataToDisplay kraMonthlyView = (KrasDataToDisplay) childListItem;
        kraMonthlyViewHolder.bind(kraMonthlyView);
    }
}

