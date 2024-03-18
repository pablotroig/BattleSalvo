package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.client.JsonUtils;
import cs3500.pa04.client.ProxyController;
import cs3500.pa04.json.ControlJson;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.SetUpJson;
import cs3500.pa04.json.TakeShotsJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController pc;


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  public void testSetUpJson() {
    // Prepare sample message

    List<String> toSend = new ArrayList<>();
    FleetJson spec = new FleetJson(6, 0, 0, 0);
    SetUpServerJson sampleSetUp = new SetUpServerJson(6, 6, spec);
    toSend.add(createSampleMessage("setup", sampleSetUp).toString());

    EndGameJson endJson = new EndGameJson("LOSE", "Player returned invalid volley");
    toSend.add(createSampleMessage("end-game", endJson).toString());

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, toSend);

    // Create a Dealer
    try {
      this.pc = new ProxyController(socket, new PrintStream(socket.getOutputStream()));
      //  socket.close();
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    this.pc.run();

    System.out.println(logToString());
    String argument = logToString().substring(logToString().indexOf(","));
    String relevantJson = argument.substring(argument.indexOf("{"));
    responseToClass(SetUpJson.class, relevantJson);
  }

  @Test
  public void testTakeShotsJson() {
    // Prepare sample message

    List<String> toSend = new ArrayList<>();
    FleetJson spec = new FleetJson(6, 0, 0, 0);
    SetUpServerJson sampleSetUp = new SetUpServerJson(6, 6, spec);
    toSend.add(createSampleMessage("setup", sampleSetUp).toString());

    toSend.add("{\"method-name\": \"take-shots\",\"arguments\": {}}");
    EndGameJson endJson = new EndGameJson("LOSE", "Player returned invalid volley");
    toSend.add(createSampleMessage("end-game", endJson).toString());

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, toSend);

    // Create a Dealer
    try {
      this.pc = new ProxyController(socket, new PrintStream(socket.getOutputStream()));
      //  socket.close();
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.pc.run();

    System.out.println(logToString());
    String argument = logToString().substring(logToString().indexOf("]"));
    String relevantJson = argument.substring(argument.indexOf("{\"coordinates"));
    responseToClass(TakeShotsJson.class, relevantJson);
  }

  @Test
  public void testJoinJson() {
    // Prepare sample message

    List<String> toSend = new ArrayList<>();
    FleetJson spec = new FleetJson(6, 0, 0, 0);
    SetUpServerJson sampleSetUp = new SetUpServerJson(6, 6, spec);
    toSend.add(createSampleMessage("setup", sampleSetUp).toString());
    toSend.add("{\n\t\"method-name\": \"join\",\n\t\"arguments\": {}\n}");
    EndGameJson endJson = new EndGameJson("LOSE", "Player returned invalid volley");
    toSend.add(createSampleMessage("end-game", endJson).toString());

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, toSend);

    // Create a Dealer
    try {
      this.pc = new ProxyController(socket, new PrintStream(socket.getOutputStream()));
      //  socket.close();
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.pc.run();

    String relevantJson = logToString().substring(logToString().indexOf("{\"name"));
    String expected = "{\"name\":\"totokang\",\"game-type\":\"SINGLE\"}}\n";
    assertEquals(expected, relevantJson);
  }

  @Test
  public void testReportDamageJson() {
    // Prepare sample message

    List<String> toSend = new ArrayList<>();
    FleetJson spec = new FleetJson(6, 0, 0, 0);
    SetUpServerJson sampleSetUp = new SetUpServerJson(6, 6, spec);
    toSend.add(createSampleMessage("setup", sampleSetUp).toString());

    CoordJson coordJson = new CoordJson(0, 0);
    List<JsonNode> coordinates = new ArrayList<>();
    JsonNode testCoord = JsonUtils.serializeRecord(coordJson);
    coordinates.add(testCoord);
    ReportDamageJson damage = new ReportDamageJson(coordinates);
    toSend.add(createSampleMessage("report-damage", damage).toString());

    EndGameJson endJson = new EndGameJson("LOSE", "Player returned invalid volley");
    toSend.add(createSampleMessage("end-game", endJson).toString());

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, toSend);

    // Create a Dealer
    try {
      this.pc = new ProxyController(socket, new PrintStream(socket.getOutputStream()));
      //  socket.close();
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    this.pc.run();

    String getRidOfSetUpJson = logToString().substring(logToString().indexOf("]"));
    String relevantJson = getRidOfSetUpJson.substring(getRidOfSetUpJson.indexOf("{"));
    String expected =
        "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\":[{\"x\":0,\"y\":0}]}}\n";
    assertEquals(expected, relevantJson);
  }

  @Test
  public void testHandleSuccessfulHits() {
    // Prepare sample message

    List<String> toSend = new ArrayList<>();
    FleetJson spec = new FleetJson(6, 0, 0, 0);
    SetUpServerJson sampleSetUp = new SetUpServerJson(6, 6, spec);
    toSend.add(createSampleMessage("setup", sampleSetUp).toString());

    CoordJson coordJson = new CoordJson(0, 0);
    List<JsonNode> coordinates = new ArrayList<>();
    JsonNode testCoord = JsonUtils.serializeRecord(coordJson);
    coordinates.add(testCoord);
    ReportDamageJson successfulHits = new ReportDamageJson(coordinates);
    toSend.add(createSampleMessage("successful-hits", successfulHits).toString());

    EndGameJson endJson = new EndGameJson("LOSE", "Player returned invalid volley");
    toSend.add(createSampleMessage("end-game", endJson).toString());

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, toSend);

    // Create a Dealer
    try {
      this.pc = new ProxyController(socket, new PrintStream(socket.getOutputStream()));
      //  socket.close();
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    this.pc.run();

    String getRidOfSetUpJson = logToString().substring(logToString().indexOf("]"));
    String relevantJson = getRidOfSetUpJson.substring(getRidOfSetUpJson.indexOf("{"));
    String expected = "{\"method-name\":\"successful-hits\",\"arguments\":\"void\"}\n";
    assertEquals(expected, relevantJson);
  }

  @Test
  public void testErrorsInRun() {
    // Prepare sample message

    List<String> toSend = new ArrayList<>();
    FleetJson spec = new FleetJson(6, 0, 0, 0);
    SetUpServerJson sampleSetUp = new SetUpServerJson(6, 6, spec);
    toSend.add(createSampleMessage("setup", sampleSetUp).toString());

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, toSend);

    // Create a Dealer
    try {
      this.pc = new ProxyController(socket, new PrintStream(socket.getOutputStream()));
      //  socket.close();
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }


    assertThrows(RuntimeException.class, () -> this.pc.run());
  }

  @Test
  public void testErrorsInMessageDelegation() {
    // Prepare sample message

    List<String> toSend = new ArrayList<>();
    FleetJson spec = new FleetJson(6, 0, 0, 0);
    SetUpServerJson sampleSetUp = new SetUpServerJson(6, 6, spec);
    toSend.add(createSampleMessage("setup", sampleSetUp).toString());

    CoordJson coordJson = new CoordJson(0, 0);
    List<JsonNode> coordinates = new ArrayList<>();
    JsonNode testCoord = JsonUtils.serializeRecord(coordJson);
    coordinates.add(testCoord);
    ReportDamageJson successfulHits = new ReportDamageJson(coordinates);
    toSend.add(createSampleMessage("successful=hits", successfulHits).toString());

    EndGameJson endJson = new EndGameJson("WIN", "You sunk all ships");
    toSend.add(createSampleMessage("end-game", endJson).toString());

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, toSend);

    // Create a Dealer
    try {
      this.pc = new ProxyController(socket, new PrintStream(socket.getOutputStream()));
      //  socket.close();
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response

    assertThrows(IllegalStateException.class, () -> this.pc.run());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef,
                                   String message) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(message);
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    ControlJson controlJson =
        new ControlJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(controlJson);
  }
}
