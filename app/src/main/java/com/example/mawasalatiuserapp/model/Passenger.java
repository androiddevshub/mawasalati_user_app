package com.example.mawasalatiuserapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Passenger implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("age")
    @Expose
    private String age;

    @SerializedName("gender")
    @Expose
    private String gender;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Passenger(String name, String age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    protected Passenger(Parcel in) {
        name = in.readString();
        age = in.readString();
        gender = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(age);
        dest.writeString(gender);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Passenger> CREATOR = new Creator<Passenger>() {
        @Override
        public Passenger createFromParcel(Parcel in) {
            return new Passenger(in);
        }

        @Override
        public Passenger[] newArray(int size) {
            return new Passenger[size];
        }
    };
}
