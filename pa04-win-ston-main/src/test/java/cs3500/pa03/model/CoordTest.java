package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CoordTest {


  Coord one = new Coord(0, 0);
  Coord two = new Coord(1, 1);
  Coord three = new Coord(1, 1);

  @Test
  public void testHasBeenHit() {
    assertFalse(one.hasBeenHit());
    one.setHit();
    assertTrue(one.hasBeenHit());
  }

  @Test
  public void testHasShip() {
    assertFalse(one.getHasShip());
    one.putShipHere();
    assertTrue(one.getHasShip());
  }

  @Test
  public void testGetters() {
    assertEquals(0, one.getX());
    assertEquals(1, two.getY());
  }

  @Test
  public void testEquals() {
    three.putShipHere();
    assertEquals(three, two);
    assertEquals(two, two);
    assertNotEquals(two, one);

  }

}