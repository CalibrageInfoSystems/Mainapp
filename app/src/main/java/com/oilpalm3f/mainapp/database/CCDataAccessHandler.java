package com.oilpalm3f.mainapp.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.dbmodels.PlotDetailsObj;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


//Used to get access of Plot details based on selected screen
public class CCDataAccessHandler {
    private static final String LOG_TAG = CCDataAccessHandler.class.getName();
    private SQLiteDatabase mDatabase;

    public CCDataAccessHandler(final Context context) {
        try {
            mDatabase = Palm3FoilDatabase.openDataBaseNew();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public List<PlotDetailsObj> getPlotDetails(String farmerCode, int plotStatus, boolean withoutGps) {
        List<PlotDetailsObj> plotDetailslistObj = new ArrayList<>();
        Cursor cursor = null;
        String query = null;
        if (CommonUtils.isFromCropMaintenance()||CommonUtils.isComplaint() || CommonUtils.isFromHarvesting()) {
            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus, 89, true);
        } else  if (CommonUtils.isFromFollowUp()) {
            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus);
        } else if (CommonUtils.isPlotSplitFarmerPlots()) {
            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus);
        }else if(CommonUtils.isFromConversion()){
            query = Queries.getInstance().getPlotDetailsForConversion(farmerCode.trim(), plotStatus);
            Log.v(LOG_TAG, "@@@conversion "+query);
        }
        else if(CommonUtils.isVisitRequests()){
            query = Queries.getInstance().getPlotDetailsForVisit(farmerCode.trim());
            Log.v(LOG_TAG, "@@@conversion "+query);
        }


        Log.v(LOG_TAG, "Query for getting plots related to farmer "+query);
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



    public String getCurrentYear() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);
        return yearInString;
    }
}
