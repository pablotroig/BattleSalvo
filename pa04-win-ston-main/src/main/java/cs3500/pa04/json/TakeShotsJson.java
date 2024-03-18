package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

/**
 * Represents a JSON record to store shots taken by the player
 *
 * @param coordinates list of shots
 */
public record TakeShotsJson(
    @JsonProperty("coordinates") List<JsonNode> coordinates
) {
}
