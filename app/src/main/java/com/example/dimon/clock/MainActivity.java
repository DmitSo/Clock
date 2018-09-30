package com.example.dimon.clock;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Time    mTime;                  // used for updating time
    Date    mDate;                  // used just for updating date, not time
    Handler mUpdateClockHandler;    // used for executing clock update every sec

    TextView tvTime, tvDate;

    /**
     * This object has method run() which updates clock in current activity.
     */
    Runnable mUpdateClockRunnable = new Runnable() {
        @Override
        public void run() {
            mTime.setToNow();
            if (mTime.hour == 0 && mTime.minute == 0 && mTime.second == 0) {
                mDate = new Date();
            }

            updateClock(mDate, mTime);
            mUpdateClockHandler.postDelayed(mUpdateClockRunnable, 1000);
        }
    };

    /**
     * Variables initializes here
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = findViewById(R.id.tvTime);
        tvDate = findViewById(R.id.tvDate);
        mDate = new Date();
        mTime = new Time();
        mUpdateClockHandler = new Handler();
    }

    @Override
    protected void onResume() {
        mUpdateClockHandler.post(mUpdateClockRunnable);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mUpdateClockHandler.removeCallbacks(mUpdateClockRunnable);
        super.onPause();
    }


    /**
     * Method which updates clock using date and time provided in parameters
     * (Important : time info in Date variable aren't used, Time variable used instead)
     * @param date current date
     * @param time current time
     */
    private void updateClock(Date date, Time time) {
        int timeFormatId = R.string.time_format;
        int dateFormatId = R.string.date_format;

        String formattedTime = getString(timeFormatId,
                time.hour,
                time.minute,
                time.second);
        String formattedDate = getResources().getString(dateFormatId, date);

        tvTime.setText(formattedTime);
        tvDate.setText(formattedDate);
    }
}
