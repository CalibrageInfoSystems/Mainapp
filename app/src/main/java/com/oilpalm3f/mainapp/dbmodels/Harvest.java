package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 25/05/17.
 */

public class Harvest {
    private String PlotCode;
    private double PlotYield;
    private double YieldPerHactor;
    private int CollectionCenterId;
    private Integer TransportModeTypeId;
    private Integer VehicleTypeId;
    private int TransportPaidAmount;
    private Integer HarvestingMethodTypeId;
    private double WagesPerDay;
    private Integer HarvestingTypeId;
    private String Comments;
    private int IsActive;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private  int ServerUpdatedStatus;
    private String CropMaintenanceCode;
    private Integer WagesUnitTypeId;
    private double ContractAmount;

    public Integer getWagesUnitTypeId() {
        return WagesUnitTypeId;
    }

    public void setWagesUnitTypeId(Integer wagesUnitTypeId) {
        WagesUnitTypeId = wagesUnitTypeId;
    }

    public double getContractAmount() {
        return ContractAmount;
    }

    public void setContractAmount(double contractAmount) {
        ContractAmount = contractAmount;
    }

    public double getPlotyield() {
        return PlotYield;
    }

    public void setPlotyield(double PlotYield) {
        this.PlotYield = PlotYield;
    }

    public double getYieldperhactor() {
        return YieldPerHactor;
    }

    public void setYieldperhactor(double YieldPerHactor) {
        this.YieldPerHactor = YieldPerHactor;
    }

    public Integer getCollectioncenterid() {
        return CollectionCenterId;
    }

    public void setCollectioncenterid(Integer CollectionCenterId) {
        this.CollectionCenterId = CollectionCenterId;
    }

    public Integer getTransportmodetypeid() {
        return TransportModeTypeId;
    }

    public void setTransportmodetypeid(Integer TransportModeTypeId) {
        this.TransportModeTypeId = TransportModeTypeId;
    }

    public Integer getVehicletypeid() {
        return VehicleTypeId;
    }

    public void setVehicletypeid(Integer VehicleTypeId) {
        this.VehicleTypeId = VehicleTypeId;
    }

    public int getTransportpaidamount() {
        return TransportPaidAmount;
    }

    public void setTransportpaidamount(int TransportPaidAmount) {
        this.TransportPaidAmount = TransportPaidAmount;
    }

    public Integer getHarvestingmethodtypeid() {
        return HarvestingMethodTypeId;
    }

    public void setHarvestingmethodtypeid(Integer HarvestingMethodTypeId) {
        this.HarvestingMethodTypeId = HarvestingMethodTypeId;
    }

    public double getWagesperday() {
        return WagesPerDay;
    }

    public void setWagesperday(double WagesPerDay) {
        this.WagesPerDay = WagesPerDay;
    }

    public Integer getHarvestingtypeid() {
        return HarvestingTypeId;
    }

    public void setHarvestingtypeid(Integer HarvestingTypeId) {
        this.HarvestingTypeId = HarvestingTypeId;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public int getCreatedbyuserid() {
        return CreatedByUserId;
    }

    public void setCreatedbyuserid(int CreatedByUserId) {
        this.CreatedByUserId = CreatedByUserId;
    }

    public String getCreateddate() {
        return CreatedDate;
    }

    public void setCreateddate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public int getUpdatedbyuserid() {
        return UpdatedByUserId;
    }

    public void setUpdatedbyuserid(int UpdatedByUserId) {
        this.UpdatedByUserId = UpdatedByUserId;
    }

    public String getUpdateddate() {
        return UpdatedDate;
    }

    public void setUpdateddate(String UpdatedDate) {
        this.UpdatedDate = UpdatedDate;
    }

    public String getPlotCode() {
        return PlotCode;
    }

    public void setPlotCode(String plotCode) {
        PlotCode = plotCode;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }

    public String getCropMaintenanceCode() {
        return CropMaintenanceCode;
    }

    public void setCropMaintenanceCode(String cropMaintenanceCode) {
        CropMaintenanceCode = cropMaintenanceCode;
    }
}
