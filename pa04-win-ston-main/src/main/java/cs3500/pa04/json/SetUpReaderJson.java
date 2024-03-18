package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a record that holds the game configurations
 *
 * @param width width of board
 * @param height height of board
 * @param fleet specifications of fleet
 */
public record SetUpReaderJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") FleetJson fleet
) {
}
