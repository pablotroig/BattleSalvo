package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Ship
 *
 * @param coord the upper-leftmost coordinate the ship occupies
 * @param length the length of the ship
 * @param direction whether the ship is vertical or horizontal
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction

) {
}
