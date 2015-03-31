import java.io.*;

class Ack {
   int seq;
   int checksum;
   boolean writeToStream(OutputStream os) {
      try {
         DataOutputStream dos = new DataOutputStream(os);
         dos.writeByte(seq);
         dos.writeByte(checksum);
         dos.flush();
      }
      catch (IOException e) {
         return false;
      }
      return true;
   }
}
