package pl.wat.e5t1s1.todoapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator v = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(5000);

//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        Ringtone r = RingtoneManager.getRingtone(context, notification);
//        r.play();

        Notification(context,"Czeka na Ciebie zaplanowane zadanie!");

        }

    public void Notification(Context context, String message) {
        // Set Notification Title

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(context, MainActivity.class);
        // Send data to NotificationView Class
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.app_icon, "Previous", pIntent).build();
        // Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)
                // Set Icon
                .setSmallIcon(R.drawable.ic_dialog)
                // Set Ticker Message
                .setTicker(message)
                // Set Title
                .setContentTitle(context.getString(R.string.app_name))
                // Set Text
                .setContentText(message)
                // Add an Action Button below Notification
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Dismiss Notification
                .setAutoCancel(true);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(38, builder.build());

    }



    }
