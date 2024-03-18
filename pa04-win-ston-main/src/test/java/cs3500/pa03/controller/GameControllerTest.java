package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.model.AIPlayer;
import cs3500.pa03.model.GameBoards;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class GameControllerTest {

  GameBoards testBoard = new GameBoards(6, 6);
  HumanPlayer human = new HumanPlayer(6, 6, testBoard);
  HashMap<ShipType, Integer> specs = initShips();
  List<Ship> humanShip = human.setup(6, 6, specs);
  AIPlayer robot = new AIPlayer(6, 6, testBoard);
  List<Ship> robotShip = robot.setup(6, 6, specs);

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
    GameController tester = new GameController(human, robot, humanShip, robotShip, testBoard, 6, 6);

    assertThrows(NoSuchElementException.class, () -> tester.run());

    System.setIn(new ByteArrayInputStream(
        ("0 0 1 1 2 2 3 3").getBytes()));

    assertThrows(NoSuchElementException.class, () -> tester.run());
  }


  @Test
  public void testValidRun() {
    GameController tester = new GameController(human, robot, humanShip, robotShip, testBoard, 6, 6);

    assertThrows(NoSuchElementException.class, () -> tester.run());

    System.setIn(new ByteArrayInputStream(
        ("0 0 1 1 2 2 3 3").getBytes()));
    for (int i = 0; i < testBoard.getRobotBoard().size(); i++) {
      for (int j = 0; j < testBoard.getRobotBoard().get(i).size(); j++) {
        testBoard.getHumanBoard().get(i).get(j).putShipHere();
        testBoard.getHumanBoard().get(i).get(j).setHit();
      }
    }
    tester.run();
  }

}
