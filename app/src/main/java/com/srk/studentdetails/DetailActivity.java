package com.srk.studentdetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {
    private TextView name,sc1,sc2,sc3;
    private ImageView imageView;
    private Modal data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView = findViewById(R.id.image);
        name = findViewById(R.id.name);
        sc1 = findViewById(R.id.sc1);
        sc2 = findViewById(R.id.sc2);
        sc3 = findViewById(R.id.sc3);

        Intent i = getIntent();
        name.setText(i.getStringExtra("data-name"));
        String imageId = i.getStringExtra("data-imageId");



        sc1.setText(String.valueOf(i.getDoubleExtra("data-sc1",0)));
        sc2.setText(String.valueOf(i.getDoubleExtra("data-sc2",0)));
        sc3.setText(String.valueOf(i.getDoubleExtra("data-sc3",0)));

        if(i.hasExtra("imageData")){
            byte[] byteArray = getIntent().getByteArrayExtra("imageData");
            Bitmap img = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(img);
        }
        else{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(SplashScreen.DRIVE_IMAGE_FORMATED_URL+imageId)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        byte res[] = response.body().bytes();
                        final Bitmap b = BitmapFactory.decodeByteArray(res,0,res.length);
                        DetailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(b);
                            }
                        });
                    }

                }
            });
        }


/*


*/

    }
}