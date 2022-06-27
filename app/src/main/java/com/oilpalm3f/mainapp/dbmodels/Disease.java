package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 25/05/17.
 */

public class Disease {
    private String PlotCode;
    private Integer IsDiseaseNoticedinPreviousVisit;
    private Integer IsProblemRectified;
    private String ProblemRectifiedComments;
    private Integer DiseaseId;
    private Integer ChemicalId;
    private Integer IsResultSeen;
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
    int IsControlMeasure;
    int  PercTreesId;

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


    public Integer getIsdiseasenoticedinpreviousvisit() {
        return IsDiseaseNoticedinPreviousVisit;
    }

    public void setIsdiseasenoticedinpreviousvisit(Integer IsDiseaseNoticedinPreviousVisit) {
        this.IsDiseaseNoticedinPreviousVisit = IsDiseaseNoticedinPreviousVisit;
    }

    public Integer getIsproblemrectified() {
        return IsProblemRectified;
    }

    public void setIsproblemrectified(Integer IsProblemRectified) {
        this.IsProblemRectified = IsProblemRectified;
    }

    public String getProblemrectifiedcomments() {
        return ProblemRectifiedComments;
    }

    public void setProblemrectifiedcomments(String ProblemRectifiedComments) {
        this.ProblemRectifiedComments = ProblemRectifiedComments;
    }

    public Integer getDiseaseid() {
        return DiseaseId;
    }

    public void setDiseaseid(Integer DiseaseId) {
        this.DiseaseId = DiseaseId;
    }

    public Integer getChemicalid() {
        return ChemicalId;
    }

    public void setChemicalid(Integer ChemicalId) {
        this.ChemicalId = ChemicalId;
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