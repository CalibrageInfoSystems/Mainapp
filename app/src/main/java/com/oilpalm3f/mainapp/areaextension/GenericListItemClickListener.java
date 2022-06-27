package com.oilpalm3f.mainapp.areaextension;

/**
 * Created by siva on 06/05/17.
 */

//interface Listener for Edit/Delete Clicks
public interface GenericListItemClickListener {
    void onEditClicked(int position, int tag);
    void onDeleteClicked(int position, int tag);
}
