public class Util {
   public static int unsignedToSigned(int b) {
      return b - 127;
   }
   public static int signedToUnsigned(int b) {
      return b + 127;
   }
}
