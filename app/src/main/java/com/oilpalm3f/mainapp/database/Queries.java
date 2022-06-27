package com.oilpalm3f.mainapp.database;


import com.oilpalm3f.mainapp.common.CommonConstants;

//Queries can be written here
public class Queries {

    private static Queries instance;
    private String isActive;

    public static Queries getInstance() {
        if (instance == null) {
            instance = new Queries();
        }
        return instance;
    }

    public static String getCropId(){
        return "SELECT Id FROM LookUp WHERE Name ='Oil Palm' AND LookUpTypeId = '22'";
    }

    public static String getVillageName(String farmerCode){
        return "Select Name from Village V\n" +
                "inner join Address A on V.Id=A.VillageId\n" +
                "inner join Plot P on A.Code=P.AddressCode\n" +
                "where P.FarmerCode='"+farmerCode+"'\n" +
                "group by V.Name";
    }

   public  String getActiveHarvestors(){
        return  "SELECT Id,Code, Name, MobileNumber, Village,Mandal from Harvestor where IsActive = 'true'";
   }

    public  String lastHarvestedDate(String plotcode){
        return  "Select CreatedDate from HarvestorVisitHistory where PlotCode = '"+plotcode+"'  Order by CreatedDate DESC LIMIT 1";
    }


    public String getCollectionCenterMaster() {
        return "select Code, Name  from CollectionCenter ORDER BY Name Asc";
    }

    public String getCollectionCenterbyuser(String userid) {
//        return "select c.Code, c.Name from CollectionCenter  c      \n" +
//                "    inner join Village v on c.VillageId = v.Id\n" +
//                "   where  c.IsMill = 'false' AND c.VillageId IN (select villageId from UserVillageXref where userId IN ('"+userid+"')) and c.IsActive = 'true' ORDER BY c.Name Asc";
        return "select c.Code, c.Name from CollectionCenter  c  \n" +
        "inner join District d on d.Id = c.DistrictId \n" +
        "where \n" +
        "c.DistrictId IN \n" +
        "(select distinct d.Id from UserVillageXref x \n" +
        "inner join Village v on x.VillageId = v.Id \n" +
        "inner join Mandal m on m.Id = v.MandalId \n" +
        "inner join District d on d.Id = m.DistrictId \n" +
        "where x.userId IN ('"+userid+"')) and c.IsActive = 'true' \n" +
       "ORDER BY c.Name Asc";

    }


    public static String getAlertsPlotFollowUpQuery(int limit, int offset) {
        return "select p.Code,p.FarmerCode,f.FirstName,f.MiddleName,f.LastName,f.ContactNumber,\n" +
                "  m.Name as MandalName,\n" +
                "  v.Name as VillageName,\n" +
                "  p.TotalPlotArea, fu.PotentialScore,\n" +
                " (select GROUP_CONCAT(lkp.Name) from PlotCurrentCrop pcc\n" +
                "  inner join LookUp lkp on pcc.CropId =lkp.Id  where PlotCode = p.Code and lkp.LookUpTypeId = '22')  as Crops,\n" +
                "  fu.CreatedDate as lastVisitDate, fu.HarvestingMonth as HarvestDate,\n" +
                " (select tcd.desc from SoilResource sr inner join TypeCdDmt tcd on sr.PrioritizationTypeId = tcd.TypeCdId where PlotCode = p.Code) as plotPrioritization,\n" +
                "  ui.FirstName || ' ' || (CASE WHEN ifnull(ui.MiddleName, '') = 'null' THEN '' ELSE ui.MiddleName || ' ' END) || ui.LastName AS 'UserName'\n"+
                "  from Plot p\n" +
                "  inner join UserInfo ui on ui.id =p.CreatedByUserId\n"+
                "  inner join Farmer f on f.code=p.FarmerCode\n" +
                "  inner join FollowUp fu on fu.PlotCode = p.Code\n" +
                "  inner join Address addr on p.AddressCode = addr.Code\n" +
                "  inner join Village v on addr.VillageId = v.Id\n" +
                "  inner join Mandal m on addr.MandalId = m.Id\n" +
                "  inner join District d on addr.DistrictId = d.Id\n" +
                "  inner join State s on addr.StateId = s.Id\n" +
                "  inner join FarmerHistory fh on fh.PlotCode = p.Code AND p.FarmerCode = fh.FarmerCode AND fh.IsActive = 1\n" +
                "  where  fu.PotentialScore>=7 order by lastVisitDate limit " + limit + " offset " + offset + ";";
    }

    public static String getAlertsCount(int type) {
        return "select count(*) from Alerts where AlertType = " + type + "";
    }



    public static String getAlertsMissingTreesInfoQuery(int limit, int offset) {
        return "  select p.Code,p.FarmerCode,f.FirstName,f.MiddleName,f.LastName,\n" +
                " m.Name as MandalName,\n" +
                " v.Name as VillageName, plt.TreesCount as saplingsplanted, up.PlamsCount as currentTrees, up.MissingTreesCount as missingTrees,\n" +
                " (select ROUND(MissingTreesCount * 100.0 / SeedsPlanted, 0) from Uprootment where PlotCode =p.Code ORDER BY CreatedDate DESC LIMIT 1) as percent\n" +
                "  from Plot p\n" +
                "  inner join Farmer f on f.code=p.FarmerCode\n" +
                "  inner join Uprootment up on up.PlotCode = p.Code\n" +
                "  inner join Plantation plt on plt.PlotCode = p.Code\n" +
                "  inner join Address addr on p.AddressCode = addr.Code\n" +
                "  inner join Village v on addr.VillageId = v.Id\n" +
                "  inner join Mandal m on addr.MandalId = m.Id\n" +
                "  inner join District d on addr.DistrictId = d.Id\n" +
                "  inner join State s on addr.StateId = s.Id\n" +
                "  inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
                "  where   fh.IsActive = 1 and up.MissingTreesCount >= 1 group by p.Code order by percent desc limit " + limit + " offset " + offset + ";";
    }



    public static String getAlertsVisitsInfoQuery(int limit, int offset) {

        return "select p.Code,p.FarmerCode,f.FirstName,f.MiddleName,f.LastName,f.ContactNumber,\n" +
                " m.Name as MandalName,\n" +
                " v.Name as VillageName,\n" +
                " p.TotalPlotArea, p.DateofPlanting, cmh.CreatedDate as plotvisiteddate, fh.CreatedDate as converteddate\n" +
                " from Plot p\n" +
                " inner join Farmer f on f.code=p.FarmerCode\n" +
                " inner join Address addr on p.AddressCode = addr.Code\n" +
                " inner join Village v on addr.VillageId = v.Id\n" +
                " inner join Mandal m on addr.MandalId = m.Id\n" +
                " inner join District d on addr.DistrictId = d.Id\n" +
                " inner join State s on addr.StateId = s.Id\n" +
                " inner join FarmerHistory fh on fh.PlotCode = p.Code \n" +
                " inner join CropMaintenanceHistory cmh on cmh.PlotCode = p.Code\n" +
                " where  fh.IsActive = 1 and fh.StatusTypeId in(85,88,89) order by converteddate limit " + limit + " offset " + offset + ";";
    }

    public static String getAlertsNotVisitsInfoQuery(int limit, int offset,String fromDate,String toDate) {

        return "select p.Code,p.FarmerCode,f.FirstName,f.MiddleName,f.LastName,f.ContactNumber,\n" +
                "     m.Name as MandalName,\n" +
                "     v.Name as VillageName,\n" +
                "     p.TotalPlotArea, p.DateofPlanting, cm.UpdatedDate as plotvisiteddate, fh.CreatedDate as converteddate\n" +
                "     from Plot p\n" +
                "     inner join Farmer f on f.code=p.FarmerCode\n" +
                "     inner join Address addr on p.AddressCode = addr.Code\n" +
                "     inner join Village v on addr.VillageId = v.Id\n" +
                "     inner join Mandal m on addr.MandalId = m.Id\n" +
                "     inner join District d on addr.DistrictId = d.Id\n" +
                "     inner join State s on addr.StateId = s.Id\n" +
                "     inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
                "     LEFT JOIN (SELECT PlotCode,MAX(UpdatedDate)UpdatedDate from CropMaintenanceHistory GROUP BY PlotCode)cm ON cm.PlotCode=p.Code\n" +
                "     where  fh.IsActive = 1 and fh.StatusTypeId in(88,89)  and not exists (select 1 from CropMaintenanceHistory  where DATE(UpdatedDate) BETWEEN '"+fromDate+"' and '"+toDate+"' and PlotCode=p.code )   order by converteddate limit 30 offset 0";
    }

    public String getStatesMasterQuery() {
        return "select Id, Code, Name from State";
    }

    public String getStatesQuery() {
        return "SELECT s.Id," +
                "  s.Code," +
                "  s.Name," +
                "  s.CountryId," +
                "  c.Code AS CountryCode," +
                "  c.Name AS CountryName" +
                "FROM State  s" +
                "INNER JOIN Country c ON c.Id = s.CountryId";
    }

    public String getDistrictQuery(final String stateId) {
        return "select Id, Code, Name from District where StateId IN (Select Id from State where Id = '" + stateId + "'" + ")";
    }

    public String getMandalsQuery(final String DistrictId) {
        return "select Id, Code, Name from Mandal where DistrictId IN (Select Id from District where Id = '" + DistrictId + "'" + ")";
    }

    public String getVillagesQuery(final String mandalId) {
        return "select Id, Code, Name from Village where MandalId IN (Select Id from Mandal where Id = '" + mandalId + "'" + ")";
    }



    public String getUOM() {
        return "select Id,Name from UOM";
    }
    public String getUOMper() {
        return "select Id,Name from UOM where Id IN(6,8)";
    }

