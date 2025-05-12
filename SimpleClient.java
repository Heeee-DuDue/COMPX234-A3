import java.io.*;
import java.net.*;

public class TupleSpaceClient {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println(x:"Usage: java TupleSpaceClient <host> <port> <request-file>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1])；
        String requestFile = args[2];

        try (
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader fileReader = new BufferedReader(new FileReader(requestFile))
        ) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] parts = line.split(regex:" ", limit:3);
                if (parts.length < 2) {
                    system.err.println("Invalid request: " + line);
                    continue;
                }

                String op = parts[0].toUpperCase();
                String key = parts[1];
                String value = (parts.length > 2) ? parts[2] : "";

                //


                
  
