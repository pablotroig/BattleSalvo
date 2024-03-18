package cs3500.pa03.controller;

import cs3500.pa03.model.AIPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameBoards;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.Ship;
import cs3500.pa03.view.GameView;
import java.util.List;

/**
 * Represents a Controller that is responsible for controlling the gaming portion of BattleSalvo
 */
public class GameController implements Controller {
  private final HumanPlayer human;
  private final AIPlayer robot;
  private final int height;
  private final int width;
  private final GameBoards sessionGameBoard;
  private final List<Ship> humanShips;
  private final List<Ship> robotShips;
  private final GameView view = new GameView();

  GameController(HumanPlayer human, AIPlayer robot, List<Ship> humanShips,
                 List<Ship> robotShips,
                 GameBoards sessionGameBoard, int height, int width) {
    this.human = human;
    this.robot = robot;
    this.humanShips = humanShips;
    this.robotShips = robotShips;
    this.sessionGameBoard = sessionGameBoard;
    this.height = height;
    this.width = width;
  }

  /**
   * Runs the game portion of the BattleSalvo program
   */
  public void run() {

    while (!sessionGameBoard.isGameOver()) {
      view.showOpponentBoard(sessionGameBoard.getRobotBoard(), this.height, this.width);
      view.showMyBoard(sessionGameBoard.getHumanBoard(), this.height, this.width);

      List<Coord> humanShots = human.takeShots();
      List<Coord> robotShots = robot.takeShots();

      sessionGameBoard.hit(sessionGameBoard.getHumanBoard(), robotShots);
      sessionGameBoard.hit(sessionGameBoard.getRobotBoard(), humanShots);

    }
    view.showOpponentBoard(sessionGameBoard.getRobotBoard(), this.height, this.width);
    view.showMyBoard(sessionGameBoard.getHumanBoard(), this.height, this.width);
    view.showResult(sessionGameBoard.getGameResult());
  }
}
