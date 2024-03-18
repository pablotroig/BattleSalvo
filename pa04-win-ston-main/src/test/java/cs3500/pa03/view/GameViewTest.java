package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.GameResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class GameViewTest {

  GameView test = new GameView();

  private ArrayList<ArrayList<Coord>> makeBoard(int height, int width) {
    ArrayList<ArrayList<Coord>> board = new ArrayList<ArrayList<Coord>>();
    for (int i = 0; i < height; i++) {
      ArrayList<Coord> row = new ArrayList<Coord>();
      for (int j = 0; j < width; j++) {
        row.add(new Coord(i, j));
      }
      board.add(row);
    }
    return board;
  }

  @Test
  public void testShowBoard() {
    ArrayList<ArrayList<Coord>> twoByTwoBoard = this.makeBoard(2, 2);
    twoByTwoBoard.get(0).get(0).setHit();
    twoByTwoBoard.get(1).get(0).putShipHere();
    twoByTwoBoard.get(1).get(1).putShipHere();
    twoByTwoBoard.get(1).get(1).setHit();

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));


    test.showBoard(twoByTwoBoard, 2, 2, "X");
    assertEquals("M O \nX H \n", outContent.toString());
  }

  @Test
  public void testShowMyBoard() {
    ArrayList<ArrayList<Coord>> twoByTwoBoard = this.makeBoard(2, 2);
    twoByTwoBoard.get(0).get(0).setHit();
    twoByTwoBoard.get(1).get(0).putShipHere();
    twoByTwoBoard.get(1).get(1).putShipHere();
    twoByTwoBoard.get(1).get(1).setHit();

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));


    test.showMyBoard(twoByTwoBoard, 2, 2);
    assertEquals("\nThis is your board:\n------------------------------\nM O \nX H \n",
        outContent.toString());
  }

  @Test
  public void testShowOpponentBoard() {
    ArrayList<ArrayList<Coord>> twoByTwoBoard = this.makeBoard(2, 2);
    twoByTwoBoard.get(0).get(0).setHit();
    twoByTwoBoard.get(1).get(0).putShipHere();
    twoByTwoBoard.get(1).get(1).putShipHere();
    twoByTwoBoard.get(1).get(1).setHit();

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));


    test.showOpponentBoard(twoByTwoBoard, 2, 2);
    assertEquals("\nThis is your opponent's board:\n------------------------------\nM O \nO H \n",
        outContent.toString());
  }

  @Test
  public void testGetShots() {

    System.setIn(new ByteArrayInputStream(
        ("0 0 0 0 0 0").getBytes()));
    assertEquals(3, test.getShots(3, 3, 3).size());
  }

  @Test
  public void testIllegalGetShots() {

    System.setIn(new ByteArrayInputStream(
        ("0 4 7 0 0 0").getBytes()));

    assertThrows(NoSuchElementException.class, () -> test.getShots(3, 3, 3).size());
  }

  @Test
  public void testShowResultTie() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    test.showResult(GameResult.DRAW);
    assertEquals("You have tied!\n", outContent.toString());
  }

  @Test
  public void testShowResultWin() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    test.showResult(GameResult.WIN);
    assertEquals("Congrats! You won!\n", outContent.toString());
  }

  @Test
  public void testShowResultLose() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    test.showResult(GameResult.LOSE);
    assertEquals("Oops. You have lost!\n", outContent.toString());
  }
}