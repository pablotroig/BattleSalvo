package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a JSON record of a fleet
 *
 * @param carrierSize    the number of Carriers in the fleet
 * @param battleshipSize the number of Battleships in the fleet
 * @param destroyerSize  the number of Destroyers in the fleet
 * @param submarineSize  the number of submarines in the fleet
 */
public record FleetJson(
    @JsonProperty("CARRIER") int carrierSize,
    @JsonProperty("BATTLESHIP") int battleshipSize,
    @JsonProperty("DESTROYER") int destroyerSize,
    @JsonProperty("SUBMARINE") int submarineSize
) {


  /**
   * Generates a map of the fleet
   *
   * @return the number of ships by type to be initialized as a map
   */
  public Map<ShipType, Integer> generateFleetMap() {
    HashMap<ShipType, Integer> shipMap = new HashMap<>();
    shipMap.put(ShipType.CARRIER, this.carrierSize);
    shipMap.put(ShipType.BATTLESHIP, this.battleshipSize);
    shipMap.put(ShipType.DESTROYER, this.destroyerSize);
    shipMap.put(ShipType.SUBMARINE, this.submarineSize);
    return shipMap;
  }
}
