package Processes;

import Resources.ResourceManager;

public class JobToSwap extends Process {
    public JobToSwap(ProcessManager processManager, ResourceManager resourceManager) {
        super(processManager, resourceManager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
