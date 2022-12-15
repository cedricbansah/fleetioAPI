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

//        JsonElement rawBodyJSON = JsonParser.parseString(rawBody);
//        String bodyJSON = rawBodyJSON.toString().substring(1, (rawBodyJSON.toString().length()-1));


        dblog.debug("Creating new Object Mapper");
        ObjectMapper mapper = new ObjectMapper();
        dblog.info("New Object Mapper created");

        mapper.enable(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY);



        dblog.debug("creating new array object of Vehicles DTO class. Will accept response body and save all entries to array");
        VehiclesDTO[] vehiclesDTO = mapper.readValue(rawBody, VehiclesDTO[].class);
        dblog.info("Array object created");


        dblog.debug("Establishing connection");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "*********");
        dblog.info("Connection established");

        String query = "INSERT into vehicles(id, name, licensePlate, make, model, color)" +
                " VALUES (?, ?, ?,?,?,?)";


        dblog.debug("Running query");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        dblog.info("Query ran successfully");


        dblog.debug("Mapping table columns to their values in DTO Array");
        for (int i=0; i<vehiclesDTO.length; i++) {

            preparedStatement.setInt(1, vehiclesDTO[i].getId());

            preparedStatement.setString(2, vehiclesDTO[i].getName());

            preparedStatement.setString(3, vehiclesDTO[i].getLicensePlate());

            preparedStatement.setString(4, vehiclesDTO[i].getMake());

            preparedStatement.setString(5, vehiclesDTO[i].getModel());

            preparedStatement.setString(6, vehiclesDTO[i].getColor());

        }
        dblog.info("Values mapped to Table columns");


        preparedStatement.close();
        connection.close();

    }
}
