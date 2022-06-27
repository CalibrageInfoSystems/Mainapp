package com.oilpalm3f.mainapp.areaextension;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.oilpalm3f.mainapp.BuildConfig;
import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUiUtils;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.common.FiscalDate;
import com.oilpalm3f.mainapp.cropmaintenance.CommonUtilsNavigation;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.Address;
import com.oilpalm3f.mainapp.dbmodels.ExistingFarmerData;
import com.oilpalm3f.mainapp.dbmodels.Farmer;
import com.oilpalm3f.mainapp.dbmodels.FileRepository;
import com.oilpalm3f.mainapp.farmersearch.SearchFarmerScreen;
import com.oilpalm3f.mainapp.ui.RecyclerItemClickListener;
import com.oilpalm3f.mainapp.uihelper.InteractiveScrollView;
import com.oilpalm3f.mainapp.utils.ImageUtility;
import com.oilpalm3f.mainapp.utils.UiUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;


//Used for Personal Details
public class PersonalDetailsFragment extends Fragment implements RecyclerItemClickListener {

    public static final int REQUEST_CAM_PERMISSIONS = 1;
    public static final int REQUEST_UPDATE_PERSONAL_DETAILS = 0;
    private static final String LOG_TAG = PersonalDetailsFragment.class.getName();
    private static final int CAMERA_REQUEST = 1888;
    public ImageView scrollBottomIndicator;
    private TextView farmerCode;
    private Spinner caste_spinner, source_of_contact_spinner, titleSpinner, genderSpin, anualIncomeSpin, educationdetailsSpin;
    private ImageView farmerImage, farmerIcon;
    private EditText farmer_f_name, farmer_m_name, farmer_last_name, husbandName, motherName, dob, age, primary_contactno, secondary_contactno, address1, address2, landmark, pincode, emailAddress, pocNumber;
    private Button farmerSaveBtn;
    private String f_f_nameString, f_m_nameString, f_l_nameString, husbandString, motherString, ageString, p_contactString,
            address1String, address2String, landmarkString, emailStr, casteCode, s_contactString, pincodeString;
    private DataAccessHandler dataAccessHandler;
    private LinkedHashMap<String, String> casteDataMap, educationDatamap, source_of_contactDataMap, titleDataMap, genderDataMap, castDataMap, anualIncomeMap;
    private Calendar myCalendar = Calendar.getInstance();
    LinearLayout secondLayout;
    private Bitmap userImageData;
    private File fileToUpload;
    private LinearLayout parentLayout;
    private byte[] imageData = null;
    private String agebeforetxt, ageaftertxt;
    private View rootView;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private InteractiveScrollView interactiveScrollView;
    private ActionBar actionBar;
    private UpdateUiListener updateUiListener;
    private Farmer savedFarmerData = null;
    private Address savedAddressData = null;
    private FileRepository savedPictureData = null;
    private boolean isUpdateData = false;
    private String mCurrentPhotoPath;
    private String globalpincode = "";
    private boolean isImage = false;
    private byte[] bytes = null;
    private  int fiscalYearStartMonth;
    public int financialYear;
    private String days = "";
    private String financalSubStringYear = "";
    ArrayList<ExistingFarmerData> data;
    private  FarmerViewDetailsAdapter farmerViewDetailsAdapter;

    public PersonalDetailsFragment() {
        // Required empty public constructor
    }

    //Regex for Validating Email
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void setUpdateUiListener(UpdateUiListener updateUiListener) {
        this.updateUiListener = updateUiListener;
    }

    //Initializing the Class
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.farmer_personal_details_screen, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(activity.getResources().getString(R.string.personal_details));
        //  Financial Year..............
        final Calendar calendar = Calendar.getInstance();
        final FiscalDate fiscalDate = new FiscalDate(calendar);
         financialYear = fiscalDate.getFiscalYear();
        final String financialStartingMonth = fiscalDate.financialYearDay(calendar);

        initializeUI();

        return rootView;
    }


