package com.example.android.events;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import static com.example.android.events.EventsListActivity.CHANNEL_ID;

public class AlertReceiver extends BroadcastReceiver {
    private static final String TAG = "AlertReceiver";
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Received Broadcast");
        int id = intent.getIntExtra("event_id", 0);
        String description = intent.getStringExtra("description");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, EventsListActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_action_notification_on)
                .setContentIntent(pendingIntent)
                .setContentText(description)
                .build();
        /*if (description.length() > 50) {
            builder.setContentText("Description")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(description));
            Log.d(TAG, "onReceive: Task length is longer than 50 characters");
        } else
            builder.setContentText(description);
        Log.d(TAG, "onReceive: Task length is shorter than 50 characters");
        */
        notificationManagerCompat.notify(id, notification);
    }
}
