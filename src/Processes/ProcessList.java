package Processes;

import java.util.ArrayList;

public class ProcessList {
    private ArrayList<ProcessDescriptor> processes;

    public ProcessList()
    {
        processes = new ArrayList<>();
    }

    public int Count()
    {
        return processes.size();
    }
}
