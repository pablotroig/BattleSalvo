package cs3500.pa03.model;


import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an AI Player
 */
public class AIPlayer implements Player {

  private final GameBoards sessionGameBoard;
  private final int height;
  private final int width;
  private int coordsLeft;

  /**
   * Creates an AIPlayer
   *
   * @param height           height of the player's board
   * @param width            width of the player's board
   * @param sessionGameBoard the gameboard the user is playing on
   */
  public AIPlayer(int height, int width, GameBoards sessionGameBoard) {
    this.height = height;
    this.width = width;
    this.sessionGameBoard = sessionGameBoard;
    this.coordsLeft = height * width;
  }


  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "totokang";
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    return sessionGameBoard.initializeFleet(specifications, sessionGameBoard.getRobotBoard(),
        "robot");
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {

    List<Coord> shots = new ArrayList<Coord>();
    int shotsAllowed = Math.min(sessionGameBoard.shipsRemaining(sessionGameBoard.getRobotShips()),
        coordsLeft);
    Random rand = new Random();
    for (int i = 0; i < shotsAllowed; i++) {
      Coord newShot = sessionGameBoard.getHumanBoard().get(rand.nextInt(0, this.height))
          .get(rand.nextInt(0, this.width));
      if (!shots.contains(newShot) && !newShot.hasBeenHit()) {
        shots.add(newShot);
        newShot.setHit();
        coordsLeft--;
      } else {
        while (shots.contains(newShot) || newShot.hasBeenHit()) {
          newShot = sessionGameBoard.getHumanBoard().get(rand.nextInt(0, this.height))
              .get(rand.nextInt(0, this.width));
        }
        shots.add(newShot);
        newShot.setHit();
        coordsLeft--;
      }
    }
    return shots;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *     ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> damage = new ArrayList<Coord>();
    for (int i = 0; i < opponentShotsOnBoard.size(); i++) {
      for (Ship ship : sessionGameBoard.getRobotShips()) {
        if (ship.isHit(opponentShotsOnBoard.get(i))) {
          ship.markHit(opponentShotsOnBoard.get(i));
          damage.add(opponentShotsOnBoard.get(i));
        }
      }
    }
    return damage;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : shotsThatHitOpponentShips) {
      this.sessionGameBoard.getHumanBoard().get(c.getY()).get(c.getX()).setHit();
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    if (result.equals(GameResult.DRAW)) {
      System.out.println("You tied because " + reason);
    } else if (result.equals(GameResult.WIN)) {
      System.out.println("You won because " + reason);
    } else if (result.equals(GameResult.LOSE)) {
      System.out.println("You lost because " + reason);
    }
  }

}
