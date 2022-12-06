package FleetIO;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Deserialize {
    public static void main (String [] args) throws InterruptedException, IOException {



        // create an HttpRequest object to make a new request to the api
        HttpRequest cNANA = HttpRequest.newBuilder()

                // pass api url here with the necessary parameters
                .uri(URI.create("https://secure.fleetio.com/api/v1/vehicles?q[license_plate_eq]=CNANA22"))

                // add header values
                .header("accept", "application/json")
                .header("Authorization", "Token 5fd4c3bbc8ac14dbfd2f1381adac3f0fa6e03ce3")
                .header("Account-Token", "9e82f2090f")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        // assign response of request to a new variable as a string
        HttpResponse<String> cnanaResponse = HttpClient.newHttpClient().send(cNANA, HttpResponse.BodyHandlers.ofString());


        // assign the body of api response to variable
        String uglyJsonString = cnanaResponse.body();


        // format JSON string so it's well indented and readable
        Gson prettify = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(uglyJsonString);

        //myString variable stores the parsed string
        // and the substring method removes square brackets from beginning and end of the response body
        String myString = je.toString().substring(1, (je.toString().length()-1));

        //assign formatted string to new variable
        String prettyJsonString = prettify.toJson(myString);

        // display response as new formatted string
        System.out.println(prettyJsonString);



        // create instance of the object mapper class to map api response properties to class attributes
        ObjectMapper mapper = new ObjectMapper();

        // create instance of Vehicles class that whose class attributes are mapped from api response
        Vehicles myCar = mapper.readValue(myString, Vehicles.class);

        // print out object values to check if properties mapped successfully
        System.out.println(myCar.getName());
        System.out.println(myCar.getLicense_plate());
        System.out.println(myCar.getColor());
    }
}
