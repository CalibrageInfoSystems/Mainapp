package com.oilpalm3f.mainapp.transportservice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.oilpalm3f.mainapp.R;

public class TransportActivity extends AppCompatActivity {
Button farmerBtn,vendorBtn;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        farmerBtn=findViewById(R.id.farmerBtn);
        vendorBtn=findViewById(R.id.vendorBtn);

    }

    public void nextScreen(View view) {

        switch (view.getId()) {
            case R.id.farmerBtn:
                TransportFarmerFragment transportFarmerFragment = new TransportFarmerFragment();

                replaceFragment(transportFarmerFragment);
                break;
            case R.id.vendorBtn:
                TransportVendorFragment transportVendorFragment = new TransportVendorFragment();

                replaceFragment(transportVendorFragment);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

    }

    public void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(
                R.anim.enter_from_right, 0, 0, R.anim.exit_to_left
        );
        mFragmentTransaction.replace(android.R.id.content, fragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

}