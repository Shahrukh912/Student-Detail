package com.srk.studentdetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{
    public ArrayList<Modal> data;
    public RecyclerViewClickListener clickListener;

    public DataAdapter(ArrayList<Modal> data,RecyclerViewClickListener listener) {
        this.data = data;
        this.clickListener = listener;
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
        holder.name.setText(data.get(position).getName());
        ArrayList<Score> scores = data.get(position).getScores();
        double avg = (scores.get(0).getScore()+scores.get(1).getScore()+scores.get(2).getScore())/3;
        holder.avg.setText("Average : " + avg);
    }


    @Override
    public int getItemCount()
    {
        return data.size();
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


