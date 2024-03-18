package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Initializes the fleet of ships for this session
 */
public class FleetInitializer {

  /**
   * Randomly places the fleet on the given board
   *
   * @param specifications number of ships by type to be initialized
   * @param board          the board in which the ships will be placed
   * @param height         height of board
   * @param width          width of board
   * @return list of ships
   */
  public ArrayList<Ship> init(Map<ShipType, Integer> specifications,
                              ArrayList<ArrayList<Coord>> board, int height, int width) {
    ArrayList<Ship> ships = new ArrayList<>();
    Random rand = new Random();

    for (int c = 0; c < specifications.get(ShipType.CARRIER); c++) {
      ships.add(new Ship(ShipType.CARRIER,
          randomShipCoords(6, rand.nextInt(0, 2), height, width, board)));
    }
    for (int b = 0; b < specifications.get(ShipType.BATTLESHIP); b++) {
      ships.add(new Ship(ShipType.BATTLESHIP,
          randomShipCoords(5, rand.nextInt(0, 2), height, width, board)));
    }
    for (int d = 0; d < specifications.get(ShipType.DESTROYER); d++) {
      ships.add(new Ship(ShipType.DESTROYER,
          randomShipCoords(4, rand.nextInt(0, 2), height, width, board)));
    }
    for (int s = 0; s < specifications.get(ShipType.SUBMARINE); s++) {
      ships.add(new Ship(ShipType.SUBMARINE,
          randomShipCoords(3, rand.nextInt(0, 2), height, width, board)));
    }

    return ships;
  }

  /**
   * Ensures that the given x and y coordinates are valid by checking that its horizontal
   * trajectory is all empty
   *
   * @param x        x coordinate
   * @param y        y coordinate
   * @param shipSize size of the ship
   * @param board    the board in which the ship will be placed
   * @return is this x and y able to hold a ship of the given size on the given board?
   */
  private boolean validateHorizontal(int x, int y, int shipSize,
                                     ArrayList<ArrayList<Coord>> board) {
    for (int i = 0; i < shipSize; i++) {
      if (board.get(y).get(x + i).getHasShip()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Produces a random location that can hold a ship of the given size horizontally
   *
   * @param shipSize size of the ship
   * @param height   height of board
   * @param width    width of board
   * @param board    the board on which the ship will be placed
   * @return list of random valid Coords that will hold the ship
   */
  private ArrayList<Coord> getRandomHorizontal(int shipSize, int height, int width,
                                               ArrayList<ArrayList<Coord>> board) {
    ArrayList<Coord> horizontal = new ArrayList<Coord>();
    int x = 0;
    if (!(width == shipSize)) {
      x = new Random().nextInt(0, width - shipSize);
    }
    int y = new Random().nextInt(0, height);
    if (validateHorizontal(x, y, shipSize, board)) {
      for (int i = 0; i < shipSize; i++) {
        board.get(y).get(x + i).putShipHere();
        horizontal.add(board.get(y).get(x + i));
      }
      return horizontal;
    } else {
      return getRandomHorizontal(shipSize, height, width, board);
    }
  }

  /**
   * Ensures that the given x and y coordinates are valid by checking that its vertical
   * trajectory is all empty
   *
   * @param x        x coordinate
   * @param y        y coordinate
   * @param shipSize size of the ship
   * @param board    the board in which the ship will be placed
   * @return is this x and y able to hold a ship of the given size on the given board?
   */
  private boolean validateVertical(int x, int y, int shipSize, ArrayList<ArrayList<Coord>> board) {
    for (int i = 0; i < shipSize; i++) {
      if (board.get(y + i).get(x).getHasShip()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Produces a random location that can hold a ship of the given size vertically
   *
   * @param shipSize size of the ship
   * @param height   height of board
   * @param width    width of board
   * @param board    the board on which the ship will be placed
   * @return list of random valid Coords that will hold the ship
   */
  private ArrayList<Coord> getRandomVertical(int shipSize, int height, int width,
                                             ArrayList<ArrayList<Coord>> board) {
    ArrayList<Coord> vertical = new ArrayList<Coord>();
    int x = new Random().nextInt(0, width);
    int y = 0;
    if (!(height == shipSize)) {
      y = new Random().nextInt(0, height - shipSize);
    }
    if (validateVertical(x, y, shipSize, board)) {
      for (int i = 0; i < shipSize; i++) {
        board.get(y + i).get(x).putShipHere();
        vertical.add(board.get(y + i).get(x));
      }
      return vertical;
    } else {
      return getRandomVertical(shipSize, height, width, board);
    }
  }

  /**
   * Produces a random location that can hold a ship of the given size,
   * either vertically or horizontally
   *
   * @param shipSize size of the ship
   * @param height   height of board
   * @param width    width of board
   * @param board    the board on which the ship will be placed
   * @return list of random valid Coords that will hold the ship
   */
  private ArrayList<Coord> randomShipCoords(int shipSize, int horizontal, int height, int width,
                                            ArrayList<ArrayList<Coord>> board) {
    if (horizontal == 1) {
      try {
        return getRandomHorizontal(shipSize, height, width, board);
      } catch (StackOverflowError e) {
        return getRandomVertical(shipSize, height, width, board);
      }
    } else {
      try {
        return getRandomVertical(shipSize, height, width, board);
      } catch (StackOverflowError e) {
        return getRandomHorizontal(shipSize, height, width, board);
      }
    }
  }
}
