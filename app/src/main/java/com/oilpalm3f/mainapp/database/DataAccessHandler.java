package com.oilpalm3f.mainapp.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Pair;

import com.oilpalm3f.mainapp.FaLogTracking.LocationTracker;
import com.oilpalm3f.mainapp.alerts.AlertsPlotInfo;
import com.oilpalm3f.mainapp.alerts.AlertsVisitsInfo;
import com.oilpalm3f.mainapp.alerts.MissingTressInfo;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.datasync.refreshsyncmodel.FarmerFFBHarvestDetails;
import com.oilpalm3f.mainapp.datasync.refreshsyncmodel.ImageDetails;
import com.oilpalm3f.mainapp.dbmodels.ActivityLog;
import com.oilpalm3f.mainapp.dbmodels.Address;
import com.oilpalm3f.mainapp.dbmodels.Alerts;
import com.oilpalm3f.mainapp.dbmodels.BasicHarvestorDetails;
import com.oilpalm3f.mainapp.dbmodels.ComplaintRepository;
import com.oilpalm3f.mainapp.dbmodels.ComplaintRepositoryRefresh;
import com.oilpalm3f.mainapp.dbmodels.ComplaintStatusHistory;
import com.oilpalm3f.mainapp.dbmodels.ComplaintTypeXref;
import com.oilpalm3f.mainapp.dbmodels.Complaints;
import com.oilpalm3f.mainapp.dbmodels.CookingOil;
import com.oilpalm3f.mainapp.dbmodels.CropMaintenanceHistory;
import com.oilpalm3f.mainapp.dbmodels.DigitalContract;
import com.oilpalm3f.mainapp.dbmodels.Disease;
import com.oilpalm3f.mainapp.dbmodels.ExistingFarmerData;
import com.oilpalm3f.mainapp.dbmodels.Farmer;
import com.oilpalm3f.mainapp.dbmodels.FarmerBank;
import com.oilpalm3f.mainapp.dbmodels.FarmerBankRefresh;
import com.oilpalm3f.mainapp.dbmodels.FarmerHistory;
import com.oilpalm3f.mainapp.dbmodels.Fertilizer;
import com.oilpalm3f.mainapp.dbmodels.FertilizerProvider;
import com.oilpalm3f.mainapp.dbmodels.FileRepository;
import com.oilpalm3f.mainapp.dbmodels.FileRepositoryRefresh;
import com.oilpalm3f.mainapp.dbmodels.FollowUp;
import com.oilpalm3f.mainapp.dbmodels.GeoBoundaries;
import com.oilpalm3f.mainapp.dbmodels.Harvest;
import com.oilpalm3f.mainapp.dbmodels.HarvestorDataModel;
import com.oilpalm3f.mainapp.dbmodels.HarvestorVisitDetails;
import com.oilpalm3f.mainapp.dbmodels.HarvestorVisitHistory;
import com.oilpalm3f.mainapp.dbmodels.Healthplantation;
import com.oilpalm3f.mainapp.dbmodels.IdentityProof;
import com.oilpalm3f.mainapp.dbmodels.IdentityProofFileRepositoryXref;
import com.oilpalm3f.mainapp.dbmodels.IdentityProofRefresh;
import com.oilpalm3f.mainapp.dbmodels.InterCropPlantationXref;
import com.oilpalm3f.mainapp.dbmodels.KrasDataToDisplay;
import com.oilpalm3f.mainapp.dbmodels.LandlordBank;
import com.oilpalm3f.mainapp.dbmodels.LandlordIdProof;
import com.oilpalm3f.mainapp.dbmodels.MarketSurvey;
import com.oilpalm3f.mainapp.dbmodels.NeighbourPlot;
import com.oilpalm3f.mainapp.dbmodels.NurserySaplingDetails;
import com.oilpalm3f.mainapp.dbmodels.Nutrient;
import com.oilpalm3f.mainapp.dbmodels.Ownershipfilerepository;
import com.oilpalm3f.mainapp.dbmodels.Pest;
import com.oilpalm3f.mainapp.dbmodels.PestChemicalXref;
import com.oilpalm3f.mainapp.dbmodels.Plantation;
import com.oilpalm3f.mainapp.dbmodels.PlantationFileRepositoryXref;
import com.oilpalm3f.mainapp.dbmodels.Plot;
import com.oilpalm3f.mainapp.dbmodels.PlotCurrentCrop;
import com.oilpalm3f.mainapp.dbmodels.PlotFFBDetails;
import com.oilpalm3f.mainapp.dbmodels.PlotGradingDetails;
import com.oilpalm3f.mainapp.dbmodels.PlotIrrigationTypeXref;
import com.oilpalm3f.mainapp.dbmodels.PlotLandlord;
import com.oilpalm3f.mainapp.dbmodels.RecommndFertilizer;
import com.oilpalm3f.mainapp.dbmodels.Referrals;
import com.oilpalm3f.mainapp.dbmodels.SoilResource;
import com.oilpalm3f.mainapp.dbmodels.Uprootment;
import com.oilpalm3f.mainapp.dbmodels.UserDetails;
import com.oilpalm3f.mainapp.dbmodels.UserSync;
import com.oilpalm3f.mainapp.dbmodels.VisitLog;
import com.oilpalm3f.mainapp.dbmodels.WaterResource;
import com.oilpalm3f.mainapp.dbmodels.Weed;
import com.oilpalm3f.mainapp.dbmodels.WhiteFlyAssessment;
import com.oilpalm3f.mainapp.dbmodels.YieldAssessment;
import com.oilpalm3f.mainapp.helper.CustomCursor;
import com.oilpalm3f.mainapp.helper.PrefUtil;
import com.oilpalm3f.mainapp.dbmodels.BasicFarmerDetails;
import com.oilpalm3f.mainapp.dbmodels.ComplaintsDetails;
import com.oilpalm3f.mainapp.dbmodels.PlotDetailsObj;
import com.oilpalm3f.mainapp.prospectiveFarmers.ProspectivePlotsModel;
import com.oilpalm3f.mainapp.transportservice.Village;
import com.oilpalm3f.mainapp.utils.ImageUtility;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
@SuppressLint("Recycle")
public class DataAccessHandler<T> {

    private static final String LOG_TAG = DataAccessHandler.class.getName();

    private Context context;
    private SQLiteDatabase mDatabase;
    private String var = "";
    String queryForLookupTable = "select Name from LookUp where id=" + var;
    private int value;

    public DataAccessHandler() {

    }

    SimpleDateFormat simpledatefrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    String currentTime = simpledatefrmt.format(new Date());


    public DataAccessHandler(final Context context) {
        this.context = context;
        try {
            mDatabase = Palm3FoilDatabase.openDataBaseNew();
            DataBaseUpgrade.upgradeDataBase(context, mDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public DataAccessHandler(final Context context, boolean firstTime) {
        this.context = context;
        try {
            mDatabase = Palm3FoilDatabase.openDataBaseNew();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //get  data using query
    public LinkedHashMap<String, String> getGenericData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        LinkedHashMap<String, String> mGenericData = new LinkedHashMap<>();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData.put(genericDataQuery.getString(0), genericDataQuery.getString(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }

    public LinkedHashMap<String, String> getMoreGenericData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        LinkedHashMap<String, String> mGenericData = new LinkedHashMap<>();
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData.put(genericDataQuery.getString(0), genericDataQuery.getString(1) + "-" + genericDataQuery.getString(2) + "-" + genericDataQuery.getString(3));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }


    public LinkedHashMap<String, String> getFarmerDetailsData(String query) {
        LinkedHashMap linkedHashMap = new LinkedHashMap<String, String>();
        Cursor cursor = mDatabase.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String key = cursor.getString(cursor.getColumnIndex("Code"));
                    String value = cursor.getString(cursor.getColumnIndex("FirstName"))
                            + "-" + cursor.getString(cursor.getColumnIndex("ContactNumber"));

                    linkedHashMap.put(key, value);


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linkedHashMap;

    }


    //Saving Usersync data
    public long addUserSync(UserSync userSync) {


        ContentValues contentValues = new ContentValues();
        contentValues.put("UserId", userSync.getUserId());
        contentValues.put("App", userSync.getApp());
        contentValues.put("Date", userSync.getDate());
        contentValues.put("MasterSync", userSync.getMasterSync());
        contentValues.put("TransactionSync", userSync.getTransactionSync());
        contentValues.put("ResetData", userSync.getResetData());
        contentValues.put("IsActive", userSync.getIsActive());
        contentValues.put("CreatedByUserId", userSync.getCreatedByUserId());
        contentValues.put("CreatedDate", userSync.getCreatedDate());
        contentValues.put("UpdatedByUserId", userSync.getUpdatedByUserId());
        contentValues.put("UpdatedDate", userSync.getUpdatedDate());
        contentValues.put("ServerUpdatedStatus", userSync.getServerUpdatedStatus());
        return mDatabase.insert("UserSync", null, contentValues);

    }

    public void updateUserSync() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ServerUpdatedStatus", 1);
        mDatabase.update("UserSync", contentValues, "ServerUpdatedStatus='0'", null);
        Log.v("@@@MM", "Updating");
    }

    //update Usersync when master sync is performed
    public void updateMasterSync() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("MasterSync", 1);
        contentValues.put("ServerUpdatedStatus", 0);
        contentValues.put("UpdatedByUserId",  CommonConstants.USER_ID);

        // contentValues.put("Date",CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
       // contentValues.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        contentValues.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));

        mDatabase.update("UserSync",contentValues,"DATE(CreatedDate) = DATE('now') AND UserId = '" + CommonConstants.USER_ID +"' AND App = '3fMainApp'",null);

        // mDatabase.update("UserSync",contentValues,"ServerUpdatedStatus='0'",null);
       // mDatabase.update("UserSync", contentValues, null, null);
        Log.v("@@@MM", "Updating");
    }

    //update Usersync when transaction sync is performed
    public void updateTransactionSync() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TransactionSync", 1);
        contentValues.put("ServerUpdatedStatus", 0);
        contentValues.put("UpdatedByUserId",  CommonConstants.USER_ID);

        // contentValues.put("Date",CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        //contentValues.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        contentValues.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));

        mDatabase.update("UserSync",contentValues,"DATE(CreatedDate) = DATE('now') AND UserId = '" + CommonConstants.USER_ID +"' AND App = '3fMainApp'",null);

        // mDatabase.update("UserSync",contentValues,"ServerUpdatedStatus='0'",null);
       // mDatabase.update("UserSync", contentValues, null, null);
        Log.v("@@@MM", "Updating");
    }

    //update Usersync when reset data is performed
    public void updateResetDataSync() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ResetData", 1);
        contentValues.put("ServerUpdatedStatus", 0);
        contentValues.put("UpdatedByUserId",  CommonConstants.USER_ID);

        //contentValues.put("Date",CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
       // contentValues.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        contentValues.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));

        mDatabase.update("UserSync",contentValues,"DATE(CreatedDate) = DATE('now') AND UserId = '" + CommonConstants.USER_ID +"' AND App = '3fMainApp'",null);


        // mDatabase.update("UserSync",contentValues,"ServerUpdatedStatus='0'",null);
       // mDatabase.update("UserSync", contentValues, null, null);
        Log.v("@@@MM", "Updating");
    }

//Get two values from db using query
    public String getTwoValues(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        String mGenericData = "";
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData = CommonUtils.twoDForm.format(genericDataQuery.getDouble(0)) + "-" + CommonUtils.twoDForm.format(genericDataQuery.getDouble(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }

    //get plots age & stateid using query
    public String getplotAgeandSateId(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        String mGenericData = "";
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData = (genericDataQuery.getInt(0)) + "-" + (genericDataQuery.getInt(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }

    public String getYPHvaluefromBenchMark(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return "";
    }

    //get paired/linked hashmap data using query from db
    public LinkedHashMap<String, Pair> getPairData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        LinkedHashMap<String, Pair> mGenericData = new LinkedHashMap<>();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData.put(genericDataQuery.getString(0), Pair.create(genericDataQuery.getString(1), genericDataQuery.getString(2)));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }


    //to check value existed in db
    public boolean checkValueExistedInDatabase(final String query) {
        Cursor mOprQuery = mDatabase.rawQuery(query, null);
        try {
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return (mOprQuery.getInt(0) > 0);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return false;
    }

    /*    public String  getAreaUnderPalm(String query) {
            Log.v(LOG_TAG, "@@@ query " + query);
            Cursor mOprQuery = null;
            try {
                mOprQuery = mDatabase.rawQuery(query, null);
                if (mOprQuery != null && mOprQuery.moveToFirst()) {
                    return mOprQuery.getString(5);
                }

                return null;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != mOprQuery)
                    mOprQuery.close();

                closeDataBase();
            }
            return "";
        }*/

    //get only one Integer value from Db
    public Integer getOnlyOneIntValueFromDb(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getInt(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return null;
    }

    //get last visit code from db
    public String getLastVistCodeFromDb(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(mOprQuery.getColumnIndex("Code"));
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return "";
    }

    //get only one value from db
    public String getOnlyOneValueFromDb(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return "";
    }


    //get two values from db
    public String getOnlyTwoValueFromDb(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0) + "@" + mOprQuery.getString(1);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return "";
    }

    //Generates Unique farmer code
    public String getGeneratedFarmerCode(final String query, final String financalYaerDays) {
        String maxNum = getOnlyOneValueFromDb(query);
        String convertedNum = "";
        if (!TextUtils.isEmpty(maxNum)) {
            convertedNum = CommonUtils.serialNumber(Integer.parseInt(maxNum) + 1, 3);
        } else {
            convertedNum = CommonUtils.serialNumber(1, 3);
        }
        StringBuilder farmerCoder = new StringBuilder();

        farmerCoder.append(CommonConstants.stateCode)
                .append(CommonConstants.districtCode)
                .append(CommonConstants.TAB_ID)
                .append(financalYaerDays)
                .append(convertedNum);
        Log.v(LOG_TAG, "@@@ farmer code " + farmerCoder.toString() + " D->" + financalYaerDays + " n->" + convertedNum);
        return farmerCoder.toString();
    }

    //Generates Unique Harvesting visit code
    public String getGenerateHarvestingVisitiCode(final String query) {
        String maxNum = getOnlyOneValueFromDb(query);
        String convertedNum = "";
        if (!TextUtils.isEmpty(maxNum)) {
            //convertedNum = "123";
            convertedNum = CommonUtils.serialNumber(Integer.parseInt(maxNum) + 1, 3);
            Log.d("convertedNum", convertedNum);
        } else {
            convertedNum = CommonUtils.serialNumber(1, 3);
        }
        StringBuilder farmerCoder = new StringBuilder();
        String finalNumber = StringUtils.leftPad(convertedNum,3,"0");
        farmerCoder.append("HRT")
                .append(CommonConstants.TAB_ID)
                .append(CommonConstants.PLOT_CODE)
                .append("-")
                .append(finalNumber);
        Log.v(LOG_TAG, "@@@ farmer code " + farmerCoder.toString() );
        return farmerCoder.toString();
    }
  /*  public String getGeneratedMarketSurveyCode(final String query) {
        String maxNum = getOnlyOneValueFromDb(query);
//        String convertedNum = "";
//        String maxNum = getOnlyOneValueFromDb(ccQuery);
        int convertedNum = 0;
        if (!TextUtils.isEmpty(maxNum)) {
//            convertedNum = CommonUtils.serialNumber(Integer.parseInt(maxNum) + 1, 6);
            convertedNum = Integer.parseInt(maxNum) + 1;
        } else {
            convertedNum = 1;
        }
        StringBuilder farmerCoder = new StringBuilder();
f
        farmerCoder.append(CommonConstants.MARKET_SURVEY_CODE_PREFIX)
                .append(CommonConstants.FARMER_CODE).append("-").append(String.valueOf(convertedNum));

        Log.v(LOG_TAG, "@@@ MarketSurvey code " + farmerCoder.toString());
        return farmerCoder.toString();
    }*/

    //Generates Unique Plot Id
    public String getGeneratedPlotId(final String query, final String financalYrDays) {
        String maxNum = getOnlyOneValueFromDb(query);
        String convertedNum = "";
        if (!TextUtils.isEmpty(maxNum)) {
            convertedNum = CommonUtils.serialNumber(Integer.parseInt(maxNum) + 1, 3);
        } else {
            convertedNum = CommonUtils.serialNumber(1, 3);
        }
        StringBuilder farmerCoder = new StringBuilder();
        farmerCoder.append(CommonConstants.stateCodePlot)
                .append(CommonConstants.TAB_ID)
                .append(financalYrDays)
                .append(!convertedNum.isEmpty() ? convertedNum : CommonUtils.serialNumber(1, 3));
        Log.v(LOG_TAG, "@@@ farmer code " + farmerCoder.toString());
        return farmerCoder.toString();
    }

    public String getGenerateHealthPlantImageId(final String query, final String plotCode) {
        String maxNum = getOnlyOneValueFromDb(query);
        String convertedNum = "";
        if (!TextUtils.isEmpty(maxNum)) {
            convertedNum = "001";
        }
        StringBuffer farmerCoder = new StringBuffer();
        farmerCoder.append(plotCode)
                .append(convertedNum);
        Log.v(LOG_TAG, "@@@ farmer code " + farmerCoder.toString());
        return farmerCoder.toString();
    }


    public void updateMasterSyncDate(final boolean isNotFirstTime, String userId) {
        final List<LinkedHashMap> listMap = new ArrayList<>();
        final LinkedHashMap dataMap = new LinkedHashMap();
        dataMap.put(DatabaseKeys.COLUMN_USERID, (null == userId) ? "1" : userId);
        final String finalUserId = userId;
        dataMap.put(DatabaseKeys.COLUMN_UPDATEDON, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY));
        listMap.add(dataMap);
        ApplicationThread.dbPost("MasterVersionTrackingSystem Saving..", "insert", new Runnable() {
            @Override
            public void run() {
                if (isNotFirstTime) {
                    insertData(DatabaseKeys.TABLE_MASTERVERSIONTRACKINGSYSTEM, listMap, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void run() {
                            if (success) {
                                PrefUtil.putBool(context, CommonConstants.IS_MASTER_SYNC_SUCCESS, true);
                                Log.v(LOG_TAG, "@@@ MasterVersionTrackingSystem inserted ");
                            }
                        }
                    });
                } else {
                    String whereCondition = "  where " + DatabaseKeys.COLUMN_USERID + "='" + finalUserId + "'";
                    updateData(DatabaseKeys.TABLE_MASTERVERSIONTRACKINGSYSTEM, listMap, false, whereCondition, null);
                    Log.v(LOG_TAG, "@@@ MasterVersionTrackingSystem updated ");
                }
            }
        });
    }

    /**
     * Inserting data into database
     *
     * @param tableName ---> Table name to insert data
     * @param mapList   ---> map list which contains data
     */
    public synchronized void insertDataOld(String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
//        if (!ApplicationThread.dbThreadCheck())
//            Log.e(LOG_TAG, "called on non-db thread", new RuntimeException());
        int checkCount = 0;
        boolean errorMessageSent = false;
        try {
            for (int i = 0; i < mapList.size(); i++) {
                checkCount++;
                List<LinkedHashMap.Entry> entryList = new ArrayList<>((mapList.get(i)).entrySet());
                String query = "insert into " + tableName;
                String namestring, valuestring;
                StringBuffer values = new StringBuffer();
                StringBuffer columns = new StringBuffer();
                for (LinkedHashMap.Entry temp : entryList) {
//                    if (temp.getKey().equals("Id"))
//                        continue;
                    columns.append(temp.getKey());
                    columns.append(",");
                    values.append("'");
                    values.append(temp.getValue());
                    values.append("'");
                    values.append(",");
                }
                namestring = "(" + columns.deleteCharAt(columns.length() - 1).toString() + ")";
                valuestring = "(" + values.deleteCharAt(values.length() - 1).toString() + ")";
                query = query + namestring + "values" + valuestring;
                Log.v(getClass().getSimpleName(), "query.." + query);
                Log.v(LOG_TAG, "@@@@ log check " + checkCount + " here " + mapList.size());
                try {
                    mDatabase.execSQL(query);
                } catch (Exception e) {
                    Log.v(LOG_TAG, "@@@ Error while inserting data " + e.getMessage());
                    if (checkCount == mapList.size()) {
                        errorMessageSent = true;
                        if (null != oncomplete)
                            oncomplete.execute(false, "failed to insert data", "");
                    }
                }
                if (checkCount == mapList.size() && !errorMessageSent) {
                    if (null != oncomplete)
                        oncomplete.execute(true, "data inserted successfully", "");
                }
            }
        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            Log.v(LOG_TAG, "@@@@ exception log check " + checkCount + " here " + mapList.size());
            if (checkCount == mapList.size()) {
                if (null != oncomplete)
                    oncomplete.execute(false, "data insertion failed", "" + e.getMessage());
            }
        } finally {
            closeDataBase();
        }
    }

    //Inserts Data into database
    public synchronized void insertData(boolean fromMaster, String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
        int checkCount = 0;
        try {
            List<ContentValues> values1 = new ArrayList<>();
            for (int i = 0; i < mapList.size(); i++) {
                checkCount++;
                List<LinkedHashMap.Entry> entryList = new ArrayList<>((mapList.get(i)).entrySet());

                ContentValues contentValues = new ContentValues();
                for (LinkedHashMap.Entry temp : entryList) {
                    String keyToInsert = temp.getKey().toString();
                    if (!fromMaster) {
                        if (keyToInsert.equalsIgnoreCase("Id") && !tableName.equalsIgnoreCase(DatabaseKeys.TABLE_ALERTS))
                            continue;
                    }
                    if (keyToInsert.equalsIgnoreCase("ServerUpdatedStatus")) {
                        contentValues.put(keyToInsert, "1");
                    } else {
                        contentValues.put(temp.getKey().toString(), temp.getValue().toString());
                    }
                }
                values1.add(contentValues);
            }
            Log.v(LOG_TAG, "@@@@ log check " + checkCount + " here " + values1. size());
            boolean hasError = bulkinserttoTable(values1, tableName);
            if (hasError) {
                Log.v(LOG_TAG, "@@@ Error while inserting data ");
                if (null != oncomplete) {
                    oncomplete.execute(false, "failed to insert data", "");
                }
            } else {
                Log.v(LOG_TAG, "@@@ data inserted successfully for table :" + tableName);
                if (null != oncomplete) {
                    oncomplete.execute(true, "data inserted successfully", "");
                }
            }
        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            Log.v(LOG_TAG, "@@@@ exception log check " + checkCount + " here " + mapList.size());
            if (checkCount == mapList.size()) {
                if (null != oncomplete)
                    oncomplete.execute(false, "data insertion failed", "" + e.getMessage());
            }
        } finally {
            closeDataBase();
        }
    }

    //Save Data into the tables using linkedhash map
    public synchronized void savedata(boolean fromMaster, String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
        int checkCount = 0;
        try {
            List<ContentValues> values1 = new ArrayList<>();
            for (int i = 0; i < mapList.size(); i++) {
                checkCount++;
                List<LinkedHashMap.Entry> entryList = new ArrayList<>((mapList.get(i)).entrySet());

                ContentValues contentValues = new ContentValues();
                for (LinkedHashMap.Entry temp : entryList) {
                    String keyToInsert = temp.getKey().toString();
                    if (!fromMaster) {
                        if (keyToInsert.equalsIgnoreCase("Id") && !tableName.equalsIgnoreCase(DatabaseKeys.TABLE_ALERTS))
                            continue;
                    }
                    if (keyToInsert.equalsIgnoreCase("ServerUpdatedStatus")) {
                        contentValues.put(keyToInsert, "0");
                    } else {
                        contentValues.put(temp.getKey().toString(), temp.getValue().toString());
                    }
                }
                values1.add(contentValues);
            }
            Log.v(LOG_TAG, "@@@@ log check " + checkCount + " here " + values1.size());
            boolean hasError = bulkinserttoTable(values1, tableName);
            if (hasError) {
                Log.v(LOG_TAG, "@@@ Error while inserting data ");
                if (null != oncomplete) {
                    oncomplete.execute(false, "failed to insert data", "");
                }
            } else {
                Log.v(LOG_TAG, "@@@ data inserted successfully for table :" + tableName);
                if (null != oncomplete) {
                    oncomplete.execute(true, "data inserted successfully", "");
                }
            }
        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            Log.v(LOG_TAG, "@@@@ exception log check " + checkCount + " here " + mapList.size());
            if (checkCount == mapList.size()) {
                if (null != oncomplete)
                    oncomplete.execute(false, "data insertion failed", "" + e.getMessage());
            }
        } finally {
            closeDataBase();
        }
    }

    public synchronized void saveData(String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
        savedata(false, tableName, mapList, oncomplete);
    }
    public synchronized void insertData(String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
        insertData(false, tableName, mapList, oncomplete);
    }

    /**
     * Updating database records
     *
     * @param tableName      ---> Table name to update
     * @param list           ---> List which contains data values
     * @param isClaues       ---> Checking where condition availability
     * @param whereCondition ---> condition
     */
    public synchronized void updateData(String tableName, List<LinkedHashMap> list, Boolean isClaues, String whereCondition, final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {
            for (int i = 0; i < list.size(); i++) {
                checkCount++;
                List<Map.Entry> entryList = new ArrayList<Map.Entry>((list.get(i)).entrySet());
                String query = "update " + tableName + " set ";
                String namestring = "";

                System.out.println("\n==> Size of Entry list: " + entryList.size());
                StringBuffer columns = new StringBuffer();
                for (Map.Entry temp : entryList) {
                    columns.append(temp.getKey());
                    columns.append("='");
                    columns.append(temp.getValue());
                    columns.append("',");
                }

                namestring = columns.deleteCharAt(columns.length() - 1).toString();
                query = query + namestring + "" + whereCondition;
                mDatabase.execSQL(query);
                isUpdateSuccess = true;
                Log.v(LOG_TAG, "@@@ query for Plantation " + query);
            }
        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (checkCount == list.size()) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for " + tableName);
                    oncomplete.execute(true, null, "data updated successfully for " + tableName);
                } else {
                    oncomplete.execute(false, null, "data updation failed for " + tableName);
                }
            }
        }
    }

    /**
     * Deleting records from database table
     *
     * @param tableName  ---> Table name
     * @param columnName ---> Column name to deleting
     * @param value      ---> Value for where condition
     * @param isWhere    ---> Checking where condition is required or not
     */
    public synchronized void deleteRow(String tableName, String columnName, String value, boolean isWhere, final ApplicationThread.OnComplete<String> onComplete) {
        boolean isDataDeleted = true;
//        if (!ApplicationThread.dbThreadCheck())
//            Log.e(LOG_TAG, "called on non-db thread", new RuntimeException());

        try {
//            mDatabase = palm3FoilDatabase.getWritableDatabase();
            String query = "delete from " + tableName;
            if (isWhere) {
                query = query + " where " + columnName + " = '" + value + "'";
            }
            mDatabase.execSQL(query);
        } catch (Exception e) {
            isDataDeleted = false;
            Log.e(LOG_TAG, "@@@ master data deletion failed for " + tableName + " error is " + e.getMessage());
            onComplete.execute(false, null, "master data deletion failed for " + tableName + " error is " + e.getMessage());
        } finally {
            closeDataBase();

            if (isDataDeleted) {
                Log.v(LOG_TAG, "@@@ master data deleted successfully for " + tableName);
                onComplete.execute(true, null, "master data deleted successfully for " + tableName);
            }

        }
    }

    public ArrayList<String> getListOfCodes(final String query) {
        ArrayList<String> plotCodes = new ArrayList<>();
        Cursor paCursor = null;
        try {
            paCursor = mDatabase.rawQuery(query, null);
            if (paCursor.moveToFirst()) {
                do {
                    plotCodes.add(paCursor.getString(0).trim());
                } while (paCursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != paCursor)
                paCursor.close();

            closeDataBase();
        }
        return plotCodes;
    }

    //get count
    public String getCountValue(String query) {
//        mDatabase = palm3FoilDatabase.getWritableDatabase();
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mOprQuery.close();
            closeDataBase();
        }
        return "";
    }

    public String getdeleteDuplicateValue(String query) {
//        mDatabase = palm3FoilDatabase.getWritableDatabase();
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mOprQuery.close();
            closeDataBase();
        }
        return "";
    }

