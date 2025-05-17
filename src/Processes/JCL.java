package Processes;

public class JCL extends Process {
    public JCL(ProcessManager manager) {
        super(manager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
