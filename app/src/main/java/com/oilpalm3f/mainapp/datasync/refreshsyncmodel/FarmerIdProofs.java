package com.oilpalm3f.mainapp.datasync.refreshsyncmodel;

/**
 * Created by skasam on 10/3/2016.
 */
public class FarmerIdProofs {

    private int ProofID ;
    private String ProofType ;
    private String ProofNo ;
    private String FarmerCode ;
    private String CreatedBy ;
    private String CreatedDate ;
    private String UpdatedBy ;
    private String UpdatedDate ;
    private int ServerUpdatedStatus ;

    public int getProofID() {
        return ProofID;
    }

    public void setProofID(int proofID) {
        ProofID = proofID;
    }

    public String getProofType() {
        return ProofType;
    }

    public void setProofType(String proofType) {
        ProofType = proofType;
    }

    public String getProofNo() {
        return ProofNo;
    }

    public void setProofNo(String proofNo) {
        ProofNo = proofNo;
    }

    public String getFarmerCode() {
        return FarmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        FarmerCode = farmerCode;
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
