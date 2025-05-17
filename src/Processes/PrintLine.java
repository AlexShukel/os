package Processes;

public class PrintLine extends Process {
    public PrintLine(ProcessManager manager) {
        super(manager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
