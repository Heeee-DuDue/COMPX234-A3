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
                if (parts.length < 2) {x`
                    system.err.println("Invalid request: " + line);
                    continue;
                }

                String op = parts[0].toUpperCase();
                String key = parts[1];
                String value = (parts.length > 2) ? parts[2] : "";

                // Construct protocol messages
                String protocolOp;
                String requestBody;
                switch (op) {
                    case "PUT" :
                        protocolOp = "P";
                        requestBody = protocolOp + " " + key + " " + value;
                        break;
                    case "GET" :
                        protocolOp = "G";
                        requestBody = protocolOp + " " + key;
                        break;
                    case "READ" :
                        protocolOp = "R";
                        requestBody = protocolOp + " " + key;
                        break;
                    default:
                        System.err.println("Unknown operation: " + op);
                        continue;
                }

                // Calculate message length (NNN) 
                int length = requestBody.length() + 4; //3-digit length+space
                String formattedRequest = String.format("%03d %s", length, requestBody);

                // Send requests and receive responses
                out.println(formattedRequest);
                String response = in.readline();
                System.out.println(line + ": " + response);
            }
        } catch (ConnectException e) {
            System.err.println("Failed to connect to server:" + e.getMassage());
        } catch (FileNotFoundException e) {
            System.err.println("Request file not found: " + requestFile);
        } catch (IOException e) {
            System.err.println("IO Exception: " + e);
            


                
  
