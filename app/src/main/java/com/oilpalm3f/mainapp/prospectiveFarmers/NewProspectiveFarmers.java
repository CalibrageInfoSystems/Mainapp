package com.oilpalm3f.mainapp.prospectiveFarmers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.database.SqlString;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.Farmer;
import com.oilpalm3f.mainapp.dbmodels.FileRepository;
import com.oilpalm3f.mainapp.dbmodels.GeoBoundaries;
import com.oilpalm3f.mainapp.ui.OilPalmBaseActivity;
import com.oilpalm3f.mainapp.uihelper.CircleImageView;
import com.oilpalm3f.mainapp.utils.UiUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHENCHAIAH on 5/27/2017.
 */

//Displaying the prospective Farmers Data
public class NewProspectiveFarmers extends OilPalmBaseActivity {

    private static final String LOG_TAG = NewProspectiveFarmers.class.getName();

    private TextView farmerNameTxt;
    private TextView tvfathername, tvvillagename, tvcontactnum, tvaddress, selectedPlotsTxt;
    private CircleImageView userImage;
    private RecyclerView rvplotlist;
    private Farmer selectedFarmer;
    private DataAccessHandler dataAccessHandler = null;
    private List<ProspectivePlotsModel> plotDetailsObjArrayList = new ArrayList<>();
    private ProspectivePlotsAdapter prospectivePlotsAdapter;
    private FileRepository savedPictureData;

    @Override
    public void Initialize() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View parentView = inflater.inflate(R.layout.activity_prospectivefarmers, null);
        baseLayout.addView(parentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setTile(getString(R.string.prospective_details));

        selectedFarmer = (Farmer) DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS);
        dataAccessHandler = new DataAccessHandler(this);
        savedPictureData= dataAccessHandler.getSelectedFileRepository(Queries.getInstance().getSelectedFileRepositoryQuery(selectedFarmer.getCode(), 193));

        if (selectedFarmer == null) {
            UiUtils.showCustomToastMessage("Proper farmer data not found", this, 1);
            return;
        }

        initViews();

        final String imageUrl = CommonUtils.getImageUrl(savedPictureData);
        if(imageUrl.isEmpty())
        {
            userImage.setImageResource(R.drawable.no_image);
        }
        else {

//        Glide.with(this)
//                .load(Uri.parse("file://" + savedPictureData.getPicturelocation()))
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .error(R.mipmap.ic_launcher)
//                .into(userImage);
        Picasso.with(this)
                .load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(userImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        if (!isFinishing()) {
                            ApplicationThread.uiPost(LOG_TAG, "loading image", new Runnable() {
                                @Override
                                public void run() {
                                    if (savedPictureData != null && !savedPictureData.getPicturelocation().isEmpty() && savedPictureData.getPicturelocation() != null) {
                                        loadImageFromStorage(savedPictureData.getPicturelocation(), userImage);
                                    }
                                }
                            }, 50);
                        }
                    }
                });

        }
        farmerNameTxt.setText(selectedFarmer.getFirstname() + " " + selectedFarmer.getMiddlename() + " " + selectedFarmer.getLastname() + " (" + selectedFarmer.getCode() + ")");
    }

    private void loadImageFromStorage(String path, ImageView imageView) {
        Glide.with(this)
                .load(path)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    private void initViews() {
        farmerNameTxt = (TextView) findViewById(R.id.farmerNameTxt);
        tvfathername = (TextView) findViewById(R.id.tvfathername);
        tvvillagename = (TextView) findViewById(R.id.tvvillagename);
        tvcontactnum = (TextView) findViewById(R.id.tvcontactnumber);
        tvaddress = (TextView) findViewById(R.id.tvaddress);
        userImage = (CircleImageView) findViewById(R.id.profile_pic);
        selectedPlotsTxt = (TextView) findViewById(R.id.selectedPlotsTxt);
        rvplotlist = (RecyclerView) findViewById(R.id.lv_farmerplotdetails);
        bindData();
        bindSelectedFarmerData();
    }

    private void bindData() {
        dataAccessHandler = new DataAccessHandler(this);
        plotDetailsObjArrayList = dataAccessHandler.getProspectivePlotDetails(selectedFarmer.getCode(), 81);
        if (plotDetailsObjArrayList != null && plotDetailsObjArrayList.size() > 0) {
            prospectivePlotsAdapter = new ProspectivePlotsAdapter(this, plotDetailsObjArrayList);
            rvplotlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvplotlist.setAdapter(prospectivePlotsAdapter);
        }
    }

    private void bindSelectedFarmerData() {
        tvfathername.setText(selectedFarmer.getGuardianname());
        tvvillagename.setText(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().onlyValueFromDb("Village", "Name", " Id = "+ SqlString.Int(selectedFarmer.getVillageid()))));
        tvcontactnum.setText(selectedFarmer.getContactnumber() != null ? selectedFarmer.getContactnumber().trim() : "");
    }
}
