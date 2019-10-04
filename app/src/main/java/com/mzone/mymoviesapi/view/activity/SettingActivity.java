package com.mzone.mymoviesapi.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mzone.mymoviesapi.R;
import com.mzone.mymoviesapi.notification.DailyNotificationReceiver;
import com.mzone.mymoviesapi.notification.ReleaseTodayReminder;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences pref;
    DailyNotificationReceiver dailyNotificationReceiver = new DailyNotificationReceiver();
    ReleaseTodayReminder releaseTodayReminder = new ReleaseTodayReminder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Button btnLanguage = findViewById(R.id.change_language);
        Switch dailySwitch = findViewById(R.id.daily_switch);
        Switch releaseSwitch = findViewById(R.id.release_switch);

        pref = getSharedPreferences("setting", MODE_PRIVATE);

        boolean prefDaily = pref.getBoolean("daily", false);
        boolean prefRelease = pref.getBoolean("release", false);

        if (prefDaily)
            dailySwitch.setChecked(true);
        else
            dailySwitch.setChecked(false);

        if (prefRelease)
            releaseSwitch.setChecked(true);
        else
            releaseSwitch.setChecked(false);

        dailySwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                changeDaily(true);
                dailyNotificationReceiver.setNotificationDaily(this);
            } else {
                changeDaily(false);
                dailyNotificationReceiver.cancelNotificationDaily(this);
            }
        });

        releaseSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                changeRelease(true);
                releaseTodayReminder.setReminderToday(this);
            } else {
                changeRelease(false);
                releaseTodayReminder.cancelReminderToday(this);
            }
        });

        btnLanguage.setOnClickListener(view -> startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS)));
    }

    private void changeDaily(boolean b) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("daily", b);
        editor.apply();
    }

    private void changeRelease(boolean b) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("release", b);
        editor.apply();
    }
}
