public class Logger {
    public static void debug(String format, Object... args) {
        System.out.print("[DEBUG]: ");
        System.out.printf(format, args);
        System.out.println();
    }
}
