package cs3500.pa03.view;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a View of the MVC pattern. Deals with interacting with the user during the setup
 */
public class SetUpView {

  /**
   * Retrieves the user's desired board dimension
   *
   * @return board dimension for this session
   */
  public ArrayList<Integer> getBoardDimensions() {
    ArrayList<Integer> dimensions = new ArrayList<Integer>();
    Scanner s = new Scanner(System.in);
    System.out.println("\n----------------------------------------\n"
        + "Hi! Let's play BattleSalvo. Enter your desired board's height and width\n"
        + "below (keep in mind, the board dimensions must be between 6 and 10 inclusive):\n"
        + "---------------------------------------");
    dimensions.add(s.nextInt());
    dimensions.add(s.nextInt());
    return dimensions;
  }

  /**
   * Retrieves the board dimension from user when the user previously entered invalid dimensions
   *
   * @return valid board dimensions for the session
   */
  public ArrayList<Integer> getBoardDimensionsAgain() {
    ArrayList<Integer> dimensions = new ArrayList<Integer>();
    Scanner s = new Scanner(System.in);
    System.out.println("\nYou've entered invalid dimensions. Enter a dimension within 6 and 10:\n"
        + "---------------------------------------");
    dimensions.add(s.nextInt());
    dimensions.add(s.nextInt());
    return dimensions;
  }

  /**
   * Retrieves the fleet dimension from user when the user previously entered invalid dimensions
   *
   * @param maxSize maximum number of fleet the user can have
   * @return valid fleet dimensions for the session
   */
  public ArrayList<Integer> getFleetNumberAgain(int maxSize) {
    ArrayList<Integer> fleet = new ArrayList<Integer>();
    Scanner s = new Scanner(System.in);
    System.out.println(
        "\nYou've entered invalid dimensions. Let's try again."
            + "\nPlease enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]."
            + "\nRemember, your fleet may not exceed size " + maxSize
            + " and there must be at least one ship of each type"
            + "\n---------------------------------------");
    fleet.add(s.nextInt());
    fleet.add(s.nextInt());
    fleet.add(s.nextInt());
    fleet.add(s.nextInt());
    return fleet;
  }

  /**
   * Retrieves the fleet dimension from user
   *
   * @param maxSize maximum size of fleet the user can have
   * @return fleet dimensions for the session
   */
  public ArrayList<Integer> getFleetNumber(int maxSize) {
    ArrayList<Integer> fleet = new ArrayList<Integer>();
    Scanner s = new Scanner(System.in);
    System.out.println(
        "\nPlease enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
            + "Remember, your fleet may not exceed size " + maxSize
            + "\n---------------------------------------");
    fleet.add(s.nextInt());
    fleet.add(s.nextInt());
    fleet.add(s.nextInt());
    fleet.add(s.nextInt());
    return fleet;
  }

}
