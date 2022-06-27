package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by skasam on 5/18/2017.
 */

public class SoilResource {

    private String PlotCode;
    private Integer SoilTypeId;
    private Integer SoilNatureId;
    private float IrrigatedArea;
    private Integer IsPowerAvailable;
    private double AvailablePowerHours;
    private Integer PrioritizationTypeId;
    private String Comments;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;


    public String getPlotcode(){
        return PlotCode;
    }

    public void setPlotcode(String PlotCode){
        this.PlotCode=PlotCode;
    }

    public Integer getSoiltypeid(){
        return SoilTypeId;
    }

    public void setSoiltypeid(Integer SoilTypeId){
        this.SoilTypeId=SoilTypeId;
    }

    public Integer getIspoweravailable(){
        return IsPowerAvailable;
    }

    public void setIspoweravailable(Integer IsPowerAvailable){
        this.IsPowerAvailable=IsPowerAvailable;
    }

    public double getAvailablepowerhours(){
        return AvailablePowerHours;
    }

    public void setAvailablepowerhours(double AvailablePowerHours){
        this.AvailablePowerHours=AvailablePowerHours;
    }

    public Integer getPrioritizationtypeid(){
        return PrioritizationTypeId;
    }

    public void setPrioritizationtypeid(Integer PrioritizationTypeId){
        this.PrioritizationTypeId=PrioritizationTypeId;
    }

    public String getComments(){
        return Comments;
    }

    public void setComments(String Comments){
        this.Comments=Comments;
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

    public Integer getSoilNatureId() {
        return SoilNatureId;
    }

    public void setSoilNatureId(Integer soilNatureId) {
        SoilNatureId = soilNatureId;
    }

    public float getIrrigatedArea() {
        return IrrigatedArea;
    }

    public void setIrrigatedArea(float irrigatedArea) {
        IrrigatedArea = irrigatedArea;
    }
}