//Initializing the UI
    public void initializeUI() {

        scrollBottomIndicator = rootView.findViewById(R.id.bottomScroll);
        interactiveScrollView = rootView.findViewById(R.id.scrollView);
        scrollBottomIndicator.setVisibility(View.VISIBLE);
        dataAccessHandler = new DataAccessHandler(getActivity());
        parentLayout = rootView.findViewById(R.id.parent);
        farmerCode = rootView.findViewById(R.id.farmerCode);
        farmer_f_name = rootView.findViewById(R.id.farmer_f_name);
        farmer_m_name = rootView.findViewById(R.id.farmer_m_name);
        farmer_last_name = rootView.findViewById(R.id.farmer_last_name);
        husbandName = rootView.findViewById(R.id.husbandName);
        emailAddress = rootView.findViewById(R.id.farmerEmail);
        motherName = rootView.findViewById(R.id.motherName);
        dob = rootView.findViewById(R.id.dob);
        age = rootView.findViewById(R.id.age);
        primary_contactno = rootView.findViewById(R.id.primary_contactno);
        secondary_contactno = rootView.findViewById(R.id.secondary_contactno);
        address1 = rootView.findViewById(R.id.address1);
        address2 = rootView.findViewById(R.id.address2);
        landmark = rootView.findViewById(R.id.landmark);
        pincode = rootView.findViewById(R.id.pincode);
        farmerSaveBtn = rootView.findViewById(R.id.farmerSaveBtn);
        farmerImage = rootView.findViewById(R.id.farmer_image);
        farmerIcon = rootView.findViewById(R.id.farmer_icon);
        caste_spinner = rootView.findViewById(R.id.castSpin);
        source_of_contact_spinner = rootView.findViewById(R.id.source_of_contact_spinner);
        titleSpinner = rootView.findViewById(R.id.titleSpinner);
        genderSpin = rootView.findViewById(R.id.genderSpin);
        anualIncomeSpin = rootView.findViewById(R.id.anualIncomeSpin);
        educationdetailsSpin = rootView.findViewById(R.id.educationdetailsSpin);
        secondLayout=rootView.findViewById(R.id.secondLayout);

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
              String currentdate = CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_3);
                String financalDate = "01/04/"+String.valueOf(financialYear);
                Date date1 = dateFormat.parse(currentdate);
                Date date2 = dateFormat.parse(financalDate);
                long diff = date1.getTime() - date2.getTime();
               String noOfDays = String.valueOf(TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS)+1);
                days = StringUtils.leftPad(noOfDays,3,"0");
               Log.v(LOG_TAG,"days -->"+days+financialYear+diff);

            }catch (Exception e){
                e.printStackTrace();
            }
        if (CommonConstants.REGISTRATION_SCREEN_FROM.equalsIgnoreCase(CommonConstants.REGISTRATION_SCREEN_FROM_CP_MAINTENANCE)) {
          secondLayout.setVisibility(View.GONE);
        }else {
            secondLayout.setVisibility(View.VISIBLE);
        }


        bindMasterData();
        handleListeners();

        savedFarmerData = (Farmer) DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS);
        savedAddressData = (Address) DataManager.getInstance().getDataFromManager(DataManager.FARMER_ADDRESS_DETAILS);
        savedPictureData = (FileRepository) DataManager.getInstance().getDataFromManager(DataManager.FILE_REPOSITORY);

        if (savedFarmerData != null && savedAddressData != null) {
            isUpdateData = true;
            if(savedPictureData==null)
            {
                savedPictureData = new FileRepository();
            }
            bindData();
            if (!CommonUtils.isNewRegistration()) {
                handleViewsEnableAndDisable();
            }
        } else {
            savedAddressData = new Address();
            savedFarmerData = new Farmer();
            savedPictureData = new FileRepository();
            financalSubStringYear = String.valueOf(financialYear).substring(2,4);
            CommonConstants.FARMER_CODE = dataAccessHandler.getGeneratedFarmerCode(Queries.getInstance().getMaxNumberQuery(financalSubStringYear+days),financalSubStringYear+days);
            farmerCode.setText(CommonConstants.FARMER_CODE);
        }

        farmerSaveBtn.setOnClickListener(view -> {
            hideSoftKeyboard();
            if (validate()) {
                saveAddressData();
            }
        });

        primary_contactno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() >= 10)
                {
                    data = dataAccessHandler.getFarmerListforPersonalDetails(Queries.getInstance().getNumber(s.toString())) ;

                    if(data.isEmpty())
                    {

                    }else
                    {
                        Dialog dialog = new Dialog(getActivity());
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.alertdilog_famer_number);
                      //  dialog.getWindow().setLayout(500,500);

                        RecyclerView recyclerView =dialog.findViewById(R.id.recycler_alert_view);
                        Button cancel =dialog.findViewById(R.id.button_farmer);
                        TextView duplicateText = dialog.findViewById(R.id.duplicate);

                        duplicateText.setText("Duplicate Contact(s) Found");

                        farmerViewDetailsAdapter = new FarmerViewDetailsAdapter(getActivity(),data);
                        recyclerView.setAdapter(farmerViewDetailsAdapter);
                        farmerViewDetailsAdapter.setDuplicateFarmers(PersonalDetailsFragment.this);

                        cancel.setOnClickListener(v -> {
                            dialog.dismiss();
                        });


                        dialog.show();
                    }


                }

            }
        });

    }


