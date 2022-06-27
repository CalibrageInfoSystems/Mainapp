package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 25/05/17.
 */

public class Uprootment {
    private String PlotCode;
    private String CropMaintenanceCode;
    private int SeedsPlanted;
    private int PlamsCount;
    private Integer IsTreesMissing;
    private int MissingTreesCount;
    private Integer ReasonTypeId;
    private String Comments;
    private int IsActive;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;
    private int ExpectedPlamsCount;

    public int getExpectedPlamsCount() {
        return ExpectedPlamsCount;
    }

    public void setExpectedPlamsCount(int expectedPlamsCount) {
        ExpectedPlamsCount = expectedPlamsCount;
    }

    public String getPlotcode(){
        return PlotCode;
    }

    public void setPlotcode(String PlotCode){
        this.PlotCode=PlotCode;
    }

    public int getSeedsplanted(){
        return SeedsPlanted;
    }

    public void setSeedsplanted(int SeedsPlanted){
        this.SeedsPlanted=SeedsPlanted;
    }

    public int getPlamscount(){
        return PlamsCount;
    }

    public void setPlamscount(int PlamsCount){
        this.PlamsCount=PlamsCount;
    }

    public Integer getIstreesmissing(){
        return IsTreesMissing;
    }

    public void setIstreesmissing(Integer IsTreesMissing){
        this.IsTreesMissing=IsTreesMissing;
    }

    public int getMissingtreescount(){
        return MissingTreesCount;
    }

    public void setMissingtreescount(int MissingTreesCount){
        this.MissingTreesCount=MissingTreesCount;
    }

    public Integer getReasontypeid(){
        return ReasonTypeId;
    }

    public void setReasontypeid(Integer ReasonTypeId){
        this.ReasonTypeId=ReasonTypeId;
    }

    public String getComments(){
        return Comments;
    }

    public void setComments(String Comments){
        this.Comments=Comments;
    }

    public int getIsactive(){
        return IsActive;
    }

    public void setIsactive(int IsActive){
        this.IsActive=IsActive;
    }

    public int getCreatedbyuserid(){
        return CreatedByUserId;
    }

    public void setCreatedbyuserid(int CreatedByUserId){
        this.CreatedByUserId=CreatedByUserId;
    }

    public String getCreateddate(){
        return CreatedDate;
    }

    public void setCreateddate(String CreatedDate){
        this.CreatedDate=CreatedDate;
    }

    public int getUpdatedbyuserid(){
        return UpdatedByUserId;
    }

    public void setUpdatedbyuserid(int UpdatedByUserId){
        this.UpdatedByUserId=UpdatedByUserId;
    }

    public String getUpdateddate(){
        return UpdatedDate;
    }

    public void setUpdateddate(String UpdatedDate){
        this.UpdatedDate=UpdatedDate;
    }

    public int getServerupdatedstatus(){
        return ServerUpdatedStatus;
    }

    public void setServerupdatedstatus(int ServerUpdatedStatus){
        this.ServerUpdatedStatus=ServerUpdatedStatus;
    }

    public String getCropMaintenanceCode() {
        return CropMaintenanceCode;
    }

    public void setCropMaintenanceCode(String cropMaintenanceCode) {
        CropMaintenanceCode = cropMaintenanceCode;
    }
}
