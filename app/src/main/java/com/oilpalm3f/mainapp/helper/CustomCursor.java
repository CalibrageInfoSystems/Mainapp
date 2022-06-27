package com.oilpalm3f.mainapp.helper;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by siva on 15/06/17.
 */

public class CustomCursor extends CursorWrapper {

    public CustomCursor(Cursor cursor) { super(cursor); } //simple constructor

    public Integer getInteger(int columnIndex) { // new method to return Integer instead of int
        if (super.isNull(columnIndex)){
            return null;
        }else{
            return super.getInt(columnIndex);
        }
    }
}
