package Processes;

public class MainProc extends Process {
    public MainProc(ProcessManager manager) {
        super(manager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
