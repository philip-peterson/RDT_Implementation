import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

public class Sender extends GenericClient {

   public Path path;

   public Sender(String host, int port, Path path) {
      super(host, port);
      this.path = path;
   }

   int numWords = 0;
   int sentWords = 0;

   public void run() {
      super.run();
      
      try {
         sock.setSoTimeout(2000);
      }
      catch (SocketException e) {
         ioError();
      }

      try {
         out.write(ProgramCodes.SENDER);
         out.flush();
      }
      catch (IOException e) {
         ExitCodes.ExitWithMessage(ExitCodes.SOCKIO);
      }

      String s = null;

      try {
         s = new String(Files.readAllBytes(path));
      }
      catch (IOException e) {
         ExitCodes.ExitWithMessage(ExitCodes.INPUTIO);
      }

      String[] words = s.split("\\s+");
      numWords = words.length;

      if (!words[numWords-1].endsWith(".")) {
         // All message sequences need to end with a period, per
         // specification.
         words[numWords-1] += ".";
      }

      try {
         for (byte i = 0; i < numWords; i++) {
               rdt_send(words[i], i);
         }
         out.write(Util.signedToUnsigned(-1)); // We want to QUIT now. :)
         out.flush();
      }
      catch (IOException e) {
         ioError();
      }

      System.out.println("Exiting successfully.");
   }

   private Ack rcv() throws IOException {
      Ack rcvpkt = Ack.readFromStream(in);
      _numPackets++;

      String nextAction = "";
      if (rcvpkt.isTimeoutAck() || rcvpkt.isCorrupt()) {
         nextAction = "resend Packet" + (rcvpkt.seq);
      }
      else if (sentWords == numWords) {
         nextAction = "no more packets to send";
      }
      else {
         nextAction = "send Packet"+(1-rcvpkt.seq);
      }

      String summary = "";
      if (rcvpkt.isTimeoutAck()) {
         summary = "DROP";
      }
      else if(rcvpkt.isCorrupt()) {
         summary = "CORRUPT";
      }
      else {
         summary = "ACK"+rcvpkt.seq;
      }

      System.out.println("Waiting ACK"+_curSeq+", " + _numPackets + ", " + summary + ", " + nextAction);

      return rcvpkt;
   }

   private int _numPackets = 0;
   private int _curSeq = 0;

   private void rdt_send(String data, byte id) throws IOException {
      Packet sndpkt = new Packet((byte)_curSeq, id, data);

      Ack rcvpkt;
      while (true) {
         out.write(Util.signedToUnsigned(0)); // Indicate we do not wish to QUIT
         sndpkt.writeToStreamAndFlush(out);

         try {
            rcvpkt = rcv();
         }
         catch (SocketTimeoutException e) {
            // Timed out, resending
            continue;
         }

         if (rcvpkt.isCorrupt()) {
            // Received corrupt ACK. resending...
            continue;
         }
         else if (rcvpkt.seq == 1-_curSeq) {
            //System.out.println("Received ACK"+(1-_curSeq)+" when I was expecting an ACK"+_curSeq+". resending...");
            continue;
         }
         else if (rcvpkt.isTimeoutAck()) {
            //System.out.println("#" + id + " timed out, resending.");
            continue;
         }
         else {
            //System.out.println("#" + id + " sent successfully.");
            break;
         }
      }
      _curSeq = 1-_curSeq;
      sentWords++;
   }

   public static void main(String[] args) {
      if (args.length != 3) {
         System.err.println("Usage: sender HOST PORT FILE");
         System.exit(ExitCodes.USAGE);
      }
      int port = Integer.parseInt(args[1]);
      String host = args[0];
      Path path = FileSystems.getDefault().getPath(args[2]);
      Sender r = new Sender(host, port, path);
      r.run();
   }
}
