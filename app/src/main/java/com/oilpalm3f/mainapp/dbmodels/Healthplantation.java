package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 25/05/17.
 */

public class Healthplantation {
    private String PlotCode;
    private Integer PlantationStateTypeId;
    private Integer TreesAppearanceTypeId;
    private Integer TreeGirthTypeId;
    private Integer TreeHeightTypeId;
    private Integer FruitColorTypeId;
    private Integer FruitSizeTypeId;
    private Integer FruitHyegieneTypeId;
    private Integer PlantationTypeId;
    private int IsActive;
    private int CreatedByUserId;
    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;
    private String CropMaintenanceCode;
    int SpearleafId;
    private String SpearLeafRating;
    private String NutDefRating;
    private String BasinHealthRating;
    private String InflorescenceRating;
    private String WeevilsRating;
    private String PestRating;
    private String DiseasesRating;
    private int NoOfFlorescene;
    private int NoOfBuches;
    private int BunchWeight;


    public int getSpearleafId() {
        return SpearleafId;
    }

    public void setSpearleafId(int spearleafId) {
        SpearleafId = spearleafId;
    }

    public String getSpearLeafRating() {
        return SpearLeafRating;
    }

    public void setSpearLeafRating(String spearLeafRating) {
        SpearLeafRating = spearLeafRating;
    }

    public String getNutDefRating() {
        return NutDefRating;
    }

    public void setNutDefRating(String nutDefRating) {
        NutDefRating = nutDefRating;
    }

    public String getBasinHealthRating() {
        return BasinHealthRating;
    }

    public void setBasinHealthRating(String basinHealthRating) {
        BasinHealthRating = basinHealthRating;
    }

    public String getInflorescenceRating() {
        return InflorescenceRating;
    }

    public void setInflorescenceRating(String inflorescenceRating) {
        InflorescenceRating = inflorescenceRating;
    }

    public String getWeevilsRating() {
        return WeevilsRating;
    }

    public void setWeevilsRating(String weevilsRating) {
        WeevilsRating = weevilsRating;
    }

    public String getPestRating() {
        return PestRating;
    }

    public void setPestRating(String pestRating) {
        PestRating = pestRating;
    }

    public String getDiseasesRating() {
        return DiseasesRating;
    }

    public void setDiseasesRating(String diseasesRating) {
        DiseasesRating = diseasesRating;
    }

    public Integer getPlantationstatetypeid() {
        return PlantationStateTypeId;
    }

    public void setPlantationstatetypeid(Integer PlantationStateTypeId) {
        this.PlantationStateTypeId = PlantationStateTypeId;
    }

    public Integer getTreesappearancetypeid() {
        return TreesAppearanceTypeId;
    }

    public void setTreesappearancetypeid(Integer TreesAppearanceTypeId) {
        this.TreesAppearanceTypeId = TreesAppearanceTypeId;
    }

    public Integer getTreegirthtypeid() {
        return TreeGirthTypeId;
    }

    public void setTreegirthtypeid(Integer TreeGirthTypeId) {
        this.TreeGirthTypeId = TreeGirthTypeId;
    }

    public Integer getTreeheighttypeid() {
        return TreeHeightTypeId;
    }

    public void setTreeheighttypeid(Integer TreeHeightTypeId) {
        this.TreeHeightTypeId = TreeHeightTypeId;
    }

    public Integer getFruitcolortypeid() {
        return FruitColorTypeId;
    }

    public void setFruitcolortypeid(Integer FruitColorTypeId) {
        this.FruitColorTypeId = FruitColorTypeId;
    }

    public Integer getFruitsizetypeid() {
        return FruitSizeTypeId;
    }

    public void setFruitsizetypeid(Integer FruitSizeTypeId) {
        this.FruitSizeTypeId = FruitSizeTypeId;
    }

    public Integer getFruithyegienetypeid() {
        return FruitHyegieneTypeId;
    }

    public void setFruithyegienetypeid(Integer FruitHyegieneTypeId) {
        this.FruitHyegieneTypeId = FruitHyegieneTypeId;
    }

    public Integer getPlantationtypeid() {
        return PlantationTypeId;
    }

    public void setPlantationtypeid(Integer PlantationTypeId) {
        this.PlantationTypeId = PlantationTypeId;
    }

    public int getIsactive() {
        return IsActive;
    }

    public void setIsactive(int IsActive) {
        this.IsActive = IsActive;
    }

    public int getCreatedbyuserid() {
        return CreatedByUserId;
    }

    public void setCreatedbyuserid(int CreatedByUserId) {
        this.CreatedByUserId = CreatedByUserId;
    }

    public String getCreateddate() {
        return CreatedDate;
    }

    public void setCreateddate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public int getUpdatedbyuserid() {
        return UpdatedByUserId;
    }

    public void setUpdatedbyuserid(int UpdatedByUserId) {
        this.UpdatedByUserId = UpdatedByUserId;
    }

    public String getUpdateddate() {
        return UpdatedDate;
    }

    public void setUpdateddate(String UpdatedDate) {
        this.UpdatedDate = UpdatedDate;
    }

    public String getPlotCode() {
        return PlotCode;
    }

    public void setPlotCode(String plotCode) {
        PlotCode = plotCode;
    }

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }

    public String getCropMaintenanceCode() {
        return CropMaintenanceCode;
    }

    public void setCropMaintenanceCode(String cropMaintenanceCode) {
        CropMaintenanceCode = cropMaintenanceCode;
    }


    public int getNoOfFlorescene() {
        return NoOfFlorescene;
    }

    public void setNoOfFlorescene(int noOfFlorescene) {
        NoOfFlorescene = noOfFlorescene;
    }

    public int getNoOfBuches() {
        return NoOfBuches;
    }

    public void setNoOfBuches(int noOfBuches) {
        NoOfBuches = noOfBuches;
    }

    public int getBunchWeight() {
        return BunchWeight;
    }

    public void setBunchWeight(int bunchWeight) {
        BunchWeight = bunchWeight;
    }
}