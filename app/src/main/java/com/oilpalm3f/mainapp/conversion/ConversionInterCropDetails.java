package com.oilpalm3f.mainapp.conversion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.ui.BaseFragment;

/**
 * Created by skasam on 1/21/2017.
 */
public class ConversionInterCropDetails extends BaseFragment {

    private DataAccessHandler dataAccessHandler;
    private Context mContext;
    private Spinner intercropnameSpin,reasonformissingtreesSpin;
    private TextView noofseedlingsplantedTV,noofmissingtreesTV;
    private EditText countofpalmscurrentlypresentEdt,commentsEdit;
    private RadioButton missingtreesRadioBtnYesland,missingtreesRadioBtnNoland;
    private RadioGroup careTakerRadioGroupland;
    private LinearLayout parentlayout;
    private Button IntercropSaveBtn;

    public ConversionInterCropDetails()
    {

    }

    @Override
    public void Initialize() {


        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View parentView = inflater.inflate(R.layout.frag_intercrop_details, null);
        baseLayout.addView(parentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setTile(getActivity().getResources().getString(R.string.interCropDetails));

        parentlayout = (LinearLayout) parentView.findViewById(R.id.parent_layout);
        commentsEdit = (EditText) parentView.findViewById(R.id.commentsEdit);
        reasonformissingtreesSpin = (Spinner) parentView.findViewById(R.id.reason_for_missing_treesSpin);
        noofmissingtreesTV = (TextView) parentView.findViewById(R.id.no_of_missing_treesTV);
        careTakerRadioGroupland = (RadioGroup) parentView.findViewById(R.id.careTakerRadioGroup_land);
        missingtreesRadioBtnNoland = (RadioButton) parentView.findViewById(R.id.missing_treesRadioBtnNo_land);
        missingtreesRadioBtnYesland = (RadioButton) parentView.findViewById(R.id.missing_treesRadioBtnYes_land);
        countofpalmscurrentlypresentEdt = (EditText) parentView.findViewById(R.id.count_of_palms_currently_presentEdt);
        noofseedlingsplantedTV = (TextView) parentView.findViewById(R.id.no_of_seedlings_plantedTV);
        intercropnameSpin = (Spinner) parentView.findViewById(R.id.intercrop_nameSpin);
        IntercropSaveBtn = (Button)parentView.findViewById(R.id.IntercropSaveBtn);


        mContext = getActivity();
        dataAccessHandler = new DataAccessHandler(getActivity());


    }



}
