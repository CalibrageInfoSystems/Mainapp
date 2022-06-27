package com.oilpalm3f.mainapp.kras;

import android.support.v4.util.SparseArrayCompat;

/**
 * Created by siva on 09/09/17.
 */

public interface IViewBinderProvider {
    IViewBinder provideViewBinder(StickyHeaderViewAdapter adapter, SparseArrayCompat<? extends IViewBinder> viewBinderPool, int position);
}
