package com.example.sanstoi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sans toi on 2017/3/8.
 */

public class ParentScenic implements Parcelable {

    private int parentScenicID;
    private String parentScenicName;
    private String parentScenicOpenDate;
    private String parentScenicStartTime;
    private String parentScenicEndTime;
    private double parentScenicPrice;
    private String parentScenicBrief;
    private String parentScenicCity;
    private String parentScenicProvince;
    private String image;
    private String line1;
    private String line2;
    private String line3;
    private Integer Version;
    private List<ChildScenic> childscenics = new ArrayList<ChildScenic>();

    public int getParentScenicID() {
        return parentScenicID;
    }

    public void setParentScenicID(int parentScenicID) {
        this.parentScenicID = parentScenicID;
    }

    public List<ChildScenic> getChildsenics() {
        return childscenics;
    }

    public void setChildsenics(ChildScenic childsenic) {
        childscenics.add(childsenic);
    }

    public Integer getVersion() {
        return Version;
    }

    public void setVersion(Integer version) {
        Version = version;
    }

    public String getParentScenicProvince() {
        return parentScenicProvince;
    }

    public void setParentScenicProvince(String parentScenicProvince) {
        this.parentScenicProvince = parentScenicProvince;
    }

    public String getParentScenicCity() {
        return parentScenicCity;
    }

    public void setParentScenicCity(String parentScenicCity) {
        this.parentScenicCity = parentScenicCity;
    }

    public String getParentScenicBrief() {
        return parentScenicBrief;
    }

    public void setParentScenicBrief(String parentScenicBrief) {
        this.parentScenicBrief = parentScenicBrief;
    }

    public double getParentScenicPrice() {
        return parentScenicPrice;
    }

    public void setParentScenicPrice(double parentScenicPrice) {
        this.parentScenicPrice = parentScenicPrice;
    }

    public String getParentScenicEndTime() {
        return parentScenicEndTime;
    }

    public void setParentScenicEndTime(String parentScenicEndTime) {
        this.parentScenicEndTime = parentScenicEndTime;
    }

    public String getParentScenicStartTime() {
        return parentScenicStartTime;
    }

    public void setParentScenicStartTime(String parentScenicStartTime) {
        this.parentScenicStartTime = parentScenicStartTime;
    }

    public String getParentScenicName() {
        return parentScenicName;
    }

    public void setParentScenicName(String parentScenicName) {
        this.parentScenicName = parentScenicName;
    }

    public String getParentScenicOpenDate() {
        return parentScenicOpenDate;
    }

    public void setParentScenicOpenDate(String parentScenicOpenDate) {
        this.parentScenicOpenDate = parentScenicOpenDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public static final Parcelable.Creator<ParentScenic> CREATOR=new Creator<ParentScenic>() {

        @Override
        public ParentScenic createFromParcel(Parcel source) {
            ParentScenic bean=new ParentScenic();
            bean.parentScenicID=source.readInt();
            bean.parentScenicName=source.readString();
            bean.parentScenicOpenDate=source.readString();
            bean.parentScenicStartTime=source.readString();
            bean.parentScenicEndTime=source.readString();
            bean.parentScenicPrice=source.readDouble();
            bean.parentScenicBrief=source.readString();
            bean.parentScenicCity=source.readString();
            bean.parentScenicProvince=source.readString();
            bean.image=source.readString();
            bean.line1=source.readString();
            bean.line2=source.readString();
            bean.line3=source.readString();
            return bean;
        }

        @Override
        public ParentScenic[] newArray(int size) {
            return new ParentScenic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(parentScenicID);
        dest.writeString(parentScenicName);
        dest.writeString(parentScenicOpenDate);
        dest.writeString(parentScenicStartTime);
        dest.writeString(parentScenicEndTime);
        dest.writeDouble(parentScenicPrice);
        dest.writeString(parentScenicBrief);
        dest.writeString(parentScenicCity);
        dest.writeString(parentScenicProvince);
        dest.writeString(image);
        dest.writeString(line1);
        dest.writeString(line2);
        dest.writeString(line3);
    }
}
