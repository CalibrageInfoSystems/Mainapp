package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by pc on 01-10-2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlantProtectionModel {

    @SerializedName("PlantProtectionId")
    @Expose
    private String plantProtectionId;
    @SerializedName("PlantProtecionTypeId")
    @Expose
    private String plantProtecionTypeId;
    @SerializedName("FarmerCode")
    @Expose
    private String farmerCode;
    @SerializedName("PlotCode")
    @Expose
    private String plotCode;
    @SerializedName("DiseaseCode")
    @Expose
    private String diseaseCode;
    @SerializedName("DiseaseName")
    @Expose
    private String diseaseName;
    @SerializedName("ChemicalCode")
    @Expose
    private String chemicalCode;
    @SerializedName("ChemicalName")
    @Expose
    private String chemicalName;
    @SerializedName("PestCode")
    @Expose
    private String pestCode;
    @SerializedName("PestName")
    @Expose
    private String pestName;
    @SerializedName("UOM")
    @Expose
    private String uOM;
    @SerializedName("DosageGiven")
    @Expose
    private String dosageGiven;
    @SerializedName("LastAppliedDate")
    @Expose
    private String lastAppliedDate;
    @SerializedName("Observations")
    @Expose
    private String observations;
    @SerializedName("Weavils")
    @Expose
    private String weavils;
    @SerializedName("Mulching")
    @Expose
    private String mulching;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("UpdatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("ServerUpdatedStatus")
    @Expose
    private String serverUpdatedStatus;

    /**
     *
     * @return
     * The plantProtectionId
     */
    public String getPlantProtectionId() {
        return plantProtectionId;
    }

    /**
     *
     * @param plantProtectionId
     * The PlantProtectionId
     */
    public void setPlantProtectionId(String plantProtectionId) {
        this.plantProtectionId = plantProtectionId;
    }

    /**
     *
     * @return
     * The plantProtecionTypeId
     */
    public String getPlantProtecionTypeId() {
        return plantProtecionTypeId;
    }

    /**
     *
     * @param plantProtecionTypeId
     * The PlantProtecionTypeId
     */
    public void setPlantProtecionTypeId(String plantProtecionTypeId) {
        this.plantProtecionTypeId = plantProtecionTypeId;
    }

    /**
     *
     * @return
     * The farmerCode
     */
    public String getFarmerCode() {
        return farmerCode;
    }

    /**
     *
     * @param farmerCode
     * The FarmerCode
     */
    public void setFarmerCode(String farmerCode) {
        this.farmerCode = farmerCode;
    }

    /**
     *
     * @return
     * The plotCode
     */
    public String getPlotCode() {
        return plotCode;
    }

    /**
     *
     * @param plotCode
     * The PlotCode
     */
    public void setPlotCode(String plotCode) {
        this.plotCode = plotCode;
    }

    /**
     *
     * @return
     * The diseaseCode
     */
    public String getDiseaseCode() {
        return diseaseCode;
    }

    /**
     *
     * @param diseaseCode
     * The DiseaseCode
     */
    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    /**
     *
     * @return
     * The diseaseName
     */
    public String getDiseaseName() {
        return diseaseName;
    }

    /**
     *
     * @param diseaseName
     * The DiseaseName
     */
    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    /**
     *
     * @return
     * The chemicalCode
     */
    public String getChemicalCode() {
        return chemicalCode;
    }

    /**
     *
     * @param chemicalCode
     * The ChemicalCode
     */
    public void setChemicalCode(String chemicalCode) {
        this.chemicalCode = chemicalCode;
    }

    /**
     *
     * @return
     * The chemicalName
     */
    public String getChemicalName() {
        return chemicalName;
    }

    /**
     *
     * @param chemicalName
     * The ChemicalName
     */
    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    /**
     *
     * @return
     * The pestCode
     */
    public String getPestCode() {
        return pestCode;
    }

    /**
     *
     * @param pestCode
     * The PestCode
     */
    public void setPestCode(String pestCode) {
        this.pestCode = pestCode;
    }

    /**
     *
     * @return
     * The pestName
     */
    public String getPestName() {
        return pestName;
    }

    /**
     *
     * @param pestName
     * The PestName
     */
    public void setPestName(String pestName) {
        this.pestName = pestName;
    }

    /**
     *
     * @return
     * The uOM
     */
    public String getUOM() {
        return uOM;
    }

    /**
     *
     * @param uOM
     * The UOM
     */
    public void setUOM(String uOM) {
        this.uOM = uOM;
    }

    /**
     *
     * @return
     * The dosageGiven
     */
    public String getDosageGiven() {
        return dosageGiven;
    }

    /**
     *
     * @param dosageGiven
     * The DosageGiven
     */
    public void setDosageGiven(String dosageGiven) {
        this.dosageGiven = dosageGiven;
    }

    /**
     *
     * @return
     * The lastAppliedDate
     */
    public String getLastAppliedDate() {
        return lastAppliedDate;
    }

    /**
     *
     * @param lastAppliedDate
     * The LastAppliedDate
     */
    public void setLastAppliedDate(String lastAppliedDate) {
        this.lastAppliedDate = lastAppliedDate;
    }

    /**
     *
     * @return
     * The observations
     */
    public String getObservations() {
        return observations;
    }

    /**
     *
     * @param observations
     * The Observations
     */
    public void setObservations(String observations) {
        this.observations = observations;
    }

    /**
     *
     * @return
     * The weavils
     */
    public String getWeavils() {
        return weavils;
    }

    /**
     *
     * @param weavils
     * The Weavils
     */
    public void setWeavils(String weavils) {
        this.weavils = weavils;
    }

    /**
     *
     * @return
     * The mulching
     */
    public String getMulching() {
        return mulching;
    }

    /**
     *
     * @param mulching
     * The Mulching
     */
    public void setMulching(String mulching) {
        this.mulching = mulching;
    }

    /**
     *
     * @return
     * The createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     * The CreatedBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return
     * The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     * The CreatedDate
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return
     * The updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     *
     * @param updatedBy
     * The UpdatedBy
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     *
     * @return
     * The updatedDate
     */
    public String getUpdatedDate() {
        return updatedDate;
    }

    /**
     *
     * @param updatedDate
     * The UpdatedDate
     */
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     *
     * @return
     * The serverUpdatedStatus
     */
    public String getServerUpdatedStatus() {
        return serverUpdatedStatus;
    }

    /**
     *
     * @param serverUpdatedStatus
     * The ServerUpdatedStatus
     */
    public void setServerUpdatedStatus(String serverUpdatedStatus) {
        this.serverUpdatedStatus = serverUpdatedStatus;
    }

}
