package Processes;

import Resources.*;
import utils.Logger;

import java.util.ArrayList;

public class ReadFromInterface extends Process {
    private int executionStep = 1;
    private final ResourceManager resourceManager;

    public ReadFromInterface(Process parent, ResourceManager resourceManager) {
        super("ReadFromInterface", parent, 100);
        this.resourceManager = resourceManager;
    }

    private String getUserInput() {
        Resource resource = resourceManager.getFirstResourceByDescriptor(new IS_VARTOTOJO_SASAJOS());
        Element element = resource.getElements().get(0);
        return (String) element.getData();
    }

    @Override
    public void execute() {
        Resource resource;
        ArrayList<Element> elements;
        switch (executionStep) {
            case 1:
                Logger.debug("ReadFromInterface: Step 1 - Block waiting for 'From user interface' resource");
                Resource fromUserInterface = resourceManager.getFirstResourceByDescriptor(new IS_VARTOTOJO_SASAJOS());
                if (fromUserInterface != null) {
                    resourceManager.removeResource(fromUserInterface);
                }
                resourceManager.requestResource(new IS_VARTOTOJO_SASAJOS(), this);
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
                resourceManager.releaseResource(resourceManager.getFirstOrNewResourceByDescriptor(new MOS_PABAIGA(), this));
                executionStep = 1;
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
                resource = resourceManager.getFirstOrNewResourceByDescriptor(new EILUTE_ATMINTYJE(), this);
                elements = getHelpMessageElements();
                resource.setElements(elements);
                resourceManager.releaseResource(resource);
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
                resource = resourceManager.getFirstOrNewResourceByDescriptor(new EILUTE_ATMINTYJE(), this);
                elements = getSingleMessageElements("Unknown command received.");
                resource.setElements(elements);
                resourceManager.releaseResource(resource);
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
    }

    private ArrayList<Element> getSingleMessageElements(String message) {
        Element element = new Element(message, ElementType.STRING);
        ArrayList<Element> elements = new ArrayList<>();
        elements.add(element);
        return elements;
    }

    private ArrayList<Element> getHelpMessageElements() {
        String helpMessage = """
                Available commands:
                
                  help
                      Display this help message.
                
                  exit
                      Exit the program.
                
                  run <path>
                      Load and execute the program from the specified input file.
                      <path> is a string representing the path to the input file.
                
                  debug <path>
                      Load the program from the specified input file and start it in debug mode.
                      <path> is a string representing the path to the input file.
                """;

        return getSingleMessageElements(helpMessage);
    }
}
