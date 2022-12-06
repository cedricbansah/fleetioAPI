package FleetIO;


// import necessary libraries
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import java.io.Serializable;

@Data // wrap objects of class as data item

// ignore undeclared fields in api response body
@JsonIgnoreProperties(ignoreUnknown = true)


// class declared to implement serializable interface
public class Vehicles implements Serializable {


    // all api properties and their corresponding attributes in Vehicles class
    @JsonProperty("archived_at")
    public String archived_at;

    @JsonProperty("account_id")
    public int account_id;


    @JsonProperty("id")
    public int id;

    @JsonProperty("vehicle_type_name")
    public String vehicle_type_name;

    @JsonProperty("license_plate")
    public String license_plate;

    @JsonProperty("make")
    public String make;

    @JsonProperty("model")
    public String model;

    @JsonProperty("color")
    public String color;

    @JsonProperty("name")
    public String name;


    // getters and setters for class attributes
    public String getVehicleType() {
        return vehicle_type_name;
    }

    public void setVehicleType(String vehicle_type_name) {
        this.vehicle_type_name = vehicle_type_name;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicensePlate(String license_plate) {
        this.license_plate = license_plate;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArchived_at() {
        return archived_at;
    }

    public void setArchived_at(String archived_at) {
        this.archived_at = archived_at;
    }

    public String getVehicle_type_name() {
        return vehicle_type_name;
    }

    public void setVehicle_type_name(String vehicle_type_name) {
        this.vehicle_type_name = vehicle_type_name;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }



}