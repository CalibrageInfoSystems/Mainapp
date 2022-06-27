package com.oilpalm3f.mainapp.datasync.refreshsyncmodel;

/**
 * Created by skasam on 10/3/2016.
 */
public class FarmerPlotBoundaries {

    private int BoundaryId ;
    private String FarmerCode ;
    private String PlotCode ;
    private Double Latitude ;
    private Double Longitude ;
    private String CreatedBy ;
    private String CreatedDate ;
    private String UpdatedBy ;
    private String UpdatedDate ;
    private int ServerUpdatedStatus ;

    public int getBoundaryId() {
        return BoundaryId;
    }

    public void setBoundaryId(int boundaryId) {
        BoundaryId = boundaryId;
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

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
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
