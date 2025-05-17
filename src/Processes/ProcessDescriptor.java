package Processes;

import Machine.*;
import utils.Logger;
import Resources.*;

import java.util.ArrayList;

public class ProcessDescriptor {
    private RegisterImage registerImage;
    private CPU cpu;
    private ProcessState state;

    private ProcessDescriptor parent;
    private ArrayList<ProcessDescriptor> children;
    private ResourceList createdResources;
    private ElementList ownedResources;

    private int id;
    private int priority;
    private String userName;

    private Word sp = new Word(65536);
    private int stackSize;

    private Process process;
    private final int maxTicks = 10;

    public ProcessDescriptor(Process process, CPU cpu, String userName, int priority)
    {
        this.cpu = cpu;
        this.priority = priority;
        this.userName = userName;
        this.process = process;
        this.process.SetDescriptor(this);

        registerImage = new RegisterImage();
        state = ProcessState.READY;

        parent = null;
        children = new ArrayList<>();
    }

    public void Run()
    {
        if (state != ProcessState.RUN)
        {
            Logger.debug(this + " is not in " + ProcessState.RUN + " state. Aborting");
            return;
        }

        int currentTicks = 0;
        while (!process.EndedWork() && currentTicks < maxTicks) {
            process.Step();

            ++currentTicks;
        }
    }

    public boolean Completed()
    {
        return process.EndedWork();
    }

    public ProcessDescriptor GetParent()
    {
        return parent;
    }

    public void SetParent(ProcessDescriptor parent)
    {
        if (parent != this)
            this.parent = parent;
    }

    public void AddChildProcess(ProcessDescriptor child)
    {
        children.add(child);
    }

    public void RemoveChildProcess(ProcessDescriptor child)
    {
        children.remove(child);
    }

    public ProcessState GetState()
    {
        return state;
    }

    public void SetState(ProcessState state)
    {
        Logger.debug("Changed " + this + " state from " + this.state + " to " + state);
        this.state = state;
    }

    public void SetId(int id)
    {
        this.id = id;
    }

    public int GetPriority()
    {
        return priority;
    }

    @Override
    public String toString()
    {
        return "'" + userName + "' (id: " + id + ")";
    }
}
