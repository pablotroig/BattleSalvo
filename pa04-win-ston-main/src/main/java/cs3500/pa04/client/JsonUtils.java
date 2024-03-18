package cs3500.pa04.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.ControlJson;

/**
 * Simple utils class used to hold static methods that help with serializing and deserializing JSON.
 */
public class JsonUtils {
  /**
   * Converts a given record object to a JsonNode.
   *
   * @param record the record to convert
   * @return the JsonNode representation of the given record
   * @throws IllegalArgumentException if the record could not be converted correctly
   */
  public static JsonNode serializeRecord(Record record) throws IllegalArgumentException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.convertValue(record, JsonNode.class);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Given record cannot be serialized");
    }
  }

  /**
   * Converts a given record into a serialized ControlJson record
   *
   * @param argument the argument of ControlJson
   * @param methodName method name of ControlJson
   * @return a serialized version of ControlJson
   */
  public static JsonNode serializedControlJson(Record argument, String methodName) {
    JsonNode argumentToNode = serializeRecord(argument);
    ControlJson nodeToControlJson = new ControlJson(methodName, argumentToNode);
    return serializeRecord(nodeToControlJson);
  }
}
