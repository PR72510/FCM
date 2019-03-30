package com.example.fcm;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.ExecutionException;

import static com.example.fcm.NotificationCannel.CHANNEL_1;

public class FCMService extends FirebaseMessagingService {
    Bitmap bitmap = null;
    String price;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        price = remoteMessage.getData().get("message");
        String url = remoteMessage.getData().get("image");
        try {
            bitmap = Glide.with(this)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), url, price, bitmap);
    }

    private void showNotification(String title, String text, String Url, String Price, Bitmap bmp) {

        Intent notificationIntent = new Intent(this.getApplicationContext(), TargetActivity.class);
        notificationIntent.putExtra("URL", Url);
        notificationIntent.putExtra("Price", Price);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1)     // constructing notification
                .setSmallIcon(R.drawable.pic1)
                .setLargeIcon(bmp)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bmp)
                        .bigLargeIcon(null))
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(2, notification);
    }


}
