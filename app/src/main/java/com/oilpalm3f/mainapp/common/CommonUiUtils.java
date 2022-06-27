package com.oilpalm3f.mainapp.common;

import android.content.Context;
import android.text.TextUtils;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.conversion.ConversionDigitalContractFragment;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.DatabaseKeys;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.Complaints;
import com.oilpalm3f.mainapp.dbmodels.Address;
import com.oilpalm3f.mainapp.dbmodels.Farmer;
import com.oilpalm3f.mainapp.dbmodels.FarmerBank;
import com.oilpalm3f.mainapp.dbmodels.FileRepository;
import com.oilpalm3f.mainapp.dbmodels.FollowUp;
import com.oilpalm3f.mainapp.dbmodels.GeoBoundaries;
import com.oilpalm3f.mainapp.dbmodels.IdentityProof;
import com.oilpalm3f.mainapp.dbmodels.Plantation;
import com.oilpalm3f.mainapp.dbmodels.Plot;
import com.oilpalm3f.mainapp.dbmodels.SoilResource;
import com.oilpalm3f.mainapp.dbmodels.WaterResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siva on 22/05/17.
 */

// Commonly used UI methods are written here

public class CommonUiUtils {

    //Checks whether all mandatory data is entered or not in Registration Screen
    public static boolean isMandatoryDataEnteredForNRF() {
        return DataManager.getInstance().getDataFromManager(DataManager.FARMER_ADDRESS_DETAILS) != null
                && DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS) != null
                && DataManager.getInstance().getDataFromManager(DataManager.PLOT_ADDRESS_DETAILS) != null
                && DataManager.getInstance().getDataFromManager(DataManager.PLOT_DETAILS) != null
                && DataManager.getInstance().getDataFromManager(DataManager.PLOT_FOLLOWUP) != null;
    }

    //Checks whether all mandatory data is entered or not in Conversion Screen
    public static boolean isMandatoryDataEnteredForConversion(final Context context) {
        DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        boolean idProofsRecordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(DatabaseKeys.TABLE_IDENTITYPROOF, "FarmerCode", CommonConstants.FARMER_CODE));
        boolean farmerBankRecordExisted = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().checkRecordStatusInTable(DatabaseKeys.TABLE_FARMERBANK, "FarmerCode", CommonConstants.FARMER_CODE));

        return (DataManager.getInstance().getDataFromManager(DataManager.ID_PROOFS_DATA) != null || idProofsRecordExisted)
                && (DataManager.getInstance().getDataFromManager(DataManager.FARMER_BANK_DETAILS) != null || farmerBankRecordExisted)
                && DataManager.getInstance().getDataFromManager(DataManager.PLANTATION_CON_DATA) != null
                && DataManager.getInstance().getDataFromManager(DataManager.PLOT_GEO_TAG) != null
                && ConversionDigitalContractFragment.isContractAgreed;
    }

    //Checks whether all mandatory data is entered or not in Followup Screen
    public static boolean isMandatoryDataEnteredForFollowUp() {
        return DataManager.getInstance().getDataFromManager(DataManager.PLOT_FOLLOWUP) != null;
    }

    //Checks whether all mandatory data is entered or not in Crop Maintenance Screen
    public static boolean isMandatoryDataEnteredForCropMaintenance() {
        return DataManager.getInstance().getDataFromManager(DataManager.CURRENT_PLANTATION) != null
                && (DataManager.getInstance().getDataFromManager(DataManager.WEEDING_HEALTH_OF_PLANTATION_DETAILS) != null ||   CommonConstants.CURRENT_TREE==0)
                && DataManager.getInstance().getDataFromManager(DataManager.PLOT_GEO_TAG) != null;

    }

    //Checks whether market survery added or not

    public static boolean isMarketSurveyAddedForFarmer(final Context context, final String query) {
        DataAccessHandler accessHandler = new DataAccessHandler(context);
        return accessHandler.checkValueExistedInDatabase(query);
    }

    //To set the Address Strings
    public static void setGeoGraphicalData(Farmer selectedFarmer, Context context) {
        DataAccessHandler accessHandler = new DataAccessHandler(context);
        CommonConstants.stateId = String.valueOf(selectedFarmer.getStateid());
        CommonConstants.districtId = String.valueOf(selectedFarmer.getDistictid());
        CommonConstants.mandalId = String.valueOf(selectedFarmer.getMandalid());
        CommonConstants.villageId = String.valueOf(selectedFarmer.getVillageid());
        CommonConstants.stateCode = accessHandler.getOnlyOneValueFromDb(Queries.getInstance().getCodeFromId("State", CommonConstants.stateId));
        CommonConstants.districtCode = accessHandler.getOnlyOneValueFromDb(Queries.getInstance().getCodeFromId("District", CommonConstants.districtId));
        CommonConstants.mandalCode = accessHandler.getOnlyOneValueFromDb(Queries.getInstance().getCodeFromId("Mandal", CommonConstants.mandalId));
        CommonConstants.villageCode = accessHandler.getOnlyOneValueFromDb(Queries.getInstance().getCodeFromId("Village", CommonConstants.villageId));
    }

    //To Reset the Data
    public static void resetPrevRegData() {
        DataManager.getInstance().deleteData(DataManager.FARMER_ADDRESS_DETAILS);
        DataManager.getInstance().deleteData(DataManager.FARMER_PERSONAL_DETAILS);
        DataManager.getInstance().deleteData(DataManager.FILE_REPOSITORY);
        DataManager.getInstance().deleteData(DataManager.PLOT_ADDRESS_DETAILS);
        DataManager.getInstance().deleteData(DataManager.PLOT_DETAILS);
        DataManager.getInstance().deleteData(DataManager.PLOT_CURRENT_CROPS_DATA);
        DataManager.getInstance().deleteData(DataManager.PLOT_NEIGHBOURING_PLOTS_DATA);
        DataManager.getInstance().deleteData(DataManager.SOURCE_OF_WATER);
        DataManager.getInstance().deleteData(DataManager.SoilType);
        DataManager.getInstance().deleteData(DataManager.PLOT_GEO_TAG);
        DataManager.getInstance().deleteData(DataManager.PLOT_FOLLOWUP);
        DataManager.getInstance().deleteData(DataManager.REFERRALS_DATA);
        DataManager.getInstance().deleteData(DataManager.MARKET_SURVEY_DATA);
        DataManager.getInstance().deleteData(DataManager.OIL_TYPE_MARKET_SURVEY_DATA);
        DataManager.getInstance().deleteData(DataManager.ID_PROOFS_DATA);
        DataManager.getInstance().deleteData(DataManager.FARMER_BANK_DETAILS);
        DataManager.getInstance().deleteData(DataManager.PLANTATION_CON_DATA);
        DataManager.getInstance().deleteData(DataManager.COMPLAINT_DETAILS);
        DataManager.getInstance().deleteData(DataManager.COMPLAINT_REPOSITORY);
        DataManager.getInstance().deleteData(DataManager.COMPLAINT_STATUS_HISTORY);
        DataManager.getInstance().deleteData(DataManager.COMPLAINT_TYPE);
        DataManager.getInstance().deleteData(DataManager.NEW_COMPLAINT_DETAILS);
        DataManager.getInstance().deleteData(DataManager.NEW_COMPLAINT_REPOSITORY);
        DataManager.getInstance().deleteData(DataManager.NEW_COMPLAINT_STATUS_HISTORY);
        DataManager.getInstance().deleteData(DataManager.NEW_COMPLAINT_TYPE);
        ConversionDigitalContractFragment.isContractAgreed = false;
        CommonConstants.PLOT_CODE = "";
        CommonConstants.FARMER_CODE = "";
    }

    //Checks whether Geotag/Geo Boundaries are taken or not
    public static boolean checkForGeoTag(final Context context) {
        DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        boolean existed = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().queryGeoTagCheck(CommonConstants.PLOT_CODE));
        if (existed) {
            return false;
        }
        FollowUp followUp = (FollowUp) DataManager.getInstance().getDataFromManager(DataManager.PLOT_FOLLOWUP);
        if (followUp == null) {
            return false;
        }
        GeoBoundaries geoBoundaries = (GeoBoundaries) DataManager.getInstance().getDataFromManager(DataManager.PLOT_GEO_TAG);
        return followUp.getIsfarmerreadytoconvert() == 1 && geoBoundaries == null;
    }

    //Checks whether Identity Proof details entered or not
    public static boolean checkforIdentityDetails(final Context context){
        DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        boolean existed=dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().queryIdentityCheck(CommonConstants.FARMER_CODE));
        if (existed) {
            return false;
        }
        FollowUp followUp = (FollowUp) DataManager.getInstance().getDataFromManager(DataManager.PLOT_FOLLOWUP);
        if (followUp == null) {
            return false;
        }
       List<IdentityProof> identityProof=(ArrayList<IdentityProof>) DataManager.getInstance().getDataFromManager(DataManager.ID_PROOFS_DATA);
        return followUp.getIsfarmerreadytoconvert() == 1 && identityProof == null;

    }

    //Checks whether Bank details entered or not
    public static boolean checkBankDetails(final Context context){
        DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        boolean existed = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().queryBankChecking(CommonConstants.FARMER_CODE));
        if(existed){
            return false;
        }
        FollowUp followUp = (FollowUp) DataManager.getInstance().getDataFromManager(DataManager.PLOT_FOLLOWUP);
        if(followUp == null){
            return  false;
        }
        FarmerBank farmerBank = (FarmerBank) DataManager.getInstance().getDataFromManager(DataManager.FARMER_BANK_DETAILS);
        return followUp.getIsfarmerreadytoconvert() == 1 && farmerBank == null;

    }

    //Checks whether Horticulture & Land type details entered or not
    public static boolean checkHorticultureAndLandType(final Context context) {
        DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        boolean existed = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().queryGeoTagCheck(CommonConstants.PLOT_CODE));
        if (existed) {
            return false;
        }
        FollowUp followUp = (FollowUp) DataManager.getInstance().getDataFromManager(DataManager.PLOT_FOLLOWUP);
        if (followUp == null) {
            return false;
        }
        Plot plot = (Plot) DataManager.getInstance().getDataFromManager(DataManager.PLOT_DETAILS);
        if (plot == null)
        {
            return false;
        }

        Integer landId = plot.getLandTypeId();
        Log.v("@@@landID",""+landId);
        return followUp.getIsfarmerreadytoconvert() == 1  && landId == null && plot.getTotalAreaUnderHorticulture() == 0.0f ;
    }


    //Checks whether Soil,Power & Water details entered or not
    public static boolean checkForWaterSoilPowerDetails(final Context context) {
        DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        boolean existed = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().queryWaterResourceCheck(CommonConstants.PLOT_CODE));
        if (existed) {
            return false;
        }
        FollowUp followUp = (FollowUp) DataManager.getInstance().getDataFromManager(DataManager.PLOT_FOLLOWUP);
        if (followUp == null) {
            return false;
        }
        List<WaterResource> waterResource = (ArrayList<WaterResource>) DataManager.getInstance().getDataFromManager(DataManager.SOURCE_OF_WATER);
        return followUp.getIsfarmerreadytoconvert() == 1 && waterResource == null;
    }

    //Checks whether Farmer Photo Taken or not
    public static boolean isFarmerPhotoTaken(final Context context) {
        DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        boolean existed = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().getSelectedFileRepositoryCheckQuery(CommonConstants.FARMER_CODE, 193));
        if (existed) {
            return true;
        }
        FileRepository fileRepository = (FileRepository) DataManager.getInstance().getDataFromManager(DataManager.FILE_REPOSITORY);
        return fileRepository != null && fileRepository.getPicturelocation() != null && !fileRepository.getPicturelocation().equalsIgnoreCase("null");
    }
    public static boolean isFarmerPhotoSavedInDB(final Context context) {
        DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        boolean existed = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().getSelectedFileRepositoryCheckQuery(CommonConstants.FARMER_CODE, 193));
        return existed;
    }


    //Checks whether Soil,Power & Water details entered or not in Conversion
    public static boolean isWSPowerDataEntered(final Context context) {
        DataAccessHandler dataAccessHandler = new DataAccessHandler(context);
        boolean existed = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().getWaterResourceBinding(CommonConstants.PLOT_CODE));
        if (existed) {
            return true;
        }
        boolean existed2 = dataAccessHandler.checkValueExistedInDatabase(Queries.getInstance().getSoilResourceBinding(CommonConstants.PLOT_CODE));
        if (existed2) {
            return true;
        }

        List<WaterResource> mWaterTypeModelList = (ArrayList<WaterResource>) DataManager.getInstance().getDataFromManager(DataManager.SOURCE_OF_WATER);
        if (mWaterTypeModelList != null) {
            return true;
        }

        SoilResource msoilTypeModel = (SoilResource) DataManager.getInstance().getDataFromManager(DataManager.SoilType);
        return msoilTypeModel != null;

    }

    //Checks whether required plots data is entered or not while conversion
    public static boolean isConversionPlotDataEntered() {
        Plot plotData = (Plot) DataManager.getInstance().getDataFromManager(DataManager.PLOT_DETAILS);
        if (plotData == null) {
            return false;
        } else {
            Integer plotCareTakerStatus = plotData.getIsplothandledbycaretaker();
            return !(plotCareTakerStatus == null || plotCareTakerStatus == 0);
        }
    }

    //Checks whether required plots data is entered or not while crop maintenance
    public static boolean isConversionPlotAddressDataEntered() {
        Address plotData = (Address) DataManager.getInstance().getDataFromManager(DataManager.VALIDATE_PLOT_ADDRESS_DETAILS);
        if (plotData == null) {
            return false;
        } else {
            String plotCareTakerStatus = plotData.getLandmark();
            return !TextUtils.isEmpty(plotCareTakerStatus);
        }
    }

    //Checks whether required farmer data is entered or not while crop maintenance
    public static boolean isFarmerMandatoryDataEntered() {
        Farmer farmerData = (Farmer) DataManager.getInstance().getDataFromManager(DataManager.FARMER_PERSONAL_DETAILS);
        if (farmerData == null) {
            return false;
        } else {
            Integer anualIncomeTypeId = farmerData.getAnnualincometypeid();
//            String gaurdianName = farmerData.getGuardianname();
            return !(anualIncomeTypeId == null || anualIncomeTypeId == 0) ;
        }
    }

    //Checks whether required plot data is entered or not while crop maintenance
    public static boolean isPlotDataEntered() {
        Plot enteredPlot = (Plot) DataManager.getInstance().getDataFromManager(DataManager.PLOT_DETAILS);
        if (enteredPlot.getPlotownershiptypeid() == null && enteredPlot.getIsplothandledbycaretaker() == null) {
            return false;
        } else {

            return true ;
        }
    }

    public static boolean isComplaintsDataEntered() {
        Complaints complaintsData = (Complaints) DataManager.getInstance().getDataFromManager(DataManager.NEW_COMPLAINT_DETAILS);
        return complaintsData != null;
    }
}
