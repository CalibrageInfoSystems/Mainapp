package com.oilpalm3f.mainapp.cropmaintenance;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.BuildConfig;
import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.UpdateUiListener;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUiUtils;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.FileRepository;
import com.oilpalm3f.mainapp.dbmodels.Healthplantation;
import com.oilpalm3f.mainapp.dbmodels.Uprootment;
import com.oilpalm3f.mainapp.ui.BaseFragment;
import com.oilpalm3f.mainapp.utils.ImageUtility;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.app.Activity.RESULT_OK;
import static com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation.getKey;


//Health of Plantation Screen, used to enter plantation health details during crop maintenance
public class HealthOfPlantationDetailsFragment extends Fragment implements View.OnClickListener, UpdateUiListener {
    public static final int REQUEST_CAM_PERMISSIONS = 1;
    private static final int CAMERA_REQUEST = 1888;
    private static final String LOG_TAG = HealthOfPlantationDetailsFragment.class.getName();
    public static RelativeLayout sec_rel;
    private View rootView;
    private Context mContext;
    private ImageView profile_pic;
    private DataAccessHandler dataAccessHandler;
    private Spinner appearanceSpin, girthOfTreeSpin, heightOfTreeSpin, colorOfFruitSpin, sizeOfFruitSpin, palmHyegieneSpin, spearLeafSpin;
    private EditText commentsEdt,FloresceneEd,BuchesEd,BuchesWeightEd;
    private Button saveBtn;
    private LinkedHashMap<String, String> appearanceDataMap, girthOfTreeDataMap, heightOfTreeDataMap, colorOfFruitDataMap,
            sizeOfFruitDataMap, palmHyegieneDataMap, spearLeafDataMap;
    private LinearLayout parentLayout;
    private Healthplantation mHealthplantation;
    private UpdateUiListener updateUiListener;
    Toolbar toolbar;
    ActionBar actionBar;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String mCurrentPhotoPath,plantationImageId;
    private FileRepository savedPictureData = null;
    private Button complaintsBtn;

    private Button historyBtn;
    private ArrayList<Healthplantation> healthplantationlastvisitdatamap;


    public HealthOfPlantationDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.fragment_health_of_plantation_details, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Health of plantation");

