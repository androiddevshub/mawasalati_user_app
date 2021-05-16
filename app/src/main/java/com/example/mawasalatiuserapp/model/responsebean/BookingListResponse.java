package com.example.mawasalatiuserapp.model.responsebean;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mawasalatiuserapp.model.BookingList;
import com.example.mawasalatiuserapp.model.ScheduledBus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookingListResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("data")
    @Expose
    private ArrayList<BookingList> bookingListArrayList;

    protected BookingListResponse(Parcel in) {
        status = in.readByte() != 0;
        bookingListArrayList = in.createTypedArrayList(BookingList.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeTypedList(bookingListArrayList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingListResponse> CREATOR = new Creator<BookingListResponse>() {
        @Override
        public BookingListResponse createFromParcel(Parcel in) {
            return new BookingListResponse(in);
        }

        @Override
        public BookingListResponse[] newArray(int size) {
            return new BookingListResponse[size];
        }
    };

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<BookingList> getBookingListArrayList() {
        return bookingListArrayList;
    }

    public void setBookingListArrayList(ArrayList<BookingList> bookingListArrayList) {
        this.bookingListArrayList = bookingListArrayList;
    }
}
