package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by Calibrage11 on 8/8/2017.
 */

public class ComplaintRepository {

    private String ComplaintCode;
    private int ModuleTypeId;
    private String FileName;
    private String FileLocation;
    private String FileExtension;
    private int IsVideoRecording;
    private int IsResult;
    private int IsActive;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;


    public String getComplaintCode() {
        return ComplaintCode;
    }

    public void setComplaintCode(String complaintCode) {
        ComplaintCode = complaintCode;
    }

    public int getModuleTypeId() {
        return ModuleTypeId;
    }

    public void setModuleTypeId(int moduleTypeId) {
        ModuleTypeId = moduleTypeId;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFileLocation() {
        return FileLocation;
    }

    public void setFileLocation(String fileLocation) {
        FileLocation = fileLocation;
    }

    public String getFileExtension() {
        return FileExtension;
    }

    public void setFileExtension(String fileExtension) {
        FileExtension = fileExtension;
    }

    public int getIsVideoRecording() {
        return IsVideoRecording;
    }

    public void setIsVideoRecording(int isVideoRecording) {
        IsVideoRecording = isVideoRecording;
    }

    public int getIsResult() {
        return IsResult;
    }

    public void setIsResult(int isResult) {
        IsResult = isResult;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
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

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }
}
