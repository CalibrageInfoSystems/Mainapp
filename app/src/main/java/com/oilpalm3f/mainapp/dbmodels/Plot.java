package com.oilpalm3f.mainapp.dbmodels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by siva on 19/02/17.
 */

public class Plot implements Parcelable {

    private int IsActive;
    private String Code;
    private String FarmerCode;
    private double TotalPlotArea;
    private double TotalPalmArea;
    private Integer IsBoundryFencing;
    private double GPSPlotArea;
    private Integer CropIncomeTypeId;
    private String AddressCode;
    private String SurveyNumber;
    private String AdangalNumber;
    private double LeftOutArea;
    private Integer LeftOutAreaCropId;
    private Integer PlotOwnerShipTypeId;
    private Integer IsPlotHandledByCareTaker;
    private String CareTakerName;
    private String CareTakerContactNumber;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;
    private String Comments ;
    private Integer  IsPLotSubsidySubmission;
    private Integer IsPLotHavingIdCard;
    private Integer IsGeoBoundariesVerification;
    private Double SuitablePalmOilArea;
    private String DateofPlanting;
    private Integer SwapingReasonId;
    private String OriginCode;
    private Integer ReasonTypeId;
    private String InactivatedDate;
    private int InactivatedByUserId;
    private int InActiveReasonTypeId;
    private int PlansToPlanInFuture;
    private int IsRetakeGeoTagRequired;
    private float TotalAreaUnderHorticulture;
    private Integer LandTypeId;


    public String getFarmerCode() {
        return FarmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        FarmerCode = farmerCode;
    }

    public int getIsRetakeGeoTagRequired() {
        return IsRetakeGeoTagRequired;
    }

    public void setIsRetakeGeoTagRequired(int isRetakeGeoTagRequired) {
        IsRetakeGeoTagRequired = isRetakeGeoTagRequired;
    }

    public int getPlansToPlanInFuture() {
        return PlansToPlanInFuture;
    }

    public void setPlansToPlanInFuture(int plansToPlanInFuture) {
        PlansToPlanInFuture = plansToPlanInFuture;
    }

    public String getOriginCode() {
        return OriginCode;
    }

    public void setOriginCode(String originCode) {
        OriginCode = originCode;
    }

    public Integer getReasonTypeId() {
        return ReasonTypeId;
    }

    public void setReasonTypeId(Integer reasonTypeId) {
        ReasonTypeId = reasonTypeId;
    }

    public String getInactivatedDate() {
        return InactivatedDate;
    }

    public void setInactivatedDate(String inactivatedDate) {
        InactivatedDate = inactivatedDate;
    }

    public int getInactivatedByUserId() {
        return InactivatedByUserId;
    }

    public void setInactivatedByUserId(int inactivatedByUserId) {
        InactivatedByUserId = inactivatedByUserId;
    }

    public int getInActiveReasonTypeId() {
        return InActiveReasonTypeId;
    }

    public void setInActiveReasonTypeId(int inActiveReasonTypeId) {
        InActiveReasonTypeId = inActiveReasonTypeId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }


    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public Integer getIsPLotSubsidySubmission() {
        return IsPLotSubsidySubmission;
    }

    public void setIsPLotSubsidySubmission(Integer isPLotSubsidySubmission) {
        IsPLotSubsidySubmission = isPLotSubsidySubmission;
    }

    public Integer getIsPLotHavingIdCard() {
        return IsPLotHavingIdCard;
    }

    public void setIsPLotHavingIdCard(Integer isPLotHavingIdCard) {
        IsPLotHavingIdCard = isPLotHavingIdCard;
    }

    public Integer getIsGeoBoundariesVerification() {
        return IsGeoBoundariesVerification;
    }

    public void setIsGeoBoundariesVerification(Integer isGeoBoundariesVerification) {
        IsGeoBoundariesVerification = isGeoBoundariesVerification;
    }

    public Integer getIsBoundryFencing() {
        return IsBoundryFencing;
    }

