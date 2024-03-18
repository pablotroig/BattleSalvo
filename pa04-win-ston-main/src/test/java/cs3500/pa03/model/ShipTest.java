package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class ShipTest {

  @Test
  public void testShips() {
    ArrayList<Coord> location = new ArrayList<>();
    location.add(new Coord(9, 0));
    location.add(new Coord(9, 1));
    location.add(new Coord(9, 2));
    location.add(new Coord(9, 3));
    location.add(new Coord(9, 4));
    Ship one = new Ship(ShipType.DESTROYER, location);

    assertFalse(one.getIsSunk());
    one.markHit(one.getLocation().get(1));
    assertFalse(one.getIsSunk());
    one.markHit(one.getLocation().get(0));
    one.markHit(one.getLocation().get(2));
    one.markHit(one.getLocation().get(3));
    assertFalse(one.getIsSunk());
  }

}