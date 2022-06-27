package com.oilpalm3f.mainapp.areaextension;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by latitude on 04-05-2017.
 */

//Soil Type Model
public class soilTypeModel {
    @SerializedName("soilType")
    @Expose
    private Integer soilType;
    @SerializedName("powerAvailability")
    @Expose
    private Boolean powerAvailability;
    @SerializedName("powerHours")
    @Expose
    private String powerHours;
    @SerializedName("plotPrioritization")
    @Expose
    private Integer plotPrioritization;
    @SerializedName("comments")
    @Expose
    private String comments;

    public Integer getSoilType() {
        return soilType;
    }

    public void setSoilType(Integer soilType) {
        this.soilType = soilType;
    }

    public Boolean getPowerAvailability() {
        return powerAvailability;
    }

    public void setPowerAvailability(Boolean powerAvailability) {
        this.powerAvailability = powerAvailability;
    }

    public String getPowerHours() {
        return powerHours;
    }

    public void setPowerHours(String powerHours) {
        this.powerHours = powerHours;
    }

    public Integer getPlotPrioritization() {
        return plotPrioritization;
    }

    public void setPlotPrioritization(Integer plotPrioritization) {
        this.plotPrioritization = plotPrioritization;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
