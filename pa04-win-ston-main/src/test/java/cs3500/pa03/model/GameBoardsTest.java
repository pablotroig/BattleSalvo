package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameBoardsTest {

  GameBoards testBoard;

  @BeforeEach
  public void init() {
    testBoard = new GameBoards(6, 6);
  }

  @Test
  public void testIsGameOver() {
    Map<ShipType, Integer> fleet = new HashMap<ShipType, Integer>();

    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.BATTLESHIP, 1);
    fleet.put(ShipType.DESTROYER, 2);
    fleet.put(ShipType.SUBMARINE, 1);


    testBoard.initializeFleet(fleet, testBoard.getHumanBoard(), "human");
    testBoard.initializeFleet(fleet, testBoard.getRobotBoard(), "robot");

    assertFalse(testBoard.isGameOver());

    for (int i = 0; i < testBoard.getRobotBoard().size(); i++) {
      for (int j = 0; j < testBoard.getRobotBoard().get(i).size(); j++) {
        testBoard.getHumanBoard().get(i).get(j).putShipHere();
        testBoard.getHumanBoard().get(i).get(j).setHit();
      }
    }
    assertTrue(testBoard.isGameOver());
  }

  @Test
  public void testGetGameResult() {
    Map<ShipType, Integer> fleet = new HashMap<ShipType, Integer>();

    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.BATTLESHIP, 1);
    fleet.put(ShipType.DESTROYER, 2);
    fleet.put(ShipType.SUBMARINE, 0);


    testBoard.initializeFleet(fleet, testBoard.getHumanBoard(), "human");

    assertEquals(GameResult.WIN, testBoard.getGameResult());

    for (int i = 0; i < testBoard.getRobotBoard().size(); i++) {
      for (int j = 0; j < testBoard.getRobotBoard().get(i).size(); j++) {
        testBoard.getHumanBoard().get(i).get(j).putShipHere();
        testBoard.getHumanBoard().get(i).get(j).setHit();
      }
    }
    assertEquals(GameResult.DRAW, testBoard.getGameResult());

    testBoard.initializeFleet(fleet, testBoard.getRobotBoard(), "robot");

    assertEquals(GameResult.LOSE, testBoard.getGameResult());

  }

  @Test
  public void testUpdateShips() {
    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0));
    shots.add(new Coord(0, 1));
    shots.add(new Coord(0, 2));

    ArrayList<Coord> location = new ArrayList<>();
    location.add(new Coord(0, 0));
    location.add(new Coord(0, 1));
    location.add(new Coord(0, 2));

    ArrayList<Coord> locationTwo = new ArrayList<>();
    locationTwo.add(new Coord(1, 0));
    locationTwo.add(new Coord(1, 1));
    locationTwo.add(new Coord(1, 2));

    ArrayList<Ship> testShips = new ArrayList<>();
    testShips.add(new Ship(ShipType.SUBMARINE, location));
    testShips.add(new Ship(ShipType.SUBMARINE, locationTwo));

    assertFalse(testShips.get(0).getLocation().get(0).hasBeenHit());
    testBoard.updateShips(shots, testShips);
    assertTrue(testShips.get(0).getLocation().get(0).hasBeenHit());
  }

  @Test
  public void testHit() {
    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0));
    shots.add(new Coord(0, 1));
    shots.add(new Coord(0, 2));

    assertFalse(testBoard.getRobotBoard().get(0).get(0).hasBeenHit());
    testBoard.hit(testBoard.getRobotBoard(), shots);
    assertTrue(testBoard.getRobotBoard().get(0).get(0).hasBeenHit());
  }

}