package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 25/05/17.
 */

public class Pest {
    private String PlotCode;
    private String Code;
    private Integer PestId;
    private Integer IsResultsSeen;
    private String Comments;
    private int IsActive;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;
    private String CropMaintenanceCode;
    private Integer RecommendedChemicalId ;
    private Integer UOMId  ;
    private double Dosage  ;
    int PercTreesId;
    int IsControlMeasure;
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

    public int getIsControlMeasure() {
        return IsControlMeasure;
    }

    public void setIsControlMeasure(int isControlMeasure) {
        IsControlMeasure = isControlMeasure;
    }

    public Integer getRecommendFertilizerProviderId() {
        return RecommendedChemicalId ;
    }

    public void setRecommendFertilizerProviderId(Integer recommendFertilizerProviderId) {
        RecommendedChemicalId  = recommendFertilizerProviderId;
    }

    public Integer getRecommendUOMId() {
        return UOMId  ;
    }

    public void setRecommendUOMId(Integer recommendUOMId) {
        UOMId   = recommendUOMId;
    }

    public double getRecommendDosage() {
        return Dosage  ;
    }

    public void setRecommendDosage(double recommendDosage) {
        Dosage   = recommendDosage;
    }
    public Integer getPestid() {
        return PestId;
    }

    public void setPestid(Integer PestId) {
        this.PestId = PestId;
    }

    public Integer getIsresultsseen() {
        return IsResultsSeen;
    }

    public void setIsresultsseen(Integer IsResultsSeen) {
        this.IsResultsSeen = IsResultsSeen;
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

    public String getPlotCode() {
        return PlotCode;
    }

    public void setPlotCode(String plotCode) {
        this.PlotCode = plotCode;
    }

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getCropMaintenanceCode() {
        return CropMaintenanceCode;
    }

    public void setCropMaintenanceCode(String cropMaintenanceCode) {
        CropMaintenanceCode = cropMaintenanceCode;
    }
}