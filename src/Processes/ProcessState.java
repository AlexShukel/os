package Processes;

public enum ProcessState {
    RUN("run"),
    READY("ready"),
    BLOCK("blocked"),
    READY_STOP("ready stopped"),
    BLOCK_STOP("blocked stopped");

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
