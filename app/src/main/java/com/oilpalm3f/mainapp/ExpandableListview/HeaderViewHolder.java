package com.oilpalm3f.mainapp.ExpandableListview;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.CloudDataHandler;
import com.oilpalm3f.mainapp.dbmodels.KrasDataToDisplay;
import com.oilpalm3f.mainapp.kras.KraItemHeader;

import java.util.List;

/**
 * Created by Lenovo on 11/16/2017.
 */

public class HeaderViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;
    TextView tvPrefix, annualTargetTxt, annualAchievedTargetTxt;
    ImageView ivDropDown;
    private LinearLayout yearLL;
    private TextView mMovieTextView;

    public HeaderViewHolder(View rootView) {
        super(rootView);
        this.annualTargetTxt = (TextView) rootView.findViewById(R.id.annualTarget);
        this.annualAchievedTargetTxt = (TextView) rootView.findViewById(R.id.annualAchievedTarget);
        this.yearLL = (LinearLayout) rootView.findViewById(R.id.yearLL);
        this.tvPrefix = (TextView) rootView.findViewById(R.id.tv_prefix);
        this.ivDropDown=(ImageView)rootView.findViewById(R.id.ivExpand);
    }

    public void bind(String krasDataToDisplay) {
       /* KraItemHeader entity=null;
        List<KrasDataToDisplay> krasDataToDisplays = entity.getKraData();
       tvPrefix.setText(krasDataToDisplays.get(0).getkRACode() + "-" + krasDataToDisplays.get(0).getkRAName());
       annualTargetTxt.setText(""+krasDataToDisplays.get(0).getAnnualTarget()+ " " + krasDataToDisplays.get(0).getuOM());
       annualAchievedTargetTxt.setText(""+krasDataToDisplays.get(0).getAnnualAchievedTarget()+ " " + krasDataToDisplays.get(0).getuOM());
*/
       /*
        KraItemHeader entity=null;
        List<KrasDataToDisplay> krasDataToDisplays = entity.getKraData();*/
       String krasDataToDisplays[]=  krasDataToDisplay.split("\n");
        tvPrefix.setText(krasDataToDisplays[0] + "-" + krasDataToDisplays[1]);
        annualTargetTxt.setText(""+krasDataToDisplays[2]+ " " + krasDataToDisplays[3]);
        annualAchievedTargetTxt.setText(""+krasDataToDisplays[4]+ " " + krasDataToDisplays[5]);

    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);

        if (expanded) {
            ivDropDown.setImageResource(R.drawable.ic_minus);
        } else {
            ivDropDown.setImageResource(R.drawable.ic_add);
        }

    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);

        RotateAnimation rotateAnimation;
        if (expanded) { // rotate clockwise
            rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                    INITIAL_POSITION,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        } else { // rotate counterclockwise
            rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                    INITIAL_POSITION,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        }

        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        ivDropDown.startAnimation(rotateAnimation);

    }
}

