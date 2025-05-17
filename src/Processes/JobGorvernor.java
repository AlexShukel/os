package Processes;

public class JobGorvernor extends Process {
    public JobGorvernor(ProcessManager manager) {
        super(manager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
