package com.oilpalm3f.mainapp.ui;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.ApplicationThread;
import com.oilpalm3f.mainapp.common.CommonUtils;
import com.oilpalm3f.mainapp.database.DataAccessHandler;
import com.oilpalm3f.mainapp.database.Queries;
import com.oilpalm3f.mainapp.datasync.helpers.DataManager;
import com.oilpalm3f.mainapp.dbmodels.ComplaintRepository;
import com.oilpalm3f.mainapp.dbmodels.ComplaintStatusHistory;
import com.oilpalm3f.mainapp.dbmodels.ComplaintTypeXref;
import com.oilpalm3f.mainapp.dbmodels.Complaints;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewCurrentComplaintFragment extends BaseFragment {

    private static final String LOG_TAG = ViewCurrentComplaintFragment.class.getName();

    private View rootView;
    private android.widget.TextView compId;
    private android.widget.TextView compType;
    private android.widget.TextView compStatus;
    private android.widget.TextView compComments;
    private android.widget.ImageView farmerImage;
    private android.widget.ImageView secondImageRelImage;
    private Complaints complaintData;
    private ArrayList<ComplaintRepository> complaintRepository;
    private ComplaintTypeXref complaintsTypeXref;
    private ComplaintStatusHistory complaintsStatusHistory;

    public ViewCurrentComplaintFragment() {

    }

    @Override
    public void Initialize() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        rootView = inflater.inflate(R.layout.fragment_view_current_complaint, null);
        baseLayout.addView(rootView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setTile(getActivity().getResources().getString(R.string.view_complaints));

        initView();

        bindData();
    }

    private void bindData() {
        DataAccessHandler dataAccessHandler = new DataAccessHandler(getActivity());
        complaintsStatusHistory = (ComplaintStatusHistory) DataManager.getInstance().getDataFromManager(DataManager.NEW_COMPLAINT_STATUS_HISTORY);
        complaintsTypeXref = (ComplaintTypeXref) DataManager.getInstance().getDataFromManager(DataManager.NEW_COMPLAINT_TYPE);
        complaintRepository = (ArrayList<ComplaintRepository>) DataManager.getInstance().getDataFromManager(DataManager.NEW_COMPLAINT_REPOSITORY);
        complaintData = (Complaints) DataManager.getInstance().getDataFromManager(DataManager.NEW_COMPLAINT_DETAILS);

        if (null != complaintsStatusHistory) {
            compId.setText(complaintData.getCode());

            String complaintType = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getTypecdDesc(complaintsTypeXref.getComplaintTypeId()));
            String complaintStatus = dataAccessHandler.getOnlyOneValueFromDb(Queries.getInstance().getTypecdDesc(complaintsStatusHistory.getStatusTypeId()));
            String comments = complaintsStatusHistory.getComments();

            loadImageFromStorage(complaintRepository.get(0).getFileLocation(), farmerImage, complaintRepository.get(0));

            ApplicationThread.uiPost(LOG_TAG, "", new Runnable() {
                @Override
                public void run() {
                    loadImageFromStorage(complaintRepository.get(1).getFileLocation(), secondImageRelImage, complaintRepository.get(1));
                }
            }, 1000);

            compType.setText(complaintType);
            compStatus.setText(complaintStatus);
            compComments.setText(comments);
        }
    }

    private void initView() {
        compId = (TextView) rootView.findViewById(R.id.compId);
        compType = (TextView) rootView.findViewById(R.id.compType);
        compStatus = (TextView) rootView.findViewById(R.id.compStatus);
        compComments = (TextView) rootView.findViewById(R.id.compComments);
        farmerImage = (ImageView) rootView.findViewById(R.id.farmer_image);
        secondImageRelImage = (ImageView) rootView.findViewById(R.id.secondImageRelImage);
    }

    private void loadImageFromStorage(final String path, final ImageView imageViewToUpdate, final ComplaintRepository complaintRepository) {
        if (null != complaintRepository) {
            final String imageUrl = CommonUtils.getImageUrl(complaintRepository);

            Picasso.with(getActivity())
                    .load(imageUrl)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(imageViewToUpdate, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            if (!getActivity().isFinishing()) {
                                Glide.with(getActivity())
                                        .load(path)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .into(imageViewToUpdate);

                            }
                        }
                    });
        } else {
            if (!getActivity().isFinishing()) {
                Glide.with(getActivity())
                        .load(path)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imageViewToUpdate);
            }
        }
    }

}
