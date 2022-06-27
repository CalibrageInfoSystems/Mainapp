package com.oilpalm3f.mainapp.conversion;

/**
 * Created by pc on 29-09-2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//intercrop model
public class InterCropModel {

    @SerializedName("InterCropCount")
    @Expose
    private String interCropCount;
    @SerializedName("InterCropInYear")
    @Expose
    private String interCropInYear;

    private int interCropId;

    /**
     *
     * @return
     * The interCropCount
     */
    public String getInterCropCount() {
        return interCropCount;
    }

    /**
     *
     * @param interCropCount
     * The InterCropCount
     */
    public void setInterCropCount(String interCropCount) {
        this.interCropCount = interCropCount;
    }

    /**
     *
     * @return
     * The interCropInYear
     */
    public String getInterCropInYear() {
        return interCropInYear;
    }

    /**
     *
     * @param interCropInYear
     * The InterCropInYear
     */
    public void setInterCropInYear(String interCropInYear) {
        this.interCropInYear = interCropInYear;
    }

    public int getInterCropId() {
        return interCropId;
    }

    public void setInterCropId(int interCropId) {
        this.interCropId = interCropId;
    }
}