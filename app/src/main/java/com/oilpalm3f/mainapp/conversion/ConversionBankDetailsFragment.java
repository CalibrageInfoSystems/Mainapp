package com.oilpalm3f.mainapp.conversion;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oilpalm3f.mainapp.BuildConfig;
import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.areaextension.FarmerViewDetailsAdapter;
import com.oilpalm3f.mainapp.areaextension.PersonalDetailsFragment;
import com.oilpalm3f.mainapp.areaextension.UpdateUiListener;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.common.OilPalmException;
import com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DataSavingHelper;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.ExistingFarmerData;
import com.oilpalm3f.mainapp.dbmodels.Farmer;
import com.oilpalm3f.mainapp.dbmodels.FarmerBank;
import com.oilpalm3f.mainapp.dbmodels.WaterResource;
import com.oilpalm3f.mainapp.farmersearch.SearchFarmerScreen;
import com.oilpalm3f.mainapp.ui.BaseFragment;
import com.oilpalm3f.mainapp.ui.RecyclerItemClickListener;
import com.oilpalm3f.mainapp.uihelper.ProgressBar;
import com.oilpalm3f.mainapp.utils.ImageUtility;
import com.oilpalm3f.mainapp.utils.UiUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.app.Activity.RESULT_OK;
import static com.oilpalm3f.mainapp.common.CommonUtils.isFromConversion;
import static com.oilpalm3f.mainapp.common.CommonUtils.isFromCropMaintenance;
import static com.oilpalm3f.mainapp.common.CommonUtils.isFromFollowUp;

/**
 * Created by skasam on 1/9/2017.
 */

//Bank Details Screen
public class ConversionBankDetailsFragment extends BaseFragment implements RecyclerItemClickListener {


    public static final int REQUEST_CAM_PERMISSIONS = 1;
    public static final int REQUEST_UPDATE_PERSONAL_DETAILS = 0;

    private static final String LOG_TAG = ConversionBankDetailsFragment.class.getName();

    private Spinner bankdetailsSpin, branchNameSpin;
    private String bankdetailnameStr, branchnameStr,mCurrentPhotoPath;
    private EditText accountholdername, accountnumber, bankNameText;
    private String accountholdernameStr, accountnumberStr, bankNameTextStr, ifsccodeStr,passBookImage_str;
    private LinearLayout otherBankLayout, parentLayout;
    private ImageView passbookImage;
    private Button bankDetailsSaveBtn;
    private LinkedHashMap<String, String> bankCodeDataMap;
    private LinkedHashMap<String, String> branchNameDataMap;
    private DataAccessHandler dataAccessHandler;
    ArrayList<ExistingFarmerData> data;
    private  FarmerViewDetailsAdapter farmerViewDetailsAdapter;

    private TextView ifsccode;
    private FarmerBank farmerBank = null;
    private boolean isUpdate;
    private boolean isImage = false;
    private byte[] bytes = null;
    private UpdateUiListener updateUiListener;
    private static final int CAMERA_REQUEST = 1888;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private int checkUpdateValueBank = 0, checkUpdateValueBranch = 0;

    public ConversionBankDetailsFragment() {

    }

