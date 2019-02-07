package com.example.android.events;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Random;

public class AlertReceiver extends BroadcastReceiver {
    private static final String TAG = "AlertReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Received Broadcast");
        String description = intent.getStringExtra("description");
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,new Intent(context,EventActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, EventsListActivity.CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_action_notification_on)
                .setContentIntent(pendingIntent);

        if (description.length() > 50) {
            builder.setContentText("Description")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(description));
            Log.d(TAG, "onReceive: Task length is longer than 50 characters");
        } else
            builder.setContentText(description);
        Log.d(TAG, "onReceive: Task length is shorter than 50 characters");
        Notification notification=builder.build();
        notificationManager.notify(new Random().nextInt(),notification);
    }
}
