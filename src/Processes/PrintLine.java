package Processes;

import Resources.ResourceManager;

public class PrintLine extends Process {
    public PrintLine(ProcessManager processManager, ResourceManager resourceManager) {
        super(processManager, resourceManager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
