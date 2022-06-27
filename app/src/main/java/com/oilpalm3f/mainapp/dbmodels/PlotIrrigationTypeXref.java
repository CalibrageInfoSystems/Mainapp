package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by skasam on 5/18/2017.
 */

public class PlotIrrigationTypeXref {

    private String PlotCode;
    private String Name;
    private Integer IrrigationTypeId;
    private Integer RecmIrrgId;
    private int IsActive;
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

    public String getName(){
        return Name;
    }

    public void setName(String Name){
        this.Name=Name;
    }

    public int getIrrigationtypeid(){
        return IrrigationTypeId;
    }

    public void setIrrigationtypeid(Integer IrrigationTypeId){
        this.IrrigationTypeId=IrrigationTypeId;
    }

    public Integer getRecmIrrgId() {
        return RecmIrrgId;
    }

    public void setRecmIrrgId(Integer recmIrrgId) {
        RecmIrrgId = recmIrrgId;
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
}
