class ExitCodes {
   public static int USAGE        =   1;
   public static int UNKNOWN_HOST =   2;
   public static int INPUTIO      =   3;

   public static int SOCKALLOC    = 200;
   public static int SOCKACCEPT   = 201;
   public static int SOCKIO       = 202;
   public static int SOCKBROKEN   = 203;

   public static int WRONG_CLIENT = 300;

   public static void ExitWithMessage(int code) {
      System.err.println("Error!");
      System.exit(code);
   }

   public static void ExitWithMessage(int code, Exception e) {
      System.err.println("Error!");
      System.err.println("(Specifically: " + e.getMessage() + ")");
      System.exit(code);
   }
}
