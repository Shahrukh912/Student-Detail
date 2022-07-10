package com.srk.studentdetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

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

public class SplashScreen extends AppCompatActivity {
    public static final String JSON_URL= "https://raw.githubusercontent.com/ozlerhakan/mongodb-json-files/master/datasets/students.json";
    public static final String DRIVE_FOLDER_URL = "https://drive.google.com/drive/folders/1TCXA8qIEHxoDhcpARlUFBIuJH3AQ-v2t";
    public static final String DRIVE_IMAGE_FORMATED_URL = "https://drive.google.com/uc?export=download&id=";
    public static final int DATA_SIZE = 30;
    private OkHttpClient client;
    ArrayList<Modal> modals;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        modals = new ArrayList<Modal>();

        loadCompleteData();

    }

    private void loadCompleteData() {
        //Requesting the JSON FILE .
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(JSON_URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    final String arr[] = response.body().string().split("\\r?\\n|\\r");
                    //final String a = response.body().string();
                    for(int i=0;i<arr.length && i<DATA_SIZE;i++){
                        Modal m = new Gson().fromJson(arr[i],Modal.class);
                        //Log.d("srk",String.valueOf(m.getScores().size()));
                        modals.add(m);
                    }
                    getImageId();
                }
            }
        });
    }

    private void getImageId() {
        //Getting image id from the html response of Google Drive website.
        Request request = new Request.Builder()
                .url(DRIVE_FOLDER_URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Pattern pattern = Pattern.compile("data-id=\"\\S*\"");
                    Matcher matcher = pattern.matcher(response.body().string());

                    int i=0;
                    while(matcher.find() && i<DATA_SIZE && i< modals.size()){
                        String temp = matcher.group(0);
                        modals.get(i).setImageId(temp.substring(9,temp.length() - 1));
                        i++;
                    }
                    startMainActivity();
                }
            }
        });
    }



    private void startMainActivity() {

        Intent i = new Intent(SplashScreen.this,MainActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("modals",modals);
        i.putExtras(b);
        startActivity(i);
        finish();

    }
}