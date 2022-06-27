package com.oilpalm3f.mainapp.transportservice;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.cropmaintenance.YieldFragment;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.dbmodels.BasicFarmerDetails;
import com.oilpalm3f.mainapp.dbmodels.Farmer;
import com.oilpalm3f.mainapp.dbmodels.TransportServiceQuestioner;
import com.oilpalm3f.mainapp.farmersearch.FarmerDetailsRecyclerAdapter;
import com.oilpalm3f.mainapp.farmersearch.SearchFarmerScreen;
import com.oilpalm3f.mainapp.ui.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class farmerDropDownFragment extends DialogFragment implements RecyclerItemClickListener {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_farmers_card_view, container, false);
        dataAccessHandler = new DataAccessHandler(getContext());
        String farmerfCount = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().queryFarmersCount());



        progress = (ProgressBar)v. findViewById(R.id.progress);
        farmerDetailsRecyclerAdapter = new FarmerDetailsRecyclerAdapter(getContext(), mFarmersList);




        farmlandLVMembers = (RecyclerView)v.findViewById(R.id.lv_farmerlanddetails);

        mEtSearch = (EditText) v.findViewById(R.id.et_search);
        mIVClear = (ImageView) v.findViewById(R.id.iv_clear);
        tvNorecords = (TextView) v.findViewById(R.id.no_records);
        mIVClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearch = false;
                mFarmersList.clear();
                mEtSearch.setText("");
            }
        });
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        farmlandLVMembers.setLayoutManager(layoutManager);
        farmlandLVMembers.setAdapter(farmerDetailsRecyclerAdapter);
        farmerDetailsRecyclerAdapter.setRecyclerItemClickListener(this);
        mEtSearch.addTextChangedListener(mTextWatcher);
        farmlandLVMembers.addOnScrollListener(recyclerViewOnScrollListener);


        getAllUsers();


        return v;

    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //
        }

        @Override
        public void afterTextChanged(final Editable s) {
            offset = 0;
            ApplicationThread.uiPost(LOG_TAG, "search", new Runnable() {
                @Override
                public void run() {
                    doSearch(s.toString());
                    if (s.toString().length() > 0) {
                        mIVClear.setVisibility(View.VISIBLE);
                    } else {
                        mIVClear.setVisibility(View.GONE);
                    }
                }
            }, 100);
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
//                        Toast.makeText(SearchFarmerScreen.this, "No more items", Toast.LENGTH_SHORT).show();
                    } else if (isSearch){
                        isLoading = true;
                        offset = 0;
                        getAllUsers();
                    }else {
                        isLoading = true;
                        offset = offset + LIMIT;
                        getAllUsers();
                    }

                }
            }
        }
    };

    public void doSearch(String searchQuery) {
        offset = 0;
        hasMoreItems = true;
        if (!TextUtils.isEmpty(searchQuery)) {
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
                dataAccessHandler.getFarmerDetailsForSearchYield(searchKey, offset, LIMIT,
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
                                    mFarmersList.addAll(farmerDetails);
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
                                            //  setTile(getString(R.string.farmer_list));
                                        } else {
                                            //setTile(getString(R.string.farmer_list) + "("+mFarmersList.size()+")");
                                            tvNorecords.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }

                        }));

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        if(window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = 600;
        params.height = 600;
    }



    @Override
    public void onItemSelected(int position) {

        Farmer selectedFarmer = (Farmer) dataAccessHandler.getSelectedFarmerData(Queries.getInstance().getSelectedFarmer(mFarmersList.get(position).getFarmerCode()), 0);
        CommonConstants.FarmerCodeForYield=selectedFarmer.getCode();
        Log.v("@@@FFF","Far"+CommonConstants.FarmerCodeForYield);

        TransportFarmerFragment.farmerSelectionAns.setText(selectedFarmer.getCode());

        dismiss();
    }

}