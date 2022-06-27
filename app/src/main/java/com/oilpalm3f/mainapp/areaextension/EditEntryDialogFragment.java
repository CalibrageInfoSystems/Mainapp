package com.oilpalm3f.mainapp.areaextension;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.utils.UiUtils;

import java.util.LinkedHashMap;

/**
 * Created by siva on 06/05/17.
 */


//Edit Dialog
public class EditEntryDialogFragment extends DialogFragment {

    public static final String LOG_TAG = EditEntryDialogFragment.class.getName();

    private View rootView;
    public TextView itemDisplayNameTxt, title2Txt, title1Txt;
    public EditText inputEditBox, inputBox2;
    public Spinner inputSpinner,recommendationCropSpn;
    private int typeDialog;

    public static final int TYPE_EDIT_BOX = 0;
    public static final int TYPE_SPINNER = 1;
    public static final int EDIT_INTER_CROP = 4;
    public static final int TYPE_MULTI_EDIT_BOX = 2;
    public static final int TYPE_SPINNER_IRIGATION_TYPE = 3;
    private String prevData;
    private android.widget.Button saveBtn;
    private android.widget.Button cancelBtn;
    private DataAccessHandler dataAccessHandler;
    private LinkedHashMap dataMap;
    private LinearLayout inputTypeLayout1, inputTypeLayout2;


    public void setOnDataEditChangeListener(OnDataEditChangeListener onDataEditChangeListener) {
        this.onDataEditChangeListener = onDataEditChangeListener;
    }

    private OnDataEditChangeListener onDataEditChangeListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle inputBundle = getArguments();
        typeDialog = inputBundle.getInt("typeDialog");

        if(typeDialog == EDIT_INTER_CROP)
        {
            rootView = inflater.inflate(R.layout.edit_inter_crop, null);
        }
        else {
            rootView = inflater.inflate(R.layout.edit_entry_dialog, null);
        }

        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        rootView.setMinimumWidth((int) (displayRectangle.width() * 0.7f));

        dataAccessHandler = new DataAccessHandler(getActivity());

        itemDisplayNameTxt = (TextView) rootView.findViewById(R.id.selectedItemTxt);
        inputEditBox = (EditText) rootView.findViewById(R.id.inputBox);
        inputSpinner = (Spinner) rootView.findViewById(R.id.inputSpin);
        saveBtn = (Button) rootView.findViewById(R.id.saveBtn);
        cancelBtn = (Button) rootView.findViewById(R.id.cancelBtn);
        recommendationCropSpn = rootView.findViewById(R.id.recommendationCropSpn);

        inputTypeLayout1 = (LinearLayout) rootView.findViewById(R.id.inputTypeLayout1);
        inputTypeLayout2 = (LinearLayout) rootView.findViewById(R.id.inputTypeLayout2);
        title1Txt = (TextView) rootView.findViewById(R.id.title1);
        title2Txt = (TextView) rootView.findViewById(R.id.title2);
        inputBox2 = (EditText) rootView.findViewById(R.id.inputBox2);



        prevData = inputBundle.getString("prevData");

