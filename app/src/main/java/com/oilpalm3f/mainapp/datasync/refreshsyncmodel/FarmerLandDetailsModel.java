package com.oilpalm3f.mainapp.datasync.refreshsyncmodel;

/**
 * Created by skasam on 10/3/2016.
 */
public class FarmerLandDetailsModel {

    private String PlotCode ;
    private String FarmerCode ;
    private int DistrictCode ;
    private int MandalCode;
    private int VillageCode ;
    private String SurveyNumber ;
    private String AdangalOrFileNo ;
    private int CareTaker ;
    private String POCContactInfo ;
    private String POCContactNumber;
    private int OwnerShip;
    private String LandlordName;
    private String LandlordContactNumber;
    private String LeaseDateFrom;
    private String LeaseDateTo;
    private String Comments;
    private double TotalArea ;
    private Double TotalPalm ;
    private double AreaLeftOut ;
    private String MOUNo ;
    private String MOUDate ;
    private String LandMark;
    private String PlotAddress;
    private String SourceOfWater;
    private int NumberOfBoreWells;
    private double GPSPlotArea ;
    private double PlotDifference ;
    private String Representative;
    private int Active ;
    private String CreatedBy ;
    private String CreatedDate ;
    private String UpdatedBy ;
    private String UpdatedDate ;
    private int ServerUpdatedStatus ;

    public String getPlotCode() {
        return PlotCode;
    }

    public void setPlotCode(String plotCode) {
        PlotCode = plotCode;
    }

    public String getFarmerCode() {
        return FarmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        FarmerCode = farmerCode;
    }

    public int getVillageCode() {
        return VillageCode;
    }

    public void setVillageCode(int villageCode) {
        VillageCode = villageCode;
    }

    public String getSurveyNumber() {
        return SurveyNumber;
    }

    public void setSurveyNumber(String surveyNumber) {
        SurveyNumber = surveyNumber;
    }

    public String getAdangalOrFileNo() {
        return AdangalOrFileNo;
    }

    public void setAdangalOrFileNo(String adangalOrFileNo) {
        AdangalOrFileNo = adangalOrFileNo;
    }

    public double getTotalArea() {
        return TotalArea;
    }

    public void setTotalArea(double totalArea) {
        TotalArea = totalArea;
    }

    public Double getTotalPalm() {
        return TotalPalm;
    }

    public void setTotalPalm(Double totalPalm) {
        TotalPalm = totalPalm;
    }

    public double getAreaLeftOut() {
        return AreaLeftOut;
    }

    public void setAreaLeftOut(double areaLeftOut) {
        AreaLeftOut = areaLeftOut;
    }

    public String getMOUNo() {
        return MOUNo;
    }

    public void setMOUNo(String MOUNo) {
        this.MOUNo = MOUNo;
    }

    public String getMOUDate() {
        return MOUDate;
    }

    public void setMOUDate(String MOUDate) {
        this.MOUDate = MOUDate;
    }

    public double getGPSPlotArea() {
        return GPSPlotArea;
    }

    public void setGPSPlotArea(double GPSPlotArea) {
        this.GPSPlotArea = GPSPlotArea;
    }

    public double getPlotDifference() {
        return PlotDifference;
    }

    public void setPlotDifference(double plotDifference) {
        PlotDifference = plotDifference;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }

    public int getMandalCode() {
        return MandalCode;
    }

    public void setMandalCode(int mandalCode) {
        MandalCode = mandalCode;
    }

    public String getPOCContactInfo() {
        return POCContactInfo;
    }

    public void setPOCContactInfo(String POCContactInfo) {
        this.POCContactInfo = POCContactInfo;
    }

    public String getPOCContactNumber() {
        return POCContactNumber;
    }

    public void setPOCContactNumber(String POCContactNumber) {
        this.POCContactNumber = POCContactNumber;
    }

    public int getOwnerShip() {
        return OwnerShip;
    }

    public void setOwnerShip(int ownerShip) {
        OwnerShip = ownerShip;
    }

    public String getLeaseDateFrom() {
        return LeaseDateFrom;
    }

    public void setLeaseDateFrom(String leaseDateFrom) {
        LeaseDateFrom = leaseDateFrom;
    }

    public String getLeaseDateTo() {
        return LeaseDateTo;
    }

    public void setLeaseDateTo(String leaseDateTo) {
        LeaseDateTo = leaseDateTo;
    }

    public String getLandMark() {
        return LandMark;
    }

    public void setLandMark(String landMark) {
        LandMark = landMark;
    }

    public String getPlotAddress() {
        return PlotAddress;
    }

    public void setPlotAddress(String plotAddress) {
        PlotAddress = plotAddress;
    }

    public int getCareTaker() {
        return CareTaker;
    }

    public void setCareTaker(int careTaker) {
        CareTaker = careTaker;
    }

    public String getLandlordName() {
        return LandlordName;
    }

    public void setLandlordName(String landlordName) {
        LandlordName = landlordName;
    }

    public String getLandlordContactNumber() {
        return LandlordContactNumber;
    }

    public void setLandlordContactNumber(String landlordContactNumber) {
        LandlordContactNumber = landlordContactNumber;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public  int getDistrictCode(){
        return DistrictCode;
    }

    public void setDistrictCode(int districtCode){
        DistrictCode = districtCode;
    }

    public String getSourceOfWater() {
        return SourceOfWater;
    }

    public void setSourceOfWater(String sourceOfWater) {
        SourceOfWater = sourceOfWater;
    }

    public int getNumberOfBoreWells() {
        return NumberOfBoreWells;
    }

    public void setNumberOfBoreWells(int numberOfBoreWells) {
        NumberOfBoreWells = numberOfBoreWells;
    }

    public String getRepresentative() {
        return Representative;
    }

    public void setRepresentative(String representative) {
        Representative = representative;
    }
}
