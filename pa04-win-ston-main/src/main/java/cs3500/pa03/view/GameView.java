package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.GameResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a View of the MVC pattern. Deals with interacting with the user during the game
 */
public class GameView {

  /**
   * Shows the user the given board
   *
   * @param board        board to show user
   * @param height       height of board
   * @param width        width of board
   * @param shipNotation how a ship should be notated to the user
   */
  public void showBoard(ArrayList<ArrayList<Coord>> board, int height, int width,
                        String shipNotation) {
    for (int row = 0; row < height; row++) {
      String boardRow = "";
      for (int col = 0; col < width; col++) {
        if (board.get(row).get(col).getHasShip() && board.get(row).get(col).hasBeenHit()) {
          boardRow += "H ";
        } else if (board.get(row).get(col).getHasShip()) {
          boardRow += (shipNotation + " ");
        } else if (board.get(row).get(col).hasBeenHit()) {
          boardRow += "M ";
        } else {
          boardRow += "O ";
        }
      }
      System.out.println(boardRow);
    }
  }

  /**
   * Shows the user their own board. Ships are shown as "X"
   *
   * @param board  the user's board
   * @param height height of the board
   * @param width  width of the board
   */
  public void showMyBoard(ArrayList<ArrayList<Coord>> board, int height, int width) {
    System.out.println("\nThis is your board:\n------------------------------");
    this.showBoard(board, height, width, "X");
  }

  /**
   * Shows the user their opponent's board. Ships are shown as "O" so that users do not know
   * where the ships are located
   *
   * @param board  the opponent's board
   * @param height height of the board
   * @param width  width of the board
   */
  public void showOpponentBoard(ArrayList<ArrayList<Coord>> board, int height, int width) {
    System.out.println("\nThis is your opponent's board:\n------------------------------");
    this.showBoard(board, height, width, "O");
  }

  /**
   * Retrieves the shots that the user wants to take
   *
   * @param count  number of shots the user needs to take
   * @param height height of the board
   * @param width  width of the board
   * @return shots that the user took
   */
  public List<Coord> getShots(int count, int height, int width) {
    Scanner s = new Scanner(System.in);
    ArrayList<Coord> shots = new ArrayList<Coord>();
    System.out.println("\nPlease enter " + count + " shots:\n------------------------------");
    for (int i = 0; i < count; i++) {
      int x = s.nextInt();
      int y = s.nextInt();
      if (x < height && y < width && x >= 0 && y >= 0) {
        shots.add(new Coord(x, y));
      } else {
        System.out.println("\nPlease enter valid shots.");
        return getShots(count, height, width);
      }
    }
    return shots;
  }

  /**
   * Shows the result of the game
   *
   * @param result Result of the game in terms of the human user
   */
  public void showResult(GameResult result) {
    if (result.equals(GameResult.DRAW)) {
      System.out.println("You have tied!");
    }
    if (result.equals(GameResult.WIN)) {
      System.out.println("Congrats! You won!");
    }
    if (result.equals(GameResult.LOSE)) {
      System.out.println("Oops. You have lost!");
    }
  }

}
