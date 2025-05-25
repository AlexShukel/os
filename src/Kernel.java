import Processes.ProcessManager;
import Processes.ShutdownCallback;
import Processes.StartStop;
import Resources.*;
import utils.Logger;

import java.io.IOException;
import java.util.ArrayList;

public class Kernel implements ShutdownCallback {
    private final ProcessManager processManager;
    private boolean systemRunning;
    private final StartStop startStopProcess;

    public Kernel() {
        this.processManager = new ProcessManager();
        this.systemRunning = false;
        startStopProcess = new StartStop(this, processManager);
    }

    public void boot() {
        processManager.addProcess(startStopProcess);
        systemRunning = true;

        runSystemLoop();
    }

    private void runSystemLoop() {
        StringBuilder buffer = new StringBuilder();
        while (systemRunning) {
            try {
                if (System.in.available() > 0) {
                    int ch = System.in.read();
                    if (ch == '\n' || ch == '\r') {
                        String input = buffer.toString();
                        buffer.setLength(0);
                        ResourceManager resourceManager = processManager.getResourceManager();
                        Resource resource = resourceManager.getFirstOrNewResourceByDescriptor(new IS_VARTOTOJO_SASAJOS(), startStopProcess);
                        ArrayList<Element> elements = new ArrayList<>();
                        elements.add(new Element(input, ElementType.STRING));
                        resource.setElements(elements);
                        resourceManager.releaseResource(resource);
                    } else {
                        buffer.append((char) ch);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            executePlanner();

            if (!systemRunning) {
                Logger.debug("Kernel: exit signal received");
                break;
            }
        }
    }

    public void executePlanner() {
        processManager.printStatus();

        processManager.schedule();

        processManager.executeCurrentProcess();
    }

    public void shutdown() {
        systemRunning = false;
    }

    public boolean isRunning() {
        return systemRunning;
    }

    public ProcessManager getProcessManager() {
        return processManager;
    }

    @Override
    public void requestShutdown() {
        systemRunning = false;
    }
}
