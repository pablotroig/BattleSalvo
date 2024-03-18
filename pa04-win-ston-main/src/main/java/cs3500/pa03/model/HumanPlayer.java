package cs3500.pa03.model;


import cs3500.pa03.view.GameView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a human player
 */
public class HumanPlayer implements Player {

  private final GameBoards sessionGameBoard;
  private final GameView gameView = new GameView();
  private final int height;
  private final int width;


  /**
   * Creates a new HumanPlayer
   *
   * @param height           height of the player's board
   * @param width            width of the player's board
   * @param sessionGameBoard the gameboard the user is playing on
   */
  public HumanPlayer(int height, int width, GameBoards sessionGameBoard) {
    this.sessionGameBoard = sessionGameBoard;
    this.height = height;
    this.width = width;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "Toto";
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
    return sessionGameBoard.initializeFleet(specifications, sessionGameBoard.getHumanBoard(),
        "human");
  }


  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    return gameView.getShots(sessionGameBoard.shotsAllowed(sessionGameBoard.getHumanShips(),
        sessionGameBoard.getRobotBoard()), this.height, this.width);
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
      for (Ship ship : sessionGameBoard.getHumanShips()) {
        if (ship.isHit(opponentShotsOnBoard.get(i))) {
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
      this.sessionGameBoard.getRobotBoard().get(c.getY()).get(c.getX()).setHit();
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

  }
}
