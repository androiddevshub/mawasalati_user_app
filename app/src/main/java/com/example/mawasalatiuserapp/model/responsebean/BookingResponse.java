package com.example.mawasalatiuserapp.model.responsebean;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mawasalatiuserapp.model.Booking;
import com.example.mawasalatiuserapp.model.City;
import com.example.mawasalatiuserapp.model.Passenger;
import com.example.mawasalatiuserapp.model.ScheduledBus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookingResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("booking")
    @Expose
    private Booking booking;

    @SerializedName("scheduled_bus")
    @Expose
    private ScheduledBus scheduledBus;


    protected BookingResponse(Parcel in) {
        status = in.readByte() != 0;
        booking = in.readParcelable(Booking.class.getClassLoader());
        scheduledBus = in.readParcelable(ScheduledBus.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public ScheduledBus getScheduledBus() {
        return scheduledBus;
    }

    public void setScheduledBus(ScheduledBus scheduledBus) {
        this.scheduledBus = scheduledBus;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(message);
        dest.writeParcelable(booking, flags);
        dest.writeParcelable(scheduledBus, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingResponse> CREATOR = new Creator<BookingResponse>() {
        @Override
        public BookingResponse createFromParcel(Parcel in) {
            return new BookingResponse(in);
        }

        @Override
        public BookingResponse[] newArray(int size) {
            return new BookingResponse[size];
        }
    };
}
