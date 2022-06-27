package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by skasam on 6/1/2017.
 */

public class Resources {

    private String PlotCode;
    private Integer IsPowerAvailable;
    private Integer PowerTypeId;
    private Double AvailablePowerHours;
    private String Comments;
    private Integer SoilTypeId;
    private Integer SourceofWaterId;
    private int BorewellNumber;
    private Double Depth;
    private Double MotorCapacity;
    private Double FieldDistance;
    private Double CanalWater;
    private Double WaterDischargeCapacity;
    private Double TotalWaterDischare;
    private Double TotalWaterDischargeCapacity;
    private Integer IrrigationTypeId;
    private Integer PrioritizationTypeId;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;

    public String getPlotCode() {
        return PlotCode;
    }

    public void setPlotCode(String plotCode) {
        PlotCode = plotCode;
    }

    public Integer getIsPowerAvailable() {
        return IsPowerAvailable;
    }

    public void setIsPowerAvailable(Integer isPowerAvailable) {
        IsPowerAvailable = isPowerAvailable;
    }

    public Integer getPowerTypeId() {
        return PowerTypeId;
    }

    public void setPowerTypeId(Integer powerTypeId) {
        PowerTypeId = powerTypeId;
    }

    public Double getAvailablePowerHours() {
        return AvailablePowerHours;
    }

    public void setAvailablePowerHours(Double availablePowerHours) {
        AvailablePowerHours = availablePowerHours;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public Integer getSoilTypeId() {
        return SoilTypeId;
    }

    public void setSoilTypeId(Integer soilTypeId) {
        SoilTypeId = soilTypeId;
    }

    public Integer getSourceofWaterId() {
        return SourceofWaterId;
    }

    public void setSourceofWaterId(Integer sourceofWaterId) {
        SourceofWaterId = sourceofWaterId;
    }

    public int getBorewellNumber() {
        return BorewellNumber;
    }

    public void setBorewellNumber(int borewellNumber) {
        BorewellNumber = borewellNumber;
    }

    public Double getDepth() {
        return Depth;
    }

    public void setDepth(Double depth) {
        Depth = depth;
    }

    public Double getMotorCapacity() {
        return MotorCapacity;
    }

    public void setMotorCapacity(Double motorCapacity) {
        MotorCapacity = motorCapacity;
    }

    public Double getFieldDistance() {
        return FieldDistance;
    }

    public void setFieldDistance(Double fieldDistance) {
        FieldDistance = fieldDistance;
    }

    public Double getCanalWater() {
        return CanalWater;
    }

    public void setCanalWater(Double canalWater) {
        CanalWater = canalWater;
    }

    public Double getWaterDischargeCapacity() {
        return WaterDischargeCapacity;
    }

    public void setWaterDischargeCapacity(Double waterDischargeCapacity) {
        WaterDischargeCapacity = waterDischargeCapacity;
    }

    public Double getTotalWaterDischare() {
        return TotalWaterDischare;
    }

    public void setTotalWaterDischare(Double totalWaterDischare) {
        TotalWaterDischare = totalWaterDischare;
    }

    public Double getTotalWaterDischargeCapacity() {
        return TotalWaterDischargeCapacity;
    }

    public void setTotalWaterDischargeCapacity(Double totalWaterDischargeCapacity) {
        TotalWaterDischargeCapacity = totalWaterDischargeCapacity;
    }

    public Integer getIrrigationTypeId() {
        return IrrigationTypeId;
    }

    public void setIrrigationTypeId(Integer irrigationTypeId) {
        IrrigationTypeId = irrigationTypeId;
    }

    public Integer getPrioritizationTypeId() {
        return PrioritizationTypeId;
    }

    public void setPrioritizationTypeId(Integer prioritizationTypeId) {
        PrioritizationTypeId = prioritizationTypeId;
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