//Enable/Disabling the Fields based on Registration Type
    private void handleViewsEnableAndDisable() {
        source_of_contact_spinner.setEnabled(false);
        source_of_contact_spinner.setFocusable(false);
        farmerCode.setEnabled(false);
        farmerCode.setFocusable(false);
        titleSpinner.setEnabled(false);
        titleSpinner.setFocusable(false);
        farmer_f_name.setEnabled(false);
        farmer_f_name.setFocusable(false);
        farmer_m_name.setEnabled(false);
        farmer_m_name.setFocusable(false);
        farmer_last_name.setEnabled(false);
        farmer_last_name.setFocusable(false);
        husbandName.setEnabled(false);
        husbandName.setFocusable(false);
        motherName.setEnabled(false);
        motherName.setFocusable(false);
        genderSpin.setEnabled(false);
        genderSpin.setFocusable(false);
        dob.setEnabled(false);
        dob.setFocusable(false);
        age.setEnabled(false);
        age.setFocusable(false);
        caste_spinner.setEnabled(false);
        caste_spinner.setFocusable(false);

        if (CommonUiUtils.isFarmerPhotoTaken(getActivity()) ) {
            farmerImage.setEnabled(false);
            farmerImage.setFocusable(false);
        } else {
            farmerImage.setEnabled(true);
            farmerImage.setFocusable(true);
        }

    }




