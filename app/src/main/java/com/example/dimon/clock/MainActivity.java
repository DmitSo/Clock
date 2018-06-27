package com.example.dimon.clock;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    final Time time = new Time();
    Date date = new Date();
    final Handler handler = new Handler();

    TextView tvHours, tvMinutes, tvSeconds, tvAmPm, tvDate;

    final Runnable updateClockRunnable = new Runnable() {
        @Override
        public void run() {
            time.setToNow();
            if (time.hour == 0 && time.minute == 0 && time.second == 0)
                date = new Date();
            updateClock(time.hour, time.minute, time.second, date);
            handler.postDelayed(updateClockRunnable, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHours = findViewById(R.id.tvHours);
        tvMinutes = findViewById(R.id.tvMinutes);
        tvSeconds = findViewById(R.id.tvSeconds);
        tvAmPm = findViewById(R.id.tvAmPm);
        tvDate = findViewById(R.id.tvDate);

        handler.post(updateClockRunnable);
    }

    private void updateClock(int hours, int minutes, int seconds, Date date) {
        String amPm;
        if (hours > 12) {
            amPm = getResources().getString(R.string.str_pm);
            hours -= 12;
        } else {
            amPm = getResources().getString(R.string.str_am);
        }

        tvHours.setText(String.format("%02d", hours));
        tvMinutes.setText(String.format("%02d", minutes));
        tvSeconds.setText(String.format("%02d", seconds));
        tvAmPm.setText(amPm);
        tvDate.setText(String.format("%tF %n", date));
    }
}
