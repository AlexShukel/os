package Processes;

import utils.Logger;

public class Interrupt extends Process {
    private int executionStep = 1;

    public Interrupt(Process parent) {
        super("Interrupt", parent, 100);
    }

    @Override
    public void execute() {
        switch (executionStep) {
            case 1:
                Logger.debug("Interrupt: Step 1 - Block waiting for 'Interrupt' resource");
                break;
            case 2:
                Logger.debug("Interrupt: Step 2 - Interrupt type identification");
                break;
            case 3:
                Logger.debug("Interrupt: Step 3 - JobGovernor, responsible for interrupted VM work, notification");
                break;
            case 4:
                Logger.debug("Interrupt: Step 4 - Release 'From Interrupt' resource designated for specific JobGovernor");
                finish();
                break;
        }

        ++executionStep;
    }
}
