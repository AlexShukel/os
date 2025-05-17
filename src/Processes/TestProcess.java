package Processes;

import utils.Logger;

public class TestProcess implements Process {
    private int currentTick = 0;
    private int currentStep = 1;
    private boolean endedWork = false;

    @Override
    public void Step() {
        switch (currentStep) {
            case 1:
                Logger.debug("1st step");
                break;
            case 2:
                Logger.debug("2nd step");
                break;
            case 3:
                Logger.debug("3rd step");
                endedWork = true;
                break;
        }

        ++currentStep;
        ++currentTick;
    }

    @Override
    public boolean EndedWork() {
        return endedWork;
    }

    @Override
    public int GetTick() {
        return currentTick;
    }
}
