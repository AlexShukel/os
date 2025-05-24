package Processes;

import Resources.ResourceManager;

public abstract class Process {
    private boolean endedWork = false;
    protected ProcessDescriptor descriptor;

    protected final ProcessManager processManager;
    protected final ResourceManager resourceManager;

    public Process(ProcessManager processManager, ResourceManager resourceManager) {
        this.processManager = processManager;
        this.resourceManager = resourceManager;
    }

    public abstract void Step();

    public void CompleteWork() {
        endedWork = true;
    }

    public boolean EndedWork() {
        return endedWork;
    }

    public void SetDescriptor(ProcessDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public ProcessDescriptor GetDescriptor() {
        return descriptor;
    }
}