package com.oilpalm3f.mainapp.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siva on 24/09/16.
 */

//Strings used in DB are defined here
public class DatabaseKeys {

    public static String TABLE_WHITE="WhiteFlyAssessment";
    public static List<String> transactionTables = new ArrayList<>();
    public static String TABLE_YIELDASSESMENT="YieldAssessment";
    public static String TABLE_ADDRESS = "Address";

    public static String TABLE_FILEREPOSITORY = "FileRepository";
    public static String TABLE_NURSERYSAPLING_DETAILS = "NurserySaplingDetails";
    public static String TABLE_ADVANCED_DETAILS = "AdvancedDetails";
    public static String TABLE_PLOT = "Plot";
    public static String TABLE_PLOTCURRENTCROP = "PlotCurrentCrop";
    public static String TABLE_NEIGHBOURPLOT = "NeighbourPlot";
    public static String TABLE_INTERCROPPLANTATIONXREF = "InterCropPlantationXref";
    public static String TABLE_NUTRIENT = "Nutrient";
    public static String TABLE_OWNERSHIPFILEREPO = "OwnerShipFileRepository";
    public static String TABLE_WATERESOURCE = "WaterResource";
    public static String TABLE_PLOTIRRIGATIONTYPEXREF = "PlotIrrigationTypeXref";
    public static String TABLE_FERTLIZER = "Fertilizer";
    public static String TABLE_RECOMMND_FERTLIZER = "FertilizerRecommendations";

    public static String TABLE_PlotFFB_Details = "PlotFFBDetails";
    public static String TABLE_PlotGrading_Details = "PlotGradingDetails";

    public static String TABLE_visit_Details = "VisitRequests";
    public static String TABLE_FERTLIZER_TYPE = "FertilizerType";
    public static String TABLE_PEST = "Pest";
    public static String TABLE_RESOURCES = "Resources";
    public static String TABLE_PESTCHEMICALXREF = "PestChemicalXref";
    public static String TABLE_DIGITALCONTRACT ="DigitalContract";
    public static String TABLE_IDPROOFFILEREPOXREF = "IdentityProofFileRepositoryXref";
    public static String TABLE_PLANTATIONFILEREPOXREF = "PlantationFileRepositoryXref";
    public static String TABLE_DISEASE = "Disease";
    public static String TABLE_DISTRICT = "District";
    public static String TABLE_WEED = "Weed";
    public static String TABLE_UPROOTMENTt = "Uprootment";
    public static String TABLE_HEALTHPLANTATION = "Healthplantation";
    public static String TABLE_HARVEST = "Harvest";
    public static String TABLE_SOILRESOURCE = "SoilResource";
    public static String TABLE_GEOBOUNDARIES = "GeoBoundaries";
    public static String TABLE_FOLLOWUP = "FollowUp";
    public static String TABLE_REFERRALS = "Referrals";
    public static String TABLE_IDPROOFS = "IdentityProof";
    public static String TABLE_FARMERHISTORY = "FarmerHistory";
    public static String TABLE_MARKETSURVEY = "MarketSurvey";
    public static String TABLE_COOKINGOIL = "CookingOil";
    public static String TABLE_COOKINGOILBRAND = "CookingOilBrand";
    public static String TABLE_IDENTITYPROOF = "IdentityProof";
    public static String TABLE_FARMERBANK = "FarmerBank";
    public static String TABLE_PLANTATION = "Plantation";
    public static String TABLE_PLOTLANDLORD = "PlotLandlord";
    public static String TABLE_LANDLORDBANK = "LandlordBank";
    public static String TABLE_LANDLORDIDPROOFS = "LandlordIdentityProof";
    public static String TABLE_COLLECTIONPLOTXREF = "CollectionPlotXref";
    public static String TABLE_ACTIVITYLOG = "ActivityLog";
    public static String TABLE_ACTIVITYRIGHIT = "ActivityRight";
    public static String TABLE_CROPMAINTENANCEHISTORY = "CropMaintenanceHistory";
    public static String TABLE_COMPLAINTSTATUSHISTORY = "ComplaintStatusHistory";
    public static String TABLE_COMPLAINTTYPEXREF = "ComplaintTypeXref";
    public static String TABLE_COMPLAINT = "Complaints";
    public static String TABLE_COMPLAINTREPOSITORY = "ComplaintRepository";
    public static String TABLE_BANK = "Bank";
    public static String TABLE_COUNTRY = "Country";
    public static String TABLE_CLASSTYPE = "ClassType";
    public static String TABLE_COLLECTIONCENTER= "CollectionCenter";
    public static String TABLE_COMPANY= "Company";
    public static String TABLE_CROPVARIETY= "CropVariety";
    public static String TABLE_CROPVARIETYTYPE= "CropVarietyType";
    public static String TABLE_FARMER= "Farmer";
    public static String TABLE_NOTIFICATIONS = "notifications";

