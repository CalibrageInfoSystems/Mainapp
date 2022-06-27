package com.oilpalm3f.mainapp.ExpandableListview;

import com.oilpalm3f.mainapp.dbmodels.KrasDataToDisplay;
import com.oilpalm3f.mainapp.ui.KrasDisplayScreen;

import java.util.List;

/**
 * Created by Lenovo on 11/16/2017.
 */

public class HeaderCategory implements ParentListItem {
    private String mName;
    private List<KrasDataToDisplay> mKras;

    public HeaderCategory(String name, List<KrasDataToDisplay> kraList) {
        mName = name;
        mKras = kraList;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<?> getChildItemList() {
        return mKras;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
