package com.oilpalm3f.mainapp.datasync.refreshsyncmodel;

/**
 * Created by skasam on 10/3/2016.
 */
public class FarmerFFBHarvestDetails {

    private int FFBHarvestId ;
    private String FarmerCode ;
    private String PlotCode ;
    private int CollectionCentreId ;
    private String ModeOfTransport ;
    private String HarvestingMethod ;
    private Double WagesPerDay ;
    private Double ContractRsPerMonth ;
    private Double ContractRsPerAnum ;
    private String TypeOfHarvesting ;
    private String CreatedBy ;
    private String CreatedDate ;
    private String UpdatedBy ;
    private String UpdatedDate ;
    private int ServerUpdatedStatus ;
    private int ContractorPitch ;
    private int FarmerConsent ;
    private String Comments ;

    public int getFFBHarvestId() {
        return FFBHarvestId;
    }

    public void setFFBHarvestId(int FFBHarvestId) {
        this.FFBHarvestId = FFBHarvestId;
    }

    public String getFarmerCode() {
        return FarmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        FarmerCode = farmerCode;
    }

    public String getPlotCode() {
        return PlotCode;
    }

    public void setPlotCode(String plotCode) {
        PlotCode = plotCode;
    }

    public int getCollectionCentreId() {
        return CollectionCentreId;
    }

    public void setCollectionCentreId(int collectionCentreId) {
        CollectionCentreId = collectionCentreId;
    }

    public String getModeOfTransport() {
        return ModeOfTransport;
    }

    public void setModeOfTransport(String modeOfTransport) {
        ModeOfTransport = modeOfTransport;
    }

    public String getHarvestingMethod() {
        return HarvestingMethod;
    }

    public void setHarvestingMethod(String harvestingMethod) {
        HarvestingMethod = harvestingMethod;
    }

    public Double getWagesPerDay() {
        return WagesPerDay;
    }

    public void setWagesPerDay(Double wagesPerDay) {
        WagesPerDay = wagesPerDay;
    }

    public Double getContractRsPerMonth() {
        return ContractRsPerMonth;
    }

    public void setContractRsPerMonth(Double contractRsPerMonth) {
        ContractRsPerMonth = contractRsPerMonth;
    }

    public Double getContractRsPerAnum() {
        return ContractRsPerAnum;
    }

    public void setContractRsPerAnum(Double contractRsPerAnum) {
        ContractRsPerAnum = contractRsPerAnum;
    }

    public String getTypeOfHarvesting() {
        return TypeOfHarvesting;
    }

    public void setTypeOfHarvesting(String typeOfHarvesting) {
        TypeOfHarvesting = typeOfHarvesting;
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

    public String getComments(){
        return Comments;
    }
    public  void setComments(String comments){
        Comments = comments;
    }
    public  int getContractorPitch(){
        return ContractorPitch;
    }
    public void  setContractorPitch(int contractorPitch){
        ContractorPitch = contractorPitch;
    }

    public int getFarmerConsent(){
        return FarmerConsent;
    }

    public void setFarmerConsent(int farmerConsent){
        FarmerConsent = farmerConsent;
    }

}
