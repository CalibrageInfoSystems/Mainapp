package com.oilpalm3f.mainapp.dbmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTargetsByUserIdandFinancialYear {

    @SerializedName("UserKraId")
    @Expose
    private Integer userKraId;
    @SerializedName("KRACode")
    @Expose
    private String kRACode;
    @SerializedName("KRAName")
    @Expose
    private String kRAName;
    @SerializedName("UOM")
    @Expose
    private String uOM;
    @SerializedName("AnnualTarget")
    @Expose
    private Double annualTarget;
    @SerializedName("AchievedTarget")
    @Expose
    private Double achievedTarget;
    @SerializedName("UserId")
    @Expose
    private Integer userId;

    public Integer getUserKraId() {
        return userKraId;
    }

    public void setUserKraId(Integer userKraId) {
        this.userKraId = userKraId;
    }

    public String getKRACode() {
        return kRACode;
    }

    public void setKRACode(String kRACode) {
        this.kRACode = kRACode;
    }

    public String getKRAName() {
        return kRAName;
    }

    public void setKRAName(String kRAName) {
        this.kRAName = kRAName;
    }

    public String getUOM() {
        return uOM;
    }

    public void setUOM(String uOM) {
        this.uOM = uOM;
    }

    public Double getAnnualTarget() {
        return annualTarget;
    }

    public void setAnnualTarget(Double annualTarget) {
        this.annualTarget = annualTarget;
    }

    public Double getAchievedTarget() {
        return achievedTarget;
    }

    public void setAchievedTarget(Double achievedTarget) {
        this.achievedTarget = achievedTarget;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}