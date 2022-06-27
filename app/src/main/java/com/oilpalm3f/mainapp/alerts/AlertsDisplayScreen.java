package com.oilpalm3f.mainapp.alerts;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.ui.HomeScreen;
import com.oilpalm3f.mainapp.ui.RecyclerItemClickListener;
import com.oilpalm3f.mainapp.uihelper.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//Display Alerts Screen

public class AlertsDisplayScreen extends AppCompatActivity  {

    public static final int LIMIT = 30;
    public static final int TYPE_PLOT_FOLLOWUP = 1;
    public static final int TYPE_PLOT_VISITS = 2;
    public static final int TYPE_PLOT_MISSING_TREES = 3;
    public static final int TYPE_PLOT_NOT_VISIT = 4;
    private static final String LOG_TAG = AlertsDisplayScreen.class.getName();
    private Toolbar toolbar;
    private int offset;
    private ActionBar actionBar;
    private LinearLayout dateSelection;
    private EditText fromDate,toDate;
    private Button searchBtn;
    private DataAccessHandler dataAccessHandler;
    private List<AlertsPlotInfo> alertsPlotInfoList = new ArrayList<>();
    private AlertPlotInfoAdapter alertsPlotDetailsRecyclerAdapter;
    private List<MissingTressInfo> alertsMissingTreesInfoList = new ArrayList<>();
    private MissingTreesInfoAdapter alertsMissingTreesDetailsRecyclerAdapter;
    private List<AlertsVisitsInfo> alertsVisitsInfoList = new ArrayList<>();
    private AlertsVisitsInfoAdapter alertsVisitsDetailsRecyclerAdapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView alerts_list;
    private boolean isLoading = false;
    private Calendar myCalendar = Calendar.getInstance();
    private boolean hasMoreItems = true;
    private int alert_type;
    private String fromDateStr = "";
    private String toDateStr = "";
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    if (!hasMoreItems) {
//                        Toast.makeText(SearchFarmerScreen.this, "No more items", Toast.LENGTH_SHORT).show();
                    } else {
                        isLoading = true;
                        offset = offset + LIMIT;
                        renderAlerts(alert_type, offset);
                    }

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_display_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        alerts_list = (RecyclerView) findViewById(R.id.alerts_list);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        dataAccessHandler = new DataAccessHandler(this);
        dateSelection = findViewById(R.id.dateSelection);
        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        searchBtn = findViewById(R.id.searchBtnT);

        alert_type = getIntent().getIntExtra(HomeScreen.AlertsChooser.ALERT_TYPE, 0);
        setTitle(alert_type, 0);



        alerts_list.addOnScrollListener(recyclerViewOnScrollListener);