    public void setIsBoundryFencing(Integer isBoundryFencing) {
        IsBoundryFencing = isBoundryFencing;
    }



    public String getFarmercode() {
        return FarmerCode;
    }

    public void setFarmercode(String FarmerCode) {
        this.FarmerCode = FarmerCode;
    }

    public double getTotalplotarea() {
        return TotalPlotArea;
    }

    public void setTotalplotarea(double TotalPlotArea) {
        this.TotalPlotArea = TotalPlotArea;
    }

    public double getTotalpalmarea() {
        return TotalPalmArea;
    }

    public void setTotalpalmarea(double TotalPalmArea) {
        this.TotalPalmArea = TotalPalmArea;
    }

    public double getGpsplotarea() {
        return GPSPlotArea;
    }

    public void setGpsplotarea(double GPSPlotArea) {
        this.GPSPlotArea = GPSPlotArea;
    }

    public Integer getCropincometypeid() {
        return CropIncomeTypeId;
    }

    public void setCropincometypeid(Integer CropIncomeTypeId) {
        this.CropIncomeTypeId = CropIncomeTypeId;
    }

    public String getAddesscode() {
        return AddressCode;
    }

    public void setAddesscode(String AddessCode) {
        this.AddressCode = AddessCode;
    }

    public String getSurveynumber() {
        return SurveyNumber;
    }

    public void setSurveynumber(String SurveyNumber) {
        this.SurveyNumber = SurveyNumber;
    }

    public String getAdangalnumber() {
        return AdangalNumber;
    }

    public void setAdangalnumber(String AdangalNumber) {
        this.AdangalNumber = AdangalNumber;
    }

    public double getLeftoutarea() {
        return LeftOutArea;
    }

    public void setLeftoutarea(double LeftOutArea) {
        this.LeftOutArea = LeftOutArea;
    }

    public Integer getLeftoutareacropid() {
        return LeftOutAreaCropId;
    }

    public void setLeftoutareacropid(Integer LeftOutAreaCropId) {
        this.LeftOutAreaCropId = LeftOutAreaCropId;
    }

    public Integer getPlotownershiptypeid() {
        return PlotOwnerShipTypeId;
    }

    public void setPlotownershiptypeid(Integer PlotOwnerShipTypeId) {
        this.PlotOwnerShipTypeId = PlotOwnerShipTypeId;
    }

    public Integer getIsplothandledbycaretaker() {
        return IsPlotHandledByCareTaker;
    }

    public void setIsplothandledbycaretaker(Integer IsPlotHandledByCareTaker) {
        this.IsPlotHandledByCareTaker = IsPlotHandledByCareTaker;
    }

    public String getCaretakername() {
        return CareTakerName;
    }

    public void setCaretakername(String CareTakerName) {
        this.CareTakerName = CareTakerName;
    }

    public String getCaretakercontactnumber() {
        return CareTakerContactNumber;
    }

