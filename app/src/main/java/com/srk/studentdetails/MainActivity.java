package com.srk.studentdetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
                //Log.d("srk-data",String.valueOf(scores.size()));
                startActivity(i);
            }
        });
        rcv.setAdapter(adapter);

    }
}