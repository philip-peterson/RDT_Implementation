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

   public void corruptify() {
      checksum = 1;
   }

   public boolean isCorrupt() {
      return checksum != 0;
   }

   public void writeToStreamAndFlush(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeByte(seq);
      dos.writeByte(checksum);
      dos.flush();
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
