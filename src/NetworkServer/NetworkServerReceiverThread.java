import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

public class NetworkServerReceiverThread extends NetworkServerThread {
   public NetworkServerReceiverThread(
         Socket sock,
         InputStream in,
         OutputStream out,
         NetworkServer ns
      ) {
      super(sock, in, out, ns);
   }

   private Random r = new Random();

   void run2() throws IOException {
      while(true) {
         Ack ack = Ack.readFromStream(in);
         System.out.print("Received: ACK"+ack.seq+", ");
         double rand = r.nextDouble();
         if (rand < .5) {
            // PASS -- send it on through
            System.out.println("PASS");
            ns.ackQueue.add(ack);
         }
         else if (rand < .75) {
            // CORRUPT
            System.out.println("CORRUPT");
            ack.corruptify();
            ns.ackQueue.add(ack);
         }
         else {
            // DROP -- pretend it got lost (do nothing!)
            System.out.println("DROP");
         }
      }
   }

   public void run() {
      try {
         run2();
      }
      catch (SocketException e) {
         if (!ns.wantsExit) {
            ExitCodes.ExitWithMessage(ExitCodes.SOCKBROKEN);
         }
      }
      catch (IOException e) {
         ExitCodes.ExitWithMessage(ExitCodes.SOCKIO);
      }
   }
}
