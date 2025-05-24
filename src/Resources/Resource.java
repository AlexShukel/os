package Resources;

import Processes.Process;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Resource {
    private int id;
    private String name;
    private ResourceType type;
    private Process creator;
    private Queue<Process> waitingProcesses;
    private ArrayList<Element> elements;

    private static int nextId = 0;

    public Resource(String name, ResourceType type, Process creator) {
        this.id = nextId++;
        this.name = name;
        this.type = type;
        this.creator = creator;
        this.waitingProcesses = new ArrayDeque<>();
        this.elements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ResourceType getType() {
        return type;
    }

    public Queue<Process> getWaitingProcesses() {
        return waitingProcesses;
    }

    public void addWaitingProcess(Process process) {
        waitingProcesses.offer(process);
    }

    public Process pollFirstWaitingProcess() {
        return waitingProcesses.poll();
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void addElement(Element element) {
        elements.add(element);
    }
}
