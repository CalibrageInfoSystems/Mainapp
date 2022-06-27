package com.oilpalm3f.mainapp.cropmaintenance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.UpdateUiListener;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.conversion.PalmDetailsEditListener;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.Weed;
import com.oilpalm3f.mainapp.dbmodels.YieldAssessment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

//Used to enter Yeild details during crop maintenance
public class YieldFragment extends Fragment implements View.OnClickListener, PalmDetailsEditListener, UpdateUiListener {

    private Spinner mSpinner_parent, mSpinner_reason, mSpinner_supply_outside, mPaymentSpinner;
    private ArrayList<String> arrayList_parent, arrayList_outside, arrayList_reason, arrayList_paymentmode;
    private ArrayAdapter<String> arrayAdapter_parent, arrayAdapter_outside, arrayAdapter_reason, arrayAdapter_paymentmode;
    private FragmentActivity myContext;
    private UpdateUiListener updateUiListener;
    private ActionBar actionBar;
    Button saveBtn;
    int rb1 = 0;
    int rb2 = 0;
    int rb3 = 0;
    DataAccessHandler dataAccessHandler;
    private ArrayList<YieldAssessment> yieldArray = new ArrayList<>();
    String fName="",nFName="",nFcode="";


    YieldAssessment yieldAssesment;
    TextView question1, supplyingOutsideTv, offHighRateTv, factoryRepresentTv, mopaymentTv, question4, issueName, ofRadQues,
            detailedreasonTv, EfbbTv, monthTv, cpQuestion, reasonTv, commentsTv, howmuchTv;
  public   EditText offHighRateEt, factoryRepresentEt, issueEt, DetailedReasonEt, monthEt, commentsEt, howmuchEt;
  public static EditText EfbbEt;
    LinearLayout sellingOutside, supplyingOutSide, commonLayout, otherIssuesLayout, higherLayout, logisticsLayout, othersLayout;
    RadioGroup radiogup, opGroup, cpRadio;
    RadioButton yId, nId;
    CheckBox explainoilpalm, explaincompanyservice;
    int selectedId, opGroupId, cpGroupId;

    View view;
    SimpleDateFormat simpledatefrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    String currentTime = simpledatefrmt.format(new Date());

