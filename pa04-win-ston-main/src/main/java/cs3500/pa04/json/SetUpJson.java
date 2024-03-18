package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

/**
 * Represents a JSON record used to set up the game
 *
 * @param fleet list of ships
 */
public record SetUpJson(
    @JsonProperty("fleet") List<JsonNode> fleet
) {
}