        mContext = getActivity();
        setHasOptionsMenu(true);
        initViews();
        setViews();
        bindData();
        return rootView;
    }

    private void bindData() {
        mHealthplantation = (Healthplantation) DataManager.getInstance().getDataFromManager(DataManager.WEEDING_HEALTH_OF_PLANTATION_DETAILS);
        if (mHealthplantation != null) {
            appearanceSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(appearanceDataMap, mHealthplantation.getTreesappearancetypeid()));
            girthOfTreeSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(girthOfTreeDataMap, mHealthplantation.getTreegirthtypeid()));
            heightOfTreeSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(heightOfTreeDataMap, mHealthplantation.getTreeheighttypeid()));
            colorOfFruitSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(colorOfFruitDataMap, (null != mHealthplantation.getFruitcolortypeid()) ? mHealthplantation.getFruitcolortypeid() : 0));
            sizeOfFruitSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(sizeOfFruitDataMap, (null != mHealthplantation.getFruitsizetypeid()) ? mHealthplantation.getFruitsizetypeid() : 0));
            palmHyegieneSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(palmHyegieneDataMap, mHealthplantation.getFruithyegienetypeid()));
            spearLeafSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(spearLeafDataMap, mHealthplantation.getSpearleafId()));
            savedPictureData = (FileRepository) DataManager.getInstance().getDataFromManager(DataManager.HOP_FILE_REPOSITORY_PLANTATION);
            if (savedPictureData != null && !TextUtils.isEmpty(savedPictureData.getPicturelocation())) {
                mCurrentPhotoPath = savedPictureData.getPicturelocation();
                loadImageFromStorage(savedPictureData.getPicturelocation());
                profile_pic.invalidate();
        }
        }
    }

    private void initViews() {
        dataAccessHandler = new DataAccessHandler(mContext);
        savedPictureData = new FileRepository();
        sec_rel = (RelativeLayout) rootView.findViewById(R.id.sec_rel);
        profile_pic = (ImageView) rootView.findViewById(R.id.profile_pic);
        appearanceSpin = (Spinner) rootView.findViewById(R.id.appearanceSpin);
        girthOfTreeSpin = (Spinner) rootView.findViewById(R.id.girthOfTreeSpin);
        heightOfTreeSpin = (Spinner) rootView.findViewById(R.id.heightOfTreeSpin);
        colorOfFruitSpin = (Spinner) rootView.findViewById(R.id.colorOfFruitSpin);
        sizeOfFruitSpin = (Spinner) rootView.findViewById(R.id.sizeOfFruitSpin);
        palmHyegieneSpin = (Spinner) rootView.findViewById(R.id.palmHyegieneSpin);
        spearLeafSpin = (Spinner) rootView.findViewById(R.id.spearLeafSpin);
        commentsEdt = (EditText) rootView.findViewById(R.id.commentsEdt);
        FloresceneEd = (EditText) rootView.findViewById(R.id.Florescene);
        BuchesEd = (EditText) rootView.findViewById(R.id.Buches);
        BuchesWeightEd = (EditText) rootView.findViewById(R.id.BunchWeight);
        saveBtn = (Button) rootView.findViewById(R.id.saveBtn);
        parentLayout = (LinearLayout) rootView.findViewById(R.id.healthParentLayout);
        complaintsBtn = (Button) rootView.findViewById(R.id.complaintsBtn);
        complaintsBtn.setVisibility(View.GONE);
        complaintsBtn.setEnabled(false);

        historyBtn = (Button) rootView.findViewById(R.id.hoplastvisitdataBtn);
        //.setVisibility((CommonUiUtils.isComplaintsDataEntered()) ? View.GONE : View.VISIBLE);

        complaintsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();
                dataBundle.putBoolean(CommonConstants.KEY_NEW_COMPLAINT, true);
                ComplaintDetailsFragment complaintDetailsFragment = new ComplaintDetailsFragment();
				complaintDetailsFragment.setArguments(dataBundle);
				complaintDetailsFragment.setUpdateUiListener(HealthOfPlantationDetailsFragment.this);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, complaintDetailsFragment).addToBackStack(null)
                        .commit();
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(getContext());
            }
        });

    }

    public void showDialog(Context activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.hoplastvisteddata);

        Toolbar titleToolbar;
        titleToolbar = (Toolbar) dialog.findViewById(R.id.titleToolbar);
        titleToolbar.setTitle("Health Plantation History");
        titleToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        LinearLayout mainLL = (LinearLayout) dialog.findViewById(R.id.hopmainlyt);
        LinearLayout hopfluorescencell = (LinearLayout) dialog.findViewById(R.id.hopfluorescencell);
        LinearLayout hopnoofbunchesll = (LinearLayout) dialog.findViewById(R.id.hopnoofbunchesll);
        LinearLayout hopbunchesweightll = (LinearLayout) dialog.findViewById(R.id.hopbunchesweightll);

        TextView hopappearance = (TextView) dialog.findViewById(R.id.hopappearance);
        TextView hopGirth = (TextView) dialog.findViewById(R.id.hopGirth);
        TextView hopheight = (TextView) dialog.findViewById(R.id.hopheight);
        TextView hophygiene = (TextView) dialog.findViewById(R.id.hophygiene);
        TextView hopfluorescence = (TextView) dialog.findViewById(R.id.hopfluorescence);
        TextView hopBunches = (TextView) dialog.findViewById(R.id.hopBunches);
        TextView hopBuncheweight = (TextView) dialog.findViewById(R.id.hopBuncheweight);
        TextView hopSpear = (TextView) dialog.findViewById(R.id.hopSpear);
        TextView norecords = (TextView) dialog.findViewById(R.id.hopnorecord_tv);

        String lastVisitCode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getLatestCropMaintanaceHistoryCode(CommonConstants.PLOT_CODE));
        healthplantationlastvisitdatamap = (ArrayList<Healthplantation>) dataAccessHandler.getHealthplantationData(Queries.getInstance().getRecommndCropMaintenanceHistoryData(lastVisitCode, DatabaseKeys.TABLE_HEALTHPLANTATION), 1);

        if (healthplantationlastvisitdatamap.size() > 0){
            norecords.setVisibility(View.GONE);
            mainLL.setVisibility(View.VISIBLE);

            String appearance = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(healthplantationlastvisitdatamap.get(0).getTreesappearancetypeid()));
            String girth = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(healthplantationlastvisitdatamap.get(0).getTreegirthtypeid()));
            String height = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(healthplantationlastvisitdatamap.get(0).getTreeheighttypeid()));
            String hygiene = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().gettypcdmtdata(healthplantationlastvisitdatamap.get(0).getFruithyegienetypeid()));
            String spear = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getlookupdata(healthplantationlastvisitdatamap.get(0).getSpearleafId()));

            hopappearance.setText(appearance);
            hopGirth.setText(girth);
            hopheight.setText(height);
            hophygiene.setText(hygiene);

            if (healthplantationlastvisitdatamap.get(0).getNoOfFlorescene() != 0){
                hopfluorescencell.setVisibility(View.VISIBLE);
                hopfluorescence.setText(healthplantationlastvisitdatamap.get(0).getNoOfFlorescene() + "");
            }else{
                hopfluorescencell.setVisibility(View.GONE);
            }

            if (healthplantationlastvisitdatamap.get(0).getNoOfBuches() != 0){
                hopnoofbunchesll.setVisibility(View.VISIBLE);
                hopBunches.setText(healthplantationlastvisitdatamap.get(0).getNoOfBuches() + "");
            }else{
                hopnoofbunchesll.setVisibility(View.GONE);
            }

            if (healthplantationlastvisitdatamap.get(0).getBunchWeight() != 0){
                hopbunchesweightll.setVisibility(View.VISIBLE);
                hopBuncheweight.setText(healthplantationlastvisitdatamap.get(0).getBunchWeight() + "");
            }else{
                hopbunchesweightll.setVisibility(View.GONE);
            }

            hopSpear.setText(spear);


        }else{
            mainLL.setVisibility(View.GONE);
            norecords.setVisibility(View.VISIBLE);
        }



        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 500);
    }

    private void setViews() {

        parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                CommonUtilsNavigation.hideKeyBoard(getActivity());
                return false;
            }
        });
        saveBtn.setOnClickListener(this);
        sec_rel.setOnClickListener(this);

        appearanceDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("22"));
        girthOfTreeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("23"));
        heightOfTreeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("24"));
        colorOfFruitDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("25"));
        sizeOfFruitDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("26"));
        palmHyegieneDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("27"));
        spearLeafDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getLookUpData("577"));

        appearanceSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Appearance of trees", appearanceDataMap));
        girthOfTreeSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Grith of trees", girthOfTreeDataMap));
        heightOfTreeSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Height of trees", heightOfTreeDataMap));
        colorOfFruitSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Color of trees", colorOfFruitDataMap));
        sizeOfFruitSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Size of trees", sizeOfFruitDataMap));
        palmHyegieneSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "Palm hyegiene", palmHyegieneDataMap));
        spearLeafSpin.setAdapter(CommonUtilsNavigation.adapterSetFromHashmap(getActivity(), "spear Leaf", spearLeafDataMap));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                if (validateFields()) {
                    savePictureData();
                    mHealthplantation = new Healthplantation();
                    mHealthplantation.setTreesappearancetypeid(Integer.parseInt(getKey(appearanceDataMap, appearanceSpin.getSelectedItem().toString())));
                    mHealthplantation.setTreegirthtypeid(Integer.parseInt(getKey(girthOfTreeDataMap, girthOfTreeSpin.getSelectedItem().toString())));
                    mHealthplantation.setTreeheighttypeid(Integer.parseInt(getKey(heightOfTreeDataMap, heightOfTreeSpin.getSelectedItem().toString())));
                    if (!CommonUtils.isEmptySpinner(colorOfFruitSpin)) {
                        mHealthplantation.setFruitcolortypeid(Integer.parseInt(getKey(colorOfFruitDataMap, colorOfFruitSpin.getSelectedItem().toString())));
                    } else {
                        mHealthplantation.setFruitcolortypeid(null);
                    }
                    if (!CommonUtils.isEmptySpinner(sizeOfFruitSpin)) {
                        mHealthplantation.setFruitsizetypeid(Integer.parseInt(getKey(sizeOfFruitDataMap, sizeOfFruitSpin.getSelectedItem().toString())));
                    } else {
                        mHealthplantation.setFruitsizetypeid(null);
                    }
                    mHealthplantation.setFruithyegienetypeid(Integer.parseInt(getKey(palmHyegieneDataMap, palmHyegieneSpin.getSelectedItem().toString())));
                    mHealthplantation.setSpearleafId(Integer.parseInt(getKey(spearLeafDataMap, spearLeafSpin.getSelectedItem().toString())));
                    mHealthplantation.setPlantationstatetypeid(113);

                    mHealthplantation.setNoOfFlorescene(Integer.parseInt(FloresceneEd.getText().length()>0?FloresceneEd.getText().toString():"0"));
                    mHealthplantation.setNoOfBuches(Integer.parseInt(BuchesEd.getText().length()>0?BuchesEd.getText().toString():"0"));
                    mHealthplantation.setBunchWeight(Integer.parseInt(BuchesWeightEd.getText().length()>0?BuchesWeightEd.getText().toString():"0"));

                    calculateRating();

                }
                CommonUtilsNavigation.hideKeyBoard(getActivity());
                break;
            case R.id.sec_rel:
