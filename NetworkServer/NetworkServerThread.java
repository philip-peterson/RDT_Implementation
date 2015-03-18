import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

public class NetworkServerThread extends Thread {
   private Socket sock;
   private BufferedReader reader;
   private DataOutputStream writer;
   private NetworkServer ns;

   public NetworkServerThread(
         Socket sock,
         BufferedReader reader,
         DataOutputStream writer,
         NetworkServer ns
      ) {
      this.sock = sock;
      this.reader = reader;
      this.writer = writer;
      this.ns = ns;
   }
}
