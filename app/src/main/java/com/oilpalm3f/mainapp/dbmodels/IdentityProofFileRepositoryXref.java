package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by skasam on 6/2/2017.
 */

public class IdentityProofFileRepositoryXref {

    private Integer IdentityProofId;
    private Integer FileRepositoryId;
    private int ServerUpdatedStatus;

    public Integer getIdentityProofId() {
        return IdentityProofId;
    }

    public void setIdentityProofId(Integer identityProofId) {
        IdentityProofId = identityProofId;
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