    public synchronized void insertImageData(String code, String farmercode, String imagepath, String serverUpdatedStatus) {
        try {
//            mDatabase = palm3FoilDatabase.getWritableDatabase();
            ContentValues update_values = new ContentValues();
            update_values.put(DatabaseKeys.COLUMN_CODE, code);
            update_values.put(DatabaseKeys.COLUMN_FARMERCODE, farmercode);
            update_values.put(DatabaseKeys.COLUMN_MODULEID, 100);
//            update_values.put(DatabaseKeys.COLUMN_PLOTCODE, "");
            update_values.put(DatabaseKeys.COLUMN_PHOTO, imagepath);
            update_values.put(DatabaseKeys.COLUMN_CREATEDDATE, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            update_values.put(DatabaseKeys.COLUMN_CREATEDBYUSERID, CommonConstants.USER_ID);
            update_values.put(DatabaseKeys.COLUMN_CREATEDDATE, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            update_values.put(DatabaseKeys.COLUMN_UPDATEDBYUSERID, CommonConstants.USER_ID);
            update_values.put(DatabaseKeys.COLUMN_UPDATEDDATE, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            update_values.put(DatabaseKeys.COLUMN_SERVERUPDATEDSTATUS, serverUpdatedStatus);
            mDatabase.insert(DatabaseKeys.TABLE_PICTURE_REPORTING, null, update_values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDataBase();
        }
    }

    public void closeDataBase() {
//        if (mDatabase != null)
//            mDatabase.close();
    }

    public void executeRawQuery(String query) {
        try {
            if (mDatabase != null) {
                mDatabase.execSQL(query);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public List<Pair> getOnlyPairData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        List<Pair> mGenericData = new ArrayList<>();
//        mDatabase = palm3FoilDatabase.getWritableDatabase();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData.add(Pair.create(genericDataQuery.getString(0), genericDataQuery.getString(1)));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }


    //to get the nursery saplings data
    public List<NurserySaplingDetails> getNurserySaplingDetails(final String query) {
        List<NurserySaplingDetails> nurserySaplingDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    NurserySaplingDetails saplingDetails = new NurserySaplingDetails();
                    saplingDetails.setNurseryId(cursor.getInt(cursor.getColumnIndex("NurseryId")));
                    saplingDetails.setCropVarietyId(cursor.getInt(cursor.getColumnIndex("CropVarietyId")));
                    saplingDetails.setSaplingSourceId(cursor.getInt(cursor.getColumnIndex("SaplingSourceId")));
                    saplingDetails.setSaplingVendorId(cursor.getInt(cursor.getColumnIndex("SaplingVendorId")));
                    saplingDetails.setNoOfSaplingsDispatched(cursor.getInt(cursor.getColumnIndex("NoOfSaplingsDispatched")));

                    nurserySaplingDetails.add(saplingDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return nurserySaplingDetails;
    }

    public List<Village> getVillageDetails(final String query) {
        List<Village> nurserySaplingDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    Village saplingDetails = new Village();
                    saplingDetails.setName(cursor.getString(cursor.getColumnIndex("Name")));


                    nurserySaplingDetails.add(saplingDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return nurserySaplingDetails;
    }


    //get farmer details based on type of screen selected
    public void getFarmerDetailsForSearch(String key, int offset, int limit, final ApplicationThread.OnComplete onComplete) {
        List<BasicFarmerDetails> farmerDetails = new ArrayList<>();
        Cursor cursor = null;
        String query = null;
        if (CommonUtils.isFromConversion()) {
            query = Queries.getInstance().getFilterBasedFarmers(83, key, offset, limit);
        } else if (CommonUtils.isFromCropMaintenance() || CommonUtils.isFromHarvesting()) {
            query = Queries.getInstance().getFilterBasedFarmersCrop(key,offset,limit);
        } else if (CommonUtils.isViewProspectiveFarmers()) {
            query = Queries.getInstance().getFilterBasedProspectiveFarmers(81, key, offset, limit);
        } else if (CommonUtils.isFromFollowUp()) {
            query = Queries.getInstance().getFilterBasedFarmersFollowUp(key, offset, limit);
        } else if (CommonUtils.isComplaint()) {
            query = Queries.getInstance().getFilterBasedFarmersCrop(key, offset, limit);
        } else if (CommonUtils.isPlotSplitFarmerPlots()) {
            query = Queries.getInstance().getFilterBasedFarmersCropRetake(key, offset, limit);
        } else if (CommonUtils.isVisitRequests()) {
            query = Queries.getInstance().getVisitRequestFarmers(key, offset, limit);
            Log.v("@@@query", "2");
        } else {
            query = Queries.getInstance().getFarmersDataForWithOffsetLimit(key, offset, limit);
            Log.v("@@@query", "1");
        }

        Log.v(LOG_TAG, "Query for getting farmers " + query);

        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    BasicFarmerDetails farmlanddetails = new BasicFarmerDetails();
                    farmlanddetails.setFarmerCode(cursor.getString(cursor.getColumnIndex("Code")));
                    farmlanddetails.setFarmerFirstName(cursor.getString(cursor.getColumnIndex("FirstName")));
                    farmlanddetails.setFarmerMiddleName(cursor.getString(cursor.getColumnIndex("MiddleName")));
                    farmlanddetails.setFarmerLastName(cursor.getString(cursor.getColumnIndex("LastName")));
                    farmlanddetails.setFarmerStateName(cursor.getString(cursor.getColumnIndex("StateName")));
                    farmlanddetails.setPrimaryContactNum(cursor.getString(cursor.getColumnIndex("ContactNumber")));
                    farmlanddetails.setFarmerVillageName(cursor.getString(cursor.getColumnIndex("Name")));
                    farmlanddetails.setPhotoLocation(cursor.getString(cursor.getColumnIndex("FileLocation")));
                    farmlanddetails.setPhotoName(cursor.getString(cursor.getColumnIndex("FileName")));
                    farmlanddetails.setFileExtension(cursor.getString(cursor.getColumnIndex("FileExtension")));
                    farmlanddetails.setFarmerFatherName(cursor.getString(cursor.getColumnIndex("GuardianName")));
                    farmerDetails.add(farmlanddetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "getting failed fromLocalDb" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDataBase();

            onComplete.execute(true, farmerDetails, "getting data");
        }
    }

    //get harvestor details when search is performed
    public void getharvestorForSearch(String key, int offset, int limit, final ApplicationThread.OnComplete onComplete) {
        List<BasicHarvestorDetails> harvestorDetails = new ArrayList<>();
        Cursor cursor = null;
        String query = null;
            query = Queries.getInstance().getFilterBasedHarvestors(key);
          Log.v("@@@query", "1");


        Log.v(LOG_TAG, "Query for getting farmers " + query);

        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    BasicHarvestorDetails harvesrorrdetails = new BasicHarvestorDetails();
                    harvesrorrdetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    harvesrorrdetails.setCode(cursor.getString(cursor.getColumnIndex("Code")));
                    harvesrorrdetails.setName(cursor.getString(cursor.getColumnIndex("Name")));
                    harvesrorrdetails.setMobileNumber(cursor.getString(cursor.getColumnIndex("MobileNumber")));
                    harvesrorrdetails.setVillage(cursor.getString(cursor.getColumnIndex("Village")));
                    harvesrorrdetails.setMandal(cursor.getString(cursor.getColumnIndex("Mandal")));
                    harvesrorrdetails.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    harvesrorrdetails.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    harvesrorrdetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    harvesrorrdetails.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    harvesrorrdetails.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    harvestorDetails.add(harvesrorrdetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "getting failed fromLocalDb" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDataBase();

            onComplete.execute(true, harvestorDetails, "getting data");
        }
    }

    //get farmers for yield
    public void getFarmerDetailsForSearchYield(String key, int offset, int limit, final ApplicationThread.OnComplete onComplete) {
        List<BasicFarmerDetails> farmerDetails = new ArrayList<>();
        Cursor cursor = null;
        String query = null;
        query = Queries.getInstance().getFarmersDataForWithOffsetLimit(key, offset, limit);
        Log.v("@@@query", "1");


        Log.v(LOG_TAG, "Query for getting farmers " + query);

        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    BasicFarmerDetails farmlanddetails = new BasicFarmerDetails();
                    farmlanddetails.setFarmerCode(cursor.getString(cursor.getColumnIndex("Code")));
                    farmlanddetails.setFarmerFirstName(cursor.getString(cursor.getColumnIndex("FirstName")));
                    farmlanddetails.setFarmerMiddleName(cursor.getString(cursor.getColumnIndex("MiddleName")));
                    farmlanddetails.setFarmerLastName(cursor.getString(cursor.getColumnIndex("LastName")));
                    farmlanddetails.setFarmerStateName(cursor.getString(cursor.getColumnIndex("StateName")));
                    farmlanddetails.setPrimaryContactNum(cursor.getString(cursor.getColumnIndex("ContactNumber")));
                    farmlanddetails.setFarmerVillageName(cursor.getString(cursor.getColumnIndex("Name")));
                    farmlanddetails.setPhotoLocation(cursor.getString(cursor.getColumnIndex("FileLocation")));
                    farmlanddetails.setPhotoName(cursor.getString(cursor.getColumnIndex("FileName")));
                    farmlanddetails.setFileExtension(cursor.getString(cursor.getColumnIndex("FileExtension")));
                    farmlanddetails.setFarmerFatherName(cursor.getString(cursor.getColumnIndex("GuardianName")));
                    farmerDetails.add(farmlanddetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "getting failed fromLocalDb" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDataBase();

            onComplete.execute(true, farmerDetails, "getting data");
        }
    }

    public List<FarmerFFBHarvestDetails> getFFBHarvestRefresh(String query) {
        List<FarmerFFBHarvestDetails> listFarmerActivities = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    try {
                        FarmerFFBHarvestDetails rec = new FarmerFFBHarvestDetails();
                        rec.setFFBHarvestId(cursor.getInt(0));
                        rec.setFarmerCode(cursor.getString(1));
                        rec.setPlotCode(cursor.getString(2));
                        rec.setCollectionCentreId(cursor.getInt(3));
                        rec.setModeOfTransport(cursor.getString(4));
                        rec.setHarvestingMethod(cursor.getString(5));
                        rec.setWagesPerDay(cursor.getDouble(6));
                        rec.setContractRsPerMonth(cursor.getDouble(7));
                        rec.setContractRsPerAnum(cursor.getDouble(8));
                        rec.setTypeOfHarvesting(cursor.getString(9));
                        rec.setContractorPitch(cursor.getInt(10));
                        rec.setFarmerConsent(cursor.getInt(11));
                        rec.setComments(cursor.getString(12));
                        rec.setCreatedBy(cursor.getString(13));
                        rec.setCreatedDate(cursor.getString(14));
                        rec.setUpdatedBy(cursor.getString(15));
                        rec.setUpdatedDate(cursor.getString(16));
                        rec.setServerUpdatedStatus(cursor.getInt(17));
                        listFarmerActivities.add(rec);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();

        }
        return listFarmerActivities;
    }

    //tp get single list data from db
    public List<String> getSingleListData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        List<String> mGenericData = new ArrayList<>();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    String plotCode = genericDataQuery.getString(0);
                    mGenericData.add(plotCode);
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();
            closeDataBase();
        }
        return mGenericData;
    }

    public List<String> getZombiData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        List<String> mGenericData = new ArrayList<>();
//        mDatabase = palm3FoilDatabase.getWritableDatabase();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    String plotCode = genericDataQuery.getString(0);
                    if (!TextUtils.isEmpty(plotCode)) {
                        mGenericData.add("'" + plotCode + "'");
                    }
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }


    //get user details
    public T getUserDetails(final String query, int dataReturnType) {
        UserDetails userDetails = null;
        Cursor cursor = null;
        List userDataList = new ArrayList();
        Log.v(LOG_TAG, "@@@ user details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    userDetails = new UserDetails();
                    userDetails.setUserId(cursor.getString(0));
                    userDetails.setUserName(cursor.getString(1));
                    userDetails.setPassword(cursor.getString(2));
                    userDetails.setRoleId(cursor.getInt(3));
                    userDetails.setManagerId(cursor.getInt(4));
                    userDetails.setId(cursor.getString(5));
                    userDetails.setFirstName(cursor.getString(6));
                    userDetails.setTabName(cursor.getString(7));
                    userDetails.setUserCode(cursor.getString(8));
//                    userDetails.setTabletId(cursor.getInt(5));
//                    userDetails.setUserVillageId(cursor.getString(6));
                    if (dataReturnType == 1) {
                        userDataList.add(userDetails);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((dataReturnType == 0) ? userDetails : userDataList);
    }


    //get plot details based selected screen type
    public List<PlotDetailsObj> getPlotDetails(String farmerCode, int plotStatus, boolean withoutGps) {
        List<PlotDetailsObj> plotDetailslistObj = new ArrayList<>();
        Cursor cursor = null;
        String query = null;
        if (CommonUtils.isFromCropMaintenance() || CommonUtils.isComplaint()) {
            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus, 89, true);
        } else if (CommonUtils.isFromFollowUp()) {
            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus);
        } else if (CommonUtils.isPlotSplitFarmerPlots()) {
            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus);
        } else if (CommonUtils.isFromConversion()) {
            query = Queries.getInstance().getPlotDetailsForConversion(farmerCode.trim(), plotStatus);
        }
//        if (withoutGps && !CommonUtils.isFromCropMaintenance()) {
//            query = Queries.getInstance().getPlotDetailsForNonGeo(farmerCode.trim(), plotStatus);
//        } else if (CommonUtils.isFromCropMaintenance()) {
//            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus, 89, true);
//        } else {
//            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus);
//        }
        Log.v(LOG_TAG, "Query for getting plots related to farmer " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    PlotDetailsObj plotDetailsObj = new PlotDetailsObj();
                    plotDetailsObj.setPlotID(cursor.getString(0));
                    plotDetailsObj.setTotalPalm(cursor.getString(1));
                    plotDetailsObj.setPlotArea(cursor.getString(2));
                    plotDetailsObj.setGpsPlotArea(cursor.getString(3));
                    plotDetailsObj.setSurveyNumber(cursor.getString(4));
                    plotDetailsObj.setPlotLandMark(cursor.getString(5));
                    plotDetailsObj.setVillageCode(cursor.getString(6));
                    plotDetailsObj.setVillageName(cursor.getString(7));
                    plotDetailsObj.setVillageId(cursor.getString(8));
                    plotDetailsObj.setMandalCode(cursor.getString(9));
                    plotDetailsObj.setFarmerMandalName(cursor.getString(10));
                    plotDetailsObj.setMandalId(cursor.getString(11));
                    plotDetailsObj.setDistrictCode(cursor.getString(12));
                    plotDetailsObj.setFarmerDistrictName(cursor.getString(13));
                    plotDetailsObj.setDistrictId(cursor.getString(14));
                    plotDetailsObj.setStateCode(cursor.getString(15));
                    plotDetailsObj.setFarmerStateName(cursor.getString(16));
                    plotDetailsObj.setStateId(cursor.getString(17));
                    plotDetailsObj.setDateofPlanting(cursor.getString(18));
                    plotDetailslistObj.add(plotDetailsObj);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return plotDetailslistObj;
    }


    //get alert details
    public T getAlertsDetails(final String query, int dataReturnType, boolean fromRefresh) {
        Cursor cursor = null;
        Alerts alertDetails = null;
        List alertsDataList = new ArrayList();
        Log.v(LOG_TAG, "@@@ alertDetails  query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    alertDetails = new Alerts();
                    alertDetails.setId(cursor.getInt(0));
                    alertDetails.setName(cursor.getString(1));
                    alertDetails.setDesc(cursor.getString(2));
                    alertDetails.setUserId(cursor.getInt(3));
                    alertDetails.setHTMLDesc(cursor.getString(4));
                    alertDetails.setIsRead(cursor.getInt(5));
                    alertDetails.setPlotCode(cursor.getString(6));
                    alertDetails.setComplaintCode(cursor.getString(7));
                    alertDetails.setAlertTypeId(cursor.getInt(8));
                    alertDetails.setCreatedByUserId(cursor.getInt(9));
                    alertDetails.setCreatedDate(cursor.getString(10));
                    alertDetails.setUpdatedByUserId(cursor.getInt(11));
                    alertDetails.setUpdatedDate(cursor.getString(12));
                    alertDetails.setServerUpdatedStatus(cursor.getInt(13));
                    if (!fromRefresh) {
                        alertDetails.setHTMLDesc(cursor.getString(14));
                    }
                    if (dataReturnType == 1) {
                        alertsDataList.add(alertDetails);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting  alertDetails " + e.getMessage());
        }
        return (T) ((dataReturnType == 0) ? alertDetails : alertsDataList);
    }

    //used to bulk insert into table
    public boolean bulkinserttoTable(List<ContentValues> cv, final String tableName) {
        boolean isError = false;
        mDatabase.beginTransaction();
        try {
            for (int i = 0; i < cv.size(); i++) {
                ContentValues stockResponse = cv.get(i);
                long id = mDatabase.insert(tableName, null, stockResponse);
                if (id < 0) {
                    isError = true;
                }
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return isError;
    }

    public boolean bulkUpdateToTable(List<ContentValues> cv, final String tableName) {
        boolean isError = false;
        mDatabase.beginTransaction();
        try {
            for (int i = 0; i < cv.size(); i++) {
                ContentValues stockResponse = cv.get(i);
                long id = mDatabase.replaceOrThrow(tableName, null, stockResponse);
                if (id < 0) {
                    isError = true;
                }
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return isError;
    }

    public <T> ArrayList<T> SelectAll() {
        Class<T> foo = (Class<T>) Farmer.class;
        Cursor cursor = mDatabase.rawQuery("select * from Farmer", null);
        ArrayList<T> list = new ArrayList<>();

        Field[] fields = foo.getFields();
        try {
            Constructor<T> constructor = foo.getConstructor(foo);
            list.add(constructor.newInstance());
        } catch (Exception ex) {
            return list;
        }
        return null;
    }

    //get selected farmer address
    public T getSelectedFarmerAddress(final String query, final int type) {
        List<Address> farmerAddrList = new ArrayList<>();
        Address mAddress = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mAddress = new Address();
                    mAddress.setCode(cursor.getString(1));
                    mAddress.setAddressline1(cursor.getString(2));
                    mAddress.setAddressline2(cursor.getString(3));
                    mAddress.setAddressline3(cursor.getString(4));
                    mAddress.setLandmark(cursor.getString(5));
                    mAddress.setVillageid(cursor.getInt(6));
                    mAddress.setMandalid(cursor.getInt(7));
                    mAddress.setDistictid(cursor.getInt(8));
                    mAddress.setStateid(cursor.getInt(9));
                    mAddress.setCountryid(cursor.getInt(10));
                    mAddress.setPincode(cursor.getInt(11));
                    mAddress.setIsactive(cursor.getInt(12));
                    mAddress.setCreatedbyuserid(cursor.getInt(13));
                    mAddress.setCreateddate(cursor.getString(14));
                    mAddress.setUpdatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
                    mAddress.setUpdateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    mAddress.setServerupdatedstatus(Integer.parseInt(CommonConstants.ServerUpdatedStatus));
                    if (type == 1) {
                        farmerAddrList.add(mAddress);
                        mAddress = null;
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mAddress : farmerAddrList);
    }


    //get selected farmer data
    public T getSelectedFarmerData(final String query, int type) {

        List<Farmer> farmerRefresh = new ArrayList<>();
        Cursor cursor = null;
        Farmer mFarmer = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFarmer = new Farmer();
                    mFarmer.setCode(cursor.getString(1));
                    mFarmer.setCountryid(cursor.getInt(2));
                    mFarmer.setRegionid(null);
                    mFarmer.setStateid(cursor.getInt(4));
                    mFarmer.setDistictid(cursor.getInt(5));
                    mFarmer.setMandalid(cursor.getInt(6));
                    mFarmer.setVillageid(cursor.getInt(7));
                    mFarmer.setSourceofcontacttypeid((cursor.getInt(8) == 0) ? null : cursor.getInt(8));
                    mFarmer.setTitletypeid((cursor.getInt(9) == 0) ? null : cursor.getInt(9));
                    mFarmer.setFirstname(cursor.getString(10));
                    mFarmer.setMiddlename(cursor.getString(11));
                    mFarmer.setLastname(cursor.getString(12));
                    mFarmer.setGuardianname(cursor.getString(13));
                    mFarmer.setMothername(cursor.getString(14));
                    mFarmer.setGendertypeid(cursor.getInt(15));
                    mFarmer.setContactnumber(cursor.getString(16));
                    mFarmer.setMobilenumber(cursor.getString(17));
                    mFarmer.setDOB(cursor.getString(18));
                    mFarmer.setAge(cursor.getInt(19));
                    mFarmer.setEmail(cursor.getString(20));
                    mFarmer.setCategorytypeid(cursor.getInt(21));
                    mFarmer.setAnnualincometypeid((cursor.getInt(22) == 0) ? null : cursor.getInt(22));
                    mFarmer.setAddresscode(cursor.getString(23));
                    mFarmer.setEducationtypeid((cursor.getInt(24) == 0) ? null : cursor.getInt(24));
                    mFarmer.setIsactive(cursor.getInt(25));
                    mFarmer.setCreatedbyuserid(cursor.getInt(26));
                    mFarmer.setCreateddate(cursor.getString(27));
                    mFarmer.setUpdatedDate(cursor.getString(28));
                    mFarmer.setInActivatedByUserId(null);
                    mFarmer.setInactivatedDate(null);
                    mFarmer.setInactivatedReasonTypeId(null);
                    mFarmer.setUpdatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
                    mFarmer.setServerupdatedstatus(0);
                    mFarmer.setUpdatedDate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    mFarmer.setFileName(cursor.getString(34));
                    mFarmer.setFileLocation(cursor.getString(35));
                    mFarmer.setFileExtension(cursor.getString(36));

                    File imgFile = new File(mFarmer.getFileLocation());

                    if (imgFile.exists()) {
                        RandomAccessFile f = new RandomAccessFile(imgFile, "r");
                        byte[] b = new byte[(int) f.length()];
                        f.readFully(b);

                        if (b.length > 0) {
                            String base64String = android.util.Base64.encodeToString(b, android.util.Base64.NO_WRAP);
                            //android.util.Log.i("File Base64 string", "IMAGE PARSE ==>" + base64String);
                            mFarmer.setByteImage(base64String);
                        }
                    }else {
                        mFarmer.setByteImage("");
                    }

                    if (type == 1) {
                        farmerRefresh.add(mFarmer);

                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFarmer : farmerRefresh);
    }


    //get selected harvestor data
    public T getSelectedHarvestorData(final String query, int type) {

        List<HarvestorVisitDetails> harvestorRefresh = new ArrayList<>();
        Cursor cursor = null;
        HarvestorVisitDetails mharvestor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mharvestor = new HarvestorVisitDetails();
                    mharvestor.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    mharvestor.setHarvestorVisitCode(cursor.getString(cursor.getColumnIndex("HarvestorVisitCode")));
                    mharvestor.setHarvestorTypeId(cursor.getInt(cursor.getColumnIndex("HarvestorTypeId")));
                    mharvestor.setHarvestorCode(cursor.getString(cursor.getColumnIndex("HarvestorCode")));
                    mharvestor.setHasPole(cursor.getInt(cursor.getColumnIndex("HasPole")));
                    mharvestor.setTonnageCost(cursor.getDouble(cursor.getColumnIndex("TonnageCost")));
                    mharvestor.setInterval(cursor.getInt(cursor.getColumnIndex("Interval")));
                    mharvestor.setQuantity(cursor.getDouble(cursor.getColumnIndex("Quantity")));
                    mharvestor.setCCCode(cursor.getString(cursor.getColumnIndex("CCCode")));
                    mharvestor.setUnRipen(cursor.getDouble(cursor.getColumnIndex("UnRipen")));
                    mharvestor.setUnderRipe(cursor.getDouble(cursor.getColumnIndex("UnderRipe")));
                    mharvestor.setRipen(cursor.getDouble(cursor.getColumnIndex("Ripen")));
                    mharvestor.setOverRipe(cursor.getDouble(cursor.getColumnIndex("OverRipe")));
                    mharvestor.setDiseased(cursor.getDouble(cursor.getColumnIndex("Diseased")));
                    mharvestor.setEmptyBunches(cursor.getDouble(cursor.getColumnIndex("EmptyBunches")));
                    mharvestor.setFFBQualityLong(cursor.getDouble(cursor.getColumnIndex("FFBQualityLong")));
                    mharvestor.setFFBQualityMedium(cursor.getDouble(cursor.getColumnIndex("FFBQualityMedium")));
                    mharvestor.setFFBQualityShort(cursor.getDouble(cursor.getColumnIndex("FFBQualityShort")));
                    mharvestor.setFFBQualityOptimum(cursor.getDouble(cursor.getColumnIndex("FFBQualityOptimum")));
                    mharvestor.setFarmerAvailable(cursor.getInt(cursor.getColumnIndex("FarmerAvailable")));
                    //mharvestor.setDetailesInformed(cursor.getInt(cursor.getColumnIndex("DetailesInformed")));
                    if(cursor.getInt(cursor.getColumnIndex("FarmerAvailable")) == 0)
                        mharvestor.setDetailesInformed(cursor.getInt(cursor.getColumnIndex("DetailesInformed")));
                    else
                        mharvestor.setDetailesInformed(null);
                    mharvestor.setName(cursor.getString(cursor.getColumnIndex("Name")));
                    mharvestor.setMobileNumber(cursor.getString(cursor.getColumnIndex("MobileNumber")));
                    mharvestor.setVillage(cursor.getString(cursor.getColumnIndex("Village")));
                    mharvestor.setMandal(cursor.getString(cursor.getColumnIndex("Mandal")));
                    mharvestor.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mharvestor.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mharvestor.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mharvestor.setLooseFruit(cursor.getInt(cursor.getColumnIndex("LooseFruit")));
                    if(cursor.getInt(cursor.getColumnIndex("LooseFruit")) == 1)
                        mharvestor.setLooseFruitWeight(cursor.getInt(cursor.getColumnIndex("LooseFruitWeight")));
                    else
                        mharvestor.setLooseFruitWeight(null);



                    if (type == 1) {
                        harvestorRefresh.add(mharvestor);

                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mharvestor : harvestorRefresh);
    }


    //get selected harvestor history data
    public T getSelectedHarvestorHistoryData(final String query, int type) {

        List<HarvestorVisitHistory> harvestorhistoryRefresh = new ArrayList<>();
        Cursor cursor = null;
        HarvestorVisitHistory mharvestorhistory = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mharvestorhistory = new HarvestorVisitHistory();
                    mharvestorhistory.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    mharvestorhistory.setCode(cursor.getString(cursor.getColumnIndex("Code")));
                    mharvestorhistory.setName(cursor.getString(cursor.getColumnIndex("Name")));
                    mharvestorhistory.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mharvestorhistory.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mharvestorhistory.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mharvestorhistory.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));


                    if (type == 1) {
                        harvestorhistoryRefresh.add(mharvestorhistory);

                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mharvestorhistory : harvestorhistoryRefresh);
    }

    //get Harvestor Data
    public ArrayList<HarvestorDataModel> getHarvestorData(String query) {
        HarvestorDataModel harvestorDataModel = null;
        Cursor cursor = null;
        ArrayList<HarvestorDataModel> harvestorDataModelArrayList = new ArrayList<>();
        android.util.Log.v(LOG_TAG, "village query" + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    harvestorDataModel = new HarvestorDataModel();


                    harvestorDataModel.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    harvestorDataModel.setCode(cursor.getString(cursor.getColumnIndex("Code")));
                    harvestorDataModel.setName(cursor.getString(cursor.getColumnIndex("Name")));
                    harvestorDataModel.setMobileNumber(cursor.getString(cursor.getColumnIndex("MobileNumber")));
                    harvestorDataModel.setVillage(cursor.getString(cursor.getColumnIndex("Village")));
                    harvestorDataModel.setMandal(cursor.getString(cursor.getColumnIndex("Mandal")));

                    harvestorDataModelArrayList.add(harvestorDataModel);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return harvestorDataModelArrayList;
    }

    //get geotag of selected plotcode
    public int getSelectedRetakeGeoTag(final String query) {

        Plot mPlot = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ Plot IsRetakeGeoTag details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                 /*   mPlot = new Plot();
                    mPlot.setIsRetakeGeoTagRequired(cursor.getInt(0));*/

                    value = cursor.getInt(0);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return value;
    }

    //get selected plot data
    public T getSelectedPlotData(final String query, final int type) {
        List<Plot> plotList = new ArrayList<>();
        Plot mPlot = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlot = new Plot();
                    mPlot.setCode(cursor.getString(1));
                    mPlot.setFarmercode(cursor.getString(2));
                    mPlot.setIsBoundryFencing((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mPlot.setTotalplotarea(cursor.getDouble(4));
                    mPlot.setTotalpalmarea(cursor.getDouble(5));
                    mPlot.setGpsplotarea(cursor.getDouble(6));
                    mPlot.setCropincometypeid((cursor.getInt(7) == 0) ? null : cursor.getInt(7));
                    mPlot.setAddesscode(cursor.getString(8));
                    mPlot.setSurveynumber(cursor.getString(9));
                    mPlot.setAdangalnumber(cursor.getString(10));
                    mPlot.setLeftoutarea(cursor.getDouble(11));
                    mPlot.setLeftoutareacropid((cursor.getInt(12) == 0) ? null : cursor.getInt(12));
                    mPlot.setPlotownershiptypeid((cursor.getInt(13) == 0) ? null : cursor.getInt(13));
                    if (cursor.getInt(14) == 0) {
                        mPlot.setIsplothandledbycaretaker(null);
                    } else {
                        Integer careTakerType = cursor.getInt(14);
                        if (careTakerType != null && careTakerType == 2) {
                            careTakerType = 0;
                        }
                        mPlot.setIsplothandledbycaretaker(careTakerType);
                    }
                    mPlot.setCaretakername(cursor.getString(15));
                    mPlot.setCaretakercontactnumber(cursor.getString(16));
                    mPlot.setIsActive(cursor.getInt(17));
                    mPlot.setCreatedbyuserid(cursor.getInt(18));
                    mPlot.setCreateddate(cursor.getString(19));
                    mPlot.setUpdatedbyuserid(cursor.getInt(20));
                    mPlot.setUpdateddate(cursor.getString(21));
                    mPlot.setServerupdatedstatus(cursor.getInt(22));
                    mPlot.setComments(cursor.getString(23));
                    mPlot.setIsPLotSubsidySubmission((cursor.getInt(24) == 0) ? null : cursor.getInt(24));
                    mPlot.setIsPLotHavingIdCard((cursor.getInt(25) == 0) ? null : cursor.getInt(25));
                    mPlot.setIsGeoBoundariesVerification((cursor.getInt(26) == 0) ? null : cursor.getInt(26));
                    mPlot.setSuitablePalmOilArea(cursor.getDouble(27));
                    mPlot.setDateofPlanting(cursor.getString(28));
                    mPlot.setSwapingReasonId((cursor.getInt(29) == 0) ? null : cursor.getInt(29));
                    mPlot.setOriginCode((cursor.getString(30)));
                    mPlot.setReasonTypeId((cursor.getInt(31)));
                    mPlot.setInactivatedDate((cursor.getString(32)));
                    mPlot.setInactivatedByUserId((cursor.getInt(33)));
                    mPlot.setInActiveReasonTypeId((cursor.getInt(34)));
                    mPlot.setPlansToPlanInFuture((cursor.getInt(35)));
                    mPlot.setIsRetakeGeoTagRequired((cursor.getInt(36)));
                    mPlot.setTotalAreaUnderHorticulture(cursor.getFloat(37));
                    mPlot.setLandTypeId((cursor.getInt(38) == 0) ? null : cursor.getInt(38));

                    if (type == 1) {
                        plotList.add(mPlot);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPlot : plotList);
    }

    //get selected plots current crop data
    public T getSelectedPlotCurrentCropData(final String query, final int type) {
        PlotCurrentCrop mPlotCurrentCrop = null;
        List<PlotCurrentCrop> plotCurrentCropList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ PlotCurrentCrop query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    mPlotCurrentCrop = new PlotCurrentCrop();
                    mPlotCurrentCrop.setPlotcode(cursor.getString(1));
                    mPlotCurrentCrop.setCropid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mPlotCurrentCrop.setCurrentcroparea(cursor.getDouble(3));
                    mPlotCurrentCrop.setIsactive(cursor.getInt(4));
                    mPlotCurrentCrop.setCreatedbyuserid(cursor.getInt(5));
                    mPlotCurrentCrop.setCreateddate(cursor.getString(6));
                    mPlotCurrentCrop.setUpdatedbyuserid(cursor.getInt(7));
                    mPlotCurrentCrop.setUpdateddate(cursor.getString(8));
                    mPlotCurrentCrop.setServerupdatedstatus(cursor.getInt(9));
                    if (type == 1) {
                        plotCurrentCropList.add(mPlotCurrentCrop);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPlotCurrentCrop : plotCurrentCropList);
    }

    //get selected neighbour plot data
    public T getSelectedNeighbourPlotData(final String query, final int type) {
        NeighbourPlot mNeighbourPlot = null;
        List<NeighbourPlot> neighbourPlotList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ NeighbourPlot query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mNeighbourPlot = new NeighbourPlot();
                    mNeighbourPlot.setPlotCode(cursor.getString(1));
                    mNeighbourPlot.setName(cursor.getString(2));
                    mNeighbourPlot.setCropid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mNeighbourPlot.setIsactive(cursor.getInt(4));
                    mNeighbourPlot.setCreatedbyuserid(cursor.getInt(5));
                    mNeighbourPlot.setCreateddate(cursor.getString(6));
                    mNeighbourPlot.setUpdatedbyuserid(cursor.getInt(7));
                    mNeighbourPlot.setUpdateddate(cursor.getString(8));
                    mNeighbourPlot.setServerupdatedstatus(cursor.getInt(9));
                    if (type == 1) {
                        neighbourPlotList.add(mNeighbourPlot);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mNeighbourPlot : neighbourPlotList);
    }

    //get selected Id proof data
    public T getSelectedIdProofsData(final String query, final int type) {
        IdentityProof mIdentityProof = null;
        List<IdentityProof> mIdentityProofList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ IdentityProof query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mIdentityProof = new IdentityProof();
                    mIdentityProof.setFarmercode(cursor.getString(1));
                    mIdentityProof.setIdprooftypeid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mIdentityProof.setIdproofnumber(cursor.getString(3));
                    mIdentityProof.setIsActive(cursor.getInt(4));
                    mIdentityProof.setCreatedbyuserid(cursor.getInt(5));
                    mIdentityProof.setCreatedDate(cursor.getString(6));
                    mIdentityProof.setUpdatedbyuserid(cursor.getInt(7));
                    mIdentityProof.setUpdatedDate(cursor.getString(8));
                    mIdentityProof.setServerUpdatedStatus(cursor.getInt(9));
                    if (type == 1) {
                        mIdentityProofList.add(mIdentityProof);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mIdentityProof : mIdentityProofList);
    }

    //gets if proof data
    public T getIdProofsData(final String query, final int type) {
        IdentityProofRefresh mIdentityProof = null;
        List<IdentityProofRefresh> mIdentityProofList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ IdentityProof query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mIdentityProof = new IdentityProofRefresh();
                    mIdentityProof.setFarmercode(cursor.getString(1));
                    mIdentityProof.setIdprooftypeid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mIdentityProof.setIdproofnumber(cursor.getString(3));
                    mIdentityProof.setIsActive(cursor.getInt(4));
                    mIdentityProof.setCreatedbyuserid(cursor.getInt(5));
                    mIdentityProof.setCreatedDate(cursor.getString(6));
                    mIdentityProof.setUpdatedbyuserid(cursor.getInt(7));
                    mIdentityProof.setUpdatedDate(cursor.getString(8));
                    mIdentityProof.setServerUpdatedStatus(cursor.getInt(9));
                    mIdentityProof.setFileName(cursor.getString(10));
                    mIdentityProof.setFileLocation(cursor.getString(11));
                    mIdentityProof.setFileExtension(cursor.getString(12));
                    File imgFile = new File(mIdentityProof.getFileLocation());
                    if (imgFile.exists()) {
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(imgFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bm = BitmapFactory.decodeStream(fis);
                        bm = ImageUtility.rotatePicture(90, bm);
                        String base64string = ImageUtility.convertBitmapToString(bm);
                        mIdentityProof.setByteImage(base64string);
                    } else {
                        mIdentityProof.setByteImage("");
                    }

                    if (type == 1) {
                        mIdentityProofList.add(mIdentityProof);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mIdentityProof : mIdentityProofList);
    }


    //get farmer history data
    public T getFarmerHistoryData(final String query) {
        FarmerHistory mFarmerHistory = null;
        List<FarmerHistory> mFarmerHistoryList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmerHistory details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFarmerHistory = new FarmerHistory();
                    mFarmerHistory.setFarmercode(cursor.getString(1));
                    mFarmerHistory.setPlotcode(cursor.getString(2));
                    mFarmerHistory.setStatustypeid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mFarmerHistory.setIsactive(cursor.getInt(4));
                    mFarmerHistory.setCreatedbyuserid(cursor.getInt(5));
                    mFarmerHistory.setCreateddate(cursor.getString(6));
                    mFarmerHistory.setUpdatedbyuserid(cursor.getInt(7));
                    mFarmerHistory.setUpdateddate(cursor.getString(8));
                    mFarmerHistory.setServerUpdatedStatus(cursor.getInt(9));

                    mFarmerHistoryList.add(mFarmerHistory);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting Farmer History details " + e.getMessage());
        }

        return (T) (mFarmerHistoryList);
    }

    //get selected farmer bank data
    public T getSelectedFarmerBankData(final String query, final int type) {
        FarmerBank mFarmerBank = null;
        List<FarmerBank> mFarmerBankList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFarmerBank = new FarmerBank();
                    mFarmerBank.setFarmercode(cursor.getString(1));
                    mFarmerBank.setBankid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mFarmerBank.setAccountholdername(cursor.getString(3));
                    mFarmerBank.setAccountnumber(cursor.getString(4));
                    mFarmerBank.setFilename(cursor.getString(5));
                    mFarmerBank.setFilelocation(cursor.getString(6));
                    mFarmerBank.setFileextension(cursor.getString(7));
                    mFarmerBank.setIsActive(cursor.getInt(8));
                    mFarmerBank.setCreatedbyuserid(cursor.getInt(9));
                    mFarmerBank.setCreatedDate(cursor.getString(10));
                    mFarmerBank.setUpdatedbyuserid(cursor.getInt(11));
                    mFarmerBank.setUpdatedDate(cursor.getString(12));
                    mFarmerBank.setServerUpdatedStatus(cursor.getInt(13));
                    // mFarmerBank.setBranchId((cursor.getInt(14)== 0) ? null :cursor.getInt(14));
                    if (type == 1) {
                        mFarmerBankList.add(mFarmerBank);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFarmerBank : mFarmerBankList);
    }

    //get farmer bank data
    public T getFarmerBankData(final String query, final int type) {
        FarmerBankRefresh mFarmerBank = null;
        List<FarmerBankRefresh> mFarmerBankList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFarmerBank = new FarmerBankRefresh();
                    mFarmerBank.setFarmercode(cursor.getString(1));
                    mFarmerBank.setBankid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mFarmerBank.setAccountholdername(cursor.getString(3));
                    mFarmerBank.setAccountnumber(cursor.getString(4));
                    mFarmerBank.setFilename(cursor.getString(5));
                    mFarmerBank.setFilelocation(cursor.getString(6));
                    mFarmerBank.setFileextension(cursor.getString(7));
                    mFarmerBank.setIsActive(cursor.getInt(8));
                    mFarmerBank.setCreatedbyuserid(cursor.getInt(9));
                    mFarmerBank.setCreatedDate(cursor.getString(10));
                    mFarmerBank.setUpdatedbyuserid(cursor.getInt(11));
                    mFarmerBank.setUpdatedDate(cursor.getString(12));
                    mFarmerBank.setServerUpdatedStatus(cursor.getInt(13));
                    // mFarmerBank.setBranchId((cursor.getInt(14)== 0) ? null :cursor.getInt(14));
                    File imgFile = new File(mFarmerBank.getFilelocation());
                    if (imgFile.exists()) {
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(imgFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bm = BitmapFactory.decodeStream(fis);
                        bm = ImageUtility.rotatePicture(90, bm);
                        String base64string = ImageUtility.convertBitmapToString(bm);
                        mFarmerBank.setByteImage(base64string);
                    } else {
                        mFarmerBank.setByteImage("");
                    }
                    if (type == 1) {
                        mFarmerBankList.add(mFarmerBank);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFarmerBank : mFarmerBankList);
    }

    //get water resource data
    public T getWaterResourceData(final String query, final int type) {
        WaterResource mWaterResource = null;
        List<WaterResource> mWaterResourceList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mWaterResource = new WaterResource();
                    mWaterResource.setPlotcode(cursor.getString(1));
                    mWaterResource.setSourceofwaterid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mWaterResource.setBorewellnumber((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mWaterResource.setWaterdischargecapacity(cursor.getDouble(4));
                    mWaterResource.setCanalwater(cursor.getDouble(5));
                    mWaterResource.setIsactive(cursor.getInt(6));
                    mWaterResource.setCreatedbyuserid(cursor.getInt(7));
                    mWaterResource.setCreateddate(cursor.getString(8));
                    mWaterResource.setUpdatedbyuserid(cursor.getInt(9));
                    mWaterResource.setUpdateddate(cursor.getString(10));
                    mWaterResource.setServerupdatedstatus(cursor.getInt(11));
                    if (type == 1) {
                        mWaterResourceList.add(mWaterResource);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mWaterResource : mWaterResourceList);
    }

    //get soil resource data
    public T getSoilResourceData(final String query, final int type) {
        SoilResource mSoilResource = null;
        List<SoilResource> mSoilResourceeList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mSoilResource = new SoilResource();
                    mSoilResource.setPlotcode(cursor.getString(1));
                    mSoilResource.setSoiltypeid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mSoilResource.setIspoweravailable((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mSoilResource.setAvailablepowerhours(cursor.getDouble(4));
                    mSoilResource.setPrioritizationtypeid((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mSoilResource.setComments(cursor.getString(6));
                    mSoilResource.setCreatedbyuserid(cursor.getInt(7));
                    mSoilResource.setCreateddate(cursor.getString(8));
                    mSoilResource.setUpdatedbyuserid(cursor.getInt(9));
                    mSoilResource.setUpdateddate(cursor.getString(10));
                    mSoilResource.setServerupdatedstatus(cursor.getInt(11));
                    mSoilResource.setIrrigatedArea(cursor.getFloat(12));
                    mSoilResource.setSoilNatureId((cursor.getInt(13) == 0) ? null : cursor.getInt(13));

                    if (type == 1) {
                        mSoilResourceeList.add(mSoilResource);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mSoilResource : mSoilResourceeList);
    }

    //to get plot irrigation xref data
    public T getPlotIrrigationXRefData(final String query, final int type) {
        PlotIrrigationTypeXref mPlotIrrigationTypeXref = null;
        List<PlotIrrigationTypeXref> mPlotIrrigationTypeXrefList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlotIrrigationTypeXref = new PlotIrrigationTypeXref();
                    mPlotIrrigationTypeXref.setPlotcode(cursor.getString(1));
                    mPlotIrrigationTypeXref.setName(cursor.getString(2));
                    mPlotIrrigationTypeXref.setIrrigationtypeid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mPlotIrrigationTypeXref.setIsactive(cursor.getInt(4));
                    mPlotIrrigationTypeXref.setCreatedbyuserid(cursor.getInt(5));
                    mPlotIrrigationTypeXref.setCreateddate(cursor.getString(6));
                    mPlotIrrigationTypeXref.setUpdatedbyuserid(cursor.getInt(7));
                    mPlotIrrigationTypeXref.setUpdateddate(cursor.getString(8));
                    mPlotIrrigationTypeXref.setServerupdatedstatus(cursor.getInt(9));
                    mPlotIrrigationTypeXref.setRecmIrrgId(cursor.getInt(cursor.getColumnIndex("RecmIrrgId")));
                    if (type == 1) {
                        mPlotIrrigationTypeXrefList.add(mPlotIrrigationTypeXref);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPlotIrrigationTypeXref : mPlotIrrigationTypeXrefList);
    }

    //get plantation data
    public T getPlantationDataset(final String query, final int type) {
        Plantation mPlantation = null;
        List<Plantation> mPlantationList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlantation = new Plantation();
                    mPlantation.setPlotcode(cursor.getString(1));
                    mPlantation.setNurserycode((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mPlantation.setSaplingsourceid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mPlantation.setSaplingvendorid((cursor.getInt(4) == 0) ? null : cursor.getInt(4));
                    mPlantation.setCropVarietyId((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mPlantation.setAllotedarea(cursor.getDouble(6));
                    mPlantation.setTreescount(cursor.getInt(7));
                    mPlantation.setCreatedbyuserid(cursor.getInt(8));
                    mPlantation.setCreateddate(cursor.getString(9));
                    mPlantation.setUpdatedbyuserid(cursor.getInt(10));
                    mPlantation.setUpdateddate(cursor.getString(11));
                    mPlantation.setIsActive(cursor.getInt(12));
                    mPlantation.setServerUpdatedStatus(cursor.getInt(13));
                    mPlantation.setReasonTypeId(cursor.getInt(14));
                    mPlantation.setGFReceiptNumber(cursor.getString(15));
                   mPlantation.setSaplingsplanted((cursor.getInt(16) == 0) ? 0 : cursor.getInt(16));
                    //mPlantation.setSaplingsplanted(cursor.getInt(16));
                    mPlantation.setMissingPlantsComments((cursor.getString(17)));
                    if (type == 1) {
                        mPlantationList.add(mPlantation);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }

        return (T) ((type == 0) ? mPlantation : mPlantationList);
    }

    //get crop vareity
    public String getCropVariety(final String query, final int type) {
        ArrayList<String> arrCropVariety = new ArrayList<>();
        String resultCropVariety = "";
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    arrCropVariety.add(cursor.getString(0));


                } while (cursor.moveToNext());
            }
            Set<String> hs = new HashSet<>(arrCropVariety);
            arrCropVariety.clear();
            arrCropVariety.addAll(hs);

            for (int i = 0; i < arrCropVariety.size(); i++) {

                cursor = mDatabase.rawQuery("select Name from LookUp where id=" + arrCropVariety.get(i), null);
                if (cursor != null && cursor.moveToFirst()) {
                    do {

                        resultCropVariety = resultCropVariety + cursor.getString(0) + "\n";


                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        // resultCropVariety.replace("@",",");
        return resultCropVariety;
    }

    //get followup data
    public T getFollowupData(final String query, final int type) {
        FollowUp mFollowUp = null;
        List<FollowUp> mFollowUpList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ FollowUp query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFollowUp = new FollowUp();
                    mFollowUp.setPlotcode(cursor.getString(1));
                    mFollowUp.setIsfarmerreadytoconvert(cursor.getInt(2));
                    mFollowUp.setIssuedetails(cursor.getString(3));
                    mFollowUp.setComments(cursor.getString(4));
                    mFollowUp.setPotentialscore((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mFollowUp.setHarvestingmonth(cursor.getString(6));
                    mFollowUp.setCreatedbyuserid(cursor.getInt(7));
                    mFollowUp.setCreateddate(cursor.getString(8));
                    mFollowUp.setUpdatedbyuserid(cursor.getInt(9));
                    mFollowUp.setUpdateddate(cursor.getString(10));
                    mFollowUp.setServerupdatedstatus(cursor.getInt(11));
                    mFollowUp.setExpectedMonthofSowing(cursor.getString(12));
                    if (type == 1) {
                        mFollowUpList.add(mFollowUp);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFollowUp : mFollowUpList);
    }

    //get market survery data
    public T getMarketSurveyData(final String query, final int type) {
        MarketSurvey mMarketSurvey = null;
        List<MarketSurvey> mMarketSurveyList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mMarketSurvey = new MarketSurvey();
                    mMarketSurvey.setVillageId(cursor.getInt(1));
                    mMarketSurvey.setSurveyDate(cursor.getString(2));
                    mMarketSurvey.setFarmerCode(cursor.getString(3));
                    mMarketSurvey.setFarmerName(cursor.getString(4));
                    mMarketSurvey.setFamilyMembersCount(cursor.getInt(5));
                    mMarketSurvey.setReason(cursor.getString(6));
                    mMarketSurvey.setIsFarmerWillingtoUse((cursor.getInt(7) == 0) ? null : cursor.getInt(7));
                    mMarketSurvey.setEstimatedAmounttoPay(cursor.getDouble(8));
                    mMarketSurvey.setIsFarmerUseSmartPhone((cursor.getInt(9) == 0) ? null : cursor.getInt(9));
                    mMarketSurvey.setIsCattleExist((cursor.getInt(10) == 0) ? null : cursor.getInt(10));
                    mMarketSurvey.setCattleTypeId((cursor.getInt(11) == 0) ? null : cursor.getInt(11));
                    mMarketSurvey.setCattlesCount(cursor.getInt(12));
                    mMarketSurvey.setIsFarmerHavingOwnVehicle((cursor.getInt(13) == 0) ? null : cursor.getInt(13));
                    mMarketSurvey.setVehicleTypeId((cursor.getInt(14) == 0) ? null : cursor.getInt(14));
                    mMarketSurvey.setIsActive(cursor.getInt(15));
                    mMarketSurvey.setCreatedByUserId(cursor.getInt(16));
                    mMarketSurvey.setCreatedDate(cursor.getString(17));
                    mMarketSurvey.setUpdatedByUserId(cursor.getInt(18));
                    mMarketSurvey.setUpdatedDate(cursor.getString(19));
                    mMarketSurvey.setServerUpdatedStatus(cursor.getInt(20));
                    mMarketSurvey.setCode(cursor.getString(21));
                    mMarketSurvey.setFarmerMobileNumber(cursor.getString(22));
                    if (type == 1) {
                        mMarketSurveyList.add(mMarketSurvey);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mMarketSurvey : mMarketSurveyList);
    }

    //get referrals data
    public T getReferralsData(final String query, final int type) {
        Referrals mReferrals = null;
        List<Referrals> mReferralsList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mReferrals = new Referrals();
                    mReferrals.setMandalname(cursor.getString(1));
                    mReferrals.setVillageName(cursor.getString(2));
                    mReferrals.setFarmername(cursor.getString(3));
                    mReferrals.setContactnumber(cursor.getString(4));
                    mReferrals.setCreatedbyuserid(cursor.getInt(5));
                    mReferrals.setCreateddate(cursor.getString(6));
                    mReferrals.setUpdatedbyuserid(cursor.getInt(7));
                    mReferrals.setUpdatedDate(cursor.getString(8));
                    mReferrals.setServerUpdatedStatus(cursor.getInt(9));
                    if (type == 1) {
                        mReferralsList.add(mReferrals);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mReferrals : mReferralsList);
    }

    //get visitlog data
    public T getVisitLogData(final String query) {
        Cursor cursor = null;
        VisitLog visitLog = null;
        List<VisitLog> visitLogList = new ArrayList<>();
        Log.v(LOG_TAG, "@@@ VisitLog data " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    visitLog = new VisitLog();
                    visitLog.setClientName(cursor.getString(1));
                    visitLog.setMobileNumber(cursor.getString(2));
                    visitLog.setLocation(cursor.getString(3));
                    visitLog.setDetails(cursor.getString(4));
                    visitLog.setLatitude(cursor.getFloat(5));
                    visitLog.setLongitude(cursor.getFloat(6));
                    visitLog.setCreatedByUserId(cursor.getInt(7));
                    visitLog.setCreatedDate(cursor.getString(8));
                    visitLog.setServerUpdatedStatus(cursor.getInt(9));
                    visitLogList.add(visitLog);
                } while (cursor.moveToNext());
            }
        } catch (Exception se) {
            Log.e(LOG_TAG, "@@@ getting VisitLog details " + se.getMessage());
            se.printStackTrace();
        }
        return (T) (visitLogList);
    }

    //get user sync data
    public T getUserSyncData(final String query) {
        Cursor cursor = null;
        UserSync userSync = null;
        List<UserSync> userSyncList = new ArrayList<>();
        Log.v(LOG_TAG, "@@@ UserSync data " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    userSync = new UserSync();
                    userSync.setUserId(cursor.getInt(cursor.getColumnIndex("UserId")));
                    userSync.setApp(cursor.getString(cursor.getColumnIndex("App")));
                    userSync.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                    userSync.setMasterSync(cursor.getInt(cursor.getColumnIndex("MasterSync")));
                    userSync.setTransactionSync(cursor.getInt(cursor.getColumnIndex("TransactionSync")));
                    userSync.setResetData(cursor.getInt(cursor.getColumnIndex("ResetData")));
                    userSync.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    userSync.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    userSync.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    userSync.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    userSync.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    userSync.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    userSyncList.add(userSync);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@  UserSyncdetails " + e.getMessage());
            e.printStackTrace();
        }
        return (T) (userSyncList);

    }


    //get file repository data
    public T getFileRepositoryData(final String query, final int type) {
        List<FileRepositoryRefresh> mFileRepositoryList = new ArrayList<>();
        FileRepositoryRefresh mFileRepository = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ FileRepository query " + query);
        try {

            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFileRepository = new FileRepositoryRefresh();
                    mFileRepository.setFarmerCode(cursor.getString(1));
                    int moduleTypeId = cursor.getInt(3);
                    mFileRepository.setModuleTypeId(moduleTypeId);
                    if (moduleTypeId == 193) {
                        mFileRepository.setPlotCode(null);
                        mFileRepository.setCropMaintenanceCode(null);
                    } else if (moduleTypeId == 303) {
                        mFileRepository.setPlotCode(cursor.getString(2));
                        mFileRepository.setCropMaintenanceCode(null);
                    } else {
                        mFileRepository.setPlotCode(cursor.getString(2));

                        mFileRepository.setCropMaintenanceCode(cursor.getString(13));
                    }
                    mFileRepository.setFileName(cursor.getString(4));
                    mFileRepository.setFileLocation(cursor.getString(5));
                    mFileRepository.setFileExtension(cursor.getString(6));
                    mFileRepository.setIsActive(cursor.getInt(7));
                    mFileRepository.setCreatedByUserId(cursor.getInt(8));
                    mFileRepository.setCreatedDate(cursor.getString(9));
                    mFileRepository.setUpdatedByUserId(cursor.getInt(10));
                    mFileRepository.setUpdatedDate(cursor.getString(11));
                    mFileRepository.setServerUpdatedStatus(cursor.getInt(12));
                    // mFileRepository.setCropMaintenanceCode(cursor.getString(13));
                    File imgFile = new File(mFileRepository.getFileLocation());
                    if (imgFile.exists()) {
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(imgFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bm = BitmapFactory.decodeStream(fis);
                        bm = ImageUtility.rotatePicture(90, bm);
                        String base64string = ImageUtility.convertBitmapToString(bm);
                        mFileRepository.setByteImage(base64string);
                    } else {
                        mFileRepository.setByteImage("");
                    }
                    if (type == 1) {
                        mFileRepositoryList.add(mFileRepository);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFileRepository : mFileRepositoryList);
    }

    //Falog_Tracking...
    public T getGpsTrackingData(final String query, final int type) {
        LocationTracker mGpsBoundaries = null;
        List<LocationTracker> mGpsBoundariesList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ GeoBoundaries query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mGpsBoundaries = new LocationTracker();

                    mGpsBoundaries.setUserId(cursor.getInt(1));
                    mGpsBoundaries.setLatitude(cursor.getDouble(2));
                    mGpsBoundaries.setLongitude(cursor.getDouble(3));
                    mGpsBoundaries.setAddress(cursor.getString(4));
                    mGpsBoundaries.setLogDate(cursor.getString(5));
                    //mGpsBoundaries.setServerUpdatedStatus(cursor.getInt(6));
                    if (type == 1) {
                        mGpsBoundariesList.add(mGpsBoundaries);
                    }

                    Log.v(LOG_TAG, "Lat@Log" + String.valueOf(mGpsBoundariesList));


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mGpsBoundaries : mGpsBoundariesList);

    }

    //get geotag data
    public T getGeoTagData(final String query, final int type) {
        GeoBoundaries mGeoBoundaries = null;
        List<GeoBoundaries> mGeoBoundariesList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ GeoBoundaries query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mGeoBoundaries = new GeoBoundaries();
                    mGeoBoundaries.setPlotcode(cursor.getString(1));
                    mGeoBoundaries.setLatitude(cursor.getDouble(2));
                    mGeoBoundaries.setLongitude(cursor.getDouble(3));
                    mGeoBoundaries.setGeocategorytypeid((cursor.getInt(4) == 0) ? null : cursor.getInt(4));
                    mGeoBoundaries.setCreatedbyuserid(cursor.getInt(5));
                    mGeoBoundaries.setCreateddate(cursor.getString(6));
                    mGeoBoundaries.setUpdatedbyuserid(cursor.getInt(7));
                    mGeoBoundaries.setUpdateddate(cursor.getString(8));
                    mGeoBoundaries.setServerupdatedstatus(cursor.getInt(9));
                    String cropMainCode = cursor.getString(10);
                    mGeoBoundaries.setCropMaintenanceCode((TextUtils.isEmpty(cropMainCode) || cropMainCode.equalsIgnoreCase("null") ? null : cropMainCode));
                    if (type == 1) {
                        mGeoBoundariesList.add(mGeoBoundaries);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mGeoBoundaries : mGeoBoundariesList);
    }

    //get selected filerepo data
    public FileRepository getSelectedFileRepository(String query) {
        FileRepository savedPictureData = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ FileRepository details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    savedPictureData = new FileRepository();
                    savedPictureData.setFarmercode(cursor.getString(1));
                    savedPictureData.setPlotcode(cursor.getString(2));
                    savedPictureData.setModuletypeid(cursor.getInt(3));
                    savedPictureData.setFilename(cursor.getString(4));
                    savedPictureData.setPicturelocation(cursor.getString(5));
                    savedPictureData.setFileextension(cursor.getString(6));
                    savedPictureData.setIsActive((cursor.getInt(7)));
                    savedPictureData.setCreatedbyuserid(cursor.getInt(8));
                    savedPictureData.setCreatedDate(cursor.getString(9));
                    savedPictureData.setUpdatedbyuserid(cursor.getInt(10));
                    savedPictureData.setUpdatedDate(cursor.getString(11));
                    savedPictureData.setServerUpdatedStatus(cursor.getInt(12));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting getSelectedFileRepository " + e.getMessage());
        }
        return savedPictureData;
    }


    //get lookup data
    public LinkedHashMap<String, String> getLookUpData11(String query) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        Cursor cursor = mDatabase.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    linkedHashMap.put(cursor.getString(cursor.getColumnIndex("Code")),
                            cursor.getString(cursor.getColumnIndex("Name")));


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linkedHashMap;

    }


    //get village list
    public ArrayList<Village> mgetVillageList(String query) {
        Village village = null;
        Cursor cursor = null;
        ArrayList<Village> villageArrayList = new ArrayList<>();
        android.util.Log.v(LOG_TAG, "village query" + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    village = new Village();

                    village.setName(cursor.getString(cursor.getColumnIndex("Name")));

                    villageArrayList.add(village);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return villageArrayList;
    }

//get plantation data
    public T getPlantationData(final String query, final int type) {
        Plantation mPlantation = null;
        List<Plantation> mPlantationList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ Plantation query " + query);
        try {

            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlantation = new Plantation();
                    mPlantation.setPlotcode(cursor.getString(1));
                    mPlantation.setNurserycode((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mPlantation.setSaplingsourceid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mPlantation.setSaplingvendorid((cursor.getInt(4) == 0) ? null : cursor.getInt(4));
                    mPlantation.setCropVarietyId((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mPlantation.setAllotedarea(cursor.getDouble(6));
                    mPlantation.setTreescount(cursor.getInt(7));
                    mPlantation.setCreatedbyuserid(cursor.getInt(8));
                    mPlantation.setCreateddate(cursor.getString(9));
                    mPlantation.setUpdatedbyuserid(cursor.getInt(10));
                    mPlantation.setUpdateddate(cursor.getString(11));
                    mPlantation.setIsActive(cursor.getInt(12));
                    mPlantation.setServerUpdatedStatus(cursor.getInt(13));
                    mPlantation.setReasonTypeId((cursor.getInt(14) == 0) ? null : cursor.getInt(14));
                    mPlantation.setGFReceiptNumber(cursor.getString(15));
                    mPlantation.setSaplingsplanted((cursor.getInt(16) == 0) ? null : cursor.getInt(16));
                    // mPlantation.setSaplingsplanted(cursor.getInt(16));
                    mPlantation.setMissingPlantsComments(cursor.getString(17));



                    if (type == 1) {
                        mPlantationList.add(mPlantation);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPlantation : mPlantationList);
    }

    //get plots landlords data
    public T getPlotLandLordData(final String query, final int type) {
        PlotLandlord mPlotLandlord = null;
        List<PlotLandlord> mPlotLandlordList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {

            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlotLandlord = new PlotLandlord();
                    mPlotLandlord.setPlotcode(cursor.getString(1));
                    mPlotLandlord.setLandlordname(cursor.getString(2));
                    mPlotLandlord.setLandlordcontactnumber(cursor.getString(3));
                    mPlotLandlord.setLeasestartdate(cursor.getString(4));
                    mPlotLandlord.setLeaseenddate(cursor.getString(5));
                    mPlotLandlord.setIsactive(cursor.getInt(6));
                    mPlotLandlord.setCreatedbyuserid(cursor.getInt(7));
                    mPlotLandlord.setCreateddate(cursor.getString(8));
                    mPlotLandlord.setUpdatedbyuserid(cursor.getInt(9));
                    mPlotLandlord.setUpdateddate(cursor.getString(10));
                    mPlotLandlord.setServerupdatedstatus(cursor.getInt(11));
                    if (type == 1) {
                        mPlotLandlordList.add(mPlotLandlord);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPlotLandlord : mPlotLandlordList);
    }

//get crop maintenance history data
    public T getCropMaintanceHistoryData(final String query, final int type) {
        CropMaintenanceHistory cropMaintenanceHistory = null;
        List<CropMaintenanceHistory> maintenanceHistoryList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    cropMaintenanceHistory = new CropMaintenanceHistory();
                    cropMaintenanceHistory.setCode(cursor.getString(1));
                    cropMaintenanceHistory.setPlotCode(cursor.getString(2));
                    cropMaintenanceHistory.setIsActive(cursor.getInt(3));
                    cropMaintenanceHistory.setCreatedByUserId(cursor.getInt(4));
                    cropMaintenanceHistory.setCreatedDate(cursor.getString(5));
                    cropMaintenanceHistory.setUpdatedByUserId(cursor.getInt(6));
                    cropMaintenanceHistory.setUpdatedDate(cursor.getString(7));
                    cropMaintenanceHistory.setServerUpdatedStatus(cursor.getInt(8));
                    cropMaintenanceHistory.setName(cursor.getString(9));
                    if (type == 1) {
                        maintenanceHistoryList.add(cropMaintenanceHistory);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? cropMaintenanceHistory : maintenanceHistoryList);
    }

    //get landlord bank data
    public T getLandLordBankData(final String query, final int type) {
        LandlordBank mLandlordBank = null;
        List<LandlordBank> mLandlordBankList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mLandlordBank = new LandlordBank();
                    mLandlordBank.setPlotcode(cursor.getString(1));
                    mLandlordBank.setAccountholdername(cursor.getString(2));
                    mLandlordBank.setAccountnumber(cursor.getString(3));
                    mLandlordBank.setBankid(cursor.getInt(4));
                    mLandlordBank.setFilename(cursor.getString(5));
                    mLandlordBank.setFilelocation(cursor.getString(6));
                    mLandlordBank.setFileextension(cursor.getString(7));
                    mLandlordBank.setIsActive(cursor.getInt(8));
                    mLandlordBank.setCreatedbyuserid(cursor.getInt(9));
                    mLandlordBank.setCreatedDate(cursor.getString(10));
                    mLandlordBank.setUpdatedbyuserid(cursor.getInt(11));
                    mLandlordBank.setUpdatedDate(cursor.getString(12));
                    mLandlordBank.setServerUpdatedStatus(cursor.getInt(13));
                    if (type == 1) {
                        mLandlordBankList.add(mLandlordBank);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mLandlordBank : mLandlordBankList);
    }

    //get landlord Id proof data
    public T getLandLordIDProofsData(final String query, final int type) {
        LandlordIdProof mLandlordIdProof = null;
        List<LandlordIdProof> mLandlordIdProofList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mLandlordIdProof = new LandlordIdProof();
                    mLandlordIdProof.setPlotCode(cursor.getString(1));
                    mLandlordIdProof.setIDProofTypeId((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mLandlordIdProof.setIdProofNumber(cursor.getString(3));
                    mLandlordIdProof.setIsActive(cursor.getInt(4));
                    mLandlordIdProof.setCreatedByUserId(cursor.getInt(5));
                    mLandlordIdProof.setCreatedDate(cursor.getString(6));
                    mLandlordIdProof.setUpdatedByUserId(cursor.getInt(7));
                    mLandlordIdProof.setUpdatedDate(cursor.getString(8));
                    mLandlordIdProof.setServerUpdatedStatus(cursor.getInt(9));
                    if (type == 1) {
                        mLandlordIdProofList.add(mLandlordIdProof);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mLandlordIdProof : mLandlordIdProofList);
    }


    //get image details
    public List<ImageDetails> getImageDetails() {
        List<ImageDetails> imageDetailsList = new ArrayList<>();
        Cursor cursor = null;
        try {
            // mDatabase = palm3FoilDatabase.getReadableDatabase();
            cursor = mDatabase.rawQuery(Queries.getInstance().getImageDetails(), null);
            if (cursor.moveToFirst()) {
                do {
                    try {
                        if (cursor.getString(3) != null) {
                            ImageDetails rec = new ImageDetails();
                            rec.setFarmerCode(cursor.getString(0));
                            rec.setPlotCode(cursor.getString(1));
                            if (cursor.getInt(2) == 193) {
                                rec.setTableName(DatabaseKeys.TABLE_FARMER);
                            } else {
                                rec.setTableName("");
                            }
                            if (cursor.getString(3) != null && cursor.getString(3).length() > 0) {
                                String filepath = cursor.getString(3);
                                File imagefile = new File(filepath);
                                FileInputStream fis = null;
                                try {
                                    fis = new FileInputStream(imagefile);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                Bitmap bm = BitmapFactory.decodeStream(fis);
                                bm = ImageUtility.rotatePicture(90, bm);
                                String base64string = ImageUtility.convertBitmapToString(bm);
                                rec.setImageString(base64string);
                                imageDetailsList.add(rec);

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();

        }
        return imageDetailsList;
    }

    //get cooking oil data
    public T getCookingOilData(final String query, final int type) {
        CookingOil mCookingOil = null;
        List<CookingOil> mCookingOilList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mCookingOil = new CookingOil();
                    mCookingOil.setMarketSurveyCode(cursor.getString(1));
                    mCookingOil.setCookingoiltypeid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mCookingOil.setBrandname(cursor.getString(3));
                    mCookingOil.setMonthlyquantity(cursor.getDouble(4));
                    mCookingOil.setTotalpaidamount(cursor.getDouble(5));
                    mCookingOil.setCreatedbyuserid(cursor.getInt(6));
                    mCookingOil.setCreateddate(cursor.getString(7));
                    mCookingOil.setUpdatedbyuserid(cursor.getInt(8));
                    mCookingOil.setUpdateddate(cursor.getString(9));
                    mCookingOil.setServerUpdatedStatus(cursor.getInt(10));
                    if (type == 1) {
                        mCookingOilList.add(mCookingOil);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mCookingOil : mCookingOilList);
    }

    //get diseases data
    public T getDiseaseData(final String query, final int type) {
        Disease mDisease = null;
        List<Disease> mDiseaseList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mDisease = new Disease();
                    mDisease.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mDisease.setIsdiseasenoticedinpreviousvisit((cursor.getInt(cursor.getColumnIndex("IsDiseaseNoticedinPreviousVisit")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsDiseaseNoticedinPreviousVisit")));
                    mDisease.setIsproblemrectified((cursor.getInt(cursor.getColumnIndex("IsProblemRectified")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsProblemRectified")));
                    mDisease.setProblemrectifiedcomments(cursor.getString(cursor.getColumnIndex("ProblemRectifiedComments")));
                    mDisease.setDiseaseid((cursor.getInt(cursor.getColumnIndex("DiseaseId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("DiseaseId")));
                    mDisease.setChemicalid((cursor.getInt(cursor.getColumnIndex("ChemicalId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("ChemicalId")));
                    mDisease.setIsresultseen((cursor.getInt(cursor.getColumnIndex("IsResultSeen")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsResultSeen")));
                    mDisease.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                    mDisease.setIsactive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mDisease.setCreatedbyuserid(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mDisease.setCreateddate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mDisease.setUpdatedbyuserid(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mDisease.setUpdateddate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mDisease.setServerupdatedstatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mDisease.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mDisease.setRecommendFertilizerProviderId((cursor.getInt(cursor.getColumnIndex("RecommendedChemicalId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("RecommendedChemicalId")));
                    mDisease.setRecommendDosage(cursor.getDouble(cursor.getColumnIndex("Dosage")));
                    mDisease.setRecommendUOMId((cursor.getInt(cursor.getColumnIndex("UOMId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("UOMId")));
                    mDisease.setPercTreesId(cursor.getInt(cursor.getColumnIndex("PercTreesId")));
                    mDisease.setIsControlMeasure(cursor.getInt((cursor.getColumnIndex("IsControlMeasure"))));
                    mDisease.setRecommendedUOMId((cursor.getInt(cursor.getColumnIndex("RecommendedUOMId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("RecommendedUOMId")));

                    if (type == 1) {
                        mDiseaseList.add(mDisease);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mDisease : mDiseaseList);
    }

    public T getSaplingsData(final String query, final int type) {
        Disease mDisease = null;
        List<Disease> mDiseaseList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mDisease = new Disease();
                    mDisease.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mDisease.setIsdiseasenoticedinpreviousvisit((cursor.getInt(cursor.getColumnIndex("IsDiseaseNoticedinPreviousVisit")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsDiseaseNoticedinPreviousVisit")));
                    mDisease.setIsproblemrectified((cursor.getInt(cursor.getColumnIndex("IsProblemRectified")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsProblemRectified")));
                    mDisease.setProblemrectifiedcomments(cursor.getString(cursor.getColumnIndex("ProblemRectifiedComments")));
                    mDisease.setDiseaseid((cursor.getInt(cursor.getColumnIndex("DiseaseId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("DiseaseId")));
                    mDisease.setChemicalid((cursor.getInt(cursor.getColumnIndex("ChemicalId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("ChemicalId")));
                    mDisease.setIsresultseen((cursor.getInt(cursor.getColumnIndex("IsResultSeen")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsResultSeen")));
                    mDisease.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                    mDisease.setIsactive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mDisease.setCreatedbyuserid(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mDisease.setCreateddate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mDisease.setUpdatedbyuserid(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mDisease.setUpdateddate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mDisease.setServerupdatedstatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mDisease.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mDisease.setRecommendFertilizerProviderId((cursor.getInt(cursor.getColumnIndex("RecommendedChemicalId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("RecommendedChemicalId")));
                    mDisease.setRecommendDosage(cursor.getDouble(cursor.getColumnIndex("Dosage")));
                    mDisease.setRecommendUOMId((cursor.getInt(cursor.getColumnIndex("UOMId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("UOMId")));
                    mDisease.setPercTreesId(cursor.getInt(cursor.getColumnIndex("PercTreesId")));
                    mDisease.setIsControlMeasure(cursor.getInt((cursor.getColumnIndex("IsControlMeasure"))));
                    mDisease.setRecommendedUOMId(cursor.getInt(cursor.getColumnIndex("RecommendedUOMId")));

                    if (type == 1) {
                        mDiseaseList.add(mDisease);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mDisease : mDiseaseList);
    }

    //get fertilizer data
    public T getFertilizerData(final String query, final int type) {
        Fertilizer mFertilizer = null;
        List<Fertilizer> mFertilizerList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFertilizer = new Fertilizer();
                    mFertilizer.setPlotcode(cursor.getString(1));
                    mFertilizer.setFertilizersourcetypeid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mFertilizer.setFertilizerid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mFertilizer.setFertilizerproviderid((cursor.getInt(4) == 0) ? null : cursor.getInt(4));
                    mFertilizer.setUomid((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mFertilizer.setDosage(cursor.getDouble(6));
                    mFertilizer.setLastapplieddate(cursor.getString(7));
                    mFertilizer.setApplyfertilizerfrequencytypeid((cursor.getInt(8) == 0) ? null : cursor.getInt(8));
                    mFertilizer.setRatescale((cursor.getInt(9) == 0) ? null : cursor.getInt(9));
                    mFertilizer.setComments(cursor.getString(10));
                    mFertilizer.setIsactive(cursor.getInt(11));
                    mFertilizer.setCreatedbyuserid(cursor.getInt(12));
                    mFertilizer.setCreateddate(cursor.getString(13));
                    mFertilizer.setUpdatedbyuserid(cursor.getInt(14));
                    mFertilizer.setUpdateddate(cursor.getString(15));
                    mFertilizer.setServerupdatedstatus(cursor.getInt(16));
                    mFertilizer.setCropMaintenanceCode(cursor.getString(17));
                    mFertilizer.setSourceName(cursor.getString(18));
                    mFertilizer.setIsFertilizerApplied(cursor.getInt(19));
                    mFertilizer.setApplicationYear(cursor.getInt(20));
                    mFertilizer.setApplicationMonth(cursor.getString(21));
                    mFertilizer.setQuarter(cursor.getInt(22));
                    mFertilizer.setApplicationType(cursor.getString(23));
                    mFertilizer.setBioFertilizerId(cursor.getInt(24));
                    //mFertilizer.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    //mFertilizer.setSourceName(cursor.getString(cursor.getColumnIndex("SourceName")));
                    if (type == 1) {
                        mFertilizerList.add(mFertilizer);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFertilizer : mFertilizerList);
    }

//get fertilizer previous quater details
    public T getFertilizerPrevQtrdtls(final String query, final int type) {
        Fertilizer mFertilizer = null;
        List<Fertilizer> mFertilizerList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFertilizer = new Fertilizer();

                    mFertilizer.setSourceName(cursor.getString(0));
                    mFertilizer.setApplicationYear(cursor.getInt(1));
                    mFertilizer.setApplicationMonth(cursor.getString(2));
                    mFertilizer.setApplicationType(cursor.getString(3));

                    mFertilizer.setComments(cursor.getString(4));
                    mFertilizer.setFertilizersourcetypeid(cursor.getInt(5));

                    //mFertilizer.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    //mFertilizer.setSourceName(cursor.getString(cursor.getColumnIndex("SourceName")));
                    if (type == 1) {
                        mFertilizerList.add(mFertilizer);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFertilizer : mFertilizerList);
    }

    /* public T getRecommndFertilizerData(final String query, final int type) {
         RecommndFertilizer mFertilizer = null;
         List<RecommndFertilizer> mFertilizerList = new ArrayList<>();
         Cursor cursor = null;
         Log.v(LOG_TAG, "@@@ farmer details query " + query);
         try {
             cursor = mDatabase.rawQuery(query, null);
             if (cursor != null && cursor.moveToFirst()) {
                 do {
                     mFertilizer = new RecommndFertilizer();
                     mFertilizer.setPlotcode(cursor.getString(1));
                     mFertilizer.setCropMaintenanceCode((""*//*+cursor.getInt(2) == 0) ? null : cursor.getInt(2)*//*));
                    mFertilizer.setRecommendFertilizerProviderId((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mFertilizer.setRecommendDosage((cursor.getInt(4) == 0) ? null : cursor.getInt(4));
                    mFertilizer.setRecommendUOMId((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                   *//* mFertilizer.setDosage(cursor.getDouble(6));
                    mFertilizer.setLastapplieddate(cursor.getString(7));
                    mFertilizer.setApplyfertilizerfrequencytypeid((cursor.getInt(8) == 0) ? null : cursor.getInt(8));
                    mFertilizer.setRatescale((cursor.getInt(9) == 0) ? null : cursor.getInt(9));*//*
                    mFertilizer.setComments(cursor.getString(6));
                    mFertilizer.setIsactive(cursor.getInt(7));
                    mFertilizer.setCreatedbyuserid(cursor.getInt(8));
                    mFertilizer.setCreateddate(cursor.getString(9));
                    mFertilizer.setUpdatedbyuserid(cursor.getInt(10));
                    mFertilizer.setUpdateddate(cursor.getString(11));
                    mFertilizer.setServerupdatedstatus(cursor.getInt(12));
                    mFertilizer.setCropMaintenanceCode(*//*cursor.getString(13)*//*"");
                    if (type == 1) {
                        mFertilizerList.add(mFertilizer);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFertilizer : mFertilizerList);
    }*/

    //get fertilizer provider data
    public T getFertilizerProviderData(final String query, final int type) {
        FertilizerProvider mFertilizerProvider = null;
        List<FertilizerProvider> mFertilizerProviderList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ FertilizerProvider query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFertilizerProvider = new FertilizerProvider();
                    mFertilizerProvider.setName(cursor.getString(1));
                    mFertilizerProvider.setFertilizerTypeId((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mFertilizerProvider.setIsActive(cursor.getInt(3));
                    mFertilizerProvider.setCreatedByUserId(cursor.getInt(4));
                    mFertilizerProvider.setCreatedDate(cursor.getString(5));
                    mFertilizerProvider.setUpdatedByUserId(cursor.getInt(6));
                    mFertilizerProvider.setUpdatedDate(cursor.getString(7));
                    mFertilizerProvider.setServerUpdatedStatus(cursor.getInt(8));
                    if (type == 1) {
                        mFertilizerProviderList.add(mFertilizerProvider);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFertilizerProvider : mFertilizerProviderList);
    }

    //get harvest data
    public T getHarvestData(final String query, final int type) {
        Harvest mHarvest = null;
        List<Harvest> mHarvestList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ Harvest query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mHarvest = new Harvest();
                    mHarvest.setPlotCode(cursor.getString(1));
                    mHarvest.setPlotyield(cursor.getDouble(2));
                    mHarvest.setYieldperhactor(cursor.getDouble(3));
                    mHarvest.setCollectioncenterid((cursor.getInt(4) == 0) ? null : cursor.getInt(4));
                    mHarvest.setTransportmodetypeid((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mHarvest.setVehicletypeid((cursor.getInt(6) == 0) ? null : cursor.getInt(6));
                    mHarvest.setTransportpaidamount(cursor.getInt(7));
                    mHarvest.setHarvestingmethodtypeid((cursor.getInt(8) == 0) ? null : cursor.getInt(8));
                    mHarvest.setWagesperday(cursor.getDouble(9));
                    mHarvest.setHarvestingtypeid((cursor.getInt(10) == 0) ? null : cursor.getInt(10));
                    mHarvest.setComments(cursor.getString(11));
                    mHarvest.setIsActive(cursor.getInt(12));
                    mHarvest.setCreatedbyuserid(cursor.getInt(13));
                    mHarvest.setCreateddate(cursor.getString(14));
                    mHarvest.setUpdatedbyuserid(cursor.getInt(15));
                    mHarvest.setUpdateddate(cursor.getString(16));
                    mHarvest.setServerUpdatedStatus(cursor.getInt(17));
                    mHarvest.setCropMaintenanceCode(cursor.getString(18));
                    mHarvest.setWagesUnitTypeId((cursor.getInt(19) == 0) ? null : cursor.getInt(19));
                    mHarvest.setContractAmount(cursor.getDouble(20));
                    if (type == 1) {
                        mHarvestList.add(mHarvest);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mHarvest : mHarvestList);
    }


    //get health plantation data
    public T getHealthplantationData(final String query, final int type) {
        Healthplantation mHealthplantation = null;
        List<Healthplantation> mHealthplantationList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ Healthplantation query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    mHealthplantation = new Healthplantation();
                    mHealthplantation.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mHealthplantation.setPlantationstatetypeid((cursor.getInt(cursor.getColumnIndex("PlantationStateTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("PlantationStateTypeId")));
                    mHealthplantation.setTreesappearancetypeid((cursor.getInt(cursor.getColumnIndex("TreesAppearanceTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("TreesAppearanceTypeId")));
                    mHealthplantation.setTreegirthtypeid((cursor.getInt(cursor.getColumnIndex("TreeGirthTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("TreeGirthTypeId")));
                    mHealthplantation.setTreeheighttypeid((cursor.getInt(cursor.getColumnIndex("TreeHeightTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("TreeHeightTypeId")));
                    mHealthplantation.setFruitcolortypeid((cursor.getInt(cursor.getColumnIndex("FruitColorTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("FruitColorTypeId")));
                    mHealthplantation.setFruitsizetypeid((cursor.getInt(cursor.getColumnIndex("FruitSizeTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("FruitSizeTypeId")));
                    mHealthplantation.setFruithyegienetypeid((cursor.getInt(cursor.getColumnIndex("FruitHyegieneTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("FruitHyegieneTypeId")));
                    mHealthplantation.setPlantationtypeid((cursor.getInt(cursor.getColumnIndex("PlantationTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("PlantationTypeId")));
                    mHealthplantation.setIsactive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mHealthplantation.setCreatedbyuserid(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mHealthplantation.setCreateddate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mHealthplantation.setUpdatedbyuserid(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mHealthplantation.setUpdateddate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mHealthplantation.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mHealthplantation.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mHealthplantation.setSpearleafId(cursor.getInt(cursor.getColumnIndex("SpearleafId")));
                    mHealthplantation.setSpearLeafRating(cursor.getString(cursor.getColumnIndex("SpearLeafRating")));
                    mHealthplantation.setDiseasesRating(cursor.getString(cursor.getColumnIndex("DiseasesRating")));
                    mHealthplantation.setPestRating(cursor.getString(cursor.getColumnIndex("PestRating")));
                    mHealthplantation.setWeevilsRating(cursor.getString(cursor.getColumnIndex("WeevilsRating")));
                    mHealthplantation.setInflorescenceRating(cursor.getString(cursor.getColumnIndex("InflorescenceRating")));
                    mHealthplantation.setBasinHealthRating(cursor.getString(cursor.getColumnIndex("BasinHealthRating")));
                    mHealthplantation.setNutDefRating(cursor.getString(cursor.getColumnIndex("NutDefRating")));
                    mHealthplantation.setNoOfFlorescene(cursor.getInt(cursor.getColumnIndex("NoOfFlorescene")));
                    mHealthplantation.setNoOfBuches(cursor.getInt(cursor.getColumnIndex("NoOfBuches")));
                    mHealthplantation.setBunchWeight(cursor.getInt(cursor.getColumnIndex("BunchWeight")));
                    if (type == 1) {
                        mHealthplantationList.add(mHealthplantation);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mHealthplantation : mHealthplantationList);
    }

    //get intercrop plantationxref data
    public T getInterCropPlantationXrefData(final String query, final int type) {
        InterCropPlantationXref mInterCropPlantationXref = null;
        List<InterCropPlantationXref> mInterCropPlantationXrefList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    mInterCropPlantationXref = new InterCropPlantationXref();
                    mInterCropPlantationXref.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mInterCropPlantationXref.setCropId((cursor.getInt(cursor.getColumnIndex("CropId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("CropId")));
                    mInterCropPlantationXref.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mInterCropPlantationXref.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mInterCropPlantationXref.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mInterCropPlantationXref.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mInterCropPlantationXref.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mInterCropPlantationXref.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mInterCropPlantationXref.setRecmCropId(cursor.getInt(cursor.getColumnIndex("RecmCropId")));
                    if (cursor.getString(8).equalsIgnoreCase("")) {
                        mInterCropPlantationXref.setCropMaintenanceCode(null);
                    } else {
                        mInterCropPlantationXref.setCropMaintenanceCode(cursor.getString(8));
                    }
                    if (type == 1) {
                        mInterCropPlantationXrefList.add(mInterCropPlantationXref);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mInterCropPlantationXref : mInterCropPlantationXrefList);
    }


    //get recommended fertilizer data
    public T getRecomFertlizerData(final String query, final int type) {
        RecommndFertilizer mRecomFertilizer = null;
        List<RecommndFertilizer> mNutrientList = new ArrayList<>();
        Cursor customCursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            customCursor = mDatabase.rawQuery(query, null);
            CustomCursor cursor = new CustomCursor(customCursor);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mRecomFertilizer = new RecommndFertilizer();
                    mRecomFertilizer.setPlotcode(cursor.getString(1));
                    mRecomFertilizer.setCropMaintenanceCode(cursor.getString(2));
                    mRecomFertilizer.setRecommendFertilizerProviderId((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mRecomFertilizer.setRecommendDosage((cursor.getDouble(4) == 0) ? null : cursor.getDouble(4));
                    mRecomFertilizer.setRecommendUOMId((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mRecomFertilizer.setComments(cursor.getString(6));
                    mRecomFertilizer.setIsactive(cursor.getInt(7));
                    mRecomFertilizer.setCreatedbyuserid(cursor.getInt(8));
                    mRecomFertilizer.setCreateddate(cursor.getString(9));
                    mRecomFertilizer.setUpdatedbyuserid(cursor.getInt(10));
                    mRecomFertilizer.setUpdateddate(cursor.getString(11));
                    mRecomFertilizer.setServerupdatedstatus(cursor.getInt(12));
                    //mNutrient.setCropMaintenanceCode(cursor.getString(16));
                    if (type == 1) {
                        mNutrientList.add(mRecomFertilizer);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting mRecomFertilizer details " + e.getMessage());
        }
        return (T) ((type == 0) ? mRecomFertilizer : mNutrientList);
    }

    //get Nutrient data
    public T getNutrientData(final String query, final int type) {
        Nutrient mNutrient = null;
        List<Nutrient> mNutrientList = new ArrayList<>();
        Cursor customCursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            customCursor = mDatabase.rawQuery(query, null);
            CustomCursor cursor = new CustomCursor(customCursor);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mNutrient = new Nutrient();
                    mNutrient.setPlotcode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mNutrient.setIsPreviousNutrientDeficiency((cursor.getInt(cursor.getColumnIndex("IsPreviousNutrientDeficiency")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsPreviousNutrientDeficiency")));
                    mNutrient.setIsproblemrectified((cursor.getInt(cursor.getColumnIndex("IsProblemRectified")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsProblemRectified")));
                    mNutrient.setIscurrentnutrientdeficiency((cursor.getInt(cursor.getColumnIndex("IsCurrentNutrientDeficiency")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsCurrentNutrientDeficiency")));
                    mNutrient.setNutrientid((cursor.getInt(cursor.getColumnIndex("NutrientId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("NutrientId")));
                    mNutrient.setChemicalid((cursor.getInt(cursor.getColumnIndex("ChemicalId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("ChemicalId")));
                    mNutrient.setApplynutrientfrequencytypeid((cursor.getInt(cursor.getColumnIndex("ApplyNutrientFrequencyTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("ApplyNutrientFrequencyTypeId")));
                    mNutrient.setIsresultseen((cursor.getInt(cursor.getColumnIndex("IsResultSeen")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsResultSeen")));
                    mNutrient.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                    mNutrient.setIsactive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mNutrient.setCreatedbyuserid(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mNutrient.setCreateddate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mNutrient.setUpdatedbyuserid(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mNutrient.setUpdateddate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mNutrient.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mNutrient.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mNutrient.setRecommendFertilizerProviderId((cursor.getInt(cursor.getColumnIndex("RecommendedFertilizerId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("RecommendedFertilizerId")));
                    mNutrient.setRecommendDosage(cursor.getDouble(cursor.getColumnIndex("Dosage")));
                    mNutrient.setRecommendUOMId((cursor.getInt(cursor.getColumnIndex("UOMId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("UOMId")));
                    mNutrient.setPercTreesId(cursor.getInt(cursor.getColumnIndex("PercTreesId")));
                    mNutrient.setRecommendedUOMId((cursor.getInt(cursor.getColumnIndex("RecommendedUOMId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("RecommendedUOMId")));

                    if (type == 1) {
                        mNutrientList.add(mNutrient);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mNutrient : mNutrientList);
    }

    //get ownership file repo data
    public T getOwnershipfilerepositoryData(final String query, final int type) {
        Ownershipfilerepository mOwnershipfilerepository = null;
        List<Ownershipfilerepository> mOwnershipfilerepositoryList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mOwnershipfilerepository = new Ownershipfilerepository();
                    mOwnershipfilerepository.setFarmerCode(cursor.getString(1));
                    mOwnershipfilerepository.setPlotcode(cursor.getString(2));
                    mOwnershipfilerepository.setModuletypeid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mOwnershipfilerepository.setFilename(cursor.getString(4));
                    mOwnershipfilerepository.setFilelocation(cursor.getString(5));
                    mOwnershipfilerepository.setFileextension(cursor.getString(6));
                    mOwnershipfilerepository.setComments(cursor.getString(7));
                    mOwnershipfilerepository.setIsactive(cursor.getInt(8));
                    mOwnershipfilerepository.setCreatedbyuserid(cursor.getInt(9));
                    mOwnershipfilerepository.setCreateddate(cursor.getString(10));
                    mOwnershipfilerepository.setUpdatedbyuserid(cursor.getInt(11));
                    mOwnershipfilerepository.setUpdateddate(cursor.getString(12));
                    mOwnershipfilerepository.setServerupdatedstatus(cursor.getInt(13));
                    if (type == 1) {
                        mOwnershipfilerepositoryList.add(mOwnershipfilerepository);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mOwnershipfilerepository : mOwnershipfilerepositoryList);
    }

    //get Pest data
    public T getPestData(final String query, final int type) {
        Pest mPest = null;
        List<Pest> mPestList = new ArrayList<>();
        Cursor cursor = null;

        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    mPest = new Pest();
                    mPest.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mPest.setPestid((cursor.getInt(cursor.getColumnIndex("PestId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("PestId")));
                    mPest.setIsresultsseen((cursor.getInt(cursor.getColumnIndex("IsResultsSeen")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsResultsSeen")));
                    mPest.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                    mPest.setIsactive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mPest.setCreatedbyuserid(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mPest.setCreateddate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mPest.setUpdatedbyuserid(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mPest.setUpdateddate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mPest.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mPest.setCode(cursor.getString(cursor.getColumnIndex("Code")));
                    mPest.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mPest.setRecommendFertilizerProviderId((cursor.getInt(cursor.getColumnIndex("RecommendedChemicalId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("RecommendedChemicalId")));
                    mPest.setRecommendDosage(cursor.getDouble(cursor.getColumnIndex("Dosage")));
                    mPest.setRecommendUOMId((cursor.getInt(cursor.getColumnIndex("UOMId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("UOMId")));
                    mPest.setPercTreesId(cursor.getInt(cursor.getColumnIndex("PercTreesId")));
                    mPest.setIsControlMeasure(cursor.getInt(cursor.getColumnIndex("IsControlMeasure")));
                    mPest.setRecommendedUOMId((cursor.getInt(cursor.getColumnIndex("RecommendedUOMId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("RecommendedUOMId")));

                    if (type == 1) {
                        mPestList.add(mPest);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting pest details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPest : mPestList);
    }

    //get Uprootment data
    public T getUprootmentData(final String query, final int type) {
        Uprootment mUprootment = null;
        List<Uprootment> mUprootmentList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mUprootment = new Uprootment();
                    mUprootment.setPlotcode(cursor.getString(1));
                    mUprootment.setSeedsplanted(cursor.getInt(2));
                    mUprootment.setPlamscount(cursor.getInt(3));
                    mUprootment.setIstreesmissing(cursor.getInt(4));
                    mUprootment.setMissingtreescount(cursor.getInt(5));
                    mUprootment.setReasontypeid((cursor.getInt(6) == 0) ? null : cursor.getInt(6));
                    mUprootment.setComments(cursor.getString(7));
                    mUprootment.setIsactive(cursor.getInt(8));
                    mUprootment.setCreatedbyuserid(cursor.getInt(9));
                    mUprootment.setCreateddate(cursor.getString(10));
                    mUprootment.setUpdatedbyuserid(cursor.getInt(11));
                    mUprootment.setUpdateddate(cursor.getString(12));
                    mUprootment.setServerupdatedstatus(cursor.getInt(13));
                    mUprootment.setCropMaintenanceCode(cursor.getString(14));
                    mUprootment.setExpectedPlamsCount(cursor.getInt(15));
                    if (type == 1) {
                        mUprootmentList.add(mUprootment);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mUprootment : mUprootmentList);
    }

    //get Weed data
    public T getWeedData(final String query, final int type) {
        Weed mWeed = null;
        List<Weed> mWeedList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mWeed = new Weed();
                    mWeed.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mWeed.setIsweedproperlydone((cursor.getInt(cursor.getColumnIndex("IsWeedProperlyDone")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsWeedProperlyDone")));
                    mWeed.setMethodtypeid((cursor.getInt(cursor.getColumnIndex("MethodTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("MethodTypeId")));
                    mWeed.setChemicalid((cursor.getInt(cursor.getColumnIndex("ChemicalId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("ChemicalId")));
                    mWeed.setApplicationfrequency((cursor.getInt(cursor.getColumnIndex("ApplicationFrequency")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("ApplicationFrequency")));
                    mWeed.setIsprunning((cursor.getInt(cursor.getColumnIndex("IsPrunning")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsPrunning")));
                    mWeed.setPrunningfrequency((cursor.getInt(cursor.getColumnIndex("PrunningFrequency")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("PrunningFrequency")));
                    mWeed.setIsmulchingseen((cursor.getInt(cursor.getColumnIndex("IsMulchingSeen")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsMulchingSeen")));
                    mWeed.setIsweavilsseen((cursor.getInt(cursor.getColumnIndex("IsWeavilsSeen")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsWeavilsSeen")));
                    mWeed.setIsactive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mWeed.setCreatedbyuserid(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mWeed.setCreateddate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mWeed.setUpdatedbyuserid(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mWeed.setUpdateddate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mWeed.setServerupdatedstatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mWeed.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mWeed.setBasinHealthId(cursor.getInt(cursor.getColumnIndex("BasinHealthId")));
                    mWeed.setPruningId(cursor.getInt(cursor.getColumnIndex("PruningId")));
                    mWeed.setWeedId(cursor.getInt(cursor.getColumnIndex("WeedId")));
                    mWeed.setWeevilsId(cursor.getInt(cursor.getColumnIndex("WeevilsId")));
                    mWeed.setInflorescenceId(cursor.getInt(cursor.getColumnIndex("InflorescenceId")));


                    if (type == 1) {
                        mWeedList.add(mWeed);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mWeed : mWeedList);
    }


    //get yield data
    public T getYieldData(final String query, final int type) {
        YieldAssessment mYield = null;
        List<YieldAssessment> mYieldList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mYield = new YieldAssessment();
                    mYield.setCropMaintenaceCode(cursor.getString(cursor.getColumnIndex("CropMaintenaceCode")));
                    mYield.setQuestion(cursor.getString(cursor.getColumnIndex("Question")));
                    mYield.setAnswer(cursor.getString(cursor.getColumnIndex("Answer")));
                    mYield.setValue(cursor.getString(cursor.getColumnIndex("Value")));
                    mYield.setYear(cursor.getInt(cursor.getColumnIndex("Year")));
                    mYield.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mYield.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mYield.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mYield.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mYield.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mYield.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));

                    if (type == 1) {
                        mYieldList.add(mYield);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mYield : mYieldList);
    }


    //get white data
    public T getWhiteData(final String query, final int type) {
        WhiteFlyAssessment mWhite = null;
        List<WhiteFlyAssessment> mWhiteList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mWhite = new WhiteFlyAssessment();
                    mWhite.setCropMaintenaceCode(cursor.getString(cursor.getColumnIndex("CropMaintenaceCode")));
                    mWhite.setQuestion(cursor.getString(cursor.getColumnIndex("Question")));
                    mWhite.setAnswer(cursor.getString(cursor.getColumnIndex("Answer")));
                    mWhite.setValue(cursor.getString(cursor.getColumnIndex("Value")));
                    mWhite.setYear(cursor.getInt(cursor.getColumnIndex("Year")));
                    mWhite.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mWhite.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mWhite.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mWhite.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mWhite.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mWhite.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));


                    if (type == 1) {
                        mWhiteList.add(mWhite);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mWhite : mWhiteList);
    }


    //get identity proof file repo xref data
    public T getIdentityProofFileRepositoryXrefData(final String query, final int type) {
        IdentityProofFileRepositoryXref mIdentityProofFileRepositoryXref = null;
        List<IdentityProofFileRepositoryXref> mIdentityProofFileRepositoryXrefList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mIdentityProofFileRepositoryXref = new IdentityProofFileRepositoryXref();
                    mIdentityProofFileRepositoryXref.setIdentityProofId((cursor.getInt(0) == 0) ? null : cursor.getInt(0));
                    mIdentityProofFileRepositoryXref.setFileRepositoryId((cursor.getInt(1) == 0) ? null : cursor.getInt(1));
                    mIdentityProofFileRepositoryXref.setServerUpdatedStatus(cursor.getInt(2));
                    if (type == 1) {
                        mIdentityProofFileRepositoryXrefList.add(mIdentityProofFileRepositoryXref);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mIdentityProofFileRepositoryXref : mIdentityProofFileRepositoryXrefList);
    }

    //get pestchemical xref data
    public T getPestChemicalXrefData(final String query, final int type) {
        PestChemicalXref mPestChemicalXref = null;
        List<PestChemicalXref> mPestChemicalXrefList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPestChemicalXref = new PestChemicalXref();
                    mPestChemicalXref.setPestCode(cursor.getString(1));
                    mPestChemicalXref.setChemicalId((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mPestChemicalXref.setCreatedByUserId(cursor.getInt(3));
                    mPestChemicalXref.setCreatedDate(cursor.getString(4));
                    mPestChemicalXref.setUpdatedByUserId(cursor.getInt(5));
                    mPestChemicalXref.setUpdatedDate(cursor.getString(6));
                    mPestChemicalXref.setServerUpdatedStatus(cursor.getInt(7));
                    mPestChemicalXref.setCropMaintenanceCode(cursor.getString(8));
                    if (type == 1) {
                        mPestChemicalXrefList.add(mPestChemicalXref);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPestChemicalXref : mPestChemicalXrefList);
    }

    //get plantation file repo xref data
    public T getPlantationFileRepositoryXrefData(final String query, final int type) {
        PlantationFileRepositoryXref mPlantationFileRepositoryXref = null;
        List<PlantationFileRepositoryXref> mPlantationFileRepositoryXrefList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlantationFileRepositoryXref = new PlantationFileRepositoryXref();
                    mPlantationFileRepositoryXref.setPlantationId((cursor.getInt(0) == 0) ? null : cursor.getInt(0));
                    mPlantationFileRepositoryXref.setPlantationId((cursor.getInt(1) == 0) ? null : cursor.getInt(1));
                    mPlantationFileRepositoryXref.setServerUpdatedStatus(cursor.getInt(2));
                    if (type == 1) {
                        mPlantationFileRepositoryXrefList.add(mPlantationFileRepositoryXref);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPlantationFileRepositoryXref : mPlantationFileRepositoryXrefList);
    }

    //get digital contract data
    public T getDigitalContractData(final String query, final int type) {
        DigitalContract mDigitalContract = null;
        List<DigitalContract> mDigitalContractList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mDigitalContract = new DigitalContract();
                    mDigitalContract.setName(cursor.getString(1));
                    mDigitalContract.setFILENAME(cursor.getString(2));
                    mDigitalContract.setFileLocation(cursor.getString(3));
                    mDigitalContract.setFileExtension(cursor.getString(4));
                    mDigitalContract.setStateId((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mDigitalContract.setIsActive(cursor.getInt(6));
                    mDigitalContract.setCreatedByUserId(cursor.getInt(7));
                    mDigitalContract.setCreatedDate(cursor.getString(8));
                    mDigitalContract.setUpdatedByUserId(cursor.getInt(9));
                    mDigitalContract.setUpdatedDate(cursor.getString(10));
                    mDigitalContract.setServerUpdatedStatus(cursor.getInt(11));
                    if (type == 1) {
                        mDigitalContractList.add(mDigitalContract);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mDigitalContract : mDigitalContractList);
    }

    //update plot status
    public void upDataPlotStatus(String plotCode) {
        if (checkValueExistedInDatabase(Queries.getInstance().isPlotExisted(DatabaseKeys.TABLE_FARMERHISTORY, plotCode))) {
            ContentValues update_values = new ContentValues();
            update_values.put("IsActive", "0");
            update_values.put("ServerUpdatedStatus", "0");
            update_values.put("UpdatedByUserId", CommonConstants.USER_ID);
            update_values.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            String where = " PlotCode = '" + plotCode + "'" + " and IsActive = '1'";
            mDatabase.update("FarmerHistory", update_values, where, null);
        }
    }

    //update plot current crop status
    public void upDataPlotCureentCropStatus(String plotCode) {
        if (checkValueExistedInDatabase(Queries.getInstance().isPlotExisted(DatabaseKeys.TABLE_PLOTCURRENTCROP, plotCode))) {
            ContentValues update_values = new ContentValues();
            update_values.put("IsActive", "0");
            update_values.put("ServerUpdatedStatus", "0");
            update_values.put("UpdatedByUserId", CommonConstants.USER_ID);
            update_values.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            String where = " PlotCode = '" + plotCode + "'" + " and IsActive = '1'";
            mDatabase.update("PlotCurrentCrop", update_values, where, null);
        }
    }

    //update notification status
    public void upNotificationStatus() {
        ContentValues update_values = new ContentValues();
        update_values.put("ServerUpdatedStatus", "0");
        update_values.put("isRead", 1);
        update_values.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        String where = " isRead ='" + 0 + "'";
        mDatabase.update("Alerts", update_values, where, null);
    }

    //update complaint status
    public void updateComplaintStatus(String complaintCode) {
        ContentValues update_values = new ContentValues();
        update_values.put("IsActive", "0");
        update_values.put("ServerUpdatedStatus", "0");
        update_values.put("UpdatedByUserId", CommonConstants.USER_ID);
        update_values.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        String where = " ComplaintCode ='" + complaintCode + "'" + " and IsActive = '1'";
        mDatabase.update("ComplaintStatusHistory", update_values, where, null);
    }

    //update complaint file path
    public void updateComplaintFilePath(String complaintCode, String filePath) {
        ContentValues update_values = new ContentValues();
        update_values.put("FileLocation", filePath);
        String where = "ComplaintCode ='" + complaintCode + "'" + " and FileExtension='.mp3'";
        mDatabase.update("ComplaintRepository", update_values, where, null);
    }

    //get prospective plot details
    public List<ProspectivePlotsModel> getProspectivePlotDetails(String farmerCode, int plotStatus) {
        List<ProspectivePlotsModel> plotsModels = new ArrayList<>();
        Cursor cursor = null;
        String query = Queries.getInstance().getPlotDetails(farmerCode.trim(), plotStatus);
        Log.v(LOG_TAG, "Query for getting plots related to farmer " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ProspectivePlotsModel plotsModel = new ProspectivePlotsModel();
                    plotsModel.setPlotID(cursor.getString(0));
                    plotsModel.setPlotArea(cursor.getDouble(1));
                    plotsModel.setPlotVillageName(cursor.getString(2));
                    plotsModel.setMandalName(cursor.getString(3));
                    plotsModel.setPlotIncome(cursor.getString(4));
                    plotsModel.setPotentialScore(cursor.getInt(5));
                    plotsModel.setPlotCrops(cursor.getString(6));
                    plotsModel.setLastVisitedDate(cursor.getString(7));
                    plotsModels.add(plotsModel);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return plotsModels;
    }

    //get activity log data
    public List<ActivityLog> getActivityLogData() {
        List<ActivityLog> activityLogs = new ArrayList<>();
        Cursor cursor = null;
        String query = Queries.getInstance().queryActivityLog();
        Log.v(LOG_TAG, "Query for getting plots related to farmer " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ActivityLog activityLog = new ActivityLog();
                    activityLog.setFarmerCode(cursor.getString(1));
                    activityLog.setPlotCode(cursor.getString(2));
                    activityLog.setCollectionCode(null);
                    activityLog.setComplaintCode(null);
                    activityLog.setActivityTypeId(cursor.getInt(5));
                    activityLog.setCreatedByUserId(cursor.getInt(6));
                    activityLog.setCreatedDate(cursor.getString(7));
                    activityLog.setServerUpdatedStatus(cursor.getInt(8));
                    activityLog.setConsignmentCode(null);
                    activityLogs.add(activityLog);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return activityLogs;
    }

//get FAlog loglongs
    public String getFalogLatLongs(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        String latlongData = "";
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.getCount() > 0 && genericDataQuery.moveToFirst()) {
                do {
                    latlongData = (genericDataQuery.getDouble(0) + "-" + genericDataQuery.getDouble(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return latlongData;
    }

    //get latlongs
    public String getLatLongs(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        String latlongData = "";
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.getCount() > 0 && genericDataQuery.moveToFirst()) {
                do {
                    latlongData = (genericDataQuery.getDouble(0) + "-" + genericDataQuery.getDouble(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return latlongData;
    }

    //get complaints data by plot
    public List<ComplaintsDetails> getComplaintsDataByPlot(String plotCode, String farmerCode) {
        List<ComplaintsDetails> complaintsDetailsesArrayList = new ArrayList<>();
        Cursor cursor = null;
        String qurey = Queries.getInstance().getComplaintsDataByPlot(plotCode, farmerCode);
        Log.v(LOG_TAG, "Query for getting complaints of plot " + qurey);
        try {
            cursor = mDatabase.rawQuery(qurey, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ComplaintsDetails complaintsDetails = new ComplaintsDetails();
                    complaintsDetails.setComplaintId(cursor.getString(0));
                    complaintsDetails.setComplaintTypeId(cursor.getString(1));
                    complaintsDetails.setAssigntoUserId(cursor.getString(2));
                    complaintsDetails.setStatusTypeId(cursor.getString(3));
                    complaintsDetails.setCriticalityByTypeId(cursor.getString(4));
                    complaintsDetails.setComplaintRaisedon(cursor.getString(5));
                    complaintsDetails.setPlotId(cursor.getString(6));
                    complaintsDetails.setfarmerFirstName(cursor.getString(7));
                    complaintsDetails.setFarmerLastName(cursor.getString(8));
                    complaintsDetails.setVillage(cursor.getString(9));
                    complaintsDetailsesArrayList.add(complaintsDetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return complaintsDetailsesArrayList;
    }

    //get complaints
    public T getComplaints(final String query, int type) {
        List<Complaints> complaintsList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for getting complaints " + query);
        Complaints complaints = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    complaints = new Complaints();
                    complaints.setCode(cursor.getString(1));
                    complaints.setPlotCode(cursor.getString(2));
                    complaints.setCriticalityByTypeId(null);
                    complaints.setIsActive(cursor.getInt(4));
                    complaints.setCreatedByUserId(cursor.getInt(5));
                    complaints.setCreatedDate(cursor.getString(6));
                    complaints.setUpdatedByUserId(cursor.getInt(7));
                    complaints.setUpdatedDate(cursor.getString(8));
                    complaints.setServerUpdatedStatus(cursor.getInt(9));
                    if (type == 1) {
                        complaintsList.add(complaints);
                    }
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? complaints : complaintsList);
    }

//get complaint repo
    public T getComplaintRepository(final String query, int type) {
        List<ComplaintRepository> complaintRepositories = new ArrayList<>();
        Cursor cursor = null;
        ComplaintRepository complaintRepository = null;
        Log.v(LOG_TAG, "Query for getting complaints " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    complaintRepository = new ComplaintRepository();
                    complaintRepository.setComplaintCode(cursor.getString(1));
                    complaintRepository.setModuleTypeId(cursor.getInt(2));
                    complaintRepository.setFileName(cursor.getString(3));
                    complaintRepository.setFileLocation(cursor.getString(4));
                    complaintRepository.setFileExtension(cursor.getString(5));
                    complaintRepository.setIsVideoRecording(cursor.getInt(6));
                    complaintRepository.setIsResult(cursor.getInt(7));
                    complaintRepository.setIsActive(cursor.getInt(8));
                    complaintRepository.setCreatedByUserId(cursor.getInt(9));
                    complaintRepository.setCreatedDate(cursor.getString(10));
                    complaintRepository.setUpdatedByUserId(cursor.getInt(11));
                    complaintRepository.setUpdatedDate(cursor.getString(12));
                    complaintRepository.setServerUpdatedStatus(cursor.getInt(13));
                    if (type == 1) {
                        complaintRepositories.add(complaintRepository);
                    }
                } while (cursor.moveToNext());

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? complaintRepository : complaintRepositories);
    }

    //get complaint refresh repo
    public T getComplaintRefreshRepository(final String query, int type) {
        List<ComplaintRepositoryRefresh> complaintRepositories = new ArrayList<>();
        Cursor cursor = null;
        ComplaintRepositoryRefresh complaintRepository = null;
        Log.v(LOG_TAG, "Query for getting complaints " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    complaintRepository = new ComplaintRepositoryRefresh();
                    complaintRepository.setcomplaintCode(cursor.getString(1));
                    complaintRepository.setModuleTypeId(cursor.getInt(2));
                    complaintRepository.setFileName(cursor.getString(3));
                    complaintRepository.setFileLocation(cursor.getString(4));
                    complaintRepository.setFileExtension(cursor.getString(5));
                    complaintRepository.setIsVideoRecording(cursor.getInt(6));
                    complaintRepository.setIsResult(cursor.getInt(7));
                    complaintRepository.setIsActive(cursor.getInt(8));
                    complaintRepository.setCreatedByUserId(cursor.getInt(9));
                    complaintRepository.setCreatedDate(cursor.getString(10));
                    complaintRepository.setUpdatedByUserId(cursor.getInt(11));
                    complaintRepository.setUpdatedDate(cursor.getString(12));
                    complaintRepository.setServerUpdatedStatus(cursor.getInt(13));
                    if (complaintRepository.isIsVideoRecording() == 0) {
                        File imgFile = new File(complaintRepository.getFileLocation());
                        if (imgFile.exists()) {
                            FileInputStream fis = null;
                            try {
                                fis = new FileInputStream(imgFile);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Bitmap bm = BitmapFactory.decodeStream(fis);
                            bm = ImageUtility.rotatePicture(90, bm);
                            String base64string = ImageUtility.convertBitmapToString(bm);
                            complaintRepository.setByteImage(base64string);
                        } else {
                            complaintRepository.setByteImage("");
                        }
                    } else {

                        complaintRepository.setByteImage(doFileUpload(complaintRepository.getFileLocation()));
                    }
                    if (type == 1) {
                        complaintRepositories.add(complaintRepository);
                    }
                } while (cursor.moveToNext());

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? complaintRepository : complaintRepositories);
    }


    //file upload
    private String doFileUpload(String selectedPath) {
        byte[] videoBytes;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            File AudioFile = new File(selectedPath);
            if (AudioFile.exists()) {
                FileInputStream fis = new FileInputStream(AudioFile);

                byte[] buf = new byte[1024];
                int n;
                while (-1 != (n = fis.read(buf)))
                    baos.write(buf, 0, n);

                videoBytes = baos.toByteArray();


                String video_str = android.util.Base64.encodeToString(videoBytes, 0);
                System.out.println("video array" + video_str);
                return video_str;
            } else {
                return "";
            }

        } catch (Exception e) {
            return "";
            // TODO: handle exception
        }
    }

    //get complaint status history
    public T getComplaintStatusHistory(String query, int type) {
        List<ComplaintStatusHistory> complaintStatusHistorys = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for getComplaintStatusHistory " + query);
        ComplaintStatusHistory complaintStatusHistory = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    complaintStatusHistory = new ComplaintStatusHistory();
                    complaintStatusHistory.setComplaintCode(cursor.getString(1));
                    complaintStatusHistory.setStatusTypeId(cursor.getString(2));
                    complaintStatusHistory.setAssigntoUserId(cursor.getString(3));
                    complaintStatusHistory.setComments(cursor.getString(4));
                    complaintStatusHistory.setIsActive(cursor.getInt(5));
                    complaintStatusHistory.setCreatedByUserId(cursor.getInt(6));
                    complaintStatusHistory.setCreatedDate(cursor.getString(7));
                    complaintStatusHistory.setUpdatedByUserId(cursor.getInt(8));
                    complaintStatusHistory.setUpdatedDate(cursor.getString(9));
                    complaintStatusHistory.setServerUpdatedStatus(cursor.getInt(10));

                    if (type == 1) {
                        complaintStatusHistorys.add(complaintStatusHistory);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? complaintStatusHistory : complaintStatusHistorys);
    }

    //get complainttype xref
    public T getComplaintTypeXref(String query, int type) {
        List<ComplaintTypeXref> complaintTypeXref = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for getComplaintTypeXref " + query);
        ComplaintTypeXref complaintTypeXref1 = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    complaintTypeXref1 = new ComplaintTypeXref();
                    complaintTypeXref1.setComplaintCode(cursor.getString(1));
                    complaintTypeXref1.setComplaintTypeId(cursor.getInt(2));
                    complaintTypeXref1.setCreatedByUserId(cursor.getInt(3));
                    complaintTypeXref1.setCreatedDate(cursor.getString(4));
                    complaintTypeXref1.setUpdatedByUserId(cursor.getInt(5));
                    complaintTypeXref1.setUpdatedDate(cursor.getString(6));
                    complaintTypeXref1.setServerUpdatedStatus(cursor.getInt(7));
                    complaintTypeXref.add(complaintTypeXref1);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? complaintTypeXref1 : complaintTypeXref);

    }

    //get complaints by user
    public void getComplaintsByUser(String query, final ApplicationThread.OnComplete onComplete) {
        List<ComplaintsDetails> complaintsDetailsesArrayList = new ArrayList<>();
        Cursor cursor = null;
        // String qurey = Queries.getInstance().getComplaintsDataByPlot(plotCode, farmerCode);
        Log.v(LOG_TAG, "Query for getting complaints of plot " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ComplaintsDetails complaintsDetails = new ComplaintsDetails();
                    complaintsDetails.setComplaintId(cursor.getString(0));
                    complaintsDetails.setComplaintTypeId(cursor.getString(1));
                    complaintsDetails.setAssigntoUserId(cursor.getString(2));
                    complaintsDetails.setStatusTypeId(cursor.getString(3));
                    complaintsDetails.setCriticalityByTypeId(null);
                    complaintsDetails.setComplaintRaisedon(cursor.getString(5));
                    complaintsDetails.setPlotId(cursor.getString(6));
                    complaintsDetails.setfarmerFirstName(cursor.getString(7));
                    complaintsDetails.setFarmerLastName(cursor.getString(8));
                    complaintsDetails.setVillage(cursor.getString(9));
                    complaintsDetails.setComplaintTypeName(cursor.getString(10));
                    complaintsDetails.setComplaintStatusTypeName(cursor.getString(11));
                    complaintsDetails.setComplaintCreatedBy(cursor.getString(12));
                    complaintsDetailsesArrayList.add(complaintsDetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            onComplete.execute(true, complaintsDetailsesArrayList, "getting data");
        }
    }

    //get kras data
//    public LinkedHashMap<String, List<KrasDataToDisplay>> getKrasDataToDisplay(String query) {
//        Log.v(LOG_TAG, "@@@@ kras query " + query);
//        Cursor cursor = null;
//        LinkedHashMap<String, List<KrasDataToDisplay>> krasMap = new LinkedHashMap<>();
//        try {
//            cursor = mDatabase.rawQuery(query, null);
//            List<KrasDataToDisplay> krasDataToDisplays = null;
//            String oldKraCode = "", newKraCode;
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    newKraCode = cursor.getString(1);
//                    if (TextUtils.isEmpty(oldKraCode) || !newKraCode.equalsIgnoreCase(oldKraCode)) {
//                        Log.v(LOG_TAG, "@@@ kra code changed " + newKraCode);
//                        oldKraCode = newKraCode;
//                        krasDataToDisplays = new ArrayList<>();
//                    }
//                    KrasDataToDisplay krasDataToDisplay = new KrasDataToDisplay();
//                    krasDataToDisplay.setUserKraId(cursor.getInt(0));
//                    krasDataToDisplay.setkRACode(oldKraCode);
//                    krasDataToDisplay.setkRAName(cursor.getString(2));
//                    krasDataToDisplay.setuOM(cursor.getString(3));
//                    krasDataToDisplay.setAnnualTarget(cursor.getDouble(4));
//                    krasDataToDisplay.setAnnualAchievedTarget(cursor.getDouble(5));
//                    krasDataToDisplay.setUserId(cursor.getInt(6));
//                    krasDataToDisplay.setMonthNumber(cursor.getInt(7));
//                    krasDataToDisplay.setMonthlyTarget(cursor.getDouble(8));
//                    krasDataToDisplay.setMonthlyAchievedTarget(cursor.getDouble(9));
//                    krasDataToDisplays.add(krasDataToDisplay);
//                    krasMap.put(oldKraCode, krasDataToDisplays);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return krasMap;
//    }

    public LinkedHashMap<String, List<KrasDataToDisplay>> getKrasDataToDisplay(String query) {
        Log.v(LOG_TAG, "@@@@ kras query " + query);
        Cursor cursor = null;
        LinkedHashMap<String, List<KrasDataToDisplay>> krasMap = new LinkedHashMap<>();
        try {
            cursor = mDatabase.rawQuery(query, null);
            List<KrasDataToDisplay> krasDataToDisplays = null;
            String oldKraCode = "", newKraCode;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    newKraCode = cursor.getString(0);
                    if (TextUtils.isEmpty(oldKraCode) || !newKraCode.equalsIgnoreCase(oldKraCode)) {
                        Log.v(LOG_TAG, "@@@ kra code changed " + newKraCode);
                        oldKraCode = newKraCode;
                        krasDataToDisplays = new ArrayList<>();
                    }
                    KrasDataToDisplay krasDataToDisplay = new KrasDataToDisplay();
                    krasDataToDisplay.setkRACode(oldKraCode);
                    krasDataToDisplay.setkRAName(cursor.getString(1));
                    krasDataToDisplay.setuOM(cursor.getString(2));
                    krasDataToDisplay.setAnnualTarget(cursor.getDouble(3));
                    krasDataToDisplay.setAnnualAchievedTarget(cursor.getDouble(4));
                    krasDataToDisplay.setUserId(cursor.getInt(5));
                    krasDataToDisplay.setMonthNumber(cursor.getInt(6));
                    krasDataToDisplay.setMonthlyTarget(cursor.getDouble(7));
                    krasDataToDisplay.setMonthlyAchievedTarget(cursor.getDouble(8));
                    krasDataToDisplays.add(krasDataToDisplay);
                    krasMap.put(oldKraCode, krasDataToDisplays);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return krasMap;
    }

    //Alerts

    public T getAlertsPlotInfo(String query, int type) {
        List<AlertsPlotInfo> alertsPlotInfoList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for alertsPlotInfo " + query);
        AlertsPlotInfo alertsPlotInfoObj = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    alertsPlotInfoObj = new AlertsPlotInfo();
                    alertsPlotInfoObj.setPlotCode(cursor.getString(0));
                    alertsPlotInfoObj.setFarmerCode(cursor.getString(1));
                    alertsPlotInfoObj.setFirstName(cursor.getString(2));
                    alertsPlotInfoObj.setMiddleName(cursor.getString(3));
                    alertsPlotInfoObj.setLastName(cursor.getString(4));
                    alertsPlotInfoObj.setContactNumber(cursor.getString(5));
                    alertsPlotInfoObj.setMandalName(cursor.getString(6));
                    alertsPlotInfoObj.setVillageName(cursor.getString(7));
                    alertsPlotInfoObj.setTotalPlotArea(cursor.getString(8));
                    alertsPlotInfoObj.setPotentialScore(cursor.getString(9));
                    alertsPlotInfoObj.setCropName(cursor.getString(10));
                    alertsPlotInfoObj.setLastVistDate(cursor.getString(11));
                    alertsPlotInfoObj.setHarvestDate(cursor.getString(12));
                    alertsPlotInfoObj.setPrioritization(cursor.getString(13));
                    alertsPlotInfoObj.setUserName(cursor.getString(14));
                    alertsPlotInfoList.add(alertsPlotInfoObj);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? alertsPlotInfoObj : alertsPlotInfoList);

    }

    //get alerts visits info
    public T getAlertsVisitsInfo(String query, int type) {
        List<AlertsVisitsInfo> alertsPlotInfoList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for alertsPlotInfo " + query);
        AlertsVisitsInfo alertsPlotInfoObj = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    alertsPlotInfoObj = new AlertsVisitsInfo();
                    alertsPlotInfoObj.setPlotCode(cursor.getString(0));
                    alertsPlotInfoObj.setFarmerCode(cursor.getString(1));
                    alertsPlotInfoObj.setFirstName(cursor.getString(2));
                    alertsPlotInfoObj.setMiddleName(cursor.getString(3));
                    alertsPlotInfoObj.setLastName(cursor.getString(4));
                    alertsPlotInfoObj.setContactNumber(cursor.getString(5));
                    alertsPlotInfoObj.setMandalName(cursor.getString(6));
                    alertsPlotInfoObj.setVillageName(cursor.getString(7));
                    alertsPlotInfoObj.setTotalPlotArea(cursor.getString(8));
                    alertsPlotInfoObj.setDateofplanting(cursor.getString(9));
                    alertsPlotInfoObj.setPlotvisiteddate(cursor.getString(10));
                    alertsPlotInfoList.add(alertsPlotInfoObj);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? alertsPlotInfoObj : alertsPlotInfoList);

    }

    //get missing tress info
    public T getAletsMissingTreesInfo(String query, int type) {
        List<MissingTressInfo> alertsMissingTreesInfoList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for alertsPlotInfo " + query);
        MissingTressInfo alertsMissingTreesInfoObj = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    alertsMissingTreesInfoObj = new MissingTressInfo();
                    alertsMissingTreesInfoObj.setPlotCode(cursor.getString(0));
                    alertsMissingTreesInfoObj.setFarmerCode(cursor.getString(1));
                    alertsMissingTreesInfoObj.setFirstName(cursor.getString(2));
                    alertsMissingTreesInfoObj.setMiddleName(cursor.getString(3));
                    alertsMissingTreesInfoObj.setLastName(cursor.getString(4));
                    alertsMissingTreesInfoObj.setMandalName(cursor.getString(5));
                    alertsMissingTreesInfoObj.setVillageName(cursor.getString(6));
                    alertsMissingTreesInfoObj.setSaplingsplanted(cursor.getString(7));
                    alertsMissingTreesInfoObj.setCurrentTrees(cursor.getString(8));
                    alertsMissingTreesInfoObj.setMissingTrees(cursor.getString(9));
                    alertsMissingTreesInfoObj.setPercent(cursor.getString(10));
                    alertsMissingTreesInfoList.add(alertsMissingTreesInfoObj);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return (T) ((type == 0) ? alertsMissingTreesInfoObj : alertsMissingTreesInfoList);
    }


    //get farmers list
    public ArrayList<Farmer> getFarmerList(final String query) {
        ArrayList<Farmer> farmerDetails = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for getting farmers " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Farmer farmlanddetails = new Farmer();


                    farmlanddetails.setCode(cursor.getString(cursor.getColumnIndex("Code")));
                    farmlanddetails.setFirstname(cursor.getString(cursor.getColumnIndex("FirstName")));
                    farmlanddetails.setMiddlename(cursor.getString(cursor.getColumnIndex("MiddleName")));
                    farmlanddetails.setLastname(cursor.getString(cursor.getColumnIndex("LastName")));
                    farmerDetails.add(farmlanddetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "getting failed fromLocalDb" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDataBase();


        }
        return farmerDetails;
    }

    //get farmerslist for personal details
    public ArrayList<ExistingFarmerData> getFarmerListforPersonalDetails(final String query) {
        ArrayList<ExistingFarmerData> farmerDetails = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for getting farmers " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ExistingFarmerData farmlanddetails = new ExistingFarmerData();


                    farmlanddetails.setCode(cursor.getString(cursor.getColumnIndex("Code")));
                    farmlanddetails.setFirstName(cursor.getString(cursor.getColumnIndex("FirstName")));
                    farmlanddetails.setMiddleName(cursor.getString(cursor.getColumnIndex("MiddleName")));
                    farmlanddetails.setLastName(cursor.getString(cursor.getColumnIndex("LastName")));
                    farmlanddetails.setVillageName(cursor.getString(cursor.getColumnIndex("Villagename")));
                    farmlanddetails.setClusterName(cursor.getString(cursor.getColumnIndex("Clustername")));
                    farmerDetails.add(farmlanddetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "getting failed fromLocalDb" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDataBase();


        }
        return farmerDetails;
    }

    //get plotffbDetails list
    public ArrayList<PlotFFBDetails> getPlotffbDetails (final String query) {
        ArrayList<PlotFFBDetails> plotFFBDetails = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for getting PlotffbDetails " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    PlotFFBDetails plotFFBDetails1 = new PlotFFBDetails();


                    plotFFBDetails1.setCode(cursor.getString(cursor.getColumnIndex("Code")));
                    plotFFBDetails1.setYieldPerHectare(cursor.getDouble(cursor.getColumnIndex("YieldPerHectare")));
                    plotFFBDetails1.setExpectedYieldPerHectare(cursor.getDouble(cursor.getColumnIndex("ExpectedYieldPerHectare")));
                    plotFFBDetails.add(plotFFBDetails1);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "getting failed fromLocalDb" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDataBase();


        }
        return plotFFBDetails;
    }

    public ArrayList<PlotGradingDetails> getPlotGradingDetails (final String query) {
        ArrayList<PlotGradingDetails> plotGradingDetails = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for getting PlotGradingDetails " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    PlotGradingDetails plotGradingDetails1 = new PlotGradingDetails();


                    plotGradingDetails1.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    plotGradingDetails1.setRipen(cursor.getDouble(cursor.getColumnIndex("Ripen")));
                    plotGradingDetails1.setUnderRipe(cursor.getDouble(cursor.getColumnIndex("UnderRipe")));
                    plotGradingDetails1.setUnRipen(cursor.getDouble(cursor.getColumnIndex("UnRipen")));
                    plotGradingDetails1.setOverRipe(cursor.getDouble(cursor.getColumnIndex("OverRipe")));
                    plotGradingDetails1.setDiseased(cursor.getDouble(cursor.getColumnIndex("Diseased")));
                    plotGradingDetails1.setEmptyBunches(cursor.getDouble(cursor.getColumnIndex("EmptyBunches")));
                    plotGradingDetails.add(plotGradingDetails1);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "getting failed fromLocalDb" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDataBase();


        }
        return plotGradingDetails;
    }
}
