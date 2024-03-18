package cs3500.pa03.model;

/**
 * Represents a ShipType
 */
public enum ShipType {
  CARRIER(6), BATTLESHIP(5), DESTROYER(4), SUBMARINE(3);
  private final int size;

  /**
   * Represents a Type of ship
   *
   * @param size size of the Type of ship
   */
  ShipType(final int size) {
    this.size = size;
  }


  /**
   * Gets the size of this type
   *
   * @return size of the type of ship
   */
  public int getSize() {
    return size;
  }

}
