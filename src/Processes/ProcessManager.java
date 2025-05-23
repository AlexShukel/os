package Processes;

import Machine.RealMachine;
import Resources.ResourceManager;
import utils.Logger;
import Resources.Resource;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class ProcessManager {
    private RealMachine realMachine;
    private ArrayList<ProcessDescriptor> processes; // The list of all processes
    private PriorityQueue<ProcessDescriptor> readyProcesses; // The list of ready to execute processes

    private ArrayList<ProcessDescriptor> blockedProcesses; // The list of blocked processes
    private ArrayList<Resource> resources; // The list of all resources
    private ProcessDescriptor currentRunProcess; // Current run process descriptor

    private ResourceManager resourceManager;

    public ProcessManager(RealMachine realMachine) {
        this.realMachine = realMachine;

        processes = new ArrayList<>();
        blockedProcesses = new ArrayList<>();
        resources = new ArrayList<>();
        readyProcesses = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.GetPriority(), a.GetPriority())
        );

        resourceManager = new ResourceManager(this);

        ProcessDescriptor descriptor = CreateProcess(new StartStop(this, resourceManager), null, "StartStop", 10, ProcessState.READY);
        descriptor.SetState(ProcessState.RUN);
        currentRunProcess = descriptor;
    }

    public ProcessDescriptor GetProcessByName(String name) {
        for (ProcessDescriptor descriptor : processes) {
            if (descriptor.GetName().equals(name)) {
                return descriptor;
            }
        }

        return null;
    }

    public ResourceManager GetResourceManager() {
        return resourceManager;
    }

    /**
     * Creates the ProcessDescriptor from Process
     *
     * @param process      The process to be created
     * @param creator      The parent process descriptor
     * @param userName     Unique name of the process
     * @param priority     Priority number
     * @param initialState The initial process state
     * @return ProcessDescriptor
     */
    public ProcessDescriptor CreateProcess(Process process, ProcessDescriptor creator, String userName, int priority, ProcessState initialState) {
        ProcessDescriptor descriptor = new ProcessDescriptor(process, realMachine.cpu, userName, priority, initialState);

        if (creator != null) {
            creator.AddChildProcess(descriptor);
            descriptor.SetParent(creator);
        }

        descriptor.SetId(processes.size());

        processes.add(descriptor);

        Logger.debug("Created process " + descriptor + ". Initial state " + initialState + ".");
        if (initialState == ProcessState.READY) {
            readyProcesses.add(descriptor);
        }

        return descriptor;
    }

    public void RemoveProcess(ProcessDescriptor process) {
        // TODO: Delete resources and children

        if (process.GetParent() != null) {
            process.GetParent().RemoveChildProcess(process);
        }

        processes.remove(process);
        readyProcesses.remove(process);

        Logger.debug("Removed process " + process);
    }

    public void StopProcess(ProcessDescriptor process) {
        if (process.GetState() == ProcessState.BLOCK) {
            process.SetState(ProcessState.BLOCK_STOP);
        } else if (process.GetState() == ProcessState.READY) {
            process.SetState(ProcessState.READY_STOP);
        }
    }

    public void ActivateProcess(ProcessDescriptor process) {
        if (process.GetState() == ProcessState.BLOCK_STOP) {
            process.SetState(ProcessState.BLOCK);
        } else if (process.GetState() == ProcessState.READY_STOP) {
            process.SetState(ProcessState.READY);
        } else if (process.GetState() == ProcessState.BLOCK) {
            process.SetState(ProcessState.READY);
            blockedProcesses.remove(process);
            readyProcesses.add(process);
        }
    }

    public void BlockProcess(ProcessDescriptor process) {
        readyProcesses.remove(process);
        blockedProcesses.add(process);
        process.SetState(ProcessState.BLOCK);
    }

    public int ExecutePlanner() {
        if (currentRunProcess.GetState() == ProcessState.BLOCK) {
            blockedProcesses.add(currentRunProcess);
        }

        if (readyProcesses.isEmpty()) {
            // activate WhileTrue process
            Logger.debug("No ready processes");
            return -1;
        }

        currentRunProcess = readyProcesses.poll();
        currentRunProcess.SetState(ProcessState.RUN);
        currentRunProcess.Run();

        if (currentRunProcess.Completed()) {
            RemoveProcess(currentRunProcess);
        } else if (currentRunProcess.GetState() == ProcessState.RUN) {
            currentRunProcess.SetState(ProcessState.READY);
            readyProcesses.add(currentRunProcess);
        }

        return 0;
    }
}
