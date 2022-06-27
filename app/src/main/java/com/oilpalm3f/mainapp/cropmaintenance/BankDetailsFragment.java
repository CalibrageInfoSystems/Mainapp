package com.oilpalm3f.mainapp.cropmaintenance;



import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.UpdateUiListener;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.FarmerBank;


//Displaying Farmer Bank Details
public class BankDetailsFragment extends Fragment {

    private TextView bank_name_txt, branch_name_text, ifsc_code_text, account_holder_name_text, account_number_text;
    private View rootView;
    private Context mContext;
    private DataAccessHandler dataAccessHandler;
    private UpdateUiListener updateUiListener;
    private FarmerBank farmerBank = null;
    private ActionBar actionBar;
    private  Toolbar toolbar;

    public BankDetailsFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_bank_details, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Bank Details");
        initViews();
        return rootView;
    }

    private void initViews() {
        dataAccessHandler = new DataAccessHandler(getActivity());
        bank_name_txt = (TextView) rootView.findViewById(R.id.bank_name_txt);
        branch_name_text = (TextView) rootView.findViewById(R.id.branch_name_text);
        ifsc_code_text = (TextView) rootView.findViewById(R.id.ifsc_code_text);
        account_holder_name_text = (TextView) rootView.findViewById(R.id.account_holder_name_text);
        account_number_text = (TextView) rootView.findViewById(R.id.account_number_text);

        farmerBank = (FarmerBank) DataManager.getInstance().getDataFromManager(DataManager.FARMER_BANK_DETAILS);
        if(farmerBank==null) {
            farmerBank = (FarmerBank) dataAccessHandler.getSelectedFarmerBankData(Queries.getInstance().getFarmerBankData(CommonConstants.FARMER_CODE), 0);
        }
        if (null != farmerBank) {
            bindData();
        }
    }

    private void bindData() {

        CommonConstants.bankTypeId = String.valueOf(farmerBank.getBankid());
        String banktypecddmtId = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getTypecdDmtIdBank(CommonConstants.bankTypeId));
        String bankName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getTypecdDesc(banktypecddmtId));
        String branchName = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getBranchName(CommonConstants.bankTypeId));

        bank_name_txt.setText(bankName);
        branch_name_text.setText(branchName);
        account_holder_name_text.setText(farmerBank.getAccountholdername());
        account_number_text.setText(farmerBank.getAccountnumber());
        ifsc_code_text.setText(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getIfscCode(String.valueOf(farmerBank.getBankid()))));
    }
}