    public String getSaplingsNursery() {
        return "select Id, Name from Nursery where IsActive = 'true'";
    }


    public String getMaxNumberQuery(final String financalYrDay) {
        return "SELECT max(substr(Code, length(Code) - 2,length(Code))) as Maxnumber FROM Farmer  where substr(Code,9,5) = "+"'"+financalYrDay+"'";
    }
    public String getMaxNumberQueryForHarvesting() {
        return "SELECT max(substr(Code, length(Code) - 2,length(Code))) as Maxnumber FROM HarvestorVisitHistory";
    }


    public String getMaxNumberForPlotQuery(final String financalYrDay) {
        return "SELECT  max(substr(Plot.Code, length(Plot.Code) - 2,length(Plot.Code))) as Maxnumber\n" +
                " FROM Plot WHERE   substr(Plot.Code,7,5) = '" + financalYrDay + "'";
    }



    public String getCastesQuery() {
        return "select CastId,CastName from CastMaster";
    }

    public String getsource_of_contactQuery() {
        return "SELECT Id,NAme FROM LookUp where LookUpTypeId = '13' and isActive ='true'";
    }

    public String getharvestingtypesQuery() {
        return "SELECT TypeCdId,Desc FROM TypeCdDmt where ClassTypeId= '67' and isActive ='true'";
    }

    public String gettitleQuery() {
        return "SELECT Typecdid,desc FROM typecddmt where classtypeid= '6' and isActive ='true'";
    }

    public String getgenderQuery() {
        return "SELECT Typecdid,desc FROM typecddmt where classtypeid= '7' and isActive ='true'";
    }

    public String getcastQuery() {
        return "SELECT Typecdid,desc FROM typecddmt where classtypeid= '8' and isActive ='true'";
    }

    public String getVehicleTypeQuery() {
        return "SELECT Typecdid,desc FROM typecddmt where classtypeid= '19' and isActive ='true'";
    }

    public String getTypeCdDmtData(String classTypeId) {
        return "SELECT Typecdid,desc FROM typecddmt where classtypeid= '" + classTypeId + "' and isActive ='true' ORDER BY desc  Asc";
    }

    public String getTypeCdDmtComplaintsTypeData(String classTypeId) {
        return "SELECT Typecdid,desc FROM typecddmt where classtypeid= '" + classTypeId + "' and isActive ='true'";
    }
    public String getCloseDoneStatus(){
        return "SELECT Typecdid,desc FROM typecddmt where classtypeid=40 and  TypeCdId in (202) and isActive ='true'";
    }

    public String getYesNo(){
        return "SELECT '1' key,'Yes' value UNION ALL SELECT '0','No'";
    }

    public String getFertilizerPrevQtrCM(int Qtr,int Year,String PlotCode,String min,String max){
        //return "SELECT CropMaintenanceCode FROM Fertilizer WHERE PlotCode='"+PlotCode+"' AND Quarter="+String.valueOf(Qtr)+" AND ApplicationYear="+String.valueOf(Year)+" Order BY CreatedDate DESC LIMIT 1";
        return "SELECT CropMaintenanceCode FROM Fertilizer WHERE PlotCode='"+PlotCode+"' AND lastapplieddate between '"+min+"' and '"+max+"' Order BY CreatedDate DESC LIMIT 1";
    }
    public String getFertilizerQtrCMCnt(int Qtr,int Year,String PlotCode,String min,String max){
        return "SELECT COUNT(*) FROM Fertilizer WHERE PlotCode='"+PlotCode+"' AND lastapplieddate between '"+min+"' and '"+max+"'  Order BY CreatedDate DESC LIMIT 1";
    }
    public String getFertilizerPrevQtrDosage(int Qtr,int Year,String Code,int Id,String min,String max){
        return "SELECT Dosage FROM Fertilizer WHERE CropMaintenanceCode='"+Code+"' AND lastapplieddate between '"+min+"' and '"+max+"'  AND FertilizerId="+String.valueOf(Id)+" Order BY CreatedDate DESC LIMIT 1";
    }
    public String getBioFertilizerPrevQtrDosage(int Qtr,int Year,String Code,int Id,int BId,String min,String max){
        return "SELECT Dosage FROM Fertilizer WHERE CropMaintenanceCode='"+Code+"' AND lastapplieddate between '"+min+"' and '"+max+"'  AND FertilizerId="+String.valueOf(Id)+" AND BioFertilizerId="+String.valueOf(BId)+" Order BY CreatedDate DESC LIMIT 1";
    }
    public String getFertilizerPrevQtrdtls(int Qtr,int Year,String Code,String min,String max){
        return "select SourceName,ApplicationYear,ApplicationMonth,ApplicationType,Comments,FertilizerSourceTypeId from Fertilizer f INNER JOIN TypeCdDmt l ON f.FertilizerSourceTypeId=l.TypeCdId WHERE f.CropMaintenanceCode='"+Code+"' AND lastapplieddate between '"+min+"' and '"+max+"'  Order BY f.CreatedDate DESC LIMIT 1";
    }

    public String deleteTableData() {
        return "delete from %s";
    }




    public String marketSurveyDataCheck(String farmerCode) {
        return "select * from MarketSurveyAndReferrals where FarmerCode = '" + farmerCode + "'";
    }



    //********************* REFRESH QUERIES****************************************************************************************

    public String getRefreshCountQuery(String tablename) {
        return "select count(0) from " + tablename + " where ServerUpdatedStatus='0'";
    }



    public String getRefreshCountQueryForFileRepo() {
        return "select count(0) from FileRepository where ServerUpdatedStatus='0'";
    }

    public String getPincode(String villageid) {
        return "select PinCode from Village where Id ='" + villageid + "'";
    }

    //*****************************************************END OF REFRESH QUERIES************************************************************


    public String getComplaintRefreshQueries() {
        return "select ComplaintId,FarmerCode,PlotCode,NatureofComplaint,DegreeOfComplaint,Comments,Status,Resolution,ResolvedBy," +
                "FollowupRequired,NextFollowupDate,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from ComplaintDetails where ServerUpdatedStatus=0";
    }


    public String updateServerUpdatedStatus() {
        return "update %s set ServerUpdatedStatus = '1' where ServerUpdatedStatus = '0'";
    }

    public String getPlotExistanceInAnyPalmDetailsQuery(final String plotCode) {
        return "select COUNT(*) " +
                "from (" +
                "    select PlotCode from CropInfo" +
                "    union all" +
                "    select PlotCode from CropMaintenance" +
                "    union all" +
                "    select PlotCode from FFB_HarvestDetails" +
                "    union all" +
                "    select PlotCode from HealthofPlantationDetails" +
                "    union all" +
                "    select PlotCode from InterCropDetails" +
                "    union all" +
                "    select PlotCode from NeighboringPlot" +
                "    union all" +
                "    select PlotCode from PlantProtectionDetails" +
                "     union all" +
                "    select PlotCode from UprootmentDetails" +
                "     union all" +
                "    select PlotCode from ComplaintDetails" +
                ") a" +
                "where PlotCode = '" + plotCode + "'";
    }





    public String getImageDetails() {
        return "select FarmerCode, PlotCode, ModuleTypeId, FileLocation from FileRepository where ServerUpdatedStatus = '0'";
    }

    public String updatedImageDetailsStatus(String code, final String farmerCode, int moduleId) {
        return "update PictureReporting set ServerUpdatedStatus = 'true' where Code = '" + code + "' and FarmerCode ='" + farmerCode + "'" + " and ModuleId = " + moduleId;
    }



    public String updatedConsignmentDetailsStatus(final String consignmentCode) {
        return "update Consignment set ServerUpdatedStatus = 'true' where Code = " + consignmentCode;
    }

    public String updatedCollectionPlotXRefDetailsStatus(final String collectionCode, String plotCode) {
        return "update CollectionPlotXref set ServerUpdatedStatus = 'true' where CollectionCode ='" + collectionCode + "' and PlotCode ='" + plotCode + "'";
    }

    public String updatedCollectionDetailsStatus(final String collectionCode) {
        return "update Collection set ServerUpdatedStatus = 'true' where Code = " + collectionCode;
    }




    public String queryToFindJunkData() {
        return "SELECT DISTINCT(src.PlotCode) as PlotCode, src.FarmerCode from %s src " +
                "LEFT JOIN LandDetails l" +
                "on l.PlotCode = src.PlotCode " +
                "where l.PlotCode IS NULL";
    }

    public String deleteInCompleteData() {
        return "delete from %s where PlotCode IN (" + "%s" + ")";
    }



    public String queryToGetIncompleteMarketSurveyData() {
        return "SELECT src.FarmerCode from MarketSurveyAndReferrals src " +
                "LEFT JOIN FarmerDetails l" +
                "on src.FarmerCode = l.FarmerCode" +
                "where l.FarmerCode IS NULL";
    }

    public String deleteInCompleteMarketSurveyData() {
        return "delete from %s where FarmerCode IN (" + "%s" + ")";
    }


    //*****water,power,soil Queries************//
    public String getTypeofIrrigationQuery() {
        return "";
    }

    public String getplotPrioritizationQuery() {
        return "";
    }

    public String getSoilTypeQuery() {
        return "";
    }


    public String getTypeOfPowerQuery() {
        return "";
    }



    /* Query for getting farmers data  */