    // MasterVersionTrackingSystem
    public final static String TABLE_MASTERVERSIONTRACKINGSYSTEM = "MasterVersionTrackingSystem";
    public final static String COLUMN_USERID = "UserId";
    public final static String COLUMN_UPDATEDON = "UpdatedOn";


    // PlotBoundaries database table info
    public final static String COLUMN_PLOTCODE = "PlotCode";
    public final static String COLUMN_CREATEDBY = "CreatedBy";
    public final static String COLUMN_CCREATEDDATE = "CreatedDate";
    public final static String COLUMN_UPDATEDBY = "UpdatedBy";
    public final static String COLUMN_CUPDATEDDATE = "UpdatedDate";

    //UprootMentDetails database table info
    public final static String TABLE_UPROOTMENT = "Uprootment";
    public final static String COLUMN_FARMERCODE = "FarmerCode";
    //FFB Harvest Details database info
    public final static String TABLE_FFBHARVESTDETAILS = "FFB_HarvestDetails";
    public final static String COLUMN_COLLECTIONCENTERID = "CollectionCentreId";
    public final static String COLUMN_MODEOFTRANSPORT = "ModeOfTransport";
    public final static String COLUMN_HARVESTINGMETHOD = "HarvestingMethod";
    public final static String COLUMN_WAGESPERDAY = "WagesPerDay";
    public final static String COLUMN_CONTRACTRSPERMONTH = "ContractRsPerMonth";
    public final static String COLUMN_CONTRACTRSPERANNUM = "ContractRsPerAnum";
    public final static String COLUMN_TYPEOFHARVESTING = "TypeOfHarvesting";
    public final static String COLUMN_CONTRACTORPITCH = "ContractorPitch";
    public final static String COLUMN_FARMERCONSENT = "FarmerConsent";
    public final static String COLUMN_COMMENTSHARVESTING = "Comments";


    //HealthofPlantationDetails database info
    public final static String TABLE_HEALTHOFPLANTATIONDETAILS = "HealthofPlantationDetails";
    public final static String COLUMN_PLANTATIONDETAILSID = "PlantationDetailsId";
    public final static String COLUMN_APPEARANCEOFTREES = "AppearanceOfTrees";
    public final static String COLUMN_GROWTHOFTREE = "GrowthOfTree";
    public final static String COLUMN_HEIGHTOFTREE = "HeightOfTree";
    public final static String COLUMN_COLOROFFRUIT = "ColorOfFruit";
    public final static String COLUMN_SIZEOFFRUIT = "SizeOfFruit";
    public final static String COLUMN_PLAMHYGIENE = "PalmHygiene";
    public final static String COLUMN_TYPEOFPLANTATION = "TypeOfPlantation";
    public final static String COLUMN_COMMENTS = "Comments";
    public final static String COLUMN_PHOTO = "PicturePath";

    //InterCropDetails database table info
    public final static String TABLE_INTERCROPDETAILSDETAILS = "InterCropDetails";
    public final static String COLUMN_INTERCROPID = "InterCropId";
    public final static String TABLE_CROPINFO = "CropInfo";
    //    public  final static String COLUMN_PLOTCODE = "PlotCode";
    public final static String COLUMN_INTERCROPINYEAR = "InterCropInYear";
    public final static String COLUMN_CROPCODE = "CropCode";
    public final static String COLUMN_CROPID = "CropId";
    public final static String COLUMN_CROPNAME = "OtherCrop";
    public final static String COLUMN_VILLAGECODE = "VillageCode";


