package Resources;

import Processes.Process;

import java.util.ArrayDeque;
import java.util.Queue;

public class KANALU_IRENGINYS extends ResourceDescriptor {
    @Override
    public String getName() {
        return "KANALU_IRENGINYS";
    }

    @Override
    public ResourceType getType() {
        return ResourceType.STATIC;
    }

    private static final Queue<Processes.Process> waitingProcesses = new ArrayDeque<>();

    @Override
    public Queue<Processes.Process> getWaitingProcesses() {
        return waitingProcesses;
    }

    @Override
    public void addWaitingProcess(Processes.Process process) {
        waitingProcesses.offer(process);
    }

    @Override
    public Process pollFirstWaitingProcess() {
        return waitingProcesses.poll();
    }
}
