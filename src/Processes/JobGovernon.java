package Processes;

import utils.Logger;

public class JobGovernon extends Process {
    private int executionStep = 1;

    public JobGovernon(Process parent, int priority) {
        super("JobGovernon", parent, priority);
    }

    @Override
    public void execute() {
        switch (executionStep) {
            case 1:
                Logger.debug("JobGovernor: Step 1 - Block waiting for 'User memory' resource");
                break;
            case 2:
                Logger.debug("JobGovernor: Step 2 - Release 'Loading package' resource");
                break;
            case 3:
                Logger.debug("JobGovernor: Step 3 - Block waiting for 'From Loader' resource");
                break;
            case 4:
                Logger.debug("JobGovernor: Step 4 - Release 'External memory' resources");
                break;
            case 5:
                Logger.debug("JobGovernor: Step 5 - Block waiting for 'User memory' resource, designated for page table storage");
                break;
            case 6:
                Logger.debug("JobGovernor: Step 6 - Create 'Virtual machine' process");
                break;
            case 7:
                Logger.debug("JobGovernor: Step 7 - Block waiting for 'From Interrupt' resource");
                break;
            case 8:
                Logger.debug("JobGovernor: Step 8 - Stop 'Virtual machine' process");
                break;
            case 9:
                Logger.debug("JobGovernor: Step 9 - Check if Input-Output interrupt");
                break;
            case 10:
                Logger.debug("JobGovernor: Step 10 - Destroy 'Virtual machine' process");
                break;
            case 11:
                Logger.debug("JobGovernor: Step 11 - Release 'User memory' resources");
                break;
            case 12:
                Logger.debug("JobGovernor: Step 12 - Release fictitious 'Task in drum' resource");
                break;
            case 13:
                Logger.debug("JobGovernor: Step 13 - Block waiting for 'Non-existent' resource");
                break;
            case 14:
                Logger.debug("JobGovernor: Step 14 - Check if GETD interrupt");
                break;
            case 15:
                Logger.debug("JobGovernor: Step 15 - Block waiting for 'Channel device' resource");
                break;
            case 16:
                Logger.debug("JobGovernor: Step 16 - Set channel device registers and execute XCHG command");
                break;
            case 17:
                Logger.debug("JobGovernor: Step 17 - Release 'Channel device' resources");
                break;
            case 18:
                Logger.debug("JobGovernor: Step 18 - Activate 'Virtual machine' process");
                break;
            case 19:
                Logger.debug("JobGovernor: Step 19 - Check if PRTW interrupt");
                break;
            case 20:
                Logger.debug("JobGovernor: Step 20 - Send words from stack top to output stream");
                break;
            case 21:
                Logger.debug("JobGovernor: Step 21 - Send bytes to output stream until special symbol $, starting from certain memory cell addresses");
                break;
            case 22:
                Logger.debug("JobGovernor: Step 22 - Block waiting for 'Channel device' resource");
                break;
            case 23:
                Logger.debug("JobGovernor: Step 23 - Set channel device registers and execute XCHG command");
                break;
            case 24:
                Logger.debug("JobGovernor: Step 24 - Release 'Channel device' resources");
                finish();
                break;
        }

        ++executionStep;
    }
}
