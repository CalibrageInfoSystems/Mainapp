package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by Lenovo on 3/5/2018.
 */

public class Handler {
    public String tab_id;
    public String version;

    public Handler() {
    }

    public Handler(String tab_id, String version) {
        this.tab_id = tab_id;
        this.version = version;

    }

    public String getTab_id() {
        return tab_id;
    }

    public void setTab_id(String tab_id) {
        this.tab_id = tab_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
