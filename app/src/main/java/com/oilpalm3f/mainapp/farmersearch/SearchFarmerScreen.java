package com.oilpalm3f.mainapp.farmersearch;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.dbmodels.Plantation;
import com.oilpalm3f.mainapp.prospectiveFarmers.NewProspectiveFarmers;
import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.RegistrationFlowScreen;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUiUtils;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.Address;
import com.oilpalm3f.mainapp.dbmodels.Farmer;
import com.oilpalm3f.mainapp.dbmodels.FileRepository;
import com.oilpalm3f.mainapp.dbmodels.BasicFarmerDetails;
import com.oilpalm3f.mainapp.ui.OilPalmBaseActivity;
import com.oilpalm3f.mainapp.ui.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;


//Search functionality in farmer screen
public class  SearchFarmerScreen extends OilPalmBaseActivity implements RecyclerItemClickListener {

    public static final int LIMIT = 30;
    private static final String LOG_TAG = SearchFarmerScreen.class.getSimpleName();
    String searchKey = "";
    int offset;
    private DataAccessHandler dataAccessHandler;
    private RecyclerView farmlandLVMembers;
    private EditText mEtSearch;
    private ImageView mIVClear;
    private TextView tvNorecords;
    private ProgressBar progress;
    private List<BasicFarmerDetails> mFarmersList = new ArrayList<>();
    private FarmerDetailsRecyclerAdapter farmerDetailsRecyclerAdapter;
    private LinearLayoutManager layoutManager;
    private boolean isLoading = false;
    private boolean hasMoreItems = true;
    public static  boolean FarmerImage = false ;
    private boolean isSearch = false;
    List<BasicFarmerDetails> farmerDetails;

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            Log.d("WhatisinSearch", "is :"+ s);
            //
            offset = 0;
            ApplicationThread.uiPost(LOG_TAG, "search", new Runnable() {
                @Override
                public void run() {
                    doSearch(s.toString().trim());
                    if (s.toString().length() > 0) {
                        mIVClear.setVisibility(View.VISIBLE);
                    } else {
                        mIVClear.setVisibility(View.GONE);
                    }
                }
            }, 100);
        }

        @Override
        public void afterTextChanged(final Editable s) {

        }
    };

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
                        Toast.makeText(SearchFarmerScreen.this, "No more items", Toast.LENGTH_SHORT).show();
                    } else if (isSearch){
                        isLoading = true;
                        offset = 0;
                        getAllUsers();
                    }else {
                        isLoading = true;
                        offset = offset + LIMIT;
                        getAllUsers();
                        //recyclerView.getLayoutManager().scrollToPosition(0);

                    }

                }
            }
        }
    };


    @Override
    public void Initialize() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View parentView = inflater.inflate(R.layout.activity_todolist, null);
        baseLayout.addView(parentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dataAccessHandler = new DataAccessHandler(this);

        String farmerfCount = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().queryFarmersCount());
        setTile(getString(R.string.farmer_list) + "("+farmerfCount+")");

        progress = (ProgressBar) findViewById(R.id.progress);
        farmerDetailsRecyclerAdapter = new FarmerDetailsRecyclerAdapter(SearchFarmerScreen.this, mFarmersList);

        initUI();
        getAllUsers();

        CommonUtils.currentActivity = this;
    }
    private void initUI() {
        farmlandLVMembers = (RecyclerView) findViewById(R.id.lv_farmerlanddetails);

        mEtSearch = (EditText) findViewById(R.id.et_search);
        mIVClear = (ImageView) findViewById(R.id.iv_clear);
        tvNorecords = (TextView) findViewById(R.id.no_records);

        mIVClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearch = false;
                mFarmersList.clear();
                mEtSearch.setText("");
            }
        });
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        farmlandLVMembers.setLayoutManager(layoutManager);
        farmlandLVMembers.setAdapter(farmerDetailsRecyclerAdapter);
        farmerDetailsRecyclerAdapter.setRecyclerItemClickListener(this);
        mEtSearch.addTextChangedListener(mTextWatcher);
        farmlandLVMembers.addOnScrollListener(recyclerViewOnScrollListener);
    }

    public void doSearch(String searchQuery) {
        Log.d("DoSearchQuery", "is :" +  searchQuery);
        offset = 0;
        hasMoreItems = true;
        if (searchQuery !=null &  !TextUtils.isEmpty(searchQuery)  & searchQuery.length()  > 0) {

            offset = 0;
            isSearch = true;
            searchKey = searchQuery.trim();
            getAllUsers();
        } else {
            searchKey = "";
            isSearch = false;
            getAllUsers();
        }
    }

    public void getAllUsers() {

        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
//        ProgressBar.showProgressBar(this, "Please wait...");
        ApplicationThread.bgndPost(LOG_TAG, "getting transactions data", () ->
                dataAccessHandler.getFarmerDetailsForSearch(searchKey, offset, LIMIT,
                        new ApplicationThread.OnComplete<List<BasicFarmerDetails>>() {
            @Override
            public void execute(boolean success, final List<BasicFarmerDetails> farmerDetails, String msg) {
//                        ProgressBar.hideProgressBar();
                                isLoading = false;
                                if (farmerDetails.isEmpty()) {
                                    hasMoreItems = false;
                                }

                                if (offset == 0 && isSearch) {
                                    mFarmersList.clear();
                                    mFarmersList.addAll(farmerDetails);

                                } else {

                                    if(farmerDetails != null  & farmerDetails.size()  > 0)
                                        mFarmersList.clear();

                                    mFarmersList.addAll(farmerDetails);
                                    //farmlandLVMembers.getLayoutManager().scrollToPosition(0);
                                }
                                ApplicationThread.uiPost(LOG_TAG, "update ui", new Runnable() {
                                    @Override
                                    public void run() {
                                        progress.setVisibility(View.GONE);
                                        int farmersSize = farmerDetails.size();
                                        Log.v(LOG_TAG, "data size " + farmersSize);
                                        farmerDetailsRecyclerAdapter.addItems(mFarmersList);
                                        if (farmerDetailsRecyclerAdapter.getItemCount() == 0) {
                                            tvNorecords.setVisibility(View.VISIBLE);
                                            setTile(getString(R.string.farmer_list));
                                        } else {
                                            setTile(getString(R.string.farmer_list) + "("+mFarmersList.size()+")");
                                            tvNorecords.setVisibility(View.GONE);
                                            farmlandLVMembers.getLayoutManager().scrollToPosition(0);

                                        }
                                    }
                                });
                            }

        }));

    }



        @Override
    public void onItemSelected(int position) {
        moveToNextFlow(position);
    }

    public void moveToNextFlow(final int position) {
        DataManager.getInstance().addData(DataManager.IS_FARMER_DATA_UPDATED, false);
        DataManager.getInstance().addData(DataManager.IS_PLOTS_DATA_UPDATED, false);
        CommonUiUtils.resetPrevRegData();
        Farmer selectedFarmer = (Farmer) dataAccessHandler.getSelectedFarmerData(Queries.getInstance().getSelectedFarmer(mFarmersList.get(position).getFarmerCode()), 0);
        Address selectedFarmerAddress = (Address) dataAccessHandler.getSelectedFarmerAddress(Queries.getInstance().getSelectedFarmerAddress(selectedFarmer.getAddresscode()), 0);
        FileRepository selectedFileRepository = dataAccessHandler.getSelectedFileRepository(Queries.getInstance().getSelectedFileRepositoryQuery(selectedFarmer.getCode(), 193));
        FileRepository selectedFileRepository_FarmerImage = dataAccessHandler.getSelectedFileRepository(Queries.getInstance().getSelectedFileRepositoryQuery(selectedFarmer.getCode(), 193));

        if (null != selectedFarmer && selectedFarmerAddress != null) {
            CommonUiUtils.setGeoGraphicalData(selectedFarmer, this);
            CommonConstants.FARMER_CODE = selectedFarmer.getCode();
            DataManager.getInstance().addData(DataManager.FARMER_PERSONAL_DETAILS, selectedFarmer);
            DataManager.getInstance().addData(DataManager.FARMER_ADDRESS_DETAILS, selectedFarmerAddress);
            if(selectedFileRepository!=null) {
                DataManager.getInstance().addData(DataManager.FILE_REPOSITORY, selectedFileRepository);
            }
            if (selectedFileRepository_FarmerImage!=null){
                FarmerImage = true;
            }
        }

        if (CommonConstants.REGISTRATION_SCREEN_FROM.equalsIgnoreCase(CommonConstants.REGISTRATION_SCREEN_FROM_VPF)) {
            Intent intent = new Intent(SearchFarmerScreen.this, NewProspectiveFarmers.class);
            startActivity(intent);
        } else if (CommonUtils.isFromFollowUp() || CommonUtils.isFromConversion() || CommonUtils.isFromCropMaintenance()||
                CommonUtils.isPlotSplitFarmerPlots() || CommonUtils.isVisitRequests() || CommonUtils.isFromHarvesting())  {
            FragmentManager fm = getSupportFragmentManager();
            DisplayPlotsFragment displayPlotsFragment = DisplayPlotsFragment.newInstance(mFarmersList.get(position));
            displayPlotsFragment.show(fm, "displayPlotsFragment");
        } else {
            startActivity(new Intent(this, RegistrationFlowScreen.class));
        }
    }

}
