import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

import java.nio.channels.IllegalBlockingModeException;

public class NetworkServer {
   /* Static vars */

      public static int SOCKET_NR = 65003;

      /* Return Codes */
      public static int RC_ERR_SOCKALLOC = 1;
      public static int RC_ERR_SOCKACCEPT = 2;

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

   private Socket getSocketOrDie() {
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

   public void run() {
      System.out.println("Waiting for connection from receiver...");
      Socket receiverSock = this.acceptConnectionOrDie();
      //receiverThread = new ServerSocketReceiverThread();

      System.out.println("Waiting for connection from sender...");
      Socket senderSock = this.acceptConnectionOrDie();
      //senderThread = new ServerSocketSenderThread();
   }

   public static void main(String[] args ) {
      ServerSocket ss = makeSocketOrDie(SOCKET_NR);
      NetworkServer ns = new NetworkServer(ss);
      ns.run();
   }
}