    public String getFarmersDataForWithOffsetLimit(String seachKey, int offset, int limit) {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.GuardianName,\n" +
                "s.Name as StateName,\n" +
                "f.ContactNumber, f.MobileNumber, v.Name, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension \n" +
                "from Farmer f \n" +
                "left join Village v on f.VillageId = v.Id\n" +
                "left join State s on f.StateId = s.Id\n" +
                "left join FileRepository fileRep on f.Code = fileRep.FarmerCode\n" +
                "and fileRep.ModuleTypeId = 193 \n" +
                "where  IFNULL(f.InactivatedReasonTypeId,0) != 319 AND (f.FirstName like '%" + seachKey + "%' or f.MiddleName like '%" + seachKey + "%' or f.LastName like '%" + seachKey + "%' or f.Code like '%" + seachKey + "%' \n" +
                "or f.ContactNumber like '%" + seachKey + "%' or f.GuardianName like '%" + seachKey + "%') group by f.Code limit " + limit + " offset " + offset + ";";
    }

    public String getPlotDetailsForConversion(final String farmercode, final int plotStatus) {

        return "select p.Code, p.TotalPalmArea, p.TotalPlotArea, p.GPSPlotArea, p.SurveyNumber, addr.Landmark,\n" +
                "v.Code AS VillageCode, v.Name as VillageName, v.Id as VillageId,\n" +
                "m.Code as MandalCode, m.Name as MandalName, m.Id as MandalId,\n" +
                "d.Code as DistrictCode, d.Name as DistrictName, d.Id as DistrictId,\n" +
                "s.Code as StateCode, s.Name as StateName, s.Id as StateId,\n" +
                "  ASD.NoOfSaplingsAdvancePaidFor as advanced , ND.NoOfSaplingsDispatched  as nursery \n" +
                " from Plot p\n" +
                "inner join Address addr on p.AddressCode = addr.Code\n" +
                "inner join Village v on addr.VillageId = v.Id\n" +
                "inner join Mandal m on addr.MandalId = m.Id\n" +
                "inner join District d on addr.DistrictId = d.Id\n" +
                "inner join State s on addr.StateId = s.Id\n" +
                "inner join FarmerHistory fh on fh.PlotCode = p.Code \n" +
                " INNER JOIN AdvanceSummary ASD on ASD.PlotCode = P.Code\n" +
                " INNER JOIN NurserySummary ND on ND.PlotCode = P.Code\n" +
                "where p.IsActive = 1 and  p.FarmerCode='" + farmercode + "'" +"and fh.StatusTypeId = '" + plotStatus + "'" + " and fh.IsActive = 1  group by p.Code HAVING advanced = nursery ";
    }

    public String getPlotDetailsForVisit(final String farmercode) {

        return "select p.Code, p.TotalPalmArea, p.TotalPlotArea, p.GPSPlotArea, p.SurveyNumber, addr.Landmark,\n" +
                "v.Code AS VillageCode, v.Name as VillageName, v.Id as VillageId,\n" +
                "m.Code as MandalCode, m.Name as MandalName, m.Id as MandalId,\n" +
                "d.Code as DistrictCode, d.Name as DistrictName, d.Id as DistrictId,\n" +
                "s.Code as StateCode, s.Name as StateName, s.Id as StateId , p.DateofPlanting from Plot p\n" +
                "inner join Address addr on p.AddressCode = addr.Code\n" +
                "inner join Village v on addr.VillageId = v.Id\n" +
                "inner join Mandal m on addr.MandalId = m.Id\n" +
                "inner join District d on addr.DistrictId = d.Id\n" +
                "inner join State s on addr.StateId = s.Id\n" +
                "inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
                "inner join VisitRequests vr on vr.PlotCode = p.Code\n" +
                "left join (select plotcode,max(CreatedDate)CreatedDate from CropMaintenanceHistory group by plotcode) cm on vr.PlotCode = cm.PlotCode "+
                "where p.IsActive = 1  and (cm.PlotCode is null or (cm.PlotCode is not null and  DATE(vr.CreatedDate)>DATE(cm.CreatedDate ))) and p.FarmerCode='" + farmercode + "' and fh.StatusTypeId IN ('88','89','308') and fh.IsActive = 1  group by p.Code";
    }

    public String getPlotDetailsForCC(final String farmercode, final int plotStatus, final int multiStatus, boolean fromCm) {
        String statusType = "";
        if (fromCm) {
            statusType = "and fh.StatusTypeId IN ('" + plotStatus + "','" + multiStatus + "','308' )";
        } else {
            statusType = "and fh.StatusTypeId = '" + plotStatus + "'";
        }
        return "select p.Code, p.TotalPalmArea, p.TotalPlotArea, p.GPSPlotArea, p.SurveyNumber, addr.Landmark,\n" +
                "v.Code AS VillageCode, v.Name as VillageName, v.Id as VillageId,\n" +
                "m.Code as MandalCode, m.Name as MandalName, m.Id as MandalId,\n" +
                "d.Code as DistrictCode, d.Name as DistrictName, d.Id as DistrictId,\n" +
                "s.Code as StateCode, s.Name as StateName, s.Id as StateId , p.DateofPlanting from Plot p\n" +
                "inner join Address addr on p.AddressCode = addr.Code\n" +
                "inner join Village v on addr.VillageId = v.Id\n" +
                "inner join Mandal m on addr.MandalId = m.Id\n" +
                "inner join District d on addr.DistrictId = d.Id\n" +
                "inner join State s on addr.StateId = s.Id\n" +
                "inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
                "where p.IsActive = 1 and p.FarmerCode='" + farmercode + "'" + statusType + " and fh.IsActive = 1  group by p.Code";
    }

    public String getPlotDetailsForCC(final String farmercode, final int plotStatus) {
        return getPlotDetailsForCC(farmercode, plotStatus, 0, false);
    }

    public String getComplaintsDataByPlot(String plotcode, String farmerCode) {
        return "SELECT cx.ComplaintCode,cx.ComplaintTypeId,csh.AssigntoUserId,csh.StatusTypeId,c.CriticalityByTypeId,c.createddate ,p.code," +
                "(select firstname from farmer where code = '" + farmerCode + "') as fname," +
                "(select lastname from farmer where code = '" + farmerCode + "') as lname," +
                "(select villageid from farmer where code = '" + farmerCode + "') as vcode\n" +
                "from Complaints c \n" +
                "inner join \n" +
                "ComplaintTypeXref cx on c.code=cx.ComplaintCode " +
                "inner join" +
                " ComplaintStatusHistory csh on csh.ComplaintCode = c.Code  " +
                "inner join plot p on p.code =c.plotcode  " +
                "where c.plotcode = '" + plotcode + "'" + " group by cx.ComplaintCode";
    }

    public String getUserVillageIds(final String userId) {
        return "select DISTINCT(villageId) from UserVillageXref where userId IN (" + userId + ")";
    }

    public String getUserMandalIds(final String villageIds) {
        return "select DISTINCT(MandalId) from Village where Id IN (" + villageIds + ")";
    }

    public String getUserDistrictIds(final String mandalIds) {
        return "select DISTINCT(DistrictId) from Mandal where Id IN (" + mandalIds + ")";
    }

    public String getUserStateIds(final String DistrictIds) {
        return "select DISTINCT(StateId) from District where Id IN (" + DistrictIds + ")";
    }

    public String getTabId(final String imieiNumber) {

//        return "select Name from Tablet where IMEINumber = '" + imieiNumber + "'";
        // Update For Check IME number in 2 COlumns #### CIS ## 21/05/2021
        return "select Name from Tablet where ((IMEINumber = '" + imieiNumber + "') OR (IMEINumber2 = '" + imieiNumber + "'))";
    }

    public String getUserDetailsNewQuery(String imeiNumber) {
//        return "SELECT u.UserId, u.UserName, u.Password, u.RoleId, u.ManagerId, u.Id, u.FirstName, t.Name, u.UserCode \n" +
//                " from Tablet t\n" +
//                " inner join UserInfo u on u.TabletId = t.Id\n" +
//                " where t.IMEINumber = '"+ imeiNumber +"' and u.IsActive = 'true' ";
    // Added For Check Ime number in 2 Columns ### CIS ## 21/05/21
       return  "SELECT u.UserId, u.UserName, u.Password, u.RoleId, u.ManagerId, u.Id, u.FirstName, t.Name, u.UserCode \n" +
                " from Tablet t\n" +
                " inner join UserInfo u on u.TabletId = t.Id\n" +
                " where (t.IMEINumber = '"+ imeiNumber +"'  OR  t.IMEINumber2 = '"+ imeiNumber +"') and u.IsActive = 'true' ";
    }

    public String getUserDetailsForKrasQuery(final int managerId) {
        return "SELECT u.UserId, u.UserName, u.Password, u.RoleId, u.ManagerId, u.Id, u.FirstName, t.Name, u.UserCode \n" +
                " from Tablet t\n" +
                " inner join UserInfo u on u.TabletId = t.Id\n" +
                " where ManagerId = '" + managerId + "' OR u.Id = '"+ managerId +"'";
    }


    public String getusernamequery(final String userid) {
        return "select UserName from UserInfo where Id='"+ userid +"'";
    }

    public String getCropsMasterInfo() {
        return "select Id, name from LookUp where LookupTypeId = '22'";
    }

    public String getSourceOfWaterInfo() {
        return "select Id, name from LookUp where LookupTypeId = '12'";
    }

