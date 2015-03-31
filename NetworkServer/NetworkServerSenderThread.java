import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class NetworkServerSenderThread extends NetworkServerThread {
   public NetworkServerSenderThread(
         Socket sock,
         InputStream in,
         OutputStream out,
         NetworkServer ns
      ) {
      super(sock, in, out, ns);
   }

   public void run() {
   }
}
