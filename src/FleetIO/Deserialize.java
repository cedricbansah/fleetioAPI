package FleetIO;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.apache.log4j.*;


public class Deserialize {

    static Logger log = Logger.getLogger(Deserialize.class);
    public static void main (String [] args) throws InterruptedException, IOException {

        // create an HttpRequest object to make a new request to the api
        log.debug("Sending HTTP request to API");
        HttpRequest cNANA = HttpRequest.newBuilder()

                // pass api url here with the necessary parameters
                .uri(URI.create("https://secure.fleetio.com/api/v1/vehicles?q[license_plate_eq]=GR3330-11"))

                // add header values
                .header("accept", "application/json")
                .header("Authorization", "Token 463227032b0367550695cf350481eb81d9df46af")
                .header("Account-Token", "4cf4569fb1")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        log.info("HTTP request sent to API successfully");

        // assign response of request to a new variable as a string
        log.debug("Receiving response of request as string");
        HttpResponse<String> cnanaResponse = HttpClient.newHttpClient().send(cNANA, HttpResponse.BodyHandlers.ofString());
        log.info("Response of request received as string");

        log.debug("Extract body of HTTP response");
        // assign the body of api response to variable
        String uglyJsonString = cnanaResponse.body();
        log.info("Body of HTTP response extracted successfully");



        log.debug("Format body of response");
        // format JSON string so it's well indented and readable
        Gson prettify = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(uglyJsonString);

        //myString variable stores the parsed string
        // and the substring method removes square brackets from beginning and end of the response body
        String myString = je.toString().substring(1, (je.toString().length()-1));

        //assign formatted string to new variable
        String prettyJsonString = prettify.toJson(je);
        log.info("Response body formatted successfully");


        // display response as new formatted string
//        System.out.println(prettyJsonString);



        log.debug("create mapper object and map response to new object of Vehicles class");
        // create instance of the object mapper class to map api response properties to class attributes
        ObjectMapper mapper = new ObjectMapper();

        // create instance of Vehicles class that whose class attributes are mapped from api response
        Vehicles myCar = mapper.readValue(myString, Vehicles.class);
        log.info("New vehicle created and mapped to JSON response successfully");


//         print out object values to check if properties mapped successfully
         System.out.println("Car Name: " + myCar.getName());
         System.out.println("License Plate: " + myCar.getLicense_plate());
         System.out.println("Color: " + myCar.getColor());
         System.out.println("Specs Account ID: " + myCar.getSpecs().getAccountId());
         System.out.println("Specs Vehicle ID: " + myCar.getSpecs().getVehicleId());
         System.out.println("Specs Body Type: " + myCar.getSpecs().getBodyType());
    }
}
