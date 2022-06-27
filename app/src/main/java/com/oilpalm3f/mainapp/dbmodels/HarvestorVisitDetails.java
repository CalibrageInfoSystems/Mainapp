package com.oilpalm3f.mainapp.dbmodels;

public class HarvestorVisitDetails {

    private int Id;
    private String HarvestorVisitCode;
    private int HarvestorTypeId;
    private String HarvestorCode;
    private int HasPole;
    private Double TonnageCost;
    private int Interval;
    private Double Quantity;
    private String CCCode;

    private Double UnRipen;
    private Double UnderRipe;
    private Double Ripen;
    private Double OverRipe;
    private Double Diseased;
    private Double EmptyBunches;

    private Double FFBQualityLong;
    private Double FFBQualityMedium;
    private Double FFBQualityShort;
    private Double FFBQualityOptimum;

    private int FarmerAvailable;
    private Integer  DetailesInformed;
    private String Name;
    private String MobileNumber;
    private String Village;
    private String Mandal;
    private int CreatedByUserId;
    private String CreatedDate;
    private int ServerUpdatedStatus;
    private int LooseFruit;
    private Integer LooseFruitWeight;



    public int getLooseFruit() {
        return LooseFruit;
    }

    public void setLooseFruit(int looseFruit) {
        LooseFruit = looseFruit;
    }

    public Integer getLooseFruitWeight() {
        return LooseFruitWeight;
    }

    public void setLooseFruitWeight(Integer looseFruitWeight) {
        LooseFruitWeight = looseFruitWeight;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getHarvestorVisitCode() {
        return HarvestorVisitCode;
    }

    public void setHarvestorVisitCode(String harvestorVisitCode) {
        HarvestorVisitCode = harvestorVisitCode;
    }

    public int getHarvestorTypeId() {
        return HarvestorTypeId;
    }

    public void setHarvestorTypeId(int harvestorTypeId) {
        HarvestorTypeId = harvestorTypeId;
    }

    public String getHarvestorCode() {
        return HarvestorCode;
    }

    public void setHarvestorCode(String harvestorCode) {
        HarvestorCode = harvestorCode;
    }

    public int getHasPole() {
        return HasPole;
    }

    public void setHasPole(int hasPole) {
        HasPole = hasPole;
    }

    public Double getTonnageCost() {
        return TonnageCost;
    }

    public void setTonnageCost(Double tonnageCost) {
        TonnageCost = tonnageCost;
    }

    public int getInterval() {
        return Interval;
    }

    public void setInterval(int interval) {
        Interval = interval;
    }

    public Double getQuantity() {
        return Quantity;
    }

    public void setQuantity(Double quantity) {
        Quantity = quantity;
    }

    public String getCCCode() {
        return CCCode;
    }

    public void setCCCode(String CCCode) {
        this.CCCode = CCCode;
    }

    public Double getUnRipen() {
        return UnRipen;
    }

    public void setUnRipen(Double unRipen) {
        UnRipen = unRipen;
    }

    public Double getUnderRipe() {
        return UnderRipe;
    }

    public void setUnderRipe(Double underRipe) {
        UnderRipe = underRipe;
    }

    public Double getRipen() {
        return Ripen;
    }

    public void setRipen(Double ripen) {
        Ripen = ripen;
    }

    public Double getOverRipe() {
        return OverRipe;
    }

    public void setOverRipe(Double overRipe) {
        OverRipe = overRipe;
    }

    public Double getDiseased() {
        return Diseased;
    }

    public void setDiseased(Double diseased) {
        Diseased = diseased;
    }

    public Double getEmptyBunches() {
        return EmptyBunches;
    }

    public void setEmptyBunches(Double emptyBunches) {
        EmptyBunches = emptyBunches;
    }

    public Double getFFBQualityLong() {
        return FFBQualityLong;
    }

    public void setFFBQualityLong(Double FFBQualityLong) {
        this.FFBQualityLong = FFBQualityLong;
    }

    public Double getFFBQualityMedium() {
        return FFBQualityMedium;
    }

    public void setFFBQualityMedium(Double FFBQualityMedium) {
        this.FFBQualityMedium = FFBQualityMedium;
    }

    public Double getFFBQualityShort() {
        return FFBQualityShort;
    }

    public void setFFBQualityShort(Double FFBQualityShort) {
        this.FFBQualityShort = FFBQualityShort;
    }

    public Double getFFBQualityOptimum() {
        return FFBQualityOptimum;
    }

    public void setFFBQualityOptimum(Double FFBQualityOptimum) {
        this.FFBQualityOptimum = FFBQualityOptimum;
    }

    public int getFarmerAvailable() {
        return FarmerAvailable;
    }

    public void setFarmerAvailable(int farmerAvailable) {
        FarmerAvailable = farmerAvailable;
    }

    public Integer getDetailesInformed() {
        return DetailesInformed;
    }

    public void setDetailesInformed(Integer detailesInformed) {
        DetailesInformed = detailesInformed;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getVillage() {
        return Village;
    }

    public void setVillage(String village) {
        Village = village;
    }

    public String getMandal() {
        return Mandal;
    }

    public void setMandal(String mandal) {
        Mandal = mandal;
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

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }
}
