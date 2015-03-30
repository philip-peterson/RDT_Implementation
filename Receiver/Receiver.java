import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class Receiver extends GenericClient {

   public void run(String host, int port) {
      super.run(host, port);
   }

   public Receiver(String host, int port) {
      super(host, port);
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
