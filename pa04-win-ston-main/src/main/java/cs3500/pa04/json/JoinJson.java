package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a JSON record used to join the server
 *
 * @param name     GitHub username
 * @param gameType either SINGLE or MULTI
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType
) {
}
