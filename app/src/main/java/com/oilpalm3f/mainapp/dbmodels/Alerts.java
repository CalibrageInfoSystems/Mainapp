package com.oilpalm3f.mainapp.dbmodels;

public class Alerts {
    private int Id;
    private int AlertTypeId;
    private String PlotCode;
    private int CreatedByUserId;
    private String CreatedDate;
    private int ServerUpdatedStatus;
    private int IsRead;
    private String Desc;
    private String Name;
    private int UserId;
    private String ComplaintCode;
    private String HTMLDesc;
    private int UpdatedByUserId;
    private String UpdatedDate;

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }

    public int getIsRead() {
        return IsRead;
    }

    public void setIsRead(int isRead) {
        IsRead = isRead;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getComplaintCode() {
        return ComplaintCode;
    }

    public void setComplaintCode(String complaintCode) {
        ComplaintCode = complaintCode;
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

    public int getAlertTypeId() {
        return AlertTypeId;
    }

    public void setAlertTypeId(int alertType) {
        AlertTypeId = alertType;
    }

    public String getPlotCode() {
        return PlotCode;
    }

    public void setPlotCode(String plotCode) {
        PlotCode = plotCode;
    }

    public String getHTMLDesc() {
        return HTMLDesc;
    }

    public void setHTMLDesc(String HTMLDesc) {
        this.HTMLDesc = HTMLDesc;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
}
