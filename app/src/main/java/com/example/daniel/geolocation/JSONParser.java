package com.example.daniel.geolocation;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

// JSON PARSER retrieves and set information
// Returns a Restaurant Bean (object) array

public class JSONParser {

    public static ArrayList<Restaurant> parseFeed(String content) {

        try {
            JSONArray restaurantData = new JSONArray(content);
            ArrayList<Restaurant> restaurantList = new ArrayList<>();
            for (int i = 0; i < restaurantData.length(); i++) {
                JSONObject obj = restaurantData.getJSONObject(i);
                Restaurant restaurant = new Restaurant();
                restaurant.setid(obj.getInt("id"));
                restaurant.setBusinessName(obj.getString("BusinessName"));
                restaurant.setAddressLine1(obj.getString("AddressLine1"));
                restaurant.setAddressLine2(obj.getString("AddressLine2"));
                restaurant.setAddressLine3(obj.getString("AddressLine3"));
                restaurant.setPostCode(obj.getString("PostCode"));
                restaurant.setRatingValue(obj.getInt("RatingValue"));
                restaurant.setRatingDate(obj.getString("RatingDate"));
                restaurant.setLongitude(obj.getDouble("Longitude"));
                restaurant.setLatitude(obj.getDouble("Latitude"));

                if(obj.has("DistanceKM")){
                    restaurant.setDistanceKM(obj.getString("DistanceKM"));
                }

                restaurantList.add(restaurant);
            }
            return restaurantList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }





}
