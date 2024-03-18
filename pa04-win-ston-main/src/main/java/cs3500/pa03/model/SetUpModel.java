package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Model that holds and manipulates information in the setup process
 */
public class SetUpModel {

  /**
   * Verifies if the given dimensions are within the correct bounds of 6 and 10
   *
   * @param dimensions user-inputted dimensions for the gameboard
   * @return whether the given dimensions are valid for the gameboard
   */
  public boolean areValidDimensions(ArrayList<Integer> dimensions) {
    return (dimensions.get(0) >= 6 && dimensions.get(0) <= 10 && dimensions.get(1) >= 6
        && dimensions.get(1) <= 10);
  }

  /**
   * Converts the user-inputted number of fleets into a map of the count of ships to its ship type
   *
   * @param fleetCount the desired number of ships
   * @return the number of ships for the game by ship type
   */
  public Map<ShipType, Integer> setFleet(ArrayList<Integer> fleetCount) {

    Map<ShipType, Integer> fleet = new HashMap<ShipType, Integer>();
    fleet.put(ShipType.CARRIER, fleetCount.get(0));
    fleet.put(ShipType.BATTLESHIP, fleetCount.get(1));
    fleet.put(ShipType.DESTROYER, fleetCount.get(2));
    fleet.put(ShipType.SUBMARINE, fleetCount.get(3));
    return fleet;
  }

  /**
   * Verifies that the given numbers of the fleet do not exceed the maximum allowed number of fleets
   *
   * @param fleetCount user-inputted number of ships by ship type
   * @param maxNum     maximum number of fleets allowed
   * @return whether the given fleet numbers are valid for the game
   */
  public boolean isValidFleet(ArrayList<Integer> fleetCount, int maxNum) {
    boolean atLeastOneShip = true;
    int sum = 0;
    for (int count : fleetCount) {
      if (count == 0) {
        atLeastOneShip = false;
      }
      sum += count;
    }
    return (sum <= maxNum && atLeastOneShip);
  }

}
