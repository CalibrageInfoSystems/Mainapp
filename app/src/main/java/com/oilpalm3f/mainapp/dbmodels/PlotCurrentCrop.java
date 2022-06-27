package com.oilpalm3f.mainapp.dbmodels;

import android.os.Parcel;
import android.os.Parcelable;

public class PlotCurrentCrop implements Parcelable {
    private String PlotCode;
    private Integer CropId;
    private double CurrentCropArea;
    private int IsActive;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;

    public PlotCurrentCrop() {

    }

    protected PlotCurrentCrop(Parcel in) {
        PlotCode = in.readString();
        CropId = in.readInt();
        CurrentCropArea = in.readDouble();
        IsActive = in.readInt();
        CreatedByUserId = in.readInt();
        CreatedDate = in.readString();
        UpdatedByUserId = in.readInt();
        UpdatedDate = in.readString();
        ServerUpdatedStatus = in.readInt();
    }

    public static final Creator<PlotCurrentCrop> CREATOR = new Creator<PlotCurrentCrop>() {
        @Override
        public PlotCurrentCrop createFromParcel(Parcel in) {
            return new PlotCurrentCrop(in);
        }

        @Override
        public PlotCurrentCrop[] newArray(int size) {
            return new PlotCurrentCrop[size];
        }
    };

    public String getPlotcode() {
        return PlotCode;
    }

    public void setPlotcode(String PlotCode) {
        this.PlotCode = PlotCode;
    }

    public Integer getCropid() {
        return CropId;
    }

    public void setCropid(Integer CropId) {
        this.CropId = CropId;
    }

    public double getCurrentcroparea() {
        return CurrentCropArea;
    }

    public void setCurrentcroparea(double CurrentCropArea) {
        this.CurrentCropArea = CurrentCropArea;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PlotCode);
        dest.writeInt(CropId);
        dest.writeDouble(CurrentCropArea);
        dest.writeInt(IsActive);
        dest.writeInt(CreatedByUserId);
        dest.writeString(CreatedDate);
        dest.writeInt(UpdatedByUserId);
        dest.writeString(UpdatedDate);
        dest.writeInt(ServerUpdatedStatus);
    }

}