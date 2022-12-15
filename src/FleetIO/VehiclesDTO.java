package FleetIO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehiclesDTO implements Serializable {

    @JsonProperty("id")
    public int id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("license_plate")
    public String licensePlate;

    @JsonProperty("make")
    public String make;

    @JsonProperty("model")
    public String model;

    @JsonProperty("color")
    public String color;

    @JsonProperty("specs")
    public SpecsDTO Specs;

    public SpecsDTO getSpecs() {
        return Specs;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }



}
