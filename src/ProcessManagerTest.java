import Machine.RealMachine;
import Processes.ProcessManager;
import utils.Logger;

public class ProcessManagerTest {
    public static void main(String[] args)
    {
        RealMachine realMachine = new RealMachine();
        ProcessManager processManager = new ProcessManager(realMachine);

        while (true) {
            int result = processManager.ExecutePlanner();

            if (result == -1) {
                Logger.debug("Terminating");
                break;
            }
        }
    }
}
