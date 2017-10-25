package com.tempus.timewalk.timewalk.Classes;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Isaac on 12/10/17.
 */

public class Data implements Parcelable{
    private int ID;
    private String Name;
    private String Latitude;
    private String Longitude;
    private String Description;
    private int ImageID;

    public Data(int ID, String Name, String Latitude, String Longitude, String Description, int ImageId ){
        this.ID = ID;
        this.Name = Name;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.Description = Description;
        this.ImageID = ImageId;
    }


    protected Data(Parcel in) {
        ID = in.readInt();
        Latitude = in.readString();
        Longitude = in.readString();
        Description = in.readString();
        ImageID = in.readInt();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public int getID(){
        return ID;
    }
    public String getLatitude() {return Latitude;}
    public String getLongitude(){return  Longitude;}
    public String getDescription(){
        return Description;
    }
    public int getImageiD(){
        return ImageID;
    }
    public String getName(){return Name;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.Latitude);
        dest.writeString(this.Longitude);
        dest.writeString(this.Description);
        dest.writeInt(this.ImageID);
    }
}
