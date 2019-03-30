package com.example.fcm;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fcm.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.example.fcm.NotificationCannel.CHANNEL_1;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
        binding.setClicker(this);

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }

                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void sendNotification1(View view) {

        Intent notificationIntent = new Intent(this.getApplicationContext(), TargetActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, notificationIntent, 0);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.images);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1)     // constructing notification
                .setSmallIcon(R.drawable.pic1)
                .setLargeIcon(bitmap)
                .setContentTitle("Channel 1")
                .setContentText("This is high priority notification")
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(null))
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, notification);            // id should be different if we want to send more than 1 notifications
    }
}