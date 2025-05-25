package Processes;

import Resources.*;
import utils.Logger;

public class PrintLine extends Process {
    private int executionStep = 1;
    private final ResourceManager resourceManager;

    public PrintLine(Process parent, ResourceManager resourceManager) {
        super("PrintLine", parent, 100);
        this.resourceManager = resourceManager;
    }

    @Override
    public void execute() {
        switch (executionStep) {
            case 1:
                Logger.debug("PrintLine: Step 1 - Block waiting for 'Line in memory' resource");
                Resource lineInMemory = resourceManager.getFirstResourceByDescriptor(new EILUTE_ATMINTYJE());
                if (lineInMemory != null) {
                    resourceManager.removeResource(lineInMemory);
                }
                resourceManager.requestResource(new EILUTE_ATMINTYJE(), this);
                ++executionStep;
                break;
            case 2:
                Logger.debug("PrintLine: Step 2 - Block waiting for 'Channel device' resource");

                // DEBUG
                Resource resource = resourceManager.getFirstResourceByDescriptor(new EILUTE_ATMINTYJE());

                resourceManager.requestResource(new KANALU_IRENGINYS(), this);
                ++executionStep;
                break;
            case 3:
                Logger.debug("PrintLine: Step 3 - Set channel device registers and execute XCHG command");
                break;
            case 4:
                Logger.debug("PrintLine: Step 4 - Release 'Channel device' resources");
                finish();
                break;
        }
    }
}
