package com.oilpalm3f.mainapp.areaextension;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.oilpalm3f.mainapp.R;

/**
 * Created by latitude on 26-04-2017.
 */

//Water/Soil/Power Selection Dialog
public class WaterSoilTypeDialogFragment extends DialogFragment {
    public WaterSoilTypeDialogFragment.onTypeSelected onTypeSelected;
    public interface onTypeSelected {
        void onTypeSelected(int type);
    }

    public void setOnTypeSelected(WaterSoilTypeDialogFragment.onTypeSelected onTypeSelected) {
        this.onTypeSelected = onTypeSelected;
    }
    public WaterSoilTypeDialogFragment() {
        // Required empty public constructor
    }

    public static WaterSoilTypeDialogFragment newInstance(String type) {
        WaterSoilTypeDialogFragment fragment = new WaterSoilTypeDialogFragment();
        Bundle args = new Bundle();
        args.putString("type", "" + type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_water_soil, container);
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        view.setMinimumWidth((int)(displayRectangle.width() * 0.7f));

        RelativeLayout waterDetailsRel = view.findViewById(R.id.waterDetailsRel);
        waterDetailsRel.setOnClickListener(view12 -> {
//                    replaceFragment(new AreaWaterTypeFragment());
            onTypeSelected.onTypeSelected(1);
            getDialog().dismiss();
        });

        RelativeLayout soildetailsRel = view.findViewById(R.id.soildetailsRel);
        soildetailsRel.setOnClickListener(view1 -> {
            onTypeSelected.onTypeSelected(2);
            getDialog().dismiss();
        });

        return view;
    }

}
