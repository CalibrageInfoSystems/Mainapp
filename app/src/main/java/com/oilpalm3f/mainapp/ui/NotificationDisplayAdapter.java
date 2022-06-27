package com.oilpalm3f.mainapp.ui;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.dbmodels.Alerts;

import java.util.List;

//Displaying Notifications Adapter
public class NotificationDisplayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Alerts> alertsList;

    public NotificationDisplayAdapter(List<Alerts> alertsList) {
        this.alertsList = alertsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noti_display_item, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NotificationViewHolder) {
            NotificationViewHolder notificationViewHolder = (NotificationViewHolder) holder;
            Alerts alerts = alertsList.get(position);
            notificationViewHolder.headerTextView.setText(""+alerts.getName());
            notificationViewHolder.descriptionTextView.setText(alerts.getDesc());
            notificationViewHolder.createdDateTextView.setText(CommonUtils.getProperDate(alerts.getCreatedDate()));

            if (alerts.getIsRead() == 0) {
                notificationViewHolder.descriptionTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                notificationViewHolder.headerTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                notificationViewHolder.createdDateTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
            } else {
                notificationViewHolder.descriptionTextView.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                notificationViewHolder.headerTextView.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                notificationViewHolder.createdDateTextView.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
            }

        }
    }

    @Override
    public int getItemCount() {
        return alertsList.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView headerTextView;
        TextView descriptionTextView;
        TextView createdDateTextView;

        NotificationViewHolder(View view) {
            super(view);
            headerTextView = (TextView) view.findViewById(R.id.notiHeaderTxt);
            descriptionTextView = (TextView) view.findViewById(R.id.notiDesc);
            createdDateTextView = (TextView) view.findViewById(R.id.notiDate);
        }
    }
}
