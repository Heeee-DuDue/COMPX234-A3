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

    
    

    
