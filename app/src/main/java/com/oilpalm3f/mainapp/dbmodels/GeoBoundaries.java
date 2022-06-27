package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by skasam on 5/18/2017.
 */

public class GeoBoundaries {

    private String PlotCode;
    private double Latitude;
    private double Longitude;
    private Integer GeoCategoryTypeId;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;
    private String CropMaintenanceCode;

    public String getPlotcode(){
        return PlotCode;
    }

    public void setPlotcode(String PlotCode){
        this.PlotCode=PlotCode;
    }

    public double getLatitude(){
        return Latitude;
    }

    public void setLatitude(double Latitude){
        this.Latitude=Latitude;
    }

    public double getLongitude(){
        return Longitude;
    }

    public void setLongitude(double Longitude){
        this.Longitude=Longitude;
    }

    public Integer getGeocategorytypeid(){
        return GeoCategoryTypeId;
    }

    public void setGeocategorytypeid(Integer GeoCategoryTypeId){
        this.GeoCategoryTypeId=GeoCategoryTypeId;
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