    private Button historyBtn;
    private ArrayList<YieldAssessment> yieldlastvisitdatamap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataAccessHandler=new DataAccessHandler(getContext());
        view = inflater.inflate(R.layout.fragment_yield, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Yield Assesement");

        mSpinner_reason = (Spinner) view.findViewById(R.id.spinner_reason);
        mSpinner_parent = (Spinner) view.findViewById(R.id.spinner_parent);
        mSpinner_supply_outside = (Spinner) view.findViewById(R.id.spinner_supply_outside);
        mPaymentSpinner = (Spinner) view.findViewById(R.id.paymentSpinner);


        supplyingOutSide = (LinearLayout) view.findViewById(R.id.supplyingOutSide);
        sellingOutside = (LinearLayout) view.findViewById(R.id.sellingOutside);
        otherIssuesLayout = (LinearLayout) view.findViewById(R.id.otherIssuesLayout);

        higherLayout = (LinearLayout) view.findViewById(R.id.higherLayout);
        logisticsLayout = (LinearLayout) view.findViewById(R.id.logisticsLayout);
        othersLayout = (LinearLayout) view.findViewById(R.id.othersLayout);

        explainoilpalm = (CheckBox) view.findViewById(R.id.explainoilpalm);
        explaincompanyservice = (CheckBox) view.findViewById(R.id.explaincompanyservice);

        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        question1 = (TextView) view.findViewById(R.id.question1);
        offHighRateTv = (TextView) view.findViewById(R.id.offHighRateTv);
        supplyingOutsideTv = (TextView) view.findViewById(R.id.supplyingOutsideTv);
        factoryRepresentTv = (TextView) view.findViewById(R.id.factoryRepresentTv);
        howmuchTv = (TextView) view.findViewById(R.id.howmuchTv);
        mopaymentTv = (TextView) view.findViewById(R.id.mopaymentTv);
        question4 = (TextView) view.findViewById(R.id.question4);
        issueName = (TextView) view.findViewById(R.id.issueName);
        ofRadQues = (TextView) view.findViewById(R.id.ofRadQues);
        detailedreasonTv = (TextView) view.findViewById(R.id.detailedreasonTv);
        EfbbTv = (TextView) view.findViewById(R.id.EfbbTv);
        monthTv = (TextView) view.findViewById(R.id.monthTv);
        cpQuestion = (TextView) view.findViewById(R.id.cpQuestion);
        reasonTv = (TextView) view.findViewById(R.id.reasonTv);
        commentsTv = (TextView) view.findViewById(R.id.commentsTv);

        offHighRateEt = (EditText) view.findViewById(R.id.offHighRateEt);
        factoryRepresentEt = (EditText) view.findViewById(R.id.factoryRepresentEt);
        issueEt = (EditText) view.findViewById(R.id.issueEt);
        DetailedReasonEt = (EditText) view.findViewById(R.id.DetailedReasonEt);
     //   farmerSpinner = (Spinner) view.findViewById(R.id.fbbEt);
        monthEt = (EditText) view.findViewById(R.id.monthEt);
        commentsEt = (EditText) view.findViewById(R.id.commentsEt);
        howmuchEt = (EditText) view.findViewById(R.id.howmuchEt);

        EfbbEt=(EditText)view.findViewById(R.id.EfbbEt);

        radiogup = (RadioGroup) view.findViewById(R.id.radiogup);
        opGroup = (RadioGroup) view.findViewById(R.id.opGroup);
        cpRadio = (RadioGroup) view.findViewById(R.id.cpRadio);


        yId = (RadioButton) view.findViewById(R.id.yId);
        nId = (RadioButton) view.findViewById(R.id.nId);

        historyBtn = view.findViewById(R.id.yieldlastvisitdataBtn);

        EfbbEt.setClickable(true);
        EfbbEt.setFocusable(false);


        arrayList_parent = new ArrayList<>();
        arrayList_parent.add("Select Reason");
        arrayList_parent.add("Whitefly");
        arrayList_parent.add("Supplying Outside");
        arrayList_parent.add("Selling in other person name");
        arrayList_parent.add("Other Issues");

        arrayAdapter_parent = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList_parent);
        mSpinner_parent.setAdapter(arrayAdapter_parent);

        arrayList_outside = new ArrayList<>();
        arrayList_outside.add("Higher Rate");
        arrayList_outside.add("Higher Rate and Spot Payment");
        arrayList_outside.add("Logistic");
        arrayList_outside.add("Other Issue");


