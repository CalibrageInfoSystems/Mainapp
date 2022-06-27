package com.oilpalm3f.mainapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.oilpalm3f.mainapp.ExpandableListview.HeaderCategory;
import com.oilpalm3f.mainapp.ExpandableListview.HeaderCategoryAdapter;
import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.datasync.helpers.DataSyncHelper;
import com.oilpalm3f.mainapp.dbmodels.KrasDataToDisplay;
import com.oilpalm3f.mainapp.dbmodels.UserDetails;
import com.oilpalm3f.mainapp.helper.PrefUtil;
import com.oilpalm3f.mainapp.kras.KraAdapterData;
import com.oilpalm3f.mainapp.kras.KraItemHeader;
import com.oilpalm3f.mainapp.kras.KraItemViewBinder;
import com.oilpalm3f.mainapp.kras.KrasItemHeaderViewBinder;
import com.oilpalm3f.mainapp.kras.StickyHeaderViewAdapter;
import com.oilpalm3f.mainapp.uihelper.ProgressBar;
import com.oilpalm3f.mainapp.utils.UiUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.oilpalm3f.mainapp.datasync.helpers.DataManager.USER_DETAILS;


//Displaying KRA(User Targets)
public class KrasDisplayScreen extends AppCompatActivity {
    public static final String LOG_TAG = KrasDisplayScreen.class.getName();
    LinkedHashMap<String, List<KrasDataToDisplay>> krasListToDisplay = null;
    private RecyclerView rvKrasList;
    private StickyHeaderViewAdapter krasAdapter;
    private LinearLayoutManager layoutManager;
    private Button refreshBtn;
    private DataAccessHandler dataAccessHandler;
    private LinearLayout usersLinearLayout;
    private Spinner usersSpinner;
    private List<UserDetails> userDetailsList = null;
    protected boolean userSelect = true;
    final List<HeaderCategory> kraHeaderCategories = new ArrayList<>();
    private HeaderCategoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kras_display_screen);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("KrasDisplayScreen");

        rvKrasList =  findViewById(R.id.rvKras);
        layoutManager = new LinearLayoutManager(this);
        refreshBtn = findViewById(R.id.refreshBtn);
        usersLinearLayout =  findViewById(R.id.usersLinearLayout);
        usersSpinner =  findViewById(R.id.userNameSpin);
        usersSpinner.setSelection(0, false);

        dataAccessHandler = new DataAccessHandler(KrasDisplayScreen.this);


        List userNames = new ArrayList();

        String username = "";

        username = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getusernamequery(CommonConstants.USER_ID));

        userNames.add(username);

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(KrasDisplayScreen.this, android.R.layout.simple_spinner_item,
                userNames.toArray());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usersSpinner.setAdapter(spinnerArrayAdapter);

        updateUI(Integer.parseInt(CommonConstants.USER_ID));
        //updateUI(1);
        startDataSync();



        refreshBtn.setOnClickListener(v -> startDataSync());

        //updateUI(0);
    }


    public void bindUserInfo() {
        final UserDetails userDetails = (UserDetails) DataManager.getInstance().getDataFromManager(USER_DETAILS);

        userDetailsList = (List<UserDetails>) dataAccessHandler.getUserDetails(Queries.getInstance().getUserDetailsForKrasQuery(Integer.parseInt(userDetails.getId())), 1);

        List userNames = new ArrayList();
        //userNames.add("All");
//        for (int i = 0; i < userDetailsList.size(); i++) {
//            userNames.add(userDetailsList.get(i).getUserName());
//        }

//        String username = "";
//
//        username = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getusernamequery(CommonConstants.USER_ID));
//
//        userNames.add(username);

//        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(KrasDisplayScreen.this, android.R.layout.simple_spinner_item,
//                userNames.toArray());
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        usersSpinner.setAdapter(spinnerArrayAdapter);

        usersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//                if (userSelect) {
//                    userSelect = false;
//                } else {
//                    if (i == 0) {
//                        krasListToDisplay.clear();
//                        kraHeaderCategories.clear();
//                        rvKrasList.setVisibility(View.GONE);
//                        updateUI(0);
//                    } else {
                        krasListToDisplay.clear();
                        kraHeaderCategories.clear();
                        rvKrasList.setVisibility(View.GONE);
                        updateUI(Integer.parseInt(CommonConstants.USER_ID));
                        //updateUI(1);
//                    }
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public List<KraAdapterData> getKrasDisplayItems() {

        List<KraAdapterData> krasListWithStickyHeader = new ArrayList<>();
        for (Object o : krasListToDisplay.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String key = (String) entry.getKey();
            List<KrasDataToDisplay> value = (List<KrasDataToDisplay>) entry.getValue();
            HeaderCategory kraHeader = new HeaderCategory(
                    value.get(0).getkRACode()
                            + "\n" + value.get(0).getkRAName()
                            + "\n" + value.get(0).getAnnualTarget()
                            + "\n" + value.get(0).getuOM()
                            + "\n" + value.get(0).getAnnualAchievedTarget()
                            + "\n" + value.get(0).getuOM(), value);
            System.out.println("Key = " + key + ", Value = " + value);
            kraHeaderCategories.add(kraHeader);
            krasListWithStickyHeader.add(new KraItemHeader(value));
            krasListWithStickyHeader.addAll(KraAdapterData.createKrasDisplayList(value));
        }
        return krasListWithStickyHeader;
    }

    private void updateUI(int userId) {
        krasListToDisplay = dataAccessHandler.getKrasDataToDisplay(Queries.getInstance().getKRAsDisplayQuery(userId));
        final List<KraAdapterData> krasListWithStickyHeader = getKrasDisplayItems();
        Log.v(LOG_TAG, "@@@ kras data " + krasListToDisplay);
        ApplicationThread.uiPost(LOG_TAG, "### populateList, updating list", new Runnable() {
            @Override
            public void run() {
                if (krasListWithStickyHeader != null && krasListWithStickyHeader.size() > 0) {
                    rvKrasList.setVisibility(View.VISIBLE);
                    /*krasAdapter = new StickyHeaderViewAdapter(krasListWithStickyHeader, KrasDisplayScreen.this)
                            .RegisterItemType(new KrasItemHeaderViewBinder())
                            .RegisterItemType(new KraItemViewBinder());*/

                    //rvKrasList.setAdapter(krasAdapter);
                    mAdapter = new HeaderCategoryAdapter(KrasDisplayScreen.this, kraHeaderCategories);
                    rvKrasList.setAdapter(mAdapter);
                    rvKrasList.setLayoutManager(layoutManager);
                } else {
                    rvKrasList.setVisibility(View.GONE);
                }
            }
        });
    }

    public void startDataSync() {
        if (CommonUtils.isNetworkAvailable(this)) {
            krasListToDisplay.clear();
            kraHeaderCategories.clear();
            rvKrasList.setVisibility(View.GONE);
            DataSyncHelper.performMasterSync(this, PrefUtil.getBool(this, CommonConstants.IS_MASTER_SYNC_SUCCESS), new ApplicationThread.OnComplete() {
                @Override
                public void execute(boolean success, Object result, String msg) {
                    ProgressBar.hideProgressBar();
                    if (success) {
                        ApplicationThread.uiPost(LOG_TAG, "master sync message", new Runnable() {
                            @Override
                            public void run() {

                                updateUI(Integer.parseInt(CommonConstants.USER_ID));
                                //updateUI(1);
                                bindUserInfo();
                                UiUtils.showCustomToastMessage("Data updated", KrasDisplayScreen.this, 0);
                            }
                        });
                    } else {
                        Log.v(LOG_TAG, "@@@ Master sync failed " + msg);
                        ApplicationThread.uiPost(LOG_TAG, "master sync message", new Runnable() {
                            @Override
                            public void run() {
                                UiUtils.showCustomToastMessage("Data syncing failed", KrasDisplayScreen.this, 1);
                            }
                        });
                    }
                }
            });
        } else {
            UiUtils.showCustomToastMessage("Internet is not available", KrasDisplayScreen.this, 1);

        }
    }

}
