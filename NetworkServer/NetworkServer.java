import java.net.*;
import java.io.*;
import java.nio.channels.*;

public class NetworkServer {
   /* Static vars */

      public static int SOCKET_NR = 65003;

      /* Return Codes */
      public static int RC_ERR_SOCKALLOC    = 1;
      public static int RC_ERR_SOCKACCEPT   = 2;
      public static int RC_ERR_SOCKIO       = 3;
      public static int RC_ERR_WRONG_CLIENT = 4;

   /* End static vars */

   public Thread receiverThread = null;
   public Thread senderThread = null;

   private static ServerSocket makeSocketOrDie(int port) {
      ServerSocket s = null;
      try {
         s = new ServerSocket(port);
      }
      catch (IOException|SecurityException e) {
         System.err.println(String.format("Error: Could not make socket on port %d", port));
         System.exit(RC_ERR_SOCKALLOC);
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
         System.err.println("Error: Could not accept connection: " + e.getMessage());
         System.exit(RC_ERR_SOCKACCEPT);
      }
      return s;
   }

   public ServerSocket serverSock;

   public NetworkServer(ServerSocket serverSock) {
      this.serverSock = serverSock;
   }

   public void ioError(IOException e) {
      System.err.println("Error: I/O -- " + e.getMessage());
      System.exit(RC_ERR_SOCKIO);
   }

   public void run() {
      doAwaitConnection(true);
      doAwaitConnection(false);
   }

   /**
    * @param boolean isReceiver false if sender, true if receiver
    */
   public void doAwaitConnection(boolean isReceiver) {
      String clientName = isReceiver ? "receiver" : "sender";

      System.out.println(String.format("Waiting for connection from %s...", clientName));
      Socket sock = this.acceptConnectionOrDie();

      DataOutputStream out = null;
      BufferedReader in = null;

      try {
         out = new DataOutputStream(sock.getOutputStream());
         in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

         String ident = in.readLine();
         if (!ident.equals(clientName)) {
            System.err.println(
               String.format("Expected %s to connect, but something else connected. Quitting.", clientName)
            );
            System.exit(RC_ERR_WRONG_CLIENT);
         }
      }
      catch (IOException e) {
         this.ioError(e);
      }

      Thread thread;
      if (isReceiver) {
         thread = new NetworkServerReceiverThread(sock, in, out, this);
      }
      else {
         thread = new NetworkServerSenderThread(sock, in, out, this);
      }
      thread.start();
   }

   public static void main(String[] args ) {
      ServerSocket ss = makeSocketOrDie(SOCKET_NR);
      NetworkServer ns = new NetworkServer(ss);
      ns.run();
   }
}
