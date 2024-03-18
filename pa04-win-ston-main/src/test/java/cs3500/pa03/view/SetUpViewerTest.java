package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class SetUpViewerTest {

  SetUpView test = new SetUpView();

  @Test
  public void testGetBoardDimensions() {
    System.setIn(new ByteArrayInputStream(
        ("6\n6").getBytes()));

    assertEquals(new ArrayList<>(Arrays.asList(6, 6)), test.getBoardDimensions());
  }

  @Test
  public void testGetBoardDimensionsAgain() {
    System.setIn(new ByteArrayInputStream(
        ("8\n8").getBytes()));

    assertEquals(new ArrayList<>(Arrays.asList(8, 8)), test.getBoardDimensionsAgain());
  }

  @Test
  public void testGetFleetNumber() {
    System.setIn(new ByteArrayInputStream(
        ("2\n1\n0\n1").getBytes()));

    assertEquals(new ArrayList<>(Arrays.asList(2, 1, 0, 1)), test.getFleetNumber(6));
  }

  @Test
  public void testGetFleetNumberAgain() {
    System.setIn(new ByteArrayInputStream(
        ("2\n1\n0\n1").getBytes()));

    assertEquals(new ArrayList<>(Arrays.asList(2, 1, 0, 1)), test.getFleetNumberAgain(6));
  }

}