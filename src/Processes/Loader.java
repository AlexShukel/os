package Processes;

import Resources.ResourceManager;

public class Loader extends Process {

    public Loader(ProcessManager processManager, ResourceManager resourceManager) {
        super(processManager, resourceManager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
