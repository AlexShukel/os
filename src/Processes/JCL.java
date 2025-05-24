package Processes;

import Resources.ResourceManager;

public class JCL extends Process {
    public JCL(ProcessManager processManager, ResourceManager resourceManager) {
        super(processManager, resourceManager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
