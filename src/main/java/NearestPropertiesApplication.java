import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class NearestPropertiesApplication {

    public static void main (String[] args){

        try{
            //open csv file to read from
            BufferedReader br = new BufferedReader(new FileReader("Dallas_houses.csv"));
            String line = br.readLine();

            //create new csv file for writing
            PrintWriter writer = new PrintWriter("Dallas_houses_results.csv", "UTF-8");
            writer.println(line + ",longitude,latitude,mls1,rent1,mls2,rent2,mls3,rent3"); //write header

            //loop through each line in csv
            while ((line = br.readLine()) != null){
                String[] house = line.split(",");

                System.out.println("Working on property " + house[0]);

                //address + state + city + zip
                String address = house[3] + " " + house[4] + " " + house[5] + " " + house[6];

                List<Double> lngLat = MapboxConnection.getLngLat(address);
                List<RentalProperty> nearestProperties = DatabaseConnection.getNearestProperties(lngLat.get(0), lngLat.get(1));

                //write to results file
                writer.print(line);
                writer.print("," + lngLat.get(0) + "," + lngLat.get(1));
                for (RentalProperty property : nearestProperties) {
                    writer.print("," + property.getMls() + "," +property.getRent());
                }
                writer.println();
            }

            System.out.println("Finished");

            br.close();
            writer.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
}
