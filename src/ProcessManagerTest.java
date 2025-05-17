import Machine.RealMachine;
import Processes.ProcessManager;
import Processes.TestProcess;

public class ProcessManagerTest {
    public static void main(String[] args)
    {
        RealMachine realMachine = new RealMachine();
        ProcessManager processManager = new ProcessManager(realMachine);
        TestProcess test = new TestProcess();
        processManager.CreateProcess(test, null, "first", 100);

        while (true) {
            processManager.ExecutePlanner();
        }
    }
}
