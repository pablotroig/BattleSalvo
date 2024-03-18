package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AIPlayerTest {

  GameBoards testBoard = new GameBoards(6, 6);
  AIPlayer testPlayer = new AIPlayer(6, 6, testBoard);

  Map<ShipType, Integer> fleet = new HashMap<ShipType, Integer>();

  Map<ShipType, Integer> fullFleet = new HashMap<ShipType, Integer>();

  @BeforeEach
  public void init() {
    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.BATTLESHIP, 1);
    fleet.put(ShipType.DESTROYER, 2);
    fleet.put(ShipType.SUBMARINE, 0);

    fullFleet.put(ShipType.CARRIER, 6);
    fullFleet.put(ShipType.BATTLESHIP, 0);
    fullFleet.put(ShipType.DESTROYER, 0);
    fullFleet.put(ShipType.SUBMARINE, 0);

  }

  @Test
  public void testName() {
    assertEquals("totokang", testPlayer.name());
  }

  @Test
  public void testSetUp() {
    assertEquals(4, testPlayer.setup(6, 6, fleet).size());
  }

  @Test
  public void testTakeShots() {
    testPlayer.setup(6, 6, fleet);
    for (int i = 0; i < 100; i++) {
     // assertEquals(4, testPlayer.takeShots().size());
    }
  }


  @Test
  public void testReportDamage() {
    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0));
    shots.add(new Coord(0, 1));
    shots.add(new Coord(0, 2));

    testPlayer.setup(6, 6, fullFleet);
    assertEquals(3, testPlayer.reportDamage(shots).size());
  }

  @Test
  public void testSuccessfulHits() {
    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0));
    assertFalse(testBoard.getHumanBoard().get(0).get(0).hasBeenHit());
    testPlayer.successfulHits(shots);
    assertTrue(testBoard.getHumanBoard().get(0).get(0).hasBeenHit());
  }

  @Test
  public void testEndGameWin() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    testPlayer.endGame(GameResult.WIN, "you sunk all ships");
    assertEquals("You won because you sunk all ships\n", outContent.toString());
  }

  @Test
  public void testEndGameDraw() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    testPlayer.endGame(GameResult.DRAW, "you both sunk all ships");
    assertEquals("You tied because you both sunk all ships\n", outContent.toString());
  }

}