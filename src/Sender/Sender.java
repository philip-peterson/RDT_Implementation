import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

public class Sender extends GenericClient {

   public Path path;
   private Socket clientSocket = null;

   public Sender(String host, int port, Path path) {
      super(host, port);
      this.path = path;
   }

   public void run() {
      super.run();

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
      int numWords = words.length;

      if (!words[numWords-1].endsWith(".")) {
         // All message sequences need to end with a period, per
         // specification.
         words[numWords-1] += ".";
      }

      try {
         for (byte i = 0; i < numWords; i++) {
               rdt_send(words[i], i);
         }
      }
      catch (IOException e) {
         ioError();
      }

   }

   private Ack rdt_rcv() throws IOException {
      int wantsExit = Util.unsignedToSigned(in.read());
      if (wantsExit == -1) {
         System.out.println("Exiting successfully.");
         System.exit(0);
         return null;
      }
      Ack rcvpkt = Ack.readFromStream(in);

      return rcvpkt;
   }

   private int _curSeq = 0;

   private void rdt_send(String data, byte id) throws IOException {
      Packet sndpkt = new Packet((byte)_curSeq, id, data);

      System.out.println("Writing #" + id + " to stream.");
      out.write(Util.signedToUnsigned(0)); // Indicate we do not wish to QUIT
      sndpkt.writeToStream(out);
      out.flush();

      Ack rcvpkt;
      while (true) {
         rcvpkt = rdt_rcv();
         if (rcvpkt.isCorrupt()) {
            System.out.println("Received corrupt ACK. Waiting...");
         }
         else if (rcvpkt.seq == 1-_curSeq) {
            System.out.println("Received ACK"+(1-_curSeq)+" when I was expecting an ACK"+_curSeq+". Waiting...");
         }
         else if (rcvpkt.isTimeoutAck()) {
            System.out.println("#" + id + " timed out, resending.");
            sndpkt.writeToStream(out);
         }
         else {
            System.out.println("#" + id + " sent successfully.");
            break;
         }
      }
      _curSeq = 1-_curSeq;
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
