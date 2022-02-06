import java.sql.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    static java.sql.Connection conn;

    static String url = "jdbc:postgresql://3.235.170.15:5432/gis";
    static String username = "guest";
    static String password = "U8OPtddp";

    // returns list of 3 rental properties closest to a location
    public static List<RentalProperty> getNearestProperties(Double longitude, Double latitude){

        // found from https://postgis.net/docs/manual-1.5/ch05.html
        try {
            /*
             * Load the JDBC driver and establish a connection.
             */
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, username, password);

            /*
             * Create a statement and execute a select query.
             */
            Statement s = conn.createStatement();

            //find 3 nearest houses within 1 km
            String queryStr = "SELECT mls, rent " +
                    "FROM dallas_rent " +
                    "WHERE ST_DWithin(geometry::geography, ST_Point(" + longitude + ", " + latitude + ")::geography, " + 1000 + ") " +
                    "ORDER BY ST_Distance(geometry::geography, ST_Point(" + longitude + ", " + latitude + ")::geography) ASC " +
                    "LIMIT 3;";

            ResultSet r = s.executeQuery(queryStr);
            List<RentalProperty> rentalProperties = new ArrayList<RentalProperty>();
            while( r.next() ) {

                //get mls and rent from database
                int mls = r.getInt(1);
                int rent = r.getInt(2);

                rentalProperties.add(new RentalProperty(mls, rent));
            }

            s.close();
            conn.close();

            return rentalProperties;
        }
        catch( Exception e ) {
            e.printStackTrace();
        }

        return null;
    }

}