    public String getPlotOwnerShip() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = 11";
    }

    public String getAnualIncome() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = 9";
    }

    public String gePlotCareTakerfromDB(String plotcode) {
        return "select IsPlotHandledByCareTaker from Plot where Code ='" + plotcode + "'";
    }

    public String getSelectedFarmer(final String farmerCode) {
        return "select * from Farmer where Code = '" + farmerCode + "'";
    }

    public String getSelectedPlot(final String plotCode) {
        return "select * from Plot where Code = '" + plotCode + "'";
    }

    //*************************Refresh Queries****************************//

    public String getSelectedFarmerAddress(final String addressCode) {
        return "select * from Address where Code = '" + addressCode + "'";
    }

    public String getSelectedPlotAddress(final String addressCode) {
        return "select * from Address where Code = '" + addressCode + "'";
    }

    public String getSelectedPlotCurrentCrop(final String plotCode) {
        return "select * from PlotCurrentCrop  where PlotCode = '" + plotCode + "'";
    }

    public String getSelectedNeighbourPlot(final String plotCode) {
        return "select * from NeighbourPlot where PlotCode = '" + plotCode + "'";
    }

    public String getCodeFromId(String tableName, String Id) {
        return "select Code from " + tableName + " where Id = '" + Id + "'";
    }

    //*************************Refresh Queries****************************//

    public String getSelectedFarmerRefresh() {
        return "select * from Farmer where ServerUpdatedStatus = 0";
    }

    public String getSelectedHarvestorRefresh() {
        return "select * from HarvestorVisitDetails where ServerUpdatedStatus = 0";
    }

    public String getSelectedHarvestorHistoryRefresh() {
        return "select * from HarvestorVisitHistory where ServerUpdatedStatus = 0";
    }

    public String getAddressRefresh() {
        return "select * from Address where ServerUpdatedStatus = 0";
    }

    public String getFileRepositoryRefresh() {
        return "select * from FileRepository where ServerUpdatedStatus = 0";
    }

    public String getVistLogs(){
        return "Select * from VisitLog where ServerUpdatedStatus = 0";
    }
    public String getUserSyncDetails(){
        return "Select * from UserSync where ServerUpdatedStatus = 0 ";
    }

    public String countOfSync(String UserId){
        return "Select * from UserSync where DATE(CreatedDate)= DATE('now') AND App = '3fMainApp' AND UserId = '"+UserId+"' ";
    }

    public String countOfMasterSync(){
        return "select * from UserSync where MasterSync=1 and TransactionSync=0 and ResetData=0 and DATE(CreatedDate)= DATE('now') ";
    }
    public String countOfTraSync(){
        return "select * from UserSync where MasterSync=0 and TransactionSync=1 and ResetData=0 and DATE(CreatedDate)= DATE('now')";
    }

    public String countOfResetdata(){
        return "select * from UserSync where MasterSync=0 and TransactionSync=0 and ResetData=1 and DATE(CreatedDate)= DATE('now')";
    }
    public String getPlotRefresh() {
        return "select * from Plot where ServerUpdatedStatus = 0";
    }

    //Tracking

    public String getPlotCurrentCropRefresh() {
        return "select * from PlotCurrentCrop where ServerUpdatedStatus = 0";
    }

    public String getNeighbourPlotRefresh() {
        return "select * from NeighbourPlot where ServerUpdatedStatus = 0";
    }

    public String getWaterResourceRefresh() {
        return "select * from WaterResource where ServerUpdatedStatus = 0";
    }

    public String getSoilResourceRefresh() {
        return "select * from SoilResource where ServerUpdatedStatus = 0";
    }

    public String getPlotIrrigationTypeXrefRefresh() {
        return "select * from PlotIrrigationTypeXref where ServerUpdatedStatus = 0";
    }

    public String getGpsTrackingRefresh() {
        return "select * from LocationTracker where ServerUpdatedStatus = 0";
    }


    public String getGeoBoundariesRefresh() {
        return "select * from GeoBoundaries where ServerUpdatedStatus = 0";
    }

    public String getFollowUpRefresh() {
        return "select * from FollowUp where ServerUpdatedStatus=0";
    }

    public String getReferralsRefresh() {
        return "select * from Referrals where ServerUpdatedStatus = 0";
    }

    public String getMarketSurveyRefresh() {
        return "select * from MarketSurvey where ServerUpdatedStatus = 0";
    }

    public String getFarmerHistoryRefresh() {
        return "select * from FarmerHistory where ServerUpdatedStatus = 0";
    }

    public String getIdentityProofRefresh() {
        return "select * from IdentityProof where ServerUpdatedStatus = 0";
    }

    public String getFarmerBankRefresh() {
        return "select * from FarmerBank where ServerUpdatedStatus = 0";
    }

    public String getPlantationRefresh() {
        return "select Id, PlotCode, NurseryId, SaplingSourceId, SaplingVendorId,CropVarietyId, AllotedArea, TreesCount, CreatedByUserId, CreatedDate, UpdatedByUserId, UpdatedDate, IsActive, ServerUpdatedStatus, ReasonTypeId, GFReceiptNumber, SaplingsPlanted, MissingPlantsComments from Plantation where ServerUpdatedStatus = 0";
    }

    public String getPlotLandlordRefresh() {
        return "select * from PlotLandlord  where ServerUpdatedStatus = 0";
    }

    public String getLandlordBankRefresh() {
        return "select * from LandlordBank  where ServerUpdatedStatus = 0";
    }

    public String getLandlordIDProofsRefresh() {
        return "select * from LandlordIdentityProof  where ServerUpdatedStatus = 0";
    }

    public String getCookingOilRefresh() {
        return "select * from CookingOil where ServerUpdatedStatus = 0";
    }

    public String getDiseaseRefresh() {
        return "select * from Disease where ServerUpdatedStatus = 0";
    }

    public String getFertilizerRefresh() {
        return "select * from Fertilizer  where ServerUpdatedStatus = 0";
    }


    public String getHarvestRefresh() {
        return "select * from Harvest  where ServerUpdatedStatus = 0";
    }

    public String getHealthPlantationRefresh() {
        return "select * from HealthPlantation  where ServerUpdatedStatus = 0";
    }

    public String getInterCropPlantationXrefRefresh() {
        return "select * from InterCropPlantationXref  where ServerUpdatedStatus = 0";
    }

    public String getNutrientRefresh() {
        return "select * from Nutrient where ServerUpdatedStatus = 0";
    }
    public String getRecomFertilizerRefresh() {
        return "select * from FertilizerRecommendations where ServerUpdatedStatus = 0";
    }

    public String getOwnerShipFileRepositoryRefresh() {
        return "select * from OwnerShipFileRepository  where ServerUpdatedStatus = 0";
    }

    public String getPestRefresh() {
        return "select * from Pest  where ServerUpdatedStatus = 0";
    }


    public String getUprootmentRefresh() {
        return "select * from Uprootment  where ServerUpdatedStatus = 0";
    }

    public String getWeedRefresh() {
        return "select * from Weed  where ServerUpdatedStatus = 0";
    }

    public String getYieldRefresh() {
        return "select * from YieldAssessment  where ServerUpdatedStatus = 0";
    }

    public String getWhiteRefresh() {
        return "select * from WhiteFlyAssessment  where ServerUpdatedStatus = 0";
    }

    public String getIdentityProofFileRepositoryXrefRefresh() {
        //return "select * from IdentityProofFileRepositoryXref  where ServerUpdatedStatus = 0";
        return "select * from IdentityProofFileRepositoryXref  ";
    }

    public String getPestChemicalXrefRefresh() {
        return "select * from PestChemicalXref  where ServerUpdatedStatus = 0";
    }

    public String getPlantationFileRepositoryXrefRefresh() {
        return "select * from PlantationFileRepositoryXref  where ServerUpdatedStatus = 0";
    }

    public String getCropMaintanenanceHistoryRefresh() {
        return "select * from CropMaintenanceHistory  where ServerUpdatedStatus = 0";
    }

    //****Finding codes in refresh*********************//


    public String getPlotCodeFromFarmerCode(String farmerCode) {
        return "select Code from Plot where FarmerCode = '" + farmerCode + "'";
    }

    public String getMarketSurveyFromFarmerCode(String farmerCode) {
        return "select * from MarketSurvey where FarmerCode = '" + farmerCode + "'";
    }

    public String getSelectedFileRepositoryQuery(String farmerCode, int moduleTypeId) {
        return "select * from FileRepository where FarmerCode = '" + farmerCode + "'" + " and moduleTypeId = '" + moduleTypeId + "'";
    }

    public String getSelectedFileRepositoryCheckQuery(String farmerCode, int moduleTypeId) {
        return "select * from FileRepository where FarmerCode = '" + farmerCode + "'" + " and moduleTypeId = '" + moduleTypeId + "'" + "and filelocation != 'null'";
    }

    public String getBranchDetails(String branchTypeId) {
        return "select Id, BranchName from Bank where BankTypeId = '" + branchTypeId + "'";
    }


    public String getIfscCode(String branchId) {
        return "select IFSCCode from Bank where Id = '" + branchId + "'";
    }


    public String getLookUpData(String LookupTypeId) {
        return "SELECT Id, Name FROM LookUp where LookupTypeId ='" + LookupTypeId + "'";
    }


    public String getWaterResourceBinding(String plotCode) {
        return "select * from WaterResource where PlotCode = '" + plotCode + "'";
    }

    public String getFarmerBankData(String farmerCode) {
        return "select * from FarmerBank where FarmerCode = '" + farmerCode + "'";
    }

    public String getFarmerIdentityProof(String farmerCode) {
        return "select * from IdentityProof where FarmerCode = '" + farmerCode + "'";
    }

    public String getSoilResourceBinding(String plotCode) {
        return "select * from SoilResource where PlotCode = '" + plotCode + "'";
    }

    public String getPlantatiobData(String plotCode) {
        return "select * from Plantation where PlotCode = '" + plotCode + "'";
    }

    public String getPlotIrrigationTypeXrefBinding(String plotCode) {
        return "select * from PlotIrrigationTypeXref where PlotCode = '" + plotCode + "'";
    }

    public String getHarvestBinding(String plotCode) {
        return "select * from Harvest where PlotCode = '" + plotCode + "'";
    }

    public String getFollowUpBinding(String plotCode) {
        return "select * from FollowUp where PlotCode = '" + plotCode + "'";
    }

    public String getBankTypeId(String Id) {
        return "select BankTypeId from Bank where Id = '" + Id + "'";
    }

    public String getBranchName(String banktypeid) {
        return "select BranchName from Bank where Id = '" + banktypeid + "'";
    }

    public String getTypecdDmtIdBank(String banktypeid) {
        return "select BankTypeId from Bank where Id = '" + banktypeid + "'";
    }

    public String getTypecdDesc(String typeid) {
        return "select Desc from TypeCdDmt where TypeCdId = '" + typeid + "'";
    }

    public String getTypecdDesc(int typeid) {
        return "select Desc from TypeCdDmt where TypeCdId = '" + typeid + "'";
    }


    public String isPlotExisted(String tableName, String PlotCode) {
        return "select * from '" + tableName + "' where PlotCode = '" + PlotCode + "'";
    }

    public String getPlotStatuesId(String type) {
        return "select TypeCdId from TypeCdDmt where Desc = '" + type + "'";
    }

    public String getUserVillages(String villageIds, final String mandalId) {
        return "select Id, Code, Name from Village where Id IN  (" + villageIds + ") and MandalId IN (Select Id from Mandal where Id IN (" + mandalId + "))";
    }

    public String setUserVillages(String villageIds, final String mandalId) {
        return "select Id, Code, Name from Village where Id IN  (" + villageIds + ") and MandalId IN (Select Id from Mandal where Id = '" + mandalId + "'" + ")";
    }

    public String getUserVillages(String villageIds) {
        return "select Id, Code, Name from Village where Id IN  (" + villageIds + ")";
    }

    public String getUserMandals(String mandalIds, final String DistrictId) {
        return "select Id, Code, Name from Mandal where Id IN  (" + mandalIds + ") and DistrictId IN (Select Id from District where Id IN (" + DistrictId + "))";
    }

    public String setUserMandals(String mandalIds, final String DistrictId) {
        return "select Id, Code, Name from Mandal where Id IN  (" + mandalIds + ") and DistrictId IN (Select Id from District where Id = '" + DistrictId + "'" + ")";
    }

    public String getUserDistricts(String districtIds, final String stateId) {
        return "select Id, Code, Name from District where Id IN  (" + districtIds + ") and StateId IN (Select Id from State where Id = '" + stateId + "'" + ")";
    }

    public String getUserStates(String stateIds) {
        return "select Id, Code, Name from State where Id IN  (" + stateIds + ")";
    }

    public String activityRightQuery(final int RoleId) {
        return "select Name  from RoleActivityRightXref rarx\n" +
                "inner join ActivityRight ar on ar.Id = rarx.ActivityRightId where RoleId = '" + RoleId + "' order by rarx.ActivityRightId";
    }
    public String checkPlantationRecordStatusInTable(String tableName, String plotCode, int createdId, int sourceid,int vendorid,int cropvareityid, String createdDate,String gfReceiptNumber) {
        return "SELECT EXISTS(SELECT 1 FROM " + tableName + " where PlotCode = '" + plotCode + "' and " +
                                     " CreatedByUserId = " + createdId +" and " +
                                     " SaplingSourceId = " + sourceid +" and " +
                                     " SaplingVendorId = " + vendorid +" and " +
                                     " CropVarietyId = " + cropvareityid +" and " +
                                     "  GFReceiptNumber = '" + gfReceiptNumber +"' and"+
                                     " datetime(CreatedDate) =  datetime('"+ createdDate +"')" + " LIMIT 1)";
    }
    public String checkRecordStatusInTable(String tableName, String columnName, String columnValue) {
        return "SELECT EXISTS(SELECT 1 FROM " + tableName + " where " + columnName + "= '" + columnValue + "'" + " LIMIT 1)";
    }
    public String checkRecordStatusInAdvanceDetailsTable(String plotCode, String receiptNum, String createdDate) {
        return " SELECT EXISTS(SELECT 1 FROM AdvancedDetails where PlotCode = '" + plotCode + "' and " +
                " ReceiptNumber = '"+receiptNum+"' " +
                " and CreatedDate = '"+createdDate+"' " + " LIMIT 1)";
    }
    public String checkRecordStatusInFarmerHistoryTable(String tableName, String columnName, String columnValue,String StatusTypeId) {
        return "SELECT EXISTS(SELECT 1 FROM " + tableName + " where " + columnName + "= '" + columnValue + "' and StatusTypeId = '"+ StatusTypeId + "' LIMIT 1)";
    }

    public String checkRecordStatusInTable(String tableName, String columnName, String columnName2, String columnValue, int columnValue2) {
        return "SELECT EXISTS(SELECT 1 FROM " + tableName + " where " + columnName + " = '" + columnValue + "'" + " and " + columnName2 + " = '" + columnValue2 + "'" + " LIMIT 1)";
    }

    public String checkRecordStatusInTable(String tableName, String columnName, String columnName2, String columnName3, String columnValue, String columnValue2, int columnValue3) {
        return "SELECT EXISTS(SELECT 1 FROM " + tableName + " where " + columnName + " = '" + columnValue + "'" + " and " + columnName2 + " = '" + columnValue2 + "'" + " LIMIT 1)";
    }

    public String checkRecordStatusInTable(String tableName, String columnName, String columnName2, String columnName3, String columnValue, String columnValue2, String columnValue3) {
        return "SELECT EXISTS(SELECT 1 FROM " + tableName + " where " + columnName + " = '" + columnValue + "'" + " and " + columnName2 + " = '" + columnValue2 + "'" + " and " + columnName3 + " = '" + columnValue3 + "'" + " LIMIT 1)";
    }

    public String getPlotDetails(final String farmercode, final int plotStatus) {
        return "select p.Code, p.TotalPlotArea, v.Name as VillageName, m.Name as MandalName, t.Desc, f.PotentialScore , (select GROUP_CONCAT(lkp.Name) from PlotCurrentCrop pcc \n" +
                        "inner join LookUp lkp on pcc.CropId =lkp.Id  where PlotCode = p.Code and lkp.LookUpTypeId = '22')  as Crops, f.UpdatedDate from Plot p\n" +
                        "inner join Address addr on p.AddressCode = addr.Code\n" +
                        "inner join Village v on addr.VillageId = v.Id\n" +
                        "inner join Mandal m on addr.MandalId = m.Id\n" +
                        "inner join TypeCdDmt t on t.TypecdId = p.CropIncomeTypeId\n" +
                        "inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
                        "inner join FollowUp f on f.PlotCode = p.Code\n" +
                        "where p.FarmerCode='" + farmercode + "'" + " and fh.StatusTypeId = " + plotStatus + " and fh.IsActive = '1' group by p.Code";
    }


    public String getMaxPestCode(final String plotCode) {
        return "SELECT MAX(cast(substr(Code, INSTR(Code,'-') + 1, length(Code)) as INTEGER)) as maxNumber , Code" +
                " FROM Pest where Code like '%" + plotCode + "%' ORDER BY ID DESC LIMIT 1";
    }

    public String getMaxCropMaintenanceHistoryCode(final String plotCode, final String userId) {
        return "SELECT MAX(cast(substr(replace(code,PlotCOde,''), INSTR(replace(code,PlotCOde,''),'-') + 1, length(replace(code,PlotCOde,''))) as INTEGER)) as maxNumber , Code " +
                "FROM CropMaintenanceHistory where Code like '%" + plotCode + "%' and " +
                "CreatedByUserId = '" + userId + "' ORDER BY ID DESC LIMIT 1";
    }

    public String getMaxComplaintsHistoryCode(final String plotCode, final String userId) {
        return "SELECT Code FROM Complaints where Code like '%" + plotCode + "%' and CreatedByUserId = '" + userId + "' ORDER BY ID DESC LIMIT 1";
    }

    public String getAPDigitalContract() {
        return "Select * from DigitalContract where IsActive = 'true' AND StateId = 1";
    }

    public String getTEDigitalContract() {
        return "Select * from DigitalContract where IsActive = 'true' AND StateId = 2";
    }

    public String getCKDigitalContract() {
        return "Select * from DigitalContract where IsActive = 'true' AND StateId = 3";
    }

    public String getARDigitalContract() {
        return "Select * from DigitalContract where IsActive = 'true' AND StateId = 4";
    }

    public String getCGDigitalContract() {
        return "Select * from DigitalContract where IsActive = 'true' AND StateId = 5";
    }

    public String getNKDigitalContract() {
        return "Select * from DigitalContract where IsActive = 'true' AND StateId = 6";
    }

    public String getSKDigitalContract() {
        return "Select * from DigitalContract where IsActive = 'true' AND StateId = 7";
    }


    public String getInterCropPlantationXref(String plotCode) {
        return "select * from InterCropPlantationXref  where PlotCode = '" + plotCode + "'";
    }

    public String getYieldQuery(final String fromDate, final String todate) {
        return "select sum(cpx.NetWeightPerPlot) as totalYield, sum(cpx.YieldPerHectar) as totalYieldPerHecter from CollectionPlotXref cpx\n" +
                "inner join Collection c on c.Code = cpx.CollectionCode\n" +
                "where  date(c.CreatedDate) BETWEEN date('" + fromDate + "')" +
                " AND date('" + todate + "') and cpx.PlotCode  = '" + CommonConstants.PLOT_CODE + "'";
    }

    public String querySumOfSaplings(final String plotCode) {
        return "select IFNULL(sum(SaplingsPlanted),0) from Plantation where PlotCode = '" + plotCode + "' ";
    }

    public String querySaplingsPlantedCount(final String plotCode) {
        return "select SaplingsPlanted from Plantation where PlotCode = '" + plotCode + "' ";
    }

    public String queryGetCountOfPreviousTrees(final String plotCode) {
        return "select PlamsCount from Uprootment  where PlotCode = '" + plotCode + "' order by CreatedDate  desc";
    }

    public String queryGeoTagCheck(final String plotCode) {
        return "select * from GeoBoundaries where PlotCode = '" + plotCode + "'" + " and GeoCategoryTypeId = '207'";
    }
    public String queryIdentityCheck(final String farmercode){
        return "select * from IdentityProof where FarmerCode='"+farmercode +"'";
    }

    public String queryBankChecking(final String farmarcode){
        return "select * from FarmerBank where FarmerCode='"+farmarcode +"'";
    }

    public String queryWaterResourceCheck(final String plotCode) {
        return "select * from WaterResource where PlotCode = '" + plotCode + "'";
    }

    public String querySoilResourceCheck(final String plotCode) {
        return "select * from SoilResource where PlotCode = '" + plotCode + "'";
    }

    public String queryActivityLog() {
        return "select * from ActivityLog where ServerUpdatedStatus = '0'";
    }

    public String getFilterBasedProspectiveFarmers(final int statusTypeId, String seachKey, int offset, int limit) {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.GuardianName,\n" +
                "s.Name as StateName,\n" +
                "f.ContactNumber, f.ContactNumber, v.Name, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension \n" +
                "from Farmer f \n" +
                "left join Village v on f.VillageId = v.Id\n" +
                "left join State s on f.StateId = s.Id\n" +
                "left join FileRepository fileRep on f.Code = fileRep.FarmerCode " + "and fileRep.ModuleTypeId = 193" + "\n" +
                "inner join Plot p on p.FarmerCode = f.Code \n"+
                "inner join FarmerHistory fh on fh.FarmerCode = f.Code\n" +
                "  and fh.StatusTypeId = '" + statusTypeId + "'" + "\n" +
                "and fh.IsActive = '1'" + "\n" +
                "where  f.IsActive = 1 AND ( f.FirstName like '%" + seachKey + "%' or f.MiddleName like '%" + seachKey + "%' or f.LastName like '%" + seachKey + "%' or f.Code like '%" + seachKey + "%' \n" +
                "or f.ContactNumber like '%" + seachKey + "%' or f.GuardianName like '%" + seachKey + "%') group by f.Code limit " + limit + " offset " + offset + ";";
    }
    public String getFilterBasedFarmers(final int statusTypeId, String seachKey, int offset, int limit) {
        return "SELECT  DISTINCT F.Code, F.FirstName, F.MiddleName, F.LastName, F.GuardianName, S.Name AS StateName,\n"+
        " F.ContactNumber, F.ContactNumber, V.Name, FR.FileLocation, FR.FileName, FR.FileExtension,\n"+
        " ASD.NoOfSaplingsAdvancePaidFor, ND.NoOfSaplingsDispatched  FROM   Farmer F\n"+
        " INNER JOIN Plot P ON P.FarmerCode = F.Code\n"+
        " INNER JOIN AdvanceSummary ASD on ASD.PlotCode = P.Code\n"+
        " INNER JOIN NurserySummary ND on ND.PlotCode = P.Code\n"+
        " INNER JOIN FarmerHistory FH ON FH.PlotCode=P.Code\n"+
        " and FH.StatusTypeId = '" + statusTypeId + "'" + "\n" +
        "and FH.IsActive = '1'" + "\n" +
        "LEFT JOIN Village V ON F.VillageId = V.Id \n"+
        "LEFT JOIN State S ON F.StateId = S.Id \n"+
        "LEFT JOIN FileRepository FR ON F.Code = FR.FarmerCode and FR.ModuleTypeId = 193\n"+
        "where  f.IsActive = 1 AND ( F.FirstName like'%" + seachKey + "%' or F.MiddleName like '%" + seachKey + "%' or" +
        " F.LastName like '%" + seachKey + "%'  or F.Code like '%" + seachKey + "%' \n"+
        " or F.ContactNumber like '%" + seachKey + "%' or F.GuardianName like '%" + seachKey + "%')  \n"+
        " AND ND.NoOfSaplingsDispatched  =  ASD.NoOfSaplingsAdvancePaidFor\n" +
        "GROUP BY F.Code, F.FirstName, F.MiddleName, F.LastName, F.GuardianName,S.Name ,\n" +
        "F.ContactNumber, F.ContactNumber, V.Name, FR.FileLocation, FR.FileName, FR.FileExtension\n" +
        "limit " + limit + " offset " + offset + "; " ;

    }

    public String getFilterBasedHarvestors(String seachKey) {
        return "select * from Harvestor where name like  '%" + seachKey + "%'";


    }

    public String getFilterBasedFarmersCrop(String seachKey, int offset, int limit) {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.GuardianName,\n" +
                "s.Name as StateName,\n" +
                "f.ContactNumber, f.ContactNumber, v.Name, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension \n" +
                "from Farmer f \n" +
                "left join Village v on f.VillageId = v.Id\n" +
                "left join State s on f.StateId = s.Id\n" +
                "left join FileRepository fileRep on f.Code = fileRep.FarmerCode" + " and fileRep.ModuleTypeId = 193" + "\n" +
                "inner join Plot p on p.FarmerCode = f.Code \n"+
                "inner join FarmerHistory fh on fh.FarmerCode = f.Code\n" +
                " and fh.StatusTypeId in ('88','89','308')" + "\n" +
                "and fh.IsActive = '1'" + "\n" +
                "where  f.IsActive = 1 AND (f.FirstName like '%" + seachKey + "%' or f.MiddleName like '%" + seachKey + "%' or f.LastName like '%" + seachKey + "%' or f.Code like '%" + seachKey + "%' \n" +
                "or f.ContactNumber like '%" + seachKey + "%' or f.GuardianName like '%" + seachKey + "%' ) group by f.Code limit " + limit + " offset " + offset + ";";
    }
    public String getFilterBasedFarmersCropM(String seachKey) {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.GuardianName,\n" +
                "s.Name as StateName,\n" +
                "f.ContactNumber, f.ContactNumber, v.Name, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension \n" +
                "from Farmer f \n" +
                "left join Village v on f.VillageId = v.Id\n" +
                "left join State s on f.StateId = s.Id\n" +
                "left join FileRepository fileRep on f.Code = fileRep.FarmerCode" + " and fileRep.ModuleTypeId = 193" + "\n" +
                "inner join Plot p on p.FarmerCode = f.Code \n"+
                "inner join FarmerHistory fh on fh.FarmerCode = f.Code\n" +
                " and fh.StatusTypeId in ('88','89','308')" + "\n" +
                "and fh.IsActive = '1'" + "\n" +
                "where  f.IsActive = 1 AND (f.FirstName like '%" + seachKey + "%' or f.MiddleName like '%" + seachKey + "%' or f.LastName like '%" + seachKey + "%' or f.Code like '%" + seachKey + "%' \n" +
                "or f.ContactNumber like '%" + seachKey + "%' or f.GuardianName like '%" + seachKey + "%' ) group by f.Code;";
    }




    public String getFilterFarmersWeedFly() {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName,  f.ContactNumber, f.ContactNumber from Farmer f ";

    }



    public String getFilterBasedFarmersCropRetake(String seachKey, int offset, int limit) {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.GuardianName,\n" +
                "s.Name as StateName,\n" +
                "f.ContactNumber, f.ContactNumber, v.Name, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension \n" +
                "from Farmer f \n" +
                "left join Village v on f.VillageId = v.Id \n" +
                "left join State s on f.StateId = s.Id \n" +
                "left join FileRepository fileRep on f.Code = fileRep.FarmerCode" + " and fileRep.ModuleTypeId = 193" + "\n" +
                "inner join Plot p on p.FarmerCode = f.Code \n"+
                "inner join FarmerHistory fh on fh.FarmerCode = f.Code\n" +
                " and fh.StatusTypeId in ('258')" + "\n" +
                "and fh.IsActive = '1'" + "\n" +
                "where  f.IsActive = 1 AND ( f.FirstName like '%" + seachKey + "%' or f.MiddleName like '%" + seachKey + "%' or f.LastName like '%" + seachKey + "%' or f.Code like '%" + seachKey + "%' \n" +
                "or f.ContactNumber like '%" + seachKey + "%' or f.GuardianName like '%" + seachKey + "%' )group by f.Code limit " + limit + " offset " + offset + ";";
    }

    public String getVisitRequestFarmers(String seachKey, int offset, int limit) {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.GuardianName,\n" +
                "s.Name as StateName,\n" +
                "f.ContactNumber, f.ContactNumber, v.Name, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension \n" +
                "from Farmer f \n" +
                "left join Village v on f.VillageId = v.Id \n" +
                "left join State s on f.StateId = s.Id \n" +
                "left join FileRepository fileRep on f.Code = fileRep.FarmerCode" + " and fileRep.ModuleTypeId = 193" + "\n" +
                "inner join Plot p on p.FarmerCode = f.Code \n"+
                "inner join FarmerHistory fh on fh.FarmerCode = f.Code\n" +
                " and fh.StatusTypeId in ('88','89','308')" + "\n" +
                "and fh.IsActive = '1'" + "\n" +
                " inner join VisitRequests vr on vr.FarmerCode = f.Code \n" +
                "  left join (select plotcode,max(CreatedDate)CreatedDate from CropMaintenanceHistory group by plotcode) cm on vr.PlotCode = cm.PlotCode \n" +
                "  where  f.IsActive = 1  and (cm.PlotCode is null or (cm.PlotCode is not null and DATE(vr.CreatedDate)>DATE(cm.CreatedDate ))) AND ( f.FirstName like '%" + seachKey + "%' or f.MiddleName like '%" + seachKey + "%' or f.LastName like '%" + seachKey + "%' or f.Code like '%" + seachKey + "%' \n" +
                "or f.ContactNumber like '%" + seachKey + "%' or f.GuardianName like '%" + seachKey + "%' )group by f.Code limit " + limit + " offset " + offset + ";";
    }

    public String getFilterBasedFarmersFollowUp(String seachKey, int offset, int limit)  {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.GuardianName,\n" +
                "s.Name as StateName,\n" +
                "f.ContactNumber, f.ContactNumber, v.Name, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension \n" +
                "from Farmer f \n" +
                "left join Village v on f.VillageId = v.Id\n" +
                "left join State s on f.StateId = s.Id\n" +
                "left join FileRepository fileRep on f.Code = fileRep.FarmerCode\n" +
                "and fileRep.ModuleTypeId = 193\n" +
                "inner join Plot p on p.FarmerCode = f.Code \n"+
                "inner join FarmerHistory fh on fh.FarmerCode = f.Code\n" +
                "and fh.StatusTypeId in ('81')" + "\n" +
                "and fh.IsActive = '1'" + "\n" +
                "where  f.IsActive = 1 AND (f.FirstName like '%" + seachKey + "%' or f.MiddleName like '%" + seachKey + "%' or f.LastName like '%" + seachKey + "%' or f.Code like '%" + seachKey + "%' \n" +
                "or f.ContactNumber like '%" + seachKey + "%' or f.GuardianName like '%" + seachKey + "%') group by f.Code limit " + limit + " offset " + offset + ";";
    }

    public String queryCropLastVisitDate() {
        return "SELECT CreatedDate from Uprootment where PlotCode = '" + CommonConstants.PLOT_CODE + "'" + " order by Id desc LIMIT 1";
    }

    public String queryFarmersCount() {
        return "select count(distinct(f.code)) from Farmer f\n" +
                "inner join FarmerHistory fh on fh.FarmerCode = f.Code \n" +
                "and fh.StatusTypeId in ('88', '89')";
    }
    public String getCocaInterCropCnt(){
        return "SELECT COUNT(*) FROM InterCropPlantationXref c WHERE  c.CropId in (129,227) AND c.PlotCode='"+CommonConstants.PLOT_CODE+"'";
    }

