package com.oilpalm3f.mainapp.dbmodels;

public class CollectionPlotXref {
    private String PlotCode;
    private String CollectionCode;
    private int ServerUpdatedStatus;
    private int IsMainFarmerPlot;

    public int isMainFarmerPlot() {
        return IsMainFarmerPlot;
    }

    public void setMainFarmerPlot(int mainFarmerPlot) {
        IsMainFarmerPlot = mainFarmerPlot;
    }

    public Float getYieldPerHectar() {
        return YieldPerHectar;
    }

    public void setYieldPerHectar(Float yieldPerHectar) {
        YieldPerHectar = yieldPerHectar;
    }

    private Float YieldPerHectar;
    private Float NetWeightPerPlot;

    public Float getNetWeightPerPlot() {
        return NetWeightPerPlot;
    }

    public void setNetWeightPerPlot(Float netWeightPerPlot) {
        NetWeightPerPlot = netWeightPerPlot;
    }

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }

    public String getCollectionCode() {
        return CollectionCode;
    }

    public void setCollectionCode(String collectionCode) {
        CollectionCode = collectionCode;
    }

    public String getPlotcode(){
        return PlotCode;
    }

    public void setPlotcode(String PlotCode){
        this.PlotCode=PlotCode;
    }
}
