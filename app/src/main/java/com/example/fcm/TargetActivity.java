package com.example.fcm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class TargetActivity extends AppCompatActivity {
    TextView tvPrice;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        String price = intent.getStringExtra("Price");
        tvPrice = findViewById(R.id.tv_Price);
        imageView = findViewById(R.id.img_View);

        putData(url, price);
    }

    private void putData(String url, String price) {
        tvPrice.setText(price);
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(imageView);

        Log.i("TargetActivity", "putData: " + price);
    }

}
