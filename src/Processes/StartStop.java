package Processes;

import Resources.*;
import utils.Logger;

public class StartStop extends Process {
    private int executionStep = 1;
    private final ShutdownCallback shutdownCallback;

    private final ProcessManager processManager;

    public StartStop(ShutdownCallback shutdownCallback, ProcessManager processManager) {
        super("StartStop", null, 100);
        this.shutdownCallback = shutdownCallback;
        this.processManager = processManager;
    }

    @Override
    public void execute() {
        switch (executionStep) {
            case 1:
                initializeSystemResources();
                ++executionStep;
                break;
            case 2:
                initializeSystemProcesses();
                ++executionStep;
                break;
            case 3:
                Logger.debug("StartStop: blocking for MOS_END");
                ResourceManager resourceManager = processManager.getResourceManager();
                resourceManager.requestResource(new MOS_PABAIGA(), this);
                ++executionStep;
                break;
            case 4:
                cleanupProcesses();
                ++executionStep;
                break;
            case 5:
                cleanupResources();
                finish();
                shutdownCallback.requestShutdown();
                break;
        }
    }

    private void initializeSystemResources() {
        Logger.debug("StartStop: Initializing system resources (Step %d)", executionStep);
        ResourceManager resourceManager = processManager.getResourceManager();
        resourceManager.addResource(new Resource(new SUPERVIZORINE_ATMINTIS(), this), this);
        resourceManager.addResource(new Resource(new ISORINE_ATMINTIS(), this), this);
        resourceManager.addResource(new Resource(new KANALU_IRENGINYS(), this), this);
        resourceManager.addResource(new Resource(new VARTOTOJO_ATMINTIS(), this), this);
    }

    private void initializeSystemProcesses() {
        Logger.debug("StartStop: Initializing system processes (Step %d)", executionStep);
        processManager.addProcess(new WhileTrue(this));
        processManager.addProcess(new ReadFromInterface(this, processManager.getResourceManager()));
        processManager.addProcess(new JCL(this));
        processManager.addProcess(new JobToSwap(this));
        processManager.addProcess(new MainProc(this));
        processManager.addProcess(new Loader(this));
        processManager.addProcess(new Interrupt(this));
        processManager.addProcess(new PrintLine(this, processManager.getResourceManager()));
    }

    private void cleanupProcesses() {
        Logger.debug("StartStop: Cleaning up %d child processes (Step %d)", children.size(), executionStep);
        for (Process child : children) {
            Logger.debug("StartStop: Destroying process %s", child.getName());
        }
        children.clear();
        Logger.debug("StartStop: Process cleanup completed");
    }

    private void cleanupResources() {
        Logger.debug("StartStop: Cleaning up system resources (Step %d)", executionStep);
        ResourceManager resourceManager = processManager.getResourceManager();
        resourceManager.removeResource(resourceManager.getFirstResourceByDescriptor(new SUPERVIZORINE_ATMINTIS()));
        resourceManager.removeResource(resourceManager.getFirstResourceByDescriptor(new ISORINE_ATMINTIS()));
        resourceManager.removeResource(resourceManager.getFirstResourceByDescriptor(new KANALU_IRENGINYS()));
        resourceManager.removeResource(resourceManager.getFirstResourceByDescriptor(new VARTOTOJO_ATMINTIS()));
    }
}
