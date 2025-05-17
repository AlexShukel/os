import Machine.RealMachine;
import Processes.ProcessManager;

public class ProcessManagerTest {
    public static void main(String[] args)
    {
        RealMachine realMachine = new RealMachine();
        ProcessManager processManager = new ProcessManager(realMachine);

        while (true) {
            processManager.ExecutePlanner();
        }
    }
}
