package com.example.mawasalatiuserapp.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mawasalatiuserapp.R;
import com.example.mawasalatiuserapp.model.BookingList;
import com.example.mawasalatiuserapp.model.ScheduledBus;
import com.example.mawasalatiuserapp.view.activities.BookedActivity;

import java.util.ArrayList;

public class BookingListRecyclerAdapter extends RecyclerView.Adapter<BookingListRecyclerAdapter.BookingsViewHolder> {

    private ArrayList<BookingList> bookingArrayList;
    private Context context;

    public BookingListRecyclerAdapter(ArrayList<BookingList> bookingArrayList, Context context) {
        this.bookingArrayList = bookingArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.layout_booking_details, parent, false);

        // Return a new holder instance
        BookingsViewHolder viewHolder = new BookingsViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsViewHolder holder, int position) {

        ScheduledBus scheduledBus = bookingArrayList.get(position).getScheduledBus();

        holder.busName.setText(scheduledBus.getBus_name());
        holder.busDescription.setText(scheduledBus.getBus_type());
        holder.busOrigin.setText(scheduledBus.getOrigin());
        holder.busDestination.setText(scheduledBus.getDestination());
        holder.busDepartureTime.setText(scheduledBus.getDeparture_time());
        holder.busArrivalTime.setText(scheduledBus.getArrival_time());
        holder.busJourneyDuration.setText(scheduledBus.getDuration());
        holder.busDepartureDate.setText(scheduledBus.getSeats()+ " Seats");
        holder.busArrivalDate.setText("$"+ scheduledBus.getPrice());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "I m being clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BookedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("bookingId",bookingArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingArrayList.size();
    }

    public class BookingsViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView busName, busDescription,  busOrigin, busDestination, busDepartureTime, busArrivalTime,
                busJourneyDuration, busDepartureDate, busArrivalDate ;

        public View view;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public BookingsViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            view = itemView;
            busName = (TextView) itemView.findViewById(R.id.tv_booked_bus_name_item);
            busDescription = (TextView) itemView.findViewById(R.id.tv_booked_bus_type_item);
            busOrigin = (TextView) itemView.findViewById(R.id.tv_booked_bus_origin_item);
            busDestination = (TextView) itemView.findViewById(R.id.tv_booked_bus_destination_item);
            busDepartureTime = (TextView) itemView.findViewById(R.id.tv_booked_bus_pickup_time_item);
            busArrivalTime = (TextView) itemView.findViewById(R.id.tv_booked_bus_drop_time_item);
            busJourneyDuration = (TextView) itemView.findViewById(R.id.tv_booked_bus_duration_item);
            busDepartureDate = (TextView) itemView.findViewById(R.id.tv_booked_bus_start_date_item);
            busArrivalDate = (TextView) itemView.findViewById(R.id.tv_booked_bus_end_date_item);



        }
    }




}
