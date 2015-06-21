package com.example.daniel.geolocation;


import java.io.Serializable;


// Restaurant Bean
public class Restaurant implements Serializable {

    private int id;
    private String BusinessName;
    private String AddressLine1;
    private String AddressLine2;
    private String AddressLine3;
    private String PostCode;
    private int RatingValue;
    private String RatingDate;
    private double Longitude, Latitude;
    private String DistanceKM = null;

    public int getid(int id) {
        return this.id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        this.BusinessName = businessName;
    }

    public String getAddressLine1() {
        return AddressLine1;
    }

    public void setAddressLine1(String AddressLine1) {
        this.AddressLine1 = AddressLine1;
    }

    public String getAddressLine2() {
        return AddressLine2;
    }

    public void setAddressLine2(String AddressLine2) {
        this.AddressLine2 = AddressLine2;
    }

    public String getAddressLine3() {
        return AddressLine3;
    }

    public void setAddressLine3(String AddressLine3) {
        this.AddressLine3 = AddressLine3;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String PostCode) {
        this.PostCode = PostCode;
    }

    public int getRatingValue() {
        return RatingValue;
    }

    public void setRatingValue(int RatingValue) {
        this.RatingValue = RatingValue;
    }

    public String getRatingDate() {
        return RatingDate;
    }

    public void setRatingDate(String RatingDate) {
        this.RatingDate = RatingDate;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }


    public String getDistanceKM() {
        return DistanceKM;
    }

    public void setDistanceKM(String DistanceKM) {
        this.DistanceKM = DistanceKM;
    }


}
