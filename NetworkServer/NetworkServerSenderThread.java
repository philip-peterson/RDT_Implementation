import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkServerSenderThread extends NetworkServerThread {
   public NetworkServerSenderThread(
         Socket sock,
         BufferedReader reader,
         PrintWriter writer,
         NetworkServer ns
      ) {
      super(sock, reader, writer, ns);
   }

   public void run() {
   }
}
