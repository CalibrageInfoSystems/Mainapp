package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 25/05/17.
 */

public class Fertilizer {
    private String PlotCode;
    private Integer FertilizerSourceTypeId;
    private Integer FertilizerId;
    private Integer FertilizerProviderId;
    private Integer UOMId;
    private double Dosage;
    private String LastAppliedDate;
    private Integer ApplyFertilizerFrequencyTypeId;
    private Integer RateScale;
    private String Comments;
    private int IsActive;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;
    private String CropMaintenanceCode;
    private String SourceName;
    private int IsFertilizerApplied;
    private int ApplicationYear;
    private String ApplicationMonth;
    private int Quarter;
    private String ApplicationType;
    private  int BioFertilizerId;

    public int getBioFertilizerId() {
        return BioFertilizerId;
    }

    public void setBioFertilizerId(int bioFertilizerId) {
        BioFertilizerId = bioFertilizerId;
    }

    public String getPlotcode() {
        return PlotCode;
    }

    public void setPlotcode(String PlotCode) {
        this.PlotCode = PlotCode;
    }

    public Integer getFertilizersourcetypeid() {
        return FertilizerSourceTypeId;
    }

    public void setFertilizersourcetypeid(Integer FertilizerSourceTypeId) {
        this.FertilizerSourceTypeId = FertilizerSourceTypeId;
    }

    public Integer getFertilizerid() {
        return FertilizerId;
    }

    public void setFertilizerid(Integer FertilizerId) {
        this.FertilizerId = FertilizerId;
    }

    public Integer getFertilizerproviderid() {
        return FertilizerProviderId;
    }

    public void setFertilizerproviderid(Integer FertilizerProviderId) {
        this.FertilizerProviderId = FertilizerProviderId;
    }

    public int getUomid() {
        return UOMId;
    }

    public void setUomid(Integer UOMId) {
        this.UOMId = UOMId;
    }

    public double getDosage() {
        return Dosage;
    }

    public void setDosage(double Dosage) {
        this.Dosage = Dosage;
    }

    public String getLastapplieddate() {
        return LastAppliedDate;
    }

    public void setLastapplieddate(String LastAppliedDate) {
        this.LastAppliedDate = LastAppliedDate;
    }

    public Integer getApplyfertilizerfrequencytypeid() {
        return ApplyFertilizerFrequencyTypeId;
    }

    public void setApplyfertilizerfrequencytypeid(Integer ApplyFertilizerFrequencyTypeId) {
        this.ApplyFertilizerFrequencyTypeId = ApplyFertilizerFrequencyTypeId;
    }

    public Integer getRatescale() {
        return RateScale;
    }

    public void setRatescale(Integer RateScale) {
        this.RateScale = RateScale;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public int getIsactive() {
        return IsActive;
    }

    public void setIsactive(int IsActive) {
        this.IsActive = IsActive;
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

    public int getServerupdatedstatus() {
        return ServerUpdatedStatus;
    }

    public void setServerupdatedstatus(int ServerUpdatedStatus) {
        this.ServerUpdatedStatus = ServerUpdatedStatus;
    }



    public void setCropMaintenanceCode(String cropMaintenanceCode) {
        this.CropMaintenanceCode = cropMaintenanceCode;
    }

    public String getCropMaintenanceCode() {
        return CropMaintenanceCode;
    }

    public void setSourceName(String sourceName) {
        this.SourceName = sourceName;
    }

    public String getSourceName() {
        return SourceName;
    }


    public int getIsFertilizerApplied() {
        return IsFertilizerApplied;
    }

    public void setIsFertilizerApplied(int isFertilizerApplied) {
        IsFertilizerApplied = isFertilizerApplied;
    }

    public int getApplicationYear() {
        return ApplicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        ApplicationYear = applicationYear;
    }

    public String getApplicationMonth() {
        return ApplicationMonth;
    }

    public void setApplicationMonth(String applicationMonth) {
        ApplicationMonth = applicationMonth;
    }

    public int getQuarter() {
        return Quarter;
    }

    public void setQuarter(int quarter) {
        Quarter = quarter;
    }

    public String getApplicationType() {
        return ApplicationType;
    }

    public void setApplicationType(String applicationType) {
        ApplicationType = applicationType;
    }
}
