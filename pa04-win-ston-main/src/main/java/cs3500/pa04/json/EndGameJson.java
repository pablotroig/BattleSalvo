package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a JSON record used at the end of the game
 *
 * @param result result of the game
 * @param reason reason behind the result
 */
public record EndGameJson(
    @JsonProperty String result,
    @JsonProperty String reason

) {
}
