import java.io.*;
import java.net.*;

class SimpleServer
{

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(51234);
        System.out.println("Server started on port 51234");
        // Continuously accepting client connections
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket);

            // Create a new thread for each client
            new Thread(() -> {
                try (
                    BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream())
                    );
                    PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true
                    )
                ) {
                    String request;
                    while ((request = in.readLine()) != null) {
                        // Simple processing request: return the client message as is
                        out.println("Server Response: " + request);
                    }
                } catch (IOException e) {
                    System.err.println("Client error: " + e);
                } finally {
                    try {
                        clientSocket.close();
                    } catch(IOException e) {
                        System.err.printlnq("Failed to close socket: " + e);
                    }
                }
            }).start();
        }
    }
}
