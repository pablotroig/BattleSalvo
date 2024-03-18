package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTypeTest {

  ArrayList<Coord> location = new ArrayList<Coord>();
  Ship one = new Ship(ShipType.SUBMARINE, location);
  Ship two = new Ship(ShipType.BATTLESHIP, location);
  Ship three = new Ship(ShipType.SUBMARINE, location);

  @BeforeEach
  public void init() {
    location.add(new Coord(0, 0));
    location.add(new Coord(0, 1));
    location.add(new Coord(0, 2));
  }

  @Test
  public void testIsSunk() {
    assertFalse(one.getIsSunk());
    one.markHit(new Coord(0, 0));
    one.markHit(new Coord(0, 1));
    one.markHit(new Coord(0, 2));
    assertTrue(one.getIsSunk());
  }

  @Test
  public void testEquals() {
    assertEquals(ShipType.SUBMARINE, ShipType.SUBMARINE);
    assertNotEquals(three, two);
  }
}