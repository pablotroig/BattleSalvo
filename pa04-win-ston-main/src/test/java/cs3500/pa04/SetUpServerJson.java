package cs3500.pa04;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.FleetJson;

/**
 * The format in which the server communicates the game specifications
 *
 * @param width width of board
 * @param height height of board
 * @param spec specifications of fleet
 */
public record SetUpServerJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") FleetJson spec
) {
}
