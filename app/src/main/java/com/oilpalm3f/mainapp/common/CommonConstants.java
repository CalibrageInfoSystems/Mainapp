package com.oilpalm3f.mainapp.common;

import com.oilpalm3f.mainapp.datasync.refreshsyncmodel.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siva on 28/09/16.
 */

//Commonly used String/Constants can be defined here

public class CommonConstants {
    public static int white_fly_details = 589;
    public static int Yield_Details = 588;
    public static final String main_file_structure = "/3F_Files/";
    public static final String JPEG_FILE_SUFFIX = ".jpg";
    public static final String MP4_FILE_SUFFIX = ".mp3";
    public static String CROP_MAINTENANCE_HISTORY_CODE = null;
    public static String COMPLAINT_CODE = "";
    public static String CROP_MAINTENANCE_HISTORY_NAME = "Crop Visit ";
    public static String IS_FRESH_INSTALL = "is_fresh_install";
    public static String IS_FALOG_Service = "is_falog_service";
    public static String collectionType = "";
    public static String migrationSync = "true";
    public static String stateCode = "";
    public static String districtCode = "";
    public static String mandalCode = "";
    public static String stateName = "";
    public static String villageCode = "";
    public static String districtName = "";
    public static String KEY_NEW_COMPLAINT = "newComplaint";
    public static String mandalName = "";
    public static String bankTypeId = "";
    public static String branchTypeId = "";
    public static String castCode = "";
    public static String villageName = "";
    public static String districtCodePlot = "";
    public static String mandalCodePlot = "";
    public static String villageCodePlot = "";
    public static String stateCodePlot = "";
    public static int prevMandalPos = 0;
    public static int prevVillagePos = 0;
    public static int preDistrictPos = 0;
    public static String farmerFirstName = "";
    public static String farmerMiddleName = "";
    public static String farmerLastName = "";
    public static String farmerMobileNumber = "";
    public static String DATE_FORMAT_DDMMYYYY = "yyyy-MM-dd";
    public static String DATE_FORMAT_DDMM = "dd-MM-yyyy";
    public static String DATE_FORMAT_MMYYYY = "yyyy-MM";
    public static String DATE_FORMAT_DDMMYYYY_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT_DDMMYYYY_HHMMSS_SSS ="yyyy-MM-dd HH:mm:ss.SSS";
    public static String DATE_FORMAT_1 = "dd-MM-yyyy HH:mm a";
    public static String DATE_FORMAT_2 = "dd-MM-yyyy";
    public static String DATE_FORMAT_3 = "dd/MM/yyyy";
    public static String IS_MASTER_SYNC_SUCCESS = "is_master_sync_success";
    public static String PREVIOUS_PRINTER_ADDRESS = "previous_printer_address";
    public static String IS_ANY_PALM_DETAIL_Fill = "is_any_palm_detail_fill";
    public static String USER_ID = "12345";
    public static String TAB_ID = "0000";
    public static String USER_CODE = "SA001";
    public static String L_Contact = "";
    public static String PASSWORD = "";
    public static String USER_NAME = "";
    public static String FARMER_CODE = "";
    public static String PLOT_CODE = null;
    public static String TotalPlot_Area = "0";
    public static String ServerUpdatedStatus = "0";
    public static String countryID = "1";
    public static String stateId = "";
    public static String districtId = "";
    public static String mandalId = "";
    public static String villageId = "";
    public static String villageIdPlot = "";
    public static String mandalIdPlot = "";
    public static String districtIdPlot = "";
    public static String statePlotId = "";
    public static String landMark = "";
    public static String palmArea = "";
    public static String plotVillage = "";
    public static String gpsArea = "";
    public static int Crop_pruning = 0;
    public static double AREA_ALLOCATED = 0;
    public static String COUNT_OF_TREES = "";
    public static int CURRENT_TREE = 1;
    public static String screenFrom = "";
    public static double Current_Latitude = 0.0;
    public static double Current_Longitude = 0.0;
    public static  char perc_tree =' ';
    public static char perc_tree_pest=' ';
    public static char perc_tree_disease=' ';
    public static String Inflorescence_rating = "";
    public static String Weevils_rating = "";
    public static String Basin_Health_rating = "";
    public static String Spear_leaf_rating = "";
    public static String Prev_Fertilizer_CMD ="";

