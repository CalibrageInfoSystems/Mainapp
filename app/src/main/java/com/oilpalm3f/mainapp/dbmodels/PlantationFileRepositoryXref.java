package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by skasam on 6/2/2017.
 */

public class PlantationFileRepositoryXref {

    private Integer PlantationId;
    private Integer FileRepositoryId;
    private  int ServerUpdatedStatus;

    public int getRecmIrrgId() {
        return RecmIrrgId;
    }

    public void setRecmIrrgId(int recmIrrgId) {
        RecmIrrgId = recmIrrgId;
    }

    int  RecmIrrgId;



    public Integer getPlantationId() {
        return PlantationId;
    }

    public void setPlantationId(Integer plantationId) {
        PlantationId = plantationId;
    }

    public Integer getFileRepositoryId() {
        return FileRepositoryId;
    }

    public void setFileRepositoryId(Integer fileRepositoryId) {
        FileRepositoryId = fileRepositoryId;
    }

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }
}
