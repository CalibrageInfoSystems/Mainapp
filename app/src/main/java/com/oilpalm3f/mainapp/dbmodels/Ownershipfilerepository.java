package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 25/05/17.
 */

public class Ownershipfilerepository {
    private String FarmerCode;
    private String PlotCode;
    private Integer ModuleTypeId;
    private String FileName;
    private String FileLocation;
    private String FileExtension;
    private String Comments;
    private int IsActive;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;

    public String getPlotcode(){
        return PlotCode;
    }

    public void setPlotcode(String PlotCode){
        this.PlotCode=PlotCode;
    }

    public Integer getModuletypeid(){
        return ModuleTypeId;
    }

    public void setModuletypeid(Integer ModuleTypeId){
        this.ModuleTypeId=ModuleTypeId;
    }

    public String getFilename(){
        return FileName;
    }

    public void setFilename(String FileName){
        this.FileName=FileName;
    }

    public String getFilelocation(){
        return FileLocation;
    }

    public void setFilelocation(String FileLocation){
        this.FileLocation=FileLocation;
    }

    public String getFileextension(){
        return FileExtension;
    }

    public void setFileextension(String FileExtension){
        this.FileExtension=FileExtension;
    }

    public String getComments(){
        return Comments;
    }

    public void setComments(String Comments){
        this.Comments=Comments;
    }

    public int getIsactive(){
        return IsActive;
    }

    public void setIsactive(int IsActive){
        this.IsActive=IsActive;
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

    public String getFarmerCode() {
        return FarmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        FarmerCode = farmerCode;
    }
}
