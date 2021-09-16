package com.example.suareapp.MedicineReminder;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.suareapp.R;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("İLAÇ VAKTİ")
                .setContentText("Kullanmanız Gereken İlaçlar Var")
                .setSmallIcon(R.drawable.ic_baseline_android_24);
    }

    /*mymedia= MediaPlayer.create();
        mymedia.start;

        //Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("İLAÇ VAKTİ")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("KULLANMANIZ GEREKEN İLAÇLARIN ZAMANI GELDİ"))
                .setContentText("KULLANMANIZ GEREKEN İLAÇLARIN ZAMANI GELDİ")
                .setAutoCancel(true);
                //setSound(defaultSoundUri);

         */
}
