import java.io.*;

class Ack {
   byte seq;
   byte checksum;

   private Ack(byte seq, byte checksum) {
      this.seq = seq;
      this.checksum = checksum;
   }

   public Ack(byte seq) {
      this.seq = seq;
      this.checksum = 0;
   }

   public boolean isCorrupt() {
      return checksum != 0;
   }

   public boolean writeToStream(OutputStream os) {
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

   public boolean isTimeoutAck() {
      return seq == 2;
   }

   static Ack readFromStream(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      byte seq = dis.readByte();
      byte checksum = dis.readByte();

      return new Ack(seq, checksum);
   }
}
