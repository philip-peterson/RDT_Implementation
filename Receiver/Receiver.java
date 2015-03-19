import java.net.*;
import java.io.*;

public class Receiver {
   /* Return codes */
   public static int RC_ERR_USAGE = 1;
   public static int RC_ERR_IO = 2;
   public static int RC_ERR_UNKNOWN_HOST = 3;

   public void run(String host, int port) {
      try {
         Socket clientSocket = new Socket(host, port);
      }
      catch (UnknownHostException e) {
         System.err.println("Error: Unknown host. Exiting.");
         System.exit(RC_ERR_UNKNOWN_HOST);
      }
      catch (IOException e) {
         this.ioError();
      }
   }

   private String host;
   private int port;

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
