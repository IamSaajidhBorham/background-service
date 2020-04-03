package com.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.facebook.react.HeadlessJsTaskService;
import com.reactnativebridgedemo.R;

import javax.annotation.Nullable;

/**
 * Created by DELL on 10/23/2017.
 */

public class GPS_Service extends Service {

    private static final int SERVICE_NOTIFICATION_ID = 1880;

    private static final String CHANNEL_ID = "HEARTBEAT";
    private Handler handler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this.getApplicationContext(), "Service created", Toast.LENGTH_SHORT).show();

        HeadlessJsTaskService.acquireWakeLockNow(this.getApplication());
        handler.postDelayed( this::onCreate, 20000);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Turning into a foreground service
        createNotificationChannel(); // Creating channel for API 26+
        Intent notificationIntent = new Intent(this, BootUpReceiver.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Heartbeat service")
                .setContentText("Running…")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(contentIntent)
                .setOngoing(false)
                .build();
        startForeground(SERVICE_NOTIFICATION_ID, notification);

        Toast.makeText(this.getApplicationContext(), "Service started", Toast.LENGTH_SHORT).show();

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this.getApplicationContext(), "Service Stopped", Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "HEARTBEAT", importance);
            channel.setDescription("CHANEL DESCRIPTION");
            channel.setShowBadge(false);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}