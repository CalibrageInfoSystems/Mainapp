package com.oilpalm3f.mainapp.dbmodels;

public class PlotGradingDetails {

    private String PlotCode;
    private Double Ripen;
    private Double UnderRipe;
    private Double UnRipen;
    private Double OverRipe;
    private Double Diseased;
    private Double EmptyBunches;

    public String getPlotCode() {
        return PlotCode;
    }

    public void setPlotCode(String plotCode) {
        PlotCode = plotCode;
    }

    public Double getRipen() {
        return Ripen;
    }

    public void setRipen(Double ripen) {
        Ripen = ripen;
    }

    public Double getUnderRipe() {
        return UnderRipe;
    }

    public void setUnderRipe(Double underRipe) {
        UnderRipe = underRipe;
    }

    public Double getUnRipen() {
        return UnRipen;
    }

    public void setUnRipen(Double unRipen) {
        UnRipen = unRipen;
    }

    public Double getOverRipe() {
        return OverRipe;
    }

    public void setOverRipe(Double overRipe) {
        OverRipe = overRipe;
    }

    public Double getDiseased() {
        return Diseased;
    }

    public void setDiseased(Double diseased) {
        Diseased = diseased;
    }

    public Double getEmptyBunches() {
        return EmptyBunches;
    }

    public void setEmptyBunches(Double emptyBunches) {
        EmptyBunches = emptyBunches;
    }
}