    public static UserData totalFarmerData = new UserData();
    // Bundle Constants
    public static String REFERRAL_DATA_MODEL = "referral_data_model";
    public static List<String> selectedPlotCode = new ArrayList<>();

    public static String REGISTRATION_SCREEN_FROM = "";
    public static String REGISTRATION_SCREEN_FROM_NEW_FARMER = "registration_screen_from_new_farmer";
    public static String REGISTRATION_SCREEN_FROM_NEW_PLOT = "registration_screen_from_new_plot";
    public static String REGISTRATION_SCREEN_FROM_FOLLOWUP = "registration_screen_from_followup";
    public static String REGISTRATION_SCREEN_FROM_COMPLAINT = "registration_screen_from_complaint";
    public static String REGISTRATION_SCREEN_FROM_VPF = "registration_screen_from_vpf";
    public static String REGISTRATION_SCREEN_FROM_CONVERSION = "registration_screen_from_conversion";
    public static String REGISTRATION_SCREEN_FROM_CP_MAINTENANCE = "registration_screen_from_cp_maintenance";
    public static String REGISTRATION_SCREEN_FROM_HARVESTING = "registration_screen_from_harvesting";

    public static String REGISTRATION_SCREEN_FROM_PLOT_SPLIT = "registration_screen_from_plot_split";
    public static String REGISTRATION_SCREEN_FROM_VISIT_REQUESTS = "registration_screen_from_visit_request";
    public static String REGISTRATION_SCREEN_FROM_TRANSPORT_SERVICE_QUESTIONER = "registration_screen_from_transport_service_questioner";

    public static boolean isModeEdit = false;
    public static String ADDRESS_CODE_PREFIX = "Addr";
    public static String MARKET_SURVEY_CODE_PREFIX = "MS";
    public static String PEST_CODE_PREFIX = "PS";
    public static String CROP_CODE_PREFIX = "CROP";
    public static String COMP_CODE_PREFIX = "COMP";
    public static String GEO_TAG_ADDRESS = "";
    public static String MarketSurveyCode = "";

    public static String STATUS_TYPE_ID_PROSPECTIVE = "Prospective";
    public static String STATUS_TYPE_ID_READY_TO_CONVERT = "Ready to Convert";
    public static String STATUS_TYPE_ID_APPROVED = "Approved";
    public static String STATUS_TYPE_ID_DECLINED = "Declined";
    public static String STATUS_TYPE_ID_CONVERTED = "Converted";
    public static String STATUS_TYPE_ID_UPROOTED = "Uprooted";
    public static String STATUS_TYPE_ID_NOT_PLANTED = "Not Planted";
    public static String STATUS_TYPE_ID_NOT_CURRENT_CROP_NON_YIELDING = "Current Crop Non- Yielding";
    public static String STATUS_TYPE_ID_NOT_CURRENT_CROP_CURRENT_CROP_YIELDING = "Current Crop Yielding";
    public static String STATUS_TYPE_GEO_BOUNDARIES_TAKEN = "Geo Boundaries Done";

//    public static int BoreWellID = 129;
//    public static int OpenWellID = 130;
//    public static int CanalOthersID = 131;

