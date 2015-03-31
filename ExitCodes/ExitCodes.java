class ExitCodes {
   public static int USAGE        = 1;
   public static int SOCKALLOC    = 2;
   public static int SOCKACCEPT   = 3;
   public static int SOCKIO       = 4;
   public static int WRONG_CLIENT = 5;
   public static int UNKNOWN_HOST = 6;

   public static void ExitWithMessage(int code) {
      System.err.println("Error!");
      System.exit(code);
   }
}