        arrayAdapter_outside = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_outside);
        mSpinner_supply_outside.setAdapter(arrayAdapter_outside);

        arrayList_reason = new ArrayList<>();
        arrayList_reason.add("Select Reason");
        arrayList_reason.add("Bad Maintenance");
        arrayList_reason.add("Other Pest");
        arrayList_reason.add("Disease");
        arrayList_reason.add("Water Shortage");
        arrayList_reason.add("Absentee LandLord");
        arrayList_reason.add("Lease Change");
        arrayList_reason.add("Other");

        arrayAdapter_reason = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_reason);
        mSpinner_reason.setAdapter(arrayAdapter_reason);

        radiogup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yId) {
                    rb1 = 1;
                } else rb1 = 0;
            }
        });

        opGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yId) {
                    rb2 = 1;
                } else rb2 = 0;
            }
        });


        EfbbEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment df = new FarmersCardViewFragment();
                df.show(getActivity().getSupportFragmentManager(), "dialog");

            }
        });


        cpRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yId) {
                    rb3 = 1;
                } else rb3 = 0;
            }
        });

        mSpinner_parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    otherIssuesLayout.setVisibility(View.GONE);
                    sellingOutside.setVisibility(View.GONE);
                    supplyingOutSide.setVisibility(View.GONE);

                } else if (position == 2) {
                    supplyingOutSide.setVisibility(View.VISIBLE);
                    sellingOutside.setVisibility(View.GONE);
                    otherIssuesLayout.setVisibility(View.GONE);


                } else if (position == 3) {
                    sellingOutside.setVisibility(View.VISIBLE);
                    supplyingOutSide.setVisibility(View.GONE);
                    otherIssuesLayout.setVisibility(View.GONE);

                } else if (position == 4) {
                    otherIssuesLayout.setVisibility(View.VISIBLE);
                    sellingOutside.setVisibility(View.GONE);
                    supplyingOutSide.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mSpinner_supply_outside.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == 1) {
                    higherLayout.setVisibility(View.VISIBLE);
                    logisticsLayout.setVisibility(View.GONE);
                    othersLayout.setVisibility(View.GONE);

                } else if (position == 2) {
                    higherLayout.setVisibility(View.GONE);
                    logisticsLayout.setVisibility(View.VISIBLE);
                    othersLayout.setVisibility(View.GONE);

                } else if (position == 3) {
                    higherLayout.setVisibility(View.GONE);
                    logisticsLayout.setVisibility(View.GONE);
                    othersLayout.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrayList_paymentmode = new ArrayList<>();
        arrayList_paymentmode.add("Cash");
        arrayList_paymentmode.add("Bank Transfer");

        arrayAdapter_paymentmode = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_paymentmode);
        mPaymentSpinner.setAdapter(arrayAdapter_paymentmode);

        mPaymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //Cash Mode
                } else if (position == 1) {
                    // Bank transfer
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        LinkedHashMap obList = dataAccessHandler.getFarmerDetailsData(Queries.getInstance().getFilterFarmersWeedFly());


//        farmerSpinner.setAdapter(CommonUtilsNavigation.adapterHasMap(getContext(),"Farmer",obList));
//        farmerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(farmerSpinner.getSelectedItemPosition()!=0){
//
//                    fName=farmerSpinner.getSelectedItem().toString();
//                    String code=obList.keySet().toArray()[position-1].toString();
//                    Log.v("@@@F","FCODE"+code);
//                    ArrayList<String> aL = new ArrayList<>();
//                    nFcode=code;
//                    Log.v("@@@FF","FCODE"+nFcode);
//
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
       Log.v("@@@NFFF","FarmerNNN"+CommonConstants.FarmerCodeForYield);
     //   EfbbEt.setText(""+CommonConstants.FarmerCodeForYield);


        saveBtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           if (valid()) {
                                               yieldArray.clear();
                                               if (mSpinner_parent.getSelectedItemPosition() != -1) {
                                                   YieldAssessment yieldAssesment = new YieldAssessment();
                                                   yieldAssesment.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                                                   yieldAssesment.setQuestion(question1.getText().toString());
                                                   yieldAssesment.setAnswer("");
                                                   yieldAssesment.setValue(mSpinner_parent.getSelectedItem().toString());
                                                   yieldAssesment.setYear(2020);
                                                   yieldAssesment.setServerUpdatedStatus(0);
                                                   yieldArray.add(yieldAssesment);
                                                   getFragmentManager().popBackStack();
                                               }


                                               if (mSpinner_parent.getSelectedItem().toString().equals("Supplying Outside")) {


                                                   if (explainoilpalm.isChecked()) {
                                                       YieldAssessment yieldAssesment20 = new YieldAssessment();
                                                       yieldAssesment20.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                                                       yieldAssesment20.setQuestion(explainoilpalm.getText().toString());
                                                       yieldAssesment20.setAnswer("");
                                                       yieldAssesment20.setValue("Yes");
                                                       yieldAssesment20.setServerUpdatedStatus(0);
                                                       yieldAssesment20.setYear(2020);
                                                       yieldArray.add(yieldAssesment20);
                                                       getFragmentManager().popBackStack();
                                                   } else {
                                                       YieldAssessment yieldAssesment21 = new YieldAssessment();
                                                       yieldAssesment21.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                                                       yieldAssesment21.setQuestion(explainoilpalm.getText().toString());
                                                       yieldAssesment21.setAnswer("");
                                                       yieldAssesment21.setValue("No");
                                                       yieldAssesment21.setServerUpdatedStatus(0);
                                                       yieldAssesment21.setYear(2020);
                                                       yieldArray.add(yieldAssesment21);
                                                       getFragmentManager().popBackStack();
                                                   }

                                                   if (explaincompanyservice.isChecked()) {
                                                       YieldAssessment yieldAssesment22 = new YieldAssessment();
                                                       yieldAssesment22.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                                                       yieldAssesment22.setQuestion(explaincompanyservice.getText().toString());
                                                       yieldAssesment22.setAnswer("");
                                                       yieldAssesment22.setValue("Yes");
                                                       yieldAssesment22.setServerUpdatedStatus(0);
                                                       yieldAssesment22.setYear(2020);
                                                       yieldArray.add(yieldAssesment22);
                                                       getFragmentManager().popBackStack();
                                                   } else {
                                                       YieldAssessment yieldAssesment23 = new YieldAssessment();
                                                       yieldAssesment23.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                                                       yieldAssesment23.setQuestion(explaincompanyservice.getText().toString());
                                                       yieldAssesment23.setAnswer("");
                                                       yieldAssesment23.setValue("No");
                                                       yieldAssesment23.setServerUpdatedStatus(0);
                                                       yieldAssesment23.setYear(2020);
                                                       yieldArray.add(yieldAssesment23);
                                                       getFragmentManager().popBackStack();
                                                   }


                                                   if (mSpinner_supply_outside.getSelectedItem().toString().equals("Higher Rate")|| mSpinner_supply_outside.getSelectedItem().toString().equals("Higher Rate and Spot Payment")) {
                                                       YieldAssessment yieldAssesment1 = new YieldAssessment();
                                                       yieldAssesment1.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                                                       yieldAssesment1.setQuestion(supplyingOutsideTv.getText().toString());
                                                       yieldAssesment1.setAnswer("");
                                                       yieldAssesment1.setValue(mSpinner_supply_outside.getSelectedItem().toString());
                                                       yieldAssesment1.setServerUpdatedStatus(0);
                                                       yieldAssesment1.setYear(2020);
                                                       yieldArray.add(yieldAssesment1);
                                                       getFragmentManager().popBackStack();


                                                       if (!TextUtils.isEmpty(offHighRateEt.getText().toString())) {
                                                           YieldAssessment yieldAssesment2 = new YieldAssessment();
                                                           yieldAssesment2.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                                                           yieldAssesment2.setQuestion(offHighRateTv.getText().toString());
                                                           yieldAssesment2.setAnswer("");
                                                           yieldAssesment2.setValue(offHighRateEt.getText().toString());
                                                           yieldAssesment2.setServerUpdatedStatus(0);
                                                           yieldAssesment2.setYear(2020);
                                                           yieldArray.add(yieldAssesment2);
                                                           getFragmentManager().popBackStack();
                                                       }

                                                       if (!TextUtils.isEmpty(factoryRepresentEt.getText().toString())) {
                                                           YieldAssessment yieldAssesment3 = new YieldAssessment();
                                                           yieldAssesment3.setQuestion(factoryRepresentTv.getText().toString());
                                                           yieldAssesment3.setAnswer("");
                                                           yieldAssesment3.setValue(factoryRepresentEt.getText().toString());
                                                           yieldAssesment3.setServerUpdatedStatus(0);
                                                           yieldAssesment3.setYear(2020);
                                                           yieldArray.add(yieldAssesment3);
                                                           getFragmentManager().popBackStack();
                                                       }
                                                       if (!TextUtils.isEmpty(howmuchEt.getText().toString())) {
                                                           YieldAssessment yieldAssesment4 = new YieldAssessment();
                                                           yieldAssesment4.setQuestion(howmuchTv.getText().toString());
                                                           yieldAssesment4.setAnswer("");
                                                           yieldAssesment4.setValue(howmuchEt.getText().toString());
                                                           yieldAssesment4.setServerUpdatedStatus(0);
                                                           yieldAssesment4.setYear(2020);
                                                           yieldArray.add(yieldAssesment4);
                                                           getFragmentManager().popBackStack();
                                                       }

                                                       if (mPaymentSpinner.getSelectedItemPosition() >= 0) {
                                                           YieldAssessment yieldAssesment5 = new YieldAssessment();
                                                           yieldAssesment5.setQuestion(mopaymentTv.getText().toString());
                                                           yieldAssesment5.setAnswer("");
                                                           yieldAssesment5.setValue(mPaymentSpinner.getSelectedItem().toString());
                                                           yieldAssesment5.setServerUpdatedStatus(0);
                                                           yieldAssesment5.setYear(2020);
                                                           yieldArray.add(yieldAssesment5);
                                                           getFragmentManager().popBackStack();
                                                       }

                                                       if (rb1 == 1) {
                                                           YieldAssessment yieldAssesment6 = new YieldAssessment();
                                                           String value = "Yes";
                                                           yieldAssesment6.setQuestion(question4.getText().toString());
                                                           yieldAssesment6.setAnswer("");
                                                           yieldAssesment6.setValue(value);
                                                           yieldAssesment6.setServerUpdatedStatus(0);
                                                           yieldAssesment6.setYear(2020);
                                                           yieldArray.add(yieldAssesment6);
                                                           getFragmentManager().popBackStack();
                                                       } else {
                                                           YieldAssessment yieldAssesment7 = new YieldAssessment();
                                                           String value = "NO";
                                                           yieldAssesment7.setQuestion(question4.getText().toString());
                                                           yieldAssesment7.setAnswer("");
                                                           yieldAssesment7.setValue(value);
                                                           yieldAssesment7.setServerUpdatedStatus(0);
                                                           yieldAssesment7.setYear(2020);
                                                           yieldArray.add(yieldAssesment7);
                                                           getFragmentManager().popBackStack();
                                                       }
                                                   }

                                                   if (mSpinner_supply_outside.getSelectedItem().toString().equals("Logistic")) {
                                                       YieldAssessment yieldAssesment8 = new YieldAssessment();
                                                       yieldAssesment8.setQuestion(supplyingOutsideTv.getText().toString());
                                                       yieldAssesment8.setAnswer("");
                                                       yieldAssesment8.setValue(mSpinner_supply_outside.getSelectedItem().toString());
                                                       yieldAssesment8.setServerUpdatedStatus(0);
                                                       yieldAssesment8.setYear(2020);
                                                       yieldArray.add(yieldAssesment8);
                                                       getFragmentManager().popBackStack();


                                                       if (!TextUtils.isEmpty(issueEt.getText().toString())) {
                                                           YieldAssessment yieldAssesment9 = new YieldAssessment();
                                                           yieldAssesment9.setQuestion(issueName.getText().toString());
                                                           yieldAssesment9.setAnswer("");
                                                           yieldAssesment9.setValue(issueEt.getText().toString());
                                                           yieldAssesment9.setServerUpdatedStatus(0);
                                                           yieldAssesment9.setYear(2020);
                                                           yieldArray.add(yieldAssesment9);
                                                           getFragmentManager().popBackStack();
                                                       }

                                                       if (rb2 == 1) {
                                                           YieldAssessment yieldAssesment10 = new YieldAssessment();
                                                           String val = "Yes";
                                                           yieldAssesment10.setQuestion(ofRadQues.getText().toString());
                                                           yieldAssesment10.setAnswer("");
                                                           yieldAssesment10.setServerUpdatedStatus(0);
                                                           yieldAssesment10.setValue(val);
                                                           yieldAssesment10.setYear(2020);
                                                           yieldArray.add(yieldAssesment10);
                                                           getFragmentManager().popBackStack();
                                                       } else {
                                                           YieldAssessment yieldAssesment11 = new YieldAssessment();
                                                           yieldAssesment11.setQuestion(ofRadQues.getText().toString());
                                                           yieldAssesment11.setAnswer("");
                                                           yieldAssesment11.setValue("No");
                                                           yieldAssesment11.setServerUpdatedStatus(0);
                                                           yieldAssesment11.setYear(2020);
                                                           yieldArray.add(yieldAssesment11);
                                                           getFragmentManager().popBackStack();
                                                       }

                                                   }


                                                   if (mSpinner_supply_outside.getSelectedItem().toString().equals("Other Issue")) {
                                                       YieldAssessment yieldAssesment12 = new YieldAssessment();
                                                       yieldAssesment12.setQuestion(supplyingOutsideTv.getText().toString());
                                                       yieldAssesment12.setAnswer("");
                                                       yieldAssesment12.setValue(mSpinner_supply_outside.getSelectedItem().toString());
                                                       yieldAssesment12.setServerUpdatedStatus(0);
                                                       yieldAssesment12.setYear(2020);
                                                       yieldArray.add(yieldAssesment12);
                                                       getFragmentManager().popBackStack();

                                                       if (!TextUtils.isEmpty(DetailedReasonEt.getText().toString())) {
                                                           YieldAssessment yieldAssesment13 = new YieldAssessment();
                                                           yieldAssesment13.setQuestion(detailedreasonTv.getText().toString());
                                                           yieldAssesment13.setAnswer("");
                                                           yieldAssesment13.setValue(DetailedReasonEt.getText().toString());
                                                           yieldAssesment13.setServerUpdatedStatus(0);
                                                           yieldAssesment13.setYear(2020);
                                                           yieldArray.add(yieldAssesment13);
                                                           getFragmentManager().popBackStack();
                                                       }
                                                   }
                                               }

                                               if (mSpinner_parent.getSelectedItem().toString().equals("Selling in other person name")) {

                                                   if (!TextUtils.isEmpty(EfbbEt.getText().toString())) {
                                                       YieldAssessment yieldAssesment14 = new YieldAssessment();
                                                       yieldAssesment14.setQuestion(EfbbTv.getText().toString());
                                                       yieldAssesment14.setAnswer("");
                                                       yieldAssesment14.setValue(EfbbEt.getText().toString());
                                                       yieldAssesment14.setServerUpdatedStatus(0);
                                                       yieldAssesment14.setYear(2020);
                                                       yieldArray.add(yieldAssesment14);
                                                       getFragmentManager().popBackStack();
                                                   }

                                                   if (!TextUtils.isEmpty(monthEt.getText().toString())) {
                                                       YieldAssessment yieldAssesment15 = new YieldAssessment();
                                                       yieldAssesment15.setQuestion(monthTv.getText().toString());
                                                       yieldAssesment15.setAnswer("");
                                                       yieldAssesment15.setValue(monthEt.getText().toString());
                                                       yieldAssesment15.setYear(2020);
                                                       yieldAssesment15.setServerUpdatedStatus(0);
                                                       yieldArray.add(yieldAssesment15);
                                                       getFragmentManager().popBackStack();
                                                   }

                                                   if (rb3 == 1) {
                                                       YieldAssessment yieldAssesment16 = new YieldAssessment();
                                                       yieldAssesment16.setQuestion(cpQuestion.getText().toString());
                                                       yieldAssesment16.setAnswer("");
                                                       yieldAssesment16.setValue("Yes");
                                                       yieldAssesment16.setServerUpdatedStatus(0);
                                                       yieldAssesment16.setYear(2020);
                                                       yieldArray.add(yieldAssesment16);
                                                       getFragmentManager().popBackStack();

                                                   } else {
                                                       YieldAssessment yieldAssesment17 = new YieldAssessment();
                                                       yieldAssesment17.setQuestion(cpQuestion.getText().toString());
                                                       yieldAssesment17.setAnswer("");
                                                       yieldAssesment17.setValue("No");
                                                       yieldAssesment17.setYear(2020);
                                                       yieldAssesment17.setServerUpdatedStatus(0);
                                                       yieldArray.add(yieldAssesment17);
                                                       getFragmentManager().popBackStack();
                                                   }


                                               }

                                               if (mSpinner_parent.getSelectedItem().toString().equals("Other Issues")) {

                                                   if (mSpinner_reason.getSelectedItemPosition() != 0) {
                                                       YieldAssessment yieldAssesment18 = new YieldAssessment();
                                                       yieldAssesment18.setQuestion(reasonTv.getText().toString());
                                                       yieldAssesment18.setAnswer("");
                                                       yieldAssesment18.setValue(mSpinner_reason.getSelectedItem().toString());
                                                       yieldAssesment18.setServerUpdatedStatus(0);
                                                       yieldAssesment18.setYear(2020);
                                                       yieldArray.add(yieldAssesment18);
                                                       getFragmentManager().popBackStack();
                                                   }

                                                   if (!TextUtils.isEmpty(commentsEt.getText().toString())) {
                                                       YieldAssessment yieldAssesment19 = new YieldAssessment();
                                                       yieldAssesment19.setQuestion(commentsTv.getText().toString());
                                                       yieldAssesment19.setAnswer("");
                                                       yieldAssesment19.setValue(commentsEt.getText().toString());
                                                       yieldAssesment19.setServerUpdatedStatus(0);
                                                       yieldAssesment19.setYear(2020);
                                                       yieldArray.add(yieldAssesment19);
                                                       getFragmentManager().popBackStack();
                                                   }
                                               }
                                               DataManager.getInstance().addData(DataManager.YIELD_ASSESSMENT, yieldArray);
                                           } else {
                                               Toast.makeText(getActivity(), "Please Answer All The Questions", Toast.LENGTH_SHORT).show();
                                           }

                                       }

                                   }

        );

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
        dialog.setContentView(R.layout.yieldlastvisteddata);

        Toolbar titleToolbar;
        titleToolbar = (Toolbar) dialog.findViewById(R.id.titleToolbar);
        titleToolbar.setTitle("Yield Assessment History");
        titleToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        YieldVisitedDataAdapter yieldVisitedDataAdapter;

        LinearLayout yieldmainlyt = (LinearLayout) dialog.findViewById(R.id.yieldmainlyt);
        RecyclerView yieldrcv = (RecyclerView) dialog.findViewById(R.id.yieldrcv);
        TextView norecords = (TextView) dialog.findViewById(R.id.yieldnorecord_tv);

        String lastVisitCode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getLatestCropMaintanaceHistoryCode(CommonConstants.PLOT_CODE));
        yieldlastvisitdatamap = (ArrayList<YieldAssessment>) dataAccessHandler.getYieldData(Queries.getInstance().getRecommndCropMaintenanceHistoryDataforYieldandwhitefly(lastVisitCode, DatabaseKeys.TABLE_YIELDASSESMENT), 1);

        if (yieldlastvisitdatamap.size() > 0){
            norecords.setVisibility(View.GONE);
            yieldmainlyt.setVisibility(View.VISIBLE);

            yieldVisitedDataAdapter = new YieldVisitedDataAdapter(getActivity(), yieldlastvisitdatamap,dataAccessHandler);
            yieldrcv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            yieldrcv.setAdapter(yieldVisitedDataAdapter);

        }else{
            yieldmainlyt.setVisibility(View.GONE);
            norecords.setVisibility(View.VISIBLE);
        }

        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 500);
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

    private boolean valid() {
        return (mSpinner_parent.getSelectedItemPosition() > 0)
                && (mSpinner_parent.getSelectedItem().toString().equals("Supplying Outside") ?
                (mSpinner_supply_outside.getSelectedItem().toString().equals("Higher Rate")|| mSpinner_supply_outside.getSelectedItem().toString().equals("Higher Rate and Spot Payment") ?
                        !TextUtils.isEmpty(offHighRateEt.getText().toString()) &&
                                !TextUtils.isEmpty(factoryRepresentEt.getText().toString()) &&
                                !TextUtils.isEmpty(howmuchEt.getText().toString()) &&
                                mPaymentSpinner.getSelectedItemPosition() >= 0 : true)
                        && (mSpinner_supply_outside.getSelectedItem().toString().equals("Logistic") ?
                        !TextUtils.isEmpty(issueEt.getText().toString()) : true)
                        && (mSpinner_supply_outside.getSelectedItem().toString().equals("Other Issue") ?
                        !TextUtils.isEmpty(DetailedReasonEt.getText().toString()) : true) : true)
                && (mSpinner_parent.getSelectedItem().toString().equals("Selling in other person name") ?
                ! TextUtils.isEmpty(EfbbEt.getText().toString())&&
                        !TextUtils.isEmpty(monthEt.getText().toString()) : true)
                && (mSpinner_parent.getSelectedItem().toString().equals("Other Issues") ?
                (mSpinner_reason.getSelectedItemPosition() != 0) &&
                        !TextUtils.isEmpty(commentsEt.getText().toString()) : true)
                ;
    }
}
