import utils.Logger;

public class MultiprogrammingOS {
    public static void main(String[] args) {
        Logger.debug("Multiprogramming OS Starting...");

        try {
            Kernel kernel = new Kernel();
            kernel.boot();

            Logger.debug("System execution completed successfully");

        } catch (Exception e) {
            Logger.debug("System error occurred: %s", e.getMessage());
            e.printStackTrace();
        }

        Logger.debug("Multiprogramming OS Terminated...");
    }
}
