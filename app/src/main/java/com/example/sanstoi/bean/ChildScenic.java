package com.example.sanstoi.bean;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sans toi on 2017/3/8.
 */

public class ChildScenic implements Parcelable  {
    private int childScenicID;
    private int parentScenicID;
    private String childScenicName;
    private String childScenicOpenDate;
    private String childScenicStartTime;
    private String childScenicEndTime;
    private Double childScenicPrice;
    private String childScenicBrief;
    private double mLatitude;
    private double mLongtitude;
    private String image;
    private Integer version;

    public ChildScenic(){}

    public ChildScenic(Parcel source) {
        this.childScenicID=source.readInt();
        this.parentScenicID=source.readInt();
        this.childScenicName=source.readString();
        this.childScenicOpenDate=source.readString();
        this.childScenicStartTime=source.readString();
        this.childScenicEndTime=source.readString();
        this.childScenicPrice=source.readDouble();
        this.childScenicBrief=source.readString();
        this.image = source.readString();
        this.mLongtitude=source.readDouble();
        this.mLatitude=source.readDouble();
    }

    public int getChildScenicID() {
        return childScenicID;
    }

    public void setChildScenicID(int childScenicID) {
        this.childScenicID = childScenicID;
    }

    public String getChildScenicBrief() {
        return childScenicBrief;
    }

    public void setChildScenicBrief(String childScenicBrief) {
        this.childScenicBrief = childScenicBrief;
    }

    public String getChildScenicStartTime() {
        return childScenicStartTime;
    }

    public void setChildScenicStartTime(String childScenicStartTime) {
        this.childScenicStartTime = childScenicStartTime;
    }

    public int getParentScenicID() {
        return parentScenicID;
    }

    public void setParentScenicID(int parentScenicID) {
        this.parentScenicID = parentScenicID;
    }

    public String getChildScenicEndTime() {
        return childScenicEndTime;
    }

    public void setChildScenicEndTime(String childScenicEndTime) {
        this.childScenicEndTime = childScenicEndTime;
    }

    public String getChildScenicName() {
        return childScenicName;
    }

    public void setChildScenicStartName(String childScenicName) {
        this.childScenicName = childScenicName;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setChildScenicName(String childScenicName) {
        this.childScenicName = childScenicName;
    }

    public String getChildScenicOpenDate() {
        return childScenicOpenDate;
    }

    public void setChildScenicOpenDate(String childScenicOpenDate) {
        this.childScenicOpenDate = childScenicOpenDate;
    }

    public Double getChildScenicPrice() {
        return childScenicPrice;
    }

    public void setChildScenicPrice(Double childScenicPrice) {
        this.childScenicPrice = childScenicPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongtitude() {
        return mLongtitude;
    }

    public void setmLongtitude(double mLongtitude) {
        this.mLongtitude = mLongtitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(childScenicID);
        dest.writeInt(parentScenicID);
        dest.writeString(childScenicName);
        dest.writeString(childScenicOpenDate);
        dest.writeString(childScenicStartTime);
        dest.writeString(childScenicEndTime);
        dest.writeDouble(childScenicPrice);
        dest.writeString(childScenicBrief);
        dest.writeString(image);
        dest.writeDouble(mLongtitude);
        dest.writeDouble(mLatitude);
    }

    public static final Parcelable.Creator<ChildScenic> CREATOR = new Creator<ChildScenic>() {
        @Override
        public ChildScenic[] newArray(int size) {
            return new ChildScenic[size];
        }

        //将Parcel对象反序列化为ChildScenic
        @Override
        public ChildScenic createFromParcel(Parcel source) {
            return new ChildScenic(source);
        }
    };
}
