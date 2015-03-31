import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class GenericClient {

   /* protected vars */
   protected String host;
   protected int port;
   protected OutputStream out;
   protected InputStream in;

   public void run(String host, int port) {
      Socket clientSocket = null;
      try {
         clientSocket = new Socket(host, port);
      }
      catch (UnknownHostException e) {
         System.err.println("Error: Unknown host. Exiting.");
         System.exit(ExitCodes.UNKNOWN_HOST);
      }
      catch (IOException e) {
         this.ioError();
      }

      try {
         out = clientSocket.getOutputStream();
         in = clientSocket.getInputStream();
      }
      catch (IOException e) {
         this.ioError();
      }

   }

   public void ioError() {
      ExitCodes.ExitWithMessage(ExitCodes.SOCKIO);
   }

   public GenericClient(String host, int port) {
      this.port = port;
      this.host = host;
   }
}
