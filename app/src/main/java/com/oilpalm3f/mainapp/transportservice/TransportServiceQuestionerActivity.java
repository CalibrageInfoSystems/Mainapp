package com.oilpalm3f.mainapp.transportservice;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation;
import com.oilpalm3f.mainapp.cropmaintenance.FarmersCardViewFragment;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.TransportServiceQuestioner;
import com.oilpalm3f.mainapp.dbmodels.YieldAssessment;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TransportServiceQuestionerActivity extends AppCompatActivity {
    TransportServiceQuestioner transportServiceQuestioner;
    private ArrayList<TransportServiceQuestioner> transportArray = new ArrayList<>();

    TextView farmerSelectionQue, farmerVechileQue, vechileTypreQues, OthersQ, driveForTransportQue, pmntToDrvrQue, vechileRentQuestion,
            rentToOtherQuestion, howTransportques, OtherTransportQ, hiringBasisQue, hireChargesQue, plot1Village, tractor2wque, tractor4wqus,
            pickupVanQuestion, autoQuestion, bullockQuestion, otherQue, plot1VillageLaen, tractor2wqueLean, tractor4wqusLean,
            pickupVanQuestionLean, autoQuestionLean, bullockQuestionLean, otherQueLean, Name, address, mobile_numberque,
            hiringVechileTransportQue, labourEngaegementQue, labourChargesQue, paymentModeQue, OtherLabourPaymentQ, transpor3fServiceQue,
            suggestionQue, suggestionAns, serviceCompanyProvideQues, akshayaAppQue;

    EditText otherVechileQuestion, paymentAns, OtherTransportAns, tractor2wAns1, tractor2wAns2, tractor4wANs1, tractor4WAns2,
            pickUpVanAns1, pickUpVanAns2, autoQuestionAns1, autoQuestionAns2, bullockAns1, bullockAns2, peakOterAns1, peakOterAns2,
            tractor2wAns1Lean, tractor2wAns2Lean, tractor4wANs1Lean, tractor4WAns2Lean, pickUpVanAns1Lean, pickUpVanAns2Lean,
            autoQuestionAns1Lean,
            autoQuestionAns2Lean, bullockAns1Lean, bullockAns2Lean, peakOterAns1Lean, peakOterAns2Lean, nameAns, addressAns, mobile_numberAns,
            hiringVechileTransportAns, labourChargesAns, OtherLabourPaymentAns, serviceCompanyProvideAns, hireChargesDrop;

    public static EditText farmerSelectionAns;

    RadioGroup vechileRadioGp;
    RadioButton vcleYesRb, vcleNoRb;
    Spinner vechileSpinnerAns, drivingDropDownAns, paymentDropAns, vechileRentAnsDrop, rentToOtherDrop, howTransPortDropAns, hiringBasisDropAns,

    toCC1Drop, toCC2Drop, toCC1layoutLean, toCC2layoutLean, labourEngmentDropAns,
            paymentModeDropAns, labourChargesSpinner, transpor3fServiceDropAns, akshayaAppDropAns;

    LinearLayout vechileYesLayout, vechileNoLayout, otherTransportLayout, contactLabourLayout, otherPaymentLayout, otherVechileLayout;
    ArrayList<String> vechileTypeList, drivingTypeList, paymentList, yesNoList, tranportTypeList, hiringBasisList, labourEngaugeList,
            paymentModeList, maybeList;
    private ArrayAdapter<String> Adapter_vechileType, adapter_drivingType, payment_adapter, yesNoAdapter, transportAdapter, hiringAdapter,
            labourEngaugeAdapter, paymentModeAapter, maybeAdapter;

    DataAccessHandler dataAccessHandler;
    LinkedHashMap collecectionCenterList;
    Integer toCC1 = 0, toCC2 = 0, toCC3 = 0, toCC4 = 0, VechicleSpinner = 0, TransportDrive = 0, Paymenttype = 0, FarmerCurrentRent = 0, willingToRent = 0;
    String toCC1Name = "", toCC2Name = "", toCC3name = "", toCC4Name = "";
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_service_questioner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TransportServiceQuestioner");
        dataAccessHandler = new DataAccessHandler(this);
        initViews();
        setViews();
        onClickListeners();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saving();
            }
        });
    }

    private void initViews() {
        save = findViewById(R.id.save);
        farmerSelectionQue = findViewById(R.id.farmerSelectionQue);
        farmerSelectionAns = findViewById(R.id.farmerSelectionAns);
        farmerVechileQue = findViewById(R.id.farmerVechileQue);
        vechileRadioGp = findViewById(R.id.vechileRadioGp);
        vcleYesRb = findViewById(R.id.vcleYesRb);
        vcleNoRb = findViewById(R.id.vcleNoRb);
        vechileTypreQues = findViewById(R.id.vechileTypreQues);
        vechileSpinnerAns = findViewById(R.id.vechileSpinnerAns);
        OthersQ = findViewById(R.id.OthersQ);
        otherVechileQuestion = findViewById(R.id.otherVechileQuestion);

        driveForTransportQue = findViewById(R.id.driveForTransportQue);
        drivingDropDownAns = findViewById(R.id.drivingDropDownAns);
        pmntToDrvrQue = findViewById(R.id.pmntToDrvrQue);
        vechileRentQuestion = findViewById(R.id.vechileRentQuestion);

        rentToOtherQuestion = findViewById(R.id.rentToOtherQuestion);
        howTransportques = findViewById(R.id.howTransportques);
        OtherTransportQ = findViewById(R.id.OtherTransportQ);
        hiringBasisQue = findViewById(R.id.hiringBasisQue);
        hireChargesQue = findViewById(R.id.hireChargesQue);
        plot1Village = findViewById(R.id.plot1Village);
        tractor2wque = findViewById(R.id.tractor2wque);
        tractor4wqus = findViewById(R.id.tractor4wqus);
        pickupVanQuestion = findViewById(R.id.pickupVanQuestion);
        autoQuestion = findViewById(R.id.autoQuestion);
        bullockQuestion = findViewById(R.id.bullockQuestion);
        otherQue = findViewById(R.id.otherQue);
        plot1VillageLaen = findViewById(R.id.plot1VillageLaen);
        tractor2wqueLean = findViewById(R.id.tractor2wqueLean);
        tractor4wqusLean = findViewById(R.id.tractor4wqusLean);

        tractor4wqusLean = findViewById(R.id.tractor4wqusLean);
        pickupVanQuestionLean = findViewById(R.id.pickupVanQuestionLean);
        autoQuestionLean = findViewById(R.id.autoQuestionLean);
        bullockQuestionLean = findViewById(R.id.bullockQuestionLean);
        otherQueLean = findViewById(R.id.otherQueLean);
        Name = findViewById(R.id.Name);
        address = findViewById(R.id.address);
        mobile_numberque = findViewById(R.id.mobile_numberque);
        hiringVechileTransportQue = findViewById(R.id.hiringVechileTransportQue);
        labourEngaegementQue = findViewById(R.id.labourEngaegementQue);
        labourChargesQue = findViewById(R.id.labourChargesQue);
        paymentModeQue = findViewById(R.id.paymentModeQue);
        OtherLabourPaymentQ = findViewById(R.id.OtherLabourPaymentQ);
        transpor3fServiceQue = findViewById(R.id.transpor3fServiceQue);
        suggestionQue = findViewById(R.id.suggestionQue);
        suggestionAns = findViewById(R.id.suggestionAns);
        serviceCompanyProvideQues = findViewById(R.id.serviceCompanyProvideQues);
        akshayaAppQue = findViewById(R.id.akshayaAppQue);
        paymentAns = findViewById(R.id.paymentAns);
        OtherTransportAns = findViewById(R.id.OtherTransportAns);
        tractor2wAns1 = findViewById(R.id.tractor2wAns1);

        tractor2wAns2 = findViewById(R.id.tractor2wAns2);
        tractor4wANs1 = findViewById(R.id.tractor4wANs1);
        tractor4WAns2 = findViewById(R.id.tractor4WAns2);
        pickUpVanAns1 = findViewById(R.id.pickUpVanAns1);
        pickUpVanAns2 = findViewById(R.id.pickUpVanAns2);
        autoQuestionAns1 = findViewById(R.id.autoQuestionAns1);
        autoQuestionAns2 = findViewById(R.id.autoQuestionAns2);
        bullockAns1 = findViewById(R.id.bullockAns1);
        bullockAns2 = findViewById(R.id.bullockAns2);
        peakOterAns1 = findViewById(R.id.peakOterAns1);
        peakOterAns2 = findViewById(R.id.peakOterAns2);
        tractor2wAns1Lean = findViewById(R.id.tractor2wAns1Lean);
        tractor2wAns2Lean = findViewById(R.id.tractor2wAns2Lean);
        tractor4wANs1Lean = findViewById(R.id.tractor4wANs1Lean);
        tractor4WAns2Lean = findViewById(R.id.tractor4WAns2Lean);
        pickUpVanAns1Lean = findViewById(R.id.pickUpVanAns1Lean);
        pickUpVanAns2Lean = findViewById(R.id.pickUpVanAns2Lean);
        autoQuestionAns1Lean = findViewById(R.id.autoQuestionAns1Lean);
        autoQuestionAns2Lean = findViewById(R.id.autoQuestionAns2Lean);
        bullockAns1Lean = findViewById(R.id.bullockAns1Lean);
        bullockAns2Lean = findViewById(R.id.bullockAns2Lean);
        peakOterAns1Lean = findViewById(R.id.peakOterAns1Lean);
        peakOterAns2Lean = findViewById(R.id.peakOterAns2Lean);

        nameAns = findViewById(R.id.nameAns);
        addressAns = findViewById(R.id.addressAns);
        mobile_numberAns = findViewById(R.id.mobile_numberAns);
        hiringVechileTransportAns = findViewById(R.id.hiringVechileTransportAns);
        labourChargesAns = findViewById(R.id.labourChargesAns);
        OtherLabourPaymentAns = findViewById(R.id.OtherLabourPaymentAns);
        akshayaAppDropAns = findViewById(R.id.akshayaAppDropAns);
        serviceCompanyProvideAns = findViewById(R.id.serviceCompanyProvideAns);
        paymentDropAns = findViewById(R.id.paymentDropAns);
        vechileRentAnsDrop = findViewById(R.id.vechileRentAnsDrop);
        rentToOtherDrop = findViewById(R.id.rentToOtherDrop);
        howTransPortDropAns = findViewById(R.id.howTransPortDropAns);
        hiringBasisDropAns = findViewById(R.id.hiringBasisDropAns);
        hireChargesDrop = findViewById(R.id.hireChargesDrop);
        toCC1Drop = findViewById(R.id.toCC1Drop);
        toCC2Drop = findViewById(R.id.toCC2Drop);
        toCC1layoutLean = findViewById(R.id.toCC1layoutLean);
        toCC2layoutLean = findViewById(R.id.toCC2layoutLean);

        labourEngmentDropAns = findViewById(R.id.labourEngmentDropAns);
        paymentModeDropAns = findViewById(R.id.paymentModeDropAns);
        labourChargesSpinner = findViewById(R.id.labourChargesSpinner);
        transpor3fServiceDropAns = findViewById(R.id.transpor3fServiceDropAns);
        vechileNoLayout = findViewById(R.id.vechileNoLayout);
        otherTransportLayout = findViewById(R.id.otherTransportLayout);
        contactLabourLayout = findViewById(R.id.contactLabourLayout);
        otherPaymentLayout = findViewById(R.id.otherPaymentLayout);
        vechileYesLayout = findViewById(R.id.vechileYesLayout);
        otherVechileLayout = findViewById(R.id.otherVechileLayout);


    }

    private void setViews() {
        vechileTypeList = new ArrayList<>();
        vechileTypeList.add("select Vechile");
        vechileTypeList.add("Tractor - 2 Wheeler");
        vechileTypeList.add("Tractor - 4 Wheeler");
        vechileTypeList.add("Pickup Van");
        vechileTypeList.add("Auto");
        vechileTypeList.add("Bullock Cart");
        vechileTypeList.add("Other");

        Adapter_vechileType = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, vechileTypeList);
        vechileSpinnerAns.setAdapter(Adapter_vechileType);


        drivingTypeList = new ArrayList<>();
        drivingTypeList.add("select Drive");
        drivingTypeList.add("Self Driving");
        drivingTypeList.add("Hire Driver");

        adapter_drivingType = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, drivingTypeList);
        drivingDropDownAns.setAdapter(adapter_drivingType);

        paymentList = new ArrayList<>();
        paymentList.add("Select PaymentType");
        paymentList.add("Hour");
        paymentList.add("Day");
        paymentList.add("Month");

        payment_adapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, paymentList);
        paymentDropAns.setAdapter(payment_adapter);
        labourChargesSpinner.setAdapter(payment_adapter);


        yesNoList = new ArrayList<>();
        yesNoList.add("Select rent or not");
        yesNoList.add("Yes");
        yesNoList.add("No");

        yesNoAdapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, yesNoList);
        vechileRentAnsDrop.setAdapter(yesNoAdapter);
        rentToOtherDrop.setAdapter(yesNoAdapter);
        akshayaAppDropAns.setAdapter(yesNoAdapter);

        tranportTypeList = new ArrayList<>();
        tranportTypeList.add("Select Tranport");
        tranportTypeList.add("Hire");
        tranportTypeList.add("Fellow Farmers");
        tranportTypeList.add("Relative Vechile");
        tranportTypeList.add("Others");

        transportAdapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tranportTypeList);
        howTransPortDropAns.setAdapter(transportAdapter);

        hiringBasisList = new ArrayList<>();
        hiringBasisList.add("Select Hiring Basis");
        hiringBasisList.add("Per Trip");
        hiringBasisList.add("Per MT");
        hiringBasisList.add("Per KM");

        hiringAdapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, hiringBasisList);
        hiringBasisDropAns.setAdapter(hiringAdapter);


        labourEngaugeList = new ArrayList<>();
        labourEngaugeList.add("Select Labour Type");
        labourEngaugeList.add("Own Labour");
        labourEngaugeList.add("Contract Labour");

        labourEngaugeAdapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, labourEngaugeList);
        labourEngmentDropAns.setAdapter(labourEngaugeAdapter);


        labourEngaugeList = new ArrayList<>();
        labourEngaugeList.add("Select Labour Type");
        labourEngaugeList.add("Own Labour");
        labourEngaugeList.add("Contract Labour");

        labourEngaugeAdapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, labourEngaugeList);
        labourEngmentDropAns.setAdapter(labourEngaugeAdapter);

        paymentModeList = new ArrayList<>();
        paymentModeList.add("Select Payment mode");
        paymentModeList.add("Cash");
        paymentModeList.add("Bank Transfer");
        paymentModeList.add("Other");

        paymentModeAapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, paymentModeList);
        paymentModeDropAns.setAdapter(paymentModeAapter);


        maybeList = new ArrayList<>();
        maybeList.add("Select");
        maybeList.add("Yes");
        maybeList.add("No");
        maybeList.add("May Be");

        maybeAdapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, maybeList);
        transpor3fServiceDropAns.setAdapter(maybeAdapter);

        collecectionCenterList = dataAccessHandler.getLookUpData11(Queries.getInstance().getCollectionCenterMaster());
        toCC1Drop.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(this, "CC Name", collecectionCenterList));
        toCC1Drop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (toCC1Drop.getSelectedItemPosition() != 0) {

                    toCC1 = 1;
                    toCC1Name = toCC1Drop.getSelectedItem().toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        toCC2Drop.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(this, "CC Name", collecectionCenterList));
        toCC2Drop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (toCC2Drop.getSelectedItemPosition() != 0) {

                    toCC2 = 1;
                    toCC2Name = toCC2Drop.getSelectedItem().toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        toCC1layoutLean.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(this, "CC Name", collecectionCenterList));
        toCC1layoutLean.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (toCC1layoutLean.getSelectedItemPosition() != 0) {

                    toCC3 = 1;
                    toCC3name = toCC1layoutLean.getSelectedItem().toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        toCC2layoutLean.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(this, "CC Name", collecectionCenterList));
        toCC2layoutLean.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (toCC2layoutLean.getSelectedItemPosition() != 0) {

                    toCC4 = 1;
                    toCC4Name = toCC2layoutLean.getSelectedItem().toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        farmerSelectionAns.setClickable(true);
        farmerSelectionAns.setFocusable(false);
        farmerSelectionAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment df = new farmerDropDownFragment();
                df.show(getSupportFragmentManager(), "dialog");

            }
        });


    }

    private void onClickListeners() {
        vechileRadioGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.vcleYesRb) {
                    vechileYesLayout.setVisibility(View.VISIBLE);
                    vechileNoLayout.setVisibility(View.GONE);
                }
                if (checkedId == R.id.vcleNoRb) {
                    vechileYesLayout.setVisibility(View.GONE);
                    vechileNoLayout.setVisibility(View.VISIBLE);

                }
            }
        });

        vechileSpinnerAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (vechileSpinnerAns.getSelectedItem().toString().equalsIgnoreCase("Other")) {
                    otherVechileLayout.setVisibility(View.VISIBLE);

                } else {
                    otherVechileLayout.setVisibility(View.GONE);

                }
                if (vechileSpinnerAns.getSelectedItemPosition() != 0) {
                    VechicleSpinner = 1;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        howTransPortDropAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (howTransPortDropAns.getSelectedItem().toString().equalsIgnoreCase("Others")) {
                    otherTransportLayout.setVisibility(View.VISIBLE);
                } else {
                    otherTransportLayout.setVisibility(View.GONE);
                }

                if (howTransPortDropAns.getSelectedItemPosition() != 0) {
                    TransportDrive = 1;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        paymentDropAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (paymentDropAns.getSelectedItemPosition() != 0) {
                    Paymenttype = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        vechileRentAnsDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (vechileRentAnsDrop.getSelectedItemPosition() != 0) {
                    FarmerCurrentRent = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rentToOtherDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (rentToOtherDrop.getSelectedItemPosition() != 0) {
                    willingToRent = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        labourEngmentDropAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (labourEngmentDropAns.getSelectedItem().toString().equalsIgnoreCase("Contract Labour")) {
                    contactLabourLayout.setVisibility(View.VISIBLE);
                } else {
                    contactLabourLayout.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        paymentModeDropAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (paymentModeDropAns.getSelectedItem().toString().equalsIgnoreCase("Other")) {
                    otherPaymentLayout.setVisibility(View.VISIBLE);
                } else {
                    otherPaymentLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private boolean validation() {
        if (farmerSelectionAns.getText().toString().length() == 0) {
            Toast.makeText(this, "Please Farmer Name", Toast.LENGTH_LONG).show();
            return false;

        }
        if (vcleYesRb.isChecked()) {
            if (VechicleSpinner == 0) {
                Toast.makeText(this, "Please give Vechile Type", Toast.LENGTH_LONG).show();
                return false;
            } else if (TransportDrive == 0) {
                Toast.makeText(this, "Please give Drive for Transportation", Toast.LENGTH_LONG).show();
                return false;
            } else if (paymentAns.getText().toString().length() == 0) {
                Toast.makeText(this, "Please give Payment to Driver", Toast.LENGTH_LONG).show();
                return false;
            } else if (Paymenttype == 0) {
                Toast.makeText(this, "Please select Type of Payment", Toast.LENGTH_LONG).show();
                return false;

            } else if (FarmerCurrentRent == 0) {
                Toast.makeText(this, "Please select Rent his vechile or not", Toast.LENGTH_LONG).show();
                return false;
            } else if (willingToRent == 0) {
                Toast.makeText(this, "Please select Rent his vechile or not", Toast.LENGTH_LONG).show();
                return false;
            } else if (vechileSpinnerAns.getSelectedItem().toString().equalsIgnoreCase("Other") &&
                    otherVechileQuestion.getText().toString().length() == 0) {
                Toast.makeText(this, "Please Other Vechile", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private void saving() {
        if (validation()) {
            transportServiceQuestioner = new TransportServiceQuestioner();

            if (!TextUtils.isEmpty(farmerSelectionAns.getText().toString())) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(farmerSelectionQue.getText().toString());
                transportServiceQuestioner.setValue(farmerSelectionAns.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (vcleYesRb.isChecked()) {

                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(farmerVechileQue.getText().toString());
                transportServiceQuestioner.setValue(vcleYesRb.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);
            }
            if(vechileSpinnerAns.getSelectedItemPosition()!=0){
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(vechileTypreQues.getText().toString());
                transportServiceQuestioner.setValue(vechileSpinnerAns.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if(drivingDropDownAns.getSelectedItemPosition()!=0){
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(driveForTransportQue.getText().toString());
                transportServiceQuestioner.setValue(drivingDropDownAns.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if((!TextUtils.isEmpty(paymentAns.getText().toString()))&&paymentDropAns.getSelectedItemPosition()!=0){
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(pmntToDrvrQue.getText().toString());
                transportServiceQuestioner.setValue(paymentAns.getText().toString()+
                        drivingDropDownAns.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if(vechileRentAnsDrop.getSelectedItemPosition()!=0){
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(vechileRentQuestion.getText().toString());
                transportServiceQuestioner.setValue(vechileRentAnsDrop.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }

            if(rentToOtherDrop.getSelectedItemPosition()!=0){
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(rentToOtherQuestion.getText().toString());
                transportServiceQuestioner.setValue(rentToOtherDrop.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if(otherVechileQuestion.getText().toString().length()!=0){
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(OthersQ.getText().toString());
                transportServiceQuestioner.setValue(otherVechileQuestion.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            DataManager.getInstance().addData(DataManager.TransportServiceQuestioner, transportArray);



        }
    }
}