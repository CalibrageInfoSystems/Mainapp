package com.oilpalm3f.mainapp.cropmaintenance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.common.FiscalDate;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

//Whitefields entry fields and save
public class WhiteFieldFragment extends DialogFragment {

    int oilUsed = 0;
    int sprayingDone = 0;
    int controlDone = 0;
    int boostFertilizer = 0;
    DataAccessHandler dataAccessHandler;

    int count = 0;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    String[] listItems_1;
    boolean[] checkedItems_1;
    ArrayList<Integer> mUserItems_1 = new ArrayList<>();

    View view = null;
    private int year;
    private Bundle dataBundle;
    private ArrayList<String> arrayList_percent,
            arrayList_intensity,
            arrayList_sooty,
            arrayList_infestation, arrayList_oilused, arrayList_sprayingdone, arrayList_controldone, arrayList_fertilizer, arrayList_quantity, arraylist_CM;  // arralist functions
    private ArrayAdapter<String> arrayAdapter_percent,
            arrayAdapter_intensity,
            arrayAdapter_sooty, arrayAdapter_sprayingdone, arrayAdapter_controldone,
            arrayAdapter_infestation, arrayAdapter_oilused, arrayAdapter_fertilizer, arrayAdapter_quantity; // arrayAdapter functions
    private ArrayAdapter<String> Ad_neemOilsuccess, Ad_sprayingsuccess, Ad_controlsuccess;
    private RadioGroup
            mSprayingDoneRg,
            mboostfertiligerRg,
            controlDoneRg;  //radioGroup Functions
    TextView mspinner_selectFertilizer, mspinner_infestation, _urea, _mop, _ssp, _mgs, _boron, finyear;
    private Button saveBtn;  // Save Button
    private EditText mMeasuresusedEt; // EditText
    private Spinner mspinner_percent,
            mspinner_intensity,
            mspinner_sooty,

    spinner_sprayingDone, spinner_controldoneTv; //Spinner Functions

    String fName = "", nFName = "", nFcode = "";
    Integer fyear = 0;

    TextView sprayingTV, controlTv, measuresusedTv;
    private CheckBox msuccessCBox,
            msprayingDoneCBox, controlDoneCBox; //CheckBox
    EditText mspinner_quantity, urea, mop, ssp, mgs, boron;
    TextView controldoneTv, percentTv, intensityTv, sootyTv, infestationTv, sprayingdoneTv, fertilizerTv, selectFertilizerTv, quantityTv;
    LinearLayout sprayingLayout, fertiLayout, controlLayout, rL0, sprayingsuccess, controlsucess;

    TextView sprayingsucessTv, controlsuccessTv;
    Spinner spinner_sprayingsuccess, spinner_controlsuccess;

    public onDataSelectedListener onDataSelectedListener;


    Calendar calendar;

    public String currentFYStr, previousFYStr, secondpreviousFYStr;
    public String currentYearStr, previousYearStr, secondpreviousYearStr;


    public void setOnDataSelectedListener(WhiteFieldFragment.onDataSelectedListener onDataSelectedListener) {
        this.onDataSelectedListener = onDataSelectedListener;
    }

    public interface onDataSelectedListener {
        void onDataSelected(int type, Bundle bundle);
    }

    public WhiteFieldFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataAccessHandler = new DataAccessHandler(getContext());
        view = inflater.inflate(R.layout.fragment_white_field, container, false);
        Bundle inputBundle = getArguments();
        getDialog().setCanceledOnTouchOutside(false);

        calendar = Calendar.getInstance();
        final FiscalDate fiscalDate = new com.oilpalm3f.mainapp.common.FiscalDate(calendar);
        final String financialYear = fiscalDate.getFinancialYearr(calendar);

        currentYearStr = financialYear + "";
        previousYearStr = Integer.parseInt(financialYear) - 1 + "";
        secondpreviousYearStr = Integer.parseInt(financialYear) - 2 + "";

        Log.d("FinancialYear123", currentYearStr + "");
        Log.d("FinancialYear456", previousYearStr + "");
        Log.d("FinancialYear789", secondpreviousYearStr + "");

