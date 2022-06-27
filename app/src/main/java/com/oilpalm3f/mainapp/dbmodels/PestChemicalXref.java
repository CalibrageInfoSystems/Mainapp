package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 25/05/17.
 */

public class PestChemicalXref {
    private String PestCode;
    private Integer ChemicalId;
    private int ServerUpdatedStatus;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private String CropMaintenanceCode;


    public String getPestCode() {
        return PestCode;
    }

    public void setPestCode(String pestCode) {
        PestCode = pestCode;
    }

    public Integer getChemicalId() {
        return ChemicalId;
    }

    public void setChemicalId(Integer chemicalId) {
        ChemicalId = chemicalId;
    }

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
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

    public void setCropMaintenanceCode(String cropMaintenanceCode) {
        CropMaintenanceCode = cropMaintenanceCode;
    }
}