//Binding Data to Spinners
    public void bindMasterData() {
        educationDatamap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("10"));
        source_of_contactDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getsource_of_contactQuery());
        titleDataMap = dataAccessHandler.getGenericData(Queries.getInstance().gettitleQuery());
        genderDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getgenderQuery());
        castDataMap = dataAccessHandler.getGenericData(Queries.getInstance().getcastQuery());
        anualIncomeMap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeCdDmtData("9"));

        educationdetailsSpin.setAdapter(UiUtils.createAdapter(getActivity(), educationDatamap, "Education"));
        source_of_contact_spinner.setAdapter(UiUtils.createAdapter(getActivity(), source_of_contactDataMap, "Source of Contact"));
        titleSpinner.setAdapter(UiUtils.createAdapter(getActivity(), titleDataMap, getString(R.string.title)));
        genderSpin.setAdapter(UiUtils.createAdapter(getActivity(), genderDataMap, "Gender"));
        caste_spinner.setAdapter(UiUtils.createAdapter(getActivity(), castDataMap, "Category"));
        anualIncomeSpin.setAdapter(UiUtils.createAdapter(getActivity(), anualIncomeMap, "Income"));
        setPinCode();
    }

    //Set Pincode based on Village Id
    public void setPinCode() {
        globalpincode = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getPincode(CommonConstants.villageId));
        if (globalpincode != null && !globalpincode.isEmpty() && globalpincode.length() > 0) {
            pincode.setText(globalpincode);
            pincode.setEnabled(false);
            pincode.setCursorVisible(false);
            pincode.setClickable(false);
            pincode.setTextColor(Color.BLACK);
        }
    }

    //Handling Scroll Listener, Textchanged and Click Listeners
    public void handleListeners() {
        scrollBottomIndicator.setOnClickListener(view -> interactiveScrollView.post(() ->
                interactiveScrollView.fullScroll(ScrollView.FOCUS_DOWN)));
        interactiveScrollView.setOnBottomReachedListener(() -> scrollBottomIndicator.setVisibility(View.GONE));

        interactiveScrollView.setOnTopReachedListener(() -> {
        });

        interactiveScrollView.setOnScrollingListener(() -> {
            android.util.Log.d(LOG_TAG, "onScrolling: ");
            scrollBottomIndicator.setVisibility(View.VISIBLE);
        });


        parentLayout.setOnTouchListener((view, motionEvent) ->
        {
            hideSoftKeyboard();
            return false;
        });

        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println("afteragevalue:::::" + editable.toString());
                ageaftertxt = age.getText().toString().trim();
                if (agebeforetxt != null && agebeforetxt.trim().length() > 0) {
                    if (agebeforetxt.equalsIgnoreCase(ageaftertxt)) {

                    } else {
                        dob.setText("");
                    }
                }

            }
        });

        farmerImage.setOnClickListener(view -> {

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


        });

        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();

            agebeforetxt = getAge(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            age.setText(getAge(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)));
        };
        dob.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));  //date is dateSetListener as per your code in question
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();


        });

        caste_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (casteDataMap != null && casteDataMap.size() > 0 && caste_spinner.getSelectedItemPosition() != 0)
                    casteCode = casteDataMap.keySet().toArray(new String[casteDataMap.size()])[i - 1];
                Log.v(LOG_TAG, "@@@ Selected State " + casteCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //Below methods are used to handle the Image
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

    private File createImageFile() {
        File pictureDirectory = new File(CommonUtils.get3FFileRootPath() + "3F_Pictures/" + "FarmerPhotos");
        if (!pictureDirectory.exists()) {
            pictureDirectory.mkdirs();
        }

        File finalFile = new File(pictureDirectory, CommonConstants.FARMER_CODE + CommonConstants.JPEG_FILE_SUFFIX);
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


        } // switch
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
        int targetW = farmerImage.getWidth();
        int targetH = farmerImage.getHeight();

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
        farmerImage.setImageBitmap(bitmap);

        farmerImage.setVisibility(View.VISIBLE);
        farmerIcon.setVisibility(View.GONE);
        isImage = true;
        farmerImage.invalidate();
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


    //Binding Data to the Fields
    private void bindData() {
        CommonConstants.villageId = String.valueOf(savedAddressData.getVillageid());
        CommonConstants.mandalId = String.valueOf(savedAddressData.getMandalid());
        CommonConstants.districtId = String.valueOf(savedAddressData.getDistictid());
        CommonConstants.stateId = String.valueOf(savedAddressData.getStateid());

        CommonConstants.FARMER_CODE = savedFarmerData.getCode();
        farmerCode.setText(CommonConstants.FARMER_CODE);
        farmer_f_name.setText("" + savedFarmerData.getFirstname());
        farmer_m_name.setText("" + savedFarmerData.getMiddlename());
        farmer_last_name.setText("" + savedFarmerData.getLastname());
        husbandName.setText("" + savedFarmerData.getGuardianname());
        motherName.setText("" + savedFarmerData.getMothername());

        if(savedFarmerData.getContactnumber()!=null)
        primary_contactno.setText("" + savedFarmerData.getContactnumber());
        if(savedFarmerData.getMobilenumber().equalsIgnoreCase("null")){
            secondary_contactno.setText("" );
        }else {
            secondary_contactno.setText("" + savedFarmerData.getMobilenumber());
        }
        if (savedFarmerData.getDOB().equalsIgnoreCase("null")){
            dob.setText("");
        }else {
            dob.setText("" + savedFarmerData.getDOB());
        }
        age.setText("" + savedFarmerData.getAge());
        if(savedFarmerData.getEmail().equalsIgnoreCase("null")){
            emailAddress.setText("");
        }else {
            emailAddress.setText("" + savedFarmerData.getEmail());
        }
        address1.setText("" + savedAddressData.getAddressline1());
        address2.setText("" + savedAddressData.getAddressline2());
        if(savedAddressData.getLandmark().equalsIgnoreCase("null")){
            landmark.setText("");
        }else {
            landmark.setText("" + savedAddressData.getLandmark());
        }

        pincode.setText("" + savedAddressData.getPincode());

        source_of_contact_spinner.setSelection(CommonUtilsNavigation.getvalueFromHashMap(source_of_contactDataMap, savedFarmerData.getSourceofcontacttypeid()));
        educationdetailsSpin.setSelection(savedFarmerData.getEducationtypeid() == null ? 0 : CommonUtilsNavigation.getvalueFromHashMap(educationDatamap, savedFarmerData.getEducationtypeid()));
        genderSpin.setSelection(CommonUtilsNavigation.getvalueFromHashMap(genderDataMap, savedFarmerData.getGendertypeid()));

        titleSpinner.setSelection(savedFarmerData.getTitletypeid() == null ? 0 : CommonUtilsNavigation.getvalueFromHashMap(titleDataMap, savedFarmerData.getTitletypeid()));
        caste_spinner.setSelection(savedFarmerData.getCategorytypeid() == null ? 0 : CommonUtilsNavigation.getvalueFromHashMap(castDataMap, savedFarmerData.getCategorytypeid()));
        anualIncomeSpin.setSelection(savedFarmerData.getAnnualincometypeid() == null ? 0 : CommonUtilsNavigation.getvalueFromHashMap(anualIncomeMap, savedFarmerData.getAnnualincometypeid()));

        if (savedPictureData == null) {
            savedPictureData = dataAccessHandler.getSelectedFileRepository(Queries.getInstance().getSelectedFileRepositoryQuery(savedFarmerData.getCode(), 193));
        }
        if (savedPictureData != null && savedPictureData.getPicturelocation() != null) {
            mCurrentPhotoPath = savedPictureData.getPicturelocation();
            loadImageFromStorage(mCurrentPhotoPath);
            farmerImage.invalidate();
        }


        if (CommonUtils.isFromCropMaintenance()) {

            primary_contactno.setEnabled(false);
            primary_contactno.setFocusable(false);

        }
    }

    //Saving the Address
    public void saveAddressData() {
        savedAddressData.setAddressline1(address1String);
        savedAddressData.setAddressline2(address2String);
        savedAddressData.setAddressline3("");
        savedAddressData.setLandmark(landmarkString);
        savedAddressData.setCode(CommonConstants.ADDRESS_CODE_PREFIX + CommonConstants.FARMER_CODE);
        savedAddressData.setVillageid(Integer.parseInt(CommonConstants.villageId));
        savedAddressData.setMandalid(Integer.parseInt(CommonConstants.mandalId));
        savedAddressData.setDistictid(Integer.parseInt(CommonConstants.districtId));
        savedAddressData.setStateid(Integer.parseInt(CommonConstants.stateId));
        savedAddressData.setCountryid(Integer.parseInt(CommonConstants.countryID));
        savedAddressData.setPincode(pincodeString.equalsIgnoreCase("") ? 0 : new BigInteger(pincodeString).intValue());
        savedAddressData.setIsactive(1);
        if (CommonConstants.REGISTRATION_SCREEN_FROM.equalsIgnoreCase(CommonConstants.REGISTRATION_SCREEN_FROM_NEW_FARMER)) {
            savedAddressData.setCreatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
            savedAddressData.setCreateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        }
        savedAddressData.setUpdatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
        savedAddressData.setUpdateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        savedAddressData.setServerupdatedstatus(Integer.parseInt(CommonConstants.ServerUpdatedStatus));
        DataManager.getInstance().addData(DataManager.FARMER_ADDRESS_DETAILS, savedAddressData);
        saveFarmerData();
    }

    //Saving the Farmer Data
    public void saveFarmerData() {
        savedFarmerData.setCode(farmerCode.getText().toString());
        CommonConstants.FARMER_CODE = farmerCode.getText().toString();
        savedFarmerData.setCountryid(Integer.parseInt(CommonConstants.countryID));
        savedFarmerData.setRegionid(null);
        savedFarmerData.setStateid(Integer.parseInt(CommonConstants.stateId));
        savedFarmerData.setDistictid(Integer.parseInt(CommonConstants.districtId));
        savedFarmerData.setMandalid(Integer.parseInt(CommonConstants.mandalId));
        savedFarmerData.setVillageid(Integer.parseInt(CommonConstants.villageId));
        savedFarmerData.setSourceofcontacttypeid(Integer.parseInt(CommonUtilsNavigation.getKey(source_of_contactDataMap, source_of_contact_spinner.getSelectedItem().toString())));
        savedFarmerData.setTitletypeid(Integer.parseInt(CommonUtilsNavigation.getKey(titleDataMap, titleSpinner.getSelectedItem().toString())));
        savedFarmerData.setFirstname(f_f_nameString);
        savedFarmerData.setMiddlename(f_m_nameString);
        savedFarmerData.setLastname(f_l_nameString);
        savedFarmerData.setEducationtypeid(educationdetailsSpin.getSelectedItemPosition());
        savedFarmerData.setGuardianname(husbandName.getText().toString());
        if (TextUtils.isEmpty(motherString)){
            savedFarmerData.setMothername("");
        }else {
            savedFarmerData.setMothername(motherString);
        }
        savedFarmerData.setGendertypeid(Integer.parseInt(CommonUtilsNavigation.getKey(genderDataMap, genderSpin.getSelectedItem().toString())));
        savedFarmerData.setMobilenumber(s_contactString);
        savedFarmerData.setContactnumber(p_contactString);
        savedFarmerData.setDOB(dob.getText().toString());
        savedFarmerData.setAge(Integer.parseInt(ageString));
        savedFarmerData.setEmail(emailAddress.getText().toString());
        savedFarmerData.setCategorytypeid(Integer.parseInt(CommonUtilsNavigation.getKey(castDataMap, caste_spinner.getSelectedItem().toString())));
        savedFarmerData.setAnnualincometypeid(anualIncomeSpin.getSelectedItemPosition()==0 ? null:Integer.parseInt(CommonUtilsNavigation.getKey(anualIncomeMap, anualIncomeSpin.getSelectedItem().toString())));
        savedFarmerData.setAddresscode(CommonConstants.ADDRESS_CODE_PREFIX + farmerCode.getText().toString());
        savedFarmerData.setEducationtypeid(educationdetailsSpin.getSelectedItemPosition() == 0 ? null : Integer.parseInt(CommonUtilsNavigation.getKey(educationDatamap, educationdetailsSpin.getSelectedItem().toString())));

        savedFarmerData.setIsactive(1);
        if (CommonConstants.REGISTRATION_SCREEN_FROM.equalsIgnoreCase(CommonConstants.REGISTRATION_SCREEN_FROM_NEW_FARMER)) {
            savedFarmerData.setCreatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
            savedFarmerData.setCreateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        }
        savedFarmerData.setUpdatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
        savedFarmerData.setServerupdatedstatus(0);
        savedFarmerData.setUpdatedDate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        DataManager.getInstance().addData(DataManager.FARMER_PERSONAL_DETAILS, savedFarmerData);
        if (CommonUiUtils.isFarmerPhotoSavedInDB(getActivity()) ) {
          //  savePictureData();
            updateUiListener.updateUserInterface(REQUEST_UPDATE_PERSONAL_DETAILS);
            if (isUpdateData) {
                DataManager.getInstance().addData(DataManager.IS_FARMER_DATA_UPDATED, true);
            } else {
                DataManager.getInstance().addData(DataManager.IS_FARMER_DATA_UPDATED, false);
            }
            CommonConstants.Flags.isFarmersDataUpdated = true;
            getFragmentManager().popBackStack();
        }else {
            if (mCurrentPhotoPath != null && !mCurrentPhotoPath.equalsIgnoreCase("null"))
                savePictureData();
            else {
                updateUiListener.updateUserInterface(REQUEST_UPDATE_PERSONAL_DETAILS);
                if (isUpdateData) {
                    DataManager.getInstance().addData(DataManager.IS_FARMER_DATA_UPDATED, true);
                } else {
                    DataManager.getInstance().addData(DataManager.IS_FARMER_DATA_UPDATED, false);
                }
                CommonConstants.Flags.isFarmersDataUpdated = true;
                getFragmentManager().popBackStack();

            }
        }


    }

    //Saving Image Data
    private void savePictureData() {
        savedPictureData.setFarmercode(CommonConstants.FARMER_CODE);
        savedPictureData.setPlotcode(null);
        savedPictureData.setModuletypeid(193);
        savedPictureData.setFilename(CommonConstants.FARMER_CODE);
        savedPictureData.setPicturelocation(mCurrentPhotoPath);
        savedPictureData.setFileextension(CommonConstants.JPEG_FILE_SUFFIX);
        savedPictureData.setCreatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
        savedPictureData.setCreatedDate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        savedPictureData.setUpdatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
        savedPictureData.setServerUpdatedStatus(0);
        savedPictureData.setIsActive(1);
        savedPictureData.setCropMaintenanceCode(null);
        savedPictureData.setUpdatedDate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        DataManager.getInstance().addData(DataManager.FILE_REPOSITORY, savedPictureData);
        updateUiListener.updateUserInterface(REQUEST_UPDATE_PERSONAL_DETAILS);
        if (isUpdateData) {
            DataManager.getInstance().addData(DataManager.IS_FARMER_DATA_UPDATED, true);
        } else {
            DataManager.getInstance().addData(DataManager.IS_FARMER_DATA_UPDATED, false);
        }
        CommonConstants.Flags.isFarmersDataUpdated = true;
        getFragmentManager().popBackStack();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "@@@ check on resume called");
        loadImageFromStorage(mCurrentPhotoPath);
        farmerImage.invalidate();
    }

    //Loads Image from the storage onResume
    private void loadImageFromStorage(final String path) {
        if (null != savedPictureData) {
            if(CommonUtils.isNetworkAvailable(getActivity())) {
                final String imageUrl = CommonUtils.getImageUrl(savedPictureData);


                Picasso.with(getActivity())
                        .load(imageUrl)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .into(farmerImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                isImage = true;
                            }

                            @Override
                            public void onError() {

                                    Glide.with(getActivity())
                                            .load(path)
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .listener(new RequestListener<String, GlideDrawable>() {
                                                @Override
                                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                                    return false;
                                                }

                                                @Override
                                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                                    return false;
                                                }
                                            })
                                            .into(farmerImage);
                                    isImage = false;

                            }
                        });
            }
            else {
                if (!getActivity().isFinishing()) {
                    Glide.with(getActivity())
                            .load(path)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(farmerImage);
                }
            }
