package com.oilpalm3f.mainapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.UpdateUiListener;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.cropmaintenance.ComplaintDetailsFragment;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.ComplaintRepository;
import com.oilpalm3f.mainapp.dbmodels.ComplaintStatusHistory;
import com.oilpalm3f.mainapp.dbmodels.ComplaintTypeXref;
import com.oilpalm3f.mainapp.dbmodels.Complaints;
import com.oilpalm3f.mainapp.dbmodels.ComplaintsDetails;

import java.util.List;

/**
 * Created by RAMESH BABU on 02-07-2017.
 */

//Displaying Complaints Screen
public class ComplaintsScreenActivity extends AppCompatActivity implements View.OnClickListener, ComplaintsDetailsRecyclerAdapter.ClickListener, UpdateUiListener {
    private static final String LOG_TAG = ComplaintsScreenActivity.class.getName();
    private RecyclerView complaints_list;
    private ProgressBar progress;
    private Button complaint_add, viewCurrentComplaintsBtn;
    private ComplaintsDetailsRecyclerAdapter ComplaintsDetailsRecyclerAdapter;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private Intent  intent;
    private boolean isPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_complaints_screen);
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Complaints");
        intent= getIntent();
        if(intent!=null){
            isPlot = intent.getBooleanExtra("plot",false);
        }
        setUI();

        Complaints complaints = (Complaints) DataManager.getInstance().getDataFromManager(DataManager.NEW_COMPLAINT_DETAILS);
        viewCurrentComplaintsBtn.setVisibility((complaints != null) ? View.VISIBLE : View.GONE);

    }

    private void setUI() {
        complaints_list = (RecyclerView) findViewById(R.id.complaints_list);
        complaint_add = (Button) findViewById(R.id.complaint_add);
        viewCurrentComplaintsBtn = (Button) findViewById(R.id.view_complaint);
        progress = (ProgressBar) findViewById(R.id.progress);
        bindData();
        viewCurrentComplaintsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewCurrentComplaintFragment viewCurrentComplaintFragment = new ViewCurrentComplaintFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, viewCurrentComplaintFragment).addToBackStack(null)
                        .commit();
            }
        });

    }

    private void bindData() {
        com.oilpalm3f.mainapp.uihelper.ProgressBar.showProgressBar(this, "Please wait...");
        ApplicationThread.bgndPost(LOG_TAG, "", new Runnable() {
            @Override
            public void run() {
                final DataAccessHandler dataAccessHandler = new DataAccessHandler(ComplaintsScreenActivity.this);
                dataAccessHandler.getComplaintsByUser(Queries.getInstance().getComplaintToDisplay(isPlot, CommonConstants.PLOT_CODE), new ApplicationThread.OnComplete<List<ComplaintsDetails>>() {
                    @Override
                    public void execute(boolean success, final List<ComplaintsDetails> complaintsDetails , String msg) {
                        com.oilpalm3f.mainapp.uihelper.ProgressBar.hideProgressBar();
                        ApplicationThread.uiPost(LOG_TAG, "", new Runnable() {
                            @Override
                            public void run() {
                                ComplaintsDetailsRecyclerAdapter = new ComplaintsDetailsRecyclerAdapter(ComplaintsScreenActivity.this, complaintsDetails, dataAccessHandler);
                                layoutManager = new LinearLayoutManager(ComplaintsScreenActivity.this, LinearLayoutManager.VERTICAL, false);
                                complaints_list.setLayoutManager(layoutManager);
                                complaints_list.setAdapter(ComplaintsDetailsRecyclerAdapter);
                                ComplaintsDetailsRecyclerAdapter.setOnClickListener(ComplaintsScreenActivity.this);
                            }
                        });
                    }
                });

            }
        });
        complaint_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == complaint_add) {
            System.out.println("add ====================================>");
            Bundle dataBundle = new Bundle();
            dataBundle.putBoolean(CommonConstants.KEY_NEW_COMPLAINT, true);
            ComplaintDetailsFragment complaintDetailsFragment = new ComplaintDetailsFragment();
            complaintDetailsFragment.setArguments(dataBundle);
            complaintDetailsFragment.setUpdateUiListener(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, complaintDetailsFragment).addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(String complaintCode) {
        Log.v(LOG_TAG, "### complant code " + complaintCode);
        DataAccessHandler dataAccessHandler = new DataAccessHandler(this);

        Complaints complaintsList = (Complaints) dataAccessHandler.getComplaints(Queries.getInstance().getComplaintDataByCode(complaintCode), 0);

        List<ComplaintStatusHistory> complaintsStatusHistory = (List<ComplaintStatusHistory>) dataAccessHandler.getComplaintStatusHistory
                (Queries.getInstance().getComplaintStatusHistoryByCode(complaintCode), 1);

        List<ComplaintTypeXref> complaintsTypeXref = (List<ComplaintTypeXref>) dataAccessHandler
                .getComplaintTypeXref(Queries.getInstance().getComplaintXrefByCode(complaintCode), 1);

        List<ComplaintRepository> complaintsRepository = (List<ComplaintRepository>) dataAccessHandler
                .getComplaintRepository(Queries.getInstance().getComplaintRepositoryByCode(complaintCode), 1);

        if (null != complaintsList) {
            DataManager.getInstance().addData(DataManager.COMPLAINT_DETAILS, complaintsList);
            DataManager.getInstance().addData(DataManager.COMPLAINT_STATUS_HISTORY, complaintsStatusHistory);
            DataManager.getInstance().addData(DataManager.COMPLAINT_TYPE, complaintsTypeXref);
            DataManager.getInstance().addData(DataManager.COMPLAINT_REPOSITORY, complaintsRepository);
        }

        CommonConstants.COMPLAINT_CODE = complaintCode;

        Bundle dataBundle = new Bundle();
        dataBundle.putString("complaintCode", complaintCode);
        dataBundle.putBoolean(CommonConstants.KEY_NEW_COMPLAINT, false);
        ComplaintDetailsFragment complaintDetailsFragment = new ComplaintDetailsFragment();
        complaintDetailsFragment.setArguments(dataBundle);
        complaintDetailsFragment.setUpdateUiListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, complaintDetailsFragment).addToBackStack(null)
                .commit();
    }

    @Override
    public void updateUserInterface(int position) {
        Log.v(LOG_TAG, "@@@ ui update called");
        bindData();
    }

    @Override
    public void onResume() {
        super.onResume();
        bindData();
    }
}


