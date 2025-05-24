package Processes;

import Resources.ResourceManager;

public class JobGorvernor extends Process {
    public JobGorvernor(ProcessManager processManager, ResourceManager resourceManager) {
        super(processManager, resourceManager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
