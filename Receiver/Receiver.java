import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class Receiver extends GenericClient {

   /* private vars */
   private String host;
   private int port;

   public void run(String host, int port) {
      Socket clientSocket = null;
      try {
         clientSocket = new Socket(host, port);
      }
      catch (UnknownHostException e) {
         ExitCodes.ExitWithMessage(ExitCodes.UNKNOWN_HOST);
      }
      catch (IOException e) {
         ExitCodes.ExitWithMessage(ExitCodes.SOCKIO);
      }

      try {
         out.write(ProgramCodes.RECEIVER);
      }
      catch (IOException e) {
         ExitCodes.ExitWithMessage(ExitCodes.SOCKIO);
      }

   }

   public Receiver(String host, int port) {
      super(host, port);
   }

   public static void main(String[] args) {
      if (args.length != 2) {
         System.err.println("Usage: receiver HOST PORT");
         System.exit(ExitCodes.USAGE);
      }
      int port = Integer.parseInt(args[1]);
      String host = args[0];
      Receiver r = new Receiver(host, port);
      r.run(host, port);
   }
}
