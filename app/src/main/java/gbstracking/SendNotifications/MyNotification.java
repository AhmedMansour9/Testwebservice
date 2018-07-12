package gbstracking.SendNotifications;

/**
 * Created by HP on 14/04/2018.
 */


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.gbstracking.R;

import gbstracking.Nvigation;
import gbstracking.friends.Common;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyNotification {
    public static final int ID_SMALL_NOTIFICATION = 235;
    private Context mCtx;
     public static  Notification notification;
    public MyNotification(Context mCtx) {
        this.mCtx = mCtx;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showSmallNotification(String title, String message, String address, String time, String day, Intent intent) {


        PendingIntent resultPendingIntent =
                PendingIntent.getService(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );


        Notification.Builder mBuilder = new Notification.Builder(mCtx);
        mBuilder.setStyle(new Notification.InboxStyle()
                .addLine(message)
                .addLine(address)
                .addLine("Time    "+time+"     "+"Day     "+day));
        notification = mBuilder.setTicker(title)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.warnicon)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.warnicon))
                .build();
        NotificationManager notificationManager=(NotificationManager)mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,mBuilder.build());

        try {

            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mCtx.getPackageName() + "/raw/songemergency");

            AudioManager manager = (AudioManager)mCtx.getSystemService(Context.AUDIO_SERVICE);
            manager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                    0);

//            Ringtone r = RingtoneManager.getRingtone(mCtx, alarmSound);
            MediaPlayer player = MediaPlayer.create(mCtx, alarmSound);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setLooping(true);
            player.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showSmallNotificati(String title, String message, Intent intent) {
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        Notification.Builder mBuilder = new Notification.Builder(mCtx);
        Notification notification;
        notification = mBuilder.setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.mesagebig)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.mesagebig))
                .setContentText(message)
                .setStyle(new Notification.BigTextStyle().bigText(message))
                .build();

        try {
            Uri notificatio = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notificatio);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_SMALL_NOTIFICATION, notification);
    }

}
