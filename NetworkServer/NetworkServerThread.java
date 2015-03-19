import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkServerThread extends Thread {
   protected Socket sock;
   protected BufferedReader reader;
   protected PrintWriter writer;
   protected NetworkServer ns;

   public NetworkServerThread(
         Socket sock,
         BufferedReader reader,
         PrintWriter writer,
         NetworkServer ns
      ) {
      this.sock = sock;
      this.reader = reader;
      this.writer = writer;
      this.ns = ns;
   }
}