        String[] dataArr = prevData.split("-");
        itemDisplayNameTxt.setText(""+inputBundle.getString("title"));
        if (typeDialog == TYPE_EDIT_BOX) {
            inputEditBox.setVisibility(View.VISIBLE);
            inputSpinner.setVisibility(View.GONE);
            inputEditBox.setText(""+dataArr[0]);
            inputTypeLayout1.setVisibility(View.VISIBLE);
            title1Txt.setText(dataArr[1]);
        } else if (typeDialog == EDIT_INTER_CROP) {
            itemDisplayNameTxt.setText("Inter Crop");
            dataMap = dataAccessHandler.getGenericData(Queries.getInstance().getCropsMasterInfo());
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                    CommonUtils.fromMap(dataMap, "Crop"));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            inputEditBox.setVisibility(View.GONE);
            inputSpinner.setVisibility(View.VISIBLE);
            inputSpinner.setAdapter(spinnerArrayAdapter);
            recommendationCropSpn.setAdapter(spinnerArrayAdapter);
            int cropPos = CommonUtils.getIndex(dataMap.keySet(), CommonUtils.getKeyFromValue(dataMap, dataArr[0]));
            inputSpinner.setSelection((cropPos > -1) ? cropPos + 1 : 0);
        }else if (typeDialog == TYPE_SPINNER_IRIGATION_TYPE) {
            title1Txt.setText(dataArr[1]);
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line,
                    getResources().getStringArray(R.array.irigationType_values));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            inputEditBox.setVisibility(View.GONE);
            inputSpinner.setVisibility(View.VISIBLE);
            inputSpinner.setAdapter(spinnerArrayAdapter);
        } else if (typeDialog == TYPE_MULTI_EDIT_BOX) {
            String prevData2 = inputBundle.getString("prevData2");
            String[] dataArr2 = prevData2.split("-");
            title1Txt.setText(""+dataArr[0]);
            title2Txt.setText(""+dataArr2[0]);
            inputEditBox.setText(""+dataArr[1]);
            inputBox2.setText(""+dataArr2[1]);
            inputTypeLayout1.setVisibility(View.VISIBLE);
            inputTypeLayout2.setVisibility(View.VISIBLE);
            inputSpinner.setVisibility(View.GONE);
            inputEditBox.setVisibility(View.VISIBLE);
            inputBox2.setVisibility(View.VISIBLE);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();
                if (typeDialog == TYPE_EDIT_BOX) {
                    if (!TextUtils.isEmpty(inputEditBox.getText().toString())) {
                        dataBundle.putString("inputValue", inputEditBox.getText().toString());
//                        int cropPos = CommonUtils.getIndex(dataMap.keySet(), CommonUtils.getKeyFromValue(dataMap, dataArr[0]));
                        onDataEditChangeListener.onDataEdited(dataBundle);
                        dismiss();
                    } else {
                        UiUtils.showCustomToastMessage("Please enter proper data", getActivity(), 1);
                    }
                } else if (typeDialog == EDIT_INTER_CROP) {
                    if (!CommonUtils.isEmptySpinner(inputSpinner) && !CommonUtils.isEmptySpinner(recommendationCropSpn)) {
                        dataBundle.putString("inputValue", inputSpinner.getSelectedItem().toString());
                        dataBundle.putString("recValue", recommendationCropSpn.getSelectedItem().toString());
                        dataBundle.putInt("cropId",inputSpinner.getSelectedItemPosition());
                        dataBundle.putInt("recId",recommendationCropSpn.getSelectedItemPosition());
                        onDataEditChangeListener.onDataEdited(dataBundle);
                        dismiss();
                    } else {
                        UiUtils.showCustomToastMessage("Please select proper data", getActivity(), 1);
                    }
                } else if (typeDialog == TYPE_SPINNER_IRIGATION_TYPE) {
                    if (!CommonUtils.isEmptySpinner(inputSpinner)) {
                        dataBundle.putString("inputValue", inputSpinner.getSelectedItem().toString());
                        onDataEditChangeListener.onDataEdited(dataBundle);
                        dismiss();
                    } else {
                        UiUtils.showCustomToastMessage("Please select proper data", getActivity(), 1);
                    }
                }if (typeDialog == TYPE_MULTI_EDIT_BOX) {
                    if (!TextUtils.isEmpty(inputEditBox.getText().toString()) && !TextUtils.isEmpty(inputBox2.getText().toString())) {
                        dataBundle.putString("inputValue", inputEditBox.getText().toString());
                        dataBundle.putString("inputValue2", inputBox2.getText().toString());
                        onDataEditChangeListener.onDataEdited(dataBundle);
                        dismiss();
                    } else {
                        UiUtils.showCustomToastMessage("Please enter proper data", getActivity(), 1);
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    public interface OnDataEditChangeListener {
        void onDataEdited(final Bundle dataBundle);
    }
}
