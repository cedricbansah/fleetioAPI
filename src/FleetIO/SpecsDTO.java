package FleetIO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecsDTO {

    @JsonProperty("vehicle_id")
    private int vehicleId;

    @JsonProperty("body_type")
    private String bodyType;

    @JsonProperty("drive_type")
    private String driveType;

    @JsonProperty("engine_description")
    private String engineDescription;

    @JsonProperty("max_hp")
    private String maxHP;

    public String getBodyType() {
        return bodyType;
    }

    public String getDriveType() {
        return driveType;
    }

    public String getEngineDescription() {
        return engineDescription;
    }

    public String getMaxHP() {
        return maxHP;
    }
}
