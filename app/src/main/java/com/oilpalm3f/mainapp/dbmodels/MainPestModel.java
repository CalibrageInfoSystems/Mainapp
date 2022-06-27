package com.oilpalm3f.mainapp.dbmodels;

/**
 * Created by siva on 28/05/17.
 */

public class MainPestModel {
    private PestChemicalXref mPestChemicalXref;
    private  Pest pest;

    public Pest getPest() {
        return pest;
    }

    public void setPest(Pest pest) {
        this.pest = pest;
    }

    public PestChemicalXref getmPestChemicalXref() {
        return mPestChemicalXref;
    }

    public void setmPestChemicalXref(PestChemicalXref mPestChemicalXref) {
        this.mPestChemicalXref = mPestChemicalXref;
    }
}
