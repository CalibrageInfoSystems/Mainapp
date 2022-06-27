package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 25/05/17.
 */

public class Weed {
    private String PlotCode;
    private Integer IsWeedProperlyDone;
    private Integer MethodTypeId;
    private Integer ChemicalId;
    private Integer ApplicationFrequency;
    private Integer IsPrunning;
    private Integer PrunningFrequency;
    private Integer IsMulchingSeen;
    private int IsWeavilsSeen;
    private int IsActive;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;
    private String CropMaintenanceCode;
    int BasinHealthId;
    int PruningId;
    int  WeedId;
    int WeevilsId;
    int InflorescenceId;

    public int getBasinHealthId() {
        return BasinHealthId;
    }

    public void setBasinHealthId(int basinHealthId) {
        BasinHealthId = basinHealthId;
    }

    public int getPruningId() {
        return PruningId;
    }

    public void setPruningId(int pruningId) {
        PruningId = pruningId;
    }

    public int getWeedId() {
        return WeedId;
    }

    public void setWeedId(int weedId) {
        WeedId = weedId;
    }

    public int getWeevilsId() {
        return WeevilsId;
    }

    public void setWeevilsId(int weevilsId) {
        WeevilsId = weevilsId;
    }

    public int getInflorescenceId() {
        return InflorescenceId;
    }

    public void setInflorescenceId(int inflorescenceId) {
        InflorescenceId = inflorescenceId;
    }

    public Integer getIsweedproperlydone() {
        return IsWeedProperlyDone;
    }

    public void setIsweedproperlydone(Integer IsWeedProperlyDone) {
        this.IsWeedProperlyDone = IsWeedProperlyDone;
    }

    public Integer getMethodtypeid() {
        return MethodTypeId;
    }

    public void setMethodtypeid(Integer MethodTypeId) {
        this.MethodTypeId = MethodTypeId;
    }

    public Integer getChemicalid() {
        return ChemicalId;
    }

    public void setChemicalid(Integer ChemicalId) {
        this.ChemicalId = ChemicalId;
    }

    public Integer getApplicationfrequency() {
        return ApplicationFrequency;
    }

    public void setApplicationfrequency(Integer ApplicationFrequency) {
        this.ApplicationFrequency = ApplicationFrequency;
    }

    public Integer getIsprunning() {
        return IsPrunning;
    }

    public void setIsprunning(Integer IsPrunning) {
        this.IsPrunning = IsPrunning;
    }

    public Integer getPrunningfrequency() {
        return PrunningFrequency;
    }

    public void setPrunningfrequency(Integer PrunningFrequency) {
        this.PrunningFrequency = PrunningFrequency;
    }

    public Integer getIsmulchingseen() {
        return IsMulchingSeen;
    }

    public void setIsmulchingseen(Integer IsMulchingSeen) {
        this.IsMulchingSeen = IsMulchingSeen;
    }

    public int getIsweavilsseen() {
        return IsWeavilsSeen;
    }

    public void setIsweavilsseen(int IsWeavilsSeen) {
        this.IsWeavilsSeen = IsWeavilsSeen;
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

    public String getPlotCode() {
        return PlotCode;
    }

    public void setPlotCode(String plotCode) {
        PlotCode = plotCode;
    }

    public String getCropMaintenanceCode() {
        return CropMaintenanceCode;
    }

    public void setCropMaintenanceCode(String cropMaintenanceCode) {
        CropMaintenanceCode = cropMaintenanceCode;
    }
}
