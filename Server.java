import java.net.ServerSocket;
import java.io.IOException;

class Server {
   public static int SOCKET_NR = 65003;

   /* Return Codes */
   public static int RC_ERR_SOCKALLOC;

   private static ServerSocket makeSocketOrDie(int port) {
      ServerSocket s = null;
      try {
         s = new ServerSocket(port);
      }
      catch (IOException e) {
         System.err.println(String.format("Could not make socket on port %d", port));
         System.exit(RC_ERR_SOCKALLOC);
      }
      return s;
   }

   public static void main(String[] args ) {
      ServerSocket sock = makeSocketOrDie(SOCKET_NR);
   }
}
