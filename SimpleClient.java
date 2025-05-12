import java.io.*;
import java.net.*;

public class SimpleClient {
  public static void main(String[] args) throws IOExceptions {
    Socket socket = new Socket ("localhost", 51234);
    System.out.println("Connected to server.");

    try (
      BufferedReader in = new BufferedReader(
        new inputStreamReader(socket.getOutputStream(), true);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader console = new BufferedReader(
        new InputStreamReader(System.in)
      )
    ){
      String userInput;
      while ((userInput = console.readLine()) != null) {
        out.println(userInput); // Send a message to the server
        String response = in.readLine(); // Receive server response
        System.out.println("Server:" + response);
      }
    }
  }
}