        currentFYStr = financialYear + " - " + (Integer.parseInt(financialYear) + 1);
        previousFYStr = (Integer.parseInt(financialYear) - 1) +  " - " + financialYear;
        secondpreviousFYStr = Integer.parseInt(financialYear) - 2 + "-" + (Integer.parseInt(financialYear) - 1);

        Log.d("currentFinancialYear", currentFYStr + "");
        Log.d("previousFinancialYear", previousFYStr + "");
        Log.d("ptopFinancialYear", secondpreviousFYStr + "");

        if (null == inputBundle)
            return null;
        else {
            fyear = inputBundle.getInt("type");
        }

        finyear = (TextView) view.findViewById(R.id.finyear);
        sprayingTV = (TextView) view.findViewById(R.id.sprayingTV);
        controlTv = (TextView) view.findViewById(R.id.controlTV);
        measuresusedTv = (TextView) view.findViewById(R.id.measuresusedTv);
        year = inputBundle.getInt("type", 0);
        mMeasuresusedEt = (EditText) view.findViewById(R.id.measuresusedEt);
        mspinner_percent = (Spinner) view.findViewById(R.id.spinner_percent);
        mspinner_intensity = (Spinner) view.findViewById(R.id.spinner_intensity);
        mspinner_sooty = (Spinner) view.findViewById(R.id.spinner_sooty);
        mspinner_selectFertilizer = (TextView) view.findViewById(R.id.spinner_selectFertilizer);
        mspinner_quantity = (EditText) view.findViewById(R.id.spinner_quantity);
        mspinner_infestation = (TextView) view.findViewById(R.id.spinner_infestation);
//        msuccessCBox = (CheckBox) view.findViewById(R.id.successCBox);
//        msprayingDoneCBox = (CheckBox) view.findViewById(R.id.sprayingDoneCBox);
//        controlDoneCBox= (CheckBox) view.findViewById(R.id.controlDoneCBox);
        spinner_sprayingDone = (Spinner) view.findViewById(R.id.spinner_sprayingDone);
        spinner_controldoneTv = (Spinner) view.findViewById(R.id.spinner_controlDone);
        mSprayingDoneRg = (RadioGroup) view.findViewById(R.id.sprayingDoneRg);
        mboostfertiligerRg = (RadioGroup) view.findViewById(R.id.boostfertiligerRg);
        sprayingLayout = (LinearLayout) view.findViewById(R.id.sprayingLayout);
        fertiLayout = (LinearLayout) view.findViewById(R.id.fertiLayout);
        controlLayout = (LinearLayout) view.findViewById(R.id.controlLayout);
        sprayingsuccess = (LinearLayout) view.findViewById(R.id.sprayingsuccess);
        controlsucess = (LinearLayout) view.findViewById(R.id.controlsucess);
        mMeasuresusedEt = (EditText) view.findViewById(R.id.measuresusedEt);
        measuresusedTv = (TextView) view.findViewById(R.id.measuresusedTv);
        percentTv = (TextView) view.findViewById(R.id.percentTv);
        intensityTv = (TextView) view.findViewById(R.id.intensityTv);
        sootyTv = (TextView) view.findViewById(R.id.sootyTv);
        infestationTv = (TextView) view.findViewById(R.id.infestationTv);
        controldoneTv = (TextView) view.findViewById(R.id.controldoneTv);
        sprayingdoneTv = (TextView) view.findViewById(R.id.sprayingdoneTv);
        fertilizerTv = (TextView) view.findViewById(R.id.fertilizerTv);
        selectFertilizerTv = (TextView) view.findViewById(R.id.selectFertilizerTv);
        quantityTv = (TextView) view.findViewById(R.id.quantityTv);
        controlDoneRg = (RadioGroup) view.findViewById(R.id.controlDoneRg);
        //msuccessCBox = (CheckBox) view.findViewById(R.id.successCBox);
        urea = (EditText) view.findViewById(R.id.urea);
        mop = (EditText) view.findViewById(R.id.mop);
        ssp = (EditText) view.findViewById(R.id.ssp);
        mgs = (EditText) view.findViewById(R.id.mgs);
        boron = (EditText) view.findViewById(R.id.boron);
        _urea = (TextView) view.findViewById(R.id._urea);
        _mop = (TextView) view.findViewById(R.id._mop);
        _ssp = (TextView) view.findViewById(R.id._ssp);
        _mgs = (TextView) view.findViewById(R.id._mgs);
        _boron = (TextView) view.findViewById(R.id._boron);
        rL0 = (LinearLayout) view.findViewById(R.id.rL0);

