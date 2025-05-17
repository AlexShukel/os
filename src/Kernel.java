public class Kernel {
    private ProcessList processes;
    private ResourceList resources;
    private ProcessList readyProcesses;
    private ProcessList runProcesses;
    private RealMachine realMachine;

    public Kernel()
    {
        processes = new ProcessList(this);
        resources = new ResourceList(this);
        readyProcesses = new ProcessList(this);
        runProcesses = new ProcessList(this);
        realMachine = new RealMachine();
    }
}
