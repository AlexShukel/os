import java.util.ArrayList;

public class ProcessList {
    private Kernel kernel;
    private ArrayList<ProcessDescriptor> processes;

    public ProcessList(Kernel kernel)
    {
        this.kernel = kernel;
        processes = new ArrayList<>();
    }

    public int Count()
    {
        return processes.size();
    }
}
