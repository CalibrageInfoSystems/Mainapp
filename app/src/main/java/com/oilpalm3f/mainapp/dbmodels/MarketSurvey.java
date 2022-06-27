package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 21/05/17.
 */

public class MarketSurvey {
    private int VillageId;
    private String Code;
    private String SurveyDate;
    private String FarmerCode;
    private String FarmerName;
    private int FamilyMembersCount;
    private String Reason;
    private Integer IsFarmerWillingtoUse;
    private double EstimatedAmounttoPay;
    private Integer IsFarmerUseSmartPhone;
    private Integer IsCattleExist;
    private Integer CattleTypeId;
    private int CattlesCount;
    private Integer IsFarmerHavingOwnVehicle;
    private Integer VehicleTypeId;
    private int IsActive;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;
    private String FarmerMobileNumber;

    public String getFarmerMobileNumber() {
        return FarmerMobileNumber;
    }

    public void setFarmerMobileNumber(String farmerMobileNumber) {
        FarmerMobileNumber = farmerMobileNumber;
    }

    public int getVillageId() {
        return VillageId;
    }

    public void setVillageId(int villageId) {
        VillageId = villageId;
    }

    public String getSurveyDate() {
        return SurveyDate;
    }

    public void setSurveyDate(String surveyDate) {
        SurveyDate = surveyDate;
    }


    public String getFarmerCode() {
        return FarmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        FarmerCode = farmerCode;
    }

    public String getFarmerName() {
        return FarmerName;
    }

    public void setFarmerName(String farmerName) {
        FarmerName = farmerName;
    }

    public int getFamilyMembersCount() {
        return FamilyMembersCount;
    }

    public void setFamilyMembersCount(int familyMembersCount) {
        FamilyMembersCount = familyMembersCount;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public Integer getIsFarmerWillingtoUse() {
        return IsFarmerWillingtoUse;
    }

    public void setIsFarmerWillingtoUse(Integer isFarmerWillingtoUse) {
        IsFarmerWillingtoUse = isFarmerWillingtoUse;
    }

    public double getEstimatedAmounttoPay() {
        return EstimatedAmounttoPay;
    }

    public void setEstimatedAmounttoPay(double estimatedAmounttoPay) {
        EstimatedAmounttoPay = estimatedAmounttoPay;
    }

    public Integer getIsFarmerUseSmartPhone() {
        return IsFarmerUseSmartPhone;
    }

    public void setIsFarmerUseSmartPhone(Integer isFarmerUseSmartPhone) {
        IsFarmerUseSmartPhone = isFarmerUseSmartPhone;
    }

    public Integer getIsCattleExist() {
        return IsCattleExist;
    }

    public void setIsCattleExist(Integer isCattleExist) {
        IsCattleExist = isCattleExist;
    }

    public Integer getCattleTypeId() {
        return CattleTypeId;
    }

    public void setCattleTypeId(Integer cattleTypeId) {
        CattleTypeId = cattleTypeId;
    }

    public int getCattlesCount() {
        return CattlesCount;
    }

    public void setCattlesCount(int cattlesCount) {
        CattlesCount = cattlesCount;
    }

    public Integer getIsFarmerHavingOwnVehicle() {
        return IsFarmerHavingOwnVehicle;
    }

    public void setIsFarmerHavingOwnVehicle(Integer isFarmerHavingOwnVehicle) {
        IsFarmerHavingOwnVehicle = isFarmerHavingOwnVehicle;
    }

    public Integer getVehicleTypeId() {
        return VehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        VehicleTypeId = vehicleTypeId;
    }


    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }

    public int getCreatedByUserId() {
        return CreatedByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        CreatedByUserId = createdByUserId;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public int getUpdatedByUserId() {
        return UpdatedByUserId;
    }

    public void setUpdatedByUserId(int updatedByUserId) {
        UpdatedByUserId = updatedByUserId;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
