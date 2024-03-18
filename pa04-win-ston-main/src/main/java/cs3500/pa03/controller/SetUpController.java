package cs3500.pa03.controller;

import cs3500.pa03.model.AIPlayer;
import cs3500.pa03.model.GameBoards;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.SetUpModel;
import cs3500.pa03.model.Ship;
import cs3500.pa03.view.SetUpView;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Controller that is responsible for controlling the setup portion of BattleSalvo
 */
public class SetUpController implements Controller {
  private final SetUpView setUpView = new SetUpView();
  private final SetUpModel setUpModel = new SetUpModel();

  /**
   * Runs the setup portion of the BattleSalvo program
   */
  public void run() {

    ArrayList<Integer> dimensions = setDimensions();
    int height = dimensions.get(0);
    int width = dimensions.get(1);


    int maxNum = Math.min(height, width);

    ArrayList<Integer> fleetCount = setFleetDimensions(maxNum);

    GameBoards sessionGameBoard = new GameBoards(height, width);

    AIPlayer robot = new AIPlayer(height, width, sessionGameBoard);
    List<Ship> robotShips = robot.setup(height, width, setUpModel.setFleet(fleetCount));

    HumanPlayer human = new HumanPlayer(height, width, sessionGameBoard);
    List<Ship> humanShips = human.setup(height, width, setUpModel.setFleet(fleetCount));

    new GameController(human, robot, humanShips, robotShips, sessionGameBoard, height, width).run();
  }

  /**
   * Sets the board dimensions of the game
   *
   * @return the valid dimensions to be used for the game
   */
  public ArrayList<Integer> setDimensions() {
    ArrayList<Integer> dimensions = setUpView.getBoardDimensions();

    if (setUpModel.areValidDimensions(dimensions)) {
      return dimensions;
    } else {
      while (!setUpModel.areValidDimensions(dimensions)) {
        dimensions = setUpView.getBoardDimensionsAgain();
      }
    }
    return dimensions;
  }

  /**
   * Sets the number of ships of the game
   *
   * @param maxNum the maximum number of ships allowed for this game
   * @return the valid count of ships to be used for the game
   */
  public ArrayList<Integer> setFleetDimensions(int maxNum) {

    ArrayList<Integer> fleetCount = setUpView.getFleetNumber(maxNum);

    if (setUpModel.isValidFleet(fleetCount, maxNum)) {
      return fleetCount;
    } else {
      while (!setUpModel.isValidFleet(fleetCount, maxNum)) {
        fleetCount = setUpView.getFleetNumberAgain(maxNum);
      }
    }
    return fleetCount;
  }
}


