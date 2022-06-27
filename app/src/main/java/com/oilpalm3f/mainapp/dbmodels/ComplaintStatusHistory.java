package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by Calibrage11 on 8/8/2017.
 */

public class ComplaintStatusHistory {

    private String ComplaintCode;
    private String StatusTypeId;
    private String AssigntoUserId;
    private String Comments;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;
    private int IsActive;


    public String getComplaintCode() {
        return ComplaintCode;
    }

    public void setComplaintCode(String complaintCode) {
        ComplaintCode = complaintCode;
    }

    public String getStatusTypeId() {
        return StatusTypeId;
    }

    public void setStatusTypeId(String statusTypeId) {
        StatusTypeId = statusTypeId;
    }

    public String getAssigntoUserId() {
        return AssigntoUserId;
    }

    public void setAssigntoUserId(String assigntoUserId) {
        AssigntoUserId = assigntoUserId;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
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

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }
}
