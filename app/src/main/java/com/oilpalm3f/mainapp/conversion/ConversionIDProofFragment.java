package com.oilpalm3f.mainapp.conversion;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.oilpalm3f.mainapp.areaextension.FarmerViewDetailsAdapter;
import com.oilpalm3f.mainapp.areaextension.PersonalDetailsFragment;
import com.oilpalm3f.mainapp.areaextension.UpdateUiListener;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
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
import com.oilpalm3f.mainapp.dbmodels.IdentityProof;
import com.oilpalm3f.mainapp.dbmodels.PlotIrrigationTypeXref;
import com.oilpalm3f.mainapp.farmersearch.SearchFarmerScreen;
import com.oilpalm3f.mainapp.ui.BaseFragment;
import com.oilpalm3f.mainapp.ui.HomeScreen;
import com.oilpalm3f.mainapp.ui.RecyclerItemClickListener;
import com.oilpalm3f.mainapp.uihelper.ProgressBar;
import com.oilpalm3f.mainapp.utils.ImageUtility;
import com.oilpalm3f.mainapp.utils.UiUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.oilpalm3f.mainapp.common.CommonUtils.isFromConversion;
import static com.oilpalm3f.mainapp.common.CommonUtils.isFromCropMaintenance;
import static com.oilpalm3f.mainapp.common.CommonUtils.isFromFollowUp;

/**
 * Created by skasam on 1/11/2017.
 */

//Id Proof Details Screen
public class ConversionIDProofFragment extends BaseFragment implements IdProofsListAdapter.idProofsClickListener, RecyclerItemClickListener {

    private static final String LOG_TAG = ConversionIDProofFragment.class.getName();
    private Spinner idproof;
    private ImageView idproofImageView;
    private boolean isImage = false;
    private byte[] bytes = null;
    private String aadharStr,mCurrentPhotoPath = "";
    private IdProofsListAdapter idProofsListAdapter;
    private RecyclerView idProofsRecyclerView;
    private AlertDialog alert;
    private LinearLayout headerLL;
    private View headerView;
    private RelativeLayout addIdProof, addIdProofBottomView;
    private DataAccessHandler dataAccessHandler;
    private LinkedHashMap<String, String> idProofsData, mainData;
    private List<IdentityProof> identityProofsList;
    private Button saveBtn;
    ArrayList<ExistingFarmerData> data;
    private  FarmerViewDetailsAdapter farmerViewDetailsAdapter;
    private UpdateUiListener updateUiListener;
    private String blockCharacterSet = "~#^|$%&*!";
    private static final int CAMERA_REQUEST = 1888;
    public static final int REQUEST_CAM_PERMISSIONS = 1;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private InputFilter filter = (source, start, end, dest, dstart, dend) -> {

        if (source != null && blockCharacterSet.contains(("" + source))) {
            return "";
        }
        return null;
    };

    public ConversionIDProofFragment() {

    }

    //Initializing the Class & set Adapter
    @Override
    public void Initialize() {
        dataAccessHandler = new DataAccessHandler(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View parentView = inflater.inflate(R.layout.frag_conversion_idproof, null);
        baseLayout.addView(parentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setTile(getActivity().getResources().getString(R.string.idproofstitle));

        idProofsRecyclerView = (RecyclerView) parentView.findViewById(R.id.idProofsRecyclerView);
        addIdProof = (RelativeLayout) parentView.findViewById(R.id.add_proofs);
        headerLL = (LinearLayout) parentView.findViewById(R.id.headerLL);
        headerView = parentView.findViewById(R.id.divider);

        saveBtn = (Button) parentView.findViewById(R.id.saveBtn);

        addIdProofBottomView = (RelativeLayout) parentView.findViewById(R.id.add_proofs_bottom);
        addIdProofBottomView.setVisibility(View.GONE);

        addIdProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayIdProofsDialog();
            }
        });

        addIdProofBottomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayIdProofsDialog();
            }
        });


         idProofsData = mainData = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("12"));

        identityProofsList = (List<IdentityProof>) DataManager.getInstance().getDataFromManager(DataManager.ID_PROOFS_DATA);

        if (identityProofsList == null && (isFromFollowUp() || isFromCropMaintenance() || isFromConversion())) {
            identityProofsList = (List<IdentityProof>) dataAccessHandler.getSelectedIdProofsData(Queries.getInstance().getFarmerIdentityProof(CommonConstants.FARMER_CODE), 1);
        }

        if (null == identityProofsList) {
            identityProofsList = new ArrayList<>();
        } else {
            idProofsRecyclerView.setVisibility(View.VISIBLE);
        }

        idProofsRecyclerView.setHasFixedSize(true);
        idProofsListAdapter = new IdProofsListAdapter(getActivity(), identityProofsList, mainData);
        idProofsListAdapter.setIdProofsClickListener(ConversionIDProofFragment.this);
        idProofsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        idProofsRecyclerView.setAdapter(idProofsListAdapter);
//        filterIdProofs();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().addData(DataManager.ID_PROOFS_DATA, identityProofsList);
                updateUiListener.updateUserInterface(0);
                getFragmentManager().popBackStack();

            /*    DataSavingHelper.saveIdProofsData(getActivity(), new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        if (success) {
                            ProgressBar.hideProgressBar();
                            UiUtils.showCustomToastMessage("Conversion Details Data updated successfully", getActivity(), 0);

                        } else {
                            ProgressBar.hideProgressBar();
                            com.oilpalm3f.mainapp.cloudhelper.Log.pushExceptionToCrashlytics(new OilPalmException("Data saving failed in conversion "+msg+"-"+result));
                            UiUtils.showCustomToastMessage("Data saving failed "+msg+"-"+result, getActivity(), 1);
                        }
                    }
                });*/

            }
        });
    }

    public void filterIdProofs() {
        for (IdentityProof identityProof : identityProofsList) {
            idProofsData.remove(String.valueOf(identityProof.getIdprooftypeid()));
        }
    }

    //Enter Id Proof Details Dialog
    protected void displayIdProofsDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View promptView = layoutInflater.inflate(R.layout.dialog_idproof, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText aadhar_edt = (EditText) promptView.findViewById(R.id.idproofsEdt);
        final TextView text_idproof = (TextView)promptView.findViewById(R.id.idproof);

        filterIdProofs();
        mCurrentPhotoPath = "";
        idproofImageView = promptView.findViewById(R.id.idproofsImageV);
        idproof = (Spinner) promptView.findViewById(R.id.idProofsSpin);


        idproof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aadhar_edt.setText("");
                String selectedProof = ""+idproof.getSelectedItem().toString();
                aadhar_edt.setInputType(selectedProof.contains("Aadhar") ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT) ;

                if(selectedProof.contains("Driving"))
                {
                    aadhar_edt.setHint("Driving Licience Number");
                    text_idproof.setText("Capture Driving Licience ");
                    aadhar_edt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(16),filter});


                }
                else if(selectedProof.contains("PAN"))
                {
                    aadhar_edt.setHint("PAN Card Number");
                    text_idproof.setText("Capture PAN Card");
                    aadhar_edt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10),filter});

                }
                else if(selectedProof.contains("Aadhar"))
                {
                    aadhar_edt.setHint("Aadhar Number");
                    text_idproof.setText("Capture Aadhar Card");
                    aadhar_edt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(12),filter});

                }
                else if(selectedProof.contains("Passport"))
                {
                   // aadhar_edt.setKeyListener(DigitsKeyListener.getInstance("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
                    //aadhar_edt.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
                    aadhar_edt.setHint("PassPort Number");
                    text_idproof.setText("Capture Passport Image");
                    aadhar_edt.setInputType(InputType.TYPE_CLASS_NUMBER);
                    aadhar_edt.setInputType(InputType.TYPE_CLASS_TEXT);
                    aadhar_edt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10),filter});
                }else if(selectedProof.contains("Voter")){
                    aadhar_edt.setHint("VoterId Number");
                    text_idproof.setText("Capture Voter Card");
                    aadhar_edt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10),filter});
                }


                aadhar_edt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(s.toString().length()>=10){
                            data = dataAccessHandler.getFarmerListforPersonalDetails(Queries.getInstance().getIdProofNumber(s.toString())) ;
                            if(data.isEmpty()){

                            }else {
                                Dialog dialog = new Dialog(getActivity());
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.alertdilog_famer_number);

                                RecyclerView recyclerView =dialog.findViewById(R.id.recycler_alert_view);
                                Button cancel =dialog.findViewById(R.id.button_farmer);
                                TextView duplicateText = dialog.findViewById(R.id.duplicate);

                                duplicateText.setText("Duplicate IDProof(s) Found");

                                farmerViewDetailsAdapter = new FarmerViewDetailsAdapter(getActivity(),data);
                                recyclerView.setAdapter(farmerViewDetailsAdapter);
                                farmerViewDetailsAdapter.setDuplicateFarmers(ConversionIDProofFragment.this);

                                cancel.setOnClickListener(v -> {
                                    dialog.dismiss();
                                });


                                dialog.show();

                            }



                        }


                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        idproof.setAdapter(UiUtils.createAdapter(getActivity(), idProofsData, "Id proof"));

        final Button SaveBtn = (Button) promptView.findViewById(R.id.SaveBtn);
        final Button CancelBtn = (Button) promptView.findViewById(R.id.CancelBtn);


        //Save Btn on Click Listener & Validations
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aadharStr = aadhar_edt.getText().toString();
                aadharStr = aadhar_edt.getText().toString();
                if (!CommonUtils.isEmptySpinner(idproof)) {
                    if (!TextUtils.isEmpty(aadharStr)) {
                        String selectedProof = idproof.getSelectedItem().toString();

                        if (TextUtils.isEmpty(selectedProof)) {
                            UiUtils.showCustomToastMessage("Please select id proof", getActivity(), 1);
                            return;
                        }
                        if (selectedProof.equalsIgnoreCase(getResources().getString(R.string.adhar_number))) {
                            if (!TextUtils.isDigitsOnly(aadharStr)) {
                                UiUtils.showCustomToastMessage("Enter only numbers", getActivity(), 1);
                                return;
                            }
                            if (aadharStr.length() < 12) {
                                UiUtils.showCustomToastMessage("Enter proper Aadhar card number", getActivity(), 1);
                                return;
                            } else {
                                saveIdProofsData();
                            }
                        } else if (selectedProof.equalsIgnoreCase(getResources().getString(R.string.pancard_number))) {
                            if (aadharStr.length() < 10) {
                                UiUtils.showCustomToastMessage("Enter proper PAN card number", getActivity(), 1);
                                return;
                            } else {
                                saveIdProofsData();
                            }
                        } else if (selectedProof.equalsIgnoreCase(getResources().getString(R.string.drive_number))) {
                            if (aadharStr.length() < 16) {
                                UiUtils.showCustomToastMessage("Enter proper Driving License number", getActivity(), 1);
                                return;
                            } else {
                                saveIdProofsData();
                            }
                        } else {
                          saveIdProofsData();
                        }
                    } else {
                        UiUtils.showCustomToastMessage("Please enter the Idproof detail", getActivity(), 1);
                    }
                } else {
                    UiUtils.showCustomToastMessage("Please select IdProof", getActivity(), 1);
                }
            }

            private void saveIdProofsData() {
                if (identityProofsList == null || identityProofsList.isEmpty()) {
                    startAnimation();
                }
                updateIdProofsAdapter(idproof.getSelectedItem().toString(), aadharStr,mCurrentPhotoPath);
                alert.cancel();
            }

        });

        idproofImageView.setOnClickListener(v -> {
            aadharStr = aadhar_edt.getText().toString();
            if (!TextUtils.isEmpty(aadharStr)){
                takeIdProofImage();
            }else {
                UiUtils.showCustomToastMessage("Please Enter IdProof Number ", getActivity(), 1);
            }

        });

        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();

            }
        });
        // create an alert dialog
        alert = alertDialogBuilder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    //Image Handling methods
    private void takeIdProofImage() {
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
        File pictureDirectory = new File(CommonUtils.get3FFileRootPath() + "3F_Pictures/" + "FarmerIdProofs");
        if (!pictureDirectory.exists()) {
            pictureDirectory.mkdirs();
        }

        File finalFile = new File(pictureDirectory,  aadharStr + CommonConstants.JPEG_FILE_SUFFIX);
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
        int targetW = idproofImageView.getWidth();
        int targetH = idproofImageView.getHeight();

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
        idproofImageView.setImageBitmap(bitmap);

        idproofImageView.setVisibility(View.VISIBLE);
        isImage = true;
        idproofImageView.invalidate();
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
    public void onEditClicked(int position) {
        Log.v(LOG_TAG, "@@@ edit clicked " + position);
         showEditDialog(position);
    }

    @Override
    public void onDeleteClicked(int position) {
        Log.v(LOG_TAG, "@@@ delete clicked " + position);
        identityProofsList.remove(position);
        if(identityProofsList.size()==0)
        {
            saveBtn.setVisibility(View.GONE);
            DataManager.getInstance().addData(DataManager.ID_PROOFS_DATA, identityProofsList);
        }
        idProofsListAdapter.notifyDataSetChanged();
    }

    public void startAnimation() {
        Animation logoMoveAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_anim);
        logoMoveAnimation.setFillAfter(true);
        logoMoveAnimation.setFillEnabled(true);
        addIdProof.startAnimation(logoMoveAnimation);
        logoMoveAnimation.setAnimationListener(animationInListener);
        headerLL.setVisibility(View.VISIBLE);
        headerView.setVisibility(View.VISIBLE);
    }

    public void updateIdProofsAdapter(final String idProofName, final String enteredNumber, final String idproofImage) {
        String idProofId = CommonUtils.getKeyFromValue(mainData, idProofName);
        IdentityProof identityProof = new IdentityProof();
        identityProof.setIdprooftypeid(Integer.parseInt(idProofId));
        identityProof.setIdproofnumber(enteredNumber);
        identityProof.setFileName(enteredNumber);
        identityProof.setFileLocation(idproofImage);
        identityProof.setFileExtension(".jpg");
        identityProofsList.add(identityProof);
        idProofsRecyclerView.setVisibility(View.VISIBLE);
        mainData = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("12"));
        idProofsListAdapter.updateData(identityProofsList, mainData);
        saveBtn.setVisibility(View.VISIBLE);
    }

    Animation.AnimationListener animationInListener
            = new Animation.AnimationListener() {

        @Override
        public void onAnimationEnd(Animation animation) {
            addIdProofBottomView.setVisibility(View.VISIBLE);
            addIdProof.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationStart(Animation animation) {

        }
    };

    public void showEditDialog(final int position) {
        mainData = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("12"));
        final EditText idEdit = new EditText(getActivity());
        final String title = mainData.get(String.valueOf(identityProofsList.get(position).getIdprooftypeid()));
        idEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        idEdit.setText(identityProofsList.get(position).getIdproofnumber());
        @SuppressLint("RestrictedApi") final AlertDialog.Builder idProofsBuilder = new AlertDialog.Builder(getActivity())
                .setTitle("Edit")
                .setMessage(title)
                .setView(idEdit, 20, 0, 20, 0)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        final AlertDialog idProofsDialog = idProofsBuilder.create();
        idProofsDialog.setCancelable(false);
        idProofsDialog.setCanceledOnTouchOutside(false);
        idProofsDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button muteBtn = idProofsDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                muteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(idEdit.getText().toString())) {
                            if (!TextUtils.isEmpty(title) && title.equalsIgnoreCase(getResources().getString(R.string.adhar_number))
                                    && !TextUtils.isDigitsOnly(idEdit.getText().toString())) {
                                Toast.makeText(getActivity(), "Adhar card accepts only numbers", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            IdentityProof identityProof = new IdentityProof();
                            identityProof.setIdprooftypeid(identityProofsList.get(position).getIdprooftypeid());
                            identityProof.setIdproofnumber(idEdit.getText().toString());
                            identityProof.setFarmercode(CommonConstants.FARMER_CODE);
                            identityProof.setIsActive(1);
                            identityProofsList.set(position, identityProof);
                            idProofsListAdapter.updateData(identityProofsList, mainData);
                            idProofsDialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Please enter id proof value", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        idProofsDialog.show();
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
