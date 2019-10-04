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

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.mzone.mymoviesapi.R;
import com.mzone.mymoviesapi.data.remote.service.TheMovieDBClient;
import com.mzone.mymoviesapi.data.remote.service.TheMovieDBInterface;
import com.mzone.mymoviesapi.data.remote.models.MovieResponse;
import com.mzone.mymoviesapi.view.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mzone.mymoviesapi.BuildConfig.API_KEY;


public class ReleaseTodayReminder extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 101;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    public ReleaseTodayReminder() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        getMovieData(context);
    }

    private void getMovieData(Context context) {
        String notificationTitle = context.getString(R.string.release_today);
        String no_release_data = context.getResources().getString(R.string.release_no_data);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        TheMovieDBInterface apiRequest = TheMovieDBClient.getRetrofitInstance().create(TheMovieDBInterface.class);
        apiRequest.getReleaseToday(API_KEY, currentDate, currentDate)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.body() != null) {
                            if (response.body().getTotalPages() > 0)
                                showAlarmNotification(context, notificationTitle, response.body().getResults().get(0).getTitle());
                            else
                                showAlarmNotification(context, notificationTitle, no_release_data);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        showAlarmNotification(context, notificationTitle, no_release_data);
                        t.printStackTrace();
                    }
                });
    }

    private void showAlarmNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, ReleaseTodayReminder.NOTIFICATION_ID, intent,
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
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "RELEASE_TODAY", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        assert notificationManager != null;
        notificationManager.notify(ReleaseTodayReminder.NOTIFICATION_ID, builder.build());
    }

    public void setReminderToday(Context context) {
        cancelReminderToday(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
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

    public void cancelReminderToday(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.cancel(getPendingIntent(context));
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent alarmIntent = new Intent(context, ReleaseTodayReminder.class);
        boolean isAlarmOn = (PendingIntent.getBroadcast(context, NOTIFICATION_ID, alarmIntent,
                PendingIntent.FLAG_NO_CREATE) != null);

        Log.e("isAlarmOn : ", String.valueOf(isAlarmOn));

        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
