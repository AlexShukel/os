package Resources;

import Processes.*;

import java.util.ArrayList;

public class Resource {
    private ProcessDescriptor creator;
    private ElementList elements;

    private ProcessList waitingProcesses;
    private ArrayList<Integer> waitingCount;
    private ArrayList<Integer> waitingProcPoint;

    private int id;
    private ResourceList resourceList;

    public Resource(ProcessDescriptor creator, ResourceList resourceList)
    {
        this.creator = creator;
        this.resourceList = resourceList;

        elements = null;
        waitingProcesses = null;
        waitingCount = null;
        waitingProcPoint = null;
        id = 0;
    }
}
