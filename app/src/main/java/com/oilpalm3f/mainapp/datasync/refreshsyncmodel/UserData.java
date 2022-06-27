package com.oilpalm3f.mainapp.datasync.refreshsyncmodel;

import com.oilpalm3f.mainapp.dbmodels.LandlordBank;
import com.oilpalm3f.mainapp.dbmodels.PlotLandlord;

import java.util.List;

/**
 * Created by skasam on 10/3/2016.
 */
public class UserData {

    List<FarmerPersonalDetailsModel> FarmerDetails ;
    List<FarmerIdProofs> IDProofs;
    List<FarmerBankDetailsModel> BankDetails;
    List<FarmerLandDetailsModel> LandDetails;
    List<FarmerPlotBoundaries> PlotBoundaries;
    List<FarmerCropsInfo> CropInfo;
    List<FarmerInterCropDetails> InterCropDetails;
    List<FarmerNeighbourPlots> NeighboringPlots;
    List<FarmerFFBHarvestDetails> FFB_HarvestDetails;
    List<FarmerupRootmentDetails> UprootmentDetails;
    List<FarmerCropMaintainsData> CropMaintenance;
    List<FarmerPlantProtectionData> PlantProtectionDetails;
    List<FarmerHealthPlantationData> HealthofPlantationDetails;
    List<FarmerComplaintsData> ComplaintDetails;
    List<PlotLandlord> LandLordDetails;

    public List<PlotLandlord> getLandLordDetails() {
        return LandLordDetails;
    }

    public void setLandLordDetails(List<PlotLandlord> landLordDetails) {
        LandLordDetails = landLordDetails;
    }

    public List<LandlordBank> getLandLordBank() {
        return LandLordBank;
    }

    public void setLandLordBank(List<LandlordBank> landLordBank) {
        LandLordBank = landLordBank;
    }

    List<LandlordBank> LandLordBank;



    public List<FarmerPersonalDetailsModel> getFarmerDetails() {
        return FarmerDetails;
    }

    public void setFarmerDetails(List<FarmerPersonalDetailsModel> farmerDetails) {
        FarmerDetails = farmerDetails;
    }

    public List<FarmerIdProofs> getIDProofs() {
        return IDProofs;
    }

    public void setIDProofs(List<FarmerIdProofs> IDProofs) {
        this.IDProofs = IDProofs;
    }

    public List<FarmerBankDetailsModel> getBankDetails() {
        return BankDetails;
    }

    public void setBankDetails(List<FarmerBankDetailsModel> bankDetails) {
        BankDetails = bankDetails;
    }

    public List<FarmerLandDetailsModel> getLandDetails() {
        return LandDetails;
    }

    public void setLandDetails(List<FarmerLandDetailsModel> landDetails) {
        LandDetails = landDetails;
    }

    public List<FarmerPlotBoundaries> getPlotBoundaries() {
        return PlotBoundaries;
    }

    public void setPlotBoundaries(List<FarmerPlotBoundaries> plotBoundaries) {
        PlotBoundaries = plotBoundaries;
    }

    public List<FarmerCropsInfo> getCropInfo() {
        return CropInfo;
    }

    public void setCropInfo(List<FarmerCropsInfo> cropInfo) {
        CropInfo = cropInfo;
    }

    public List<FarmerInterCropDetails> getInterCropDetails() {
        return InterCropDetails;
    }

    public void setInterCropDetails(List<FarmerInterCropDetails> interCropDetails) {
        InterCropDetails = interCropDetails;
    }

    public List<FarmerNeighbourPlots> getNeighboringPlots() {
        return NeighboringPlots;
    }

    public void setNeighboringPlots(List<FarmerNeighbourPlots> neighboringPlots) {
        NeighboringPlots = neighboringPlots;
    }

    public List<FarmerFFBHarvestDetails> getFFB_HarvestDetails() {
        return FFB_HarvestDetails;
    }

    public void setFFB_HarvestDetails(List<FarmerFFBHarvestDetails> FFB_HarvestDetails) {
        this.FFB_HarvestDetails = FFB_HarvestDetails;
    }

    public List<FarmerupRootmentDetails> getUprootmentDetails() {
        return UprootmentDetails;
    }

    public void setUprootmentDetails(List<FarmerupRootmentDetails> uprootmentDetails) {
        UprootmentDetails = uprootmentDetails;
    }

    public List<FarmerCropMaintainsData> getCropMaintenance() {
        return CropMaintenance;
    }

    public void setCropMaintenance(List<FarmerCropMaintainsData> cropMaintenance) {
        CropMaintenance = cropMaintenance;
    }

    public List<FarmerPlantProtectionData> getPlantProtectionDetails() {
        return PlantProtectionDetails;
    }

    public void setPlantProtectionDetails(List<FarmerPlantProtectionData> plantProtectionDetails) {
        PlantProtectionDetails = plantProtectionDetails;
    }

    public List<FarmerHealthPlantationData> getHealthofPlantationDetails() {
        return HealthofPlantationDetails;
    }

    public void setHealthofPlantationDetails(List<FarmerHealthPlantationData> healthofPlantationDetails) {
        HealthofPlantationDetails = healthofPlantationDetails;
    }

    public List<FarmerComplaintsData> getComplaintDetails() {
        return ComplaintDetails;
    }

    public void setComplaintDetails(List<FarmerComplaintsData> complaintDetails) {
        ComplaintDetails = complaintDetails;
    }

}
