package com.oilpalm3f.mainapp.cropmaintenance;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.UpdateUiListener;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.FiscalDate;
import com.oilpalm3f.mainapp.conversion.PalmDetailsEditListener;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.WhiteFlyAssessment;
import com.oilpalm3f.mainapp.dbmodels.YieldAssessment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

//Used to display Whitefly years
public class WhiteFlyFragment extends Fragment implements View.OnClickListener, WhiteFieldFragment.onDataSelectedListener, PalmDetailsEditListener, UpdateUiListener {
    private UpdateUiListener updateUiListener;
    private ActionBar actionBar;
    Button secnodpreviousfyBtn, previousfyBtn, currentfyBtn, saveBtn;
    public ArrayList<WhiteFlyAssessment> secondPreviousFYWhiteFlyList = new ArrayList<>();
    public ArrayList<WhiteFlyAssessment> previousFYWhiteFlyList = new ArrayList<>();
    public ArrayList<WhiteFlyAssessment> currentFYWhiteFlyList = new ArrayList<>();
    public int year = 0;
    Calendar calendar;

    public String currentFYStr, previousFYStr, secondpreviousFYStr;
    public String currentYearStr, previousYearStr, secondpreviousYearStr;

    private Button historyBtn;
    private ArrayList<WhiteFlyAssessment> whiteflylastvisitdatamap;
    DataAccessHandler dataAccessHandler;


    public WhiteFlyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_white_fly, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("WhiteFly");

        dataAccessHandler=new DataAccessHandler(getContext());

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

        secnodpreviousfyBtn = (Button) view.findViewById(R.id.secnodpreviousfy);
        previousfyBtn = (Button) view.findViewById(R.id.previousfy);
        currentfyBtn = (Button) view.findViewById(R.id.currentfy);
        saveBtn = (Button) view.findViewById(R.id.saveBtn);

        secnodpreviousfyBtn.setText(secondpreviousFYStr + "");
        previousfyBtn.setText(previousFYStr + "");
        currentfyBtn.setText(currentFYStr + "");

        historyBtn = view.findViewById(R.id.whiteflylastvisitdataBtn);

