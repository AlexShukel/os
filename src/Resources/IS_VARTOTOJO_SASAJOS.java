package Resources;

import Processes.Process;

import java.util.ArrayDeque;
import java.util.Queue;

public class IS_VARTOTOJO_SASAJOS extends ResourceDescriptor {
    @Override
    public String getName() {
        return "IS_VARTOTOJO_SASAJOS";
    }

    @Override
    public ResourceType getType() {
        return ResourceType.STATIC;
    }

    private static final Queue<Process> waitingProcesses = new ArrayDeque<>();

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
