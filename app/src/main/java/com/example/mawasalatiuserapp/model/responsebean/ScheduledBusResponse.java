package com.example.mawasalatiuserapp.model.responsebean;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mawasalatiuserapp.model.ScheduledBus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ScheduledBusResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("data")
    @Expose
    private ArrayList<ScheduledBus> scheduledBusArrayList;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<ScheduledBus> getScheduledBusArrayList() {
        return scheduledBusArrayList;
    }

    public void setScheduledBusArrayList(ArrayList<ScheduledBus> scheduledBusArrayList) {
        this.scheduledBusArrayList = scheduledBusArrayList;
    }

    protected ScheduledBusResponse(Parcel in) {
        status = in.readByte() != 0;
        scheduledBusArrayList = in.createTypedArrayList(ScheduledBus.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeTypedList(scheduledBusArrayList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ScheduledBusResponse> CREATOR = new Creator<ScheduledBusResponse>() {
        @Override
        public ScheduledBusResponse createFromParcel(Parcel in) {
            return new ScheduledBusResponse(in);
        }

        @Override
        public ScheduledBusResponse[] newArray(int size) {
            return new ScheduledBusResponse[size];
        }
    };
}
