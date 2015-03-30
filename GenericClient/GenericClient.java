import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class GenericClient {
   /* Return codes */
   public static int RC_ERR_USAGE = 1;
   public static int RC_ERR_IO = 2;
   public static int RC_ERR_UNKNOWN_HOST = 3;

   /* protected vars */
   protected String host;
   protected int port;
   protected PrintWriter out;
   protected BufferedReader in;

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

   public void ioError() {
      System.err.println("Error: IO error. Exiting.");
      System.exit(RC_ERR_IO);
   }

   public GenericClient(String host, int port) {
      this.port = port;
      this.host = host;
   }
}
