import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkServerSenderThread extends NetworkServerThread {
   public NetworkServerSenderThread(
         Socket sock,
         InputStream in,
         OutputStream out,
         NetworkServer ns
      ) {
      super(sock, reader, writer, ns);
   }

   public void run() {
   }
}
