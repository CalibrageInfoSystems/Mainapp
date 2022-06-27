package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 25/05/17.
 */

public class Nutrient {
    private String PlotCode;
    private Integer IsPreviousNutrientDeficiency;
    private Integer IsCurrentNutrientDeficiency;
    private Integer NutrientId;
    private Integer ChemicalId;
    private Integer ApplyNutrientFrequencyTypeId;
    private Integer IsResultSeen;
    private String Comments;
    private int IsActive;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private Integer IsProblemRectified;
    private int ServerUpdatedStatus;
    private String CropMaintenanceCode;
    private Integer RecommendedFertilizerId;
    private Integer UOMId  ;
    private double Dosage;
    int PercTreesId;
    private Integer RecommendedUOMId;

    public Integer getRecommendedUOMId() {
        return RecommendedUOMId;
    }

    public void setRecommendedUOMId(Integer recommendedUOMId) {
        RecommendedUOMId = recommendedUOMId;
    }

    public int getPercTreesId() {
        return PercTreesId;
    }

    public void setPercTreesId(int percTreesId) {
        PercTreesId = percTreesId;
    }

    public Integer getRecommendFertilizerProviderId() {
        return RecommendedFertilizerId;
    }

    public void setRecommendFertilizerProviderId(Integer recommendFertilizerProviderId) {
        RecommendedFertilizerId = recommendFertilizerProviderId;
    }

    public Integer getRecommendUOMId() {
        return UOMId  ;
    }

    public void setRecommendUOMId(Integer recommendUOMId) {
        UOMId   = recommendUOMId;
    }

    public double getRecommendDosage() {
        return Dosage;
    }

    public void setRecommendDosage(double recommendDosage) {
        Dosage = recommendDosage;
    }

    public String getPlotcode() {
        return PlotCode;
    }

    public void setPlotcode(String PlotCode) {
        this.PlotCode = PlotCode;
    }

    public Integer getIsproblemrectified() {
        return IsProblemRectified;
    }

    public void setIsproblemrectified(Integer IsProblemRectified) {
        this.IsProblemRectified = IsProblemRectified;
    }

    public Integer getIscurrentnutrientdeficiency() {
        return IsCurrentNutrientDeficiency;
    }

    public void setIscurrentnutrientdeficiency(Integer IsCurrentNutrientDeficiency) {
        this.IsCurrentNutrientDeficiency = IsCurrentNutrientDeficiency;
    }

    public Integer getNutrientid() {
        return NutrientId;
    }

    public void setNutrientid(Integer NutrientId) {
        this.NutrientId = NutrientId;
    }

    public Integer getChemicalid() {
        return ChemicalId;
    }

    public void setChemicalid(Integer ChemicalId) {
        this.ChemicalId = ChemicalId;
    }

    public Integer getApplynutrientfrequencytypeid() {
        return ApplyNutrientFrequencyTypeId;
    }

    public void setApplynutrientfrequencytypeid(Integer ApplyNutrientFrequencyTypeId) {
        this.ApplyNutrientFrequencyTypeId = ApplyNutrientFrequencyTypeId;
    }

    public Integer getIsresultseen() {
        return IsResultSeen;
    }

    public void setIsresultseen(Integer IsResultSeen) {
        this.IsResultSeen = IsResultSeen;
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

    public Integer getIsPreviousNutrientDeficiency() {
        return IsPreviousNutrientDeficiency;
    }

    public void setIsPreviousNutrientDeficiency(Integer isPreviousNutrientDeficiency) {
        IsPreviousNutrientDeficiency = isPreviousNutrientDeficiency;
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