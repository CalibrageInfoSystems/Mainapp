package com.oilpalm3f.mainapp.dbmodels;


import android.os.Parcel;
import android.os.Parcelable;

public class Collection implements Parcelable {
    private String Code;
    private String CollectionCenterCode;
    private String FarmerCode;
    private Integer WeighbridgeCenterId;
    private String VehicleNumber;
    private String DriverName;
    private double GrossWeight;
    private double TareWeight;
    private double NetWeight;
    private String OperatorName;
    private String Comments;
    private int TotalBunches;
    private int RejectedBunches;
    private int AcceptedBunches;
    private String Remarks;
    private String GraderName;
    private String ReceiptCode;
    private String WBReceiptName;
    private String WBReceiptLocation;
    private String WBReceiptExtension;
    private int CreatedByUserId;
    private int UpdatedByUserId;
    private String WeighingDate;
    private String ReceiptGeneratedDate;
    private String CreatedDate;
    private String UpdatedDate;
    private int IsActive;
    private int IsCollectionWithOutFarmer;

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

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getRecieptGeneratedDate() {
        return ReceiptGeneratedDate;
    }

    public void setRecieptGeneratedDate(String recieptGeneratedDate) {
        ReceiptGeneratedDate = recieptGeneratedDate;
    }

    public String getWeighingDate() {
        return WeighingDate;
    }

    public void setWeighingDate(String weighingDate) {
        WeighingDate = weighingDate;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getCollectioncentercode() {
        return CollectionCenterCode;
    }

    public void setCollectioncentercode(String CollectionCenterCode) {
        this.CollectionCenterCode = CollectionCenterCode;
    }

    public String getFarmercode() {
        return FarmerCode;
    }

    public void setFarmercode(String FarmerCode) {
        this.FarmerCode = FarmerCode;
    }

    public Integer getWeighbridgecenterid() {
        return WeighbridgeCenterId;
    }

    public void setWeighbridgecenterid(Integer WeighbridgeCenterId) {
        this.WeighbridgeCenterId = WeighbridgeCenterId;
    }

    public String getVehiclenumber() {
        return VehicleNumber;
    }

    public void setVehiclenumber(String VehicleNumber) {
        this.VehicleNumber = VehicleNumber;
    }

    public String getDrivername() {
        return DriverName;
    }

    public void setDrivername(String DriverName) {
        this.DriverName = DriverName;
    }

    public double getGrossweight() {
        return GrossWeight;
    }

    public void setGrossweight(double GrossWeight) {
        this.GrossWeight = GrossWeight;
    }

    public double getTareweight() {
        return TareWeight;
    }

    public void setTareweight(double TareWeight) {
        this.TareWeight = TareWeight;
    }

    public double getNetweight() {
        return NetWeight;
    }

    public void setNetweight(double NetWeight) {
        this.NetWeight = NetWeight;
    }

    public String getOperatorname() {
        return OperatorName;
    }

    public void setOperatorname(String OperatorName) {
        this.OperatorName = OperatorName;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public int getTotalbunches() {
        return TotalBunches;
    }

    public void setTotalbunches(int TotalBunches) {
        this.TotalBunches = TotalBunches;
    }

    public int getRejectedbunches() {
        return RejectedBunches;
    }

    public void setRejectedbunches(int RejectedBunches) {
        this.RejectedBunches = RejectedBunches;
    }

    public int getAcceptedbunches() {
        return AcceptedBunches;
    }

    public void setAcceptedbunches(int AcceptedBunches) {
        this.AcceptedBunches = AcceptedBunches;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getGradername() {
        return GraderName;
    }

    public void setGradername(String GraderName) {
        this.GraderName = GraderName;
    }

    public String getRecieptcode() {
        return ReceiptCode;
    }

    public void setRecieptcode(String RecieptCode) {
        this.ReceiptCode = RecieptCode;
    }

    public String getRecieptname() {
        return WBReceiptName;
    }

    public void setRecieptname(String RecieptName) {
        this.WBReceiptName = RecieptName;
    }

    public String getRecieptlocation() {
        return WBReceiptLocation;
    }

    public void setRecieptlocation(String RecieptLocation) {
        this.WBReceiptLocation = RecieptLocation;
    }

    public String getRecieptextension() {
        return WBReceiptExtension;
    }

    public void setRecieptextension(String RecieptExtension) {
        this.WBReceiptExtension = RecieptExtension;
    }

    public int getCreatedbyuserid() {
        return CreatedByUserId;
    }

    public void setCreatedbyuserid(int CreatedByUserId) {
        this.CreatedByUserId = CreatedByUserId;
    }

    public int getUpdatedbyuserid() {
        return UpdatedByUserId;
    }

    public void setUpdatedbyuserid(int UpdatedByUserId) {
        this.UpdatedByUserId = UpdatedByUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Code);
        dest.writeString(this.CollectionCenterCode);
        dest.writeString(this.FarmerCode);
        dest.writeValue(this.WeighbridgeCenterId);
        dest.writeString(this.VehicleNumber);
        dest.writeString(this.DriverName);
        dest.writeDouble(this.GrossWeight);
        dest.writeDouble(this.TareWeight);
        dest.writeDouble(this.NetWeight);
        dest.writeString(this.OperatorName);
        dest.writeString(this.Comments);
        dest.writeInt(this.TotalBunches);
        dest.writeInt(this.RejectedBunches);
        dest.writeInt(this.AcceptedBunches);
        dest.writeString(this.Remarks);
        dest.writeString(this.GraderName);
        dest.writeString(this.ReceiptCode);
        dest.writeString(this.WBReceiptName);
        dest.writeString(this.WBReceiptLocation);
        dest.writeString(this.WBReceiptExtension);
        dest.writeInt(this.CreatedByUserId);
        dest.writeInt(this.UpdatedByUserId);
        dest.writeString(this.WeighingDate);
        dest.writeString(this.CreatedDate);
        dest.writeString(this.ReceiptGeneratedDate);
        dest.writeInt(this.IsActive);
    }

    public Collection() {
    }

    protected Collection(Parcel in) {
        this.Code = in.readString();
        this.CollectionCenterCode = in.readString();
        this.FarmerCode = in.readString();
        this.WeighbridgeCenterId = in.readInt();
        this.VehicleNumber = in.readString();
        this.DriverName = in.readString();
        this.GrossWeight = in.readDouble();
        this.TareWeight = in.readDouble();
        this.NetWeight = in.readDouble();
        this.OperatorName = in.readString();
        this.Comments = in.readString();
        this.TotalBunches = in.readInt();
        this.RejectedBunches = in.readInt();
        this.AcceptedBunches = in.readInt();
        this.Remarks = in.readString();
        this.GraderName = in.readString();
        this.ReceiptCode = in.readString();
        this.WBReceiptName = in.readString();
        this.WBReceiptLocation = in.readString();
        this.WBReceiptLocation = in.readString();
        this.CreatedByUserId = in.readInt();
        this.UpdatedByUserId = in.readInt();
        this.ReceiptGeneratedDate = in.readString();
        this.CreatedDate = in.readString();
        this.WeighingDate = in.readString();
        this.IsActive = in.readInt();
    }

    public static final Parcelable.Creator<Collection> CREATOR = new Parcelable.Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel source) {
            return new Collection(source);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };

    public int isCollectionWithOutFarmer() {
        return IsCollectionWithOutFarmer;
    }

    public void setCollectionWithOutFarmer(int collectionWithOutFarmer) {
        IsCollectionWithOutFarmer = collectionWithOutFarmer;
    }
}

