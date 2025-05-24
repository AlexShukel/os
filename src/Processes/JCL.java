package Processes;

import utils.Logger;

public class JCL extends Process {
    private int executionStep = 1;

    public JCL(Process parent) {
        super("JCL", parent, 100);
    }

    @Override
    public void execute() {
        switch (executionStep) {
            case 1:
                Logger.debug("JCL: Step 1 - Block waiting for 'Task in supervisor memory' resource");
                break;
            case 2:
                Logger.debug("JCL: Step 2 - Initialize program list");
                break;
            case 3:
                Logger.debug("JCL: Step 3 - Take block from supervisor memory");
                break;
            case 4:
                Logger.debug("JCL: Step 4 - Check if this is @FILE0 block");
                break;
            case 5:
                Logger.debug("JCL: Step 5 - Release line resource with @FILE0 block missing message");
                break;
            case 6:
                Logger.debug("JCL: Step 6 - Check if @FILE0 block is correct");
                break;
            case 7:
                Logger.debug("JCL: Step 7 - Release line resource with incorrect @FILE0 block message");
                break;
            case 8:
                Logger.debug("JCL: Step 8 - Release task name in supervisor memory resource");
                break;
            case 9:
                Logger.debug("JCL: Step 9 - Check if this is @CODE0 block");
                break;
            case 10:
                Logger.debug("JCL: Step 10 - Release line resource with @CODE0 block missing message");
                break;
            case 11:
                Logger.debug("JCL: Step 11 - Take block from supervisor memory");
                break;
            case 12:
                Logger.debug("JCL: Step 12 - Check if this is @DATA0 block or no more blocks");
                break;
            case 13:
                Logger.debug("JCL: Step 13 - Add block to program list");
                break;
            case 14:
                Logger.debug("JCL: Step 14 - Check if this is @DATA0 block");
                break;
            case 15:
                Logger.debug("JCL: Step 15 - Release line resource with @DATA0 block missing message");
                break;
            case 16:
                Logger.debug("JCL: Step 16 - Take block from supervisor memory");
                break;
            case 17:
                Logger.debug("JCL: Step 17 - Check if this is @END00 block or no more blocks");
                break;
            case 18:
                Logger.debug("JCL: Step 18 - Add block to program list");
                break;
            case 19:
                Logger.debug("JCL: Step 19 - Check if this is @END00 block");
                break;
            case 20:
                Logger.debug("JCL: Step 20 - Release line resource with @END00 block missing message");
                break;
            case 21:
                Logger.debug("JCL: Step 21 - Check if program list is not empty");
                break;
            case 22:
                Logger.debug("JCL: Step 22 - Release line resource with no user program message");
                break;
            case 23:
                Logger.debug("JCL: Step 23 - Release task program in supervisor memory resource");
                finish();
                break;
        }

        ++executionStep;
    }
}
