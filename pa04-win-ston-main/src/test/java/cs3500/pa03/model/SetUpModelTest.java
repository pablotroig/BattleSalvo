package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SetUpModelTest {

  SetUpModel tester = new SetUpModel();

  @Test
  public void testAreValidDimensions() {
    assertFalse(tester.areValidDimensions(new ArrayList<Integer>(Arrays.asList(0, 0))));
    assertFalse(tester.areValidDimensions(new ArrayList<Integer>(Arrays.asList(13, 20))));
    assertFalse(tester.areValidDimensions(new ArrayList<Integer>(Arrays.asList(8, 0))));
    assertFalse(tester.areValidDimensions(new ArrayList<Integer>(Arrays.asList(0, 8))));
    assertFalse(tester.areValidDimensions(new ArrayList<Integer>(Arrays.asList(8, 20))));
    assertFalse(tester.areValidDimensions(new ArrayList<Integer>(Arrays.asList(20, 8))));
    assertTrue(tester.areValidDimensions(new ArrayList<Integer>(Arrays.asList(10, 10))));
  }

  @Test
  public void testSetFleet() {
    Map<ShipType, Integer> result = new HashMap<ShipType, Integer>();
    result.put(ShipType.CARRIER, 1);
    result.put(ShipType.BATTLESHIP, 1);
    result.put(ShipType.DESTROYER, 2);
    result.put(ShipType.SUBMARINE, 1);

    assertEquals(result, tester.setFleet(new ArrayList<Integer>(Arrays.asList(1, 1, 2, 1))));
  }

  @Test
  public void testIsValidFleet() {
    assertFalse(tester.isValidFleet(new ArrayList<Integer>(Arrays.asList(8, 1, 2, 1)), 8));
    assertTrue(tester.isValidFleet(new ArrayList<Integer>(Arrays.asList(7, 1, 2, 1)), 13));
  }


}