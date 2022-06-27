package com.oilpalm3f.mainapp.dbmodels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by siva on 19/02/17.
 */

public class Plantation implements Parcelable {


    private String PlotCode;
    private Integer NurseryId;
    private Integer SaplingSourceId;
    private Integer SaplingVendorId;
    private Integer CropVarietyId;
    private double AllotedArea;
    private int TreesCount;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int IsActive;
    private  int ServerUpdatedStatus;
    private  Integer ReasonTypeId;
    private String GFReceiptNumber;
    private Integer SaplingsPlanted;
    private String MissingPlantsComments;

    public Integer getSaplingsplanted() {
        return SaplingsPlanted;
    }

    public void setSaplingsplanted(Integer saplingsplanted) {
        SaplingsPlanted = saplingsplanted;
    }

    public String getMissingPlantsComments() {
        return MissingPlantsComments;
    }

    public void setMissingPlantsComments(String missingPlantsComments) {
        MissingPlantsComments = missingPlantsComments;
    }

    public String getGFReceiptNumber() {
        return GFReceiptNumber;
    }

    public void setGFReceiptNumber(String GFReceiptNumber) {
        this.GFReceiptNumber = GFReceiptNumber;
    }

    public Plantation() {

    }

    public Integer getReasonTypeId() {
        return ReasonTypeId;
    }

    public void setReasonTypeId(Integer reasonTypeId) {
        ReasonTypeId = reasonTypeId;
    }

    public String getPlotcode(){
        return PlotCode;
    }

    public void setPlotcode(String PlotCode){
        this.PlotCode=PlotCode;
    }

    public Integer getNurserycode(){
        return NurseryId;
    }

    public void setNurserycode(Integer NurseryCode){
        this.NurseryId =NurseryCode;
    }

    public Integer getSaplingsourceid(){
        return SaplingSourceId;
    }

    public void setSaplingsourceid(Integer SaplingSourceId){
        this.SaplingSourceId=SaplingSourceId;
    }

    public Integer getSaplingvendorid(){
        return SaplingVendorId;
    }

    public void setSaplingvendorid(Integer SaplingVendorId){
        this.SaplingVendorId=SaplingVendorId;
    }

    public Integer getCropVarietyId(){
        return CropVarietyId;
    }

    public void setCropVarietyId(Integer CropVarietyId){
        this.CropVarietyId =CropVarietyId;
    }

    public double getAllotedarea(){
        return AllotedArea;
    }

    public void setAllotedarea(double AllotedArea){
        this.AllotedArea=AllotedArea;
    }

    public int getTreescount(){
        return TreesCount;
    }

    public void setTreescount(int TreesCount){
        this.TreesCount=TreesCount;
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



    protected Plantation(Parcel in) {
        PlotCode = in.readString();
        NurseryId = in.readInt();
        SaplingSourceId = in.readInt();
        SaplingVendorId = in.readInt();
        CropVarietyId = in.readInt();
        AllotedArea = in.readDouble();
        TreesCount = in.readInt();
        CreatedByUserId = in.readInt();
        CreatedDate = in.readString();
        UpdatedByUserId = in.readInt();
        UpdatedDate = in.readString();
        ReasonTypeId = in.readInt();
        GFReceiptNumber = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PlotCode);
        dest.writeInt(NurseryId);
        dest.writeInt(SaplingSourceId);
        dest.writeInt(SaplingVendorId);
        dest.writeInt(CropVarietyId);
        dest.writeDouble(AllotedArea);
        dest.writeInt(TreesCount);
        dest.writeInt(CreatedByUserId);
        dest.writeString(CreatedDate);
        dest.writeInt(UpdatedByUserId);
        dest.writeString(UpdatedDate);
        dest.writeInt(ReasonTypeId);
        dest.writeString(GFReceiptNumber);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Plantation> CREATOR = new Parcelable.Creator<Plantation>() {
        @Override
        public Plantation createFromParcel(Parcel in) {
            return new Plantation(in);
        }

        @Override
        public Plantation[] newArray(int size) {
            return new Plantation[size];
        }
    };

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
