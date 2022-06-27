package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 21/05/17.
 */

public class CookingOil {

    public String getMarketSurveyCode() {
        return MarketSurveyCode;
    }

    public void setMarketSurveyCode(String marketSurveyCode) {
        MarketSurveyCode = marketSurveyCode;
    }

    private String MarketSurveyCode;
    private Integer CookingOilTypeId;
    private String BrandName;
    private double MonthlyQuantity;
    private double TotalPaidAmount;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }

    private int ServerUpdatedStatus;

    public Integer getCookingoiltypeid() {
        return CookingOilTypeId;
    }

    public void setCookingoiltypeid(Integer CookingOilTypeId) {
        this.CookingOilTypeId = CookingOilTypeId;
    }

    public String getBrandname() {
        return BrandName;
    }

    public void setBrandname(String BrandName) {
        this.BrandName = BrandName;
    }

    public double getMonthlyquantity() {
        return MonthlyQuantity;
    }

    public void setMonthlyquantity(double MonthlyQuantity) {
        this.MonthlyQuantity = MonthlyQuantity;
    }

    public double getTotalpaidamount() {
        return TotalPaidAmount;
    }

    public void setTotalpaidamount(double TotalPaidAmount) {
        this.TotalPaidAmount = TotalPaidAmount;
    }

    public int getCreatedbyuserid() {
        return CreatedByUserId;
    }

    public void setCreatedbyuserid(int CreatedByUserId) {
        this.CreatedByUserId = CreatedByUserId;
    }

    public String getCreateddate() {
        return CreatedDate;
    }

    public void setCreateddate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public int getUpdatedbyuserid() {
        return UpdatedByUserId;
    }

    public void setUpdatedbyuserid(int UpdatedByUserId) {
        this.UpdatedByUserId = UpdatedByUserId;
    }

    public String getUpdateddate() {
        return UpdatedDate;
    }

    public void setUpdateddate(String UpdatedDate) {
        this.UpdatedDate = UpdatedDate;
    }
}