        sprayingsucessTv = (TextView) view.findViewById(R.id.sprayingsucessTv);
        spinner_sprayingsuccess = (Spinner) view.findViewById(R.id.spinner_sprayingsuccess);
        controlsuccessTv = (TextView) view.findViewById(R.id.controlsuccessTv);
        spinner_controlsuccess = (Spinner) view.findViewById(R.id.spinner_controlsuccess);


        saveBtn = (Button) view.findViewById(R.id.saveBtn);

        if (fyear == Integer.parseInt(secondpreviousYearStr)){

            finyear.setText(secondpreviousFYStr + "");
        }
        else if (fyear == Integer.parseInt(previousYearStr)){
            finyear.setText(previousFYStr + "");
        }
        else {
            finyear.setText(currentFYStr + "");
        }


        mspinner_quantity.setVisibility(View.GONE);
        mspinner_selectFertilizer.setVisibility(View.GONE);
        quantityTv.setVisibility(View.GONE);

        arrayList_percent = new ArrayList<>();
        arrayList_percent.add("Select Percentage");
        arrayList_percent.add("0%");
        arrayList_percent.add("10%");
        arrayList_percent.add("20%");
        arrayList_percent.add("30%");
        arrayList_percent.add("40%");
        arrayList_percent.add("50%");
        arrayList_percent.add("60%");
        arrayList_percent.add("70%");
        arrayList_percent.add("80%");
        arrayList_percent.add("90%");
        arrayList_percent.add("100%");

        arraylist_CM = new ArrayList<>();
        arraylist_CM.add("Select %");
        arraylist_CM.add("0% to 10%");
        arraylist_CM.add("10% to 25%");
        arraylist_CM.add("25% to 50%");
        arraylist_CM.add("50% to 75%");
        arraylist_CM.add("75% to 100%");


        arrayList_intensity = new ArrayList<>();
        arrayList_intensity.add("Select Fly Intensity");
        arrayList_intensity.add("0%");
        arrayList_intensity.add("10%");
        arrayList_intensity.add("20%");
        arrayList_intensity.add("30%");
        arrayList_intensity.add("40%");
        arrayList_intensity.add("50%");
        arrayList_intensity.add("60%");
        arrayList_intensity.add("70%");
        arrayList_intensity.add("80%");
        arrayList_intensity.add("90%");
        arrayList_intensity.add("100%");


        arrayList_sooty = new ArrayList<>();
        arrayList_sooty.add("Select Percentage");
        arrayList_sooty.add("0%");
        arrayList_sooty.add("10%");
        arrayList_sooty.add("20%");
        arrayList_sooty.add("30%");
        arrayList_sooty.add("40%");
        arrayList_sooty.add("50%");
        arrayList_sooty.add("60%");
        arrayList_sooty.add("70%");
        arrayList_sooty.add("80%");
        arrayList_sooty.add("90%");
        arrayList_sooty.add("100%");


        arrayList_infestation = new ArrayList<>();
        arrayList_infestation.add("Select Month");
        arrayList_infestation.add("None");
        arrayList_infestation.add("January");
        arrayList_infestation.add("February");
        arrayList_infestation.add("March");
        arrayList_infestation.add("April");
        arrayList_infestation.add("May");
        arrayList_infestation.add("June");
        arrayList_infestation.add("July");
        arrayList_infestation.add("August");
        arrayList_infestation.add("September");
        arrayList_infestation.add("October");
        arrayList_infestation.add("November");
        arrayList_infestation.add("December");

