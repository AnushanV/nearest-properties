package com.anushan.nearestproperties;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CsvWriter {

    public static void writeCsv (List<House> houses, RentalPropertyService rentalPropertyService){

        try{
            //create new csv file
            PrintWriter writer = new PrintWriter("Dallas_houses_results.csv", "UTF-8");
            writer.println("ID,unit_address_as_provided,ADDRESS,state,city,zip_code,rent,longitude,latitude,mls1,rent1,mls2,rent2,mls3,rent3"); //write header

            //write line in csv for each house
            for (House house: houses) {
                writeLine(house, rentalPropertyService, writer);
            }

            System.out.println("Finished");
            writer.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    private static void writeLine(House house, RentalPropertyService rentalPropertyService, PrintWriter writer){
        //find nearest rental properties
        String address = house.getAddress() + " " + house.getState() + " " + house.getCity() + " " + house.getZipCode();
        List<Float> lngLat = MapboxConnection.getLngLat(address);
        List<RentalProperty> nearestProperties = rentalPropertyService.getNearestProperties(lngLat.get(0), lngLat.get(1));

        //write current house info
        String houseInfo = house.getId() + ","
                + house.getUnitAddress() + ","
                + house.getAddress() + ","
                + house.getState() + ","
                + house.getZipCode() + ","
                + house.getRent() + ","
                + lngLat.get(0) + ","
                + lngLat.get(1);
        writer.print(houseInfo);

        //write nearest rental properties
        for (RentalProperty property: nearestProperties) {
            writer.print("," + property.getMls() + "," +property.getRent());
        }
        writer.println();
    }
}
