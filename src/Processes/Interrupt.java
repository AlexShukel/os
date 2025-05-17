package Processes;

public class Interrupt extends Process {
    public Interrupt(ProcessManager manager) {
        super(manager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