    public static String CanViewGeoLocation  = "CanViewGeoLocation";
    public static String CanManageGeoLocation = "CanManageGeoLocation";
    public static String CanViewUsers = "CanViewUsers";
    public static String CanManageUsers = "CanManageUsers";
    public static String CanViewMasters = "CanViewMasters";
    public static String CanManageMasters = "CanManageMasters";
    public static String CanViewFarmers = "CanViewFarmers";
    public static String CanManageFarmers = "CanManageFarmers";
    public static String CanApproveFarmers = "CanApproveFarmers";
    public static String CanViewEmployeePerformanceDetails = "CanViewEmployeePerformanceDetails";
    public static String CanManageEmployeePerformanceDetails = "CanManageEmployeePerformanceDetails";
    public static String CanViewCollectionCenterDetails = "CanViewCollectionCenterDetails";
    public static String CanManageCollectionCenterDetails = "CanManageCollectionCenterDetails";
    public static String CanViewFarmerComplaints = "CanViewFarmerComplaints";
    public static String CanResolveFarmerComplaints = "CanResolveFarmerComplaints";
    public static String CanEscalateFarmerComplaints = "CanEscalateFarmerComplaints";
    public static String CanViewMarketSurvey = "CanViewMarketSurvey";
    public static String CanManageMarketSurvey = "CanManageMarketSurvey";
    public static String CanViewReferals = "CanViewReferals";
    public static String CanMangeReferals = "CanMangeReferals";
    public static String CanViewCollectionCenterReports = "CanViewCollectionCenterReports";
    public static String CanAssignCollection = "CanAssignCollection";
    public static String CanApproveOrDeclineFarmerPlots = "CanApproveOrDeclineFarmerPlots";
    public static String CanManagePlotAdvanceDetails = "CanManagePlotAdvanceDetails";
    public static String CanPlotUproot = "CanPlotUproot";
    public static String CanVerifyPlotStatus = "CanVerifyPlotStatus";
    public static String CanUpdatePlotStatusHasNotPlanted = "CanUpdatePlotStatusHasNotPlanted";
    public static String CanCheckPlotDetails = "CanCheckPlotDetails";
    public static String CanChangePlotOwnership = "CanChangePlotOwnership";
    public static String CanAddManualCollectionConsignment = "CanAddManualCollectionConsignment";
    public static String CanManageSaplingPickUpDate = "CanManageSaplingPickUpDate";
    public static String FarmerCodeForYield = "";

    public static int New_plot_registration_existing_farmer = 209;
    public static int New_plot_registration_new_farmer = 210;
    public static int Followup_Log_Conversion_Potential_Score = 211;
    public static int Water_Soil_Power_details_update = 212;
    public static int Plot_Point_Geo_Tag = 213;
    public static int Plot_Point_Geo_Boundaries = 214;
    public static int Plot_Point_Retake_Geo_Boundaries = 278;
    public static int Intercrop = 215;
    public static int Harvest_Details = 216;
    public static int Uprootment_Details = 217;
    public static int Fertilizer_Details = 218;
    public static int Weed_Details = 219;
    public static int Pest_Details = 220;
    public static int Disease_Details = 221;
    public static int Complaints_Raised = 222;
    public static int Complaints_Assigned = 223;
    public static int Complaints_Resolved = 224;
    public static int Complaints_Closed = 225;
    public static int Market_Survey = 226;
    public static int Farmer_Personal_Details = 227;
    public static int Farmer_Bank_Details = 228;
    public static int Digital_Contract = 229;
    public static int Edit_Geo_boundaries = 230;
    public static int Conversion = 231;
    public static int Crop_Maintenance = 232;
    public static int Assign_Collections_to_Farmers = 233;
    public static int Ownership_Change = 234;
    public static int Plot_Approval = 235;
    public static int Plot_Verification = 236;
    public static int Plot_Split = 237;
    public static int Uprooted_Status_Change = 238;
    public static int Crop_Maintenance_Plantation_Category = 239;
    public static int Crop_Maintenance_No_fo_trees = 240;
    public static boolean fromDigitalContract = false;

    public static boolean isplotupdated = false;

    public static String SyncTableName = "Table";


    public static final class Flags {
        public static boolean isFarmersDataUpdated = false;
        public static boolean isPlotsDataUpdated = false;
        public static boolean isWOPDataUpdated = false;
        public static boolean isFollowUpUpdated = false;
        public static boolean isReferralsUpdated = false;
        public static boolean isMarketSurveyUpdated = false;

        public static void resetFlags() {
            isFarmersDataUpdated = false;
            isPlotsDataUpdated = false;
            isWOPDataUpdated = false;
            isFollowUpUpdated = false;
            isReferralsUpdated = false;
            isMarketSurveyUpdated = false;
        }
    }




}


