import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class NetworkServerReceiverThread extends NetworkServerThread {
   public NetworkServerReceiverThread(
         Socket sock,
         InputStream in,
         OutputStream out,
         NetworkServer ns
      ) {
      super(sock, in, out, ns);
   }

   Packet rdt_rcv() throws IOException {
      return Packet.readFromStream(in);
   }

   void run2() throws IOException {
      int code = in.read();
      if (code == -1) {
         // exit
      }

      Packet rcvpkt;
      rcvpkt = rdt_rcv();
   }

   public void run() {
      try {
         run2();
      }
      catch (IOException e) {
         System.err.println("I/O error.");
         System.exit(ExitCodes.SOCKIO);
      }
   }
}
