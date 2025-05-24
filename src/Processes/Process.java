package Processes;

import Resources.Resource;
import utils.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class Process {
    protected int processId;
    protected String name;
    protected ProcessState state;
    protected int priority;
    protected Process parent;
    protected ArrayList<Process> children;
    protected static int nextId = 1;
    protected boolean finished = false;

    protected ArrayList<Resource> childResources;

    public Process(String name, Process parent, int priority) {
        this.processId = nextId++;
        this.name = name;
        this.parent = parent;
        this.priority = priority;
        this.state = ProcessState.READY;
        this.children = new ArrayList<>();
        this.childResources = new ArrayList<>();

        if (parent != null) {
            parent.addChild(this);
        }

        Logger.debug("Created process: %s (ID: %d, Priority: %d)", name, processId, priority);
    }

    public void addChildResource(Resource resource) {
        childResources.add(resource);
    }

    public void addChild(Process child) {
        children.add(child);
        Logger.debug("Process %s added child %s", name, child.getName());
    }

    public abstract void execute();

    public void finish() {
        this.finished = true;
        this.state = ProcessState.READY;
        Logger.debug("Process %s finished execution", name);
    }

    public int getProcessId() {
        return processId;
    }

    public String getName() {
        return name;
    }

    public ProcessState getState() {
        return state;
    }

    public int getPriority() {
        return priority;
    }

    public Process getParent() {
        return parent;
    }

    public List<Process> getChildren() {
        return children;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setState(ProcessState state) {
        Logger.debug("Process %s state changed: %s -> %s", name, this.state, state);
        this.state = state;
    }

    @Override
    public String toString() {
        return String.format("Process[%s, ID:%d, State:%s, Priority:%d, Finished:%s]",
                name, processId, state, priority, finished);
    }
}
