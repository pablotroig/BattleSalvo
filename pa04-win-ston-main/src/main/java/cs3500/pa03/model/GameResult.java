package cs3500.pa03.model;

/**
 * Represents a result, which can be a WIN, LOSE, or TIE
 */
public enum GameResult {
  WIN, LOSE, DRAW;

  /**
   * Converts a string into a GameResult
   *
   * @param result the String representation of a result name
   * @return the GameResult equivalent of the given String result
   */
  public static GameResult toGameResult(String result) {
    if (result.equals(GameResult.WIN.toString())) {
      return GameResult.WIN;
    } else if (result.equals(GameResult.LOSE.toString())) {
      return GameResult.LOSE;
    } else if (result.equals(GameResult.DRAW.toString())) {
      return GameResult.DRAW;
    } else {
      throw new IllegalArgumentException("invalid result name");
    }
  }
}
