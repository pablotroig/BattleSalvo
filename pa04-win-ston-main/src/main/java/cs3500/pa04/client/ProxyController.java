package cs3500.pa04.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.AIPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameBoards;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.json.ControlJson;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.SetUpJson;
import cs3500.pa04.json.SetUpReaderJson;
import cs3500.pa04.json.ShipJson;
import cs3500.pa04.json.TakeShotsJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a controller that utilizes the proxy pattern to communicate with the server
 */
public class ProxyController {

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");
  private final ObjectMapper mapper = new ObjectMapper();
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private Player player;

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param ps the printstream the controller is communicating through
   * @throws IOException if the server disconnects or there is a malformed JSON
   */
  public ProxyController(Socket server, PrintStream ps) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = ps;
  }

  /**
   * Listens for messages from the server as JSON in the format of a ControlJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        ControlJson message = parser.readValueAs(ControlJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      throw new RuntimeException("The game has run into an error. The server is either closed "
          + "or experiencing issues communicating between players.");
    }
  }

  /**
   * Delegates the ControlJson message to its correct helper method
   *
   * @param message the ControlJson that needs to be handled
   */
  private void delegateMessage(ControlJson message) {
    String name = message.methodName();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      handleStart();
    } else if ("setup".equals(name)) {
      handleSetUp(arguments);
    } else if ("take-shots".equals(name)) {
      handleShots();
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }


  /**
   * Communicates with the server to properly join the BattleSalvo game
   */
  private void handleStart() {
    JoinJson joinResponse = new JoinJson("totokang", "SINGLE");
    this.out.println(JsonUtils.serializedControlJson(joinResponse, "join"));
  }

  /**
   * Handles setting up the BattleSalvo game by communicating a list of ships with their locations
   * to the server when given the board specifications
   *
   * @param arguments specifications for the board given by the server
   */
  private void handleSetUp(JsonNode arguments) {

    SetUpReaderJson setUpArgs = this.mapper.convertValue(arguments, SetUpReaderJson.class);

    // extract height and width of board
    int height = setUpArgs.height();
    int width = setUpArgs.width();

    // instantiate player
    this.player = new AIPlayer(height, width, new GameBoards(height, width));

    // extract ship specifications
    Map<ShipType, Integer> specs = setUpArgs.fleet().generateFleetMap();

    // instantiate ships at random locations
    List<Ship> myShips = player.setup(height, width, specs);

    List<JsonNode> fleet = new ArrayList<>();

    // parse ships into JSON
    for (int i = 0; i < myShips.size(); i++) {
      ArrayList<Coord> testLocation = myShips.get(i).getLocation();
      CoordJson firstCoord = new CoordJson(testLocation.get(0).getX(), testLocation.get(0).getY());
      ShipJson currShip =
          new ShipJson(firstCoord, testLocation.size(), checkOrientation(testLocation));
      JsonNode shipToNode = JsonUtils.serializeRecord(currShip);
      fleet.add(shipToNode);
    }

    SetUpJson setUpJson = new SetUpJson(fleet);
    this.out.println(JsonUtils.serializedControlJson(setUpJson, "setup"));
  }

  /**
   * Checks the orientation of a ship given its coordinates
   *
   * @param location coordinates that a ship occupies
   * @return whether the ship is horizontal of vertical
   */
  private String checkOrientation(ArrayList<Coord> location) {
    int firstX = location.get(0).getX();
    int nextX = location.get(1).getX();
    if (firstX == nextX) {
      return "VERTICAL";
    } else {
      return "HORIZONTAL";
    }
  }

  /**
   * Prompts the AI player to take shots
   */
  private void handleShots() {
    List<Coord> shots = player.takeShots();
    List<JsonNode> coordList = new ArrayList<>();
    for (int i = 0; i < shots.size(); i++) {
      CoordJson curCoord = new CoordJson(shots.get(i).getX(), shots.get(i).getY());
      JsonNode coordToNode = JsonUtils.serializeRecord(curCoord);
      coordList.add(coordToNode);
    }
    TakeShotsJson takeShots = new TakeShotsJson(coordList);
    this.out.println(JsonUtils.serializedControlJson(takeShots, "take-shots"));
  }

  /**
   * Given a list of shots the opponent took on the player's board from the server, communicate
   * which shots have actually hit ships on the player's board
   *
   * @param arguments list of shots the opponent took on the player's board
   */
  private void handleReportDamage(JsonNode arguments) {
    ReportDamageJson reportDamageArgs = this.mapper.convertValue(arguments, ReportDamageJson.class);
    List<JsonNode> incomingShots = reportDamageArgs.coordinates();
    List<Coord> readableIncomingShots = new ArrayList<Coord>();

    for (JsonNode js : incomingShots) {
      CoordJson currCoord = this.mapper.convertValue(js, CoordJson.class);
      int x = currCoord.x();
      int y = currCoord.y();
      Coord readableCoord = new Coord(x, y);
      readableIncomingShots.add(readableCoord);
    }
    List<Coord> shotsThatHitShips = player.reportDamage(readableIncomingShots);
    List<JsonNode> outgoingReportDamage = new ArrayList<>();
    for (Coord c : shotsThatHitShips) {
      CoordJson currCoord = new CoordJson(c.getX(), c.getY());
      JsonNode coordToNode = JsonUtils.serializeRecord(currCoord);
      outgoingReportDamage.add(coordToNode);
    }
    ReportDamageJson damageJson = new ReportDamageJson(outgoingReportDamage);
    this.out.println(JsonUtils.serializedControlJson(damageJson, "report-damage"));
  }

  /**
   * Receives from the server which of the player's shots successfuly hit opponent's ships
   *
   * @param arguments shots that successfully hit opponent's ships
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    ReportDamageJson reportDamageArgs = this.mapper.convertValue(arguments, ReportDamageJson.class);
    List<JsonNode> incomingShots = reportDamageArgs.coordinates();
    List<Coord> readableIncomingShots = new ArrayList<Coord>();

    for (JsonNode js : incomingShots) {
      CoordJson currCoord = this.mapper.convertValue(js, CoordJson.class);
      int x = currCoord.x();
      int y = currCoord.y();
      Coord readableCoord = new Coord(x, y);
      readableIncomingShots.add(readableCoord);
    }
    player.successfulHits(readableIncomingShots);
    ControlJson response = new ControlJson("successful-hits", VOID_RESPONSE);
    JsonNode finalResponse = JsonUtils.serializeRecord(response);
    this.out.println(finalResponse);
  }

  /**
   * Handles the ending of the game
   *
   * @param arguments Result of the game and its reason
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson endingArgs = this.mapper.convertValue(arguments, EndGameJson.class);
    String result = endingArgs.result();
    String reason = endingArgs.reason();
    player.endGame(GameResult.toGameResult(result), reason);
    try {
      server.close();
    } catch (IOException e) {
      throw new RuntimeException("The server was unable to close.");
    }
  }


}
