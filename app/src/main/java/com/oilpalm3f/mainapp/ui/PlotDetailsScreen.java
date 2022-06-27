package com.oilpalm3f.mainapp.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.PlotDetailsFragment;

//Initializing the Plot details screen
public class PlotDetailsScreen extends OilPalmBaseActivity {

    @Override
    public void Initialize() {
        setTile("Plot Details");
        replaceFragment(new PlotDetailsFragment());
    }

}
