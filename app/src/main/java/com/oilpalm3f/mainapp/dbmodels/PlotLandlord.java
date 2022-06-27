package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 25/05/17.
 */

public class PlotLandlord {
    private String PlotCode;
    private String LandLordName;
    private String LandLordContactNumber;
    private String LeaseStartDate;
    private String LeaseEndDate;
    private int IsActive;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;

    public String getPlotcode() {
        return PlotCode;
    }

    public void setPlotcode(String PlotCode) {
        this.PlotCode = PlotCode;
    }

    public String getLandlordname() {
        return LandLordName;
    }

    public void setLandlordname(String LandLordName) {
        this.LandLordName = LandLordName;
    }

    public String getLandlordcontactnumber() {
        return LandLordContactNumber;
    }

    public void setLandlordcontactnumber(String LandLordContactNumber) {
        this.LandLordContactNumber = LandLordContactNumber;
    }

    public String getLeasestartdate() {
        return LeaseStartDate;
    }

    public void setLeasestartdate(String LeaseStartDate) {
        this.LeaseStartDate = LeaseStartDate;
    }

    public String getLeaseenddate() {
        return LeaseEndDate;
    }

    public void setLeaseenddate(String LeaseEndDate) {
        this.LeaseEndDate = LeaseEndDate;
    }

    public int getIsactive() {
        return IsActive;
    }

    public void setIsactive(int IsActive) {
        this.IsActive = IsActive;
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

    public int getServerupdatedstatus() {
        return ServerUpdatedStatus;
    }

    public void setServerupdatedstatus(int ServerUpdatedStatus) {
        this.ServerUpdatedStatus = ServerUpdatedStatus;
    }

}