        DataAccessHandler dataAccessHandler = new DataAccessHandler(getActivity());
        String secondpreviousyeardata = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getsecondpreviousyearWhiteFlyCount(secondpreviousYearStr));
        String previousyeardata = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getpreviousyearWhiteFlyCount(previousYearStr));
        if(secondpreviousyeardata.length()>0){
            if(Integer.parseInt(secondpreviousyeardata)>0){
                secnodpreviousfyBtn.setEnabled(false);
                secnodpreviousfyBtn.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        }

        if(previousyeardata.length()>0){
            if(Integer.parseInt(previousyeardata)>0){
                previousfyBtn.setEnabled(false);
                previousfyBtn.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        }


        secnodpreviousfyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = Integer.parseInt(secondpreviousYearStr);
               // secondPreviousFYWhiteFlyList.clear();
                WhiteFieldFragment multiEntryDialogFragment = new WhiteFieldFragment();
                multiEntryDialogFragment.setOnDataSelectedListener(WhiteFlyFragment.this);
                Bundle inpuptBundle = new Bundle();
                inpuptBundle.putInt("type", Integer.parseInt(secondpreviousYearStr));
                multiEntryDialogFragment.setArguments(inpuptBundle);
                FragmentManager mFragmentManager = getChildFragmentManager();
                multiEntryDialogFragment.show(mFragmentManager, "fragment_edit_name");
            }
        });

        previousfyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = Integer.parseInt(previousYearStr);
              //  previousFYWhiteFlyList.clear();
                WhiteFieldFragment multiEntryDialogFragment = new WhiteFieldFragment();
                multiEntryDialogFragment.setOnDataSelectedListener(WhiteFlyFragment.this);
                Bundle inpuptBundle = new Bundle();
                inpuptBundle.putInt("type", Integer.parseInt(previousYearStr));
                multiEntryDialogFragment.setArguments(inpuptBundle);
                FragmentManager mFragmentManager = getChildFragmentManager();
                multiEntryDialogFragment.show(mFragmentManager, "fragment_edit_name");

            }
        });

        currentfyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = Integer.parseInt(currentYearStr);;
              //  currentFYWhiteFlyList.clear();
                WhiteFieldFragment multiEntryDialogFragment = new WhiteFieldFragment();
                multiEntryDialogFragment.setOnDataSelectedListener(WhiteFlyFragment.this);
                Bundle inpuptBundle = new Bundle();
                inpuptBundle.putInt("type", Integer.parseInt(currentYearStr));
                multiEntryDialogFragment.setArguments(inpuptBundle);
                FragmentManager mFragmentManager = getChildFragmentManager();
                multiEntryDialogFragment.show(mFragmentManager, "fragment_edit_name");

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               secondPreviousFYWhiteFlyList = (List<WhiteFlyAssessment>) DataManager.getInstance().getDataFromManager(DataManager.WHITE_FLY);
//                previousFYWhiteFlyList = (List<WhiteFlyAssessment>) DataManager.getInstance().getDataFromManager(DataManager.WHITE_FLY_18);
//                 currentFYWhiteFlyList = (List<WhiteFlyAssessment>) DataManager.getInstance().getDataFromManager(DataManager.WHITE_FLY_19);
                // if (year == 2018 && secondPreviousFYWhiteFlyList.size()>0){
                if ( secondPreviousFYWhiteFlyList.size()>0){
                    DataManager.getInstance().addData(DataManager.WHITE_FLY_18, secondPreviousFYWhiteFlyList);
                    // }else if(year == 2019 && previousFYWhiteFlyList.size()>0){
                }
                if(previousFYWhiteFlyList.size()>0){
                    DataManager.getInstance().addData(DataManager.WHITE_FLY_19, previousFYWhiteFlyList);
                    // }else if(year == 2020 && currentFYWhiteFlyList.size()>0){
                }
                if(currentFYWhiteFlyList.size()>0){
                    DataManager.getInstance().addData(DataManager.WHITE_FLY, currentFYWhiteFlyList);
                }
                updateUiListener.updateUserInterface(0);
                getFragmentManager().popBackStack();
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(getContext());
            }
        });

        return view;
    }

    public void showDialog(Context activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.whiteflylastvisteddata);

        Toolbar titleToolbar;
        titleToolbar = (Toolbar) dialog.findViewById(R.id.titleToolbar);
        titleToolbar.setTitle("WhiteFly Assessment History");
        titleToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        WhiteflyVisitedDataAdapter whiteflyVisitedDataAdapter;

        LinearLayout whiteflymainlyt = (LinearLayout) dialog.findViewById(R.id.whiteflymainlyt);


        RecyclerView whiteflyrcv = (RecyclerView) dialog.findViewById(R.id.whiteflyrcv);
        TextView norecords = (TextView) dialog.findViewById(R.id.whiteflynorecord_tv);

        String lastVisitCode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getLatestCropMaintanaceHistoryCode(CommonConstants.PLOT_CODE));
        whiteflylastvisitdatamap = (ArrayList<WhiteFlyAssessment>) dataAccessHandler.getWhiteData(Queries.getInstance().getRecommndCropMaintenanceHistoryDataforYieldandwhitefly(lastVisitCode, DatabaseKeys.TABLE_WHITE), 1);

        if (whiteflylastvisitdatamap.size() > 0){
            norecords.setVisibility(View.GONE);
            whiteflymainlyt.setVisibility(View.VISIBLE);

            whiteflyVisitedDataAdapter = new WhiteflyVisitedDataAdapter(getActivity(), whiteflylastvisitdatamap,dataAccessHandler);
            whiteflyrcv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            whiteflyrcv.setAdapter(whiteflyVisitedDataAdapter);

        }else{
            whiteflymainlyt.setVisibility(View.GONE);
            norecords.setVisibility(View.VISIBLE);
        }

        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 500);
    }


    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void updateUserInterface(int refreshPosition) {

    }

    @Override
    public void onEditClicked(int position) {

    }

    @Override
    public void onDataSelected(int type, Bundle bundle) {
        if (currentFYWhiteFlyList == null || currentFYWhiteFlyList.isEmpty()) {
            currentFYWhiteFlyList = new ArrayList<>();
        }

        if (secondPreviousFYWhiteFlyList == null || secondPreviousFYWhiteFlyList.isEmpty()) {
            secondPreviousFYWhiteFlyList = new ArrayList<>();
        }

        if (previousFYWhiteFlyList == null || previousFYWhiteFlyList.isEmpty()) {
            previousFYWhiteFlyList = new ArrayList<>();
        }


        if (bundle.getInt("Year") == Integer.parseInt(secondpreviousYearStr) && bundle != null){
           // secondPreviousFYWhiteFlyList.clear();
            secondPreviousFYWhiteFlyList.add(new WhiteFlyAssessment(bundle.getString("Question"),
                    bundle.getString("Answer"),
                    bundle.getString("Value"),
                    bundle.getInt("Year")));
        }else if (bundle.getInt("Year") == Integer.parseInt(previousYearStr) && bundle != null){
           // previousFYWhiteFlyList.clear();
            previousFYWhiteFlyList.add(new WhiteFlyAssessment(bundle.getString("Question"),
                    bundle.getString("Answer"),
                    bundle.getString("Value"),
                    bundle.getInt("Year")));
        }else if (bundle != null){
           // currentFYWhiteFlyList.clear();
            currentFYWhiteFlyList.add(new WhiteFlyAssessment(bundle.getString("Question"),
                    bundle.getString("Answer"),
                    bundle.getString("Value"),
                    bundle.getInt("Year")));
        }

        saveBtn.setVisibility(View.VISIBLE);
    }
}
