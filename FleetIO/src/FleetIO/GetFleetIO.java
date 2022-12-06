package FleetIO;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import com.google.gson.*;
import com.google.gson.JsonParser;


public class GetFleetIO {

    public static void main(String[] args) throws IOException, InterruptedException {

        // create an HttpRequest object to make a new request to the api
        HttpRequest vehiclesRequest = HttpRequest.newBuilder()

                // pass api url here with the necessary parameters
                .uri(URI.create("https://secure.fleetio.com/api/v1/vehicles?include_archived=0"))

                // add header values
                .header("accept", "application/json")
                .header("Authorization", "Token 5fd4c3bbc8ac14dbfd2f1381adac3f0fa6e03ce3")
                .header("Account-Token", "9e82f2090f")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        // assign response of request to a new variable as a string
        HttpResponse<String> vehiclesResponse = HttpClient.newHttpClient().send(vehiclesRequest, HttpResponse.BodyHandlers.ofString());


        // assign the body of api response to variable
        String uglyJsonString = vehiclesResponse.body();


        // format JSON string so it's well indented and readable
        Gson prettify = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(uglyJsonString);
        String prettyJsonString = prettify.toJson(je);
        System.out.println(prettyJsonString);




    }








}