package com.beattheheat.beatthestreet.Networking;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import android.R;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;

/**
 * Created by kylec on 2017-10-25.
 */

public class NotificationUtil {
    private static NotificationUtil myObj;
    final private int defaultIcon = R.drawable.btn_default;

    private NotificationUtil() {

    }

    public static NotificationUtil getInstance() {
        if(myObj == null) {
            myObj = new NotificationUtil();
        }

        return myObj;
    }

    public void notify(Context ctx, int id, String title)
    {
        notify(ctx, id, title, null);
    }

    public void notify(Context ctx, int id, String title, String text)
    {
        notify(ctx, id, title, text, defaultIcon);
    }

    public void notify(Context ctx, int id, String title, String text, int icon)
    {
        notify(ctx, id, title, text, icon, false);
    }

    public void notify(Context ctx, int id, String title, String text, int icon, boolean persist)
    {
        notify(ctx, id, title, text, icon, persist, Settings.System.DEFAULT_NOTIFICATION_URI);
    }

    public void notify(Context ctx, int id, String title, String text, int icon, boolean persistent,
                       Uri sound) {
        if (ctx == null || id < 0) {
            return;
        }

        if(icon < 0) {
            icon = defaultIcon;
        }

        if(sound == null) {
            sound = Settings.System.DEFAULT_NOTIFICATION_URI;
        }

        Notification n = new Notification.Builder(ctx)
                .setContentTitle(title)
                .setContentText(text)
                .setOngoing(persistent)
                .setSmallIcon(icon)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setSound(sound)
                .build();

        notify(ctx, id, n);
    }

    public void notify(Context ctx, int id, Notification n) {
        NotificationManager notificationManager = (NotificationManager)
                ctx.getSystemService(ctx.NOTIFICATION_SERVICE);

        notificationManager.notify(id, n);
    }
}