//            Glide.with(getActivity())
//                    .load(Uri.parse("file://" + path))
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .into(farmerImage);
        } else {
            if (!getActivity().isFinishing()) {
                Glide.with(getActivity())
                        .load(path)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(farmerImage);
            }
        }
    }

    /**
     * Hides the soft keyboard
     */

    //Hides Keyboard
    public void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    //Calculates DOB based on Age
    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    //Updates the DOB label
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));

    }


    //Validations of the Fields
    public boolean validate() {

        f_f_nameString = farmer_f_name.getText().toString();
        CommonConstants.farmerFirstName = f_f_nameString;
        f_m_nameString = farmer_m_name.getText().toString();
        CommonConstants.farmerMiddleName = f_m_nameString;
        f_l_nameString = farmer_last_name.getText().toString();
        CommonConstants.farmerLastName = f_l_nameString;
        husbandString = husbandName.getText().toString();
        motherString = motherName.getText().toString();
        ageString = age.getText().toString();
        emailStr = emailAddress.getText().toString();

        p_contactString = primary_contactno.getText().toString();
        CommonConstants.farmerMobileNumber = p_contactString;
        s_contactString = secondary_contactno.getText().toString();
        address1String = address1.getText().toString();
        address2String = address2.getText().toString();
        landmarkString = landmark.getText().toString();
        pincodeString = pincode.getText().toString();

        if (CommonUtils.isEmptySpinner(source_of_contact_spinner)) {
            UiUtils.showCustomToastMessage(getResources().getString(R.string.error_sourceofContract), getActivity(), 0);
            return false;
        }

        if (CommonUtils.isEmptySpinner(titleSpinner)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_title), Toast.LENGTH_SHORT).show();
            UiUtils.showCustomToastMessage(getResources().getString(R.string.error_sourceofContract), getActivity(), 0);
            return false;
        }

        if (TextUtils.isEmpty(f_f_nameString)) {
            // farmer_f_name.setError(getResources().getString(R.string.error_farmer_first_name));
            UiUtils.showCustomToastMessage(getResources().getString(R.string.error_farmer_first_name), getActivity(), 0);
            farmer_f_name.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(f_l_nameString)) {
            // farmer_last_name.setError(getResources().getString(R.string.error_farmer_last_name));
            UiUtils.showCustomToastMessage(getResources().getString(R.string.error_farmer_last_name), getActivity(), 0);
            farmer_last_name.requestFocus();
            return false;
        }
