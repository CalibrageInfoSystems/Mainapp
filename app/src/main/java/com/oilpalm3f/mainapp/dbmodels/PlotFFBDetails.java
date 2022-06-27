package com.oilpalm3f.mainapp.dbmodels;

public class PlotFFBDetails {
    private String Code;
    private Double YieldPerHectare;
    private Double ExpectedYieldPerHectare;

    public String getCode() {
        return Code;
    }

    public void setCode(String plotCode) {
        Code = plotCode;
    }

    public Double getYieldPerHectare() {
        return YieldPerHectare;
    }

    public void setYieldPerHectare(Double yieldPerHectare) {
        YieldPerHectare = yieldPerHectare;
    }

    public Double getExpectedYieldPerHectare() {
        return ExpectedYieldPerHectare;
    }

    public void setExpectedYieldPerHectare(Double expectedYieldPerHectare) {
        ExpectedYieldPerHectare = expectedYieldPerHectare;
    }

}


