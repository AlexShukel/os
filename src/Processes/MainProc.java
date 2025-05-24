package Processes;

import utils.Logger;

public class MainProc extends Process {
    private int executionStep = 1;

    public MainProc(Process parent) {
        super("MainProc", parent, 100);
    }

    @Override
    public void execute() {
        switch (executionStep) {
            case 1:
                Logger.debug("MainProc: Step 1 - Block waiting for 'Task in drum' resource");
                break;
            case 2:
                Logger.debug("MainProc: Step 2 - Check if execution time is not 0");
                break;
            case 3:
                Logger.debug("MainProc: Step 3 - Create JobGovernor process, giving it 'Task in drum' as initial resource");
                break;
            case 4:
                Logger.debug("MainProc: Step 4 - Destroy JobGovernor process, receiving the resource");
                finish();
                break;
        }

        ++executionStep;
    }
}
