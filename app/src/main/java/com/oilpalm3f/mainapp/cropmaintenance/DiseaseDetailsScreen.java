package com.oilpalm3f.mainapp.cropmaintenance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.ui.BaseFragment;

/**
 * Created by Siva on 21/01/17.
 */

public class DiseaseDetailsScreen extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private View rootView;

    @Override
    public void onClick(View v) {

    }

    @Override
    public void Initialize() {
        mContext = getActivity();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        rootView = inflater.inflate(R.layout.diseasedetails_screnn, null);

        baseLayout.addView(rootView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setTile(getString(R.string.disease_details_screen));
    }
}
