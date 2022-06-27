package com.oilpalm3f.mainapp.dbmodels;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by siva on 12/05/17.
 */

public class Referrals implements Serializable{
    private String MandalName;
    private String FarmerName;
    private String ContactNumber;
    private int CreatedByUserId;
    private String CreatedDate;
    private String UpdatedDate;
    private int UpdatedByUserId;

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

    private int ServerUpdatedStatus;

    public void setVillageName(String villageName) {
        VillageName = villageName;
    }

    private String VillageName;

    public String getMandalname(){
        return MandalName;
    }

    public void setMandalname(String MandalName){
        this.MandalName=MandalName;
    }

    public String getFarmername(){
        return FarmerName;
    }

    public void setFarmername(String FarmerName){
        this.FarmerName=FarmerName;
    }

    public String getContactnumber(){
        return ContactNumber;
    }

    public void setContactnumber(String ContactNumber){
        this.ContactNumber=ContactNumber;
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

    public String getVillageName() {
        return VillageName;
    }
}
