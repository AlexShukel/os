package Processes;

import Resources.ResourceManager;
import Resources.ResourceName;
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
                resourceManager.requestResource(resourceManager.getResourceByName(ResourceName.EILUTE_ATMINTYJE.name()), this);
                ++executionStep;
                break;
            case 2:
                Logger.debug("PrintLine: Step 2 - Block waiting for 'Channel device' resource");
                resourceManager.requestResource(resourceManager.getResourceByName(ResourceName.KANALU_IRENGINYS.name()), this);
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

        ++executionStep;
    }
}
