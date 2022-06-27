package com.oilpalm3f.mainapp.datasync.refreshsyncmodel;

import com.google.gson.annotations.Expose;

/**
 * Created by skasam on 10/1/2016.
 */
public class FarmerPersonalDetailsModel {


    private String FarmerCode ;
    private String FarmerFirstName ;
    private String FarmerMiddleName ;
    private String FarmerLastName ;
    private String DOB ;
    private String Gender ;
    private String FatherName ;
    private String MotherName ;
    private String EmailAddress ;
    private int Age ;
    @Expose
    private Integer CastId ;
    private String PrimaryContactNumber ;
    private String SecondaryContactNumber ;
    private String POCContactInfo ;
    private int CareTaker ;
    private String Address1 ;
    private String Address2 ;
    private String Landmark ;
    private int StateCode ;
    private int DistrictCode ;
    private int MandalCode ;
    private int VillageCode ;
    private Integer CityCode ;
    private String Pincode ;
    private String FarmerPhoto ;
    private int Active ;
    private String CreatedBy ;
    private String CreatedDate ;
    private String UpdatedBy ;
    private String UpdatedDate ;
    private int ServerUpdatedStatus ;

    public String getFarmerCode() {
        return FarmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        FarmerCode = farmerCode;
    }

    public String getFarmerFirstName() {
        return FarmerFirstName;
    }

    public void setFarmerFirstName(String farmerFirstName) {
        FarmerFirstName = farmerFirstName;
    }

    public String getFarmerMiddleName() {
        return FarmerMiddleName;
    }

    public void setFarmerMiddleName(String farmerMiddleName) {
        FarmerMiddleName = farmerMiddleName;
    }

    public String getFarmerLastName() {
        return FarmerLastName;
    }

    public void setFarmerLastName(String farmerLastName) {
        FarmerLastName = farmerLastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getMotherName() {
        return MotherName;
    }

    public void setMotherName(String motherName) {
        MotherName = motherName;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public Integer getCastId() {
        return CastId;
    }

    public void setCastId(Integer castId) {
        CastId = castId;
    }

    public String getPrimaryContactNumber() {
        return PrimaryContactNumber;
    }

    public void setPrimaryContactNumber(String primaryContactNumber) {
        PrimaryContactNumber = primaryContactNumber;
    }

    public String getSecondaryContactNumber() {
        return SecondaryContactNumber;
    }

    public void setSecondaryContactNumber(String secondaryContactNumber) {
        SecondaryContactNumber = secondaryContactNumber;
    }

    public String getPOCContactInfo() {
        return POCContactInfo;
    }

    public void setPOCContactInfo(String POCContactInfo) {
        this.POCContactInfo = POCContactInfo;
    }

    public int getCareTaker() {
        return CareTaker;
    }

    public void setCareTaker(int careTaker) {
        CareTaker = careTaker;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public int getStateCode() {
        return StateCode;
    }

    public void setStateCode(int stateCode) {
        StateCode = stateCode;
    }

    public int getDistrictCode() {
        return DistrictCode;
    }

    public void setDistrictCode(int districtCode) {
        DistrictCode = districtCode;
    }

    public int getMandalCode() {
        return MandalCode;
    }

    public void setMandalCode(int mandalCode) {
        MandalCode = mandalCode;
    }

    public int getVillageCode() {
        return VillageCode;
    }

    public void setVillageCode(int villageCode) {
        VillageCode = villageCode;
    }

    public int getCityCode() {
        return CityCode;
    }

    public void setCityCode(Integer cityCode) {
        CityCode = cityCode;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getFarmerPhoto() {
        return FarmerPhoto;
    }

    public void setFarmerPhoto(String farmerPhoto) {
        FarmerPhoto = farmerPhoto;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
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
}
