package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

/**
 * Represents a JSON record used to report damages on the player's gameboard caused by
 * opponent's shots
 *
 * @param coordinates list of coordinates
 */
public record ReportDamageJson(
    @JsonProperty("coordinates") List<JsonNode> coordinates
) {
}