//
//        if (TextUtils.isEmpty(fatherString)) {
//            // fatherName.setError(getResources().getString(R.string.error_husband_name));
//            UiUtils.showCustomToastMessage("Please enter father name", getActivity(), 0);
//            fatherName.requestFocus();
//            return false;
//        }

        if (TextUtils.isEmpty(husbandName.getText().toString())) {
            UiUtils.showCustomToastMessage("Please enter Husband name", getActivity(), 0);
            husbandName.requestFocus();
            return false;
        }



        if (CommonUtils.isEmptySpinner(genderSpin)) {
            UiUtils.showCustomToastMessage("Please Select Gender", getActivity(), 0);
            return false;
        }

        if (TextUtils.isEmpty(p_contactString) || p_contactString.length() < 10) {
            //   primary_contactno.setError(getResources().getString(R.string.error_contact_number));
            UiUtils.showCustomToastMessage(getResources().getString(R.string.error_contact_number), getActivity(), 0);
            primary_contactno.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(ageString)) {
            //    age.setError(getResources().getString(R.string.error_age));
            UiUtils.showCustomToastMessage(getResources().getString(R.string.error_age), getActivity(), 0);
            age.requestFocus();
            return false;
        }

        if (!TextUtils.isEmpty(emailStr) && isValidEmail(emailStr) == false) {
            UiUtils.showCustomToastMessage("Please enter validate email", getActivity(), 0);
            return false;
        }

        if (CommonUtils.isEmptySpinner(caste_spinner)) {
            UiUtils.showCustomToastMessage("Please Select Category", getActivity(), 0);
            caste_spinner.requestFocus();
            return false;
        }

        if (CommonUtils.isEmptySpinner(anualIncomeSpin)) {
            UiUtils.showCustomToastMessage("Please Select Annual income", getActivity(), 0);
            return false;
        }

        /*if (CommonUtils.isEmptySpinner(educationdetailsSpin)) {
            UiUtils.showCustomToastMessage("Please Select education", getActivity(), 0);
            return false;
        }*/

        if (TextUtils.isEmpty(address1String)) {
            // address1.setError(getResources().getString(R.string.error_address));
            UiUtils.showCustomToastMessage("Please Select door No", getActivity(), 0);
            address1.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(address2String)) {
            //   address2.setError(getResources().getString(R.string.error_address));
            Toast.makeText(getActivity(), "Please enter Street No", Toast.LENGTH_SHORT).show();
            address2.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(landmarkString)) {
            //  landmark.setError(getResources().getString(R.string.error_landmark));
            UiUtils.showCustomToastMessage(getResources().getString(R.string.error_landmark), getActivity(), 0);
            landmark.requestFocus();
            return false;
        }

        if (CommonUtils.isFromConversion()) {
            if (TextUtils.isEmpty(mCurrentPhotoPath) || mCurrentPhotoPath.equalsIgnoreCase("null")) {
                UiUtils.showCustomToastMessage("Please Capture Photo", getActivity(), 1);
                return false;
            }
        }

        return true;

    }

    //getting bitmap from the image path
    public byte[] getBytes() {
        if (mCurrentPhotoPath != null) {
            File imagefile = new File(mCurrentPhotoPath);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(imagefile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                bytes = null;
            }
            try {
                Bitmap bm = BitmapFactory.decodeStream(fis);
                getBytesFromBitmap(bm);
            } catch (Exception e) {
                bytes = null;
            }
        }
        return bytes;
    }

    //on farmer selected
    @Override
    public void onItemSelected(int position) {
        Intent intent = new Intent(getActivity(), SearchFarmerScreen.class);
        intent.putExtra("Code",data.get(position).getCode());
        startActivity(intent);

    }

/*
    @Override
    public int calculateFiscalYear(Calendar calendarDate) {
        return 0;
    }

    @Override
    public long calculateFiscalMonth(Calendar calendarDate) {
        return 0;
    }

    @Override
    public long calculateFiscalWeekOfYear(Calendar calendarDate) {
        return 0;
    }

    @Override
    public final long calculateFiscalDayOfYear(final Calendar calendarDate) {
        final Calendar fiscalYearStartDate = createMatchingFiscalYearStartDate(calendarDate);

        return ChronoUnit.DAYS.between(fiscalYearStartDate, calendarDate) + 1;
    }

    @Override
    public Calendar calculateCalendarDate(int fiscalYear, int fiscalMonth, int fiscalDay) {
        return null;
    }


    private Calendar createMatchingFiscalYearStartDate(final Calendar calendarDate) {
        if (fiscalYearStartMonth <= Calendar.MONTH) {
            return Calendar.of(Calendar.YEAR, fiscalYearStartMonth, 1);
        }

        return Calendar.of(calendarDate.getYear() - 1, fiscalYearStartMonth, 1);
    }*/
}
