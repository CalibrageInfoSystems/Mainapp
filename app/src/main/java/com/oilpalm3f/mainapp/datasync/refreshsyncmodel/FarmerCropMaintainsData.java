package com.oilpalm3f.mainapp.datasync.refreshsyncmodel;

import com.google.gson.annotations.Expose;

/**
 * Created by skasam on 10/3/2016.
 */
public class FarmerCropMaintainsData {

    private int CropMaintenanceId ;
    private int CropMaintenanceTypeId ;
    private String FarmerCode ;
    private String PlotCode ;
    private String StafflastVisit ;
    private int RateOurService ;
    private String Comments ;
    private int Pruning ;
    private String FertilizerSource ;
    private String FertilizerType ;
    private String FertilizerProductName ;
    private Integer WeedCode ;
    private String WeedMethod ;
    @Expose
    private Integer ChemicalCode ;
    private String OtherChemical ;
    private String UnitOfMeasure ;
    private Double DosageGiven ;
    private String LastAppliedDate ;
    private String FrequencyApplicationPerYear ;
    private int RateOnScale ;
    private String CreatedBy ;
    private String CreatedDate ;
    private String UpdatedBy ;
    private String UpdatedDate ;
    private int ServerUpdatedStatus ;


    public int getCropMaintenanceId() {
        return CropMaintenanceId;
    }

    public void setCropMaintenanceId(int cropMaintenanceId) {
        CropMaintenanceId = cropMaintenanceId;
    }

    public int getCropMaintenanceTypeId() {
        return CropMaintenanceTypeId;
    }

    public void setCropMaintenanceTypeId(int cropMaintenanceTypeId) {
        CropMaintenanceTypeId = cropMaintenanceTypeId;
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

    public String getStafflastVisit() {
        return StafflastVisit;
    }

    public void setStafflastVisit(String stafflastVisit) {
        StafflastVisit = stafflastVisit;
    }

    public int getRateOurService() {
        return RateOurService;
    }

    public void setRateOurService(int rateOurService) {
        RateOurService = rateOurService;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public int getPruning() {
        return Pruning;
    }

    public void setPruning(int pruning) {
        Pruning = pruning;
    }

    public String getFertilizerSource() {
        return FertilizerSource;
    }

    public void setFertilizerSource(String fertilizerSource) {
        FertilizerSource = fertilizerSource;
    }

    public String getFertilizerProductName() {
        return FertilizerProductName;
    }

    public void setFertilizerProductName(String fertilizerProductName) {
        FertilizerProductName = fertilizerProductName;
    }

    public String getFertilizerType() {
        return FertilizerType;
    }

    public void setFertilizerType(String fertilizerType) {
        FertilizerType = fertilizerType;
    }

    public Integer getWeedCode() {
        return WeedCode;
    }

    public void setWeedCode(Integer weedCode) {
        WeedCode = weedCode;
    }

    public String getWeedMethod() {
        return WeedMethod;
    }

    public void setWeedMethod(String weedMethod) {
        WeedMethod = weedMethod;
    }

    public Integer getChemicalCode() {
        return ChemicalCode;
    }

    public void setChemicalCode(Integer chemicalCode) {
        ChemicalCode = chemicalCode;
    }

    public String getOtherChemical() {
        return OtherChemical;
    }

    public void setOtherChemical(String otherChemical) {
        OtherChemical = otherChemical;
    }

    public String getUnitOfMeasure() {
        return UnitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        UnitOfMeasure = unitOfMeasure;
    }

    public Double getDosageGiven() {
        return DosageGiven;
    }

    public void setDosageGiven(Double dosageGiven) {
        DosageGiven = dosageGiven;
    }

    public String getFrequencyApplicationPerYear() {
        return FrequencyApplicationPerYear;
    }

    public void setFrequencyApplicationPerYear(String frequencyApplicationPerYear) {
        FrequencyApplicationPerYear = frequencyApplicationPerYear;
    }

    public String getLastAppliedDate() {
        return LastAppliedDate;
    }

    public void setLastAppliedDate(String lastAppliedDate) {
        LastAppliedDate = lastAppliedDate;
    }

    public int getRateOnScale() {
        return RateOnScale;
    }

    public void setRateOnScale(int rateOnScale) {
        RateOnScale = rateOnScale;
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
}
