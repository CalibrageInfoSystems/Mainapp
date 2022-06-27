package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by skasam on 5/18/2017.
 */

public class FollowUp {

    private String PlotCode;
    private Integer IsFarmerReadytoConvert;
    private String IssueDetails;
    private String Comments;
    private Integer PotentialScore;
    private String HarvestingMonth;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;
    private String ExpectedMonthofSowing;

    public String getPlotcode(){
        return PlotCode;
    }

    public void setPlotcode(String PlotCode){
        this.PlotCode=PlotCode;
    }

    public Integer getIsfarmerreadytoconvert(){
        return IsFarmerReadytoConvert;
    }

    public void setIsfarmerreadytoconvert(Integer IsFarmerReadytoConvert){
        this.IsFarmerReadytoConvert=IsFarmerReadytoConvert;
    }

    public String getIssuedetails(){
        return IssueDetails;
    }

    public void setIssuedetails(String IssueDetails){
        this.IssueDetails=IssueDetails;
    }

    public String getComments(){
        return Comments;
    }

    public void setComments(String Comments){
        this.Comments=Comments;
    }

    public Integer getPotentialscore(){
        return PotentialScore;
    }

    public void setPotentialscore(Integer PotentialScore){
        this.PotentialScore=PotentialScore;
    }

    public String getHarvestingmonth(){
        return HarvestingMonth;
    }

    public void setHarvestingmonth(String HarvestingMonth){
        this.HarvestingMonth=HarvestingMonth;
    }

    public int getCreatedbyuserid(){
        return CreatedByUserId;
    }

    public void setCreatedbyuserid(int CreatedByUserId){
        this.CreatedByUserId=CreatedByUserId;
    }

    public String getCreateddate(){
        return CreatedDate;
    }

    public void setCreateddate(String CreatedDate){
        this.CreatedDate=CreatedDate;
    }

    public int getUpdatedbyuserid(){
        return UpdatedByUserId;
    }

    public void setUpdatedbyuserid(int UpdatedByUserId){
        this.UpdatedByUserId=UpdatedByUserId;
    }

    public String getUpdateddate(){
        return UpdatedDate;
    }

    public void setUpdateddate(String UpdatedDate){
        this.UpdatedDate=UpdatedDate;
    }

    public int getServerupdatedstatus(){
        return ServerUpdatedStatus;
    }

    public void setServerupdatedstatus(int ServerUpdatedStatus){
        this.ServerUpdatedStatus=ServerUpdatedStatus;
    }

    public String getExpectedMonthofSowing() {
        return ExpectedMonthofSowing;
    }

    public void setExpectedMonthofSowing(String expectedMonthofSowing) {
       this.ExpectedMonthofSowing = expectedMonthofSowing;
    }
}
