package Processes;

import Resources.ResourceManager;

public class Interrupt extends Process {
    public Interrupt(ProcessManager processManager, ResourceManager resourceManager) {
        super(processManager, resourceManager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