//    public String get2018WhiteCount(){
//        return "SELECT COUNT(*) FROM WhiteFlyAssessment w INNER JOIN CropmaintenanceHistory c ON w.CropMaintenaceCode=c.code WHERE w.Year=2018 AND c.PlotCode='"+CommonConstants.PLOT_CODE+"'";
//    }
//
//    public String get2019WhiteCount(){
//        return "SELECT COUNT(*) FROM WhiteFlyAssessment w INNER JOIN CropmaintenanceHistory c ON w.CropMaintenaceCode=c.code WHERE w.Year=2019 AND c.PlotCode='"+CommonConstants.PLOT_CODE+"'";
//    }

    public String getsecondpreviousyearWhiteFlyCount(String secondpreviousyear){
        return "SELECT COUNT(*) FROM WhiteFlyAssessment w INNER JOIN CropmaintenanceHistory c ON w.CropMaintenaceCode=c.code WHERE w.Year='" + secondpreviousyear + "'" + " AND c.PlotCode='"+CommonConstants.PLOT_CODE+"'";
    }

    public String getpreviousyearWhiteFlyCount(String previousyear){
        return "SELECT COUNT(*) FROM WhiteFlyAssessment w INNER JOIN CropmaintenanceHistory c ON w.CropMaintenaceCode=c.code WHERE w.Year='" + previousyear + "'" + " AND c.PlotCode='"+CommonConstants.PLOT_CODE+"'";
    }

    public String getPlotDistrictId(){
        return "select DistrictId from Address a INNER JOIN Plot P ON p.AddressCode=a.Code WHERE p.Code='"+CommonConstants.PLOT_CODE+"'";
    }
    public String queryVerifyGeoTag() {
        return "select Latitude, Longitude from GeoBoundaries where PlotCode = '" + CommonConstants.PLOT_CODE + "'" + "  and GeoCategoryTypeId = '207'";
    }

    public String queryVerifyFalogDistance() {

        return "select Latitude, Longitude from LocationTracker ORDER BY Id DESC LIMIT 1";
    }

    public String onlyValueFromDb(String tableName, String columnName, String whereCondition) {
        return "SELECT " + columnName + " from " + tableName + " where " + whereCondition;
    }

    public String queryLandLordBankData(final String plotCode) {
        return "select * from LandlordBank where PlotCode = '" + plotCode + "'";
    }

    public String queryPlotLandlordData(final String plotCode) {
        return "select * from PlotLandlord where PlotCode = '" + plotCode + "'";
    }


    public String getLatestCropMaintanaceHistoryCode(String plotcode) {
        return "select Code, max(CreatedDate) date from CropmaintenanceHistory where plotcode='" + plotcode + "'";
    }

    public String getCropMaintenanceHistoryData(String historyCode, String tableName) {
        return "select * from " + tableName + " where CropMaintenanceCode =  '" + historyCode + "' and PlotCode = '" + CommonConstants.PLOT_CODE + "'";
    }
    public String getRecommndCropMaintenanceHistoryData(String historyCode, String tableName) {
        return "select * from " + tableName + " where CropMaintenanceCode =  '" + historyCode + "' and PlotCode = '" + CommonConstants.PLOT_CODE + "'";
    }

    public String getFertilizerCropMaintenanceHistoryData(String historyCode) {
        return "select * from Fertilizer where CropMaintenanceCode =  '" + historyCode + "' and BioFertilizerId = '0'";
    }

    public String getBioFertilizerCropMaintenanceHistoryData(String historyCode) {
        return "select * from Fertilizer where CropMaintenanceCode =  '" + historyCode + "' and BioFertilizerId != '0'";
    }

    public String getRecommndCropMaintenanceHistoryDataforYieldandwhitefly(String historyCode, String tableName) {
        return "select * from " + tableName + " where CropMaintenaceCode =  '" + historyCode + "'";
    }


    public String getPestXrefData(String pestCode) {
        return "select * from PestChemicalXref where PestCode = '" + pestCode + "'";
    }


    public String getComplaintData() {
        return "select * from Complaints where ServerUpdatedStatus=0";
    }

    public String getComplaintDataByCode(String complaintCode) {
        return "select * from Complaints where Code = '" + complaintCode + "'";
    }

    public String getComplaintStatusHistoryByCode(String complaintCode) {
        return "select * from ComplaintStatusHistory where ComplaintCode = '" + complaintCode + "'" + " and IsActive = '1'";
    }

    public String getComplaintStatusHistoryAll(String complaintCode) {
        return "select * from ComplaintStatusHistory where ComplaintCode = '" + complaintCode + "'";
    }

    public String getComplaintXrefByCode(String complaintCode) {
        return "select * from ComplaintTypeXref where ComplaintCode = '" + complaintCode + "'";
    }

    public String getComplaintTypeXref() {
        return "select * from ComplaintTypeXref where ServerUpdatedStatus=0";
    }

    public String getComplaintStatusHistory() {
        return "select * from ComplaintStatusHistory  where ServerUpdatedStatus=0";
    }

    public String getComplaintRepository() {
        return "select * from ComplaintRepository  where ServerUpdatedStatus=0";
    }

    public String getComplaintRepositoryByCode(String complaintCode) {
        return "select * from ComplaintRepository where ComplaintCode = '" + complaintCode + "'";
    }

    public String getComplaintRepositoryByCodeForAudio(String complaintCode) {
        return "select * from ComplaintRepository where ComplaintCode = '" + complaintCode + "'" + " and FileExtension ='.mp3'";
    }

    public String UpgradeCount() {
        //number of Users
        return "select count(*) from UserInfo";
    }

    public String getComplaintToDisplay(boolean isPlot, String plotcode) {
        String wherecondition;
        if (isPlot) {
            wherecondition = "and cp.plotcode = " + "'" + plotcode + "'";
        } else {
            wherecondition = "";
        }
        return "select cp.Code, cx.ComplaintTypeId, csh.AssigntoUserId, csh.StatusTypeId,cp.CriticalityByTypeId, cp.CreatedDate, cp.PlotCode,\n" +
                "(select FirstName from Farmer f where f.Code = (select FarmerCode from Plot p where p.Code = cp.PlotCode)) as FarmerFirstName,\n" +
                "(select LastName from Farmer f where f.Code = (select FarmerCode from Plot p where p.Code = cp.PlotCode)) as FarmerLastName,\n" +
                "(select Name from Village where Id = (select VillageId from Farmer f where f.Code = (select FarmerCode from Plot p where p.Code = cp.PlotCode))) as FarmerVillageName,\n" +
                "(select Desc from TypeCdDmt where TypeCdId = cx.ComplaintTypeId) ComplaintType,\n" +
                "(select Desc from TypeCdDmt where TypeCdId = csh.StatusTypeId) ComplaintStatusType, \n" +
                "(select UserName from UserInfo ui where  ui.Id = cp.CreatedByUserId) as CreatedName\n" +
                "from Complaints cp\n" +
                "inner join\n" +
                "ComplaintTypeXref cx on cp.code = cx.ComplaintCode\n" +
                "inner join\n" +
                "ComplaintStatusHistory csh on csh.ComplaintCode = cp.Code where csh.IsActive = '1' " + wherecondition + " group by csh.ComplaintCode order by cp.CreatedDate DESC";
    }

    public String getKRAsDisplayQuery(int userId) {
        if (userId == 0) {
//            return "select ut.UserKRAId, ut.KRACode, ut.KRAName, ut.UOM, ut.AnnualTarget, " +
//                    "ut.AchievedTarget, ut.UserId, umt.MonthNumber, umt.MonthlyTarget, " +
//                    "umt.AchievedTarget from UserTarget ut\n" +
//                    "left join UserMonthlyTarget umt on ut.KRACode = umt.KRACode";
            return "select ut.KRACode, ut.KRAName, ut.UOM, ut.AnnualTarget, " +
                    "ut.AchievedTarget, ut.UserId, umt.MonthNumber, umt.MonthlyTarget, " +
                    "umt.AchievedTarget from UserTarget ut\n" +
                    "left join UserMonthlyTarget umt on ut.KRACode = umt.KRACode";
        }

//        return "select ut.UserKRAId, ut.KRACode, ut.KRAName, ut.UOM, ut.AnnualTarget, ut.AchievedTarget, ut.UserId, umt.MonthNumber, umt.MonthlyTarget, umt.AchievedTarget from UserTarget ut\n" +
//                "left join UserMonthlyTarget umt on ut.KRACode = umt.KRACode where ut.UserId = '" + userId + "'";
        return "select ut.KRACode, ut.KRAName, ut.UOM, ut.AnnualTarget, ut.AchievedTarget, ut.UserId, umt.MonthNumber, umt.MonthlyTarget, umt.AchievedTarget from UserTarget ut\n" +
                "left join UserMonthlyTarget umt on ut.KRACode = umt.KRACode where ut.UserId = '" + userId + "'";
    }

    public String getUnreadNotificationsCountQuery() {
        return "select count(*) from Alerts where isRead = 0";
    }



    public String getAlertsDetailsQueryToRender() {
        return "select a.*, t.Desc as alertType from Alerts a\n" +
                "inner join TypeCdDmt t on a.AlertTypeId = t.TypeCdId";
    }

    public String getAlertsDetailsQueryToSendCloud() {
        return "select * from Alerts where ServerUpdatedStatus = 0";
    }

    public String retakeGeoBoundry(final String PlotCode) {
        return "select IsRetakeGeoTagRequired from Plot where Code ='" + PlotCode + "'";
    }



    public static String plotAgeAndPlotLocation(final String PlotCode,final String currentDate) {

        return   "Select Cast (( JulianDay('" + currentDate + "') - JulianDay(p.DateofPlanting)) As Integer)/365 as plotAge,addr.StateId \n " +
                 "FROM Plot p \n" +
                " inner join Address addr on p.AddressCode = addr.Code \n" +
                "inner join State s on addr.StateId = s.Id \n" +
                "WHERE p.Code = '" + PlotCode + "'";
    }

    public static String CalculateExpectedYield(final int FincYear,final String PlotAge,final String stateId, final String curremtMonth) {

        return   "SELECT Round(sum(MonthlyPercentage/100 * YieldPerHectar),2) as ExpectedYield \n" +
                " FROM Benchmark WHERE Year = '" + FincYear + "' AND Age = '" + PlotAge + "' AND StateId = '" + stateId + "' \n" +
                "and  MonthSequenceNumber <= (select MonthSequenceNumber from  Benchmark \n" +
                " WHERE Year = '" + FincYear + "' AND Age = '" + PlotAge + "' AND StateId = '" + stateId + "' and MonthName = '" + curremtMonth + "' )";
    }

    public String ExpectedYield(final String plotCode,String YPHValue) {
        return "select Round(TotalPalmArea * '" + YPHValue + "',2) as areaunderpalm FROM Plot WHERE  Code = '" + plotCode + "'";
    }

   public String getFarmerCount(){
        return "Select count(*) from farmer";
   }
    public String getPlotCount(){
        return "Select count(*) from Plot";
    }


    public String getClusterName(final String VillageId){
        return "SELECT Name from Cluster WHERE Id =  (Select ClusterId FROM VillageClusterxref WHERE VillageId = '" + VillageId + "')";
    }

    public String getGapFillingTreeCount(final String plotCode,final String latestDate){
        return "select sum(TreesCount) \n" +
                "  from Plantation \n" +
                "  where Datetime('"+ latestDate +"') < Datetime(CreatedDate) and PlotCode ='"+plotCode +"'  AND ReasonTypeId = 330 ";
    }

    public String getExpectedTreeCount(final String plotCode){
        return "Select PlamsCount,CreatedDate FROM Uprootment Where PlotCode ='" + plotCode +"' ORDER BY CreatedDate DESC LIMIT 1";
    }


    public String getTotalPlotArea(final String plotCode){
        return "select TotalPlotArea FROM Plot where Code = '"+ plotCode +"'";
    }

    public String getNurserySaplings(final String plotCode){
//        return "select NurseryId,CropVarietyId,SaplingSourceId," +
//                "SaplingVendorId,NoOfSaplingsDispatched from NurserySaplingDetails where PlotCode = '"+plotCode+"'";

      return   "select NurseryId,CropVarietyId,SaplingSourceId,SaplingVendorId, SUM(NoOfSaplingsDispatched) as  NoOfSaplingsDispatched from NurserySaplingDetails where PlotCode = '"+plotCode+"' Group by CropVarietyId";
    }

    public String getNurserySaplingsArea(final String plotCode){
        return "select sum(NoOfSaplingsDispatched) from NurserySaplingDetails where PlotCode = '"+plotCode+"'";
    }

