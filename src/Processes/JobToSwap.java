package Processes;

import utils.Logger;

public class JobToSwap extends Process {
    private int executionStep = 1;

    public JobToSwap(Process parent) {
        super("JobToSwap", parent, 100);
    }

    @Override
    public void execute() {
        switch (executionStep) {
            case 1:
                Logger.debug("JobToSwap: Step 1 - Block waiting for 'Task execution parameters in supervisor memory' resource");
                break;
            case 2:
                Logger.debug("JobToSwap: Step 2 - Block waiting for 'Task program in supervisor memory' resource");
                break;
            case 3:
                Logger.debug("JobToSwap: Step 3 - Block waiting for 'External memory' resource");
                break;
            case 4:
                Logger.debug("JobToSwap: Step 4 - Block waiting for 'Channel device' resource");
                break;
            case 5:
                Logger.debug("JobToSwap: Step 5 - Set channel device registers and execute XCHG command");
                break;
            case 6:
                Logger.debug("JobToSwap: Step 6 - Release 'Channel device' resources");
                break;
            case 7:
                Logger.debug("JobToSwap: Step 7 - Release 'Task in drum' resources");
                finish();
                break;
        }

        ++executionStep;
    }
}
