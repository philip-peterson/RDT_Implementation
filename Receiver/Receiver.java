import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class Receiver {
   /* Return codes */
   public static int RC_ERR_USAGE = 1;
   public static int RC_ERR_IO = 2;
   public static int RC_ERR_UNKNOWN_HOST = 3;

   /* private vars */
   private String host;
   private int port;
   private PrintWriter out;
   private BufferedReader in;

   public void run(String host, int port) {
      Socket clientSocket = null;
      try {
         clientSocket = new Socket(host, port);
      }
      catch (UnknownHostException e) {
         System.err.println("Error: Unknown host. Exiting.");
         System.exit(RC_ERR_UNKNOWN_HOST);
      }
      catch (IOException e) {
         this.ioError();
      }

      try {
         out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), Charset.forName("UTF-8")), true);
         in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), Charset.forName("UTF-8")));
         out.println("receiver");
      }
      catch (IOException e) {
         this.ioError();
      }

   }

   public Receiver(String host, int port) {
      this.port = port;
      this.host = host;
   }

   public void ioError() {
      System.err.println("Error: IO error. Exiting.");
      System.exit(RC_ERR_IO);
   }

   public static void main(String[] args) {
      if (args.length != 2) {
         System.err.println("Usage: receiver HOST PORT");
         System.exit(RC_ERR_USAGE);
      }
      int port = Integer.parseInt(args[1]);
      String host = args[0];
      Receiver r = new Receiver(host, port);
      r.run(host, port);
   }
}
