package Resources;

import Processes.*;

import java.util.ArrayList;

public abstract class Resource {
    private ProcessDescriptor creator;

    private ArrayList<ProcessDescriptor> waitingProcesses;
    private ArrayList<Integer> waitingCount;
    private ArrayList<Integer> waitingProcPoint;

    private String name;
    private int id;

    private ResourceType type;
    private String element;

    public Resource(ProcessDescriptor creator, String name) {
        this.creator = creator;
        this.name = name;
        waitingCount = null;
        waitingProcPoint = null;
        id = 0;

        this.waitingProcesses = new ArrayList<>();
    }

    public String GetElement() {
        return element;
    }

    public void SetElement(String data) {
        element = data;
    }

    public void AddWaitingProcess(ProcessDescriptor process) {
        waitingProcesses.add(process);
    }

    public void RemoveWaitingProcess(ProcessDescriptor process) {
        waitingProcesses.remove(process);
    }

    public String GetName() {
        return name;
    }

    public void SetId(int id) {
        this.id = id;
    }
}
