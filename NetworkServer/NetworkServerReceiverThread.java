import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkServerReceiverThread extends NetworkServerThread {
   public NetworkServerReceiverThread(
         Socket sock,
         InputStream reader,
         OutputStream writer,
         NetworkServer ns
      ) {
      super(sock, in, out, ns);
   }

   public void run() {

   }
}