//                CommonUtils.profilePic(getActivity());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (!CommonUtils.isPermissionAllowed(getActivity(), Manifest.permission.CAMERA))) {
                    android.util.Log.v(LOG_TAG, "Camera Permissions Not Granted");
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            PERMISSIONS_STORAGE,
                            REQUEST_CAM_PERMISSIONS
                    );
                } else {
                    // CommonUtils.profilePic(getActivity());
                    dispatchTakePictureIntent(CAMERA_REQUEST);
                }
                break;
        }
    }

    private void loadImageFromStorage(String path) {
        File photoFiles = new File(path);
        if (photoFiles != null) {
            Uri uri = Uri.fromFile(photoFiles);
            if (uri != null) {
                Picasso.with(getActivity()).load(uri).into(profile_pic, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
//                        renderImage();
                    }
                });
            }
        }
    }

    public void renderImage(String imageUrl, ImageView imageView) {
        Picasso.with(getActivity())
                .load(imageUrl)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    private void dispatchTakePictureIntent(int actionCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        switch (actionCode) {
            case CAMERA_REQUEST:
                File f = null;
                mCurrentPhotoPath = null;
                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    Uri photoURI = FileProvider.getUriForFile(getActivity(),
                            BuildConfig.APPLICATION_ID + ".provider",
                            f);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch
        startActivityForResult(takePictureIntent, actionCode);
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private File createImageFile() throws IOException {
        File pictureDirectory = new File(CommonUtils.get3FFileRootPath() + "/3F_Pictures/" + "PlantationPhotos");
        if (!pictureDirectory.exists()) {
            pictureDirectory.mkdirs();
        }
        File finalFile = new File(pictureDirectory, CommonConstants.PLOT_CODE + CommonConstants.JPEG_FILE_SUFFIX);
        return finalFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            } // ACTION_TAKE_PHOTO_B

        } // switch
    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
//            mCurrentPhotoPath = null;
        }

    }

    private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = profile_pic.getWidth();
        int targetH = profile_pic.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        bitmap = ImageUtility.rotatePicture(90, bitmap);
        profile_pic.setImageBitmap(bitmap);
        profile_pic.setVisibility(View.VISIBLE);
