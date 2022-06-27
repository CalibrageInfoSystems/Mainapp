package com.oilpalm3f.mainapp.dbmodels;

import java.io.Serializable;

/**
 * Created by RAMESH BABU on 02-07-2017.
 */

public class ComplaintsDetails implements Serializable {
    private String ComplaintId;
    private String farmerFirstName;
    private String  farmerLastName;
    private String Village;
    private String PlotId;
    private String ComplaintName;
    private String ComplaintRaisedon;
    private String Status;
    private String ComplaintTypeId;
    private String AssigntoUserId;
    private String StatusTypeId;
    private String CriticalityByTypeId;
    private String complaintTypeName;
    private String complaintStatusTypeName;
    private String complaintCreatedBy;


    public String getComplaintId() {
        return ComplaintId;
    }

    public void setComplaintId(String complaintId) {
        ComplaintId = complaintId;
    }

    public String getfarmerFirstName() {
        return farmerFirstName;
    }

    public void setfarmerFirstName(String farmerFirstName) {
        this.farmerFirstName = farmerFirstName;
    }

    public String getVillage() {
        return Village;
    }

    public void setVillage(String village) {
        Village = village;
    }

    public String getPlotId() {
        return PlotId;
    }

    public void setPlotId(String plotId) {
        PlotId = plotId;
    }

    public String getComplaintName() {
        return ComplaintName;
    }

    public void setComplaintName(String complaintName) {
        ComplaintName = complaintName;
    }

    public String getComplaintRaisedon() {
        return ComplaintRaisedon;
    }

    public void setComplaintRaisedon(String complaintRaisedon) {
        ComplaintRaisedon = complaintRaisedon;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    public String getComplaintTypeId() {
        return ComplaintTypeId;
    }

    public void setComplaintTypeId(String complaintTypeId) {
        ComplaintTypeId = complaintTypeId;
    }

    public String getAssigntoUserId() {
        return AssigntoUserId;
    }

    public void setAssigntoUserId(String assigntoUserId) {
        AssigntoUserId = assigntoUserId;
    }

    public String getStatusTypeId() {
        return StatusTypeId;
    }

    public void setStatusTypeId(String statusTypeId) {
        StatusTypeId = statusTypeId;
    }

    public String getFarmerLastName() {
        return farmerLastName;
    }

    public void setFarmerLastName(String farmerLastName) {
        this.farmerLastName = farmerLastName;
    }

    public String getCriticalityByTypeId() {
        return CriticalityByTypeId;
    }

    public void setCriticalityByTypeId(String criticalityByTypeId) {
        CriticalityByTypeId = criticalityByTypeId;
    }

    public String getComplaintTypeName() {
        return complaintTypeName;
    }

    public void setComplaintTypeName(String complaintTypeName) {
        this.complaintTypeName = complaintTypeName;
    }

    public String getComplaintStatusTypeName() {
        return complaintStatusTypeName;
    }

    public void setComplaintStatusTypeName(String complaintStatusTypeName) {
        this.complaintStatusTypeName = complaintStatusTypeName;
    }

    public String getComplaintCreatedBy() {
        return complaintCreatedBy;
    }

    public void setComplaintCreatedBy(String complaintCreatedBy) {
        this.complaintCreatedBy = complaintCreatedBy;
    }
}
