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

import java.util.ArrayList;

public class ScheduledBusesRecyclerAdapter extends RecyclerView.Adapter<ScheduledBusesRecyclerAdapter.ScheduledBusViewHolder> {

    private ArrayList<ScheduledBus> scheduledBusArrayList;
    private Context context;

    public ScheduledBusesRecyclerAdapter(ArrayList<ScheduledBus> scheduledBusArrayList, Context context) {
        this.scheduledBusArrayList = scheduledBusArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ScheduledBusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.layout_bus_details, parent, false);

        // Return a new holder instance
        ScheduledBusViewHolder viewHolder = new ScheduledBusViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduledBusViewHolder holder, int position) {

        ScheduledBus scheduledBus = scheduledBusArrayList.get(position);

        double finalBuildTime = scheduledBus.getDuration();
        int hours = (int) finalBuildTime;
        int minutes = (int) (finalBuildTime * 60) % 60;

        holder.busName.setText(scheduledBus.getBus_name());
        holder.busOrigin.setText(scheduledBus.getOrigin());
        holder.busDestination.setText(scheduledBus.getDestination());
        holder.busPickUpPoint.setText(scheduledBus.getPickup_point());
        holder.busDropPoint.setText(scheduledBus.getDrop_point());
        holder.busDepartureTime.setText(scheduledBus.getDeparture_time());
        holder.busArrivalTime.setText(scheduledBus.getArrival_time());
        holder.busJourneyDuration.setText(hours+"h "+ minutes+ "m");
        holder.busSeats.setText(scheduledBus.getSeats()+ " Seats");
        holder.busFairPrice.setText("$"+ scheduledBus.getPrice());


        holder.busRatingBar.setRating(Float.valueOf(scheduledBus.getRating()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(contex)
            }
        });

    }

    @Override
    public int getItemCount() {
        return scheduledBusArrayList.size();
    }

    public class ScheduledBusViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView busName, busOrigin, busDestination, busPickUpPoint,
                busDropPoint, busDepartureTime, busArrivalTime, busJourneyDuration, busSeats, busFairPrice ;

        public RatingBar busRatingBar;
        public View view;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ScheduledBusViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            view = itemView;
            busName = (TextView) itemView.findViewById(R.id.tv_bus_name);
            busOrigin = (TextView) itemView.findViewById(R.id.tv_bus_origin);
            busDestination = (TextView) itemView.findViewById(R.id.tv_bus_destination);
            busPickUpPoint = (TextView) itemView.findViewById(R.id.tv_bus_origin_pickup);
            busDropPoint = (TextView) itemView.findViewById(R.id.tv_bus_destination_drop);
            busDepartureTime = (TextView) itemView.findViewById(R.id.tv_bus_departure_time);
            busArrivalTime = (TextView) itemView.findViewById(R.id.tv_bus_arrival_time);
            busJourneyDuration = (TextView) itemView.findViewById(R.id.tv_bus_travel_duration);
            busSeats = (TextView) itemView.findViewById(R.id.tv_bus_seats);
            busFairPrice = (TextView) itemView.findViewById(R.id.tv_bus_price);
            busRatingBar = (RatingBar) itemView.findViewById(R.id.rb_bus_rating);



        }
    }




}
