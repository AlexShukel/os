package Processes;

import Resources.ResourceManager;

public class MainProc extends Process {
    public MainProc(ProcessManager processManager, ResourceManager resourceManager) {
        super(processManager, resourceManager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
