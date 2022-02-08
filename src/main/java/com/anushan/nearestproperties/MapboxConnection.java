package com.anushan.nearestproperties;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MapboxConnection {

    //returns the coordinates of given address
    public static List<Float> getLngLat(String address) {

        //read json response to get the longitude and latitude
        String response = getMapboxResponse(address);
        if(response != ""){
            return readJson(response);
        }

        return new ArrayList<Float>();
    }

    //gets response from mapbox api request
    private static String getMapboxResponse(String address){
        String response = "";

        try{
            String accessToken = "pk.eyJ1IjoiYW51c2hhbnYiLCJhIjoiY2t6MXp3a3MzMHhuOTMwcDg3Z2U2cm94ZyJ9.QnNVaa82YhP-E6W9ZwKF_w";
            //establish url connection
            String urlString = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + address + ".json?limit=1&access_token=" + accessToken;
            if(urlString.contains(" ")){
                urlString = urlString.replace(" ", "%20");
            }
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //read response from url
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while((inputLine = input.readLine()) != null){
                response += inputLine;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

        return response;
    }

    //gets geographical coordinates from a json string
    private static List<Float> readJson(String response){
        List<Float> lngLat = new ArrayList<Float>();

        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject(); //get
        JsonArray features = jsonResponse.getAsJsonArray("features");
        String center = features.get(0).getAsJsonObject().get("center").toString();

        //retrieve longitude and latitude from string
        center = center.substring(1, center.length()-1);
        String[] lngLatArr = center.split(",");
        lngLat.add(Float.parseFloat(lngLatArr[0]));
        lngLat.add(Float.parseFloat(lngLatArr[1]));

        return lngLat;
    }
}
