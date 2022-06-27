package com.oilpalm3f.mainapp.transportservice;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation;
import com.oilpalm3f.mainapp.cropmaintenance.FarmersCardViewFragment;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.TransportServiceQuestioner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class TransportFarmerFragment extends Fragment {
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

    @SuppressLint("StaticFieldLeak")
    public static EditText farmerSelectionAns;
    List<Village> villageArrayList;
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
    Integer toCC1 = 0, toCC2 = 0, toCC3 = 0, toCC4 = 0, VechicleSpinner = 0, TransportDrive = 0, Paymenttype = 0,
            FarmerCurrentRent = 0, willingToRent = 0, HowTranport = 0, HiringBsis = 0, LabourType = 0,
            Interest = 0, AkshaApp = 0, LabourChargeTime = 0, PaymentModee = 0;
    String toCC1Name = "", toCC2Name = "", toCC3name = "", toCC4Name = "";
    Button save;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_transport_farmer, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar;
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Transport Service Quetioner Farmer");

        toolbar.setNavigationOnClickListener(view1 -> {

            getActivity().onBackPressed();
        });
        dataAccessHandler = new DataAccessHandler(getContext());

        initViews();
        setViews();
        onClickListeners();

        if (farmerSelectionAns.getText().toString().length() != 0) {


            villageArrayList = dataAccessHandler.mgetVillageList(Queries.getInstance().
                    getVillageName(farmerSelectionAns.getText().toString()));
            Log.v("@@@LL","Size  "+villageArrayList.size());
            if (villageArrayList.size() == 1) {
                plot1Village.setText(villageArrayList.get(0).getName());
                Log.v("@@@LL", "Plot  " + villageArrayList.get(0).getName());
            }


        }else {
            Toast.makeText(getContext(),"Please Select Farmer",Toast.LENGTH_LONG).show();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saving();
            }
        });

        return view;
    }

    private void initViews() {
        save = view.findViewById(R.id.save);
        farmerSelectionQue = view.findViewById(R.id.farmerSelectionQue);
        farmerSelectionAns = view.findViewById(R.id.farmerSelectionAns);
        farmerVechileQue = view.findViewById(R.id.farmerVechileQue);
        vechileRadioGp = view.findViewById(R.id.vechileRadioGp);
        vcleYesRb = view.findViewById(R.id.vcleYesRb);
        vcleNoRb = view.findViewById(R.id.vcleNoRb);
        vechileTypreQues = view.findViewById(R.id.vechileTypreQues);
        vechileSpinnerAns = view.findViewById(R.id.vechileSpinnerAns);
        OthersQ = view.findViewById(R.id.OthersQ);
        otherVechileQuestion = view.findViewById(R.id.otherVechileQuestion);

        driveForTransportQue = view.findViewById(R.id.driveForTransportQue);
        drivingDropDownAns = view.findViewById(R.id.drivingDropDownAns);
        pmntToDrvrQue = view.findViewById(R.id.pmntToDrvrQue);
        vechileRentQuestion = view.findViewById(R.id.vechileRentQuestion);

        rentToOtherQuestion = view.findViewById(R.id.rentToOtherQuestion);
        howTransportques = view.findViewById(R.id.howTransportques);
        OtherTransportQ = view.findViewById(R.id.OtherTransportQ);
        hiringBasisQue = view.findViewById(R.id.hiringBasisQue);
        hireChargesQue = view.findViewById(R.id.hireChargesQue);
        plot1Village = view.findViewById(R.id.plot1Village);
        tractor2wque = view.findViewById(R.id.tractor2wque);
        tractor4wqus = view.findViewById(R.id.tractor4wqus);
        pickupVanQuestion = view.findViewById(R.id.pickupVanQuestion);
        autoQuestion = view.findViewById(R.id.autoQuestion);
        bullockQuestion = view.findViewById(R.id.bullockQuestion);
        otherQue = view.findViewById(R.id.otherQue);
        plot1VillageLaen = view.findViewById(R.id.plot1VillageLaen);
        tractor2wqueLean = view.findViewById(R.id.tractor2wqueLean);
        tractor4wqusLean = view.findViewById(R.id.tractor4wqusLean);

        tractor4wqusLean = view.findViewById(R.id.tractor4wqusLean);
        pickupVanQuestionLean = view.findViewById(R.id.pickupVanQuestionLean);
        autoQuestionLean = view.findViewById(R.id.autoQuestionLean);
        bullockQuestionLean = view.findViewById(R.id.bullockQuestionLean);
        otherQueLean = view.findViewById(R.id.otherQueLean);
        Name = view.findViewById(R.id.Name);
        address = view.findViewById(R.id.address);
        mobile_numberque = view.findViewById(R.id.mobile_numberque);
        hiringVechileTransportQue = view.findViewById(R.id.hiringVechileTransportQue);
        labourEngaegementQue = view.findViewById(R.id.labourEngaegementQue);
        labourChargesQue = view.findViewById(R.id.labourChargesQue);
        paymentModeQue = view.findViewById(R.id.paymentModeQue);
        OtherLabourPaymentQ = view.findViewById(R.id.OtherLabourPaymentQ);
        transpor3fServiceQue = view.findViewById(R.id.transpor3fServiceQue);
        suggestionQue = view.findViewById(R.id.suggestionQue);
        suggestionAns = view.findViewById(R.id.suggestionAns);
        serviceCompanyProvideQues = view.findViewById(R.id.serviceCompanyProvideQues);
        akshayaAppQue = view.findViewById(R.id.akshayaAppQue);
        paymentAns = view.findViewById(R.id.paymentAns);
        OtherTransportAns = view.findViewById(R.id.OtherTransportAns);
        tractor2wAns1 = view.findViewById(R.id.tractor2wAns1);

        tractor2wAns2 = view.findViewById(R.id.tractor2wAns2);
        tractor4wANs1 = view.findViewById(R.id.tractor4wANs1);
        tractor4WAns2 = view.findViewById(R.id.tractor4WAns2);
        pickUpVanAns1 = view.findViewById(R.id.pickUpVanAns1);
        pickUpVanAns2 = view.findViewById(R.id.pickUpVanAns2);
        autoQuestionAns1 = view.findViewById(R.id.autoQuestionAns1);
        autoQuestionAns2 = view.findViewById(R.id.autoQuestionAns2);
        bullockAns1 = view.findViewById(R.id.bullockAns1);
        bullockAns2 = view.findViewById(R.id.bullockAns2);
        peakOterAns1 = view.findViewById(R.id.peakOterAns1);
        peakOterAns2 = view.findViewById(R.id.peakOterAns2);
        tractor2wAns1Lean = view.findViewById(R.id.tractor2wAns1Lean);
        tractor2wAns2Lean = view.findViewById(R.id.tractor2wAns2Lean);
        tractor4wANs1Lean = view.findViewById(R.id.tractor4wANs1Lean);
        tractor4WAns2Lean = view.findViewById(R.id.tractor4WAns2Lean);
        pickUpVanAns1Lean = view.findViewById(R.id.pickUpVanAns1Lean);
        pickUpVanAns2Lean = view.findViewById(R.id.pickUpVanAns2Lean);
        autoQuestionAns1Lean = view.findViewById(R.id.autoQuestionAns1Lean);
        autoQuestionAns2Lean = view.findViewById(R.id.autoQuestionAns2Lean);
        bullockAns1Lean = view.findViewById(R.id.bullockAns1Lean);
        bullockAns2Lean = view.findViewById(R.id.bullockAns2Lean);
        peakOterAns1Lean = view.findViewById(R.id.peakOterAns1Lean);
        peakOterAns2Lean = view.findViewById(R.id.peakOterAns2Lean);

        nameAns = view.findViewById(R.id.nameAns);
        addressAns = view.findViewById(R.id.addressAns);
        mobile_numberAns = view.findViewById(R.id.mobile_numberAns);
        hiringVechileTransportAns = view.findViewById(R.id.hiringVechileTransportAns);
        labourChargesAns = view.findViewById(R.id.labourChargesAns);
        OtherLabourPaymentAns = view.findViewById(R.id.OtherLabourPaymentAns);
        akshayaAppDropAns = view.findViewById(R.id.akshayaAppDropAns);
        serviceCompanyProvideAns = view.findViewById(R.id.serviceCompanyProvideAns);
        paymentDropAns = view.findViewById(R.id.paymentDropAns);
        vechileRentAnsDrop = view.findViewById(R.id.vechileRentAnsDrop);
        rentToOtherDrop = view.findViewById(R.id.rentToOtherDrop);
        howTransPortDropAns = view.findViewById(R.id.howTransPortDropAns);
        hiringBasisDropAns = view.findViewById(R.id.hiringBasisDropAns);
        hireChargesDrop = view.findViewById(R.id.hireChargesDrop);
        toCC1Drop = view.findViewById(R.id.toCC1Drop);
        toCC2Drop = view.findViewById(R.id.toCC2Drop);
        toCC1layoutLean = view.findViewById(R.id.toCC1layoutLean);
        toCC2layoutLean = view.findViewById(R.id.toCC2layoutLean);

        labourEngmentDropAns = view.findViewById(R.id.labourEngmentDropAns);
        paymentModeDropAns = view.findViewById(R.id.paymentModeDropAns);
        labourChargesSpinner = view.findViewById(R.id.labourChargesSpinner);
        transpor3fServiceDropAns = view.findViewById(R.id.transpor3fServiceDropAns);
        vechileNoLayout = view.findViewById(R.id.vechileNoLayout);
        otherTransportLayout = view.findViewById(R.id.otherTransportLayout);
        contactLabourLayout = view.findViewById(R.id.contactLabourLayout);
        otherPaymentLayout = view.findViewById(R.id.otherPaymentLayout);
        vechileYesLayout = view.findViewById(R.id.vechileYesLayout);
        otherVechileLayout = view.findViewById(R.id.otherVechileLayout);


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

        Adapter_vechileType = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, vechileTypeList);
        vechileSpinnerAns.setAdapter(Adapter_vechileType);


        drivingTypeList = new ArrayList<>();
        drivingTypeList.add("select Drive");
        drivingTypeList.add("Self Driving");
        drivingTypeList.add("Hire Driver");

        adapter_drivingType = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, drivingTypeList);
        drivingDropDownAns.setAdapter(adapter_drivingType);

        paymentList = new ArrayList<>();
        paymentList.add("Select PaymentType");
        paymentList.add("Hour");
        paymentList.add("Day");
        paymentList.add("Month");

        payment_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, paymentList);
        paymentDropAns.setAdapter(payment_adapter);
        labourChargesSpinner.setAdapter(payment_adapter);


        yesNoList = new ArrayList<>();
        yesNoList.add("Select rent or not");
        yesNoList.add("Yes");
        yesNoList.add("No");

        yesNoAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, yesNoList);
        vechileRentAnsDrop.setAdapter(yesNoAdapter);
        rentToOtherDrop.setAdapter(yesNoAdapter);
        akshayaAppDropAns.setAdapter(yesNoAdapter);

        tranportTypeList = new ArrayList<>();
        tranportTypeList.add("Select Tranport");
        tranportTypeList.add("Hire");
        tranportTypeList.add("Fellow Farmers");
        tranportTypeList.add("Relative Vechile");
        tranportTypeList.add("Others");

        transportAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, tranportTypeList);
        howTransPortDropAns.setAdapter(transportAdapter);

        hiringBasisList = new ArrayList<>();
        hiringBasisList.add("Select Hiring Basis");
        hiringBasisList.add("Per Trip");
        hiringBasisList.add("Per MT");
        hiringBasisList.add("Per KM");

        hiringAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, hiringBasisList);
        hiringBasisDropAns.setAdapter(hiringAdapter);


        labourEngaugeList = new ArrayList<>();
        labourEngaugeList.add("Select Labour Type");
        labourEngaugeList.add("Own Labour");
        labourEngaugeList.add("Contract Labour");

        labourEngaugeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, labourEngaugeList);
        labourEngmentDropAns.setAdapter(labourEngaugeAdapter);


        labourEngaugeList = new ArrayList<>();
        labourEngaugeList.add("Select Labour Type");
        labourEngaugeList.add("Own Labour");
        labourEngaugeList.add("Contract Labour");

        labourEngaugeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, labourEngaugeList);
        labourEngmentDropAns.setAdapter(labourEngaugeAdapter);

        paymentModeList = new ArrayList<>();
        paymentModeList.add("Select Payment mode");
        paymentModeList.add("Cash");
        paymentModeList.add("Bank Transfer");
        paymentModeList.add("Other");

        paymentModeAapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, paymentModeList);
        paymentModeDropAns.setAdapter(paymentModeAapter);


        maybeList = new ArrayList<>();
        maybeList.add("Select");
        maybeList.add("Yes");
        maybeList.add("No");
        maybeList.add("May Be");

        maybeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, maybeList);
        transpor3fServiceDropAns.setAdapter(maybeAdapter);

        collecectionCenterList = dataAccessHandler.getLookUpData11(Queries.getInstance().getCollectionCenterMaster());
        toCC1Drop.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getContext(), "CC Name", collecectionCenterList));
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

        toCC2Drop.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getContext(), "CC Name", collecectionCenterList));
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

        toCC1layoutLean.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getContext(), "CC Name", collecectionCenterList));
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

        toCC2layoutLean.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getContext(), "CC Name", collecectionCenterList));
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
                df.show(getActivity().getSupportFragmentManager(), "dialog");


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

