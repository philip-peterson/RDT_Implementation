import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

public class NetworkServerSenderThread extends NetworkServerThread {
   public NetworkServerSenderThread(
         Socket sock,
         BufferedReader reader,
         DataOutputStream writer,
         NetworkServer ns
      ) {
      super(sock, reader, writer, ns);
   }

   public void run() {
   }
}
