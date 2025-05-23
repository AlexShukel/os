package Processes;

public enum ProcessState {
    RUN("run"), // has CPU
    READY("ready"), // the only missing resource in CPU
    BLOCK("blocked"), // waiting for some resource
    READY_STOP("ready stopped"), // stopped by another process - was in "ready" or "run" state before
    BLOCK_STOP("blocked stopped"); // stopped by another process - was in "blocked" state before

    private final String name;

    ProcessState(String str)
    {
        name = str;
    }

    public String toString()
    {
        return "'" + this.name + "'";
    }
}
