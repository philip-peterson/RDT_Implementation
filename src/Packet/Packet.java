import java.io.*;

class Packet {
   byte seq;
   byte id;
   int checksum;
   String content;

   static final int PAYLOAD = 256;

   private Packet(byte seq, byte id, int checksum, String content) {
      this.seq = seq;
      this.id = id;
      this.checksum = checksum;
      this.content = content;
   }

   public Packet(byte seq, byte id, String content) {
      this.seq = seq;
      this.id = id;
      this.content = content;
      this.recalculateChecksum();
   }

   boolean isCorrupt() {
      return checksum != getCorrectChecksum();
   }

   int getCorrectChecksum() {
      int checksum = 0;
      byte[] data = content.getBytes();
      int c = data.length;
      for (int i = 0; i < c; i++) {
         checksum += data[i];
      }
      return checksum;
   }

   void recalculateChecksum() {
      this.checksum = this.getCorrectChecksum();
   }

   static Packet readFromStream(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      byte seq = dis.readByte();
      byte id = dis.readByte();
      byte checksum = (byte)dis.readInt();
      byte[] data = new byte[PAYLOAD];
      dis.readFully(data, 0, PAYLOAD);

      StringBuilder sb = new StringBuilder(PAYLOAD);
      for (int i = 0; i < PAYLOAD; i++) {
         if (data[i] == 0) {
            break;
         }
         sb.append((char)data[i]);
      }
      return new Packet(seq, id, checksum, sb.toString());
   }

   boolean writeToStream(OutputStream os) {
      byte[] data = new byte[PAYLOAD];
      int i;

      for (i = 0; i < content.length(); i++) {
         data[i] = (byte)content.charAt(i);
      }
      for (i = content.length(); i < PAYLOAD; i++) {
         data[i] = 0;
      }

      try {
         DataOutputStream dos = new DataOutputStream(os);
         dos.writeByte(seq);
         dos.writeByte(id);
         dos.writeInt(checksum);
         dos.write(data, 0, PAYLOAD);
         dos.flush();
      }
      catch (IOException e) {
         return false;
      }
      return true;
   }
}
