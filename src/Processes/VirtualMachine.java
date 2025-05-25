package Processes;

import utils.Logger;

public class VirtualMachine extends Process {
    private int executionStep = 1;

    public VirtualMachine(Process parent, int priority) {
        super("VirtualMachine", parent, priority);
    }

    @Override
    public void execute() {
        switch (executionStep) {
            case 1:
                Logger.debug("VirtualMachine: Step 1 - Processor switched to user mode");
                break;
            case 2:
                Logger.debug("VirtualMachine: Step 2 - Execute user program");
                break;
            case 3:
                Logger.debug("VirtualMachine: Step 3 - Release 'Interrupt' resource");
                finish();
                break;
        }

        ++executionStep;
    }
}
