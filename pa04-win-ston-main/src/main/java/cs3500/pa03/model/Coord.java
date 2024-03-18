package cs3500.pa03.model;

/**
 * Represents a coordinate on the gameboard
 */
public class Coord {
  private final int x;
  private final int y;
  private boolean hasShip;
  private boolean beenHit;

  /**
   * Creates a new Coord with the given x and y that has not been hit, and has no ship on it
   *
   * @param x x coordinate
   * @param y y coordinate
   */
  public Coord(int x, int y) {
    this.x = x;
    this.y = y;
    this.hasShip = false;
    this.beenHit = false;
  }

  /**
   * Has this coordinate been hit?
   *
   * @return whether this coordinate has been hit
   */
  public boolean hasBeenHit() {
    return this.beenHit;
  }

  /**
   * Marks this coordinate as hit
   */
  public void setHit() {
    this.beenHit = true;
  }

  /**
   * Gets the x-value of this Coord
   *
   * @return x
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y-value of this Coord
   *
   * @return y
   */
  public int getY() {
    return y;
  }

  /**
   * Does this Coord hold a ship?
   *
   * @return whether this Coord has a ship
   */
  public boolean getHasShip() {
    return this.hasShip;
  }

  /**
   * Marks this Coord as holding a ship
   */
  public void putShipHere() {
    this.hasShip = true;
  }

  /**
   * Overriding the equals method to compare just the x and y values of the coordinate
   *
   * @param obj the other Coord
   * @return whether this Coord and the given Coord have the same x and y values
   */
  @Override
  public boolean equals(Object obj) {
    Coord other = (Coord) obj;
    return (other.x == this.x) && (other.y == this.y);
  }
}