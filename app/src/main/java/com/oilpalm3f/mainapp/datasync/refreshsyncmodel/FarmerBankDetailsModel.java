package com.oilpalm3f.mainapp.datasync.refreshsyncmodel;

/**
 * Created by skasam on 10/3/2016.
 */
public class FarmerBankDetailsModel {

    private int BankDetailId ;
    private String FarmerCode ;
    private int BankCode ;
    private String OtherBank ;
    private String AccountHolderName ;
    private String AccountNumber ;
    private String BranchName ;
    private String IFSCCode ;
    private int Active ;
    private String CreatedBy ;
    private String CreatedDate ;
    private String UpdatedBy ;
    private String UpdatedDate ;
    private int ServerUpdatedStatus ;

    public int getBankDetailId() {
        return BankDetailId;
    }

    public void setBankDetailId(int bankDetailId) {
        BankDetailId = bankDetailId;
    }

    public String getFarmerCode() {
        return FarmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        FarmerCode = farmerCode;
    }

    public int getBankCode() {
        return BankCode;
    }

    public void setBankCode(int bankCode) {
        BankCode = bankCode;
    }

    public String getOtherBank() {
        return OtherBank;
    }

    public void setOtherBank(String otherBank) {
        OtherBank = otherBank;
    }

    public String getAccountHolderName() {
        return AccountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        AccountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public void setIFSCCode(String IFSCCode) {
        this.IFSCCode = IFSCCode;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

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
}
