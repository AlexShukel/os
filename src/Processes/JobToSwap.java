package Processes;

public class JobToSwap extends Process {
    public JobToSwap(ProcessManager manager) {
        super(manager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
