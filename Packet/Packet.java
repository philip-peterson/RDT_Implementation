import java.io.*;

class Packet {
   byte seq;
   byte id;
   int checksum;
   byte[] content;

   static final int PAYLOAD = 256;

   Packet(byte seq, byte id, int checksum, byte[] data) {
      content = c.getBytes();
   }

   void isValid() {

   }

   static Packet readFromStream(InputStream is) throws IOException {
      DataOutputStream dis = new DataInputStream(is);
      byte seq = dis.readByte();
      byte id = dis.readByte();
      byte checksum = dis.readInt();
      byte[] data = new byte[PAYLOAD];
      dis.readFully(data, 0, PAYLOAD);
      return new Packet(seq, id, checksum, data);
   }

   boolean writeToStream(OutputStream os) {
      try {
         DataOutputStream dos = new DataOutputStream(os);
         dos.writeInt(getSize());
         dos.writeByte(seq);
         dos.writeByte(id);
         dos.writeInt(checksum);
         dos.write(content, 0, content.length);
         dos.flush();
      }
      catch (IOException e) {
         return false;
      }
      return true;
   }
}
