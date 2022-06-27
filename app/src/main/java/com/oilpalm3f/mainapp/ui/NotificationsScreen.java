package com.oilpalm3f.mainapp.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataSyncHelper;
import com.oilpalm3f.mainapp.dbmodels.Alerts;
import com.oilpalm3f.mainapp.uihelper.ProgressBar;
import com.oilpalm3f.mainapp.utils.UiUtils;

import java.util.Collections;
import java.util.List;

import static com.oilpalm3f.mainapp.datasync.helpers.DataSyncHelper.refreshtableNamesList;
import static com.oilpalm3f.mainapp.datasync.helpers.DataSyncHelper.refreshtransactionsDataMap;

//Notifications Screen
public class NotificationsScreen extends AppCompatActivity {
    public static final String LOG_TAG = NotificationsScreen.class.getName();
    private Button refreshBtn;
    private RecyclerView notiRecyclerView;
    private NotificationDisplayAdapter notificationDisplayAdapter;
    private LinearLayoutManager layoutManager;
    private DataAccessHandler dataAccessHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        notiRecyclerView = (RecyclerView) findViewById(R.id.notificationRecyclerView);
        refreshBtn = (Button) findViewById(R.id.refreshBtn);
        layoutManager = new LinearLayoutManager(this);
        notiRecyclerView.setLayoutManager(layoutManager);

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSyncHelper.getAlertsData(NotificationsScreen.this, new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        if (success) {
                            renderNotifications();
                        } else {
                            //UiUtils.showCustomToastMessage("Error while getting alerts Data", NotificationsScreen.this, 1);
                        }
                    }
                });
            }
        });

       dataAccessHandler = new DataAccessHandler(this);

        renderNotifications();

        updateNotificationStatus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateNotificationStatus() {
        dataAccessHandler.upNotificationStatus();

        List<Alerts> dataToSendCloud = (List<Alerts>) dataAccessHandler.getAlertsDetails(Queries.getInstance()
                .getAlertsDetailsQueryToSendCloud(), 1, true);

        if (dataToSendCloud == null || dataToSendCloud.isEmpty()) {
            return;
        }

        DataSyncHelper.reverseSyncTransCount = 0;
        DataSyncHelper.transactionsCheck = 0;
        refreshtableNamesList.clear();
        refreshtransactionsDataMap.clear();
        refreshtableNamesList.add(DatabaseKeys.TABLE_ALERTS);
        refreshtransactionsDataMap.put(DatabaseKeys.TABLE_ALERTS, dataToSendCloud);

        DataSyncHelper.postTransactionsDataToCloud(this, DatabaseKeys.TABLE_ALERTS, dataAccessHandler, new ApplicationThread.OnComplete() {
            @Override
            public void execute(boolean success, Object result, String msg) {
                super.execute(success, result, msg);
                refreshtableNamesList.clear();
                refreshtransactionsDataMap.clear();
            }
        });

    }

    private void renderNotifications() {

        ProgressBar.showProgressBar(this, "Please wait...");
        ApplicationThread.bgndPost(LOG_TAG, "", new Runnable() {
            @Override
            public void run() {
                ProgressBar.hideProgressBar();
                final List<Alerts> alertsList = (List<Alerts>) dataAccessHandler.getAlertsDetails(Queries.getInstance().getAlertsDetailsQueryToRender(), 1, false);

               Collections.reverse(alertsList);
               ApplicationThread.uiPost(LOG_TAG, "", new Runnable() {
                    @Override
                    public void run() {
                        if (null != alertsList && !alertsList.isEmpty()) {
                            notificationDisplayAdapter = new NotificationDisplayAdapter(alertsList);
                            notiRecyclerView.setAdapter(notificationDisplayAdapter);
                        }
                    }
                });
            }
        });
    }

}
