package com.example.mawasalatiuserapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Booking implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("scheduled_bus_id")
    @Expose
    private int scheduled_bus_id;

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("booking_id")
    @Expose
    private String booking_str_id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("tax_fare")
    @Expose
    private Double tax_fare;

    @SerializedName("passenger_fare")
    @Expose
    private Double passenger_fare;

    @SerializedName("total_fare")
    @Expose
    private Double total_fare;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("passengers")
    @Expose
    private ArrayList<Passenger> passengerArrayList;

    public Booking() {
    }

    protected Booking(Parcel in) {
        id = in.readInt();
        scheduled_bus_id = in.readInt();
        user_id = in.readInt();
        booking_str_id = in.readString();
        date = in.readString();
        if (in.readByte() == 0) {
            tax_fare = null;
        } else {
            tax_fare = in.readDouble();
        }
        if (in.readByte() == 0) {
            passenger_fare = null;
        } else {
            passenger_fare = in.readDouble();
        }
        if (in.readByte() == 0) {
            total_fare = null;
        } else {
            total_fare = in.readDouble();
        }
        email = in.readString();
        phone = in.readString();
        status = in.readString();
        passengerArrayList = in.createTypedArrayList(Passenger.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(scheduled_bus_id);
        dest.writeInt(user_id);
        dest.writeString(booking_str_id);
        dest.writeString(date);
        if (tax_fare == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(tax_fare);
        }
        if (passenger_fare == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(passenger_fare);
        }
        if (total_fare == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(total_fare);
        }
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(status);
        dest.writeTypedList(passengerArrayList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheduled_bus_id() {
        return scheduled_bus_id;
    }

    public void setScheduled_bus_id(int scheduled_bus_id) {
        this.scheduled_bus_id = scheduled_bus_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getBooking_str_id() {
        return booking_str_id;
    }

    public void setBooking_str_id(String booking_str_id) {
        this.booking_str_id = booking_str_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTax_fare() {
        return tax_fare;
    }

    public void setTax_fare(Double tax_fare) {
        this.tax_fare = tax_fare;
    }

    public Double getPassenger_fare() {
        return passenger_fare;
    }

    public void setPassenger_fare(Double passenger_fare) {
        this.passenger_fare = passenger_fare;
    }

    public Double getTotal_fare() {
        return total_fare;
    }

    public void setTotal_fare(Double total_fare) {
        this.total_fare = total_fare;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Passenger> getPassengerArrayList() {
        return passengerArrayList;
    }

    public void setPassengerArrayList(ArrayList<Passenger> passengerArrayList) {
        this.passengerArrayList = passengerArrayList;
    }
}
