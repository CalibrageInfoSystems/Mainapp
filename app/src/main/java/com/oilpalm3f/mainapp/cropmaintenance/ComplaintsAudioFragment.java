package com.oilpalm3f.mainapp.cropmaintenance;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.mainapp.R;
import com.oilpalm3f.mainapp.cloudhelper.Log;

//Displays recorded audio clips
public class ComplaintsAudioFragment extends DialogFragment {

    private static final String LOG_TAG = ComplaintsAudioFragment.class.getSimpleName();
    long timeWhenPaused = 0;
    //Recording controls
    private ImageView mRecordButton = null;
    private Button btnSave = null;
    private TextView mRecordingPrompt;
    private int mRecordPromptCount = 0;
    private boolean mStartRecording = true;
    private Chronometer mChronometer = null;
    private String filePath;

    public void setRecordingFinishedListener(RecordingFinishedListener recordingFinishedListener) {
        this.recordingFinishedListener = recordingFinishedListener;
    }

    private RecordingFinishedListener recordingFinishedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View recordView = inflater.inflate(R.layout.fragment_complaint_audio, null);
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        recordView.setMinimumWidth((int) (displayRectangle.width() * 0.7f));
        recordView.setMinimumHeight((int) (displayRectangle.width() * 0.4f));

        mChronometer = (Chronometer) recordView.findViewById(R.id.chronometer);
        //update recording prompt text
        mRecordingPrompt = (TextView) recordView.findViewById(R.id.recording_status_text);
        mRecordingPrompt.setVisibility(View.VISIBLE);
        mRecordButton = (ImageView) recordView.findViewById(R.id.recordBtn);
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                mStartRecording = !mStartRecording;
            }
        });
        btnSave = (Button) recordView.findViewById(R.id.btnPlay);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

        Bundle bundle = getArguments();
        filePath = bundle.getString("filePath");

        return recordView;
    }

    private void onRecord(boolean start) {
        Intent intent = new Intent(getActivity(), RecordingService.class);
        intent.putExtra("filePath", filePath);
        if (start) {
            // start recording
            mRecordButton.setVisibility(View.GONE);
            btnSave.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Recording started", Toast.LENGTH_SHORT).show();
            //start Chronometer
            mChronometer.setBase(SystemClock.elapsedRealtime());
            mChronometer.start();
            mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    long timeElapsed = SystemClock.elapsedRealtime() - chronometer.getBase();

                    if (mRecordPromptCount == 0) {
                        mRecordingPrompt.setText(getString(R.string.record_in_progress) + ".");
                    } else if (mRecordPromptCount == 1) {
                        mRecordingPrompt.setText(getString(R.string.record_in_progress) + "..");
                    } else if (mRecordPromptCount == 2) {
                        mRecordingPrompt.setText(getString(R.string.record_in_progress) + "...");
                        mRecordPromptCount = -1;
                    }

                    int hours = (int) (timeElapsed / 3600000);
                    int minutes = (int) (timeElapsed - hours * 3600000) / 60000;
                    Log.v(LOG_TAG, "@@@@ minutes test "+minutes);
                    if (minutes == 3) {
                        stopRecording();
                    }
                    mRecordPromptCount++;
                }
            });

            //start RecordingService
            getActivity().startService(intent);
            //keep screen on while recording
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            mRecordingPrompt.setText(getString(R.string.record_in_progress) + ".");
            mRecordPromptCount++;

        }
    }

    public void stopRecording() {
        Intent intent = new Intent(getActivity(), RecordingService.class);
        mChronometer.stop();
        mChronometer.setBase(SystemClock.elapsedRealtime());
        timeWhenPaused = 0;
        mRecordingPrompt.setText(getString(R.string.record_prompt));

        getActivity().stopService(intent);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        recordingFinishedListener.onRecordingFinished(filePath);
        dismiss();
    }

    public interface RecordingFinishedListener {
        void onRecordingFinished(String filePath);
    }
}
