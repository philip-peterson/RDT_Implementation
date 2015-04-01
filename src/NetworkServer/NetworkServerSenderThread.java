import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

public class NetworkServerSenderThread extends NetworkServerThread {
   private Random r = new Random();

   public NetworkServerSenderThread(
         Socket sock,
         InputStream in,
         OutputStream out,
         NetworkServer ns
      ) {
      super(sock, in, out, ns);
   }

   void run2() throws IOException {
      while (true) {
         int code = Util.unsignedToSigned(in.read());
         if (code == -1) {
            ns.wantsExit = true;
            sock.close();
            return;
         }

         Packet p = Packet.readFromStream(in);
         double rand = r.nextDouble();
         if (true || rand < .5) {
            // PASS -- send it on through
            System.out.println("Will PASS");
            ns.packetQueue.add(p);
         }
         else if (rand < .75) {
            // CORRUPT
            System.out.println("Will CORRUPT");
            p.corruptify();
            ns.packetQueue.add(p);
         }
         else {
            // DROP -- pretend it got dropped by sending a timeout packet.
            System.out.println("Will DROP");
            Ack timeout = new Ack((byte)2);
            ns.ackQueue.add(timeout);
         }
         
      }
   }

   public void run() {
      try {
         run2();
      }
      catch (IOException e) {
         ExitCodes.ExitWithMessage(ExitCodes.SOCKIO);
      }
   }
}
