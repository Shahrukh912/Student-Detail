package com.srk.studentdetails;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcv;
    private DataAdapter adapter;
    private String a;
    ArrayList<Modal> modal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv = findViewById(R.id.recview);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        modal = new ArrayList<Modal>();


        Intent i = getIntent();
        Bundle b = i.getExtras();
        modal = (ArrayList<Modal>) b.getSerializable("modals");

        adapter = new DataAdapter((ArrayList<Modal>) modal, new DataAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int pos) {
                //Toast.makeText(getApplicationContext(),modal.get(pos).getName(),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                ArrayList<Score> scores = modal.get(pos).getScores();
                i.putExtra("data-name", modal.get(pos).getName());
                i.putExtra("data-imageId", modal.get(pos).getImageId());
                i.putExtra("data-sc1", scores.get(0).getScore());
                i.putExtra("data-sc2", scores.get(1).getScore());
                i.putExtra("data-sc3", scores.get(2).getScore());
                if(modal.get(pos).getImage() != null){
                    //Convert Bitmap to byte array
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    modal.get(pos).getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    i.putExtra("imageData",byteArray);
                }
                startActivity(i);
            }
        });
        rcv.setAdapter(adapter);


/*
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(SplashScreen.DRIVE_IMAGE_FORMATED_URL+modal.get(0).getImageId())
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if(response.isSuccessful()){
                        byte res[] = response.body().bytes();
                        final Bitmap b = BitmapFactory.decodeByteArray(res,0,res.length);
                        modal.get(0).setImage(b);
                        adapter.notifyItemChanged(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });*/
    }
}