package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by skasam on 5/18/2017.
 */

public class WaterResource {

    private String  PlotCode ;
    private Integer  SourceofWaterId ;
    private Integer  BorewellNumber ;
    private double  WaterDischargeCapacity ;
    private double  CanalWater ;
    private int  IsActive ;
    private int  CreatedByUserId ;
    private String  CreatedDate ;
    private int  UpdatedByUserId ;
    private String  UpdatedDate ;
    private int  ServerUpdatedStatus ;

    public String getPlotcode (){
        return  PlotCode ;
    }

    public void setPlotcode (String  PlotCode ){
        this. PlotCode = PlotCode ;
    }

    public Integer getSourceofwaterid (){
        return  SourceofWaterId ;
    }

    public void setSourceofwaterid (Integer  SourceofWaterId ){
        this. SourceofWaterId = SourceofWaterId ;
    }

    public Integer getBorewellnumber (){
        return  BorewellNumber ;
    }

    public void setBorewellnumber (Integer  BorewellNumber ){
        this. BorewellNumber = BorewellNumber ;
    }

    public double getWaterdischargecapacity (){
        return  WaterDischargeCapacity ;
    }

    public void setWaterdischargecapacity (double  WaterDischargeCapacity ){
        this. WaterDischargeCapacity = WaterDischargeCapacity ;
    }

    public double getCanalwater (){
        return  CanalWater ;
    }

    public void setCanalwater (double  CanalWater ){
        this. CanalWater = CanalWater ;
    }

    public int getIsactive (){
        return  IsActive ;
    }

    public void setIsactive (int  IsActive ){
        this. IsActive = IsActive ;
    }

    public int getCreatedbyuserid (){
        return  CreatedByUserId ;
    }

    public void setCreatedbyuserid (int  CreatedByUserId ){
        this. CreatedByUserId = CreatedByUserId ;
    }

    public String getCreateddate (){
        return  CreatedDate ;
    }

    public void setCreateddate (String  CreatedDate ){
        this. CreatedDate = CreatedDate ;
    }

    public int getUpdatedbyuserid (){
        return  UpdatedByUserId ;
    }

    public void setUpdatedbyuserid (int  UpdatedByUserId ){
        this. UpdatedByUserId = UpdatedByUserId ;
    }

    public String getUpdateddate (){
        return  UpdatedDate ;
    }

    public void setUpdateddate (String  UpdatedDate ){
        this. UpdatedDate = UpdatedDate ;
    }

    public int getServerupdatedstatus (){
        return  ServerUpdatedStatus ;
    }

    public void setServerupdatedstatus (int  ServerUpdatedStatus ){
        this. ServerUpdatedStatus = ServerUpdatedStatus ;
    }
}
