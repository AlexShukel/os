public class ProcessDescriptor {
    private ProcessList processList;
    private RegisterImage registerImage;
    private CPU cpu;
    private ProcessState state;

    private Kernel kernel;
    private ProcessDescriptor parent;
    private ProcessList children;
    private ResourceList createdResources;
    private ElementList ownedResources;

    private int id;
    private int priority;
    private String userName;

    private Word sp = new Word(65536);
    private int stackSize;

    public ProcessDescriptor(ProcessList processList, CPU cpu, Kernel kernel, String userName)
    {
        this.processList = processList;
        this.cpu = cpu;

        registerImage = new RegisterImage();
        state = ProcessState.READY;

        parent = null;
        children = null;

        this.kernel = kernel;
        createdResources = new ResourceList(kernel);
        ownedResources = null;

        priority = 0;
        this.userName = userName;

        // TODO: set id
        id = 0;
    }

    public void SetParent(ProcessDescriptor process)
    {
        if (process != this)
            parent = process;
    }
}
