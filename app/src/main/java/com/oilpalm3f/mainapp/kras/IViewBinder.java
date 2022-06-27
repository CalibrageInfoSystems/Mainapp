package com.oilpalm3f.mainapp.kras;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by siva on 09/09/17.
 */

public interface IViewBinder<T, VH> extends LayoutItemType {
    VH provideViewHolder(View itemView);
    void bindView(StickyHeaderViewAdapter adapter, VH holder, int position, T entity, AppCompatActivity activity);
}
