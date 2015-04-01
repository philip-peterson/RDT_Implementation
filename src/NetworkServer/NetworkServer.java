import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.concurrent.*;

public class NetworkServer {
   public NetworkServerThread receiverThread = null;
   public NetworkServerThread senderThread = null;

   private static ServerSocket makeSocketOrDie(int port) {
      ServerSocket s = null;
      try {
         s = new ServerSocket(port);
      }
      catch (IOException|SecurityException e) {
         System.err.println(String.format("Error: Could not make socket on port %d", port));
         System.exit(ExitCodes.SOCKALLOC);
      }
      return s;
   }

   private Socket acceptConnectionOrDie() {
      Socket s = null;
      try {
         s = serverSock.accept();
      }
      catch (
            IOException
               |SecurityException
               |IllegalBlockingModeException
            e
         ) {
         ExitCodes.ExitWithMessage(ExitCodes.SOCKACCEPT, e);
      }
      return s;
   }

   public ServerSocket serverSock;

   public NetworkServer(ServerSocket serverSock) {
      this.serverSock = serverSock;
   }

   public void ioError(IOException e) {
      System.out.println(e.getMessage());
      ExitCodes.ExitWithMessage(ExitCodes.SOCKIO, e);
   }

   public void run() {
      doAwaitConnection(true);
      doAwaitConnection(false);

      try {
         mainLoop();
      }
      catch (IOException e) {
         ioError(e);
      }

   }

   void mainLoop() throws IOException {
      while (true) {
         // Main loop

         if (wantsExit) {
            receiverThread.out.write(Util.signedToUnsigned(-1));
            receiverThread.out.flush();
            receiverThread.sock.close();
            System.out.println("Exiting successfully.");
            System.exit(0);
         }

         Ack ack = this.ackQueue.poll();
         if (ack != null) {
            ack.writeToStreamAndFlush(senderThread.out);
         }

         Packet p = this.packetQueue.poll();
         if (p != null) {
            receiverThread.out.write(Util.signedToUnsigned(0)); // Indicate we don't want to QUIT
            p.writeToStreamAndFlush(receiverThread.out);
         }

      }
   }

   /**
    * @param boolean isReceiver false if sender, true if receiver
    */
   public void doAwaitConnection(boolean isReceiver) {
      String clientName = isReceiver ? "receiver" : "sender";

      System.out.println(String.format("Waiting for connection from %s...", clientName));
      Socket sock = this.acceptConnectionOrDie();

      OutputStream out = null;
      InputStream in = null;

      int ident;

      try {
         out = sock.getOutputStream();
         in = sock.getInputStream();

         ident = in.read();
      }
      catch (IOException e) {
         ident = -1;
         this.ioError(e);
      }

      if (ident != (isReceiver ? ProgramCodes.RECEIVER : ProgramCodes.SENDER)) {
         System.err.println(
            String.format("Expected %s to connect, but something else happened. Quitting.", clientName)
         );
         System.exit(ExitCodes.WRONG_CLIENT);
      }

      NetworkServerThread thread;
      if (isReceiver) {
         thread = new NetworkServerReceiverThread(sock, in, out, this);
         receiverThread = thread;
      }
      else {
         thread = new NetworkServerSenderThread(sock, in, out, this);
         senderThread = thread;
      }
      thread.start();
   }

   protected ConcurrentLinkedQueue<Ack> ackQueue = new ConcurrentLinkedQueue<Ack>();
   protected ConcurrentLinkedQueue<Packet> packetQueue = new ConcurrentLinkedQueue<Packet>();
   protected boolean wantsExit = false;

   public static void main(String[] args ) {

      if (args.length != 1) {
         System.err.println("Usage: network PORT");
         System.exit(ExitCodes.USAGE);
      }
      int port = Integer.parseInt(args[0]);

      ServerSocket ss = makeSocketOrDie(port);
      NetworkServer ns = new NetworkServer(ss);
      ns.run();

   }
}
