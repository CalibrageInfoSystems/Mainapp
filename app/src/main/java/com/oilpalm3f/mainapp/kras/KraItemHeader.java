package com.oilpalm3f.mainapp.kras;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.dbmodels.KrasDataToDisplay;

import java.util.List;

/**
 * Created by siva on 09/09/17.
 */

public class KraItemHeader  extends KraAdapterData {
    List<KrasDataToDisplay> krasDataToDisplayList;

    public List<KrasDataToDisplay> getKraData() {
        return krasDataToDisplayList;
    }

    public KraItemHeader(List<KrasDataToDisplay> krasDataToDisplayList) {
        super(R.layout.kra_header, true, null);
        this.krasDataToDisplayList = krasDataToDisplayList;
    }
}
