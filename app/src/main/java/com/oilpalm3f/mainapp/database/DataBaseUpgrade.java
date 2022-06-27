package com.oilpalm3f.mainapp.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.oilpalm3f.mainapp.cloudhelper.Log;
import com.oilpalm3f.mainapp.common.CommonConstants;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.datasync.ui.RefreshSyncActivity;
import com.oilpalm3f.mainapp.utils.UiUtils;

import static android.content.Context.MODE_PRIVATE;


//Any modifications in DB or adding tables can be done here
class DataBaseUpgrade {

    private static final String LOG_TAG = DataBaseUpgrade.class.getName();

    static void upgradeDataBase(final Context context, final SQLiteDatabase db) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appprefs", MODE_PRIVATE);
        boolean result = true;
        try {
            boolean isFreshInstall = sharedPreferences.getBoolean(CommonConstants.IS_FRESH_INSTALL, true);
            if (isFreshInstall) {
                upgradeDb1(db);
                upgradeDb2(db);
                upgradeDb3(db);
                upgradeDb4(db);
                upgradeDb6(db);
                upgradeDb7(db);
                upgradeDb8(db);
                upgradeDb9(db);
                upgradeDb10(db);
                upgradeDb11(db);
                upgradeDb12(db);
                upgradeDb13(db);
                upgradeDb14(db);
                upgradeDb15(db);
                upgradeDb16(db);
                checkEveryTableAndColumn(db);
                upgradeDb18(db);
                upgradeDb19(db);
                upgradeDb20(db);
                upgradeDb21(db);
                upgradeDb22(db);
                upgradeDb24(db);
                upgradeDb25(db);
                upgradeDb26(db);
                upgradeDb27(db);
                upgradeDb28(db);
                upgradeDb29(db);
                upgradeDb30(db);
                upgradeDb31(db);

            } else {
                boolean isDbUpgradeFinished = sharedPreferences.getBoolean(String.valueOf(Palm3FoilDatabase.DATA_VERSION), false);
                Log.v(LOG_TAG, "@@@@ database....." + isDbUpgradeFinished);
                if (!isDbUpgradeFinished) {
                    switch (Palm3FoilDatabase.DATA_VERSION) {
                        case 1:
                            upgradeDb1(db);
                            break;
                        case 2:
                            break;
                        case 3:
                            upgradeDb2(db);
                            break;
                        case 4:
                            upgradeDb3(db);
                            break;
                        case 5:
                            upgradeDb4(db);
                            break;
                        case 6:
                            UiUtils.showCustomToastMessage("Updating database 6-->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            upgradeDb6(db);
                            break;
                        case 7:
                            upgradeDb7(db);
//                            Log.pushLogToCrashlytics("Updating database 7 :"+"\n "+" "+CommonConstants.TAB_ID+" "+ CommonUtils.getAppVersion(context));
                            UiUtils.showCustomToastMessage("Updating database 7 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 8:
                            upgradeDb8(db);

                            UiUtils.showCustomToastMessage("Updating database 8 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 9:
                            upgradeDb9(db);
                            UiUtils.showCustomToastMessage("Updating database 9 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 10:
                            upgradeDb10(db);
                            UiUtils.showCustomToastMessage("Updating database 10 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 11:
                            upgradeDb11(db);
                            UiUtils.showCustomToastMessage("Updating database 11 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 12:
                            upgradeDb12(db);
                            UiUtils.showCustomToastMessage("Updating database 12 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;

                        case 13:
                            upgradeDb13(db);
                            UiUtils.showCustomToastMessage("Updating database 13 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;

                        case 14:
                            upgradeDb14(db);
                            UiUtils.showCustomToastMessage("Updating database 14 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 15:
                            upgradeDb15(db);
                            UiUtils.showCustomToastMessage("Updating database 15 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 16:
                            upgradeDb16(db);
                            UiUtils.showCustomToastMessage("Updating database 15 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 17:
                            checkEveryTableAndColumn(db);
                            UiUtils.showCustomToastMessage("Updating database 16 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 18:
                            upgradeDb18(db);
                            UiUtils.showCustomToastMessage("Updating database 17 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 19:
                            upgradeDb19(db);
                            UiUtils.showCustomToastMessage("Updating database 18 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 20:
                            upgradeDb20(db);
                            UiUtils.showCustomToastMessage("Updating database 19 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 21:
                            upgradeDb21(db);
                            UiUtils.showCustomToastMessage("Updating database 20 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 22:
                            upgradeDb22(db);
                            UiUtils.showCustomToastMessage("Updating database 22 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 23:
                            upgradeDb23(db);
                            UiUtils.showCustomToastMessage("Updating database 23 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;

                        case 24:
                            upgradeDb24(db);
                            UiUtils.showCustomToastMessage("Updating database 24 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 25:
                            upgradeDb25(db);
                            UiUtils.showCustomToastMessage("Updating database 25 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 26:
                            upgradeDb26(db);
                            UiUtils.showCustomToastMessage("Updating database 26 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 27:
                            upgradeDb27(db);
                            UiUtils.showCustomToastMessage("Updating database 27 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 28:
                            upgradeDb28(db);
                            UiUtils.showCustomToastMessage("Updating database 28 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 29:
                            upgradeDb29(db);
                            UiUtils.showCustomToastMessage("Updating database 29 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 30:
                            upgradeDb30(db);
                            UiUtils.showCustomToastMessage("Updating database 30 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;
                        case 31:
                            upgradeDb31(db);
                            UiUtils.showCustomToastMessage("Updating database 31 -->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            break;



                    }
                } else {
                    Log.v(LOG_TAG, "@@@@ database is already upgraded " + Palm3FoilDatabase.DATA_VERSION);
                }
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, e);
            result = false;
        } finally {
            if (result) {
                Log.v(LOG_TAG, "@@@@ database is upgraded " + Palm3FoilDatabase.DATA_VERSION);
            } else {
                Log.e(LOG_TAG, "@@@@ database is upgrade failed or already upgraded");
            }
            sharedPreferences.edit().putBoolean(CommonConstants.IS_FRESH_INSTALL, false).apply();
            sharedPreferences.edit().putBoolean(String.valueOf(Palm3FoilDatabase.DATA_VERSION), true).apply();
        }
    }


    public static void upgradeDb1(final SQLiteDatabase db) {
        Log.d(LOG_TAG, "******* upgradeDataBase " + Palm3FoilDatabase.DATA_VERSION);

        String alterGeoBoundariesTable1 = "ALTER TABLE GeoBoundaries ADD COLUMN CropMaintenanceCode VARCHAR (60)";

        try {
            db.execSQL(alterGeoBoundariesTable1);
            //  db.execSQL(alterPlotTable1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void upgradeDb2(final SQLiteDatabase db) {
        Log.v(LOG_TAG, "@@@@ database is upgraded upgradeDb2 " + Palm3FoilDatabase.DATA_VERSION);
        String CREATE_USER_TARGET = "CREATE TABLE UserTarget(\n" +
                "Id INTEGER   PRIMARY KEY AUTOINCREMENT\n" +
                "                                      NOT NULL,\n" +
                "UserKRAId INT NOT NULL ,\n" +
                "KRACode VARCHAR ,\n" +
                "KRAName VARCHAR ,\n" +
                "UOM VARCHAR ,\n" +
                "AnnualTarget Float ,\n" +
                "AchievedTarget Float ,\n" +
                "UserId INT NOT NULL\n" +
                ")";
        try {
            db.execSQL(CREATE_USER_TARGET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String CREATE_USER_MONTHLY_TARGET = "CREATE TABLE UserMonthlyTarget(\n" +
                "Id INTEGER   PRIMARY KEY AUTOINCREMENT\n" +
                "                                      NOT NULL,\n" +
                "UserKRAId INT NOT NULL ,\n" +
                "KRACode VARCHAR ,\n" +
                "KRAName VARCHAR ,\n" +
                "UOM VARCHAR ,\n" +
                "UserId INT NOT NULL ,\n" +
                "MonthNumber INT NOT NULL,\n" +
                "MonthlyTarget Float ,\n" +
                "AchievedTarget Float \n" +
                ")";
        try {
            db.execSQL(CREATE_USER_MONTHLY_TARGET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dropComplaintsTable = "DROP TABLE ComplaintStatusHistory";
        db.execSQL(dropComplaintsTable);
        String createComplaintsStatusHistoryTable = "CREATE TABLE ComplaintStatusHistory (\n" +
                "    Id                        INTEGER       PRIMARY KEY AUTOINCREMENT\n" +
                "                                            NOT NULL,\n" +
                "    ComplaintCode       VARCHAR (60)   NOT NULL,\n" +
                "    StatusTypeId        INT            NOT NULL,\n" +
                "    AssigntoUserId      INT            NOT NULL,\n" +
                "    Comments            VARCHAR (150),\n" +
                "    IsActive            BOOLEAN            NOT NULL,\n" +
                "    CreatedByUserId     INT            NOT NULL,\n" +
                "    CreatedDate         VARCHAR       NOT NULL,\n" +
                "    UpdatedByUserId     INT            NOT NULL,\n" +
                "    UpdatedDate         VARCHAR       NOT NULL,\n" +
                "    ServerUpdatedStatus BOOLEAN            DEFAULT 0\n" +
                "                                       NOT NULL,\n" +
                "    FOREIGN KEY (\n" +
                "        ComplaintCode\n" +
                "    )\n" +
                "    REFERENCES Complaints (Code),\n" +
                "    FOREIGN KEY (\n" +
                "        StatusTypeId\n" +
                "    )\n" +
                "    REFERENCES TypeCdDmt (TypeCdId),\n" +
                "    FOREIGN KEY (\n" +
                "        AssigntoUserId\n" +
                "    )\n" +
                "    REFERENCES UserInfo (Id),\n" +
                "    FOREIGN KEY (\n" +
                "        CreatedByUserId\n" +
                "    )\n" +
                "    REFERENCES UserInfo (Id),\n" +
                "    FOREIGN KEY (\n" +
                "        UpdatedByUserId\n" +
                "    )\n" +
                "    REFERENCES UserInfo (Id) \n" +
                ");";
        try {
            db.execSQL(createComplaintsStatusHistoryTable);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String dropComplaintTypeXref = "DROP TABLE ComplaintTypeXref";
        db.execSQL(dropComplaintTypeXref);
        String createComplaintTypeXref = "CREATE TABLE ComplaintTypeXref(\n" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "ComplaintCode varchar(60) NOT NULL,\n" +
                "ComplaintTypeId INT   NOT NULL,\n" +
                "CreatedByUserId INT  NOT NULL,\n" +
                "CreatedDate DATETIME NOT NULL,\n" +
                "UpdatedByUserId INT  NOT NULL,\n" +
                "UpdatedDate DATETIME NOT NULL,\n" +
                "ServerUpdatedStatus BOOLEAN DEFAULT 0 NOT NULL\n" +
                ");";
        try {
            db.execSQL(createComplaintTypeXref);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String alterComplaints = "ALTER TABLE Complaints ADD COLUMN CropMaintenanceCode VARCHAR (60)";
        try {
            db.execSQL(alterComplaints);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void upgradeDb3(final SQLiteDatabase db) {
        Log.d(LOG_TAG, "******* upgradeDataBase " + Palm3FoilDatabase.DATA_VERSION);
        String dropQuery = "Drop table LocationTracker";
        db.execSQL(dropQuery);
        String locationTracker = "CREATE TABLE LocationTracker (\n" +
                " Id  INTEGER  PRIMARY KEY AUTOINCREMENT\n" +
                " NOT NULL,\n" +
                " UserId INT NOT NULL,\n" +
                " Latitude           FLOAT               NOT NULL,\n" +
                " Longitude          FLOAT               NOT NULL,\n" +
                " Address VARCHAR,\n" +
                " LogDate VARCHAR,\n" +
                " ServerUpdatedStatus BOOLEAN DEFAULT 0 NOT NULL " +
                ");";
        try {
            db.execSQL(locationTracker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void upgradeDb4(final SQLiteDatabase db) {


        String alertsTable = "CREATE TABLE Alerts( \n" +
                "Id INTEGER, \n" +
                "Name VARCHAR, \n" +
                "[Desc] VARCHAR, \n" +
                "UserId INT NOT NULL, \n" +
                "HTMLDesc Varchar(2000), \n" +
                "IsRead INT NOT NULL, \n" +
                "PlotCode VARCHAR, \n" +
                "ComplaintCode VARCHAR, \n" +
                "AlertTypeId INT, \n" +
                "CreatedByUserId INT,\n" +
                "CreatedDate VARCHAR,\n" +
                "UpdatedByUserId INT, \n" +
                "UpdatedDate VARCHAR,\n" +
                "ServerUpdatedStatus INT\n" +
                ")";
        String alterPlotTable1 = "ALTER TABLE Plot ADD COLUMN OriginCode VARCHAR (60)  ";
        String alterPlotTable2 = "ALTER TABLE Plot ADD COLUMN ReasonTypeId int  ";
        String alterPlotTable3 = "ALTER TABLE Plot ADD COLUMN InactivatedByUserId INT ";
        String alterPlotTable4 = "ALTER TABLE Plot ADD COLUMN InactivatedDate DATETIME ";
        String alterPlotTable5 = "ALTER TABLE Plot ADD COLUMN InActiveReasonTypeId int ";
        String alterPlotTable6 = "ALTER TABLE Plot ADD COLUMN PlansToPlanInFuture BOOLEAN DEFAULT NULL";

        String alterFarmerTable1 = "ALTER TABLE Farmer ADD COLUMN InActivatedByUserId INTEGER ";
        String alterFarmerTable2 = "ALTER TABLE Farmer ADD COLUMN InactivatedDate DATETIME ";
        String alterFarmerTable3 = "ALTER TABLE Farmer ADD COLUMN InactivatedReasonTypeId INT ";

        String alterUserTargetTable1 = "ALTER TABLE UserTarget ADD COLUMN CreatedByUserId INT   ";
        String alterUserTargetTable2 = "ALTER TABLE UserTarget ADD COLUMN CreatedDate DATETIME  ";
        String alterUserTargetTable3 = "ALTER TABLE UserTarget ADD COLUMN UpdatedByUserId INT   ";
        String alterUserTargetTable4 = "ALTER TABLE UserTarget ADD COLUMN UpdatedDate DATETIME  ";
        String alterUserTargetTable5 = "ALTER TABLE UserTarget ADD COLUMN IsActive INT  ";




      /*  ALTER TABLE dbo.Pest ADD RecommendedChemicalId INT NULL FOREIGN KEY REFERENCES dbo.lookup(Id),
                Dosage  Float Null,
        UOMId  INT NULL FOREIGN KEY REFERENCES dbo.UOM(Id);
*/

        try {
            db.execSQL(alertsTable);
            db.execSQL(alterPlotTable1);
            db.execSQL(alterPlotTable2);
            db.execSQL(alterPlotTable3);
            db.execSQL(alterPlotTable4);
            db.execSQL(alterPlotTable5);
            db.execSQL(alterPlotTable6);
            db.execSQL(alterFarmerTable1);
            db.execSQL(alterFarmerTable2);
            db.execSQL(alterFarmerTable3);
            db.execSQL(alterUserTargetTable1);
            db.execSQL(alterUserTargetTable2);
            db.execSQL(alterUserTargetTable3);
            db.execSQL(alterUserTargetTable4);
            db.execSQL(alterUserTargetTable5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void upgradeDb6(final SQLiteDatabase db) {
        Log.d(LOG_TAG, "******* upgradeDataBase 6 " + Palm3FoilDatabase.DATA_VERSION);

        String alertPlotTable7 = "ALTER TABLE Plot ADD COLUMN IsRetakeGeoTagRequired INTEGER DEFAULT 0";

        try {

            db.execSQL(alertPlotTable7);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void upgradeDb7(final SQLiteDatabase db) {
        Log.d(LOG_TAG, "******* upgradeDataBase 7 " + Palm3FoilDatabase.DATA_VERSION);
        String alterPestTable1 = "ALTER TABLE Pest ADD COLUMN RecommendedChemicalId INT   ";
        String alterPestTable2 = "ALTER TABLE Pest ADD COLUMN Dosage  Float ";
        String alterPestTable3 = "ALTER TABLE Pest ADD COLUMN UOMId  INT ";
        String alterNutrientTable1 = "ALTER TABLE Nutrient ADD COLUMN RecommendedFertilizerId INT  ";
        String alterNutrientTable2 = "ALTER TABLE Nutrient ADD COLUMN Dosage  Float ";
        String alterNutrientTable3 = "ALTER TABLE Nutrient ADD COLUMN UOMId  INT ";
        String alterDiseaseTable1 = "ALTER TABLE Disease ADD COLUMN RecommendedChemicalId INT  ";
        String alterDiseaseTable2 = "ALTER TABLE Disease ADD COLUMN Dosage  Float ";
        String alterDiseaseTable3 = "ALTER TABLE Disease ADD COLUMN UOMId  INT ";

        String FertilizerRecommendationsTable = "CREATE TABLE FertilizerRecommendations(        " +
                "    Id             INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "    PlotCode       VARCHAR (60)   NOT NULL,\n" +
                "    CropMaintenanceCode            Varchar(60)            NOT NULL ,\n" +
                "    RecommendedFertilizerId            INTEGER             ,\n" +
                "    Dosage        Float               ,\n" +
                "    UOMId            INTEGER             ,\n" +
                "    Comments            VARCHAR (150),\n" +
                "    IsActive            BOOLEAN            NOT NULL,\n" +
                "    CreatedByUserId     INT            NOT NULL,\n" +
                "    CreatedDate         VARCHAR       NOT NULL,\n" +
                "    UpdatedByUserId     INT            NOT NULL,\n" +
                "    UpdatedDate         VARCHAR       NOT NULL,\n" +
                "    ServerUpdatedStatus BOOLEAN            DEFAULT 0\n" +

                ");";


        String BenchMarkTable = "CREATE TABLE Benchmark(        " +
                "    Id                  INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "    Year                   INT        NOT NULL,\n" +
                "    MonthName            Varchar(10)  NOT NULL ,\n" +
                "    MonthSequenceNumber    INT        NOT NULL ,\n" +
                "    MonthlyPercentage      FLOAT      NOT NULL ,\n" +
                "    Age                    INT        NOT NULL,\n" +
                "    YieldPerHectar        FLOAT       NOT NULL,\n" +
                "    StateId                INT            \n" +
                ");";

        String alterActivityLog = "ALTER TABLE ActivityLog  ADD COLUMN ConsignmentCode   Varchar(50)  ";

        String DropHarvestTable = "Drop table Harvest";
        db.execSQL(DropHarvestTable);

        String createHarvestTable = "CREATE TABLE Harvest (         " +
                "   Id                     INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "   PlotCode               VARCHAR (50),\n" +
                "   PlotYield              FLOAT,\n" +
                "   YieldPerHactor         FLOAT,\n" +
                "   CollectionCenterId     INT,\n" +
                "   TransportModeTypeId    INT,\n" +
                "   VehicleTypeId          INT,\n" +
                "   TransportPaidAmount    INT,\n" +
                "   HarvestingMethodTypeId INT,\n" +
                "   WagesPerDay            FLOAT         NOT NULL,\n" +
                "   HarvestingTypeId       INT,\n" +
                "   Comments               VARCHAR (150),\n" +
                "   IsActive               BIT           NOT NULL,\n" +
                "   CreatedByUserId        INT,\n" +
                "   CreatedDate            VARCHAR       NOT NULL,\n" +
                "   UpdatedByUserId        INT,\n" +
                "   UpdatedDate            VARCHAR       NOT NULL,\n" +
                "   ServerUpdatedStatus    INT,\n" +
                "   CropMaintenanceCode    VARCHAR,\n" +
                "   WagesUnitTypeId        INT           , \n" +
                "   ContractAmount         FLOAT             \n" +
                ");";

        try {

            db.execSQL(alterPestTable1);
            db.execSQL(alterPestTable2);
            db.execSQL(alterPestTable3);
            db.execSQL(alterNutrientTable1);
            db.execSQL(alterNutrientTable2);
            db.execSQL(alterNutrientTable3);
            db.execSQL(alterDiseaseTable1);
            db.execSQL(alterDiseaseTable2);
            db.execSQL(alterDiseaseTable3);
            db.execSQL(FertilizerRecommendationsTable);
            db.execSQL(BenchMarkTable);
            db.execSQL(alterActivityLog);
            db.execSQL(createHarvestTable);



            /*db.execSQL(alterDiseaseTable);*/


        } catch (Exception e) {
            Log.v("DataBaseUpgrade", "error Updating database 7-->" + Palm3FoilDatabase.DATA_VERSION + e.getMessage());

            e.printStackTrace();
        }
    }

    public static void upgradeDb8(final SQLiteDatabase db) {
        Log.d(LOG_TAG, "****** upgradeDataBase 8 " + Palm3FoilDatabase.DATA_VERSION);

        String drop_Plot_Table = "Drop table Plot";
        db.execSQL(drop_Plot_Table);
        String create_Plot_table = "CREATE TABLE Plot   (          " +
                " Id                          INTEGER       PRIMARY KEY,\n" +
                " Code                        VARCHAR (50)  NOT NULL,\n" +
                " FarmerCode                  VARCHAR (50)  NOT NULL,\n" +
                " IsBoundryFencing            BOOLEAN,\n" +
                " TotalPlotArea               DECIMAL       NOT NULL,\n" +
                " TotalPalmArea               DECIMAL,\n" +
                " GPSPlotArea                 DECIMAL,\n" +
                " CropIncomeTypeId            INT,\n" +
                " AddressCode                 VARCHAR (60)  NOT NULL,\n" +
                " SurveyNumber                VARCHAR (30),\n" +
                " AdangalNumber               VARCHAR (30),\n" +
                " LeftOutArea                 DECIMAL,\n" +
                " LeftOutAreaCropId           INT,\n" +
                " PlotOwnerShipTypeId         INT,\n" +
                " IsPlotHandledByCareTaker    INT           DEFAULT (NULL),\n" +
                " CareTakerName               VARCHAR (150),\n" +
                " CareTakerContactNumber      VARCHAR (15),\n" +
                " IsActive                    BOOLEAN       NOT NULL,\n" +
                " CreatedByUserId             INT           NOT NULL,\n" +
                " CreatedDate                 VARCHAR       NOT NULL,\n" +
                " UpdatedByUserId             INT           NOT NULL,\n" +
                " UpdatedDate                 VARCHAR       NOT NULL,\n" +
                " ServerUpdatedStatus         BOOLEAN       NOT NULL  DEFAULT (0),\n" +
                " Comments                    VARCHAR (500),\n" +
                " IsPLotSubsidySubmission     BOOLEAN,\n" +
                " IsPLotHavingIdCard          BOOLEAN,\n" +
                " IsGeoBoundariesVerification BOOLEAN,\n" +
                " SuitablePalmOilArea         FLOAT,\n" +
                " DateofPlanting              VARCHAR,\n" +
                " SwapingReasonId             INTEGER,\n" +
                " OriginCode                  VARCHAR (60),\n" +
                " ReasonTypeId                INT,\n" +
                " InactivatedByUserId         INT,\n" +
                " InactivatedDate             DATETIME,\n" +
                " InActiveReasonTypeId        INT,\n" +
                " PlansToPlanInFuture         BOOLEAN       DEFAULT NULL,\n" +
                " IsRetakeGeoTagRequired      INTEGER       DEFAULT 0\n" +
                ");";

        String crate_Table_AdvancedDetails = "CREATE TABLE AdvancedDetails(    " +
                " Id                                INTEGER     PRIMARY KEY AUTOINCREMENT ,\n" +
                " PlotCode                         VARCHAR(50)             NOT NULL,\n" +
                " AdvanceAmountReceived              DOUBLE,\n" +
                " DateOfAdvanceReceived             DATETIME,\n" +
                " ExpectedMonthOfPlanting           DATETIME,\n" +
                " NoOfSaplingsAdvancePaidFor         INT                    ,\n" +
                " NoOfImportedSaplingsToBeIssued     INT                    ,\n" +
                " NoOfIndigenousSaplingsToBeIssued   INT                    , \n" +
                " AdvanceReceivedArea               FLOAT                   ,\n" +
                " SurveyNumber                     VARCHAR(50)              ,\n" +
                " ReceiptNumber                    VARCHAR(50)              ,\n" +
                " Comments                         VARCHAR(500)             ,\n" +
                " CreatedByUserId                     INT                 NOT NULL,\n" +
                " CreatedDate                      DATETIME               NOT NULL,\n" +
                " UpdatedByUserId                    INT                  NOT NULL,\n" +
                " UpdatedDate                      DATETIME               NOT NULL\n" +
                " );";

        String Create_Table_NurserySaplingDetails = "CREATE TABLE NurserySaplingDetails(" +
                " Id                                  INTEGER       PRIMARY KEY AUTOINCREMENT ,\n" +
                " PlotCode                          VARCHAR(50)                     NOT NULL,\n" +
                " SaplingPickUpDate                 DATETIME                        NOT NULL,\n" +
                " NoOfSaplingsDispatched              INT                           NOT NULL,\n" +
                " NoOfImportedSaplingsDispatched      INT                           NOT NULL,\n" +
                " NoOfIndigenousSaplingsDispatched    INT                           NOT NULL,\n" +
                " ReceiptNumber                     VARCHAR(50)                     NOT NULL,\n" +
                " CreatedByUserId                     INT                           NOT NULL,\n" +
                " CreatedDate                       DATETIME                        NOT NULL,\n" +
                " UpdatedByUserId                     INT                           NOT NULL,\n" +
                " UpdatedDate                       DATETIME                        NOT NULL" + " );";

        String CollectionCenter_Table1 = "ALTER TABLE CollectionCenter ADD COLUMN Latitude   FLOAT";
        String CollectionCenter_Table2 = "ALTER TABLE CollectionCenter ADD COLUMN Longitude  FLOAT";


        try {
            db.execSQL(create_Plot_table);
            db.execSQL(crate_Table_AdvancedDetails);
            db.execSQL(Create_Table_NurserySaplingDetails);
            db.execSQL(CollectionCenter_Table1);
            db.execSQL(CollectionCenter_Table2);
        } catch (Exception e) {

            Log.i("DataBaseUpgrade", "error Updating database 8-->" + Palm3FoilDatabase.DATA_VERSION + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void upgradeDb9(final SQLiteDatabase db) {
        Log.d(LOG_TAG, "****** upgradeDataBase 9 " + Palm3FoilDatabase.DATA_VERSION);


        String ALTER_BENCHMARK_Table1 = "ALTER TABLE Benchmark ADD COLUMN CreatedByUserId   INT   ";
        String ALTER_BENCHMARK_Table2 = "ALTER TABLE Benchmark ADD COLUMN CreatedDate  DATETIME   ";

        try {
            db.execSQL(ALTER_BENCHMARK_Table1);
            db.execSQL(ALTER_BENCHMARK_Table2);
        } catch (Exception e) {

            Log.i("DataBaseUpgrade", "error Updating database 9-->" + Palm3FoilDatabase.DATA_VERSION + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void upgradeDb10(final SQLiteDatabase db) {
        Log.d(LOG_TAG, "******* upgradeDataBase 9 to " + Palm3FoilDatabase.DATA_VERSION);
        String drop_Farmer_Table = "Drop table Farmer";

        String alterFarmerTable = "CREATE TABLE Farmer (\n" +
                "    Id                      INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "    Code                    VARCHAR (50)  NOT NULL\n" +
                "                                          UNIQUE,\n" +
                "    CountryId               INT           NOT NULL,\n" +
                "    RegionId                INT           NOT NULL,\n" +
                "    StateId                 INT           NOT NULL,\n" +
                "    DistrictId              INT           NOT NULL,\n" +
                "    MandalId                INT           NOT NULL,\n" +
                "    VillageId               INT           NOT NULL,\n" +
                "    SourceOfContactTypeId   INT           NOT NULL,\n" +
                "    TitleTypeId             INT           NOT NULL,\n" +
                "    FirstName               VARCHAR (255) NOT NULL,\n" +
                "    MiddleName              VARCHAR (255),\n" +
                "    LastName                VARCHAR (255) NOT NULL,\n" +
                "    GuardianName            VARCHAR (255) NOT NULL,\n" +
                "    MotherName              VARCHAR (255)          ,\n" +
                "    GenderTypeId            INT           NOT NULL,\n" +
                "    ContactNumber           VARCHAR (15)  NOT NULL,\n" +
                "    MobileNumber            VARCHAR (15),\n" +
                "    DOB                     VARCHAR,\n" +
                "    Age                     INT           NOT NULL,\n" +
                "    Email                   VARCHAR (50),\n" +
                "    CategoryTypeId          INT,\n" +
                "    AnnualIncomeTypeId      INT,\n" +
                "    AddressCode             VARCHAR (60),\n" +
                "    EducationTypeId         INT,\n" +
                "    IsActive                BOOLEAN       NOT NULL,\n" +
                "    CreatedByUserId         INT           NOT NULL,\n" +
                "    CreatedDate             VARCHAR       NOT NULL,\n" +
                "    UpdatedDate             VARCHAR       NOT NULL,\n" +
                "    UpdatedByUserId         INT           NOT NULL,\n" +
                "    ServerUpdatedStatus     BOOLEAN       NOT NULL\n" +
                "                                          DEFAULT 0,\n" +
                "    InActivatedByUserId     INTEGER,\n" +
                "    InactivatedDate         DATETIME,\n" +
                "    InactivatedReasonTypeId INT,\n" +
                "    FOREIGN KEY (\n" +
                "        CountryId\n" +
                "    )\n" +
                "    REFERENCES Country (Id),\n" +
                "    FOREIGN KEY (\n" +
                "        RegionId\n" +
                "    )\n" +
                "    REFERENCES Region (Id),\n" +
                "    FOREIGN KEY (\n" +
                "        StateId\n" +
                "    )\n" +
                "    REFERENCES State (Id),\n" +
                "    FOREIGN KEY (\n" +
                "        DistrictId\n" +
                "    )\n" +
                "    REFERENCES DistrictId (Id),\n" +
                "    FOREIGN KEY (\n" +
                "        MandalId\n" +
                "    )\n" +
                "    REFERENCES Mandal (Id),\n" +
                "    FOREIGN KEY (\n" +
                "        VillageId\n" +
                "    )\n" +
                "    REFERENCES Village (Id),\n" +
                "    FOREIGN KEY (\n" +
                "        SourceOfContactTypeId\n" +
                "    )\n" +
                "    REFERENCES TypeCdDmt (TypeCdId),\n" +
                "    FOREIGN KEY (\n" +
                "        TitleTypeId\n" +
                "    )\n" +
                "    REFERENCES TypeCdDmt (TypeCdId),\n" +
                "    FOREIGN KEY (\n" +
                "        GenderTypeId\n" +
                "    )\n" +
                "    REFERENCES TypeCdDmt (TypeCdId),\n" +
                "    FOREIGN KEY (\n" +
                "        CategoryTypeId\n" +
                "    )\n" +
                "    REFERENCES TypeCdDmt (TypeCdId),\n" +
                "    FOREIGN KEY (\n" +
                "        AnnualIncomeTypeId\n" +
                "    )\n" +
                "    REFERENCES TypeCdDmt (TypeCdId),\n" +
                "    FOREIGN KEY (\n" +
                "        AddressCode\n" +
                "    )\n" +
                "    REFERENCES Address (Code),\n" +
                "    FOREIGN KEY (\n" +
                "        EducationTypeId\n" +
                "    )\n" +
                "    REFERENCES TypeCdDmt (TypeCdId),\n" +
                "    FOREIGN KEY (\n" +
                "        CreatedByUserId\n" +
                "    )\n" +
                "    REFERENCES UserInfo (Id),\n" +
                "    FOREIGN KEY (\n" +
                "        UpdatedByUserId\n" +
                "    )\n" +
                "    REFERENCES UserInfo (Id) \n" +
                ");";
        try {
            db.execSQL(drop_Farmer_Table);
            db.execSQL(alterFarmerTable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void upgradeDb11(final SQLiteDatabase db) {

        String ClusterTable = "CREATE TABLE  Cluster ( \n" +
                "Id             INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "Code           VARCHAR(5)      NOT NULL,\n" +
                "Name           VARCHAR(50)     NOT NULL,\n" +
                "IsActive       BIT             NOT NULL DEFAULT 1,\n" +
                "CreatedByUserId INT            NOT NULL  ,\n" +
                "CreatedDate    DATETIME        NOT NULL,\n" +
                "UpdatedByUserId INT            NOT NULL ,\n" +
                "UpdatedDate    DATETIME        NOT NULL \n" +
                ");";

        String ClusterVillageXref = "  CREATE TABLE     VillageClusterxref     (\n" +
                " VillageId    INT     NOT NULL ,\n" +
                " ClusterId    INT     NOT NULL \n" +
                ");";

        String DropHarvestTable = "Drop table Harvest";
        db.execSQL(DropHarvestTable);

        String createHarvestTable = "CREATE TABLE Harvest (         " +
                "   Id                     INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "   PlotCode               VARCHAR (50),\n" +
                "   PlotYield              FLOAT,\n" +
                "   YieldPerHactor         FLOAT,\n" +
                "   CollectionCenterId     INT,\n" +
                "   TransportModeTypeId    INT,\n" +
                "   VehicleTypeId          INT,\n" +
                "   TransportPaidAmount    INT,\n" +
                "   HarvestingMethodTypeId INT,\n" +
                "   WagesPerDay            FLOAT         NOT NULL,\n" +
                "   HarvestingTypeId       INT,\n" +
                "   Comments               VARCHAR (150),\n" +
                "   IsActive               BIT           NOT NULL,\n" +
                "   CreatedByUserId        INT,\n" +
                "   CreatedDate            VARCHAR       NOT NULL,\n" +
                "   UpdatedByUserId        INT,\n" +
                "   UpdatedDate            VARCHAR       NOT NULL,\n" +
                "   ServerUpdatedStatus    INT,\n" +
                "   CropMaintenanceCode    VARCHAR,\n" +
                "   WagesUnitTypeId        INT           , \n" +
                "   ContractAmount         FLOAT             \n" +
                ");";
        String AlterMarketSurveyTable = "ALTER TABLE MarketSurvey ADD COLUMN FarmerMobileNumber VARCHAR(15)";


        try {

            db.execSQL(ClusterTable);
            db.execSQL(ClusterVillageXref);
            db.execSQL(createHarvestTable);
            db.execSQL(AlterMarketSurveyTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void upgradeDb12(final SQLiteDatabase db) {

        String ErrorLogTable = "CREATE TABLE ErrorLogs (    " +
                "    Id          INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "    TableName   VARCHAR (100),\n" +
                "    Error       VARCHAR,\n" +
                "    CreatedDate DATETIME\n" +
                ");";

        try {
            db.execSQL(ErrorLogTable);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public static void upgradeDb13(final SQLiteDatabase db) {

        String alterTable = "ALTER TABLE FollowUp ADD COLUMN ExpectedMonthofSowing DateTime  ";
        String alterTable1 = "ALTER TABLE IdentityProof ADD COLUMN FileName VARCHAR(100)  ";
        String alterTable2 = "ALTER TABLE IdentityProof ADD COLUMN FileLocation VARCHAR(250)  ";
        String alterTable3 = "ALTER TABLE IdentityProof ADD COLUMN FileExtension VARCHAR(25)  ";

        String createVisitLogTable = " CREATE TABLE VisitLog (         " +
                "   Id                        INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "   ClientName                 VARCHAR (255),\n" +
                "   MobileNumber               VARCHAR (255),\n" +
                "   Location                   VARCHAR (255),\n" +
                "   Details                    VARCHAR (255),\n" +
                "   Latitude                   FLOAT      ,\n" +
                "   Longitude                  FLOAT     ,\n" +
                "   CreatedByUserId            INT,\n" +
                "   CreatedDate               VARCHAR       ,\n" +
                "   ServerUpdatedStatus       INT  \n" +
                ");";
        String alterPlantation = " ALTER TABLE Plantation ADD COLUMN ReasonTypeId INT ";
        String alterUprootMeant = " ALTER TABLE Uprootment ADD COLUMN ExpectedPlamsCount  INT ";

        try {
            db.execSQL(alterTable);
            db.execSQL(alterTable1);
            db.execSQL(alterTable2);
            db.execSQL(alterTable3);
            db.execSQL(createVisitLogTable);
            db.execSQL(alterPlantation);
            db.execSQL(alterUprootMeant);

        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }

    public static void upgradeDb14(final SQLiteDatabase db) {

        String advanceSummaryView = "CREATE VIEW AdvanceSummary AS\n" +
                "SELECT AD.PlotCode, SUM(AD.NoOfSaplingsAdvancePaidFor) NoOfSaplingsAdvancePaidFor\n" +
                "FROM AdvancedDetails AD\n" +
                "Group By AD.PlotCode ";

        String nurserySummaryView = "CREATE VIEW NurserySummary AS\n" +
                "SELECT NSD.PlotCode, SUM(NSD.NoOfSaplingsDispatched) NoOfSaplingsDispatched\n" +
                "FROM NurserySaplingDetails NSD\n" +
                "Group By NSD.PlotCode ";

        // Update Advance Details Table
        String alterAdvanceDetailsTable = "ALTER TABLE AdvancedDetails ADD COLUMN RateOfIndegenousNumberOfSaplings DOUBLE  ";
        String alterAdvanceDetailsTable1 = "ALTER TABLE AdvancedDetails ADD COLUMN RateOfImportedNumberOfSaplings DOUBLE  ";
        String alterAdvanceDetailsTable2 = "ALTER TABLE AdvancedDetails ADD COLUMN ModeOfPayment INT  ";

        // Update NurserySapling Table
        String alterNurserySaplingTable = "ALTER TABLE NurserySaplingDetails ADD COLUMN NurseryId INT  ";
        String alterNurserySaplingTable1 = "ALTER TABLE NurserySaplingDetails ADD COLUMN CropVarietyId INT  ";
        String alterNurserySaplingTable2 = "ALTER TABLE NurserySaplingDetails ADD COLUMN SaplingSourceId INT  ";
        String alterNurserySaplingTable3 = "ALTER TABLE NurserySaplingDetails ADD COLUMN SaplingVendorId INT  ";
        String alterNurserySaplingTable4 = "ALTER TABLE NurserySaplingDetails ADD COLUMN BatchNo varchar(100)";
        String alterNurserySaplingTable5 = "ALTER TABLE NurserySaplingDetails ADD COLUMN PurchaseDate DATETIME ";

        String alterPlantationTable = "ALTER TABLE Plantation ADD COLUMN GFReceiptNumber varchar(50) ";


        try {
            db.execSQL(advanceSummaryView);
            db.execSQL(nurserySummaryView);
            db.execSQL(alterAdvanceDetailsTable);
            db.execSQL(alterAdvanceDetailsTable1);
            db.execSQL(alterAdvanceDetailsTable2);
            db.execSQL(alterNurserySaplingTable);
            db.execSQL(alterNurserySaplingTable1);
            db.execSQL(alterNurserySaplingTable2);
            db.execSQL(alterNurserySaplingTable3);
            db.execSQL(alterNurserySaplingTable4);
            db.execSQL(alterNurserySaplingTable5);
            db.execSQL(alterPlantationTable);
        } catch (SQLiteException se) {
            se.printStackTrace();
        }
    }


    public static void upgradeDb15(final SQLiteDatabase db) {

        String ChequeNo = "ALTER TABLE AdvancedDetails ADD COLUMN ChequeNo varchar(50) ";
        String ChequeDate = "ALTER TABLE AdvancedDetails ADD COLUMN ChequeDate DATETIME ";
        String BankId = "ALTER TABLE AdvancedDetails ADD COLUMN BankId Int ";

        try {
            db.execSQL(ChequeNo);
            db.execSQL(ChequeDate);
            db.execSQL(BankId);

        } catch (SQLiteException se) {
            se.printStackTrace();
        }


    }

    public static void upgradeDb16(final SQLiteDatabase db) {

        String createAdvanceSample = "CREATE TABLE sqlitestudio_temp_table AS SELECT * FROM AdvancedDetails";
        String dropAdvance = "DROP TABLE AdvancedDetails;";

        String addDataToAdvance = "CREATE TABLE AdvancedDetails (\n" +
                "    Id                                           INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "    PlotCode                                     VARCHAR (50)  NOT NULL,\n" +
                "    FarmerContributionReceived                   DOUBLE,\n" +
                "    DateOfAdvanceReceived                        DATETIME,\n" +
                "    ExpectedMonthOfPlanting                      DATETIME,\n" +
                "    NoOfSaplingsAdvancePaidFor                   INT,\n" +
                "    NoOfImportedSaplingsToBeIssued               INT,\n" +
                "    NoOfIndigenousSaplingsToBeIssued             INT,\n" +
                "    AdvanceReceivedArea                          FLOAT,\n" +
                "    SurveyNumber                                 VARCHAR (50),\n" +
                "    ReceiptNumber                                VARCHAR (50),\n" +
                "    Comments                                     VARCHAR (500),\n" +
                "    CreatedByUserId                              INT           NOT NULL,\n" +
                "    CreatedDate                                  DATETIME      NOT NULL,\n" +
                "    UpdatedByUserId                              INT           NOT NULL,\n" +
                "    UpdatedDate                                  DATETIME      NOT NULL,\n" +
                "    FarmerContributionPriceForImportedSaplings   DOUBLE,\n" +
                "    FarmerContributionPriceForIndigenousSaplings DOUBLE,\n" +
                "    ModeOfPayment                                INT,\n" +
                "    ChequeNo                                     VARCHAR (50),\n" +
                "    ChequeDate                                   DATETIME,\n" +
                "    BankId                                       INT\n" +
                ");\n" +
                "\n" +
                "INSERT INTO AdvancedDetails (\n" +
                "                                Id,\n" +
                "                                PlotCode,\n" +
                "                                FarmerContributionReceived,\n" +
                "                                DateOfAdvanceReceived,\n" +
                "                                ExpectedMonthOfPlanting,\n" +
                "                                NoOfSaplingsAdvancePaidFor,\n" +
                "                                NoOfImportedSaplingsToBeIssued,\n" +
                "                                NoOfIndigenousSaplingsToBeIssued,\n" +
                "                                AdvanceReceivedArea,\n" +
                "                                SurveyNumber,\n" +
                "                                ReceiptNumber,\n" +
                "                                Comments,\n" +
                "                                CreatedByUserId,\n" +
                "                                CreatedDate,\n" +
                "                                UpdatedByUserId,\n" +
                "                                UpdatedDate,\n" +
                "                                FarmerContributionPriceForImportedSaplings,\n" +
                "                                FarmerContributionPriceForIndigenousSaplings,\n" +
                "                                ModeOfPayment,\n" +
                "                                ChequeNo,\n" +
                "                                ChequeDate,\n" +
                "                                BankId\n" +
                "                            )\n" +
                "                            SELECT Id,\n" +
                "                                   PlotCode,\n" +
                "                                   AdvanceAmountReceived,\n" +
                "                                   DateOfAdvanceReceived,\n" +
                "                                   ExpectedMonthOfPlanting,\n" +
                "                                   NoOfSaplingsAdvancePaidFor,\n" +
                "                                   NoOfImportedSaplingsToBeIssued,\n" +
                "                                   NoOfIndigenousSaplingsToBeIssued,\n" +
                "                                   AdvanceReceivedArea,\n" +
                "                                   SurveyNumber,\n" +
                "                                   ReceiptNumber,\n" +
                "                                   Comments,\n" +
                "                                   CreatedByUserId,\n" +
                "                                   CreatedDate,\n" +
                "                                   UpdatedByUserId,\n" +
                "                                   UpdatedDate,\n" +
                "                                   RateOfIndegenousNumberOfSaplings,\n" +
                "                                   RateOfImportedNumberOfSaplings,\n" +
                "                                   ModeOfPayment,\n" +
                "                                   ChequeNo,\n" +
                "                                   ChequeDate,\n" +
                "                                   BankId\n" +
                "                              FROM sqlitestudio_temp_table;\n" +
                "\n" +
                "DROP TABLE sqlitestudio_temp_table;\n" +
                "\n";


        String AdvancedDetails1 = "ALTER TABLE AdvancedDetails ADD COLUMN TotalPriceOfImportedSaplings FLOAT ";
        String AdvancedDetails2 = "ALTER TABLE AdvancedDetails ADD COLUMN TotalPriceOfIndigenousSaplings FLOAT ";
        String AdvancedDetails3 = "ALTER TABLE AdvancedDetails ADD COLUMN TotalSaplingsPrice FLOAT ";
        String AdvancedDetails4 = "ALTER TABLE AdvancedDetails ADD COLUMN SubsidyPriceForImportedSaplings FLOAT ";
        String AdvancedDetails5 = "ALTER TABLE AdvancedDetails ADD COLUMN SubsidyPriceForIndigenousSaplings FLOAT ";
        String AdvancedDetails6 = "ALTER TABLE AdvancedDetails ADD COLUMN SubsidyPrice FLOAT ";
        String plot1 = "ALTER TABLE Plot ADD COLUMN TotalAreaUnderHorticulture  FLOAT";
        String plot2 = "ALTER TABLE Plot ADD COLUMN LandTypeId  INT";
        String soil1 = "ALTER TABLE SoilResource ADD COLUMN IrrigatedArea   FLOAT";
        String soil2 = "ALTER TABLE SoilResource ADD COLUMN SoilNatureId  INT";


        try {
            db.execSQL(createAdvanceSample);
            db.execSQL(dropAdvance);
            db.execSQL(addDataToAdvance);
            db.execSQL(AdvancedDetails1);
            db.execSQL(AdvancedDetails2);
            db.execSQL(AdvancedDetails3);
            db.execSQL(AdvancedDetails4);
            db.execSQL(AdvancedDetails5);
            db.execSQL(AdvancedDetails6);
            db.execSQL(plot1);
            db.execSQL(plot2);
            db.execSQL(soil1);
            db.execSQL(soil2);


        } catch (SQLiteException se) {
            se.printStackTrace();
            Log.v("@@@response", "failed" + se.getMessage());

        }


    }


    public static void upgradeDb18(final SQLiteDatabase db) {


        checkTheColumnIsThere("PlotIrrigationTypeXref", "RecmIrrgId", "INT", db);
        checkTheColumnIsThere("InterCropPlantationXref", "RecmCropId", "INT", db);

        checkTheColumnIsThere("Nutrient", "PercTreesId", "INT", db);
        // checkTheColumnIsThere("Nutrient","IsControlMeasure","BIT",db);

        checkTheColumnIsThere(" pest", "PercTreesId", "INT", db);
        checkTheColumnIsThere("pest", "IsControlMeasure", "BIT", db);

        checkTheColumnIsThere("Disease", "IsControlMeasure", "BIT", db);
        checkTheColumnIsThere("Disease", "PercTreesId", "INT", db);

        checkTheColumnIsThere("Weed", "BasinHealthId", "INT", db);
        checkTheColumnIsThere("Weed", "PruningId", "INT", db);
        checkTheColumnIsThere("Weed", "WeedId", "INT", db);
        checkTheColumnIsThere("Weed", "WeevilsId", "INT", db);
        checkTheColumnIsThere("Weed", "InflorescenceId", "INT", db);

        checkTheColumnIsThere("HealthPlantation", "SpearleafId", "varchar (1)", db);
        checkTheColumnIsThere("HealthPlantation", "SpearLeafRating", "varchar (1)", db);
        checkTheColumnIsThere("HealthPlantation", "NutDefRating", "varchar (1)", db);
        checkTheColumnIsThere("HealthPlantation", "BasinHealthRating", "varchar (1)", db);
        checkTheColumnIsThere("HealthPlantation", "InflorescenceRating", "varchar (1)", db);
        checkTheColumnIsThere("HealthPlantation", "WeevilsRating", "varchar (1)", db);
        checkTheColumnIsThere("HealthPlantation", "PestRating", "varchar (1)", db);
        checkTheColumnIsThere("HealthPlantation", "DiseasesRating", "varchar (1)", db);

        checkTheColumnIsThere("AdvancedDetails", "TotalTransportationcost", "FLOAT", db);
        checkTheColumnIsThere("AdvancedDetails", "FarmerContributionTransportationcost", "FLOAT", db);
        checkTheColumnIsThere("AdvancedDetails", "SubsidyTransportationcost", "FLOAT", db);

    }

    public static void checkEveryTableAndColumn(final SQLiteDatabase db) {


        checkTheColumnIsThere("AdvancedDetails", "TotalPriceOfImportedSaplings", "FLOAT", db);
        checkTheColumnIsThere("AdvancedDetails", "TotalPriceOfIndigenousSaplings", "FLOAT", db);
        checkTheColumnIsThere("AdvancedDetails", "TotalSaplingsPrice", "FLOAT", db);
        checkTheColumnIsThere("AdvancedDetails", "SubsidyPriceForImportedSaplings", "FLOAT", db);
        checkTheColumnIsThere("AdvancedDetails", "SubsidyPriceForIndigenousSaplings", "FLOAT", db);
        checkTheColumnIsThere("AdvancedDetails", "SubsidyPrice", "FLOAT", db);
    }


    public static void upgradeDb19(final SQLiteDatabase db) {
        String createVisitRequests = "CREATE TABLE VisitRequests (\n" +
                "    RequestCode     VARCHAR,\n" +
                "    FarmerCode      VARCHAR,\n" +
                "    PlotCode        VARCHAR,\n" +
                "    CreatedDate     DATETIME,\n" +
                "    StatusTypeId    INT,\n" +
                "    IsFarmerRequest BIT,\n" +
                "    Comments        VARCHAR,\n" +
                "    IssueTypeId     INT,\n" +
                "    FileTypeId      INT,\n" +
                "    Url             VARCHAR\n" +
                ");\n";


        try {
            db.execSQL(createVisitRequests);
        } catch (SQLiteException se) {
            se.printStackTrace();
            Log.v("@@@response", "failed" + se.getMessage());

        }
    }

    public static void upgradeDb20(final SQLiteDatabase db) {
    }

    public static void upgradeDb21(final SQLiteDatabase db) {
        String createWhiteFlyAssessment = "CREATE TABLE WhiteFlyAssessment(\n" +
                "Id Integer primary key Autoincrement,\n" +
                "CropMaintenaceCode varchar(50) NOT NULL,\n" +
                "Question varchar(50) NOT NULL,\n" +
                "Answer varchar(50) NOT NULL,\n" +
                "Value varchar(1024) NOT NULL,\n" +
                "Year int NOT NULL,\n" +
                "IsActive bit NOT NULL,\n" +
                "CreatedByUserId int NOT NULL,\n" +
                "CreatedDate datetime NOT NULL,\n" +
                "UpdatedByUserId int NOT NULL,\n" +
                "UpdatedDate datetime NOT NULL,\n" +
                "ServerUpdatedStatus int NOT NULL)";


        String createYieldAssessment = "CREATE TABLE YieldAssessment(\n" +
                "Id Integer primary key Autoincrement,\n" +
                "CropMaintenaceCode varchar(50) NOT NULL,\n" +
                "Question varchar(50) NOT NULL,\n" +
                "Answer varchar(50) NOT NULL,\n" +
                "Value varchar(1024)  NOT NULL,\n" +
                "Year int NOT NULL,\n" +
                "IsActive bit NOT NULL,\n" +
                "CreatedByUserId int NOT NULL,\n" +
                "CreatedDate datetime NOT NULL,\n" +
                "UpdatedByUserId int NOT NULL,\n" +
                "UpdatedDate datetime NOT NULL,\n" +
                "ServerUpdatedStatus int NOT NULL)";
        String alterFertilizer = "ALTER TABLE Fertilizer ADD COLUMN SourceName VARCHAR (100)";

        try {
            db.execSQL(createYieldAssessment);
            db.execSQL(createWhiteFlyAssessment);
            db.execSQL(alterFertilizer);
        } catch (SQLiteException se) {
            se.printStackTrace();
            Log.v("@@@response", "failed" + se.getMessage());

        }
    }


    public static void upgradeDb22(final SQLiteDatabase db) {
        String createUserSync = "CREATE TABLE UserSync(\n" +
                "Id Integer primary key Autoincrement,\n" +
                "UserId int NOT NULL,\n" +
                "App varchar(50) NOT NULL,\n" +
                "Date datetime NOT NULL,\n" +
                "MasterSync bit NOT NULL,\n" +
                "TransactionSync bit NOT NULL,\n" +
                "ResetData bit NOT NULL,\n" +
                "IsActive bit NOT NULL,\n" +
                "CreatedByUserId int NOT NULL,\n" +
                "CreatedDate datetime NOT NULL,\n" +
                "UpdatedByUserId int NOT NULL,\n" +
                "ServerUpdatedStatus bit NOT NULL,\n" +
                "UpdatedDate datetime NOT NULL)\n";
        try {
            db.execSQL(createUserSync);

        } catch (SQLiteException se) {
            se.printStackTrace();
            Log.v("@@@response", "failed" + se.getMessage());

        }
    }

    public static void upgradeDb23(final SQLiteDatabase db) {
        String DropNurserySaplingDetails = "DROP TABLE NurserySaplingDetails";
        String Create_Table_NurserySaplingDetails = "CREATE TABLE NurserySaplingDetails(" +
                " Id                                  INTEGER       PRIMARY KEY AUTOINCREMENT ,\n" +
                " PlotCode                          VARCHAR(50)                     NOT NULL,\n" +
                " SaplingPickUpDate                 DATETIME                        NOT NULL,\n" +
                " NoOfSaplingsDispatched              INT                           NOT NULL,\n" +
                " NoOfImportedSaplingsDispatched      INT                           NOT NULL,\n" +
                " NoOfIndigenousSaplingsDispatched    INT                           NOT NULL,\n" +
                " ReceiptNumber                     VARCHAR(50)                     NOT NULL,\n" +
                " CreatedByUserId                     INT                           NOT NULL,\n" +
                " CreatedDate                       DATETIME                        NOT NULL,\n" +
                " UpdatedByUserId                     INT                           NOT NULL,\n" +
                " UpdatedDate                       DATETIME                        NOT NULL" + " );";

        String alterNurserySaplingTable = "ALTER TABLE NurserySaplingDetails ADD COLUMN NurseryId INT  ";
        String alterNurserySaplingTable1 = "ALTER TABLE NurserySaplingDetails ADD COLUMN CropVarietyId INT  ";
        String alterNurserySaplingTable2 = "ALTER TABLE NurserySaplingDetails ADD COLUMN SaplingSourceId INT  ";
        String alterNurserySaplingTable3 = "ALTER TABLE NurserySaplingDetails ADD COLUMN SaplingVendorId INT  ";
        String alterNurserySaplingTable4 = "ALTER TABLE NurserySaplingDetails ADD COLUMN BatchNo varchar(100)";
        String alterNurserySaplingTable5 = "ALTER TABLE NurserySaplingDetails ADD COLUMN PurchaseDate DATETIME ";


        try {
            db.execSQL(DropNurserySaplingDetails);
            db.execSQL(Create_Table_NurserySaplingDetails);
            db.execSQL(alterNurserySaplingTable);
            db.execSQL(alterNurserySaplingTable1);
            db.execSQL(alterNurserySaplingTable2);
            db.execSQL(alterNurserySaplingTable3);
            db.execSQL(alterNurserySaplingTable4);
            db.execSQL(alterNurserySaplingTable5);
        } catch (SQLiteException se) {
            se.printStackTrace();
            Log.v("@@@response", "failed" + se.getMessage());

        }
    }


    public static void upgradeDb24(final SQLiteDatabase db) {

        String d1 = "DROP TABLE WhiteFlyAssessment";
        String d2 = "DROP TABLE YieldAssessment";


        String alter1 = "CREATE TABLE WhiteFlyAssessment(\n" +
                "Id Integer primary key Autoincrement,\n" +
                "CropMaintenaceCode varchar(50) NOT NULL,\n" +
                "Question varchar(50) NOT NULL,\n" +
                "Answer varchar(50) NOT NULL,\n" +
                "Value varchar(1024) NOT NULL,\n" +
                "Year int NOT NULL,\n" +
                "IsActive bit NOT NULL,\n" +
                "CreatedByUserId int NOT NULL,\n" +
                "CreatedDate datetime NOT NULL,\n" +
                "UpdatedByUserId int NOT NULL,\n" +
                "UpdatedDate datetime NOT NULL,\n" +
                "ServerUpdatedStatus int NOT NULL)";


        String alter2 = "CREATE TABLE YieldAssessment(\n" +
                "Id Integer primary key Autoincrement,\n" +
                "CropMaintenaceCode varchar(50) NOT NULL,\n" +
                "Question varchar(50) NOT NULL,\n" +
                "Answer varchar(50) NOT NULL,\n" +
                "Value varchar(1024)  NOT NULL,\n" +
                "Year int NOT NULL,\n" +
                "IsActive bit NOT NULL,\n" +
                "CreatedByUserId int NOT NULL,\n" +
                "CreatedDate datetime NOT NULL,\n" +
                "UpdatedByUserId int NOT NULL,\n" +
                "UpdatedDate datetime NOT NULL,\n" +
                "ServerUpdatedStatus int NOT NULL)";


        try {
            db.execSQL(d1);
            db.execSQL(d2);
            db.execSQL(alter1);
            db.execSQL(alter2);
        } catch (SQLiteException se) {
            se.printStackTrace();
            Log.v("@@@response", "failed" + se.getMessage());

        }
    }

    public static void upgradeDb25(final SQLiteDatabase db) {


    }

    public static void upgradeDb26(final SQLiteDatabase db) {


        String alter0 = "ALTER TABLE Fertilizer ADD COLUMN IsFertilizerApplied int   ";
        String alter1 = "ALTER TABLE Fertilizer ADD COLUMN ApplicationYear int   ";
        String alter2 = "ALTER TABLE Fertilizer ADD COLUMN ApplicationMonth varchar(15)   ";
        String alter3 = "ALTER TABLE Fertilizer ADD COLUMN Quarter int   ";
        String alter4 = "ALTER TABLE Fertilizer ADD COLUMN ApplicationType Varchar(50)   ";
        String alter8 = "ALTER TABLE Fertilizer ADD COLUMN BioFertilizerId int ";

        String alter5 = "ALTER TABLE HealthPlantation ADD COLUMN NoOfFlorescene int   ";
        String alter6 = "ALTER TABLE HealthPlantation ADD COLUMN NoOfBuches int   ";
        String alter7 = "ALTER TABLE HealthPlantation ADD COLUMN BunchWeight int   ";


        try {
            db.execSQL(alter0);
            db.execSQL(alter1);
            db.execSQL(alter2);
            db.execSQL(alter3);
            db.execSQL(alter4);
            db.execSQL(alter5);
            db.execSQL(alter6);
            db.execSQL(alter7);
            db.execSQL(alter8);
        } catch (SQLiteException se) {
            se.printStackTrace();
            Log.v("@@@response", "failed" + se.getMessage());

        }
    }


    public static void upgradeDb27(final SQLiteDatabase db) {


        String createTransportServiceQuestioner = "CREATE TABLE TransportServiceQuestioner(\n" +
                "                Id Integer primary key Autoincrement,\n" +
                "                CropMaintenaceCode varchar(50) NOT NULL,\n" +
                "                 Question varchar(1024) NOT NULL,\n" +
                "               Value varchar(1024) NOT NULL,\n" +
                "                Year int NOT NULL,\n" +
                "                IsActive bit NOT NULL,\n" +
                "                CreatedByUserId int NOT NULL,\n" +
                "                CreatedDate datetime NOT NULL,\n" +
                "                UpdatedByUserId int NOT NULL,\n" +
                "                UpdatedDate datetime NOT NULL,\n" +
                "                ServerUpdatedStatus int NOT NULL)\n";


        try {

            db.execSQL(createTransportServiceQuestioner);
        } catch (SQLiteException se) {
            se.printStackTrace();
            Log.v("@@@response", "failed" + se.getMessage());

        }
    }

    private static void upgradeDb28( final SQLiteDatabase db) {
        Log.d(LOG_TAG, "******* upgradeDataBase 28 ******" + Palm3FoilDatabase.DATA_VERSION);


        String column4 = "ALTER TABLE CollectionCenter ADD COLUMN ReadMethod  Varchar(50)";
        String column3 = "ALTER TABLE CollectionCenter ADD COLUMN CollectionType Varchar(50)";
        String column5 = "Alter Table CollectionCenter Add NoOfChars Varchar(10) Not Null Default(0)";
        String column6 = "Alter Table CollectionCenter Add UpToCharacter Varchar(50) Not Null Default(0)";
        //String column7 = "Alter Table Plantation ADD COLUMN SaplingsPlanted int  ";

        try {
            db.execSQL(column3);
            db.execSQL(column4);
            db.execSQL(column5);
            db.execSQL(column6);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void upgradeDb29( final SQLiteDatabase db) {
        Log.d(LOG_TAG, "******* upgradeDataBase 29 ******" + Palm3FoilDatabase.DATA_VERSION);

        String column1 = "Alter Table Tablet Add IMEINumber2 Varchar(50)"; // Added by CIS DATE : 20/05/21

        try {
            db.execSQL(column1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void upgradeDb30( final SQLiteDatabase db) {
        Log.d(LOG_TAG, "******* upgradeDataBase 30 ******" + Palm3FoilDatabase.DATA_VERSION);

        String column1 = "Alter Table Plantation ADD COLUMN SaplingsPlanted int  ";
        String column2 = "Alter Table Pest ADD COLUMN RecommendedUOMId int";
        String column3 = "Alter Table Disease ADD COLUMN RecommendedUOMId int";
        String column4 = "Alter Table Nutrient ADD COLUMN RecommendedUOMId int";
        String column5 = "Alter Table Plantation Add MissingPlantsComments Varchar(500)";

        String HarvestorVisitHistory = "CREATE TABLE HarvestorVisitHistory(\n" +
                "                Id Integer primary key Autoincrement,\n" +
                "                Code varchar(60) NOT NULL,\n" +
                "                 Name  varchar(250) NOT NULL,\n" +
                "               PlotCode varchar(50) NOT NULL,\n" +
                "                CreatedByUserId int NOT NULL,\n" +
                "                CreatedDate datetime NOT NULL,\n" +
                "                ServerUpdatedStatus int NOT NULL)\n";

        String HarvestorVisitDetails = "CREATE TABLE HarvestorVisitDetails(\n" +
                "                Id Integer primary key Autoincrement,\n" +
                "                HarvestorVisitCode  varchar(60) NOT NULL,\n" +
                "                HarvestorTypeId int,\n" +
                "                HarvestorCode varchar(50),\n" +
                "                HasPole bit NOT NULL ,\n" +
                "                TonnageCost FLOAT NOT NULL ,\n" +
                "                Interval int NOT NULL ,\n" +
                "                Quantity FLOAT NOT NULL ,\n" +
                "                CCCode varchar(10) NOT NULL,\n" +
                "                UnRipen FLOAT ,\n" +
                "                UnderRipe FLOAT ,\n" +
                "                Ripen FLOAT ,\n" +
                "                OverRipe FLOAT ,\n" +
                "                Diseased FLOAT ,\n" +
                "                EmptyBunches FLOAT ,\n" +
                "                FFBQualityLong FLOAT,\n" +
                "                FFBQualityMedium FLOAT,\n" +
                "                FFBQualityShort FLOAT,\n" +
                "                FFBQualityOptimum FLOAT,\n" +
                "                FarmerAvailable bit NOT NULL,\n"+
                "                DetailesInformed bit,\n"+
                "                Name  varchar(150) ,\n" +
                "                MobileNumber  varchar(15) ,\n" +
                "                Village varchar(50) ,\n" +
                "                Mandal varchar(50) ,\n" +
                "                CreatedByUserId int NOT NULL,\n" +
                "                CreatedDate datetime NOT NULL,\n" +
                "                ServerUpdatedStatus bit NOT NULL,\n" +
                "                LooseFruit  bit,\n" +
                "                LooseFruitWeight int)\n";

        String Harvestor = "CREATE TABLE Harvestor\n" +
                "(\n" +
                "Id Integer primary key Autoincrement,\n" +
                "Code VARCHAR(50) UNIQUE NOT NULL,\n" +
                "Name VARCHAR(150) NOT NULL,\n" +
                "MobileNumber VARCHAR(15) NOT NULL,\n" +
                "Village VARCHAR(50) NOT NULL,\n" +
                "Mandal VARCHAR(50) NOT NULL,\n" +
                "IsActive BIT DEFAULT 1,\n" +
                "CreatedByUserId INT NOT NULL,\n" +
                "CreatedDate DATETIME NOT NULL,\n" +
                "UpdatedByUserId INT NOT NULL,\n" +
                "UpdatedDate DATETIME NOT NULL\n" +
                ")";
        try {

            db.execSQL(column1);
            db.execSQL(column2);
            db.execSQL(column3);
            db.execSQL(column4);
            db.execSQL(column5);
            db.execSQL(Harvestor);
            db.execSQL(HarvestorVisitHistory);
            db.execSQL(HarvestorVisitDetails);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Added on 25th Nov 2021
    private static void upgradeDb31( final SQLiteDatabase db) {
        Log.d(LOG_TAG, "******* upgradeDataBase 31 ******" + Palm3FoilDatabase.DATA_VERSION);

        String column1 = "Alter Table Farmer Add FileName Varchar(100)";
        String column2 = "Alter Table Farmer Add FileLocation Varchar(250)";
        String column3 = "Alter Table Farmer Add FileExtension Varchar(25)";
        String column4 = "Alter Table Farmer Add ByteImage Varchar";

        String dropusertargetstable = "DROP TABLE UserTarget";
        String dropusermonthlytargetTable = "DROP TABLE UserMonthlyTarget";

        String CREATE_USER_TARGETT = "CREATE TABLE UserTarget(\n" +
                "UserId INT ,\n" +
                "FinancialYear Varchar ,\n" +

                "KRAName VARCHAR ,\n" +
                "KRACode VARCHAR ,\n" +
                "UOM VARCHAR ,\n" +
                "AnnualTarget Float ,\n" +
                "AchievedTarget Float \n" +
                ")";

        String CREATE_USER_MONTHLY_TARGETT = "CREATE TABLE UserMonthlyTarget(\n" +
                "UserId INT ,\n" +
                "FinancialYear Varchar ,\n" +
                "KRAName VARCHAR ,\n" +
                "KRACode VARCHAR ,\n" +
                "UOM VARCHAR ,\n" +
                "MonthNumber INT NOT NULL,\n" +
                "NameOfMonth VARCHAR ,\n" +
                "MonthlyTarget Float ,\n" +
                "AchievedTarget Float \n" +
                ")";

        String PlotFFBDetails = "CREATE TABLE PlotFFBDetails(\n" +
                "Code Varchar(50) ,\n" +
                "YieldPerHectare Float ,\n" +
                "ExpectedYieldPerHectare Float \n" +
                ")";

        String PlotGradingDetails = "CREATE TABLE PlotGradingDetails(\n" +
                "PlotCode Varchar(50) ,\n" +
                "Ripen Float ,\n" +
                "UnderRipe Float ,\n" +
                "UnRipen Float ,\n" +
                "OverRipe Float ,\n" +
                "Diseased Float ,\n" +
                "EmptyBunches Float \n" +
                ")";


        try {

            db.execSQL(column1);
            db.execSQL(column2);
            db.execSQL(column3);
            db.execSQL(column4);
            db.execSQL(dropusertargetstable);
            db.execSQL(dropusermonthlytargetTable);
            db.execSQL(CREATE_USER_TARGETT);
            db.execSQL(CREATE_USER_MONTHLY_TARGETT);
            db.execSQL(PlotFFBDetails);
            db.execSQL(PlotGradingDetails);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkTheColumnIsThere(String tableName, String columnName, String dataType, final SQLiteDatabase db) {

        boolean isThere = false;
        String query = "PRAGMA table_info(" + tableName + ");";
        try (Cursor cursor = db.rawQuery(query, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));

                    if (name.equals(columnName)) {
                        isThere = true;
                    }

                } while (cursor.moveToNext());


                if (!isThere) {
                    db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + dataType);
                    Log.v(LOG_TAG, "@@@ added the column " + columnName);
                }
            }


        } catch (Exception e) {
            Log.v(LOG_TAG, "@@@ checking the column " + e.getMessage());
        }
    }

}
