package Processes;

public class ReadFromInterface extends Process {
    public ReadFromInterface(ProcessManager manager) {
        super(manager);
    }

    @Override
    public void Step() {
        CompleteWork();
    }
}
