package cs3500.pa03.model;

import java.util.ArrayList;

/**
 * Represents a single Ship
 */
public class Ship {
  private final ShipType type;
  private final ArrayList<Coord> location;
  private final boolean isSunk;

  /**
   * Represents a ship
   *
   * @param type type of ship
   * @param location coordinates this ship occupies
   */
  public Ship(ShipType type, ArrayList<Coord> location) {
    this.type = type;
    this.location = location;
    this.isSunk = false;
  }

  /**
   * Returns the Coords that this ship occupies
   *
   * @return the ship's location
   */
  public ArrayList<Coord> getLocation() {
    return this.location;
  }

  /**
   * Marks this ship as hit at the given Coord
   *
   * @param shot Coord in which the ship was shot
   */
  public void markHit(Coord shot) {
    for (Coord shipCoord : location) {
      if (shot.equals(shipCoord)) {
        shipCoord.setHit();
      }
    }
  }

  /**
   * Determines whether this ship has been hit in any of its location
   *
   * @param shot shot of interest
   * @return has this ship been hit by this shot?
   */
  public boolean isHit(Coord shot) {
    for (Coord shipCoord : location) {
      if (shot.equals(shipCoord)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determines if all locations of this ship has been hit
   *
   * @return has this ship sunk?
   */
  public boolean getIsSunk() {
    boolean sunk = true;
    for (int i = 0; i < location.size(); i++) {
      if (!(location.get(i).hasBeenHit())) {
        sunk = false;
      }
    }
    return sunk;
  }
}
