package com.oilpalm3f.mainapp.datasync.refreshsyncmodel;

/**
 * Created by skasam on 10/3/2016.
 */
public class FarmerNeighbourPlots {

    private int NeighboringPlotId ;
    private String FarmerCode ;
    private String PlotCode ;
    private String NeighbourPlot ;
    private int CropCode ;
    private String OtherCrop ;
    private String CreatedBy ;
    private String CreatedDate ;
    private String UpdatedBy ;
    private String UpdatedDate ;
    private int ServerUpdatedStatus ;

    public int getNeighboringPlotId() {
        return NeighboringPlotId;
    }

    public void setNeighboringPlotId(int neighboringPlotId) {
        NeighboringPlotId = neighboringPlotId;
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

    public String getNeighbourPlot() {
        return NeighbourPlot;
    }

    public void setNeighbourPlot(String neighbourPlot) {
        NeighbourPlot = neighbourPlot;
    }

    public int getCropCode() {
        return CropCode;
    }

    public void setCropCode(int cropCode) {
        CropCode = cropCode;
    }

    public String getOtherCrop() {
        return OtherCrop;
    }

    public void setOtherCrop(String otherCrop) {
        OtherCrop = otherCrop;
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
