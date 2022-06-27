package com.oilpalm3f.mainapp.dbmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMonthlyTargetsByUserIdandFinancialYear {

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
    @SerializedName("MonthNumber")
    @Expose
    private Integer monthNumber;
    @SerializedName("MonthlyTarget")
    @Expose
    private Double monthlyTarget;
    @SerializedName("AchievedTarget")
    @Expose
    private Object achievedTarget;
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

    public Integer getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(Integer monthNumber) {
        this.monthNumber = monthNumber;
    }

    public Double getMonthlyTarget() {
        return monthlyTarget;
    }

    public void setMonthlyTarget(Double monthlyTarget) {
        this.monthlyTarget = monthlyTarget;
    }

    public Object getAchievedTarget() {
        return achievedTarget;
    }

    public void setAchievedTarget(Object achievedTarget) {
        this.achievedTarget = achievedTarget;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}




