package com.oilpalm3f.mainapp.dbmodels;

/**=
 * Created by baliReddy on 3/10/2018.
 */

public class AdvancedDetails {

    private  String PlotCode;
    private  Double FarmerContributionReceived;
    private  String DateOfAdvanceReceived;
    private  String  ExpectedMonthOfPlanting;
    private  int NoOfSaplingsAdvancePaidFor;
    private  int NoOfImportedSaplingsToBeIssued;
    private  int NoOfIndigenousSaplingsToBeIssued;
    private  Float AdvanceReceivedArea;
    private  String  SurveyNumber;
    private  String  ReceiptNumber;
    private  String Comments;
    private  int CreatedByUserId;
    private  String CreatedDate;
    private  int UpdatedByUserId;
    private  String UpdatedDate;
    private  double FarmerContributionPriceForIndigenousSaplings;
    private  int FarmerContributionPriceForImportedSaplings;
    private  double ModeOfPayment;
    private String ChequeNo;
    private String ChequeDate;
    private int BankId;
    private  Float TotalPriceOfImportedSaplings;
    private  Float TotalPriceOfIndigenousSaplings;
    private  Float TotalSaplingsPrice;
    private  Float SubsidyPriceForImportedSaplings;
    private  Float SubsidyPriceForIndigenousSaplings;
    private  Float SubsidyPrice;
    private float TotalTransportationcost,FarmerContributionTransportationcost,SubsidyTransportationcost;

    public float getTotalTransportationcost() {
        return TotalTransportationcost;
    }

    public void setTotalTransportationcost(float totalTransportationcost) {
        TotalTransportationcost = totalTransportationcost;
    }

    public float getFarmerContributionTransportationcost() {
        return FarmerContributionTransportationcost;
    }

    public void setFarmerContributionTransportationcost(float farmerContributionTransportationcost) {
        FarmerContributionTransportationcost = farmerContributionTransportationcost;
    }

    public float getSubsidyTransportationcost() {
        return SubsidyTransportationcost;
    }

    public void setSubsidyTransportationcost(float subsidyTransportationcost) {
        SubsidyTransportationcost = subsidyTransportationcost;
    }

    public AdvancedDetails(){

    }

    public String getPlotCode() {
        return PlotCode;
    }

    public void setPlotCode(String plotCode) {
        PlotCode = plotCode;
    }

    public Double getFarmerContributionReceived() {
        return FarmerContributionReceived;
    }

    public void setFarmerContributionReceived(Double farmerContributionReceived) {
        FarmerContributionReceived = farmerContributionReceived;
    }

    public String getDateOfAdvanceReceived() {
        return DateOfAdvanceReceived;
    }

    public void setDateOfAdvanceReceived(String dateOfAdvanceReceived) {
        DateOfAdvanceReceived = dateOfAdvanceReceived;
    }

    public String getExpectedMonthOfPlanting() {
        return ExpectedMonthOfPlanting;
    }

    public void setExpectedMonthOfPlanting(String expectedMonthOfPlanting) {
        ExpectedMonthOfPlanting = expectedMonthOfPlanting;
    }

    public int getNoOfSaplingsAdvancePaidFor() {
        return NoOfSaplingsAdvancePaidFor;
    }

    public void setNoOfSaplingsAdvancePaidFor(int noOfSaplingsAdvancePaidFor) {
        NoOfSaplingsAdvancePaidFor = noOfSaplingsAdvancePaidFor;
    }

    public int getNoOfImportedSaplingsToBeIssued() {
        return NoOfImportedSaplingsToBeIssued;
    }

    public void setNoOfImportedSaplingsToBeIssued(int noOfImportedSaplingsToBeIssued) {
        NoOfImportedSaplingsToBeIssued = noOfImportedSaplingsToBeIssued;
    }

    public int getNoOfIndigenousSaplingsToBeIssued() {
        return NoOfIndigenousSaplingsToBeIssued;
    }

