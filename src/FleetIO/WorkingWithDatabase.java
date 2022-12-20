package FleetIO;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.Arrays;

public class WorkingWithDatabase {
    static Logger dblog = Logger.getLogger(WorkingWithDatabase.class);

    public static void main (String [] args) throws IOException, InterruptedException, SQLException {
        // create an HttpRequest object to make a new request to the api
        dblog.debug("Sending HTTP request to API");
        HttpRequest request = HttpRequest.newBuilder()

                // pass api url here with the necessary parameters
                .uri(URI.create("https://secure.fleetio.com/api/v1/vehicles"))

                // add header values
                .header("accept", "application/json")
                .header("Authorization", "Token 463227032b0367550695cf350481eb81d9df46af")
                .header("Account-Token", "4cf4569fb1")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        dblog.info("HTTP request sent to API successfully");

        dblog.debug("Assigning HTTP response to new variable a string");
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        dblog.info("Response handled as string successfully");

        String rawBody = response.body();


        dblog.debug("Creating new Object Mapper");
        ObjectMapper mapper = new ObjectMapper();
        dblog.info("New Object Mapper created");

        mapper.enable(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY);



        dblog.debug("creating new array object of Vehicles DTO class. Will accept response body and save all entries to array");
        VehiclesDTO[] vehiclesDTO = mapper.readValue(rawBody, VehiclesDTO[].class);
        dblog.info("Array object created");


        populateVehiclesDB(vehiclesDTO);
        populateSpecsDB(vehiclesDTO);




    }





    public static void populateVehiclesDB(VehiclesDTO[] dtos) throws SQLException {
        dblog.debug("Establishing connection");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "********");
        connection.setAutoCommit(true);
        dblog.info("Connection established");

        String query = "INSERT INTO vehicles(id, name, license_plate, make, model, color)" +
                " VALUES" + "(?, ?, ?,?,?,?)";


        dblog.debug("Running query");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        dblog.info("Query ran successfully");

        int vlen = dtos.length;
        System.out.println(vlen);


        dblog.debug("Mapping table columns to their values in DTO Array");
        for (int i=0; i<dtos.length; i++) {

            preparedStatement.setInt(1, dtos[i].getId());

            preparedStatement.setString(2, dtos[i].getName());

            preparedStatement.setString(3, dtos[i].getLicensePlate());

            preparedStatement.setString(4, dtos[i].getMake());

            preparedStatement.setString(5, dtos[i].getModel());

            preparedStatement.setString(6, dtos[i].getColor());

            preparedStatement.execute();


        }

        preparedStatement.close();
        connection.close();





    }





    public static void populateSpecsDB(VehiclesDTO[] specsDTOS) throws SQLException {

        dblog.debug("Establishing connection");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "********");
        dblog.info("Connection established");

        String query = "INSERT INTO specs(vehicle_id, body_type, drive_type, engine_description, max_hp)" +
                " VALUES" + "(?, ?, ?, ?, ?)";


        dblog.debug("Running query");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        dblog.info("Query ran successfully");

        int len = specsDTOS.length;
        System.out.println(len);

        dblog.debug("Mapping table columns to their values in DTO Array");
        for (int i = 0; i < specsDTOS.length; i++) {

            preparedStatement.setInt(1, specsDTOS[i].getSpecs().getVehicleId());

            preparedStatement.setString(2, specsDTOS[i].getSpecs().getBodyType());

            preparedStatement.setString(3, specsDTOS[i].getSpecs().getDriveType());

            preparedStatement.setString(4, specsDTOS[i].getSpecs().getEngineDescription());

            preparedStatement.setString(5, specsDTOS[i].getSpecs().getMaxHP());

            preparedStatement.execute();


        }

        preparedStatement.close();
        connection.close();
    }
}
