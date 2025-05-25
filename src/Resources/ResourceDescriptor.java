package Resources;

import Processes.Process;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class ResourceDescriptor {
    public abstract Queue<Process> getWaitingProcesses();

    public abstract void addWaitingProcess(Processes.Process process);

    public abstract Process pollFirstWaitingProcess();

    public abstract String getName();

    public abstract ResourceType getType();
}
