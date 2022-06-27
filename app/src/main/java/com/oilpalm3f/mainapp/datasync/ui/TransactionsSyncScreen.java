    package com.oilpalm3f.mainapp.datasync.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataSyncHelper;
import com.oilpalm3f.mainapp.uihelper.ProgressBar;

import java.util.LinkedHashMap;

public class TransactionsSyncScreen extends AppCompatActivity {

    private static final String LOG_TAG = TransactionsSyncScreen.class.getName();
    private Button globalSyncBtn, submitBtn;
    private Spinner statespin, districtSpin, mandalSpin, villageSpin;
    private LinkedHashMap<String, Pair> stateDataMap = null, districtDataMap, mandalDataMap, villagesDataMap;
    private DataAccessHandler dataAccessHandler;
    private String villageCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_sync_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataAccessHandler = new DataAccessHandler(TransactionsSyncScreen.this);

        statespin = (Spinner) findViewById(R.id.statespin);
        villageSpin = (Spinner) findViewById(R.id.villageSpin);

        stateDataMap = dataAccessHandler.getPairData(Queries.getInstance().getStatesQuery());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(TransactionsSyncScreen.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(stateDataMap, "State"));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statespin.setAdapter(spinnerArrayAdapter);

        districtSpin = (Spinner) findViewById(R.id.districtSpin);
        mandalSpin = (Spinner) findViewById(R.id.mandalSpin);
        globalSyncBtn = (Button) findViewById(R.id.globalSyncBtn);

        globalSyncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog(TransactionsSyncScreen.this);
            }
        });

        submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtils.isEmptySpinner(statespin)) {
                    Toast.makeText(TransactionsSyncScreen.this, "Please select state", Toast.LENGTH_SHORT).show();
                } else if (CommonUtils.isEmptySpinner(districtSpin)) {
                    Toast.makeText(TransactionsSyncScreen.this, "Please select district", Toast.LENGTH_SHORT).show();
                } else if (CommonUtils.isEmptySpinner(mandalSpin)) {
                    Toast.makeText(TransactionsSyncScreen.this, "Please select mandal", Toast.LENGTH_SHORT).show();
                } else if (CommonUtils.isEmptySpinner(villageSpin)) {
                    Toast.makeText(TransactionsSyncScreen.this, "Please select village", Toast.LENGTH_SHORT).show();
                }  else {
                    ProgressBar.showProgressBar(TransactionsSyncScreen.this, "Getting transactions data...");
                    DataSyncHelper.startTransactionSync(TransactionsSyncScreen.this, null);
                }

            }
        });

        statespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (stateDataMap != null && stateDataMap.size() > 0 && statespin.getSelectedItemPosition() != 0) {

                    districtDataMap = dataAccessHandler.getPairData(Queries.getInstance().getDistrictQuery(stateDataMap.keySet().toArray(new String[stateDataMap.size()])[i - 1]));
                    ArrayAdapter<String> spinnerDistrictArrayAdapter = new ArrayAdapter<>(TransactionsSyncScreen.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(districtDataMap, "District"));
                    spinnerDistrictArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    districtSpin.setAdapter(spinnerDistrictArrayAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        districtSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (districtDataMap != null && districtDataMap.size() > 0 && districtSpin.getSelectedItemPosition() != 0) {
                    mandalDataMap = dataAccessHandler.getPairData(Queries.getInstance().getMandalsQuery(districtDataMap.keySet().toArray(new String[districtDataMap.size()])[i - 1]));
                    ArrayAdapter<String> spinnerMandalArrayAdapter = new ArrayAdapter<>(TransactionsSyncScreen.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(mandalDataMap, "Mandal"));
                    spinnerMandalArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mandalSpin.setAdapter(spinnerMandalArrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mandalSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mandalDataMap != null && mandalDataMap.size() > 0 && mandalSpin.getSelectedItemPosition() != 0) {
                    villagesDataMap = dataAccessHandler.getPairData(Queries.getInstance().getVillagesQuery(mandalDataMap.keySet().toArray(new String[mandalDataMap.size()])[i - 1]));
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(TransactionsSyncScreen.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(villagesDataMap, "Village"));
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    villageSpin.setAdapter(spinnerArrayAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        villageSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (villagesDataMap != null && villagesDataMap.size() > 0 && villageSpin.getSelectedItemPosition() != 0) {
                    villageCode = villagesDataMap.keySet().toArray(new String[villagesDataMap.size()])[i - 1];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void showEditDialog(final Context context) {
        final EditText idEdit = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Transactions Sync")
                .setMessage("Please enter transactions sync password")
                .setView(idEdit, 20, 0, 20, 0)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button muteBtn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                muteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(idEdit.getText().toString())) {
                            ProgressBar.showProgressBar(TransactionsSyncScreen.this, "Validating password...");
                            DataSyncHelper.ableToProceedToTransactionSync(idEdit.getText().toString(), new ApplicationThread.OnComplete() {
                                @Override
                                public void execute(boolean success, Object result, String msg) {
                                    ProgressBar.hideProgressBar();
                                    if (success && result != null && result.toString().contains("Valid Password")) {
                                        ProgressBar.showProgressBar(TransactionsSyncScreen.this, "Getting transactions data...");
                                        DataSyncHelper.startTransactionSync(TransactionsSyncScreen.this, null);
                                        alertDialog.dismiss();
                                    } else {
                                        CommonUtils.showToast((result != null ? ""+result.toString() : "Invalid Password"), TransactionsSyncScreen.this);
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        alertDialog.show();
    }

}