//        farmerIcon.setVisibility(View.GONE);
        profile_pic.invalidate();
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }


    public boolean validateFields() {

        if (CommonUtilsNavigation.spinnerSelect("Appearance of trees", appearanceSpin.getSelectedItemPosition(), getActivity()) &&
                CommonUtilsNavigation.spinnerSelect("Girth of tree", girthOfTreeSpin.getSelectedItemPosition(), getActivity()) &&
                CommonUtilsNavigation.spinnerSelect("Height of tree", heightOfTreeSpin.getSelectedItemPosition(), getActivity()) &&
                CommonUtilsNavigation.spinnerSelect("Palm hyegiene", palmHyegieneSpin.getSelectedItemPosition(), getActivity()) &&
                CommonUtilsNavigation.spinnerSelect("spear Leaf", spearLeafSpin.getSelectedItemPosition(), getActivity())) {

            if (TextUtils.isEmpty(mCurrentPhotoPath)) {
                Toast.makeText(getActivity(), "Please take the photo", Toast.LENGTH_LONG).show();
                return false;
            } else {
                return true;
            }


        }
        return false;
    }

    private void savePictureData() {
        Log.v("@@@L",""+CommonConstants.FARMER_CODE);
        savedPictureData=new FileRepository();
        savedPictureData.setFarmercode(CommonConstants.FARMER_CODE);
        savedPictureData.setPlotcode(CommonConstants.PLOT_CODE);
        savedPictureData.setModuletypeid(194);
        savedPictureData.setFilename(CommonConstants.PLOT_CODE);
        savedPictureData.setPicturelocation(mCurrentPhotoPath);
        savedPictureData.setFileextension(CommonConstants.JPEG_FILE_SUFFIX);
        savedPictureData.setCreatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
        savedPictureData.setCreatedDate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        savedPictureData.setUpdatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
        savedPictureData.setServerUpdatedStatus(0);
        savedPictureData.setIsActive(1);
        savedPictureData.setUpdatedDate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        DataManager.getInstance().addData(DataManager.HOP_FILE_REPOSITORY_PLANTATION, savedPictureData);
        updateUiListener.updateUserInterface(0);
        getFragmentManager().popBackStack();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CommonUtils.PERMISSION_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    Uri photoURI = FileProvider.getUriForFile(getActivity(),
                            BuildConfig.APPLICATION_ID + ".provider",
                            f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, 1);
                } else {

                }
                return;
            }
        }
    }


    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }

    @Override
    public void updateUserInterface(int refreshPosition) {
        complaintsBtn.setVisibility(View.GONE);
    }



    private void calculateRating()
    {


        CommonConstants.Spear_leaf_rating =  dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getRating(577,mHealthplantation.getSpearleafId()));


        Log.v("@@@Ratings",""+CommonConstants.Spear_leaf_rating+""+CommonConstants.perc_tree+""+CommonConstants.perc_tree_pest+""+CommonConstants.perc_tree_disease+""+CommonConstants.Basin_Health_rating+""+CommonConstants.Inflorescence_rating+""+CommonConstants.Weevils_rating);

        if((CommonConstants.Spear_leaf_rating.equals("A"))&& (CommonConstants.perc_tree=='A')&&
                (CommonConstants.perc_tree_pest=='A')&&(CommonConstants.perc_tree_disease=='A')&&
                (CommonConstants.Basin_Health_rating.equals("A"))&&(CommonConstants.Inflorescence_rating.equals("A")&&
                (CommonConstants.Weevils_rating.equals("A"))))
        {
            mHealthplantation.setPlantationtypeid(dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getPerOfTree(28,"A")));
        }else if((CommonConstants.Spear_leaf_rating.equals("A"))&& (CommonConstants.perc_tree=='B')&&
                (CommonConstants.perc_tree_pest=='A')&&(CommonConstants.perc_tree_disease=='A')&&
                (CommonConstants.Basin_Health_rating.equals("B"))&&(CommonConstants.Inflorescence_rating.equals("A")&&
                (CommonConstants.Weevils_rating.equals("A"))))
        {
            mHealthplantation.setPlantationtypeid(dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getPerOfTree(28,"A")));

        }else if((CommonConstants.Spear_leaf_rating.equals("B"))&& (CommonConstants.perc_tree=='B')&&
                (CommonConstants.perc_tree_pest=='B')&&(CommonConstants.perc_tree_disease=='B')&&
                (CommonConstants.Basin_Health_rating.equals("B"))&&(CommonConstants.Inflorescence_rating.equals("B")&&
                (CommonConstants.Weevils_rating.equals("B")))){
            mHealthplantation.setPlantationtypeid(dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getPerOfTree(28,"B")));

        }else {
            mHealthplantation.setPlantationtypeid(dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getPerOfTree(28,"C")));

        }

        mHealthplantation.setSpearLeafRating(CommonConstants.Spear_leaf_rating);
        mHealthplantation.setNutDefRating(String.valueOf(CommonConstants.perc_tree_pest));
        mHealthplantation.setBasinHealthRating(CommonConstants.Basin_Health_rating);
        mHealthplantation.setInflorescenceRating(CommonConstants.Inflorescence_rating);
        mHealthplantation.setWeevilsRating(CommonConstants.Weevils_rating);
        mHealthplantation.setPestRating(String.valueOf(CommonConstants.perc_tree_pest));
        mHealthplantation.setDiseasesRating(String.valueOf(CommonConstants.perc_tree_disease));


        DataManager.getInstance().addData(DataManager.WEEDING_HEALTH_OF_PLANTATION_DETAILS, mHealthplantation);
        getFragmentManager().popBackStack();
        updateUiListener.updateUserInterface(0);


    }


}
