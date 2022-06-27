package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by balireddy on 3/10/2018.
 */

public class NurserySaplingDetails {
    private  String PlotCode;
    private String SaplingPickUpDate;
    private  int NoOfSaplingsDispatched;
    private  int  NoOfImportedSaplingsDispatched;
    private  int NoOfIndigenousSaplingsDispatched;
    private  String ReceiptNumber;
    private  int CreatedByUserId;
    private  String CreatedDate;
    private  int UpdatedByUserId;
    private  String UpdatedDate;
    private int NurseryId;
    private int SaplingSourceId;
    private int SaplingVendorId;
    private int CropVarietyId;
//    private int AdvanceId;
//
//    public int getAdvanceId() {
//        return AdvanceId;
//    }
//
//    public void setAdvanceId(int advanceId) {
//        AdvanceId = advanceId;
//    }

    public int getNurseryId() {
        return NurseryId;
    }

    public void setNurseryId(int nurseryId) {
        NurseryId = nurseryId;
    }

    public int getSaplingSourceId() {
        return SaplingSourceId;
    }

    public void setSaplingSourceId(int saplingSourceId) {
        SaplingSourceId = saplingSourceId;
    }

    public int getSaplingVendorId() {
        return SaplingVendorId;
    }

    public void setSaplingVendorId(int saplingVendorId) {
        SaplingVendorId = saplingVendorId;
    }

    public int getCropVarietyId() {
        return CropVarietyId;
    }

    public void setCropVarietyId(int cropVarietyId) {
        CropVarietyId = cropVarietyId;
    }



    public NurserySaplingDetails(){

    }

    public String getPlotCode() {
        return PlotCode;
    }

    public void setPlotCode(String plotCode) {
        PlotCode = plotCode;
    }

    public String getSaplingPickUpDate() {
        return SaplingPickUpDate;
    }

    public void setSaplingPickUpDate(String saplingPickUpDate) {
        SaplingPickUpDate = saplingPickUpDate;
    }

    public int getNoOfSaplingsDispatched() {
        return NoOfSaplingsDispatched;
    }

    public void setNoOfSaplingsDispatched(int noOfSaplingsDispatched) {
        NoOfSaplingsDispatched = noOfSaplingsDispatched;
    }

    public int getNoOfImportedSaplingsDispatched() {
        return NoOfImportedSaplingsDispatched;
    }

    public void setNoOfImportedSaplingsDispatched(int noOfImportedSaplingsDispatched) {
        NoOfImportedSaplingsDispatched = noOfImportedSaplingsDispatched;
    }

    public int getNoOfIndigenousSaplingsDispatched() {
        return NoOfIndigenousSaplingsDispatched;
    }

    public void setNoOfIndigenousSaplingsDispatched(int noOfIndigenousSaplingsDispatched) {
        NoOfIndigenousSaplingsDispatched = noOfIndigenousSaplingsDispatched;
    }

    public String getReceiptNumber() {
        return ReceiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        ReceiptNumber = receiptNumber;
    }

    public int getCreatedByUserId() {
        return CreatedByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        CreatedByUserId = createdByUserId;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public int getUpdatedByUserId() {
        return UpdatedByUserId;
    }

    public void setUpdatedByUserId(int updatedByUserId) {
        UpdatedByUserId = updatedByUserId;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

}
