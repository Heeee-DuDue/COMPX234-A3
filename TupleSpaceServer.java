import java.io.*;
import java.net.*;

class HelloWorldServer
{

    public static void main(String[] args) {
        try {
             /* listen on port 51234 for incoming connections */
             ServerSocket ss = new ServerSocket(51234);

             /* loop around, accepting new connections as they arrive */
             while (true) {
                 Socket s = ss.accept();
                 BufferedReader read = 
                     new BufferedReader(new InputStreamReader(s.getInputStream()));
                 PrintWriter write = 
                     new PrintWriter(s.getOutputStream(), true);

                 /* read a line of text, and then echo it back */
                 String in = read.readLine();
                 write.println("You said: " + in);
                 s.close();/* close the socket */
            }
       }
       catch(exception e) {
           System.err.println(e);
       }
    }
}


 


        
