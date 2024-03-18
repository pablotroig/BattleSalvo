package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GameResultTest {

  @Test
  public void testEquals() {
    GameResult win = GameResult.WIN;
    GameResult lose = GameResult.LOSE;
    GameResult draw = GameResult.DRAW;
    assertEquals(GameResult.toGameResult("WIN"), win);
    assertEquals(GameResult.toGameResult("LOSE"), lose);
    assertEquals(GameResult.toGameResult("DRAW"), draw);
    assertThrows(IllegalArgumentException.class, () -> GameResult.toGameResult("notResult"));
  }

}