        arrayList_oilused = new ArrayList<>();
        arrayList_oilused.add("Select Month");
        arrayList_oilused.add("January");
        arrayList_oilused.add("February");
        arrayList_oilused.add("March");
        arrayList_oilused.add("April");
        arrayList_oilused.add("May");
        arrayList_oilused.add("June");
        arrayList_oilused.add("July");
        arrayList_oilused.add("August");
        arrayList_oilused.add("September");
        arrayList_oilused.add("October");
        arrayList_oilused.add("November");
        arrayList_oilused.add("December");

        /** Multiple Month Selection For Infestation **/


        listItems = getResources().getStringArray(R.array.Month);
        checkedItems = new boolean[listItems.length];


        mspinner_percent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mspinner_percent.getSelectedItem().toString() == "0%") {
                    rL0.setVisibility(View.GONE);
                } else {
                    rL0.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mspinner_infestation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setTitle("Month of Infestation");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            mUserItems.add(position);
                        } else {
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        if (item.length() == 0) {
                            mspinner_infestation.setText("--Month Of Infestation--");
                        } else {
                            mspinner_infestation.setText(item);
                        }

                    }
                });

                mBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

//                mBuilder.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        for (int i = 0; i < checkedItems.length; i++) {
//                            checkedItems[i] = false;
//                            mUserItems.clear();
//                        }
//                    }
//                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        listItems_1 = getResources().getStringArray(R.array.Fertilizers);
        checkedItems_1 = new boolean[listItems.length];

        mspinner_selectFertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setTitle("Select Fertilizers");
                mBuilder.setMultiChoiceItems(listItems_1, checkedItems_1, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            mUserItems_1.add(position);
                        } else {
                            mUserItems_1.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item_1 = "";
                        for (int i = 0; i < mUserItems_1.size(); i++) {
                            item_1 = item_1 + listItems_1[mUserItems_1.get(i)];
                            if (i != mUserItems_1.size() - 1) {
                                item_1 = item_1 + ", ";
                            }
                        }
                        if (item_1.length() == 0) {
                            mspinner_selectFertilizer.setText("--Fertilizer--");
                        } else {
                            mspinner_selectFertilizer.setText(item_1);
                        }

                    }
                });

                mBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

//                mBuilder.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        for (int i = 0; i < checkedItems.length; i++) {
//                            checkedItems[i] = false;
//                            mUserItems.clear();
//                        }
//                    }
//                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        arrayAdapter_percent = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_percent);
        mspinner_percent.setAdapter(arrayAdapter_percent);


        arrayAdapter_intensity = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_intensity);
        mspinner_intensity.setAdapter(arrayAdapter_intensity);

        arrayAdapter_sooty = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_sooty);
        mspinner_sooty.setAdapter(arrayAdapter_sooty);

        arrayAdapter_infestation = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_infestation);
