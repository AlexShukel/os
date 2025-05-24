package Processes;

import Resources.ResourceManager;
import utils.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class ProcessManager {
    private ArrayList<Process> processList;
    private PriorityQueue<Process> readyProcesses;
    private ArrayList<Process> blockedProcesses;
    private Process currentProcess;
    private final ResourceManager resourceManager;

    public ProcessManager() {
        this.processList = new ArrayList<>();
        this.readyProcesses = new PriorityQueue<>((p1, p2) -> Integer.compare(p2.getPriority(), p1.getPriority()));
        this.blockedProcesses = new ArrayList<>();
        this.currentProcess = null;
        this.resourceManager = new ResourceManager(this);
    }

    public void addProcess(Process process) {
        processList.add(process);
        if (process.getState() == ProcessState.READY) {
            readyProcesses.offer(process);
            Logger.debug("ProcessManager: Added process %s to ready queue", process.getName());
        }
    }

    public void removeProcess(Process process) {
        processList.remove(process);
        readyProcesses.remove(process);
        blockedProcesses.remove(process);
        Logger.debug("ProcessManager: Removed process %s", process.getName());
    }

    public void activateProcess(Process process) {
        if (process.getState() == ProcessState.BLOCKED) {
            process.setState(ProcessState.READY);
            blockedProcesses.remove(process);
            readyProcesses.add(process);
        }
    }

    public void blockProcess(Process process) {
        if (process.getState() == ProcessState.READY || process.getState() == ProcessState.RUNNING) {
            readyProcesses.remove(process);
            blockedProcesses.add(process);
            process.setState(ProcessState.BLOCKED);
        }
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }

    public void schedule() {
        cleanupFinishedProcesses();

        if (currentProcess != null && currentProcess.getState() == ProcessState.RUNNING && !currentProcess.isFinished()) {
            return;
        }

        if (currentProcess != null) {
            if (currentProcess.isFinished()) {
                Logger.debug("ProcessManager: Current process %s finished", currentProcess.getName());
                removeProcess(currentProcess);
            } else if (currentProcess.getState() == ProcessState.READY) {
                readyProcesses.offer(currentProcess);
                Logger.debug("ProcessManager: Moved process %s back to ready queue", currentProcess.getName());
            }
        }

        currentProcess = readyProcesses.poll();

        if (currentProcess != null) {
            currentProcess.setState(ProcessState.RUNNING);
            Logger.debug("ProcessManager: Scheduled process %s for execution", currentProcess.getName());
        } else {
            Logger.debug("ProcessManager: No processes available to schedule");
        }
    }

    private void cleanupFinishedProcesses() {
        Iterator<Process> iterator = processList.iterator();
        while (iterator.hasNext()) {
            Process process = iterator.next();
            if (process.isFinished()) {
                iterator.remove();
                readyProcesses.remove(process);
                Logger.debug("ProcessManager: Cleaned up finished process %s", process.getName());
            }
        }
    }

    public boolean hasActiveProcesses() {
        return !processList.isEmpty() || currentProcess != null;
    }

    public void executeCurrentProcess() {
        int iterations = 0;
        int MAX_TICKS = 10;
        while (currentProcess != null && !currentProcess.isFinished() && currentProcess.getState() == ProcessState.RUNNING && iterations < MAX_TICKS) {
            currentProcess.execute();

            ++iterations;
        }

        if (currentProcess != null && iterations == MAX_TICKS) {
            currentProcess.setState(ProcessState.READY);
        }
    }

    public int getProcessCount() {
        return processList.size();
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void printStatus() {
        Logger.debug("ProcessManager Status: Total=%d, Ready=%d, Current=%s", processList.size(), readyProcesses.size(), currentProcess != null ? currentProcess.getName() : "None");
    }
}
