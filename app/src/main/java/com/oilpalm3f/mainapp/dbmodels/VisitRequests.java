package com.oilpalm3f.mainapp.dbmodels;

public class VisitRequests {
    private String RequestCode;
    private String FarmerCode;
    private String PlotCode;
    private String CreatedDate;
    private int StatusTypeId;
    private int IsFarmerRequest;
    private int Comments;
    private int IssueTypeId;
    private int FileTypeId;
    private String Url;

    public String getRequestCode() {
        return RequestCode;
    }

    public void setRequestCode(String requestCode) {
        RequestCode = requestCode;
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

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public int getStatusTypeId() {
        return StatusTypeId;
    }

    public void setStatusTypeId(int statusTypeId) {
        StatusTypeId = statusTypeId;
    }

    public int getIsFarmerRequest() {
        return IsFarmerRequest;
    }

    public void setIsFarmerRequest(int isFarmerRequest) {
        IsFarmerRequest = isFarmerRequest;
    }

    public int getComments() {
        return Comments;
    }

    public void setComments(int comments) {
        Comments = comments;
    }

    public int getIssueTypeId() {
        return IssueTypeId;
    }

    public void setIssueTypeId(int issueTypeId) {
        IssueTypeId = issueTypeId;
    }

    public int getFileTypeId() {
        return FileTypeId;
    }

    public void setFileTypeId(int fileTypeId) {
        FileTypeId = fileTypeId;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
