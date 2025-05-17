import Processes.Process;
import Processes.WhileTrue;
import utils.Logger;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class ProcessManager {
    private RealMachine realMachine;
    private ArrayList<ProcessDescriptor> processes;
    private PriorityQueue<ProcessDescriptor> readyProcesses;

    private ArrayList<ProcessDescriptor> blockedProcesses;
    private ArrayList<Resource> resources;
    private ProcessDescriptor currentRunProcess;

    public ProcessManager(RealMachine realMachine)
    {
        this.realMachine = realMachine;

        processes = new ArrayList<>();
        blockedProcesses = new ArrayList<>();
        resources = new ArrayList<>();
        readyProcesses = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.GetPriority(), a.GetPriority())
        );

        WhileTrue whileTrueProcess = new WhileTrue();
        ProcessDescriptor descriptor = new ProcessDescriptor(whileTrueProcess, realMachine.cpu, "WhileTrue", 0);
        descriptor.SetState(ProcessState.RUN);
        processes.add(descriptor);
        readyProcesses.add(descriptor);
        currentRunProcess = descriptor;
    }

    public void CreateProcess(Process process, ProcessDescriptor creator, String userName, int priority)
    {
        ProcessDescriptor descriptor = new ProcessDescriptor(process, realMachine.cpu, userName, priority);

        if (creator != null)
        {
            creator.AddChildProcess(descriptor);
            descriptor.SetParent(creator);
        }

        descriptor.SetId(processes.size());

        processes.add(descriptor);

        Logger.debug("Created process " + descriptor);
        descriptor.SetState(ProcessState.READY);
        readyProcesses.add(descriptor);
    }

    public void RemoveProcess(ProcessDescriptor process)
    {
        // TODO: Delete resources and children

        if (process.GetParent() != null)
        {
            process.GetParent().RemoveChildProcess(process);
        }

        processes.remove(process);
        readyProcesses.remove(process);

        Logger.debug("Removed process " + process);
    }

    public void StopProcess(ProcessDescriptor process)
    {
        if (process.GetState() == ProcessState.BLOCK)
        {
            process.SetState(ProcessState.BLOCK_STOP);
        }
        else if (process.GetState() == ProcessState.READY)
        {
            process.SetState(ProcessState.READY_STOP);
        }
    }

    public void ActivateProcess(ProcessDescriptor process)
    {
        if (process.GetState() == ProcessState.BLOCK_STOP)
        {
            process.SetState(ProcessState.BLOCK);
        }
        else if (process.GetState() == ProcessState.READY_STOP)
        {
            process.SetState(ProcessState.READY);
        }
    }

    public void ExecutePlanner()
    {
        if (currentRunProcess.GetState() == ProcessState.BLOCK)
        {
            blockedProcesses.add(currentRunProcess);
        }

        currentRunProcess = readyProcesses.peek();
        currentRunProcess.SetState(ProcessState.RUN);
        currentRunProcess.Run();

        if (currentRunProcess.Completed())
        {
            RemoveProcess(currentRunProcess);
        }
    }
}