    //Initializing the Class and adding Listeners
    @Override
    public void Initialize() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View parentView = inflater.inflate(R.layout.frag_conversion_bankdetails, null);
        baseLayout.addView(parentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setTile(getActivity().getResources().getString(R.string.bank_account_details));

        dataAccessHandler = new DataAccessHandler(getActivity());
        bankDetailsSaveBtn = (Button) parentView.findViewById(R.id.bankDetailsSaveBtn);
        ifsccode = (TextView) parentView.findViewById(R.id.ifsc_code);
        branchNameSpin = (Spinner) parentView.findViewById(R.id.branchNameSpin);
        otherBankLayout = (LinearLayout) parentView.findViewById(R.id.otherBankLayout);
        bankNameText = (EditText) parentView.findViewById(R.id.bankNameText);
        accountnumber = (EditText) parentView.findViewById(R.id.account_number);
        accountholdername = (EditText) parentView.findViewById(R.id.account_holder_name);
        bankdetailsSpin = (Spinner) parentView.findViewById(R.id.bankCodeSpin);
        parentLayout = (LinearLayout) parentView.findViewById(R.id.parent_layout);
        passbookImage = (ImageView) parentView.findViewById(R.id.passbookImage);

        parentLayout.setOnTouchListener((view, motionEvent) -> {
            CommonUtilsNavigation.hideKeyBoard(getActivity());
            return false;
        });

        bankCodeDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("3"));
        ArrayAdapter<String> bankCodeSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.fromMap(bankCodeDataMap, "BankName"));
        bankCodeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankdetailsSpin.setAdapter(bankCodeSpinnerArrayAdapter);

        bankdetailsSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (bankCodeDataMap != null && bankCodeDataMap.size() > 0 && bankdetailsSpin.getSelectedItemPosition() != 0 && checkUpdateValueBank == 0) {
                    CommonConstants.bankTypeId = bankCodeDataMap.keySet().toArray(new String[bankCodeDataMap.size()])[i - 1];
                    branchNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getBranchDetails(CommonConstants.bankTypeId));
                    ArrayAdapter<String> branchNameSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.fromMap(branchNameDataMap, "BranchName"));
                    branchNameSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    branchNameSpin.setAdapter(branchNameSpinnerArrayAdapter);
                    bankdetailnameStr = bankdetailsSpin.getSelectedItem().toString();
                } else {
                    checkUpdateValueBank = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        branchNameSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (branchNameDataMap != null && branchNameDataMap.size() > 0 && branchNameSpin.getSelectedItemPosition() != 0 && checkUpdateValueBranch == 0) {
                    CommonConstants.branchTypeId = branchNameDataMap.keySet().toArray(new String[branchNameDataMap.size()])[i - 1];
                    ifsccode.setText(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getIfscCode(CommonConstants.branchTypeId)));
                    branchnameStr = branchNameSpin.getSelectedItem().toString();
                } else {
                    checkUpdateValueBranch = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        accountnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>=8){
                    data = dataAccessHandler.getFarmerListforPersonalDetails(Queries.getInstance().getBAnkNumber(s.toString())) ;

                    if(data.isEmpty()){}else {
                        Dialog dialog = new Dialog(getActivity());
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.alertdilog_famer_number);
                        RecyclerView recyclerView =dialog.findViewById(R.id.recycler_alert_view);
                        Button cancel =dialog.findViewById(R.id.button_farmer);

                        TextView duplicateText = dialog.findViewById(R.id.duplicate);

                        duplicateText.setText("Duplicate Bank Account(s) Found");
                        farmerViewDetailsAdapter = new FarmerViewDetailsAdapter(getActivity(),data);
                        recyclerView.setAdapter(farmerViewDetailsAdapter);
                        farmerViewDetailsAdapter.setDuplicateFarmers(ConversionBankDetailsFragment.this);

                        cancel.setOnClickListener(v -> {
                            dialog.dismiss();
                        });


                        dialog.show();


                    }

                }

            }
        });
        passbookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(accountnumber.getText().toString())){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (!CommonUtils.isPermissionAllowed(getActivity(), Manifest.permission.CAMERA))) {
                        android.util.Log.v(LOG_TAG, "Location Permissions Not Granted");
                        ActivityCompat.requestPermissions(
                                getActivity(),
                                PERMISSIONS_STORAGE,
                                REQUEST_CAM_PERMISSIONS
                        );
                    } else {
                        dispatchTakePictureIntent(CAMERA_REQUEST);
                    }
                }else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_account_number_label), Toast.LENGTH_SHORT).show();
                    accountnumber.requestFocus();

                }

            }
        });

        bankDetailsSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatedUi()) {
                    FarmerBank farmerBank = new FarmerBank();
                    String bankId = CommonUtils.getKeyFromValue(branchNameDataMap, branchNameSpin.getSelectedItem().toString());
                    farmerBank.setBankid(Integer.parseInt(bankId));
                   // farmerBank.setBranchId(Integer.parseInt(CommonUtils.getKeyFromValue(branchNameDataMap, branchNameSpin.getSelectedItem().toString())));
                    farmerBank.setAccountholdername(accountholdernameStr);
                    farmerBank.setAccountnumber(accountnumberStr);
                    farmerBank.setFilename(accountnumberStr);
                    farmerBank.setFilelocation(mCurrentPhotoPath);
                    farmerBank.setFileextension(CommonConstants.JPEG_FILE_SUFFIX);
                    DataManager.getInstance().addData(DataManager.FARMER_BANK_DETAILS, farmerBank);
//                    saveData();
                    updateUiListener.updateUserInterface(0);
                    getFragmentManager().popBackStack();
                }
            }
        });

        farmerBank = (FarmerBank) DataManager.getInstance().getDataFromManager(DataManager.FARMER_BANK_DETAILS);
        if (farmerBank == null && (isFromFollowUp() || isFromCropMaintenance() || isFromConversion())) {
            farmerBank = (FarmerBank) dataAccessHandler.getSelectedFarmerBankData(Queries.getInstance().getFarmerBankData(CommonConstants.FARMER_CODE), 0);
        }

        if (null != farmerBank) {
            isUpdate = true;
            bindData();
        }
    }


    //Binding Data to the fields
    private void bindData() {
        checkUpdateValueBank = 1;
        checkUpdateValueBranch = 1;
        CommonConstants.bankTypeId = String.valueOf(farmerBank.getBankid());
        String bankTypeId = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getBankTypeId(CommonConstants.bankTypeId));
        bankdetailsSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(bankCodeDataMap, Integer.parseInt(bankTypeId)));
        branchNameDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getBranchDetails(bankTypeId));
        ArrayAdapter<String> branchNameSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CommonUtils.fromMap(branchNameDataMap, "BranchName"));
        branchNameSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchNameSpin.setAdapter(branchNameSpinnerArrayAdapter);
        int branchPos = CommonUtilsNavigation.getvalueFromHashMap(branchNameDataMap, farmerBank.getBankid());
        Log.v(LOG_TAG, "##### selected branch pos "+branchPos);
        branchNameSpin.setSelection(branchPos);
        accountholdername.setText(farmerBank.getAccountholdername());
        accountnumber.setText(farmerBank.getAccountnumber());
        ifsccode.setText(dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getIfscCode(String.valueOf(farmerBank.getBankid()))));
        mCurrentPhotoPath = farmerBank.getFilelocation();
        loadImageFromStorage(mCurrentPhotoPath);
        passbookImage.invalidate();
    }

    //Handling Picture
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
        File pictureDirectory = new File(CommonUtils.get3FFileRootPath() + "3F_Pictures/" + "FarmerBankPhotos");
        if (!pictureDirectory.exists()) {
            pictureDirectory.mkdirs();
        }

        File finalFile = new File(pictureDirectory,  accountnumber.getText().toString() + CommonConstants.JPEG_FILE_SUFFIX);
        return finalFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (resultCode == RESULT_OK) {
                    try {
                        handleBigCameraPhoto();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    mCurrentPhotoPath = null;
                }
                break;
            }


        }
    }
    private void handleBigCameraPhoto() throws Exception {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
        }

    }
    private void setPic() throws Exception {

        /* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */

        /* Get the size of the ImageView */
        int targetW = passbookImage.getWidth();
        int targetH = passbookImage.getHeight();

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
        getBytesFromBitmap(bitmap);
        bitmap = ImageUtility.rotatePicture(90, bitmap);
        passbookImage.setImageBitmap(bitmap);

        passbookImage.setVisibility(View.VISIBLE);
        isImage = true;
        passbookImage.invalidate();
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }
    public byte[] getBytesFromBitmap(Bitmap bitmap) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        bytes = stream.toByteArray();
        return stream.toByteArray();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "@@@ check on resume called");
        loadImageFromStorage(mCurrentPhotoPath);
        passbookImage.invalidate();
    }

    //Loads image from the Storage
    private void loadImageFromStorage(final String path) {
        if (null != farmerBank) {
            if (!getActivity().isFinishing()) {
                Glide.with(getActivity())
                        .load(path)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(passbookImage);
            }
            if(CommonUtils.isNetworkAvailable(getActivity())) {
                final String imageUrl =path;
            }
            else {
                if (!getActivity().isFinishing()) {
                    Glide.with(getActivity())
                            .load(path)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(passbookImage);
                }
            }
              } else {
            if (!getActivity().isFinishing()) {
                Glide.with(getActivity())
                        .load(path)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(passbookImage);
            }
        }
    }

    //Validations
    public boolean validatedUi() {

        accountnumberStr = accountnumber.getText().toString();
        accountholdernameStr = accountholdername.getText().toString();
        ifsccodeStr = ifsccode.getText().toString().toUpperCase();

        if (CommonUtils.isEmptySpinner(bankdetailsSpin)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_select_bankname), Toast.LENGTH_SHORT).show();
            bankdetailsSpin.requestFocus();
            return false;
        }

        if (CommonUtils.isEmptySpinner(branchNameSpin)) {
            Toast.makeText(getActivity(), "Please select branch name", Toast.LENGTH_SHORT).show();
            bankdetailsSpin.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(accountholdernameStr)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_account_name_label), Toast.LENGTH_SHORT).show();
            accountholdername.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(accountnumberStr)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_account_number_label), Toast.LENGTH_SHORT).show();
            accountnumber.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(mCurrentPhotoPath)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.Bank_PassBook), Toast.LENGTH_SHORT).show();
            passbookImage.requestFocus();
            return false;
        }
        return true;
    }

    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }

    @Override
    public void onItemSelected(int position) {
        Intent intent = new Intent(getActivity(), SearchFarmerScreen.class);
        intent.putExtra("Code",data.get(position).getCode());
        startActivity(intent);


    }
}
