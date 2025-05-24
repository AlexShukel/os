package Processes;

import Resources.Element;
import Resources.Resource;
import Resources.ResourceManager;
import Resources.ResourceName;
import utils.Logger;

public class ReadFromInterface extends Process {
    private int executionStep = 1;
    private final ResourceManager resourceManager;

    public ReadFromInterface(Process parent, ResourceManager resourceManager) {
        super("ReadFromInterface", parent, 100);
        this.resourceManager = resourceManager;
    }

    private String getUserInput() {
        Resource resource = resourceManager.getResourceByName(ResourceName.IS_VARTOTOJO_SASAJOS.name());
        Element element = resource.getElements().get(0);
        return (String) element.getData();
    }

    @Override
    public void execute() {
        switch (executionStep) {
            case 1:
                Logger.debug("ReadFromInterface: Step 1 - Block waiting for 'From user interface' resource");
                resourceManager.requestResource(resourceManager.getResourceByName(ResourceName.IS_VARTOTOJO_SASAJOS.name()), this);
                ++executionStep;
                break;
            case 2:
                Logger.debug("ReadFromInterface: Step 2 - Check if command is 'exit'");
                if (getUserInput().equals("exit")) {
                    executionStep = 3;
                } else {
                    executionStep = 4;
                }
                break;
            case 3:
                Logger.debug("ReadFromInterface: Step 3 - Release 'MOS end' resource");
                resourceManager.releaseResource(resourceManager.getResourceByName(ResourceName.MOS_PABAIGA.name()));
                break;
            case 4:
                Logger.debug("ReadFromInterface: Step 4 - Check if command is 'help'");
                if (getUserInput().equals("help")) {
                    executionStep = 5;
                } else {
                    executionStep = 6;
                }
                break;
            case 5:
                Logger.debug("ReadFromInterface: Step 5 - Release line resource with usage instructions");
                resourceManager.releaseResource(resourceManager.getResourceByName(ResourceName.EILUTE_ATMINTYJE.name()));
                executionStep = 1;
                break;
            case 6:
                Logger.debug("ReadFromInterface: Step 6 - Check if command is 'run'");
                if (getUserInput().equals("run")) {
                    executionStep = 10;
                } else {
                    executionStep = 7;
                }
                break;
            case 7:
                Logger.debug("ReadFromInterface: Step 7 - Check if command is 'debug'");
                if (getUserInput().equals("debug")) {
                    executionStep = 9;
                } else {
                    executionStep = 8;
                }
                break;
            case 8:
                Logger.debug("ReadFromInterface: Step 8 - Release line resource with unknown command message");
                resourceManager.releaseResource(resourceManager.getResourceByName(ResourceName.EILUTE_ATMINTYJE.name()));
                executionStep = 1;
                break;
            case 9:
                Logger.debug("ReadFromInterface: Step 9 - Set debug flag");
                break;
            case 10:
                Logger.debug("ReadFromInterface: Step 10 - Read file");
                break;
            case 11:
                Logger.debug("ReadFromInterface: Step 11 - Block waiting for supervisor memory resource");
                break;
            case 12:
                Logger.debug("ReadFromInterface: Step 12 - Copy blocks to supervisor memory");
                break;
            case 13:
                Logger.debug("ReadFromInterface: Step 13 - Release 'Task in supervisor memory' resource for JCL process");
                finish();
                break;
        }

        ++executionStep;
    }
}