    //MarketSurveyAndReferrals database table info
    public final static String TABLE_MARKETSURVEYDETAILS = "MarketSurveyAndReferrals";
    public final static String COLUMN_SURVEYID = "SurveyId";
    //    public  final static String COLUMN_VILLAGECODE = "VillageCode";
    public final static String COLUMN_VILLAGENAME = "VillageName";
    public final static String COLUMN_MARKETSURVEYNO = "MarketSurveyNo";
    public final static String COLUMN_MARKETSURVEYDATE = "MarketSurveyDate";
    public final static String COLUMN_FARMER = "Farmer";
    //    public  final static String COLUMN_FARMERCODE = "FarmerCode";
    public final static String COLUMN_PERSONNAME = "PersonName";
    public final static String COLUMN_FAMILYCOUNT = "FamilyCount";
    public final static String COLUMN_COOKINGOILTYPE = "CookingOilType";
    public final static String COLUMN_BRAND = "Brand";
    public final static String COLUMN_QUANTITYCONSUMEDPERMONTH = "QuantityConsumedperMonth";
    public final static String COLUMN_AMOUNTPAIDFOROILMONTH = "AmountPaidForOilPerMonth";
    public final static String COLUMN_TOTAL = "Total";
    public final static String COLUMN_REASONFORPARTICULAROIL = "ReasonForParticularOil";
    public final static String COLUMN_SWAPTOPALMOIL = "SwapToPalmOil";
    public final static String COLUMN_BRANDAMOUNT = "BrandAmount";
    public final static String COLUMN_SMARTPHONE = "SmartPhone";
    public final static String COLUMN_CATTLE = "Cattle";
    public final static String COLUMN_CATTLEQUANTITY = "CattleQuantity";
    public final static String COLUMN_CATTLEDETAILS = "CattleDetails";
    public final static String COLUMN_OWNVECHICLES = "OwnVehicles";
    public final static String COLUMN_VECHICLEDETAILS = "VehiclesDetails";
    public final static String COLUMN_COLLECTIONCENTERISSUES = "CollectionCentreIssues";
    public final static String COLUMN_ISSUESDETAILS = "IssueDetails";
    public final static String COLUMN_REFERRALS = "Referrals";
    public final static String COLUMN_REFERRALNAME = "ReferralName";
    public final static String COLUMN_MOBILENO = "MobileNo";
    public final static String COLUMN_COMMENTS_FARMER = "Complaint";

    //NeighboringPlot details table info
    public final static String TABLE_NEIGHBORINGPLOT = "NeighboringPlot";

    //CropMaintenance details table info
    public final static String TABLE_CROPMAINTENANCE = "CropMaintenance";

    public final static String TABLE_COMPLAINTDETAILS = "ComplaintDetails";
    public final static String COLUMN_COMPLAINTDESCRIPITION = "NatureofComplaint";
    public final static String COLUMN_DEGREEOFCOMPLAINT = "DegreeOfComplaint";
    public final static String COLUMN_STATUS = "Status";
    public final static String COLUMN_RESOLUTION = "Resolution";
    public final static String COLUMN_RESOLVEDBY = "ResolvedBy";
    public final static String COLUMN_FOLLOWUPREQURIED = "FollowupRequired";
    public final static String COLUMN_NEXTFOLLOWUPDATE = "NextFollowupDate";
    public final static String COLUMN_COMMENTSCOMPLAINT = "Comments";

    public final static String TABLE_PLANTPROTECTIONDETAILS = "PlantProtectionDetails";
    public final static String COLUMN_MODULEID = "ModuleId";
    public final static String TABLE_PICTURE_REPORTING = "PictureReporting";

    static {
        transactionTables.add(TABLE_MARKETSURVEYDETAILS);
        transactionTables.add(TABLE_CROPINFO);
        transactionTables.add(TABLE_INTERCROPDETAILSDETAILS);
        transactionTables.add(TABLE_NEIGHBORINGPLOT);
        transactionTables.add(TABLE_FFBHARVESTDETAILS);
        transactionTables.add(TABLE_UPROOTMENT);
        transactionTables.add(TABLE_CROPMAINTENANCE);
        transactionTables.add(TABLE_PLANTPROTECTIONDETAILS);
        transactionTables.add(TABLE_HEALTHOFPLANTATIONDETAILS);
        transactionTables.add(TABLE_COMPLAINTDETAILS);

    }

    //consignment tables keys

