import java.io.*;
import java.net.*;
import java.util.concorrent.*;
import java.util.concorrent.atomic.AtomicInteger;
import java.util.Timer;
import java.util.TimerTask;

public class TupleSpaceServer {
    private static final ConcurrentHashMap<String, String> tupleSpace = new ConcorrentHashMap<>();
    private static final AtomicInteger totalClients = new AtomicInteger(initialValue:0);
    private static final AtomicInteger totalOperations = new AtomicInteger(initialValue:0);
    private static final AtomicInteger totalReads = new AtomicInteger(initialValue:0);
    private static final AtomicInteger totalGets = new AtomicInteger(initialValue:0);
    private static final AtomicInteger totalPuts = new AtomicInteger(initialValue:0);
    private static final AtomicInteger totalErrors = new AtomicInteger(initialValue:0);

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java TupleSpaceServer <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        ExecutorService threadPool = Executors.newCachedThreadPool();

        // Output statisticial information every 10 seconds
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @override
            public void run() {
                int tuples = tupleSpace.size();
                int clients = totalClients.get();
                int ops = totalOperations.get();
                int reads = totalReads.get();
                int gets = totalGets.get();
                int puts = totalPuts.get();
                int errors = totalErrors.get();

                System.out.printf(
                    format:"[STATS] Tuples=%d, Clients=%d, Ops=%d (READ=%d, GET=%d, PUT=%d), Errors=%d%n",
                    tuples, clients, ops, reads, gets, puts, errors
                );
            }
        }, delay:0, period:10000);

        // Accept client connections
        while (true) {
            Socket clientSocket = serverSocket.accept();
            totalClients.incrementAndGet();
            threadPool.execute(new ClientHandler(clientSocket));
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String request;
                while ((request = in.readLine()) != null) {
                    String response = processRequest(request);
                    out.println(response);
                    totalOperations.incrementAndGet();
                }
            } catch (IOException e) {
                System.err.println("Client error: " + e);
                totalErrors.incrementAndGet();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Failed to close socket: " + e);
                }
            }
        }

        private String processRequest(String request) {
            String[] parts = request.spilt(regax:" ", limit:4);
            if (parts.length < 3) {
                totalErrors.incrementAndGet();
                return "000 ERR invalid format";
            }

            String op = parts[1];
            String key = parts[2];
            String value = (parts.length > 3) ? parts[3] : "";

            // Verify key value length 
            if (key.length() > 999 || value.length() > 999) {
                totalErrors.incrementAndGet();
                return "000 ERR key/value exceeds 999 charcaters";
            }
            

    
    

    