//                    if (farmerSelectionAns.getText().toString().length() != 0) {
//
//
//                        villageArrayList = dataAccessHandler.mgetVillageList(Queries.getInstance().
//                                getVillageName(farmerSelectionAns.getText().toString()));
//                        Log.v("@@@LL","Size  "+villageArrayList.size());
//                        if (villageArrayList.size() == 1) {
//                            plot1Village.setText(villageArrayList.get(0).getName());
//                            Log.v("@@@LL", "Plot  " + villageArrayList.get(0).getName());
//                        }
//
//
//                    }else {
//                        Toast.makeText(getContext(),"Please Select Farmer",Toast.LENGTH_LONG).show();
//                    }





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

        drivingDropDownAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (drivingDropDownAns.getSelectedItemPosition() != 0) {
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


        howTransPortDropAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (howTransPortDropAns.getSelectedItem().toString().equalsIgnoreCase("Others")) {
                    otherTransportLayout.setVisibility(View.VISIBLE);

                } else {
                    otherTransportLayout.setVisibility(View.GONE);

                }
                if (howTransPortDropAns.getSelectedItemPosition() != 0) {
                    HowTranport = 1;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        hiringBasisDropAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (hiringBasisDropAns.getSelectedItemPosition() != 0) {
                    HiringBsis = 1;
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
                if (labourEngmentDropAns.getSelectedItemPosition() != 0) {
                    LabourType = 1;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        labourChargesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (labourChargesSpinner.getSelectedItemPosition() != 0) {
                    LabourChargeTime = 1;
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
                if (paymentModeDropAns.getSelectedItemPosition() != 0) {
                    PaymentModee = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        transpor3fServiceDropAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (transpor3fServiceDropAns.getSelectedItemPosition() != 0) {
                    Interest = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        akshayaAppDropAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (akshayaAppDropAns.getSelectedItemPosition() != 0) {
                    AkshaApp = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private boolean validation() {
        if (farmerSelectionAns.getText().toString().length() == 0) {
            Toast.makeText(getContext(), "Please Farmer Name", Toast.LENGTH_LONG).show();
            return false;

        }
        if (vcleYesRb.isChecked()) {
            if (VechicleSpinner == 0) {
                Toast.makeText(getContext(), "Please give Vechile Type", Toast.LENGTH_LONG).show();
                return false;
            } else if (TransportDrive == 0) {
                Toast.makeText(getContext(), "Please give Drive for Transportation", Toast.LENGTH_LONG).show();
                return false;
            } else if (paymentAns.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Please give Payment to Driver", Toast.LENGTH_LONG).show();
                return false;
            } else if (Paymenttype == 0) {
                Toast.makeText(getContext(), "Please select Type of Payment", Toast.LENGTH_LONG).show();
                return false;

            } else if (FarmerCurrentRent == 0) {
                Toast.makeText(getContext(), "Please select Rent his vechile or not", Toast.LENGTH_LONG).show();
                return false;
            } else if (willingToRent == 0) {
                Toast.makeText(getContext(), "Please select Rent his vechile or not", Toast.LENGTH_LONG).show();
                return false;
            } else if (vechileSpinnerAns.getSelectedItem().toString().equalsIgnoreCase("Other") &&
                    otherVechileQuestion.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Please Other Vechile", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (vcleNoRb.isChecked()) {
            if (HowTranport == 0) {
                Toast.makeText(getContext(), "Please give How Farmers Transport", Toast.LENGTH_LONG).show();
                return false;

            } else if (HiringBsis == 0) {
                Toast.makeText(getContext(), "Please give Hiring Basis", Toast.LENGTH_LONG).show();
                return false;
            } else if (hireChargesDrop.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Please give Hiring Charges", Toast.LENGTH_LONG).show();
                return false;
            } else if (toCC1 == 0) {
                Toast.makeText(getContext(), "Please give Collection Centre1", Toast.LENGTH_LONG).show();
                return false;

            } else if (toCC2 == 0) {
                Toast.makeText(getContext(), "Please give Collection Centre2", Toast.LENGTH_LONG).show();
                return false;
            }
//            else if (((tractor2wAns1.getText().toString().length() == 0) || (tractor2wAns2.getText().toString().length() == 0))
//                    || ((tractor4wANs1.getText().toString().length() == 0) || (tractor4WAns2.getText().toString().length() == 0))
//                    || ((pickUpVanAns1.getText().toString().length() == 0) || (pickUpVanAns2.getText().toString().length() == 0))
//                    || ((autoQuestionAns1.getText().toString().length() == 0) || (autoQuestionAns2.getText().toString().length() == 0))
//                    || ((bullockAns1.getText().toString().length() == 0) || (bullockAns2.getText().toString().length() == 0))
//                    || ((peakOterAns1.getText().toString().length() == 0) || (peakOterAns2.getText().toString().length() == 0))) {
//                Toast.makeText(getContext(), "Please give any Vechile Details", Toast.LENGTH_LONG).show();
//                return false;
//            }
            else if (toCC3 == 0) {
                Toast.makeText(getContext(), "Please give Collection Centre1 Lean", Toast.LENGTH_LONG).show();
                return false;

            } else if (toCC4 == 0) {
                Toast.makeText(getContext(), "Please give Collection Centre2 Lean", Toast.LENGTH_LONG).show();
                return false;
            }
//            else if (((tractor2wAns1Lean.getText().toString().length() == 0) || (tractor2wAns2Lean.getText().toString().length() == 0))
//                    || ((tractor4wANs1Lean.getText().toString().length() == 0) || (tractor4WAns2Lean.getText().toString().length() == 0))
//                    || ((pickUpVanAns1Lean.getText().toString().length() == 0) || (pickUpVanAns2Lean.getText().toString().length() == 0))
//                    || ((autoQuestionAns1.getText().toString().length() == 0) || (autoQuestionAns2.getText().toString().length() == 0))
//                    || ((bullockAns1Lean.getText().toString().length() == 0) || (bullockAns2Lean.getText().toString().length() == 0))
//                    || ((peakOterAns1Lean.getText().toString().length() == 0) || (peakOterAns2Lean.getText().toString().length() == 0))) {
//                Toast.makeText(getContext(), "Please give any Vechile Details for Lean", Toast.LENGTH_LONG).show();
//                return false;
//            }
            else if (nameAns.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Please give Name", Toast.LENGTH_LONG).show();
                return false;

            } else if (addressAns.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Please give Adress", Toast.LENGTH_LONG).show();
                return false;

            } else if (mobile_numberAns.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Please give Mobile Number", Toast.LENGTH_LONG).show();
                return false;

            } else if (hiringVechileTransportAns.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Please give Farmer facing any Problem", Toast.LENGTH_LONG).show();
                return false;

            } else if (LabourType == 0) {
                Toast.makeText(getContext(), "Please give Labour Engaugement", Toast.LENGTH_LONG).show();
                return false;

            } else if ((labourEngmentDropAns.getSelectedItem().toString().equalsIgnoreCase("Contract Labour")) &&
                    (labourChargesAns.getText().toString().length() == 0) && LabourChargeTime == 0) {
                Toast.makeText(getContext(), "Please give Labour Charges", Toast.LENGTH_LONG).show();
                return false;

            } else if ((labourEngmentDropAns.getSelectedItem().toString().equalsIgnoreCase("Contract Labour")) &&
                    (PaymentModee == 0)) {
                Toast.makeText(getContext(), "Please give Payment Mode", Toast.LENGTH_LONG).show();
                return false;
            } else if (paymentModeDropAns.getSelectedItem().toString().equalsIgnoreCase("Other")
                    && (OtherLabourPaymentAns.getText().toString().length() == 0)) {
                Toast.makeText(getContext(), "Please give Other Payment", Toast.LENGTH_LONG).show();
                return false;
            } else if (Interest == 0) {
                Toast.makeText(getContext(), "Please give 3f service Interest", Toast.LENGTH_LONG).show();
                return false;
            } else if (suggestionAns.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Please give any Suggestion", Toast.LENGTH_LONG).show();
                return false;

            } else if (serviceCompanyProvideAns.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Please give any Other Service company", Toast.LENGTH_LONG).show();
                return false;
            } else if (AkshaApp == 0) {
                Toast.makeText(getContext(), "Please give Akshaya App Details", Toast.LENGTH_LONG).show();
                return false;

            } else if ((howTransPortDropAns.getSelectedItem().toString().equals("Others")) && (
                    OtherTransportAns.getText().toString().length() == 0)) {
                Toast.makeText(getContext(), "Please give Other Transport", Toast.LENGTH_LONG).show();
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
            if (vechileSpinnerAns.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(vechileTypreQues.getText().toString());
                transportServiceQuestioner.setValue(vechileSpinnerAns.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (drivingDropDownAns.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(driveForTransportQue.getText().toString());
                transportServiceQuestioner.setValue(drivingDropDownAns.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if ((!TextUtils.isEmpty(paymentAns.getText().toString())) && paymentDropAns.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(pmntToDrvrQue.getText().toString());
                transportServiceQuestioner.setValue(paymentAns.getText().toString() +
                        drivingDropDownAns.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (vechileRentAnsDrop.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(vechileRentQuestion.getText().toString());
                transportServiceQuestioner.setValue(vechileRentAnsDrop.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }

            if (rentToOtherDrop.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(rentToOtherQuestion.getText().toString());
                transportServiceQuestioner.setValue(rentToOtherDrop.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (otherVechileQuestion.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(OthersQ.getText().toString());
                transportServiceQuestioner.setValue(otherVechileQuestion.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (howTransPortDropAns.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(howTransportques.getText().toString());
                transportServiceQuestioner.setValue(howTransPortDropAns.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (OtherTransportAns.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(OtherTransportQ.getText().toString());
                transportServiceQuestioner.setValue(OtherTransportAns.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (hiringBasisDropAns.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(hiringBasisQue.getText().toString());
                transportServiceQuestioner.setValue(hiringBasisDropAns.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (hireChargesDrop.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(hireChargesQue.getText().toString());
                transportServiceQuestioner.setValue(hireChargesDrop.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (toCC1Drop.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion("Peak Collection Centre Code1");
                transportServiceQuestioner.setValue(toCC1Drop.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (toCC2Drop.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion("Peak Collection Centre Code2");
                transportServiceQuestioner.setValue(toCC2Drop.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);
            }
            if (tractor2wAns1.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(tractor2wque.getText().toString());
                transportServiceQuestioner.setValue(tractor2wAns1.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (tractor2wAns2.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(tractor2wque.getText().toString());
                transportServiceQuestioner.setValue(tractor2wAns2.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }

            if (tractor4wANs1.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(tractor4wqus.getText().toString());
                transportServiceQuestioner.setValue(tractor4wANs1.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (tractor4WAns2.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(tractor4wqus.getText().toString());
                transportServiceQuestioner.setValue(tractor4WAns2.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }

            if (pickUpVanAns1.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(pickupVanQuestion.getText().toString());
                transportServiceQuestioner.setValue(pickUpVanAns1.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (pickUpVanAns2.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(pickupVanQuestion.getText().toString());
                transportServiceQuestioner.setValue(pickUpVanAns2.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (autoQuestionAns1.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(autoQuestion.getText().toString());
                transportServiceQuestioner.setValue(autoQuestionAns1.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (autoQuestionAns2.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(autoQuestion.getText().toString());
                transportServiceQuestioner.setValue(autoQuestionAns2.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }

            if (bullockAns1.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(bullockQuestion.getText().toString());
                transportServiceQuestioner.setValue(bullockAns1.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (bullockAns2.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(bullockQuestion.getText().toString());
                transportServiceQuestioner.setValue(bullockAns2.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }

            if (peakOterAns1.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(otherQue.getText().toString());
                transportServiceQuestioner.setValue(peakOterAns1.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (peakOterAns2.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(otherQue.getText().toString());
                transportServiceQuestioner.setValue(peakOterAns2.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (toCC1layoutLean.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion("Lean Collection Centre Code1");
                transportServiceQuestioner.setValue(toCC1layoutLean.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (toCC2layoutLean.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion("Lean Collection Centre Code2");
                transportServiceQuestioner.setValue(toCC2layoutLean.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }

            if (tractor2wAns1Lean.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(tractor2wqueLean.getText().toString());
                transportServiceQuestioner.setValue(tractor2wAns1Lean.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (tractor2wAns2Lean.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(tractor2wqueLean.getText().toString());
                transportServiceQuestioner.setValue(tractor2wAns2Lean.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }

            if (tractor4wANs1Lean.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(tractor4wqusLean.getText().toString());
                transportServiceQuestioner.setValue(tractor4wANs1Lean.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (tractor4WAns2Lean.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(tractor4wqusLean.getText().toString());
                transportServiceQuestioner.setValue(tractor4WAns2Lean.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }

            if (pickUpVanAns1Lean.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(pickupVanQuestionLean.getText().toString());
                transportServiceQuestioner.setValue(pickUpVanAns1Lean.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (pickUpVanAns2Lean.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(pickupVanQuestionLean.getText().toString());
                transportServiceQuestioner.setValue(pickUpVanAns2Lean.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (autoQuestionAns1Lean.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(autoQuestionLean.getText().toString());
                transportServiceQuestioner.setValue(autoQuestionAns1Lean.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (autoQuestionAns2Lean.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(autoQuestionLean.getText().toString());
                transportServiceQuestioner.setValue(autoQuestionAns2Lean.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }

            if (bullockAns1Lean.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(bullockQuestionLean.getText().toString());
                transportServiceQuestioner.setValue(bullockAns1Lean.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (bullockAns2Lean.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(bullockQuestionLean.getText().toString());
                transportServiceQuestioner.setValue(bullockAns2Lean.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }

            if (peakOterAns1Lean.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(otherQueLean.getText().toString());
                transportServiceQuestioner.setValue(peakOterAns1Lean.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (peakOterAns2Lean.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(otherQueLean.getText().toString());
                transportServiceQuestioner.setValue(peakOterAns2Lean.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (nameAns.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(Name.getText().toString());
                transportServiceQuestioner.setValue(nameAns.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (addressAns.getText().toString().length() == 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(address.getText().toString());
                transportServiceQuestioner.setValue(addressAns.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (mobile_numberAns.getText().toString().length() == 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(mobile_numberque.getText().toString());
                transportServiceQuestioner.setValue(mobile_numberAns.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (hiringVechileTransportAns.getText().toString().length() == 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(hiringVechileTransportQue.getText().toString());
                transportServiceQuestioner.setValue(hiringVechileTransportAns.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (labourEngmentDropAns.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(labourEngaegementQue.getText().toString());
                transportServiceQuestioner.setValue(labourEngmentDropAns.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if ((labourChargesAns.getText().toString().length() != 0) && labourChargesSpinner.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(labourChargesQue.getText().toString());
                transportServiceQuestioner.setValue(labourChargesAns.getText().toString() + labourChargesSpinner.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (paymentModeDropAns.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(paymentModeQue.getText().toString());
                transportServiceQuestioner.setValue(paymentModeDropAns.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (OtherLabourPaymentAns.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(OtherLabourPaymentQ.getText().toString());
                transportServiceQuestioner.setValue(OtherLabourPaymentAns.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (transpor3fServiceDropAns.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(transpor3fServiceQue.getText().toString());
                transportServiceQuestioner.setValue(transpor3fServiceDropAns.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (suggestionAns.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(suggestionQue.getText().toString());
                transportServiceQuestioner.setValue(suggestionAns.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }
            if (serviceCompanyProvideAns.getText().toString().length() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(serviceCompanyProvideQues.getText().toString());
                transportServiceQuestioner.setValue(serviceCompanyProvideAns.getText().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);
            }
            if (akshayaAppDropAns.getSelectedItemPosition() != 0) {
                transportServiceQuestioner.setCropMaintenaceCode(CommonConstants.CROP_MAINTENANCE_HISTORY_CODE);
                transportServiceQuestioner.setQuestion(akshayaAppQue.getText().toString());
                transportServiceQuestioner.setValue(akshayaAppDropAns.getSelectedItem().toString());
                transportServiceQuestioner.setYear(2020);
                transportServiceQuestioner.setServerUpdatedStatus(0);
                transportArray.add(transportServiceQuestioner);

            }


            DataManager.getInstance().addData(DataManager.TransportServiceQuestioner, transportArray);


        }

    }


}

