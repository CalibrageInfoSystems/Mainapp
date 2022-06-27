package com.oilpalm3f.mainapp.conversion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation;
import com.oilpalm3f.mainapp.ui.BaseFragment;
import com.oilpalm3f.mainapp.uihelper.InteractiveScrollView;

/**
 * Created by skasam on 1/9/2017.
 */

//Used to enter Plot details for conversion
public class ConversionPlotDetailsFragment extends BaseFragment {

    private EditText plotsurveyEdt,plotadangalEdt,totalareaunderpalmEdt,gpaholdernameEdt,contactnumbergpaholderlEdt,
            caretakernameEdt,caretakercontactnumlEdt;
    private TextView plotareatvbind,arealeftouttvbind,landmarkedt,plotaddress,villageName,mandalName,
            pincode,gpstext,plotdifftext ;
    private Spinner cropgrowninleftareaSpin,plotownnershipSpin;
    private RecyclerView neighbourPlotRecyclerView;
    private ImageView addRowImgNbPlot;
    private Button farmerSaveBtn,startgpsbtn;
    private InteractiveScrollView interactiveScrollView;
    public ImageView scrollBottomIndicator;
    private static final String LOG_TAG = ConversionPlotDetailsFragment.class.getName();
    private RadioButton activeCareTakerYes, activeCareTakerNo;
    private RadioGroup careTakerRadioGroup;
    private LinearLayout parentLayout;

    public ConversionPlotDetailsFragment()
    {

    }
    
    //Initializing the UI and Class
    @Override
    public void Initialize() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View parentView = inflater.inflate(R.layout.frag_conversion_plotdetails, null);
        baseLayout.addView(parentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setTile(getActivity().getResources().getString(R.string.plot_details));

        parentLayout = (LinearLayout)parentView.findViewById(R.id.parent_layout);
        this.interactiveScrollView = (InteractiveScrollView) parentView.findViewById(R.id.scrollView);
        scrollBottomIndicator = (ImageView) parentView.findViewById(R.id.bottomScroll);
        this.farmerSaveBtn = (Button) parentView.findViewById(R.id.farmerSaveBtn);
        this.addRowImgNbPlot = (ImageView) parentView.findViewById(R.id.addRowImgNbPlot);
        this.neighbourPlotRecyclerView = (RecyclerView) parentView.findViewById(R.id.neighbourPlotRecyclerView);
        this.plotdifftext = (TextView) parentView.findViewById(R.id.plot_diff_text);
        this.gpstext = (TextView) parentView.findViewById(R.id.gps_text);
        this.startgpsbtn = (Button) parentView.findViewById(R.id.start_gps_btn);
        this.caretakercontactnumlEdt = (EditText) parentView.findViewById(R.id.caretakercontactnumlEdt);
        this.caretakernameEdt = (EditText) parentView.findViewById(R.id.caretakernameEdt);
        careTakerRadioGroup = (RadioGroup) parentView.findViewById(R.id.careTakerRadioGroup_land);
        activeCareTakerYes = (RadioButton) parentView.findViewById(R.id.careTakerRadioBtnYes_land);
        activeCareTakerNo = (RadioButton) parentView.findViewById(R.id.careTakerRadioBtnNo_land);
        this.contactnumbergpaholderlEdt = (EditText) parentView.findViewById(R.id.contactnumbergpaholderlEdt);
        this.gpaholdernameEdt = (EditText) parentView.findViewById(R.id.gpaholdernameEdt);
        this.plotownnershipSpin = (Spinner) parentView.findViewById(R.id.plotownnershipSpin);
        this.pincode = (TextView) parentView.findViewById(R.id.pincode);
        this.mandalName = (TextView) parentView.findViewById(R.id.mandalName);
        this.villageName = (TextView) parentView.findViewById(R.id.villageName);
        this.plotaddress = (TextView) parentView.findViewById(R.id.plot_address);
        this.landmarkedt = (TextView) parentView.findViewById(R.id.land_mark_edt);
        this.cropgrowninleftareaSpin = (Spinner) parentView.findViewById(R.id.cropgrowninleftareaSpin);
        this.arealeftouttvbind = (TextView) parentView.findViewById(R.id.arealeftouttvbind);
        this.totalareaunderpalmEdt = (EditText) parentView.findViewById(R.id.totalareaunderpalmEdt);
        this.plotareatvbind = (TextView) parentView.findViewById(R.id.plot_area_tvbind);
        this.plotadangalEdt = (EditText) parentView.findViewById(R.id.plotadangalEdt);
        this.plotsurveyEdt = (EditText) parentView.findViewById(R.id.plotsurveyEdt);

        parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                CommonUtilsNavigation.hideKeyBoard(getActivity());
                return false;
            }
        });

        scrollBottomIndicator.setVisibility(View.VISIBLE);
        scrollBottomIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interactiveScrollView.post(new Runnable() {

                    @Override
                    public void run() {
                        interactiveScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
        interactiveScrollView.setOnBottomReachedListener(new InteractiveScrollView.OnBottomReachedListener() {
            @Override
            public void onBottomReached() {
                scrollBottomIndicator.setVisibility(View.GONE);
            }
        });

        interactiveScrollView.setOnTopReachedListener(new InteractiveScrollView.OnTopReachedListener() {
            @Override
            public void onTopReached() {
            }
        });

        interactiveScrollView.setOnScrollingListener(new InteractiveScrollView.OnScrollingListener() {
            @Override
            public void onScrolling() {
                android.util.Log.d(LOG_TAG, "onScrolling: ");
                scrollBottomIndicator.setVisibility(View.VISIBLE);
            }
        });


    }
}
