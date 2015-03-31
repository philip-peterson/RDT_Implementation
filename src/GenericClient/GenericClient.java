import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class GenericClient {

   /* protected vars */
   protected String host;
   protected int port;
   protected OutputStream out;
   protected InputStream in;
   protected Socket sock;

   public void run() {
      sock = null;

      try {
         sock = new Socket(host, port);
      }
      catch (UnknownHostException e) {
         ExitCodes.ExitWithMessage(ExitCodes.UNKNOWN_HOST);
      }
      catch (IOException e) {
         this.ioError();
      }

      try {
         out = sock.getOutputStream();
         in = sock.getInputStream();
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
