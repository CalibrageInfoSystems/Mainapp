package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 15/06/17.
 */

public class ActivityLog {
    private String FarmerCode;
    private String PlotCode;
    private String CollectionCode;
    private String ComplaintCode;
    private int ActivityTypeId;
    private int CreatedByUserId;
    private String CreatedDate;
    private int ServerUpdatedStatus;
    private String ConsignmentCode;

    public String getConsignmentCode() {
        return ConsignmentCode;
    }

    public void setConsignmentCode(String consignmentCode) {
        ConsignmentCode = consignmentCode;
    }

    public String getFarmerCode() {
        return FarmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        FarmerCode = farmerCode;
    }

    public String getPlotCode() {
        return PlotCode;
    }

    public void setPlotCode(String plotCode) {
        PlotCode = plotCode;
    }

    public String getCollectionCode() {
        return CollectionCode;
    }

    public void setCollectionCode(String collectionCode) {
        CollectionCode = collectionCode;
    }

    public String getComplaintCode() {
        return ComplaintCode;
    }

    public void setComplaintCode(String complaintCode) {
        ComplaintCode = complaintCode;
    }

    public int getActivityTypeId() {
        return ActivityTypeId;
    }

    public void setActivityTypeId(int activityTypeId) {
        ActivityTypeId = activityTypeId;
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

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }
}
