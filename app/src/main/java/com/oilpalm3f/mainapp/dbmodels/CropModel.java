package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 20/05/17.
 */

public class CropModel {
    public String cropName;
    public int cropId;
    public int recId;
    public String recName;
    public int isActive;

    public CropModel(String cropName, int cropId, String value) {
        this.cropName = cropName;
        this.cropId = cropId;
        this.recName = value;
    }

    public CropModel(String cropName, int cropId,int recId, String value) {
        this.cropName = cropName;
        this.cropId = cropId;
        this.recId = recId;
        this.recName = value;
    }

    public CropModel(String cropName, int cropId, String value, int active) {
        this.cropName = cropName;
        this.cropId = cropId;
        this.recName = value;
        this.isActive = active;
    }

}
