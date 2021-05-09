package com.example.mawasalatiuserapp.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mawasalatiuserapp.R;
import com.example.mawasalatiuserapp.model.ScheduledBus;
import com.example.mawasalatiuserapp.view.activities.BookingActivity;

import java.util.ArrayList;

public class PassengerSetDetailsRecyclerAdapter extends RecyclerView.Adapter<PassengerSetDetailsRecyclerAdapter.PassengerSetDetailsViewHolder> {

    private ArrayList<ScheduledBus> scheduledBusArrayList;
    private Context context;

    public PassengerSetDetailsRecyclerAdapter(ArrayList<ScheduledBus> scheduledBusArrayList, Context context) {
        this.scheduledBusArrayList = scheduledBusArrayList;
        this.context = context;
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

        ScheduledBus scheduledBus = scheduledBusArrayList.get(position);
        

        holder.passengerName.setText(scheduledBus.getBus_name());
        holder.passengerAge.setText(scheduledBus.getOrigin());
        holder.passengerGender.setText(scheduledBus.getDestination());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "I m being clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BookingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return scheduledBusArrayList.size();
    }

    public class PassengerSetDetailsViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView passengerName, passengerAge, passengerGender;

        public View view;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public PassengerSetDetailsViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            view = itemView;
            passengerName = (TextView) itemView.findViewById(R.id.tv_pd_name);
            passengerAge = (TextView) itemView.findViewById(R.id.tv_pd_age);
            passengerGender = (TextView) itemView.findViewById(R.id.tv_pd_gender);



        }
    }




}
