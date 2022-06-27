package com.oilpalm3f.mainapp.prospectiveFarmers;

/**
 * Created by siva on 11/06/17.
 */

public class ProspectivePlotsModel {
    private String plotID;
    private double plotArea;
    private String plotVillageName;
    private String mandalName;
    private String plotIncome;
    private int potentialScore;
    private String plotCrops;
    private String lastVisitedDate;

    public String getPlotID() {
        return plotID;
    }

    public void setPlotID(String plotID) {
        this.plotID = plotID;
    }

    public double getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(double plotArea) {
        this.plotArea = plotArea;
    }

    public String getPlotVillageName() {
        return plotVillageName;
    }

    public void setPlotVillageName(String plotVillageName) {
        this.plotVillageName = plotVillageName;
    }

    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }

    public String getPlotIncome() {
        return plotIncome;
    }

    public void setPlotIncome(String plotIncome) {
        this.plotIncome = plotIncome;
    }

    public int getPotentialScore() {
        return potentialScore;
    }

    public void setPotentialScore(int potentialScore) {
        this.potentialScore = potentialScore;
    }

    public String getPlotCrops() {
        return plotCrops;
    }

    public void setPlotCrops(String plotCrops) {
        this.plotCrops = plotCrops;
    }

    public String getLastVisitedDate() {
        return lastVisitedDate;
    }

    public void setLastVisitedDate(String lastVisitedDate) {
        this.lastVisitedDate = lastVisitedDate;
    }
}
