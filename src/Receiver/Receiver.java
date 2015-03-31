import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class Receiver extends GenericClient {

   /* private vars */
   private String host;

   public Receiver(String host, int port) {
      super(host, port);
   }

   public void run() {
      super.run();

      try {
         out.write(ProgramCodes.RECEIVER);
         out.flush();
      }
      catch (IOException e) {
         ExitCodes.ExitWithMessage(ExitCodes.SOCKIO);
      }

      try {
         while(true) {
            Packet rcvpkt;
            rcvpkt = rdt_rcv();
            System.out.println("Got packet "+rcvpkt.seq+"!!");
         }
      }
      catch (IOException e) {
         ioError();
      }

   }

   private int _curSeq = 0;
   Packet rdt_rcv() throws IOException {
      Packet pkt;
      while(true) {
         int wantsExit = Util.unsignedToSigned(in.read());
         if (wantsExit == -1) {
            System.out.println("Exiting successfully.");
            System.exit(0);
         }
         pkt = Packet.readFromStream(in);
         if (!pkt.isCorrupt()) {
            System.out.println("Received corrupt Packet (seq="+_curSeq+"). Waiting...");
         }
         else {
            Ack ack = new Ack(pkt.seq);
            _curSeq = 1 - pkt.seq;
            out.write(Util.signedToUnsigned(0)); // indicate we do not want to QUIT
            ack.writeToStream(out);
            break;
         }
      }
      return pkt;
   }

   public static void main(String[] args) {
      if (args.length != 2) {
         System.err.println("Usage: receiver HOST PORT");
         System.exit(ExitCodes.USAGE);
      }
      int port = Integer.parseInt(args[1]);
      String host = args[0];
      Receiver r = new Receiver(host, port);
      r.run();
   }
}
