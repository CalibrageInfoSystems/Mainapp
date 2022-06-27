package com.oilpalm3f.mainapp.areaextension;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.dbmodels.Referrals;
import com.oilpalm3f.mainapp.utils.UiUtils;

/**
 * A simple {@link Fragment} subclass.
 */

//Used to fill the Referral Details
public class  ReferralsDialogFragment extends DialogFragment {

    private EditText referralEdt, referralMobileNoEdt, villageNameEdt, mandalNameEdt;

    public ReferralsDialogFragment() {

    }

    private AddReferralInfoListener onReferralInfoAddedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View promptView = inflater.inflate(R.layout.referral_activity, null);
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        promptView.setMinimumWidth((int) (displayRectangle.width() * 0.7f));

        referralEdt = (EditText) promptView.findViewById(R.id.referral_edt);
        villageNameEdt = (EditText) promptView.findViewById(R.id.villageName_edt);
        mandalNameEdt = (EditText) promptView.findViewById(R.id.mandalName_edt);
        referralMobileNoEdt = (EditText) promptView.findViewById(R.id.referralMobileNo_edt);

        final Button referralSaveBtn = (Button) promptView.findViewById(R.id.saveBtn);
        final Button cancelBtn = (Button) promptView.findViewById(R.id.cancelBtn);

        setDataToViews();

        referralSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String villageName = villageNameEdt.getText().toString();
                String mandalName = mandalNameEdt.getText().toString();
                String referralStr = referralEdt.getText().toString();
                String mobileNoStr = referralMobileNoEdt.getText().toString();
                if (TextUtils.isEmpty(referralStr)) {
                    UiUtils.showCustomToastMessage("Please Enter Referral Name", getActivity(), 1);
                    return;
                } else if (TextUtils.isEmpty(mobileNoStr) || mobileNoStr.length() < 10) {
                    UiUtils.showCustomToastMessage("Please Enter Valid Mobile Number", getActivity(), 1);
                    return;
                } else if (TextUtils.isEmpty(villageName)) {
                    UiUtils.showCustomToastMessage("Please Enter Village", getActivity(), 1);
                    return;
                } else if (TextUtils.isEmpty(mandalName)) {
                    UiUtils.showCustomToastMessage("Please Enter Mandal", getActivity(), 1);
                    return;
                } else {
                    Referrals referralDataModel = new Referrals();
                    referralDataModel.setFarmername(referralStr);
                    referralDataModel.setContactnumber(mobileNoStr);
                    referralDataModel.setMandalname(mandalName);
                    referralDataModel.setVillageName(villageName);
                    if (getArguments() != null) {
                        onReferralInfoAddedListener.editReferralInfo(referralDataModel);
                    } else {
                        onReferralInfoAddedListener.addReferralInfo(referralDataModel);
                    }
                    getDialog().cancel();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();

            }
        });
        return promptView;
    }

    private void setDataToViews() {
        if (getArguments() != null) {
            Referrals dataModel = (Referrals) getArguments().getSerializable(CommonConstants.REFERRAL_DATA_MODEL);
            referralEdt.setText(dataModel.getFarmername());
            referralMobileNoEdt.setText(dataModel.getContactnumber());
            villageNameEdt.setText(dataModel.getVillageName());
            mandalNameEdt.setText(dataModel.getMandalname());
        }
    }

    public void setAddReferralListener(AddReferralInfoListener onReferralInfoAddedListener) {
        this.onReferralInfoAddedListener = onReferralInfoAddedListener;
    }

    public interface AddReferralInfoListener {
        void addReferralInfo(Referrals referralDataModel);
        void editReferralInfo(Referrals referralDataModel);
    }

}