    public void setNoOfIndigenousSaplingsToBeIssued(int noOfIndigenousSaplingsToBeIssued) {
        NoOfIndigenousSaplingsToBeIssued = noOfIndigenousSaplingsToBeIssued;
    }

    public Float getAdvanceReceivedArea() {
        return AdvanceReceivedArea;
    }

    public void setAdvanceReceivedArea(Float advanceReceivedArea) {
        AdvanceReceivedArea = advanceReceivedArea;
    }

    public String getSurveyNumber() {
        return SurveyNumber;
    }

    public void setSurveyNumber(String surveyNumber) {
        SurveyNumber = surveyNumber;
    }

    public String getReceiptNumber() {
        return ReceiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        ReceiptNumber = receiptNumber;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
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

    public double getFarmerContributionPriceForIndigenousSaplings() {
        return FarmerContributionPriceForIndigenousSaplings;
    }

    public void setFarmerContributionPriceForIndigenousSaplings(double farmerContributionPriceForIndigenousSaplings) {
        FarmerContributionPriceForIndigenousSaplings = farmerContributionPriceForIndigenousSaplings;
    }

    public int getFarmerContributionPriceForImportedSaplings() {
        return FarmerContributionPriceForImportedSaplings;
    }

    public void setFarmerContributionPriceForImportedSaplings(int farmerContributionPriceForImportedSaplings) {
        FarmerContributionPriceForImportedSaplings = farmerContributionPriceForImportedSaplings;
    }

    public double getModeOfPayment() {
        return ModeOfPayment;
    }

    public void setModeOfPayment(double modeOfPayment) {
        ModeOfPayment = modeOfPayment;
    }

    public String getChequeNo() {
        return ChequeNo;
    }

    public void setChequeNo(String chequeNo) {
        ChequeNo = chequeNo;
    }

    public String getChequeDate() {
        return ChequeDate;
    }

    public void setChequeDate(String chequeDate) {
        ChequeDate = chequeDate;
    }

    public int getBankId() {
        return BankId;
    }

    public void setBankId(int bankId) {
        BankId = bankId;
    }

    public Float getTotalPriceOfImportedSaplings() {
        return TotalPriceOfImportedSaplings;
    }

    public void setTotalPriceOfImportedSaplings(Float totalPriceOfImportedSaplings) {
        TotalPriceOfImportedSaplings = totalPriceOfImportedSaplings;
    }

    public Float getTotalPriceOfIndigenousSaplings() {
        return TotalPriceOfIndigenousSaplings;
    }

    public void setTotalPriceOfIndigenousSaplings(Float totalPriceOfIndigenousSaplings) {
        TotalPriceOfIndigenousSaplings = totalPriceOfIndigenousSaplings;
    }

    public Float getTotalSaplingsPrice() {
        return TotalSaplingsPrice;
    }

    public void setTotalSaplingsPrice(Float totalSaplingsPrice) {
        TotalSaplingsPrice = totalSaplingsPrice;
    }

    public Float getSubsidyPriceForImportedSaplings() {
        return SubsidyPriceForImportedSaplings;
    }

    public void setSubsidyPriceForImportedSaplings(Float subsidyPriceForImportedSaplings) {
        SubsidyPriceForImportedSaplings = subsidyPriceForImportedSaplings;
    }

    public Float getSubsidyPriceForIndigenousSaplings() {
        return SubsidyPriceForIndigenousSaplings;
    }

    public void setSubsidyPriceForIndigenousSaplings(Float subsidyPriceForIndigenousSaplings) {
        SubsidyPriceForIndigenousSaplings = subsidyPriceForIndigenousSaplings;
    }

    public Float getSubsidyPrice() {
        return SubsidyPrice;
    }

    public void setSubsidyPrice(Float subsidyPrice) {
        SubsidyPrice = subsidyPrice;
    }
}
