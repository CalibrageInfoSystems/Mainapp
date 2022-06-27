package com.oilpalm3f.mainapp.kras;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by siva on 09/09/17.
 */

public abstract class ViewBinder<T extends IViewBinderProvider, VH extends RecyclerView.ViewHolder> implements IViewBinder<T, VH> {
    public abstract VH provideViewHolder(View itemView);

    public abstract void bindView(StickyHeaderViewAdapter adapter, VH holder, int position, T entity, AppCompatActivity activity);

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View rootView) {
            super(rootView);
        }

        protected <T extends View> T findViewById(@IdRes int id) {
            return (T) itemView.findViewById(id);
        }
    }

}
