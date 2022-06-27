package com.oilpalm3f.mainapp.dbmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Calibrage11 on 8/31/2017.
 */

public class ComplaintRepositoryRefresh {

        @SerializedName("ByteImage")
        @Expose
        private String byteImage;
        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("ComplaintCode")
        @Expose
        private String complaintCode;
        @SerializedName("ModuleTypeId")
        @Expose
        private Integer moduleTypeId;
        @SerializedName("FileName")
        @Expose
        private String fileName;
        @SerializedName("FileLocation")
        @Expose
        private String fileLocation;
        @SerializedName("FileExtension")
        @Expose
        private String fileExtension;
        @SerializedName("IsVideoRecording")
        @Expose
        private Integer isVideoRecording;
        @SerializedName("IsResult")
        @Expose
        private Integer isResult;
        @SerializedName("IsActive")
        @Expose
        private Integer isActive;
        @SerializedName("CreatedByUserId")
        @Expose
        private Integer createdByUserId;
        @SerializedName("CreatedDate")
        @Expose
        private String createdDate;
        @SerializedName("UpdatedByUserId")
        @Expose
        private Integer updatedByUserId;
        @SerializedName("UpdatedDate")
        @Expose
        private String updatedDate;
        @SerializedName("ServerUpdatedStatus")
        @Expose
        private Integer serverUpdatedStatus;

        public String getByteImage() {
            return byteImage;
        }

        public void setByteImage(String byteImage) {
            this.byteImage = byteImage;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getcomplaintCode() {
            return complaintCode;
        }

        public void setcomplaintCode(String complaintCode) {
            this.complaintCode = complaintCode;
        }

        public Integer getModuleTypeId() {
            return moduleTypeId;
        }

        public void setModuleTypeId(Integer moduleTypeId) {
            this.moduleTypeId = moduleTypeId;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileLocation() {
            return fileLocation;
        }

        public void setFileLocation(String fileLocation) {
            this.fileLocation = fileLocation;
        }

        public String getFileExtension() {
            return fileExtension;
        }

        public void setFileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
        }

        public Integer isIsVideoRecording() {
            return isVideoRecording;
        }

        public void setIsVideoRecording(Integer isVideoRecording) {
            this.isVideoRecording = isVideoRecording;
        }

        public Integer isIsResult() {
            return isResult;
        }

        public void setIsResult(Integer isResult) {
            this.isResult = isResult;
        }

        public Integer isIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public Integer getCreatedByUserId() {
            return createdByUserId;
        }

        public void setCreatedByUserId(Integer createdByUserId) {
            this.createdByUserId = createdByUserId;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Integer getUpdatedByUserId() {
            return updatedByUserId;
        }

        public void setUpdatedByUserId(Integer updatedByUserId) {
            this.updatedByUserId = updatedByUserId;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public Integer isServerUpdatedStatus() {
            return serverUpdatedStatus;
        }

        public void setServerUpdatedStatus(Integer serverUpdatedStatus) {
            this.serverUpdatedStatus = serverUpdatedStatus;
        }

    }

