import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class NetworkServerThread extends Thread {
   protected Socket sock;
   protected InputStream in;
   protected OutputStream out;
   protected NetworkServer ns;

   public NetworkServerThread(
         Socket sock,
         InputStream in,
         OutputStream out,
         NetworkServer ns
      ) {
      this.sock = sock;
      this.in = in;
      this.out = out;
      this.ns = ns;
   }
}