    public void setCaretakercontactnumber(String CareTakerContactNumber) {
        this.CareTakerContactNumber = CareTakerContactNumber;
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

    public float getTotalAreaUnderHorticulture() {
        return TotalAreaUnderHorticulture;
    }

    public void setTotalAreaUnderHorticulture(float totalAreaUnderHorticulture) {
        TotalAreaUnderHorticulture = totalAreaUnderHorticulture;
    }

    public Integer getLandTypeId() {
        return LandTypeId;
    }

    public void setLandTypeId(Integer landTypeId) {
        LandTypeId = landTypeId;
    }

    public Plot() {

    }

    protected Plot(Parcel in) {
        FarmerCode = in.readString();
        TotalPlotArea = in.readDouble();
        TotalPalmArea = in.readDouble();
        GPSPlotArea = in.readDouble();
        IsBoundryFencing = in.readInt();
        CropIncomeTypeId = in.readInt();
        AddressCode = in.readString();
        SurveyNumber = in.readString();
        AdangalNumber = in.readString();
        LeftOutArea = in.readDouble();
        LeftOutAreaCropId = in.readInt();
        PlotOwnerShipTypeId = in.readInt();
        IsPlotHandledByCareTaker = in.readInt();
        CareTakerName = in.readString();
        CareTakerContactNumber = in.readString();
        CreatedByUserId = in.readInt();
        CreatedDate = in.readString();
        UpdatedByUserId = in.readInt();
        UpdatedDate = in.readString();
        ServerUpdatedStatus = in.readInt();
        IsActive = in.readInt();
        Comments = in.readString();
        IsPLotSubsidySubmission = in.readInt();
        IsPLotHavingIdCard = in.readInt();
        IsGeoBoundariesVerification = in.readInt();
        SuitablePalmOilArea = in.readDouble();
        DateofPlanting = in.readString();
        SwapingReasonId = in.readInt();
        IsBoundryFencing = in.readInt();
        OriginCode = in.readString();
        ReasonTypeId = in.readInt();
        InactivatedDate = in.readString();
        InactivatedByUserId = in.readInt();
        InActiveReasonTypeId = in.readInt();
        PlansToPlanInFuture =in.readInt();
        IsRetakeGeoTagRequired = in.readInt();
        TotalAreaUnderHorticulture = in.readFloat();
        LandTypeId = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(FarmerCode);
        dest.writeDouble(TotalPlotArea);
        dest.writeDouble(TotalPalmArea);
        dest.writeDouble(GPSPlotArea);
        dest.writeInt(IsBoundryFencing);
        dest.writeInt(CropIncomeTypeId);
        dest.writeString(AddressCode);
        dest.writeString(SurveyNumber);
        dest.writeString(AdangalNumber);
        dest.writeDouble(LeftOutArea);
        dest.writeInt(LeftOutAreaCropId);
        dest.writeInt(PlotOwnerShipTypeId);
        dest.writeInt(IsPlotHandledByCareTaker);
        dest.writeString(CareTakerName);
        dest.writeString(CareTakerContactNumber);
        dest.writeInt(CreatedByUserId);
        dest.writeString(CreatedDate);
        dest.writeInt(UpdatedByUserId);
        dest.writeString(UpdatedDate);
        dest.writeInt(ServerUpdatedStatus);
        dest.writeInt(IsActive);
        dest.writeString(Comments);
        dest.writeInt(IsPLotSubsidySubmission);
        dest.writeInt(IsPLotHavingIdCard);
        dest.writeInt(IsGeoBoundariesVerification);
        dest.writeDouble(SuitablePalmOilArea);
        dest.writeString(DateofPlanting);
        dest.writeInt(SwapingReasonId);
        dest.writeInt(IsBoundryFencing);
        dest.writeString(OriginCode);
        dest.writeInt(ReasonTypeId);
        dest.writeString(InactivatedDate);
        dest.writeInt(InactivatedByUserId);
        dest.writeInt(InActiveReasonTypeId);
        dest.writeInt(PlansToPlanInFuture);
        dest.writeInt(IsRetakeGeoTagRequired);
        dest.writeFloat(TotalAreaUnderHorticulture);
        dest.writeInt(LandTypeId);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Plot> CREATOR = new Parcelable.Creator<Plot>() {
        @Override
        public Plot createFromParcel(Parcel in) {
            return new Plot(in);
        }

        @Override
        public Plot[] newArray(int size) {
            return new Plot[size];
        }
    };

    public Double getSuitablePalmOilArea() {
        return SuitablePalmOilArea;
    }

    public void setSuitablePalmOilArea(Double suitablePalmOilArea) {
        SuitablePalmOilArea = suitablePalmOilArea;
    }

    public String getDateofPlanting() {
        return DateofPlanting;
    }



    public void setDateofPlanting(String dateofPlanting) {
        DateofPlanting = dateofPlanting;
    }

    public Integer getSwapingReasonId() {
        return SwapingReasonId;
    }

    public void setSwapingReasonId(Integer swapingReasonId) {
        SwapingReasonId = swapingReasonId;
    }
}
