package cs3500.pa03.model;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents the gameboards of this game session
 */
public class GameBoards {

  private final ArrayList<ArrayList<Coord>> humanBoard;
  private final ArrayList<ArrayList<Coord>> robotBoard;
  private final int height;
  private final int width;
  private ArrayList<Ship> humanShips;
  private ArrayList<Ship> robotShips;

  /**
   * Creates a GameBoards with given height and width
   *
   * @param height height of the boards in this session
   * @param width  width of the boards in this session
   */
  public GameBoards(int height, int width) {
    this.height = height;
    this.width = width;
    this.humanBoard = this.makeBoard();
    this.robotBoard = this.makeBoard();
  }

  /**
   * Gets the list of ships that belong to the AIPlayer
   *
   * @return list of ships that belong to the AIPlayer
   */
  public ArrayList<Ship> getRobotShips() {
    return this.robotShips;
  }

  /**
   * Gets the list of ships that belong to the HumanPlayer
   *
   * @return list of ships that belong to the HumanPlayer
   */
  public ArrayList<Ship> getHumanShips() {
    return this.humanShips;
  }


  /**
   * Gets the HumanPlayer's gameboard
   *
   * @return the HumanPlayer's gameboard
   */
  public ArrayList<ArrayList<Coord>> getHumanBoard() {
    return this.humanBoard;
  }

  /**
   * Gets the AIPlayer's gameboard
   *
   * @return the AIPlayer's gameboard
   */
  public ArrayList<ArrayList<Coord>> getRobotBoard() {
    return this.robotBoard;
  }

  /**
   * Makes an empty board
   *
   * @return an empty gameboard
   */
  private ArrayList<ArrayList<Coord>> makeBoard() {
    ArrayList<ArrayList<Coord>> board = new ArrayList<ArrayList<Coord>>();
    for (int i = 0; i < this.height; i++) {
      ArrayList<Coord> row = new ArrayList<Coord>();
      for (int j = 0; j < this.width; j++) {
        row.add(new Coord(j, i));
      }
      board.add(row);
    }
    return board;
  }

  /**
   * Gets the result of the game
   *
   * @return the GameResult of this game
   */
  public GameResult getGameResult() {
    if (this.allFleetsDown(this.humanBoard) && this.allFleetsDown(this.robotBoard)) {
      return GameResult.DRAW;
    } else if (this.allFleetsDown(this.humanBoard)) {
      return GameResult.LOSE;
    } else {
      return GameResult.WIN;
    }
  }

  /**
   * Determines whether the game is over by determining whether either
   * of the players' fleets are all sunk
   *
   * @return is the game over?
   */
  public boolean isGameOver() {
    return (allFleetsDown(this.humanBoard) || allFleetsDown(this.robotBoard));
  }

  /**
   * Returns the number of coordinates that are left to be shot at
   *
   * @param board the board of interest
   * @return number of coordinates that have not been shot at
   */
  private int countOfCoordsNotShot(ArrayList<ArrayList<Coord>> board) {
    int count = 0;
    for (int i = 0; i < board.size(); i++) {
      for (int j = 0; j < board.get(i).size(); j++) {
        if (!board.get(i).get(j).hasBeenHit()) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Returns the number of shots allowed, which is the smaller value between the number of fleets
   * remaining and the number of coordinates not shot at
   *
   * @param ships list of ships
   * @param board board of interest
   * @return the number of shots a player is allowed to take
   */
  public int shotsAllowed(ArrayList<Ship> ships, ArrayList<ArrayList<Coord>> board) {
    return Math.min(shipsRemaining(ships), countOfCoordsNotShot(board));
  }

  /**
   * Determines whether all fleets of the given board have been sunk
   *
   * @param board the board of interest
   * @return are all fleets sunk on this board?
   */
  private boolean allFleetsDown(ArrayList<ArrayList<Coord>> board) {
    ArrayList<Coord> coordsWithShips = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (board.get(i).get(j).getHasShip()) {
          coordsWithShips.add(board.get(i).get(j));
        }
      }
    }
    boolean allDown = true;
    for (Coord c : coordsWithShips) {
      if (!c.hasBeenHit()) {
        allDown = false;
      }
    }
    return allDown;
  }


  /**
   * Initializes the human ships and robot ships fields
   *
   * @param specifications number of ships by type to be initialized
   * @param board          the board in which the ships will be placed
   * @param player         the player for which this list of ship will belong to
   * @return list of ships for the given player
   */
  public ArrayList<Ship> initializeFleet(Map<ShipType, Integer> specifications,
                                         ArrayList<ArrayList<Coord>> board, String player) {
    ArrayList<Ship> ships = new FleetInitializer().init(specifications, board, height, width);
    if (player.equals("human")) {
      this.humanShips = ships;
    } else {
      this.robotShips = ships;
    }
    return ships;
  }

  /**
   * Gets the number of ships that are not sunk
   *
   * @param ships list of ships
   * @return the number of ships that are remaining
   */
  public int shipsRemaining(ArrayList<Ship> ships) {
    int count = 0;
    for (Ship s : ships) {
      if (!s.getIsSunk()) {
        count++;
      }
    }
    return count;
  }

  /**
   * Updates the ships by marking them hit
   *
   * @param hitsTaken list of coordinates that has hit a ship
   * @param ships     list of ships to be updated
   */
  public void updateShips(List<Coord> hitsTaken, List<Ship> ships) {
    for (Ship s : ships) {
      for (int i = 0; i < hitsTaken.size(); i++) {
        if (s.getLocation().contains(hitsTaken.get(i))) {
          s.markHit(hitsTaken.get(i));
        }
      }
    }
  }

  /**
   * Updates the board's coordinates as hit
   *
   * @param board the board to mark hit
   * @param shots list of coordinates that have been hit
   */
  public void hit(ArrayList<ArrayList<Coord>> board, List<Coord> shots) {
    for (Coord c : shots) {
      board.get(c.getY()).get(c.getX()).setHit();
    }
  }
}



