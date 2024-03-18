package cs3500.pa04;

import cs3500.pa03.controller.SetUpController;
import cs3500.pa04.client.ProxyController;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      new SetUpController().run();
    } else {
      try {
        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        PrintStream sendToServer = new PrintStream(socket.getOutputStream());
        ProxyController proxy = new ProxyController(socket, sendToServer);
        proxy.run();
      } catch (IOException e) {
        System.out.println("Invalid host and port");
      }
    }
  }
}
