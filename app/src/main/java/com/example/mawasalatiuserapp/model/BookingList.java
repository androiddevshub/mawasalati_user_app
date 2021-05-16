package com.example.mawasalatiuserapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingList implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("scheduled_bus")
    @Expose
    private ScheduledBus scheduledBus;


    protected BookingList(Parcel in) {
        id = in.readInt();
        scheduledBus = in.readParcelable(ScheduledBus.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(scheduledBus, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingList> CREATOR = new Creator<BookingList>() {
        @Override
        public BookingList createFromParcel(Parcel in) {
            return new BookingList(in);
        }

        @Override
        public BookingList[] newArray(int size) {
            return new BookingList[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ScheduledBus getScheduledBus() {
        return scheduledBus;
    }

    public void setScheduledBus(ScheduledBus scheduledBus) {
        this.scheduledBus = scheduledBus;
    }


}
