package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.model.AIPlayer;
import cs3500.pa03.model.GameBoards;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class SetUpControllerTest {
  GameBoards testBoard = new GameBoards(6, 6);
  HumanPlayer human = new HumanPlayer(6, 6, testBoard);
  HashMap<ShipType, Integer> specs = initShips();
  List<Ship> humanShip = human.setup(6, 6, specs);
  AIPlayer robot = new AIPlayer(6, 6, testBoard);
  List<Ship> robotShip = robot.setup(6, 6, specs);

  SetUpController tester = new SetUpController();

  private HashMap<ShipType, Integer> initShips() {
    HashMap<ShipType, Integer> specs = new HashMap<ShipType, Integer>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 2);
    specs.put(ShipType.SUBMARINE, 0);
    return specs;
  }

  @Test
  public void testRun() {
    System.setIn(new ByteArrayInputStream(("6 6").getBytes()));
    assertThrows(NoSuchElementException.class, () -> tester.run());

  }

  @Test
  public void testSetDimensions() {
    System.setIn(new ByteArrayInputStream(
        ("6 6").getBytes()));
    assertEquals(new ArrayList<Integer>(Arrays.asList(6, 6)), tester.setDimensions());
  }

  @Test
  public void testSetWrongDimensions() {
    System.setIn(new ByteArrayInputStream(
        ("6 19").getBytes()));

    assertThrows(NoSuchElementException.class, () -> tester.setDimensions());
  }

  @Test
  public void testSetFleetDimensions() {

    System.setIn(new ByteArrayInputStream(
        ("1 1 1 1").getBytes()));
    assertEquals(new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1)), tester.setFleetDimensions(6));
  }

  @Test
  public void testSetFleetDimensionsInvalid() {

    System.setIn(new ByteArrayInputStream(
        ("0 4 1 3").getBytes()));
    assertThrows(NoSuchElementException.class, () -> tester.setFleetDimensions(6));
  }

}