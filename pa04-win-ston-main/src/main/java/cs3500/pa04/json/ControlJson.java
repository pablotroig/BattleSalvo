package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * The JSON that controls the communication between the server
 * JSON format of this record:
 * <p><code>
 * {
 * "name": "method name",
 * "arguments": {}
 * }
 * </code></p>
 *
 * @param methodName the name of the server method request
 * @param arguments  the arguments passed along with the message formatted as a Json object
 */
public record ControlJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") JsonNode arguments
) {
}
