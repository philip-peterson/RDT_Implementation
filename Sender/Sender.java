import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class Sender extends GenericClient {

   public void run(String host, int port) {
      super.run(host, port);
   }

   public Sender(String host, int port) {
      super(host, port);
   }

   public static void main(String[] args) {
      if (args.length != 2) {
         System.err.println("Usage: sender HOST PORT");
         System.exit(RC_ERR_USAGE);
      }
      int port = Integer.parseInt(args[1]);
      String host = args[0];
      Sender r = new Sender(host, port);
      r.run(host, port);
   }
}
