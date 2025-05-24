package Processes;

import utils.Logger;

public class Loader extends Process {
    private int executionStep = 1;

    public Loader(Process parent) {
        super("Loader", parent, 100);
    }

    @Override
    public void execute() {
        switch (executionStep) {
            case 1:
                Logger.debug("Loader: Step 1 - Block waiting for 'Loading package' resource");
                break;
            case 2:
                Logger.debug("Loader: Step 2 - Block waiting for 'Channel device' resource");
                break;
            case 3:
                Logger.debug("Loader: Step 3 - Set channel device registers and execute XCHG command");
                break;
            case 4:
                Logger.debug("Loader: Step 4 - Release 'Channel device' resources");
                break;
            case 5:
                Logger.debug("Loader: Step 5 - Release 'From Loader' resources, designated for JobGovernor process, which created and sent 'Loading package' resource");
                finish();
                break;
        }

        ++executionStep;
    }
}