//        mspinner_infestation.setAdapter(arrayAdapter_infestation);

        arrayAdapter_oilused = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_oilused);

        Ad_neemOilsuccess = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arraylist_CM);


        Ad_sprayingsuccess = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arraylist_CM);
        spinner_sprayingsuccess.setAdapter(Ad_sprayingsuccess);


        Ad_controlsuccess = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arraylist_CM);
        spinner_controlsuccess.setAdapter(Ad_controlsuccess);


        arrayList_sprayingdone = new ArrayList<>();
        arrayList_sprayingdone.add("Select Month");
        arrayList_sprayingdone.add("January");
        arrayList_sprayingdone.add("February");
        arrayList_sprayingdone.add("March");
        arrayList_sprayingdone.add("April");
        arrayList_sprayingdone.add("May");
        arrayList_sprayingdone.add("June");
        arrayList_sprayingdone.add("July");
        arrayList_sprayingdone.add("August");
        arrayList_sprayingdone.add("September");
        arrayList_sprayingdone.add("October");
        arrayList_sprayingdone.add("November");
        arrayList_sprayingdone.add("December");

        arrayAdapter_sprayingdone = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_sprayingdone);
        spinner_sprayingDone.setAdapter(arrayAdapter_sprayingdone);


        arrayList_controldone = new ArrayList<>();
        arrayList_controldone.add("Select Month");
        arrayList_controldone.add("January");
        arrayList_controldone.add("February");
        arrayList_controldone.add("March");
        arrayList_controldone.add("April");
        arrayList_controldone.add("May");
        arrayList_controldone.add("June");
        arrayList_controldone.add("July");
        arrayList_controldone.add("August");
        arrayList_controldone.add("September");
        arrayList_controldone.add("October");
        arrayList_controldone.add("November");
        arrayList_controldone.add("December");

        arrayAdapter_controldone = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_controldone);
        spinner_controldoneTv.setAdapter(arrayAdapter_controldone);


        arrayList_fertilizer = new ArrayList<>();
        arrayList_fertilizer.add("Select Type");
        arrayList_fertilizer.add("Urea");
        arrayList_fertilizer.add("MOP");
        arrayList_fertilizer.add("SSP");
        arrayList_fertilizer.add("Mgs05");
        arrayList_fertilizer.add("Boron");


        arrayList_quantity = new ArrayList<>();
        arrayList_quantity.add("Select Quantity");
        arrayList_quantity.add("1");
        arrayList_quantity.add("2");
        arrayList_quantity.add("3");
        arrayList_quantity.add("4");
        arrayList_quantity.add("5");

        arrayAdapter_fertilizer = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_fertilizer);
