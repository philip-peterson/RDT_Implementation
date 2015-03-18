import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

public class NetworkServerThread extends Thread {
   protected Socket sock;
   protected BufferedReader reader;
   protected DataOutputStream writer;
   protected NetworkServer ns;

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
