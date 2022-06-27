package com.oilpalm3f.mainapp.conversion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.ui.BaseFragment;

/**
 * Created by skasam on 1/21/2017.
 */
public class ConversionFertilizerDetailsFragment extends BaseFragment {

    public ConversionFertilizerDetailsFragment()
    {

    }

    @Override
    public void Initialize() {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View parentView = inflater.inflate(R.layout.frag_conversion_fertilizerdetails, null);
        baseLayout.addView(parentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setTile(getActivity().getResources().getString(R.string.fertilizer_details));
    }
}
