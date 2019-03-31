package com.betulerdogan.notification;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    public static final String PERSONAL_CHANNEL_ID = "PERSONAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.createChannel(PERSONAL_CHANNEL_ID, "PERSONAL");

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationUtils.sendNotificationInDefaultChannel("Basit bir bildirim", "Merhaba Dünya!", 101);
                notificationUtils.sendBigNotificationInDefaultChannel("Daha büyük bir bildirim", "Merhaba Mars!", 102);
                createCustomNotification(notificationUtils);
            }
        });

    }

    private void createCustomNotification(NotificationUtils notificationUtils) {
        Intent resultIntent = new Intent(MainActivity.this, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),
                PERSONAL_CHANNEL_ID)
                .setContentTitle("Özelleştirilmiş Bildirim Denemesi")
                .setContentText("Merhaba Yeni Dünya")
                .setTicker("Özelleştirilmiş bildirim ")
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setAutoCancel(true);
        notificationUtils.sendNotificationInChannel(103, resultIntent, builder);
    }

}