//        mspinner_selectFertilizer.setAdapter(arrayAdapter_fertilizer);

        arrayAdapter_quantity = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_quantity);


        mSprayingDoneRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.sprayingDoneYesRb) {
                    sprayingLayout.setVisibility(View.VISIBLE);
                    sprayingsuccess.setVisibility(View.VISIBLE);
                    sprayingDone = 1;
                }
                if (checkedId == R.id.sprayingDoneNoRb) {
                    sprayingLayout.setVisibility(View.GONE);
                    sprayingsuccess.setVisibility(View.GONE);
                    sprayingDone = 0;
                }

            }
        });

        controlDoneRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.controlDoneYesRb) {
                    controlLayout.setVisibility(View.VISIBLE);
                    controlsucess.setVisibility(View.VISIBLE);
                    controlDone = 1;
                }
                if (checkedId == R.id.controlDoneNoRb) {
                    controlLayout.setVisibility(View.GONE);
                    controlsucess.setVisibility(View.GONE);
                    controlDone = 0;
                }

            }
        });

        mboostfertiligerRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.fertilizerYesRb) {
                    fertiLayout.setVisibility(View.VISIBLE);
                    boostFertilizer = 1;
                }
                if (checkedId == R.id.fertilizerNoRb) {
                    fertiLayout.setVisibility(View.GONE);
                }

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mspinner_percent.getSelectedItem().toString().equals("0%")) {
                    dataBundle = new Bundle();
                    dataBundle.putString("Question", percentTv.getText().toString());
                    dataBundle.putString("Answer", "");
                    dataBundle.putString("Value", mspinner_percent.getSelectedItem().toString());
                    dataBundle.putInt("Year", year);
                    onDataSelectedListener.onDataSelected(year, dataBundle);
                    dismiss();

                } else {
                    if (valid()) {
                        dataBundle = new Bundle();
                        if (!mspinner_percent.getSelectedItem().toString().equals("Select Percentage")) {
                            dataBundle.putString("Question", percentTv.getText().toString());
                            dataBundle.putString("Answer", "");
                            dataBundle.putString("Value", mspinner_percent.getSelectedItem().toString());
                            dataBundle.putInt("Year", year);
                            onDataSelectedListener.onDataSelected(year, dataBundle);
                        }
                        if (!mspinner_intensity.getSelectedItem().toString().equals("Select Fly Intensity")) {
                            dataBundle.putString("Question", intensityTv.getText().toString());
                            dataBundle.putString("Answer", "");
                            dataBundle.putString("Value", mspinner_intensity.getSelectedItem().toString());
                            dataBundle.putInt("Year", year);
                            onDataSelectedListener.onDataSelected(year, dataBundle);
                        }
                        if (!mspinner_sooty.getSelectedItem().toString().equals("Select Percentage")) {
                            dataBundle.putString("Question", sootyTv.getText().toString());
                            dataBundle.putString("Answer", "");
                            dataBundle.putString("Value", mspinner_sooty.getSelectedItem().toString());
                            dataBundle.putInt("Year", year);
                            onDataSelectedListener.onDataSelected(year, dataBundle);
                        }
//                    if (infestationTv.getText().length() > 0) {
//                        dataBundle.putString("Question", infestationTv.getText().toString());
//                        dataBundle.putString("Answer", "");
//                        dataBundle.putString("Value", mspinner_infestation.getSelectedItem().toString());
//                        dataBundle.putInt("Year", year);
//                        onDataSelectedListener.onDataSelected(year, dataBundle);
//                    }
                        if (!mspinner_infestation.getText().toString().equals("--Month Of Infestation--")) {
                            dataBundle.putString("Question", infestationTv.getText().toString());
                            dataBundle.putString("Answer", "");
                            dataBundle.putString("Value", mspinner_infestation.getText().toString());
                            dataBundle.putInt("Year", year);
                            onDataSelectedListener.onDataSelected(year, dataBundle);
                        }


                        if (sprayingDone == 1) {
                            dataBundle.putString("Question", sprayingTV.getText().toString());
                            dataBundle.putString("Answer", "");
                            dataBundle.putString("Value", "Yes");
                            dataBundle.putInt("Year", year);
                            onDataSelectedListener.onDataSelected(year, dataBundle);

                            if (!spinner_sprayingDone.getSelectedItem().toString().equals("Select Month")) {
                                dataBundle.putString("Question", sprayingdoneTv.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", spinner_sprayingDone.getSelectedItem().toString());
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            }

                            if (!spinner_sprayingsuccess.getSelectedItem().toString().equals("Select %")) {
                                dataBundle.putString("Question", sprayingsucessTv.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", spinner_sprayingsuccess.getSelectedItem().toString());
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            } else {
                                dataBundle.putString("Question", sprayingsucessTv.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", "No");
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            }

                        } else {
                            dataBundle.putString("Question", sprayingTV.getText().toString());
                            dataBundle.putString("Answer", "");
                            dataBundle.putString("Value", "No");
                            dataBundle.putInt("Year", year);
                            onDataSelectedListener.onDataSelected(year, dataBundle);
                        }

                        if (controlDone == 1) {
                            dataBundle.putString("Question", controlTv.getText().toString());
                            dataBundle.putString("Answer", "");
                            dataBundle.putString("Value", "Yes");
                            dataBundle.putInt("Year", year);
                            onDataSelectedListener.onDataSelected(year, dataBundle);

                            if (mMeasuresusedEt.getText().toString().length() > 0) {
                                dataBundle.putString("Question", measuresusedTv.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", mMeasuresusedEt.getText().toString());
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            }

                            if (!spinner_controldoneTv.getSelectedItem().toString().equals("Select Month")) {
                                dataBundle.putString("Question", controldoneTv.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", spinner_controldoneTv.getSelectedItem().toString());
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            }

                            if (!spinner_controlsuccess.getSelectedItem().toString().equals("Select %")) {
                                dataBundle.putString("Question", controlsuccessTv.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", spinner_controlsuccess.getSelectedItem().toString());
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            } else {
                                dataBundle.putString("Question", controlsuccessTv.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", "No");
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            }

                        } else {
                            dataBundle.putString("Question", controlTv.getText().toString());
                            dataBundle.putString("Answer", "");
                            dataBundle.putString("Value", "No");
                            dataBundle.putInt("Year", year);
                            onDataSelectedListener.onDataSelected(year, dataBundle);
                        }

                        if (boostFertilizer == 1) {
                            dataBundle.putString("Question", fertilizerTv.getText().toString());
                            dataBundle.putString("Answer", "");
                            dataBundle.putString("Value", "Yes");
                            dataBundle.putInt("Year", year);
                            onDataSelectedListener.onDataSelected(year, dataBundle);


//                        if (selectFertilizerTv.getText().length()!= 0) {
//                            dataBundle.putString("Question",selectFertilizerTv.getText().toString());
//                            dataBundle.putString("Answer", "");
//                            dataBundle.putString("Value", selectFertilizerTv.getText().toString());
//                            dataBundle.putInt("Year", year);
//                            onDataSelectedListener.onDataSelected(year, dataBundle);
//                        }


                            if (!mspinner_selectFertilizer.getText().equals("--Fertilizer--")) {
                                dataBundle.putString("Question", selectFertilizerTv.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", mspinner_selectFertilizer.getText().toString());
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            }

//                        if (mspinner_quantity.getText().toString().length() > 0) {
//                            dataBundle.putString("Question", quantityTv.getText().toString());
//                            dataBundle.putString("Answer", "");
//                            dataBundle.putString("Value", mspinner_quantity.getText().toString());
//                            dataBundle.putInt("Year", year);
//                            onDataSelectedListener.onDataSelected(year, dataBundle);
//                        }
                            if (!boron.getText().toString().isEmpty()) {

                                dataBundle.putString("Question", _boron.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", boron.getText().toString());
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            }
                            if (!urea.getText().toString().isEmpty()) {

                                dataBundle.putString("Question", _urea.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", urea.getText().toString());
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            }
                            if (!mop.getText().toString().isEmpty()) {

                                dataBundle.putString("Question", _mop.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", mop.getText().toString());
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            }
                            if (!ssp.getText().toString().isEmpty()) {

                                dataBundle.putString("Question", _ssp.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", ssp.getText().toString());
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            }
                            if (!mgs.getText().toString().isEmpty()) {

                                dataBundle.putString("Question", _mgs.getText().toString());
                                dataBundle.putString("Answer", "");
                                dataBundle.putString("Value", mgs.getText().toString());
                                dataBundle.putInt("Year", year);
                                onDataSelectedListener.onDataSelected(year, dataBundle);
                            }
                        } else {
                            dataBundle.putString("Question", fertilizerTv.getText().toString());
                            dataBundle.putString("Answer", "");
                            dataBundle.putString("Value", "No");
                            dataBundle.putInt("Year", year);
                            onDataSelectedListener.onDataSelected(year, dataBundle);
                        }
                        dismiss();
                    } else {
                        // dataBundle = null;
                        Toast.makeText(getActivity(), "Please Answer All The Questions", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return view;
    }

    private boolean valid() {
        if (!boron.getText().toString().isEmpty() || !mop.getText().toString().isEmpty() || !urea.getText().toString().isEmpty() || !ssp.getText().toString().isEmpty() || !mgs.getText().toString().isEmpty()) {
            count = 1;
        }
        return !mspinner_percent.getSelectedItem().toString().equals("Select Percentage")
                && !mspinner_intensity.getSelectedItem().toString().equals("Select Fly Intensity")
                && !mspinner_sooty.getSelectedItem().toString().equals("Select Percentage")
                && !mspinner_infestation.getText().toString().equals("--Month Of Infestation--")
                && (sprayingDone == 1 ? !spinner_sprayingDone.getSelectedItem().toString().equals("Select Month") && !spinner_sprayingsuccess.getSelectedItem().toString().equals("Select %") : true)
                && (controlDone == 1 ? (mMeasuresusedEt.getText().toString().length() > 0) && !spinner_controldoneTv.getSelectedItem().toString().equals("Select Month") && !spinner_controlsuccess.getSelectedItem().toString().equals("Select %") : true)
                //  && (boostFertilizer==1 ? (!mspinner_selectFertilizer.getText().equals("--Fertilizer--"))&& mspinner_quantity.getText().toString().length() > 0 : true)
//               && (boostFertilizer==1 ? (!mspinner_selectFertilizer.getText().equals("--Fertilizer--")
//                ? ()
//                                            ): true)
                && (boostFertilizer == 1 ? (count == 1) : true);

    }

}
