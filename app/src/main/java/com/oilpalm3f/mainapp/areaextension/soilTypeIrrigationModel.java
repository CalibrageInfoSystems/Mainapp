package com.oilpalm3f.mainapp.areaextension;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by latitude on 04-05-2017.
 */

//Soil/Power model
public class soilTypeIrrigationModel {
    @SerializedName("irigationCount")
    @Expose
    private String irigationCount;
    @SerializedName("irigationvalue")
    @Expose
    private String irigationvalue;

    public String getIrigationCount() {
        return irigationCount;
    }

    public void setIrigationCount(String irigationCount) {
        this.irigationCount = irigationCount;
    }

    public String getIrigationvalue() {
        return irigationvalue;
    }

    public void setIrigationvalue(String irigationvalue) {
        this.irigationvalue = irigationvalue;
    }

}
