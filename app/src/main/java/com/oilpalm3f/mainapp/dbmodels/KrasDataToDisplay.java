package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 07/09/17.
 */

public class KrasDataToDisplay {

    private Integer userKraId;
    private String kRACode;
    private String kRAName;
    private String uOM;
    private Double annualTarget;
    private Double monthlyAchievedTarget;
    private Double annualAchievedTarget;
    private Integer userId;
    private Integer monthNumber;
    private Double monthlyTarget;

    public Integer getUserKraId() {
        return userKraId;
    }

    public void setUserKraId(Integer userKraId) {
        this.userKraId = userKraId;
    }

    public String getkRACode() {
        return kRACode;
    }

    public void setkRACode(String kRACode) {
        this.kRACode = kRACode;
    }

    public String getkRAName() {
        return kRAName;
    }

    public void setkRAName(String kRAName) {
        this.kRAName = kRAName;
    }

    public String getuOM() {
        return uOM;
    }

    public void setuOM(String uOM) {
        this.uOM = uOM;
    }

    public Double getAnnualTarget() {
        return annualTarget;
    }

    public void setAnnualTarget(Double annualTarget) {
        this.annualTarget = annualTarget;
    }

    public Double getMonthlyAchievedTarget() {
        return monthlyAchievedTarget;
    }

    public void setMonthlyAchievedTarget(Double monthlyAchievedTarget) {
        this.monthlyAchievedTarget = monthlyAchievedTarget;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Double getAnnualAchievedTarget() {
        return annualAchievedTarget;
    }

    public void setAnnualAchievedTarget(Double annualAchievedTarget) {
        this.annualAchievedTarget = annualAchievedTarget;
    }
}
