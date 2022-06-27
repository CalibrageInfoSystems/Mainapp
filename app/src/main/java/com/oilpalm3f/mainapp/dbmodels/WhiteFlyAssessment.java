package com.oilpalm3f.mainapp.dbmodels;

public class WhiteFlyAssessment {
//    private int Id;
    private String CropMaintenaceCode;
    private String Question;
    private String Answer;
    private String Value;
    private int Year;
    private Integer IsActive;
    private Integer CreatedByUserId;
    private String CreatedDate;
    private Integer UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }

//    public int getId() {
//        return Id;
//    }
//
//    public void setId(int id) {
//        Id = id;
//    }

    public String getCropMaintenaceCode() {
        return CropMaintenaceCode;
    }

    public void setCropMaintenaceCode(String cropMaintenaceCode) {
        CropMaintenaceCode = cropMaintenaceCode;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public Integer getIsActive() {
        return IsActive;
    }

    public void setIsActive(Integer isActive) {
        IsActive = isActive;
    }

    public Integer getCreatedByUserId() {
        return CreatedByUserId;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        CreatedByUserId = createdByUserId;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public Integer getUpdatedByUserId() {
        return UpdatedByUserId;
    }

    public void setUpdatedByUserId(Integer updatedByUserId) {
        UpdatedByUserId = updatedByUserId;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public WhiteFlyAssessment() {
    }
    public WhiteFlyAssessment(String question, String answer, String value, int year) {
        Question = question;
        Answer = answer;
        Value = value;
        Year = year;
    }
}
