package com.srk.studentdetails;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Runnable{
    public ArrayList<Modal> data;
    public RecyclerViewClickListener clickListener;
    public Handler uiHandler;

    public DataAdapter(ArrayList<Modal> data,RecyclerViewClickListener listener) {
        this.data = data;
        this.clickListener = listener;
        uiHandler = new Handler();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //final int tempPosition = position;
        holder.name.setText(data.get(position).getName());
        ArrayList<Score> scores = data.get(position).getScores();
        double avg = (scores.get(0).getScore()+scores.get(1).getScore()+scores.get(2).getScore())/3;
        holder.avg.setText("Average : " + avg);

        if(data.get(position).getImage() != null){
            holder.img.setImageBitmap(data.get(position).getImage());
        }else{

            Thread t = new Thread(this,String.valueOf(position));
            t.start();
        }



    }


    @Override
    public int getItemCount()
    {
        return data.size();
    }

    @Override
    public void run() {
        final int pos = Integer.parseInt(Thread.currentThread().getName());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(SplashScreen.DRIVE_IMAGE_FORMATED_URL+data.get(pos).getImageId())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful()){
                byte res[] = response.body().bytes();
                final Bitmap b = BitmapFactory.decodeByteArray(res,0,res.length);
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        data.get(pos).setImage(b);
                        notifyItemChanged(pos);
                    }
                });


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView img;
        TextView name,avg;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            img= itemView.findViewById(R.id.image);
            name= itemView.findViewById(R.id.name);
            avg = itemView.findViewById(R.id.avg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view,getAbsoluteAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v,int pos);
    }

}


