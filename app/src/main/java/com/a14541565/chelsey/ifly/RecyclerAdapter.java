package com.a14541565.chelsey.ifly;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chelsey on 01/08/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    private ArrayList<Photo> arrayList = new ArrayList<>();

    public RecyclerAdapter(ArrayList<Photo> arrayList){
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_blueprint, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.Name.setText(arrayList.get(position).getName());
        holder.Description.setText(arrayList.get(position).getDescription());
        holder.Location.setText(arrayList.get(position).getLocation());
        int Sync_Status = arrayList.get(position).getSync_Status();
        if(Sync_Status == DbPhoto.SYNC_STATUS_OK){
            holder.Sync_Status.setImageResource(R.drawable.done);
        }else{
            holder.Sync_Status.setImageResource(R.drawable.upload);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView Sync_Status;
        TextView Name, Description, Location;

        public MyViewHolder(View itemView) {
            super(itemView);
            Sync_Status = (ImageView)itemView.findViewById(R.id.imgSync);
            Name = (TextView)itemView.findViewById(R.id.editText_Name);
            Description = (TextView)itemView.findViewById(R.id.editText_Des);
            Location = (TextView)itemView.findViewById(R.id.editText_Location);



        }
    }
}
