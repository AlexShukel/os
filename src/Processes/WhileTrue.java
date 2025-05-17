package Processes;

import utils.Logger;

public class WhileTrue extends Process {
    public WhileTrue(ProcessManager manager) {
        super(manager);
    }

    @Override
    public void Step() {
        Logger.debug("WhileTrue process doing nothing");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