        String currentDate = CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        fromDate.setText(sdf.format(new Date()));
        toDate.setText(sdf.format(new Date()));

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(0);
            }
        };

        final DatePickerDialog.OnDateSetListener toDateD = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(1);
            }
        };
        String dateFormatter = "yyyy-MM-dd";
        SimpleDateFormat sdf2 = new SimpleDateFormat(dateFormatter, Locale.US);
        fromDateStr = sdf2.format(myCalendar.getTime());
        toDateStr = sdf2.format(myCalendar.getTime());
        fromDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(AlertsDisplayScreen.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));  //date is dateSetListener as per your code in question
            datePickerDialog.show();
        });


        toDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(AlertsDisplayScreen.this, toDateD, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));  //date is dateSetListener as per your code in question
            datePickerDialog.show();
        });


        searchBtn.setOnClickListener(v ->{
            ProgressBar.showProgressBar(this, "Please wait...");
            alertsVisitsInfoList = (List<AlertsVisitsInfo>) dataAccessHandler.getAlertsVisitsInfo(Queries.getInstance().getAlertsNotVisitsInfoQuery(LIMIT, offset,fromDateStr,toDateStr), 1);
            Collections.reverse(alertsVisitsInfoList);

            ApplicationThread.uiPost(LOG_TAG, "", new Runnable() {
                @Override
                public void run() {
                    ProgressBar.hideProgressBar();
                    alertsVisitsDetailsRecyclerAdapter = new AlertsVisitsInfoAdapter(AlertsDisplayScreen.this, alertsVisitsInfoList);
                    if (alertsVisitsInfoList != null && !alertsVisitsInfoList.isEmpty()) {
                        layoutManager = new LinearLayoutManager(AlertsDisplayScreen.this, LinearLayoutManager.VERTICAL, false);
                        alerts_list.setLayoutManager(layoutManager);
                        alerts_list.setAdapter(alertsVisitsDetailsRecyclerAdapter);
                        setTitle(alert_type, offset == 0 ? alertsVisitsInfoList.size() : offset);
                    }
                }
            });
        });

        if(alert_type == TYPE_PLOT_NOT_VISIT)
        {
            dateSelection.setVisibility(View.VISIBLE);
        }


        renderAlerts(alert_type, offset);

    }

    private void updateLabel(int type) {
        String myFormat = "dd-MM-yyyy";
        String dateFormatter = "yyyy-MM-dd";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf2 = new SimpleDateFormat(dateFormatter, Locale.US);

        if (type == 0) {
            fromDateStr = sdf2.format(myCalendar.getTime());
            fromDate.setText(sdf.format(myCalendar.getTime()));
        } else {
            toDateStr = sdf2.format(myCalendar.getTime());
            toDate.setText(sdf.format(myCalendar.getTime()));
        }

    }

    private void renderAlerts(final int alert_type, final int offset) {
        ProgressBar.showProgressBar(this, "Please wait...");
        ApplicationThread.bgndPost(LOG_TAG, "renderAlerts", new Runnable() {
            @Override
            public void run() {
                if (alert_type == TYPE_PLOT_FOLLOWUP) {
                    alertsPlotInfoList =(List<AlertsPlotInfo>) dataAccessHandler.getAlertsPlotInfo(Queries.getInstance().getAlertsPlotFollowUpQuery(LIMIT, offset), 1);
                    Collections.reverse(alertsPlotInfoList);
                    ApplicationThread.uiPost(LOG_TAG, "", new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar.hideProgressBar();
                            alertsPlotDetailsRecyclerAdapter = new AlertPlotInfoAdapter(AlertsDisplayScreen.this, alertsPlotInfoList);
                            if (alertsPlotInfoList != null && !alertsPlotInfoList.isEmpty()) {
                                layoutManager = new LinearLayoutManager(AlertsDisplayScreen.this, LinearLayoutManager.VERTICAL, false);
                                alerts_list.setLayoutManager(layoutManager);
                                alerts_list.setAdapter(alertsPlotDetailsRecyclerAdapter);
//                                alertsPlotDetailsRecyclerAdapter.setRecyclerItemClickListener(AlertsDisplayScreen.this);
                                setTitle(alert_type, offset == 0 ? alertsPlotInfoList.size() : offset);
                            }
                        }
                    });
                } else if (alert_type == TYPE_PLOT_MISSING_TREES) {
                    alertsMissingTreesInfoList = (List<MissingTressInfo>) dataAccessHandler.getAletsMissingTreesInfo(Queries.getInstance().getAlertsMissingTreesInfoQuery(LIMIT, offset), 1);
                    Collections.reverse(alertsMissingTreesInfoList);

                    ApplicationThread.uiPost(LOG_TAG, "", new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar.hideProgressBar();
                            alertsMissingTreesDetailsRecyclerAdapter = new MissingTreesInfoAdapter(AlertsDisplayScreen.this, alertsMissingTreesInfoList);
                            if (alertsMissingTreesInfoList != null && !alertsMissingTreesInfoList.isEmpty()) {
                                layoutManager = new LinearLayoutManager(AlertsDisplayScreen.this, LinearLayoutManager.VERTICAL, false);
                                alerts_list.setLayoutManager(layoutManager);
                                alerts_list.setAdapter(alertsMissingTreesDetailsRecyclerAdapter);
//                                alertsMissingTreesDetailsRecyclerAdapter.setRecyclerItemClickListener(AlertsDisplayScreen.this);
                                setTitle(alert_type, offset == 0 ? alertsMissingTreesInfoList.size() : offset);
                            }
                        }
                    });
                } else if (alert_type == TYPE_PLOT_VISITS) {
                    alertsVisitsInfoList = (List<AlertsVisitsInfo>) dataAccessHandler.getAlertsVisitsInfo(Queries.getInstance().getAlertsVisitsInfoQuery(LIMIT, offset), 1);
                    Collections.reverse(alertsVisitsInfoList);

                    ApplicationThread.uiPost(LOG_TAG, "", new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar.hideProgressBar();
                            alertsVisitsDetailsRecyclerAdapter = new AlertsVisitsInfoAdapter(AlertsDisplayScreen.this, alertsVisitsInfoList);
                            if (alertsVisitsInfoList != null && !alertsVisitsInfoList.isEmpty()) {
                                layoutManager = new LinearLayoutManager(AlertsDisplayScreen.this, LinearLayoutManager.VERTICAL, false);
                                alerts_list.setLayoutManager(layoutManager);
                                alerts_list.setAdapter(alertsVisitsDetailsRecyclerAdapter);
//                                alertsVisitsDetailsRecyclerAdapter.setRecyclerItemClickListener(AlertsDisplayScreen.this);
                                setTitle(alert_type, offset == 0 ? alertsVisitsInfoList.size() : offset);
                            }
                        }
                    });
                }
                else if(alert_type == TYPE_PLOT_NOT_VISIT)
                {
                    alertsVisitsInfoList = (List<AlertsVisitsInfo>) dataAccessHandler.getAlertsVisitsInfo(Queries.getInstance().getAlertsNotVisitsInfoQuery(LIMIT, offset,fromDateStr,toDateStr), 1);
                    Collections.reverse(alertsVisitsInfoList);

                    ApplicationThread.uiPost(LOG_TAG, "", new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar.hideProgressBar();
                            alertsVisitsDetailsRecyclerAdapter = new AlertsVisitsInfoAdapter(AlertsDisplayScreen.this, alertsVisitsInfoList);
                            if (alertsVisitsInfoList != null && !alertsVisitsInfoList.isEmpty()) {
                                layoutManager = new LinearLayoutManager(AlertsDisplayScreen.this, LinearLayoutManager.VERTICAL, false);
                                alerts_list.setLayoutManager(layoutManager);
                                alerts_list.setAdapter(alertsVisitsDetailsRecyclerAdapter);
                                setTitle(alert_type, offset == 0 ? alertsVisitsInfoList.size() : offset);
                            }
                        }
                    });
                }
            }
        });
    }



    private void getVisitList()
    {



    }

    public void setTitle(int type, int iCount) {
        switch (type) {
            case HomeScreen.AlertsChooser.TYPE_PLOT_FOLLOWUP:
                actionBar.setTitle(getString(R.string.Plot_Follow_up));
                break;
            case HomeScreen.AlertsChooser.TYPE_PLOT_VISITS:
                actionBar.setTitle(getString(R.string.Visits));
                break;
            case HomeScreen.AlertsChooser.TYPE_PLOT_MISSING_TREES:
                actionBar.setTitle(getString(R.string.Missing_trees));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onItemSelected(int position) {
//        Toast.makeText(getApplicationContext(), alertsPlotInfoList.get(position).getPlotCode(), Toast.LENGTH_LONG).show();
//    }
}
