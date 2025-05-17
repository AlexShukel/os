package Processes;

public abstract class Process {
    private boolean endedWork = false;
    private ProcessDescriptor descriptor;
    private final ProcessManager manager;

    public Process(ProcessManager manager)
    {
        this.manager = manager;
    }

    public abstract void Step();

    public void CreateProcess(Process process, String userName, int priority)
    {
        manager.CreateProcess(process, descriptor, userName, priority);
    }

    public void RemoveProcess(Process process)
    {
        manager.RemoveProcess(process.GetDescriptor());
    }

    public void CompleteWork() {
        endedWork = true;
    }

    public boolean EndedWork() {
        return endedWork;
    }

    public ProcessManager GetProcessManager()
    {
        return manager;
    }

    public void SetDescriptor(ProcessDescriptor descriptor)
    {
        this.descriptor = descriptor;
    }

    public ProcessDescriptor GetDescriptor()
    {
        return descriptor;
    }
}