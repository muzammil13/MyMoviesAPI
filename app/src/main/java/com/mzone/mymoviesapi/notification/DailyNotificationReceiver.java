package com.mzone.mymoviesapi.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.mzone.mymoviesapi.R;
import com.mzone.mymoviesapi.view.activity.MainActivity;

import java.util.Calendar;

public class DailyNotificationReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 101;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    public DailyNotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationTitle = context.getString(R.string.daily_reminder);
        String messageTitle = context.getString(R.string.msg_daily_reminder);
        showAlarmNotification(context, notificationTitle, messageTitle);
    }

    private void showAlarmNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, DailyNotificationReceiver.NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmRingtone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "DAILY_NOTIFICATION", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        assert notificationManager != null;
        notificationManager.notify(DailyNotificationReceiver.NOTIFICATION_ID, builder.build());
    }

    public void setNotificationDaily(Context context) {
        cancelNotificationDaily(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT < Build.VERSION_CODES.KITKAT) {
            assert alarmManager != null;
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), getPendingIntent(context));
        } else if (SDK_INT > Build.VERSION_CODES.KITKAT && SDK_INT < Build.VERSION_CODES.M) {
            assert alarmManager != null;
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    getPendingIntent(context)
            );
        } else if (SDK_INT >= Build.VERSION_CODES.M) {
            assert alarmManager != null;
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), getPendingIntent(context));
        }
    }

    public void cancelNotificationDaily(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.cancel(getPendingIntent(context));
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent alarmIntent = new Intent(context, DailyNotificationReceiver.class);
        boolean isAlarmOn = (PendingIntent.getBroadcast(context, NOTIFICATION_ID, alarmIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        Log.e("isAlarmOn : ", String.valueOf(isAlarmOn));
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
