package com.betulerdogan.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.content.Intent;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager notificationManager;

    String DEFAULT_CHANNEL_ID = "DEFAULT";

    String DEFAULT_CHANNEL_NAME = "DEFAULT CHANNEL";

    String pkgName = "notification.channel";

    public NotificationUtils(Context context) {
        super(context);
        pkgName = context.getPackageName() != null ? context.getPackageName() : pkgName;
        DEFAULT_CHANNEL_ID = pkgName + "." + DEFAULT_CHANNEL_ID.toUpperCase();
        createChannel(DEFAULT_CHANNEL_ID, DEFAULT_CHANNEL_NAME);
    }

    public void createChannel(String CHANNEL_ID, String CHANNEL_NAME) {

        if (Build.VERSION.SDK_INT >= 28) {
            NotificationChannel newChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            newChannel.enableLights(true);
            newChannel.enableVibration(true);
            newChannel.setLightColor(Color.GREEN);
            newChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getNotificationManager().createNotificationChannel(newChannel);
        }
    }
    private NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public void sendNotificationInChannel(int notificationId, Intent resultIntent,
                                          NotificationCompat.Builder builder) {
        PendingIntent pi = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent
                .FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pi);

        getNotificationManager().notify(notificationId, builder.build());
    }

    public void sendNotificationInDefaultChannel(String title, String body, int notificationId) {
        Intent resultIntent = new Intent(this, MainActivity.class);
        sendNotificationInChannel(notificationId, resultIntent,
                getDefaultNotificationBuilder(title, body, DEFAULT_CHANNEL_ID));
    }


    public void sendBigNotificationInDefaultChannel(String title, String body, int notificationId) {
        Intent resultIntent = new Intent(this, MainActivity.class);
        NotificationCompat.Builder builder = getDefaultNotificationBuilder(title, body, DEFAULT_CHANNEL_ID);
        builder = convertToBigNotificationBuilder(builder);
        sendNotificationInChannel(notificationId, resultIntent, builder);
    }


    private NotificationCompat.Builder getDefaultNotificationBuilder(String title, String body, String
            channelId) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        return new NotificationCompat.Builder(getApplicationContext(),
                channelId)
                .setContentTitle(title)
                .setContentText(body)
                .setTicker("Notification")
                .setSound(defaultSoundUri)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }


    private NotificationCompat.Builder convertToBigNotificationBuilder(NotificationCompat.Builder builder) {

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        String[] events = new String[6];
        events[0] = "1.satırdır...";
        events[1] = "2.satırdır...";
        events[2] = "3.satırdır...";
        events[3] = "4.satırdır...";
        events[4] = "5.satırdır...";
        events[5] = "6.satırdır...";

        inboxStyle.setBigContentTitle("Büyük başlık detayları:");

        for (final String event : events) {
            inboxStyle.addLine(event);
        }

        builder.setStyle(inboxStyle);
        return builder;
    }
}