    public final static String TABLE_CONSIGNMENT = "Consignment";
    public final static String COLUMN_CODE = "Code";
    public final static String COLUMN_CCCODE = "CollectionCenterCode";
    public final static String COLUMN_VEHICLENUMBER = "VehicleNumber";
    public final static String COLUMN_DRIVERNAME = "DriverName";
    public final static String COLUMN_MILLCODE = "MillCode";
    public final static String COLUMN_TOTALWEIGHT = "TotalWeight";
    public final static String COLUMN_GROSSWEIGHT = "GrossWeight";
    public final static String COLUMN_TAREWEIGHT = "TareWeight";
    public final static String COLUMN_NETWEIGHT = "NetWeight";
    public final static String COLUMN_WEIGHTDIFF = "WeightDifference";
    public final static String COLUMN_RECEIPTGENERATEDDATE = "ReceiptGeneratedDate";
    public final static String COLUMN_RECEIPTCODE = "ReceiptCode";
    public final static String COLUMN_RECEIPTNAME = "RecieptName";
    public final static String COLUMN_RECEIPTLOCATION = "RecieptLocation";
    public final static String COLUMN_RECEIPTEXTENSION = "RecieptExtension";
    public final static String COLUMN_CCREATEDBY = "createdBy";
    public final static String COLUMN_CONSIGNMENTWEIGHT = "consignmentWeight";
    public final static String COLUMN_CCOMMENTS = "comments";
    public final static String COLUMN_CREATEDBYUSERID = "CreatedByUserId";
    public final static String COLUMN_UPDATEDBYUSERID = "UpdatedByUserId";
    public final static String COLUMN_ISACTIVE = "IsActive";
    public final static String COLUMN_CREATEDDATE = "CreatedDate";
    public final static String COLUMN_UPDATEDDATE = "UpdatedDate";
    public final static String COLUMN_SERVERUPDATEDSTATUS = "ServerUpdatedStatus";

    public final static String TABLE_CONSIGNMENTSTATUSHISTORY = "ConsignmentStatusHistory";
    public final static String COLUMN_CONSIGNMENTCODE = "ConsignmentCode";
    public final static String COLUMN_STATUSTYPEID = "StatusTypeId";
    public final static String COLUMN_OPERATORNAME = "OperatorName";

    //collection tables keys

    public final static String TABLE_COLLECTION = "Collection";

    public final static String COLUMN_CFARMERCODE = "FarmerCode";
    public final static String COLUMN_WEIGHBRIDGECENTERID = "WeighbridgeCenterId";
    public final static String COLUMN_WEIGHDATE = "WeighingDate";
    public final static String COLUMN_TOTALBUNCHES = "TotalBunches";
    public final static String COLUMN_REJECTEDBUNCHES = "RejectedBunches";
    public final static String COLUMN_ACCEPTEDBUNCHES = "AcceptedBunches";
    public final static String COLUMN_REMARKS = "Remarks";
    public final static String COLUMN_GRADERNAME = "GraderName";

    public final static String TABLE_COLLECTIONXPLOTREF = "CollectionPlotXref";

    public final static String COLUMN_COLLECTIONCODE = "CollectionCode";


    // Falog  Tracking
    public static final String TABLE_Location_TRACKING_DETAILS = "LocationTracker";
    public static final String LATITUDE="Latitude";
    public static final String LONGITUDE="Longitude";
    public static  final String IsActive = "IsActive";
    public static final String Created_Date="CreatedDate";
    public static final String CreatedBy_User_Id = "CreatedByUserId";
    public static final String Updated_date="UpdatedDate";
    public static final String UpdatedBy_User_Id = "UpdatedByUserId";
    public static final String IMEI_Number="IMEINumber";
    public static final String Server_Updated_Status="ServerUpdatedStatus";
    public final static String TABLE_ALERTS = "Alerts";


    public static final String TABLE_VisitLog = "VisitLog";
    public static final String TABLE_UserSync = "UserSync";

    public static final String CLIENTNAME="ClientName";
    public static final String MOBILENUMBER="MobileNumber";
    public static  final String LOCATION = "Location";
    public static final String DETAILS="Details";
    public static final String LAT="Latitude";
    public static final String LONG="Longitude";
    public static final String CreatedByUserId = "CreatedByUserId";
    public static final String CreatedDate="CreatedDate";
    public static final String ServerUpdatedStatus="ServerUpdatedStatus";

    public  static  final  String TABLE_HarvestorVisitHistory ="HarvestorVisitHistory";
    public  static  final  String TABLE_HarvestorVisitDetails ="HarvestorVisitDetails";


}