//    public String getNurserySaplingsArea(final String plotCode){
//        return "select NoOfSaplingsDispatched from NurserySaplingDetails where PlotCode = '"+plotCode+"'";
//    }
    public  String getVisitCount(final String plotCode)
    {
        return "select SUM(CASE WHEN UpdatedDate  BETWEEN date('now', 'localtime', '-3 months', 'start of year', '+3 months') AND " +
                "date('now', 'localtime', '-3 months', 'start of year', '+1 year', '+3 months', '-1 day') THEN 1 ELSE 0 END),MAX(UpdatedDate) as  CreatedDate " +
                "from CropMaintenanceHistory where  PlotCode = '"+plotCode+"'";
    }
    public String getAdvanceReceivedArea(final String plotCode){
        return "select sum(AdvanceReceivedArea) from AdvancedDetails where PlotCode = '"+plotCode+"'";
    }

    public String getNumber(String number)
    {
//        return  " select Code,FirstName,LastName,MiddleName from farmer where ContactNumber ='"+number+"' ";
        return  " select f.Code,FirstName,LastName,MiddleName,v.name as Villagename, c.name as Clustername from farmer f \n" +
                "inner join Village v on f.VillageId = v.id  \n" +
                "Inner join VillageClusterxref vc ON vc.VillageId = v.Id\n" +
                "Inner join Cluster c ON c.Id = vc.ClusterId\n" +
                "Where  f.ContactNumber ='"+number+"'";
    }

    public String getFarmerdetails()
    {
        return  "Select f.*, v.name as VillageName, c.Id as ClusterId, c.name as ClusterName\n" +
                "from Farmer f\n" +
                "JOIN Village v ON v.Id = f.VillageId\n" +
                "JOIN VillageClusterxref vc ON vc.VillageId = v.Id\n" +
                "JOIN Cluster c ON c.Id = vc.ClusterId ";
    }

    public String getBAnkNumber(String accountno){
//        return "select Farmer.Code,Farmer.FirstName,Farmer.LastName,Farmer.MiddleName,Farmer.GuardianName from Farmer inner join  FarmerBank on Farmer.Code=FarmerBank.FarmerCode where \n" +
//                "FarmerBank.AccountNumber='"+accountno+"'";

        return  "select Farmer.Code,Farmer.FirstName,Farmer.LastName,Farmer.MiddleName,Farmer.GuardianName,v.name as Villagename, c.name as Clustername  from Farmer inner join  FarmerBank on Farmer.Code=FarmerBank.FarmerCode\n" +
                "inner join Village v on Farmer.VillageId = v.id  \n" +
                "Inner join VillageClusterxref vc ON vc.VillageId = v.Id\n" +
                "Inner join Cluster c ON c.Id = vc.ClusterId\n" +
                "where FarmerBank.AccountNumber='"+accountno+"'";
    }

    public String getIdProofNumber(String idProofNumber){
//        return "select Farmer.Code,Farmer.FirstName,Farmer.LastName,Farmer.MiddleName,Farmer.GuardianName from Farmer inner join  IdentityProof on Farmer.Code=IdentityProof.FarmerCode where \n" +
//                "IdentityProof.IdProofNumber='"+idProofNumber+"'";

        return "select Farmer.Code,Farmer.FirstName,Farmer.LastName,Farmer.MiddleName,Farmer.GuardianName,v.name as Villagename, c.name as Clustername from Farmer inner join  IdentityProof on Farmer.Code=IdentityProof.FarmerCode \n" +
                "inner join Village v on Farmer.VillageId = v.id  \n" +
                "Inner join VillageClusterxref vc ON vc.VillageId = v.Id\n" +
                "Inner join Cluster c ON c.Id = vc.ClusterId\n" +
                "where IdentityProof.IdProofNumber= '"+idProofNumber+"'";
    }

    public String getRating(int typeID,int id)
    {
        return "select Remarks from LookUp where LookUpTypeId = '"+typeID+"' and Id = '"+id+"'";
    }

    public String getPerOfTree(int typeID,String des)
    {
        return "select TypeCdId from TypeCdDmt where ClassTypeId = '"+typeID+"' and Desc = '"+des+"'";
    }

    public String getlookupdata(int id)
    {
        return "select Name from LookUp where Id = '"+id+"'";
    }

    public String getUOMdata(int id)
    {
        return "select Name from UOM where Id = '"+id+"'";
    }

    public String gettypcdmtdata(int id)
    {
        return "select Desc from TypeCdDmt where TypeCdId = '"+id+"'";
    }

    public String getCCdata(int id)
    {
        return "select Name from CollectionCenter where Id = '"+id+"'";
    }


    public String getplotffbdetails(String plotcode){

        return "select * from PlotFFBDetails where Code = '"+plotcode+"'";
    }
    public String getplotgradingdetails(String plotcode){

        return "select * from PlotGradingDetails where PlotCode = '"+plotcode+"'";
    }
}





