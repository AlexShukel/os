package Processes;

import utils.Logger;

public class WhileTrue implements Process {
    private int currentTick;

    @Override
    public void Step() {
        Logger.debug("WhileTrue process doing nothing");
        ++currentTick;
    }

    @Override
    public boolean EndedWork() {
        return false;
    }

    @Override
    public int GetTick() {
        return currentTick;
    }
}
