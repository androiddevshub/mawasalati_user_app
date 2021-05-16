package com.example.mawasalatiuserapp.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mawasalatiuserapp.R;
import com.example.mawasalatiuserapp.model.Passenger;
import com.example.mawasalatiuserapp.model.ScheduledBus;
import com.example.mawasalatiuserapp.view.activities.BookingActivity;

import java.util.ArrayList;

public class PassengerSetDetailsRecyclerAdapter extends RecyclerView.Adapter<PassengerSetDetailsRecyclerAdapter.PassengerSetDetailsViewHolder> {

    private onRecyclerViewItemClickListener mItemClickListener;

    public void setOnItemClickListener(onRecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface onRecyclerViewItemClickListener {
        void onItemClickListener(View view, int position);
    }

    private ArrayList<Passenger> passengerArrayList;
    private Context context;
    private String status;

    public PassengerSetDetailsRecyclerAdapter(ArrayList<Passenger> passengerArrayList, Context context, String status) {
        this.passengerArrayList = passengerArrayList;
        this.context = context;
        this.status = status;
    }

    @NonNull
    @Override
    public PassengerSetDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.layout_passenger_details, parent, false);

        // Return a new holder instance
        PassengerSetDetailsViewHolder viewHolder = new PassengerSetDetailsViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerSetDetailsViewHolder holder, int position) {

        Passenger passenger = passengerArrayList.get(position);

        if (status == "created"){
            holder.imgPassengerDelete.setVisibility(View.GONE);
        }

        holder.passengerName.setText(passenger.getName());
        holder.passengerAge.setText(passenger.getAge());
        holder.passengerGender.setText(passenger.getGender());


    }

    @Override
    public int getItemCount() {
        return passengerArrayList.size();
    }

    public class PassengerSetDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView passengerName, passengerAge, passengerGender;
        public ImageView imgPassengerDelete;
        public View view;

        public PassengerSetDetailsViewHolder(View itemView) {

            super(itemView);
            view = itemView;
            passengerName = (TextView) itemView.findViewById(R.id.tv_pd_name);
            passengerAge = (TextView) itemView.findViewById(R.id.tv_pd_age);
            passengerGender = (TextView) itemView.findViewById(R.id.tv_pd_gender);
            imgPassengerDelete = (ImageView) itemView.findViewById(R.id.img_delete_passenger);

            imgPassengerDelete.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClickListener(v, getAdapterPosition());
            }
        }
    }
}