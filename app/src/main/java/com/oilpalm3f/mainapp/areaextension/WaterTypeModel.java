package com.oilpalm3f.mainapp.areaextension;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by latitude on 26-04-2017.
 */

//Water Details Model
public class WaterTypeModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sourceOfWater")
    @Expose
    private String sourceOfWater;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("discharge")
    @Expose
    private String discharge;
    @SerializedName("canal")
    @Expose
    private String canal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceOfWater() {
        return sourceOfWater;
    }

    public void setSourceOfWater(String sourceOfWater) {
        this.sourceOfWater = sourceOfWater;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDischarge() {
        return discharge;
    }

    public void setDischarge(String discharge) {
        this.discharge = discharge;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

}
